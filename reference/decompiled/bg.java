/*
 * Decompiled with CFR 0.152.
 */
/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class bg {
    public int a;
    public int b;
    public static final bg a = new bg(0, 0);

    public bg() {
        this.b = 0;
        this.a = 0;
    }

    public bg(int n2, int n3) {
        this.a = n2;
        this.b = n3;
    }

    public bg(bg bg2) {
        this.a = bg2.a;
        this.b = bg2.b;
    }

    public bg(int n2, long l2, boolean bl) {
        n2 = al.a(n2);
        bl = false;
        boolean bl2 = false;
        if (n2 > 3294198) {
            bl2 = true;
            n2 = 6588397 - n2;
        }
        if (n2 > 1647099) {
            bl = true;
            n2 = 3294198 - n2;
        }
        n2 = n2 * (al.a.length - 1) / 1647099;
        this.a = al.a[al.a.length - 1 - n2];
        this.b = al.a[n2];
        if (bl) {
            this.a = -this.a;
        }
        if (bl2) {
            this.b = -this.b;
        }
        this.a = (int)((long)this.a * l2 >> 10);
        this.b = (int)((long)this.b * l2 >> 10);
    }

    public final bg a() {
        return new bg(-this.a, -this.b);
    }

    public final int a() {
        if (this.a()) {
            return 0;
        }
        int n2 = this.a < 0 ? -this.a : this.a;
        int n3 = this.b < 0 ? -this.b : this.b;
        boolean bl = false;
        if (n2 < n3) {
            bl = true;
            n2 ^= n3;
            n3 ^= n2;
            n2 ^= n3;
        }
        n2 = n3 * (al.b.length - 1) / n2;
        n2 = al.b[n2] << 10;
        if (bl) {
            n2 = 1647099 - n2;
        }
        if (this.a < 0 && this.b < 0) {
            n2 += 3294198;
        } else if (this.a < 0) {
            n2 = 3294198 - n2;
        } else if (this.b < 0) {
            n2 = 6588397 - n2;
        }
        return n2;
    }

    public final bg b() {
        this.a <<= 10;
        this.b <<= 10;
        return this;
    }

    public final void a(bg bg2) {
        this.a += bg2.a;
        this.b += bg2.b;
    }

    public final void a(int n2, int n3) {
        this.a += n2;
        this.b += n3;
    }

    public final void b(bg bg2) {
        this.a -= bg2.a;
        this.b -= bg2.b;
    }

    public final void a(int n2) {
        this.a = (int)((long)this.a * (long)n2 >> 10);
        this.b = (int)((long)this.b * (long)n2 >> 10);
    }

    public final void b(int n2) {
        this.a /= n2;
        this.b /= n2;
    }

    public final void c(int n2) {
        this.a = (int)(((long)this.a << 10) / (long)n2);
        this.b = (int)(((long)this.b << 10) / (long)n2);
    }

    public final bg a(bg bg2) {
        return new bg(this.a + bg2.a, this.b + bg2.b);
    }

    public final bg b(bg bg2) {
        return new bg(this.a - bg2.a, this.b - bg2.b);
    }

    public final int a(bg bg2) {
        return this.a * bg2.b - this.b * bg2.a;
    }

    public final boolean a() {
        return this.a == 0 && this.b == 0;
    }

    public final void c(bg bg2) {
        this.a = bg2.a;
        this.b = bg2.b;
    }

    public final void b(int n2, int n3) {
        this.a = n2;
        this.b = n3;
    }

    public final int b() {
        int n2 = this.c();
        if (n2 == 1024) {
            return n2;
        }
        this.a = (int)(((long)this.a << 10) / (long)n2);
        this.b = (int)(((long)this.b << 10) / (long)n2);
        return n2;
    }

    public final void a() {
        if (this.a == 0) {
            this.b = this.b < 0 ? -1024 : 1024;
            return;
        }
        if (this.b == 0) {
            this.a = this.a < 0 ? -1024 : 1024;
            return;
        }
        if (this.a == this.b) {
            if (this.a < 0) {
                this.a = -724;
                this.b = -724;
                return;
            }
            this.a = 724;
            this.b = 724;
            return;
        }
        if (this.a == -this.b) {
            if (this.a < 0) {
                this.a = -724;
                this.b = 724;
                return;
            }
            this.a = 724;
            this.b = -724;
            return;
        }
        this.b();
    }

    public final int c() {
        return al.a(this.a, this.b);
    }

    public final int d() {
        return (int)((long)this.a * (long)this.a + (long)this.b * (long)this.b >> 10);
    }

    public final long a() {
        return (long)this.a * (long)this.a + (long)this.b * (long)this.b;
    }
}

