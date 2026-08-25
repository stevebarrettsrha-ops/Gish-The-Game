/*
 * Decompiled with CFR 0.152.
 */
/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class u {
    public byte a;
    public short a;
    public short b;
    public boolean a;
    public boolean b;
    public int[] a;
    k a;
    public ac a;
    byte b;
    byte c;

    public u(k k2, byte by, ac ac2, int n2, int n3) {
        this.a = k2;
        this.a = by;
        this.a = ac2;
        this.a = (short)n2;
        this.b = (short)n3;
        this.b = false;
        this.a = new int[]{this.a - 1 << 15, this.b - 1 << 15, this.a + 1 << 15, this.b << 15};
    }

    public final void a() {
        this.b = false;
        switch (this.a) {
            case 3: {
                if (!this.a.a) break;
                ac ac2 = this.a;
                this.a.a = false;
                return;
            }
            case 4: {
                if (this.a.a() == 0) break;
                this.a.a((byte)1, -1);
            }
        }
    }

    public final void b() {
        this.b = true;
        switch (this.a) {
            case 1: {
                if (this.a.a) break;
                this.a.a((byte)0, 0);
                return;
            }
            case 2: {
                if (this.a.a) break;
                this.a.a((byte)1, 0);
                return;
            }
            case 3: {
                if (this.a.a) break;
                this.a.a((byte)0, 0);
                return;
            }
            case 4: {
                if (this.a.a() == 2) break;
                this.a.a((byte)1, 1);
            }
        }
    }

    public final boolean a() {
        return !(!this.b && this.a != 1 && this.a != 2 || this.a != 4 && !this.a.a);
    }
}

