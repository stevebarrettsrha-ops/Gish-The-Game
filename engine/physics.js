// Physics core — faithful port of the MIDlet's fixed-point verlet engine
// (classes x, ag, h, af, al, Main.e/Main.a collision tables).
// Units: 1024 = 1 px. Tile = 1<<15. Angle period 6588397 = 2*pi*2^20.

const FX = (() => {
  const PERIOD = 6588397, HALF = 3294198, QUARTER = 1647099;
  // 256-entry quarter-wave sine table, values 0..1024 (al.a[])
  const SIN = new Int32Array(256);
  for (let i = 0; i < 256; i++) SIN[i] = Math.round(Math.sin(i / 255 * Math.PI / 2) * 1024);

  const norm = a => { a %= PERIOD; if (a < 0) a += PERIOD; return a; };

  // vector from angle+length (bg(angle,len) — y down-positive)
  function vecFromAngle(angle, len) {
    let a = norm(angle), my = false, mx = false;
    if (a > HALF) { a = PERIOD - a; my = true; }
    if (a > QUARTER) { a = HALF - a; mx = true; }
    const i = Math.min(255, Math.floor(a * 255 / QUARTER));
    let x = SIN[255 - i], y = SIN[i];
    if (mx) x = -x;
    if (my) y = -y;
    return { x: (x * len) >> 10, y: (y * len) >> 10 };
  }

  function angleOf(x, y) {  // atan2 in PERIOD units
    return norm(Math.round(Math.atan2(y, x) / (2 * Math.PI) * PERIOD));
  }

  // fast magnitude (al.a(x,y)) — replicated exactly, ~±4% error is part of the game feel
  function fastLen(x, y) {
    let a = Math.abs(x), b = Math.abs(y);
    if (a < b) { const t = a; a = b; b = t; }
    b += b >> 1;
    return a - (a >> 5) - (a >> 7) + (b >> 2) + (b >> 6);
  }

  const rnd = (min, max) => min + Math.floor(Math.random() * (max - min + 1));

  // ring of n points at radius r px (al.a(r,n))
  function ring(r, n) {
    const pts = [];
    for (let i = 0; i < n; i++) pts.push(vecFromAngle(Math.floor(i * PERIOD / n), r << 10));
    return pts;
  }

  const frame16 = angle => (norm(angle + 205887) * 16 / PERIOD) | 0;
  const frame32 = angle => (norm(angle + 102943) * 32 / PERIOD) | 0;

  return { PERIOD, HALF, QUARTER, norm, vecFromAngle, angleOf, fastLen, rnd, ring, frame16, frame32 };
})();

// ---- point mass (class x) ----
const PINNED = 0x7fffffff;
class Pt {
  constructor(x, y, mass) {
    this.x = x; this.y = y; this.px = x; this.py = y;
    this.fx = 0; this.fy = 0;
    this.mass = mass || 1024;
    this.flags = 0;
  }
  applyForce(fx, fy) {
    if (this.mass === PINNED) return;
    this.fx += (fx * this.mass / 1024) | 0;
    this.fy += (fy * this.mass / 1024) | 0;
  }
  addForce(fx, fy) { this.fx += fx | 0; this.fy += fy | 0; }  // direct (pressure/torque)
  integrate() {
    if (this.mass === PINNED) { this.px = this.x; this.py = this.y; return; }
    const tx = this.x, ty = this.y;
    this.x = 2 * this.x - this.px + (((this.fx * 1024) / this.mass) | 0);
    this.y = 2 * this.y - this.py + (((this.fy * 1024) / this.mass) | 0);
    this.px = tx; this.py = ty;
    this.fx = 0; this.fy = 0;
  }
}

