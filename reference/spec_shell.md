# Gish Reloaded (J2ME) — Game Shell Specification for JS Remake

Reverse-engineered from CFR decompilation of `Gish_Reloaded_360.jar` (obfuscated).
Class roles (decompiled names):

| Class | Role |
|---|---|
| `com.hardwire.blob.Main` | MIDlet: main loop, sounds, vibration, RMS access, level-file parsing, achievements store |
| `g` | Canvas: splash/loading screens, image bank (`images.img/map`), key & pointer routing, text input widget, cheat detection |
| `s` | **Menu state machine** (all shell screens), settings/highscore persistence, Bluetooth flow driver |
| `k` | Game-session manager: current level, mode, score/time, save-game read/write, network sync |
| `ab` | In-game engine (HUD, dialogs, gameplay) — internals out of scope here |
| `at` | Bitmap fonts, string table (`/t_pointer.<lang>`), dialog table (`/tl_pointer.<lang>`), **level list & naming** |
| `r` | Bluetooth (server/client discovery, UUID `01834587449266546213012382234327`) |
| `j` | HTTP thread: highscore upload (`http://hardwire.cz:80/gish/`), level download (`http://www.gishmobile.com/levels/<name>` or raw URL) |
| `ak` | "More games" screen (HandyGames catalog, JAD property `moregames.url`) |
| `an/ad/ap/bh/n/y/bc…` | Zeemote controller SDK UI (strings in `/tz.<lang>`) — can be dropped in remake |

Global app state `Main.a (byte)`: `2` = splash, `3` = loading bar, `1` = menu (`s`), `0` = in-game (`ab`), `6` = More-games/Zeemote sub-screen.

---

## 1. Boot / splash sequence

`Main.run()` while `Main.a==2`:

1. Three logo images are the **first three entries of `images.img`** (internal image ids via `images.map`):
   - stage 0: id **255** — "Developed by **HardWire** / Erphenic Studios" (300×293, white line art), background color `0x1D304E` (dark navy)
   - stage 1: id **254** — **Pixalon Studios** logo (210×75), background white
   - stage 2: id **253** — **HandyGames** logo (300×112), background white
2. stage 3: `/ze_logo.png` (Zeemote logo), background black.

Each stage: image centered at (w/2, h/2); shown max **60 × 50 ms = 3 s**; any key press or pointer tap advances immediately (`g.paint` case 2, `keyPressed`/`pointerPressed` increment stage).

Then `Main.a=3` — **loading screen**: black background, horizontal progress bar centered vertically:
- bar images = `images.img` entries 3–6 (ids 252 fill 1×14, 251 cap 5×14, 249 empty 1×14, 248 end-cap 5×14); bar width = 90 % of `(screenW − 2·capW)`, progress counter 0…196 incremented as assets load (each decoded image +1, checkpoints +4/+10);
- when full, a small blob sprite is drawn at the bar's end.

During loading: language auto-detect (`at.d()`: first of `en,de,fr,es,it,cz,chi,ru` whose `/t_pointer.<lang>` exists), fonts (`at.e()`: font0=img 8 large, font1=img 7 tiny digits, font2=img 237 large gray, font3=img 230 small), string table load, all images/sounds prefetch, settings load (`s.g()`), `achi` store created if absent.

**First-run flow** (no `settings` record → `Main.e=true`), driven by `s.a()`:
- if Zeemote lib present → screen **39**: "enable z-controller? (confirm only if you know what this means!)" [yes/no]
- → screen **40**: "enable sound?" [yes → soundVol=5 / no → 0]
- → screen **50**: "enable music?" [yes → musicVol=3 / no → 0]
- → screen **0** main menu. (Defaults are saved to `settings` on first main-menu entry.)
Subsequent runs go straight to main menu.

---

## 2. Menu framework (class `s`)

