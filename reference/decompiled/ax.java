/*
 * Decompiled with CFR 0.152.
 */
/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class ax {
    public byte a;
    public h a;
    public boolean a;
    public static v[] a;
    k a;
    int a;
    public byte b;
    byte c;
    static int[][] a;
    public int b;
    public int c;
    public boolean b;

    public ax(k k2, byte by, int n2, int n3) {
        this.a = k2;
        this.b = by;
        this.b = n2;
        this.c = n3;
    }

    public final void a() {
        this.a = 0;
        this.a = -1;
        this.b = false;
        int n2 = this.b << 15;
        int n3 = this.c << 15;
        Object object = null;
        switch (this.b) {
            case 0: {
                object = al.a(32);
                this.c = 0;
                break;
            }
            case 1: {
                object = al.a(96, 32, false);
                this.c = 1;
                break;
            }
            case 7: {
                object = al.a(64, 32, false);
                this.c = 0;
                break;
            }
            case 2: {
                object = al.a(96, 32, false);
                this.c = 1;
                break;
            }
            case 8: {
                object = al.a(128, 32, false);
                this.c = 1;
                break;
            }
            case 6: {
                object = new bg[]{new bg(33, -63), new bg(31, 31), new bg(0, 32), new bg(0, -64)};
                al.a(object);
                this.c = 1;
                break;
            }
            case 3: {
                object = new bg[]{new bg(32, -64), new bg(32, 32), new bg(0, 32), new bg(0, -64)};
                al.a(object);
                this.c = 1;
                break;
            }
            case 10: {
                object = new bg[]{new bg(32, -96), new bg(32, 32), new bg(0, 32), new bg(0, -96)};
                al.a(object);
                this.c = 1;
                break;
            }
            case 4: {
                object = new bg[]{new bg(2, 2), new bg(62, 2), new bg(62, 62), new bg(1, 62)};
                al.a(object);
                this.c = (byte)2;
                n3 -= 32 << 10;
                break;
            }
            case 5: {
                object = al.a(16, 10);
                this.c = (byte)3;
                n2 += 16384;
                break;
            }
            case 9: {
                object = al.a(96, 21, false);
                this.c = (byte)4;
                break;
            }
            case 11: {
                object = al.a(256, 32, false);
                this.c = 1;
            }
        }
        this.a = null;
        this.a = new h(this.b == 5 ? 100000 : 0, false, false, this.b == 2 || this.b == 8 || this.b == 11, this.b == 0 || this.b == 4 || this.b == 7 || this.b == 9);
        int n4 = this.a.j = this.b == 2 || this.b == 8 || this.b == 11 ? 4 : 3;
        if (this.b == 9) {
            this.a.h = 2;
        }
        this.a.a = this;
        int n5 = this.b != 5 ? 1 : 0;
        h h2 = this.a;
        h2.d = n5 != 0 ? (h2.d |= 2) : (h2.d &= 0xFFFFFFFD);
        for (int i2 = 0; i2 < ((bg[])object).length; ++i2) {
            object[i2].a += n2;
            object[i2].b += n3;
        }
        this.a.a((bg[])object, this.b == 4 ? 51200 : 2048);
        if (this.b != 5) {
            this.a.a(1024, 1024, -512, -512);
            n2 = -512;
            n5 = 1024;
            h h3 = this.a;
            n2 = h3.a.size();
            n2 >>= 1;
            for (n3 = 0; n3 < n2; ++n3) {
                object = new ag((x)h3.a.elementAt(n3), (x)h3.a.elementAt(n2 + n3), 1024, -512, -1);
                h3.a((ag)object);
            }
        } else {
            this.a.a(1024, 512, -512, -512);
        }
        this.a.a();
        if (this.b == 6) {
            this.a.a[2].a = Integer.MAX_VALUE;
        }
        if (this.a.a != null && this.a.a.b) {
            this.a.a.a(this.a);
        }
    }

    public final void b() {
        int n2;
        this.a = (byte)2;
        for (n2 = 0; n2 < this.a.a.length; ++n2) {
            this.a.a[n2].b(this.a);
        }
        if (this.a.c != null) {
            for (n2 = 0; n2 < this.a.c.length; ++n2) {
                this.a.c[n2].a();
            }
        }
        bg bg2 = this.a.a();
        this.a.a.a(bg2.a >> 10, bg2.b >> 10, 0);
    }
}