// ---- spring / distance constraint (class ag) ----
// anchor forms: Pt<->Pt, Pt<->fixed {x,y}, Pt<->edge {a:Pt,b:Pt,t}
class Spring {
  constructor(p1, p2, stiff, limit, rest) {
    this.p1 = p1; this.p2 = p2;          // p2 may be {x,y} anchor or {a,b,t} edge
    this.stiff = stiff;
    this.type = 0;                        // 0 normal, 1 pull-only, 2 push-only
    const q = this.otherPos();
    const nat = FX.fastLen(p1.x - q.x, p1.y - q.y);
    this.rest = rest === -1 || rest === undefined ? nat : rest;
    this.limit = limit === undefined ? -1 : limit;
    if (this.limit < -1) this.limit = (this.rest * -this.limit) >> 10;
  }
  otherPos() {
    const p2 = this.p2;
    if (p2 instanceof Pt) return p2;
    if (p2.a) return { x: p2.a.x + (((p2.b.x - p2.a.x) * p2.t) >> 10),
                       y: p2.a.y + (((p2.b.y - p2.a.y) * p2.t) >> 10) };
    return p2;
  }
  solve(checkBreak) {
    if ((this.p1.flags & 0x10) || (this.p2 instanceof Pt && (this.p2.flags & 0x10))) return true;
    const q = this.otherPos();
    const dx = this.p1.x - q.x, dy = this.p1.y - q.y;
    const len = FX.fastLen(dx, dy);
    if (len === 0) return false;
    if (checkBreak && this.limit !== -1 &&
        ((this.limit > this.rest && len > this.limit) || (this.limit < this.rest && len < this.limit)))
      return true;
    let err = len - this.rest;
    if (this.type === 1 && err < 0) return false;
    if (this.type === 2 && err > 0) return false;
    if (this.stiff === 512) err >>= 1;
    else if (this.stiff < 1024) err = (this.stiff * err / 1024) | 0;
    const nx = (dx * 1024 / len) | 0, ny = (dy * 1024 / len) | 0;
    const m1 = this.p1.mass;
    if (!(this.p2 instanceof Pt)) {
      if (this.p2.a) {   // edge anchor: distribute (1-t)/t on edge points, half on p1? original splits fully
        const t = this.p2.t;
        const half = err >> 1;
        if (m1 !== PINNED) { this.p1.x -= (nx * half) >> 10; this.p1.y -= (ny * half) >> 10; }
        const rem = err - half;
        const ea = this.p2.a, eb = this.p2.b;
        if (ea.mass !== PINNED) { ea.x += (nx * ((rem * (1024 - t)) >> 10)) >> 10; ea.y += (ny * ((rem * (1024 - t)) >> 10)) >> 10; }
        if (eb.mass !== PINNED) { eb.x += (nx * ((rem * t) >> 10)) >> 10; eb.y += (ny * ((rem * t) >> 10)) >> 10; }
      } else if (m1 !== PINNED) {
        this.p1.x -= (nx * err) >> 10; this.p1.y -= (ny * err) >> 10;
      }
      return false;
    }
    const m2 = this.p2.mass;
    let s1, s2;
    if (m1 === PINNED) { s1 = 0; s2 = err; }
    else if (m2 === PINNED) { s1 = err; s2 = 0; }
    else if (m1 === m2) { s1 = err >> 1; s2 = err - s1; }
    else {
      s1 = (err * m2 / (m1 + m2)) | 0; s2 = err - s1;
    }
    this.p1.x -= (nx * s1) >> 10; this.p1.y -= (ny * s1) >> 10;
    this.p2.x += (nx * s2) >> 10; this.p2.y += (ny * s2) >> 10;
    return false;
  }
}

// ---- soft body (class h) ----
const BF = { STICKY: 1, EDGEFRICT: 2, AREA: 4, CANGRAB: 8, ASLEEP: 0x10, DEAD: 0x20,
             RENDERPTS: 0x40, FIXCENTER: 0x80 };
