/*
 * Decompiled with CFR 0.152.
 */
import java.util.Vector;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class q
implements i {
    public Vector a;
    private int a = 0;
    private int b = 0;
    private final int c;

    public q() {
        this(60);
    }

    private q(int n2) {
        this.c = 60;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void a(b b2) {
        q q2 = this;
        synchronized (q2) {
            if (this.a == null) {
                return;
            }
            for (int i2 = 0; i2 < this.a.size(); ++i2) {
                ((g)this.a.elementAt(i2)).a(b2);
            }
            return;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void b(b b2) {
        q q2 = this;
        synchronized (q2) {
            if (this.a == null) {
                return;
            }
            for (int i2 = 0; i2 < this.a.size(); ++i2) {
                ((g)this.a.elementAt(i2)).b(b2);
            }
            return;
        }
    }

    public final void a(am am2) {
        int n2 = am2.a(-100, 100);
        int n3 = 0;
        if (n2 < -this.c) {
            n3 = 3;
        } else if (n2 > this.c) {
            n3 = 4;
        }
        if (n3 != this.a) {
            if (this.a != 0) {
                this.b(new b(am2.a(), -1, this.a));
            }
            this.a = n3;
            if (n3 != 0) {
                this.a(new b(am2.a(), -1, this.a));
            }
        }
        n2 = am2.b(-100, 100);
        n3 = 0;
        if (n2 < -this.c) {
            n3 = 1;
        } else if (n2 > this.c) {
            n3 = 2;
        }
        if (n3 != this.b) {
            if (this.b != 0) {
                this.b(new b(am2.a(), -1, this.b));
            }
            this.b = n3;
            if (n3 != 0) {
                this.a(new b(am2.a(), -1, this.b));
            }
        }
    }
}

