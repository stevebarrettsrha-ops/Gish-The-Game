// Bootstrap — canvas/View, splash + loading sequence, input routing, 70 ms loop.

const View = { w: 360, h: 640, scale: 1, canvas: null, ctx: null };

const Main = (() => {
  let state = 'splash';          // splash -> loading -> firstrun/menu/game
  let splashStage = 0, splashT = 0;
  let loadProgress = 0, loadTotal = 196;
  let booted = false;

  function resize() {
    const ww = window.innerWidth, wh = window.innerHeight;
    let s = Math.max(1, Math.floor(Math.min(ww / 360, wh / 560)));
    View.scale = s;
    View.w = Math.floor(ww / s);
    View.h = Math.floor(wh / s);
    const c = View.canvas;
    c.width = View.w; c.height = View.h;
    c.style.width = View.w * s + 'px';
    c.style.height = View.h * s + 'px';
    View.ctx.imageSmoothingEnabled = false;
  }

  async function boot() {
    View.canvas = document.getElementById('game');
    View.ctx = View.canvas.getContext('2d');
    resize();
    window.addEventListener('resize', resize);
    bindInput();
    requestAnimationFrame(render);  // start loop immediately so splash shows

    await Assets.loadAllImages((done, total) => {
      loadProgress = Math.min(196, Math.floor(done * 196 / total));
    });
    Font.init();
    Font.loadStrings();
    Shell.loadAll();
    await Assets.initAudio().catch(() => { });
    Assets.sfxOn = Shell.S.settings.soundVol > 0;
    booted = true;
  }

  const SPLASH = [
    { img: 255, bg: '#1d304e' },
    { img: 254, bg: '#ffffff' },
    { img: 253, bg: '#ffffff' },
  ];

  function advanceSplash() {
    splashStage++; splashT = 0;
    if (splashStage >= SPLASH.length) state = 'loading';
  }

  let last = 0, acc = 0;
  function render(ts) {
    requestAnimationFrame(render);
    const ctx = View.ctx;
    if (!ctx) return;
    if (!isFinite(ts)) ts = last;
    const dt = Math.max(0, Math.min(200, ts - last)); last = ts;
    acc += dt;

    if (state === 'splash') {
      splashT += dt;
      if (splashT > 3000 && booted) advanceSplash();
      const s = SPLASH[Math.min(splashStage, SPLASH.length - 1)];
      ctx.fillStyle = s.bg;
      ctx.fillRect(0, 0, View.w, View.h);
      const im = Assets.images[s.img];
      if (im) ctx.drawImage(im, (View.w - im.width) >> 1, (View.h - im.height) >> 1);
      return;
    }
    if (state === 'loading') {
      ctx.fillStyle = '#000';
      ctx.fillRect(0, 0, View.w, View.h);
      const I = Assets.images;
      const capL = I[251], fill = I[252], empty = I[249], capR = I[248];
      if (capL && fill && empty && capR) {
        const bw = ((View.w - (capL.width << 1)) * 9 / 10) | 0;
        const x0 = (View.w - bw) >> 1, y = View.h >> 1;
        const fw = (loadProgress * bw / 196) | 0;
        ctx.drawImage(capL, x0 - capL.width, y - (capL.height >> 1));
        for (let i = 0; i < fw; i++) ctx.drawImage(fill, x0 + i, y - (fill.height >> 1));
        for (let i = fw; i < bw; i++) ctx.drawImage(empty, x0 + i, y - (empty.height >> 1));
        ctx.drawImage(capR, x0 + bw, y - (capR.height >> 1));
      }
      if (booted && loadProgress >= 196) {
        if (Shell.S.firstRun) { state = 'shell'; Shell.open(40); }   // enable sound? -> music? -> menu
        else { state = 'shell'; Shell.open(0); }
      } else if (booted) loadProgress = Math.min(196, loadProgress + 4);
      return;
    }

    // shell / game at fixed 70 ms ticks
    while (acc >= 70) {
      acc -= 70;
      if (Game.active) Game.tick();
    }
    if (Game.active) Game.draw(ctx);
    else Shell.draw(ctx);
  }

  // ---- input ----
  const KEYMAP = {
    ArrowUp: -1, ArrowDown: -2, ArrowLeft: -3, ArrowRight: -4,
    w: -1, s: -2, a: -3, d: -4, W: -1, S: -2, A: -3, D: -4,
    ' ': -1, Enter: -5, Escape: -7, Backspace: -7,
    k: 42, K: 42, l: 35, L: 35, Shift: 42, Control: 35,
    '2': 50, '8': 56, '4': 52, '6': 54, '5': 53,
    '1': 49, '3': 51, '7': 55, '9': 57, '0': 48, '*': 42, '#': 35,
  };

  function bindInput() {
    const canvas = View.canvas;

    // ---- keyboard ----
    window.addEventListener('keydown', e => {
      if (e.repeat) { e.preventDefault(); return; }
      Assets.resumeAudio();
      if (state === 'splash') { advanceSplashIfReady(); e.preventDefault(); return; }
      // text-entry screens take raw characters
      if (!Game.active && Shell.S.widget === 2) {
        if (e.key === 'Backspace') Shell.keyChar('\b');
        else if (e.key === 'Enter') Shell.keyChar('\n');
        else if (e.key.length === 1) Shell.keyChar(e.key);
        e.preventDefault();
        return;
      }
      const code = KEYMAP[e.key];
      if (code === undefined) return;
      e.preventDefault();
      if (Game.active) Game.key(code, true);
      else {
        const digit = '0123456789'.indexOf(e.key);
        Shell.key(digit >= 0 ? 48 + digit : code, 0);
      }
    });
    // Always forward key-up, even while paused: otherwise a key released
    // during the pause menu stays held and Gish runs off on resume.
    window.addEventListener('keyup', e => {
      const code = KEYMAP[e.key];
      if (code === undefined) return;
      Game.key(code, false);
    });
    // losing focus (alt-tab, app switch, screen lock) must drop every key
    const dropAll = () => { Game.releaseKeys(); steerId = null; };
    window.addEventListener('blur', dropAll);
    document.addEventListener('visibilitychange', () => { if (document.hidden) dropAll(); });

    // ---- pointer helpers ----
    const toVirtual = t => {
      const r = canvas.getBoundingClientRect();
      return [(t.clientX - r.left) / View.scale, (t.clientY - r.top) / View.scale];
    };
    const wake = () => { Assets.resumeAudio(); };

    // ---- mouse (desktop) ----
    let mouseDown = false;
    canvas.addEventListener('mousedown', e => {
      e.preventDefault(); wake();
      if (state === 'splash') { advanceSplashIfReady(); return; }
      if (state === 'loading') return;
      mouseDown = true;
      const [x, y] = toVirtual(e);
      if (Game.active) Game.touchDown(x, y); else Shell.tap(x, y);
    });
    canvas.addEventListener('mousemove', e => {
      if (!mouseDown || !Game.active) return;
      e.preventDefault();
      const [x, y] = toVirtual(e);
      if (!Game.buttonAt(x, y)) Game.steer(x, y);
    });
    window.addEventListener('mouseup', () => {
      if (!mouseDown) return;
      mouseDown = false;
      Game.releaseKeys();
    });

    // ---- touch (phones/tablets) ----
    // One finger owns steering; other fingers can work the on-screen buttons
    // at the same time, so you can hold a direction and tap to switch surface.
    let steerId = null;
    canvas.addEventListener('touchstart', e => {
      e.preventDefault(); wake();
      if (state === 'splash') { advanceSplashIfReady(); return; }
      if (state === 'loading') return;
      for (const t of e.changedTouches) {
        const [x, y] = toVirtual(t);
        if (!Game.active) { Shell.tap(x, y); continue; }
        if (Game.buttonAt(x, y)) { Game.pressButton(Game.buttonAt(x, y)); continue; }
        if (steerId === null && Game.touchDown(x, y) === 'steer') steerId = t.identifier;
        else if (steerId === null) Game.touchDown(x, y);
      }
    }, { passive: false });

    canvas.addEventListener('touchmove', e => {
      e.preventDefault();
      if (!Game.active || steerId === null) return;
      for (const t of e.changedTouches) {
        if (t.identifier !== steerId) continue;
        const [x, y] = toVirtual(t);
        if (!Game.buttonAt(x, y)) Game.steer(x, y);
      }
    }, { passive: false });

    const endTouch = e => {
      for (const t of e.changedTouches) {
        if (t.identifier !== steerId) continue;
        steerId = null;
        Game.releaseKeys();
      }
      // a stray state where no touches remain: make sure nothing is held
      if (e.touches && e.touches.length === 0) { steerId = null; Game.releaseKeys(); }
    };
    canvas.addEventListener('touchend', endTouch);
    canvas.addEventListener('touchcancel', endTouch);

    // long-press must not raise the context menu over the canvas
    canvas.addEventListener('contextmenu', e => e.preventDefault());
  }

  function advanceSplashIfReady() { if (booted || splashStage < SPLASH.length - 1) advanceSplash(); }

  return { boot };
})();

Main.boot();