class Body {
  constructor() {
    this.pts = [];
    this.springs = [];
    this.grabs = [];
    this.extraPts = [];      // rope nodes attached to this body
    this.extraSprings = [];  // rope segments
    this.linked = [];
    this.pressure = 0;
    this.flags = 0;
    this.friction = 1024;
    this.iterations = 1;
    this.type = 0;           // 1=player, 3=box, 4=deadly
    this.restArea = 0; this.restPerim = 0;
    this.stepBits = 0;
    this.owner = null;       // back-ref to Player/Box/etc.
  }
  buildRing(cx, cy, r, n, mass) {
    const ring = FX.ring(r, n);
    for (const p of ring) this.pts.push(new Pt(cx + p.x, cy + p.y, mass));
    this.link(1024, 1024, -1, -1);
    this.captureRest();
  }
  link(stiffE, stiffO, restE, restO) {
    const n = this.pts.length;
    for (let i = 0; i < n; i += 2) this.springs.push(new Spring(this.pts[i], this.pts[(i + 1) % n], stiffE, -1, restE));
    for (let i = 1; i < n; i += 2) this.springs.push(new Spring(this.pts[i], this.pts[(i + 1) % n], stiffO, -1, restO));
  }
  captureRest() {
    this.restArea = Math.abs(this.area());
    let per = 0;
    const n = this.pts.length;
    for (let i = 0; i < n; i++) {
      const a = this.pts[i], b = this.pts[(i + 1) % n];
      per += FX.fastLen(b.x - a.x, b.y - a.y);
    }
    this.restPerim = per;
  }
  area() {  // shoelace, fixed
    let s = 0;
    const p = this.pts, n = p.length;
    for (let i = 0; i < n; i++) {
      const prev = p[(i + n - 1) % n], next = p[(i + 1) % n];
      s += (p[i].x / 32) * ((next.y - prev.y) / 32);
    }
    return (s / 2) | 0;   // area in (px/  ... consistent relative scale; used vs restArea only
  }
  centroid() {
    let x = 0, y = 0, n = 0;
    for (const p of this.pts) { if (p.flags & 0x10) continue; x += p.x; y += p.y; n++; }
    return n ? { x: (x / n) | 0, y: (y / n) | 0 } : { x: 0, y: 0 };
  }
  bbox() {
    let x0 = Infinity, y0 = Infinity, x1 = -Infinity, y1 = -Infinity;
    for (const p of this.pts) {
      if (p.x < x0) x0 = p.x; if (p.x > x1) x1 = p.x;
      if (p.y < y0) y0 = p.y; if (p.y > y1) y1 = p.y;
    }
    return { x0, y0, x1, y1 };
  }
  contactNormal() {  // average contact direction relative to centroid, negated -> outward "ground normal"
    const c = this.centroid();
    let x = 0, y = 0, n = 0, all = true;
    for (const p of this.pts) {
      if (p.flags & 0x10) continue;
      if ((p.flags & 1) || (p.flags & 4)) { x += p.x - c.x; y += p.y - c.y; n++; }
      else all = false;
    }
    if (!n) return { x: 0, y: 0, n: 0 };
    if (all) return { x: 0, y: 1024, n };
    const len = FX.fastLen(x, y) || 1;
    return { x: (x * 1024 / len) | 0, y: (y * 1024 / len) | 0, n };
  }
  applyToAll(fx, fy) { for (const p of this.pts) p.applyForce(fx, fy); }
  avgVel() {
    let x = 0, y = 0, n = 0;
    for (const p of this.pts) { x += p.x - p.px; y += p.y - p.py; n++; }
    return n ? { x: (x / n) | 0, y: (y / n) | 0 } : { x: 0, y: 0 };
  }
  clampSpeed() {  // only after a collision (stepBits&1): max 8 px/substep
    if (!(this.stepBits & 1)) return;
    for (const p of this.pts) {
      const vx = p.x - p.px, vy = p.y - p.py;
      if (vx * vx + vy * vy > 0x4000000) {
        const l = FX.fastLen(vx, vy) || 1;
        p.px = p.x - ((vx * 8192 / l) | 0);
        p.py = p.y - ((vy * 8192 / l) | 0);
      }
    }
  }
  areaPreserve() {
    if (!(this.flags & BF.AREA)) return;
    const diff = this.restArea - Math.abs(this.area());
    if (!diff || !this.restPerim) return;
    const s = ((diff * 1024) / this.restPerim) | 0;
    const p = this.pts, n = p.length;
    const px = [], py = [];
    for (let i = 0; i < n; i++) {
      const prev = p[(i + n - 1) % n], next = p[(i + 1) % n];
      // outward normal for the clockwise (y-down) ring winding
      let nx = next.y - prev.y, ny = prev.x - next.x;
      const l = FX.fastLen(nx, ny) || 1;
      px[i] = (s * nx / l) | 0; py[i] = (s * ny / l) | 0;
    }
    for (let i = 0; i < n; i++) { p[i].x += px[i]; p[i].y += py[i]; }
  }
  applyPressure() {
    if (this.pressure <= 0) return;
    const A = Math.max(Math.abs(this.area()), 32768);
    const p = this.pts, n = p.length;
    for (let i = 0; i < n; i++) {
      const prev = p[(i + n - 1) % n], cur = p[i];
      const nx = cur.y - prev.y, ny = prev.x - cur.x;
      const fx = (nx * this.pressure / A) | 0, fy = (ny * this.pressure / A) | 0;
      prev.addForce(fx, fy); cur.addForce(fx, fy);
    }
  }
  buoyancy(surfY, force, dampShift, whole) {
    const bb = this.bbox();
    if (bb.y1 <= surfY) return false;
    let frac = (((bb.y1 - surfY) * 1024) / (bb.y1 - bb.y0 || 1)) | 0;
    if (frac > 1024) frac = 1024;
    if (frac <= 0) return false;
    let f = -((force * frac) >> 9);
    const v = this.avgVel();
    f -= v.y >> dampShift;
    for (const p of this.pts)
      if (whole || p.y >= surfY) p.applyForce(0, f);
    return true;
  }
  containsPoint(x, y) {  // even-odd
    let inside = false;
    const p = this.pts, n = p.length;
    for (let i = 0, j = n - 1; i < n; j = i++) {
      if (((p[i].y > y) !== (p[j].y > y)) &&
          (x < (p[j].x - p[i].x) * (y - p[i].y) / (p[j].y - p[i].y) + p[i].x))
        inside = !inside;
    }
    return inside;
  }
  clearGrabs() {
    for (const g of this.grabs) { g.p1.flags &= ~0xC; }
    this.grabs.length = 0;
  }
  isAttached() { return this.grabs.length > 0; }
}

