/*
 * Decompiled with CFR 0.152.
 */
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Timer;
import java.util.TimerTask;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class ay
implements Runnable {
    private w a;
    private aq a;
    public Thread a;
    ah a;
    private Object a;
    private volatile boolean c;
    public volatile boolean a;
    volatile boolean b;
    private Object b;
    private Timer a;
    private byte[] a;
    private byte[] b;
    private int a;
    private short[] a;
    private short[] b;
    private int b;
    private int c = true;
    public Throwable a = null;

    public ay(w w2, ah ah2) {
        this.b = new Object();
        this.a = w2;
        this.a = new Thread(this);
        this.a = ah2;
        this.a = new short[6];
        this.b = new short[6];
        for (int i2 = 0; i2 < this.a.length; ++i2) {
            this.a[i2] = 254;
            this.b[i2] = 254;
        }
        this.a = new aq();
        this.a = new Timer();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void a() {
        Object object = this.a;
        synchronized (object) {
            this.c = false;
            this.a.notify();
        }
        this.a.b();
    }

    void b() {
        try {
            this.a();
            return;
        }
        catch (IOException iOException) {
            return;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Unable to fully structure code
     */
    public final void run() {
        block62: {
            this.a = null;
            var1_2 = false;
            try {
                var1_2 = this.a.b();
                if (var1_2) {
                    this.a.a(this);
                }
                this.a.a();
            }
            catch (SecurityException var2_6) {
                this.a = var2_6;
                this.b();
            }
            catch (IOException var2_7) {
                if (!this.c) break block62;
                this.a = var2_7;
                this.b();
            }
        }
        if (this.c) {
            var2_8 = new aw(this);
            this.a.schedule((TimerTask)var2_8, 15000L);
        }
        var2_9 = false;
        if (var1_2) {
            var1_3 = this.a;
            synchronized (var1_3) {
                while (this.c) {
                    try {
                        this.a.wait();
                    }
                    catch (InterruptedException v0) {}
                }
            }
        }
        block38: while (this.c) {
            var2_10 = this.a();
            switch (var2_10) {
                case 0: {
                    var1_4 = this;
                    if (var1_4.a == 0 || var1_4.b[0] != -95) ** GOTO lbl219
                    if (var1_4.b) ** GOTO lbl46
                    switch (var1_4.b[1]) {
                        case 3: 
                        case 4: 
                        case 5: {
                            ** GOTO lbl47
                        }
                    }
                    continue block38;
lbl46:
                    // 1 sources

                    if (!var1_4.a) ** GOTO lbl219
lbl47:
                    // 2 sources

                    block10 : switch (var1_4.b[1]) {
                        case 7: {
                            if (var1_4.a < 8) break;
                            var2_10 = 0;
                            for (var3_15 = 2; var3_15 < 8; ++var3_15) {
                                var1_4.b[var2_10] = ay.a(var1_4.b, var3_15);
                                ++var2_10;
                            }
                            var2_10 = 0;
                            for (var3_15 = 0; var3_15 < var1_4.b.length && var1_4.b[var3_15] != 254 && var1_4.b[var3_15] != 255; ++var3_15) {
                                if (var1_4.b[var3_15] == var1_4.a[var2_10]) {
                                    ++var2_10;
                                    continue;
                                }
                                if (var1_4.b[var3_15] < var1_4.a[var2_10]) {
                                    var1_4.a.a(var1_4.b[var3_15]);
                                    continue;
                                }
                                var1_4.a.b(var1_4.a[var2_10]);
                                --var3_15;
                                ++var2_10;
                            }
                            for (var3_15 = var2_10; var3_15 < var1_4.a.length && var1_4.a[var3_15] != 254; ++var3_15) {
                                var1_4.a.b(var1_4.a[var3_15]);
                            }
                            for (var3_15 = 0; var3_15 < var1_4.a.length; ++var3_15) {
                                var1_4.a[var3_15] = var1_4.b[var3_15];
                            }
                            break;
                        }
                        case -3: 
                        case 8: {
                            if (var1_4.a < 5) break;
                            var2_10 = (byte)(var1_4.b[2] & 127);
                            var5_26 = false;
                            var4_25 = null;
                            var3_16 = var1_4.b[3];
                            var4_24 = var1_4.b[4];
                            if (var1_4.b[1] == -3) {
                                if (var3_16 == var1_4.b && var4_24 == var1_4.c) break;
                                var1_4.b = var3_16;
                                var1_4.c = var4_24;
                            }
                            v1 = var1_4.a;
                            var2_11 = v1;
                            v2 = var1_4.a;
                            var2_11 = v2;
                            v3 = var1_4.a;
                            var2_11 = v3;
                            var2_11 = var1_4.a;
                            var1_4.a.a(var2_10, var3_16, var4_24, v1.g, v2.h, v3.g, var2_11.h);
                            break;
                        }
                        case 9: {
                            if (var1_4.a < 7) break;
                            var2_10 = (byte)(var1_4.b[2] & 127);
                            var3_17 = ay.b(var1_4.b, 3);
                            var4_24 = ay.b(var1_4.b, 5);
                            v4 = var1_4.a;
                            var2_12 = v4;
                            v5 = var1_4.a;
                            var2_12 = v5;
                            v6 = var1_4.a;
                            var2_12 = v6;
                            var2_12 = var1_4.a;
                            var1_4.a.a(var2_10, var3_17, var4_24, v4.g, v5.h, v6.g, var2_12.h);
                            break;
                        }
                        case 10: {
                            if (var1_4.a < 11) break;
                            var2_10 = (byte)(var1_4.b[2] & 127);
                            var3_18 = ay.b(var1_4.b, 3);
                            var4_24 = ay.b(var1_4.b, 7);
                            v7 = var1_4.a;
                            var2_13 = v7;
                            v8 = var1_4.a;
                            var2_13 = v8;
                            v9 = var1_4.a;
                            var2_13 = v9;
                            var2_13 = var1_4.a;
                            var1_4.a.a(var2_10, var3_18, var4_24, v7.g, v8.h, v9.g, var2_13.h);
                            break;
                        }
                        case 17: {
                            if (var1_4.a < 4) break;
                            var2_10 = ay.a(var1_4.b, 2);
                            var6_29 = var1_4.a;
                            var3_19 = var6_29.i;
                            var6_29 = var1_4.a;
                            var4_24 = var6_29.j;
                            var6_29 = var1_4.a;
                            var5_27 = var6_29.k;
                            if (var2_10 < var5_27) {
                                var2_10 = var5_27;
                            } else if (var2_10 > var3_19) {
                                var2_10 = var3_19;
                            }
                            var1_4.a.a(var2_10, var3_19, var4_24, var5_27);
                            break;
                        }
                        case 5: {
                            if (var1_4.a < 7) break;
                            var2_10 = var1_4.b[2];
                            var3_20 = ay.b(var1_4.b, 3);
                            switch (var2_10) {
                                case 1: {
                                    var7_30 = var3_20;
                                    var6_29 = var1_4.a;
                                    var1_4.a.d = var7_30;
                                    if (var7_30 <= 0) break block10;
                                    var6_29.a = new String[var7_30];
                                    var6_29.a = new int[var7_30];
                                    break;
                                }
                                case 2: {
                                    var7_30 = var3_20;
                                    var6_29 = var1_4.a;
                                    var1_4.a.e = var7_30;
                                    break;
                                }
                                case 3: {
                                    var7_30 = var3_20;
                                    var6_29 = var1_4.a;
                                    var1_4.a.f = var7_30;
                                    var3_20 = var7_30;
                                    var6_29.g = -aq.a(2, var3_20 - 1);
                                    var3_20 = var7_30;
                                    var6_29.h = aq.a(2, var3_20 - 1) - 1;
                                    break;
                                }
                                case 14: {
                                    var7_30 = var3_20;
                                    var6_29 = var1_4.a;
                                    var1_4.a.i = var7_30;
                                    break;
                                }
                                case 16: {
                                    var7_30 = var3_20;
                                    var6_29 = var1_4.a;
                                    var1_4.a.j = var7_30;
                                    break;
                                }
                                case 15: {
                                    var7_30 = var3_20;
                                    var6_29 = var1_4.a;
                                    var1_4.a.k = var7_30;
                                    break;
                                }
                                case -1: {
                                    if (var1_4.b) break;
                                    var2_14 = var1_4;
                                    if (var2_14.c && ao.a()) {
                                        var2_14.d();
                                    }
                                    var6_29 = var1_4.a;
                                    var1_4.a(var6_29.e <= 0 || var6_29.f > 0);
                                    break;
                                }
                            }
                            break;
                        }
                        case 4: {
                            if (var1_4.a < 37) break;
                            var2_10 = ay.a(var1_4.b, 2);
                            var3_21 = ay.a(var1_4.b, 3);
                            var4_24 = ay.a(var1_4.b, 4);
                            var5_28 = ay.a(var1_4.b, 5, var4_24);
                            var6_29 = var1_4.a;
                            if (var2_10 >= var6_29.d) break;
                            var7_30 = var2_10;
                            var6_29 = var1_4.a;
                            var6_29.a[var7_30] = var3_21;
                            var3_22 = var5_28;
                            var7_30 = var2_10;
                            var6_29 = var1_4.a;
                            var6_29.a[var7_30] = var3_22;
                            break;
                        }
                        case 3: {
                            if (var1_4.a < 45) break;
                            var2_10 = ay.a(var1_4.b, 2);
                            var3_23 = ay.a(var1_4.b, 4);
                            var4_24 = ay.a(var1_4.b, 10);
                            var7_30 = var2_10;
                            var6_29 = var1_4.a;
                            var1_4.a.a = var7_30;
                            var7_30 = var3_23;
                            var6_29 = var1_4.a;
                            var1_4.a.b = var7_30;
                            var7_30 = var4_24;
                            var6_29 = var1_4.a;
                            var1_4.a.c = var7_30;
                        }
                    }
lbl219:
                    // 22 sources

                    continue block38;
                }
            }
            var1_5 = this;
            switch (var2_10) {
                case -1: {
                    if (!var1_5.c) continue block38;
                    var1_5.b();
                    continue block38;
                }
                case -2: {
                    if (!var1_5.c) continue block38;
                    var1_5.b();
                    continue block38;
                }
            }
            if (var1_5.c) {
                var1_5.b();
            }
            throw new IllegalStateException();
        }
        if (this.b) {
            if (this.a) {
                this.a.a();
                return;
            }
        } else {
            this.a(false);
        }
    }

    private int a() {
        try {
            this.a = this.a.a(this.a);
        }
        catch (IOException iOException) {
            return -2;
        }
        if (this.a == null) {
            return -1;
        }
        this.a = this.a[0];
        if (this.a < 0) {
            return -1;
        }
        if (this.b == null || this.b.length < this.a) {
            this.b = new byte[this.a];
        }
        System.arraycopy(this.a, 1, this.b, 0, this.a);
        return 0;
    }

    private void d() {
        Object object = this.a;
        if (((aq)object).c == 3) {
            object = this.a;
            if (((aq)object).a <= 1) {
                object = this.a;
                if (((aq)object).b < 1) {
                    try {
                        object = new byte[]{4, -94, 6, 8, 0};
                        this.a.a((byte[])object);
                        object = new byte[]{4, -94, 6, -3, 1};
                        this.a.a((byte[])object);
                        return;
                    }
                    catch (IOException iOException) {
                        if (this.c) {
                            this.b();
                        }
                        return;
                    }
                }
            }
        }
        try {
            object = new byte[]{4, -94, 25, 1, -12};
            this.a.a((byte[])object);
            return;
        }
        catch (IOException iOException) {
            if (this.c) {
                this.b();
            }
            return;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void a(boolean bl) {
        Object object = this.b;
        synchronized (object) {
            if (this.b) {
                return;
            }
            this.b = true;
            this.a = bl;
            if (bl) {
                Object object2 = this.a;
                w w2 = this.a;
                this.a.a = object2;
                object2 = this.a;
                w2 = this.a;
                object2 = w2.a;
                synchronized (object2) {
                    if (w2.a && w2.a != null) {
                        new z(w2);
                        for (int i2 = 0; i2 < w2.a.size(); ++i2) {
                            w2.a.elementAt(i2);
                        }
                    }
                }
            }
            this.b.notify();
            return;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void c() {
        while (!this.b) {
            Object object = this.b;
            synchronized (object) {
                this.b.wait();
            }
        }
    }

    private static short a(byte[] byArray, int n2) {
        return (short)(byArray[n2] & 0xFF);
    }

    private static short b(byte[] byArray, int n2) {
        return (short)((byArray[n2 + 1] & 0xFF) + ((byArray[n2] & 0xFF) << 8));
    }

    private static int a(byte[] byArray, int n2) {
        return (byArray[n2 + 1] & 0xFF) + ((byArray[n2] & 0xFF) << 8);
    }

    private static int b(byte[] byArray, int n2) {
        return (byArray[n2 + 3] & 0xFF) + ((byArray[n2 + 2] & 0xFF) << 8) + ((byArray[n2 + 1] & 0xFF) << 16) + ((byArray[n2] & 0xFF) << 24);
    }

    private static String a(byte[] byArray, int n2, int n3) {
        try {
            return new String(byArray, n2, n3, "UTF-8");
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            return null;
        }
    }
}

