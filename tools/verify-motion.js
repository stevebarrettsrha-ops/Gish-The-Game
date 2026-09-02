// Motion verification — proves the game no longer stutters. Runs the built
// index.html in headless Chromium, samples where Gish and the camera are drawn
// on every animation frame while he drops down the shaft at the start of 1-1,
// and checks that the motion is spread over frames instead of landing in
// 6-10 px jumps every fourth frame (the physics runs at 70 ms ticks, the
// display at 60 Hz). Also checks the idle-hint sheet is wired to real input.
//
//   npm i playwright   (once)
//   node tools/verify-motion.js
const path = require('path');
const { chromium } = require('playwright');
const URL = 'file://' + path.join(__dirname, '..', 'index.html');
let pass = 0, fail = 0;
const ok = (c, n, d) => { if (c) { pass++; console.log('  PASS', n); } else { fail++; console.log('  FAIL', n, d === undefined ? '' : JSON.stringify(d)); } };

(async () => {
  const browser = await chromium.launch({ executablePath: '/opt/pw-browsers/chromium' });
  const page = await browser.newPage({ viewport: { width: 720, height: 1280 } });
  const errs = []; page.on('pageerror', e => errs.push(e.message));
  await page.goto(URL); await page.waitForTimeout(900);
  for (let i = 0; i < 4; i++) { await page.keyboard.press('Enter'); await page.waitForTimeout(180); }
  await page.waitForTimeout(2200);
  await page.keyboard.press('Enter'); await page.waitForTimeout(250);
  await page.keyboard.press('Enter'); await page.waitForTimeout(250);

  console.log('[motion] per-frame displacement while falling on 1-1');
  await page.evaluate(() => {
    Game.start(1, 1, 0);
    window.__s = [];
    const orig = Game.draw;
    Game.draw = function (ctx, alpha) {
      orig.call(Game, ctx, alpha);
      const G = Game.G;
      window.__s.push({ t: performance.now(), cam: G.drawCamY, gish: G.drawPy + G.drawCamY, alpha });
    };
  });
  await page.keyboard.down('ArrowRight');
  await page.waitForTimeout(3200);
  await page.keyboard.up('ArrowRight');
  const s = await page.evaluate(() => window.__s);
  const t0 = s[0].t;
  const seg = s.filter(x => x.t - t0 > 1400 && x.t - t0 < 2500);   // Gish in free fall
  const steps = (arr, key) => { const d = []; for (let i = 1; i < arr.length; i++) d.push(arr[i][key] - arr[i - 1][key]); return d; };
  const gish = steps(seg, 'gish');
  // the camera is clamped to the top of the map until Gish passes mid-screen:
  // judge it from the moment it starts scrolling
  const firstScroll = s.findIndex((x, i) => i > 0 && x.cam !== s[i - 1].cam);
  const camSeg = firstScroll < 0 ? [] : s.slice(firstScroll + 6);
  const cam = steps(camSeg, 'cam');
  const still = d => d.filter(v => v === 0).length / d.length;
  const maxStep = d => Math.max(...d.map(Math.abs));
  const mean = d => d.reduce((a, b) => a + b, 0) / d.length;
  console.log('    gish steps:', gish.slice(0, 30).map(v => +v.toFixed(1)).join(' '));
  console.log('    camera steps:', cam.slice(0, 30).map(v => +v.toFixed(1)).join(' '));
  ok(seg.length >= 40 && cam.length >= 30, 'sampled a steady stretch of frames', [seg.length, cam.length]);
  ok(mean(gish) > 1, 'Gish is falling through the sampled stretch', mean(gish));
  ok(still(gish) < 0.25, 'Gish moves on most frames (was still on ~75% of them)', still(gish));
  ok(maxStep(gish) <= 4, 'no single-frame jump over 4 px (was 6-10 px)', maxStep(gish));
  ok(still(cam) < 0.4, 'camera scrolls on most frames', still(cam));
  ok(maxStep(cam) <= 4, 'camera never jumps more than 4 px in a frame', maxStep(cam));
  ok(seg.every(x => x.alpha >= 0 && x.alpha <= 1), 'renderer receives an interpolation alpha in 0..1');

  console.log('[hints] idle sheet appears and real input dismisses it');
  await page.evaluate(() => { Game.start(2, 1, 0); Hints.state.idleMs = Hints.IDLE_MS - 140; });
  await page.waitForTimeout(600);
  const shown = await page.evaluate(() => ({ v: Hints.state.visible, kind: Hints.state.kind, page: Hints.state.page }));
  ok(shown.v === true, 'sheet is up once the idle timer runs out', shown);
  ok(shown.kind === 'key', 'worded for the keyboard the player has been using', shown.kind);
  await page.keyboard.press('ArrowLeft'); await page.waitForTimeout(120);
  ok((await page.evaluate(() => Hints.state.visible)) === false, 'a key press takes it down');
  await page.evaluate(() => { Hints.state.idleMs = Hints.IDLE_MS - 140; });
  await page.waitForTimeout(600);
  ok((await page.evaluate(() => Hints.state.visible)) === true, 'comes back after the next idle period');
  const r = await page.evaluate(() => { const b = document.getElementById('game').getBoundingClientRect(); return { l: b.left, t: b.top }; });
  await page.mouse.click(r.l + 300, r.t + 300); await page.waitForTimeout(120);
  const afterClick = await page.evaluate(() => ({ v: Hints.state.visible, kind: Hints.state.kind }));
  ok(afterClick.v === false && afterClick.kind === 'mouse', 'a click takes it down and switches the wording to mouse', afterClick);
  ok(errs.length === 0, 'no page errors', errs.slice(0, 3));

  console.log(`\n=== motion: ${pass} passed, ${fail} failed ===`);
  await browser.close();
  process.exit(fail ? 1 : 0);
})();
