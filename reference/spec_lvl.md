# Gish Reloaded (J2ME) `.lvl` File Format Specification

Reverse-engineered from the CFR-decompiled sources (obfuscated classes). All parsing
happens in `com/hardwire/blob/Main.java`:

* `Main.a(String, byte[]) : DataInputStream` — opens `/levels/<name>.lvl` and reads the
  3-byte header.
* `Main.a(DataInputStream) : void` — reads everything else, in one linear pass, to EOF.

Verified against all 88 `.lvl` files shipped in the JAR (`levels/1|2|3|coop|dm|playgr|race`,
`intro.lvl`): the grammar below consumes **every file exactly to EOF** (see
`parse_lvl.py` in this directory).

---

## 0. Conventions, units and coordinate system

* The file is a flat byte stream, no padding, no alignment, no compression.
* `u8` = unsigned byte (`readByte() & 0xFF`), `s8` = signed byte (`readByte()`).
  Every field in the format is a single byte; there are no multi-byte integers.
* **Tiles are 32×32 pixels.** The engine's physics units are fixed-point:
  1 pixel = 1024 sub-units (`<<10`), 1 tile = 32768 sub-units (`<<15`).
* **Tile grid**: `width` columns × `height` rows. Runtime tile indices are 0-based,
  column 0 = left, row 0 = **top**.
* **File coordinates** (all object positions in the file) are **1-based tile
  coordinates measured from the bottom-left corner**:
  * column index (0-based, from left) `col = fileX - 1`
  * row index (0-based, from top) `row = height - fileY`
  * Entity pixel centre: `px = fileX*32 - 16`, `py = (height - fileY)*32 + 16`
    (i.e. the centre of tile `(col,row)`).
* Counts precede their records. A count of 0 is normal (section is then empty).

### Overall layout

| # | Section | Size |
|---|---------|------|
| H | Header | 3 bytes |
| 1 | Entities (creatures + items) | 2 + 3·N bytes |
| 2 | Physics blocks (`ax`) | 1 + 3·N bytes |
| 3 | Button count + moving platforms (`ac`/`u`) | 2 + (7 or 9)·N bytes |
| 4 | Ropes (`ag` chains) | 1 + Σ(1 + 3·points) bytes |
| 5 | Tile map, 3 layers | 3 · width · height bytes |
| 6 | Tutorial/dialogue triggers | 1 + 3·N bytes |
|   | EOF | |

---

## H. Header (3 bytes)

| Offset | Type | Field | Meaning |
|--------|------|-------|---------|
| 0 | u8 | `width` | Level width in tiles (`k.d`). Observed 13–112. |
| 1 | u8 | `height` | Level height in tiles (`k.e`). Observed 10–70. |
| 2 | s8 | `theme` | Tileset/background theme (`ab.a`), observed 0, 1, 2. Selects the parallax backdrop image (global sprite id `468 + theme`) and the sky fill colour (theme 2 = light blue 0x5599FF outdoor, 1 = 0x262205 amber cave, 0 = 0x001414 dark sewer) and the tileset PNG variants. |

---

## 1. Entities

```
u8  nCreatures      # entities that become class d (soft-body blobs: players/rival blobs)
u8  nItems          # entities that become class ae (creatures/objects)
repeat (nCreatures + nItems) times:
    u8 fileX
    u8 fileY
    u8 type
```

The two counts partition the records by `type` (records of both groups may be
interleaved in any order; the counts must equal the number of matching records —
verified true for all 88 files). Position is the tile centre (see conventions).

### Entity type table

