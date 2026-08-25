// Entities — player blob (class d), monsters (ae), boxes/planks/ball (ax),
// moving platforms (ac), buttons (u), ropes, loose particles (as).

// ---- loose particle (class as) ----
class Particle extends Pt {
  constructor(x, y, mass, r, kind) {
    super(x, y, mass);
    this.r = r;           // radius, fixed
    this.kind = kind;     // 0 blood, 1 debris, 5 box gib, 6 player remains, 7 monster body
    this.ttl = -1;
    this.frict = 102;     // ~0.1
    this.owner = null;
  }
}

// ---- player / AI blob (class d) ----
class Player {
  constructor(world, x, y, variant, skin) {
    this.world = world;
    this.variant = variant | 0;    // 0 player, 1..3 AI sizes
    this.skin = skin | 0;          // 0 black, 1 dark, 2 gray AI
    const n = variant === 1 ? 14 : 18;
    const r = variant === 1 ? 20 : variant === 2 ? 26 : variant === 3 ? 30 : 24;
    this.radius = r;
    const b = new Body();
    b.buildRing(x, y, r, n, 1024);
    b.flags = BF.AREA | BF.CANGRAB;
    b.iterations = 2;
    b.type = 1;
    b.owner = this;
    this.body = b;
    world.bodies.push(b);
    this.health = 102400;
    this.ability = 0;              // 0 normal, 1 slick, 2 sticky
    this.keys = { up: false, down: false, left: false, right: false, attack: false };
    this.inWater = false;
    this.deadWater = false;
    this.attackWindow = 0;
    this.attackTargets = [];       // [body, ticksLeft]
    this.climb = 0;
    this.dead = 0;                 // 0 alive, 1 dissolving, ticks count up
    this.deadTicks = 0;
    this.hurtFlash = 0;
    this.jumpHeld = false;
    this.gaze = 0; this.gazeTarget = 0;
    this.blink = 0; this.yawn = 0;
    this.exitTouch = false;
    this.ai = null;                // AI controller closure
    this.remains = [];             // particles when dead (coop revival)
    this.spawnX = x; this.spawnY = y;
  }

  setAbility(a) {
    if (this.ability === 2 && a !== 2) this.body.clearGrabs();
    this.ability = a;
    this.body.friction = a === 1 ? 0 : 1024;
    if (a === 2) this.body.flags |= BF.STICKY; else this.body.flags &= ~BF.STICKY;
  }
  cycleAbility() { this.setAbility(this.ability === 0 ? 2 : this.ability === 2 ? 1 : 0); }

  damage(dmg) {
    if (this.dead) return;
    this.hurtFlash = 2;
    this.health -= dmg;
    if (this.health <= 0) this.die();
  }

  die() {
    if (this.dead) return;
    this.dead = 1; this.deadTicks = 0;
    Assets.play('squish');
    // dissolve into particles
    for (const p of this.body.pts) {
      const g = new Particle(p.x, p.y, 2048, 4096, 6);
      g.px = p.px; g.py = p.py;
      g.owner = this;
      this.world.particles.push(g);
      this.remains.push(g);
    }
    this.body.flags |= BF.DEAD;
    const i = this.world.bodies.indexOf(this.body);
    if (i >= 0) this.world.bodies.splice(i, 1);
  }

