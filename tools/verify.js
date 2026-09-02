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
  for (const f of ['font.js', 'level.js', 'physics.js', 'entities.js', 'bosses.js', 'hints.js', 'game.js', 'shell.js']) {
    vm.runInContext(E(f), ctx, { filename: f });
  }
  return ctx;
}

// ---------- 1. built index.html integrity ----------
console.log('[1] index.html embedded data integrity');
{
  const html = fs.readFileSync(path.join(ROOT, 'index.html'), 'utf8');
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

// ---------- 8. boss encounters ----------
console.log('[8] boss scripts (14 / 20 / 28 / 32 / 34)');
{
  // level 14: the rival must actually run the scripted route, not idle
  {
    const ctx = mkctx(true);
    const r = vm.runInContext(`
      (() => {
        Font.init(); Font.loadStrings(); Shell.loadAll();
        Game.start(14, 1, 0);
        const G = Game.G;
        const rival = G.players.find(p => p.ai);
        const start = rival ? rival.body.centroid().x >> 15 : -1;
        G.scene = 1;                       // race started
        const seen = new Set();
        for (let f = 0; f < 400; f++) {
          if (G.state === 6 && G.dialog) { G.dialog.frames = 10; Game.key(-5, true); }
          if (G.state === 5) break;
          Game.tick();
          seen.add(G.scene);
        }
        return { has: !!rival, start, end: rival.body.centroid().x >> 15,
                 scenes: [...seen].sort((a,b)=>a-b), ai: !!(rival && rival.ai) };
      })()`, ctx);
    ok(r.has && r.ai, 'level 14: rival blob spawned with waypoint AI');
    ok(r.end > r.start, 'level 14: rival advances along the route', r.start + ' -> ' + r.end);
    ok(r.scenes.length > 1, 'level 14: waypoint scenes advance', JSON.stringify(r.scenes));
  }
  // level 20: darkness grows near lava; boss burns and opens the exit
  {
    const ctx = mkctx(true);
    const r = vm.runInContext(`
      (() => {
        Font.init(); Font.loadStrings(); Shell.loadAll();
        Game.start(20, 1, 0);
        const G = Game.G;
        const r0 = G.darkR;
        const boss = G.monsters.find(m => m.kind === 4);
        // put the player on a lava-glow tile to trigger the growth + scene 3
        let lx = -1, ly = -1;
        for (let x = 0; x < G.world.w && lx < 0; x++)
          for (let y = 0; y < G.world.h; y++)
            if (G.lvl.tiles[0][x][y] === 30) { lx = x; ly = y; break; }
        const pl = G.players[0];
        // pretend Gish is standing in the glow: drive the script input directly
        G.scene = 1;
        const lavaProbe = () => { G.darkR += 0; };
        const run = n => { for (let f = 0; f < n; f++) {
          if (G.state === 6 && G.dialog) { G.dialog.frames = 10; Game.key(-5, true); }
          if (G.state === 0) Game.tick();
        } };
        if (lx >= 0) {   // move Gish onto the glow tile without burying him
          const c = pl.body.centroid();
          const dx = (lx << 15) + 16384 - c.x, dy = (ly << 15) + 16384 - c.y;
          for (const p of pl.body.pts) { p.x += dx; p.y += dy; p.px += dx; p.py += dy; }
        }
        run(4);
        const grew = G.darkR > r0;
        // now kill the boss on lava and confirm the exit gate opens
        if (boss) {   // move him onto the glow (reset verlet prev, or he slingshots)
          boss.p.x = (lx << 15) + 16384; boss.p.y = (ly << 15) + 16384;
          boss.p.px = boss.p.x; boss.p.py = boss.p.y;
        }
        if (G.scene < 3) G.scene = 3;
        run(6);
        return { r0, grew, scene: G.scene, lava: lx >= 0,
                 bossDead: boss ? !boss.alive() : null, exit: Bosses.exitOverride(G) };
      })()`, ctx);
    ok(r.lava, 'level 20: lava-glow tiles present in the map');
    ok(r.r0 === 46080, 'level 20: darkness window starts at 45 px', r.r0);
    ok(r.grew, 'level 20: window expands near lava glow');
    ok(r.bossDead === true, 'level 20: boss burns on the lava');
    ok(r.exit === true, 'level 20: exit gate opens at scene 6 after the kill', 'scene ' + r.scene);
  }
  // level 28: score 180 severs tentacles, then the god is killable + tiles clear
  {
    const ctx = mkctx(true);
    const r = vm.runInContext(`
      (() => {
        Font.init(); Font.loadStrings(); Shell.loadAll();
        Game.start(28, 1, 0);
        const G = Game.G;
        const boss = G.monsters.find(m => m.kind === 5);
        const pillars = G.monsters.filter(m => m.kind === 6).length;
        G.scene = 1;
        G.score[0] = 180;                    // tentacles severed
        // park the camera on the boss so it counts as in view
        if (boss) { G.camX = (boss.p.x >> 10) - 100; G.camY = (boss.p.y >> 10) - 100; }
        const before = boss ? [G.lvl.tiles[1][boss.p.x >> 15][(boss.p.y >> 15) + 1]] : [];
        for (let f = 0; f < 8; f++) {
          if (G.state === 6 && G.dialog) { G.dialog.frames = 10; Game.key(-5, true); }
          Game.tick();
          if (boss) { G.camX = (boss.p.x >> 10) - 100; G.camY = (boss.p.y >> 10) - 100; }
        }
        const after = boss ? [G.lvl.tiles[1][boss.p.x >> 15][(boss.p.y >> 15) + 1]] : [];
        return { boss: !!boss, pillars, dead: boss ? !boss.alive() : null,
                 scene: G.scene, before, after, exit: Bosses.exitOverride(G) };
      })()`, ctx);
    ok(r.boss, 'level 28: tower boss present');
    ok(r.dead === true, 'level 28: boss dies once the tentacles are severed (score 180)');
    ok(r.after[0] === -1, 'level 28: death clears the tiles beneath the boss',
      JSON.stringify(r.before) + ' -> ' + JSON.stringify(r.after));
    ok(r.exit === true, 'level 28: exit gate opens', 'scene ' + r.scene);
  }
  // level 32: lever puzzle kills the maw, writes tile 69, then boosts the player
  {
    const ctx = mkctx(true);
    const r = vm.runInContext(`
      (() => {
        Font.init(); Font.loadStrings(); Shell.loadAll();
        Game.start(32, 1, 0);
        const G = Game.G;
        const boss = G.monsters.find(m => m.kind === 5);
        const pl = G.players[0];
        const hold = () => {   // keep Gish hovering just above the mouth
          const c = pl.body.centroid();
          const dx = boss.p.x - c.x, dy = boss.p.y - 8192 - c.y;
          for (const p of pl.body.pts) { p.x += dx; p.y += dy; p.px = p.x; p.py = p.y; }
        };
        const run = n => { for (let f = 0; f < n; f++) {
          if (G.state === 6 && G.dialog) { G.dialog.frames = 10; Game.key(-5, true); }
          hold();
          if (G.state === 0) Game.tick();
        } };
        run(2);                                      // boss throws up the nest
        const guards = G.pillars.length;
        // only one tentacle down: the boss must survive
        if (G.pillars[0]) G.pillars[0].state = 2;
        run(4);
        const survivedOneLever = boss ? boss.alive() : null;
        // second tentacle down
        if (G.pillars[1]) G.pillars[1].state = 2;
        run(6);
        const cx = boss ? boss.p.x >> 15 : 0, cy = boss ? boss.p.y >> 15 : 0;
        return { boss: !!boss, levers: guards, survivedOneLever,
                 dead: boss ? !boss.alive() : null,
                 written: cy + 2 < G.world.h ? G.lvl.tiles[1][cx][cy + 2] : null,
                 exit: Bosses.exitOverride(G) };
      })()`, ctx);
    ok(r.boss && r.levers >= 2, 'level 32: maw boss + tentacle nest spawned', 'tentacles ' + r.levers);
    ok(r.survivedOneLever === true, 'level 32: one tentacle down does NOT open the maw');
    ok(r.dead === true, 'level 32: both tentacles down + Gish above the mouth kills it');
    ok(r.written === 69, 'level 32: death writes the tile-69 column', 'tile ' + r.written);
    ok(r.exit === true, 'level 32: exit gate opens');
  }
  // level 34: crusher walls arm at scene 1, Hera is killable, ending fires
  {
    const ctx = mkctx(true);
    const r = vm.runInContext(`
      (() => {
        Font.init(); Font.loadStrings(); Shell.loadAll();
        Game.start(34, 1, 0);
        const G = Game.G;
        const rival = G.players.find(p => p.ai);
        for (let f = 0; f < 3; f++) Game.tick();
        const wallsOffAtScene0 = G.platforms.length >= 2 &&
          !G.platforms[0].active && !G.platforms[1].active;
        G.scene = 1;                                   // dialog 128 dismissed
        for (let f = 0; f < 3; f++) Game.tick();
        const wallsOn = G.platforms.length >= 2 && G.platforms[0].active;
        // rival takes spike damage like a real blob
        const hp0 = rival ? rival.health : 0;
        if (rival) rival.damage(102400);
        let dlg = null;
        for (let f = 0; f < 130; f++) {
          Game.tick();
          if (G.state === 6 && G.dialog) { dlg = G.dialog.id; break; }
        }
        return { rival: !!rival, wallsOffAtScene0, wallsOn, hp0, dead: rival && rival.dead,
                 dlg, aiKind: rival && rival.ai === Bosses.duelAI };
      })()`, ctx);
    ok(r.rival && r.aiKind, 'level 34: Hera spawned with the duel AI');
    ok(r.wallsOffAtScene0, 'level 34: crusher walls held off at scene 0');
    ok(r.wallsOn, 'level 34: walls close in once the fight starts');
    ok(r.dead, 'level 34: Hera can be killed');
    ok(r.dlg === 129, 'level 34: her death triggers the ending dialog 129', 'got ' + r.dlg);
  }
}

// ---------- 9. idle hints ----------
console.log('[9] idle hints: 35 s of silence -> demonstration sheet');
{
  const ctx = mkctx(true);
  const res = vm.runInContext(`
    (() => {
      Font.init(); Font.loadStrings(); Shell.loadAll();
      Game.start(2, 1, 0);
      const G = Game.G, H = Hints.state;
      const stub = ${ctxStub.toString()};
      // advance ticks with no input; story dialogs are cleared directly so
      // dismissing them does not count as the player doing something
      const run = n => { for (let i = 0; i < n; i++) { if (G.state === 6) { G.dialog = null; G.state = 0; } Game.tick(); } };
      const out = {};
      run(499); out.before = H.visible;                  // 34.93 s: nothing
      run(1); out.at35 = H.visible; out.page0 = H.page;  // 35.0 s: sheet up
      const c = stub(); Game.draw(c, 0.5); out.drawCalls = c.calls.total; out.drawText = c.calls.drawImage;
      run(Hints.PAGE_TICKS); out.page1 = H.page;         // moves on to the next demonstration
      // every page draws for every input kind without throwing
      out.drawErr = null;
      for (const kind of ['key', 'mouse', 'touch']) for (let p = 0; p < Hints.PAGES.length; p++) {
        H.kind = kind; H.page = p;
        for (const a of [0, 0.37, 0.9]) { H.t = Math.floor(a * 40); try { Game.draw(stub(), a); } catch (e) { out.drawErr = kind + '/' + p + ': ' + e.message; } }
      }
      H.kind = null; H.page = 1; H.t = 0;                // put the tour back where it was
      // captions fit three lines at the narrowest phone width
      out.longest = 0;
      for (const kind of Object.keys(Hints.TEXT)) for (const p of Hints.PAGES)
        out.longest = Math.max(out.longest, Font.wrap(3, Font.encode(3, Hints.TEXT[kind][p]), 320 - 28).length);
      // pin Gish where he sits so 42 s of "holding right" cannot roll him to
      // his death (a restart would reset the tour and hide what we test)
      for (const p of G.players[0].body.pts) p.mass = PINNED;
      Game.key(-4, true);                                // press right: sheet down, timer reset
      out.afterKey = H.visible; out.idleAfterKey = H.idleMs;
      run(600); out.whileHeld = H.visible;               // key held = playing, never idle
      Game.key(-4, false);
      run(500); out.again = H.visible; out.pageAgain = H.page;   // 35 s after release: back, next page
      Game.touchDown(300, 300); out.afterTouch = H.visible;      // a tap dismisses it too
      // no idle counting behind a dialog or on the results sheet
      G.state = 6; G.dialog = { id: 0, lines: [], portrait: 0, frames: 0, slide: 0 };
      for (let i = 0; i < 600; i++) Game.tick();
      out.inDialog = H.visible; out.idleInDialog = H.idleMs;
      G.state = 0; G.dialog = null;
      // the "hints" setting switches it off
      Shell.S.settings.hints = false; run(600); out.disabled = H.visible; Shell.S.settings.hints = true;
      // a new level starts the clock again
      Game.start(3, 1, 0); out.afterStart = H.idleMs === 0 && !H.visible;
      return out;
    })()`, ctx);
  ok(res.before === false && res.at35 === true, 'sheet appears exactly after 35 s without input', JSON.stringify([res.before, res.at35]));
  ok(res.page0 === 0, 'a player who never moved is shown "move" first', res.page0);
  ok(res.drawCalls > 0 && res.drawText > 0, 'sheet renders (panel, demo scene, caption)', res.drawCalls);
  ok(res.page1 === 1, 'pages advance while the sheet stays up', res.page1);
  ok(res.drawErr === null, 'every page draws for keyboard, mouse and touch', res.drawErr);
  ok(res.longest <= 3, 'captions wrap to at most 3 lines at 320 px', res.longest);
  ok(res.afterKey === false && res.idleAfterKey === 0, 'a key press dismisses it and restarts the timer');
  ok(res.whileHeld === false, 'never shown while a direction is held');
  ok(res.again === true && res.pageAgain === 2, 'returns after another 35 s, continuing the tour', JSON.stringify([res.again, res.pageAgain]));
  ok(res.afterTouch === false, 'a tap dismisses it');
  ok(res.inDialog === false && res.idleInDialog === 0, 'no idle counting behind a story dialog');
  ok(res.disabled === false, 'respects hints = off in settings');
  ok(res.afterStart === true, 'level start resets the idle clock');
}

// ---------- 10. render interpolation ----------
console.log('[10] render interpolation between 70 ms ticks');
{
  const ctx = mkctx(true);
  const res = vm.runInContext(`
    (() => {
      Font.init(); Font.loadStrings(); Shell.loadAll();
      Game.start(2, 1, 0);
      const G = Game.G, pl = G.players[0];
      pl.keys.right = true;
      Game.tick(); Game.tick();          // Gish is falling at the start of 1-2
      let missing = 0, moved = 0;
      for (const b of G.world.bodies) for (const p of b.pts) { if (p.rx === undefined) missing++; if (p.x !== p.rx || p.y !== p.ry) moved++; }
      for (const p of G.world.particles) if (p.rx === undefined) missing++;
      const stub = ${ctxStub.toString()};
      const pos = [];
      for (const a of [0, 0.5, 1]) { const c = stub(); Game.draw(c, a); pos.push({ y: G.drawPy + G.drawCamY, x: G.drawPx + G.drawCamX, calls: c.calls.total }); }
      const c1 = pl.body.centroid();
      let prevY = 0, n = 0;
      for (const p of pl.body.pts) { prevY += p.ry; n++; }
      prevY = prevY / n / 1024;
      const legacy = stub(); Game.draw(legacy);   // no alpha given: current state, as before
      return { missing, moved, pos, cur: c1.y / 1024, prevY, legacyOk: legacy.calls.total > 0, legacyY: G.drawPy + G.drawCamY };
    })()`, ctx);
  ok(res.missing === 0, 'every physics point carries a start-of-tick snapshot', res.missing);
  ok(res.moved > 0, 'points moved during the tick (there is motion to smooth)', res.moved);
  ok(res.pos[0].y < res.pos[1].y && res.pos[1].y < res.pos[2].y, 'alpha 0 -> 0.5 -> 1 sweeps Gish from the previous to the current tick position',
    JSON.stringify(res.pos.map(p => +p.y.toFixed(2))));
  ok(Math.abs(res.pos[0].y - res.prevY) < 0.01 && Math.abs(res.pos[2].y - res.cur) < 1, 'alpha 0 = last tick, alpha 1 = current tick',
    JSON.stringify([res.pos[0].y, res.prevY, res.pos[2].y, res.cur]));
  ok(res.legacyOk && Math.abs(res.legacyY - res.cur) < 1, 'draw() without alpha still renders the current state', res.legacyY);
  // every level renders mid-tick: ropes (point, edge and fixed anchors),
  // platforms, monsters with a second body, boxes, darkness, effects
  const bad = [];
  for (let id = 0; id < 88; id++) {
    const c2 = mkctx(true);
    try {
      const sub = id >= 68 && id <= 77 ? 4 : id >= 78 ? 5 : id >= 41 && id <= 67 ? 2 : id >= 36 ? 1 : 0;
      vm.runInContext(`
        Font.init(); Font.loadStrings(); Shell.loadAll();
        Game.start(${id}, ${sub >= 2 ? 2 : 1}, ${sub});
        Game.G.players[0].keys.right = true;
        for (let f = 0; f < 6; f++) { if (Game.G.state === 6) { Game.G.dialog = null; Game.G.state = 0; } Game.tick(); }
        const stub = ${ctxStub.toString()};
        for (const a of [0, 0.33, 1]) { const c = stub(); Game.draw(c, a); if (!c.calls.total) throw new Error('nothing drawn'); }`, c2);
    } catch (e) { bad.push(id + ':' + e.message.slice(0, 50)); }
  }
  ok(bad.length === 0, 'all 88 levels draw at alpha 0 / 0.33 / 1 without exceptions', JSON.stringify(bad));
}

console.log('\\n=== ' + pass + ' passed, ' + fail + ' failed ===');
process.exit(fail ? 1 : 0);