// ---- collision shapes (Main.e()) ----
const Shapes = (() => {
  const A = [-512, -512], B = [32768, -512], C = [-512, 32768], D = [32768, 32768];
  const E = [512, -512], F = [32256, -512], G = [-512, 32256], H = [33280, 32256];
  const add = (p, o) => [p[0] + o[0], p[1] + o[1]];
  const P = [1024, 1024], M = [-1024, 1024];
  const raw = [
    [A, B], [B, D], [D, C], [C, A],
    [A, B, D], [B, D, C], [D, C, A], [C, A, B],
    [A, B, null, D, C], [B, D, null, C, A],
    [A, B, D, C], [B, D, C, A], [D, C, A, B], [C, A, B, D],
    [A, B, D, C, A],
    [E, H], [G, F],
    [add(H, M), add(E, M)], [add(F, P), add(G, P)],
    [[C[0], 15360], [D[0], 15360]],
    [[15360, D[1]], [15360, B[1]]],
    [[B[0], 17408], [A[0], 17408]],
    [[17408, A[1]], [17408, C[1]]],
    [E, H, G], [H, G, F],
    [add(H, M), add(E, M), add(F, P)],
    [add(E, M), add(F, P), add(G, P)],
    [[A[0], 15360], A, B, [B[0], 15360], [A[0], 15360]],
    [[8192, A[1]], [24576, A[1]], [24576, D[1]], [8192, D[1]], [8192, A[1]]],
  ];
  // pre-split into segment runs, detect closed
  return raw.map(sh => {
    const runs = [];
    let cur = [];
    for (const p of sh) {
      if (p === null) { if (cur.length) runs.push(cur); cur = []; }
      else cur.push({ x: p[0], y: p[1] });
    }
    if (cur.length) runs.push(cur);
    const closed = runs.length === 1 && runs[0].length > 2 &&
      runs[0][0].x === runs[0][runs[0].length - 1].x && runs[0][0].y === runs[0][runs[0].length - 1].y;
    // per-run edges with outward normals (n = (dy, -dx)/len)
    const edges = [];
    for (const run of runs)
      for (let i = 0; i + 1 < run.length; i++) {
        const a = run[i], b = run[i + 1];
        const dx = b.x - a.x, dy = b.y - a.y;
        const l = FX.fastLen(dx, dy) || 1;
        edges.push({ a, b, nx: (dy * 1024 / l) | 0, ny: (-dx * 1024 / l) | 0,
                     tx: (dx * 1024 / l) | 0, ty: (dy * 1024 / l) | 0, len: l });
      }
    return { edges, closed };
  });
})();