### 2.1 Rendering
- `s.b (int)` = current widget type: `0` = **item list menu**, `1` = **text page** (scrollable), `2` = **text input**.
- Background (`s.a(Graphics)`): black fill; decorative splats: img 242 (11×12) at (10,50), (50,100), (w−40,70 RA), (w−100,130 RA), (w−20,h−40 BRA), (30,h−40 BLA); img 243 (38×25) at (w−40,h−10 BR), (70,70 RA); frame corners img 238 (TL 0,0), 239 (TR), 240 (BR), 241 (BL). On tall screens (h ≥ 300) the **"GISH RELOADED" logo img 244** (108×82) is drawn top-center at y=20.
- Layout (`s.c()`): list area `x=35, w=screenW−70`; tall screens: `y=55, h=screenH−110`; small screens `y=18`. Extra offsets when logo shown.
- List items horizontally centered; selected item uses font 0 (bright), others font 2 (gray); too-long items marquee-slide. Value items (settings) draw blinking left/right arrows (img 231/232, animated ±3 px). If list is longer than fits: up/down scroll arrows img 1000/1001 bottom-center; drag-to-scroll supported.
- Soft buttons (images: dark `0x2F2F2F` box with word rendered in font): bottom-left = **OK/select**, bottom-right = **back**; on the highscores screen bottom-left shows **"upload"** (text 52) instead. Popup/text-page background color `0x4F504F` (`-11579569`).

### 2.2 Input mapping (`g`)
- Keys: `2/UP`=up, `8/DOWN`=down, `4/LEFT`=left, `6/RIGHT`=right, `5/FIRE/-5` or soft-left `-6` = select, soft-right `-7` = back. Number keys 1,3,7,9,*,0,# get action ids 4–11.
- Touch: tap an item to select it (tap again = activate); tap left/right half of a value item = decrease/increase; tap bottom-left soft = select/OK; bottom-right soft = back; drag = scroll. Text input: on-screen character grid + `del`; hardware keys use multi-tap (T9-style table in `g`).

### 2.3 Screen state machine — `s.a (byte)` screen id
Menu item labels come from string table `/t_pointer.en` (full list at end).
Static menu layouts `s.a[short[]][]` (index = "layout id", values = string ids):

| Layout | Items (string ids → English) | Used as |
|---|---|---|
| 0 | 13,26,60,11,10,29,5 → singleplayer, multiplayer, highscores, settings, help, about, quit | Main menu |
| 19 | 13,26,**123**,60,11,10,29,5 (adds "more games") | Main menu when `moregames.url` JAD property present |
| 1 | 12,6,11,10,29,30 → continue, restart, settings, help, about, main menu | Pause (single-player) |
| 8 | 12,6,**46**,11,10,29,30 (adds "setup game") | Pause (multiplayer server) |
| 14 | 12,11,10,29,30 (no restart) | Pause (multiplayer client) |
| 2 | 33,105,34,82,62,17 → sound vol., music vol., vibration, hints, details, z-controller | Settings |
| 4 | 21,20 → wait for server, find clients | Multiplayer |
| 6 / 7 | (106,)27,28,74,88,107 → [load game,] campaign, choose map, playgrounds, goodies, downloaded levels | Singleplayer menu (7 = with existing save) |
| 9 / 10 | (106,)27,43,44,45,94,74 → [load game,] campaign, cooperation maps, deathmatch maps, race maps, singleplayer maps, playgrounds | Multiplayer setup (server, 10 = with `msave`) |
| 3,5,11,12,13,15,17,18,21 | dynamic lists (level lists, device list, achievements etc.) | see below |

Settings value definitions (`s.a[2]`): sound vol = numeric 0–10 step 1; music vol = numeric 0–10; vibration = yes(8)/no(9); hints = yes/no; details = low(64)/medium(67)/high(63); z-controller = action (opens Zeemote config, app state 6).
Changing sound/music volume gives immediate feedback (plays click / restarts music). Values applied+saved (`settings` record) on leaving screen.

**Screen ids** (`s.a(byte)` switch — id: content [type] → behavior):

