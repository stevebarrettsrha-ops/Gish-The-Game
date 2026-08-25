/*
 * Decompiled with CFR 0.152.
 */
import java.io.IOException;
import java.util.Hashtable;
import java.util.Vector;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class be {
    protected static Hashtable a = new Hashtable();
    private Vector a;
    private boolean a = null;
    private Hashtable c;
    protected Hashtable b;

    protected be() {
    }

    protected final void a() {
        this.b();
        be be2 = this;
        this.c = null;
        try {
            be2.c = ba.a(true);
        }
        catch (IOException iOException) {}
        this.a = this.c();
    }

    protected final void b() {
        this.b = null;
        try {
            this.b = ba.a(false);
            return;
        }
        catch (IOException iOException) {
            return;
        }
    }

    protected final void c() {
        try {
            Hashtable hashtable = this.b;
            ba.a(hashtable, false);
            return;
        }
        catch (IOException iOException) {
            return;
        }
    }

    private void d() {
        try {
            Hashtable hashtable = this.c;
            ba.a(hashtable, true);
            return;
        }
        catch (IOException iOException) {
            return;
        }
    }

    public final ah a(int n2) {
        Object object = null;
        object = this.a ? this.b : this.c;
        if ((object = au.a((Hashtable)object, n2)) == null && this.a) {
            object = au.a(this.c, n2);
        }
        return object;
    }

    public final void a(int n2, ah ah2) {
        au.a(this.c, n2, ah2);
        this.d();
        if (this.a) {
            au.a(this.b, n2, ah2);
            this.c();
        }
    }

    public final boolean d() {
        Hashtable hashtable = null;
        hashtable = this.a ? this.b : this.c;
        if (this.a && !this.b.containsKey("zp.ace") && this.c.containsKey("zp.ace")) {
            this.a(au.a(this.c));
        }
        boolean bl = true;
        if (!hashtable.containsKey("zp.ace")) {
            if (this.a()) {
                bl = this.b();
            }
            this.a(bl);
        } else {
            bl = au.a(hashtable);
        }
        return bl;
    }

    public final void a(boolean bl) {
        au.a(this.c, bl);
        this.d();
        if (this.a) {
            au.a(this.b, bl);
            this.c();
        }
    }

    public final Vector a() {
        if (this.a == null) {
            String string;
            this.a = new Vector();
            int n2 = 1;
            while ((string = this.a(n2)) != null) {
                ++n2;
                int n3 = string.indexOf(58);
                if (n3 < 0) continue;
                String string2 = string.substring(0, n3);
                string = string.substring(n3 + 1);
                if (string2 == null || string == null) continue;
                this.a.addElement(ao.a(string2, string));
            }
        }
        return this.a;
    }

    protected abstract String a(int var1);

    protected abstract boolean a();

    protected abstract boolean b();

    protected abstract boolean c();
}