`type` 1 and 5 become class `d` (a soft-body blob made of particles); every other
value `t` becomes class `ae` with kind `t - 2`. `ae` sprites are global ids
`256 + s` where `s` comes from the per-kind sprite group below
(`ae.a[kind]` selects the group in `ae`'s static table `{{279,280,288,293},
{218,219,230,234,227}, {238,-1,239,242,248}, {251,-1,256,252,304}, {297,306}, {300}}`).

| type | Class / kind | Meaning | Sprites (add 256) | Notes |
|------|--------------|---------|-------------------|-------|
| 1 | `d`, variant 0 | **Player spawn** (Gish). First one = human player; additional ones = other players (co-op / deathmatch / AI, still variant 0). | 110+ (Gish frames) | In single-player only the first type-1 record is used. |
| 2 | `ae` kind 0 | Small walking creature (1-tile, radius 6 px, mass 12288). | 279, 280, 288, 293 | Most common item entity. |
| 3 | `ae` kind 1 | Two-segment tall creature: main body (r=16 px) + second body 32 px below, linked. | 218, 219, 230, 234, 227 | |
| 4 | `ae` kind 2 | Heavy creature (mass 1,000,000, r=15 px). **Reserves 3 extra runtime `ae` slots** (projectiles/fragments) — no extra file bytes. | 238, 239, 242, 248 | |
| 5 | `d`, AI | **Rival blob** (enemy Gish). Variant depends on level number: level 14 → 1, level 34 → 3, otherwise 2. | 516–530 / 110+ | |
| 6 | `ae` kind 4 | Large armed creature; drawn above the darkness overlay — only used in special game modes (level ids 20/23). | 251, 256, 252, 304 | |
| 7 | `ae` kind 5 | Boss-type static creature (giant, drawn as pillar + arms + head, anchored, takes 100 damage ticks). **Reserves 10 extra runtime `ae` slots** — no extra file bytes. | 297, 306 | |
| 8 | `ae` kind 6 | Vertical extending creature/piston (draws a stack of segments up or down). | 300 | Defined in code, not present in the shipped 88 levels. |

> Important parser note: the `n10 += 3` / `n10 += 10` statements in the decompiled
> loop only enlarge the runtime `ae[]` array for types 4 and 7 — they do **not**
> read extra bytes. Every entity record is exactly 3 bytes.

Observed distribution over the 88 files: type 1 ×170, 2 ×142, 3 ×45, 4 ×3, 5 ×3, 6 ×2, 7 ×2.

---

## 2. Physics blocks (`ax`)

Free-moving soft-body props (crates, planks, balls) built from particle rings.

```
u8 nBlocks
repeat nBlocks times:
    u8 fileX        # col = fileX - 1
    u8 fileY        # row = height - fileY
    u8 rawKind      # kind = rawKind - 1   (0..11)
```

The block's geometry is instantiated at physics position `(col<<15, row<<15)`
(top-left corner of the anchor tile) plus per-kind vertex offsets. `kind`:

| kind | Shape (px) | Tile footprint (registered for rope attachment) | Notes |
|------|-----------|--------------------------------------------------|-------|
| 0 | 32×32 box | 1×1 at (col,row) | Standard crate. |
| 1 | 96×32 box | 3 wide: (col..col+2, row) | Long plank. |
| 2 | 96×32 box | 3 wide | **Hangable** variant (drawn with a hook dot; meant to hang from ropes). |
| 3 | 32×96 box extending upward (y offsets −64..+32) | 3 tall: (col, row−2..row) | Vertical plank. |
| 4 | 64×64 box, anchor shifted up 32 px, heavy (spacing 51200) | 2×2: (col..col+1, row−1..row) | Big crate. |
| 5 | 10-vertex ball, r=16 px, mass 100000, centre +16 px x | 1×1 | Heavy ball with animated face (sprites 140/141 — blinking eyes). |
| 6 | Slanted 32×96 parallelogram (vertices (33,−63)(31,31)(0,32)(0,−64)) | 3 tall | Sheared plank. |
| 7 | 64×32 box | 2 wide | Short plank. |
| 8 | 128×32 box | 4 wide | **Hangable**. |
| 9 | 96×21 plank (thin), softer (h.h=2) | none | Bridge/see-saw plank. |
| 10 | 32×128 tall parallelepiped (vertices to −96) | none | 4-tall post. |
| 11 | 256×32 box | 8 wide: (col..col+7, row) | **Hangable** long beam. |

Kinds 2, 8, 11 are the "hangable" family (flag set in the `h` constructor and an
attachment dot drawn at their centre); kinds 0, 4, 7, 9 get another flag (rigid/heavy
handling). Rope endpoints snap to particles of the block occupying the footprint
tiles listed above (see section 4).

---

## 3. Buttons count + moving platforms (`ac` = platform, `u` = button)

```
u8 nButtons          # total number of button records that will follow inside
                     # platform records (size of the u[] array). Redundant:
                     # equals the number of platforms whose activation ∉ {0,5}
                     # (verified for all 88 files).