  tick(game) {
    if (this.dead) { this.deadTicks++; return; }
    const b = this.body;
    if (this.ai) this.ai(this, game);
    const attached = this.ability === 2 && b.isAttached();

    // water scan (layer 2: 6/7 water, 36 acid)
    const bb = b.bbox();
    let waterRow = -1;
    this.deadWater = false;
    const t2 = this.world.lvl.tiles[2];
    for (let cx = Math.max(0, bb.x0 >> 15); cx <= Math.min(this.world.w - 1, bb.x1 >> 15); cx++)
      for (let cy = Math.max(0, bb.y0 >> 15); cy <= Math.min(this.world.h - 1, bb.y1 >> 15); cy++) {
        const id = t2[cx][cy];
        if (id === 6 || id === 7 || id === 36) {
          if (waterRow === -1 || cy < waterRow) waterRow = cy;
          if (id === 36) this.deadWater = true;
        }
      }
    const wasWater = this.inWater;
    this.inWater = waterRow !== -1;
    if (this.inWater) {
      b.buoyancy(waterRow << 15, 200, 5, false);
      if (!wasWater) {
        const v = b.avgVel();
        if (v.x * v.x + v.y * v.y > 10240 * 10) { Assets.play('splash'); game.effect((bb.x0 + bb.x1) >> 1, waterRow << 15, 2); }
      }
      if (this.deadWater) this.damage(2048);
    }

    // horizontal movement
    const k = this.keys;
    let fx = 0;
    const hforce = attached ? 150 : (this.inWater ? 100 : 50);
    if (k.left) fx -= hforce;
    if (k.right) fx += hforce;

    // vertical
    let fy = 0;
    const cn = b.contactNormal();
    const grounded = cn.n > 0 && Math.abs(cn.x) < cn.y && cn.y > 0;
    if (k.up) {
      if (grounded && !this.jumpHeld) { this.jump(cn); this.jumpHeld = true; }
      else if (!grounded && !this.inWater) fy -= 100;
      else if (this.inWater) fy -= 100;
    } else this.jumpHeld = false;
    if (k.down) fy += this.inWater ? 400 : 500;   // heavy slam

    if (fx || fy) {
      const scale = this.ai ? (this.variant >= 2 ? 1331 : 1945) : 1024;
      fx = (fx * scale / 1024) | 0; fy = (fy * scale / 1024) | 0;
      for (const p of b.pts) if (!(p.flags & 5)) p.applyForce(fx, fy);
    }

    // sticky peel: while moving, release grabs stretched past 4 px so Gish can crawl/climb
    const dirPeel = (k.right ? 1 : 0) - (k.left ? 1 : 0) || (k.up ? 1 : 0) || (k.down ? 1 : 0);
    if (attached && dirPeel) {
      for (let i = b.grabs.length - 1; i >= 0; i--) {
        const g = b.grabs[i];
        const q = g.otherPos();
        if (FX.fastLen(g.p1.x - q.x, g.p1.y - q.y) > 4096) {
          g.p1.flags &= ~0xC;
          b.grabs.splice(i, 1);
        }
      }
    }

    // rolling torque (kept while sticky so Gish rolls up walls)
    const dir = (k.right ? 1 : 0) - (k.left ? 1 : 0);
    if (dir) {
      const mag = dir * (this.inWater ? 200 : 300);
      const c = b.centroid();
      for (const p of b.pts) {
        const rx = p.x - c.x, ry = p.y - c.y;
        const l = FX.fastLen(ry, rx) || 1;
        const t = (mag * 1024 / l) | 0;
        p.addForce((-ry * t) >> 10, (rx * t) >> 10);
      }
    }

    // climb assist from tile hints
    if (this.climb) {
      if (this.climb & 0x82) b.applyToAll(150, 0);
      if (this.climb & 0x44) b.applyToAll(-150, 0);
      if (this.climb & 0xC0) b.applyToAll(0, -350);
      if (this.climb & 0x08) b.applyToAll(0, -700);
      this.climb = 0;
    }

    // attack
    this.attackTargets = this.attackTargets.filter(t => --t[1] > 0 && !(t[0].flags & BF.DEAD));
    if (this.attackWindow > -60) this.attackWindow--;
    if (k.attack) {
      k.attack = false;
      if (this.attackWindow < -20) {
        this.attackWindow = 56;
        Assets.play('gishhit');
        const c = b.centroid();
        for (const [tb] of this.attackTargets) {
          const tc = tb.centroid();
          let dx = tc.x - c.x, dy = tc.y - c.y;
          const l = FX.fastLen(dx, dy) || 1;
          dx = (dx * 1024 / l) | 0; dy = (dy * 1024 / l) | 0;
          const isPlayer = tb.type === 1;
          const power = isPlayer ? 2800 : tb.type === 3 ? 11400 : 5700;
          for (const p of tb.pts) p.applyForce((dx * power) >> 10, (dy * power) >> 10);
          const rec = isPlayer ? 1024 : tb.type === 3 ? 256 : 512;
          for (const p of b.pts) p.applyForce((-dx * rec) >> 10, (-dy * rec) >> 10);
        }
      }
    }

    // face animation
    this.gaze += this.angDiff(this.gazeTarget, this.gaze) / 6 | 0;
    if (this.hurtFlash > 0) this.hurtFlash--;
    if (this.blink > 0) this.blink--;
    else if (FX.rnd(0, 60) === 0) this.blink = 4;

    // crush death
    if (Math.abs(b.area()) < b.restArea / 2 && game.levelId !== 32) this.damage(102400);
  }

