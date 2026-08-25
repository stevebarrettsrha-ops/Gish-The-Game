# Gish Reloaded (J2ME) — Bitmap Font & Text System Spec

Reverse-engineered from CFR-decompiled sources (`src/at.java` = font/text engine,
`src/an.java` = Zeemote string resources, `src/com/hardwire/blob/Main.java`).
Verified by `parse_fonts.py` (checksum of t_pointer.en matches; glyph packing
matches the atlas PNGs pixel-for-pixel).

## 1. Bitmap font system (`at.java`)

### 1.1 Fonts

Four fonts, initialized in `at.e()` via `at.a(fontId, imageId)` — the image id is
resolved through the game's image manager (images stored in `images.img`/`images2.img`,
extracted as `png_out/<id>.png`):

| Font | Image id | Atlas (WxH) | Rows | Line height `e[f]` | Space width `c[f]` | Tracking `d[f]` | Glyphs | Look |
|------|----------|-------------|------|--------------------|--------------------|-----------------|--------|------|
| 0 | 8   | 238x200 | 5 | 40 | 17 | -1 | 63 | big yellow outline caps (menus/headings) |
| 1 | 7   | 15x7    | 1 | 7  | 6  | +1 | 3  | tiny; charset `"013"` only |
| 2 | 237 | 238x200 | 5 | 40 | 17 | -1 | 63 | **orange color variant of font 0** (same metrics/charset; used for text id 108 "level url:") |
| 3 | 230 | 261x72  | 3 | 24 | 11 | 0  | 63 | small white font (body text, dialogs, level dialogue) |

There is a 5th charset entry in the static tables (`c[4]`, `d[4]`) but only 4 width
arrays / images exist and `e()` only loads 4 — a leftover; effective font count is **4**.

### 1.2 Metrics storage — NOT in the .en files

Glyph **widths are hardcoded** in `at.java` as static int arrays (fonts 0 and 2 share
array `b`, font 1 = `{6,3,6}`, font 3 has its own 63-entry array). The `.en` "pointer"
files contain only *strings* (see section 2) — no metrics.

Atlas x/y offsets are computed at load time (`at.a(int,int)`) by packing the charset
left-to-right in charset order: start at (0,0); if `x + width[i] > atlasWidth`, wrap to
`x=0, y+=lineHeight`. `lineHeight = atlasHeight / rows[f]` with `rows = {5,1,5,3}`.
Glyph i is drawn by clipping a `width[i] x lineHeight` rect and blitting the whole
atlas at `(x - xs[i], y - ys[i])` (classic clip-blit, MIDP anchor 20 = TOP|LEFT).

### 1.3 Charset & index mapping

Fonts 0/2/3 charset (index = position, 63 glyphs):

```
abcdefghijklmnopqrstuvwxyz0123456789.,:;'"!?/()#@*-_ ˇ§¨°´`˜ ¿¡ßç
 0..25 letters   26..35 digits    36..51 punct     52..58   59..62