// ---- physics world (class af + tile collision from Main) ----
const PASSABLE = new Set([-1, 8, 9, 13, 43, 70]);
const SPECIAL = new Set([1,2,3,4,7,10,11,12,16,17,37,38,39,40,64,65,66,67,68,71,72]);
// per-tile-id normal friction/climbable flag (k.g[73]; false = zero-friction surface)
const KG = (() => { const a = Array(73).fill(true); for (const i of [6,8,9,13,69,70]) a[i] = false; return a; })();
const BREAKABLE_ID = 14;

class World {
  constructor(lvl) {
    this.lvl = lvl;
    this.w = lvl.width; this.h = lvl.height;
    this.gravity = 200;
    this.bodies = [];
    this.particles = [];
    this.platforms = [];
    this.ropes = [];        // Spring[] world-level rope segments
    this.ropePts = [];      // free rope nodes
    this.coll = [];         // shape id per cell, -1 empty
    for (let x = 0; x < this.w; x++) this.coll.push(new Int8Array(this.h).fill(-1));
    this.tileCallback = null;   // (body, cx, cy) from game
    this.bodyCallback = null;   // (bodyA, bodyB)
    this.particleCallback = null; // (body, particle)
    this.buildCollision();
  }

  classOf(cx, cy) {
    if (cx < 0 || cy < 0 || cx >= this.w || cy >= this.h) return 0;
    const id = this.lvl.tiles[1][cx][cy];
    if (PASSABLE.has(id)) return -1;
    if (SPECIAL.has(id)) return 1;
    return 0;
  }

  buildCollision() {
    for (let x = 0; x < this.w; x++)
      for (let y = 0; y < this.h; y++)
        this.assign(x, y);
  }

  // Main.a(cx,cy) — collision shape assignment + background filler bookkeeping
  assign(cx, cy) {
    const t = this.lvl.tiles;
    const cls = this.classOf(cx, cy);
    const id = t[1][cx][cy];
    if (cls === 0 && id !== 69) {
      if (id !== BREAKABLE_ID) t[0][cx][cy] = -1;      // bg hidden behind opaque terrain
    } else {
      if (t[0][cx][cy] === -1) t[0][cx][cy] = 11;      // darkness filler
      if (this.lvl.theme !== 0 && t[0][cx][cy] === 11) t[0][cx][cy] = 51;
    }
    if (cls === -1) { this.coll[cx][cy] = -1; return; }
    if (cls === 1) {
      let s = -1;
      if ([1,16,37,65].includes(id)) s = this.classOf(cx, cy + 1) !== 0 ? 23 : 15;
      else if ([2,17,38,64].includes(id)) s = this.classOf(cx, cy + 1) !== 0 ? 24 : 16;
      else if ([3,39,66].includes(id)) s = this.classOf(cx, cy - 1) !== 0 ? 25 : 17;
      else if ([4,40,67].includes(id)) s = this.classOf(cx, cy - 1) !== 0 ? 26 : 18;
      else if (id === 7 || id === 68) s = 19;
      else if (id === 71) s = 20;
      else if (id === 72) s = 22;
      else if (id === 10 || id === 11 || id === 12) s = 10 + id;
      else if (id === 69) s = 28;
      this.coll[cx][cy] = s;
      return;
    }
    // class 0: neighbor-based edges. L, R, U, D classes:
    const L = this.classOf(cx - 1, cy), R = this.classOf(cx + 1, cy);
    const U = this.classOf(cx, cy - 1), Dn = this.classOf(cx, cy + 1);
    const UL = () => this.classOf(cx - 1, cy - 1), UR = () => this.classOf(cx + 1, cy - 1);
    const DR = () => this.classOf(cx + 1, cy + 1), DL = () => this.classOf(cx - 1, cy + 1);
    const nSolid = (L === 0) + (R === 0) + (U === 0) + (Dn === 0);
    let s;
    switch (nSolid) {
      case 4: {
        const ul = UL(), ur = UR(), dr = DR(), dl = DL();
        const nPass = (ul === -1) + (ur === -1) + (dr === -1) + (dl === -1);
        if (nPass >= 3) { s = 14; break; }
        if (ul === -1 && ur === -1) s = 13;
        else if (ur === -1 && dr === -1) s = 10;
        else if (dr === -1 && dl === -1) s = 11;
        else if (dl === -1 && ul === -1) s = 12;
        else if ((ul === -1 && dr === -1) || (ur === -1 && dl === -1)) s = 14;
        else if (ul === -1) s = 7;
        else if (ur === -1) s = 4;
        else if (dr === -1) s = 5;
        else if (dl === -1) s = 6;
        else s = -1;
        break;
      }
      case 3: {
        if (U !== 0) {           // top exposed
          const dr = DR(), dl = DL();
          if (dl === -1 && dr === -1) s = 14;
          else if (dl === -1) s = 12;
          else if (dr === -1) s = 10;
          else s = 0;
        } else if (R !== 0) {    // right exposed
          const ul = UL(), dl = DL();
          if (ul === -1 && dl === -1) s = 14;
          else if (ul === -1) s = 13;
          else if (dl === -1) s = 11;
          else s = 1;
        } else if (Dn !== 0) {   // bottom exposed
          const ur = UR(), ul = UL();
          if (ur === -1 && ul === -1) s = 14;
          else if (ur === -1) s = 10;
          else if (ul === -1) s = 12;
          else s = 2;
        } else {                 // left exposed
          const ur = UR(), dr = DR();
          if (ur === -1 && dr === -1) s = 14;
          else if (ur === -1) s = 13;
          else if (dr === -1) s = 11;
          else s = 3;
        }
        break;
      }
      case 2: {
        if (U !== 0 && R !== 0) s = DL() === -1 ? 14 : 4;
        else if (R !== 0 && Dn !== 0) s = UL() === -1 ? 14 : 5;
        else if (Dn !== 0 && L !== 0) s = UR() === -1 ? 14 : 6;
        else if (L !== 0 && U !== 0) s = DR() === -1 ? 14 : 7;
        else if (U !== 0 && Dn !== 0) s = 8;
        else s = 9;
        break;
      }
      case 1: {
        if (L === 0) s = 10;
        else if (U === 0) s = 11;
        else if (R === 0) s = 12;
        else s = 13;
        break;
      }
      default: s = 14;
    }
    this.coll[cx][cy] = s;
  }

