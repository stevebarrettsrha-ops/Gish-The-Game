// Asset layer — decodes the ORIGINAL game data files at runtime.
// GAME_DATA maps JAR resource paths -> base64 bytes (built by tools/build.py).

const Assets = (() => {
  const bytesOf = path => {
    const b64 = GAME_DATA[path.replace(/^\//, '')];
    if (b64 == null) return null;
    const bin = atob(b64);
    const out = new Uint8Array(bin.length);
    for (let i = 0; i < bin.length; i++) out[i] = bin.charCodeAt(i);
    return out;
  };

  // ---- .img/.map unpacker: exact port of the midlet's PNG re-assembly (class g) ----
  // entry: u16be idatLen | u8 palCount | u8 trnsFlag | u16be w | u16be h |
  //        u8 depth | 4B ihdrCRC | palCount*3+4 B PLTE+CRC | idatLen+4 B IDAT+CRC
  function unpackImg(imgBytes) {
    const out = [];
    let o = 0;
    while (o + 13 <= imgBytes.length) {
      const idatLen = (imgBytes[o] << 8) | imgBytes[o + 1];
      const palBytes = imgBytes[o + 2] * 3;
      const trns = imgBytes[o + 3];
      const total = idatLen + palBytes + 69 + trns * 13;
      const png = new Uint8Array(total);
      let p = 0;
      const push = (...v) => { for (const x of v) png[p++] = x & 0xff; };
      push(0x89, 0x50, 0x4e, 0x47, 0x0d, 0x0a, 0x1a, 0x0a);
      push(0, 0, 0, 13, 73, 72, 68, 82);
      push(0, 0, imgBytes[o + 4], imgBytes[o + 5]);           // width
      push(0, 0, imgBytes[o + 6], imgBytes[o + 7]);           // height
      push(imgBytes[o + 8], 3, 0, 0, 0);                      // depth, palette type
      push(imgBytes[o + 9], imgBytes[o + 10], imgBytes[o + 11], imgBytes[o + 12]); // IHDR CRC
      push(0, 0, palBytes >> 8, palBytes, 80, 76, 84, 69);    // PLTE header
      let q = o + 13;
      for (let i = 0; i < palBytes + 4; i++) png[p++] = imgBytes[q++]; // PLTE + CRC
      if (trns === 1) push(0, 0, 0, 1, 116, 82, 78, 83, 0, 0x40, 0xe6, 0xd8, 0x66);
      push(0, 0, idatLen >> 8, idatLen, 73, 68, 65, 84);
      for (let i = 0; i < idatLen + 4; i++) png[p++] = imgBytes[q++];  // IDAT + CRC
      push(0, 0, 0, 0, 73, 69, 78, 68, 0xae, 0x42, 0x60, 0x82);
      out.push(png);
      o = q;
    }
    return out;
  }

  function readMap(mapBytes) {
    const n = (mapBytes[0] << 8) | mapBytes[1];
    const ids = [];
    for (let i = 0; i < n; i++) ids.push((mapBytes[2 + 2 * i] << 8) | mapBytes[3 + 2 * i]);
    return ids;
  }

  const images = {};          // global sprite id -> ImageBitmap/HTMLImageElement
  const pngBlobs = {};        // sprite id -> Blob (for lazy loads)

  function pngToImage(bytes) {
    return new Promise((resolve, reject) => {
      const blob = new Blob([bytes], { type: 'image/png' });
      const url = URL.createObjectURL(blob);
      const im = new Image();
      im.onload = () => { URL.revokeObjectURL(url); resolve(im); };
      im.onerror = e => { URL.revokeObjectURL(url); reject(e); };
      im.src = url;
    });
  }

  async function loadAllImages(onProgress) {
    const packs = [
      { img: 'images.img', map: 'images.map', off: 0 },
      { img: 'images2.img', map: 'images2.map', off: 256 },
    ];
    const jobs = [];
    for (const pk of packs) {
      const entries = unpackImg(bytesOf(pk.img));
      const ids = readMap(bytesOf(pk.map));
      for (let i = 0; i < entries.length; i++) {
        const gid = ids[i] + pk.off;
        jobs.push(pngToImage(entries[i]).then(im => { images[gid] = im; }));
      }
    }
    // loose PNG overrides (loaded from original files, same paths as the midlet)
    const loose = {
      471: 'img_gish/dark_corner_alpha.png',
      // tile-layer alpha variants are looked up by path at draw time; see Tiles
    };
    for (const [gid, path] of Object.entries(loose)) {
      jobs.push(pngToImage(bytesOf(path)).then(im => { images[gid] = im; }));
    }
    let done = 0;
    for (const j of jobs) j.then(() => { onProgress && onProgress(++done, jobs.length); });
    await Promise.all(jobs);
    return images;
  }

  async function loadLoose(path) {
    return pngToImage(bytesOf(path));
  }

  // ---- audio: WebAudio, decoding the original 8kHz/8-bit WAVs + mp3 music ----
  let actx = null;
  const sounds = {};
  let musicSrc = null, musicGain = null, musicBuf = null;
  let sfxOn = true, musicOn = true;

  function parseWav(bytes) {
    const dv = new DataView(bytes.buffer, bytes.byteOffset, bytes.byteLength);
    let o = 12, fmt = null, data = null;
    while (o + 8 <= bytes.length) {
      const id = String.fromCharCode(bytes[o], bytes[o + 1], bytes[o + 2], bytes[o + 3]);
      const len = dv.getUint32(o + 4, true);
      if (id === 'fmt ') fmt = { ch: dv.getUint16(o + 10, true), rate: dv.getUint32(o + 12, true), bits: dv.getUint16(o + 22, true) };
      if (id === 'data') data = bytes.subarray(o + 8, o + 8 + len);
      o += 8 + len + (len & 1);
    }
    if (!fmt || !data) return null;
    const n = data.length / (fmt.bits / 8) / fmt.ch;
    const buf = actx.createBuffer(fmt.ch, n, fmt.rate);
    for (let c = 0; c < fmt.ch; c++) {
      const chd = buf.getChannelData(c);
      for (let i = 0; i < n; i++) {
        if (fmt.bits === 8) chd[i] = (data[i * fmt.ch + c] - 128) / 128;
        else chd[i] = dv.getInt16(data.byteOffset - bytes.byteOffset + (i * fmt.ch + c) * 2, true) / 32768;
      }
    }
    return buf;
  }

  async function initAudio() {
    if (actx) return;
    actx = new (window.AudioContext || window.webkitAudioContext)();
    const names = ['CLICK015', 'amber', 'blockbreak', 'bobattack', 'gishhit', 'necksnap',
      'ropebreak', 'splash', 'squish', 'switch', 'tarball', 'visattack'];
    for (const n of names) {
      try { sounds[n] = parseWav(bytesOf('sound/' + n + '.wav')); } catch (e) { }
    }
    try {
      const mp3 = bytesOf('sound/sewer.mp3');
      musicBuf = await actx.decodeAudioData(mp3.buffer.slice(mp3.byteOffset, mp3.byteOffset + mp3.byteLength));
    } catch (e) { musicBuf = null; }
  }

  function play(name) {
    if (!actx || !sfxOn || !sounds[name]) return;
    if (actx.state === 'suspended') actx.resume();
    const src = actx.createBufferSource();
    src.buffer = sounds[name];
    src.connect(actx.destination);
    src.start();
  }

  function music(on) {
    musicOn = on;
    if (!actx) return;
    if (on && musicBuf && !musicSrc) {
      musicSrc = actx.createBufferSource();
      musicSrc.buffer = musicBuf;
      musicSrc.loop = true;
      musicGain = actx.createGain();
      musicGain.gain.value = 0.55;
      musicSrc.connect(musicGain).connect(actx.destination);
      musicSrc.start();
    } else if (!on && musicSrc) {
      try { musicSrc.stop(); } catch (e) { }
      musicSrc = null;
    }
  }

  return {
    bytesOf, loadAllImages, loadLoose, images,
    initAudio, play, music,
    get sfxOn() { return sfxOn; }, set sfxOn(v) { sfxOn = v; },
    get musicOn() { return musicOn; },
    resumeAudio() { if (actx && actx.state === 'suspended') actx.resume(); },
  };
})();
