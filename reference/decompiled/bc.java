/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.bluetooth.BluetoothStateException
 */
import java.io.IOException;
import java.util.Vector;
import javax.bluetooth.BluetoothStateException;

public final class bc
extends ad
implements Runnable {
    private int b;
    ah a;
    n c;

    public bc(o object, int n2) {
        super((o)object);
        this.b = n2;
        switch (n2) {
            case 0: {
                return;
            }
            case 1: {
                return;
            }
            case 2: {
                return;
            }
            case 3: {
                object = this;
                n n3 = ((n)this).a.g();
                object = this;
                ((ad)this).a = n3;
                return;
            }
        }
        throw new IllegalStateException();
    }

    protected final void a() {
        switch (this.b) {
            case 0: {
                Object object;
                bc bc2 = this;
                if (((n)this).a.a()) {
                    bc2 = this;
                    object = ((n)bc2).a.b();
                    bc2 = this;
                    ((ad)this).b = object;
                } else {
                    bc2 = this;
                    object = ((n)bc2).a.c();
                    bc2 = this;
                    ((ad)this).b = object;
                }
                an.a().a(0);
                object = an.a().a(8);
                bc2 = null;
                super.a((String)object);
                this.a(false);
                new Thread(this).start();
                return;
            }
            case 1: {
                this.a(false);
                an.a().a(0);
                Object object = an.a().a(10, new String[]{this.a.a()});
                bc bc3 = this;
                super.a((String)object);
                bc3 = this;
                object = ((n)bc3).a.a((Throwable)null, this.c);
                bc3 = this;
                ((ad)this).b = object;
                bc3 = null;
                new Thread(this).start();
                return;
            }
            case 2: {
                this.a(false);
                an.a().a(0);
                Object object = this;
                object = ((n)object).a.b();
                Object object2 = an.a().a(13, new String[]{object.a()});
                object = this;
                super.a((String)object2);
                object = this;
                object2 = ((n)object).a.m();
                object = this;
                ((ad)this).b = object2;
                new Thread(this).start();
                return;
            }
            case 3: {
                this.a(false);
                an.a().a(0);
                Object object = an.a().a(5);
                bc bc4 = this;
                super.a((String)object);
                bc4 = this;
                object = ((n)bc4).a.n();
                bc4 = this;
                ((ad)this).b = object;
                new Thread(this).start();
                return;
            }
        }
        throw new IllegalStateException();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final void run() {
        switch (this.b) {
            case 0: {
                try {
                    try {
                        bc bc2 = this;
                        ar ar2 = ((n)bc2).a.a();
                        ar2.a();
                        while (true) {
                            ar ar3 = ar2;
                            if (!ar3.a) return;
                            try {
                                Thread.sleep(250L);
                            }
                            catch (InterruptedException interruptedException) {}
                        }
                    }
                    catch (IOException iOException) {}
                    return;
                }
                catch (Throwable throwable) {
                    return;
                }
                finally {
                    this.a(true);
                }
            }
            case 1: {
                try {
                    Object object;
                    Object object2 = this;
                    w w2 = ((n)object2).a.a();
                    try {
                        object2 = this;
                        ((n)object2).a.a(this.a);
                        object2 = this.a;
                        object = w2;
                        Object object3 = ((w)object).b;
                        synchronized (object3) {
                            if (((w)object).a()) {
                                throw new IllegalStateException();
                            }
                            if (object2 == null) {
                                throw new NullPointerException();
                            }
                            ay ay2 = ((w)object).a = new ay((w)object, (ah)object2);
                            ay2.a.start();
                            try {
                                ((w)object).a.c();
                            }
                            catch (InterruptedException interruptedException) {
                            }
                            ay2 = ((w)object).a;
                            object2 = ay2.a;
                            if (object2 != null) {
                                if (object2 instanceof SecurityException) {
                                    throw (SecurityException)object2;
                                }
                                if (object2 instanceof IOException) {
                                    throw (IOException)object2;
                                }
                            }
                            ay2 = ((w)object).a;
                            if (!ay2.a) {
                                throw new IOException();
                            }
                        }
                    }
                    catch (SecurityException securityException) {
                        object2 = this;
                        object = ((n)object2).a.a(securityException, this.c);
                        object2 = this;
                        ((ad)this).b = object;
                    }
                    catch (BluetoothStateException bluetoothStateException) {
                        object2 = this;
                        object = ((n)object2).a.a(bluetoothStateException, this.c);
                        object2 = this;
                        ((ad)this).b = object;
                    }
                    catch (IOException iOException) {
                        object2 = this;
                        object = ((n)object2).a.a(iOException, this.c);
                        object2 = this;
                        ((ad)this).b = object;
                    }
                    if (!w2.a()) return;
                    object2 = this;
                    ((n)object2).a.b(this.a);
                    object2 = this;
                    object = ((n)object2).a.k();
                    object2 = this;
                    ((ad)this).b = object;
                    return;
                }
                catch (Throwable throwable) {
                    bc bc3 = this;
                    n n2 = ((n)bc3).a.a((Throwable)null, this.c);
                    bc3 = this;
                    ((ad)this).b = n2;
                    return;
                }
                finally {
                    this.a(true);
                }
            }
            case 2: {
                try {
                    Object object;
                    Object object4 = this;
                    w w3 = ((n)object4).a.a();
                    try {
                        object = w3;
                        object4 = ((w)object).b;
                        synchronized (object4) {
                            if (!((w)object).a()) {
                            } else {
                                ((w)object).b = false;
                                ((w)object).a.a();
                                try {
                                    long l2 = 5000L;
                                    ay ay3 = ((w)object).a;
                                    for (long i2 = 0L; i2 < l2 && ay3.a.isAlive(); i2 += 250L) {
                                        Thread.sleep(250L);
                                    }
                                    ay3.a.isAlive();
                                }
                                catch (InterruptedException interruptedException) {}
                            }
                        }
                    }
                    catch (IOException iOException) {}
                    if (w3.a()) return;
                    object4 = this;
                    object = ((n)object4).a.l();
                    object4 = this;
                    ((ad)this).b = object;
                    return;
                }
                catch (Throwable throwable) {
                    return;
                }
                finally {
                    this.a(true);
                }
            }
            case 3: {
                Object object = this;
                try {
                    Object object5 = this;
                    Object object6 = ((n)object5).a.a();
                    try {
                        ((ar)object6).a((bc)object);
                        object5 = object6;
                        object = ((ar)object5).a;
                        if (object == null || ((Vector)object).size() <= 0) return;
                        object5 = this;
                        object6 = ((n)object5).a.h();
                        object5 = this;
                        ((ad)this).b = object6;
                        return;
                    }
                    catch (SecurityException securityException) {
                        object5 = this;
                        object6 = ((n)object5).a.a(securityException);
                        object5 = this;
                        ((ad)this).b = object6;
                        return;
                    }
                    catch (BluetoothStateException bluetoothStateException) {
                        object5 = this;
                        object6 = ((n)object5).a.a(bluetoothStateException);
                        object5 = this;
                        ((ad)this).b = object6;
                        return;
                    }
                    catch (IOException iOException) {
                        object5 = this;
                        object6 = ((n)object5).a.a(iOException);
                        object5 = this;
                        ((ad)this).b = object6;
                    }
                    return;
                }
                catch (Throwable throwable) {
                    bc bc4 = this;
                    n n3 = ((n)bc4).a.a(throwable);
                    bc4 = this;
                    ((ad)this).b = n3;
                    return;
                }
                finally {
                    this.a(true);
                }
            }
        }
        throw new IllegalStateException();
    }

    public final void a(String string) {
        super.a(string);
    }
}