  angDiff(a, b) {
    let d = FX.norm(a - b);
    if (d > FX.HALF) d -= FX.PERIOD;
    return d;
  }

  jump(cn) {
    const b = this.body;
    let min = Infinity, max = -Infinity;
    const proj = [];
    for (let i = 0; i < b.pts.length; i++) {
      const v = -b.pts[i].y >> 10;
      proj[i] = v;
      if (v < min) min = v; if (v > max) max = v;
    }
    const range = max - min;
    let contacts = 0;
    for (const p of b.pts) if (p.flags & 1) contacts++;
    if (!range || !contacts) return;
    const scale = (25000 * ((contacts << 20) / (range * b.pts.length) | 0) / 1024) | 0;
    for (let i = 0; i < b.pts.length; i++) {
      const p = b.pts[i];
      if (p.flags & 1) continue;
      p.fx += 0;
      p.fy += -((scale * (proj[i] - min)) >> 10);
    }
    Assets.play('gishhit');
    if (this.ability === 2) this.body.clearGrabs();
  }
}

// ---- monster (class ae) ----
const MON = {
  // per kind: sprite group (add 256), radius, mass, chase², attack², speed, dmg
  kinds: [
    { spr: [279, 280, 288, 293], r: 12288, mass: 6144, chase: 0x300000 * 1024, atk: 0x1E6666 * 4, speed: 500, dmg: 10240 },
    { spr: [218, 219, 230, 234, 227], r: 13312, mass: 16384, chase: 0x300000 * 1024, atk: 0x1E6666 * 4, speed: 400, dmg: 20480 },
    { spr: [238, -1, 239, 242, 248], r: 15360, mass: 1000000, chase: 0, atk: 0, speed: 0, dmg: 0 },
    null,
    { spr: [251, -1, 256, 252, 304], r: 39936, mass: 1000000, chase: 1e14, atk: 0, speed: 2764, dmg: 2560 },
    { spr: [297, 306], r: 45056, mass: 1000000, chase: 0, atk: 0, speed: 0, dmg: 512 },
    { spr: [300], r: 13312, mass: 1000000, chase: 0, atk: 0, speed: 0, dmg: 512 },
  ],
};
class Monster {
  constructor(world, x, y, kind) {
    this.world = world;
    this.kind = kind;
    const K = MON.kinds[kind] || MON.kinds[0];
    this.K = K;
    this.p = new Particle(x, y, K.mass, K.r, 7);
    this.p.owner = this;
    this.p.frict = 102;
    world.particles.push(this.p);
    if (kind === 1) {   // hanger: head + body
      this.p2 = new Particle(x, y + 32768, K.mass, 14336, 7);
      this.p2.owner = this;
      world.particles.push(this.p2);
    }
    this.dir = FX.rnd(0, 1) ? 1 : -1;
    this.anim = 0; this.frame = 0; this.ftick = 0;
    this.state = 0;     // 0 walk/idle, 1 dying, 2 dead, 3 turn, 4 attack, 5 squished
    this.hp = kind === 5 ? 200 : 1;
    this.atkCool = 0;
    this.spawnCool = 40;
    this.extend = 0;    // type 6 pillar
    this.extendDir = 1;
  }
  alive() { return this.state < 1; }
  die(byPlayer, game) {
    if (!this.alive()) return;
    this.state = 1; this.frame = 0;
    Assets.play(this.kind === 1 ? 'necksnap' : 'squish');
    if (byPlayer) { game.addScore(30); game.effect(this.p.x, this.p.y, 0); }
    this.p.flags |= 0x10;
    if (this.p2) this.p2.flags |= 0x10;
  }
  tick(game) {
    if (!this.alive()) { this.frame++; return; }
    const K = this.K;
    const players = game.players.filter(pl => !pl.dead);
    let target = null, bestD = Infinity;
    for (const pl of players) {
      const c = pl.body.centroid();
      const dx = c.x - this.p.x, dy = c.y - this.p.y;
      const d = dx * dx + dy * dy;
      if (d < bestD) { bestD = d; target = { c, d }; }
    }
    switch (this.kind) {
      case 0: case 1: {
        if (target && target.d < K.chase) {
          const dir = target.c.x > this.p.x ? 1 : -1;
          if (dir !== this.dir) { this.dir = dir; this.state = 0; }
          this.p.addForce(dir * K.speed, 0);
          if (target.d < K.atk * 1024) {
            if (--this.atkCool <= 0) {
              this.atkCool = 24;
              Assets.play('bobattack');
              const pl = players[0];
              for (const q of players) {
                const c = q.body.centroid();
                const dx = c.x - this.p.x, dy = c.y - this.p.y;
                if (dx * dx + dy * dy < K.atk * 1024) q.damage(K.dmg);
              }
            }
          }
        } else if (FX.rnd(0, 40) === 0) this.dir = -this.dir;
        else this.p.addForce(this.dir * (K.speed >> 1), 0);
        if (this.kind === 1 && this.p2) {   // keep head above body
          const mid = (this.p.x + this.p2.x) >> 1;
          this.p.x += (mid - this.p.x) >> 2; this.p2.x += (mid - this.p2.x) >> 2;
          const want = this.p2.y - 32768;
          this.p.y += (want - this.p.y) >> 2;
        }
        break;
      }
      case 2: {   // spawner
        if (--this.spawnCool <= 0) {
          this.spawnCool = 90;
          const dead = game.monsters.find(m => m.kind === 0 && !m.alive() && m.respawnable);
          if (dead) {
            dead.state = 0; dead.frame = 0;
            dead.p.flags &= ~0x10;
            dead.p.x = this.p.x; dead.p.y = this.p.y - 23 * 1024;
            dead.p.px = dead.p.x - FX.rnd(-2048, 2048); dead.p.py = dead.p.y + FX.rnd(0, 2048);
            Assets.play('visattack');
          }
        }
        break;
      }
      case 4: {   // boss walker: steered chase
        if (target) {
          const dir = target.c.x > this.p.x ? 1 : -1;
          this.dir = dir;
          this.p.addForce(dir * ((K.speed / 30) | 0) * 24, 0);
          const bb = game.players[0].body.bbox();
          if (this.p.x > bb.x0 - K.r && this.p.x < bb.x1 + K.r &&
              this.p.y > bb.y0 - K.r && this.p.y < bb.y1 + K.r)
            game.players[0].damage(K.dmg);
        }
        break;
      }
      case 5: case 6: {  // tower boss / pillar: static, AABB damage
        for (const pl of players) {
          const bb = pl.body.bbox();
          const rr = this.kind === 6 ? this.extend * 32768 + K.r : K.r;
          if (this.p.x + K.r > bb.x0 && this.p.x - K.r < bb.x1 &&
              this.p.y + rr > bb.y0 && this.p.y - K.r < bb.y1)
            pl.damage(K.dmg);
        }
        break;
      }
    }
    // animation
    if (++this.ftick >= 3) { this.ftick = 0; this.frame++; }
  }
}

