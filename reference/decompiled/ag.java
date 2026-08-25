/*
 * Decompiled with CFR 0.152.
 */
/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class ag {
    public x a;
    public x b;
    public bg a;
    private e a;
    private int a;
    private int b;
    private int c;
    public byte a;
    private bg b = new bg();

    public ag(x x2, x x3, int n2, int n3, int n4) {
        this.a = x2;
        this.b = x3;
        this.a = null;
        this.b = n2;
        this.c = n3;
        this.a = n4;
        if (this.a == -1) {
            this.a = this.a.a.b(this.b.a).c();
        }
        if (this.c < -1) {
            this.c = (int)((long)(-this.a) * (long)this.c >> 10);
        }
        this.a = 0;
    }

    public ag(x x2, bg bg2, int n2, int n3, int n4) {
        this.a = x2;
        this.a = bg2;
        this.b = null;
        this.a = null;
        this.b = n2;
        this.c = n3;
        this.a = n4;
        if (this.a == -1) {
            this.a = this.a.a.b(this.a).c();
        }
        if (this.c < -1) {
            this.c = (int)((long)(-this.a) * (long)this.c >> 10);
        }
        this.a = 0;
    }

    public ag(x x2, e e2, int n2, int n3, int n4) {
        this.a = x2;
        this.a = null;
        this.b = null;
        this.a = e2;
        this.b = 512;
        this.c = 10240;
        this.a = 0;
        if (this.a == -1) {
            this.a = this.a.a.b(this.a).c();
        }
        if (this.c < -1) {
            this.c = (int)((long)(-this.a) * (long)this.c >> 10);
        }
        this.a = 0;
    }

    public final boolean a(boolean n2) {
        int n3;
        if ((this.a.b & 0x10) != 0 || this.b != null && (this.b.b & 0x10) != 0) {
            return true;
        }
        if (this.b == null) {
            if (this.a == null) {
                n3 = 1024 - this.a.a;
                this.b.a = this.a.a.a - (int)((long)this.a.a.a.a * (long)n3 >> 10) - (int)((long)this.a.b.a.a * (long)this.a.a >> 10);
                this.b.b = this.a.a.b - (int)((long)this.a.a.a.b * (long)n3 >> 10) - (int)((long)this.a.b.a.b * (long)this.a.a >> 10);
            } else {
                this.b.a = this.a.a.a - this.a.a;
                this.b.b = this.a.a.b - this.a.b;
            }
        } else {
            this.b.a = this.a.a.a - this.b.a.a;
            this.b.b = this.a.a.b - this.b.a.b;
        }
        n3 = this.b.c();
        if (n3 == 0) {
            return false;
        }
        if (n2 != 0 && this.c != -1 && (this.c > this.a && n3 > this.c || this.c < this.a && n3 < this.c)) {
            return true;
        }
        n2 = n3 - this.a;
        if (this.a == 1 && n2 < 0) {
            return false;
        }
        if (this.a == 2 && n2 > 0) {
            return false;
        }
        if (this.b == 512) {
            n2 >>= 1;
        } else if (this.b < 1024) {
            n2 = (int)((long)this.b * (long)n2 >> 10);
        }
        this.b.a = (this.b.a << 10) / n3;
        this.b.b = (this.b.b << 10) / n3;
        n3 = n2;
        int n4 = n2;
        if (this.b != null && this.b.a != Integer.MAX_VALUE) {
            if (this.a.a != Integer.MAX_VALUE) {
                if (this.a.a == this.b.a) {
                    n3 = n4 = n2 >> 1;
                } else {
                    n3 = n2 * this.b.a / (this.a.a + this.b.a);
                    n4 = n2 - n3;
                }
            }
            this.b.a.a += this.b.a * n4 >> 10;
            this.b.a.b += this.b.b * n4 >> 10;
        } else if (this.a != null && this.a.a.a != Integer.MAX_VALUE && this.a.b.a != Integer.MAX_VALUE) {
            int n5;
            if (this.a.a != Integer.MAX_VALUE) {
                n5 = this.a.a.a + this.a.b.a >> 1;
                if (this.a.a == n5) {
                    n3 = n4 = n2 >> 1;
                } else {
                    n3 = n2 * n5 / (this.a.a + n5);
                    n4 = n2 - n3;
                }
            }
            n5 = this.b.a * n4 >> 10;
            n2 = this.b.b * n4 >> 10;
            this.a.a.a.a += n5 * (1024 - this.a.a) >> 10;
            this.a.a.a.b += n2 * (1024 - this.a.a) >> 10;
            this.a.b.a.a += n5 * this.a.a >> 10;
            this.a.b.a.b += n2 * this.a.a >> 10;
        }
        if (this.a.a != Integer.MAX_VALUE) {
            this.a.a.a -= this.b.a * n3 >> 10;
            this.a.a.b -= this.b.b * n3 >> 10;
        }
        return false;
    }

    public final void a() {
        if ((this.a.b & 0x20) != 0) {
            this.a.b |= 0x10;
        }
        if (this.b != null && (this.b.b & 0x20) != 0) {
            this.b.b |= 0x10;
        }
        this.a = null;
    }

    public final void b() {
        this.a.b &= 0xFFFFFFF3;
        if (this.b != null) {
            this.b.b &= 0xFFFFFFF3;
            return;
        }
        if (this.a != null) {
            this.a.a = Integer.MAX_VALUE;
        }
    }
}

