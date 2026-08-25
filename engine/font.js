// Bitmap font + text system — faithful port of at.java.
// Renders from the ORIGINAL glyph atlases (sprites 8, 7, 237, 230) and parses
// the original t_pointer.en / tl_pointer.en string tables at runtime.

const Font = (() => {
  const CHARSET = 'abcdefghijklmnopqrstuvwxyz0123456789.,:;\'"!?/()#@*-_ˇ§¨°´`˜¿¡ßç';
  const MARK_FIRST = 52, MARK_LAST = 58; // combining marks: ˇ § ¨ ° ´ ` ˜

  // glyph advance widths (hardcoded in at.java; fonts 0 and 2 share)
  const W02 = [17,17,17,17,17,17,17,17,9,17,17,17,25,17,17,17,17,17,17,21,17,17,25,19,17,17,
               17,11,17,17,17,17,17,17,17,17,9,9,9,9,9,18,9,17,21,13,13,25,21,15,17,17,
               17,17,17,9,16,16,17,17,9,19,17];
  const W3  = [11,11,11,11,11,11,11,11,5,11,11,11,17,11,11,11,11,11,11,13,11,11,17,11,11,11,
               11,7,11,11,11,11,11,11,11,11,5,5,5,5,5,11,5,11,17,9,9,15,14,11,13,11,
               7,7,11,7,8,7,9,11,5,13,11];
  const FONTS = [
    { img: 8,   widths: W02, rows: 5, space: 17, track: -1, charset: CHARSET },
    { img: 7,   widths: [6, 3, 6], rows: 1, space: 6, track: 1, charset: '013' },
    { img: 237, widths: W02, rows: 5, space: 17, track: -1, charset: CHARSET },
    { img: 230, widths: W3,  rows: 3, space: 11, track: 0,  charset: CHARSET },
  ];

  // accented char -> [base, combining mark] (appended AFTER the base)
  const DECOMP = {};
  const addD = (marks, mark) => { for (const [acc, base] of marks) DECOMP[acc] = base + mark; };
  addD([['ě','e'],['š','s'],['č','c'],['ř','r'],['ž','z'],['ď','d'],['ť','t'],['ň','n']], 'ˇ');
  addD([['ý','y'],['á','a'],['í','i'],['é','e'],['ú','u'],['ó','o']], '´');
  addD([['ů','u'],['å','a']], '°');
  addD([['ä','a'],['ö','o'],['ü','u']], '¨');
  addD([['â','a'],['ê','e'],['û','u'],['ô','o'],['î','i']], '§');
  addD([['è','e'],['à','a'],['ù','u'],['ò','o'],['ì','i']], '`');
  addD([['ñ','n']], '˜');
  DECOMP['’'] = "'"; DECOMP['‘'] = "'"; DECOMP['´'] = "'";

  // computed per font at init: glyph atlas x/y and line height
  function initFont(f) {
    const im = Assets.images[f.img];
    f.atlas = im;
    f.lineH = Math.floor(im.height / f.rows);
    f.gx = []; f.gy = [];
    let x = 0, y = 0;
    for (let i = 0; i < f.widths.length; i++) {
      if (x + f.widths[i] > im.width) { x = 0; y += f.lineH; }
      f.gx[i] = x; f.gy[i] = y;
      x += f.widths[i];
    }
  }

  function init() { for (const f of FONTS) initFont(f); }

  // string -> glyph code bytes (at.a(int, String))
  function encode(fid, str) {
    const f = FONTS[fid];
    const out = [];
    for (let ch of str.toLowerCase()) {
      if (DECOMP[ch]) {
        for (const c2 of DECOMP[ch]) {
          const gi = f.charset.indexOf(c2);
          if (gi >= 0) out.push(gi);
        }
        continue;
      }
      if (ch === ' ') { out.push(-1); continue; }
      if (ch === '~') { out.push(-2); continue; }
      if (ch === '^') { out.push(-3); continue; }
      const gi = f.charset.indexOf(ch);
      if (gi >= 0) out.push(gi);
    }
    return out;
  }

  function isMark(fid, g) { return fid !== 1 && g >= MARK_FIRST && g <= MARK_LAST; }

  // measure (at.a(int, byte[], int))
  function measure(fid, codes) {
    const f = FONTS[fid];
    let w = 0;
    for (const g of codes) {
      if (g === -3 || g === -2) continue;
      if (g === -1) { w += f.space + f.track; continue; }
      if (isMark(fid, g)) continue;
      w += f.widths[g] + f.track;
    }
    return w;
  }

  const lineHeight = fid => FONTS[fid].lineH;

  // integer -> digit glyphs
  function encodeNumber(fid, n) {
    return encode(fid, String(Math.max(0, Math.floor(n))));
  }

  // draw glyph codes (single line) with MIDP-style anchor
  function draw(ctx, fid, codes, x, y, anchor = 0) {
    const f = FONTS[fid];
    if (anchor & 8) x -= measure(fid, codes);
    else if (anchor & 1) x -= measure(fid, codes) >> 1;
    if (anchor & 0x20 || anchor & 0x40) y -= f.lineH;
    else if (anchor & 2) y -= f.lineH >> 1;
    let prevW = 0;
    for (let i = 0; i < codes.length; i++) {
      const g = codes[i];
      if (g === -3 || g === -2) continue;
      if (g === -1) { x += f.space + f.track; prevW = 0; continue; }
      if (isMark(fid, g)) {
        const mx = x - prevW - f.track + ((prevW - f.widths[g]) >> 1);
        ctx.drawImage(f.atlas, f.gx[g], f.gy[g], f.widths[g], f.lineH, mx, y, f.widths[g], f.lineH);
        continue;
      }
      ctx.drawImage(f.atlas, f.gx[g], f.gy[g], f.widths[g], f.lineH, x, y, f.widths[g], f.lineH);
      prevW = f.widths[g] + f.track;
      x += prevW;
    }
  }

  function drawText(ctx, fid, str, x, y, anchor = 0) {
    draw(ctx, fid, encode(fid, str), x, y, anchor);
  }

  // greedy word wrap (at.a(int, byte[], int)); ! and ? stay glued to prior word
  function wrap(fid, codes, maxW) {
    const lines = [];
    let line = [], w = 0, word = [], wordW = 0;
    const f = FONTS[fid];
    const flushWord = () => {
      if (!word.length) return;
      if (line.length && w + f.space + f.track + wordW > maxW) {
        lines.push(line); line = []; w = 0;
      }
      if (line.length) { line.push(-1); w += f.space + f.track; }
      line = line.concat(word); w += wordW;
      word = []; wordW = 0;
    };
    for (let i = 0; i < codes.length; i++) {
      const g = codes[i];
      if (g === -2) { flushWord(); lines.push(line); line = []; w = 0; continue; }
      if (g === -1) {
        const nxt = codes[i + 1];
        if (nxt === 42 || nxt === 43) { continue; } // keep !/? attached
        flushWord(); continue;
      }
      word.push(g);
      if (g !== -3 && !isMark(fid, g)) wordW += f.widths[g] + f.track;
    }
    flushWord();
    if (line.length || !lines.length) lines.push(line);
    return lines;
  }

  function drawWrapped(ctx, fid, lines, x, y, anchor = 0) {
    for (const l of lines) { draw(ctx, fid, l, x, y, anchor); y += 1 + FONTS[fid].lineH; }
  }

  // ---- string tables from the original .en files ----
  // t_pointer: '|'-separated UTF-8, 0x00 terminator, int32be checksum (sum of bytes)
  const FONT3_IDS = new Set([0,1,2,16,19,22,23,24,25,31,32,47,53,54,55,58,59,61,85,86,87,89,
                             95,96,102,103,104,109,111,119,120,125,127,128,129,130,131,132,
                             15,97,98,99]);
  const strings = [];        // t_pointer entries (raw strings)
  const levelTexts = [];     // tl_pointer entries

  function parseTable(bytes, checkSum) {
    const entries = [];
    let cur = [], sum = 0, i = 0;
    for (; i < bytes.length; i++) {
      const b = bytes[i];
      if (b === 0) break;
      if (b === 0x7c) { entries.push(cur); cur = []; continue; }
      cur.push(b); sum += b;
    }
    entries.push(cur);
    if (checkSum) {
      const dv = new DataView(bytes.buffer, bytes.byteOffset + i + 1, 4);
      if (dv.getInt32(0, false) !== sum) console.warn('string table checksum mismatch');
    }
    const dec = new TextDecoder('utf-8');
    return entries.map(e => dec.decode(new Uint8Array(e)));
  }

  function loadStrings() {
    strings.length = 0;
    for (const s of parseTable(Assets.bytesOf('t_pointer.en'), true)) strings.push(s);
    // splice version into credits entry (2nd line), like at.f() does
    strings[0] = strings[0].replace('~', '~v1.0.0~');
    levelTexts.length = 0;
    for (const s of parseTable(Assets.bytesOf('tl_pointer.en'), false)) levelTexts.push(s);
  }

  const fontOf = id => (id === 108 ? 2 : (FONT3_IDS.has(id) ? 3 : 0));
  const str = id => strings[id] || '';
  const levelText = slot => levelTexts[slot] || '';

  // draw a stored UI string by id in its assigned font
  function drawStr(ctx, id, x, y, anchor = 0) {
    drawText(ctx, fontOf(id), str(id), x, y, anchor);
  }

  return { init, loadStrings, encode, encodeNumber, measure, lineHeight, draw, drawText,
           drawStr, wrap, drawWrapped, str, fontOf, levelText, FONTS };
})();
