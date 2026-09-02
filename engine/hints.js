// Idle coaching — leave Gish alone for 35 s and a "hint!" sheet slides up in
// which a little animated Gish demonstrates one control at a time (roll,
// jump, heavy, sticky, slick, attack), worded for the input the player is
// actually using (keyboard, mouse or touch) and shown with a matching key cap,
// finger or cursor. Any press, tap or click dismisses it and restarts the
// timer; a held direction key counts as playing, so it never interrupts
// someone mid-roll. Honours the "hints" setting like the story hints do.

const Hints = (() => {
  const IDLE_MS = 35000;       // silence before the sheet appears
  const TICK_MS = 70;          // one game tick
  const PAGE_TICKS = 100;      // 7 s per demonstration before moving to the next
  const LOOP = 40;             // one animation cycle (2.8 s)
  const PAGES = ['move', 'jump', 'heavy', 'sticky', 'slick', 'attack'];

  const TEXT = {
    key: {
      move: 'arrow keys or a and d roll gish left and right.',
      jump: 'up, w or space jumps while gish stands on the ground.',
      heavy: 'down or s makes gish heavy: he drops hard and smashes blocks.',
      sticky: 'k or shift makes gish sticky (gray): he climbs walls and ceilings.',
      slick: 'l or ctrl makes gish slick (brown): he slips through narrow gaps.',
      attack: 'enter makes gish angry: he flings boxes away and squishes enemies.',
    },
    touch: {
      move: 'hold a finger left or right of gish to roll him that way.',
      jump: 'hold a finger above gish while he is on the ground to jump.',
      heavy: 'hold a finger below gish: heavy gish drops hard and smashes blocks.',
      sticky: 'tap the bottom-left button: gray sticky gish climbs walls and ceilings.',
      slick: 'tap the bottom-left button: brown slick gish slips through narrow gaps.',
      attack: "poke gish's body: angry gish flings boxes away and squishes enemies.",
    },
    mouse: {
      move: 'hold the mouse button left or right of gish to roll him that way.',
      jump: 'hold the mouse button above gish while he is on the ground to jump.',
      heavy: 'hold the mouse below gish: heavy gish drops hard and smashes blocks.',
      sticky: 'press k for sticky gish (gray): he climbs walls and ceilings.',
      slick: 'press l for slick gish (brown): he slips through narrow gaps.',
      attack: "click on gish's body: angry gish flings boxes away and squishes enemies.",
    },
  };

  const H = {
    idleMs: 0,        // ms without input while playing
    visible: false,
    page: 0,          // index into PAGES
    t: 0,             // ticks the current page has been up
    slide: 0,         // sheet slide-in progress (ticks, 0..4)
    kind: null,       // 'key' | 'mouse' | 'touch' — last input the player used
    moved: false,     // has the player driven Gish at all in this level?
    shows: 0,         // sheets shown in this level
  };

  function inputKind() {
    if (H.kind) return H.kind;
    const nav = typeof navigator !== 'undefined' ? navigator : null;
    return nav && (nav.maxTouchPoints > 0 || 'ontouchstart' in (typeof window !== 'undefined' ? window : {})) ? 'touch' : 'key';
  }

  function reset() {
    H.idleMs = 0; H.visible = false; H.page = 0; H.t = 0; H.slide = 0; H.moved = false; H.shows = 0;
  }

  // the player did something (key, tap, click): sheet down, timer back to zero
  function activity(kind) {
    if (kind) H.kind = kind;
    H.idleMs = 0;
    if (H.visible) hide();
  }

  function show() {
    H.visible = true; H.t = 0; H.slide = 0;
    // first sheet: someone who has already rolled around does not need "move";
    // later sheets continue the tour from where the last one stopped
    if (H.shows === 0) H.page = H.moved ? 1 : 0;
    else H.page = (H.page + 1) % PAGES.length;
    H.shows++;
  }
  function hide() { H.visible = false; H.idleMs = 0; }

  function tick(G) {
    const pl = G.players[0];
    const playing = G.state === 0 && pl && !pl.dead;
    const k = pl && pl.keys;
    if (k && (k.left || k.right || k.up || k.down)) {   // holding a direction = playing
      H.moved = true;
      H.idleMs = 0;
      if (H.visible) hide();
      return;
    }
    if (!playing) { if (H.visible) hide(); return; }    // dialogs, results, death
    if (H.visible) {
      if (H.slide < 4) H.slide++;
      if (++H.t >= PAGE_TICKS) { H.t = 0; H.page = (H.page + 1) % PAGES.length; }
      return;
    }
    H.idleMs += TICK_MS;
    const enabled = !Shell.S.settings || Shell.S.settings.hints !== false;
    if (H.idleMs >= IDLE_MS && enabled) show();
  }

  // ---------------- drawing ----------------
  const ease = v => v * v * (3 - 2 * v);
  const clamp01 = v => Math.max(0, Math.min(1, v));
  const win = (u, a, b) => u >= a && u < b;           // is u inside [a, b)?
  const TWO_PI = Math.PI * 2;
  const rad2gaze = r => FX.norm(Math.round(r / TWO_PI * FX.PERIOD));

  // a little Gish: wobbly ring, outline by ability, real face sprite (scaled)
  function blob(ctx, x, y, r, sx, sy, gaze, ability, face, tt) {
    const n = 16;
    ctx.beginPath();
    for (let i = 0; i < n; i++) {
      const a = i / n * TWO_PI;
      const w = 1 + 0.05 * Math.sin(a * 3 + tt * 0.7) + 0.03 * Math.sin(a * 5 - tt * 0.9);
      const px = x + Math.cos(a) * r * sx * w, py = y + Math.sin(a) * r * sy * w;
      if (i) ctx.lineTo(px, py); else ctx.moveTo(px, py);
    }
    ctx.closePath();
    ctx.fillStyle = '#000';
    ctx.fill();
    ctx.strokeStyle = ability === 2 ? '#bbbbbb' : ability === 1 ? '#a77c15' : '#000';
    ctx.lineWidth = 2; ctx.stroke(); ctx.lineWidth = 1;
    const off = FX.vecFromAngle(gaze, 2048);
    ctx.save();
    ctx.translate(Math.round(x + off.x / 1024 * sx), Math.round(y + off.y / 1024 * sy));
    ctx.scale(0.75, 0.75);
    Game.drawFace(ctx, 0, 0, gaze, 0, face);
    ctx.restore();
  }

  // expanding ring that reads as "pressed here"
  function ripple(ctx, x, y, tt) {
    const ph = (tt * 0.6) % 1;
    ctx.strokeStyle = 'rgba(255,255,255,' + (1 - ph).toFixed(2) + ')';
    ctx.lineWidth = 2;
    ctx.beginPath(); ctx.arc(x, y, 7 + 10 * ph, 0, 7); ctx.stroke();
    ctx.lineWidth = 1;
  }

  // fingertip at (x, y), hand trailing down-right
  function finger(ctx, x, y, pressed, tt) {
    if (pressed) ripple(ctx, x, y, tt);
    ctx.fillStyle = '#f2c9a0'; ctx.strokeStyle = '#3a2a1a';
    ctx.beginPath();
    ctx.moveTo(x - 5, y + 1); ctx.lineTo(x + 8, y + 24); ctx.lineTo(x + 20, y + 17); ctx.lineTo(x + 5, y - 3);
    ctx.closePath(); ctx.fill(); ctx.stroke();
    ctx.beginPath(); ctx.arc(x, y, 5.5, 0, 7); ctx.fill(); ctx.stroke();
  }

  // arrow cursor with its tip at (x, y)
  function cursor(ctx, x, y, pressed, tt) {
    if (pressed) ripple(ctx, x, y, tt);
    ctx.beginPath();
    ctx.moveTo(x, y); ctx.lineTo(x, y + 16); ctx.lineTo(x + 4, y + 12.5); ctx.lineTo(x + 7, y + 19);
    ctx.lineTo(x + 10, y + 17.5); ctx.lineTo(x + 7, y + 11.5); ctx.lineTo(x + 12, y + 11.5);
    ctx.closePath();
    ctx.fillStyle = '#fff'; ctx.fill(); ctx.strokeStyle = '#000'; ctx.stroke();
  }

  function pointer(ctx, kind, x, y, pressed, tt) {
    if (kind === 'mouse') cursor(ctx, x, y, pressed, tt); else finger(ctx, x, y, pressed, tt);
  }

  // key cap centred at (x, y); glyph is an arrow name or a short label
  function keycap(ctx, x, y, glyph, pressed, w) {
    w = w || 24;
    const d = pressed ? 2 : 0;
    ctx.fillStyle = '#111'; ctx.fillRect(x - (w >> 1), y - 10, w, 24);
    ctx.fillStyle = pressed ? '#6a5a10' : '#3a3a3a'; ctx.fillRect(x - (w >> 1), y - 12 + d, w, 24);
    ctx.strokeStyle = pressed ? '#ffe680' : '#bbb'; ctx.strokeRect(x - (w >> 1) + 0.5, y - 11.5 + d, w - 1, 23);
    const cy = y + d;
    ctx.fillStyle = pressed ? '#ffe680' : '#eee';
    if (glyph === 'up' || glyph === 'down' || glyph === 'left' || glyph === 'right') {
      const dx = glyph === 'left' ? -1 : glyph === 'right' ? 1 : 0;
      const dy = glyph === 'up' ? -1 : glyph === 'down' ? 1 : 0;
      ctx.beginPath();
      ctx.moveTo(x + dx * 6, cy + dy * 6);
      ctx.lineTo(x - dx * 4 + dy * 6, cy - dy * 4 + dx * 6);
      ctx.lineTo(x - dx * 4 - dy * 6, cy - dy * 4 - dx * 6);
      ctx.closePath(); ctx.fill();
    } else {
      ctx.save(); ctx.translate(x, cy); ctx.scale(0.6, 0.6);
      Font.drawText(ctx, 3, glyph, 0, -11, 1);
      ctx.restore();
    }
  }

  // the on-screen surface button (bottom-left in the HUD), scaled to fit
  function abilityButton(ctx, x, y, ability, tapped, tt) {
    const im = Assets.images[4 + ability];
    const size = 26;
    ctx.fillStyle = 'rgba(0,0,0,0.5)'; ctx.fillRect(x, y, size + 6, size + 6);
    if (im) {
      const s = Math.min(size / im.width, size / im.height);
      ctx.drawImage(im, x + 3, y + 3, Math.round(im.width * s), Math.round(im.height * s));
    }
    ctx.strokeStyle = tapped ? '#ffe680' : '#999'; ctx.strokeRect(x + 0.5, y + 0.5, size + 5, size + 5);
    if (tapped) ripple(ctx, x + 3 + (size >> 1), y + 3 + (size >> 1), tt);
  }

  // ---- the six scenes; each draws into the box (ax, ay, aw, ah) ----
  function scene(ctx, page, kind, ax, ay, aw, ah, tt) {
    const u = (tt % LOOP) / LOOP;                 // 0..1 through the loop
    const floorY = ay + ah - 14, r = 17, gy = floorY - r;
    const keys = kind === 'key';
    const capX = ax + aw - 18, capY = ay + 16;   // key caps live top-right
    ctx.fillStyle = '#4a3a2a'; ctx.fillRect(ax, floorY, aw, ah - (floorY - ay));
    ctx.fillStyle = '#7a6a4a'; ctx.fillRect(ax, floorY, aw, 2);

    switch (page) {
      case 'move': {
        const dir = u < 0.5 ? 1 : -1;
        const e = ease((u % 0.5) / 0.5);
        const x0 = ax + aw * 0.32, x1 = ax + aw * 0.68;
        const x = dir > 0 ? x0 + (x1 - x0) * e : x1 - (x1 - x0) * e;
        const gaze = rad2gaze((x - x0) / r);       // rolls as it goes
        blob(ctx, x, gy, r, 1, 1, gaze, 0, 0, tt);
        if (keys) {
          keycap(ctx, capX - 28, capY, 'left', dir < 0);
          keycap(ctx, capX, capY, 'right', dir > 0);
        } else pointer(ctx, kind, x + dir * 46, gy - 2, true, tt);
        break;
      }
      case 'jump': {
        const x = ax + aw * 0.5;
        let sy = 1, h = 0;
        if (win(u, 0.2, 0.3)) sy = 1 - 0.3 * Math.sin(Math.PI * (u - 0.2) / 0.1);
        else if (win(u, 0.3, 0.8)) { const p = (u - 0.3) / 0.5; h = 46 * Math.sin(Math.PI * p); sy = 1 + 0.12 * Math.sin(Math.PI * p); }
        else if (win(u, 0.8, 0.9)) sy = 1 - 0.2 * Math.sin(Math.PI * (u - 0.8) / 0.1);
        const sx = 1 / sy;
        blob(ctx, x, floorY - r * sy - h, r, sx, sy, FX.norm(-FX.QUARTER), 0, 0, tt);
        const pressed = win(u, 0.15, 0.5);
        if (keys) keycap(ctx, capX, capY, 'up', pressed);
        else pointer(ctx, kind, x, floorY - r - 44, pressed, tt);
        break;
      }
      case 'heavy': {
        const x = ax + aw * 0.5;
        let y = gy, sx = 1, sy = 1;
        if (win(u, 0.15, 0.35)) y = gy - 34 * Math.sin(Math.PI * (u - 0.15) / 0.4);       // hop up
        else if (win(u, 0.35, 0.5)) { const p = (u - 0.35) / 0.15; y = (gy - 34) + (gy + 6 - (gy - 34)) * p * p; sy = 1.15; sx = 0.9; }   // slam down
        else if (win(u, 0.5, 0.7)) { const p = Math.sin(Math.PI * (u - 0.5) / 0.2); sy = 1 - 0.45 * p; sx = 1 + 0.5 * p; y = floorY - r * sy; }
        if (win(u, 0.5, 0.75)) {                     // debris bursting out of the impact
          const p = (u - 0.5) / 0.25;
          ctx.fillStyle = '#9c8a6a';
          for (let i = 0; i < 6; i++) {
            const side = i < 3 ? -1 : 1, spd = 26 + 10 * (i % 3);
            const dx = side * (r + spd * p), dy = -(18 + 6 * (i % 3)) * p + 40 * p * p;
            ctx.fillRect(Math.round(x + dx), Math.round(floorY - 2 + dy), 2, 2);
          }
        }
        blob(ctx, x, y, r, sx, sy, FX.QUARTER, 0, 0, tt);
        const pressed = win(u, 0.3, 0.7);
        if (keys) keycap(ctx, capX, capY, 'down', pressed);
        else pointer(ctx, kind, x, floorY + 6, pressed, tt);
        break;
      }
      case 'sticky': {
        const wallX = ax + aw * 0.72;
        ctx.fillStyle = '#4a3a2a'; ctx.fillRect(wallX, ay, ax + aw - wallX, floorY - ay);
        ctx.fillStyle = '#7a6a4a'; ctx.fillRect(wallX, ay, 2, floorY - ay);
        const ability = u >= 0.12 ? 2 : 0;
        const x0 = ax + aw * 0.3;
        let x = x0, y = gy, sx = 1, gaze = 0;
        if (win(u, 0.15, 0.4)) { const e = ease((u - 0.15) / 0.25); x = x0 + (wallX - r - x0) * e; gaze = rad2gaze((x - x0) / r); }
        else if (u >= 0.4) {
          x = wallX - r * 0.85; sx = 0.85;
          const e = ease(clamp01((u - 0.4) / 0.5));
          y = gy - (gy - (ay + r + 6)) * e;
          gaze = rad2gaze((wallX - r - x0) / r + (gy - y) / r);
        }
        blob(ctx, x, y, r, sx, 1, gaze, ability, 0, tt);
        const tap = win(u, 0.02, 0.15);
        if (keys) {
          keycap(ctx, capX - 28, capY, 'k', tap);
          keycap(ctx, capX, capY, 'up', u >= 0.4 && u < 0.9);
        } else {
          abilityButton(ctx, ax + 4, floorY - 34, ability, tap, tt);
          if (u >= 0.4 && u < 0.9) pointer(ctx, kind, x, y - r - 30, true, tt);
          else if (win(u, 0.15, 0.4)) pointer(ctx, kind, x + 46, y - 2, true, tt);
        }
        break;
      }
      case 'slick': {
        const gc = ax + aw * 0.55, gap = 12;
        ctx.fillStyle = '#4a3a2a';
        ctx.fillRect(gc - gap / 2 - 40, ay + 10, 40, floorY - ay - 10);
        ctx.fillRect(gc + gap / 2, ay + 10, 40, floorY - ay - 10);
        ctx.fillStyle = '#7a6a4a';
        ctx.fillRect(gc - gap / 2 - 40, ay + 10, 40, 2); ctx.fillRect(gc + gap / 2, ay + 10, 40, 2);
        const ability = u >= 0.1 ? 1 : 0;
        const x0 = ax + aw * 0.22, x1 = ax + aw * 0.82;
        const p = clamp01((u - 0.15) / 0.7);
        const x = x0 + (x1 - x0) * p;
        const squeeze = clamp01(1 - Math.abs(x - gc) / 30);
        const sx = 1 - 0.62 * squeeze, sy = 1 / sx;
        blob(ctx, x, floorY - r * sy, r, sx, sy, rad2gaze((x - x0) / r), ability, 0, tt);
        const tap = win(u, 0.02, 0.12);
        if (keys) {
          keycap(ctx, capX - 28, capY, 'l', tap);
          keycap(ctx, capX, capY, 'right', u >= 0.15 && u < 0.85);
        } else {
          abilityButton(ctx, ax + 4, floorY - 34, ability, tap, tt);
          if (u >= 0.15 && u < 0.85) pointer(ctx, kind, x + 46, floorY - r - 2, true, tt);
        }
        break;
      }
      case 'attack': {
        const x0 = ax + aw * 0.36;
        const hit = u >= 0.3;
        const recoil = hit ? 6 * Math.sin(Math.PI * clamp01((u - 0.3) / 0.2)) : 0;
        // the box: at rest beside Gish, then flung away in an arc
        let bx = x0 + r + 16, by = floorY - 12, rot = 0;
        if (hit) { const p = (u - 0.3) / 0.7; bx += 110 * p; by -= 64 * 4 * p * (1 - p); rot = p * 3; }
        ctx.save(); ctx.translate(bx, by); ctx.rotate(rot);
        ctx.fillStyle = '#735c55'; ctx.fillRect(-12, -12, 24, 24);
        ctx.strokeStyle = '#000'; ctx.strokeRect(-11.5, -11.5, 23, 23);
        ctx.restore();
        blob(ctx, x0 - recoil, gy, r, 1, 1, 0, 0, hit && u < 0.75 ? 1 : 0, tt);
        const pressed = win(u, 0.22, 0.34);
        if (keys) keycap(ctx, capX - 8, capY, 'enter', pressed, 44);
        else {
          // the pointer approaches and lands on his body
          const a = ease(clamp01(u / 0.22));
          pointer(ctx, kind, x0 + 40 - 40 * a, gy - 30 + 30 * a, pressed, tt);
        }
        break;
      }
    }
  }

  function draw(ctx, G, alpha) {
    if (!H.visible) return;
    const W = View.w, VH = View.h;
    const kind = inputKind();
    const page = PAGES[H.page];
    const lines = Font.wrap(3, Font.encode(3, TEXT[kind][page]), W - 28);
    const demoH = 96, lh = 25;
    const boxH = 34 + demoH + 8 + lines.length * lh + 6;
    const s = Math.min(1, (H.slide + alpha) / 4);
    const y = Math.round(VH - (boxH + 76) * s);     // slides up, settling above the on-screen buttons
    const tt = H.t + alpha;

    // sheet, rope border along its top like the story dialogs
    ctx.fillStyle = 'rgb(23,23,23)'; ctx.fillRect(6, y, W - 12, boxH);
    ctx.strokeStyle = '#fff'; ctx.strokeRect(6.5, y + 0.5, W - 13, boxH - 1);
    const rope = Assets.images[90];
    if (rope) {
      ctx.save(); ctx.beginPath(); ctx.rect(6, y - rope.height, W - 12, rope.height * 2); ctx.clip();
      for (let x = 6; x < W - 6; x += rope.width) ctx.drawImage(rope, x, y - (rope.height >> 1));
      ctx.restore();
    }
    // title + page dots
    Font.drawText(ctx, 3, Font.str(81) || 'hint!', 14, y + 7);
    for (let i = 0; i < PAGES.length; i++) {
      ctx.fillStyle = i === H.page ? '#fff' : '#555';
      ctx.beginPath(); ctx.arc(W - 16 - (PAGES.length - 1 - i) * 10, y + 18, 3, 0, 7); ctx.fill();
    }
    // demonstration box
    const ax = 12, ay = y + 34, aw = W - 24, ah = demoH;
    ctx.fillStyle = '#3f78bf'; ctx.fillRect(ax, ay, aw, ah);
    ctx.save(); ctx.beginPath(); ctx.rect(ax, ay, aw, ah); ctx.clip();
    scene(ctx, page, kind, ax, ay, aw, ah, tt);
    ctx.restore();
    ctx.strokeStyle = '#000'; ctx.strokeRect(ax + 0.5, ay + 0.5, aw - 1, ah - 1);
    // caption
    Font.drawWrapped(ctx, 3, lines, 14, ay + ah + 8);
  }

  return { reset, activity, tick, draw, state: H, PAGES, TEXT, IDLE_MS, PAGE_TICKS };
})();