  // recompute shapes around a destroyed tile
  retile(cx, cy) {
    for (let x = cx - 1; x <= cx + 1; x++)
      for (let y = cy - 1; y <= cy + 1; y++)
        if (x >= 0 && y >= 0 && x < this.w && y < this.h) this.assign(x, y);
  }

  // ---- point vs tile shape (af.a) ----
  collidePoint(p, body, friction) {
    const cx = p.x >> 15, cy = p.y >> 15;
    let hit = false;
    // out of bounds: resolve against full box of clamped cell
    if (cx < 0) { p.x = 0; hit = true; }
    else if (cx >= this.w) { p.x = this.w << 15; hit = true; }
    if (cy < 0) { p.y = 0; hit = true; }
    else if (cy >= this.h) { p.y = this.h << 15; hit = true; }
    if (!hit) {
      const s = this.coll[cx][cy];
      if (s !== -1) hit = this.resolveShape(p, cx << 15, cy << 15, Shapes[s], friction, body, cx, cy);
    } else this.applyFriction(p, friction);
    return hit;
  }

  resolveShape(p, ox, oy, shape, friction, body, cx, cy) {
    const lx = p.x - ox, ly = p.y - oy;
    if (shape.closed) {
      let minDepth = Infinity, best = null;
      for (const e of shape.edges) {
        const d = ((lx - e.a.x) * e.nx + (ly - e.a.y) * e.ny) >> 10;
        if (d > 0) return false;            // outside a half-plane
        if (-d < minDepth) { minDepth = -d; best = e; }
      }
      if (!best) return false;
      p.x += (best.nx * minDepth) >> 10;
      p.y += (best.ny * minDepth) >> 10;
      this.applyFriction(p, friction);
      this.onTileContact(p, body, cx, cy);
      return true;
    }
    // open polyline: nearest violated segment
    let hit = false;
    for (const e of shape.edges) {
      const rx = lx - e.a.x, ry = ly - e.a.y;
      let t = ((rx * e.tx + ry * e.ty) >> 10);
      if (t < 0) t = 0; else if (t > e.len) t = e.len;
      const qx = e.a.x + ((e.tx * t) >> 10), qy = e.a.y + ((e.ty * t) >> 10);
      const s = ((lx - qx) * e.nx + (ly - qy) * e.ny) >> 10;
      if (s < 0 && s > -16384) {
        p.x -= (e.nx * s) >> 10;
        p.y -= (e.ny * s) >> 10;
        this.applyFriction(p, friction);
        hit = true;
      }
    }
    if (hit) this.onTileContact(p, body, cx, cy);
    return hit;
  }

