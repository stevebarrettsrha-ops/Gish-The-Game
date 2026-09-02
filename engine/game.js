// In-game session — port of classes k (session) + ab (tick/render/HUD).
// Runs the original data: 3-layer tile maps, sprite bases {276,566,407},
// dialog script from tl_pointer.en, physics per spec (70 ms frame, 2 substeps).

const Game = (() => {
  // dialog control tables (ab statics)
  const SPEAKER = [84,84,84,84,84,84,84,84,84,87,80,82,81,80,81,82,84,84,84,84,87,82,82,84,84,84,
    84,87,82,82,80,82,80,84,85,82,84,87,82,87,84,87,82,87,82,84,82,87,82,87,84,85,82,85,82,85,87,82,
    82,84,84,82,83,82,83,82,83,83,82,82,87,80,82,87,86,82,86,82,86,82,87,87,82,87,82,87,82,87,82,86,
    82,86,86,87,82,87,87,82,83,82,83,82,83,84,83,82,82,82,87,82,82,87,82,84,81,82,87,82,82,87,87,82,
    82,83,82,83,82,83,87,87,82,87,82,80,82,80,82,80,82,80,82,80,82,87,87,82,83,84,84,82,83,85,83,85,
    82,83,82,82,0];
  const SPEAKER_NAME = { 85: 76, 86: 90, 80: 77, 82: 78, 81: 79, 83: 80, 84: 81, 87: 83 };
  const CHAIN = new Set([10,11,12,13,16,20,25,27,30,31,34,35,37,38,41,42,43,46,47,48,51,52,55,61,62,
    63,64,65,69,71,72,74,75,76,78,79,81,82,83,84,85,86,87,89,90,92,93,94,96,98,99,100,101,102,107,
    108,111,115,118,120,122,123,124,125,126,127,129,131,133,134,135,136,137,138,139,140,141,144,145,
    146,147,149,150,151,152,153,154]);
  const HINT_MASK = { 0: 2, 1: 4, 2: 8, 3: 16, 4: 32, 5: 64, 6: 256, 7: 1024, 8: 2048,
    17: 2048, 23: 1032, 24: 1032, 26: 1032, 29: 64, 40: 32 };
  const FG_REMAP = { 17: 566 + 0, 18: 566 + 28, 41: 566 + 55, 42: 566 + 62, 43: 566 + 52, 44: 566 + 59, 45: 566 + 50 };
  const BDROP = new Set([1, 2, 11, 13, 19, 28, 29, 31, 34, 37, 38, 41, 42, 45, 46, 49, 51]);
  const CLIMB_HINT = [0,4,8,2,4,12,10,2,8,6,12,14,10,0,14,32,16,128,64,0,0,0,0,40,24,128,64,8,0];
  const THEME_BG = [0x001414, 0x26220D, 0x55A0FF];

  const G = {
    active: false, levelId: -1, mode: 1, submode: 0,
    world: null, lvl: null, players: [], monsters: [], boxes: [], platforms: [],
    state: 0,        // 0 play, 5/4 results, 6 dialog
    frame: 0, timeMs: 0, score: [0, 0], amber: [0, 0], amberTotal: 0,
    camX: 0, camY: 0,
    pcamX: 0, pcamY: 0,          // camera at the start of the current tick
    drawCamX: undefined, drawCamY: undefined, drawPx: undefined, drawPy: undefined,  // last rendered camera / Gish
    dialog: null,    // {id, lines, speaker, shownFrames, slide}
    scene: 0,
    triggersFired: new Set(),
    secretsFound: new Set(),
    banner: null, bannerT: 0,
    effects: [], popups: [], parts: [],
    resultsT: 0,
    totals: { score: 0, time: 0, amber: 0 },
    bossDead: false,
    cheatBuf: [],
    dark: false,
  };

  function start(levelId, mode, submode) {
    G.levelId = levelId; G.mode = mode; G.submode = submode;
    const name = Shell.LEVELS[levelId];
    const lvl = Level.load(name);
    if (!lvl) { Shell.open(0); return; }
    G.lvl = lvl;
    G.world = new World(lvl);
    G.world.tileCallback = onTileContact;
    G.world.bodyCallback = onBodyContact;
    G.players = []; G.monsters = []; G.boxes = []; G.platforms = [];
    G.state = 0; G.frame = 0; G.timeMs = 0;
    G.score = [0, 0]; G.amber = [0, 0];
    G.dialog = null; G.scene = 0; G.bossDead = false;
    G.darkR = 46080; G.heraMourned = false;
    G.triggersFired.clear(); G.secretsFound.clear();
    G.effects = []; G.popups = []; G.parts = [];
    G.dark = levelId === 20;
    G.amberTotal = lvl.amberTotal;
    // strip already-collected goodie coin
    if (Shell.S.achi.goodieFlags[levelId] || G.submode !== 0) {
      for (let x = 0; x < lvl.width; x++)
        for (let y = 0; y < lvl.height; y++)
          if (lvl.tiles[1][x][y] === 70) { lvl.tiles[1][x][y] = -1; G.world.retile(x, y); }
    }
    // entities
    let playerCount = 0;
    const coop = submode === 2 || submode === 3;
    for (const e of lvl.entities) {
      const fx = e.px << 10, fy = e.py << 10;
      if (e.type === 1) {
        if (playerCount === 0) {
          G.players.push(new Player(G.world, fx, fy, 0, 0));
        } else if (coop && playerCount === 1) {
          G.players.push(new Player(G.world, fx, fy, 0, 1));
        } else if ((submode === 4 || submode === 5) && playerCount === 1) {
          const ai = new Player(G.world, fx, fy, 0, 2);
          ai.ai = versusAI;
          G.players.push(ai);
        }
        playerCount++;
      } else if (e.type === 5) {
        const variant = levelId === 14 ? 1 : levelId === 34 ? 3 : 2;
        const ai = new Player(G.world, fx, fy, variant, 2);
        ai.ai = Bosses.aiFor(levelId, submode);
        G.players.push(ai);
      } else {
        const m = new Monster(G.world, fx, fy, e.type - 2);
        m.respawnable = true;
        G.monsters.push(m);
      }
    }
    if (!G.players.length) G.players.push(new Player(G.world, 3 << 15, 3 << 15, 0, 0));
    // tower bosses reserve 10 tentacle/pillar slots they spawn from at runtime
    G.pillars = null; G.pillarCool = 30;
    const tower = G.monsters.find(m => m.kind === 5);
    if (tower) {
      G.pillars = [];
      for (let i = 0; i < 10; i++) {
        const p = new Monster(G.world, tower.p.x, tower.p.y, 6);
        p.state = 2; p.p.flags |= 0x10; p.extend = 0; p.len = 3;
        G.monsters.push(p);
        G.pillars.push(p);
      }
    }
    for (const b of lvl.blocks) G.boxes.push(new Box(G.world, b.x, b.y, b.kind));
    for (const p of lvl.platforms) G.platforms.push(new Platform(G.world, p));
    buildRopes(G.world, lvl, G.boxes);
    G.active = true;
    // camera on player
    const c = G.players[0].body.centroid();
    G.camX = (c.x >> 10) - (View.w >> 1); G.camY = (c.y >> 10) - (View.h >> 1);
    clampCam();
    G.drawCamX = G.drawCamY = G.drawPx = G.drawPy = undefined;
    snapshot();
    Hints.reset();
  }

  // Remember where everything is at the start of a tick so the renderer can
  // interpolate between ticks (see draw()). Pts carry rx/ry, platforms too.
  function snapshot() {
    const W = G.world;
    for (const b of W.bodies) for (const p of b.pts) { p.rx = p.x; p.ry = p.y; }
    for (const p of W.ropePts) { p.rx = p.x; p.ry = p.y; }
    for (const p of W.particles) { p.rx = p.x; p.ry = p.y; }
    for (const p of G.platforms) { p.rx = p.x; p.ry = p.y; }
    G.pcamX = G.camX; G.pcamY = G.camY;
  }

  function restartLevel() { start(G.levelId, G.mode, G.submode); }
  function abort() { G.active = false; }
  function addScore(n) { G.score[0] += n; }

  function respawnBox(box) {
    const i = G.boxes.indexOf(box);
    if (i >= 0) { G.boxes.splice(i, 1); G.boxes.push(new Box(G.world, box.originX, box.originY, box.kind)); }
  }

  // ---- AI ----
  function versusAI(pl, game) {
    const me = pl.body.centroid();
    const other = game.players[0].body.centroid();
    pl.keys.left = other.x < me.x - 4096;
    pl.keys.right = other.x > me.x + 4096;
    pl.keys.up = other.y < me.y - 16384 || FX.rnd(0, 20) === 0;
    if (game.submode === 4 && FX.rnd(0, 30) === 0) pl.keys.attack = true;
  }

  // ---- tile & body contact callbacks ----
  function onTileContact(body, cx, cy) {
    if (cx < 0 || cy < 0 || cx >= G.world.w || cy >= G.world.h) return;
    const id = G.lvl.tiles[1][cx][cy];
    const pl = body.owner instanceof Player ? body.owner : null;
    if (pl) {
      // spikes hurt every blob, rivals included (that is how Hera is beaten)
      if (Level.SPIKES.has(id)) pl.damage(1024);
    }
    if (pl && !pl.ai) {
      // climb assist hints
      if (pl.ability === 2 && KG[id] !== false) {
        const s = G.world.coll[cx][cy];
        if (s >= 0) pl.climb |= CLIMB_HINT[s] || 0;
      }
    }
    // breakable
    if (id === Level.BREAKABLE) {
      const v = body.avgVel();
      if (v.x * v.x + v.y * v.y > 0x1E00000) breakTile(cx, cy);
    }
  }

  function breakTile(cx, cy) {
    G.lvl.tiles[1][cx][cy] = -1;
    G.world.retile(cx, cy);
    Assets.play('blockbreak');
    effect((cx << 15) + 16384, (cy << 15) + 16384, 0);
    for (let i = 0; i < 8; i++) {
      const p = new Particle((cx << 15) + FX.rnd(0, 32768), (cy << 15) + FX.rnd(0, 32768), 2048, 3072, 1);
      p.px = p.x - FX.rnd(-2048, 2048); p.py = p.y - FX.rnd(-1024, 3072);
      p.ttl = 20;
      G.world.particles.push(p);
    }
  }

  function onBodyContact(a, b) {
    const pa = a.owner instanceof Player ? a.owner : null;
    const pb = b.owner instanceof Player ? b.owner : null;
    if (pa && !pa.ai && b.type !== 1) pa.attackTargets.push([b, 3]);
    if (pb && !pb.ai && a.type !== 1) pb.attackTargets.push([a, 3]);
    if (pa && pb) { pa.attackTargets.push([b, 3]); pb.attackTargets.push([a, 3]); }
  }

  // pickups scanned by AABB in player tick
  function scanPickups(pl) {
    const bb = pl.body.bbox();
    const t1 = G.lvl.tiles[1];
    let exit = false;
    for (let cx = Math.max(0, bb.x0 >> 15); cx <= Math.min(G.world.w - 1, bb.x1 >> 15); cx++)
      for (let cy = Math.max(0, bb.y0 >> 15); cy <= Math.min(G.world.h - 1, bb.y1 >> 15); cy++) {
        const id = t1[cx][cy];
        if (id === 13) { exit = true; continue; }
        if (id === 8) {          // health
          pl.health = Math.min(102400, pl.health + 10240);
          t1[cx][cy] = -1; G.world.retile(cx, cy);
          Assets.play('tarball'); effect((cx << 15) + 16384, (cy << 15) + 16384, 5);
        } else if (id === 9) {   // score amber
          G.score[0] += 10;
          t1[cx][cy] = -1; G.world.retile(cx, cy);
          Assets.play('amber'); effect((cx << 15) + 16384, (cy << 15) + 16384, 4);
          popup((cx << 15) + 16384, (cy << 15) + 16384, 10);
        } else if (id === 43) {  // collectible amber (secret)
          G.amber[0]++;
          t1[cx][cy] = -1; G.world.retile(cx, cy);
          Assets.play('amber');
          if (G.submode === 0 && G.levelId >= 1 && G.levelId <= 35) {
            const idx = G.secretsFound.size;
            G.secretsFound.add(cx + ',' + cy);
            const msg = Shell.onSecretFound(G.levelId, idx);
            if (msg && typeof msg === 'string') banner(msg);
          }
        } else if (id === 70) {  // goodie coin
          t1[cx][cy] = -1; G.world.retile(cx, cy);
          Assets.play('amber');
          const msg = Shell.onGoodieFound(G.levelId);
          if (msg) banner(msg);
        }
      }
    pl.exitTouch = exit;
  }

  // ---- dialogs ----
  function openDialog(id) {
    if (id < 0 || G.triggersFired.has('d' + id)) return;
    G.triggersFired.add('d' + id);
    const raw = Font.levelText(id);
    if (!raw) { sideEffects(id); return; }
    if (HINT_MASK[id] && !Shell.S.settings.hints) { sideEffects(id); return; }
    const fid = 3;
    const lines = Font.wrap(fid, Font.encode(fid, raw), View.w - 78);
    const portrait = SPEAKER[id] || 0;
    G.dialog = { id, lines, portrait, frames: 0, slide: 0 };
    G.state = 6;
  }
  function sideEffects(id) {
    if (id === 16 || id === 23) G.scene++;
  }
  function dismissDialog() {
    const f = G.dialog.id;
    G.dialog = null;
    G.state = 0;
    sideEffects(f);
    if (CHAIN.has(f)) { G.triggersFired.delete('d' + (f + 1)); openDialog(f + 1); return; }
    if (f === 67 || f === 104) { restartLevel(); return; }
    if ([66, 77, 91, 109, 128, 130].includes(f)) G.scene++;
    if (f === 142) G.scene = 2;
    if (f === 132) G.scene++;
  }

  function banner(text) { G.banner = text; G.bannerT = 90; }
  function effect(x, y, type) { G.effects.push({ x, y, type, f: 0 }); }
  function popup(x, y, v) { G.popups.push({ x, y, v, t: 20 }); }
  // entities receive G as their game handle
  G.effect = effect; G.addScore = addScore;
  G.respawnBox = box => respawnBox(box);
  G.requestDialog = id => openDialog(id);   // boss scripts open story dialogs

  // ---- per-frame tick (70 ms) ----
  function tick() {
    if (!G.active) return;
    G.frame++;
    snapshot();
    Hints.tick(G);
    if (G.state === 6) { if (G.dialog) G.dialog.frames++; updateCam(); return; }
    if (G.state === 5 || G.state === 4) { G.resultsT++; return; }
    G.timeMs += 70;

    const W = G.world;
    for (let sub = 0; sub < 2; sub++) {
      // 1. entity ticks (once per substep like the original)
      for (const pl of G.players) pl.tick(G);
      for (const m of G.monsters) m.tick(G);
      for (const bx of G.boxes) bx.tick(G);
      for (const p of G.platforms) p.tick();
      // buttons
      for (const p of G.platforms) {
        if (!p.button) continue;
        const bx = p.button.col << 15, by = p.button.row << 15;
        let held = false;
        for (const b of W.bodies)
          for (const q of b.pts)
            if (q.x > bx && q.x < bx + 32768 && q.y > by && q.y < by + 4096) { held = true; break; }
        p.press(held);
      }
      // 3. pressure, 4. gravity
      for (const b of W.bodies) { b.applyPressure(); b.applyToAll(0, W.gravity); }
      for (const p of W.ropePts) p.applyForce(0, W.gravity);
      for (const p of W.particles) if (!(p.flags & 0x10)) p.applyForce(0, W.gravity);
      // 5. integrate
      for (const b of W.bodies) for (const p of b.pts) p.integrate();
      for (const p of W.ropePts) p.integrate();
      for (const p of W.particles) if (!(p.flags & 0x10)) p.integrate();
      // 6. area preservation
      for (const b of W.bodies) b.areaPreserve();
      // 7. speed clamp
      for (const b of W.bodies) b.clampSpeed();
      // 8. ropes
      for (let i = W.ropes.length - 1; i >= 0; i--) {
        if (W.ropes[i].solve(true)) {
          if (W.ropes[i].limit !== -1) Assets.play('ropebreak');
          W.ropes.splice(i, 1);
        }
      }
      // 9. perimeter springs
      for (const b of W.bodies) {
        for (let it = 0; it < b.iterations; it++)
          for (const s of b.springs)
            if (s.solve(b.type === 3) && b.type === 3) b.flags |= BF.DEAD;
      }
      // 10. grab springs
      for (const b of W.bodies)
        for (let i = b.grabs.length - 1; i >= 0; i--)
          if (b.grabs[i].solve(true)) {
            b.grabs[i].p1.flags &= ~0xC;
            b.grabs.splice(i, 1);
          }
      // 12. clear step flags
      for (const b of W.bodies) { b.stepBits = 0; for (const p of b.pts) p.flags &= ~0x43; }
      for (const p of W.particles) p.flags &= ~3;
      // 13. body-body
      for (let i = 0; i < W.bodies.length; i++)
        for (let j = i + 1; j < W.bodies.length; j++)
          W.collideBodies(W.bodies[i], W.bodies[j]);
      // 15. particle vs body (player eats remains / monster contact)
      for (const p of W.particles) {
        if (p.flags & 0x10) continue;
        for (const b of W.bodies) {
          if (b.type !== 1) continue;
          if (!b.containsPoint(p.x, p.y)) continue;
          const pl = b.owner;
          if (p.kind === 7 && p.owner && p.owner.alive && p.owner.alive()) {
            // monster body touching player
            const v = b.avgVel();
            if (pl && pl.attackWindow > 0) p.owner.die(true, G);
            else if (v.x * v.x + v.y * v.y > 0x3200000) p.owner.die(true, G);
          } else if (p.kind === 6 && pl && p.owner !== pl) {
            // eat dead partner's remains
            p.flags |= 0x10;
            const dead = p.owner;
            dead.remains = dead.remains.filter(q => q !== p);
            if (!dead.remains.length) reviveNear(dead, pl);
          }
        }
      }
      // 16. body vs tiles
      for (const b of W.bodies) W.collideBodyTiles(b);
      // 17. body vs platforms
      for (const p of G.platforms) for (const b of W.bodies) p.collide(b);
      // 18. particles vs tiles
      for (const p of W.particles) {
        if (p.flags & 0x10) continue;
        W.collideParticleTiles(p, p.r, p.frict === 102 ? 512 : 1024);
        if (p.ttl > 0 && --p.ttl === 0) p.flags |= 0x10;
      }
      for (const p of W.ropePts) W.collidePoint(p, null, 512);
    }
    W.particles = W.particles.filter(p => !(p.flags & 0x10) || p.kind === 6);

    // pickups + exit + dialog triggers (frame level)
    for (const pl of G.players) if (!pl.dead && !pl.ai) scanPickups(pl);
    const p0 = G.players[0];
    if (!p0.dead) {
      const c = p0.body.centroid();
      const cx = c.x >> 15, cy = c.y >> 15;
      for (const h of G.lvl.hints) {
        if (h.col === cx && h.row === cy && !G.triggersFired.has('d' + h.id)) { openDialog(h.id); break; }
      }
    }

    // special level logic
    specialLevels();

    // death / restart
    if (p0.dead && p0.deadTicks > 100 && G.submode !== 2) restartLevel();
    else if (G.submode >= 2 && G.players.every(pl => pl.dead)) restartLevel();

    // completion: boss levels use the scene counter, everything else the exit tile
    let done = false;
    const humans = G.players.filter(pl => !pl.ai);
    const override = Bosses.exitOverride(G);
    if (override !== null) done = override && humans.some(pl => !pl.dead);
    else if (G.submode === 2 || G.submode === 3) done = humans.length > 0 && humans.every(pl => pl.exitTouch && !pl.dead);
    else done = humans.some(pl => pl.exitTouch && !pl.dead);
    // versus: AI reaching exit
    if ((G.submode === 4 || G.submode === 5) && G.players.some(pl => pl.ai && pl.exitTouch)) {
      banner(Font.str(50)); restartLevel(); return;
    }
    if (G.levelId === 14) {
      const rival = G.players.find(pl => pl.ai);
      if (rival && rival.exitTouch) { openDialog(67); return; }
    }
    if (G.levelId === 25 && G.timeMs >= 180000) { openDialog(104); return; }
    if (done) {
      G.state = 5; G.resultsT = 0;
      G.totals.score += G.score[0]; G.totals.time += G.timeMs; G.totals.amber += G.amber[0];
    }

    if (G.bannerT > 0 && --G.bannerT === 0) G.banner = null;
    // effects advance
    for (const e of G.effects) if (G.frame & 1) e.f++;
    G.effects = G.effects.filter(e => e.f < 4);
    for (const p of G.popups) { p.y -= 3072; p.t--; }
    G.popups = G.popups.filter(p => p.t > 0);
    updateCam();
  }

  function reviveNear(dead, savior) {
    const c = savior.body.centroid();
    dead.dead = 0; dead.deadTicks = 0;
    dead.health = Math.max(25600, dead.health >> 2) || 25600;
    const b = new Body();
    b.buildRing(c.x + 32768, c.y - 32768, dead.radius, dead.body.pts.length, 1024);
    b.flags = BF.AREA | BF.CANGRAB; b.iterations = 2; b.type = 1; b.owner = dead;
    dead.body = b;
    G.world.bodies.push(b);
  }

  function specialLevels() {
    // intro cutscene script (spec 15.3): scene-driven dialogs
    if (G.levelId === 0) {
      const c = G.players[0].body.centroid();
      const col = c.x >> 15;
      if (G.scene === 0 && col >= 7) { G.scene = 1; openDialog(9); }
      else if (G.scene === 1 && col >= 13) { G.scene = 2; openDialog(10); }  // chains 10..14
      else if (G.scene === 2 && col >= 18) { G.scene = 3; openDialog(15); }
      return;
    }
    Bosses.tick(G);
  }

  // ---- camera ----
  function updateCam() {
    const p0 = G.players[0];
    const c = p0.dead && p0.remains.length ? { x: p0.remains[0].x, y: p0.remains[0].y } : p0.body.centroid();
    let tx = (c.x >> 10) - (View.w >> 1);
    if (G.levelId === 20) tx = (c.x >> 10) - View.w + Math.max(2 * View.w / 5 | 0, 50);
    const ty = (c.y >> 10) - (View.h >> 1);
    G.camX += (tx - G.camX) >> 1;
    G.camY += (ty - G.camY) >> 1;
    clampCam();
  }
  function clampCam() {
    const mw = G.world.w * 32, mh = G.world.h * 32;
    G.camX = Math.max(0, Math.min(G.camX, Math.max(0, mw - View.w)));
    G.camY = Math.max(0, Math.min(G.camY, Math.max(0, mh - View.h)));
    if (mw <= View.w) G.camX = 0;
    if (mh <= View.h) G.camY = mh - View.h;
  }

  // ---- input ----
  // Any press, tap or click counts as the player being present: it resets the
  // idle-hint timer (Main also calls this for input the game never sees).
  function activity(kind) { Hints.activity(kind); }

  function key(code, down) {
    const pl = G.players[0];
    if (down) Hints.activity();
    if (G.state === 6) { if (down && (code === -5 || code === -6 || code === 53 || code === 32)) { if (G.dialog && G.dialog.frames >= 8) dismissDialog(); } return; }
    if (G.state === 5 || G.state === 4) { if (down && G.resultsT > 7) finishResults(); return; }
    if (!pl || pl.dead) return;
    // cheat: 1-3-9-1-9 warp to ending
    if (down && code >= 48 && code <= 57) {
      G.cheatBuf.push(code);
      if (G.cheatBuf.length > 5) G.cheatBuf.shift();
      if (G.cheatBuf.join(',') === '49,51,57,49,57') {
        G.levelId = G.submode === 2 ? 67 : 35;
        G.state = 5; G.resultsT = 0;
        return;
      }
    }
    switch (code) {
      case -1: case 50: pl.keys.up = down; break;
      case -2: case 56: pl.keys.down = down; break;
      case -3: case 52: pl.keys.left = down; break;
      case -4: case 54: pl.keys.right = down; break;
      case -5: case 53: if (down) pl.keys.attack = true; break;
      case 42: if (down) pl.setAbility(pl.ability === 2 ? 0 : 2); break;
      case 35: if (down) pl.setAbility(pl.ability === 1 ? 0 : 1); break;
      case -6: if (down) pl.cycleAbility(); break;
      case -7: if (down) { releaseKeys(); G.active = false; Shell.open(1); } break;
    }
  }

  // release every held direction (pause, blur, focus loss, touch cancel)
  function releaseKeys() {
    for (const pl of G.players) {
      if (!pl || pl.ai) continue;
      pl.keys.left = pl.keys.right = pl.keys.up = pl.keys.down = false;
    }
  }

  // on-screen buttons: ability (bottom-left) and pause (bottom-right)
  function buttonAt(x, y) {
    if (y <= View.h - 70) return null;
    if (x > View.w - 66) return 'pause';
    if (x < 66) return 'ability';
    return null;
  }
  function pressButton(kind) {
    if (kind === 'pause') { releaseKeys(); G.active = false; Shell.open(1); return; }
    if (kind === 'ability') { const pl = G.players[0]; if (pl && !pl.dead) pl.cycleAbility(); }
  }

  // A touch/click going down. Returns 'consumed' when a dialog, the results
  // sheet or an on-screen button took it, or 'steer' when it drives Gish —
  // the caller uses that to decide which finger owns steering.
  function touchDown(x, y) {
    Hints.activity();
    if (G.state === 6) { if (G.dialog && G.dialog.frames >= 8) dismissDialog(); return 'consumed'; }
    if (G.state === 5 || G.state === 4) { if (G.resultsT > 7) finishResults(); return 'consumed'; }
    const b = buttonAt(x, y);
    if (b) { pressButton(b); return 'consumed'; }
    if (!G.players[0]) return 'consumed';
    steer(x, y);
    return 'steer';
  }

  // pointer steering: hold a direction relative to Gish; poke his body to attack
  function steer(x, y) {
    const pl = G.players[0];
    if (!pl) return;
    Hints.activity();
    // measure against where Gish was last drawn, which is what the finger sees
    let px = G.drawPx, py = G.drawPy;
    if (px === undefined || py === undefined) {
      const c = pl.body.centroid();
      px = (c.x >> 10) - G.camX; py = (c.y >> 10) - G.camY;
    }
    const dx = x - px, dy = y - py;
    if (Math.abs(dx) < 20 && Math.abs(dy) < 20) {
      pl.keys.left = pl.keys.right = pl.keys.up = pl.keys.down = false;
      pl.keys.attack = true;
      return;
    }
    pl.keys.left = dx < -16; pl.keys.right = dx > 16;
    pl.keys.up = dy < -24; pl.keys.down = dy > 24;
  }

  // mouse-style single pointer (kept for desktop click-drag)
  function tap(x, y, down) {
    if (!down) { releaseKeys(); return; }
    touchDown(x, y);
  }

  function finishResults() {
    G.active = false;
    Shell.onLevelComplete(G.levelId, G.totals.score, G.totals.time);
  }

  // ================= RENDER =================
  // Physics advances in 70 ms ticks but the screen repaints at the display
  // rate, so a frame drawn straight from the physics state would sit still
  // for 3-4 frames and then jump. draw() takes alpha = how far the renderer
  // is into the current tick (0..1) and shows every point interpolated
  // between its start-of-tick position (rx/ry, see snapshot()) and its
  // current one; the camera likewise. That turns the 14 Hz steps into
  // continuous motion without touching the simulation.
  let A = 1;                                   // alpha of the frame being drawn
  const TELEPORT = 64 << 10;                   // a 64 px jump in one tick is a respawn, not motion
  function ipt(p) {                            // interpolated position of a Pt, in px
    if (p.rx === undefined) return { x: p.x / 1024, y: p.y / 1024 };
    const dx = p.x - p.rx, dy = p.y - p.ry;
    if (dx > TELEPORT || dx < -TELEPORT || dy > TELEPORT || dy < -TELEPORT) return { x: p.x / 1024, y: p.y / 1024 };
    return { x: (p.rx + dx * A) / 1024, y: (p.ry + dy * A) / 1024 };
  }
  function ipos(q) {                           // spring anchor: Pt, edge {a,b,t} or fixed {x,y}
    if (q instanceof Pt) return ipt(q);
    if (q.a) {
      const a = ipt(q.a), b = ipt(q.b);
      return { x: a.x + (b.x - a.x) * q.t / 1024, y: a.y + (b.y - a.y) * q.t / 1024 };
    }
    return { x: q.x / 1024, y: q.y / 1024 };
  }
  function icentroid(b) {
    let x = 0, y = 0, n = 0;
    for (const p of b.pts) { if (p.flags & 0x10) continue; const q = ipt(p); x += q.x; y += q.y; n++; }
    return n ? { x: x / n, y: y / n } : { x: 0, y: 0 };
  }
  const icam = (prev, cur) => (prev === undefined || Math.abs(cur - prev) > 200) ? cur : Math.round(prev + (cur - prev) * A);

  function draw(ctx, alpha) {
    if (!G.world) return;
    A = alpha === undefined || !isFinite(alpha) ? 1 : Math.max(0, Math.min(1, alpha));
    const I = Assets.images;
    const W = View.w, H = View.h;
    const k = icam(G.pcamX, G.camX), l = icam(G.pcamY, G.camY);
    G.drawCamX = k; G.drawCamY = l;
    const t = G.lvl.tiles;
    const theme = G.lvl.theme;

    // 1. background fill
    ctx.fillStyle = '#' + THEME_BG[theme >= 0 && theme <= 2 ? theme : 0].toString(16).padStart(6, '0');
    if (G.levelId === 0) ctx.fillStyle = '#141a48';
    ctx.fillRect(0, 0, W, H);
    if (G.levelId === 0) drawStars(ctx);

    // 2. parallax backdrop
    const bd = I[468 + theme];
    if (bd && Shell.S.settings.detail > 0) {
      if (theme === 2) {
        const stripTop = Math.max(0, (H >> 1) - (bd.height >> 2));
        ctx.fillStyle = '#80f8ff';
        ctx.fillRect(0, stripTop + bd.height, W, H - stripTop - bd.height);
        const par = (k / 5) | 0;
        let x0 = -(par % bd.width);
        for (let x = x0 - bd.width; x < W; x += bd.width) ctx.drawImage(bd, x, stripTop);
      } else {
        // screen-anchored 64px grid over backdrop-set layer-0 cells
        const cx0 = Math.max(0, k >> 5), cx1 = Math.min(G.world.w - 1, (k + W) >> 5);
        const cy0 = Math.max(0, l >> 5), cy1 = Math.min(G.world.h - 1, (l + H) >> 5);
        for (let cx = cx0; cx <= cx1; cx++)
          for (let cy = cy0; cy <= cy1; cy++) {
            const id = t[0][cx][cy];
            if (id >= 0 && BDROP.has(id)) {
              const sx = cx * 32 - k, sy = cy * 32 - l;
              const gx = sx - (((sx % 64) + 64) % 64), gy = sy - (((sy % 64) + 64) % 64);
              ctx.save();
              ctx.beginPath(); ctx.rect(sx, sy, 32, 32); ctx.clip();
              for (let bx2 = gx; bx2 < sx + 32; bx2 += 64)
                for (let by2 = gy; by2 < sy + 32; by2 += 64)
                  ctx.drawImage(bd, bx2, by2);
              ctx.restore();
            }
          }
      }
    }

    const cx0 = Math.max(0, k >> 5), cx1 = Math.min(G.world.w - 1, (k + W) >> 5);
    const cy0 = Math.max(0, l >> 5), cy1 = Math.min(G.world.h - 1, (l + H) >> 5);

    // 3. layer 0
    for (let cx = cx0; cx <= cx1; cx++)
      for (let cy = cy0; cy <= cy1; cy++) {
        const id = t[0][cx][cy];
        if (id < 0 || id === 11 || id === 51) continue;
        const sx = cx * 32 - k, sy = cy * 32 - l;
        if (id === 30) { const im = I[272 + (G.frame & 3)]; if (im) ctx.drawImage(im, sx, sy); continue; }
        let base = id;
        if (id === 8) base = 5; else if (id === 9) base = 0;
        const im = I[276 + base];
        if (im) ctx.drawImage(im, sx, sy);
        if (id === 8 || id === 9) {
          const fl = I[276 + 8 + ((G.frame >> 2) & 1)];
          if (fl) ctx.drawImage(fl, sx + 1, sy + 32 - fl.height);
        }
      }

    // 4. buttons bases, boxes
    for (const p of G.platforms) {
      if (!p.button) continue;
      const im = I[p.active ? 78 : 77];
      if (im) ctx.drawImage(im, p.button.col * 32 - k, p.button.row * 32 - l);
      const lever = I[79];
      if (lever) ctx.drawImage(lever, p.button.col * 32 - k, p.button.row * 32 - l + (p.button.wasPressed ? 6 : 2));
    }
    for (const bx of G.boxes) drawBox(ctx, bx, k, l);

    // 5. players
    for (const pl of G.players) drawPlayer(ctx, pl, k, l);

    // 7. platforms
    for (const p of G.platforms) {
      const im = I[235];
      const px = Math.round((p.rx === undefined ? p.x : p.rx + (p.x - p.rx) * A) / 1024) - k;
      const py = Math.round((p.ry === undefined ? p.y : p.ry + (p.y - p.ry) * A) / 1024) - l;
      if (im) {
        for (let i = 0; i < p.K.tiles; i++)
          ctx.drawImage(im, px + (p.K.vert ? 0 : i * 32), py + (p.K.vert ? i * 32 : 0));
      }
    }

    // 8. layer 1
    for (let cx = cx0; cx <= cx1; cx++)
      for (let cy = cy0; cy <= cy1; cy++) {
        const id = t[1][cx][cy];
        if (id < 0 || id === 13 || id === 43) continue;
        const sx = cx * 32 - k, sy = cy * 32 - l;
        if (id === 8) { const im = I[46]; if (im) ctx.drawImage(im, sx + 8, sy + 8 + Math.round(3 * Math.sin(G.frame / 4 + cx))); continue; }
        if (id === 9) { const im = I[47 + (G.frame >> 2) % 3]; if (im) ctx.drawImage(im, sx + 8, sy + 8); continue; }
        if (id === 70) { const im = I[566 + 70]; if (im) ctx.drawImage(im, sx, sy + Math.round(2 * Math.sin(G.frame / 3))); continue; }
        const im = I[566 + id];
        if (im) ctx.drawImage(im, sx, sy);
      }

    // 9. monsters
    for (const m of G.monsters) drawMonster(ctx, m, k, l);

    // particles
    for (const p of G.world.particles) {
      if (p.flags & 0x10 && p.kind !== 6) continue;
      const q = ipt(p);
      const px = q.x - k, py = q.y - l;
      ctx.fillStyle = p.kind === 0 ? '#4a0000' : p.kind === 6 ? '#000' : '#5a4632';
      const r = Math.max(2, p.r >> 10);
      ctx.beginPath(); ctx.arc(px, py, r, 0, 7); ctx.fill();
      if (p.kind === 6) { ctx.strokeStyle = '#333'; ctx.stroke(); }
    }

    // 10. effects
    for (const e of G.effects) {
      const base = e.type === 0 || e.type === 1 ? 150 : e.type === 2 ? 464 : e.type === 4 ? 130 : 134;
      const im = I[base + Math.min(3, e.f)];
      if (im) ctx.drawImage(im, (e.x >> 10) - k - (im.width >> 1), (e.y >> 10) - l - (im.height >> 1));
    }

    // 11. ropes
    ctx.strokeStyle = '#6b4a2a'; ctx.lineWidth = 3;
    for (const s of G.world.ropes) {
      const a = ipt(s.p1), q = ipos(s.p2);
      ctx.beginPath();
      ctx.moveTo(a.x - k, a.y - l);
      ctx.lineTo(q.x - k, q.y - l);
      ctx.stroke();
    }
    ctx.lineWidth = 1;

    // 12. layer 2 (water over everything)
    const waterIm = { 6: Assets.images['fg6'], 7: Assets.images['fg7'] };
    for (let cx = cx0; cx <= cx1; cx++)
      for (let cy = cy0; cy <= cy1; cy++) {
        const id = t[2][cx][cy];
        if (id < 0) continue;
        const sx = cx * 32 - k, sy = cy * 32 - l;
        if (id === 6 || id === 7) { const im = waterIm[id]; if (im) ctx.drawImage(im, sx, sy); else { ctx.fillStyle = 'rgba(30,60,50,0.55)'; ctx.fillRect(sx, sy, 32, 32); } continue; }
        if (id === 36) { const im = waterIm[6]; ctx.save(); ctx.globalAlpha = 0.8; if (im) ctx.drawImage(im, sx, sy); else { ctx.fillStyle = 'rgba(40,80,30,0.6)'; ctx.fillRect(sx, sy, 32, 32); } ctx.restore(); continue; }
        if (id === 37) { const im = I[460 + ((G.frame >> 1) & 3)]; if (im) ctx.drawImage(im, sx, sy); continue; }
        const rm = FG_REMAP[id];
        const im = rm ? I[rm] : I[407 + id];
        if (im) ctx.drawImage(im, sx, sy);
      }

    // 13. darkness (level 20)
    if (G.dark) drawDarkness(ctx, k, l);

    // 15. popups
    for (const p of G.popups)
      Font.drawText(ctx, 3, '+' + p.v, (p.x >> 10) - k, (p.y >> 10) - l, 1);

    // dialog / results / HUD
    if (G.state === 6 && G.dialog) drawDialog(ctx);
    else if (G.state === 5 || G.state === 4) drawResults(ctx);
    else { drawHUD(ctx); Hints.draw(ctx, G, A); }

    if (G.banner) {
      ctx.fillStyle = 'rgba(0,0,0,0.85)';
      const lines = Font.wrap(3, Font.encode(3, G.banner), W - 20);
      const bh = lines.length * 25 + 14;
      ctx.fillRect(0, H - 70 - bh, W, bh);
      ctx.fillStyle = '#555'; ctx.fillRect(0, H - 70 - bh, W, 1);
      Font.drawWrapped(ctx, 3, lines, 10, H - 63 - bh + 7);
    }
  }

  function drawStars(ctx) {
    const n = (View.w * View.h / 2000) | 0;
    let seed = 12345;
    const rand = () => (seed = (seed * 1103515245 + 12345) & 0x7fffffff) / 0x7fffffff;
    for (let i = 0; i < n; i++) {
      const x = rand() * View.w, y = rand() * View.h;
      const bright = (i & 1) === ((G.frame / 42) & 1);
      ctx.fillStyle = bright ? '#8b91bd' : '#474e83';
      ctx.fillRect(x | 0, y | 0, 1, 1);
    }
  }

  function tracePoly(ctx, b, k, l) {
    ctx.beginPath();
    for (let i = 0; i < b.pts.length; i++) {
      const q = ipt(b.pts[i]);
      if (i) ctx.lineTo(q.x - k, q.y - l);
      else ctx.moveTo(q.x - k, q.y - l);
    }
    ctx.closePath();
  }

  function drawBox(ctx, bx, k, l) {
    if (bx.dead) return;
    const b = bx.body;
    tracePoly(ctx, b, k, l);
    const dark = G.lvl.theme === 0;
    if (bx.K.ball) ctx.fillStyle = '#1a1a1a';
    else if (bx.kind === 4 || bx.kind === 0 || bx.kind === 7) ctx.fillStyle = dark ? '#24424a' : '#2a2624';
    else if (bx.kind === 9) ctx.fillStyle = '#3f4438';
    else ctx.fillStyle = dark ? '#3e2d1f' : '#735c55';
    ctx.fill();
    ctx.strokeStyle = '#000'; ctx.stroke();
    if (bx.K.hang) {
      const c = icentroid(b);
      ctx.fillStyle = '#111';
      ctx.beginPath(); ctx.arc(c.x - k, c.y - l, 3, 0, 7); ctx.fill();
    }
    if (bx.K.ball) {
      const c = icentroid(b);
      const face = Assets.images[FX.rnd(0, 60) === 0 ? 141 : 140];
      if (face) ctx.drawImage(face, Math.round(c.x - k) - (face.width >> 1), Math.round(c.y - l) - (face.height >> 1));
    }
  }

  function drawPlayer(ctx, pl, k, l) {
    if (pl.dead) return;
    const b = pl.body;
    tracePoly(ctx, b, k, l);
    let fill = pl.skin === 2 ? '#999999' : pl.skin === 1 ? '#121516' : '#000000';
    if (pl.hurtFlash > 0) fill = ['#5a0000', '#b10000', '#ff0000'][pl.hurtFlash % 3];
    ctx.fillStyle = fill;
    ctx.fill();
    let outline;
    const theme2 = G.lvl.theme === 2;
    if (pl.skin === 2) outline = pl.ability === 2 ? '#cccccc' : pl.ability === 1 ? '#a5530b' : '#5d5d5d';
    else if (pl.ability === 1) outline = theme2 ? '#a77c15' : '#83610f';
    else if (pl.ability === 2) outline = theme2 ? '#bbbbbb' : '#636363';
    else outline = '#000000';
    ctx.strokeStyle = outline; ctx.lineWidth = 2; ctx.stroke(); ctx.lineWidth = 1;
    // face
    const c = icentroid(b);
    if (pl === G.players[0]) { G.drawPx = c.x - k; G.drawPy = c.y - l; }
    const off = FX.vecFromAngle(pl.gaze, 3072);
    const fx = Math.round(c.x + off.x / 1024) - k, fy = Math.round(c.y + off.y / 1024) - l;
    drawFace(ctx, fx, fy, pl.gaze, pl.skin, pl.hurtFlash > 0 ? 1 : pl.blink > 0 ? 2 : 0);
  }

  // Gish's face at (fx, fy): 8 base sprites picked by gaze angle, frames 8-31
  // are 90/180/270 degree turns of them. mode 1 = hurt, 2 = blink.
  function drawFace(ctx, fx, fy, gaze, skin, mode) {
    const I = Assets.images;
    let im;
    if (mode === 1) im = I[skin === 2 ? 525 : 125];
    else if (mode === 2) im = I[skin === 2 ? 524 : 119];
    else {
      const fr = FX.frame32(gaze - FX.QUARTER);
      im = I[(skin === 2 ? 516 : 110) + (fr & 7)];
      if (im && fr >= 8) {
        const rot = fr < 16 ? 90 : fr < 24 ? 180 : 270;
        ctx.save();
        ctx.translate(fx, fy);
        ctx.rotate(rot * Math.PI / 180);
        ctx.drawImage(im, -(im.width >> 1), -(im.height >> 1));
        ctx.restore();
        return;
      }
    }
    if (im) ctx.drawImage(im, fx - (im.width >> 1), fy - (im.height >> 1));
  }

  function drawMonster(ctx, m, k, l) {
    const I = Assets.images;
    const grp = m.K.spr;
    let sprId;
    if (m.state === 1) { sprId = grp[grp.length - 1]; if (m.frame > 20) return; }
    else sprId = grp[0] + (grp.length > 2 ? (m.frame >> 1) % 2 : 0);
    let im = I[256 + sprId];
    if (!im) im = I[256 + grp[0]];
    if (!im) return;
    const q = ipt(m.p);
    const px = Math.round(q.x) - k, py = Math.round(q.y) - l;
    if (m.kind === 6) {
      const segs = Math.max(1, (m.extend >> 15) + 1);
      for (let i = 0; i < segs; i++)
        ctx.drawImage(im, px - (im.width >> 1), py - (im.height >> 1) + i * 32);
      return;
    }
    ctx.save();
    if (m.dir < 0) { ctx.translate(px, py); ctx.scale(-1, 1); ctx.drawImage(im, -(im.width >> 1), -(im.height >> 1)); }
    else ctx.drawImage(im, px - (im.width >> 1), py - (im.height >> 1));
    ctx.restore();
    if (m.p2) {
      const im2 = (grp[1] >= 0 ? I[256 + grp[1]] : null) || im;
      const q2 = ipt(m.p2);
      ctx.drawImage(im2, Math.round(q2.x) - k - (im2.width >> 1), Math.round(q2.y) - l - (im2.height >> 1));
    }
  }

  function drawDarkness(ctx, k, l) {
    const pl = G.players[0];
    const c = icentroid(pl.body);
    const px = Math.round(c.x) - k, py = Math.round(c.y) - l;
    const r = G.darkR >> 10;              // grows near lava glow (level 20)
    ctx.fillStyle = '#000';
    ctx.fillRect(0, 0, View.w, py - r);
    ctx.fillRect(0, py + r, View.w, View.h - py - r);
    ctx.fillRect(0, py - r, px - r, 2 * r);
    ctx.fillRect(px + r, py - r, View.w - px - r, 2 * r);
    if (G.darkR !== 46080) {
      // while the window is expanding the original draws plain 18 px corners
      ctx.beginPath();
      ctx.moveTo(px - r, py - r); ctx.lineTo(px - r + 18, py - r); ctx.lineTo(px - r, py - r + 18);
      ctx.moveTo(px + r, py - r); ctx.lineTo(px + r - 18, py - r); ctx.lineTo(px + r, py - r + 18);
      ctx.moveTo(px + r, py + r); ctx.lineTo(px + r - 18, py + r); ctx.lineTo(px + r, py + r - 18);
      ctx.moveTo(px - r, py + r); ctx.lineTo(px - r + 18, py + r); ctx.lineTo(px - r, py + r - 18);
      ctx.fill();
      return;
    }
    const im = Assets.images[471];
    if (im) {
      ctx.drawImage(im, px - r, py - r);
      ctx.save(); ctx.translate(px + r, py - r); ctx.scale(-1, 1); ctx.drawImage(im, 0, 0); ctx.restore();
      ctx.save(); ctx.translate(px - r, py + r); ctx.scale(1, -1); ctx.drawImage(im, 0, 0); ctx.restore();
      ctx.save(); ctx.translate(px + r, py + r); ctx.scale(-1, -1); ctx.drawImage(im, 0, 0); ctx.restore();
    }
  }

  function drawDialog(ctx) {
    const d = G.dialog;
    const W = View.w, H = View.h;
    const lh = 25;
    const boxH = Math.max(64, d.lines.length * lh + 20);
    const slide = Math.min(4, ++d.slide);
    const y = H - ((boxH * slide / 4) | 0);
    ctx.fillStyle = 'rgb(23,23,23)';
    ctx.fillRect(0, y, W, boxH);
    const rope = Assets.images[90];
    if (rope) for (let x = 0; x < W; x += rope.width) ctx.drawImage(rope, x, y - (rope.height >> 1));
    const portrait = d.portrait ? Assets.images[d.portrait] : null;
    const left = d.portrait === 82 || d.portrait === 84;
    let tx = 8;
    if (portrait) {
      const px2 = left ? 6 : W - 50;
      ctx.fillStyle = 'rgb(9,9,9)'; ctx.fillRect(px2 - 1, y + 7, 46, 46);
      ctx.drawImage(portrait, px2, y + 8, 44, 44);
      ctx.strokeStyle = '#fff'; ctx.strokeRect(px2 - 1.5, y + 6.5, 47, 47);
      if (left) tx = 58;
      const nameId = SPEAKER_NAME[d.portrait];
      if (nameId !== undefined) {
        ctx.fillStyle = 'rgb(29,29,25)';
        ctx.fillRect(left ? 6 : W - 110, y + 54, 104, 16);
        Font.drawText(ctx, 3, Font.str(nameId), left ? 8 : W - 108, y + 51);
      }
    }
    let ty = y + 10;
    for (const line of d.lines) { Font.draw(ctx, 3, line, tx, ty); ty += lh; }
  }

  function drawResults(ctx) {
    const W = View.w, H = View.h;
    const slide = Math.min(7, G.resultsT);
    const oy = -H + ((H * slide / 7) | 0);
    ctx.save();
    ctx.translate(0, oy);
    ctx.fillStyle = 'rgba(0,0,0,0.88)';
    ctx.fillRect(0, 0, W, H);
    const fmt = ms => ((ms / 60000) | 0) + ':' + String(((ms / 1000) | 0) % 60).padStart(2, '0');
    let y = 40;
    Font.drawStr(ctx, 41, W >> 1, y, 1); y += 44;
    Font.drawStr(ctx, 36, W >> 1, y, 1); y += 60;
    Font.drawText(ctx, 3, Font.str(37) + ' ' + G.score[0], W >> 1, y, 1); y += 26;
    Font.drawText(ctx, 3, Font.str(38) + ' ' + fmt(G.timeMs), W >> 1, y, 1); y += 26;
    if (G.submode === 0) { Font.drawText(ctx, 3, Font.str(14) + ' ' + G.amber[0] + '/' + G.amberTotal, W >> 1, y, 1); y += 26; }
    ctx.fillStyle = '#444'; ctx.fillRect(40, y + 4, W - 80, 1); y += 16;
    Font.drawText(ctx, 3, Font.str(97) + ' ' + G.totals.score, W >> 1, y, 1); y += 26;
    Font.drawText(ctx, 3, Font.str(98) + ' ' + fmt(G.totals.time), W >> 1, y, 1); y += 26;
    Font.drawText(ctx, 3, Font.str(99) + ' ' + G.totals.amber, W >> 1, y, 1);
    ctx.restore();
  }

  function drawHUD(ctx) {
    const I = Assets.images, W = View.w, H = View.h;
    // health bar
    const cap = I[10];
    if (cap) {
      const capW = cap.width;
      const inner = ((W >> 1) - 2 * capW - 7) | 0;
      const p0 = G.players[0];
      const fillW = Math.max(0, (inner * p0.health / 102400) | 0);
      ctx.drawImage(cap, 1, 1);
      let fillIm;
      if (p0.health >= 51200) fillIm = I[12];
      else if (p0.health >= 25600) fillIm = I[14];
      else fillIm = I[14 + (G.frame % 3)];
      let x = capW + 1;
      if (fillIm && fillW > 0) {
        ctx.save(); ctx.beginPath(); ctx.rect(x, 1, fillW, 12); ctx.clip();
        for (let i = 0; i < fillW; i += fillIm.width) ctx.drawImage(fillIm, x + i, 1);
        ctx.restore();
      }
      const empty = I[13];
      if (empty && fillW < inner) {
        ctx.save(); ctx.beginPath(); ctx.rect(x + fillW, 1, inner - fillW, 12); ctx.clip();
        for (let i = 0; i < inner - fillW; i += empty.width) ctx.drawImage(empty, x + fillW + i, 1);
        ctx.restore();
      }
      if (I[11]) ctx.drawImage(I[11], x + inner, 1);
    }
    // score / timer
    Font.drawText(ctx, 3, String(G.score[0]), W - 2, 2, 8);
    const rem = G.levelId === 25 ? Math.max(0, 180000 - G.timeMs) : G.timeMs;
    const fmt = ((rem / 60000) | 0) + ':' + String(((rem / 1000) | 0) % 60).padStart(2, '0');
    Font.drawText(ctx, 3, fmt, W >> 1, 2, 1);
    // buttons
    const pause = I[2];
    if (pause) ctx.drawImage(pause, W - pause.width - 2, H - pause.height - 2);
    const ab = I[4 + G.players[0].ability];
    if (ab) ctx.drawImage(ab, 2, H - ab.height - 2);
  }

  return { start, restartLevel, abort, tick, draw, key, tap, addScore, respawnBox, effect,
           touchDown, steer, releaseKeys, buttonAt, pressButton, activity, drawFace,
           get active() { return G.active; }, set active(v) { G.active = v; },
           get mode() { return G.mode; }, get submode() { return G.submode; },
           get levelId() { return G.levelId; },
           get players() { return G.players; }, get monsters() { return G.monsters; },
           G };
})();