| id | Screen | Notes |
|---|---|---|
| 0 | Main menu [list 0/19] | starts music `sound/sewer.mp3` looped if enabled |
| 1 | Pause menu [list 1/8/14] | layout by session type (`k.b`: 1 local, 2 server, else client) |
| 2 / 18 | Help [text] | text 1 (touch controls; 127 if Zeemote active) + text 2 (secrets/goodies) |
| 3 / 15 | Settings [list 2] | 15 = opened from pause; back applies + saves |
| 4 | SP "choose map" [list 3] | list = completed campaign levels, each `name (foundSecrets/totalSecrets)`; empty → msg 86 |
| 5 | Singleplayer menu [list 6/7] | |
| 6 | Multiplayer menu [list 4] | |
| 7 | Server list [list 5] | found Bluetooth server names; select = connect |
| 8 | "searching for clients..." (22) | server discovery |
| 9 | "waiting for server..." (23) | client advertise; also used mid-game wait |
| 10 | "connection error" (24) | |
| 11 | "no phone was found" (25) | |
| 12 / 19 | About [text 0] | credits + "v"+MIDlet-Version inserted after title |
| 13 | Confirm restart level (125) [yes/no] | yes → reload level |
| 14 | Confirm quit game (31) [yes/no] | yes → back to main menu |
| 16 | Confirm new SP game (32) | yes → delete `save`, start campaign at **intro** (level id 0) |
| 17 | MP setup menu [list 9/10] | server chooses what to play |
| 20 | Coop map list [list 13] | items = completed coop levels (`achi` int2); empty → msg 95 |
| 21 | Deathmatch map list [list 11] | 5 + secret-unlocked maps (see §3.4) |
| 22 | Race map list [list 12] | 5 + secret-unlocked maps |
| 23 | Confirm new MP campaign (32) | yes → delete `msave`, start at coop/00 (id 41) |
| 24 | "disconnect?" (47) [yes/no] | yes → screen 17 |
| 25 | leave-MP confirm path | via pause "main menu" |
| 26 | Name entry for highscore (56 "your name") [input, max 11 chars] | OK → write `score` → screen 30/31 |
| 27 | "upload to gishmobile.com?" (53) [yes/no] | yes → 33 + HTTP post |
| 28 / 29 | Highscores [text] | table built from `score` record: singleplayer: best score (name+score), best time (name+time); multiplayer: same. 28 shows "upload" soft button |
| 30 / 31 | "name saved" (59) / "try harder" (58) | |
| 32 | "no highscores saved!" (61) | |
| 33 / 34 / 35 | connecting (54) / connection error (24) / uploaded (55) | |
| 36 / 37 / 38 | Zeemote SDK alert / list / progress (animated "....") | remake: drop |
| 39 | "enable z-controller?" (16) | first-run |
| 40 | "enable sound?" (19) | first-run |
| 50 | "enable music?" (104) | first-run |
| 44 / 45 | Playgrounds list [list 17] | unlocked playgrounds (names, texts 114–118); none → msg 87. 44 = SP (local), 45 = MP |
| 46 | Goodies [text] | header text 89 + one 4-digit code per found goodie; none → msg 96 |
| 47 | MP "singleplayer maps" list [list 18] | completed SP levels, playable in MP mode |
| 48 | "you can't restart deathmatch and race maps!" (102) | |
| 49 | Confirm quit app (103) | yes → exit |
| 61 | Downloaded levels [list 21] | RMS `gigolvl` entries + final "-download-" item (110); key `#`/long = delete → 67 |
| 62–68 | download flow: error(24) / complete(109) / URL input(108, max 256) / already exists(111) / connecting(54) / delete?(119) / corrupted(120) | |

Back (`s.j()`) returns along the obvious parent chain (e.g. 4→5→0; 20/21/22/23/24/45/47→17; 3→0; 15→1; 62…68→61→5). A 10-deep selection-index stack restores cursor positions.

### 2.4 Menu music
Single track `/sound/sewer.mp3` (player index 12), looped, volume `musicVol×10`%. Started on entering main menu (and SP choose-map) if `musicVol>0`; keeps playing through menus; stopped when a level loads, when music vol set to 0, and while paused-app.

---

## 3. Level / world structure

### 3.1 Master level table (`at.b`, index = **level id** used everywhere)
File = `/levels/<entry>.lvl`:

