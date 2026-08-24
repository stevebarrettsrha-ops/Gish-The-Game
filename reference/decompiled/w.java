/*
 * Decompiled with CFR 0.152.
 */
import com.hardwire.blob.Main;
import java.util.Vector;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class w {
    public final Object a;
    public final Object b;
    public Vector a;
    public Vector b;
    private Vector c = null;
    volatile boolean a;
    public int a;
    public aq a;
    public ay a = 1;
    public boolean b = true;

    public w(int n2) {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void a(i i2) {
        if (i2 == null) {
            throw new NullPointerException();
        }
        Object object = this.a;
        synchronized (object) {
            if (this.c == null) {
                this.c = new Vector(1);
            }
            this.c.addElement(i2);
            return;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected final void a(int n2) {
        Object object = this.a;
        synchronized (object) {
            if (this.a && this.b != null) {
                b b2 = new b(this, n2);
                for (int i2 = 0; i2 < this.b.size(); ++i2) {
                    ((g)this.b.elementAt(i2)).a(b2);
                }
            }
            return;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected final void b(int n2) {
        Object object = this.a;
        synchronized (object) {
            if (this.a && this.b != null) {
                b b2 = new b(this, n2);
                for (int i2 = 0; i2 < this.b.size(); ++i2) {
                    ((g)this.b.elementAt(i2)).b(b2);
                }
            }
            return;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected final void a(int n2, int n3, int n4, int n5, int n6, int n7, int n8) {
        Object object = this.a;
        synchronized (object) {
            if (this.a && this.c != null) {
                am am2 = new am(this, n2, n3, n4, n5, n6, n7, n8);
                for (n3 = 0; n3 < this.c.size(); ++n3) {
                    ((i)this.c.elementAt(n3)).a(am2);
                }
            }
            return;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected final void a(int n2, int n3, int n4, int n5) {
        Object object = this.a;
        synchronized (object) {
            if (this.a && this.a != null) {
                new t(this, n2, n3, n4, n5);
                for (n3 = 0; n3 < this.a.size(); ++n3) {
                    this.a.elementAt(n3);
                }
            }
            return;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected final void a() {
        Object object = this.a;
        synchronized (object) {
            if (this.a && this.a != null) {
                new aa(this, this.b);
                for (int i2 = 0; i2 < this.a.size(); ++i2) {
                    ((Main)this.a.elementAt(i2)).h();
                }
            }
        }
        this.b = true;
    }

    public final boolean a() {
        if (this.a != null) {
            ay ay2 = this.a;
            if (ay2.a != null && ay2.a.a() && ay2.a) {
                return true;
            }
        }
        return false;
    }
}

