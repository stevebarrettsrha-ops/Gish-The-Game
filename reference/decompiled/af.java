/*
 * Decompiled with CFR 0.152.
 */
import java.util.Vector;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class af {
    private int g = 16;
    private int h = 32;
    private int i = 32;
    public Vector a;
    public Vector b;
    public Vector c;
    public boolean a;
    public f[] a;
    public int a;
    public h[] a;
    public int b;
    public as[] a;
    public int c;
    public byte[][] a;
    public byte[][] b;
    public bg[][] a;
    public boolean[] a;
    public int d;
    public int e;
    public k a;
    public c a;
    public c b;
    public c c;
    public int[][] a;
    public int[] a;
    public int[] b;
    public int[][] b;
    public int f;
    public boolean b = false;

    public final void a(byte[][] byArray, byte[][] byArray2, bg[][] bgArray, boolean[] blArray, int n2, int n3, int n4, int n5) {
        int n6;
        this.h = n3;
        this.g = n4;
        this.i = n5;
        this.a = byArray;
        this.b = byArray2;
        if (this.a == null) {
            return;
        }
        this.a = bgArray;
        this.a = blArray;
        this.d = 15;
        this.a = new c(this.a.length << this.d, this.a[0].length << this.d, this.h);
        this.c = new c(this.a.length << this.d, this.a[0].length << this.d, this.i);
        this.b = new c(this.a.length << this.d, this.a[0].length << this.d, this.g);
        this.a = new h[this.h];
        this.b = 0;
        this.a = new f[this.g];
        this.a = 0;
        this.a = new as[this.i];
        this.c = 0;
        this.a = true;
        int n7 = this.b.size();
        for (n6 = 0; n6 < n7; ++n6) {
            this.a((h)this.b.elementAt(n6));
        }
        n7 = this.a.size();
        for (n6 = 0; n6 < n7; ++n6) {
            this.a((f)this.a.elementAt(n6));
        }
        n7 = this.c.size();
        for (n6 = 0; n6 < n7; ++n6) {
            this.a((as)this.c.elementAt(n6));
        }
        this.b.removeAllElements();
        this.a.removeAllElements();
        this.c.removeAllElements();
        this.b = null;
        this.a = null;
        this.c = null;
        for (n6 = 0; n6 < this.b; ++n6) {
            this.a.a(n6, this.a[n6].a());
        }
        for (n6 = 0; n6 < this.a; ++n6) {
            this.b.a(n6, this.a[n6].a());
        }
        for (n6 = 0; n6 < this.c; ++n6) {
            this.c.a(n6, this.a[n6].a());
        }
    }

    public final void a(h h2) {
        if (!this.a) {
            this.b.addElement(h2);
            return;
        }
        h2.d &= 0xFFFFFFEF;
        h2.i = this.b;
        this.a[this.b++] = h2;
        if (this.a != null) {
            this.a.a(this.b - 1, h2.a());
        }
    }

    public final void a(f f2) {
        if (!this.a) {
            this.a.addElement(f2);
            return;
        }
        this.a[this.a++] = f2;
        f2.b = this.a - 1;
        if (this.b != null) {
            this.b.a(f2.b, f2.a());
        }
    }

    public final void b(h h2) {
        for (int i2 = 0; i2 < this.b; ++i2) {
            if (this.a[i2] != h2) continue;
            this.a(i2);
            return;
        }
    }

    public final void a(int n2) {
        this.a.b(n2, this.a[n2].a());
        --this.b;
        if (n2 != this.b) {
            this.a.b(this.b, this.a[this.b].a());
        }
        h h2 = this.a[this.b];
        if (this.b > n2) {
            this.a[n2] = this.a[this.b];
            this.a[n2].i = n2;
            this.a.a(n2, this.a[n2].a());
        }
        this.a[this.b] = null;
        if (this.a != null) {
            int n3 = -1;
            for (int i2 = 1; i2 <= this.a[0]; ++i2) {
                if (this.a[i2] == n2) {
                    n3 = i2;
                }
                if (this.a[i2] != this.b) continue;
                this.a[i2] = h2.i;
            }
            if (n3 != -1) {
                if (n3 < this.a[0]) {
                    this.a[n3] = this.a[this.a[0]];
                }
                this.a[0] = this.a[0] - 1;
            }
        }
    }

    public final void a(as as2) {
        if (!this.a) {
            this.c.addElement(as2);
            return;
        }
        this.a[this.c++] = as2;
        as2.c = this.c - 1;
        if (this.c != null) {
            this.c.a(this.c - 1, as2.a());
        }
    }

    public final void a(x x2) {
        for (int i2 = 0; i2 < this.c; ++i2) {
            if (!this.a[i2].a.equals(x2)) continue;
            this.b(i2);
            return;
        }
    }

    public final void b(int n2) {
        this.c.b(n2, this.a[n2].a());
        --this.c;
        as as2 = this.a[this.c];
        if (n2 < this.c) {
            this.c.b(this.c, this.a[this.c].a());
            this.a[n2] = this.a[this.c];
            this.c.a(n2, this.a[n2].a());
            this.a[n2].c = n2;
        }
        this.a[this.c] = null;
        if (this.b != null) {
            int n3 = -1;
            for (int i2 = 1; i2 <= this.b[0]; ++i2) {
                if (this.b[i2] == n2) {
                    n3 = i2;
                }
                if (this.b[i2] != this.c) continue;
                this.b[i2] = as2.c;
            }
            if (n3 != -1) {
                if (n3 < this.b[0]) {
                    this.b[n3] = this.b[this.b[0]];
                }
                this.b[0] = this.b[0] - 1;
            }
        }
    }

    public void a() {
        int n2;
        Object object;
        int n3;
        int n4;
        if (this.a == null) {
            this.a = this.a.a(-1, this.b, this.a);
            n4 = this.a[0];
            for (n3 = 1; n3 <= n4; ++n3) {
                this.a[this.a[n3]].d |= 0x10;
            }
            for (n3 = 1; n3 <= n4; ++n3) {
                object = this.a[this.a[n3]];
                if (((h)object).a == null) continue;
                for (n2 = 0; n2 < ((h)object).a.length; ++n2) {
                    if ((((h)object).a[n2].d & 0x30) != 0) continue;
                    ((h)object).a[n2].d |= 0x10;
                    this.a[0] = this.a[0] + 1;
                    this.a[this.a[0]] = ((h)object).a[n2].i;
                    ++n4;
                }
            }
        }
        if (this.b == null) {
            this.b = this.c.a(-1, this.c, this.a);
            n4 = this.b[0];
            for (n3 = 1; n3 <= n4; ++n3) {
                object = this.a[this.b[n3]];
                if (((as)object).a == null) continue;
                n2 = 0;
                for (int i2 = 1; i2 <= n4; ++i2) {
                    if (n3 == i2 || this.b[i2] != ((as)object).a.c) continue;
                    n2 = 1;
                    break;
                }
                if (n2 != 0) continue;
                this.b[0] = this.b[0] + 1;
                this.b[this.b[0]] = ((as)object).a.c;
                ++n4;
            }
        }
    }

    public static boolean a(x x2, x x3, bg[] bgArray, int n2, int n3, int n4, int n5, int n6, boolean bl) {
        int n7;
        int n8;
        int n9 = n3 - n5;
        int n10 = n4 - n2;
        int n11 = Integer.MAX_VALUE;
        int n12 = 1;
        int n13 = 0;
        if (bgArray[0].a == bgArray[bgArray.length - 1].a && bgArray[0].b == bgArray[bgArray.length - 1].b) {
            n12 = 0;
            n13 = bgArray.length - 2;
        }
        while (n12 < bgArray.length - 1) {
            n8 = n12 + 1;
            if (bgArray[n8] == null) {
                n12 += 2;
            } else {
                int n14;
                int n15;
                int n16;
                bg bg2 = bgArray[n13];
                bg bg3 = bgArray[n12];
                bg bg4 = bgArray[n8];
                n8 = (int)((long)n9 * (long)(bg3.a - n2) + (long)n10 * (long)(bg3.b - n3) >> 10);
                if (n8 >= 0 && n8 < n11 && ((n16 = (int)((long)((n7 = bg2.a + bg4.a >> 1) - n4) * (long)(bg3.b - n5) - (long)((n15 = bg2.b + bg4.b >> 1) - n5) * (long)(bg3.a - n4) >> 10)) ^ (n14 = (int)((long)(n7 - n2) * (long)(bg3.b - n3) - (long)(n15 - n3) * (long)(bg3.a - n2) >> 10))) < 0 && ((n15 = (int)((long)(n2 - n7) * (long)(n5 - n15) - (long)(n3 - n15) * (long)(n4 - n7) >> 10)) ^ (n16 = n15 + n14 - n16)) < 0) {
                    n11 = n8;
                }
            }
            n13 = n12++;
        }
        if (n11 == Integer.MAX_VALUE) {
            return false;
        }
        long l2 = (long)n9 * (long)n9 + (long)n10 * (long)n10 >> 10;
        n13 = (int)((long)n9 * (long)n11 / l2);
        n8 = (int)((long)n10 * (long)n11 / l2);
        if (x2.a != Integer.MAX_VALUE) {
            x2.a.a += n13;
            x2.a.b += n8;
        }
        if (x3.a != Integer.MAX_VALUE) {
            x3.a.a += n13;
            x3.a.b += n8;
        }
        if (n6 != 0) {
            if (bl) {
                n7 = al.a(n9, n10);
                n9 = (int)(((long)n9 << 10) / (long)n7);
                n10 = (int)(((long)n10 << 10) / (long)n7);
                n13 = (x2.a.a + x3.a.a >> 1) - (x2.b.a + x3.b.a >> 1);
                int n17 = (x2.a.b + x3.a.b >> 1) - (x2.b.b + x3.b.b >> 1);
                int n18 = -n10 * n13 + n9 * n17 >> 10;
                if (n6 != 1024) {
                    n18 = n18 * n6 >> 10;
                }
                n13 = -n10 * n18 >> 10;
                n17 = n9 * n18 >> 10;
                x2.b.a += n13 >> 1;
                x2.b.b += n17 >> 1;
                x3.b.a += n13 >> 1;
                x3.b.b += n17 >> 1;
            } else if (n6 == 1024) {
                x2.b.a = x2.a.a;
                x2.b.b = x2.a.b;
                x3.b.a = x3.a.a;
                x3.b.b = x3.a.b;
            } else {
                x2.b.a += (x2.a.a - x2.b.a) * n6 >> 10;
                x2.b.b += (x2.a.b - x2.b.b) * n6 >> 10;
                x3.b.a += (x3.a.a - x3.b.a) * n6 >> 10;
                x3.b.b += (x3.a.b - x3.b.b) * n6 >> 10;
            }
        }
        return true;
    }

    public static bg a(x x2, bg[] bgArray, int n2, int n3, int n4) {
        int n5 = Integer.MAX_VALUE;
        int n6 = 0;
        int n7 = 0;
        bg bg2 = new bg();
        int n8 = 0;
        for (int i2 = 1; i2 < bgArray.length; ++i2) {
            if (bgArray[i2] != null) {
                bg2.a = bgArray[n8].b - bgArray[i2].b;
                bg2.b = bgArray[i2].a - bgArray[n8].a;
                int n9 = n2 - bgArray[n8].a;
                n8 = n3 - bgArray[n8].b;
                if ((long)n9 * (long)bg2.a + (long)n8 * (long)bg2.b < 0L) {
                    return null;
                }
                bg2.a();
                n9 = n9 * bg2.a + n8 * bg2.b >> 10;
                if (n9 < n5) {
                    n5 = n9;
                    n6 = bg2.a;
                    n7 = bg2.b;
                }
            }
            n8 = ++i2;
        }
        x2.a.a -= n6 * n5 >> 10;
        x2.a.b -= n7 * n5 >> 10;
        if (n4 != 0) {
            if (n4 == 1024) {
                x2.b.a = x2.a.a;
                x2.b.b = x2.a.b;
            } else {
                x2.b.a += (x2.a.a - x2.b.a) * n4 >> 10;
                x2.b.b += (x2.a.b - x2.b.b) * n4 >> 10;
            }
        }
        bg2.a = n6;
        bg2.b = n7;
        return bg2;
    }

    public static bg a(x x2, int n2, bg[] bgArray, int n3, int n4, int n5) {
        int n6 = Integer.MAX_VALUE;
        int n7 = 0;
        int n8 = 0;
        bg bg2 = new bg();
        int n9 = bgArray.length;
        int n10 = 0;
        for (int i2 = 1; i2 < n9; ++i2) {
            if (bgArray[i2] != null) {
                int n11;
                bg bg3 = bgArray[i2];
                bg bg4 = bgArray[n10];
                int n12 = bg3.a - bg4.a;
                int n13 = n3 - bg4.a;
                int n14 = bg3.b - bg4.b;
                n10 = n4 - bg4.b;
                int n15 = (int)((long)n12 * (long)n13 + (long)n14 * (long)n10 >> 10);
                if (n15 < 0) {
                    n11 = al.a(n13, n10);
                    int n16 = n2 - n11;
                    if (n16 >= 0 && n16 < n6) {
                        n6 = n16;
                        n7 = (int)(((long)(-n13) << 10) / (long)n11);
                        n8 = (int)(((long)(-n10) << 10) / (long)n11);
                    }
                } else {
                    int n17;
                    n11 = (int)((long)n12 * (long)n12 + (long)n14 * (long)n14 >> 10);
                    if (n15 <= n11) {
                        bg2.a = -n14;
                        bg2.b = n12;
                        bg2.a();
                        int n18 = (bg2.a * n13 + bg2.b * n10 >> 10) + n2;
                        if (n18 < 0) {
                            return null;
                        }
                        if (n18 < n6) {
                            n6 = n18;
                            n7 = bg2.a;
                            n8 = bg2.b;
                        }
                    } else if ((i2 == n9 - 1 || bgArray[i2 + 1] == null) && (n12 = n2 - (n17 = al.a(n13 = n3 - bg3.a, n10 = n4 - bg3.b))) >= 0 && n12 < n6) {
                        n6 = n12;
                        n7 = (int)(((long)(-n13) << 10) / (long)n17);
                        n8 = (int)(((long)(-n10) << 10) / (long)n17);
                    }
                }
            }
            n10 = ++i2;
        }
        x2.a.a -= n7 * n6 >> 10;
        x2.a.b -= n8 * n6 >> 10;
        if (n5 != 0) {
            if (n5 == 1024) {
                x2.b.a = x2.a.a;
                x2.b.b = x2.a.b;
            } else {
                x2.b.a += (x2.a.a - x2.b.a) * n5 >> 10;
                x2.b.b += (x2.a.b - x2.b.b) * n5 >> 10;
            }
        }
        bg2.a = n7;
        bg2.b = n8;
        return bg2;
    }

    public static boolean a(h h2, h h3) {
        int n2 = 0;
        int n3 = h2.a.length;
        int n4 = h3.a.length;
        int[] nArray = h2.a();
        int[] nArray2 = h3.a();
        int n5 = 0;
        int n6 = 0;
        int n7 = 0;
        int n8 = 0;
        int n9 = 0;
        int n10 = 0;
        int n11 = 0;
        int n12 = 0;
        int n13 = 0;
        int n14 = 0;
        int n15 = 0;
        int n16 = 0;
        int n17 = n3 - 1;
        int n18 = n3 - 2;
        while (n16 < n3) {
            x x2 = h2.a[n17];
            bg bg2 = x2.a;
            if (x2.a != Integer.MAX_VALUE && al.a(nArray2, bg2) && h3.a(bg2)) {
                int n19;
                int n20;
                int n21;
                x x3;
                x x4;
                int n22 = h2.a[n18].a.b - h2.a[n16].a.b;
                int n23 = h2.a[n16].a.a - h2.a[n18].a.a;
                n2 = Integer.MAX_VALUE;
                n18 = -1;
                int n24 = Integer.MAX_VALUE;
                n10 = -1;
                int n25 = 0;
                int n26 = n4 - 1;
                while (n25 < n4) {
                    x4 = h3.a[n25];
                    x3 = h3.a[n26];
                    if (x3.a.a > x4.a.a) {
                        n21 = x3.a.a;
                        n20 = x4.a.a;
                    } else {
                        n21 = x4.a.a;
                        n20 = x3.a.a;
                    }
                    if (n21 >= nArray[0] && n20 <= nArray[2]) {
                        if (x3.a.b > x4.a.b) {
                            n21 = x3.a.b;
                            n20 = x4.a.b;
                        } else {
                            n21 = x4.a.b;
                            n20 = x3.a.b;
                        }
                        if (n21 >= nArray[1] && n20 <= nArray[3]) {
                            n20 = x4.a.a - x3.a.a;
                            int n27 = bg2.a - x3.a.a;
                            n21 = x4.a.b - x3.a.b;
                            int n28 = bg2.b - x3.a.b;
                            n19 = (int)((long)n20 * (long)n27 + (long)n21 * (long)n28 >> 10);
                            if (n19 < 0) {
                                int n29 = al.a(n27, n28);
                                if ((int)((long)(-n21) * (long)n22 + (long)n20 * (long)n23 >> 10) > 0) {
                                    if (n29 < n24) {
                                        n24 = n29;
                                        n14 = n27;
                                        n15 = n28;
                                        n10 = -1;
                                        n8 = n26;
                                    }
                                } else if (n29 < n2) {
                                    n2 = n29;
                                    n12 = n27;
                                    n13 = n28;
                                    n18 = -1;
                                    n5 = n26;
                                }
                            } else {
                                int n30 = (int)((long)n20 * (long)n20 + (long)n21 * (long)n21 >> 10);
                                if (n19 <= n30) {
                                    int n31 = al.a(n20, n21);
                                    n20 = (int)(((long)n20 << 10) / (long)n31);
                                    n21 = (int)(((long)n21 << 10) / (long)n31);
                                    n31 = (int)((long)n20 * (long)n28 - (long)n21 * (long)n27 >> 10);
                                    if ((int)((long)(-n21) * (long)n22 + (long)n20 * (long)n23 >> 10) > 0) {
                                        if (n31 < n24) {
                                            n24 = n31;
                                            n14 = -n21;
                                            n15 = n20;
                                            n8 = n26;
                                            n9 = n25;
                                            n10 = n19;
                                            n11 = n30;
                                        }
                                    } else if (n31 < n2) {
                                        n2 = n31;
                                        n12 = -n21;
                                        n13 = n20;
                                        n5 = n26;
                                        n6 = n25;
                                        n18 = n19;
                                        n7 = n30;
                                    }
                                }
                            }
                        }
                    }
                    n26 = n25++;
                }
                if (n2 == Integer.MAX_VALUE || n24 != Integer.MAX_VALUE && (n24 < 0 ? -n24 : n24) < (n2 < 0 ? -n2 : n2) >> 1) {
                    n2 = n24;
                    n12 = n14;
                    n13 = n15;
                    n5 = n8;
                    n6 = n9;
                    n18 = n10;
                    n7 = n11;
                }
                if (n18 == -1) {
                    x2 = h2.a[n17];
                    x3 = h3.a[n5];
                    n20 = x2.a == x3.a ? 512 : (x2.a == x3.a << 1 ? 682 : (x2.a == x3.a >> 1 ? 341 : (int)(((long)x2.a << 10) / (long)(x2.a + x3.a))));
                    n21 = n20 - 1024;
                    if ((n12 != 0 || n13 != 0) && (n23 = h2.c + h3.c >> 1) != 0) {
                        n10 = x2.a.a - x2.b.a - x3.a.a + x3.b.a;
                        n22 = x2.a.b - x2.b.b - x3.a.b + x3.b.b;
                        long l2 = ((long)(-n13) * (long)n10 + (long)n12 * (long)n22 << 10) / ((long)n12 * (long)n12 + (long)n13 * (long)n13);
                        if (n23 != 1024) {
                            l2 = l2 * (long)n23 >> 10;
                        }
                        n10 = (int)((long)(-n13) * l2 >> 10);
                        n22 = (int)((long)n12 * l2 >> 10);
                        x2.b.a -= n10 * n21 >> 10;
                        x2.b.b -= n22 * n21 >> 10;
                        x3.b.a -= n10 * n20 >> 10;
                        x3.b.b -= n22 * n20 >> 10;
                    }
                    x2.a.a += n12 * n21 >> 10;
                    x2.a.b += n13 * n21 >> 10;
                    x3.a.a += n12 * n20 >> 10;
                    x3.a.b += n13 * n20 >> 10;
                    x2.b |= 1;
                    x3.b |= 1;
                    if (((h2.d & 1) != 0 || (h3.d & 1) != 0) && (x2.b & 4) == 0 && (x3.b & 4) == 0) {
                        ag ag2 = new ag(x2, x3, 512, 10240, 0);
                        n26 = (h2.d & 1) != 0 ? (int)(h2.a(ag2) ? 1 : 0) : (int)(h3.a(ag2) ? 1 : 0);
                        if (n26 != 0) {
                            x2.b |= 4;
                            x3.b |= 4;
                        }
                    }
                } else {
                    x2 = h2.a[n17];
                    x4 = h3.a[n6];
                    x3 = h3.a[n5];
                    n20 = x2.a == Integer.MAX_VALUE ? 1024 : (x3.a == Integer.MAX_VALUE ? 0 : (x2.a == x3.a && x3.a == x4.a ? 512 : (x2.a == x3.a << 1 && x3.a == x4.a ? 682 : (x2.a == x3.a >> 1 && x3.a == x4.a ? 341 : (int)(((long)x2.a << 10) / (long)(x2.a + (x4.a + x3.a >> 1)))))));
                    n21 = n20 - 1024;
                    n18 = n7 == 0 ? 0 : (int)(((long)n18 << 10) / (long)n7);
                    n24 = 1024 - n18;
                    if ((n12 != 0 || n13 != 0) && (n23 = h2.c + h3.c >> 1) != 0) {
                        n10 = x2.a.a - x2.b.a - (x3.a.a + x4.a.a >> 1) + (x3.b.a + x4.b.a >> 1);
                        n22 = x2.a.b - x2.b.b - (x3.a.b + x4.a.b >> 1) + (x3.b.b + x4.b.b >> 1);
                        n19 = -n13 * n10 + n12 * n22 >> 10;
                        if (n23 != 1024) {
                            n19 = n19 * n23 >> 10;
                        }
                        n10 = -n13 * n19 >> 10;
                        n22 = n12 * n19 >> 10;
                        x2.b.a -= n10 * n21 >> 10;
                        x2.b.b -= n22 * n21 >> 10;
                        n10 = n10 * n20 >> 10;
                        n22 = n22 * n20 >> 10;
                        x3.b.a -= n10 * n24 >> 10;
                        x3.b.b -= n22 * n24 >> 10;
                        x4.b.a -= n10 * n18 >> 10;
                        x4.b.b -= n22 * n18 >> 10;
                    }
                    n12 = n12 * n2 >> 10;
                    n13 = n13 * n2 >> 10;
                    x2.a.a += n12 * n21 >> 10;
                    x2.a.b += n13 * n21 >> 10;
                    n10 = n12 * n20 >> 10;
                    n22 = n13 * n20 >> 10;
                    x3.a.a += n10 * n24 >> 10;
                    x3.a.b += n22 * n24 >> 10;
                    x4.a.a += n10 * n18 >> 10;
                    x4.a.b += n22 * n18 >> 10;
                    x2.b |= 1;
                    x3.b |= 1;
                    x4.b |= 1;
                    if (((h2.d & 1) != 0 || (h3.d & 1) != 0) && (x2.b & 4) == 0) {
                        ag ag3 = new ag(x2, new e(x3, x4, n18), 512, 10240, 0);
                        n26 = (h2.d & 1) != 0 ? (int)(h2.a(ag3) ? 1 : 0) : (int)(h3.a(ag3) ? 1 : 0);
                        if (n26 != 0) {
                            x2.b |= 4;
                        }
                    }
                }
                n2 = 1;
            }
            n18 = n17;
            n17 = n16++;
        }
        return n2 != 0;
    }

    public static int a(bg bg2, x[] xArray, x[] xArray2) {
        int n2;
        int n3;
        int[] nArray = new int[xArray.length];
        nArray[0] = n3 = (int)((long)xArray[0].a.a * (long)bg2.a + (long)xArray[0].a.b * (long)bg2.b >> 10);
        for (n2 = 1; n2 < xArray.length; ++n2) {
            nArray[n2] = (int)((long)xArray[n2].a.a * (long)bg2.a + (long)xArray[n2].a.b * (long)bg2.b >> 10);
            if (nArray[n2] >= n3) continue;
            n3 = nArray[n2];
        }
        n2 = 0;
        int[] nArray2 = new int[2];
        boolean bl = false;
        for (int i2 = 0; i2 < xArray.length; ++i2) {
            int n4;
            if (nArray[i2] >= n3 + 1024) continue;
            int n5 = (int)((long)xArray[i2].a.a * (long)(-bg2.b) + (long)xArray[i2].a.b * (long)bg2.a >> 10);
            if (n2 < 2) {
                nArray2[n2] = n5;
                xArray2[n2] = xArray[i2];
                if (++n2 <= 1) continue;
                bl = nArray2[1] > nArray2[0];
                continue;
            }
            int n6 = bl ? 0 : 1;
            int n7 = n4 = bl ? 1 : 0;
            if (n5 < nArray2[n6]) {
                nArray2[n6] = n5;
                xArray2[n6] = xArray[i2];
                continue;
            }
            if (n5 <= nArray2[n4]) continue;
            nArray2[n4] = n5;
            xArray2[n4] = xArray[i2];
        }
        return n2;
    }

    public static boolean a(x[] xArray, x[] xArray2, int[][] object, int[] nArray, int n2) {
        object = object[n2];
        int n3 = 0;
        int n4 = 0;
        int n5 = 0;
        int n6 = 0;
        for (int i2 = 0; i2 < 2; ++i2) {
            int n7;
            x[] xArray3 = i2 == 0 ? xArray : xArray2;
            bg bg2 = xArray3[0].a;
            int n8 = n7 = (int)((long)bg2.a * (long)object[0] + (long)bg2.b * (long)object[1] >> 10);
            int n9 = xArray3.length;
            for (int i3 = 1; i3 < n9; ++i3) {
                bg2 = xArray3[i3].a;
                int n10 = (int)((long)bg2.a * (long)object[0] + (long)bg2.b * (long)object[1] >> 10);
                if (n10 < n8) {
                    n8 = n10;
                    continue;
                }
                if (n10 <= n7) continue;
                n7 = n10;
            }
            if (i2 == 0) {
                n3 = n7 - n8 >> 1;
                n4 = n7 + n8 >> 1;
                continue;
            }
            n5 = n8 - n3 - n4;
            n6 = n7 + n3 - n4;
        }
        if (n5 <= 0 && n6 >= 0) {
            if ((n5 < 0 ? -n5 : n5) < (n6 < 0 ? -n6 : n6)) {
                object[0] = -object[0];
                object[1] = -object[1];
                nArray[n2] = -n5;
            } else {
                nArray[n2] = n6;
            }
            return true;
        }
        return false;
    }
}