// ---- box / plank / ball (class ax) ----
class Box {
  constructor(world, x, y, kind) {
    this.world = world;
    this.kind = kind;
    this.originX = x; this.originY = y;
    const K = Level.BLOCK_KINDS[kind] || Level.BLOCK_KINDS[0];
    this.K = K;
    const b = new Body();
    b.type = 3;
    b.owner = this;
    b.flags = BF.EDGEFRICT;
    const mass = kind === 4 ? 51200 : 2048;
    const fx = x << 10, fy = (y + K.dy) << 10;
    if (K.ball) {
      const pts = FX.ring(16, 10);
      for (const p of pts) b.pts.push(new Pt(fx + 16384 + p.x, fy + 16384 + p.y, 2048));
      b.pressure = 100000;
      b.flags = 0;
      b.link(512, 512, -1, -1);
      b.captureRest();
    } else {
      // rectangle ring: points every 32 px along the perimeter
      const w = K.w << 10, h = K.h << 10;
      const step = 32768;
      const per = [];
      const shear = K.shear ? 1024 : 0;
      for (let px = 0; px < w; px += step) per.push([px, 0]);
      for (let py = 0; py < h; py += step) per.push([w, py]);
      for (let px = w; px > 0; px -= step) per.push([px, h]);
      for (let py = h; py > 0; py -= step) per.push([0, py]);
      for (const [px, py] of per) b.pts.push(new Pt(fx + px + ((shear * (h - py)) >> 10), fy + py, mass));
      const n = b.pts.length;
      b.link(1024, 1024, -1, -1);
      for (const s of b.springs) s.limit = (s.rest * 512) >> 10;
      // cross braces
      for (let i = 0; i < n >> 1; i++) {
        const s = new Spring(b.pts[i], b.pts[(i + (n >> 1)) % n], 1024, -1, -1);
        s.limit = (s.rest * 512) >> 10;
        b.springs.push(s);
      }
      b.captureRest();
      b.iterations = K.soft ? 2 : 1;
    }
    this.body = b;
    world.bodies.push(b);
    this.dead = false;
    this.respawnTimer = 0;
  }
  tick(game) {
    if (this.dead) {
      if (--this.respawnTimer <= 0) game.respawnBox(this);
      return;
    }
    // water buoyancy
    const b = this.body;
    const bb = b.bbox();
    const t2 = this.world.lvl.tiles[2];
    let waterRow = -1;
    for (let cx = Math.max(0, bb.x0 >> 15); cx <= Math.min(this.world.w - 1, bb.x1 >> 15); cx++)
      for (let cy = Math.max(0, bb.y0 >> 15); cy <= Math.min(this.world.h - 1, bb.y1 >> 15); cy++) {
        const id = t2[cx][cy];
        if (id === 6 || id === 7 || id === 36) { if (waterRow === -1 || cy < waterRow) waterRow = cy; }
      }
    if (waterRow !== -1) {
      const f = this.K.ball ? 600 : (this.world.lvl.theme === 0 && !this.K.ball ? 3000 : 200);
      b.buoyancy(waterRow << 15, f, 6, b.pts.length <= 4);
    }
    // check crushed
    if (b.flags & BF.DEAD) this.gib(game);
  }
  gib(game) {
    if (this.dead) return;
    this.dead = true;
    this.respawnTimer = 60;
    Assets.play('squish');
    game.effect(...(() => { const c = this.body.centroid(); return [c.x, c.y, 0]; })());
    for (const p of this.body.pts) {
      const g = new Particle(p.x, p.y, 2048, 4096, 5);
      g.px = p.px; g.py = p.py;
      g.ttl = 40;
      this.world.particles.push(g);
    }
    const i = this.world.bodies.indexOf(this.body);
    if (i >= 0) this.world.bodies.splice(i, 1);
  }
}