u8 nPlatforms
repeat nPlatforms times:
    s8 rawKind       # kind = rawKind - 1    (0..3)
    s8 speed         # travel-time divisor: time = distance/(speed*500); larger = faster.
                     # Observed 1..10, 20.
    s8 activation    # 0, 5, or a button type 1..4
    u8 x1, u8 y1     # endpoint A: physics pos ((x1-1)<<15, (height-y1)<<15) = tile top-left
    u8 x2, u8 y2     # endpoint B: same encoding
    if activation != 0 and activation != 5:
        u8 bx, u8 by # button tile: col = bx-1, row = height-by
```

Platform `kind` (collision box built by `al.a(w,h)`, drawn with sprite 235 per tile):

| kind | Size | Orientation | Endpoint adjustment |
|------|------|-------------|---------------------|
| 0 | 96×32 (3 tiles) | horizontal | none |
| 1 | 32×96 (3 tiles) | vertical | both endpoints shifted by (0,−64 px) |
| 2 | 64×32 (2 tiles) | horizontal | none |
| 3 | 32×288 (drawn as 8 tiles) | vertical | both endpoints shifted by (0,−256 px) |

(The platform's collision polygon extends right/down from its position; for the
vertical kinds the anchor is shifted so the file coordinate refers to the bottom tile.)

`activation`:

| value | Behaviour |
|-------|-----------|
| 0 | Autonomous: starts immediately, loops forever between A and B (`ac.a(0,0)`). |
| 5 | One-shot mode from the start (`ac.a(1,0)`): travels to B once, then stops (used for doors etc.). |
| 1 | Button-linked: pressing the button starts continuous looping. |
| 2 | Button-linked: pressing starts a single A→B trip. |
| 3 | Button-linked: moves (loops) **while held**, stops when released. |
| 4 | Button-linked: steps forward while pressed, reverses when released (momentary door). |

For button-linked platforms the extra 2 bytes give the tile that contains the
pressure button (drawn with sprites 77/78/79); pressing = any blob particle inside
the top 4 px of that tile.

---

## 4. Ropes (chains of `ag` distance constraints)

```
u8 nRopes
repeat nRopes times:
    u8 head          # material = head >> 4 ; nPoints = head & 0x0F
    repeat nPoints times:
        u8 fileX     # col = fileX - 1
        u8 fileY     # row = height - fileY
        s8 corner    # 0..4, position within the tile
```

Each rope is a polyline of `nPoints` anchor points; consecutive points are joined
by one constraint segment (`ag`). Point position in pixels:

| corner | Position in tile (col,row) | Diagonal-neighbour probe |
|--------|---------------------------|--------------------------|
| 0 | top-left corner `(col*32, row*32)` | (−1,−1) |
| 1 | top-right `(+32, 0)` | (+1,−1) |
| 2 | bottom-right `(+32, +32)` | (+1,+1) |
| 3 | bottom-left `(0, +32)` | (−1,+1) |
| 4 | centre `(+16, +16)` | (0,0) |

Attachment rules (runtime): the first and last points search the block (`ax`)
footprint map at the point's tile and its diagonal neighbours for the nearest block
particle within 8 px and attach to it; otherwise the point is a fixed world pin.
Interior points that touch a block particle attach to it; other interior points
become free rope nodes (new particle, flag 0x20). If the last point lands inside an
entity's bounding box, that `ae` entity's body is tied to the rope end.

`material` (indexes the static tables `k.a` stiffness, `k.b` rest-length, `k.c`
break-length; constraint = `new ag(p1, p2, k.a[m], k.b[m], k.c[m])`):

| material | stiffness (1024 = rigid) | rest length | breakable | extra |
|----------|--------------------------|-------------|-----------|-------|
| 0 | 1024 | natural (−1) | no | standard rope |
| 1 | 1024 | natural | no | pull-only (slack — no compression resistance; `ag.a = 1`) |
| 2 | 682 | 1.5 × natural (−1536) | no | stretchy/elastic |
| 3 | 341 | natural | no | very elastic |
| 4 | 1024 | natural | **breaks beyond 32 px** (32768) | breakable rope |
| 5 | 341 | 1.5 × natural | no | bungee |

Rendering: segments are drawn as brown lines / chain-link sprites.

---

## 5. Tile map (3 layers)

Exactly `3 * width * height` bytes:

```
for x in 0 .. width-1:            # columns, left to right
    for y in height-1 down to 0:  # rows, BOTTOM row first (y is the 0-based row from top)
        for layer in 0 .. 2:      # 3 bytes per cell, layers interleaved
            u8 raw
            tile[layer][x][y] = raw - 1     # 0 => -1 = empty