  applyFriction(p, friction) {
    if (friction >= 1024) { p.px = p.x; p.py = p.y; }
    else if (friction > 0) {
      p.px += (((p.x - p.px) * friction) >> 10);
      p.py += (((p.y - p.py) * friction) >> 10);
    }
  }

  onTileContact(p, body, cx, cy) {
    p.flags |= 3;
    if (body) {
      body.stepBits |= 7;
      if ((body.flags & BF.STICKY) && !(p.flags & 4) && body.grabs.length < body.pts.length * 2) {
        body.grabs.push(new Spring(p, { x: p.x, y: p.y }, 204, 10240, 0));
        p.flags |= 0xC;
      }
      if (this.tileCallback && cx !== undefined) this.tileCallback(body, cx, cy);
    }
  }

  // ring-edge vs tile shapes: approximate by clipping shape edges against body edges
  collideBodyEdges(body, friction) {
    const pts = body.pts, n = pts.length;
    for (let i = 0; i < n; i++) {
      const p1 = pts[i], p2 = pts[(i + 1) % n];
      const x0 = Math.min(p1.x, p2.x) >> 15, x1 = Math.max(p1.x, p2.x) >> 15;
      const y0 = Math.min(p1.y, p2.y) >> 15, y1 = Math.max(p1.y, p2.y) >> 15;
      for (let cx = x0; cx <= x1; cx++)
        for (let cy = y0; cy <= y1; cy++) {
          if (cx < 0 || cy < 0 || cx >= this.w || cy >= this.h) continue;
          const s = this.coll[cx][cy];
          if (s === -1) continue;
          const ox = cx << 15, oy = cy << 15;
          for (const e of Shapes[s].edges) {
            const s1 = (((p1.x - ox - e.a.x) * e.nx + (p1.y - oy - e.a.y) * e.ny)) >> 10;
            const s2 = (((p2.x - ox - e.a.x) * e.nx + (p2.y - oy - e.a.y) * e.ny)) >> 10;
            if (s1 >= 0 && s2 >= 0) continue;
            if (s1 < -13000 && s2 < -13000) continue;
            // overlap of body edge with shape segment along tangent
            const t1 = ((p1.x - ox - e.a.x) * e.tx + (p1.y - oy - e.a.y) * e.ty) >> 10;
            const t2 = ((p2.x - ox - e.a.x) * e.tx + (p2.y - oy - e.a.y) * e.ty) >> 10;
            if (Math.max(t1, t2) < 0 || Math.min(t1, t2) > e.len) continue;
            if (s1 < 0) { p1.x -= (e.nx * s1) >> 10; p1.y -= (e.ny * s1) >> 10; this.applyFriction(p1, friction); p1.flags |= 3; body.stepBits |= 7; }
            if (s2 < 0) { p2.x -= (e.nx * s2) >> 10; p2.y -= (e.ny * s2) >> 10; this.applyFriction(p2, friction); p2.flags |= 3; body.stepBits |= 7; }
          }
        }
    }
  }

  collideBodyTiles(body) {
    const cells = [];
    for (const p of body.pts) {
      if (p.flags & 0x10) continue;
      const cx0 = p.x >> 15, cy0 = p.y >> 15;
      const tileId = (cx0 >= 0 && cy0 >= 0 && cx0 < this.w && cy0 < this.h) ? this.lvl.tiles[1][cx0][cy0] : 0;
      const fr = (tileId >= 0 && tileId < KG.length && !KG[tileId]) ? 0 : body.friction;
      if (this.collidePoint(p, body, fr)) {
        if (cells.length < 15) cells.push([p.x >> 15, p.y >> 15]);
      }
    }
    this.collideBodyEdges(body, body.friction);
    return cells;
  }

