/*
 * Decompiled with CFR 0.152.
 */
/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class ac {
    public f a;
    public byte a;
    public boolean a;
    public boolean b;
    public byte b;
    k a;
    public bg a;
    public bg b;
    public int a;
    public int b;

    public ac(k bgArray, byte by, bg bg2, bg bg3, int n2) {
        this.a = bgArray;
        this.b = by;
        bgArray = null;
        switch (this.b) {
            case 0: {
                bgArray = al.a(96, 32, true);
                break;
            }
            case 1: {
                bgArray = al.a(32, 96, true);
                bg bg4 = new bg(0, 64).b();
                bg2.b(bg4);
                bg3.b(bg4);
                break;
            }
            case 2: {
                bgArray = al.a(64, 32, true);
                break;
            }
            case 3: {
                bgArray = al.a(32, 288, true);
                bg bg5 = new bg(0, 256).b();
                bg2.b(bg5);
                bg3.b(bg5);
            }
        }
        ((bg)((Object)bgArray[0])).a -= 512;
        bgArray[0].b -= 512;
        bgArray[1].b -= 512;
        bgArray[3].a -= 512;
        bgArray[4].a -= 512;
        bgArray[4].b -= 512;
        this.a = new bg(bg2);
        this.a = new f(this.a.a, bgArray, bg2);
        this.b = new bg(bg3);
        this.b = bg3.b(bg2).c() / (n2 * 500);
        this.a = 0;
        this.a = false;
        if (this.a.a != null && this.a.a.b) {
            this.a.a.a(this.a);
        }
    }

    public final void a(byte by, int n2) {
        this.a = by;
        this.a = true;
        if (this.a == 1 && (n2 > 0 && this.a == this.b || n2 < 0 && this.a == 0)) {
            ac ac2 = this;
            this.a = false;
        }
        if (n2 < 0 && this.a < this.b) {
            this.a = this.b + this.b - this.a;
            return;
        }
        if (n2 > 0 && this.a >= this.b) {
            this.a = this.b + this.b - this.a;
        }
    }

    public final byte a() {
        if (this.a) {
            if (this.a < this.b) {
                return 1;
            }
            return 3;
        }
        if (this.a == 0) {
            return 0;
        }
        if (this.a == this.b) {
            return 2;
        }
        if (this.a < this.b) {
            return 1;
        }
        return 3;
    }
}

