# Boss & rival scripts — implementation notes

Implemented in `engine/bosses.js`, ported from the decompiled state machines in
`reference/decompiled/` (`d.java` = player/AI tick, `ae.java` = monster kinds,
`ab.java` = renderer/scene). Cross-reference: `spec_engine.md` §15.2.

The original drives every boss encounter from a single **scene counter**
(`ab.c`, here `G.scene`) that dialogs, kills and world events advance in
lockstep. Boss levels ignore the usual exit tile: `d.a()` sets the exit flag
from the scene instead —

| levels | exit condition |
|---|---|
| 20, 23, 28, 32 | `scene >= 6` |
| 34 | `scene >= 2` |
| everything else | Gish overlaps a layer-1 tile id 13 |

## Level 14 — Hera's race (`waypointAI`)

`d.java` case 1. The race arms when Gish passes `x > 206438` (fixed point),
which sets scene 1 and fires dialog 61. From there the rival runs a ten-stage
route, each stage holding an ability plus direction keys and advancing on a
tile threshold. Stages 4, 5 and 8 fall *back* to an earlier stage if she drops
off the route, which is what stops her getting stuck:

| scene | ability | held | advance | fallback |
|---|---|---|---|---|
| 0 | – | right if `x<7`, left if `x>7` | (waits) | |
| 1 | normal | right | `x>=10` | |
| 2 | sticky | right | `x>=13` | |
| 3 | normal | right | `x>28` | |
| 4 | sticky | right+up | `y<=5` | → 2 if `x<29 && y>=10` |
| 5 | sticky | left+up | `x<27` | → 2 if `x<29 && y>=10` |
| 6 | normal | down+left | `x<=22` | |
| 7 | sticky | up+left | `y<=1` | |
| 8 | normal | right | `y>=18` | → 7 if `x<=30 && y>=5` |
| 9 | sticky | right | (final) | |

If she reaches an exit tile first, dialog 67 fires and the level restarts.

## Level 20 — Khafe's dark chase

- Darkness window starts at `46080` (45 px) and grows `+4608` (4.5 px) per frame
  while a background lava-glow tile (layer 0, id 30) is within the 3×3 around
  Gish; the first contact moves scene 1 → 3 and fires dialog 78. While the
  window is expanding the original draws plain 18 px corner triangles and only
  swaps in the `dark_corner_alpha` sprite (471) at exactly 46080 — reproduced.
- The boss (`ae` kind 4) burns when *he* ends up next to the lava with scene ≥ 3.
  This check is deliberately independent of Gish being alive, matching the
  original where it lives in the monster's own tick.

## Level 28 — the tentacle god

The boss (`ae` kind 5) reserves ten pillar slots (kind 6) and feeds tentacles up
from them (one per ~50 frames here). Each tentacle killed by a fast slam scores
+30; at **180** (six tentacles) dialog 110 fires, the scene advances to 2 and all
remaining tentacles die. From scene 2 the god itself dies as soon as he is on
screen, and his death **clears the three layer-1 tiles beneath him**
(`ae.java b()`), opening the route onward.

## Level 32 — the maw

At scene 0 the boss throws up the whole nest at once (all slots but the last),
at column offsets `boss.col + rnd(-6,-2)` for even slots and `+rnd(2,6)` for odd
ones. `ae.a()` means *dead*, so the gate `a[0].a() && a[1].a()` is **the first
two tentacles being killed** — not levers, as an earlier reading had it (level 32
ships with zero platforms, which is what settled it). With both down, moving Gish
above the mouth line (`centroid.y < boss.y + r`) gets him swallowed: dialog 118,
the boss dies, and his death **writes tile 69 two rows below him**. A surviving
tentacle in Gish's column then launches him clear with `(0, -5000)`.

## Level 34 — the duel

`d.java` case 3. At scene 0 the two platforms are held inactive; once dialog 128
("The walls of the cave are closing in to crush you!") is dismissed the scene
advances and both platforms start in mode 0 — these are the **crusher walls**.
Hera chases with sticky on, steering toward Gish and jumping while she has no
attack target and is above row 5.

She dies the same way Gish does: crushed (area below half rest) or on spikes.
Two supporting fixes made that reachable — **spikes now damage every blob, rivals
included**, and **striking a blob clears its grab springs**
(`((d)target.a).a.c()` in the original), which is how you peel her off a wall
into the closing walls. 100 ticks after she dies, dialog 129 fires and chains to
130; dismissing that advances the scene to 2 and opens the exit.

## Documented deviation

Boss death sets `scene = 6` directly on levels 20/23/28/32. In the original the
scene climbs to 6 through the post-death dialog chain, which depends on optional
hint-tile triggers; forcing it on the kill keeps the exit gate identical from the
player's point of view and removes any chance of a campaign dead-end. Everything
else above follows the decompiled logic.

All of it is covered by `tools/verify.js` section 8.