  // body-body vertex-in-polygon resolution (both directions)
  collideBodies(a, b) {
    const ba = a.bbox(), bb = b.bbox();
    if (ba.x1 < bb.x0 || bb.x1 < ba.x0 || ba.y1 < bb.y0 || bb.y1 < ba.y0) return;
    let touched = false;
    for (const [A, B2] of [[a, b], [b, a]]) {
      for (const v of A.pts) {
        if (v.flags & 0x10) continue;
        if (!B2.containsPoint(v.x, v.y)) continue;
        touched = true;
        // nearest edge of B2
        let best = null, bestD = Infinity, bestT = 0;
        const pts = B2.pts, n = pts.length;
        for (let i = 0; i < n; i++) {
          const e1 = pts[i], e2 = pts[(i + 1) % n];
          const ex = e2.x - e1.x, ey = e2.y - e1.y;
          const el = FX.fastLen(ex, ey) || 1;
          let t = ((v.x - e1.x) * ex + (v.y - e1.y) * ey) / (el * el / 1024);
          t = Math.max(0, Math.min(1024, t | 0));
          const qx = e1.x + ((ex * t) >> 10), qy = e1.y + ((ey * t) >> 10);
          const d = FX.fastLen(v.x - qx, v.y - qy);
          if (d < bestD) { bestD = d; best = [e1, e2, qx, qy]; bestT = t; }
        }
        if (!best) continue;
        const [e1, e2, qx, qy] = best;
        let nx = v.x - qx, ny = v.y - qy;
        const l = FX.fastLen(nx, ny) || 1;
        nx = (nx * 1024 / l) | 0; ny = (ny * 1024 / l) | 0;
        // push vertex out to edge, split correction
        const push = bestD;
        const half = push >> 1;
        v.x -= (nx * half) >> 10; v.y -= (ny * half) >> 10;
        const rem = push - half;
        const w2 = bestT, w1 = 1024 - bestT;
        if (e1.mass !== PINNED) { e1.x += (nx * ((rem * w1) >> 10)) >> 10; e1.y += (ny * ((rem * w1) >> 10)) >> 10; }
        if (e2.mass !== PINNED) { e2.x += (nx * ((rem * w2) >> 10)) >> 10; e2.y += (ny * ((rem * w2) >> 10)) >> 10; }
        const fr = (A.friction + B2.friction) >> 1;
        this.applyFriction(v, fr);
        v.flags |= 1; A.stepBits |= 1; B2.stepBits |= 1;
        if ((A.flags & BF.STICKY) && !(v.flags & 4)) {
          A.grabs.push(new Spring(v, { a: e1, b: e2, t: bestT }, 512, 10240, 0));
          v.flags |= 0xC;
        }
      }
    }
    if (touched && this.bodyCallback) this.bodyCallback(a, b);
  }

  // particle vs tiles/bodies/particles
  collideParticleTiles(pt, r, friction) {
    const cx = pt.x >> 15, cy = pt.y >> 15;
    let hit = false;
    for (let x = cx - 1; x <= cx + 1; x++)
      for (let y = cy - 1; y <= cy + 1; y++) {
        if (x < 0 || y < 0 || x >= this.w || y >= this.h) {
          continue;
        }
        const s = this.coll[x][y];
        if (s === -1) continue;
        const ox = x << 15, oy = y << 15;
        for (const e of Shapes[s].edges) {
          const rx = pt.x - ox - e.a.x, ry = pt.y - oy - e.a.y;
          let t = (rx * e.tx + ry * e.ty) >> 10;
          if (t < 0) t = 0; else if (t > e.len) t = e.len;
          const qx = e.a.x + ((e.tx * t) >> 10), qy = e.a.y + ((e.ty * t) >> 10);
          let dx = pt.x - ox - qx, dy = pt.y - oy - qy;
          const d = FX.fastLen(dx, dy);
          const pen = r - d;
          if (Shapes[s].closed) {
            const sd = ((dx * e.nx + dy * e.ny) >> 10);
            if (sd < 0) continue;
          }
          if (pen > 0 && d > 0) {
            pt.x += (dx * pen / d) | 0;
            pt.y += (dy * pen / d) | 0;
            this.applyFriction(pt, friction);
            pt.flags |= 3;
            hit = true;
          } else if (d === 0 && pen > 0) {
            pt.y -= pen; hit = true;
          }
        }
      }
    if (pt.y > (this.h << 15) + (r << 1)) pt.flags |= 0x10;  // fell out of world
    return hit;
  }
}