```

So the stream is column-major, each column written bottom-to-top, and each cell
stores its `[layer0, layer1, layer2]` bytes consecutively. Stored value is the
tile id **plus one** (0 = empty).

Layers:

* **Layer 0 — background** decoration (drawn first). Base sprite id `ab.a[0] = 276`;
  tile id `t` is drawn with global image `276 + t`. Special ids: 30 = animated
  glow (sprites 272–275); 8, 9 = torch bases with flame overlay; 11/51 = solid
  darkness filler (auto-inserted behind terrain).
* **Layer 1 — game/collision layer**. Base sprite id `ab.a[1] = 566`.
  This is the layer the physics engine reads.
* **Layer 2 — foreground** overlay drawn on top of everything. Base sprite id
  `ab.a[2] = 407`. Special ids: **6, 7 = water body, 36 = water surface**
  (the engine scans layer 2 for ids 6/7/36 to find the water line; blobs swim
  in these tiles); 37 = animated waterfall (sprites 460–463); 17, 18, 41–45 are
  remapped to layer-1 sheet sprites (`566+0`, `566+28`, `566+55`, `566+62`,
  `566+52`, `566+59`, `566+50`).

### Layer 1 (collision) tile semantics

Classification used by the auto-tiler (`Main.a(int,int)` → −1 / 0 / 1):

* **Empty / non-solid special** (class −1): `-1, 8, 9, 13, 43, 70`
  * `8` — bobbing pickup (sprite 46, small amber?);
  * `9` — animated pickup/lamp (sprites 47–49);
  * `13` — non-solid marker (skipped when drawing);
  * `43` — **collectible amber**: counted at load into the level total (`k.l`);
    removed from the map when collected;
  * `70` — **achievement coin**: bobbing (sprite `566+70`); removed at load if
    already collected (checked against the `achi` record store), only active in
    campaign mode.
* **Special solid surfaces** (class 1): `1,2,3,4,7,10,11,12,16,17,37,38,39,40,64,65,66,67,68,71,72`.
  These keep their own sprite instead of being auto-tiled; overlay index mapping:
  1/16/37/65 and 2/17/38/64 = up-facing surfaces (two styles, variant if tile below
  is solid), 3/39/66 and 4/40/67 = down-facing, 7/68, 71, 72 = free-standing,
  10/11/12 = decorated solids, 69 = hidden-passage cover (drawn as overlay 28,
  non-solid to the auto-tiler neighbours).
  **Damaging (spike) ids**: contact with `7, 10, 11, 12, 68, 71, 72` deals damage
  (`d.b(1024)`).
* **Normal solid terrain** (class 0): every other id ≥ 0. These are auto-tiled at
  load: the visible sprite is chosen from the number/arrangement of solid
  neighbours (edges, corners, inner tiles), producing a `byte[][]` visual index
  0–14; the raw id chiefly selects the material look.
  **Breakable blocks**: id 14 (the one `true` in `k.a[]`) shatters when hit by a
  heavy (slammed) blob.

Ids observed in the shipped levels: layer 0 uses −1..52, layer 1 uses −1..72,
layer 2 uses −1..55.

Sprite ids ≥ 256 map through `/images2.map`, ids < 256 through `/images.map`, into
the packed archives `/images.img` and `/images2.img`; a handful of images are
standalone PNGs (`/img_tiles/fg/6_alpha.png`, `7_alpha.png`, `36_alpha.png`,
`/img_gish/dark_corner_alpha.png` = sprite 471).

---

## 6. Tutorial/dialogue triggers ("hints")

```
u8 nHints
repeat nHints times:
    u8 fileX      # col = fileX - 1
    u8 fileY      # row = height - fileY
    u8 rawId      # id = rawId - 1
