/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.nokia.mid.ui.DeviceControl
 *  javax.microedition.lcdui.Display
 *  javax.microedition.lcdui.Displayable
 *  javax.microedition.lcdui.Image
 *  javax.microedition.media.Manager
 *  javax.microedition.media.Player
 *  javax.microedition.media.control.VolumeControl
 *  javax.microedition.midlet.MIDlet
 *  javax.microedition.rms.RecordStore
 *  javax.microedition.rms.RecordStoreException
 *  javax.microedition.rms.RecordStoreNotFoundException
 */
package com.hardwire.blob;

import com.nokia.mid.ui.DeviceControl;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Image;
import javax.microedition.media.Manager;
import javax.microedition.media.Player;
import javax.microedition.media.control.VolumeControl;
import javax.microedition.midlet.MIDlet;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;
import javax.microedition.rms.RecordStoreNotFoundException;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class Main
extends MIDlet
implements Runnable {
    public static int a;
    public static int b;
    public static int c;
    public static Main a;
    public k a;
    public s a;
    public g a;
    public at a;
    public at b;
    private Display a;
    public byte a;
    public boolean a;
    private boolean l = false;
    private boolean m = false;
    public boolean b;
    public static boolean c;
    public static boolean d;
    public static boolean e;
    public boolean f;
    public boolean g;
    private int g = true;
    private long a;
    public int d = 2;
    private long b;
    private RecordStore a;
    private ByteArrayOutputStream a;
    public DataOutputStream a;
    private ByteArrayInputStream a;
    public DataInputStream a;
    public r a;
    public w a;
    public o a;
    public byte b;
    public o b = false;
    public static final int[] a;
    public static final int[] b;
    public static final int[] c;
    public static final int[] d;
    public static final int[] e;
    public int[][] a;
    private boolean n = false;
    public static boolean h;
    public static int e;
    public static boolean i;
    private boolean o;
    public static int f;
    public static boolean j;
    public static boolean k;
    private Player[] a;
    private VolumeControl[] a = null;

    protected void startApp() {
        if (this.m) {
            this.d();
            return;
        }
        this.m = true;
        a = this;
        this.a = (byte)2;
        this.a = new at(this);
        this.b = new at(this);
        this.a = new g(this);
        this.a = new k(this);
        this.a = new s(this);
        this.a = new r(this.a);
        this.a.a();
        this.a = Display.getDisplay((MIDlet)this);
        this.a.setCurrent((Displayable)this.a);
        this.a.repaint();
        new Thread(this).start();
    }

    public final int[] a(int n2, int n3) {
        int n4;
        boolean[][] blArrayArray = new boolean[a.length][];
        boolean[] blArray = new boolean[a.length];
        for (n4 = 0; n4 < blArrayArray.length; ++n4) {
            blArrayArray[n4] = new boolean[a[n4]];
        }
        try {
            int n5;
            int n6;
            this.a("achi");
            n4 = this.a.readInt();
            int n7 = this.a.readInt();
            int n8 = this.a.readInt();
            for (n6 = 0; n6 < blArrayArray.length; ++n6) {
                for (n5 = 0; n5 < blArrayArray[n6].length; ++n5) {
                    blArrayArray[n6][n5] = this.a.readBoolean();
                }
            }
            for (n6 = 0; n6 < blArray.length; ++n6) {
                blArray[n6] = this.a.readBoolean();
            }
            this.a(false);
            if (n3 == -1) {
                n6 = !blArray[n2] ? 1 : 0;
                blArray[n2] = true;
            } else {
                n6 = !blArrayArray[n2][n3] ? 1 : 0;
                if (n6 != 0) {
                    blArrayArray[n2][n3] = true;
                    ++n8;
                }
            }
            this.b("achi");
            this.a.writeInt(n4);
            this.a.writeInt(n7);
            this.a.writeInt(n8);
            for (n5 = 0; n5 < blArrayArray.length; ++n5) {
                for (n2 = 0; n2 < blArrayArray[n5].length; ++n2) {
                    this.a.writeBoolean(blArrayArray[n5][n2]);
                }
            }
            for (n5 = 0; n5 < blArray.length; ++n5) {
                this.a.writeBoolean(blArray[n5]);
            }
            this.a(true);
            if (n6 != 0) {
                for (n5 = 0; n5 < b.length; ++n5) {
                    if (b[n5] != n8) continue;
                    return new int[]{1, n8};
                }
                for (n5 = 0; n5 < c.length; ++n5) {
                    if (c[n5] != n8) continue;
                    return new int[]{5, n8};
                }
                for (n5 = 0; n5 < d.length; ++n5) {
                    if (d[n5] != n8) continue;
                    return new int[]{4, n8};
                }
                return new int[]{3, n8};
            }
        }
        catch (Exception exception) {}
        return new int[]{0, 0};
    }

    public final void a() {
        switch (this.d) {
            case 0: {
                a = 160;
                b = 160;
                c = 4;
                return;
            }
            case 1: {
                a = 115;
                b = 115;
                c = 3;
                return;
            }
            case 2: {
                a = 70;
                b = 70;
                c = 2;
                return;
            }
            case 3: {
                a = 31;
                b = 31;
                c = 1;
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void run() {
        if (this.a == 2 || this.a == 3) {
            int n2;
            int n3;
            for (n3 = 0; n3 < 3; ++n3) {
                this.a.e = n3;
                this.a.n();
                for (n2 = 0; this.a == 2 && this.a.e == n3 && n2 < 60; ++n2) {
                    try {
                        Thread.sleep(50L);
                        continue;
                    }
                    catch (InterruptedException interruptedException) {}
                }
                this.a.a[n3] = null;
            }
            this.a.e = 3;
            try {
                this.a.a[3] = Image.createImage((String)"/ze_logo.png");
            }
            catch (IOException iOException) {}
            this.a.n();
            for (n3 = 0; this.a == 2 && this.a.e == 3 && n3 < 60; ++n3) {
                try {
                    Thread.sleep(50L);
                    continue;
                }
                catch (InterruptedException interruptedException) {}
            }
            this.a.a[3] = null;
            Main main = this;
            try {
                main.a = (byte)3;
                main.a.n();
                System.gc();
                e = !Main.a("settings");
                main.a.c();
                main.a.g();
                if (!e) {
                    main.a.g();
                }
                main.a.b(4);
                at.d();
                at.e();
                main.a.f();
                main.b.a();
                main.a.b(10);
                ax.a = new v[5];
                for (n2 = 0; n2 < ax.a.length; ++n2) {
                    n3 = -1;
                    switch (n2) {
                        case 0: {
                            n3 = 4;
                            break;
                        }
                        case 1: 
                        case 4: {
                            n3 = 4;
                            break;
                        }
                        case 2: {
                            n3 = 4;
                            break;
                        }
                        case 3: {
                            n3 = 10;
                        }
                    }
                    ax.a[n2] = n2 == 3 ? new v(6, n3) : new v(6, n3);
                }
                main.a.b(4);
                d.a(main.a);
                main.a.b(10);
                main.a.a.a = main.a.a(145);
                main.a.b(4);
                new d(main.a, 0, 0, 0, 0);
                main.a.b(4);
                new ax(main.a, 0, 0, 0);
                main.a.b(4);
                new ae(main.a);
                main.a.b(4);
                new u(main.a, 0, new ac(main.a, 0, new bg(), new bg(), 1), 0, 0);
                main.a.b(4);
                Object object = main;
                main.n = false;
                ((Main)object).f();
                main.a.g();
                main.a.b();
                an.a("/tz." + at.a[at.a]);
                an.b("UTF-8");
                an.a().a();
                main.a = new w(1);
                Object object2 = main.a;
                object = main.a;
                if (object2 == null) {
                    throw new NullPointerException();
                }
                Object object3 = ((w)object).a;
                synchronized (object3) {
                    if (((w)object).b == null) {
                        ((w)object).b = new Vector(1);
                    }
                    ((w)object).b.addElement(object2);
                }
                main.a.a(main.a);
                Object object4 = new q();
                object2 = main.a;
                object = object4;
                if (object2 == null) {
                    throw new NullPointerException();
                }
                object3 = object;
                synchronized (object3) {
                    if (((q)object).a == null) {
                        ((q)object).a = new Vector(1);
                    }
                    ((q)object).a.addElement(object2);
                }
                main.a.a((i)object4);
                object2 = main;
                object = main.a;
                if (object2 == null) {
                    throw new NullPointerException();
                }
                object3 = ((w)object).a;
                synchronized (object3) {
                    if (((w)object).a == null) {
                        ((w)object).a = new Vector(1);
                    }
                    ((w)object).a.addElement(object2);
                }
                object4 = p.a(main);
                main.a = o.a(main.a, (be)object4);
                if (e) {
                    main.a.b(false);
                }
                if (!Main.a("achi")) {
                    int n4;
                    main.b("achi");
                    main.a.writeInt(0);
                    main.a.writeInt(0);
                    main.a.writeInt(0);
                    for (n4 = 0; n4 < a.length; ++n4) {
                        for (int i2 = 0; i2 < a[n4]; ++i2) {
                            main.a.writeBoolean(false);
                        }
                    }
                    for (n4 = 0; n4 < a.length; ++n4) {
                        main.a.writeBoolean(false);
                    }
                    main.a(true);
                }
                main.a.h();
                System.gc();
            }
            catch (Exception exception) {}
            main.a();
            main.b = true;
            this.a = 1;
            this.a.b = 0;
            this.b();
            this.a.a();
            this.a = 1;
            this.a.n();
            try {
                Thread.sleep(10L);
            }
            catch (InterruptedException interruptedException) {}
        }
        this.g = 0;
        this.f = false;
        do {
            long l2 = 0L;
            long l3 = 0L;
            do {
                try {
                    long l4;
                    this.b();
                    l3 = System.currentTimeMillis();
                    boolean bl = true;
                    this.o = false;
                    if (!this.a.a) {
                        switch (this.a) {
                            case 0: {
                                this.a.c();
                                break;
                            }
                            case 1: {
                                bl = this.a.a();
                                break;
                            }
                            case 6: {
                                s.a.c();
                            }
                        }
                    }
                    if (!this.f && bl) {
                        this.a.n();
                    }
                    if (this.a.a() && (l4 = System.currentTimeMillis()) - this.b >= 1000L) {
                        this.b = l4;
                        DeviceControl.setLights((int)0, (int)100);
                    }
                    l2 = System.currentTimeMillis() - l3;
                    ++this.g;
                }
                catch (Exception exception) {}
            } while (l2 >= (long)this.a() && !this.f);
            long l5 = (long)this.a() - l2;
            l5 = l5 < 1L ? 1L : l5;
            try {
                Thread.sleep(l5);
            }
            catch (InterruptedException interruptedException) {}
        } while (!this.f);
        this.destroyApp(true);
    }

    public final void b() {
        if (this.a) {
            while (!this.l && !this.f) {
                try {
                    Thread.sleep(1L);
                }
                catch (Exception exception) {}
            }
            this.a = false;
            if (this.f) {
                return;
            }
            this.a.a = true;
            this.a.n();
            this.g();
            this.f();
            if (k && !this.a) {
                this.a(12, true);
            }
            this.a.a = false;
            if (this.a == 0 && this.a.c == 0 && d) {
                this.a.a.a(-7);
            } else if (this.a == 0 && this.a.c == 6) {
                this.a.a.e = 0;
            }
            if (this.a != 1 || this.a.b != 0) {
                this.a.n();
            }
            this.a = false;
        }
    }

    private int a() {
        if (this.a == 0) {
            if (this.a.b == 1) {
                return b;
            }
            if (this.a.b == 2) {
                return a;
            }
            if (this.a.c == 6 || this.a.c == 9 || this.a.c == 5) {
                return b;
            }
            return 0;
        }
        return b;
    }

    public final void c() {
        this.l = false;
        if (c && !this.a) {
            this.a = true;
            boolean bl = k;
            this.c(12);
            k = bl;
            this.g();
        }
        this.a.b = true;
    }

    public final void d() {
        if (this.a) {
            this.l = true;
        }
    }

    public final void a(String string) {
        string = "gigo" + string;
        try {
            this.a.closeRecordStore();
        }
        catch (Exception exception) {}
        try {
            this.a = RecordStore.openRecordStore((String)string, (boolean)false);
            this.a = new ByteArrayInputStream(this.a.getRecord(1));
            this.a = new DataInputStream(this.a);
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    public final void b(String string) {
        string = "gigo" + string;
        try {
            this.a.closeRecordStore();
        }
        catch (Exception exception) {}
        try {
            RecordStore.deleteRecordStore((String)string);
        }
        catch (RecordStoreNotFoundException recordStoreNotFoundException) {}
        try {
            this.a = RecordStore.openRecordStore((String)string, (boolean)true);
            this.a = new ByteArrayOutputStream();
            this.a = new DataOutputStream(this.a);
            return;
        }
        catch (RecordStoreNotFoundException recordStoreNotFoundException) {
            return;
        }
    }

    public final void c(String string) {
        string = "gigo" + string;
        try {
            this.a.closeRecordStore();
        }
        catch (Exception exception) {}
        try {
            this.a = RecordStore.openRecordStore((String)string, (boolean)true);
            this.a = new ByteArrayOutputStream();
            this.a = new DataOutputStream(this.a);
            return;
        }
        catch (RecordStoreNotFoundException recordStoreNotFoundException) {
            return;
        }
    }

    public final void d(String string) {
        string = "gigo" + string;
        try {
            this.a.closeRecordStore();
        }
        catch (Exception exception) {}
        try {
            RecordStore.deleteRecordStore((String)string);
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    public final void a(boolean bl) {
        try {
            if (bl) {
                this.a.flush();
                byte[] byArray = this.a.toByteArray();
                this.a.addRecord(byArray, 0, byArray.length);
                this.a.close();
                this.a.close();
            } else {
                this.a.close();
                this.a.close();
            }
        }
        catch (Exception exception) {}
        try {
            this.a.closeRecordStore();
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    public static boolean a(String string) {
        string = "gigo" + string;
        try {
            string = RecordStore.openRecordStore((String)string, (boolean)false);
            int n2 = string.getNumRecords();
            string.closeRecordStore();
            return n2 > 0;
        }
        catch (RecordStoreNotFoundException recordStoreNotFoundException) {
        }
        catch (RecordStoreException recordStoreException) {}
        return false;
    }

    protected void pauseApp() {
        this.c();
    }

    public void destroyApp(boolean bl) {
        this.f = true;
        Display.getDisplay((MIDlet)this).setCurrent(null);
        this.notifyDestroyed();
    }

    public final void a(int n2) {
        long l2 = System.currentTimeMillis();
        if (this.g && l2 - this.a > (long)n2) {
            this.a = l2;
            try {
                this.a.vibrate(n2);
                return;
            }
            catch (Exception exception) {}
        }
    }

    public final void e() {
        bg bg2 = new bg(-512, -512);
        bg bg3 = new bg(32768, -512);
        bg bg4 = new bg(-512, 32768);
        bg bg5 = new bg(32768, 32768);
        bg bg6 = new bg(512, -512);
        bg bg7 = new bg(32256, -512);
        bg bg8 = new bg(-512, 32256);
        bg bg9 = new bg(33280, 32256);
        bg bg10 = new bg(1024, 1024);
        bg bg11 = new bg(-1024, 1024);
        this.a.a = new bg[][]{{bg2, bg3}, {bg3, bg5}, {bg5, bg4}, {bg4, bg2}, {bg2, bg3, bg5}, {bg3, bg5, bg4}, {bg5, bg4, bg2}, {bg4, bg2, bg3}, {bg2, bg3, null, bg5, bg4}, {bg3, bg5, null, bg4, bg2}, {bg2, bg3, bg5, bg4}, {bg3, bg5, bg4, bg2}, {bg5, bg4, bg2, bg3}, {bg4, bg2, bg3, bg5}, {bg2, bg3, bg5, bg4, bg2}, {bg6, bg9}, {bg8, bg7}, {bg9.a(bg11), bg6.a(bg11)}, {bg7.a(bg10), bg8.a(bg10)}, {new bg(bg4.a, 15360), new bg(bg5.a, 15360)}, {new bg(15360, bg5.b), new bg(15360, bg3.b)}, {new bg(bg3.a, 17408), new bg(bg2.a, 17408)}, {new bg(17408, bg2.b), new bg(17408, bg4.b)}, {bg6, bg9, bg8}, {bg9, bg8, bg7}, {bg9.a(bg11), bg6.a(bg11), bg7.a(bg10)}, {bg6.a(bg11), bg7.a(bg10), bg8.a(bg10)}, {new bg(bg2.a, 15360), bg2, bg3, new bg(bg3.a, 15360), new bg(bg2.a, 15360)}, {new bg(8192, bg2.b), new bg(24576, bg2.b), new bg(24576, bg5.b), new bg(8192, bg5.b), new bg(8192, bg2.b)}};
        this.a.a = new byte[this.a.d][this.a.e];
        for (int i2 = 0; i2 < this.a.d; ++i2) {
            for (int i3 = 0; i3 < this.a.e; ++i3) {
                this.a(i2, i3);
            }
        }
    }

    /*
     * Handled duff style switch with additional control
     * Enabled aggressive block sorting
     */
    public final void a(int n2, int n3) {
        byte[][] byArray = this.a.a;
        int n4 = this.a(n2, n3);
        if (n4 == 0 && this.a.a[1][n2][n3] != 69) {
            if (!k.a[this.a.a[1][n2][n3]]) {
                this.a.a[0][n2][n3] = -1;
            }
        } else {
            if (this.a.a[0][n2][n3] == -1 && this.a.b != 0) {
                this.a.a[0][n2][n3] = 11;
            }
            if (this.a.a.a != 0 && this.a.a[0][n2][n3] == 11) {
                this.a.a[0][n2][n3] = 51;
            }
        }
        switch (n4) {
            case -1: {
                byArray[n2][n3] = -1;
                return;
            }
            case 0: {
                n4 = 0;
                byte by = this.a(n2 - 1, n3);
                byte by2 = this.a(n2 + 1, n3);
                byte by3 = this.a(n2, n3 - 1);
                int n5 = this.a(n2, n3 + 1);
                if (by == 0) {
                    ++n4;
                }
                if (by2 == 0) {
                    ++n4;
                }
                if (by3 == 0) {
                    ++n4;
                }
                if (n5 == 0) {
                    ++n4;
                }
                int n6 = Integer.MIN_VALUE;
                block23: do {
                    switch (n6 == Integer.MIN_VALUE ? n4 : n6) {
                        case 4: {
                            n4 = this.a(n2 - 1, n3 - 1);
                            by = this.a(n2 + 1, n3 - 1);
                            by2 = this.a(n2 + 1, n3 + 1);
                            by3 = this.a(n2 - 1, n3 + 1);
                            n5 = 0;
                            if (n4 == -1) {
                                ++n5;
                            }
                            if (by == -1) {
                                ++n5;
                            }
                            if (by2 == -1) {
                                ++n5;
                            }
                            if (by3 == -1) {
                                ++n5;
                            }
                            n6 = 0;
                            if (n5 >= 3) continue block23;
                            if (n4 == -1 && by == -1) {
                                byArray[n2][n3] = 13;
                                return;
                            }
                            if (by == -1 && by2 == -1) {
                                byArray[n2][n3] = 10;
                                return;
                            }
                            if (by2 == -1 && by3 == -1) {
                                byArray[n2][n3] = 11;
                                return;
                            }
                            if (by3 == -1 && n4 == -1) {
                                byArray[n2][n3] = 12;
                                return;
                            }
                            if (n4 == -1 && by2 == -1 || by == -1 && by3 == -1) {
                                byArray[n2][n3] = 14;
                                return;
                            }
                            if (n4 == -1) {
                                byArray[n2][n3] = 7;
                                return;
                            }
                            if (by == -1) {
                                byArray[n2][n3] = 4;
                                return;
                            }
                            if (by2 == -1) {
                                byArray[n2][n3] = 5;
                                return;
                            }
                            if (by3 == -1) {
                                byArray[n2][n3] = 6;
                                return;
                            }
                            byArray[n2][n3] = -1;
                            return;
                        }
                        case 3: {
                            if (by3 != 0) {
                                by2 = this.a(n2 + 1, n3 + 1);
                                by3 = this.a(n2 - 1, n3 + 1);
                                if (by3 == -1) {
                                    n6 = 0;
                                    if (by2 == -1) continue block23;
                                }
                                if (by3 == -1) {
                                    byArray[n2][n3] = 12;
                                    return;
                                }
                                if (by2 == -1) {
                                    byArray[n2][n3] = 10;
                                    return;
                                }
                                byArray[n2][n3] = 0;
                                return;
                            }
                            if (by2 != 0) {
                                n4 = this.a(n2 - 1, n3 - 1);
                                by3 = this.a(n2 - 1, n3 + 1);
                                if (n4 == -1) {
                                    n6 = 0;
                                    if (by3 == -1) continue block23;
                                }
                                if (n4 == -1) {
                                    byArray[n2][n3] = 13;
                                    return;
                                }
                                if (by3 == -1) {
                                    byArray[n2][n3] = 11;
                                    return;
                                }
                                byArray[n2][n3] = 1;
                                return;
                            }
                            if (n5 != 0) {
                                by = this.a(n2 + 1, n3 - 1);
                                n4 = this.a(n2 - 1, n3 - 1);
                                if (by == -1) {
                                    n6 = 0;
                                    if (n4 == -1) continue block23;
                                }
                                if (by == -1) {
                                    byArray[n2][n3] = 10;
                                    return;
                                }
                                if (n4 == -1) {
                                    byArray[n2][n3] = 12;
                                    return;
                                }
                                byArray[n2][n3] = 2;
                                return;
                            }
                            if (by == 0) return;
                            by = this.a(n2 + 1, n3 - 1);
                            by2 = this.a(n2 + 1, n3 + 1);
                            if (by == -1) {
                                n6 = 0;
                                if (by2 == -1) continue block23;
                            }
                            if (by == -1) {
                                byArray[n2][n3] = 13;
                                return;
                            }
                            if (by2 == -1) {
                                byArray[n2][n3] = 11;
                                return;
                            }
                            byArray[n2][n3] = 3;
                            return;
                        }
                        case 2: {
                            if (by3 != 0 && by2 != 0) {
                                byArray[n2][n3] = (byte)(this.a(n2 - 1, n3 + 1) == -1 ? 14 : 4);
                                return;
                            }
                            if (by2 != 0 && n5 != 0) {
                                byArray[n2][n3] = (byte)(this.a(n2 - 1, n3 - 1) == -1 ? 14 : 5);
                                return;
                            }
                            if (n5 != 0 && by != 0) {
                                byArray[n2][n3] = (byte)(this.a(n2 + 1, n3 - 1) == -1 ? 14 : 6);
                                return;
                            }
                            if (by != 0 && by3 != 0) {
                                byArray[n2][n3] = (byte)(this.a(n2 + 1, n3 + 1) == -1 ? 14 : 7);
                                return;
                            }
                            if (by3 != 0 && n5 != 0) {
                                byArray[n2][n3] = 8;
                                return;
                            }
                            if (by == 0) return;
                            if (by2 == 0) return;
                            byArray[n2][n3] = 9;
                            return;
                        }
                        case 1: {
                            if (by == 0) {
                                byArray[n2][n3] = 10;
                                return;
                            }
                            if (by3 == 0) {
                                byArray[n2][n3] = 11;
                                return;
                            }
                            if (by2 == 0) {
                                byArray[n2][n3] = 12;
                                return;
                            }
                            if (n5 != 0) return;
                            byArray[n2][n3] = 13;
                            return;
                        }
                        case 0: {
                            byArray[n2][n3] = 14;
                            return;
                        }
                    }
                    return;
                } while (true);
                return;
            }
            case 1: {
                switch (this.a.a[1][n2][n3]) {
                    case 1: 
                    case 16: 
                    case 37: 
                    case 65: {
                        byArray[n2][n3] = (byte)(this.a(n2, n3 + 1) != 0 ? 23 : 15);
                        return;
                    }
                    case 2: 
                    case 17: 
                    case 38: 
                    case 64: {
                        byArray[n2][n3] = (byte)(this.a(n2, n3 + 1) != 0 ? 24 : 16);
                        return;
                    }
                    case 3: 
                    case 39: 
                    case 66: {
                        byArray[n2][n3] = (byte)(this.a(n2, n3 - 1) != 0 ? 25 : 17);
                        return;
                    }
                    case 4: 
                    case 40: 
                    case 67: {
                        byArray[n2][n3] = (byte)(this.a(n2, n3 - 1) != 0 ? 26 : 18);
                        return;
                    }
                    case 7: 
                    case 68: {
                        byArray[n2][n3] = 19;
                        return;
                    }
                    case 71: {
                        byArray[n2][n3] = 20;
                        return;
                    }
                    case 72: {
                        byArray[n2][n3] = 22;
                        return;
                    }
                    case 10: 
                    case 11: 
                    case 12: {
                        byArray[n2][n3] = (byte)(10 + this.a.a[1][n2][n3]);
                        return;
                    }
                    case 69: {
                        byArray[n2][n3] = 28;
                    }
                }
                return;
            }
        }
    }

    public final byte a(int n2, int n3) {
        if (n2 < 0 || n3 < 0 || n2 >= this.a.d || n3 >= this.a.e) {
            return 0;
        }
        n2 = this.a.a[1][n2][n3];
        switch (n2) {
            case -1: 
            case 8: 
            case 9: 
            case 13: 
            case 43: 
            case 70: {
                return -1;
            }
            case 1: 
            case 2: 
            case 3: 
            case 4: 
            case 7: 
            case 10: 
            case 11: 
            case 12: 
            case 16: 
            case 17: 
            case 37: 
            case 38: 
            case 39: 
            case 40: 
            case 64: 
            case 65: 
            case 66: 
            case 67: 
            case 68: 
            case 71: 
            case 72: {
                return 1;
            }
        }
        return 0;
    }

    private static x a(h h2, bg bg2) {
        long l2 = Long.MAX_VALUE;
        int n2 = -1;
        for (int i2 = 0; i2 < h2.a.length; ++i2) {
            long l3 = bg2.b(h2.a[i2].a).d();
            if (l3 >= l2 || l3 > 8192L) continue;
            n2 = i2;
            l2 = l3;
        }
        if (n2 == -1) {
            return null;
        }
        return h2.a[n2];
    }

    public final DataInputStream a(String object, byte[] byArray) {
        try {
            DataInputStream dataInputStream = null;
            if (byArray != null) {
                dataInputStream = new DataInputStream(new ByteArrayInputStream(byArray));
            } else {
                object = "/levels/" + (String)object + ".lvl";
                if ((object = Main.a((String)object)) == null) {
                    return null;
                }
                dataInputStream = new DataInputStream((InputStream)object);
            }
            this.a.e();
            this.a.d = dataInputStream.readByte() & 0xFF;
            this.a.e = dataInputStream.readByte() & 0xFF;
            this.a.a.b = this.a.a.a;
            this.a.a.a = dataInputStream.readByte();
            return dataInputStream;
        }
        catch (Exception exception) {
            return null;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void a(DataInputStream dataInputStream) {
        block83: {
            try {
                int n2;
                int n3;
                int n4;
                int n5;
                int n6;
                int n7;
                int n8;
                int n9;
                this.a.a(468 + this.a.a.a);
                if (this.a.b == 23) {
                    for (n9 = 0; n9 < 4; ++n9) {
                        this.a.a(n9 + 531);
                    }
                }
                n9 = dataInputStream.readByte();
                int n10 = dataInputStream.readByte();
                int n11 = n9 + n10;
                int n12 = 0;
                Object object = new int[n11][3];
                for (n8 = 0; n8 < n11; ++n8) {
                    object[n8][0] = dataInputStream.readByte() & 0xFF;
                    object[n8][1] = dataInputStream.readByte() & 0xFF;
                    object[n8][2] = dataInputStream.readByte() & 0xFF;
                    if (object[n8][2] - 2 == 2) {
                        n10 += 3;
                        continue;
                    }
                    if (object[n8][2] - 2 == 5) {
                        n10 += 10;
                        continue;
                    }
                    if (object[n8][2] != 1) continue;
                    ++n12;
                }
                this.a.a = new d[(this.a.b == 1 ? 1 : n12) + n9 - n12];
                this.a.a = new ae[n10];
                n10 = 0;
                n8 = 0;
                n9 = 0;
                n12 = 0;
                block30: for (n7 = 0; n7 < n11; ++n7) {
                    switch (object[n7][2]) {
                        case 1: {
                            if (this.a.b == 1 && n12 != 0) continue block30;
                            this.a.a[n9] = new d(this.a, n12 == 0 ? (byte)0 : 1, 0, (object[n7][0] << 5) - 16, (this.a.e - object[n7][1] << 5) + 16);
                            this.a.a[n9].b = n9;
                            ++n9;
                            ++n12;
                            continue block30;
                        }
                        case 5: {
                            this.a.a[n9] = new d(this.a, 1, this.a.b == 14 ? (byte)1 : (this.a.b == 34 ? (byte)3 : 2), (object[n7][0] << 5) - 16, (this.a.e - object[n7][1] << 5) + 16);
                            this.a.a[n9].b = n9;
                            ++n9;
                            continue block30;
                        }
                        default: {
                            byte by = (byte)(object[n7][2] - 2);
                            n6 = 0;
                            n6 = by != 2 && by != 5 ? n10++ : this.a.a.length - 1 - n8++;
                            this.a.a[n6] = new ae(this.a);
                            this.a.a[n6].a(by, new bg((object[n7][0] << 15) - 16384, (this.a.e - object[n7][1] << 15) + 16384), true);
                            ae.a(this.a, ae.a[by]);
                            if (by == 2) {
                                ae.a(this.a, ae.a[0]);
                                continue block30;
                            }
                            if (by != 5) continue block30;
                            ae.a(this.a, ae.a[6]);
                        }
                    }
                }
                for (n7 = (int)n10; n7 < this.a.a.length; ++n7) {
                    if (this.a.a[n7] != null) continue;
                    this.a.a[n7] = new ae(this.a);
                    this.a.a[n7].d = 0;
                }
                n7 = dataInputStream.readByte() & 0xFF;
                this.a.a = new ax[n7];
                h[][] hArray = new h[this.a.d][this.a.e];
                block32: for (n6 = 0; n6 < n7; ++n6) {
                    n9 = (dataInputStream.readByte() & 0xFF) - 1;
                    n10 = this.a.e - (dataInputStream.readByte() & 0xFF);
                    n11 = (dataInputStream.readByte() & 0xFF) - 1;
                    this.a.a[n6] = new ax(this.a, (byte)n11, n9, n10);
                    this.a.a[n6].a();
                    h h2 = this.a.a[n6].a;
                    switch (n11) {
                        case 0: 
                        case 5: {
                            hArray[n9][n10] = h2;
                            continue block32;
                        }
                        case 1: 
                        case 2: {
                            hArray[n9][n10] = h2;
                            hArray[n9 + 1][n10] = h2;
                            hArray[n9 + 2][n10] = h2;
                            continue block32;
                        }
                        case 7: {
                            hArray[n9][n10] = h2;
                            hArray[n9 + 1][n10] = h2;
                            continue block32;
                        }
                        case 3: 
                        case 6: {
                            hArray[n9][n10] = h2;
                            hArray[n9][n10 - 1] = h2;
                            hArray[n9][n10 - 2] = h2;
                            continue block32;
                        }
                        case 4: {
                            hArray[n9][n10] = h2;
                            hArray[n9][n10 - 1] = h2;
                            hArray[n9 + 1][n10] = h2;
                            hArray[n9 + 1][n10 - 1] = h2;
                            continue block32;
                        }
                        case 8: {
                            hArray[n9][n10] = h2;
                            hArray[n9 + 1][n10] = h2;
                            hArray[n9 + 2][n10] = h2;
                            hArray[n9 + 3][n10] = h2;
                            continue block32;
                        }
                        case 11: {
                            for (int i2 = 0; i2 < 8; ++i2) {
                                hArray[n9 + i2][n10] = h2;
                            }
                            continue block32;
                        }
                    }
                }
                this.a.d = new boolean[this.a.d];
                this.a.c = new boolean[this.a.e];
                n6 = dataInputStream.readByte() & 0xFF;
                this.a.a = new u[n6];
                n6 = 0;
                n9 = dataInputStream.readByte() & 0xFF;
                this.a.a = new ac[n9];
                for (n10 = 0; n10 < n9; ++n10) {
                    n11 = dataInputStream.readByte() - 1;
                    n12 = dataInputStream.readByte();
                    byte by = dataInputStream.readByte();
                    bg bg2 = new bg((dataInputStream.readByte() & 0xFF) - 1 << 15, this.a.e - (dataInputStream.readByte() & 0xFF) << 15);
                    bg bg3 = new bg((dataInputStream.readByte() & 0xFF) - 1 << 15, this.a.e - (dataInputStream.readByte() & 0xFF) << 15);
                    this.a.a[n10] = new ac(this.a, (byte)n11, bg2, bg3, n12);
                    if (by == 0) {
                        this.a.a[n10].a((byte)0, 0);
                        continue;
                    }
                    if (by == 5) {
                        this.a.a[n10].a((byte)1, 0);
                        continue;
                    }
                    this.a.a[n6] = new u(this.a, by, this.a.a[n10], (dataInputStream.readByte() & 0xFF) - 1, this.a.e - (dataInputStream.readByte() & 0xFF));
                    this.a.d[this.a.a[n6].a] = true;
                    this.a.c[this.a.a[n6].b] = true;
                    ++n6;
                }
                n10 = dataInputStream.readByte() & 0xFF;
                Vector<h> vector = new Vector<h>();
                object = new bg();
                bg bg4 = new bg();
                int[][] nArrayArray = new int[][]{{-1, -1}, {1, -1}, {1, 1}, {-1, 1}, {0, 0}};
                h h3 = null;
                for (n5 = 0; n5 < n10; ++n5) {
                    n9 = dataInputStream.readByte() & 0xFF;
                    n4 = n9 >> 4;
                    n3 = n9 & 0xF;
                    x x2 = null;
                    int[][] nArray = new int[n3][3];
                    for (n2 = 0; n2 < n3; ++n2) {
                        nArray[n2][1] = (dataInputStream.readByte() & 0xFF) - 1;
                        nArray[n2][2] = this.a.e - (dataInputStream.readByte() & 0xFF);
                        nArray[n2][0] = dataInputStream.readByte();
                    }
                    for (n2 = 0; n2 < n3; ++n2) {
                        x x3;
                        block86: {
                            Object object2;
                            block82: {
                                block88: {
                                    block87: {
                                        block84: {
                                            h h4;
                                            block85: {
                                                x3 = null;
                                                int n13 = nArray[n2][0];
                                                n12 = nArray[n2][1];
                                                int n14 = nArray[n2][2];
                                                object.a = n12 << 5;
                                                object.b = n14 << 5;
                                                int n15 = nArrayArray[n13][0];
                                                int n16 = nArrayArray[n13][1];
                                                switch (n13) {
                                                    case 1: {
                                                        object.a += 32;
                                                        break;
                                                    }
                                                    case 2: {
                                                        object.a += 32;
                                                        object.b += 32;
                                                        break;
                                                    }
                                                    case 3: {
                                                        object.b += 32;
                                                        break;
                                                    }
                                                    case 4: {
                                                        object.a += 16;
                                                        object.b += 16;
                                                        break;
                                                    }
                                                }
                                                object.a <<= 10;
                                                object.b <<= 10;
                                                if (n2 != 0 && n2 != n3 - 1) break block84;
                                                h4 = null;
                                                if (hArray[n12][n14] != null && (x3 = Main.a(hArray[n12][n14], (bg)object)) != null) {
                                                    h4 = hArray[n12][n14];
                                                }
                                                if (x3 == null && hArray[n12 + n15][n14] != null && (x3 = Main.a(hArray[n12 + n15][n14], (bg)object)) != null) {
                                                    h4 = hArray[n12 + n15][n14];
                                                }
                                                if (x3 == null && hArray[n12 + n15][n14 + n16] != null && (x3 = Main.a(hArray[n12 + n15][n14 + n16], (bg)object)) != null) {
                                                    h4 = hArray[n12 + n15][n14 + n16];
                                                }
                                                if (x3 == null && hArray[n12][n14 + n16] != null && (x3 = Main.a(hArray[n12][n14 + n16], (bg)object)) != null) {
                                                    h4 = hArray[n12][n14 + n16];
                                                }
                                                if (n2 != 0) break block85;
                                                if (h4 == null) {
                                                    n12 = nArray[n3 - 1][1];
                                                    n14 = nArray[n3 - 1][2];
                                                    n15 = nArrayArray[nArray[n3 - 1][0]][0];
                                                    n16 = nArrayArray[nArray[n3 - 1][0]][1];
                                                    if (hArray[n12][n14] != null) {
                                                        h4 = hArray[n12][n14];
                                                    } else if (hArray[n12 + n15][n14] != null) {
                                                        h4 = hArray[n12 + n15][n14];
                                                    } else if (hArray[n12 + n15][n14 + n16] != null) {
                                                        h4 = hArray[n12 + n15][n14 + n16];
                                                    } else if (hArray[n12][n14 + n16] != null) {
                                                        h4 = hArray[n12][n14 + n16];
                                                    }
                                                }
                                                h3 = h4;
                                                if (n14 - 1 >= 0) {
                                                    h3.a(hArray[n12][n14 - 1]);
                                                    if (n12 - 1 >= 0) {
                                                        h3.a(hArray[n12 - 1][n14 - 1]);
                                                    }
                                                    if (n12 + 1 < this.a.d) {
                                                        h3.a(hArray[n12 + 1][n14 - 1]);
                                                    }
                                                }
                                                break block84;
                                            }
                                            if (h4 != null && h4 != h3) {
                                                h4.a(h3);
                                                h3.a(h4);
                                            }
                                        }
                                        if (n2 < 1) break block86;
                                        object2 = null;
                                        if (x2 != null || x3 != null) break block87;
                                        if (n2 == 1) {
                                            x3 = new x(new bg((bg)object), 1024);
                                            x3.b |= 0x20;
                                            h3.a(x3);
                                            object2 = new ag(x3, new bg(bg4), k.a[n4], k.b[n4], k.c[n4]);
                                        }
                                        break block82;
                                    }
                                    if (x3 != null) break block88;
                                    if (n2 == n3 - 1) {
                                        bg bg5 = new bg((bg)object);
                                        object2 = new ag(x2, bg5, k.a[n4], k.b[n4], k.c[n4]);
                                        for (int i3 = 0; i3 < this.a.a.length; ++i3) {
                                            if (!al.a(this.a.a[i3].a.a(), bg5)) continue;
                                            this.a.a[i3].a.b(bg5);
                                            break block82;
                                        }
                                        break block82;
                                    } else {
                                        x3 = new x(new bg((bg)object), 1024);
                                        x3.b |= 0x20;
                                        h3.a(x3);
                                        object2 = new ag(x2, x3, k.a[n4], k.b[n4], k.c[n4]);
                                    }
                                    break block82;
                                }
                                object2 = x2 == null ? new ag(x3, new bg(bg4), k.a[n4], k.b[n4], k.c[n4]) : new ag(x2, x3, k.a[n4], k.b[n4], k.c[n4]);
                            }
                            if (n4 == 1) {
                                ((ag)object2).a = 1;
                            }
                            vector.addElement((h)object2);
                            x2 = object2;
                            object2 = h3;
                            ((h)object2).b.addElement(x2);
                        }
                        x2 = x3;
                        bg4.c((bg)object);
                    }
                }
                n5 = vector.size();
                this.a.a = new ag[n5];
                for (n9 = 0; n9 < n5; ++n9) {
                    this.a.a[n9] = (ag)vector.elementAt(n9);
                }
                vector.removeAllElements();
                for (n9 = 0; n9 < this.a.a.length; ++n9) {
                    this.a.a[n9].a.b();
                }
                System.gc();
                this.a = null;
                if (at.a(this.a.b)) {
                    this.a = new int[a[at.b(this.a.b)]][2];
                }
                this.a.l = 0;
                this.a.a = new byte[3][this.a.d][this.a.e];
                for (n9 = 0; n9 < this.a.d; ++n9) {
                    for (n4 = this.a.e - 1; n4 >= 0; --n4) {
                        int n17;
                        for (n3 = 0; n3 < 3; ++n3) {
                            n17 = dataInputStream.readByte();
                            this.a.a[n3][n9][n4] = (byte)((n17 & 0xFF) - 1);
                            n2 = this.a.a[n3][n9][n4];
                            if (n2 == -1) continue;
                            this.a.a(ab.a[n3] + n2);
                        }
                        if (this.a.b == -1 && (this.a.a[1][n9][n4] == 43 || this.a.a[1][n9][n4] == 70)) {
                            this.a.a[1][n9][n4] = -1;
                        }
                        if (this.a.a[1][n9][n4] == 43) {
                            if (at.a(this.a.b)) {
                                this.a[this.a.l][0] = n9;
                                this.a[this.a.l][1] = n4;
                            }
                            ++this.a.l;
                        }
                        if (this.a.a[1][n9][n4] != 70 || !at.a(this.a.b)) continue;
                        if (this.a.b != 1) {
                            this.a.a[1][n9][n4] = -1;
                            continue;
                        }
                        this.a("achi");
                        this.a.readInt();
                        this.a.readInt();
                        this.a.readInt();
                        for (n3 = 0; n3 < a.length; ++n3) {
                            for (n17 = 0; n17 < a[n3]; ++n17) {
                                this.a.readBoolean();
                            }
                        }
                        for (n3 = 0; n3 < a.length; ++n3) {
                            n17 = this.a.readBoolean() ? 1 : 0;
                            if (n3 != this.a.b || n17 == 0) continue;
                            this.a.a[1][n9][n4] = -1;
                            break;
                        }
                        this.a(false);
                    }
                }
                n5 = this.b.a.length;
                for (n9 = 0; n9 < n5; ++n9) {
                    this.b.a[n9] = false;
                }
                this.a.f = new boolean[this.a.d];
                this.a.e = new boolean[this.a.e];
                this.a.c = dataInputStream.readByte() & 0xFF;
                this.a.a = new short[this.a.c][3];
                for (n9 = 0; n9 < this.a.c; ++n9) {
                    this.a.a[n9][0] = (short)((dataInputStream.readByte() & 0xFF) - 1);
                    this.a.a[n9][1] = (short)(this.a.e - (dataInputStream.readByte() & 0xFF));
                    this.a.a[n9][2] = (short)((dataInputStream.readByte() & 0xFF) - 1);
                    this.a.f[this.a.a[n9][0]] = true;
                    this.a.e[this.a.a[n9][1]] = true;
                    this.b.a[this.a.a[n9][2]] = true;
                    n4 = ab.a[this.a.a[n9][2]];
                    for (n3 = 0; n3 <= 11; ++n3) {
                        if ((n4 & 1 << n3) == 0) continue;
                        this.a.a(256 + ab.b[n3]);
                        this.a.a(256 + ab.c[n3] + 8);
                    }
                }
            }
            catch (Exception exception) {
                try {
                    dataInputStream.close();
                }
                catch (Exception exception2) {}
                break block83;
            }
            catch (Throwable throwable) {
                try {
                    dataInputStream.close();
                    throw throwable;
                }
                catch (Exception exception) {}
                throw throwable;
            }
            try {
                dataInputStream.close();
            }
            catch (Exception exception) {}
        }
        if (this.a.b == 20) {
            this.a.a(471);
        }
        this.a.d();
        ae.a(this.a);
        d.a(this.a);
        this.b.c();
    }

    public static InputStream a(String string) {
        return string.getClass().getResourceAsStream(string);
    }

    public final void f() {
        if (this.n) {
            return;
        }
        if (this.a == null) {
            this.a = new Player[13];
            this.a = new VolumeControl[this.a.length];
        }
        for (int i2 = 0; i2 < this.a.length; ++i2) {
            this.a[i2] = null;
            this.d(i2);
            if (this.b) continue;
            this.a.b(4);
        }
        this.n = true;
    }

    private void d(int n2) {
        if (n2 >= this.a.length) {
            return;
        }
        String string = "";
        switch (n2) {
            case 2: {
                string = "/sound/amber";
                break;
            }
            case 1: {
                string = "/sound/tarball";
                break;
            }
            case 0: {
                string = "/sound/gishhit";
                break;
            }
            case 3: {
                string = "/sound/CLICK015";
                break;
            }
            case 6: {
                string = "/sound/blockbreak";
                break;
            }
            case 7: {
                string = "/sound/splash";
                break;
            }
            case 4: {
                string = "/sound/squish";
                break;
            }
            case 5: {
                string = "/sound/switch";
                break;
            }
            case 10: {
                string = "/sound/bobattack";
                break;
            }
            case 8: {
                string = "/sound/necksnap";
                break;
            }
            case 9: {
                string = "/sound/ropebreak";
                break;
            }
            case 11: {
                string = "/sound/visattack";
                break;
            }
            case 12: {
                string = "/sound/sewer.mp3";
            }
        }
        if (n2 != 12) {
            string = string + ".wav";
        }
        InputStream inputStream = null;
        try {
            inputStream = Main.a(string);
        }
        catch (Exception exception) {}
        try {
            this.a[n2] = n2 == 12 ? Manager.createPlayer((InputStream)inputStream, (String)"audio/mpeg") : Manager.createPlayer((InputStream)inputStream, (String)"audio/x-wav");
        }
        catch (Exception exception) {}
        try {
            this.a[n2].realize();
        }
        catch (Exception exception) {}
        try {
            this.a[n2].prefetch();
        }
        catch (Exception exception) {}
        try {
            inputStream.close();
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    public final void g() {
        if (!this.n) {
            return;
        }
        if (this.a == null) {
            return;
        }
        for (int i2 = 0; i2 < this.a.length; ++i2) {
            if (this.a[i2] == null) continue;
            try {
                this.a[i2].close();
            }
            catch (Exception exception) {}
            this.a[i2] = null;
        }
        this.n = false;
    }

    public final boolean a(int n2) {
        try {
            return this.a[12].getState() == 400;
        }
        catch (Exception exception) {
            return false;
        }
    }

    public final void a(int n2, boolean bl) {
        try {
            if (n2 == 12) {
                if (!j) {
                    return;
                }
                if (f == 0) {
                    return;
                }
                k = true;
                if (this.a[n2].getState() == 400) {
                    return;
                }
            } else {
                if (!i || !h) {
                    return;
                }
                if (this.o) {
                    return;
                }
                if (e == 0) {
                    return;
                }
                if (n2 >= this.a.length) {
                    switch (n2) {
                        case 7: {
                            n2 = 4;
                            break;
                        }
                        case 8: {
                            n2 = 4;
                            break;
                        }
                        case 9: {
                            n2 = 6;
                            break;
                        }
                        case 10: {
                            return;
                        }
                        case 11: {
                            n2 = 0;
                            break;
                        }
                        case 3: {
                            n2 = 1;
                        }
                    }
                }
                if (n2 >= this.a.length) {
                    return;
                }
                if (this.a[n2] == null) {
                    return;
                }
                boolean bl2 = false;
                for (int i2 = n2; i2 == n2; ++i2) {
                    if (i2 == 12 || this.a[i2].getState() != 400) continue;
                    bl2 = true;
                }
                if (bl2) {
                    return;
                }
            }
            try {
                if (this.a[n2] == null || this.a[n2].getState() == 0) {
                    this.a[n2] = null;
                    this.a[n2] = null;
                    this.d(n2);
                }
            }
            catch (Exception exception) {}
            try {
                if (this.a[n2].getState() != 300) {
                    this.a[n2].prefetch();
                }
            }
            catch (Exception exception) {}
            this.b(n2);
            try {
                this.a[n2].setLoopCount(bl ? -1 : 1);
            }
            catch (Exception exception) {}
            try {
                this.a[n2].start();
                if (n2 != 12) {
                    this.o = true;
                }
            }
            catch (Exception exception) {
                return;
            }
        }
        catch (Exception exception) {}
    }

    public final void b(int n2) {
        if (this.a[n2] == null) {
            try {
                this.a[n2] = (VolumeControl)this.a[n2].getControl("VolumeControl");
            }
            catch (Exception exception) {}
        }
        if (this.a[n2] == null) {
            return;
        }
        try {
            if (n2 != 12) {
                this.a[n2].setLevel(e * 10);
                return;
            }
            this.a[n2].setLevel(f * 10);
        }
        catch (Exception exception) {}
    }

    public final void c(int n2) {
        k = false;
        n2 = 0;
        try {
            n2 = this.a[12].getState() == 400 ? 1 : 0;
        }
        catch (Exception exception) {}
        try {
            if (n2 != 0) {
                this.a[12].stop();
            }
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    public final void h() {
        this.b = this.a;
        n n2 = this.b.a();
        if (n2.a == 0) {
            this.b = this.a;
            this.a.c = this.a.a;
            ((y)this.b.a()).a();
            this.a.d();
            this.a = 1;
        }
    }

    static {
        c = 1;
        c = true;
        d = true;
        e = false;
        a = new int[]{1, 2, 2, 1, 0, 1, 2, 2, 1, 1, 0, 3, 1, 0, 0, 2, 2, 2, 1, 0, 2, 1, 0, 1, 0, 1, 3, 0, 1, 2, 1, 0, 3, 0, 1};
        b = new int[]{2, 10, 20, 30, 39};
        c = new int[]{4, 12, 18, 25, 34};
        d = new int[]{7, 15, 22, 28, 37};
        e = new int[]{0, 2327, 0, 0, 0, 6382, 0, 0, 0, 8759, 0, 0, 7437, 0, 0, 0, 0, 7519, 0, 0, 0, 9983, 0, 0, 0, 0, 1166, 0, 0, 0, 6262, 0, 0, 0, 0};
        h = false;
        e = s.a[2][0][2] / 2;
        i = true;
        f = 4;
        j = true;
        k = false;
    }
}

