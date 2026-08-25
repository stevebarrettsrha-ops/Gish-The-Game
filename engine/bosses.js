// Boss & rival scripts — ported from the decompiled state machines.
// Sources: d.java (player/AI tick: level-14 waypoint route, chase AI, level-28
// score trigger, level-32 boost, level-34 rival death), ae.java (tower-boss
// kind 5: level-28/32 kill conditions and death tile writes), ab.java
// (level-20 darkness growth). See reference/spec_engine.md §15.2.
//
// Scene counter (`ab.c` in the original) is G.scene; boss levels 20/23/28/32
// complete at scene 6 and level 34 at scene 2 — the same override the original
// applies in place of the exit tile.

const Bosses = (() => {
  const T = 32768;                    // one tile in fixed units
  const tile = v => v >> 15;

  // ---- level 14: Hera's scripted race route (d.java case 1) ----
  // Each scene sets ability + held directions and advances on a tile threshold;
  // scenes 4/5/8 fall back to an earlier scene if she drops off the route.
  function waypointAI(pl, G) {
    const k = pl.keys;
    k.left = k.right = k.up = k.down = false;
    const c = pl.body.centroid();
    const x = tile(c.x), y = tile(c.y);

    // she loses the race for you if she reaches the exit first
    const t1 = G.lvl.tiles[1];
    if (x >= 0 && y >= 0 && x < G.world.w && y < G.world.h && t1[x][y] === 13) {
      G.requestDialog(67);
      return;
    }
    const setAb = a => { if (pl.ability !== a) pl.setAbility(a); };

    switch (G.scene) {
      case 0:
        if (x < 7) k.right = true;
        else if (x > 7) k.left = true;
        break;
      case 1:
        setAb(0); k.right = true;
        if (x >= 10) G.scene++;
        break;
      case 2:
        setAb(2); k.right = true;
        if (x >= 13) G.scene++;
        break;
      case 3:
        setAb(0); k.right = true;
        if (x > 28) G.scene++;
        break;
      case 4:
        setAb(2); k.right = true; k.up = true;
        if (y <= 5) G.scene++;
        else if (x < 29 && y >= 10) G.scene = 2;
        break;
      case 5:
        setAb(2); k.left = true; k.up = true;
        if (x < 27) G.scene++;
        else if (x < 29 && y >= 10) G.scene = 2;
        break;
      case 6:
        setAb(0); k.down = true; k.left = true;
        if (x <= 22) G.scene++;
        break;
      case 7:
        setAb(2); k.up = true; k.left = true;
        if (y <= 1) G.scene++;
        break;
      case 8:
        setAb(0); k.right = true;
        if (y >= 18) G.scene++;
        else if (x <= 30 && y >= 5) G.scene = 7;
        break;
      case 9:
        setAb(2); k.right = true;
        break;
    }
  }

  // ---- pursuit AI (d.java case 2): sticky chaser used by Hera on level 25 ----
  function chaseAI(pl, G) {
    const k = pl.keys;
    k.left = k.right = k.up = k.down = false;
    if (G.scene === 0) G.scene = 1;
    if (G.scene !== 1) return;
    const human = G.players.find(p => !p.ai && !p.dead);
    if (!human) return;
    if (pl.ability !== 2) pl.setAbility(2);
    const tp = human.body.centroid(), mp = pl.body.centroid();
    const dx = tp.x - mp.x, dy = tp.y - mp.y;
    if (tp.y < mp.y && tile(mp.y) > 1 && tile(tp.y) < 15) {
      const tx = tile(tp.x), mx = tile(mp.x);
      if (tx < 7) { if (mx > 1) k.left = true; k.up = true; return; }
      if (tx <= 7) return;
      if (mx < 13) k.right = true;
      k.up = true;
      return;
    }
    if (dx > 0) k.right = true; else if (dx < 0) k.left = true;
    if (dy > 0) k.down = true; else if (dy < 0) k.up = true;
  }

  // ---- level 34 duel (d.java case 3): Hera chases while the walls close ----
  function duelAI(pl, G) {
    const k = pl.keys;
    k.left = k.right = k.up = k.down = false;
    const walls = G.platforms;
    if (G.scene === 0) {                       // walls held off until dialog 128
      if (walls[0]) walls[0].active = false;
      if (walls[1]) walls[1].active = false;
      return;
    }
    if (G.scene < 1) G.scene = 1;
    if (walls[0] && !walls[0].active) {        // crusher walls start, mode 0 (loop)
      for (const w of [walls[0], walls[1]]) {
        if (!w) continue;
        w.active = true; w.oneShot = false; w.mode = 0; w.t = 0;
      }
    }
    const human = G.players.find(p => !p.ai && !p.dead);
    if (!human) return;
    if (pl.ability !== 2) pl.setAbility(2);
    const tp = human.body.centroid(), mp = pl.body.centroid();
    const dx = tp.x - mp.x, dy = tp.y - mp.y;
    if (dx > 0) k.right = true; else if (dx < 0) k.left = true;
    if (!pl.attackTargets.length && tile(mp.y) > 5) { k.up = true; return; }
    if (dy > 0) k.down = true; else if (dy < 0) k.up = true;
  }

  function aiFor(levelId, submode) {
    if (levelId === 14) return waypointAI;
    if (levelId === 34) return duelAI;
    if (levelId === 25) return chaseAI;
    return chaseAI;
  }

  // ---- per-frame level scripts ----
  function tick(G) {
    switch (G.levelId) {
      case 14: level14(G); break;
      case 20: level20(G); break;
      case 28: level28(G); break;
      case 32: level32(G); break;
      case 34: level34(G); break;
    }
  }

  // level 14: the race starts once the player passes x = 206438 (fixed) -> dialog 61
  function level14(G) {
    if (G.scene !== 0) return;
    const human = G.players.find(p => !p.ai);
    if (human && !human.dead && human.body.centroid().x > 206438) {
      G.scene = 1;
      G.requestDialog(61);
    }
  }

  // does a 3x3 block of background tiles around (cx,cy) contain lava glow (id 30)?
  function nearLava(G, cx, cy) {
    for (let x = cx - 1; x <= cx + 1; x++)
      for (let y = cy - 1; y <= cy + 1; y++)
        if (x >= 0 && y >= 0 && x < G.world.w && y < G.world.h && G.lvl.tiles[0][x][y] === 30)
          return true;
    return false;
  }

  // level 20: darkness window grows near lava glow (bg tile 30); boss burns there
  function level20(G) {
    const human = G.players.find(p => !p.ai);
    if (human && !human.dead) {
      const c = human.body.centroid();
      if (nearLava(G, tile(c.x), tile(c.y))) {
        G.darkR += 4608;                        // +4.5 px per frame
        if (G.scene === 1) { G.scene = 3; G.requestDialog(78); }
      }
    }
    // the boss chase ends when he blunders into the lava — independent of Gish
    for (const m of G.monsters) {
      if (m.kind !== 4 || !m.alive()) continue;
      if (!nearLava(G, tile(m.p.x), tile(m.p.y))) continue;
      if (G.scene < 3) continue;
      if (G.scene < 5) G.scene = 5;
      m.die(true, G);
      onBossDeath(G, m);
    }
  }

  // Tower bosses (kind 5) own a pool of tentacle/pillar slots (kind 6) that the
  // original reserves at load and spawns at runtime; ae.java a(int).
  function spawnPillar(G, boss, slot) {
    const pool = G.pillars || [];
    const p = pool[slot];
    if (!p || p.alive()) return;
    const bossCol = tile(boss.p.x), bossRow = tile(boss.p.y);
    const off = G.levelId === 32
      ? ((slot & 1) === 0 ? FX.rnd(-6, -2) : FX.rnd(2, 6))
      : FX.rnd(-7, 6);
    let col = bossCol + off;
    col = Math.max(1, Math.min(G.world.w - 2, col));
    p.state = 0; p.frame = 0;
    p.p.flags &= ~0x10;
    p.p.x = (col << 15) + 16384;
    p.p.y = (bossRow << 15) + 16384;
    p.p.px = p.p.x; p.p.py = p.p.y;
    p.extend = 0;
    p.len = FX.rnd(2, 4);
    p.slot = slot;
    Assets.play('visattack');
  }

  // level 28: cut the tentacles down (+30 each, 180 total) then the god is killable
  function level28(G) {
    const boss = G.monsters.find(m => m.kind === 5);
    if (!boss || !boss.alive()) return;
    if (G.scene < 2) {
      // he keeps feeding tentacles up while the fight is on
      if (G.pillars && --G.pillarCool <= 0) {
        G.pillarCool = 50;
        const free = G.pillars.findIndex(p => !p.alive());
        if (free >= 0) spawnPillar(G, boss, free);
      }
    }
    if (G.scene === 1 && G.score[0] >= 180) {
      G.requestDialog(110);
      G.scene++;
      for (const m of G.monsters) if (m.kind === 6 && m.alive()) m.die(false, G);
    }
    if (G.scene !== 2) return;
    if (!inView(G, boss)) return;
    boss.die(true, G);
    onBossDeath(G, boss);
  }

  // level 32: the maw. Kill the two tentacles, then get above his mouth line and
  // he swallows you (dialog 118) — which is what actually finishes him.
  function level32(G) {
    const boss = G.monsters.find(m => m.kind === 5);
    if (!boss) return;
    if (G.scene === 0) {                        // he throws up the whole nest at once
      if (G.pillars) for (let i = 0; i < G.pillars.length - 1; i++) spawnPillar(G, boss, i);
      G.scene = 1;
    }
    // ae.a() is "dead": the gate is the first two tentacles being killed
    const guards = (G.pillars || []).slice(0, 2);
    const guardsDown = guards.length >= 2 && guards.every(p => !p.alive());
    const human = G.players.find(p => !p.ai);
    if (!human || human.dead) return;
    const c = human.body.centroid();
    if (boss.alive() && guardsDown && c.y < boss.p.y + boss.K.r) {
      G.requestDialog(118);
      boss.die(true, G);
      onBossDeath(G, boss);
      return;
    }
    // a surviving tentacle in your column throws you clear of the mouth
    if (guardsDown) {
      for (const p of (G.pillars || [])) {
        if (!p.alive()) continue;
        if (tile(c.x) === tile(p.p.x) && tile(c.y) >= tile(p.p.y)) {
          for (const q of human.body.pts) q.applyForce(0, -5000);
          break;
        }
      }
    }
  }

  // level 34: Hera's death (after 100 ticks) triggers the closing dialogs
  function level34(G) {
    const rival = G.players.find(p => p.ai);
    const human = G.players.find(p => !p.ai);
    if (!rival || !human) return;
    if (rival.dead && rival.deadTicks >= 100 && !human.dead && !human.exitTouch && !G.heraMourned) {
      G.heraMourned = true;
      G.requestDialog(129);                     // chains 129 -> 130 -> scene++
    }
  }

  function inView(G, m) {
    const x = (m.p.x >> 10), y = (m.p.y >> 10);
    return x > G.camX - 64 && x < G.camX + View.w + 64 &&
           y > G.camY - 64 && y < G.camY + View.h + 64;
  }

  // tower-boss death rewrites the map (ae.java b()): level 28 clears the three
  // tiles beneath it, level 32 writes a tile-69 column two rows down.
  function onBossDeath(G, m) {
    if (m.kind === 5) {
      const cx = tile(m.p.x), cy = tile(m.p.y);
      if (G.levelId === 28) {
        for (const x of [cx - 1, cx, cx + 1]) {
          if (x < 0 || x >= G.world.w || cy + 1 >= G.world.h) continue;
          G.lvl.tiles[1][x][cy + 1] = -1;
          G.world.retile(x, cy + 1);
        }
      } else if (G.levelId === 32) {
        if (cx >= 0 && cx < G.world.w && cy + 2 < G.world.h) {
          G.lvl.tiles[1][cx][cy + 2] = 69;
          G.world.retile(cx, cy + 2);
        }
      }
    }
    G.bossDead = true;
    // the original walks the death dialog chain up to scene 6; reaching it here
    // keeps the exit gate identical without depending on optional hint tiles.
    if ([20, 23, 28, 32].includes(G.levelId) && G.scene < 6) G.scene = 6;
  }

  // exit override (d.a(): boss levels ignore the exit tile and use the scene)
  function exitOverride(G) {
    if ([20, 23, 28, 32].includes(G.levelId)) return G.scene >= 6;
    if (G.levelId === 34) return G.scene >= 2;
    return null;                                // not a boss level: use tile 13
  }

  return { aiFor, waypointAI, chaseAI, duelAI, tick, exitOverride, onBossDeath };
})();
