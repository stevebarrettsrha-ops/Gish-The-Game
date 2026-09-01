// Controls verification — drives real key, mouse and DOM touch events against
// the built index.html on a desktop viewport and five device profiles.
//
//   npm i playwright   (once)
//   node tools/verify-controls.js
//
// Covers: every key binding, ability toggles, pause/resume, stuck-key recovery
// (pause / blur / tab-hidden), mouse press-drag steering, touch steering with a
// held finger, simultaneous multi-touch (steer + on-screen button), touchcancel,
// the soft buttons, and menu navigation by touch.
const path = require('path');
const { chromium } = require('playwright');
const URL = 'file://' + path.join(__dirname, '..', 'index.html');
let pass = 0, fail = 0;
const ok = (c, n, d) => { if (c) { pass++; console.log('  PASS', n); } else { fail++; console.log('  FAIL', n, d === undefined ? '' : JSON.stringify(d)); } };
const keys = p => p.evaluate(() => {
  const pl = Game.G.players[0];
  return { ...pl.keys, ability: pl.ability, active: Game.active, state: Game.G.state };
});

(async () => {
  const browser = await chromium.launch({ executablePath: '/opt/pw-browsers/chromium' });

  // ================= DESKTOP =================
  console.log('[desktop] keyboard');
  {
    const page = await browser.newPage({ viewport: { width: 1280, height: 800 } });
    const errs = []; page.on('pageerror', e => errs.push(e.message));
    await page.goto(URL); await page.waitForTimeout(900);
    for (let i = 0; i < 4; i++) { await page.keyboard.press('Enter'); await page.waitForTimeout(180); }
    await page.waitForTimeout(2200);
    await page.keyboard.press('Enter'); await page.waitForTimeout(250);
    await page.keyboard.press('Enter'); await page.waitForTimeout(250);
    await page.evaluate(() => Game.start(1, 1, 0)); await page.waitForTimeout(500);

    for (const [k, f] of [['ArrowRight','right'],['ArrowLeft','left'],['ArrowUp','up'],['ArrowDown','down'],
                          ['d','right'],['a','left'],['w','up'],['s','down'],[' ','up']]) {
      await page.keyboard.down(k); await page.waitForTimeout(80);
      const held = (await keys(page))[f];
      await page.keyboard.up(k); await page.waitForTimeout(80);
      const rel = (await keys(page))[f];
      ok(held === true && rel === false, `"${k}" -> ${f}`, { held, rel });
    }
    await page.keyboard.press('k'); await page.waitForTimeout(70);
    ok((await keys(page)).ability === 2, 'k = sticky');
    await page.keyboard.press('l'); await page.waitForTimeout(70);
    ok((await keys(page)).ability === 1, 'l = slick');
    await page.keyboard.press('l'); await page.waitForTimeout(70);
    ok((await keys(page)).ability === 0, 'l again = normal');

    await page.keyboard.press('Escape'); await page.waitForTimeout(300);
    let st = await page.evaluate(() => ({ a: Game.active, s: Shell.S.screen }));
    ok(!st.a && st.s === 1, 'Escape pauses', st);
    await page.keyboard.press('Enter'); await page.waitForTimeout(300);
    ok(await page.evaluate(() => Game.active), 'continue resumes');

    // stuck-key: hold, pause, release while paused, resume
    await page.keyboard.down('ArrowRight'); await page.waitForTimeout(100);
    await page.keyboard.press('Escape'); await page.waitForTimeout(250);
    await page.keyboard.up('ArrowRight'); await page.waitForTimeout(100);
    await page.keyboard.press('Enter'); await page.waitForTimeout(300);
    ok((await keys(page)).right === false, 'no stuck key after pausing mid-move', await keys(page));

    // stuck-key: blur with key held
    await page.keyboard.down('ArrowLeft'); await page.waitForTimeout(100);
    await page.evaluate(() => window.dispatchEvent(new Event('blur')));
    await page.waitForTimeout(120);
    ok((await keys(page)).left === false, 'no stuck key after window blur', await keys(page));
    await page.keyboard.up('ArrowLeft');

    // tab-away (visibility) with key held
    await page.keyboard.down('ArrowRight'); await page.waitForTimeout(100);
    await page.evaluate(() => {
      Object.defineProperty(document, 'hidden', { configurable: true, get: () => true });
      document.dispatchEvent(new Event('visibilitychange'));
    });
    await page.waitForTimeout(120);
    ok((await keys(page)).right === false, 'no stuck key after tab hidden', await keys(page));
    await page.keyboard.up('ArrowRight');

    // mouse: press-hold right of Gish steers, release stops
    const blob = await page.evaluate(() => {
      const c = Game.G.players[0].body.centroid();
      return { x: ((c.x >> 10) - Game.G.camX) * View.scale, y: ((c.y >> 10) - Game.G.camY) * View.scale, s: View.scale };
    });
    const r = await page.evaluate(() => { const b = document.getElementById('game').getBoundingClientRect(); return { l: b.left, t: b.top }; });
    await page.mouse.move(r.l + blob.x + 100 * blob.s, r.t + blob.y);
    await page.mouse.down(); await page.waitForTimeout(120);
    ok((await keys(page)).right === true, 'mouse hold right of Gish steers right', await keys(page));
    await page.mouse.up(); await page.waitForTimeout(120);
    ok((await keys(page)).right === false, 'mouse release stops', await keys(page));
    ok(errs.length === 0, 'no page errors (desktop)', errs.slice(0, 3));
    await page.close();
  }

  // ================= PHONES =================
  for (const [name, W, H] of [['iPhone SE', 320, 568], ['iPhone 12', 390, 844],
                              ['Pixel 5', 393, 851], ['landscape', 844, 390], ['tablet', 768, 1024]]) {
    console.log(`[touch] ${name} ${W}x${H}`);
    const page = await browser.newPage({ viewport: { width: W, height: H }, hasTouch: true, isMobile: true, deviceScaleFactor: 2 });
    const errs = []; page.on('pageerror', e => errs.push(e.message));
    // Dispatch real DOM TouchEvents so `touches` vs `changedTouches` follow the
    // spec exactly (CDP's touchEnd point list is ambiguous about which lifted).
    const touch = (type, active, changed) => page.evaluate(([type, active, changed]) => {
      const el = document.getElementById('game');
      const mk = p => new Touch({ identifier: p.id, target: el, clientX: p.x, clientY: p.y });
      const T = (active || []).map(mk), C = (changed || active || []).map(mk);
      el.dispatchEvent(new TouchEvent(type, { touches: T, targetTouches: T, changedTouches: C,
                                              bubbles: true, cancelable: true }));
    }, [type, active, changed]);

    await page.goto(URL); await page.waitForTimeout(900);
    for (let i = 0; i < 4; i++) {
      await touch('touchstart', [{ x: W / 2, y: H / 2, id: 1 }]);
      await touch('touchend', [], [{ x: W / 2, y: H / 2, id: 1 }]);
      await page.waitForTimeout(200);
    }
    await page.waitForTimeout(2300);
    const boot = await page.evaluate(() => ({ screen: Shell.S.screen, w: View.w, h: View.h, scale: View.scale }));
    ok(boot.screen === 40 || boot.screen === 0, 'splash advances by tap', boot);
    ok(boot.w >= 300 && boot.h >= 280, 'virtual viewport sane', boot);

    // first-run prompts: tap the OK soft button (bottom-left)
    for (let i = 0; i < 2; i++) {
      await touch('touchstart', [{ x: 20, y: H - 15, id: 1 }]);
      await touch('touchend', [], [{ x: 20, y: H - 15, id: 1 }]);
      await page.waitForTimeout(350);
    }
    ok(await page.evaluate(() => Shell.S.screen) === 0, 'reaches main menu by touch');

    // menu navigation by touch (tap the highlighted item to activate)
    const before = await page.evaluate(() => Shell.S.screen);
    const itemY = await page.evaluate(() => (View.h >= 300 ? 137 : 18) * View.scale + 12);
    await touch('touchstart', [{ x: W / 2, y: itemY, id: 1 }]);
    await touch('touchend', [], [{ x: W / 2, y: itemY, id: 1 }]);
    await page.waitForTimeout(400);
    const after = await page.evaluate(() => Shell.S.screen);
    ok(after !== before, 'menu item tap navigates', { before, after });
    // back via bottom-right soft button
    await touch('touchstart', [{ x: W - 20, y: H - 15, id: 1 }]);
    await touch('touchend', [], [{ x: W - 20, y: H - 15, id: 1 }]);
    await page.waitForTimeout(350);
    ok(await page.evaluate(() => Shell.S.screen) !== after, 'back soft-button returns');

    await page.evaluate(() => Game.start(1, 1, 0)); await page.waitForTimeout(600);
    const b = await page.evaluate(() => {
      const c = Game.G.players[0].body.centroid();
      return { x: ((c.x >> 10) - Game.G.camX) * View.scale, y: ((c.y >> 10) - Game.G.camY) * View.scale, s: View.scale };
    });
    const clampX = v => Math.max(4, Math.min(W - 4, v));
    const clampY = v => Math.max(4, Math.min(H - 80, v));

    const F1 = { x: clampX(b.x + 90 * b.s), y: clampY(b.y), id: 1 };
    const F2 = { x: 25 * b.s, y: H - 25, id: 2 };
    // HOLD right of Gish
    await touch('touchstart', [F1]);
    await page.waitForTimeout(150);
    ok((await keys(page)).right === true, 'hold right of Gish steers right', await keys(page));

    // second finger on the ability button while still steering
    const ab0 = await page.evaluate(() => Game.G.players[0].ability);
    await touch('touchstart', [F1, F2], [F2]);
    await page.waitForTimeout(180);
    const multi = await keys(page);
    const ab1 = await page.evaluate(() => Game.G.players[0].ability);
    ok(ab1 !== ab0, 'second finger works the ability button', { ab0, ab1 });
    ok(multi.right === true, 'steering survives the second finger', multi);

    // lift the button finger only: steering must continue
    await touch('touchend', [F1], [F2]);
    await page.waitForTimeout(150);
    ok((await keys(page)).right === true, 'steering continues after button finger lifts', await keys(page));

    // lift steering finger: everything stops
    await touch('touchend', [], [F1]);
    await page.waitForTimeout(150);
    const rel = await keys(page);
    ok(!rel.right && !rel.left && !rel.up && !rel.down, 'lifting the steering finger stops Gish', rel);

    // hold above to jump
    const FU = { x: clampX(b.x), y: clampY(b.y - 90 * b.s), id: 1 };
    await touch('touchstart', [FU]);
    await page.waitForTimeout(150);
    ok((await keys(page)).up === true, 'hold above Gish jumps', await keys(page));
    await touch('touchend', [], [FU]); await page.waitForTimeout(100);

    // touchcancel must not leave keys stuck
    await touch('touchstart', [F1]);
    await page.waitForTimeout(120);
    await touch('touchcancel', [], [F1]);
    await page.waitForTimeout(120);
    const canc = await keys(page);
    ok(!canc.right, 'touchcancel releases movement', canc);

    // pause button
    const FP = { x: W - 25, y: H - 25, id: 1 };
    await touch('touchstart', [FP]); await touch('touchend', [], [FP]);
    await page.waitForTimeout(300);
    const p2 = await page.evaluate(() => ({ a: Game.active, s: Shell.S.screen }));
    ok(!p2.a && p2.s === 1, 'pause button works', p2);

    ok(errs.length === 0, `no page errors (${name})`, errs.slice(0, 3));
    await page.close();
  }

  console.log(`\n=== controls: ${pass} passed, ${fail} failed ===`);
  await browser.close();
  process.exit(fail ? 1 : 0);
})();
