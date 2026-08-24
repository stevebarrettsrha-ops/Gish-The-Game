/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class ae {
    public byte a;
    public as a;
    public as b;
    private int b;
    public boolean a;
    private d a;
    private k a;
    private static final int[][] a = new int[][]{{279, 280, 288, 293}, {218, 219, 230, 234, 227}, {238, -1, 239, 242, 248}, {251, -1, 256, 252, 304}, {297, 306}, {300}};
    private static final int[][] b = new int[][]{{1, 8, 4, 4, 0}, {1, 8, 4, 4, 3}, {1, 0, 3, 6, 3}, {1, 0, 3, 4, 2}, {3, 2}, {4}};
    private static final byte[][] a = new byte[][]{{1, 8, 4, 4, 0}, {1, 8, 4, 4, 0}, {1, 0, 3, 6, 3}, {8, 0, 3, 4, 0}, {4, 0, 12, 2, 0}, {4, 0, 10, 0, 0}};
    private static final byte[][] b = new byte[][]{{0, 2, 2, 4, 0}, {0, 2, 4, 6, 0}, {0, 0, 4, 4, 0}, {2, 0, 4, 4, 0}, {4, 0, 2, 0, 0}, {4, 0, 2, 0, 0}};
    private static int[] c = new int[]{3, 1, 4, -1, 2, 1, 1};
    public byte b;
    public byte c;
    public static final int[] a = new int[]{0, 1, 2, -1, 3, 4, 5};
    public byte d;
    private static Image[][][] a;
    public boolean b;
    private boolean d;
    public int a;
    private int c;
    private int[] d;
    public boolean c;
    private static final int[] e;
    private static final int[] f;
    private static final int[] g;
    private static int[] h;
    private static final int[] i;
    public static final int[] b;
    private static final int[] j;

    public ae(k k2) {
        this.a = k2;
        this.a = (byte)2;
    }

    public final void a(byte by, bg bg2, boolean bl) {
        this.d = by;
        this.a = 0;
        this.a = false;
        this.b = false;
        this.a = null;
        this.b = null;
        this.b = 0;
        this.c = 0;
        this.a = -1;
        this.c = 0;
        if (this.d == 4) {
            this.a = new as(new x(bg2, 0), 39936);
            this.a.b = 0;
            this.d = false;
            return;
        }
        if (this.d == 0) {
            this.a = new as(new x(bg2, 6144), 12288);
            this.d = true;
        } else if (this.d == 1) {
            bg2.b += 3072;
            this.a = new as(new x(bg2, 16384), 13312);
            this.b = new as(new x(bg2.b(new bg(0, 32768)), 16384), 14336);
            this.b.d = 2;
            this.b.a = this;
            this.a.d = 5;
            this.a.a = this;
            this.b.b = 0;
            if (this.a.a != null && this.a.a.b) {
                this.a.a.a(this.b);
            }
            this.b = this.b.a.a.b - this.a.a.a.b;
            this.b.a = this.a;
            this.a.a = this.b;
            this.d = true;
        } else if (this.d == 2) {
            this.a = new as(new x(bg2, 1000000), 15360);
            this.d = false;
        } else if (this.d == 5) {
            this.a = new as(new x(bg2.b(new bg(0, 32768)), Integer.MAX_VALUE), 45056);
            this.a.b = 0;
            this.c = 40;
            this.d = false;
        } else if (this.d == 6) {
            this.a = new as(new x(bg2.b(new bg(0, 13312)), Integer.MAX_VALUE), 13312);
            this.a.b = 0;
            this.d = false;
        }
        if (this.b == null) {
            this.a.d = 2;
            this.a.a = this;
        }
        if (this.d != 5 && this.d != 6) {
            this.a.b = 102;
        }
        if (this.a.a != null && this.a.a.b) {
            this.a.a.a(this.a);
        }
    }

    public final int[] a() {
        if (this.d == 6) {
            if (this.d == null) {
                this.d = new int[4];
            }
            this.d[0] = this.a.a.a.a - this.a.a;
            this.d[2] = this.a.a.a.a + this.a.a;
            this.d[3] = this.d[1] = this.a.a.a.b + this.a.a * (this.c < 0 ? -1 : 1);
            if (this.c < 0) {
                this.d[3] = this.d[3] + (-this.c << 15);
            } else {
                this.d[1] = this.d[1] - (this.c << 15);
            }
            return this.d;
        }
        if (this.b == null) {
            return this.a.a();
        }
        if (this.d == null) {
            this.d = new int[4];
        }
        int[] nArray = this.b.a();
        this.d[0] = nArray[0];
        this.d[1] = nArray[1];
        this.d[2] = nArray[2];
        this.d[3] = nArray[3];
        if (this.d == 1) {
            this.d[3] = this.d[3] + 38912;
        }
        return this.d;
    }

    public static void a(g g2) {
        if (a == null) {
            a = new Image[a.length][][];
        }
        for (int i2 = 0; i2 < a.length; ++i2) {
            if (g2.a(256 + a[i2][0]) == null) {
                ae.a[i2] = null;
                continue;
            }
            ae.a[i2] = new Image[a[i2].length][];
            for (int i3 = 0; i3 < a[i2].length; ++i3) {
                if (a[i2][i3] == -1) continue;
                ae.a[i2][i3] = g2.a(256 + a[i2][i3], b[i2][i3], i2 < 2);
            }
        }
    }

    public static void a(g g2, int n2) {
        for (int i2 = 0; i2 < a[n2].length; ++i2) {
            if (a[n2][i2] == -1) continue;
            for (int i3 = 0; i3 < b[n2][i2]; ++i3) {
                g2.a(256 + a[n2][i2] + i3);
            }
        }
    }

    private int a() {
        switch (this.a) {
            case 0: 
            case 6: {
                return 0;
            }
            case 3: {
                return 1;
            }
            case 1: {
                return 2;
            }
            case 4: {
                return 3;
            }
            case 5: {
                return 4;
            }
        }
        return 0;
    }

    public final void a(byte by) {
        if (this.a.b != 4 && (this.d == 2 || this.d == 5) && by == 4) {
            boolean bl = false;
            int n2 = this.a.a.length;
            for (int i2 = 0; i2 < n2; ++i2) {
                if (this.a.a[i2].a != 2 || this.a.a[i2].d != 0) continue;
                bl = true;
                break;
            }
            if (this.d == 5) {
                this.c = 100;
            }
            if (bl && this.d == 2) {
                this.c = 45;
            }
            if (!bl) {
                return;
            }
        }
        this.a = by;
        this.b = 0;
        this.c = 0;
    }

    public final boolean a() {
        return this.a == 1 || this.a == 2 || this.a == 5;
    }

    public final void a(Graphics graphics) {
        int n2;
        if (this.a == 2) {
            return;
        }
        int n3 = this.a();
        if (this.b < a[a[this.d]][n3]) {
            n2 = this.a.a.a.a >> 10;
            int n4 = this.a.a.a.b >> 10;
            if (this.d == 4) {
                Image image = a[a[this.d]][0][0];
                byte by = this.b;
                if (n3 == 0) {
                    by = 0;
                    n4 = this.b < 4 ? (n4 += (this.b << 1) - 4) : (n4 += (7 - this.b << 1) - 4);
                }
                g.a(graphics, a[a[this.d]][n3], n2 + (a[a[this.d]][n3][by].getWidth() - image.getWidth() >> 1) * (this.a ? 1 : -1), n4 + (image.getHeight() >> 1), by, !this.a, false, 33);
                if (this.a == 0 || this.a == 3) {
                    n3 = -1;
                    if ((this.a.a.d & 0x10) == 0) {
                        n3 = this.a.a.d >> 1 & 0xF;
                        int n5 = n3 == 0 || n3 == 2 ? 0 : (n3 = n3 == 1 ? 1 : -1);
                    }
                    if (n3 != -1) {
                        g.a(graphics, a[a[this.d]][4], n2 + 11 * (this.a ? 1 : -1), n4 - 15, n3, !this.a, false, 3);
                    }
                }
            } else if (this.d == 5) {
                n4 += 48;
                if (this.a == 1) {
                    n4 = this.a.b == 32 ? (n4 += this.b * 7) : (n4 += this.b * 10);
                }
                Image[] imageArray = a[a[this.d]][0];
                graphics.drawImage(imageArray[0], n2, n4, 33);
                int n6 = -1;
                if ((this.a.a.d & 0x10) == 0) {
                    n6 = this.a.a.d >> 1 & 0xF;
                    int n7 = n6 == 0 || n6 == 2 ? 0 : (n6 = n6 == 1 ? 1 : -1);
                }
                if (this.a == 6) {
                    n6 = 1;
                }
                if (n6 != -1) {
                    graphics.drawImage(a[a[this.d]][1][n6], n2, n4, 33);
                }
                g.a(graphics, imageArray, n2 - 16, n4, 2, false, false, 40);
                g.a(graphics, imageArray, n2 + 16, n4, 2, true, false, 36);
                n4 -= 37;
                n3 = a[a[this.d]][0];
                n6 = 0;
                if (!this.a()) {
                    n6 = this.b << 1 < n3 >> 1 ? this.b << 1 : n3 - (this.b << 1);
                }
                int n8 = -2 + this.a.b * 6;
                if (this.a.b > 0) {
                    n8 += n6;
                    n6 = 0;
                }
                if (this.a == 6) {
                    n8 = -10;
                    n6 = -3;
                    n4 += 5;
                }
                g.a(graphics, imageArray, n2 - n8, n4 - n6 + (n3 >> 1), 1, false, false, 40);
                g.a(graphics, imageArray, n2 + n8, n4 + n6, 1, true, false, 36);
            } else if (this.d == 6) {
                int n9 = this.c < 0 ? -this.c : this.c;
                int n10 = this.c < 0 ? -1 : 1;
                n4 += ((n9 * 32 - this.a.b << 5) / 32 + (this.a.a >> 10)) * n10;
                for (n3 = 0; n3 < n9; ++n3) {
                    graphics.drawImage(a[a[this.d]][0][this.b], n2, n4 - (n3 << 5) * n10, 1 | (this.c < 0 ? 16 : 32));
                }
            } else if (this.d == 2) {
                graphics.drawImage(a[a[this.d]][n3][this.b], n2, n4 + 16, 33);
                if (n3 == 3 && this.b >= 3 && this.b <= 5) {
                    graphics.drawImage(a[a[a[this.d]]][4][this.b - 3], n2, n4 - 14, 33);
                }
            } else {
                if (this.d == 0) {
                    n4 += 12;
                } else if (this.d == 1) {
                    n4 += 13;
                }
                g.a(graphics, a[a[this.d]][n3], n2, n4, this.b, this.a, true, 33);
            }
        }
        if (this.b != null) {
            if (this.a == 5) {
                n2 = this.b.a * (5 - this.b) / 5 >> 10;
                graphics.setColor(49, 49, 49);
                graphics.fillArc((this.b.a.a.a >> 10) - n2, (this.b.a.a.b >> 10) - n2, n2 << 1, n2 << 1, 0, 360);
                return;
            }
            int n11 = n2 = this.a == 4 || this.a == 1 ? 2 : 1;
            if (this.a != null) {
                bg bg2 = this.a.a.a().b(this.b.a.a);
                if (bg2.b < 0 && (bg2.a < 0 ? -bg2.a : bg2.a) < -bg2.b) {
                    n2 = 0;
                }
            }
            g.a(graphics, a[this.d][4], this.b.a.a.a >> 10, this.b.a.a.b >> 10, n2, n2 == 0 ? false : this.a, true, 3);
        }
    }

    private void a(int n2) {
        bg bg2;
        boolean bl;
        ae ae2 = this.a.a[n2];
        int n3 = this.a.a.a.a >> 15;
        int n4 = this.a.a.a.b >> 15;
        if (this.a.b == 32 && n2 <= 1) {
            bl = false;
            int n5 = n2 == 0 ? -3 : 3;
            bg2 = this.a.a.a.a(new bg(n5 << 15, 311296));
        } else {
            int n6;
            boolean bl2;
            bl = al.b(0, 1) == 1;
            if (bl) {
                n4 -= 3;
            }
            int n7 = 0;
            do {
                n6 = this.a.b == 32 ? ((n2 & 1) == 0 ? al.b(-6, -2) : al.b(2, 6)) : (bl ? al.b(-8, 1) : al.b(-7, -2));
                bl2 = false;
                for (int i2 = 0; i2 < this.a.a.length; ++i2) {
                    ae ae3 = this.a.a[i2];
                    if (ae3.d != 6 || ae3.a() || ae3.a.a.a.a >> 15 != n3 + n6 || ae3.a.a.a.b >> 15 != n4) continue;
                    bl2 = true;
                }
            } while (bl2 && ++n7 < 100);
            bg2 = this.a.a.a.a(new bg(n6 << 15, 16384 - (bl ? 131072 : 0)));
        }
        ae2.a((byte)6, bg2, true);
        if (this.a.b == 28) {
            ae2.a = 200;
        }
        if (bl) {
            ae2.a.a.a.b += ae2.a.a << 1;
            ae2.c = -al.b(1, 2);
            return;
        }
        if (this.a.b == 32 && n2 <= 1) {
            ae2.c = 2;
            return;
        }
        ae2.c = al.b(1, 2);
    }

    public final void a() {
        block99: {
            block103: {
                int n2;
                block107: {
                    block106: {
                        bg bg2;
                        block105: {
                            block104: {
                                int n3;
                                block100: {
                                    block102: {
                                        block101: {
                                            int n4;
                                            if (this.a == 2) {
                                                return;
                                            }
                                            if (this.d == 6 && this.a == 1) {
                                                this.a.b -= 3;
                                                if (this.a.b <= 0) {
                                                    this.a = (byte)2;
                                                    this.d = 0;
                                                }
                                                return;
                                            }
                                            int n5 = this.a();
                                            if (n5 == 4) {
                                                if (this.c == 1) {
                                                    this.c = 0;
                                                    this.b = (byte)(this.b + 1);
                                                    if (this.b == 5) {
                                                        this.a = (byte)2;
                                                        this.a.a.b(this.b.c);
                                                    }
                                                }
                                                this.c = (byte)(this.c + 1);
                                                return;
                                            }
                                            if (this.c >= b[a[this.d]][n5]) {
                                                this.c = 0;
                                                this.b = (byte)(this.b + 1);
                                                if (this.a == 1) {
                                                    if (this.b != null) {
                                                        if (this.b >= 15 && (this.b.a.b & 2) != 0) {
                                                            this.a = (byte)5;
                                                            this.b = 0;
                                                            this.c = 0;
                                                            return;
                                                        }
                                                    } else if (this.b == a[a[this.d]][n5]) {
                                                        this.a = (byte)2;
                                                        if (this.d == 4 || this.d == 5) {
                                                            this.a.a.c = 6;
                                                        }
                                                        if (this.d == 5) {
                                                            this.a = 1;
                                                            this.b = (byte)(this.b - 1);
                                                        }
                                                        return;
                                                    }
                                                } else if (this.b == a[a[this.d]][n5]) {
                                                    this.b = 0;
                                                    if (n5 == 3 && this.c > 0) {
                                                        this.a((byte)0);
                                                    }
                                                }
                                            }
                                            this.c = (byte)(this.c + 1);
                                            if (this.a == 1) {
                                                return;
                                            }
                                            if (this.a.b != 5 && (this.a.a.b & 2) != 0 && ((this.a.a.b & 0x40) != 0 || this.b != null && (this.b.a.b & 0x40) != 0) && ((n5 = this.a.a.a.b - this.a.a.b.b) > j[this.d] || n5 < -j[this.d])) {
                                                n5 = 0;
                                                while (n5 < this.a.a.length) {
                                                    int n6 = n5++;
                                                    this.a.e[n6] = this.a.e[n6] + 30;
                                                }
                                                as as2 = this.a;
                                                bg bg3 = as2.a.a;
                                                this.a.a.a(bg3.a >> 10, bg3.b >> 10, 30, 30);
                                                this.b();
                                                return;
                                            }
                                            if (this.d == 2 && this.a.a()[1] >= this.a.e << 15) {
                                                this.a = (byte)2;
                                                return;
                                            }
                                            n5 = this.a.a(this.a.a());
                                            if (n5 != -1) {
                                                if (!this.b && this.a.a.g > 0 && al.a(this.a.a.d, this.a.a())) {
                                                    as as3 = this.a;
                                                    this.a.a.a(as3.a.a.a >> 10, n5 << 5, 2);
                                                }
                                                this.b = true;
                                                int n7 = n5 << 15;
                                                n5 = 6;
                                                n5 = 200;
                                                n4 = n7;
                                                as as4 = this.a;
                                                if ((n4 = (int)(((long)(as4.a[3] - n4) << 10) / (long)(as4.a[3] - as4.a[1]))) > 1024) {
                                                    n4 = 1024;
                                                }
                                                if (n4 > 0) {
                                                    n4 = (int)((long)-200 * (long)n4 >> 9);
                                                    as4.a(new bg(0, n4 -= as4.a.a.b - as4.a.b.b >> 6));
                                                }
                                            } else {
                                                this.b = false;
                                            }
                                            if (this.b != null) {
                                                int n8;
                                                this.b.a.a.a = n8 = this.b.a.a.a + this.a.a.a.a >> 1;
                                                this.a.a.a.a = n8;
                                                n4 = this.b.a.a.b - this.a.a.a.b - this.b >> 1;
                                                this.b.a.a.b -= n4;
                                                this.a.a.a.b += n4;
                                            }
                                            if (this.a.b == 0) {
                                                int n9 = 0;
                                                if (this.a.a.c <= 1) {
                                                    n9 = -f[this.d] >> 2;
                                                    this.a = false;
                                                } else {
                                                    n9 = this.a.a.a.a >> 15 >= 19 ? -f[this.d] >> 2 : f[this.d] << 1;
                                                    this.a = true;
                                                }
                                                this.a.a(new bg(n9, 0));
                                                if (this.a != 3) {
                                                    this.a((byte)3);
                                                }
                                                if (this.b.a.a.b >> 15 >= this.a.e) {
                                                    ++this.a.a.c;
                                                    this.a.a.b(this.a.a[0].a);
                                                    this.a.a.b(this.a.a[1].a);
                                                    this.a.a.a(this.a.a);
                                                    this.a.a.a(this.b.a);
                                                    this.a.a = null;
                                                    this.a.a = new ax[0];
                                                    this.a.a = null;
                                                    this.a.a = new ag[0];
                                                    this.a.a = null;
                                                    this.a.a = new ae[0];
                                                    this.a.a.g();
                                                    this.a.a.c(15);
                                                }
                                                return;
                                            }
                                            as as5 = this.a;
                                            bg2 = as5.a.a;
                                            if (this.a != null) {
                                                if (this.a.a()) {
                                                    this.a = null;
                                                } else if (this.a.a.a().b(bg2).d() > e[this.d]) {
                                                    this.a = null;
                                                }
                                            }
                                            if (this.a == null) {
                                                for (int i2 = 0; i2 < this.a.a.length; ++i2) {
                                                    if (this.a.a[i2].a() || (n3 = this.a.a[i2].a.a().b(bg2).d()) < 0 || n3 > e[this.d] || this.a != null && n3 >= this.a.a.a().b(bg2).d()) continue;
                                                    this.a = this.a.a[i2];
                                                }
                                            }
                                            if (this.a != null) {
                                                Object object = this.a.a.a().b(bg2);
                                                if (this.d == 4) {
                                                    if (this.a.b == 20) {
                                                        if (this.a.a.c == 0) {
                                                            this.a = true;
                                                            return;
                                                        }
                                                        if (((bg)object).d() > h[this.d] / 2) {
                                                            ((bg)object).b();
                                                            if (this.a.b < f[this.d]) {
                                                                this.a.b += f[this.d] / 30;
                                                            }
                                                            ((bg)object).a(this.a.b);
                                                            bg2.a((bg)object);
                                                            this.a = ((bg)object).a >= 0;
                                                            object = this.a;
                                                            this.a.a = true;
                                                        }
                                                        if (this.a.a.c == 3) {
                                                            n3 = bg2.a >> 15;
                                                            int n10 = bg2.b >> 15;
                                                            boolean bl = false;
                                                            for (int i3 = n3 - 1; i3 <= n3 + 1; ++i3) {
                                                                for (int i4 = n10 - 1; i4 <= n10 + 1; ++i4) {
                                                                    if (i3 < 0 || i4 < 0 || i3 >= this.a.d || i4 >= this.a.e || this.a.a[0][i3][i4] != 30) continue;
                                                                    bl = true;
                                                                }
                                                            }
                                                            if (bl) {
                                                                this.a.a.c = 4;
                                                                return;
                                                            }
                                                        }
                                                        if (this.a.a.c == 4) {
                                                            this.a.a.c = 5;
                                                            this.b();
                                                        }
                                                        if (this.a.a.c != 1) {
                                                            return;
                                                        }
                                                    } else {
                                                        if (this.a.a.c == 0) {
                                                            this.a = true;
                                                            return;
                                                        }
                                                        if (this.a.a.c == 4) {
                                                            this.a.a.c = 5;
                                                            this.b();
                                                        }
                                                        if (((bg)object).d() > h[this.d] / 2) {
                                                            ((bg)object).b();
                                                            if (this.a.b < f[this.d] / 2) {
                                                                this.a.b += f[this.d] / 60;
                                                            }
                                                            ((bg)object).a(this.a.b);
                                                            bg2.a((bg)object);
                                                            this.a = ((bg)object).a >= 0;
                                                            object = this.a;
                                                            this.a.a = true;
                                                        }
                                                        if (this.a.a.c == 3 && al.b(this.a.a.d, this.a.a())) {
                                                            this.a.a.c = 4;
                                                            this.a.a.c(92);
                                                        }
                                                    }
                                                } else {
                                                    if (((bg)object).a < -5120) {
                                                        this.a = false;
                                                    }
                                                    if (((bg)object).a > 5120) {
                                                        this.a = true;
                                                    }
                                                    if (((bg)object).d() > h[this.d] && this.d && (this.b || (this.a.a.b & 1) != 0)) {
                                                        n3 = 0;
                                                        n3 = (this.a.a.b & 1) != 0 && this.b ? f[this.d] >> 1 : (this.b ? f[this.d] >> 4 : f[this.d]);
                                                        this.a.a(new bg(this.a ? n3 : -n3, 0));
                                                    }
                                                }
                                            }
                                            if (this.a != 4) break block100;
                                            if (this.a != null || this.d == 2) break block101;
                                            this.a((byte)0);
                                            break block99;
                                        }
                                        if (this.a == null || this.a.a.a().b(bg2).d() <= g[this.d]) break block102;
                                        if (this.d) break block103;
                                        this.a((byte)0);
                                        break block99;
                                    }
                                    if ((this.d == 2 || this.d == 5) && this.a.b != 67) {
                                        if (this.b == c[this.d] && this.c == 1) {
                                            int n11 = this.a.a.length;
                                            for (n3 = 0; n3 < n11; ++n3) {
                                                ae ae2 = this.a.a[n3];
                                                if (ae2.a != 2 || ae2.d != 0) continue;
                                                if (this.d == 2) {
                                                    if (al.a(this.a.a.d, this.a())) {
                                                        this.a.a.a(11, false);
                                                    }
                                                    if (this.a.b == 2 && al.a(this.a.d, this.a())) {
                                                        this.a.a.addElement(new byte[]{13});
                                                    }
                                                    ae2.a((byte)0, bg2.a(new bg(0, -23552)), true);
                                                    ae2.a = 60;
                                                    ae2.a.a.b.a = ae2.a.a.a.a + ((this.a ? -1 : 1) * al.b(1, 2) << 10);
                                                    ae2.a.a.b.b = ae2.a.a.a.b + (al.b(2, 5) << 10);
                                                } else {
                                                    if (this.a.b == 32 && n3 <= 1) continue;
                                                    this.a(n3);
                                                }
                                                break block99;
                                            }
                                        }
                                    } else if (this.b == c[this.d] && this.c == 1 && this.a.a.a().b(bg2).d() <= h[this.d]) {
                                        if (this.a == -1) {
                                            if (al.a(this.a.a.d, this.a())) {
                                                this.a.a.a(10, false);
                                            }
                                            if (this.a.b == 2 && al.a(this.a.d, this.a())) {
                                                this.a.a.addElement(new byte[]{12});
                                            }
                                        }
                                        this.a.b(b[this.d]);
                                        if (this.d == 4) {
                                            this.a.b = 0;
                                        }
                                    }
                                    break block99;
                                }
                                if (this.d != 5) break block104;
                                if (this.a.b == 28 && this.a.a.c == 2 && al.b(this.a.a.d, this.a())) {
                                    this.b();
                                    return;
                                }
                                if (this.a.b == 32 && this.a.a.c == 0) {
                                    for (int i5 = 0; i5 < this.a.a.length - 1; ++i5) {
                                        this.a(i5);
                                    }
                                    this.a.a.c = 1;
                                }
                                if (this.a == 0 && this.a.a.c == 1 && this.a.b == 0 && this.c == 0) {
                                    this.a((byte)4);
                                }
                                if (this.a == 0 && this.a != null && this.a.b == 32) {
                                    int n12 = this.a.a.a.a - this.a.a.a().a;
                                    if (n12 < 0) {
                                        n12 = -n12;
                                    }
                                    int n13 = n3 = n12 < 32768 ? 1 : 0;
                                    if (this.a.a[0].a() && this.a.a[1].a() && this.a.a.a()[3] < this.a.a.a.b - this.a.a) {
                                        n3 = 0;
                                    }
                                    if (n3 != 0 && this.a.b < 3) {
                                        if (++this.a.b == 3) {
                                            this.a.a.b(this.a.c);
                                        }
                                    } else if (n3 == 0 && this.a.b > 0 && this.a.b-- == 3) {
                                        this.a.a.a(this.a);
                                    }
                                }
                                if (this.a == 0 && this.a.b == 32 && this.a.b == 0 && this.a.a[0].a() && this.a.a[1].a() && this.a != null && this.a.a.a().b < this.a.a.a.b + this.a.a) {
                                    this.a((byte)6);
                                    this.a.a.c(118);
                                    this.a.b = (byte)2;
                                }
                                break block99;
                            }
                            if (this.d != 6) break block105;
                            int n14 = 32 * (this.c < 0 ? -this.c : this.c);
                            if (this.a.b < n14) {
                                this.a.b += 3;
                                if (this.a.b > n14) {
                                    this.a.b = n14;
                                }
                            }
                            if (this.a != null && al.a(this.a.a.a(), this.a())) {
                                this.a.b(b[this.d]);
                            }
                            break block99;
                        }
                        if (this.d != 2 && (this.a == null || this.c != 0 || this.a.a.a().b(bg2).d() > g[this.d])) break block106;
                        this.a((byte)4);
                        break block99;
                    }
                    if (this.a != 3) break block107;
                    int n15 = this.a.a.a.a - this.a.a.b.a;
                    if (n15 > -i[this.d] && n15 < i[this.d]) {
                        this.a((byte)0);
                    }
                    break block99;
                }
                if (!this.d || this.a != 0 || (n2 = this.a.a.a.a - this.a.a.b.a) > -i[this.d] && n2 < i[this.d]) break block99;
            }
            this.a((byte)3);
        }
        if (this.d != 6 && this.c > 0) {
            --this.c;
        }
        if (this.a != -1) {
            --this.a;
            if (this.a == 0) {
                this.b();
            }
        }
    }

    public final void b() {
        if (!this.a()) {
            if ((this.d != 5 || this.a.b == 32) && this.a == -1 && al.a(this.a.a.d, this.a())) {
                this.a.a.a(this.d == 1 ? 8 : 4, false);
            }
            this.a((byte)1);
            this.a = null;
            if (this.d != 4 && (this.d != 5 || this.a.b != 3)) {
                this.a.a.b(this.a.c);
            }
            if (this.d == 5) {
                if (this.a.b == 28) {
                    int n2 = this.a.a.a.a >> 15;
                    int n3 = this.a.a.a.b >> 15;
                    this.a.a[1][n2][n3 + 1] = -1;
                    this.a.a[1][n2 - 1][n3 + 1] = -1;
                    this.a.a[1][n2 + 1][n3 + 1] = -1;
                    this.a.a.a(n2 - 1, n3 + 1);
                    this.a.a.a(n2, n3 + 1);
                    this.a.a.a(n2 + 1, n3 + 1);
                } else if (this.a.b == 32) {
                    int n4 = this.a.a.a.a >> 15;
                    int n5 = this.a.a.a.b >> 15;
                    this.a.a[1][n4][n5 + 2] = 69;
                    this.a.a.a(n4, n5 + 2);
                }
            }
            this.a.a = null;
            if (this.b != null) {
                this.b.a = null;
            }
        }
    }

    static {
        e = new int[]{0x3200000, 1048576000, 1048576000, -1, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE};
        f = new int[]{500, 400, 250, 0, 2764, 0, 0};
        g = new int[]{0x300000, 0x280000, Integer.MAX_VALUE, -1, 0x500000, Integer.MAX_VALUE, 0x300000};
        h = new int[]{0x1E6666, 0x280000, 0, -1, 0x600000, -1, 0x300000};
        i = new int[]{500, 300, 200, 0, 0, 0, 0};
        b = new int[]{10240, 20480, 0, -1, 2560, 512, 512};
        j = new int[]{3072, 3072, 0, -1, 0};
    }
}