| id | entry | id | entry |
|---|---|---|---|
| 0 | `intro` | 36–40 | `playgr/01`–`playgr/05` |
| 1–15 | `1/01_s0` … `1/15_s18` (world 1, 15 levels) | 41–67 | `coop/00`–`coop/26` (27 levels) |
| 16–26 | `2/01_e0` … `2/11_e15` (world 2, 11 levels) | 68–77 | `dm/01`–`dm/10` |
| 27–35 | `3/01_h2` … `3/09_h15` (world 3, 9 levels) | 78–87 | `race/01`–`race/10` |

(The `_sN/_jN/_eN/_hN` filename suffixes are internal editor tags; ordering is purely by the two-digit prefix.)

### 3.2 Display names (`at.a(int id)`)
- id 0 → "intro" (69); ids 1–15 → `"1-1"…"1-15"`; 16–26 → `"2-1"…"2-11"`; 27–35 → `"3-1"…"3-9"`; coop → `"c-1"…"c-27"`; dm → `"d-1"…"d-10"`; race → `"r-1"…"r-10"`.
- Overrides: id 15 and id 26 → "bonus" (112); id 35 and id 67 → "outro" (113).
- Boss-name suffixes appended: ids 5, 11 → `" bucket"`; ids 20, 23 → `" khafe"`; ids 28, 32 → `" ev'ill"`; ids 14, 25, 34 → `" hera"` (e.g. "1-5 bucket", "2-10 hera").
- Playground names: 36–40 → "filthy sewers", "egypt secrets", "jungle fever", "broken bridge", "jungle river" (114–118).

### 3.3 Modes (`k.a(mode b, submode e)`)
`k.b` session type: `1` = local single-player, `2` = Bluetooth server, `4` = Bluetooth client. `k.e` submode:
- `e=0` **SP campaign**: sequential ids 0(intro)→1→…→35(outro); progress saved to `save` after each completion (next id = `at.a(id)=id+1`).
- `e=1` **SP single map** ("choose map" replays, playgrounds, downloaded levels): no progress saving.
- `e=2` **MP campaign (cooperation)**: ids 41→…→67(outro), progress in `msave`. 2 players; teammate revival rules (dialog texts 147/148). *Remake: run as local 2-blob co-op or select-one-blob.*
- `e=3` **MP single map** (coop map, singleplayer map, playground replay via MP setup).
- `e=4` **deathmatch** (`dm/*`): round-based; first to **5 round wins** (checked `k.e[0]==5||k.e[1]==5`) ends match → winner (49) / loser (50); round texts 51/75; countdown overlay "ready"(101)→"go"(100) slides across screen between rounds; restart is forbidden (msg 102).
- `e=5` **race** (`race/*`): same round/win structure as deathmatch, win by reaching exit first.
- Multiplayer transport is Bluetooth (`r`, SPP UUID `01834587449266546213012382234327`); server picks map on screen 17, sends `{levelId:short, mode:byte}` to client; state sync each tick (positions, sounds, tile-destruction events). *Remake: replace with local play; all mode/unlock logic above is transport-independent.*

### 3.4 Unlocks & progression
Stored in `achi` record (see §4). Level completion counters unlock replays; **secrets** (total 40) unlock bonus maps:
- SP "choose map"/MP "singleplayer maps" list = first `spCompleted` campaign levels (each completed level replayable).
- Coop map list = first `mpCompleted` coop levels.
- **Playgrounds** (5): unlocked at total secrets ≥ `{2,10,20,30,39}` (Main.b).
- **Deathmatch maps**: 5 always available + 5 more at secrets ≥ `{4,12,18,25,34}` (Main.c) → d-1…d-10.
- **Race maps**: 5 always + 5 more at secrets ≥ `{7,15,22,28,37}` (Main.d) → r-1…r-10.
When a newly found secret makes the total hit one of those thresholds the in-game popup says "*playground unlocked*" (84) / "*death map unlocked*" (92) / "*race map unlocked*" (93), otherwise the generic secret message (tl 19).
- Secrets per campaign level (`Main.a`, index = id−1 for ids 1…35): `{1,2,2,1,0,1,2,2,1,1,0,3,1,0,0,2,2,2,1,0,2,1,0,1,0,1,3,0,1,2,1,0,3,0,1}` (sum 40). Bossfight levels have none.
- **Goodies** (8 collectibles, tile type 70): exist in level ids 1, 5, 9, 12, 17, 21, 26, 30 with secret codes `Main.e` = 2327, 6382, 8759, 7437, 7519, 9983, 1166, 6262 (shown on the Goodies screen, redeemable at gishmobile.com). A collected goodie is removed from the level on reload. Pickup popup = text 85 + code.
- No per-level score/amber targets exist. Score (`k.h`) and time (`k.i`) accumulate across the campaign run and feed the highscore table at the outro; time limit exists only in level id 25 ("2-10 hera"): **180 000 ms countdown**, expiry triggers the fail dialog (tl 104) / death.
- Special level behaviors worth mirroring: id 20 uses darkness overlay (`/img_gish/dark_corner_alpha.png`); ids 14/25/34 spawn the Hera opponent blob; intro (0) and outro levels are playable cutscene levels driven by the dialog script.

