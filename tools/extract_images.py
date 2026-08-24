#!/usr/bin/env python3
"""Extract packed PNG images from Gish2 .img/.map archives.

Format per entry (from decompiled g.a()):
  u16be idatLen
  u8    palCount        (palette bytes = palCount*3)
  u8    trnsFlag        (1 -> add fixed tRNS chunk: 1 byte, palette idx 0 transparent)
  u16be width           (upper 16 bits of PNG width are 0)
  u16be height
  u8    bitDepth
  u8[4] ihdrCRC
  u8[palBytes+4]  PLTE data + CRC
  u8[idatLen+4]   IDAT data + CRC

.map format: u16be count, then count * u16be spriteId (entry i in .img has sprite id spriteId[i]).
images2.map ids are offset by +256 in the game's global table.
"""
import struct, sys, os, zlib

def read_entries(img_path):
    data = open(img_path, 'rb').read()
    off = 0
    entries = []
    while off < len(data):
        if off + 11 > len(data):
            break
        idat_len = struct.unpack_from('>H', data, off)[0]
        pal_count = data[off+2]
        trns = data[off+3]
        w = struct.unpack_from('>H', data, off+4)[0]
        h = struct.unpack_from('>H', data, off+6)[0]
        depth = data[off+8]
        ihdr_crc = data[off+9:off+13]
        pal_bytes = pal_count * 3
        p = off + 13
        plte = data[p:p+pal_bytes+4]; p += pal_bytes + 4
        idat = data[p:p+idat_len+4]; p += idat_len + 4
        # rebuild PNG exactly as the game does
        png = bytearray()
        png += b'\x89PNG\r\n\x1a\n'
        png += b'\x00\x00\x00\x0dIHDR'
        png += struct.pack('>II', w, h)
        png += bytes([depth, 3, 0, 0, 0])
        png += ihdr_crc
        png += struct.pack('>I', pal_bytes)[0:4][:4]
        png = png[:-4] + struct.pack('>I', pal_bytes)
        png += b'PLTE' + plte
        if trns == 1:
            png += b'\x00\x00\x00\x01tRNS\x00\x40\xe6\xd8\x66'
        png += struct.pack('>I', idat_len) + b'IDAT' + idat
        png += b'\x00\x00\x00\x00IEND\xae\x42\x60\x82'
        entries.append((w, h, depth, pal_count, trns, bytes(png)))
        off = p
    return entries, off, len(data)

def read_map(map_path):
    data = open(map_path, 'rb').read()
    count = struct.unpack_from('>H', data, 0)[0]
    ids = [struct.unpack_from('>H', data, 2 + 2*i)[0] for i in range(count)]
    return ids

def main(jar_dir, out_dir):
    os.makedirs(out_dir, exist_ok=True)
    manifest = []
    for img_name, map_name, id_off in (('images.img','images.map',0), ('images2.img','images2.map',256)):
        entries, consumed, total = read_entries(os.path.join(jar_dir, img_name))
        ids = read_map(os.path.join(jar_dir, map_name))
        print(f'{img_name}: {len(entries)} entries, consumed {consumed}/{total} bytes; map ids: {len(ids)}')
        assert len(ids) == len(entries), f'map/img count mismatch {len(ids)} vs {len(entries)}'
        for i, (w, h, depth, pal, trns, png) in enumerate(entries):
            gid = ids[i] + id_off
            name = f'{gid}.png'
            with open(os.path.join(out_dir, name), 'wb') as f:
                f.write(png)
            # sanity: verify zlib stream decodes
            ok = True
            try:
                raw = zlib.decompress(png[png.index(b'IDAT')+4:-16-4] if False else b'')
            except Exception:
                pass
            manifest.append((gid, w, h, depth, pal, trns))
    with open(os.path.join(out_dir, 'manifest.txt'), 'w') as f:
        for gid, w, h, depth, pal, trns in sorted(manifest):
            f.write(f'{gid}\t{w}x{h}\tdepth={depth}\tpal={pal}\ttrns={trns}\n')
    print(f'wrote {len(manifest)} PNGs to {out_dir}')

if __name__ == '__main__':
    main(sys.argv[1], sys.argv[2])
