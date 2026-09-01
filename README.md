# Gish Reloaded — reanimated

**Gish Reloaded** (a.k.a. *Gish 2 Mobile*) was a J2ME/MIDP-2.0 physics platformer by
**HardWire** (published via HandyGames / Pixalon Studios, ~2010) starring Gish, the
ball of tar. The platform it lived on — Java feature phones — is gone, and the game
went with it. This repository brings it back from the dead as a modern HTML5 game.

## Play

**In your browser: <https://stevebarrettsrha-ops.github.io/Gish-The-Game/>**

Or locally: open **`index.html`** in any browser. That's it — the whole game (engine + every
original data file, base64-embedded) lives in that single file. No server, no
install, no plugins.

## What this is

- The repository root mirrors the original game JAR exactly: packed image
  archives (`images.img` / `images2.img` + `.map` index files), all **88 levels**
  (`levels/` — campaign worlds 1–3, intro, playground, and the coop / deathmatch /
  race multiplayer sets), bitmap fonts (`t_pointer.en`, `tl_pointer.en`), and all
  original sounds and music (`sound/`).
- `engine/` is a from-scratch JavaScript reimplementation of the game engine,
  written against the decompiled MIDlet. It consumes the **original binary game
  files** at runtime — the same bytes the phone game shipped with: the `.img`
  packs are re-assembled into PNGs with the exact algorithm the MIDlet used, the
  `.lvl` binaries are parsed natively, the bitmap fonts render from the original
  glyph atlases and pointer tables.
- `tools/` holds the build & extraction tooling; `reference/` documents the
  reverse-engineered file formats and engine internals.

## Build

`index.html` is generated:

```
python3 tools/build.py
```

## Controls

### Desktop

| Action | Keys |
|---|---|
| Move / roll | Arrow keys or WASD |
| Jump | Up / W / Space |
| Heavy (slam down) | Down / S |
| Sticky surface | K or Left Shift |
| Slick surface | L or Left Ctrl |
| Attack (poke) | Enter |
| Pause | Escape |
| Menu select / back | Enter / Escape |

You can also steer with the mouse: press and hold anywhere relative to Gish to
roll that way, and click on his body to make him angry.

### Touch

Exactly as the phone original played — **hold** a finger in the direction you
want to go, relative to Gish's body:

| Action | Touch |
|---|---|
| Move / roll | Hold left or right of Gish |
| Jump | Hold above him |
| Heavy | Hold below him |
| Attack | Poke his body |
| Change surface | Bottom-left button (normal → sticky → slick) |
| Pause | Bottom-right button |
| Menus | Tap an item to highlight it, tap again to choose; soft buttons bottom-left (OK) and bottom-right (back) |

Steering is multi-touch aware: one finger holds a direction while another works
the surface or pause button, so you can switch surface mid-roll. Movement stops
the moment the steering finger lifts, and is released if the app loses focus.

Verify all of this on your own machine with:

```
node tools/verify-controls.js
```

## Provenance

Reconstructed for preservation from `Gish_Reloaded_360.jar` (MIDlet
`com.hardwire.blob.Main`, MIDP-2.0/CLDC-1.0). All game content © its original
rights holders (HardWire / Chronic Logic — Gish was created by Alex Austin,
Edmund McMillen & Josiah Pisciotta). Engine reimplementation written for this
repository.