### 3.5 Downloaded levels
`.lvl` files fetched by URL (screen 64) or short name (`http://www.gishmobile.com/levels/<name>`), stored in RMS `gigolvl` as `{nameUTF, len:short, bytes}`, listed on screen 61, played as `e=1` with `k.b=-1` (no goodies/secrets tracking), deletable.

---

## 4. Save data (RMS record stores, all names prefixed `"gigo"`)

| Store | Contents |
|---|---|
| `gigosettings` | `byte soundVol(0-10)`, `bool vibration`, `byte detail`, `byte speed(Main.d, default 2)`, `bool showUI`, `byte language`, `bool hints`, `byte musicVol(0-10)`, `bool ×2 (spare)`, `UTF playerName`, `bool` |
| `gigosave` | SP campaign: `int nextLevelId`, `int score`, `int timeMs`, `int deaths/collected(k.j)`, `bool flag` |
| `gigomsave` | MP campaign: same 5 fields |
| `gigoachi` | `int spLevelsCompleted (0–35)`, `int mpLevelsCompleted (0–27)`, `int totalSecrets (0–40)`, then `bool[35][secretsPerLevel]` per-secret found flags (using `Main.a` counts), then `bool[35]` goodie-found flags (indexed by level id) |
| `gigoscore` | 4 slots ×`{UTF name, int score, int time, int j, bool}`: [0]=SP best score, [1]=SP best time, [2]=MP best score, [3]=MP best time |
| `gigolvl` | downloaded levels (multiple records) |

Campaign completion counters only ever increase (written on level complete if greater). Highscore qualification (`s.b()`): campaign finished with `score > slot.score` or `time < slot.time` (or empty slot) → name-entry screen.

"Achievements" = this secrets/goodies system; there is no separate achievement list beyond the counts shown in choose-map (`name (x/y)`) and totals rows (97 "total score:", 98 "total time:", 99 "total secrets:", 91 " global secrets", 15 " level secrets").

---

## 5. Sound & vibration

Players created in `Main` (13 slots, `Manager.createPlayer`), volume via `VolumeControl.setLevel(vol*10)`:

| idx | file | idx | file |
|---|---|---|---|
| 0 | `/sound/gishhit.wav` | 7 | `/sound/splash.wav` |
| 1 | `/sound/tarball.wav` | 8 | `/sound/necksnap.wav` |
| 2 | `/sound/amber.wav` | 9 | `/sound/ropebreak.wav` |
| 3 | `/sound/CLICK015.wav` | 10 | `/sound/bobattack.wav` |
| 4 | `/sound/squish.wav` | 11 | `/sound/visattack.wav` |
| 5 | `/sound/switch.wav` | 12 | `/sound/sewer.mp3` (music, loop) |
| 6 | `/sound/blockbreak.wav` | | |

