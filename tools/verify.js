#!/usr/bin/env node
// Structural verification for the Gish Reloaded reanimation.
// Run from repo root: node tools/verify.js
// Exercises every layer headlessly: embedded data integrity, image-pack
// decoding, level parsing, font/string tables, physics soak across all 88
// levels with fuzzed input, shell screen coverage, and progression paths.

const fs = require('fs');
const path = require('path');
const vm = require('vm');
const zlib = require('zlib');

const ROOT = path.join(__dirname, '..');
const E = p => fs.readFileSync(path.join(ROOT, 'engine', p), 'utf8');

let pass = 0, fail = 0;
const ok = (cond, name, detail) => {
  if (cond) { pass++; console.log('  PASS', name); }
  else { fail++; console.log('  FAIL', name, detail || ''); }
};

// minimal canvas 2D context stub that records call counts
function ctxStub() {
  const calls = { total: 0 };
  const fn = name => (...a) => { calls.total++; calls[name] = (calls[name] || 0) + 1; };
  const c = { calls };
  for (const m of ['fillRect', 'drawImage', 'beginPath', 'moveTo', 'lineTo', 'closePath',
    'fill', 'stroke', 'arc', 'save', 'restore', 'translate', 'rotate', 'scale', 'clip',
    'rect', 'fillArc', 'strokeRect', 'fillText']) c[m] = fn(m);
  return c;
}

