// .lvl binary level parser — faithful port of Main.a(String,byte[]) + Main.a(DataInputStream).
// All fields are single bytes. File coords are 1-based tiles from the bottom-left;
// runtime rows are 0-based from the top. Tile = 32 px; physics units = px<<10.

const Level = (() => {
  const TILE = 32;

  // layer sprite bases (ab.a): layer0 bg=276, layer1 game=566, layer2 fg=407
  const LAYER_BASE = [276, 566, 407];

  // layer-1 semantics
  const AMBER = 43, COIN = 70, BREAKABLE = 14;
  const NONSOLID = new Set([-1, 8, 9, 13, 43, 70]);
  const SPECIAL_SOLID = new Set([1,2,3,4,7,10,11,12,16,17,37,38,39,40,64,65,66,67,68,71,72]);
  const SPIKES = new Set([7, 10, 11, 12, 68, 71, 72]);
  const HIDDEN_COVER = 69;
  // layer-2 water: 6/7 body, 36 surface, 37 waterfall
  const WATER_BODY = new Set([6, 7]), WATER_SURFACE = 36, WATERFALL = 37;

  // tile class (Main.a(int,int)): -1 empty/special-nonsolid, 1 special solid, 0 auto-tiled solid
  function tileClass(id) {
    if (NONSOLID.has(id)) return -1;
    if (SPECIAL_SOLID.has(id)) return 1;
    return 0;
  }

  // rope material tables (k.a stiffness /1024, k.b rest-length mode, k.c break length px)
  const ROPE_MAT = [
    { stiff: 1.0,   rest: 1.0, breakLen: 0, pullOnly: false },
    { stiff: 1.0,   rest: 1.0, breakLen: 0, pullOnly: true  },
    { stiff: 0.666, rest: 1.5, breakLen: 0, pullOnly: false },
    { stiff: 0.333, rest: 1.0, breakLen: 0, pullOnly: false },
    { stiff: 1.0,   rest: 1.0, breakLen: 32, pullOnly: false },
    { stiff: 0.333, rest: 1.5, breakLen: 0, pullOnly: false },
  ];
  // rope point corner offsets within tile (px)
  const CORNER_OFF = [[0, 0], [32, 0], [32, 32], [0, 32], [16, 16]];

  // block kinds (ax): [w,h,anchor dx,dy in px], footprint tiles for rope attach
  const BLOCK_KINDS = [
    { w: 32,  h: 32, dy: 0,   foot: [[0, 0]] },
    { w: 96,  h: 32, dy: 0,   foot: [[0, 0], [1, 0], [2, 0]] },
    { w: 96,  h: 32, dy: 0,   foot: [[0, 0], [1, 0], [2, 0]], hang: true },
    { w: 32,  h: 96, dy: -64, foot: [[0, -2], [0, -1], [0, 0]] },
    { w: 64,  h: 64, dy: -32, foot: [[0, -1], [1, -1], [0, 0], [1, 0]], heavy: true },
    { w: 32,  h: 32, dy: 0,   foot: [[0, 0]], ball: true, r: 16 },
    { w: 32,  h: 96, dy: -64, foot: [[0, -2], [0, -1], [0, 0]], shear: true },
    { w: 64,  h: 32, dy: 0,   foot: [[0, 0], [1, 0]] },
    { w: 128, h: 32, dy: 0,   foot: [[0, 0], [1, 0], [2, 0], [3, 0]], hang: true },
    { w: 96,  h: 21, dy: 0,   foot: [], soft: true },
    { w: 32,  h: 128, dy: -96, foot: [] },
    { w: 256, h: 32, dy: 0,   foot: [[0,0],[1,0],[2,0],[3,0],[4,0],[5,0],[6,0],[7,0]], hang: true },
  ];

  // platform kinds (ac): tiles drawn horizontally/vertically with sprite 235
  const PLAT_KINDS = [
    { w: 96,  h: 32, tiles: 3, vert: false, dy: 0 },
    { w: 32,  h: 96, tiles: 3, vert: true,  dy: -64 },
    { w: 64,  h: 32, tiles: 2, vert: false, dy: 0 },
    { w: 32,  h: 288, tiles: 8, vert: true, dy: -256 },
  ];

  function parse(bytes) {
    let o = 0;
    const u8 = () => bytes[o++];
    const s8 = () => (bytes[o++] << 24) >> 24;

    const width = u8(), height = u8(), theme = s8();
    const lvl = { width, height, theme, entities: [], blocks: [], platforms: [],
                  ropes: [], hints: [], amberTotal: 0 };

    const nCre = u8(), nItm = u8();
    for (let i = 0; i < nCre + nItm; i++) {
      const fx = u8(), fy = u8(), type = u8();
      lvl.entities.push({
        type,
        col: fx - 1, row: height - fy,
        px: fx * TILE - 16, py: (height - fy) * TILE + 16,
      });
    }

    const nBlocks = u8();
    for (let i = 0; i < nBlocks; i++) {
      const fx = u8(), fy = u8(), kind = u8() - 1;
      lvl.blocks.push({ kind, col: fx - 1, row: height - fy,
                        x: (fx - 1) * TILE, y: (height - fy) * TILE });
    }

    const nButtons = u8(), nPlats = u8();
    for (let i = 0; i < nPlats; i++) {
      const kind = s8() - 1, speed = s8(), act = s8();
      const x1 = u8(), y1 = u8(), x2 = u8(), y2 = u8();
      const pk = PLAT_KINDS[kind] || PLAT_KINDS[0];
      const p = { kind, speed, act,
        ax: (x1 - 1) * TILE, ay: (height - y1) * TILE + pk.dy,
        bx: (x2 - 1) * TILE, by: (height - y2) * TILE + pk.dy };
      if (act !== 0 && act !== 5) { const bx = u8(), by = u8(); p.btnCol = bx - 1; p.btnRow = height - by; }
      lvl.platforms.push(p);
    }

    const nRopes = u8();
    for (let i = 0; i < nRopes; i++) {
      const head = u8();
      const material = head >> 4, nPts = head & 0x0f;
      const pts = [];
      for (let j = 0; j < nPts; j++) {
        const fx = u8(), fy = u8(), corner = s8();
        const col = fx - 1, row = height - fy;
        const co = CORNER_OFF[corner] || CORNER_OFF[4];
        pts.push({ col, row, corner, x: col * TILE + co[0], y: row * TILE + co[1] });
      }
      lvl.ropes.push({ material, pts });
    }

    // 3-layer tile map: column-major, bottom row first, 3 bytes per cell, stored = id+1
    const t = [[], [], []];
    for (let l = 0; l < 3; l++) {
      t[l] = new Array(width);
      for (let x = 0; x < width; x++) t[l][x] = new Int16Array(height);
    }
    for (let x = 0; x < width; x++)
      for (let y = height - 1; y >= 0; y--)
        for (let l = 0; l < 3; l++)
          t[l][x][y] = bytes[o++] - 1;
    lvl.tiles = t;

    const nHints = u8();
    for (let i = 0; i < nHints; i++) {
      const fx = u8(), fy = u8(), id = u8() - 1;
      lvl.hints.push({ col: fx - 1, row: height - fy, id });
    }
    lvl.bytesConsumed = o;

    // count collectible amber like the loader does (k.l)
    for (let x = 0; x < width; x++)
      for (let y = 0; y < height; y++)
        if (t[1][x][y] === AMBER) lvl.amberTotal++;

    return lvl;
  }

  function load(name) {                    // name e.g. "1/01_s0", "intro", "coop/00"
    const bytes = Assets.bytesOf('levels/' + name + '.lvl');
    if (!bytes) return null;
    const lvl = parse(bytes);
    lvl.name = name;
    return lvl;
  }

  return { TILE, LAYER_BASE, AMBER, COIN, BREAKABLE, NONSOLID, SPECIAL_SOLID, SPIKES,
           HIDDEN_COVER, WATER_BODY, WATER_SURFACE, WATERFALL, ROPE_MAT, CORNER_OFF,
           BLOCK_KINDS, PLAT_KINDS, tileClass, parse, load };
})();
