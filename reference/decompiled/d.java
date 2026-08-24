/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import com.hardwire.blob.Main;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class d {
    public byte a;
    public as[] a;
    private int[][] a;
    public int a;
    private int k;
    public int b;
    private static int[] a = new int[]{-16777216, -15592170};
    private static final int[][][] a = new int[][][]{new int[][]{{50, 49, 20}, {200, 200, 200}}, new int[][]{{58, 49, 20}, {200, 200, 200}}, new int[][]{{38, 30, 10}, {200, 200, 200}}};
    public byte b;
    public byte c;
    public byte d;
    private int l;
    public int c;
    public int d;
    public int e;
    private h b;
    private int m;
    public boolean a;
    public boolean b;
    public boolean c;
    public int f;
    public int g;
    private k a;
    private ab a;
    public h a;
    private v a;
    private static Image[][] a;
    private int n = Integer.MAX_VALUE;
    private int o;
    private int p;
    private int q;
    private int r;
    private int s;
    private int t;
    private int u;
    private int v;
    private int w;
    private int x;
    private boolean i;
    public boolean d;
    public boolean e;
    public boolean f;
    public boolean g;
    public boolean h;
    private int y;
    public int h;
    public int i;
    private boolean j;
    public int j;
    public byte e;

    public d(k k2, byte by, byte by2, int n2, int n3) {
        this.a = k2;
        this.a = by;
        this.e = by2;
        this.a = this.a.a;
        this.a(n2, n3);
        if (this.a == 1) {
            for (int i2 = 516; i2 <= 530; ++i2) {
                this.a.a.a(i2);
            }
        }
    }

    public static void a(g g2) {
        Image[][] imageArrayArray = new Image[2][];
        a = imageArrayArray;
        imageArrayArray[0] = g2.a(110, true);
        if (g2.a(516) == null) {
            d.a[1] = null;
            return;
        }
        d.a[1] = g2.a(516, true);
    }

    public final void a(int n2, int n3) {
        bg bg2 = new bg(n2 << 10, n3 << 10);
        if (this.a != null) {
            if (this.d == 1) {
                bg2 = this.a.a();
                n3 = this.a.a.length;
                if (this.a.a != null && this.a.a.b) {
                    for (int i2 = 0; i2 < n3; ++i2) {
                        this.a.a.a(this.a.a[i2]);
                    }
                }
            } else {
                bg2 = this.a.a();
                if (this.a.a != null && this.a.a.b) {
                    this.a.a.b(this.a);
                }
            }
            h h2 = this.a;
            this.a.a = null;
            h2.a = null;
            h2.a = null;
            h2.b = null;
            h2.b = null;
            h2.c = null;
            h2.c = null;
            h2.a = null;
            h2.b = null;
            h2.c = null;
        }
        this.d = 0;
        this.y = 0;
        this.j = 0;
        this.b = false;
        this.a = false;
        this.c = 0;
        this.d = 102400;
        this.e = Integer.MIN_VALUE;
        this.o = -1;
        this.p = 20;
        this.q = -1;
        this.s = -1;
        this.t = -1;
        this.a = null;
        this.a = null;
        this.a = new as[16];
        this.a = new int[16][2];
        this.a = 0;
        this.k = 0;
        this.u = -1;
        this.a = null;
        this.a = new h(0, true, true, false, false);
        this.a.j = 1;
        this.a.a = this;
        this.a.h = 2;
        this.l = 24;
        n3 = 18;
        if (this.e == 1) {
            this.l = 20;
            n3 = 14;
        } else if (this.e == 2) {
            this.l = 26;
        } else if (this.e == 3) {
            this.l = 30;
        }
        bg[] bgArray = al.a(this.l, n3);
        for (n3 = 0; n3 < bgArray.length; ++n3) {
            bgArray[n3].a(bg2);
        }
        this.a.a(bgArray, 1024);
        this.a.a(1024, 1024, -1, -1);
        this.a.a();
        this.a.b();
        if (this.a.a != null && this.a.a.b) {
            this.a.a.a(this.a);
        }
        this.a = null;
        this.a = new v(4, bgArray.length);
        this.c = 0;
        this.b = 0;
    }

    public final void a(ae ae2) {
        if (!ae2.a() && ae2.d != 2) {
            if (ae2.d == 5 && (this.a.b != 28 || this.a.c <= 1) && ae2.a != 6) {
                this.b(ae.b[ae2.d]);
                return;
            }
            long l2 = this.a.c().a();
            if (l2 > 0x3200000L || this.e > 0 && l2 > 0L) {
                if (ae2.a == -1 || this.a.b == 28) {
                    int n2 = this.b;
                    this.a.e[n2] = this.a.e[n2] + 30;
                    Object object = ae2.a;
                    object = ((as)object).a.a;
                    this.a.a(((bg)object).a >> 10, ((bg)object).b >> 10, 30, 30);
                }
                ae2.b();
            }
        }
    }

    public final void a(h h2) {
        if (this.d == 0) {
            if (h2.j == 1 && ((d)h2.a).a == 1 && ((d)h2.a).e == 1) {
                return;
            }
            if (h2.j != 1 && this.c != 2) {
                return;
            }
            this.b = h2;
            this.m = 3;
        }
    }

    public final void b(h h2) {
        boolean bl = false;
        for (int i2 = 0; i2 < this.a.g; ++i2) {
            for (int i3 = 0; i3 < h2.a.length; ++i3) {
                if (this.a.b[i2].a != h2.a[i3] && this.a.b[i2].b != h2.a[i3]) continue;
                bl = true;
            }
        }
        if (bl) {
            this.a.c();
            this.b = null;
        }
    }

    public final void a(int n2) {
        int n3 = this.a.a().a >> 10;
        int n4 = n3 >> 5;
        if (!this.a.a(n4, n2)) {
            int n5 = this.a.a()[0] >> 15;
            for (n3 = n4 - 1; n3 >= n5 && !this.a.a(n3, n2); --n3) {
            }
            if (!this.a.a(n3, n2)) {
                n5 = this.a.a()[2] >> 15;
                for (n3 = n4 + 1; n3 >= n5 && !this.a.a(n3, n2); ++n3) {
                }
            }
            n3 = (n3 << 5) + 16;
        }
        this.a.a(n3, n2 << 5, 2);
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    public final void a() {
        block189: {
            block181: {
                block185: {
                    block188: {
                        block187: {
                            block186: {
                                block180: {
                                    block183: {
                                        block184: {
                                            block182: {
                                                v0 = this.c = this.a.c == 6 && (this.a.b == 20 || this.a.b == 23 || this.a.b == 28 || this.a.b == 32) || this.a.c == 2 && this.a.b == 34;
                                                if (this.d != 0) break block181;
                                                if (this.a.b == 28 && this.a.c == 1 && this.a.e[this.b] >= 180) {
                                                    this.a.c(110);
                                                    ++this.a.c;
                                                    for (var1_1 = 0; var1_1 < this.a.a.length; ++var1_1) {
                                                        if (this.a.a[var1_1].d != 6) continue;
                                                        this.a.a[var1_1].b();
                                                    }
                                                }
                                                if (this.a.b == 32 && this.a.a[0].a() && this.a.a[1].a() && !this.a.a[this.a.a.length - 1].a() && this.a.a().a >> 15 == this.a.a[this.a.a.length - 1].a.a.a.a >> 15 && this.a.a().b >> 15 >= this.a.a[this.a.a.length - 1].a.a.a.b >> 15) {
                                                    this.a.b(new bg(0, -5000));
                                                    this.b = 1;
                                                }
                                                var1_2 = this.a.a();
                                                if (this.a != 1 || this.e == 0) {
                                                    var2_4 = var1_2[0] >> 15;
                                                    var3_18 = var1_2[1] >> 15;
                                                    var4_23 = var1_2[2] >> 15;
                                                    var5_30 = var1_2[3] >> 15;
                                                    var2_4 = var2_4 < 0 ? 0 : var2_4;
                                                    var3_18 = var3_18 < 0 ? 0 : var3_18;
                                                    var4_23 = var4_23 >= this.a.d ? this.a.d - 1 : var4_23;
                                                    var5_30 = var5_30 >= this.a.e ? this.a.e - 1 : var5_30;
                                                    var6_39 = new bg();
                                                    while (var2_4 <= var4_23) {
                                                        for (var7_44 = var3_18; var7_44 <= var5_30; ++var7_44) {
                                                            var8_48 = this.a.a[1][var2_4][var7_44];
                                                            if (var8_48 == 8 || var8_48 == 9 || var8_48 == 70) {
                                                                var6_39.a = (var2_4 << 15) + 16384;
                                                                var6_39.b = (var7_44 << 15) + 16384;
                                                                if (!this.a.a(var6_39)) continue;
                                                                switch (var8_48) {
                                                                    case 8: {
                                                                        if (this.b == this.a.f) {
                                                                            this.a.a.a(1, false);
                                                                            this.a.a(var6_39.a >> 10, var6_39.b >> 10, 5);
                                                                        }
                                                                        this.d += 10240;
                                                                        if (this.d > 102400) {
                                                                            this.d = 102400;
                                                                        }
                                                                        if (this.a.b != 2) break;
                                                                        var8_48 = 2;
                                                                        if (this.b == this.a.g) {
                                                                            var8_48 = 3;
                                                                        }
                                                                        this.a.a.addElement(new byte[]{var8_48, (byte)var2_4, (byte)var7_44});
                                                                        break;
                                                                    }
                                                                    case 9: {
                                                                        if (this.b == this.a.f) {
                                                                            this.a.a.a(2, false);
                                                                            this.a.a(var6_39.a >> 10, var6_39.b >> 10, 4);
                                                                        }
                                                                        v1 = this.b;
                                                                        this.a.e[v1] = this.a.e[v1] + 10;
                                                                        this.a.a(var6_39.a >> 10, var6_39.b >> 10, 10, 10);
                                                                        if (this.a.b != 2) break;
                                                                        var8_48 = 0;
                                                                        if (this.b == this.a.g) {
                                                                            var8_48 = 1;
                                                                        }
                                                                        this.a.a.addElement(new byte[]{var8_48, (byte)var2_4, (byte)var7_44});
                                                                        break;
                                                                    }
                                                                    case 70: {
                                                                        if (this.b == this.a.f) {
                                                                            this.a.a.a(2, false);
                                                                        }
                                                                        var8_48 = at.b(this.a.b);
                                                                        this.a.a.a((int)var8_48, -1);
                                                                        this.a.a[1][var2_4][var7_44] = -1;
                                                                        var9_53 = this.a.a.a(85);
                                                                        var10_58 = new byte[var9_53.length][];
                                                                        System.arraycopy(var9_53, 0, var10_58, 0, var9_53.length - 1);
                                                                        var10_58[var10_58.length - 1] = at.a(new byte[][]{var9_53[var9_53.length - 1], {at.a(3, ' ')}, at.a(3, Main.e[var8_48])});
                                                                        this.a.e = 0;
                                                                        this.a.f = -11;
                                                                        this.a.a.a(var10_58, 3, 3, -11579569, g.b / 2 - 20);
                                                                        this.a.d = (byte)6;
                                                                    }
                                                                }
                                                                this.a.a[1][var2_4][var7_44] = -1;
                                                                continue;
                                                            }
                                                            if (var8_48 == 13) {
                                                                this.c = true;
                                                                continue;
                                                            }
                                                            if (var8_48 == 43 && this.a.b == 1) {
                                                                v2 = this.b;
                                                                this.a.f[v2] = this.a.f[v2] + 1;
                                                                this.a.a[1][var2_4][var7_44] = -1;
                                                                var8_49 = null;
                                                                for (var9_54 = 0; var9_54 < this.a.a.a.length; ++var9_54) {
                                                                    if (this.a.a.a[var9_54][0] != var2_4 || this.a.a.a[var9_54][1] != var7_44) continue;
                                                                    var8_49 = this.a.a.a(at.b(this.a.b), var9_54);
                                                                }
                                                                if (this.b == this.a.f) {
                                                                    this.a.a(var8_49);
                                                                    continue;
                                                                }
                                                                if (this.a.b != 2) continue;
                                                                this.a.a.addElement(new byte[]{16});
                                                                continue;
                                                            }
                                                            if (!this.a.f[var2_4] || !this.a.e[var7_44]) continue;
                                                            var8_48 = 0;
                                                            for (var9_55 = 0; var9_55 < this.a.c; ++var9_55) {
                                                                if (this.a.a[var9_55][0] != var2_4 || this.a.a[var9_55][1] != var7_44) continue;
                                                                var10_59 = this.a.a[var9_55][2];
                                                                var9_55 = this.a.b == 35 && var10_59 == 131 ? 1 : 0;
                                                                var11_60 = this.a.b == 35 && var10_59 == 3;
                                                                if (var11_60) {
                                                                    this.a.c = 0;
                                                                    break;
                                                                }
                                                                if (var9_55 != 0 && this.a.c != 0) break;
                                                                var8_48 = 1;
                                                                if (this.a.b == 2) {
                                                                    if (this.a.e == 2 || this.a.e == 3 && at.b(this.a.b)) {
                                                                        this.a.c(var10_59);
                                                                        this.a.a.addElement(new byte[]{14, (byte)var10_59});
                                                                    }
                                                                } else if (this.b == this.a.f) {
                                                                    this.a.c(var10_59);
                                                                }
                                                                if (var9_55 != 0) break;
                                                                for (var9_55 = 0; var9_55 < this.a.c; ++var9_55) {
                                                                    if (this.a.a[var9_55][2] != var10_59 || this.a.a[var9_55][0] < var2_4 - 4 || this.a.a[var9_55][0] > var2_4 + 4 || this.a.a[var9_55][1] < var7_44 - 4 || this.a.a[var9_55][1] > var7_44 + 4) continue;
                                                                    this.a.e[this.a.a[var9_55][1]] = false;
                                                                    this.a.f[this.a.a[var9_55][0]] = false;
                                                                    --this.a.c;
                                                                    if (var9_55 != this.a.c) {
                                                                        this.a.a[var9_55] = this.a.a[this.a.c];
                                                                    }
                                                                    this.a.a[this.a.c] = null;
                                                                    --var9_55;
                                                                }
                                                                break;
                                                            }
                                                            if (var8_48 == 0) continue;
                                                            for (var9_55 = 0; var9_55 < this.a.c; ++var9_55) {
                                                                this.a.e[this.a.a[var9_55][1]] = true;
                                                                this.a.f[this.a.a[var9_55][0]] = true;
                                                            }
                                                        }
                                                        ++var2_4;
                                                    }
                                                }
                                                if (this.e != -2147483648) {
                                                    --this.e;
                                                }
                                                if (this.b != null) {
                                                    --this.m;
                                                    if (this.m == 0) {
                                                        this.b = null;
                                                    }
                                                }
                                                if ((var2_4 = this.a.a((int[])var1_2)) != -1) {
                                                    if (al.a(this.a.d, (int[])var1_2)) {
                                                        if (!this.a && this.a.c().d() > 10240) {
                                                            this.a.a.a(7, false);
                                                            if (this.a.g > 0 && !k.c) {
                                                                this.a(var2_4);
                                                            }
                                                        }
                                                        if (this.a.g > 1 && this.a.a()[1] >> 15 < var2_4) {
                                                            this.a.b(this.a.a().a >> 10, var2_4 << 5, 16, 1);
                                                        }
                                                    }
                                                    this.a = true;
                                                    this.a.a(var2_4 << 15, 200, 5, false);
                                                    if (k.c) {
                                                        this.b(2048);
                                                    }
                                                } else {
                                                    this.a = false;
                                                }
                                                if (this.a == 1 && this.e > 0) {
                                                    v3 = this;
                                                    var1_2 = v3;
                                                    v3.b();
                                                    switch (var1_2.e) {
                                                        case 1: {
                                                            var2_5 = var1_2.a.a();
                                                            if (var1_2.a.a[1][var2_5.a >> 15][var2_5.b >> 15] == 13) {
                                                                var1_2.a.c(67);
                                                            }
                                                            switch (var1_2.a.c) {
                                                                case 0: {
                                                                    if (var1_2.a.a().a >> 15 < 7) {
                                                                        var1_2.g = true;
                                                                        break;
                                                                    }
                                                                    if (var1_2.a.a().a >> 15 <= 7) break;
                                                                    var1_2.f = true;
                                                                    break;
                                                                }
                                                                case 1: {
                                                                    var1_2.b = 0;
                                                                    var1_2.g = true;
                                                                    if (var1_2.a.a().a >> 15 < 10) break;
                                                                    ++var1_2.a.c;
                                                                    break;
                                                                }
                                                                case 2: {
                                                                    var1_2.b = (byte)2;
                                                                    var1_2.g = true;
                                                                    if (var1_2.a.a().a >> 15 < 13) break;
                                                                    ++var1_2.a.c;
                                                                    break;
                                                                }
                                                                case 3: {
                                                                    var1_2.b = 0;
                                                                    var1_2.g = true;
                                                                    if (var1_2.a.a().a >> 15 <= 28) break;
                                                                    ++var1_2.a.c;
                                                                    break;
                                                                }
                                                                case 4: {
                                                                    var1_2.b = (byte)2;
                                                                    var1_2.g = true;
                                                                    var1_2.d = true;
                                                                    if (var1_2.a.a().b >> 15 <= 5) {
                                                                        ++var1_2.a.c;
                                                                        break;
                                                                    }
                                                                    if (var1_2.a.a().a >> 15 >= 29 || var1_2.a.a().b >> 15 < 10) break;
                                                                    var1_2.a.c = 2;
                                                                    break;
                                                                }
                                                                case 5: {
                                                                    var1_2.b = (byte)2;
                                                                    var1_2.f = true;
                                                                    var1_2.d = true;
                                                                    if (var1_2.a.a().a >> 15 < 27) {
                                                                        ++var1_2.a.c;
                                                                        break;
                                                                    }
                                                                    if (var1_2.a.a().a >> 15 >= 29 || var1_2.a.a().b >> 15 < 10) break;
                                                                    var1_2.a.c = 2;
                                                                    break;
                                                                }
                                                                case 6: {
                                                                    var1_2.b = 0;
                                                                    var1_2.e = true;
                                                                    var1_2.f = true;
                                                                    if (var1_2.a.a().a >> 15 > 22) break;
                                                                    ++var1_2.a.c;
                                                                    break;
                                                                }
                                                                case 7: {
                                                                    var1_2.b = (byte)2;
                                                                    var1_2.d = true;
                                                                    var1_2.f = true;
                                                                    if (var1_2.a.a().b >> 15 > 1) break;
                                                                    ++var1_2.a.c;
                                                                    break;
                                                                }
                                                                case 8: {
                                                                    var1_2.b = 0;
                                                                    var1_2.g = true;
                                                                    if (var1_2.a.a().b >> 15 >= 18) {
                                                                        ++var1_2.a.c;
                                                                        break;
                                                                    }
                                                                    if (var1_2.a.a().a >> 15 > 30 || var1_2.a.a().b >> 15 < 5) break;
                                                                    var1_2.a.c = 7;
                                                                    break;
                                                                }
                                                                case 9: {
                                                                    var1_2.b = (byte)2;
                                                                    var1_2.g = true;
                                                                }
                                                            }
                                                            break;
                                                        }
                                                        case 2: {
                                                            if (var1_2.a.c == 0) {
                                                                var1_2.a.c = 1;
                                                            }
                                                            if (var1_2.a.c != 1) break;
                                                            var2_6 = var1_2.a.a[0];
                                                            var1_2.b = (byte)2;
                                                            var3_19 = var2_6.a.a();
                                                            var4_24 = var1_2.a.a();
                                                            var5_31 = var3_19.b(var4_24);
                                                            if (var3_19.b < var4_24.b && var4_24.b >> 15 > 1 && var3_19.b >> 15 < 15) {
                                                                var2_7 = var3_19.a >> 15;
                                                                var6_40 = var4_24.a >> 15;
                                                                if (var2_7 < 7) {
                                                                    if (var6_40 > 1) {
                                                                        var1_2.f = true;
                                                                    }
                                                                    var1_2.d = true;
                                                                    break;
                                                                }
                                                                if (var2_7 <= 7) break;
                                                                if (var6_40 < 13) {
                                                                    var1_2.g = true;
                                                                }
                                                                var1_2.d = true;
                                                                break;
                                                            }
                                                            if (var5_31.a > 0) {
                                                                var1_2.g = true;
                                                            } else if (var5_31.a < 0) {
                                                                var1_2.f = true;
                                                            }
                                                            if (var5_31.b > 0) {
                                                                var1_2.e = true;
                                                                break;
                                                            }
                                                            if (var5_31.b >= 0) break;
                                                            var1_2.d = true;
                                                            break;
                                                        }
                                                        case 3: {
                                                            if (var1_2.a.c == 0) {
                                                                var2_8 = var1_2.a.a[0];
                                                                var1_2.a.a[0].a = false;
                                                                var2_8 = var1_2.a.a[1];
                                                                var1_2.a.a[1].a = false;
                                                                break;
                                                            }
                                                            var1_2.a.c = 1;
                                                            if (!var1_2.a.a[0].a) {
                                                                var1_2.a.a[0].a((byte)0, 0);
                                                                var1_2.a.a[1].a((byte)0, 0);
                                                            }
                                                            var2_9 = var1_2.a.a[0];
                                                            var1_2.b = (byte)2;
                                                            var3_20 = var2_9.a.a();
                                                            var4_25 = var1_2.a.a();
                                                            var5_32 = var3_20.b(var4_25);
                                                            if (var5_32.a > 0) {
                                                                var1_2.g = true;
                                                            } else if (var5_32.a < 0) {
                                                                var1_2.f = true;
                                                            }
                                                            if (var1_2.b == null && var4_25.b >> 15 > 5) {
                                                                var1_2.d = true;
                                                                break;
                                                            }
                                                            if (var5_32.b > 0) {
                                                                var1_2.e = true;
                                                                break;
                                                            }
                                                            if (var5_32.b >= 0) break;
                                                            var1_2.d = true;
                                                        }
                                                    }
                                                }
                                                v4 = this;
                                                var1_2 = v4;
                                                if (v4.a.b != 0) break block182;
                                                if (var1_2.a.c == 0) {
                                                    var1_2.g = true;
                                                    var1_2.b = (byte)2;
                                                    if (var1_2.a.a().a >> 15 >= 7) {
                                                        ++var1_2.a.c;
                                                        var1_2.a.c(9);
                                                    }
                                                } else if (var1_2.a.c == 1) {
                                                    var1_2.g = true;
                                                } else if (var1_2.a.c == 2) {
                                                    var1_2.b = 0;
                                                    var1_2.g = false;
                                                } else if (var1_2.a.c >= 3) {
                                                    var1_2.a.c();
                                                    var1_2.g = true;
                                                }
                                                break block183;
                                            }
                                            if (var1_2.a.b != 14 || var1_2.a != 0) break block184;
                                            if (var1_2.a.c == 0 && var1_2.a.a().a > 206438) {
                                                var1_2.a.c = 1;
                                                for (var2_10 = 0; var2_10 < var1_2.a.a.length; ++var2_10) {
                                                    var1_2.a.a[var2_10].b.c(var1_2.a.a[var2_10].a);
                                                }
                                                var1_2.a.c(61);
                                            }
                                            break block183;
                                        }
                                        if (var1_2.a.b == 34 && var1_2.a.c == 0) break block185;
                                        if (var1_2.a.b == 35 && var1_2.a.c == 2) {
                                            var1_2.b();
                                            var1_2.g = true;
                                            var1_2.b = 0;
                                        } else if (var1_2.a.b == 28 && var1_2.a.c == 2) {
                                            var1_2.b();
                                            var1_2.g = true;
                                            var1_2.d = true;
                                            var1_2.b = 0;
                                        }
                                    }
                                    if (var1_2.h && var1_2.b != null) {
                                        if (var1_2.b == var1_2.a.f) {
                                            var1_2.a.a.a(0, false);
                                        } else {
                                            var1_2.a.a.addElement(new byte[]{9});
                                        }
                                        var2_11 = var1_2.b.a().b(var1_2.a.a());
                                        var2_11.b();
                                        var3_21 = var1_2.b.j == 1;
                                        var4_26 = var3_21 != false && ((d)var1_2.b.a).e == 2;
                                        v5 = var5_33 = var3_21 != false && ((d)var1_2.b.a).e == 3;
                                        if (var3_21) {
                                            ((d)var1_2.b.a).a.c();
                                        }
                                        var2_11.a((var4_26 != false ? 57 : (var3_21 != false ? 28 : 114)) * 100);
                                        var1_2.b.a(var2_11);
                                        var2_11.a(var4_26 != false || var5_33 != false ? -512 : (var3_21 != false ? -1024 : -256));
                                        var1_2.a.a(var2_11);
                                        var1_2.a.c();
                                        var1_2.b = null;
                                        var1_2.e = 0;
                                    }
                                    if (var1_2.h && var1_2.e < -20) {
                                        var1_2.e = 56;
                                    }
                                    if (var1_2.b != var1_2.c) {
                                        if (var1_2.b != 2 && var1_2.c == 2) {
                                            var1_2.a.c();
                                            var1_2.b = null;
                                        }
                                        var1_2.c = var1_2.b;
                                    }
                                    var2_12 = 0;
                                    var3_22 = var1_2.a.b();
                                    if (var1_2.y == 0 && (var1_2.d && !var1_2.i || var1_2.j && var1_2.i < -100) && (var1_2.a.b & 1) != 0 && var3_22.a < var3_22.b && var3_22.a > -var3_22.b && var3_22.b > 0) {
                                        var1_2.j = false;
                                        if ((var1_2.a.b & 2) == 0) {
                                            var2_12 = 1;
                                        } else {
                                            var1_2.y = 1;
                                        }
                                    } else if (!var1_2.d && var1_2.i >= -100) {
                                        var1_2.y = 0;
                                    }
                                    if (var1_2.y > 0) {
                                        var4_27 = var1_2.a.a();
                                        if (++var1_2.y > 10 || var1_2.y == 2 && var4_27[3] - var4_27[1] < (var1_2.l << 10) * 14 / 10) {
                                            var2_12 = 1;
                                            var1_2.y = 0;
                                        }
                                    }
                                    var1_2.i = var1_2.d;
                                    if (!var1_2.j && var1_2.i >= -100) {
                                        var1_2.j = true;
                                    } else if (var1_2.j && var1_2.i < -100) {
                                        var1_2.j = false;
                                    }
                                    var1_2.a.c = var1_2.c == 1 ? 0 : 1024;
                                    var6_41 = var1_2.c == 2 ? 1 : 0;
                                    var5_34 = var1_2.a;
                                    var5_34.d = var6_41 != 0 ? (var5_34.d |= 1) : (var5_34.d &= -2);
                                    if (var2_12 != 0 && !var3_22.a()) {
                                        var4_28 = null;
                                        var4_28 = new bg(0, -1024);
                                        var5_35 = 0x7FFFFFFF;
                                        var2_12 = -2147483648;
                                        var6_41 = 0;
                                        var7_44 = var1_2.a.a.length;
                                        var8_50 = new int[var7_44];
                                        for (var9_56 = 0; var9_56 < var7_44; ++var9_56) {
                                            var8_50[var9_56] = var1_2.a.a[var9_56].a.a * var4_28.a + var1_2.a.a[var9_56].a.b * var4_28.b >> 10;
                                            if (var8_50[var9_56] < var5_35) {
                                                var5_35 = var8_50[var9_56];
                                            }
                                            if (var8_50[var9_56] > var2_12) {
                                                var2_12 = var8_50[var9_56];
                                            }
                                            if ((var1_2.a.a[var9_56].b & 1) == 0) continue;
                                            ++var6_41;
                                        }
                                        if ((var2_12 -= var5_35) != 0 && var6_41 > 0) {
                                            if (var1_2.b == var1_2.a.f) {
                                                var1_2.a.a.a(0, false);
                                            } else if (var1_2.a.b == 2) {
                                                var1_2.a.a.addElement(new byte[]{11});
                                            }
                                            var4_28.a(25000);
                                            var4_28.a((int)(((long)var6_41 << 20) / ((long)var2_12 * (long)var7_44)));
                                            for (var9_56 = 0; var9_56 < var7_44; ++var9_56) {
                                                if ((var1_2.a.a[var9_56].b & 1) != 0) continue;
                                                v6 = var9_56;
                                                var8_50[v6] = var8_50[v6] - var5_35;
                                                var1_2.a.a[var9_56].c.a = (int)((long)var1_2.a.a[var9_56].c.a + ((long)var4_28.a * (long)var8_50[var9_56] >> 10));
                                                var1_2.a.a[var9_56].c.b = (int)((long)var1_2.a.a[var9_56].c.b + ((long)var4_28.b * (long)var8_50[var9_56] >> 10));
                                            }
                                        }
                                        if (var1_2.c == 2) {
                                            var1_2.a.c();
                                        }
                                    }
                                    if (var1_2.c != 2) ** GOTO lbl-1000
                                    if (var1_2.b) ** GOTO lbl-1000
                                    var5_36 = var1_2.a;
                                    for (var6_41 = 0; var6_41 < var5_36.g; ++var6_41) {
                                        if (var5_36.b[var6_41].a == null) continue;
                                        v7 = true;
                                        break block180;
                                    }
                                    v7 = false;
                                }
                                if (v7) lbl-1000:
                                // 2 sources

                                {
                                    v8 = 1;
                                } else lbl-1000:
                                // 2 sources

                                {
                                    v8 = 0;
                                }
                                var4_29 = v8;
                                var5_37 = new bg();
                                if (var1_2.f || var1_2.g) {
                                    var5_37.a((var1_2.f != false ? -1 : (var1_2.g != false ? 1 : 0)) * (var4_29 != 0 ? 150 : (var1_2.a != false && (var1_2.a.b & 2) == 0 ? 100 : 50)), 0);
                                }
                                if (var1_2.d) {
                                    if (var4_29 != 0) {
                                        if (var3_22.b < 0) {
                                            var5_37.a(0, -350);
                                        } else if (!var3_22.a()) {
                                            var5_37.a(0, (int)(-350L * (long)(var3_22.a < 0 ? -var3_22.a : var3_22.a) / (long)var3_22.c()));
                                        }
                                    } else if (!var1_2.a) {
                                        var5_37.a(0, -100);
                                    }
                                }
                                if (var1_2.y <= 0) break block186;
                                v9 = var5_37;
                                v10 = 0;
                                v11 = 1000;
                                break block187;
                            }
                            if (!var1_2.e) break block188;
                            v9 = var5_37;
                            v10 = 0;
                            v11 = (var1_2.a.b & 2) != 0 ? 500 : (var1_2.a != false ? 400 : 100);
                        }
                        v9.a(v10, v11);
                    }
                    if (var5_37.a()) {
                        if (var1_2.h != 0) {
                            var5_37.a(var1_2.h * (var4_29 != 0 ? 150 : (var1_2.a != false && (var1_2.a.b & 2) == 0 ? 100 : 50)) / 127, 0);
                        }
                        if (var1_2.i < 0) {
                            if (var4_29 != 0) {
                                if (var3_22.b < 0) {
                                    var5_37.a(0, -var1_2.i * -350 / 127);
                                } else if (!var3_22.a()) {
                                    var5_37.a(0, -var1_2.i * (int)(-350L * (long)(var3_22.a < 0 ? -var3_22.a : var3_22.a) / (long)var3_22.c()) / 127);
                                }
                            } else if (!var1_2.a) {
                                var5_37.a(0, -var1_2.i * -100 / 127);
                            }
                        }
                        if (var1_2.i > 0) {
                            var5_37.a(0, var1_2.i * ((var1_2.a.b & 2) != 0 ? 500 : (var1_2.a != false ? 400 : 100)) / 127);
                        }
                    }
                    if ((var1_2.j & 130) != 0) {
                        var5_37.a(150, 0);
                    }
                    if ((var1_2.j & 68) != 0) {
                        var5_37.a(-150, 0);
                    }
                    if ((var1_2.j & 192) != 0) {
                        var5_37.a(0, -350);
                    } else if ((var1_2.j & 8) != 0) {
                        var5_37.a(0, -700);
                    }
                    if (var1_2.e == 1) {
                        var5_37.a(1945);
                    } else if (var1_2.e == 2 || var1_2.e == 3) {
                        var5_37.a(1331);
                    }
                    if (!var5_37.a()) {
                        var6_42 = var5_37;
                        var5_37 = var1_2.a;
                        for (var2_12 = 0; var2_12 < var5_37.a.length; ++var2_12) {
                            var7_46 = var5_37.a[var2_12];
                            if ((var7_46.b & 5) != 0) continue;
                            var8_51 = var6_42;
                            if (var7_46.a == 0x7FFFFFFF) continue;
                            var7_46.c.a += var8_51.a;
                            var7_46.c.b += var8_51.b;
                        }
                    }
                    if ((var1_2.a.b & 1) != 0) {
                        var2_13 = new bg(var1_2.f != false || (var1_2.j & 32) != 0 ? -1 : (var1_2.g != false || (var1_2.j & 16) != 0 ? 1 : 0), var1_2.d != false ? -1 : (var1_2.e != false ? 1 : 0));
                        var6_43 = 0;
                        if (var2_13.a()) {
                            var2_13.b(var1_2.h, var1_2.i);
                            var6_43 = 1;
                        }
                        if (!var2_13.a() && !var3_22.a() && (var2_13.a != 0 || var2_13.b != -1 || var3_22.b <= 0 || var3_22.a >= var3_22.b && var3_22.a <= -var3_22.b)) {
                            var7_47 = var3_22.a(var2_13);
                            var7_47 = var7_47 > 0 ? -1 : (var7_47 < 0 ? 1 : 0);
                            var8_52 = var1_2.a.a();
                            var7_47 = var6_43 != 0 ? var2_13.c() * (var7_47 * (var4_29 == 0 && var1_2.a.a() != false ? 200 : 300)) / 127 << 10 : var7_47 * (var4_29 == 0 && var1_2.a.a() != false ? 200 : 300) << 10;
                            var9_57 = var1_2.a.a.length;
                            for (var2_14 = 0; var2_14 < var9_57; ++var2_14) {
                                var3_22 = var1_2.a.a[var2_14];
                                var4_29 = -var3_22.a.b + var8_52.b;
                                var5_38 = var3_22.a.a - var8_52.a;
                                if (var4_29 == 0 || var5_38 == 0) continue;
                                var6_43 = var7_47 / al.a(var4_29, var5_38);
                                var3_22.c.a += var4_29 * var6_43 >> 10;
                                var3_22.c.b += var5_38 * var6_43 >> 10;
                            }
                        }
                    }
                    var1_2.j = 0;
                }
                if (this.a.b != 32) {
                    v12 = this;
                    var1_2 = v12;
                    var2_15 = v12.a.a();
                    if (var2_15 < 0) {
                        var2_15 = -var2_15;
                    }
                    if (var2_15 < var1_2.a.e >> 1) {
                        var1_2.b(102400);
                    }
                }
                break block189;
            }
            if (this.d == 1) {
                var2_16 = this.a;
                this.a.a = true;
                this.a.d();
                this.a.e();
                if (this.a.b == 1 && this.r++ >= 100) {
                    if (this.a == 0) {
                        this.a.d = (byte)2;
                    } else if (this.e == 3 && this.a.a[this.a.f].d == 0 && !this.a.a[this.a.f].c) {
                        this.a.c(129);
                    }
                }
                if ((this.a.e == 4 || this.a.e == 5) && this.r++ >= 50) {
                    this.a.d = (byte)5;
                    if (this.a.b == 2) {
                        this.a.a = (byte)3;
                    }
                    v13 = this.a.a = this.b == 0 ? 1 : 0;
                    this.a.e[v13] = this.a.e[v13] + 1;
                }
            } else if (this.d == 2) {
                ++this.c;
                if (this.c == 10) {
                    this.c = 0;
                    this.d = 0;
                }
            }
        }
        if (this.a.g > 1 || this.a.b == 11) {
            for (var1_3 = 0; var1_3 != this.a; ++var1_3) {
                var2_17 = this.a[var1_3];
                if (this.a.b == 1 && !al.a(this.a.d, this.a[var1_3].a()) || this.a.b == 2 && !this.a.a(this.a[var1_3].a())) {
                    var2_17[0] = 30;
                    var2_17[1] = 3;
                }
                if (var2_17[0] == 30) {
                    var2_17[1] = var2_17[1] + 1;
                    if (var2_17[1] != 4) continue;
                    this.a.a.b(this.a[var1_3].c);
                    --this.a;
                    if (var1_3 != this.a) {
                        this.a[var1_3] = this.a[this.a];
                        var2_17[0] = this.a[this.a][0];
                        var2_17[1] = this.a[this.a][1];
                    }
                    this.a[this.a] = null;
                    --var1_3;
                    continue;
                }
                if (var2_17[0] >= 29 && (this.a[var1_3].a.b & 1) == 0) continue;
                var2_17[0] = var2_17[0] + 1;
            }
            if (this.k > 0) {
                --this.k;
            }
        }
        this.b = false;
    }

    public final boolean a() {
        return this.d == 1;
    }

    public final void b(int n2) {
        int n3;
        Object object;
        if (this.a.a.g) {
            return;
        }
        if (this.b == this.a.f) {
            this.a.a.a(Main.b / Main.c);
        }
        this.c = 2;
        this.d -= n2;
        if (this.d <= 0) {
            this.d = 0;
            object = this;
            if (((d)object).d == 0 && (((d)object).a != 1 || ((d)object).e == 0 || ((d)object).e == 3)) {
                if (al.a(((d)object).a.d, ((d)object).a.a())) {
                    ((d)object).a.a.a(4, false);
                }
                ((d)object).d = 1;
                ((d)object).r = 0;
                ((d)object).a.a.b(((d)object).a);
                n3 = ((d)object).a.a.length;
                for (int i2 = 0; i2 < n3; ++i2) {
                    as as2 = new as(((d)object).a.a[i2], 4096);
                    if (((d)object).a != 1 || ((d)object).e == 0) {
                        as2.d = 6;
                    }
                    as2.a = object;
                    ((d)object).a.a.a(as2);
                }
                ((d)object).g = n3;
            }
        }
        if ((this.a.g > 1 || this.a.b == 11) && this.k == 0) {
            this.k = 2;
            if ((n2 >>= 10) == 0) {
                n2 = 1;
            }
            object = this.a.a();
            for (n3 = 0; this.a != 16 && n3 < n2; ++n3) {
                x x2 = new x(new bg(al.b((int)(object[0] + 5120), (int)(object[2] - 5120)), al.b((int)(object[1] + 5120), (int)(object[3] - 10240))), 2048);
                new x(new bg(al.b((int)(object[0] + 5120), (int)(object[2] - 5120)), al.b((int)(object[1] + 5120), (int)(object[3] - 10240))), 2048).b.a = x2.a.a + al.b(-3072, 3072);
                x2.b.b = x2.a.b + al.b(2048, 3072);
                this.a[this.a] = new as(x2, 5120);
                this.a[this.a].b = true;
                this.a.a.a(this.a[this.a]);
                this.a[this.a][0] = 0;
                this.a[this.a][1] = 0;
                ++this.a;
            }
        }
    }

    public final void b() {
        this.h = false;
        this.g = false;
        this.f = false;
        this.e = false;
        this.d = false;
        this.i = 0;
        this.h = 0;
        this.j = true;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void a(Graphics graphics) {
        int n2;
        int n3;
        int n4;
        block60: {
            block59: {
                int n5;
                block63: {
                    block68: {
                        block67: {
                            block66: {
                                block65: {
                                    block64: {
                                        block62: {
                                            block58: {
                                                block61: {
                                                    if (this.a.g > 1 || this.a.b == 11) {
                                                        graphics.setColor(74, 0, 0);
                                                        for (n4 = 0; n4 != this.a; ++n4) {
                                                            n3 = this.a[n4].a.a.a >> 10;
                                                            n5 = this.a[n4].a.a.b >> 10;
                                                            if (this.a[n4][0] == 30) {
                                                                n2 = 5 * (5 - this.a[n4][1]) / 6 << 10 >> 10;
                                                                graphics.fillArc(n3 - n2, n5 - n2, n2 << 1, n2 << 1, 0, 360);
                                                                continue;
                                                            }
                                                            graphics.drawImage(this.a.a.a(this.a[n4][0] > 15 ? 129 : 118), n3, n5, 3);
                                                        }
                                                    }
                                                    if (this.d != 0) break block60;
                                                    if (this.a && this.a.g > 1 && (this.a.d & 1) == 0 && al.b(0, 1) == 0) {
                                                        bg bg2 = this.a.a();
                                                        this.a.b(bg2.a >> 10, bg2.b >> 10, 0, 2);
                                                    }
                                                    if (this.o != -1) break block61;
                                                    n4 = 1647099 - this.n;
                                                    if (this.p != 0 || (this.a.b & 1) == 0 || n4 >= 183011 || n4 <= -183011) break block58;
                                                    n3 = 0;
                                                    if (this.a.b == 1) {
                                                        this.f = n3 = this.a.c().d();
                                                    }
                                                    if (n3 < 1024) {
                                                        if (al.b(0, 2) == 0) {
                                                            this.o = 0;
                                                            break block58;
                                                        } else {
                                                            this.p = 5;
                                                        }
                                                    }
                                                    break block58;
                                                }
                                                ++this.o;
                                                if (this.o == 5) {
                                                    this.o = -1;
                                                    this.p = 20;
                                                    this.n = 1647099;
                                                }
                                            }
                                            if (this.p > 0) {
                                                --this.p;
                                            }
                                            if (this.e <= 0) break block62;
                                            this.n = 1647099;
                                            this.q = -1;
                                            if (this.s < 1) {
                                                ++this.s;
                                            }
                                            break block63;
                                        }
                                        if (this.s <= -1) break block64;
                                        --this.s;
                                        break block63;
                                    }
                                    if (this.q != -1 || (this.a.b & 1) != 0) break block65;
                                    n4 = 1647099 - this.n;
                                    if (n4 < 253399 && n4 > -253399) {
                                        ++this.r;
                                        if (this.r == 2) {
                                            this.q = 0;
                                            this.r = 0;
                                        }
                                    }
                                    break block63;
                                }
                                if (this.q >= 2 || this.q <= -1) break block66;
                                ++this.q;
                                break block63;
                            }
                            if (this.q != 2 || (this.a.b & 1) == 0) break block67;
                            this.q = 3;
                            break block63;
                        }
                        if (this.q <= 2) break block68;
                        ++this.q;
                        if (this.q != 6) break block63;
                        this.q = -1;
                    }
                    this.r = 0;
                }
                if (this.q != -1 || this.s != -1 || this.t != -1) {
                    this.n = 1647099;
                } else if (this.o == -1) {
                    bg bg3 = this.a.b();
                    if (bg3.a()) {
                        bg3.b = 1024;
                    }
                    n3 = bg3.a();
                    if (this.n == Integer.MAX_VALUE) {
                        this.n = n3;
                    } else {
                        n5 = n3 - this.n;
                        if (n5 < -3294198) {
                            n5 += 6588397;
                        } else if (n5 > 3294198) {
                            n5 -= 6588397;
                        }
                        this.n = al.a(this.n + n5 / 6);
                    }
                }
                this.a.a(this.a.a, this.a.a());
                int n6 = 0;
                n6 = this.a == 1 ? -6710887 : a[this.b];
                if (this.c > 0) {
                    if (this.t++ == 2) {
                        this.t = 0;
                    }
                    int[] nArray = new int[]{-65536, -5177344, -10878976};
                    n6 = nArray[this.t];
                    --this.c;
                } else {
                    this.t = -1;
                }
                this.a.a(graphics, n6);
                try {
                    int n7;
                    int n8;
                    int n9;
                    int n10;
                    int n11;
                    int n12;
                    if (this.a.g <= 1 || this.t != -1) break block59;
                    Graphics graphics2 = graphics;
                    d d2 = this;
                    bg bg4 = d2.a.a();
                    n2 = bg4.a >> 15;
                    int n13 = bg4.b >> 15;
                    if (n2 < 0 || n13 < 0 || n2 >= d2.a.d || n13 >= d2.a.e || (n12 = d2.a.a[0][n2][n13]) <= -1 || k.b[n12]) break block59;
                    int n14 = d2.a.a.length;
                    int n15 = -1;
                    int n16 = 0;
                    if (d2.u == n2 && d2.v == n13) {
                        n15 = d2.w;
                        n16 = d2.x;
                    } else {
                        n11 = Integer.MAX_VALUE;
                        n12 = n2 - 8;
                        n10 = n13 - 8;
                        n9 = n2 + 8;
                        n8 = n13 + 8;
                        n12 = n12 < 0 ? 0 : n12;
                        n10 = n10 < 0 ? 0 : n10;
                        n9 = n9 >= d2.a.d ? d2.a.d - 1 : n9;
                        n8 = n8 >= d2.a.e ? d2.a.e - 1 : n8;
                        for (n7 = n12; n7 <= n9; ++n7) {
                            for (int i2 = n10; i2 <= n8; ++i2) {
                                int n17;
                                int n18;
                                n12 = d2.a.a[0][n7][i2];
                                if (n12 != 8 && n12 != 9 && n12 != 30 && d2.a.a[2][n7][i2] != 37 || !d2.a.a(bg4.a, bg4.b, n7 << 15, i2 << 15) || (n18 = (n12 = n7 - n2) * n12 + (n17 = i2 - n13) * n17) >= n11) continue;
                                n15 = n7;
                                n16 = i2;
                                n11 = n18;
                            }
                        }
                    }
                    if (d2.a.a == 2 && n15 == -1) {
                        n15 = (bg4.a >> 15) - 2;
                        n16 = (bg4.b >> 15) - 4;
                        if (n15 == -1) {
                            n15 = -2;
                        }
                    }
                    if (n15 != -1) {
                        d2.u = n2;
                        d2.v = n13;
                        d2.w = n15;
                        d2.x = n16;
                        n11 = (n15 << 15) + 16384 - bg4.a;
                        int n19 = (n16 << 15) + 16384 - bg4.b;
                        n12 = al.a(n11, n19);
                        n11 = (int)(((long)n11 << 10) / (long)n12);
                        n19 = (int)(((long)n19 << 10) / (long)n12);
                        graphics2.setColor(a[d2.a.a][d2.a][0], a[d2.a.a][d2.a][1], a[d2.a.a][d2.a][2]);
                        n10 = 0;
                        n9 = n14 - 1;
                        n8 = n14 - 2;
                        n7 = n14 - 3;
                        while (n10 < n14) {
                            bg bg5 = d2.a.a[n10].a;
                            bg bg6 = d2.a.a[n9].a;
                            bg bg7 = d2.a.a[n8].a;
                            bg bg8 = d2.a.a[n7].a;
                            n2 = bg6.b - bg7.b;
                            n13 = bg7.a - bg6.a;
                            if (n2 * n11 + n13 * n19 > 0 && n13 * (bg5.a - bg6.a) - n2 * (bg5.b - bg6.b) <= 0 && n13 * (bg7.a - bg8.a) - n2 * (bg7.b - bg8.b) <= 0) {
                                n15 = al.a(n2, n13);
                                if ((n15 = (n2 = (int)(((long)n2 << 10) / (long)n15)) * n19 - (n13 = (int)(((long)n13 << 10) / (long)n15)) * n11 >> 10) < 341 && n15 > -341) {
                                    n15 = (bg6.a + bg7.a >> 1) - n2 * 5;
                                    int n20 = (bg6.b + bg7.b >> 1) - n13 * 5;
                                    if (n2 < 0) {
                                        n2 = -n2;
                                    }
                                    if (n13 < 0) {
                                        n13 = -n13;
                                    }
                                    n2 = (8 * (1024 - n2) >> 10) + 2;
                                    n13 = (8 * (1024 - n13) >> 10) + 2;
                                    graphics2.fillArc((n15 >> 10) - n2, (n20 >> 10) - n13, n2 << 1, n13 << 1, 0, 360);
                                }
                            }
                            n7 = n8;
                            n8 = n9;
                            n9 = n10++;
                        }
                    }
                }
                catch (Exception exception) {}
            }
            if (this.a == 1) {
                this.a.a(graphics, bg.a, this.c == 0 ? 0x5D5D5D : (this.c == 2 ? 0xCCCCCC : 10834699));
            } else {
                int n21 = this.c == 1 ? (this.a.a == 2 ? 10976277 : 8610063) : (this.c == 2 ? (this.a.a == 2 ? 0xBBBBBB : (this.a.g == 0 ? 0x888888 : 0x636363)) : (this.a.g == 0 ? 0x363636 : 0));
                this.a.a(graphics, bg.a, n21);
            }
            bg bg9 = this.a.a();
            bg bg10 = bg9.b(new bg(this.n, 3072L, false));
            bg10.a >>= 10;
            bg10.b >>= 10;
            Image image = null;
            if (this.t != -1) {
                image = this.a.a.a(this.a == 1 ? 525 : 125);
                graphics.drawImage(image, bg9.a >> 10, (bg9.b >> 10) - 1, 3);
                graphics.drawImage(this.a.a.a(126 + this.t), bg9.a >> 10, (bg9.b >> 10) - 20, 3);
                return;
            }
            if (this.s != -1) {
                image = this.a.a.a(this.a == 1 ? 529 + this.s : 123 + this.s);
                graphics.drawImage(image, bg9.a >> 10, bg9.b >> 10, 3);
                return;
            }
            if (this.q != -1) {
                int n22 = this.q < 3 ? this.q : 5 - this.q;
                image = this.a.a.a(this.a == 1 ? n22 + 526 : n22 + 120);
                graphics.drawImage(image, bg9.a >> 10, (bg9.b >> 10) + 1, 3);
                return;
            }
            if (this.o == -1) {
                g.a(graphics, a[this.a], bg10, this.n - 1647099);
                return;
            }
            if (this.o == 2) return;
            image = this.a.a.a(this.o == 0 || this.o == 4 ? (this.a == 1 ? 516 : 110) : (this.a == 1 ? 524 : 119));
            graphics.drawImage(image, bg10.a, bg10.b, 3);
            return;
        }
        if (this.d != 1) {
            if (this.d != 2) return;
            if (this.a == 1) {
                graphics.setColor(-6710887);
            } else {
                graphics.setColor(a[this.b]);
            }
            bg bg11 = this.a.a();
            n3 = this.c * 24 / 10 << 10 >> 10;
            graphics.fillArc((bg11.a >> 10) - n3, (bg11.b >> 10) - n3, n3 << 1, n3 << 1, 0, 360);
            return;
        }
        n4 = this.a.a.length;
        n3 = 0;
        while (n3 < n4) {
            x x2 = this.a.a[n3];
            if ((x2.b & 0x10) == 0) {
                n2 = x2.a.a >> 10;
                int n23 = x2.a.b >> 10;
                if (this.a == 1) {
                    graphics.setColor(-6710887);
                    graphics.fillArc(n2 - 4, n23 - 4, 8, 8, 0, 360);
                } else {
                    graphics.drawImage(this.a.a.a(109), n2, n23, 3);
                }
            }
            ++n3;
        }
    }
}