- SFX play only if `soundVol>0`; one SFX start per tick (`o` guard); music independent via `musicVol` and only in menus.
- Low-memory fallback remaps: splash/necksnap→squish, ropebreak→blockbreak, visattack→gishhit, CLICK015→tarball, bobattack dropped.
- Vibration `Main.a(ms)`: `Display.vibrate(ms)`, rate-limited (min gap = duration), only if enabled; used on landing/impact (half frame-interval ms) and menus don't vibrate. Backlight kept alive with `DeviceControl.setLights(0,100)` every 1 s.
- Frame pacing (`Main.a()`/`Main.d` speed 0–3 → tick 160/115/70/31 ms; default 2 → 70 ms tick ≈14 FPS logic; menus tick at same interval).

---

## 6. In-game shell (HUD, pause, dialogs) — from `ab.c(Graphics)` / `s`

HUD (drawn when UI enabled):
- **Bottom-left**: surface-state button (img `4 + state`, 58×62) showing current tar mode (normal / slick / sticky); tapping it (or corner) cycles Gish's surface.
- **Bottom-right**: pause button (img 2, 58×62) → opens pause menu (`s` screen 1, `Main.a=1`).
- **Top-left**: health bar — cap img 10, animated fill img 14–16 when health <25 % (blinking) else img 12, empty img 13, end cap img 11; width ≈ half screen. Health = current player blob's `d.d` (0–100<<10).
- **Top-right**: score (font 0), = `k.e[player]`.
- **Top-center**: elapsed time `m:ss` (counts **down from 3:00** on level id 25).
- Secret/goodie/unlock banner slides up from bottom (black panel, gray top line, small font), auto-retracts.
- Round banner (dm/race): full-width black band mid-screen, "ready"/"go" text sliding horizontally.
- "waiting..." (48) boxed message center when waiting for the other player.
- Touch-control overlay arrows around Gish (img 256+… direction hints) fade after a few frames.

**Dialog/hint system** (`ab.c(int dialogId)` / `d()`): story lines from `/tl_pointer.en` (159 entries, full script incl. intro/outro cinematics); each has a speaker (table `ab.b`: Honeybucket 76, Eyeling 77, Gish 78, Gimp 79, Hera 80, "hint!" 81, Narrator 83, Khafe 90) and an auto-chain flag (`ab.a[]`) linking consecutive lines; shown in a `0x4F504F` box, select advances. Hints (tap-control tutorials, tl 16/23/26/40/50 etc.) only shown if hints setting on; Zeemote variants override ids 16→128, 26→129, 23→130, 50→131, 40→132.

**Pause menu**: continue (resumes, restarts music-stop state), restart (confirm 125 → reload level; blocked in dm/race with msg 102), setup game (server only → map select 17), settings, help, about, main menu (confirm 31/47). App interruption (`hideNotify`) auto-pauses into it.

**Level-complete flow** (`k.c()` states: 0 play, 2 (re)load, 5 completed, 6 dialog, 7 loading, 8 post-level, 9 round-end):
- campaign: save progress → load next id; after intro → level 1.
- single map: back to its chooser list with cursor advanced.
- SP id 35 / MP id 67 (outro) finished: campaign over — if highscore-worthy → name entry (26) → save+message; else "try harder" (58); save deleted.

---

## 7. Cheat codes (`g`)

Input buffers: last 6 keycodes; last 6 corner-taps (corner zones: x<w/8 & y<h/6 = TL(1), x>7w/8 & y<h/6 = TR(2), BL(3), BR(4)).

1. **Unlock-everything cheat — `g.o()`**: triggered by key sequence **1,3,9,1,9,1** or corner-tap sequence **TL,TR,TL,TL,TR,TR**, only while on the **singleplayer menu** (Main.a==1 && screen 5). Effect: rewrites `achi` to `spCompleted=35, mpCompleted=27, secrets=40`, all per-level secrets found, all 8 goodies found — i.e. everything unlocked (all levels, playgrounds, dm/race maps, goodie codes) — then auto-selects the "playgrounds" item and activates it (simulated fire key), dropping you into the fully-unlocked playgrounds list.
2. **Skip-to-ending cheat (in-game)**: key sequence **1,3,9,1,9** during gameplay (not as Bluetooth client) → `ab.j()`: sets current level to the final one (35 SP / 67 MP-coop) and marks it complete — effectively warps to the outro/ending (and records full campaign completion).

