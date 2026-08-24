/*
 * Decompiled with CFR 0.152.
 */
/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class ad
extends n {
    public volatile String a;
    public volatile boolean a;
    n a;
    n b;
    private volatile a a;

    ad(o o2) {
        super(2, o2);
    }

    synchronized void a(String string) {
        this.a = string;
    }

    final synchronized void a(boolean bl) {
        this.a = bl;
    }

    public final boolean a() {
        return this.a != null;
    }

    public final synchronized n a() {
        if (this.a()) {
            this.a((a)null);
            ad ad2 = this;
            return ((n)ad2).a.a(this, this.a);
        }
        throw new IllegalStateException();
    }

    public final n b() {
        ad ad2 = this;
        if (!ad2.a) {
            throw new IllegalStateException();
        }
        this.a((a)null);
        ad2 = this;
        return ((n)ad2).a.a(this, this.b);
    }

    private synchronized void a(a a2) {
        this.a = null;
    }
}