// ---- moving platform (ac) + button (u) ----
class Platform {
  constructor(world, def) {
    this.world = world;
    this.def = def;
    const K = Level.PLAT_KINDS[def.kind] || Level.PLAT_KINDS[0];
    this.K = K;
    this.ax = def.ax << 10; this.ay = def.ay << 10;
    this.bx = def.bx << 10; this.by = def.by << 10;
    this.x = this.ax; this.y = this.ay;
    this.px = this.x; this.py = this.y;
    const dist = FX.fastLen(this.bx - this.ax, this.by - this.ay);
    this.period = Math.max(1, (dist / (def.speed * 500)) | 0);
    this.t = 0;
    this.active = def.act === 0;
    this.oneShot = def.act === 5 || def.act === 2;
    this.mode = def.act;
    this.dirHeld = 1;
    this.anchors = [];       // grab-spring anchors riding this platform
    this.button = def.btnCol !== undefined ? { col: def.btnCol, row: def.btnRow, pressed: 0, wasPressed: false } : null;
    if (def.act === 5) this.active = true;
  }
  polyAt() {
    const w = this.K.w << 10, h = this.K.h << 10;
    return { x0: this.x, y0: this.y, x1: this.x + w, y1: this.y + h };
  }
  tick() {
    this.px = this.x; this.py = this.y;
    if (this.active || (this.mode === 4)) {
      if (this.mode === 4) this.t += this.active ? 1 : -1;
      else this.t++;
      if (this.t < 0) this.t = 0;
      let phase;
      if (this.oneShot) {
        phase = Math.min(this.t, this.period);
        if (this.t >= this.period && this.mode !== 4) this.active = false;
      } else {
        const m = this.t % (2 * this.period);
        phase = m < this.period ? m : 2 * this.period - m;
      }
      this.x = this.ax + (((this.bx - this.ax) * phase / this.period) | 0);
      this.y = this.ay + (((this.by - this.ay) * phase / this.period) | 0);
    }
    const dx = this.x - this.px, dy = this.y - this.py;
    if (dx || dy) for (const a of this.anchors) { a.x += dx; a.y += dy; }
  }
  // collide a body against the platform rectangle
  collide(body) {
    const w = this.K.w << 10, h = this.K.h << 10;
    for (const p of body.pts) {
      if (p.x <= this.x || p.x >= this.x + w || p.y <= this.y || p.y >= this.y + h) continue;
      // push out along least penetration
      const dl = p.x - this.x, dr = this.x + w - p.x, dt = p.y - this.y, db = this.y + h - p.y;
      const m = Math.min(dl, dr, dt, db);
      if (m === dt) p.y = this.y;
      else if (m === db) p.y = this.y + h;
      else if (m === dl) p.x = this.x;
      else p.x = this.x + w;
      // inherit platform motion + friction
      const vx = this.x - this.px, vy = this.y - this.py;
      if (body.friction >= 1024) { p.px = p.x - vx; p.py = p.y - vy; }
      p.flags |= 0x43;
      body.stepBits |= 7;
      if ((body.flags & BF.STICKY) && !(p.flags & 4)) {
        const anchor = { x: p.x, y: p.y };
        this.anchors.push(anchor);
        body.grabs.push(new Spring(p, anchor, 512, 5120, 0));
        p.flags |= 0xC;
      }
    }
  }
  press(held) {
    switch (this.mode) {
      case 1: if (held && !this.button.wasPressed) this.active = true; break;
      case 2: if (held && !this.button.wasPressed) { this.active = true; this.oneShot = true; } break;
      case 3: this.active = held; break;
      case 4: this.active = held; break;
    }
    if (held && !this.button.wasPressed) Assets.play('switch');
    this.button.wasPressed = held;
  }
}

