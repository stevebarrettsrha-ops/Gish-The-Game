/*
 * Decompiled with CFR 0.152.
 */
import java.util.Vector;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
final class bb
extends ap {
    private int b;
    private boolean a = false;
    private boolean b = false;

    protected bb(o object, int n2) {
        super((o)object);
        this.b = n2;
        switch (n2) {
            case 0: {
                object = this;
                n n3 = ((n)this).a.b();
                object = this;
                ((ap)this).a = n3;
                return;
            }
            case 1: {
                object = this;
                n n4 = ((n)this).a.b();
                object = this;
                ((ap)this).a = n4;
                return;
            }
            case 2: {
                this.b();
                return;
            }
            case 3: {
                this.b();
                return;
            }
        }
        throw new IllegalStateException();
    }

    protected final void a() {
        switch (this.b) {
            case 0: {
                an.a().a(0);
                bb bb2 = this;
                String[] stringArray = ((ap)this).a;
                if (((ap)this).a == null || stringArray.length != 2) {
                    stringArray = new String[2];
                }
                stringArray[0] = an.a().a(4);
                bb2 = this;
                stringArray[1] = ((n)bb2).a.c() ? an.a().a(20) : an.a().a(19);
                String[] stringArray2 = stringArray;
                bb2 = this;
                ((ap)this).a = stringArray2;
                return;
            }
            case 1: {
                an.a().a(0);
                int n2 = 1;
                ++n2;
                bb bb3 = this;
                if (((n)bb3).a.b()) {
                    ++n2;
                } else {
                    bb3 = this;
                    if (((n)bb3).a.d()) {
                        this.a = true;
                        ++n2;
                    }
                }
                bb3 = this;
                if (((n)bb3).a.e()) {
                    this.b = true;
                    ++n2;
                }
                bb3 = this;
                String[] stringArray = ((ap)bb3).a;
                if (((ap)bb3).a == null || stringArray.length != n2) {
                    stringArray = new String[n2];
                }
                n2 = 0;
                bb3 = this;
                if (((n)bb3).a.b()) {
                    ++n2;
                    stringArray[0] = an.a().a(24);
                } else if (this.a) {
                    ++n2;
                    stringArray[0] = an.a().a(1);
                }
                if (this.b) {
                    stringArray[n2++] = an.a().a(2);
                }
                stringArray[n2++] = an.a().a(3);
                bb3 = this;
                stringArray[n2] = ((n)bb3).a.c() ? an.a().a(20) : an.a().a(19);
                String[] stringArray3 = stringArray;
                bb3 = this;
                ((ap)this).a = stringArray3;
                return;
            }
            case 2: {
                this.c();
                return;
            }
            case 3: {
                this.c();
                return;
            }
        }
        throw new IllegalStateException();
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    public final n a(int var1_1) {
        switch (this.b) {
            case 0: {
                var2_4 /* !! */  = null;
                var3_6 = this;
                var2_4 /* !! */  = var3_6.a;
                if (var1_1 < 0 || var1_1 > var2_4 /* !! */ .length - 1) {
                    throw new IllegalArgumentException();
                }
                var3_6 = this;
                var1_2 = var3_6.a[var1_1];
                if (var1_2.equals(an.a().a(4))) {
                    var3_6 = this;
                    var2_4 /* !! */  = var3_6.a.i();
                } else if (var1_2.equals(an.a().a(20))) {
                    var3_6 = this;
                    var2_4 /* !! */  = this.a(false, var3_6.a.d());
                } else if (var1_2.equals(an.a().a(19))) {
                    var3_6 = this;
                    var2_4 /* !! */  = this.a(true, var3_6.a.d());
                } else {
                    throw new IllegalArgumentException();
                }
                var3_6 = this;
                return var3_6.a.a(this, (n)var2_4 /* !! */ );
            }
            case 1: {
                var2_5 /* !! */  = null;
                var3_7 = this;
                var2_5 /* !! */  = var3_7.a;
                if (var1_1 < 0 || var1_1 > var2_5 /* !! */ .length - 1) {
                    throw new IllegalArgumentException();
                }
                var3_7 = this;
                var1_3 = var3_7.a[var1_1];
                if (!var1_3.equals(an.a().a(1)) || !this.a) ** GOTO lbl37
                var3_7 = this;
                var2_5 /* !! */  = var3_7.a.j();
                ** GOTO lbl64
lbl37:
                // 1 sources

                if (!var1_3.equals(an.a().a(24))) ** GOTO lbl-1000
                var3_7 = this;
                if (var3_7.a.b()) {
                    v0 = this;
                    var3_7 = v0;
                    v1 = this;
                    var3_7 = v1;
                    var3_7 = this;
                    var2_5 /* !! */  = v0.a.a(v1.a.a(), var3_7.a.c());
                } else if (var1_3.equals(an.a().a(2)) && this.b) {
                    var3_7 = this;
                    var2_5 /* !! */  = var3_7.a.e();
                } else if (var1_3.equals(an.a().a(3))) {
                    var3_7 = this;
                    var2_5 /* !! */  = var3_7.a.f();
                } else if (var1_3.equals(an.a().a(20))) {
                    var3_7 = this;
                    var2_5 /* !! */  = this.a(false, var3_7.a.c());
                } else if (var1_3.equals(an.a().a(19))) {
                    var3_7 = this;
                    var2_5 /* !! */  = this.a(true, var3_7.a.c());
                } else {
                    throw new IllegalArgumentException();
                }
lbl64:
                // 6 sources

                var3_7 = this;
                return var3_7.a.a(this, (n)var2_5 /* !! */ );
            }
            case 2: {
                return this.b(var1_1);
            }
            case 3: {
                return this.b(var1_1);
            }
        }
        throw new IllegalStateException();
    }

    private n a(boolean bl, n n2) {
        bb bb2 = this;
        ((n)bb2).a.b(bl);
        bb2 = this;
        return ((n)bb2).a.a(bl, n2);
    }

    private void b() {
        bb bb2 = this;
        n n2 = ((n)bb2).a.c();
        bb2 = this;
        ((ap)this).a = n2;
    }

    private void c() {
        an.a().a(0);
        Object object = this.a();
        String[] stringArray = new String[((Vector)object).size()];
        for (int i2 = 0; i2 < stringArray.length; ++i2) {
            ah ah2 = (ah)((Vector)object).elementAt(i2);
            stringArray[i2] = ah2.a();
        }
        object = this;
        ((ap)this).a = stringArray;
    }

    private n b(int n2) {
        Object var2_3 = null;
        int n3 = n2;
        Object object = null;
        object = this.a();
        if (n3 < 0 || n3 > ((Vector)object).size() - 1) {
            throw new IllegalArgumentException();
        }
        if ((object = (ah)((Vector)object).elementAt(n3)) == null) {
            throw new IllegalArgumentException();
        }
        bb bb2 = this;
        n n4 = ((n)bb2).a.a((ah)object, (n)this);
        bb2 = this;
        return ((n)bb2).a.a(this, n4);
    }

    private Vector a() {
        switch (this.b) {
            case 2: {
                Object object = this;
                object = ((n)object).a.a();
                return ((ar)object).a;
            }
            case 3: {
                bb bb2 = this;
                return ((n)bb2).a.a();
            }
        }
        throw new IllegalStateException();
    }
}

