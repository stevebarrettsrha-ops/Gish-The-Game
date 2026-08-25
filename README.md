# Gish Reloaded — reanimated

**Gish Reloaded** (a.k.a. *Gish 2 Mobile*) was a J2ME/MIDP-2.0 physics platformer by
**HardWire** (published via HandyGames / Pixalon Studios, ~2010) starring Gish, the
ball of tar. The platform it lived on — Java feature phones — is gone, and the game
went with it. This repository brings it back from the dead as a modern HTML5 game.

## Play

Open **`Index.html`** in any browser. That's it — the whole game (engine + every
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

`Index.html` is generated:

```
python3 tools/build.py
```

## Controls

| Action | Keys |
|---|---|
| Move | Arrow keys / WASD |
| Jump | Space / W / Up |
| Heavy | Down / S |
| Sticky | Left Shift / K |
| Slick | Left Ctrl / L |
| Menu select / back | Enter / Escape |

Touch controls appear automatically on touch devices, matching the original's
corner-tap layout.

## Provenance

Reconstructed for preservation from `Gish_Reloaded_360.jar` (MIDlet
`com.hardwire.blob.Main`, MIDP-2.0/CLDC-1.0). All game content © its original
rights holders (HardWire / Chronic Logic — Gish was created by Alex Austin,
Edmund McMillen & Josiah Pisciotta). Engine reimplementation written for this
repository.
