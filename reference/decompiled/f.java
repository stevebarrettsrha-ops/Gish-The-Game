/*
 * Decompiled with CFR 0.152.
 */
/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class f {
    public bg[] a;
    public bg a;
    public bg[] b;
    public int a;
    public int[] a;
    private int[] b;
    private af a;
    public int b;

    public f(af af2, bg[] bgArray, bg bg2) {
        this.a = af2;
        this.a = bgArray;
        this.a = bg2;
        this.a = 0;
        this.a = new int[4];
        this.b = new int[4];
        this.a[1] = Integer.MAX_VALUE;
        this.a[0] = Integer.MAX_VALUE;
        this.a[3] = Integer.MIN_VALUE;
        this.a[2] = Integer.MIN_VALUE;
        for (int i2 = 0; i2 < this.a.length; ++i2) {
            if (this.a[i2].a < this.a[0]) {
                this.a[0] = this.a[i2].a;
            }
            if (this.a[i2].a > this.a[2]) {
                this.a[2] = this.a[i2].a;
            }
            if (this.a[i2].b < this.a[1]) {
                this.a[1] = this.a[i2].b;
            }
            if (this.a[i2].b <= this.a[3]) continue;
            this.a[3] = this.a[i2].b;
        }
    }

    public final int[] a() {
        this.b[0] = this.a[0] + this.a.a;
        this.b[1] = this.a[1] + this.a.b;
        this.b[2] = this.a[2] + this.a.a;
        this.b[3] = this.a[3] + this.a.b;
        return this.b;
    }

    public final void a(bg object) {
        bg bg2 = ((bg)object).b(this.a);
        object = this;
        for (int i2 = 0; i2 < ((f)object).a; ++i2) {
            ((f)object).b[i2].a(bg2);
        }
        bg bg3 = new bg(((f)object).a);
        ((f)object).a.a(bg2);
        if (((f)object).a.b != null) {
            ((f)object).a.b.a(((f)object).b, ((f)object).a[0] + bg3.a, ((f)object).a[1] + bg3.b, ((f)object).a[2] + bg3.a, ((f)object).a[3] + bg3.b, ((f)object).a[0] + ((f)object).a.a, ((f)object).a[1] + ((f)object).a.b, ((f)object).a[2] + ((f)object).a.a, ((f)object).a[3] + ((f)object).a.b);
        }
    }

    public final void b(bg bg2) {
        if (this.b == null) {
            this.b = new bg[8];
        } else if (this.a == this.b.length) {
            bg[] bgArray = new bg[this.a << 1];
            System.arraycopy(this.b, 0, bgArray, 0, this.a);
            this.b = bgArray;
        }
        this.b[this.a++] = bg2;
    }
}

