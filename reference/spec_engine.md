# Gish Reloaded (J2ME) — Gameplay Engine Specification

Reverse-engineered from CFR decompilation of `Gish_Reloaded_360.jar`.
Class map (obfuscated → role):

| Class | Role |
|---|---|
| `Main` (com.hardwire.blob) | MIDlet, main loop, frame timing, sounds, collision-shape table, level loader |
| `k`    | Game session: level state, tile callbacks, scoring, MP sync |
| `ab`   | World renderer + per-frame game tick (`h()`), HUD, effects, camera |
| `af`   | Physics world: body lists, broadphase, tile & body collision routines |
| `h`    | Soft body (ring of point masses + springs) |
| `x`    | Point mass (verlet) |
| `ag`   | Spring / distance constraint (also "grab" springs and ropes) |
| `as`   | Particle (single point with radius): blood, gibs, entity proxies |
| `f`    | Static convex obstacle (moving-platform collision body) |
| `e`    | Edge anchor (point attached to an edge between two `x` at parameter t) |
| `d`    | Player blob (Gish) + AI blobs |
| `ae`   | Monsters / bosses / spawners / falling pillars |
| `ax`   | Dynamic boxes / planks / ball |
| `ac`   | Moving platforms (chained) |
| `u`    | Levers / buttons |
| `c`    | Bitmask broadphase grid |
| `bg`   | Fixed-point 2D vector |
| `al`   | Fixed-point math: sine/atan tables, RNG, AABB tests |
| `at`   | Bitmap fonts, texts, level list/order |
| `g`    | Canvas: screen, key mapping, image atlas, rotated-sprite helpers |
| `v`    | Polygon triangulator/rasterizer (fills soft-body/box shapes) |
| `s`, `r`, `o`, `w`, `q`, `p`, `ay`, `bb`, `bc`, `ba`, `an`, `j`, `ak` | Menus, Bluetooth MP, Zeemote controller, i18n, HTTP — **not part of the core engine** |

---

## 0. Numeric conventions, units, timing

### Fixed point
* **All world coordinates and physics values are 10-bit fixed point: `1024 == 1.0 pixel`.** Convert to pixels with `>> 10`.
* Tile coordinate: `worldFixed >> 15` (i.e. `pixel >> 5`), because **tile size = 32 px** (`32 << 10 = 32768 = 1 << 15`).
* `bg` vector ops (all fixed-point):
  * `mulFixed(v, s)`: `v.x = v.x * s >> 10` (64-bit intermediate).
  * `normalize()`: to length 1024 (fast paths: axis → ±1024, diagonal → ±724).
  * `length()` = `al.a(x, y)` — *approximate* magnitude (see below), **not** exact sqrt.
  * `lengthSq()` `d()` = `(x*x + y*y) >> 10` (fixed-point, i.e. len²·1024); `a()` long variant without shift.

### Angles (class `al`)
* Angle unit: **radian · 2²⁰**. Full circle = `6588397` (= 2π·1048576). Half = `3294198`, quarter = `1647099`.
* `al.a(angle)` normalizes into `[0, 6588397)`.
* `sin` via 256-entry quarter-wave table `al.a[]` (values 0…1024). `bg(angle, len, _)` constructor builds a vector `(cos,sin)·len>>10` — note it returns `(x = cos-like, y = sin-like)` with y **down-positive** screen convention:
  ```
  a = normalize(angle); mirrorY = a > 3294198 (then a = 6588397-a); mirrorX = a > 1647099 (then a = 3294198-a)
  i = a * 255 / 1647099
  x = table[255-i]; y = table[i];  if mirrorX: x=-x; if mirrorY: y=-y
  scale by len (fixed, >>10)
  ```
* `atan2` (`bg.a()` angle-of-vector) via 256-entry table `al.b[]` (0…804 ≈ π/4·1024), result in the same 6588397-period unit.
* **Fast magnitude** `al.a(x,y)` (used *everywhere* instead of sqrt — replicate exactly for fidelity):
  ```
  a=|x|; b=|y|; if a<b swap; b += b>>1;
  return a - (a>>5) - (a>>7) + (b>>2) + (b>>6)
  ```
* `al.b(min,max)` = uniform random int in [min,max].
* `al.a(r, n)` = ring of `n` points: `point[i] = bg(i*6588397/n, r<<10)` (radius r px, fixed-point result).
* Sprite-rotation frame pickers (class `g`):
  * 16 frames: `frame = (al.a(angle + 205887) << 4) / 6588397` (offset = half of 1/16 turn).
  * 32 frames: `frame = (al.a(angle + 102943) << 5) / 6588397`. Only 8 base images exist; frames 8-15/16-23/24-31 are transforms (J2ME transform 5 = ROT90, 3 = ROT180, 6 = ROT270).

### Timing
`Main.a()` sets frame interval `Main.b` (ms) and **physics substep count `Main.c` per rendered frame**, by quality setting `Main.d` (default 2):

| quality `d` | frame ms (`a`=MP, `b`=SP) | substeps `Main.c` |
|---|---|---|
| 0 | 160 | 4 |
| 1 | 115 | 3 |
| **2 (default)** | **70** | **2** |
| 3 | 31 | 1 |

All physics constants below are **per substep** and are *not* rescaled with quality — the game literally runs different dynamics per quality; the shipped default is **70 ms frame, 2 substeps ⇒ ~14.3 fps render, ~28.6 Hz physics**. For a JS port, pick quality 2 semantics: `dt` is implicit (constants are already "per step").
Game timer: `k.k += Main.b` once per rendered frame (ms).

---

## 1. Point mass — class `x` (verlet)

```
x { bg pos;        // a  (fixed)
    bg prev;       // b
    bg force;      // c  (accumulated per step)
    int mass;      // a: 1024 = 1.0; Integer.MAX_VALUE = pinned/static
    bg tmp;        // d
    int flags; }   // b (bitfield, see below)
```

* `applyForce(f)` (`a(bg)`): **`force += f * mass >> 10`** (skip if pinned). (Mass-proportional because integration divides by mass again ⇒ `h.applyToAll(g)` yields equal acceleration for all masses; forces added *directly* to `force` — pressure, buoyancy, torque — are divided by mass, so heavier points respond less.)
* `integrate()` (`a()`):
  ```
  if pinned: prev = pos; return
  tmp = pos
  pos = 2*pos - prev + (force << 10) / mass     // mass 1024 → +force; 2048 → +force/2
  prev = tmp
  force = 0
  ```
  **No global damping** — energy is only removed by collision friction, the speed clamp, and constraint projection.
* `flags` (`x.b`) bits: `1` collided this step, `2` collided with tile/static, `4` currently held by a grab spring, `8` grab-spring bookkeeping, `0x10` deleted/detached (skip everywhere), `0x20` rope endpoint marker (on delete of spring → mark 0x10), `0x40` touched a moving platform `f`.

## 2. Spring / constraint — class `ag`

```
ag(xA, xB | bg anchor | e edgeAnchor, stiffness, limit, restLen)
   restLen (a): if -1 → measured from current positions (via al.a fast length)
   limit  (c): break/slack threshold. If limit < -1 it is relative: limit = restLen * (-limit) >> 10
               (e.g. -1536 ⇒ 1.5×restLen). -1 = never breaks.
   type   (a byte): 0 normal; 1 = pull-only (no force when shorter than rest);
                    2 = push-only.
```

Positional relaxation `solve(checkBreak)` per iteration:
```
if either endpoint has flag 0x10 → report "break" (true)
delta = posA - posB(or anchor);  len = al.a(delta)          // fast length
if len == 0 → false
if checkBreak && limit != -1 &&
   ((limit > rest && len > limit) || (limit < rest && len < limit)) → true (break)
err = len - rest
if type==1 && err<0 → false;  if type==2 && err>0 → false
if stiffness==512: err >>= 1; elif stiffness<1024: err = stiffness*err>>10
dir = delta * 1024/len
split err between endpoints ∝ inverse mass (equal masses → half/half; anchor or pinned → all on free end)
posA -= dir*shareA>>10 ;  posB += dir*shareB>>10
```
Edge-anchored variant (`e{xA,xB,t}`): target point = lerp(xA.pos, xB.pos, t/1024); correction distributed `(1-t)`/`t` between the two edge points.

**Spring parameter sets used:**

| Use | stiffness | rest | limit |
|---|---|---|---|
| Player body: "spoke-pair" springs (see §4) | 1024 | natural | -1 (unbreakable) |
| Box perimeter springs | 1024 | natural | -512 (breaks < 0.5×rest ⇒ crushed box) |
| Box cross-brace (point i ↔ point i+n/2) | 1024 | natural | -512 |
| Ball (type 5) alternate perimeter | 512 | natural | -512 |
| Sticky grab → tile geometry | **204** | 0 | **10240** (10 px) |
| Sticky grab → moving platform `f` | 512 | 0 | 5120 |
| Sticky grab → other soft body | 512 | 0 | 10240 |
| Ropes, type from level nibble `t` 0-5: stiffness `k.a[t]`={1024,1024,682,341,1024,341}; limit `k.b[t]`={-1,-1,-1536,-1,-1,-1536}; rest `k.c[t]`={-1,-1,-1,-1,32768,-1}; type-1 ropes are pull-only |