function mkctx(imageStub) {
  const images = {};
  if (imageStub) {
    // fake images with plausible sizes so font init and draw paths run
    const sizes = { 8: [238, 200], 7: [15, 7], 237: [238, 200], 230: [261, 72] };
    for (let i = 0; i < 1100; i++) images[i] = { width: (sizes[i] || [32, 32])[0], height: (sizes[i] || [32, 32])[1] };
    images.fg6 = { width: 32, height: 32 }; images.fg7 = { width: 32, height: 32 };
  }
  const ctx = {
    console,
    Assets: {
      bytesOf: p => { try { return new Uint8Array(fs.readFileSync(path.join(ROOT, p.replace(/^\//, '')))); } catch (e) { return null; } },
      images, play: () => { }, music: () => { }, sfxOn: false, resumeAudio: () => { },
    },
    View: { w: 360, h: 640 },
    window: { addEventListener: () => { } },
    localStorage: (() => { const m = {}; return { getItem: k => m[k] ?? null, setItem: (k, v) => { m[k] = v; }, removeItem: k => { delete m[k]; } }; })(),
    requestAnimationFrame: () => { }, document: { getElementById: () => null },
    Math, JSON, Set, Map, Int8Array, Int16Array, Int32Array, Uint8Array, DataView, TextDecoder,
    isFinite, Infinity, NaN, Object, Array, String, Number, Promise, Blob: class { }, URL: { createObjectURL: () => '', revokeObjectURL: () => { } }, Image: class { },
  };
  vm.createContext(ctx);
  // assets.js is browser-only (GAME_DATA base64, WebAudio); the stub Assets above
  // stands in for it here, and its unpack logic is verified in section 2.
  for (const f of ['font.js', 'level.js', 'physics.js', 'entities.js', 'game.js', 'shell.js']) {
    vm.runInContext(E(f), ctx, { filename: f });
  }
  return ctx;
}

// ---------- 1. built Index.html integrity ----------
console.log('[1] Index.html embedded data integrity');
{
  const html = fs.readFileSync(path.join(ROOT, 'Index.html'), 'utf8');
  const m = html.match(/const GAME_DATA=(\{.*?\});/s);
  ok(!!m, 'GAME_DATA present');
  const data = JSON.parse(m[1]);
  let mismatches = 0, missing = 0;
  for (const [rel, b64] of Object.entries(data)) {
    const p = path.join(ROOT, rel);
    if (!fs.existsSync(p)) { missing++; continue; }
    if (Buffer.compare(Buffer.from(b64, 'base64'), fs.readFileSync(p)) !== 0) mismatches++;
  }
  ok(missing === 0, 'all embedded files exist in repo', missing + ' missing');
  ok(mismatches === 0, 'embedded bytes identical to repo files', mismatches + ' differ');
  const lvls = Object.keys(data).filter(k => k.endsWith('.lvl'));
  ok(lvls.length === 88, 'all 88 levels embedded', lvls.length);
  const sounds = Object.keys(data).filter(k => k.startsWith('sound/'));
  ok(sounds.length === 13, 'all 13 sound files embedded', sounds.length);
  ok(html.includes('/*__FAVICON__*/') === false && html.includes('data:image/png;base64,'), 'favicon substituted');
}

// ---------- 2. image pack decoding ----------
console.log('[2] image pack decoding');
{
  const ctx = mkctx(false);
  const res = vm.runInContext(`
    (() => {
      const src = ${JSON.stringify(E('assets.js'))};
      return null;
    })()`, ctx);
  // decode via the engine's own unpacker exposed indirectly: re-run core logic here
  const src = E('assets.js');
  const un = src.match(/function unpackImg[\s\S]*?\n  \}/)[0];
  const rm = src.match(/function readMap[\s\S]*?\n  \}/)[0];
  const FXstub = {};
  const unpackImg = eval('(' + un.replace('function unpackImg', 'function') + ')');
  const readMap = eval('(' + rm.replace('function readMap', 'function') + ')');
  let total = 0, badPng = 0;
  for (const [img, map, off] of [['images.img', 'images.map', 0], ['images2.img', 'images2.map', 256]]) {
    const entries = unpackImg(new Uint8Array(fs.readFileSync(path.join(ROOT, img))));
    const ids = readMap(new Uint8Array(fs.readFileSync(path.join(ROOT, map))));
    ok(entries.length === ids.length, img + ' entry count matches map', entries.length + ' vs ' + ids.length);
    for (const png of entries) {
      total++;
      // validate PNG signature + IDAT zlib stream decompresses
      const buf = Buffer.from(png);
      if (buf.readUInt32BE(0) !== 0x89504e47) { badPng++; continue; }
      const idat = buf.indexOf('IDAT');
      const len = buf.readUInt32BE(idat - 4);
      try { zlib.inflateSync(buf.subarray(idat + 4, idat + 4 + len)); } catch (e) { badPng++; }
    }
  }
  ok(total === 366, '366 sprites decoded', total);
  ok(badPng === 0, 'every sprite is a valid PNG with intact zlib stream', badPng + ' bad');
}

// ---------- 3. level parsing ----------
console.log('[3] level parsing');
{
  const ctx = mkctx(false);
  const res = vm.runInContext(`
    (() => {
      const out = { okCnt: 0, bad: [], stats: { ent: 0, blocks: 0, plats: 0, ropes: 0, hints: 0, amber: 0 } };
      const names = Shell.LEVELS;
      for (const name of names) {
        const l = Level.load(name);
        if (!l) { out.bad.push(name + ':load'); continue; }
        const raw = Assets.bytesOf('levels/' + name + '.lvl');
        if (l.bytesConsumed !== raw.length) { out.bad.push(name + ':short'); continue; }
        out.okCnt++;
        out.stats.ent += l.entities.length; out.stats.blocks += l.blocks.length;
        out.stats.plats += l.platforms.length; out.stats.ropes += l.ropes.length;
        out.stats.hints += l.hints.length; out.stats.amber += l.amberTotal;
      }
      return out;
    })()`, ctx);
  ok(res.okCnt === 88, 'all 88 campaign/coop/dm/race/playground levels parse to EOF', JSON.stringify(res.bad));
  ok(res.stats.amber === 40 + (res.stats.amber - 40) && res.stats.amber >= 40, 'amber collectibles present (' + res.stats.amber + ' across all maps)');
  console.log('    totals:', JSON.stringify(res.stats));
}

// ---------- 4. fonts & string tables ----------
console.log('[4] fonts & string tables');
{
  const ctx = mkctx(true);
  const res = vm.runInContext(`
    (() => {
      Font.init();
      Font.loadStrings();
      const out = {};
      out.strings = Font.str(30);
      out.count = 0; while (Font.str(out.count) !== '' || out.count < 5) out.count++;
      out.dlg9 = Font.levelText(9).slice(0, 20);
      out.mainMenu = Font.str(30);
      out.campaign = Font.str(27);
      // measure/wrap sanity
      const enc = Font.encode(0, 'quit');
      out.quitW = Font.measure(0, enc);
      out.wrapped = Font.wrap(3, Font.encode(3, Font.levelText(9)), 290).length;
      // every glyph within atlas bounds
      out.oob = 0;
      for (const f of Font.FONTS) for (let i = 0; i < f.widths.length; i++)
        if (f.gx[i] + f.widths[i] > f.atlas.width + 0 || f.gy[i] + f.lineH > f.atlas.height) out.oob++;
      return out;
    })()`, ctx);
  ok(res.mainMenu === 'main menu', 'string table: id 30 = "main menu"', res.mainMenu);
  ok(res.campaign === 'campaign', 'string table: id 27 = "campaign"', res.campaign);
  ok(res.dlg9.startsWith('Our story begins'), 'dialogue table: tl 9 is the intro narration', res.dlg9);
  ok(res.quitW === 60, 'font metrics: width("quit") in font 0 = 60 px', res.quitW);
  ok(res.wrapped >= 4 && res.wrapped <= 10, 'word wrap produces sane line count', res.wrapped);
  ok(res.oob === 0, 'all glyphs inside atlas bounds', res.oob + ' out of bounds');
}

// ---------- 5. physics soak: all 88 levels, fuzzed input, invariants ----------
console.log('[5] physics soak (88 levels x 250 frames, fuzzed input)');
{
  let crashed = [], nanLevels = [], leakLevels = [], escaped = [];
  for (let id = 0; id < 88; id++) {
    const ctx = mkctx(true);
    try {
      const sub = id >= 68 && id <= 77 ? 4 : id >= 78 ? 5 : id >= 41 && id <= 67 ? 2 : id >= 36 ? 1 : 0;
      const res = vm.runInContext(`
        (() => {
          Font.init(); Font.loadStrings(); Shell.loadAll();
          Game.start(${id}, ${sub >= 2 ? 2 : 1}, ${sub});
          const G = Game.G;
          const pl = G.players[0];
          let seed = ${id} * 2654435761 % 0x7fffffff;
          const rnd = () => (seed = (seed * 1103515245 + 12345) & 0x7fffffff) / 0x7fffffff;
          let maxParticles = 0;
          for (let f = 0; f < 250; f++) {
            pl.keys.right = rnd() < 0.6; pl.keys.left = rnd() < 0.15;
            pl.keys.up = rnd() < 0.25; pl.keys.down = rnd() < 0.15;
            if (rnd() < 0.1) pl.keys.attack = true;
            if (f === 50) pl.setAbility(2);
            if (f === 120) pl.setAbility(1);
            if (f === 170) pl.setAbility(0);
            if (G.state === 6 && G.dialog) { G.dialog.frames = 10; Game.key(-5, true); }
            if (G.state === 5 || G.state === 4) break;
            Game.tick();
            maxParticles = Math.max(maxParticles, G.world.particles.length);
          }
          // invariants
          let nan = 0, oob = 0;
          const W = G.world;
          for (const b of W.bodies) for (const p of b.pts) {
            if (!isFinite(p.x) || !isFinite(p.y)) nan++;
            if (p.x < -(64 << 10) || p.x > ((W.w + 2) << 15) || p.y < -(640 << 10) || p.y > ((W.h + 2) << 15)) oob++;
          }
          for (const p of W.particles) if (!isFinite(p.x) || !isFinite(p.y)) nan++;
          return { nan, oob, maxParticles, state: G.state, dead: pl.dead, hp: pl.health };
        })()`, ctx);
      if (res.nan) nanLevels.push(id);
      if (res.oob) escaped.push(id + ':' + res.oob);
      if (res.maxParticles > 400) leakLevels.push(id + ':' + res.maxParticles);
    } catch (e) { crashed.push(id + ':' + e.message.slice(0, 60)); }
  }
  ok(crashed.length === 0, 'no exceptions in any level', JSON.stringify(crashed));
  ok(nanLevels.length === 0, 'no NaN/Infinity positions after 250 fuzzed frames', JSON.stringify(nanLevels));
  ok(escaped.length === 0, 'no physics points escaped the world bounds', JSON.stringify(escaped));
  ok(leakLevels.length === 0, 'particle counts bounded (<400)', JSON.stringify(leakLevels));
}

// ---------- 6. shell screen coverage ----------
console.log('[6] shell screen machine + draw coverage');
{
  const ctx = mkctx(true);
  const res = vm.runInContext(`
    (() => {
      Font.init(); Font.loadStrings(); Shell.loadAll();
      const stub = ${ctxStub.toString()};
      const screens = [0, 5, 17, 3, 2, 12, 13, 14, 16, 23, 49, 4, 20, 21, 22, 44, 45, 46, 47, 26, 28, 30, 31, 40, 50];
      const bad = [];
      for (const s of screens) {
        try {
          Shell.open(s);
          const c = stub();
          Shell.draw(c);
          if (c.calls.total === 0) bad.push(s + ':nodraw');
          // poke navigation keys on each screen
          Shell.key(-2, 0); Shell.key(-1, 0);
          Shell.draw(stub());
        } catch (e) { bad.push(s + ':' + e.message.slice(0, 50)); }
      }
      // cheat unlock then rebuild the unlock-gated lists
      Shell.open(5); Shell.cheatUnlockAll();
      const counts = {};
      for (const [scr, key] of [[4, 'choose'], [20, 'coop'], [21, 'dm'], [22, 'race'], [44, 'playgr']]) {
        Shell.open(scr);
        counts[key] = (Shell.S.list || []).length;
      }
      return { bad, counts };
    })()`, ctx);
  ok(res.bad.length === 0, 'all 25 shell screens open, draw and take input', JSON.stringify(res.bad));
  const c = res.counts;
  ok(c.choose === 35, 'unlock-all: 35 campaign maps replayable', c.choose);
  ok(c.coop === 27, 'unlock-all: 27 coop maps', c.coop);
  ok(c.dm === 10 && c.race === 10, 'unlock-all: 10 dm + 10 race maps', c.dm + '/' + c.race);
  ok(c.playgr === 5, 'unlock-all: 5 playgrounds', c.playgr);
}

// ---------- 7. progression paths ----------
console.log('[7] progression: completion -> save -> next level');
{
  const ctx = mkctx(true);
  const res = vm.runInContext(`
    (() => {
      Font.init(); Font.loadStrings(); Shell.loadAll();
      const out = {};
      // complete intro -> should save next=1 and load level 1
      Game.start(0, 1, 0);
      Game.G.state = 5; Game.G.resultsT = 10;
      Game.key(-5, true);
      out.afterIntro = { lvl: Game.G.levelId, save: JSON.parse(localStorage.getItem('gigosave')) };
      // world 1 -> world 2 boundary: complete level 15
      Game.start(15, 1, 0);
      Game.G.state = 5; Game.G.resultsT = 10; Game.key(-5, true);
      out.afterW1 = { lvl: Game.G.levelId, sp: Shell.S.achi.sp };
      // finish the campaign at level 35 -> name entry (highscore) or try-harder
      Game.start(35, 1, 0);
      Game.G.state = 5; Game.G.resultsT = 10; Game.key(-5, true);
      out.afterEnd = { screen: Shell.S.screen, saveGone: localStorage.getItem('gigosave') === null, active: Game.active };
      // single-map replay returns to chooser
      Shell.S.achi.sp = 5;
      Game.start(2, 1, 1);
      Game.G.state = 5; Game.G.resultsT = 10; Game.key(-5, true);
      out.afterReplay = { screen: Shell.S.screen, active: Game.active };
      return out;
    })()`, ctx);
  ok(res.afterIntro.lvl === 1 && res.afterIntro.save.next === 1, 'intro completion saves and loads 1-1',
    JSON.stringify(res.afterIntro));
  ok(res.afterW1.lvl === 16 && res.afterW1.sp >= 15, 'world 1 -> world 2 crossing (level 15 -> 16)',
    JSON.stringify(res.afterW1));
  ok(res.afterEnd.saveGone && !res.afterEnd.active && (res.afterEnd.screen === 26 || res.afterEnd.screen === 31),
    'campaign end: save cleared, highscore/name flow opens', JSON.stringify(res.afterEnd));
  ok(res.afterReplay.screen === 4 && !res.afterReplay.active, 'single-map replay returns to choose-map',
    JSON.stringify(res.afterReplay));
}

console.log('\\n=== ' + pass + ' passed, ' + fail + ' failed ===');
process.exit(fail ? 1 : 0);