// ---- ropes ----
function buildRopes(world, lvl, boxes) {
  for (const rope of lvl.ropes) {
    const mat = Level.ROPE_MAT[rope.material] || Level.ROPE_MAT[0];
    const stiff = (mat.stiff * 1024) | 0;
    const limit = mat.breakLen ? mat.breakLen << 10 : -1;
    const restMode = mat.rest > 1 ? -1536 : -1;
    const nodes = [];
    for (let i = 0; i < rope.pts.length; i++) {
      const rp = rope.pts[i];
      const wx = rp.x << 10, wy = rp.y << 10;
      const isEnd = i === 0 || i === rope.pts.length - 1;
      let node = null;
      if (isEnd || true) {
        // try attach to a nearby box ring point (within 8 px)
        let best = null, bestD = 8192;
        for (const bx of boxes) {
          if (bx.dead) continue;
          for (const p of bx.body.pts) {
            const d = FX.fastLen(p.x - wx, p.y - wy);
            if (d < bestD) { bestD = d; best = p; }
          }
        }
        if (best) node = best;
      }
      if (!node) {
        if (isEnd) { node = new Pt(wx, wy, PINNED); }
        else { node = new Pt(wx, wy, 1024); node.flags |= 0x20; world.ropePts.push(node); }
      }
      nodes.push(node);
    }
    for (let i = 0; i + 1 < nodes.length; i++) {
      const s = new Spring(nodes[i], nodes[i + 1], stiff, -1, restMode);
      if (limit > 0) s.limit = limit;
      else if (mat.rest > 1) s.rest = (s.rest * 1536) >> 10;
      if (mat.pullOnly) s.type = 1;
      world.ropes.push(s);
    }
  }
}