```

(the two spaces above are for readability only; `§` is drawn as a circumflex glyph).

Encoding (`at.a(int font, String)` → `byte[]` of glyph indices):
- String is lower-cased first (font is caps-only; case is cosmetic).
- Precomposed accented chars `ěščřžýáíéúůďťňäåâöèàêüûùôóòìîñ´’‘` are decomposed via a
  table into base letter + combining mark appended AFTER the base
  (e.g. `é` → `e`,`´`; `â` → `a`,`§`; `’`/`‘`/`´` → `'`). Marks: `ˇ`=caron, `´`=acute,
  `` ` ``=grave, `§`=circumflex, `°`=ring, `¨`=umlaut, `˜`=tilde.
- Control bytes: `' '` → **-1** (space), `'~'` → **-2** (hard newline), `'^'` → **-3**
  (zero-width marker, skipped in measure/draw; marks section headings in credits/help text).
- Any other char → `(byte)charset.indexOf(ch)`.

Combining-mark glyphs (indices 52-58 in fonts 0/2/3, flagged via `d[f]` strings) have
special layout: drawn centered over the *previous* glyph
(`x += prevWidth/2 - markWidth/2`), advance 0; after a mark, the pen advances by the
*previous* glyph's width.

### 1.4 Measuring — `at.a(int font, byte[], int len)`

`width = Σ` over bytes: skip `-3` and combining marks; `-1` adds `space[f]+tracking[f]`;
glyph adds `width[g]+tracking[f]`. (`-2` newline adds nothing.) Helpers:
`at.a(int,byte)` single-glyph advance, `at.a(int,String)` (measures using **font 0**
metrics regardless of the arg), instance `f(textId)` = width of stored text.
`at.e(f)` returns line height; `at.a(int font, int number)` converts an integer 0..999999
to digit glyph bytes.

### 1.5 Drawing — `at.a(Graphics g, int font, byte[] codes, int x, int y, int clipL, int clipR, int anchor)`

Anchor flags mirror MIDP: `8`=RIGHT (x -= textWidth), `1`=HCENTER (x -= w/2),
`0x20|0x40`=BOTTOM/BASELINE (y -= lineHeight), `2`=VCENTER (y -= lineHeight/2).
Only glyphs intersecting `[clipL, clipR)` are blitted. Public wrappers pass
`(MIN_INT, MAX_INT)`. Instance overload `a(Graphics, int textId, int x, int y, int anchor)`
draws one stored line of text `textId` in that text's assigned font.
There are **no palette/tint color options** — "color variants" are simply duplicate
atlases (font 2 = orange copy of font 0's yellow atlas).

### 1.6 Word wrap — `at.a(int font, byte[] codes, int maxWidth)` → `byte[][]` lines

Greedy word wrap at spaces (`-1`); `-2` forces a line break; `!` and `?` (glyph
indices 42/43) stay attached to the preceding word (a space directly before them is
not a break point). Wrap widths: UI strings `at.a()` = `g.a - 74`; text ids 128-132
and all level texts use `g.a - 10` (`g.a`/`g.b` = Canvas width/height).

## 2. String/text system

### 2.1 Languages

`at.a[] = {en, de, fr, es, it, cz, chi, ru}`. `at.d()` picks the first language whose
`/t_pointer.<lang>` resource exists (this JAR ships only `.en`).

### 2.2 `/t_pointer.<lang>` — UI string table (loaded by `at.f()`)

Format: UTF-8 text, entries separated by `'|'` (0x7C), terminated by a 0x00 byte,
followed by a **big-endian int32 checksum = sum of all entry bytes** (pipes excluded);
on mismatch the table is discarded. `t_pointer.en`: 134 entries, checksum 426529 (valid).

Each entry i gets a font id (`byte fontOf[i]`): default **0**; the ~38 "long" ids
(word-wrapped: 0-2,16,19,22-25,31,32,47,53-55,58,59,61,85-87,89,95,96,102-104,109,111,
119,120,125,127-132) plus 15,97,98,99 get font **3**; id 108 gets font **2**.
Wrapped entries are stored as `byte[][]` line arrays; entry 0 (credits) gets
`"v" + MIDlet-Version` spliced in as its 2nd line at load time. Code references
strings by index: e.g. `textEngine.a(g, 30, x, y, anchor)` draws "main menu",
`f(48)` measures "waiting...", `at.e(a(15))` = line height of the "level secrets" font.

### 2.3 `/tl_pointer.<lang>` — level/dialogue text table (lazy, `at.c()`)

Same `'|'`-separated 0x00-terminated format, **no checksum**. Entry index = text slot;
a `boolean[200]` request mask (set by level scripts) controls which entries are decoded
(others are skipped byte-by-byte). Each loaded entry is encoded in **font 3** and
wrapped to `g.a - 10`. 159 entries; slots 0-8 empty; slots 9+ are the story dialogue
(speaker names come from t_pointer ids 76-83,90: Honeybucket, Eyeling, Gish, Gimp,
Hera, Narrator, Khafe). `at.a(int levelIdx)` maps level → title bytes: intro→text 69,
levels 15/26→112 ("bonus"), 35/67→113 ("outro"), playgrounds 36-40→114-118, otherwise a
generated "1-5"/"c-3"/"d-2"/"r-7" label, with boss-name suffix texts 70-73
(" bucket"/" khafe"/" ev'ill"/" hera") appended on boss levels.

### 2.4 `/tz.<lang>` + `/zc-<locale>.txt` — Zeemote controller strings (`an.java`)

`an.java` is the Zeemote JZC library's StringResource class. Default resource is
`/zc-en-US.txt`; `Main` calls `an.a("/tz." + lang)` + `an.b("UTF-8")` so the game's
`/tz.<lang>` overrides it (in this JAR `tz.en` is byte-identical to `zc-en-US.txt`).
`zcv` holds the Zeemote library version, `1.3.7`. Format: lines split on `\n`
(trailing `\r` stripped, empty lines skipped), referenced by 0-based line index via
`an.a(int id, Object[] args)`. **Placeholders `%N`** substitute `args[N]`
(e.g. "Connected to %0.", "(%0 of %1)"); `\` escapes the next char. 32 strings
(Bluetooth search/connect/auto-connect UI).

## 3. Verification

`parse_fonts.py` (same directory) reimplements the packing, encoding and measuring,
validates the t_pointer checksum, and dumps all tables; its full output is in
`parse_out.txt`. Sample width check: "quit" in font 0 = 17+17+9+21 - 4x1 = 60 px, matching
`at.a(0, ...)`.

---

## Appendix A — Glyph tables (char → atlas x,y, width)


### Font 0 (image 8, 238x200, line height 40, space 17, tracking -1)

| idx | char | x | y | w | combining |
|----|------|---|---|---|-----------|
| 0 | `a` | 0 | 0 | 17 |  |
| 1 | `b` | 17 | 0 | 17 |  |
| 2 | `c` | 34 | 0 | 17 |  |
| 3 | `d` | 51 | 0 | 17 |  |
| 4 | `e` | 68 | 0 | 17 |  |
| 5 | `f` | 85 | 0 | 17 |  |
| 6 | `g` | 102 | 0 | 17 |  |
| 7 | `h` | 119 | 0 | 17 |  |
| 8 | `i` | 136 | 0 | 9 |  |
| 9 | `j` | 145 | 0 | 17 |  |
| 10 | `k` | 162 | 0 | 17 |  |
| 11 | `l` | 179 | 0 | 17 |  |
| 12 | `m` | 196 | 0 | 25 |  |
| 13 | `n` | 221 | 0 | 17 |  |
| 14 | `o` | 0 | 40 | 17 |  |
| 15 | `p` | 17 | 40 | 17 |  |
| 16 | `q` | 34 | 40 | 17 |  |
| 17 | `r` | 51 | 40 | 17 |  |
| 18 | `s` | 68 | 40 | 17 |  |
| 19 | `t` | 85 | 40 | 21 |  |
| 20 | `u` | 106 | 40 | 17 |  |
| 21 | `v` | 123 | 40 | 17 |  |
| 22 | `w` | 140 | 40 | 25 |  |
| 23 | `x` | 165 | 40 | 19 |  |
| 24 | `y` | 184 | 40 | 17 |  |
| 25 | `z` | 201 | 40 | 17 |  |
| 26 | `0` | 218 | 40 | 17 |  |
| 27 | `1` | 0 | 80 | 11 |  |
| 28 | `2` | 11 | 80 | 17 |  |
| 29 | `3` | 28 | 80 | 17 |  |
| 30 | `4` | 45 | 80 | 17 |  |
| 31 | `5` | 62 | 80 | 17 |  |
| 32 | `6` | 79 | 80 | 17 |  |
| 33 | `7` | 96 | 80 | 17 |  |
| 34 | `8` | 113 | 80 | 17 |  |
| 35 | `9` | 130 | 80 | 17 |  |
| 36 | `.` | 147 | 80 | 9 |  |
| 37 | `,` | 156 | 80 | 9 |  |
| 38 | `:` | 165 | 80 | 9 |  |
| 39 | `;` | 174 | 80 | 9 |  |
| 40 | `'` | 183 | 80 | 9 |  |
| 41 | `"` | 192 | 80 | 18 |  |
| 42 | `!` | 210 | 80 | 9 |  |
| 43 | `?` | 219 | 80 | 17 |  |
| 44 | `/` | 0 | 120 | 21 |  |
| 45 | `(` | 21 | 120 | 13 |  |
| 46 | `)` | 34 | 120 | 13 |  |
| 47 | `#` | 47 | 120 | 25 |  |
| 48 | `@` | 72 | 120 | 21 |  |
| 49 | `*` | 93 | 120 | 15 |  |
| 50 | `-` | 108 | 120 | 17 |  |
| 51 | `_` | 125 | 120 | 17 |  |
| 52 | `ˇ` | 142 | 120 | 17 | yes |
| 53 | `§` | 159 | 120 | 17 | yes |
| 54 | `¨` | 176 | 120 | 17 | yes |
| 55 | `°` | 193 | 120 | 9 | yes |
| 56 | `´` | 202 | 120 | 16 | yes |
| 57 | ``` | 218 | 120 | 16 | yes |
| 58 | `˜` | 0 | 160 | 17 | yes |
| 59 | `¿` | 17 | 160 | 17 |  |
| 60 | `¡` | 34 | 160 | 9 |  |
| 61 | `ß` | 43 | 160 | 19 |  |
| 62 | `ç` | 62 | 160 | 17 |  |

### Font 1 (image 7, 15x7, line height 7, space 6, tracking +1)

| idx | char | x | y | w | combining |
|----|------|---|---|---|-----------|
| 0 | `0` | 0 | 0 | 6 |  |
| 1 | `1` | 6 | 0 | 3 |  |
| 2 | `3` | 9 | 0 | 6 |  |

### Font 2 (image 237, 238x200, line height 40, space 17, tracking -1)

| idx | char | x | y | w | combining |
|----|------|---|---|---|-----------|
| 0 | `a` | 0 | 0 | 17 |  |
| 1 | `b` | 17 | 0 | 17 |  |
| 2 | `c` | 34 | 0 | 17 |  |
| 3 | `d` | 51 | 0 | 17 |  |
| 4 | `e` | 68 | 0 | 17 |  |
| 5 | `f` | 85 | 0 | 17 |  |
| 6 | `g` | 102 | 0 | 17 |  |
| 7 | `h` | 119 | 0 | 17 |  |
| 8 | `i` | 136 | 0 | 9 |  |
| 9 | `j` | 145 | 0 | 17 |  |
| 10 | `k` | 162 | 0 | 17 |  |
| 11 | `l` | 179 | 0 | 17 |  |
| 12 | `m` | 196 | 0 | 25 |  |
| 13 | `n` | 221 | 0 | 17 |  |
| 14 | `o` | 0 | 40 | 17 |  |
| 15 | `p` | 17 | 40 | 17 |  |
| 16 | `q` | 34 | 40 | 17 |  |
| 17 | `r` | 51 | 40 | 17 |  |
| 18 | `s` | 68 | 40 | 17 |  |
| 19 | `t` | 85 | 40 | 21 |  |
| 20 | `u` | 106 | 40 | 17 |  |
| 21 | `v` | 123 | 40 | 17 |  |
| 22 | `w` | 140 | 40 | 25 |  |
| 23 | `x` | 165 | 40 | 19 |  |
| 24 | `y` | 184 | 40 | 17 |  |
| 25 | `z` | 201 | 40 | 17 |  |
| 26 | `0` | 218 | 40 | 17 |  |
| 27 | `1` | 0 | 80 | 11 |  |
| 28 | `2` | 11 | 80 | 17 |  |
| 29 | `3` | 28 | 80 | 17 |  |
| 30 | `4` | 45 | 80 | 17 |  |
| 31 | `5` | 62 | 80 | 17 |  |
| 32 | `6` | 79 | 80 | 17 |  |
| 33 | `7` | 96 | 80 | 17 |  |
| 34 | `8` | 113 | 80 | 17 |  |
| 35 | `9` | 130 | 80 | 17 |  |
| 36 | `.` | 147 | 80 | 9 |  |
| 37 | `,` | 156 | 80 | 9 |  |
| 38 | `:` | 165 | 80 | 9 |  |
| 39 | `;` | 174 | 80 | 9 |  |
| 40 | `'` | 183 | 80 | 9 |  |
| 41 | `"` | 192 | 80 | 18 |  |
| 42 | `!` | 210 | 80 | 9 |  |
| 43 | `?` | 219 | 80 | 17 |  |
| 44 | `/` | 0 | 120 | 21 |  |
| 45 | `(` | 21 | 120 | 13 |  |
| 46 | `)` | 34 | 120 | 13 |  |
| 47 | `#` | 47 | 120 | 25 |  |
| 48 | `@` | 72 | 120 | 21 |  |
| 49 | `*` | 93 | 120 | 15 |  |
| 50 | `-` | 108 | 120 | 17 |  |
| 51 | `_` | 125 | 120 | 17 |  |
| 52 | `ˇ` | 142 | 120 | 17 | yes |
| 53 | `§` | 159 | 120 | 17 | yes |
| 54 | `¨` | 176 | 120 | 17 | yes |
| 55 | `°` | 193 | 120 | 9 | yes |
| 56 | `´` | 202 | 120 | 16 | yes |
| 57 | ``` | 218 | 120 | 16 | yes |
| 58 | `˜` | 0 | 160 | 17 | yes |
| 59 | `¿` | 17 | 160 | 17 |  |
| 60 | `¡` | 34 | 160 | 9 |  |
| 61 | `ß` | 43 | 160 | 19 |  |
| 62 | `ç` | 62 | 160 | 17 |  |

### Font 3 (image 230, 261x72, line height 24, space 11, tracking +0)

| idx | char | x | y | w | combining |
|----|------|---|---|---|-----------|
| 0 | `a` | 0 | 0 | 11 |  |
| 1 | `b` | 11 | 0 | 11 |  |
| 2 | `c` | 22 | 0 | 11 |  |
| 3 | `d` | 33 | 0 | 11 |  |
| 4 | `e` | 44 | 0 | 11 |  |
| 5 | `f` | 55 | 0 | 11 |  |
| 6 | `g` | 66 | 0 | 11 |  |
| 7 | `h` | 77 | 0 | 11 |  |
| 8 | `i` | 88 | 0 | 5 |  |
| 9 | `j` | 93 | 0 | 11 |  |
| 10 | `k` | 104 | 0 | 11 |  |
| 11 | `l` | 115 | 0 | 11 |  |
| 12 | `m` | 126 | 0 | 17 |  |
| 13 | `n` | 143 | 0 | 11 |  |
| 14 | `o` | 154 | 0 | 11 |  |
| 15 | `p` | 165 | 0 | 11 |  |
| 16 | `q` | 176 | 0 | 11 |  |
| 17 | `r` | 187 | 0 | 11 |  |
| 18 | `s` | 198 | 0 | 11 |  |
| 19 | `t` | 209 | 0 | 13 |  |
| 20 | `u` | 222 | 0 | 11 |  |
| 21 | `v` | 233 | 0 | 11 |  |
| 22 | `w` | 244 | 0 | 17 |  |
| 23 | `x` | 0 | 24 | 11 |  |
| 24 | `y` | 11 | 24 | 11 |  |
| 25 | `z` | 22 | 24 | 11 |  |
| 26 | `0` | 33 | 24 | 11 |  |
| 27 | `1` | 44 | 24 | 7 |  |
| 28 | `2` | 51 | 24 | 11 |  |
| 29 | `3` | 62 | 24 | 11 |  |
| 30 | `4` | 73 | 24 | 11 |  |
| 31 | `5` | 84 | 24 | 11 |  |
| 32 | `6` | 95 | 24 | 11 |  |
| 33 | `7` | 106 | 24 | 11 |  |
| 34 | `8` | 117 | 24 | 11 |  |
| 35 | `9` | 128 | 24 | 11 |  |
| 36 | `.` | 139 | 24 | 5 |  |
| 37 | `,` | 144 | 24 | 5 |  |
| 38 | `:` | 149 | 24 | 5 |  |
| 39 | `;` | 154 | 24 | 5 |  |
| 40 | `'` | 159 | 24 | 5 |  |
| 41 | `"` | 164 | 24 | 11 |  |
| 42 | `!` | 175 | 24 | 5 |  |
| 43 | `?` | 180 | 24 | 11 |  |
| 44 | `/` | 191 | 24 | 17 |  |
| 45 | `(` | 208 | 24 | 9 |  |
| 46 | `)` | 217 | 24 | 9 |  |
| 47 | `#` | 226 | 24 | 15 |  |
| 48 | `@` | 241 | 24 | 14 |  |
| 49 | `*` | 0 | 48 | 11 |  |
| 50 | `-` | 11 | 48 | 13 |  |
| 51 | `_` | 24 | 48 | 11 |  |
| 52 | `ˇ` | 35 | 48 | 7 | yes |
| 53 | `§` | 42 | 48 | 7 | yes |
| 54 | `¨` | 49 | 48 | 11 | yes |
| 55 | `°` | 60 | 48 | 7 | yes |
| 56 | `´` | 67 | 48 | 8 | yes |
| 57 | ``` | 75 | 48 | 7 | yes |
| 58 | `˜` | 82 | 48 | 9 | yes |
| 59 | `¿` | 91 | 48 | 11 |  |
| 60 | `¡` | 102 | 48 | 5 |  |
| 61 | `ß` | 107 | 48 | 13 |  |
| 62 | `ç` | 120 | 48 | 11 |  |

## Appendix B — t_pointer.en full string table (134 entries, `~` shown as ` / `)

| id | font | text |
|----|------|------|
| 0 | 3 | ^Gish Reloaded / (c) 2010 Hardwire / All rights reserved. / www.gishmobile.com /  /  / ^developer / Ondřej Mocný / www.hardwire.cz /  / ^art / Stefan åhlin / www.heartfloppy.se /  / Official licensed product of Cryptic Sea. /  / powered by the Bloft physics engine / bloft.hardwire.cz /  / ^Published by / Pixalon Studios /  / ^Distributed by / (c) 2010 / www.handy-games.com GmbH. / All rights reserved. |
| 1 | 3 | ^controls / You can control the movement of Gish by pointing next to his body in the direction you want him to move in. If Gish is standing on a horizontal surface, you can jump by pointing upwards from his body. You can change Gish's surface by clicking on the bottom left corner of the screen. If you poke the body of Gish he becomes angry, which can be used for throwing objects or killing enemies! /  / More info at www.gishmobile.com |
| 2 | 3 |  / ^secrets / in each level (except the bossfight levels) there is at least one secret area. If you find more of these secrets, you will unlock several bonus playground maps as well as additional multiplayer deathmatch and race maps. / You can see the number of secrets found in the 'choose map' menu. /  / ^goodies / You can also sometimes collect goodies, special items which give you a secret code. Enter the codes at www.gishmobile.com to get wallpapers and ringtones! |
| 3 | 0 | loading |
| 4 | 0 | back |
| 5 | 0 | quit |
| 6 | 0 | restart |
| 7 | 0 | ok |
| 8 | 0 | yes |
| 9 | 0 | no |
| 10 | 0 | help |
| 11 | 0 | settings |
| 12 | 0 | continue |
| 13 | 0 | singleplayer |
| 14 | 0 | secrets: |
| 15 | 3 |  level secrets |
| 16 | 3 | enable z-controller? (confirm only if you know what this means!) |
| 17 | 0 | z-controller |
| 18 | 0 | language |
| 19 | 3 | enable sound? |
| 20 | 0 | find clients |
| 21 | 0 | wait for server |
| 22 | 3 | searching for clients... |
| 23 | 3 | waiting for server... |
| 24 | 3 | connection error |
| 25 | 3 | no phone was found |
| 26 | 0 | multiplayer |
| 27 | 0 | campaign |
| 28 | 0 | choose map |
| 29 | 0 | about |
| 30 | 0 | main menu |
| 31 | 3 | Do you want to quit the current game? |
| 32 | 3 | Do you want to start a new game? The current one will be lost! |
| 33 | 0 | sound vol.:  |
| 34 | 0 | vibration:  |
| 35 | 0 | debug info:  |
| 36 | 0 | completed! |
| 37 | 0 | score: |
| 38 | 0 | time: |
| 39 | 0 | game |
| 40 | 0 | finished! |
| 41 | 0 | level |
| 42 | 0 | level  |
| 43 | 0 | cooperation maps |
| 44 | 0 | deathmatch maps |
| 45 | 0 | race maps |
| 46 | 0 | setup game |
| 47 | 3 | disconnect? |
| 48 | 0 | waiting... |
| 49 | 0 | winner! |
| 50 | 0 | loser! |
| 51 | 0 | round winner! |
| 52 | 0 | upload |
| 53 | 3 | upload to gishmobile.com? |
| 54 | 3 | connecting |
| 55 | 3 | uploaded |
| 56 | 0 | your name |
| 57 | 0 | name:  |
| 58 | 3 | You should try harder to have your name entered into the highscores. |
| 59 | 3 | Your name is now saved in highscores! |
| 60 | 0 | highscores |
| 61 | 3 | no highscores saved! |
| 62 | 0 | details:  |
| 63 | 0 | high |
| 64 | 0 | low |
| 65 | 0 | FPS:  |
| 66 | 0 | show UI:  |
| 67 | 0 | medium |
| 68 | 0 | turbo |
| 69 | 0 | intro |
| 70 | 0 |  bucket |
| 71 | 0 |  khafe |
| 72 | 0 |  ev'ill |
| 73 | 0 |  hera |
| 74 | 0 | playgrounds |
| 75 | 0 | round loser! |
| 76 | 0 | Honeybucket |
| 77 | 0 | Eyeling |
| 78 | 0 | Gish |
| 79 | 0 | Gimp |
| 80 | 0 | Hera |
| 81 | 0 | hint! |
| 82 | 0 | hints:  |
| 83 | 0 | Narrator |
| 84 | 0 | *playground unlocked* |
| 85 | 3 | You've found a goodie! You can see all your goodies from the 'singleplayer' menu.  / goodie code: |
| 86 | 3 | Finish some single player storymode levels first! |
| 87 | 3 | You have to find more secrets in single player levels to get access to this feature! Only previously unvisited secrets count. |
| 88 | 0 | goodies |
| 89 | 3 | These secret codes represent goodies that you have found.  / Use them at www.gishmobile.com to get actual goodies! |
| 90 | 0 | Khafe |
| 91 | 0 |  global secrets |
| 92 | 0 | *death map unlocked* |
| 93 | 0 | *race map unlocked* |
| 94 | 0 | singleplayer maps |
| 95 | 3 | Finish some multiplayer storymode levels first! |
| 96 | 3 | You haven't found any goodies yet! Look for secret areas in the singleplayer levels. |
| 97 | 3 | total score: |
| 98 | 3 | total time: |
| 99 | 3 | total secrets: |
| 100 | 0 | go |
| 101 | 0 | ready |
| 102 | 3 | you can't restart deathmatch and race maps! |
| 103 | 3 | quit the game? |
| 104 | 3 | enable music? |
| 105 | 0 | music vol.:  |
| 106 | 0 | load game |
| 107 | 0 | downloaded levels |
| 108 | 2 | level url: |
| 109 | 3 | download complete |
| 110 | 0 | -download- |
| 111 | 3 | level already exists |
| 112 | 0 | bonus |
| 113 | 0 | outro |
| 114 | 0 | filthy sewers |
| 115 | 0 | egypt secrets |
| 116 | 0 | jungle fever |
| 117 | 0 | broken bridge |
| 118 | 0 | jungle river |
| 119 | 3 | delete level? |
| 120 | 3 | corrupted level |
| 121 | 0 | accelerom.:  |
| 122 | 0 | enable accelerometer? |
| 123 | 0 | more games |
| 124 | 0 | start game |
| 125 | 3 | Restart the current level? Your progress will be lost. |
| 126 | 0 | sound:  |
| 127 | 3 | ^controls / you can control the movement of Gish using the Zeemote controller joystick. If Gish is standing on a horizontal surface, you can jump by moving the joystick in the up direction. The zeemote controller button C enables / disables sliding, which can be used to get through tight spots. The the zeemote controller button A  enables / disables sticking, which can be used to climb walls and the ceiling. The zeemote controller button B makes Gish angry, which can be used for throwing objects or killing enemies! /  / More info at www.gishmobile.com |
| 128 | 3 | Gish becomes 'slick' when you press the zeemote controller button C (his surface becomes brownish). When Gish is slick he can easily slide through narrow passages, but can't climb walls. |
| 129 | 3 | Gish jumps best when he's flattened. Move the zeemote controller joystick up and right. Stick to the top of the wall. |
| 130 | 3 | Gish becomes 'sticky' when you press the zeemote controller button A (his surface becomes grayish). When sticky, Gish can climb the walls and ceiling. |
| 131 | 3 | Boxes can be easily operated if you stick them to your body and then press the zeemote controller button B to fire them away from you. |
| 132 | 3 | You can also squish enemies by jumping on them when Gish is angry (press the zeemote controller button B). |
| 133 | 0 |  |

## Appendix C — tl_pointer.en level/dialogue table (non-empty slots)

| slot | text |
|------|------|
| 9 | Our story begins on a midnight walk... or in Gish and his tarball kids' case, a midnight roll. A roll rudely interrupted by a rancid little creature of the sewers... |
| 10 | Help! |
| 11 | Pfew! I know that stench... Hera's henchman from the Sewers of Dross! |
| 12 | Aw, don't you go worryin' about your little tarballs, Gish. We'll take gooood care a'them! Ahahaha! *cough* |
| 13 | Daddy! Help! |
| 14 | Shut yo' tarhole you little creep! Oh, Hera says hi. Hahaha! *cough* Smell ya later! |
| 15 | Hera? As in ex-girlfriend Hera? As in Hera who I threw into the lava Hera? And what does she want with my Eyelings anyway? This can only mean one thing... a sequel. Time to kick some ass and get to the bottom of this! |
| 16 | Gish becomes 'slick' when you change his surface by clicking the bottom left corner of the screen (his surface becomes brownish). When Gish is slick he can easily slide through narrow passages, but can't climb walls. |
| 17 | smazano |
| 18 | Buttons open doors and activate lifts. The light near the button is green if the lift/door is activated. |
| 19 | You found a secret passage! Find more to unlock bonus maps. There is at least one on every level so keep your eyes open! |
| 20 | Hey this is not how I designed the story! What's going on?! We're supposed to be in the sewers. |
| 21 | At least it doesn't smell that bad here... |
| 22 | About time someone built me a monumental statue. It looks adequately hungry and mean. Could be more round though... |
| 23 | Gish becomes 'sticky' when you change his surface by clicking the bottom left corner of the screen (his surface becomes grayish). When sticky, Gish can climb the walls and ceiling. |
| 25 | This wall is too slippery for Gish to climb. |
| 26 | Gish jumps best when he's flattened. Point up in the direction from Gish to jump. Keep the pointer there to jump higher. Stick to the top of the wall. |
| 27 | I feel the original plotline returning, Gish! Finally! |
| 28 | You know, I don't mind to kick Hera's ass in an alternative way. |
| 29 | Hm, that's strange, the water should be frozen at this temperature... |
| 30 | Dad! I chewed my way out of the sack and escaped! |
| 31 | Good boy! Don't forget to floss when you get home. |
| 32 | Aw, Dad! |
| 33 | Spikes and enemies can hurt you! You can see your health level in the top left corner of the screen. Raise it by collecting tarballs just like the ones next to you now. |
| 34 | Well lookee what we've got here. If it ain't our good ol' buddy Gish. I guess you was thinkin' you could just walk in here and see Hera? Hahaha! Hear that boys? Whaddya say we show our pal here how we look after guests? |
| 35 | Oh, is there complimentary ice cream? |
| 36 | Mr. Questionmark says, don't even try to kill the boss; explore the room instead. |
| 37 | Why do they keep doing this? The storyline has taken a different path again! |
| 38 | I guess it is because the only cool thing in your original story was me! |
| 39 | I... I will just quit this job, that's what I'll do. |
| 40 | You can also squish enemies by jumping on them when Gish is angry (click on his body). |
| 41 | And now for something completely different - Egypt! Don't ask me how we got here! |
| 42 | How did we get here? |
| 43 | I said... |
| 44 | Whatever. |
| 45 | This button must be kept down, otherwise the lift will stop. There is a box nearby, push it onto this button. You know the switch is activated when the light is green. |
| 46 | Another monument as a tribute to me! Aw, they shouldn't have... |
| 47 | Don't you think that it is a monument to somebody else? Perhaps to a bloodthirsty god of some tribal cultists? |
| 48 | Not really. |
| 49 | Why am I even asking. |
| 50 | Boxes can be easily operated if you stick them to your body and click on Gish to fire them away from you. |
| 51 | Well boys, it's a-lookin like our little tar buddy's come back for more. I ain't sittin' on no trapdoor this time! Now squish him, boys! |
| 52 | What's a matter? Too scared to fight me by your lonesome? |
| 53 | Bad news - I just got even more angry! |
| 54 | Hmm, something tells me this heavy box could help me get out of here. I just better not get in the way... |
| 55 | Well lookee who's got himself all trapped up! Poor little Gishy. Hope you're not claustrophobic! *evil laughter* |
| 56 | Psst! He doesn't know I just made a secret passage here! |
| 57 | Looks like I'm in for a slide... I knew I should've packed my snowboard! |
| 58 | You've gotta love the sound of squishing flesh, right? Right?! |
| 59 | You need to get this box to a button on the right side of the room. You can also throw it at an enemy to kill it (target the head). If the box gets destroyed, it will respawn at its original position.  |
| 60 | Throwing the box up might seem hard, but after some practice it's a piece of cake. |
| 61 | So Hera really IS alive... |
| 62 | Ah, my darling Gish... don't look so surprised - do you really think lava would kill me? Ha! Lava can't harm you when you're as hot as I am! And now I'm going to be the most beautiful ball of tar ever! You'll never catch me! |
| 63 | Not catch you? Last I knew, you were slower than a legless drunk. |
| 64 | How dare you! We'll see who's legless here! |
| 65 | Um... actually we both are... |
| 66 | Shut Up! *starts to run* |
| 67 | Ha! Too slow you are, my Love. And now for the main course! |
| 68 | Bling bling! |
| 69 | Arg, Egypt. Probably loads of puzzles and traps just like last time. I hope you know what you're doing, thing which gets in my head and tells me what to do... |
| 70 | Um, I think Gish may be referring to *YOU* there. |
| 71 | *cough cough* Dad! This place, it's h-haunted! Run! |
| 72 | I ain't afraid of no ghost.  |
| 73 | You haven't seen this one yet... *shudders* |
| 74 | A visitor! It's been... well, a while. Now I'm terribly sorry, but I suspect now that I must take your soul. It's a curse, you see. It's all in small print at the entrance to my tomb. |
| 75 | Uh huh. So, I see. And how were you thinking of doing that? |
| 76 | I shall cut you into small pieces with my super sharp sword, then catch your diminishing soul in the plastic bag I have with me. |
| 77 | How did my son put it? Ah yes... Ruuuuun!!! |
| 78 | Please! Anything but the light! Anything! |
| 79 | Anything, you say? *evil flash in his eyes* |
| 80 | Whatever you've got in mind, don't. |
| 81 | And on that day, Gish bravely defeated... |
| 82 | Piece of cake. |
| 83 | ...the army of hideous cultists... |
| 84 | They are kinda cute in fact. |
| 85 | ...who were threatening to enslave the whole world... |
| 86 | Just this island I guess. |
| 87 | Eergh! Would you please let me sound epic here!? |
| 88 | Oh, anytime! |
| 89 | So... we meet again. Do ignore my last performance; we all have our 'off days', don't you think? Now, there still is the pressing issue of me needing your soul. Unfortunately my tomb was built a few thousand years before central heating and it actually needs souls to be anything approaching comfortably warm. |
| 90 | Let me get this straight - you expect me to be your central heating? |
| 91 | No, Mr. Gish, I expect you to die. |
| 92 | Warmth! That's much better, thank you! Terribly sorry about the upset. All's well that ends well, I suppose. See you in the afterlife I'll wager! |
| 93 | Your alignment has been changed to 'CHAOTIC GOOD' |
| 94 | What the heck is chaotic good? |
| 95 | Oops, sorry, wrong game... |
| 96 | You know you don't have to kill everybody! |
| 97 | But I can! Ok I'll skip these two. Hope it'll make you proud. |
| 98 | You didn't think you could get rid of me so easily, Cutie, did you? |
| 99 | Actually, I did. |
| 100 | Well then you've got a lot to learn little boy. Look how little you are compared to me! And in 3 minutes I'll be even larger, after eating your kids! |
| 101 | Don't be so sure. What could you do to stop me? |
| 102 | Perhaps a double-reinforced door will do the trick? *evil laughter* |
| 103 | Mr. Questionmark points out that you can throw Hera by making Gish angry if he is stuck to her. |
| 104 | Now, where's my knife and fork? |
| 105 | Money money money; swinging is so funny, tra la la la la! |
| 106 | Hmm, strange smells, rats... let me guess, another sewer! |
| 107 | What the hell is that? It's got more teeth than a pool of sharks! |
| 108 | The god of the cultists! It's got tentacles coming out of the ground, too! |
| 109 | Aha! A weak spot! I will cut them off, one by one! |
| 110 | Who's your daddy?! You don't like this at all, do you! |
| 111 | You know you shouldn't kill this one. I like him. |
| 112 | Yeah he could be your boyfriend! I'll leave him but tell him to open this door for me. |
| 113 | Mr. Questionmark points out that you can break some springs. This one, for example. |
| 114 | We not hungry. We not kill you. We carry you over if you leave us alone. Grumpfl. |
| 115 | Just taking out the tentacles isn't going to work this time. Hmm, seems hungry... Wonder if he's ever tasted tarball? |
| 116 | As silly as it might sound, jumping into his maw is the only logical option. |
| 117 | Well, I hope I give you indigestion. |
| 118 | Hah! Looks like you bit off a bit more than you could chew! |
| 119 | Strike now, while the creature is doubled over in pain! |
| 120 | Now that was tough. Seems like they didn't like their god gone, haha! I feel we're getting back on the track with my story! Hurray for me! |
| 121 | Yeah. Whatever. |
| 122 | Hmm. Hera's skin sticks to me like chewing gum to the bottom of my shoe. |
| 123 | And now, you and I will stay here. Together. Forever! Forget about Brea! Think of the life we would share here! |
| 124 | Stay together? Here with you? After all that stuff about eating my kids?! |
| 125 | They are children of a human! Wake up, Gish! You can't be with a member of such a subrace! You are destined to be with me! |
| 126 | Well, in that case, it's time to kick destiny's ass! |
| 127 | Nooo! Then we must die together! |
| 128 | The walls of the cave are closing in to crush you! |
| 129 | *dramatic voice* and this is how our little drama ends... |
| 130 | Hang on, there's one more thing to do! |
| 131 | Ready! Aim! Fire! |
| 132 | Wait, wha- *kaboom* |
| 133 | Dad! You made it! |
| 134 | Sure I did! It's OK kids, you're safe now. |
| 135 | Er, yeah, about that... |
| 136 | ? |
| 137 | It just didn't feel like a proper ending... |
| 138 | Alright, kids, what have you done? |
| 139 | Well, we kinda rigged the whole place to, well, explode. |
| 140 | You did WHAT?! |
| 141 | It's no good ending unless everything explodes, right Dad? We thought you'd be pleased. |
| 142 | We'll talk about this later, in the meantime.... Ruuuuun!!! |
| 143 | Welcome to my little playground. Feel free to play with the toys to the death. I'll be over here if you need me. |
| 144 | A long time ago in a sewer far away lived Gish and Hera. They were friends, close friends... lovers, actually. Like a Bonnie and Clyde of the tarball world, this infamous duo was well known for raiding tombs and sewers for trinkets and treasure. |
| 145 | Yeah! |
| 146 | Rock and roll! |
| 147 | If your teammate dies, you can revive him by collecting all his tarballs. |
| 148 | To complete a level, both of you must get to the level exit. Alive. |
| 149 | Ugh, what a stench! |
| 150 | Smells like... |
| 151 | ...go on, say it, like the stuff which collects when a toilet leaks. Go on, everyone says it. You might as well too. |
| 152 | Actually, I think he's kind of cute! |
| 153 | *blushes*  |
| 154 | Ok, that's it, we're going topside, Hera. And you'd better wash your hands or you're not getting any dinner. |
| 155 | Bye, sweet honey... bucket! |
| 156 | You think you have a chance in a squadron? Well you don't! |
| 157 | I should get my own piranha pets one day. |

## Appendix D — tz.en / zc-en-US.txt Zeemote strings

| id | text |
|----|------|
| 0 | Controller |
| 1 | Reconnect |
| 2 | Quick Connect |
| 3 | Search |
| 4 | Disconnect |
| 5 | Searching for devices. This may take a few moments. |
| 6 | Searching for devices. This may take a few moments. (%0 found) |
| 7 | Looking up device names. This may take a few moments. (%0 of %1) |
| 8 | Canceling device search. This may take a few moments. |
| 9 | No devices found. Check that your controller is on and search again. |
| 10 | Connecting to %0. This may take a few moments. |
| 11 | Connected to %0. |
| 12 | Unable to connect. Make sure your controller is on and Bluetooth is enabled on your phone. |
| 13 | Disconnecting from %0. |
| 14 | Disconnected from %0. |
| 15 | Unable to disconnect. Please try again in a few moments. |
| 16 | OK |
| 17 | Cancel |
| 18 | Back |
| 19 | Auto Connect: Off |
| 20 | Auto Connect: On |
| 21 | Auto Connect is now enabled. The next time you start the application, it will automatically connect to your controller. |
| 22 | Auto Connect is now disabled. The next time you start the application, you will need to use the menu to connect to your controller. |
| 23 | Select |
| 24 | Retry Connection |
| 25 | Unable to search for devices. Make sure Bluetooth is enabled on your phone. |
| 26 | Unable to search for devices. Restart the application and grant permission to use Bluetooth. |
| 27 | Unable to connect. Restart the application and grant permission to use Bluetooth. |
| 28 | Unable to search for devices. Make sure Bluetooth is enabled on your phone. |
| 29 | Unable to connect. Make sure Bluetooth is enabled on your phone. |
| 30 | Connection to the controller was lost. |
| 31 | Auto Connect is now enabled. Go to the Controller menu to disable it. |