---

## 8. String table `/t_pointer.en` (id → text, for faithful labels)

0 about/credits · 1 help-controls · 2 help-secrets/goodies · 3 loading · 4 back · 5 quit · 6 restart · 7 ok · 8 yes · 9 no · 10 help · 11 settings · 12 continue · 13 singleplayer · 14 "secrets:" · 15 " level secrets" · 16 z-controller confirm · 17 z-controller · 18 language · 19 enable sound? · 20 find clients · 21 wait for server · 22 searching for clients... · 23 waiting for server... · 24 connection error · 25 no phone was found · 26 multiplayer · 27 campaign · 28 choose map · 29 about · 30 main menu · 31 quit-game confirm · 32 new-game confirm · 33 "sound vol.: " · 34 "vibration: " · 35 "debug info: " · 36 completed! · 37 "score:" · 38 "time:" · 39 game · 40 finished! · 41 level · 42 "level " · 43 cooperation maps · 44 deathmatch maps · 45 race maps · 46 setup game · 47 disconnect? · 48 waiting... · 49 winner! · 50 loser! · 51 round winner! · 52 upload · 53 upload to gishmobile.com? · 54 connecting · 55 uploaded · 56 your name · 57 "name: " · 58 try-harder · 59 name saved · 60 highscores · 61 no highscores saved! · 62 "details: " · 63 high · 64 low · 65 "FPS: " · 66 "show UI: " · 67 medium · 68 turbo · 69 intro · 70 " bucket" · 71 " khafe" · 72 " ev'ill" · 73 " hera" · 74 playgrounds · 75 round loser! · 76 Honeybucket · 77 Eyeling · 78 Gish · 79 Gimp · 80 Hera · 81 hint! · 82 "hints: " · 83 Narrator · 84 \*playground unlocked\* · 85 goodie found · 86 finish-SP-levels-first · 87 need-more-secrets · 88 goodies · 89 goodies header · 90 Khafe · 91 " global secrets" · 92 \*death map unlocked\* · 93 \*race map unlocked\* · 94 singleplayer maps · 95 finish-MP-levels-first · 96 no goodies yet · 97 total score: · 98 total time: · 99 total secrets: · 100 go · 101 ready · 102 no-restart-dm/race · 103 quit the game? · 104 enable music? · 105 "music vol.: " · 106 load game · 107 downloaded levels · 108 level url: · 109 download complete · 110 -download- · 111 level already exists · 112 bonus · 113 outro · 114–118 playground names · 119 delete level? · 120 corrupted level · 121 "accelerom.: " · 122 enable accelerometer? · 123 more games · 124 start game · 125 restart confirm · 126 "sound: " · 127–132 Zeemote help/hints.

`/tl_pointer.en` (159 entries) = full in-level dialog script (intro tl 9–15, tutorials, boss banter, outro tl 129–142, coop intro tl 144–157).

---

## 9. Remake flow summary (quick reference)

```
Boot → HardWire logo → Pixalon → HandyGames → [Zeemote] → loading bar
  → (first run: sound? music?) → MAIN MENU (music: sewer.mp3 loop)
MAIN: singleplayer | multiplayer | [more games] | highscores | settings | help | about | quit
  singleplayer: [load game] campaign (intro→1-1…1-15→2-1…2-11→3-1…3-9/outro)
                choose map (completed levels, "name (x/y)" secrets)
                playgrounds (secret-count unlocks) | goodies (codes) | downloaded levels
  multiplayer → (pair) → setup: [load game] campaign(coop 00–26+outro) | coop maps |
                deathmatch maps (first-to-5) | race maps (first-to-5) | singleplayer maps | playgrounds
In-game: HUD (health, score, time, surface btn BL, pause btn BR), dialogs/hints,
  pause = continue/restart/[setup]/settings/help/about/main menu
Persistence: gigo{settings,save,msave,achi,score,lvl}
Cheats: menu 1-3-9-1-9-1 (or TL,TR,TL,TL,TR,TR corner taps) = unlock all; in-game 1-3-9-1-9 = warp to ending
```
