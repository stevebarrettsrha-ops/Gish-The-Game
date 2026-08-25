#!/usr/bin/env python3
"""Build the self-contained Index.html for Gish Reloaded — HTML5 reanimation.

Embeds every original game data file (image packs, levels, fonts, sounds)
as base64 into a single Index.html, so the game runs from a double-click
with no server. The engine consumes the ORIGINAL binary files at runtime —
the same bytes the J2ME midlet shipped with.

Usage: python3 tools/build.py   (run from repo root or tools/)
"""
import base64, json, os, sys

ROOT = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))

# Every original game data file the engine consumes, keyed by its
# JAR-absolute resource path (exactly what Main.a(String) received).
DATA_FILES = [
    'images.img', 'images.map', 'images2.img', 'images2.map',
    't_pointer.en', 'tl_pointer.en',
    'img_gish/dark_corner_alpha.png',
    'img_tiles/fg/6_alpha.png', 'img_tiles/fg/7_alpha.png',
    'icons/icon_64x64.png',
    'sound/CLICK015.wav', 'sound/amber.wav', 'sound/blockbreak.wav',
    'sound/bobattack.wav', 'sound/gishhit.wav', 'sound/necksnap.wav',
    'sound/ropebreak.wav', 'sound/sewer.mp3', 'sound/splash.wav',
    'sound/squish.wav', 'sound/switch.wav', 'sound/tarball.wav',
    'sound/visattack.wav',
]

def collect_levels():
    files = []
    for dirpath, _, names in os.walk(os.path.join(ROOT, 'levels')):
        for n in sorted(names):
            if n.endswith('.lvl'):
                rel = os.path.relpath(os.path.join(dirpath, n), ROOT)
                files.append(rel.replace(os.sep, '/'))
    return sorted(files)

def main():
    data = {}
    for rel in DATA_FILES + collect_levels():
        p = os.path.join(ROOT, rel)
        with open(p, 'rb') as f:
            data[rel] = base64.b64encode(f.read()).decode('ascii')
    payload = json.dumps(data, separators=(',', ':'))

    with open(os.path.join(ROOT, 'engine', 'index.template.html')) as f:
        template = f.read()
    js_parts = []
    for name in ('assets.js', 'font.js', 'level.js', 'physics.js',
                 'entities.js', 'bosses.js', 'game.js', 'shell.js', 'main.js'):
        p = os.path.join(ROOT, 'engine', name)
        if os.path.exists(p):
            with open(p) as f:
                js_parts.append('// ==== engine/' + name + ' ====\n' + f.read())
    html = template.replace('/*__GAME_DATA__*/', 'const GAME_DATA=' + payload + ';')
    html = html.replace('/*__ENGINE__*/', '\n'.join(js_parts))
    html = html.replace('/*__FAVICON__*/',
                        'data:image/png;base64,' + data['icons/icon_64x64.png'])

    out = os.path.join(ROOT, 'Index.html')
    with open(out, 'w') as f:
        f.write(html)
    print('wrote %s (%.1f KB, %d data files)' % (out, len(html) / 1024.0, len(data)))

if __name__ == '__main__':
    main()