```

Stored in `k.a` (`short[nHints][3]`), with per-column/per-row boolean fast-filter
arrays (`k.f`, `k.e`). When the player blob enters tile `(col,row)`, dialogue text
number `id` from the translated string table (`tz.<lang>`) is displayed in the
letterboxed dialogue bar (with a character portrait for some ids), and the static
table `ab.a[id]` (a `short` bitmask, 201 entries) supplies optional on-screen
**direction-arrow overlays** around the player (bit 1 = up-left, 2 = up, 3 =
up-right, 4 = left, 5 = jump, 6 = right, 10/11 = context action; arrow sprites
`256 + ab.b[bit]` / pressed variants `+8`). Ids observed: 0–157. Campaign levels
use many; multiplayer levels use none.

After this section the file ends. (EOF is the terminator; there is no checksum
or footer.)

---

## 7. Grammar summary (pseudocode)

```text
lvl := width:u8 height:u8 theme:s8
       nCreatures:u8 nItems:u8 entity{nCreatures+nItems}
       nBlocks:u8 block{nBlocks}
       nButtons:u8 nPlats:u8 platform{nPlats}
       nRopes:u8 rope{nRopes}
       cell{width*height}          # column-major, bottom row first
       nHints:u8 hint{nHints}
       EOF

entity   := x:u8 y:u8 type:u8
block    := x:u8 y:u8 kindPlus1:u8
platform := kindPlus1:s8 speed:s8 act:s8 x1:u8 y1:u8 x2:u8 y2:u8
            [bx:u8 by:u8  iff act not in {0,5}]
rope     := head:u8 point{head & 15}         # material = head >> 4
point    := x:u8 y:u8 corner:s8
cell     := l0:u8 l1:u8 l2:u8                # value-1 = tile id, 0 = empty
hint     := x:u8 y:u8 idPlus1:u8
```

---

## 8. Example: `intro.lvl` (853 bytes) parsed

First bytes: `1B 0A 00 01 01 02 07 01 11 06 03 02 03 07 06 03 ...`
(27, 10, 0, 1 creature, 1 item, entity(2,7,1), entity(17,6,3), 2 blocks, block(2,3+..), ...)

```json
{
  "width": 27, "height": 10, "theme": 0,
  "entities": [
    {"x": 2,  "y": 7, "type": 1, "kind": "player_spawn", "px": 48,  "py": 112},
    {"x": 17, "y": 6, "type": 3, "kind": "two_segment_creature", "px": 528, "py": 144}
  ],
  "blocks": [
    {"col": 2, "row": 3, "kind": 5},
    {"col": 2, "row": 2, "kind": 5}
  ],
  "platforms": [],
  "ropes": [],
  "tiles": "3 layers, 27x10, column-major bottom-up (e.g. layer1 row 9 is solid ground ids 5,3,10,...)",
  "hints": [
    {"col": 19, "row": 1, "kind": 15}, {"col": 20, "row": 1, "kind": 14},
    {"col": 21, "row": 1, "kind": 13}, {"col": 22, "row": 1, "kind": 12},
    {"col": 23, "row": 1, "kind": 11}, {"col": 24, "row": 1, "kind": 10},
    {"col": 25, "row": 1, "kind": 9}
  ]
}
```

Layer-1 map of `intro.lvl` (`.` = empty, hex digit = tile id mod 16), row 0 at top:

```
...........................
...........................
...........................
...........................
ffffffff0..................
ffffffffffffffffff0..1fffff
0054444454420540540..000544
529553a425444454487..205444
3555a54454544444442..544544
55555555535a895355add895355
```

---

## 9. Field-destination cross-reference (for further reversing)

| File value | Runtime field | Class role |
|-----------|---------------|-----------|
| width, height | `k.d`, `k.e` | game world (`k` = level/world manager) |
| theme | `ab.a` (int, prev in `ab.b`) | `ab` = renderer/HUD |
| entities type 1/5 | `k.a : d[]` | `d` = soft-body blob (player/AI) |
| entities other | `k.a : ae[]` | `ae` = creature/object |
| blocks | `k.a : ax[]` (`ax.b` kind, `ax.b/c` col/row) | `ax` = physics prop; body = `h` of particles `x` |
| platforms | `k.a : ac[]` | `ac` = elevator; polygon `f` |
| buttons | `k.a : u[]` | `u` = pressure button |
| rope segments | `k.a : ag[]` | `ag` = distance constraint |
| tile map | `k.a : byte[3][w][h]` | auto-tile visuals into `k.a : byte[w][h]` |
| hints | `k.a : short[][3]`, filters `k.f/k.e` | text via `at`, arrows via `ab.a[]` |
```
