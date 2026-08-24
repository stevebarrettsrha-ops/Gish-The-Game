/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.bluetooth.BluetoothStateException
 */
import java.io.IOException;
import java.util.Hashtable;
import java.util.Vector;
import javax.bluetooth.BluetoothStateException;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class o {
    private w a;
    private be a;
    private ar a;
    private n a;
    private y a;
    private bb a;
    private bb b;
    private bb c;
    private bc a;
    private bc b;
    private bb d;
    private bc c;
    private bc d;
    private static Hashtable a = new Hashtable(1);
    private boolean a;
    private ah a = null;
    private boolean b;
    private ah b = null;
    private boolean c = false;

    final boolean a() {
        return this.a;
    }

    final void a(boolean bl) {
        this.a = bl;
        this.a = null;
    }

    final boolean b() {
        return this.a != null;
    }

    final void a(ah ah2) {
        this.a = ah2;
    }

    final ah a() {
        return this.a;
    }

    public static o a(w w2, be be2) {
        o o2 = (o)a.get(w2);
        if (o2 == null) {
            o2 = new o(w2, be2);
            a.put(w2, o2);
        }
        return o2;
    }

    private o(w w2, be be2) {
        this.a = w2;
        this.a = be2;
        this.a((n)null, this.b());
    }

    final w a() {
        return this.a;
    }

    final ar a() {
        if (this.a == null) {
            az az2 = ao.a();
            if (az2.a == null) {
                az2.a = new ar();
            }
            this.a = az2.a;
        }
        return this.a;
    }

    public final n a() {
        return this.a;
    }

    public final boolean c() {
        if (this.a != null) {
            return this.a.d();
        }
        return this.b;
    }

    public final void b(boolean bl) {
        if (this.a != null) {
            this.a.a(bl);
            return;
        }
        this.b = bl;
    }

    final ah b() {
        if (this.a != null) {
            w w2 = this.a;
            return this.a.a(w2.a);
        }
        return this.b;
    }

    final void b(ah ah2) {
        ah ah3 = null;
        if (this.a != null) {
            w w2 = this.a;
            ah3 = this.a.a(w2.a);
            w2 = this.a;
            this.a.a(w2.a, ah2);
        } else {
            ah3 = this.b;
            this.b = ah2;
        }
        this.c = false;
        if (!this.c()) {
            try {
                if (ah3 == null || !ah3.b().equalsIgnoreCase(ah2.b())) {
                    this.b(true);
                    this.c = true;
                }
                return;
            }
            catch (IOException iOException) {}
        }
    }

    final boolean d() {
        return this.b() != null;
    }

    final Vector a() {
        if (this.a != null) {
            return this.a.a();
        }
        return null;
    }

    final boolean e() {
        return (this = ((o)this).a()) != null && ((Vector)this).size() > 0;
    }

    final n b() {
        if (this.a == null) {
            this.a = new y(this);
        }
        return this.a;
    }

    final n c() {
        if (this.a == null) {
            this.a = new bb(this, 1);
        }
        return this.a;
    }

    final n d() {
        if (this.b == null) {
            this.b = new bb(this, 0);
        }
        return this.b;
    }

    final n e() {
        if (this.c == null) {
            this.c = new bb(this, 3);
        }
        return this.c;
    }

    final n f() {
        if (this.a == null) {
            this.a = new bc(this, 3);
        }
        return this.a;
    }

    final n g() {
        if (this.b == null) {
            this.b = new bc(this, 0);
        }
        return this.b;
    }

    final n h() {
        if (this.d == null) {
            this.d = new bb(this, 2);
        }
        return this.d;
    }

    final n i() {
        if (this.c == null) {
            this.c = new bc(this, 2);
        }
        return this.c;
    }

    final n a(n n2, n n3) {
        if (n2 != this.a) {
            throw new IllegalStateException();
        }
        n3.a();
        this.a = n3;
        return n3;
    }

    final n a(ah ah2, n n2) {
        if (this.d == null) {
            this.d = new bc(this, 1);
        }
        Object object = n2;
        n2 = this.d;
        this.d.c = object;
        object = ah2;
        n2 = this.d;
        this.d.a = object;
        return this.d;
    }

    final n j() {
        return this.a(this.b(), this.c());
    }

    final n k() {
        bh bh2 = new bh(this);
        an.a().a(0);
        Object object = this.b();
        Object object2 = an.a().a(11, new String[]{object.a()});
        Object object3 = bh2;
        bh2.a = object2;
        if (this.c) {
            object = new bh(this);
            an.a().a(0);
            object2 = an.a().a(31);
            object3 = object;
            ((bh)object).a = object2;
            object2 = this.b();
            object3 = object;
            ((bh)object).a = object2;
            object2 = object;
            object3 = bh2;
            bh2.a = object2;
        } else {
            object2 = this.b();
            object3 = bh2;
            bh2.a = object2;
        }
        return bh2;
    }

    final n a(Throwable object, n n2) {
        bh bh2 = new bh((o)((Object)bh2));
        an.a().a(0);
        object = object instanceof SecurityException ? an.a().a(27) : (object instanceof BluetoothStateException ? an.a().a(29) : an.a().a(12));
        Object object2 = object;
        object = bh2;
        bh2.a = object2;
        object2 = n2;
        object = bh2;
        bh2.a = object2;
        return bh2;
    }

    final n l() {
        bh bh2 = new bh(this);
        an.a().a(0);
        Object object = this.b();
        Object object2 = an.a().a(14, new String[]{object.a()});
        object = bh2;
        bh2.a = object2;
        object2 = this.c();
        object = bh2;
        bh2.a = object2;
        return bh2;
    }

    final n m() {
        bh bh2 = new bh(this);
        an.a().a(0);
        Object object = an.a().a(15);
        bh bh3 = bh2;
        bh2.a = object;
        object = this.d();
        bh3 = bh2;
        bh2.a = object;
        return bh2;
    }

    final n n() {
        bh bh2 = new bh(this);
        an.a().a(0);
        Object object = an.a().a(9);
        bh bh3 = bh2;
        bh2.a = object;
        object = this.c();
        bh3 = bh2;
        bh2.a = object;
        return bh2;
    }

    final n a(Throwable throwable) {
        bh bh2 = new bh((o)((Object)string));
        an.a().a(0);
        Object object = ((o)((Object)string)).c();
        bh bh3 = bh2;
        bh2.a = object;
        String string = throwable instanceof SecurityException ? an.a().a(26) : (throwable instanceof BluetoothStateException ? an.a().a(28) : an.a().a(25));
        object = string;
        bh3 = bh2;
        bh2.a = object;
        return bh2;
    }

    private n a(String object, n n2) {
        bh bh2 = new bh((o)((Object)bh2));
        an.a().a(0);
        Object object2 = object;
        object = bh2;
        bh2.a = object2;
        object2 = n2;
        object = bh2;
        bh2.a = object2;
        return bh2;
    }

    final n a(boolean bl, n n2) {
        if (bl) {
            return this.a(an.a().a(21), n2);
        }
        return this.a(an.a().a(22), n2);
    }
}

