/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import java.util.Vector;
import javax.microedition.lcdui.Graphics;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class h {
    public Vector a;
    public Vector b;
    private Vector c;
    public ag[] a;
    public x[] a;
    public x[] b;
    public int a;
    public bg a;
    public bg b;
    public bg c;
    public int b;
    public int c;
    public int d;
    public int e;
    public int f;
    public int[] a;
    public boolean a;
    public ag[] b;
    public int g;
    public int h;
    public ag[] c;
    public x[] c;
    public h[] a;
    public int i;
    public int j = 0;
    public Object a = new Vector();

    public h(int n2, boolean bl, boolean bl2, boolean bl3, boolean bl4) {
        this.b = new Vector();
        this.c = new Vector();
        this.a = n2;
        this.d = 0;
        if (bl) {
            this.d |= 4;
        }
        if (bl2) {
            this.d |= 8;
        }
        if (bl4) {
            this.d |= 0x40;
        }
        if (bl3) {
            this.d |= 0x80;
        }
        this.c = 1024;
        this.h = 1;
    }

    public final int[] a() {
        if (this.a) {
            this.a[1] = Integer.MAX_VALUE;
            this.a[0] = Integer.MAX_VALUE;
            this.a[3] = Integer.MIN_VALUE;
            this.a[2] = Integer.MIN_VALUE;
            int n2 = this.a.length;
            for (int i2 = 0; i2 < n2; ++i2) {
                bg bg2 = this.a[i2].a;
                if (bg2.a < this.a[0]) {
                    this.a[0] = bg2.a;
                }
                if (bg2.a > this.a[2]) {
                    this.a[2] = bg2.a;
                }
                if (bg2.b < this.a[1]) {
                    this.a[1] = bg2.b;
                }
                if (bg2.b <= this.a[3]) continue;
                this.a[3] = bg2.b;
            }
            this.a = false;
        }
        return this.a;
    }

    public final void a(bg[] bgArray, int n2) {
        for (int i2 = 0; i2 < bgArray.length; ++i2) {
            this.a.addElement(new x(bgArray[i2], n2));
        }
    }

    public final void a(ag ag2) {
        this.b.addElement(ag2);
    }

    public final boolean a(ag ag2) {
        if (this.g == this.b.length) {
            return false;
        }
        this.b[this.g++] = ag2;
        return true;
    }

    public final void a(x x2) {
        this.a.addElement(x2);
    }

    public final void a(h h2) {
        if (h2 != null && !this.c.contains(h2)) {
            this.c.addElement(h2);
        }
    }

    public final void a(int n2, int n3, int n4, int n5) {
        ag ag2;
        int n6;
        n2 = this.a.size();
        int n7 = n2 >> 1;
        for (n6 = 0; n6 < n7; ++n6) {
            ag2 = new ag((x)this.a.elementAt(n6 * 2), (x)this.a.elementAt(n6 * 2 + 1), 1024, n4, -1);
            this.a(ag2);
        }
        for (n6 = 0; n6 < n7; ++n6) {
            int n8 = n6 * 2 + 2;
            if (n8 >= n2) {
                n8 = 0;
            }
            ag2 = new ag((x)this.a.elementAt(n6 * 2 + 1), (x)this.a.elementAt(n8), n3, n5, -1);
            this.a(ag2);
        }
        if (n2 % 2 != 0) {
            this.a(new ag((x)this.a.elementAt(n2 - 1), (x)this.a.elementAt(0), 1024, n4, -1));
        }
    }

    public final void a() {
        int n2;
        this.a = new x[this.a.size()];
        for (n2 = 0; n2 < this.a.length; ++n2) {
            this.a[n2] = (x)this.a.elementAt(n2);
        }
        this.a.removeAllElements();
        this.a = new ag[this.b.size()];
        for (n2 = 0; n2 < this.a.length; ++n2) {
            this.a[n2] = (ag)this.b.elementAt(n2);
        }
        this.b.removeAllElements();
        if ((this.d & 4) != 0) {
            this.e = this.a();
            if (this.e < 0) {
                this.e = -this.e;
            }
            h h2 = this;
            int n3 = 0;
            int n4 = h2.a.length;
            int n5 = 0;
            int n6 = n4 - 1;
            while (n5 < n4) {
                n3 += h2.a[n5].a.b(h2.a[n6].a).c();
                n6 = n5++;
            }
            this.f = n3;
        }
        if ((this.d & 8) != 0) {
            this.b = new ag[this.a.length];
            this.g = 0;
        }
        if ((this.d & 0x40) != 0) {
            this.b = new x[this.a.length];
            for (int i2 = 0; i2 < this.b.length; ++i2) {
                this.b[i2] = new x(new bg(this.a[i2].a), Integer.MAX_VALUE);
            }
        }
        if ((this.d & 0x80) != 0) {
            this.c = new bg();
            for (int i3 = 0; i3 < this.a.length; ++i3) {
                this.c.a(this.a[i3].a);
            }
            this.c.b(this.a.length);
        }
    }

    public final void b() {
        int n2;
        int n3 = this.b.size();
        if (n3 == 0) {
            this.c = null;
        } else {
            this.c = new ag[n3];
            for (n2 = 0; n2 < n3; ++n2) {
                this.c[n2] = (ag)this.b.elementAt(n2);
            }
            this.b.removeAllElements();
        }
        this.b = null;
        n3 = this.a.size();
        if (n3 == 0) {
            this.c = null;
        } else {
            this.c = new x[n3];
            for (n2 = 0; n2 < n3; ++n2) {
                this.c[n2] = (x)this.a.elementAt(n2);
            }
            this.a.removeAllElements();
        }
        this.a = null;
        n3 = this.c.size();
        if (n3 == 0) {
            this.a = null;
        } else {
            this.a = new h[n3];
            for (n2 = 0; n2 < n3; ++n2) {
                this.a[n2] = (h)this.c.elementAt(n2);
            }
            this.c.removeAllElements();
        }
        this.c = null;
    }

    public final x[] a() {
        if (this.b != null) {
            return this.b;
        }
        return this.a;
    }

    public final boolean a() {
        return this.g != 0;
    }

    public final void c() {
        for (int i2 = 0; i2 < this.g; ++i2) {
            this.b[i2].b();
            this.b[i2] = null;
        }
        this.g = 0;
    }

    public final int a() {
        int n2 = 0;
        int n3 = this.a.length;
        int n4 = 0;
        int n5 = n3 - 1;
        int n6 = n3 - 2;
        while (n4 < n3) {
            n2 = (int)((long)n2 + ((long)this.a[n5].a.a * (long)(this.a[n4].a.b - this.a[n6].a.b) >> 10));
            n6 = n5;
            n5 = n4++;
        }
        return n2 >>= 1;
    }

    public final void a(bg bg2) {
        for (int i2 = 0; i2 < this.a.length; ++i2) {
            if (this.a[i2].a == Integer.MAX_VALUE) continue;
            this.a[i2].c.a += bg2.a;
            this.a[i2].c.b += bg2.b;
        }
    }

    public final void b(bg bg2) {
        for (int i2 = 0; i2 < this.a.length; ++i2) {
            this.a[i2].a(bg2);
        }
    }

    public final boolean a(bg bg2) {
        boolean bl = false;
        int n2 = 0;
        int n3 = this.a.length - 1;
        while (n2 < this.a.length) {
            bg bg3 = this.a[n3].a;
            bg bg4 = this.a[n2].a;
            if (bg3.b <= bg2.b && bg4.b > bg2.b || bg3.b > bg2.b && bg4.b <= bg2.b) {
                int n4 = (int)((long)(bg4.a - bg3.a) * (long)(bg2.b - bg3.b) >> 10);
                int n5 = (int)((long)(bg4.b - bg3.b) * (long)(bg2.a - bg3.a) >> 10);
                if (bg4.b >= bg3.b && n4 >= n5 || bg4.b < bg3.b && n4 <= n5) {
                    bl = !bl;
                }
            }
            n3 = n2++;
        }
        return bl;
    }

    public final void d() {
        this.a = null;
        this.b = null;
    }

    public final void e() {
        int n2 = this.a.length;
        bg bg2 = new bg();
        for (int i2 = 0; i2 < n2; ++i2) {
            x x2 = this.a[i2];
            bg2.a = x2.a.a - x2.b.a;
            bg2.b = x2.a.b - x2.b.b;
            if ((long)bg2.a * (long)bg2.a + (long)bg2.b * (long)bg2.b <= 0x4000000L) continue;
            bg2.b();
            x2.b.a = x2.a.a - (bg2.a << 13 >> 10);
            x2.b.b = x2.a.b - (bg2.b << 13 >> 10);
        }
    }

    public final bg a() {
        if (this.a == null) {
            this.a = new bg();
            int n2 = this.a.length;
            int n3 = 0;
            for (int i2 = 0; i2 < n2; ++i2) {
                x x2 = this.a[i2];
                if ((x2.b & 0x10) != 0) continue;
                this.a.a += x2.a.a;
                this.a.b += x2.a.b;
                ++n3;
            }
            this.a.a /= n3;
            this.a.b /= n3;
        }
        return this.a;
    }

    public final bg b() {
        if (this.b == null) {
            this.b = new bg();
            int n2 = 0;
            if ((this.b & 2) != 0) {
                int n3 = this.a.length;
                for (int i2 = 0; i2 < n3; ++i2) {
                    if ((this.a[i2].b & 2) == 0) continue;
                    ++n2;
                    this.b.a(this.a[i2].a);
                }
            } else {
                int n4 = this.a.length;
                for (int i3 = 0; i3 < n4; ++i3) {
                    if ((this.a[i3].b & 5) == 0) continue;
                    ++n2;
                    this.b.a(this.a[i3].a);
                }
            }
            if (n2 == this.a.length) {
                this.b.a = 0;
                this.b.b = 1024;
            } else if (n2 != 0) {
                this.b.b(n2);
                this.b.b(this.a());
            }
        }
        return this.b;
    }

    public final bg c() {
        bg bg2 = new bg();
        int n2 = this.a.length;
        for (int i2 = 0; i2 < n2; ++i2) {
            x x2 = this.a[i2];
            bg2.a += x2.a.a - x2.b.a;
            bg2.b += x2.a.b - x2.b.b;
        }
        bg2.b(this.a.length);
        return bg2;
    }

    public final void a(int n2, int n3, int n4, boolean bl) {
        int n5 = (int)(((long)(this.a[3] - n2) << 10) / (long)(this.a[3] - this.a[1]));
        if (n5 > 1024) {
            n5 = 1024;
        }
        if (n5 > 0) {
            n3 = (int)((long)(-n3) * (long)n5 >> 9);
            bg bg2 = new bg(0, n3 -= this.c().b >> n4);
            if (!bl) {
                for (n4 = 0; n4 < this.a.length; ++n4) {
                    if (this.a[n4].a.b < n2) continue;
                    this.a[n4].a(bg2);
                }
                return;
            }
            this.b(bg2);
        }
    }

    public final void a(Graphics graphics, bg bg2, int n2) {
        x[] xArray = this.a();
        int n3 = xArray.length;
        graphics.setColor(n2);
        n2 = xArray[n3 - 1].a.a + bg2.a >> 10;
        int n4 = xArray[n3 - 1].a.b + bg2.b >> 10;
        for (int i2 = 0; i2 < n3; ++i2) {
            bg bg3 = xArray[i2].a;
            int n5 = bg3.a + bg2.a >> 10;
            int n6 = bg3.b + bg2.b >> 10;
            graphics.drawLine(n5, n6, n2, n4);
            n2 = n5;
            n4 = n6;
        }
    }
}

