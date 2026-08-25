// Game shell — boot/splash, menu state machine, saves, unlocks. Port of class s
// (menus), Main (boot/saves) per the original flow. Bluetooth multiplayer runs
// locally (shared keyboard); Zeemote/HTTP subsystems are dropped.

const Shell = (() => {
  // ---- master level table (at.b): level id -> file name ----
  const LEVELS = ['intro',
    '1/01_s0','1/02_j0','1/03_j4','1/04_s3','1/05_s4','1/06_j3','1/07_j1','1/08_s5',
    '1/09_','1/10_j2','1/11_s11','1/12_s13','1/13_','1/14_s17','1/15_s18',
    '2/01_e0','2/02_','2/03_','2/04_e3','2/05_e4','2/06_','2/07_','2/08_e9','2/09_',
    '2/10_e14','2/11_e15',
    '3/01_h2','3/02_h4','3/03_','3/04_h7','3/05_j5','3/06_h9','3/07_','3/08_h14','3/09_h15',
    'playgr/01','playgr/02','playgr/03','playgr/04','playgr/05'];
  for (let i = 0; i <= 26; i++) LEVELS.push('coop/' + String(i).padStart(2, '0'));
  for (let i = 1; i <= 10; i++) LEVELS.push('dm/' + String(i).padStart(2, '0'));
  for (let i = 1; i <= 10; i++) LEVELS.push('race/' + String(i).padStart(2, '0'));

  // secrets per campaign level (Main.a, index = id-1 for ids 1..35; sum = 40)
  const SECRETS = [1,2,2,1,0,1,2,2,1,1,0,3,1,0,0,2,2,2,1,0,2,1,0,1,0,1,3,0,1,2,1,0,3,0,1];
  // goodies: level id -> secret code (Main.e)
  const GOODIES = { 1: 2327, 5: 6382, 9: 8759, 12: 7437, 17: 7519, 21: 9983, 26: 1166, 30: 6262 };
  // unlock thresholds (Main.b/c/d)
  const PLAYGROUND_THRESH = [2, 10, 20, 30, 39];
  const DM_THRESH = [4, 12, 18, 25, 34];
  const RACE_THRESH = [7, 15, 22, 28, 37];
  const BOSS_SUFFIX = { 5: 70, 11: 70, 20: 71, 23: 71, 28: 72, 32: 72, 14: 73, 25: 73, 34: 73 };

  function levelName(id) {
    if (id === 0) return Font.str(69);                      // intro
    if (id === 15 || id === 26) return Font.str(112);       // bonus
    if (id === 35 || id === 67) return Font.str(113);       // outro
    if (id >= 36 && id <= 40) return Font.str(114 + id - 36); // playground names
    let base;
    if (id <= 15) base = '1-' + id;
    else if (id <= 26) base = '2-' + (id - 15);
    else if (id <= 35) base = '3-' + (id - 26);
    else if (id <= 67) base = 'c-' + (id - 40);
    else if (id <= 77) base = 'd-' + (id - 67);
    else base = 'r-' + (id - 77);
    if (BOSS_SUFFIX[id] !== undefined) base += Font.str(BOSS_SUFFIX[id]);
    return base;
  }

  // ---- persistence (RMS "gigo*" -> localStorage) ----
  const store = (k, v) => { try { localStorage.setItem('gigo' + k, JSON.stringify(v)); } catch (e) { } };
  const fetchS = (k, d) => { try { const v = localStorage.getItem('gigo' + k); return v ? JSON.parse(v) : d; } catch (e) { return d; } };

  const S = {  // runtime state
    settings: null, save: null, msave: null, achi: null, scores: null,
    screen: -1, widget: 0, sel: 0, scroll: 0, selStack: [],
    listIds: [], listActions: [], listValues: [], page: null, pageLine: 0,
    input: '', inputMax: 11, marquee: 0, anim: 0,
    keyBuf: [0,0,0,0,0,0], tapBuf: [0,0,0,0,0,0],
    firstRun: false, pendingLevel: -1, banner: null,
  };

  function defaults() {
    return {
      settings: { soundVol: 5, musicVol: 3, vibration: true, hints: true, detail: 2, name: '' },
      save: null, msave: null,
      achi: { sp: 0, mp: 0, secrets: 0, secretFlags: SECRETS.map(n => Array(n).fill(false)),
              goodieFlags: Array(36).fill(false) },
      scores: [null, null, null, null],   // SP score, SP time, MP score, MP time
    };
  }

  function loadAll() {
    const d = defaults();
    S.settings = fetchS('settings', null);
    S.firstRun = !S.settings;
    if (!S.settings) S.settings = d.settings;
    S.save = fetchS('save', null);
    S.msave = fetchS('msave', null);
    S.achi = fetchS('achi', d.achi);
    S.scores = fetchS('score', d.scores);
  }
  function persist() {
    store('settings', S.settings); store('achi', S.achi); store('score', S.scores);
    if (S.save) store('save', S.save); else try { localStorage.removeItem('gigosave'); } catch (e) { }
    if (S.msave) store('msave', S.msave); else try { localStorage.removeItem('gigomsave'); } catch (e) { }
  }

  function unlockedPlaygrounds() { return PLAYGROUND_THRESH.filter(t => S.achi.secrets >= t).length; }
  function unlockedDm() { return 5 + DM_THRESH.filter(t => S.achi.secrets >= t).length; }
  function unlockedRace() { return 5 + RACE_THRESH.filter(t => S.achi.secrets >= t).length; }

  function cheatUnlockAll() {
    S.achi.sp = 35; S.achi.mp = 27; S.achi.secrets = 40;
    S.achi.secretFlags = SECRETS.map(n => Array(n).fill(true));
    for (const id of Object.keys(GOODIES)) S.achi.goodieFlags[id] = true;
    persist();
    Assets.play('switch');
    open(44);
  }

  // ---- screens ----
  // ids follow class s where practical: 0 main, 1 pause, 3 settings, 4 choose map,
  // 5 SP menu, 12 about, 2 help, 13/14/16 confirms, 17 MP setup, 20/21/22 lists,
  // 26 name entry, 28 highscores, 44/45 playgrounds, 46 goodies, 49 quit-app.

  const backTo = { 4: 5, 5: 0, 3: 0, 15: 1, 12: 0, 2: 0, 13: 1, 14: 1, 16: 5, 17: 0,
                   20: 17, 21: 17, 22: 17, 23: 17, 45: 17, 47: 17, 44: 5, 46: 5,
                   26: 0, 28: 0, 29: 0, 30: 0, 31: 0, 32: 0, 48: 17, 49: 0, 40: 40, 50: 50 };

  function open(id, keepSel) {
    if (!keepSel) { S.selStack.push(S.sel); S.sel = 0; }
    S.screen = id; S.scroll = 0; S.marquee = 0; S.widget = 0; S.page = null; S.pageLine = 0;
    build(id);
    Game.active = false;
    if (id === 0 && S.settings.musicVol > 0) Assets.music(true);
  }

  function goBack() {
    Assets.play('CLICK015');
    if (S.screen === 3 || S.screen === 15) persist();  // settings applied on leave
    if (S.screen === 15) { open(1, true); resume(); return; }
    if (S.screen === 1) { resume(); return; }
    const t = backTo[S.screen];
    if (t !== undefined && t !== S.screen) { S.sel = S.selStack.pop() || 0; S.screen = t; build(t); }
  }

  function resume() { Game.active = true; S.screen = -1; Assets.music(false); }

  function textPage(strIds, extra) {
    S.widget = 1; S.pageLine = 0;
    let lines = [];
    for (const sid of strIds) {
      const fid = Font.fontOf(sid);
      lines = lines.concat(Font.wrap(fid, Font.encode(fid, Font.str(sid)), View.w - 74)
        .map(l => ({ f: fid, l })));
      lines.push({ f: 3, l: [] });
    }
    if (extra) for (const s of extra) lines.push({ f: 3, l: Font.encode(3, s) });
    S.page = lines;
  }

  function confirm(strId) {
    S.widget = 0;
    S.listIds = [8, 9];  // yes / no
    textHeader = strId;
  }
  let textHeader = -1;

  function build(id) {
    textHeader = -1; S.listIds = []; S.listValues = []; S.widget = 0; S.list = null;
    switch (id) {
      case 0: S.listIds = [13, 26, 60, 11, 10, 29, 5]; break;
      case 1: S.listIds = Game.mode >= 2 ? [12, 6, 46, 11, 10, 29, 30] : [12, 6, 11, 10, 29, 30]; break;
      case 3: case 15:
        S.listIds = [33, 105, 34, 82, 62];
        S.listValues = [S.settings.soundVol, S.settings.musicVol,
                        S.settings.vibration ? 8 : 9, S.settings.hints ? 8 : 9,
                        [64, 67, 63][S.settings.detail]];
        break;
      case 5: S.listIds = (S.save ? [106] : []).concat([27, 28, 74, 88]); break;
      case 17: S.listIds = (S.msave ? [106] : []).concat([27, 43, 44, 45, 94, 74]); break;
      case 2: textPage([1, 2]); break;
      case 12: textPage([0]); break;
      case 13: confirm(125); break;
      case 14: confirm(31); break;
      case 16: confirm(32); break;
      case 23: confirm(32); break;
      case 49: confirm(103); break;
      case 4: case 47: {  // choose map: completed SP campaign levels
        S.list = [];
        for (let i = 1; i <= Math.min(S.achi.sp, 35); i++) {
          const found = S.achi.secretFlags[i - 1].filter(Boolean).length;
          const tot = SECRETS[i - 1];
          S.list.push({ label: levelName(i) + (tot ? ' (' + found + '/' + tot + ')' : ''), lvl: i });
        }
        if (!S.list.length) { textPage([86]); }
        break;
      }
      case 20: {  // coop maps
        S.list = [];
        for (let i = 0; i < Math.min(S.achi.mp, 27); i++) S.list.push({ label: levelName(41 + i), lvl: 41 + i });
        if (!S.list.length) textPage([95]);
        break;
      }
      case 21: { S.list = []; for (let i = 0; i < unlockedDm(); i++) S.list.push({ label: levelName(68 + i), lvl: 68 + i }); break; }
      case 22: { S.list = []; for (let i = 0; i < unlockedRace(); i++) S.list.push({ label: levelName(78 + i), lvl: 78 + i }); break; }
      case 44: case 45: {
        S.list = [];
        for (let i = 0; i < unlockedPlaygrounds(); i++) S.list.push({ label: levelName(36 + i), lvl: 36 + i });
        if (!S.list.length) textPage([87]);
        break;
      }
      case 46: {  // goodies
        const codes = [];
        for (const [lid, code] of Object.entries(GOODIES))
          if (S.achi.goodieFlags[lid]) codes.push(String(code));
        if (codes.length) textPage([89], codes); else textPage([96]);
        break;
      }
      case 28: case 29: {  // highscores
        const rows = [];
        const fmt = t => Math.floor(t / 60000) + ':' + String(Math.floor(t / 1000) % 60).padStart(2, '0');
        const lbl = ['singleplayer', 'singleplayer', 'multiplayer', 'multiplayer'];
        let any = false;
        for (let i = 0; i < 4; i++) {
          const s = S.scores[i];
          if (!s) continue;
          any = true;
          rows.push(lbl[i] + ' ' + (i % 2 ? Font.str(38) : Font.str(37)) + ' ' +
                    s.name + ' ' + (i % 2 ? fmt(s.time) : s.score));
        }
        if (any) textPage([60], rows); else textPage([61]);
        break;
      }
      case 26: S.widget = 2; S.input = S.settings.name || ''; S.inputMax = 11; break;
      case 40: confirm(19); break;   // enable sound?
      case 50: confirm(104); break;  // enable music?
    }
  }

  // ---- selection / activation ----
  function startLevel(lvlId, mode, submode) {
    Assets.music(false);
    S.screen = -1;
    Game.start(lvlId, mode, submode);
  }

  function activate() {
    const id = S.screen;
    Assets.play('CLICK015');
    // yes/no confirms
    if (textHeader >= 0) {
      const yes = S.listIds[S.sel] === 8;
      switch (id) {
        case 13: if (yes) { Game.restartLevel(); resume(); } else goBack(); return;
        case 14: if (yes) { Game.abort(); open(0); } else goBack(); return;
        case 16: if (yes) { S.save = null; persist(); startLevel(0, 1, 0); } else goBack(); return;
        case 23: if (yes) { S.msave = null; persist(); startLevel(41, 2, 2); } else goBack(); return;
        case 49: if (yes) { try { window.close(); } catch (e) { } } else goBack(); return;
        case 40: S.settings.soundVol = yes ? 5 : 0; open(50); return;
        case 50: S.settings.musicVol = yes ? 3 : 0; persist(); open(0); return;
      }
      return;
    }
    if (S.list && S.list.length && (id === 4 || id === 20 || id === 21 || id === 22 || id === 44 || id === 45 || id === 47)) {
      const e = S.list[S.sel];
      if (!e) return;
      const mp = (id !== 4 && id !== 44);
      if (id === 21) startLevel(e.lvl, 2, 4);
      else if (id === 22) startLevel(e.lvl, 2, 5);
      else if (id === 20 || id === 47 || id === 45) startLevel(e.lvl, 2, 3);
      else startLevel(e.lvl, 1, 1);
      return;
    }
    const sid = S.listIds[S.sel];
    switch (id) {
      case 0:
        switch (sid) {
          case 13: open(5); break;
          case 26: open(17); break;   // multiplayer -> local setup
          case 60: open(28); break;
          case 11: open(3); break;
          case 10: open(2); break;
          case 29: open(12); break;
          case 5: open(49); break;
        }
        break;
      case 5:
        switch (sid) {
          case 106: startLevel(S.save.next, 1, 0); break;
          case 27: if (S.save) startLevel(S.save.next, 1, 0); else open(16); break;
          case 28: open(4); break;
          case 74: open(44); break;
          case 88: open(46); break;
        }
        break;
      case 17:
        switch (sid) {
          case 106: startLevel(S.msave.next, 2, 2); break;
          case 27: if (S.msave) startLevel(S.msave.next, 2, 2); else open(23); break;
          case 43: open(20); break;
          case 44: open(21); break;
          case 45: open(22); break;
          case 94: open(47); break;
          case 74: open(45); break;
        }
        break;
      case 1:
        switch (sid) {
          case 12: resume(); break;
          case 6: if (Game.submode === 4 || Game.submode === 5) open(48); else open(13); break;
          case 46: Game.abort(); open(17); break;
          case 11: open(15); break;
          case 10: open(2); break;
          case 29: open(12); break;
          case 30: open(14); break;
        }
        break;
      case 3: case 15: settingsAdjust(1, true); break;
      case 26: {  // save highscore name
        S.settings.name = S.input.substring(0, 11);
        const qual = S.pendingScore;
        if (qual) {
          S.scores[qual.slot] = { name: S.settings.name, score: qual.score, time: qual.time };
          S.pendingScore = null;
        }
        persist();
        open(30);
        break;
      }
      case 30: case 31: case 32: case 48: goBack(); break;
    }
  }

  function settingsAdjust(dir, activateOnly) {
    const i = S.sel;
    const st = S.settings;
    switch (S.listIds[i]) {
      case 33: st.soundVol = Math.min(10, Math.max(0, st.soundVol + dir)); Assets.sfxOn = st.soundVol > 0; Assets.play('CLICK015'); break;
      case 105: st.musicVol = Math.min(10, Math.max(0, st.musicVol + dir)); Assets.music(st.musicVol > 0 && S.screen === 3); break;
      case 34: st.vibration = !st.vibration; break;
      case 82: st.hints = !st.hints; break;
      case 62: st.detail = (st.detail + (dir > 0 ? 1 : 2)) % 3; break;
    }
    build(S.screen);
  }

  // ---- called by Game on progress events ----
  function onLevelComplete(lvlId, score, time) {
    if (Game.submode === 0) {         // SP campaign
      if (lvlId >= S.achi.sp && lvlId >= 1 && lvlId <= 35) S.achi.sp = Math.max(S.achi.sp, lvlId);
      if (lvlId === 35) { finishCampaign(score, time, false); return; }
      S.save = { next: lvlId + 1, score, time };
      persist();
      startLevel(lvlId + 1, 1, 0);
    } else if (Game.submode === 2) {  // coop campaign
      const idx = lvlId - 41;
      S.achi.mp = Math.max(S.achi.mp, idx + 1);
      if (lvlId === 67) { finishCampaign(score, time, true); return; }
      S.msave = { next: lvlId + 1, score, time };
      persist();
      startLevel(lvlId + 1, 2, 2);
    } else {
      persist();
      open(Game.mode >= 2 ? 17 : (lvlId >= 36 && lvlId <= 40 ? 44 : 4), false);
    }
  }

  function finishCampaign(score, time, mp) {
    if (mp) S.msave = null; else S.save = null;
    const sSlot = mp ? 2 : 0, tSlot = mp ? 3 : 1;
    let slot = -1;
    if (!S.scores[sSlot] || score > S.scores[sSlot].score) slot = sSlot;
    else if (!S.scores[tSlot] || time < S.scores[tSlot].time) slot = tSlot;
    persist();
    if (slot >= 0) { S.pendingScore = { slot, score, time }; open(26); }
    else open(31);
  }

  function onSecretFound(lvlId, secretIdx) {
    const f = S.achi.secretFlags[lvlId - 1];
    if (!f || secretIdx >= f.length || f[secretIdx]) return false;
    f[secretIdx] = true;
    S.achi.secrets++;
    let msg = null;
    if (PLAYGROUND_THRESH.includes(S.achi.secrets)) msg = Font.str(84);
    else if (DM_THRESH.includes(S.achi.secrets)) msg = Font.str(92);
    else if (RACE_THRESH.includes(S.achi.secrets)) msg = Font.str(93);
    persist();
    return msg || Font.levelText(19);
  }

  function onGoodieFound(lvlId) {
    if (S.achi.goodieFlags[lvlId]) return null;
    S.achi.goodieFlags[lvlId] = true;
    persist();
    return Font.str(85) + ' ' + GOODIES[lvlId];
  }

  // ---- input ----
  function key(code, corner) {
    // cheat buffers
    S.keyBuf.shift(); S.keyBuf.push(code);
    if (corner) { S.tapBuf.shift(); S.tapBuf.push(corner); }
    const K = S.keyBuf.join(','), T = S.tapBuf.join(',');
    if (S.screen === 5 && (K === '49,51,57,49,57,49' || T === '1,2,1,1,2,2')) { cheatUnlockAll(); return; }

    if (S.widget === 2) return;  // text input handled via keyChar
    const items = S.list && S.list.length ? S.list.length : S.listIds.length;
    switch (code) {
      case -1: case 50:  // up
        if (S.widget === 1) { S.pageLine = Math.max(0, S.pageLine - 1); }
        else if (items) { S.sel = (S.sel + items - 1) % items; Assets.play('CLICK015'); }
        break;
      case -2: case 56:  // down
        if (S.widget === 1) { S.pageLine++; }
        else if (items) { S.sel = (S.sel + 1) % items; Assets.play('CLICK015'); }
        break;
      case -3: case 52: if ((S.screen === 3 || S.screen === 15) && textHeader < 0) settingsAdjust(-1); break;
      case -4: case 54: if ((S.screen === 3 || S.screen === 15) && textHeader < 0) settingsAdjust(1); break;
      case -5: case 53: case -6: activate(); break;
      case -7: goBack(); break;
    }
  }

  function keyChar(ch) {
    if (S.widget !== 2) return;
    if (ch === '\b') S.input = S.input.slice(0, -1);
    else if (ch === '\n') activate();
    else if (S.input.length < S.inputMax && /[a-z0-9 .:\/@_-]/i.test(ch)) S.input += ch;
  }

  function tap(x, y) {
    const w = View.w, h = View.h;
    let corner = 0;
    if (x < w / 8 && y < h / 6) corner = 1;
    else if (x > w * 7 / 8 && y < h / 6) corner = 2;
    else if (x < w / 8 && y > h * 5 / 6) corner = 3;
    else if (x > w * 7 / 8 && y > h * 5 / 6) corner = 4;
    // soft buttons
    if (y > h - 40) {
      if (x < w / 3) { key(-6, corner); return; }
      if (x > w * 2 / 3) { key(-7, corner); return; }
    }
    if (S.widget === 2) { tapInput(x, y); return; }
    // list item tap
    const lay = layout();
    const items = S.list && S.list.length ? S.list : S.listIds;
    const rowH = rowHeight();
    const idx = Math.floor((y - lay.y) / rowH) + S.scroll;
    if (idx >= 0 && idx < items.length && y >= lay.y) {
      if (S.sel === idx) activate();
      else { S.sel = idx; Assets.play('CLICK015');
        if ((S.screen === 3 || S.screen === 15) && x > w / 2) settingsAdjust(1);
        else if ((S.screen === 3 || S.screen === 15)) settingsAdjust(-1);
      }
    } else if (S.widget === 1) {
      if (y < h / 2) S.pageLine = Math.max(0, S.pageLine - 3); else S.pageLine += 3;
    }
    S.tapBuf.shift(); S.tapBuf.push(corner);
    const T = S.tapBuf.join(',');
    if (S.screen === 5 && T === '1,2,1,1,2,2') cheatUnlockAll();
  }

  const INPUT_CHARS = 'abcdefghijklmnopqrstuvwxyz0123456789.-_ ';
  function tapInput(x, y) {
    const w = View.w;
    const cols = 8, cw = Math.floor((w - 20) / cols), ch = 34, gy = 120;
    const c = Math.floor((x - 10) / cw), r = Math.floor((y - gy) / ch);
    const i = r * cols + c;
    if (r >= 0 && c >= 0 && c < cols && i < INPUT_CHARS.length) keyChar(INPUT_CHARS[i]);
    else if (y > gy + ch * Math.ceil(INPUT_CHARS.length / cols)) keyChar('\b');
  }

  // ---- rendering ----
  function layout() {
    const tall = View.h >= 300;
    return { x: 35, w: View.w - 70, y: tall ? (View.h >= 300 ? 55 + (showLogo() ? 82 : 0) : 55) : 18 };
  }
  const showLogo = () => View.h >= 300;
  const rowHeight = () => (S.list && S.list.length ? 30 : 44);

  function drawBackground(ctx) {
    ctx.fillStyle = '#000';
    ctx.fillRect(0, 0, View.w, View.h);
    const I = Assets.images, w = View.w, h = View.h;
    const im242 = I[242], im243 = I[243];
    if (im242) {
      ctx.drawImage(im242, 10, 50); ctx.drawImage(im242, 50, 100);
      ctx.drawImage(im242, w - 40 - im242.width, 70);
      ctx.drawImage(im242, w - 100 - im242.width, 130);
      ctx.drawImage(im242, w - 20 - im242.width, h - 40 - im242.height);
      ctx.drawImage(im242, 30, h - 40 - im242.height);
    }
    if (im243) {
      ctx.drawImage(im243, w - 40 - im243.width, h - 10 - im243.height);
      ctx.drawImage(im243, 70 - im243.width, 70);
    }
    if (I[238]) ctx.drawImage(I[238], 0, 0);
    if (I[239]) ctx.drawImage(I[239], w - I[239].width, 0);
    if (I[240]) ctx.drawImage(I[240], w - I[240].width, h - I[240].height);
    if (I[241]) ctx.drawImage(I[241], 0, h - I[241].height);
    if (showLogo() && I[244]) ctx.drawImage(I[244], (w - I[244].width) >> 1, 20);
  }

  function softButtons(ctx, okId, backId) {
    const h = View.h;
    ctx.fillStyle = '#2f2f2f';
    if (okId != null) {
      const t = Font.str(okId), tw = Font.measure(3, Font.encode(3, t));
      ctx.fillRect(0, h - 30, tw + 18, 30);
      Font.drawText(ctx, 3, t, 9, h - 27);
    }
    if (backId != null) {
      const t = Font.str(backId), tw = Font.measure(3, Font.encode(3, t));
      ctx.fillRect(View.w - tw - 18, h - 30, tw + 18, 30);
      Font.drawText(ctx, 3, t, View.w - tw - 9, h - 27, 8);
    }
  }

  function draw(ctx) {
    S.anim++;
    drawBackground(ctx);
    const lay = layout();
    const I = Assets.images;

    if (textHeader >= 0) {  // confirm dialog
      const fid = Font.fontOf(textHeader);
      const lines = Font.wrap(fid, Font.encode(fid, Font.str(textHeader)), View.w - 74);
      let y = lay.y;
      ctx.fillStyle = '#4f504f';
      ctx.fillRect(24, y - 8, View.w - 48, lines.length * (Font.lineHeight(fid) + 1) + 110);
      Font.drawWrapped(ctx, fid, lines, View.w >> 1, y, 1);
      y += lines.length * (Font.lineHeight(fid) + 1) + 14;
      for (let i = 0; i < S.listIds.length; i++) {
        Font.drawText(ctx, i === S.sel ? 0 : 2, Font.str(S.listIds[i]), View.w >> 1, y, 1);
        y += 44;
      }
      softButtons(ctx, 7, 4);
      return;
    }

    if (S.widget === 1 && S.page) {  // text page
      const areaH = View.h - lay.y - 40;
      const lh = 25;
      const maxLines = Math.floor(areaH / lh);
      S.pageLine = Math.max(0, Math.min(S.pageLine, Math.max(0, S.page.length - maxLines)));
      let y = lay.y;
      ctx.fillStyle = '#4f504f';
      ctx.fillRect(24, y - 8, View.w - 48, areaH + 8);
      for (let i = S.pageLine; i < Math.min(S.page.length, S.pageLine + maxLines); i++) {
        Font.draw(ctx, S.page[i].f, S.page[i].l, 30, y);
        y += lh;
      }
      if (S.page.length > maxLines) {
        ctx.fillStyle = '#fff';
        const barH = Math.max(8, areaH * maxLines / S.page.length);
        ctx.fillRect(View.w - 30, lay.y + (areaH - barH) * S.pageLine / (S.page.length - maxLines), 4, barH);
      }
      softButtons(ctx, null, 4);
      return;
    }

    if (S.widget === 2) {  // name entry
      Font.drawStr(ctx, 56, View.w >> 1, lay.y, 1);
      ctx.fillStyle = '#4f504f';
      ctx.fillRect(30, lay.y + 50, View.w - 60, 30);
      Font.drawText(ctx, 3, S.input + (S.anim % 20 < 10 ? '_' : ''), 36, lay.y + 53);
      const cols = 8, cw = Math.floor((View.w - 20) / cols), chh = 34, gy = 120;
      for (let i = 0; i < INPUT_CHARS.length; i++) {
        const cx = 10 + (i % cols) * cw, cy = gy + Math.floor(i / cols) * chh;
        Font.drawText(ctx, 3, INPUT_CHARS[i] === ' ' ? '_' : INPUT_CHARS[i], cx + (cw >> 1), cy, 1);
      }
      Font.drawText(ctx, 3, 'del', View.w >> 1, gy + Math.ceil(INPUT_CHARS.length / cols) * chh + 8, 1);
      softButtons(ctx, 7, 4);
      return;
    }

    // item list
    const items = S.list && S.list.length ? S.list.map(e => e.label) : S.listIds.map(i => Font.str(i));
    const rowH = rowHeight();
    const areaH = View.h - lay.y - 44;
    const maxRows = Math.max(1, Math.floor(areaH / rowH));
    if (S.sel < S.scroll) S.scroll = S.sel;
    if (S.sel >= S.scroll + maxRows) S.scroll = S.sel - maxRows + 1;
    let y = lay.y;
    for (let i = S.scroll; i < Math.min(items.length, S.scroll + maxRows); i++) {
      const selected = i === S.sel;
      const fid = S.list && S.list.length ? (selected ? 0 : 2) : (selected ? 0 : 2);
      let label = items[i];
      if (S.listValues.length) {
        const v = S.listValues[i];
        label += typeof v === 'number' && (S.listIds[i] === 33 || S.listIds[i] === 105) ? v
               : (typeof v === 'number' ? Font.str(v) : v);
      }
      const scale = S.list && S.list.length ? 0.6 : 1;
      const enc = Font.encode(fid, label);
      const tw = Font.measure(fid, enc);
      if (S.list && S.list.length) {
        // level lists draw in the small font for density
        Font.drawText(ctx, selected ? 0 : 2, label, View.w >> 1, y, 1);
      } else {
        Font.draw(ctx, fid, enc, View.w >> 1, y, 1);
        if (selected && S.listValues.length) {
          const a = 3 * ((S.anim >> 2) & 1);
          if (I[231]) ctx.drawImage(I[231], lay.x - 20 - a, y + 8);
          if (I[232]) ctx.drawImage(I[232], View.w - lay.x + 4 + a, y + 8);
        }
      }
      y += rowH;
    }
    if (items.length > maxRows) {
      if (S.scroll > 0 && I[1000]) ctx.drawImage(I[1000], (View.w - 23) >> 1, lay.y - 38);
      if (S.scroll + maxRows < items.length && I[1001]) ctx.drawImage(I[1001], (View.w - 23) >> 1, View.h - 40);
    }
    softButtons(ctx, 7, S.screen === 0 ? 5 : 4);
  }

  return { S, LEVELS, SECRETS, GOODIES, levelName, loadAll, persist, open, goBack, resume,
           key, keyChar, tap, draw, activate, onLevelComplete, onSecretFound, onGoodieFound,
           unlockedPlaygrounds, unlockedDm, unlockedRace, cheatUnlockAll };
})();