## 3. Soft body — class `h`

A closed ring of `x` points, perimeter springs, optional pressure & grabbing.

```
h(pressure, hasArea, canGrab, fixCenter, hasRenderPoints)
  a  int      pressure        (player 0; ball ax type5: 100000)
  d  int      flags: 1=STICKY grabbing enabled (runtime), 2=EDGE-FRICTION mode,
              4=AREA-PRESERVE (hasArea), 8=CAN-GRAB (allocates grab array),
              0x40=render-points copy, 0x80=fixed centroid, 0x10=asleep/off-view, 0x20=destroyed
  c  int      friction 0..1024 (default 1024)
  h  int      spring iterations per substep (player & seesaw: 2, others: 1)
  j  int      type tag: 1=player, 3=box(ax), 4=deadly body; used for contact callbacks
  e,f int     rest area, rest perimeter (captured at build when flag 4)
  a  x[]      ring points;  a ag[] ring springs;  b ag[] grab springs (size = #points), g = count
  c  x[]      extra points (rope nodes attached to this body)
  c  ag[]     extra springs (rope segments)
  a  h[]      linked bodies (wake side-by-side)
  b  int      per-step status bits (1 = had any collision → speed clamp applies)
```

* **Ring construction** `a(stiffEven, stiffOdd, restEven, restOdd)`: springs (p0,p1),(p2,p3)… with `stiffEven/restEven`; then (p1,p2),(p3,p4)… with `stiffOdd/restOdd`. Player: `(1024,1024,-1,-1)` (all natural length, rigid projection). Boxes add cross braces.
* **Area** `a()` (shoelace, fixed): `sum += p[i-1].x * (p[i].y - p[i-2].y) >> 10; return sum >> 1`.
* **Centroid** `a()` — average of non-deleted points (cached per step).
* **Contact normal** `b()` — average of collided points (flag 1&4, or flag 2 if any static contact) minus centroid, i.e. "average contact direction"; if *all* points collided → (0,1024). Used for jump/ground checks.
* **Velocity clamp** `e()` (applied only when body collided this step, `b & 1`): per point, if `|v|² > 0x4000000` (i.e. |pos-prev| > 8192 = **8 px/substep**) then `prev = pos - normalize(v)*8` (`<<13>>10`).
* **Buoyancy** `a(surfaceY, force, dampShift, wholeBody)`:
  ```
  frac = ((bbox.bottom - surfaceY) << 10) / bbox.height, clamped ≤ 1024
  if frac > 0:
     f = -(force * frac) >> 9                       // up to -2*force
     f -= avgVelocity.y >> dampShift                // vertical drag
     if wholeBody: applyForce((0,f)) to all points
     else:        applyForce((0,f)) to points with pos.y ≥ surfaceY
  ```
* **Point-in-polygon** `a(bg)` — even-odd ray crossing (used for body-vs-body deep test and pickup tests).

## 4. The player — class `d`

### Body
* Ring of **18 points, radius 24 px** (small blob `e==1`: 14 pts r=20; `e==2`: 18 pts r=26; `e==3`: 18 pts r=30), each **mass 1024**, spring set `(1024,1024,-1,-1)`, flags: AREA-PRESERVE + CAN-GRAB, `j=1`, **iterations `h=2`**, friction default 1024.
* Health `d.d` = **102400** (100.0 fixed). HUD shows `d/1024` percent.
* Fields: `b,c` requested/active ability; `d,e,f,g` = up/down/left/right key held; `h` = attack key held; `h,i` (ints) analog stick −127..127; `a` bool inWater; `e` int attack-window timer; `j` byte climb-assist bits.

### Abilities (state `c`: 0 = normal, 1 = **slick**, 2 = **sticky**)
Applied every tick:
```
body.friction = (c == 1) ? 0 : 1024        // slick: frictionless contacts
body.flags: STICKY(1) set iff c == 2       // sticky: spawn grab springs on contact
```
* Toggle keys: `*` (action 9) toggles sticky (`b = c==2 ? 0 : 2`), `#` (action 10) toggles slick (`b = c==1 ? 0 : 1`). Left soft key / on-screen button cycles 0→2→1→0. When leaving sticky, all grab springs are released (`clearGrabs()`).
* **Sticky mechanics**: during tile/platform/body collision resolution (see §6), if body has STICKY and the point isn't already held (`flag 4`), a grab spring is created from that point to the contact location (`ag(point, worldAnchor, 204, breakLen 10240, type 0)` for tiles). Grab springs are solved each substep with break-check; broken/overstretched ones are removed. A body "isAttached" iff any grab spring exists — grants full climbing force.
* **Heavy** (original Gish's heavy) is not a separate state in this port: holding **down** applies a large downward force (see below) — that is the slam/heavy behavior.

### Movement forces (per substep, applied to all non-colliding-free points; see code path)
Let `attached` = sticky and ≥1 grab spring active (or any grab existing when sticky). Forces (fixed-point px/step²·1024, added via `applyForce` so equal acceleration on all points):

```
horizontal (left/right key):  Fx = ±(attached ? 150 : (inWater && !(modeBit2) ? 100 : 50))
analog: scaled by h/127
up key, not grounded (air):   Fy = -100          (only when not inWater)
up key, grounded:             jump impulse (below)
down key:                     Fy = +(modeBit2 ? 500 : inWater ? 400 : 100)   // heavy slam
AI blobs: total force vector scaled ×1945/1024 (small) or ×1331/1024 (big types 2,3)
```
The force vector is added to every point whose flags don't have (1|4) (not currently colliding or held).

### Jump
Conditions: up pressed (edge-trigger; analog `i < -100`), contact normal `n = body.contactNormal()` satisfies `|n.x| < n.y` and `n.y > 0` (standing on ground-ish), mode allows jump. Optional two-phase "compress" (`y` counter) when mode bit 2: jump fires when `y > 10` ticks or at `y==2` if body height < `radius*1.4` (squashed). Impulse:
```
up = (0, -1024)
proj[i] = dot(p[i].pos, up) >> 10 ;  min,max over points; range = max - min
contacts = count of points with collision flag 1
if range != 0 and contacts > 0:
    up *= 25000/1024                        // bg.a(25000)
    up *= ((contacts << 20) / (range * n)) / 1024
    for each point i with no collision flag: force += up * (proj[i] - min) >> 10
```
(Top points get pushed hardest; net impulse ≈ 12.2·contacts px/step of upward velocity distributed.) Plays sound 0. If sticky, grabs are cleared after jumping.

### Rolling torque
When moving (direction from keys/analog) and not attached and not jumping straight up off the ground:
```
side = sign(cross(contactNormal, inputDir)) → −1/+1
mag = side * (inWater||attached==0 && inWater ? 200 : 300)   // 200 in water, else 300; scaled by analog length/127
for each point: r = p.pos - centroid
    t = mag<<10 / al.a(r.y, r.x)
    force += (-r.y * t >> 10, r.x * t >> 10)      // tangential, spins the blob
```

### Climb assist (`j` bits, set by tile-contact callback `k.a(h,cx,cy)` when sticky on climbable shapes)
Per shape id, `k.a short[29] = {0,4,8,2,4,12,10,2,8,6,12,14,10,0,14,32,16,128,64,0,0,0,0,40,24,128,64,8,0}` gives hint bits; applied as extra forces: bits `0x82` → +150 x; `0x44` → −150 x; `0xC0` → −350 y; `0x08` → −700 y. Cleared each tick.

### Attack ("bite", sound `bobattack`-family)
Touching another soft body registers it for 3 ticks (`d.a(h)` from collision callback). If attack key pressed while a target is registered:
```
dir = normalize(target.centroid - my.centroid)
target.applyToAll(dir * (targetIsSmallBlob ? 5700 : targetIsPlayer ? 2800 : 11400))
my.applyToAll(dir * -(512|1024|256)/1024-scaled)   // recoil: −0.5/−1.0/−0.25 of dir
plays sound 0 (gishhit); attack window e = 56 when pressed with e < −20
```
While `e > 0`, the player kills monsters on touch regardless of speed.

### Water
Each substep the player scans layer-2 tiles overlapping its AABB; ids **6, 7 = water, 36 = deadly water** (sets `k.c=true`). If found (`row` = topmost water row):
* buoyancy `body.a(row<<15, 200, 5, false)` (force 200, vertical drag v/32, applied to submerged points);
* splash on entry when `|avgVel|² > 10240`: sound 7, splash effect (type-2 effect, sprites 464-467), bubble particles;
* deadly water: `damage(2048)` per substep (2 HP).

### Damage & death (`d.b(dmg)`)
* `hurtFlash c=2`, `health -= dmg`; vibrate.
* Blood particles: on damage (detail>1), spawn up to 16 `as` particles (mass 2048, radius 5120) inside bbox with downward initial velocity, drawn dark-red, live ~30 frames.
* Sources: spikes (main tiles 7,10,11,12,68,71,72) = **1024/contact-substep**; deadly water 2048/substep; monsters `ae.b[type]` = {10240, 20480, 0, −, 2560, 512, 512}; being **crushed**: if `|area| < restArea/2` → `damage(102400)` (instant, except level 32).
* At health ≤ 0: death state `d.d=1` — body dissolved into particles (`as` radius 4096 per ring point), sound 4 (squish) if on-screen; in story mode after 100 ticks → restart level (`k.d=2`). In coop, a dead player's particles can be eaten by the partner (contact with `as.d==6`): each eaten removes one; when all eaten the dead player respawns at a free tile next to the partner with `health >>= 2` (quarter) — "rebirth".

### Face rendering (cosmetic)
Eyes track the nearest interesting tile (amber/health/exit within 8 tiles, line-of-sight via DDA raycast `ab.a(x1,y1,x2,y2)` over the collision map); pupil angle smoothed by 1/6 per frame in 6588397-units; blink (`o` counter, random when looking straight), yawn (`q`), hurt face (`s,t`), 16-frame rotated face sprites (base 110 normal skin / 516 gray skin; hurt 125/525 + 126-128 overlay). Skin color: normal `#000000`-ish (a[]={-16777216,-15592170}), gray blob `0x999999`; slick body outline `0xCCCCCC`-ish, sticky `0x636363`/`0xBBBBBB`; blood dots colored by skin.

## 5. Controls

Key → action id (`g.a(keyCode)`): `2/UP→0 up`, `8/DOWN→1 down`, `4/LEFT→2 left`, `6/RIGHT→3 right`, `1→4 up+left`, `3→5 up+right`, `7→6 down+left`, `9→7 down+right`, `5/FIRE→8 action`, `*→9 sticky toggle`, `#→10 slick toggle`, `0→11 action`. Left softkey (−6) = ability cycle / dialog advance; right softkey (−7) = pause menu. Actions 0-7 set/clear `d.d,e,f,g`; 8/11 sets `d.h` (attack); pointer/touch: corners map to soft buttons, drag from center = virtual analog (`am` events → `d.h,i`).

## 6. Physics world & step — `af` + `ab.h()`

### World data
* `af.e` **gravity = 200** (≈0.195 px/substep², down). Set at level load.
* `af.d = 15` — grid shift (tile = 1<<15 fixed).
* `af.a byte[][]` — **collision map**: per tile, shape id into `af.a bg[][]` (29 shapes) or −1 = empty.
* `af.b byte[][]` — second per-tile map (main-layer tile id) used with `af.a boolean[]` (`k.g[73]`) = per-tile-id "normal friction/climbable" flag; when false the surface gives **zero friction** (ice/metal) and no climb hints. `k.g` false at ids {6,8,9,13,69,70}.
* Body lists: `h[]` soft bodies, `as[]` particles, `f[]` static/moving convex obstacles, each with a `c` broadphase (bitmask-per-row/column grid, 32-px cells) — replaceable by any AABB broadphase.
* Active set: only bodies whose AABB intersects the **activity rect** (camera view ±1 tile, expanded by moving-platform sweep) are simulated; the rest sleep (`d |= 0x10`). In special levels (0,14,20,23,25,28,32,34 and versus) everything is active.

### Collision shape table (built in `Main.e()`, all fixed-point, per-tile local coords)
Corner points: `A(-512,-512) B(32768,-512) C(-512,32768) D(32768,32768)` (i.e. tile edges inset −0.5 px), `E(512,-512) F(32256,-512) G(-512,32256) H(33280,32256)`.
Shapes (id: polyline; `null` separates disjoint segments; a *closed* shape repeats first point):
```
0 top edge A→B          1 right B→D          2 bottom D→C        3 left C→A
4 A→B→D (top+right)     5 B→D→C              6 D→C→A             7 C→A→B
8 A→B, null, D→C        9 B→D, null, C→A     (opposite pairs)
10 A→B→D→C  11 B→D→C→A  12 D→C→A→B  13 C→A→B→D   (three edges)
14 A→B→D→C→A (full box; also used for out-of-bounds)
15 E→H  (slope ╲ 45°, from (0.5,-0.5) to (32.5,31.5))
16 G→F  (slope ╱)
17 H+(-1,1) → E+(-1,1)  (underside of ╲)     18 F+(1,1) → G+(1,1) (underside of ╱)
19 (Cx,15360)→(Dx,15360)   half-height floor at y=15 px (spike surface)
20 (15360,Dy)→(15360,By)   half-width wall
21 (Bx,17408)→(Ax,17408)   ceiling at y=17
22 (17408,Ay)→(17408,Cy)   wall at x=17
23 E→H→G   24 H→G→F   25 slope 17 + edge   26 slope 18 + edge
27 (Ax,15360)→A→B→(Bx,15360)→(Ax,15360)  channel (three sides up to y=15)
28 (8192,Ay)→(24576,Ay)→(24576,Dy)→(8192,Dy)→close  centre column (8..24 px)
```
**Shape assignment** (`Main.a(cx,cy)` — rebuild cell whenever a tile is destroyed): main-layer tile at (cx,cy) classifies as: passable (−1: empty, 8, 9, 13, 43, 70), full-solid (1: most solid ids), special (0). Full-solid picks an edge combination by which 4-neighbours are non-solid (4 open → also check diagonals, 13/10/11/12 corner or 14; 3 open → shapes 0-3/10-13; 2 open adjacent → 4-7 (or 14 if diagonal solid), 2 open opposite → 8/9; 1 open → 10-13; 0 open → 14). Special tiles: ids {1,16,37,65}→15 (23 if tile below solid), {2,17,38,64}→16/24, {3,39,66}→17/25, {4,40,67}→18/26, {7,68}→19, {71}→20, {72}→22, {10,11,12}→20/21/22, {69}→28.

### Per-substep pipeline (`ab.h()`, exactly this order, ×`Main.c` per frame)
1. Tick players `d.a()` (input forces, water, pickups by AABB overlap of layer-1 tiles, AI). Tick monsters `ae.a()`, boxes `ax` (rest/water/respawn), platform riders. Move platforms `ac` (linear interpolate `pos = lerp(A,B, tri(t/b))`, ping-pong; carry `f.a(newPos)` which shifts every stuck grab anchor with it). Levers `u` reset/`apply`.
2. `af.a()` — collect awake body/particle sets (+ linked bodies, + particles attached to awake ones).
3. **Pressure** (bodies with `pressure > 0`, e.g. ball): `A = max(|area|, 32768)`; per ring edge (prev,cur): `n = (cur.y-prev.y, prev.x-cur.x)` (outward), `f = n * pressure / A`, add `f` **directly** to both endpoints' `force` (not mass-scaled).
4. **Gravity** `(0, 200)` via `applyForce` on every awake body, its rope nodes and awake particles.
5. **Integrate** all points (rope springs' anchors too); deleted rope nodes (`flag 0x10`) removed.
6. **Area preservation** (flag 4): `diff = restArea - |area|`; `s = (diff<<10)/restPerimeter`; per point, using neighbouring edge (prev→next): `n = (prevY-nextY? see code: (dy, dx) of the two neighbors)`, normalize by fast length, `pos += s * n / len` — inflates/deflates along local normal.
7. **Speed clamp** `e()` for bodies with collision bit — max 8 px/substep.
8. **Rope/extra springs** (`h.c ag[]`): solve once with break check → broken ropes play sound 9 and are removed from `k.a ag[]`.
9. **Perimeter springs**: `for it in 0..h.h-1: for each ring spring: solve(breakable)` — `breakable = (body.b&4)&&(body.b&8)`; if a spring reports break → body destroyed (`flags |= 0x20`, removed; boxes → `ax.b()` gib into particles + effect). Bodies with fixed-centroid flag (0x80) re-center: translate all points so centroid == stored `c`.
10. **Grab springs**: solve each with break check; broken ones removed (point flags 4|8 cleared).
11. Prune dead anchor points of moving platforms (`f.b[]` list).
12. Clear per-step flags; update broadphase entries (bodies and particles).
13. **Body ↔ body** narrowphase for pairs of AABB-overlapping awake bodies:
    * If both have EDGE-FRICTION flag (2): SAT over all edge normals of both polygons (`af.a(pointsA, pointsB, axes, depths, i)` computes interval overlap; earliest-axis MTV), then find up to 2 support points on each side (`af.a(dir, points, out)` — points within 1024 of min projection), split the MTV between the two bodies ∝ point masses and edge parameters (see decompiled block — port as standard SAT + support-point positional resolution), plus tangential friction impulse scaled by `(cA+cB)/2` applied to `prev` (velocity change).
    * Else (a blob involved): **vertex-in-polygon** resolution `af.a(hA,hB)` both directions: for each vertex of A inside B, find nearest edge/vertex of B (preferring the side matching A's local surface normal), push vertex and edge points apart by penetration split ∝ masses (mass-ratio fast paths 512/682/341), apply friction `(cA+cB)/2` to velocities, and if either body is STICKY, attach a grab spring `ag(vertex, edgeAnchor e(t), 512, 10240, 0)`.
    * On any contact: both bodies get collision bit; callback `k.a(hA,hB)`: registers attack targets (`j==1` vs any), and `j==1` vs `j==4` (deadly body) → mark player "touched deadly" (`d.b=true` → crush/hazard logic).
14. **Particle ↔ particle** (`as`): circle-circle positional separation ∝ masses.
15. **Particle ↔ soft body**: circle vs polygon vertices and edges — positional resolution with mass split + friction impulse (scale `as.b`, default 102 ≈ 0.1 for entity particles, 0 for effects); on touch, callback `af.a.a(h, as)` → `k.a(h,as)`: player collects/kills monsters, boxes crush monsters, dead-player gibs eaten.
16. **Soft body ↔ tiles** (the core): for each awake body:
    * per point: cell = `pos >> 15`; out-of-bounds → resolve against shape 14; else if `collMap[cell] != -1`, run `af.a(x, shape, localX, localY, friction*)` where `friction* = (k.g[mainTileId] ? body.friction : 0)`:
      - For **closed** shapes (first==last): for each edge, if point is outside any edge half-plane → null (no collision); else find least-penetration edge, push point out along its normal by that depth, then apply friction to velocity: `friction==1024 → prev = pos` (full stop); else `prev += (pos-prev)*friction>>10`.
      - For **open** polylines `af.a(x, radius?, shape...)` (point treated as circle of radius 0 here; particles use their radius): project onto each segment, handle endpoint caps, signed distance = `dot(n, p-a) + r`; negative → push out along segment normal; same friction application.
      - If pushed into a neighbouring cell with another shape, re-resolve once there.
      - On contact: flags `|= 3`, body bits `|= 7`; **sticky**: if body STICKY and point not held → add grab spring `(point, worldPos, 204, 10240)`, flags `|= 0xC`.
      - Record touched cell (≤15 per body) → tile callbacks `k.a(h, cx, cy)` afterwards (levers, breakables, spikes, climb hints — §7).
    * per ring **edge** (pairs of consecutive points): for each overlapped cell with a shape, `af.a(x1,x2, shapeEdges, …, friction, useTangentFriction=(flags&2))`: clip each shape edge against the moving segment; find deepest crossing along segment normal; push both segment endpoints by MTV; friction: tangential impulse mode (bodies with flag 2 — boxes) or positional damping mode (blobs).
17. **Soft body ↔ moving platforms `f`**: same point & edge routines against the platform polygon (offset by platform position); on contact flags `|= 0x43`; sticky attaches `(point, worldAnchor, 512, 5120)` and registers the anchor with the platform so it moves along.
18. **Particles ↔ tiles/platforms**: circle versions of the above.
19. Lever hold bookkeeping (`u`: 10-tick grace `b`, press/release edges call `u.b()/u.a()` → start/stop platforms; sound 5).
20. After the loop: rope cleanup (broken → sound 9), score/lever/exit checks, and (level 20) recompute the darkness window rect (±45 px around player, `m=46080`).

Frame-level (once per render): `k.k += Main.b` (timer); camera update; dialog/tutorial triggers (`k.a short[][] a` positions → dialog id, opens text page state 6); level-end checks.

## 7. Tile map

* Loaded from `/levels/<name>.lvl`: `width, height, theme` bytes, then object lists (players/monsters, boxes `ax` (x,y,type), platforms `ac` + levers `u`, ropes), then **3 layers × width × height bytes column-major, y flipped, value−1** (`-1` = empty), then sign/trigger list.
* Layers: `a[0]` background (drawn behind, sprite base **276**), `a[1]` main/game layer (sprite base **566**), `a[2]` foreground/overlay (sprite base **407**).
* `ab.a int[] = {276, 566, 407}` sprite bases; theme index `k.a.a` picks palette variants of decorations; `ab.e[12]={0,0,0,0,1,1,1,1,2,2,2,2}` theme per menu-background block; `ab.a byte[11][3][10][4]` are the menu background map chunks.

### Main layer (`a[1]`) special ids
| id | meaning |
|---|---|
| 8 | **health/tar pickup** (+10240 HP, sound 1, effect 5, tile removed) — drawn as bobbing sprite 46 |
| 9 | **score amber** (+10 score, sound 2, effect 4, removed) — sprite 47-49 pulse |
| 43 | **collectible amber** (`k.f[player]++`, counts toward `k.l` total; achievement; removed; invisible on map — position list `k.a int[][]`) |
| 70 | **achievement pickup** (bobbing, opens achievement dialog, removed) |
| 13 | in-water marker (sets player inWater without splash; invisible) |
| 14 | **breakable block** (`k.a[14]` only true entry). Broken by body contact when `|avgVel|² > 0x1E00000` (|v|≳5.5 px/step) or by attacking AI: sound 6, effect at centre, +16 debris particles, tile removed from layer1 *and* collision map, neighbouring collision shapes recomputed, nearby grab springs released |
| 7, 10, 11, 12, 68, 71, 72 | **spikes** (damage 1024 per touching substep) |
| 69 | narrow column (collision 28) |
| 1-4, 16-17, 37-40, 64-67 | slopes (collision 15-18/23-26) |
| others (0,5,6,15,18-42,44-63…) | solid décor (full/edge collision, per neighbour rule) |

### Foreground layer (`a[2]`) special ids
* **6, 7 = water**, **36 = deadly water/acid** (k.c), 18 = underwater décor (bubbles rise through 6,7,18);
* 37 = waterfall (animated sprites 460-463, 4 frames at half frame rate);
* 17,18,41-45,52-55 = overlay pieces remapped into main tileset sprites (see `ab` render switch: 17→566+0, 18→566+28, 41→+55, 42→+62, 43→+52, 44→+59, 45→+50);
* on low detail (`g==0`) only water/waterfall/essential overlays draw.

### Background layer (`a[0]`) specials
* 30 = animated lava/glow (sprites 272-275, frame = tick&3); 8/9 remap to 5/0 plus torch-flame overlay (sprites 566+8+flame frame, 2 frames at ¼ rate); 11/51 = darkness filler (skipped).

## 8. Entities

### Monsters — `ae` (type `d`)
Sprite tables (`a`= 256+id bases, frames `a`, ticks/frame `b`): rows per type via `a[]={0,1,2,-1,3,4,5}`. Animation states (`a` byte): 0 idle/walk, 3 turn, 1 dying, 2 dead, 4 attack, 5 squished, 6 special. Per-type params (index by type): chase-range² `g`, attack-range² `h`, speed `f`, turn-threshold `i`, damage `b`, forget-range² `e`.

| type | who | body | behavior |
|---|---|---|---|
| 0 | **walker** (small monster) | `as` r=12288, mass 6144 | wanders/chases nearest live player within `g=0x300000`; force ±`f=500` (halved variants vs walls); turn anim when |v|>i=500; attack anim within `h=0x1E6666`, damage 10240 at attack frame (`c[0]=3`, tick 1, sound 10 `bobattack` + MP event); killed by fast player contact (+30 score, effect, sound 4 squish) or crushing box |
| 1 | **hanger** ("necksnap") | two `as` (head r=13312 + body r=14336, mass 16384, vertical link `b`) | keeps head above body (x averaged, y separated by rest `b`); same chase/attack (f=400, dmg 20480); death sound 8 **necksnap** |
| 2 | **spawner** | `as` r=15360, mass 1e6 (≈static) | on cycle frame (`c[2]=4`, tick 1) revives a dead type-0 slot: spawns walker 23 px above itself with random initial velocity, sound 11 `visattack` |
| 4 | **boss walker** | `as` r=39936 | steered chase (velocity `b` grows by `f/30` up to `f=2764`), damage 2560; scripted per level 20 (chases through the dark level, triggers dialogs 92/118, killed by lava tiles 30 → state sequence c=3,4,5) |
| 5 | **tower boss** | static `as` r=45056 | levels 28/32: spawns falling pillars (type 6) at random columns near itself, hit points via level script (attacked when player fast-touches; `a=200` HP on lvl 28); on death clears tiles under it; damage 512 |
| 6 | **falling pillar** | static `as` r=13312, extends `c` tiles vertically | slides out over `a.b += 3`/tick to length 32·|c| px, damages 512 on AABB overlap with player; retracts/dies with boss |

Death: `b()` — anim 1, sound (`d==1 ? 8 : 4`) if visible, body removed from particle world, +30 score & effect when killed by player/box.

### Boxes / planks / ball — `ax` (type `b`)
Soft bodies built from rectangles (mass **2048** per point; type 4 crate: **51200**), perimeter+cross springs stiffness 1024 rest natural, break at 0.5×rest (crush ⇒ `b()`: gibs = each point → particle, effect 0, removed; can respawn at origin cell when the area is clear).
Types: 0 = 32×32 box, 7 = 64×32, 1/2 = 96×32 plank, 8 = 128×32, 11 = 256×32, 3/6/10 = vertical planks (32×96 / L-shaped / 32×128), 4 = 64×64 heavy crate, 9 = 96×21 **seesaw** (iterations 2), 5 = **ball** (10 points r=16, pressure 100000, no edge-friction flag, rendered with eyes — the "companion ball" in the menu level).
Rendering: filled polygon (triangulated by `v`), wood grain lines by type; colors: crate `-14408668/-14009814`, seesaw `-12630984/-11117743`, planks `-9215145/-6715787` (day) or `-12699344/-14212321` (dark levels).
Water: buoyancy per substep when overlapping water rows — force `600` (ball in sewer theme), `3000` (planks in sewer), else `200`, drag shift 6, whole-body when ≤4 points.

### Moving platforms — `ac` + levers `u`
`ac(type, A, B, speed)`: convex polygon (96×32, 32×96, 64×32, 32×288 — chains drawn with sprite 235 links), position parameter `a` 0…`b` (=`dist(A,B)/(speed*500)`), advances 1/tick while active; triangle-wave ping-pong between A and B; `f.a(newPos)` translates the collision polygon **and every grab-spring anchor attached**, so Gish rides it. Modes (`a(byte,int)`): 0 = move once/forth, 1 = loop; levers `u(type 1-4)` at a tile: player/box body overlapping the tile top (within 4096 of the surface) presses it (sprite 79 raises/lowers over 5 frames, sound 5 switch); types: 1 = one-shot start, 2 = one-shot loop-start, 3 = hold-to-run, 4 = toggle direction.

### Ropes
Chains of `x` (mass 1024) loaded from the level; ends attach to nearest box ring point (within 8192) or to fixed `bg` anchors; segments are `ag` springs (§2 table). Rendered as 32-frame rotated link sprites (`g.a(g, images, pos, angle)` along the segment) or plain line on low detail. Break (over-limit) → removed + sound 9.

### Effects (all in `ab`)
* **Explosion/pickup queue** `b[10][4]` (`ab.a(x,y,type)`): type 0 gib-burst sprites 150-153; 1 = same mirrored ×4; 2 = splash 464-467 (bottom-anchored); 4 = amber sparkle 130-132; 5 = health sparkle 134-136; advance every 2nd frame, 4 frames.
* **Score popups** `a[10][4]` (`ab.a(x,y,value,ttl)`): draws "+value" with tiny font rising 3 px/frame.
* **Particles** `c[25][6]` (`ab.b(x,y,spread,kind)`): kind 0 = blood/debris burst (5 at once, vel x∈[−3072,3072], y∈[−4096,2048], gravity +1024/frame, ttl 15); 1 = rain/dust (2×, only on odd frames, vel y∈[−5120,−3072], ttl 10); 2 = bubble (single, vel (0,−5120), lives while inside water tiles 6/7/18). Colors: {−8754086, −4605658, −5593722}.

## 9. Camera & rendering

### Camera (`ab.g()`, ints `k,l` = top-left in px)
* Default: centred on player centroid: `k = cx>>10 − screenW/2; l = cy>>10 − screenH/2`.
* Level 20 (dark/boss chase): x anchored ahead: `k = cx>>10 − screenW + max(2*screenW/5, 50)`.
* Menu level (0): scripted (follow intro ball etc.).
* Free-look (`e==2/3` cheat/spectate): arrows pan ±10 px/frame.
* Clamped to `[0, mapW*32−screenW] × [0, mapH*32−screenH]`; if map narrower than screen, x=0; if shorter, y pinned to bottom overflow. View rect `d[4] = {k,l,k+W,l+H} << 10`.
* Map-view state (`c==9`): smoothed pan toward a target with spring `p += (target-p)*0.5` and damping ×621/1024 (used for level fly-bys).

### Draw order (`ab.b(Graphics)`) — translate by (−k,−l)
1. Background fill: theme color (`theme 2: 0x559FFF sky; 1: 0x262229? (2499085); else 0x001414 (5140)`); menu level: starfield.
2. Parallax backdrop (detail>1): image `468+theme`; theme 2: horizon strip tiled with x-parallax `k/5`; else tiled over the bounding box of solid layer-0 area, 64-px grid.
3. Layer 0 tiles (sprites 276+id; animated 30 and torches).
4. Button bases (sprite 77/78), boxes/planks/ball (`ax`), rope-attach visuals.
5. Players & AI blobs (`d.a(Graphics)`: filled polygon body, gloss, face), levers (sprite 79), tall entities (ae types 5,6 behind main layer).
6. Water particles/bubbles.
7. Platforms `ac` (chain sprite 235 tiles).
8. Layer 1 tiles (sprites 566+id; pickups bob: tile 8 sine-bob 4-frame, tile 9 pulse 47-49, tile 70 bob ±2 px).
9. Monsters (`ae.d < 4`).
10. Effect queue sprites; level-23 fireflies (sprites 531-534).
11. Ropes (rotated link sprites; brown line on low detail).
12. Layer 2 foreground tiles (water drawn *over* everything with alpha PNGs `6_alpha/7_alpha/36_alpha`, waterfall 460-463).
13. Level 20 **darkness**: fill black outside the window rect (player ±45 px, expands +4.5 px/frame near lava glow tile 30 up to full view), corners drawn with sprite **471 `dark_corner_alpha.png`** in 4 mirrored orientations (`b[0..3]`, transforms 0/5/3/6); while expanding draws black triangles (18 px) instead.
14. Boss entities (`d==4` on 20/23).
15. Debris/score popups; tutorial **ability arrows**: mask `ab.a short[level]`; bits 1..6 draw arrow sprites `256+ab.b[bit]` around the player at ±40 px, bits 0x400/0x800 draw sticky/slick hints bottom-left; suppressed once the corresponding input is used or player moved >150 px (`d²>23040000`).
16. HUD (`ab.c`): pause button (img 2, bottom-right), ability icon (img `4+c`, bottom-left), health bar top-left (imgs 10-14; flashes when <25%), score top-right (`k.e[player]`, tiny font), timer at top-centre `mm:ss` (level 25 race counts down from 180000 ms). Pause/level-end panels show score, time, amber `f/total`.

Frame counter `ab.d` drives all animation phases (`&1`, `&3` etc.).

## 10. Game rules

* Modes `k.b`: 0 = menu-world demo, 1 = single, 2 = MP host, 4 = MP client (client only renders state snapshots; all rules run on host/single). Sub-mode `k.e`: 0 story, 2 coop-story, 4/5 versus, etc.
* **Level order** (`at.b[]`, `at.a(n)` = next): 0 intro; story 1-35 (chapters 1: 1-15, 2: 16-26, 3: 27-35); playground 36-40; coop 41-67; deathmatch 68-77; race 78-87.
* **Score** `k.e[playerIdx]`: +10 per amber tile 9, +30 per monster kill / spike-killed corpse / crushed monster. **Collectibles** `k.f[]`: amber tile 43 count vs `k.l` per level (max 40 per chapter tracked for achievements).
* **Death**: health 0 → dissolve; single-player → level restarts after ~100 ticks (progress-of-level lost, totals kept); coop → partner can revive by eating remains; versus → respawn counter `k.e[winner]++` when only one blob remains.
* **Level completion**: story levels end by scripted triggers (dialog id per level, `ab.c(dialogId)`), e.g. reaching exit signs (`k.a[][] {x,y,dialog}` trigger tiles — standing on flagged column/row fires dialog; dialogs with `ab.a[id]==true` chain to next; ids 16/23 increment scene counter; 67/104 = game over/exit to menu; 35/67 = chapter end → save + results screen). After the end-dialog: `k.d=5` (results: add `e[]`,`f[]`,`k` into totals `h`,`i`,`j`, save "save"/"msave", achievements), then `k.d=4`→ next level.
* Race levels (`b==25` internally—level id 25 check): time limit 180000 ms → forced dialog 104 (fail).
* Timer accumulates only during play; saved totals: `h` total score, `i` total time, `j` total amber.

## 11. Sounds (`Main.a(idx, loop)` — index → file)

| idx | file | triggered by |
|---|---|---|
| 0 | `/sound/gishhit.wav` | jump; attack/bite hit |
| 1 | `/sound/tarball.wav` | health/tar pickup (tile 8); menu click alt |
| 2 | `/sound/amber.wav` | score amber (tile 9) & amber 43 pickups |
| 3 | `/sound/CLICK015.wav` | UI click |
| 4 | `/sound/squish.wav` | any blob/monster death, gib |
| 5 | `/sound/switch.wav` | lever pressed |
| 6 | `/sound/blockbreak.wav` | breakable tile 14 destroyed |
| 7 | `/sound/splash.wav` | body enters water fast |
| 8 | `/sound/necksnap.wav` | hanger monster (type 1) killed |
| 9 | `/sound/ropebreak.wav` | rope spring broken |
| 10 | `/sound/bobattack.wav` | walker/hanger attack lands |
| 11 | `/sound/visattack.wav` | spawner spawns a walker |
| 12 | `/sound/sewer.mp3` | music, looped |

Only one wav at a time (except music); volume = `e*10`/`f*10` (0-10 settings). Vibration `Main.a(ms)` on damage.

## 12. Constants quick table

| constant | value | meaning |
|---|---|---|
| fixed-point scale | 1024 = 1 px | all positions/velocities/forces |
| tile size | 32 px (`1<<15` fixed) | |
| angle period | 6588397 = 2π·2²⁰ | |
| frame / substeps (default) | 70 ms / 2 | quality 2 |
| gravity | 200 /substep² (≈0.195 px) | `af.e` |
| max speed | 8192 (8 px/substep) | only after a collision |
| player points / radius / mass | 18 / 24 px / 1024 | small 14/20; big 26 & 30 |
| player spring iterations | 2 | boxes 1, seesaw 2 |
| player health | 102400 (=100) | |
| friction | 1024 normal (full grip), 0 slick | per contact point |
| sticky grab spring | stiff 204, break 10240 (tiles); 512/5120 (platforms); 512/10240 (bodies) | |
| walk force | 50 ground / 100 water / 150 attached | ±x per substep |
| air up force | −100 | −350·normal when grounded (compress mode) |
| slam force | +100 / +400 water / +500 heavy-mode | |
| roll torque force | 300 (200 in water) tangential | |
| jump scale | 25000; per-point weight `(proj−min)·contacts·1024/(range·n)` | |
| climb assist | ±150 x, −350 y, −700 y | from tile hint bits |
| attack push | 11400 / 5700 / 2800 ·dir; recoil ×(−0.25/−0.5/−1) | box/smallblob/player |
| buoyancy | force 200 (player), 200-3000 (boxes), drag v≫5 or 6 | `h.a(surfY,f,shift,whole)` |
| water damage | 2048/substep (acid 36) | spikes 1024/contact |
| crush death | area < restArea/2 → 102400 | |
| break-block speed | avgVel² > 0x1E00000 | monster-kill speed avgVel² > 0x3200000 (kill), 0x1E00000 (crate) |
| box point mass | 2048 (crate 51200) | ball pressure 100000 |
| blood particle | mass 2048, r=5120, ttl≈30 | gib r=4096 |
| dark-window radius (lvl 20) | 46080 (45 px), grow 4608/frame | sprite 471 corners |
| effect queues | 10 popups, 10 sprites, 25 particles | ring buffers |
| camera pan (map view) | p+=(t−p)/2, damp ×621/1024 | |
| race time limit | 180000 ms | |
| monster params | see §8 tables `e,f,g,h,i,b,c` | |
| rope types | stiff {1024,1024,682,341,1024,341}; limit {−1,−1,1.5×,−1,−1,1.5×}; rest {nat,nat,nat,nat,32768,nat}; type1 pull-only | |

## 13. Porting notes

* Replicate the **fast length** `al.a` and fixed-point rounding (`>>10` truncation toward −∞ for negatives via arithmetic shift) — spring rest lengths, jump math and collision depths all bake its ~±4% error in.
* Determinism: the whole step is sequential; iteration order = body insertion order (players first, then boxes, ropes, monsters as loaded).
* The broadphase (`c`) is an optimization only — any AABB pair pruning works.
* MP sync (`k.a()/b()`) can be dropped; keep the single-player path (`k.b==1` semantics: immediate jump, jump/roll enabled).
* Sprite ids: global atlas via `/images.map` + `/images.img` (concatenated headerless PNGs; `g.a()` re-wraps them with a PNG header). Tile sprite = base(276/566/407)+id; entities base 256+table.

---

## 14. Visual tiling addendum

### 14.1 There is NO visual auto-tiling
The 0-14 neighbour classification in `Main.a(cx,cy)` produces **collision shape ids only** (written to `af.a byte[][]`, the collision map — see §6). The *drawn* tile ids for all three layers come **verbatim from the .lvl file** (`k.a[layer][x][y] = fileByte − 1`). The drawn sprite id is always:

```
spriteId = LAYER_BASE[layer] + rawTileId        // LAYER_BASE = {276, 566, 407}, no theme term
```

There is exactly **one** main-layer tileset in the atlas: sprites **566…638** = main tile ids 0…72 (all 32×32, verified against `images2.map`/manifest). The apparent "grey / gold / green" tilesets are just **id sub-ranges of the same set** that different chapters' levels use:
* ids ≈ 0-27 (sprites 566-593): grey stone / sewer bricks (chapter 1 materials),
* ids ≈ 28-48 (sprites 594-614): wood planks, girders, spike variants (chapter 2),
* ids ≈ 49-72 (sprites 615-638): green vines / organic swamp (chapter 3).

Sprites **535-563 are NOT terrain** — they are irregular-size (22×22…42×59) menu/cutscene splat art used by the menu class `s`; 543 is a small skull splat, etc. Sprites 474-514 are the monster/boss frames (`256 + ae` tables: 256+218=474 …). Ranges 566-593/594-613 therefore never get remapped per theme — a level in theme T simply *contains* the ids of its chapter's materials. **Do not implement any (material, edge-case, theme) → sprite function; copy ids from the level data.**

The only load-time *visual* mutations (in `Main.a(cx,cy)`, applied per cell at load and after tile destruction):
```
class = classify(mainTile)                 // -1 passable, 1 special-solid, 0 generic-solid
if class == 0 and mainTile != 69:
    if !k.a[mainTile]:  bg[x][y] = -1      // generic solid, not breakable(14): hide bg tile behind it
else:                                       // passable or special
    if bg[x][y] == -1 and levelId != 0: bg[x][y] = 11    // "cave interior" filler
    if theme != 0 and bg[x][y] == 11:   bg[x][y] = 51    // theme variant of the filler
```
Filler ids 11/51 are **skipped by the layer-0 renderer** (never drawn as sprites); their purpose is to be members of the *backdrop area set* below. Additionally at load: main ids 43/70 are stripped outside story mode, 70 stripped if its achievement is already owned.

### 14.2 Theme (level-header byte 0/1/2) — exact effects
Theme is stored in `ab.a int` (previous theme in `ab.b`, forces image-cache reload on change). Effects — **complete list**:

1. **Background fill color** (`ab.b(Graphics)`):
   * theme 2 → `0x55A0FF` (day sky; value 5611775)
   * theme 1 → `0x26220D` (dark olive; value 2499085)
   * theme 0 / other → `0x001414` (near-black teal; value 5140)
   * menu level (levelId 0) instead: `RGB(20,26,72)` = `0x141A48` + starfield.
2. **Backdrop sprite = `468 + theme`** (only drawn when detail `g > 1`):
   * `468` = 64×64 grey cave texture (theme 0), `469` = 64×64 (theme 1), `470` = **240×80 horizon strip** (theme 2).
   * **Theme 2 (horizon) rule:** strip top at `y = screenH/2 − imgH/4` (clamped so strip bottom ≤ screenH); below the strip fill `0x80F8FF` (pale water); horizontally tiled with **parallax x = camera.x/5** (i.e. backdrop scrolls at 1/5 of camera speed): start `x0 = k/5 + floor((k − k/5)/imgW)·imgW`, draw at `x0, x0+imgW, …` while `< k+screenW`, screen-anchored vertically (`y = l + stripTop`).
   * **Themes 0/1 rule:** compute the tile-bbox of *visible* layer-0 cells whose id is in the backdrop set `BDROP` (below); snap the top-left to a **screen-anchored 64-px grid**: `x0 = (((minTx<<5) − k) >> 6 << 6) + k` (same for y); then stamp the 64×64 texture on a 64-px grid over the bbox (`maxTx+1<<5`, `maxTy+1<<5` exclusive). Net effect: the texture is **fixed to the screen** (infinite-distance parallax) and only appears over "interior" areas.
   * `BDROP` (layer-0 ids, `ab.b boolean[53]`, true indices): `{1,2,11,13,19,28,29,31,34,37,38,41,42,45,46,49,51}` — note the auto-fillers 11 and 51 are members.
3. **Buoyancy variants** for boxes (§8: 600/3000 in theme 2) and menu-chunk choice (`ab.e`, §14.3).
4. Slick-outline color variant for the player (§14.4) and AI gray blobs, dark-level plank colors.

Nothing else — no sprite-id remapping, no per-theme tile offset table.

### 14.3 The menu-background chunks (`ab.a byte[11][3][10][4]`, `ab.e int[12]`)
These are **not** drawn behind the main menu. They are the decorative tile strip on the **loading / level-title screen** (game state `k.c == 7`, active while a level loads):
* `ab.a` holds 11 hand-made map chunks, each `3 layers × 10 columns × 4 rows` of tile ids (−1 = empty), authored in the usual layer bases (276/566/407). `ab.e = {0,0,0,0,1,1,1,1,2,2,2,2}` gives each chunk's theme.
* At load start `k` picks `ab.j = random chunk with ab.e[j] == currentTheme`.
* Render (ab.a case 7): a horizontal band of `min(10, ceil(screenW/32))` columns × 4 rows (**128 px tall**), centered; theme-2 fills the band `0x55A0FF` first; bg id 30 is drawn as sprite 272 (lava frame 0); fg id 37 as waterfall 460; layer-0 id 11 skipped; the band is framed top & bottom with the tiled **rope-border sprite 90** (top one vertically mirrored), the level name (`at.a(levelId)`: "1-3", "c-5", boss names…) above and a "loading" text below.
* The **main menu** proper (Main state 1/6, class `s`) draws its own art: black background, logo, and the splat sprites 535-563 / big art 507-514 — no live level and no `ab` involvement. The `"intro"` level (levelId 0) is a *playable scripted cutscene*, not a menu backdrop (see §15.3).

### 14.4 Confirmations
**(a) Gish body draw** (`d.a(Graphics)` — in this order):
1. Blood particles (dark red `RGB(74,0,0)`, sprite 118/129 drips, shrinking arc for splats).
2. **Filled body polygon**: triangulated fan/ear-clip via `v(4, n)` over the 18 ring points; fill color:
   * skin 0 (Gish): `0x000000`; skin 1 (2nd player): `0x121516`; AI gray blob (`a==1`): `0x999999`;
   * hurt flash (while `c>0`): cycles per frame `0xFF0000 → 0xB10000 → 0x5A0000`.
3. **Gloss dots** (detail>1, not hurt): on ring edges facing the eye-target direction (edge normal within ±341/1024 of gaze dir, front-facing): `fillArc` ellipses, center = edge midpoint − normal·5px, radii `rx = 8·(1024−|nx|)/1024 + 2`, `ry` likewise — color per `[theme][skin]`: theme0 {(50,49,20),(200,200,200)}, theme1 {(58,49,20),(200,200,200)}, theme2 {(38,30,10),(200,200,200)}.
4. **Outline** (`h.a(Graphics, offsetZero, color)` — polygon line loop):
   * gray AI blob: normal `0x5D5D5D`, sticky `0xCCCCCC`, slick `0xA5530B`;
   * player: slick → theme2 ? `0xA77C15` : `0x83610F`; sticky → theme2 ? `0xBBBBBB` : (lowest detail `0x888888`, else `0x636363`); normal → lowest detail `0x363636`, else `0x000000`.
5. **Face**: drawn at `centroid + bg(gazeAngle, 3072)` (3 px toward gaze), using the **32-frame rotation picker**: `frame = (normalize(angle + 102943) << 5) / 6588397` with `angle = gazeAngle − 1647099` (gaze − 90°); base set = 8 images **110-117** (skin 0/1) or **516-523** (gray), frames 8-31 are transforms ROT90/180/270 of frame&7. Special faces replace it: hurt = 125 (gray 525) + overlay 126-128 cycling at y−20; squish = 123+s / 529+s; yawn = 120-122 / 526-528 (sequence 0,1,2,2,1,0); blink = frames of 110/119 (gray 516/524) at the gaze offset position. Gaze angle `n` eases toward target by **1/6 of the angular difference per frame** (period-wrapped), target = atan2 to nearest visible interesting tile (ids 8/9/30 or fg 37 within 8 tiles, DDA line-of-sight), else the surface-contact normal.
   Dead body: each surviving point drawn as sprite **109** (gray: 8-px `0x999999` disc); death-throb state 2: growing disc `r = c·2.4 px` in skin color.

**(b) Torches (layer-0 ids 8/9)**: id 8 → draw base tile sprite `276+5`; id 9 → base tile sprite `276+0`; then both draw the **flame overlay** `276+8+t` (sprites **284/285**) at pixel `(tx·32+1, ty·32+32)` with anchor BOTTOM|LEFT (36). Cadence: `t` advances `0→1→0…` every **4 rendered frames** (`if((frame&3)==0) t^=1` via the shared `this.t` counter). (Confirmed 284 is the 15×7 flame image.)

**(c) HUD health bar** (`ab.c(Graphics)`, drawn at top-left, y=1):
```
capW  = width(img 10)                    // 3 px (imgs 10/11 are 3×12)
inner = screenW/2 − 2·capW − 7           // total inner width
fillW = inner · health / 102400          //  (code: inner·h/100 >> 10)
draw img 10 at (1,1)                     // left cap
x = capW + 1
tile fillImg from x, width fillW          // fillImg: health ≥ 51200 → img 12 (green)
                                          //          25600…51199   → img 14 (yellow)
                                          //          < 25600       → img 14 + frame%3 (14,15,16 flashing red)
tile img 13 from x+fillW, width inner−fillW   // empty backing
draw img 11 at (x+inner, 1)              // right cap
```
Tiling helper repeats the 12×12 fill image horizontally under a clip rect. Same HUD row: score `k.e[player]` right-aligned at `(screenW−1, top)` in tiny font 0; timer `mm:ss` (`ab.a(ms)`) left-aligned from screen center-x; pause button img 2 anchored bottom-right; ability icon img `4 + abilityState` (4 normal /5 slick /6 sticky) anchored bottom-left. Level-25 race shows `180000 − k.k` counting down.

---

## 15. Dialog & level-end addendum

### 15.1 Static tables (JS-ready)

**Direction-arrow / hint mask per dialog id** — `ab.a short[199]` (`hintMask[dialogId]`; all unlisted = 0):
```js
const HINT_MASK = new Int16Array(199);
Object.entries({0:2, 1:4, 2:8, 3:16, 4:32, 5:64, 6:256, 7:1024, 8:2048,
                17:2048, 23:1032, 24:1032, 26:1032, 29:64, 40:32}
).forEach(([i,v]) => HINT_MASK[i] = v);
```
Bit → overlay (drawn around the player at ±40 px until the matching input/state occurs, or player moves >150 px from the sign; sprite = `256 + ARROW_IMG[bit]`, sticky-variant `256 + ARROW_IMG2[bit] + 8` when attached):
```js
// bit index:      0    1        2     3         4      5       6      7  8      9  10       11
// meaning:        -    upleft   up    upright   left   jump    right  -  down   -  sticky   slick
const ARROW_IMG  = [-1,  0,      1,    2,        3,     4,      5,    -1, -1,   -1,  4,       4];
const ARROW_IMG2 = [-1,  0,      1,    2,        3,     4,      5,    -1, -1,   -1,  6,       7];
```
(1032 = 1024|8 = sticky icon + up-right arrow.) Mask bits are cleared live as the player performs the action (see `ab.b` E-mask pruning: left/up/right/down inputs, sticky/slick set).

**Speaker portrait per dialog id** — `ab.b short[159]` (`0` = none; values are atlas sprite ids of 44×44 portraits):
```js
const SPEAKER = [84,84,84,84,84,84,84,84,84,87,80,82,81,80,81,82,84,84,84,84,87,82,82,84,84,84,
84,87,82,82,80,82,80,84,85,82,84,87,82,87,84,87,82,87,82,84,82,87,82,87,84,85,82,85,82,85,87,82,
82,84,84,82,83,82,83,82,83,83,82,82,87,80,82,87,86,82,86,82,86,82,87,87,82,87,82,87,82,87,82,86,
82,86,86,87,82,87,87,82,83,82,83,82,83,84,83,82,82,82,87,82,82,87,82,84,81,82,87,82,82,87,87,82,
82,83,82,83,82,83,87,87,82,87,82,80,82,80,82,80,82,80,82,80,82,87,87,82,83,84,84,82,83,85,83,85,
82,83,82,82,0];
```
Speaker-name text id (into the localized `tl` string table) from portrait id — `ab.a(int)`:
```js
const SPEAKER_NAME = {85:76, 86:90, 80:77, 82:78, 81:79, 83:80, 84:81, 87:83};  // else -1 (no name plate)
```
Portraits **82 and 84** draw the name plate + portrait on the **left** edge (x=6); all others on the right (`x = screenW − 50`). Dialog panel: bottom sheet sliding up over 4 frames, rope-border sprite 90 tiled along its top, text in font 3 (scrollable), portrait box 44×44 with plate colors bg `(9,9,9)`, text field `(23,23,23)`, name strip `(29,29,25)`, white 1-px border. Minimum 8 frames on screen before it accepts the advance key.

**Auto-chain flags** — `ab.a boolean[159]`: after dismissing dialog `f`, if `CHAIN[f]` then immediately open dialog `f+1` (with a 40-frame world-redraw grace `e=40`):
```js
const CHAIN_TRUE = [10,11,12,13,16,20,25,27,30,31,34,35,37,38,41,42,43,46,47,48,51,52,55,61,62,63,
64,65,69,71,72,74,75,76,78,79,81,82,83,84,85,86,87,89,90,92,93,94,96,98,99,100,101,102,107,108,
111,115,118,120,122,123,124,125,126,127,129,131,133,134,135,136,137,138,139,140,141,144,145,146,
147,149,150,151,152,153,154];   // CHAIN[i] = CHAIN_TRUE.includes(i)
```

**Camera-pan cinematics** (`ab.a(boolean)`): a dialog can be *preceded* by a scripted camera pan (state `k.c=9`: camera eases to target with `p += (t−p)/2` + inertia damp ×621/1024, then the dialog opens on key press / arrival; pressing fire skips). Two tables, keyed by `dialogId + 1`:
* Pan **before showing** dialog n (`a(false)`, called from `ab.c(n)`), target tile (tx,ty) or dynamic:
  `{23:(16,11), 35:(4,16), 47:(18,30), 52:(10,19), 55:(23,2), 72:(8,8), 97:(20,7), 112:(30,9), 113:(15,7), 115:(12,16), 152:(13,11), 158:(40,11), 108|116: last rope end-point, 130: entity[1] position}` — camera centers that tile.
* Pan **after dismissing** dialog f, en route to f+1 (`a(true)`) only for `f+1 ∈ {23,37,50,54,55,72,98,110,113,115,156,158}`; start point = player, target from the same second table.

### 15.2 Dialog-id side effects & level completion

**Opening** (`ab.c(id)`): `f = id`. Special:
* `id == 17` → force dialog state, 40-frame grace.
* `id == 16 || id == 23` → **scene counter `ab.c += 1`** (and no auto modal).
* If not in a cutscene (`!ab.b`) and `SPEAKER_NAME[SPEAKER[id]] == 81` (a Gish self-talk line) and id ∉ {16,23} → shown modally immediately.
* Otherwise: run pre-pan if listed, else open the text page (`k.d = 6`; empty text → `k.d = 0`).
* On open, `E`/`G` hint masks are armed from `HINT_MASK[id]` (bits pruned by current ability state).

**Dismissing** dialog `f` (fire / soft key, `ab.a(int)`):
```
if CHAIN[f]            -> open f+1 (grace 40)
elif f == 67 or 104    -> k.d = 2   // RESTART current level (fail dialogs: "she got away" / race time-out)
elif f == 14           -> intro script: create 2 rope constraints tying Gish to the departing cart (see 15.3)
elif f in {66,77,91,109,128,130} -> ab.c += 1        // scene advance
elif f == 142          -> ab.c = 2
elif f == 132          -> ab.c += 1; teleport player 8 px up-left & re-seat (level-32 boss script)
else                   -> k.d = 0, k.c = 0 (resume), maybe pan toward f+1 (table above)
```

**Normal level completion** — *no dialog involved*:
1. The exit region is marked with **layer-1 tile id 13** (invisible). Every substep each player sets `d.c = (my AABB overlaps a 13-tile)` (boss levels override: on 20/23/28/32 `d.c` is instead set true when the boss-death scene has been reached — `ab.c==6` there — and on 34 when `ab.e==2`).
2. In `ab.h()` after ticking players: `n = count of players with c == true`.
   * versus (`k.e==4/5`): first player with `c` → `k.d = 5`, winner `k.e[winner]++`.
   * single (`k.b==1`): `n == 1` → `k.d = 5`. Coop: `n == 2` required.
   * On `d = 5`: add `k.e[]→h (score)`, `k.f[]→j (amber)`, `k.k→i (time)`.
3. `k.c()` state 5 = **results**: writes save ("save"/"msave" = next-level index via `at.a`), updates achievements ("achi" store), for the intro level (b==0) it *immediately* saves progress=1 and loads level 1. Render: state 5 slides the results sheet in over 7 frames (translate `−H + H·t/7`) then state 4 (static). Contents (`ab.d`): header texts 39/41 + 40/36 ("level complete"), rows: text 37 "score" = `k.e[me]`, text 38 "time" = `mm:ss(k.k)`, story adds text 14 "amber" = `k.f[me]/k.l`; below a divider: text 97 total score `k.h`, 98 total time `mm:ss(k.i)`, 99 total amber `i/40`; versus shows win/lose texts 49/50/51/75. **No timeout — waits for a key**, then `k.d = 8`.
4. `k.c()` state 8: story/coop mid-chapter → `b = at.a(b)` (next level: story +1 up to 35, coop 41→67), `d = 2` (load). Last level (`b==35` story / `67` coop) → final save, jump to menu screen 26 (or credits 31); versus/race → back to lobby menus.

**Death** never uses dialogs: health 0 → dissolve → after 100 ticks `k.d = 2` (reload same level; per-level score/time reset in `a(byte,byte)`).

**Boss/special level detection (code-verified):**
* **Level 14** (chapter-1 chase, AI blob `e==1`): AI runs a waypoint state machine (`ab.c` scenes 0-9, tile-coordinate thresholds in `d.a()`); if the AI's centre reaches an exit tile 13 → `ab.c(67)` → dismiss → **restart**. Beat it to the exit to win normally.
* **Level 20** (dark chase, boss `ae` type 4): scenes: boss steers toward player (`speed += f/30` to 2764); `ab.c==3` + boss stands near lava tiles (bg id 30 in 3×3) → scene 4 → 5 + boss death `b()`; dialog 92 fires when (`ab.c==3` and boss fully inside view). After the death scene (`ab.c==6` via dialog chain), `d.c` goes true → normal exit flow. Darkness window logic per §9.
* **Level 28** (tower boss, `ae` type 5, 200 HP-ish): each fast player hit +30 score; when `k.e[me] ≥ 180` → `ab.c(110)`, `ab.c += 1`, all pillars (type 6) die; boss `b()` when `ab.c==2` and boss in view; its death clears the 3 tiles under it. Exit = scene flag (see above).
* **Level 32** (second tower): scene 0 spawns pillars at fixed ±3-column offsets; both levers (`ac[0], ac[1]` active) + player below the boss line → dialog 118, boss anim 6, `b()` chain; death writes tile 69 column beneath. Player standing on the boss with both levers active gets boosted `(0,−5000)`.
* **Level 34** (blob duel, AI `e==3`): if the AI dies and the player is alive/idle → `ab.c(129)`; scenes via dialogs 128/130 (+1 each).
* **Level 25** (race): `k.k ≥ 180000` → `ab.c(104)` → restart. (Races 78-87 share the countdown HUD.)
* Levels 5/11, 20/23, 28/32, 14/25/34 append flavor suffix texts 70/71/72/73 to their title cards (`at.a(levelId)`).

### 15.3 Intro (level 0) and outro
**Intro "caravan" cutscene** — levelId 0 (`levels/intro.lvl`), mode `k.b==0`, fully engine-driven:
* Background: starfield instead of tiles-behind (`ab.b` menu branch): `n = screenW·screenH/2000` stars, colors `0x474E83` (dim) / `0x8B91BD` (bright) 1-px, twinkling by swapping the two sets every 42 frames; one "shooting star" slot (sprite 73-75 by phase).
* Cast: Gish (player, input ignored — `d.a()` drives it), the **ball** (`ax` type 5 with its blinking face, sprites 140/141: pupil 140 shown, blink 141; blink randomly every ~8 frames for 3 frames), and the cart/Brea entity (an `ae` whose menu-mode branch pushes it rightward with force `−f/4` / `+2f`).
* Script (scene counter `ab.c`):
  1. scene 0: Gish walks right (sticky on) until tile x ≥ 7 → scene 1 + `ab.c(9)` (dialog 9).
  2. Ball touching a plank body (`j==3` vs `as.d==5` contact) while `ab.c ≤ 1` → scene++ + dialog 10, which **auto-chains 10→11→12→13→14** (`CHAIN[10..13]`).
  3. Dismissing **14** creates two rope constraints (stiffness 1024, natural rest) tying Gish's body to the cart's hitch points — Gish gets dragged.
  4. Camera: scenes ≤1 fixed ahead of Gish (`k = gx−24−30`); scene ≥2 eases to the cart (`k += 10` toward it).
  5. When the cart's trailing particle falls below the map bottom (`y>>15 ≥ mapH`): scene++, ropes and all boxes/ropes/monsters despawned, sounds stopped, **dialog 15** (the title card — `at.a(0)` = text 69 "intro").
  6. Gish then walks right alone onto the exit tile → results state → `k.c()` intro branch: save `progress = at.a(0) = 1` and load **level 1**.
* The fire-key skip: any dismiss of a non-chained dialog resumes; the softkey during states 6/9 also fast-forwards pans.

**Outro/credits** — there is **no scripted outro level**: finishing story level 35 (or coop 67) routes through `k.c()` state 8 → final save → menu screen id 26 → credits screen 31 (class `s`; static art from sprites 507-514/535-563 + text pages). Level 23's fireflies (sprites 531-534, 9 sparks jittering on a fixed arc near x=648,y=670 while `ab.c<3`) and the level-20/23 boss cameos are in-level dressing, not an outro.
