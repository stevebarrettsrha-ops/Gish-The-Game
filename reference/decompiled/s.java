/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.bluetooth.ServiceRecord
 *  javax.microedition.io.ConnectionNotFoundException
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 *  javax.microedition.rms.RecordEnumeration
 *  javax.microedition.rms.RecordStore
 */
import com.hardwire.blob.Main;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;
import javax.bluetooth.ServiceRecord;
import javax.microedition.io.ConnectionNotFoundException;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.rms.RecordEnumeration;
import javax.microedition.rms.RecordStore;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class s {
    private Main a;
    private k a;
    private g a;
    private at a;
    public byte a;
    public byte b;
    private int i;
    private Vector a;
    private int j;
    public int a;
    private int k;
    private short[] a;
    private short[] b;
    private byte[] a;
    public int b;
    public int c;
    public int d;
    private byte[] b;
    private boolean a;
    private byte[][] a;
    private boolean b;
    private boolean c;
    private boolean d;
    private boolean e;
    private Image a;
    private Image b;
    private Image c;
    private boolean f;
    private boolean g;
    private boolean h;
    private static char[] a = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', ':', '/', '-', '_', '.'};
    public int e;
    public int f;
    public int g;
    public int h = true;
    private int l = 0;
    private String[] a;
    private byte[] c;
    public byte c;
    private boolean i = 10;
    private long a;
    private int m;
    private j a;
    private String a = "";
    public static ak a;
    private static short[][] a;
    public static final short[][][] a;

    /*
     * WARNING - void declaration
     */
    private void a(byte by, boolean bl, int n2, int n3) {
        boolean bl2;
        void var4_7;
        void var5_8 = var4_7;
        var4_7 = bl2;
        bl2 = bl;
        byte by2 = by;
        s s2 = this;
        this.b = 0;
        s2.j = by2;
        s2.a = 0;
        s2.b = bl2;
        s2.a = false;
        if (a[s2.j] != null) {
            s2.b = new byte[a[s2.j].length];
            s2.d = at.e(s2.a.a(a[s2.j][0]));
        } else {
            s2.d = at.e(0);
        }
        s2.d += -2;
        s2.c = 0;
        s2.b = var5_8 / s2.d;
        s2.k = var4_7;
        int n4 = s2.a();
        s2.a = new byte[n4];
        s2.a = new short[n4];
        s2.b = new short[n4];
        switch (this.j) {
            case 2: {
                this.b[0] = !Main.i ? (byte)0 : (byte)Main.e;
                this.b[1] = !Main.j ? (byte)0 : (byte)Main.f;
                this.b[2] = (byte)(!this.a.g ? 1 : 0);
                this.b[3] = (byte)(!this.a.a.b ? 1 : 0);
                this.b[4] = (byte)this.a.a.g;
            }
        }
        this.k();
    }

    private void c(int n2) {
        this.a = true;
        int n3 = n2;
        switch (this.j) {
            case 2: {
                if (n3-- == 0) {
                    Main.e = this.b[n2];
                    boolean bl = Main.i = Main.e > 0;
                    if (Main.e > 0) {
                        this.a.c(12);
                        this.a.a(2, false);
                    }
                }
                if (n3-- == 0) {
                    Main.j = true;
                    Main.f = this.b[n2];
                    if (Main.f == 0) {
                        this.a.c(12);
                    } else {
                        this.a.b(12);
                        this.a.a(12, true);
                    }
                }
                if (n3 != 0 || !(this.a.g = this.b[n2] == 0)) break;
                this.a.a(70);
            }
        }
    }

    private void h() {
        if (!this.a) {
            return;
        }
        switch (this.j) {
            case 2: {
                Main.e = this.b[0];
                Main.f = this.b[1];
                this.a.g = this.b[2] == 0;
                this.a.a.b = this.b[3] == 0;
                this.a.a.g = this.b[4];
                this.a.a();
                this.a.a = true;
                this.a.n();
                this.q();
                this.a.a = false;
            }
        }
    }

    public final void a(String string, byte[] byArray) {
        try {
            this.a.c("lvl");
            this.a.a.writeUTF(string);
            this.a.a.writeShort(byArray.length);
            this.a.a.write(byArray);
            this.a.a(true);
            this.a((byte)63);
            return;
        }
        catch (Exception exception) {
            this.a((byte)62);
            return;
        }
    }

    /*
     * Unable to fully structure code
     */
    private void i() {
        if (this.a == -1) {
            return;
        }
        if (!this.a.a(12)) {
            this.a.a(3, false);
        }
        block7 : switch (this.a) {
            case 0: {
                switch (s.a[this.j][this.a]) {
                    case 13: 
                    case 124: {
                        this.m();
                        this.a((byte)5);
                        break block7;
                    }
                    case 26: {
                        this.m();
                        this.a((byte)6);
                        break block7;
                    }
                    case 60: {
                        this.m();
                        if (Main.a("score")) {
                            this.a((byte)28);
                            break block7;
                        }
                        this.a((byte)32);
                        break block7;
                    }
                    case 11: {
                        this.m();
                        this.a((byte)3);
                        break block7;
                    }
                    case 10: {
                        this.m();
                        this.a((byte)2);
                        break block7;
                    }
                    case 29: {
                        this.m();
                        this.a((byte)12);
                        break block7;
                    }
                    case 5: {
                        this.m();
                        this.a((byte)49);
                        break block7;
                    }
                    case 123: {
                        s.a = new ak();
                        s.a.a();
                    }
                }
                return;
            }
            case 1: {
                switch (s.a[this.j][this.a]) {
                    case 12: {
                        this.j();
                        break block7;
                    }
                    case 6: {
                        this.m();
                        if (this.a.e == 4 || this.a.e == 5) {
                            this.a((byte)48);
                            break block7;
                        }
                        this.a((byte)13);
                        break block7;
                    }
                    case 46: {
                        this.m();
                        this.a((byte)24);
                        break block7;
                    }
                    case 11: {
                        this.m();
                        this.a((byte)15);
                        break block7;
                    }
                    case 10: {
                        this.m();
                        this.a((byte)18);
                        break block7;
                    }
                    case 29: {
                        this.m();
                        this.a((byte)19);
                        break block7;
                    }
                    case 30: {
                        this.m();
                        this.a((byte)14);
                    }
                }
                return;
            }
            case 5: {
                switch (s.a[this.j][this.a]) {
                    case 106: {
                        try {
                            this.a.a("save");
                            this.a.b = this.a.a.readInt();
                            this.a.h = this.a.a.readInt();
                            this.a.i = this.a.a.readInt();
                            this.a.j = this.a.a.readInt();
                            this.a.b = this.a.a.readBoolean();
                            this.a.a(false);
                        }
                        catch (Exception v0) {}
                        this.a.a((byte)1, (byte)0);
                        break block7;
                    }
                    case 27: {
                        this.m();
                        if (!Main.a("save")) {
                            this.a = (byte)16;
                            this.i();
                            break block7;
                        }
                        this.a((byte)16);
                        break block7;
                    }
                    case 28: {
                        this.m();
                        this.a((byte)4);
                        break block7;
                    }
                    case 74: {
                        this.m();
                        this.a((byte)44);
                        break block7;
                    }
                    case 88: {
                        this.m();
                        this.a((byte)46);
                        break block7;
                    }
                    case 107: {
                        this.m();
                        this.a((byte)61);
                    }
                }
                return;
            }
            case 61: {
                if (this.a == this.a.length - 1) {
                    this.m();
                    this.a((byte)64);
                    return;
                }
                try {
                    var1_1 = RecordStore.openRecordStore((String)"gigolvl", (boolean)false);
                    var2_11 = var1_1.enumerateRecords(null, null, false);
                    for (var3_15 = 0; var3_15 < this.a; ++var3_15) {
                        var2_11.nextRecordId();
                    }
                    var3_16 = new DataInputStream(new ByteArrayInputStream(var2_11.nextRecord()));
                    this.a.a = var3_16.readUTF();
                    this.a.a = new byte[var3_16.readShort()];
                    var3_16.readFully(this.a.a);
                    var3_16.close();
                    var1_1.closeRecordStore();
                    this.a.b = -1;
                    if (!this.a.a((byte)1, (byte)1)) {
                        this.a.d();
                        this.a.a = null;
                        this.a.a = 1;
                        this.a((byte)68);
                        return;
                    }
                    this.o();
                    this.a.removeAllElements();
                    break;
                }
                catch (Exception v1) {
                    return;
                }
            }
            case 64: {
                if (this.a.a.length() == 0) break;
                this.a((byte)66);
                this.a = this.a.a.toString();
                this.q();
                var1_2 = this.a.substring(this.a.lastIndexOf(47) + 1);
                var2_12 = false;
                try {
                    var3_17 = RecordStore.openRecordStore((String)"gigolvl", (boolean)false);
                    var4_21 = var3_17.enumerateRecords(null, null, false);
                    while (var4_21.hasNextElement()) {
                        var5_24 = new DataInputStream(new ByteArrayInputStream(var4_21.nextRecord()));
                        if (var5_24.readUTF().compareTo(var1_2) == 0) {
                            var2_12 = true;
                        }
                        var5_24.close();
                    }
                    var3_17.closeRecordStore();
                }
                catch (Exception v2) {}
                if (var2_12) {
                    this.a((byte)65);
                    return;
                }
                try {
                    if (this.a == null) {
                        this.a = new j(this.a);
                    }
                    var1_3 = true;
                    var2_13 = this.a;
                    var1_4 = this.a;
                    this.a.a = null;
                    var1_4.a = var2_13;
                    var1_4.a = 1;
                    var1_4 = new Thread(var1_4);
                    var1_4.setPriority(10);
                    var1_4.start();
                }
                catch (Exception v3) {}
                System.gc();
                return;
            }
            case 67: {
                try {
                    var3_18 = RecordStore.openRecordStore((String)"gigolvl", (boolean)false);
                    var4_22 = var3_18.enumerateRecords(null, null, false);
                    for (var5_25 = 0; var5_25 < this.a; ++var5_25) {
                        var4_22.nextRecordId();
                    }
                    var3_18.deleteRecord(var4_22.nextRecordId());
                    var3_18.closeRecordStore();
                }
                catch (Exception v4) {}
                this.a((byte)61);
                this.l();
                return;
            }
            case 44: {
                this.a.b = 36 + this.a;
                this.o();
                this.a.removeAllElements();
                this.a.a((byte)1, (byte)1);
                return;
            }
            case 49: {
                this.a.f = true;
                return;
            }
            case 3: 
            case 15: {
                switch (s.a[this.j][this.a]) {
                    case 17: {
                        this.h();
                        this.a.b = this.a.a;
                        var3_19 = this.a.b.a();
                        this.a.b = this.a.a;
                        this.c = this.a;
                        this.m();
                        try {
                            var1_5 = var3_19;
                            if (var1_5.a == 0) {
                                ((y)var3_19).a();
                            }
                        }
                        catch (Exception v5) {}
                        this.d();
                    }
                }
                return;
            }
            case 4: {
                this.a.b = 1 + this.a;
                this.o();
                this.a.removeAllElements();
                this.a.a((byte)1, (byte)1);
                return;
            }
            case 6: {
                switch (s.a[this.j][this.a]) {
                    case 21: {
                        this.m();
                        this.a((byte)9);
                        var1_6 = this.a.a;
                        var1_6.a();
                        var1_6.a = 0;
                        var1_6.b = 0;
                        new Thread(var1_6).start();
                        break block7;
                    }
                    case 20: {
                        this.m();
                        this.a((byte)8);
                        var1_7 = this.a.a;
                        var1_7.a();
                        var1_7.a = 1;
                        var1_7.b = 1;
                        new Thread(var1_7).start();
                    }
                }
                return;
            }
            case 7: {
                this.a.a = true;
                this.a.n();
                var2_14 = this.a;
                var1_8 = this.a.a;
                this.a.a.a = var2_14;
                var1_8.b = (byte)2;
                new Thread(var1_8).start();
                return;
            }
            case 17: {
                switch (s.a[this.j][this.a]) {
                    case 106: {
                        try {
                            this.a.a("msave");
                            this.a.b = this.a.a.readInt();
                            this.a.h = this.a.a.readInt();
                            this.a.i = this.a.a.readInt();
                            this.a.j = this.a.a.readInt();
                            this.a.b = this.a.a.readBoolean();
                            this.a.a(false);
                        }
                        catch (Exception v6) {}
                        this.b((byte)2);
                        break block7;
                    }
                    case 27: {
                        this.m();
                        if (!Main.a("msave")) {
                            this.a = (byte)23;
                            this.i();
                            break block7;
                        }
                        this.a((byte)23);
                        break block7;
                    }
                    case 43: {
                        this.m();
                        this.a((byte)20);
                        break block7;
                    }
                    case 44: {
                        this.m();
                        this.a((byte)21);
                        break block7;
                    }
                    case 45: {
                        this.m();
                        this.a((byte)22);
                        break block7;
                    }
                    case 94: {
                        this.m();
                        this.a((byte)47);
                        break block7;
                    }
                    case 74: {
                        this.m();
                        this.a((byte)45);
                    }
                }
                return;
            }
            case 45: {
                this.a.b = 36 + this.a;
                this.b((byte)3);
                return;
            }
            case 47: {
                this.a.b = 1 + this.a;
                this.b((byte)3);
                return;
            }
            case 20: {
                this.a.b = 41 + this.a;
                this.b((byte)3);
                return;
            }
            case 21: {
                this.a.b = 68 + this.a;
                this.b((byte)4);
                return;
            }
            case 22: {
                this.a.b = 78 + this.a;
                this.b((byte)5);
                return;
            }
            case 23: {
                this.a.d("msave");
                this.a.b = 41;
                this.a.h = 0;
                this.a.i = 0;
                this.a.j = 0;
                this.a.b = false;
                this.b((byte)2);
                return;
            }
            case 24: {
                this.a((byte)17);
                return;
            }
            case 13: {
                if (this.a.b == 2 || this.a.b == 4) {
                    this.a.a = 1;
                }
                this.o();
                this.a.d = (byte)2;
                this.a.a = 0;
                return;
            }
            case 14: 
            case 25: {
                if (this.a.b == 2 || this.a.b == 4) {
                    this.a.a.b();
                }
                this.a.d();
                ** GOTO lbl409
            }
            case 16: {
                this.a.d("save");
                this.a.b = 0;
                this.a.h = 0;
                this.a.i = 0;
                this.a.j = 0;
                this.a.b = false;
                this.o();
                this.a.removeAllElements();
                this.a.a((byte)1, (byte)0);
                return;
            }
            case 26: {
                if (this.a.a.length() == 0) break;
                this.a.a = true;
                this.a.n();
                var3_20 = this.c();
                if (this.a.e == 0) {
                    this.a.d("save");
                } else {
                    this.a.d("msave");
                }
                this.a.a = false;
                this.a.removeAllElements();
                this.a(var3_20 != false ? 30 : 31);
                return;
            }
            case 27: {
                this.a((byte)33);
                this.p();
                System.gc();
                return;
            }
            case 28: {
                this.a((byte)27);
                return;
            }
            case 39: {
                this.a.b = this.a.a;
                var1_9 = var4_23 = this.a.b.a();
                if (var4_23.a == 0) {
                    ((y)var4_23).a();
                }
                this.d();
                return;
            }
            case 37: {
                var1_10 = var5_26 = (ap)this.a.b.a();
                if (var5_26.a[this.a].compareTo(an.a().a(4)) == 0) {
                    this.i = true;
                }
                var5_26.a(this.a);
                this.d();
                return;
            }
            case 40: {
                Main.h = true;
                Main.i = true;
                if (Main.e == 0) {
                    Main.e = 5;
                }
                this.a((byte)50);
                return;
            }
            case 50: {
                Main.j = true;
                if (Main.f == 0) {
                    Main.f = 3;
                }
lbl409:
                // 4 sources

                this.a((byte)0);
            }
        }
    }

    public final void a() {
        this.a.b = 1;
        this.c = 0;
        if (Main.e) {
            this.c = (byte)40;
        } else {
            Main.h = true;
        }
        this.a.b = this.a.a;
        if (this.a.b.c()) {
            n n2;
            n n3 = n2 = this.a.b.a();
            if (n2.a == 0) {
                ((y)n2).b();
            }
            this.d();
            return;
        }
        if (Main.e) {
            this.a((byte)39);
            return;
        }
        if (Main.e) {
            this.a((byte)40);
            return;
        }
        Main.h = true;
        this.a((byte)0);
    }

    private void j() {
        switch (this.a) {
            case 1: {
                this.o();
                this.a.a.c();
                this.a.a = 0;
                this.a.c(12);
                return;
            }
            case 4: 
            case 16: 
            case 44: 
            case 46: {
                this.a((byte)5);
                this.l();
                return;
            }
            case 62: 
            case 63: 
            case 64: 
            case 65: 
            case 66: 
            case 67: 
            case 68: {
                this.a((byte)61);
                this.l();
                return;
            }
            case 61: {
                this.a((byte)5);
                this.l();
                return;
            }
            case 3: {
                this.h();
            }
            case 2: 
            case 5: 
            case 6: 
            case 12: 
            case 49: {
                this.a((byte)0);
                this.l();
                return;
            }
            case 15: {
                this.h();
                this.a.c(12);
            }
            case 13: 
            case 14: 
            case 18: 
            case 19: 
            case 24: 
            case 48: {
                this.a((byte)1);
                this.l();
                return;
            }
            case 7: {
                this.a((byte)6);
                this.l();
                return;
            }
            case 9: {
                r r2 = this.a.a;
                if (r2.a == 0) {
                    try {
                        r2.a.setDiscoverable(0);
                    }
                    catch (Exception exception) {}
                }
                this.a.a.b();
            }
            case 10: 
            case 11: {
                this.a((byte)6);
                this.l();
                return;
            }
            case 17: {
                this.m();
                this.a((byte)25);
                return;
            }
            case 20: 
            case 21: 
            case 22: 
            case 23: 
            case 25: 
            case 45: 
            case 47: {
                this.a((byte)17);
                this.l();
                return;
            }
            case 27: {
                this.a((byte)28);
                return;
            }
            case 28: {
                this.a((byte)0);
                this.l();
                return;
            }
            case 29: {
                this.a((byte)17);
                return;
            }
            case 30: {
                this.a(this.a.b != 1 ? (byte)29 : 28);
                return;
            }
            case 31: {
                this.a(this.a.b != 1 ? (byte)17 : 0);
                return;
            }
            case 32: {
                this.a((byte)0);
                this.l();
                return;
            }
            case 33: 
            case 34: 
            case 35: {
                this.a((byte)28);
                return;
            }
            case 39: {
                this.a(this.c);
                return;
            }
            case 36: {
                ((bh)this.a.b.a()).a();
                this.d();
                return;
            }
            case 37: {
                ((ap)this.a.b.a()).a();
                this.d();
                return;
            }
            case 38: {
                ((ad)this.a.b.a()).a();
                this.d();
                return;
            }
            case 40: {
                Main.h = true;
                Main.i = false;
                Main.e = 0;
                this.a((byte)50);
                return;
            }
            case 50: {
                Main.j = false;
                Main.f = 0;
                this.a((byte)0);
            }
        }
    }

    public final void b() {
        if (this.a == null) {
            this.a = this.a.a(1);
            this.b = this.a.a(0);
        }
    }

    public final void a(byte n2) {
        try {
            this.a.a = false;
            this.o();
            this.c();
            int n3 = this.e - 2;
            int n4 = this.f;
            this.a = n2;
            switch (this.a) {
                case 0: {
                    if (Main.e) {
                        this.q();
                        Main.e = false;
                    }
                    if (ak.a) {
                        this.a((byte)19, false, n3, n4);
                    } else {
                        this.a((byte)0, false, n3, n4);
                    }
                    if (!this.a.a) {
                        this.a.a(12, true);
                    }
                    Main.k = true;
                    break;
                }
                case 1: {
                    this.a((byte)(this.a.b == 1 ? 1 : (this.a.b == 2 ? 8 : 14)), true, n3, n4);
                    break;
                }
                case 5: {
                    this.a(Main.a("save") ? (byte)7 : 6, true, n3, n4);
                    break;
                }
                case 64: {
                    this.b = (byte)2;
                    this.e = true;
                    this.a.a((short)108, this.a, 256);
                    break;
                }
                case 66: {
                    this.a(54, false, true);
                    break;
                }
                case 63: {
                    this.a(109, false, true);
                    break;
                }
                case 62: {
                    this.a(24, false, true);
                    break;
                }
                case 65: {
                    this.a(111, false, true);
                    break;
                }
                case 67: {
                    this.a(119, true, true);
                    break;
                }
                case 68: {
                    this.a(120, false, true);
                    break;
                }
                case 61: {
                    if (Main.a("lvl")) {
                        try {
                            RecordStore recordStore = RecordStore.openRecordStore((String)"gigolvl", (boolean)false);
                            this.a = new byte[recordStore.getNumRecords() + 1][];
                            RecordEnumeration recordEnumeration = recordStore.enumerateRecords(null, null, false);
                            int n5 = 0;
                            while (recordEnumeration.hasNextElement()) {
                                DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(recordEnumeration.nextRecord()));
                                this.a[n5++] = at.a((int)this.a.a(110), dataInputStream.readUTF());
                                dataInputStream.close();
                            }
                            recordStore.closeRecordStore();
                        }
                        catch (Exception exception) {}
                    } else {
                        this.a = new byte[1][];
                    }
                    this.a[this.a.length - 1] = this.a.b(110);
                    this.a((byte)21, true, n3, n4);
                    break;
                }
                case 2: 
                case 18: {
                    byte[][] byArray = this.a.a(this.a.a.a() ? 127 : 1);
                    byte[][] byArray2 = this.a.a(2);
                    byte[][] byArrayArray = new byte[byArray.length + byArray2.length][];
                    System.arraycopy(byArray, 0, byArrayArray, 0, byArray.length);
                    System.arraycopy(byArray2, 0, byArrayArray, byArray.length, byArray2.length);
                    this.a(byArrayArray, false, true);
                    break;
                }
                case 49: {
                    this.a(103, true, true);
                    break;
                }
                case 3: 
                case 15: {
                    this.a((byte)2, true, n3, n4);
                    break;
                }
                case 4: {
                    this.a.a = true;
                    this.a.n();
                    this.a.a("achi");
                    int n6 = this.a.a.readInt();
                    n2 = n6;
                    if (n6 == 0) {
                        this.a.a(false);
                        this.a.a = false;
                        this.a(86, false, true);
                    } else {
                        byte[][] byArrayArray = new byte[6][];
                        byte[][] byArrayArray2 = byArrayArray;
                        byArrayArray[1] = at.a(0, " (");
                        byArrayArray2[3] = new byte[]{at.a(0, '/')};
                        byArrayArray2[5] = new byte[]{at.a(0, ')')};
                        this.a.a.readInt();
                        this.a.a.readInt();
                        this.a = new byte[n2][];
                        for (int i2 = 0; i2 < this.a.length; ++i2) {
                            int n7 = 0;
                            for (n2 = 0; n2 < Main.a[i2]; ++n2) {
                                if (!this.a.a.readBoolean()) continue;
                                ++n7;
                            }
                            if (Main.a[i2] == 0) {
                                this.a[i2] = this.a.a(i2 + 1);
                                continue;
                            }
                            byArrayArray2[0] = this.a.a(i2 + 1);
                            byArrayArray2[2] = at.a(0, n7);
                            byArrayArray2[4] = at.a(0, Main.a[i2]);
                            this.a[i2] = at.a(byArrayArray2);
                        }
                        this.a.a(false);
                        this.a.a = false;
                        this.a((byte)3, true, n3, n4);
                    }
                    if (!this.a.a) {
                        this.a.a(12, true);
                    }
                    Main.k = true;
                    break;
                }
                case 44: 
                case 45: {
                    int n8;
                    this.a.a = true;
                    this.a.n();
                    this.a.a("achi");
                    this.a.a.readInt();
                    this.a.a.readInt();
                    n2 = this.a.a.readInt();
                    this.a.a(false);
                    this.a.a = false;
                    int n9 = 0;
                    for (n8 = 0; n8 < Main.b.length; ++n8) {
                        if (n2 < Main.b[n8]) continue;
                        n9 = n8 + 1;
                    }
                    if (n9 == 0) {
                        this.a(87, false, true);
                        break;
                    }
                    this.a = new byte[n9][];
                    for (n8 = 0; n8 < n9; ++n8) {
                        this.a[n8] = this.a.a(n8 + 36);
                    }
                    this.a((byte)17, true, n3, n4);
                    break;
                }
                case 46: {
                    int n10;
                    this.a.a = true;
                    this.a.n();
                    this.a.a("achi");
                    this.a.a.readInt();
                    this.a.a.readInt();
                    this.a.a.readInt();
                    for (n2 = 0; n2 < Main.a.length; n2 = (int)(n2 + 1)) {
                        for (n10 = 0; n10 < Main.a[n2]; ++n10) {
                            this.a.a.readBoolean();
                        }
                    }
                    byte[][] byArrayArray = new byte[Main.a.length][];
                    n10 = 0;
                    for (int i3 = 0; i3 < Main.a.length; ++i3) {
                        boolean bl = this.a.a.readBoolean();
                        if (!bl) continue;
                        byArrayArray[n10++] = at.a(3, Main.e[i3]);
                    }
                    this.a.a(false);
                    this.a.a = false;
                    if (n10 == 0) {
                        this.a(96, false, true);
                        break;
                    }
                    byte[][] byArray = this.a.a(89);
                    byte[][] byArrayArray3 = new byte[byArray.length + n10][];
                    System.arraycopy(byArray, 0, byArrayArray3, 0, byArray.length);
                    System.arraycopy(byArrayArray, 0, byArrayArray3, byArray.length, n10);
                    this.a(byArrayArray3, false, true);
                    break;
                }
                case 6: {
                    this.a((byte)4, true, n3, n4);
                    break;
                }
                case 7: {
                    this.a = new byte[this.a.length][];
                    for (n2 = 0; n2 < this.a.length; n2 = (int)(n2 + 1)) {
                        this.a[n2] = at.a(0, this.a[n2]);
                    }
                    this.a((byte)5, true, n3, n4);
                    break;
                }
                case 8: {
                    this.a(22, false, false);
                    break;
                }
                case 9: {
                    this.a(23, false, true);
                    break;
                }
                case 10: {
                    this.a(24, false, true);
                    break;
                }
                case 48: {
                    this.a(102, false, true);
                    break;
                }
                case 11: {
                    this.a(25, false, true);
                    break;
                }
                case 17: {
                    this.a(Main.a("msave") ? (byte)10 : 9, true, n3, n4);
                    break;
                }
                case 47: {
                    this.a.a = true;
                    this.a.n();
                    this.a.a("achi");
                    n2 = this.a.a.readInt();
                    this.a.a(false);
                    this.a.a = false;
                    if (n2 == 0) {
                        this.a(86, false, true);
                        break;
                    }
                    byte[][] byArrayArray = new byte[n2][];
                    int n11 = 0;
                    for (int i4 = 0; i4 < n2; ++i4) {
                        byArrayArray[n11++] = this.a.a(i4 + 1);
                    }
                    this.a = new byte[n11][];
                    System.arraycopy(byArrayArray, 0, this.a, 0, n11);
                    this.a((byte)18, true, n3, n4);
                    break;
                }
                case 20: {
                    this.a.a = true;
                    this.a.n();
                    this.a.a("achi");
                    this.a.a.readInt();
                    n2 = this.a.a.readInt();
                    this.a.a(false);
                    this.a.a = false;
                    if (n2 == 0) {
                        this.a(95, false, true);
                        break;
                    }
                    this.a = new byte[n2][];
                    for (int i5 = 0; i5 < n2; ++i5) {
                        this.a[i5] = this.a.a(i5 + 41);
                    }
                    this.a((byte)13, true, n3, n4);
                    break;
                }
                case 21: {
                    int n12;
                    this.a.a = true;
                    this.a.n();
                    this.a.a("achi");
                    this.a.a.readInt();
                    this.a.a.readInt();
                    n2 = this.a.a.readInt();
                    this.a.a(false);
                    this.a.a = false;
                    int n13 = 0;
                    for (n12 = 0; n12 < Main.c.length; ++n12) {
                        if (n2 < Main.c[n12]) continue;
                        n13 = n12 + 1;
                    }
                    this.a = new byte[n13 += 5][];
                    for (n12 = 0; n12 < n13; ++n12) {
                        this.a[n12] = this.a.a(n12 + 68);
                    }
                    this.a((byte)11, true, n3, n4);
                    break;
                }
                case 22: {
                    int n14;
                    this.a.a = true;
                    this.a.n();
                    this.a.a("achi");
                    this.a.a.readInt();
                    this.a.a.readInt();
                    n2 = this.a.a.readInt();
                    this.a.a(false);
                    this.a.a = false;
                    int n15 = 0;
                    for (n14 = 0; n14 < Main.d.length; ++n14) {
                        if (n2 < Main.d[n14]) continue;
                        n15 = n14 + 1;
                    }
                    this.a = new byte[n15 += 5][];
                    for (n14 = 0; n14 < n15; ++n14) {
                        this.a[n14] = this.a.a(n14 + 78);
                    }
                    this.a((byte)12, true, n3, n4);
                    break;
                }
                case 23: {
                    this.a(32, true, true);
                    break;
                }
                case 24: {
                    this.a(31, true, true);
                    break;
                }
                case 25: {
                    this.a(47, true, true);
                    break;
                }
                case 12: 
                case 19: {
                    this.a(0, false, true);
                    break;
                }
                case 13: {
                    this.a(125, true, true);
                    break;
                }
                case 14: {
                    this.a(31, true, true);
                    break;
                }
                case 16: {
                    this.a(32, true, true);
                    break;
                }
                case 26: {
                    this.b = (byte)2;
                    this.e = false;
                    this.a.a((short)56, "", 11);
                    break;
                }
                case 27: {
                    this.a(53, true, true);
                    break;
                }
                case 30: {
                    this.a(59, false, true);
                    break;
                }
                case 31: {
                    this.a(58, false, true);
                    break;
                }
                case 32: {
                    this.a(61, false, true);
                    break;
                }
                case 33: {
                    this.a(54, false, true);
                    break;
                }
                case 34: {
                    this.a(24, false, true);
                    break;
                }
                case 35: {
                    this.a = null;
                    this.a(55, false, true);
                    break;
                }
                case 28: 
                case 29: {
                    this.a.a = true;
                    this.a.n();
                    byte[][] byArray = this.a();
                    if (this.c == null) {
                        this.c = this.a.b(52);
                    }
                    if (this.h + this.f + this.c.getHeight() > g.b) {
                        this.f = g.b - this.h - this.c.getHeight();
                    }
                    this.a.a = false;
                    this.a(byArray, this.a == 28, true);
                    break;
                }
                case 39: {
                    this.a(16, true, true);
                    break;
                }
                case 36: {
                    bh bh2 = (bh)this.a.b.a();
                    byte by = this.a.a(0);
                    bh bh3 = bh2;
                    this.a(at.a(by, at.a((int)by, bh3.a), at.a()), false, true);
                    break;
                }
                case 37: {
                    ap ap2;
                    ap ap3 = ap2 = (ap)this.a.b.a();
                    String[] stringArray = ap2.a;
                    this.a = new byte[stringArray.length][];
                    for (int i6 = 0; i6 < stringArray.length; ++i6) {
                        this.a[i6] = at.a(0, stringArray[i6]);
                    }
                    this.a((byte)15, true, n3, n4);
                    break;
                }
                case 38: {
                    ad ad2 = (ad)this.a.b.a();
                    byte by = this.a.a(0);
                    ad ad3 = ad2;
                    this.a(at.a(by, at.a((int)by, ad3.a), at.a()), false, ad2.a());
                    break;
                }
                case 40: {
                    this.a(19, true, true);
                    break;
                }
                case 50: {
                    this.a(104, true, true);
                }
            }
            this.n();
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    private void b(byte by) {
        this.a.a = true;
        this.a.n();
        if (this.a.a != null) {
            this.a.a = true;
            this.a.a.a(new byte[]{2});
            this.a.d();
            try {
                Thread.sleep(500L);
            }
            catch (InterruptedException interruptedException) {}
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        try {
            dataOutputStream.writeShort(this.a.b);
            dataOutputStream.writeByte(by);
        }
        catch (IOException iOException) {
            this.a((byte)10);
            this.a.a = false;
            return;
        }
        if (!this.a.a.a(byteArrayOutputStream.toByteArray())) {
            this.a((byte)10);
        } else {
            try {
                this.o();
                this.a.removeAllElements();
                this.a.e[1] = 0;
                this.a.e[0] = 0;
                this.a.a((byte)2, by);
            }
            catch (RuntimeException runtimeException) {
                this.a.a.b();
                this.a((byte)10);
            }
        }
        this.a.a = false;
    }

    public final void a(boolean bl) {
        String[] stringArray;
        if (bl) {
            this.a((byte)10);
            return;
        }
        r r2 = this.a.a;
        if (r2.a == null) {
            stringArray = new String[]{};
        } else {
            int n2 = r2.a.size();
            String[] stringArray2 = new String[n2];
            for (int i2 = 0; i2 < n2; ++i2) {
                ServiceRecord serviceRecord = (ServiceRecord)r2.a.elementAt(i2);
                serviceRecord = serviceRecord.getAttributeValue(256);
                stringArray2[i2] = (String)serviceRecord.getValue();
            }
            stringArray = this.a = stringArray2;
        }
        if (this.a.length == 0) {
            this.a((byte)11);
            return;
        }
        this.a((byte)7);
    }

    public final void b(boolean bl) {
        if (bl) {
            this.a((byte)10);
        }
    }

    public final void c(boolean bl) {
        if (bl) {
            this.a((byte)10);
            return;
        }
        while (!this.a.a.a()) {
            try {
                this.a();
                Thread.sleep(1L);
            }
            catch (InterruptedException interruptedException) {}
        }
        if (this.a.a.a) {
            this.a((byte)6);
            return;
        }
        InputStream inputStream = this.a.a.a();
        if (inputStream == null) {
            this.a((byte)10);
            return;
        }
        try {
            inputStream = new DataInputStream(inputStream);
            this.a.b = ((DataInputStream)inputStream).readShort();
            byte by = ((DataInputStream)inputStream).readByte();
            this.o();
            this.a.removeAllElements();
            this.a.e[1] = 0;
            this.a.e[0] = 0;
            this.a.a((byte)4, by);
            return;
        }
        catch (Exception exception) {
            this.a.a.b();
            this.a((byte)10);
            return;
        }
    }

    public final void d(boolean bl) {
        if (bl) {
            this.a.a = false;
            this.a((byte)10);
            return;
        }
        this.a((byte)17);
    }

    private void a(int n2, boolean bl, boolean bl2) {
        this.b = 1;
        this.c = bl;
        this.d = bl2;
        this.a.a(this.a.a(n2), 3, 3, -11579569, this.f);
        this.a.repaint();
    }

    private void a(byte[][] byArray, boolean bl, boolean bl2) {
        this.b = 1;
        this.c = bl;
        this.d = bl2;
        this.a.a(byArray, 3, 3, -11579569, this.f);
        this.a.repaint();
    }

    public final int a() {
        if (a[this.j] != null) {
            return a[this.j].length;
        }
        return this.a.length;
    }

    private int a(int n2, int n3) {
        int n4 = 0;
        if (a[n2] != null) {
            byte by = this.a.a(a[n2][n3]);
            n4 = this.a.f(a[n2][n3]);
            if (a[n2] != null && a[n2][n3] != null) {
                n4 = a[n2][n3][0] < 0 ? (n4 += at.a((int)by, at.a((int)by, String.valueOf(this.b[n3])))) : (n4 += at.a((int)by, this.a.b(a[n2][n3][this.b[n3]])));
            }
        } else {
            n4 = at.a(0, this.a[n3]);
        }
        return n4;
    }

    private void k() {
        int n2 = this.a.length;
        for (int i2 = 0; i2 < n2; ++i2) {
            int n3 = this.a(this.j, i2);
            this.a[i2] = n3 > this.k ? (byte)1 : 0;
            this.b[i2] = (short)(n3 - this.k);
        }
    }

    private void l() {
        int n2 = this.a.size();
        if (n2 > 0) {
            this.a = ((int[])this.a.elementAt(n2 - 1))[0];
            this.a.removeElementAt(n2 - 1);
            if (this.a < 0) {
                this.a = 0;
            }
            if (this.a > (n2 = this.a()) - 1) {
                this.a = 0;
            }
            if (this.a < this.c) {
                this.c = this.a;
                return;
            }
            if (this.a >= this.c + this.b) {
                this.c = this.a - this.b + 1;
            }
        }
    }

    private void m() {
        this.a.addElement(new int[]{this.a});
        if (this.a.size() > this.i) {
            this.a.removeElementAt(0);
        }
    }

    public s(Main main) {
        this.a = main;
        this.a = this.a.a;
        this.a = this.a.a;
        this.a = this.a.a;
        this.a = (byte)-1;
        this.a = new Vector(10);
        this.c = at.a(0, "hardwirerockshard");
        int n2 = 0;
        while (n2 < this.c.length) {
            int n3 = n2++;
            this.c[n3] = (byte)(this.c[n3] + 1);
        }
        ak.a(this.a);
    }

    public final void c() {
        if (g.b >= 300) {
            this.h = true;
            this.e = g.a - 70;
            this.f = g.b - 110;
            this.g = 35;
            this.h = 55;
        } else {
            this.h = false;
            this.g = 35;
            this.h = 18;
            this.e = g.a - 70;
            this.f = g.b - this.h - this.a.a(0).getHeight();
            if (this.a.a == 0 && this.a.c == 7) {
                this.h += 20;
            }
        }
        if (this.h) {
            if (this.a.a == 0 && this.a.c == 7) {
                this.h += 40;
                return;
            }
            this.h += 50;
            this.f -= 50;
        }
    }

    private void n() {
        int n2;
        int n3 = this.f;
        int n4 = this.e;
        if (this.b == 1) {
            n3 = this.a.a.length * (at.e(this.a.a(1)) + 1);
        } else if (this.b == 0) {
            n2 = this.a();
            n3 = n2 < this.b ? n2 : this.b;
            n3 = n3 * this.d;
            n4 = 0;
            for (int i2 = 0; i2 < n2; ++i2) {
                int n5 = this.a(this.j, i2);
                if (n5 <= n4) continue;
                n4 = n5;
            }
        } else if (this.b == 2) {
            n3 = 2 * (at.e(0) + 1);
        }
        n2 = this.e - n4;
        if (n2 > 0) {
            this.g += n2 >> 1;
            this.e -= n2;
        }
        if ((n2 = this.f - n3) > 0) {
            this.h += n2 >> 1;
            this.f -= n2;
        }
    }

    public final void a(Graphics graphics) {
        graphics.setColor(0);
        graphics.fillRect(0, 0, g.a, g.b);
        if (this.a.a != 0 || this.a.c != 7) {
            graphics.drawImage(this.a.a(242), 10, 50, 20);
            graphics.drawImage(this.a.a(242), 50, 100, 20);
            graphics.drawImage(this.a.a(242), g.a - 40, 70, 24);
            graphics.drawImage(this.a.a(242), g.a - 100, 130, 24);
            graphics.drawImage(this.a.a(242), g.a - 20, g.b - 40, 40);
            graphics.drawImage(this.a.a(242), 30, g.b - 40, 36);
            graphics.drawImage(this.a.a(243), g.a - 40, g.b - 10, 40);
            graphics.drawImage(this.a.a(243), 70, 70, 24);
        }
        graphics.drawImage(this.a.a(238), 0, 0, 20);
        graphics.drawImage(this.a.a(239), g.a, 0, 24);
        graphics.drawImage(this.a.a(240), g.a, g.b, 40);
        graphics.drawImage(this.a.a(241), 0, g.b, 36);
        if (this.b == 2) {
            return;
        }
        if (this.h && (this.a.a != 0 || this.a.a.c == 7)) {
            graphics.drawImage(this.a.a(244), g.c, 20, 17);
        }
    }

    private void o() {
        this.b = null;
        this.a = null;
        this.a = null;
        this.a = null;
        this.b = null;
    }

    public final boolean a() {
        try {
            if (this.g) {
                this.j();
            } else if (this.f) {
                this.i();
            }
            if (this.g || this.f) {
                this.f = false;
                this.g = false;
                return true;
            }
            if (this.a.b != null) {
                n n2;
                n n3 = n2 = this.a.b.a();
                if (n2.a == 2) {
                    n3 = (ad)n2;
                    if (((ad)n3).a) {
                        ((ad)n2).b();
                        this.d();
                    }
                }
            }
            if (this.b == 1) {
                this.a.i();
                if (this.a != 38) {
                    return false;
                }
            }
        }
        catch (Exception exception) {}
        return true;
    }

    public final void d() {
        n n2;
        Main.c = true;
        n n3 = n2 = this.a.b.a();
        switch (n2.a) {
            case 3: {
                this.a((byte)36);
                this.a.n();
                long l2 = System.currentTimeMillis();
                while (this.a.b.a() == n2 && System.currentTimeMillis() - l2 < 2000L) {
                    try {
                        this.a();
                        Thread.sleep(1L);
                    }
                    catch (InterruptedException interruptedException) {}
                }
                if (this.a.b.a() != n2) break;
                this.j();
                return;
            }
            case 2: {
                Main.c = false;
                this.m = 1;
                this.a = System.currentTimeMillis();
                this.a((byte)38);
                return;
            }
            case 1: {
                this.a((byte)37);
                return;
            }
            default: {
                this.i = false;
                this.a.a = this.a.b;
                if (this.a.a == 1) {
                    this.a(this.c);
                    this.l();
                    return;
                }
                if (this.a.a != 0 || this.a.c != 6) break;
                this.a.c = 0;
                this.a.n();
                this.a.c = (byte)6;
            }
        }
    }

    public final boolean b() {
        boolean bl = false;
        try {
            int n2;
            int[] nArray = new int[]{0, 0, 0, 0};
            int[] nArray2 = new int[]{0, 0, 0, 0};
            if (Main.a("score")) {
                this.a.a("score");
                for (n2 = 0; n2 < 4; ++n2) {
                    this.a.a.readUTF();
                    nArray[n2] = this.a.a.readInt();
                    nArray2[n2] = this.a.a.readInt();
                    this.a.a.readInt();
                    this.a.a.readBoolean();
                }
                this.a.a(false);
            }
            n2 = 0;
            if (this.a.b != 1) {
                n2 = 2;
            }
            if (nArray2[n2 + 1] == 0 || this.a.h > nArray[n2 + 0] || this.a.i < nArray2[n2 + 1]) {
                bl = true;
            }
        }
        catch (Exception exception) {}
        return bl;
    }

    private boolean c() {
        boolean bl = false;
        try {
            int n2;
            String[] stringArray = new String[]{"", "", "", ""};
            int[] nArray = new int[]{0, 0, 0, 0};
            int[] nArray2 = new int[]{0, 0, 0, 0};
            int[] nArray3 = new int[]{0, 0, 0, 0};
            boolean[] blArray = new boolean[]{false, false, false, false};
            if (Main.a("score")) {
                this.a.a("score");
                for (n2 = 0; n2 < 4; ++n2) {
                    stringArray[n2] = this.a.a.readUTF();
                    nArray[n2] = this.a.a.readInt();
                    nArray2[n2] = this.a.a.readInt();
                    nArray3[n2] = this.a.a.readInt();
                    blArray[n2] = this.a.a.readBoolean();
                }
                this.a.a(false);
            }
            n2 = 0;
            if (this.a.b != 1) {
                n2 = 2;
            }
            if (this.a.h > nArray[n2 + 0]) {
                bl = true;
                stringArray[n2 + 0] = this.a.a.toString();
                nArray[n2 + 0] = this.a.h;
                nArray2[n2 + 0] = this.a.i;
                nArray3[n2 + 0] = this.a.j;
                blArray[n2 + 0] = this.a.b;
            }
            if (nArray2[n2 + 1] == 0 || this.a.i < nArray2[n2 + 1]) {
                bl = true;
                stringArray[n2 + 1] = this.a.a.toString();
                nArray[n2 + 1] = this.a.h;
                nArray2[n2 + 1] = this.a.i;
                nArray3[n2 + 1] = this.a.j;
                blArray[n2 + 1] = this.a.b;
            }
            this.a.b("score");
            for (n2 = 0; n2 < 4; ++n2) {
                this.a.a.writeUTF(stringArray[n2]);
                this.a.a.writeInt(nArray[n2]);
                this.a.a.writeInt(nArray2[n2]);
                this.a.a.writeInt(nArray3[n2]);
                this.a.a.writeBoolean(blArray[n2]);
            }
            this.a.a(true);
        }
        catch (Exception exception) {}
        return bl;
    }

    private byte[][] a() {
        Object object = null;
        this.a.a("score");
        object = at.a(3, "^");
        byte[] byArray = at.a(3, "~");
        byte[] byArray2 = at.a(3, " ");
        String[] stringArray = new String[4];
        int[] nArray = new int[4];
        int[] nArray2 = new int[4];
        try {
            for (int i2 = 0; i2 < 4; ++i2) {
                stringArray[i2] = this.a.a.readUTF();
                nArray[i2] = this.a.a.readInt();
                nArray2[i2] = this.a.a.readInt();
                this.a.a.readInt();
                this.a.a.readBoolean();
            }
        }
        catch (Exception exception) {}
        byte[] byArray3 = new byte[]{};
        byte[][] byArrayArray = new byte[36][];
        byte[][] byArrayArray2 = byArrayArray;
        byArrayArray[0] = object;
        byArrayArray2[1] = this.a.b(13);
        byArrayArray2[2] = byArray;
        byArrayArray2[3] = this.a.b(57);
        byArrayArray2[4] = nArray[0] == 0 ? byArray3 : at.a(3, stringArray[0]);
        byArrayArray2[5] = byArray;
        byArrayArray2[6] = this.a.b(37);
        byArrayArray2[7] = byArray2;
        byArrayArray2[8] = nArray[0] == 0 ? byArray3 : at.a(3, nArray[0]);
        byArrayArray2[9] = byArray;
        byArrayArray2[10] = byArray;
        byArrayArray2[11] = this.a.b(57);
        byArrayArray2[12] = nArray2[1] == 0 ? byArray3 : at.a(3, stringArray[1]);
        byArrayArray2[13] = byArray;
        byArrayArray2[14] = this.a.b(38);
        byArrayArray2[15] = byArray2;
        byArrayArray2[16] = nArray2[1] == 0 ? byArray3 : ab.a(nArray2[1]);
        byArrayArray2[17] = byArray;
        byArrayArray2[18] = byArray;
        byArrayArray2[19] = object;
        byArrayArray2[20] = this.a.b(26);
        byArrayArray2[21] = byArray;
        byArrayArray2[22] = this.a.b(57);
        byArrayArray2[23] = nArray[2] == 0 ? byArray3 : at.a(3, stringArray[2]);
        byArrayArray2[24] = byArray;
        byArrayArray2[25] = this.a.b(37);
        byArrayArray2[26] = byArray2;
        byArrayArray2[27] = nArray[2] == 0 ? byArray3 : at.a(3, nArray[2]);
        byArrayArray2[28] = byArray;
        byArrayArray2[29] = byArray;
        byArrayArray2[30] = this.a.b(57);
        byArrayArray2[31] = nArray2[3] == 0 ? byArray3 : at.a(3, stringArray[3]);
        byArrayArray2[32] = byArray;
        byArrayArray2[33] = this.a.b(38);
        byArrayArray2[34] = byArray2;
        byArrayArray2[35] = nArray2[3] == 0 ? byArray3 : ab.a(nArray2[3]);
        this.a.a(false);
        object = at.a(3, at.a(byArrayArray2), g.a - 10);
        System.gc();
        return object;
    }

    private void p() {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            Object object = new DataOutputStream(byteArrayOutputStream);
            int n2 = 0;
            int n3 = 0;
            this.a.a("score");
            ((FilterOutputStream)object).write((System.getProperty("microedition.platform") + "\n").getBytes());
            for (int i2 = 0; i2 < 4; ++i2) {
                int n4;
                String string = this.a.a.readUTF();
                string = string + "\n";
                for (n4 = 0; n4 < string.length(); ++n4) {
                    n3 += string.charAt(n4);
                }
                ((FilterOutputStream)object).write(string.getBytes());
                n4 = this.a.a.readInt();
                n3 += n4;
                int n5 = this.a.a.readInt();
                int n6 = this.a(n2);
                n2 = n6;
                n2 = this.a(n2);
                ((DataOutputStream)object).writeInt((n4 + this.c[n6]) * this.c[n2]);
                n4 = this.a.a.readInt();
                boolean bl = this.a.a.readBoolean();
                int n7 = this.a(n2);
                n2 = n7;
                n2 = this.a(n2);
                ((DataOutputStream)object).writeInt((n5 - this.c[n7]) * this.c[n2]);
                int n8 = this.a(n2);
                n2 = n8;
                n2 = this.a(n2);
                ((DataOutputStream)object).writeInt((n4 + this.c[n8]) * this.c[n2]);
                n3 += n5 / 1000;
                ((DataOutputStream)object).writeInt(bl ? 1 : 0);
                n3 += n4;
            }
            int n9 = this.a(n2);
            n2 = n9;
            int n10 = this.a(n2);
            n2 = n10;
            int n11 = this.a(n2);
            n2 = n11;
            n2 = this.a(n2);
            ((DataOutputStream)object).writeInt((int)((long)n3 * (long)this.c[n9] % (long)(this.c[n10] * this.c[n11] * this.c[n2] * this.c[this.a(n2)])));
            ((DataOutputStream)object).writeByte(0);
            this.a.a(false);
            if (this.a == null) {
                this.a = new j(this.a);
            }
            boolean bl = false;
            object = byteArrayOutputStream.toByteArray();
            Runnable runnable = null;
            runnable = this.a;
            this.a.a = (byte[])object;
            ((j)runnable).a = null;
            ((j)runnable).a = 0;
            runnable = new Thread(runnable);
            ((Thread)runnable).setPriority(10);
            ((Thread)runnable).start();
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    private int a(int n2) {
        if (++n2 == this.c.length) {
            n2 = 0;
        }
        return n2;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final void b(Graphics graphics) {
        try {
            ++this.l;
            this.a(graphics);
            switch (this.b) {
                case 1: {
                    this.a.b(graphics, this.g, this.h, this.f, this.g + this.e - 2);
                    if (this.a == 38) {
                        int n2;
                        if (System.currentTimeMillis() - this.a >= 1000L) {
                            ++this.m;
                            if (this.m > 4) {
                                this.m = 0;
                            }
                            this.a = System.currentTimeMillis();
                        }
                        String string = "";
                        for (n2 = 0; n2 < this.m; ++n2) {
                            string = string + '.';
                        }
                        n2 = this.g;
                        int n3 = this.h + this.a.a.length * (3 + at.e(1));
                        this.a.a(graphics, 3, string, n2, n3, 20);
                    }
                    if (this.c) {
                        if (this.a == 28 || this.a == 29) {
                            graphics.drawImage(this.c, 0, g.b, 36);
                        } else {
                            graphics.drawImage(this.b, 0, g.b, 36);
                        }
                    }
                    if (!this.d) return;
                    graphics.drawImage(this.a, g.a, g.b, 40);
                    return;
                }
                case 2: {
                    int n4 = this.h;
                    int n5 = at.a(0, "m") + 2;
                    int n6 = a.length / (g.a / n5) + 2;
                    if (n4 < (n6 *= at.e(0))) {
                        n4 = n6;
                    }
                    this.a.getWidth();
                    this.a.a(graphics, this.g + 1, n4, -11579569, 0);
                    graphics.drawImage(this.b, 0, g.b, 36);
                    if (this.e) {
                        graphics.drawImage(this.a, g.a, g.b, 40);
                    }
                    n6 = 0;
                    n4 = 0;
                    for (int i2 = 0; i2 < a.length; ++i2) {
                        this.a.a(graphics, 0, String.valueOf(a[i2]), n6 + (n5 >> 1), n4, 17);
                        if ((n6 += n5) + n5 <= g.a) continue;
                        n6 = 0;
                        n4 += at.e(0);
                    }
                    n4 = a.length / (g.a / n5) + 1;
                    this.a.a(graphics, 0, "del", 1, n4 * at.e(0), 0);
                    return;
                }
                case 0: {
                    int n7;
                    int n8;
                    int n9;
                    int n10;
                    int n11;
                    int n12 = this.a();
                    int n13 = n11 = n12 < this.b ? n12 : this.b;
                    if (this.a != null) {
                        n10 = this.h + 1 + (this.f >> 1) - (n11 * this.d >> 1);
                        for (n9 = this.c; n9 < this.c + n11; ++n9) {
                            int n14 = n10 + (n9 - this.c) * this.d;
                            int n15 = g.c - (at.a(0, this.a[n9]) >> 1);
                            if (this.a[n9] != 0) {
                                n15 = g.c - (this.k >> 1) - this.a[n9];
                            }
                            n8 = g.c - (this.k >> 1);
                            n7 = g.c + (this.k >> 1);
                            int n16 = 0;
                            n16 = n9 == this.a ? 0 : 2;
                            at.a(graphics, n16, this.a[n9], n15, n14, n8, n7, 0);
                        }
                    } else {
                        n10 = this.h + 1 + (this.f >> 1) - (n11 * this.d >> 1);
                        for (n9 = this.c; n9 < this.c + n11; ++n9) {
                            int n17 = n10 + (n9 - this.c) * this.d;
                            n7 = this.a.a(a[this.j][n9]);
                            n8 = this.a.f(a[this.j][n9]);
                            byte[] byArray = null;
                            if (a[this.j] != null && a[this.j][n9] != null) {
                                byArray = a[this.j][n9][0] < 0 ? at.a(n7, String.valueOf(this.b[n9])) : this.a.b(a[this.j][n9][this.b[n9]]);
                                n8 += at.a(n7, byArray);
                            }
                            int n18 = g.c - (n8 >> 1);
                            if (this.a[n9] != 0) {
                                n18 = g.c - (this.k >> 1) - this.a[n9];
                            }
                            n7 = g.c - (this.k >> 1);
                            int n19 = g.c + (this.k >> 1);
                            int n20 = 0;
                            n20 = n9 == this.a ? 0 : 2;
                            at.a(graphics, n20, this.a.b(a[this.j][n9]), n18, n17, n7, n19, 0);
                            if (byArray == null) continue;
                            at.a(graphics, n20, byArray, n18 + n8, n17, n7, n19, 8);
                            if (n9 != this.a) continue;
                            int n21 = n17;
                            n7 = a[this.j][n9][0] < 0 && this.b[this.a] < a[this.j][n9][2] || a[this.j][n9][0] > 0 && (this.b[n9] < a[this.j][n9].length - 1 || a[this.j][n9].length == 2) ? 1 : 0;
                            n8 = a[this.j][n9][0] < 0 && this.b[this.a] > a[this.j][n9][1] || a[this.j][n9][0] > 0 && (this.b[n9] > 0 || a[this.j][n9].length == 2) ? 1 : 0;
                            Graphics graphics2 = graphics;
                            s s2 = this;
                            if (n8 == 0 && n7 == 0) continue;
                            ++n21;
                            n19 = s2.d >> 1;
                            n20 = 0;
                            n20 = s2.l % 6;
                            if (n20 > 3) {
                                n20 = 6 - n20;
                            }
                            if (n8 != 0) {
                                graphics2.drawImage(s2.a.a(231), n20 + 1, n21 + n19, 6);
                            }
                            if (n7 == 0) continue;
                            graphics2.drawImage(s2.a.a(232), g.a - 1 - n20, n21 + n19, 10);
                        }
                    }
                    for (n10 = this.c; n10 < this.c + n11; ++n10) {
                        if (this.a[n10] == 0) continue;
                        if (this.a[n10] > 0) {
                            int n22 = n10;
                            this.a[n22] = (short)(this.a[n22] + 2);
                            if (this.a[n10] <= this.b[n10]) continue;
                            this.a[n10] = this.b[n10];
                            this.a[n10] = -1;
                            continue;
                        }
                        int n23 = n10;
                        this.a[n23] = (short)(this.a[n23] - 2);
                        if (this.a[n10] >= 0) continue;
                        this.a[n10] = 0;
                        this.a[n10] = 1;
                    }
                    if (this.a != -1 && (a[this.j] == null || a[this.j][this.a] == null)) {
                        graphics.drawImage(this.b, 0, g.b, 36);
                    }
                    if (this.b) {
                        graphics.drawImage(this.a, g.a, g.b, 40);
                    }
                    if (n12 <= this.b) return;
                    if (this.c > 0) {
                        graphics.drawImage(this.a.a(1000), g.c, g.b, 33);
                    }
                    if (this.c + this.b >= this.a()) return;
                    graphics.drawImage(this.a.a(1001), g.c, g.b, 33);
                }
                default: {
                    return;
                }
            }
        }
        catch (Exception exception) {}
    }

    public final void a(int n2, int n3) {
        if ((this.a == 28 || this.a == 29) && n2 < this.c.getWidth() && n3 > g.b - this.c.getHeight()) {
            this.a(-6);
            return;
        }
        if (n2 < this.a.getWidth() && n3 > g.b - this.b.getHeight()) {
            this.a(-6);
            return;
        }
        if (n2 >= g.a - this.a.getWidth() && n3 > g.b - this.a.getHeight()) {
            this.a(-7);
            return;
        }
        if (this.b == 1 || this.b == 0) {
            this.a.d = true;
            this.a.e = false;
            this.a.i = n3;
            if (this.b == 0) {
                this.a.j = this.c;
                return;
            }
            if (this.b == 1) {
                this.a.j = this.a.f;
            }
        }
    }

    public final void b(int n2, int n3) {
        block10: {
            if (this.b == 0) {
                int n4 = this.a();
                int n5 = n4 < this.b ? n4 : this.b;
                n4 = this.h + 1 + (this.f >> 1) - (n5 * this.d >> 1);
                for (int i2 = this.c; i2 < this.c + n5; ++i2) {
                    int n6 = n4 + (i2 - this.c) * this.d;
                    if (n3 <= n6 || n3 > n6 + this.d) continue;
                    n6 = this.a(this.j, i2);
                    if (n2 >= g.a - n6 >> 1 && n2 <= g.a + n6 >> 1) {
                        this.a = i2;
                    }
                    if (this.a != i2) continue;
                    if (n2 < 60 && this.a == null && a[this.j] != null && a[this.j][this.a] != null) {
                        this.a(g.b(2));
                    } else if (n2 > g.a - 60 && this.a == null && a[this.j] != null && a[this.j][this.a] != null) {
                        this.a(g.b(3));
                    } else {
                        if (n2 < g.a - n6 >> 1 || n2 > g.a + n6 >> 1) break;
                        this.a(-6);
                    }
                    break block10;
                }
                return;
            }
            if (this.b == 2) {
                int n7 = at.a(0, "m") + 2;
                int n8 = g.a / n7;
                n7 = (n2 - 1) / n7;
                int n9 = n3 / at.e(0);
                int n10 = n7 + n9 * n8;
                if (this.a.a.length() < this.a.h && n10 < a.length) {
                    this.a.a.append(a[n10]);
                }
                n9 = 0 + (a.length / n8 + 1) * at.e(0);
                if (this.a.a.length() > 0 && n2 < 1 + at.a(0, "del") && n3 >= n9 && n3 <= n9 + at.e(0)) {
                    this.a.a.deleteCharAt(this.a.a.length() - 1);
                }
            }
        }
    }

    public final void a(b b2) {
        if (this.i) {
            return;
        }
        switch (b2.a) {
            case 1: {
                this.a(g.b(0));
                return;
            }
            case 2: {
                this.a(g.b(1));
                return;
            }
            case 3: {
                this.a(g.b(2));
                return;
            }
            case 4: {
                this.a(g.b(3));
                return;
            }
            case 5: {
                this.a(-6);
                return;
            }
            case 8: {
                this.a(-7);
            }
        }
    }

    public final void b(b b2) {
        if (this.i) {
            return;
        }
        switch (b2.a) {
            case 1: {
                this.b(g.b(0));
                return;
            }
            case 2: {
                this.b(g.b(1));
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void a(int n2) {
        try {
            block1 : switch (this.b) {
                case 1: {
                    if (g.a(n2)) {
                        if (!this.d) return;
                        this.g = true;
                        return;
                    }
                    if (this.c && (g.c(n2) || this.a.a(n2) == 8)) {
                        this.f = true;
                        return;
                    }
                    switch (this.a.a(n2)) {
                        case 0: {
                            this.a.j();
                            break block1;
                        }
                        case 1: {
                            this.a.k();
                        }
                    }
                    return;
                }
                case 2: {
                    if (g.a(n2)) {
                        this.g = true;
                        return;
                    }
                    if (!g.c(n2) && !g.b(n2)) {
                        this.a.c(n2);
                        return;
                    }
                    this.f = true;
                    return;
                }
                case 0: {
                    if (g.a(n2)) {
                        if (!this.b) return;
                        this.g = true;
                        return;
                    }
                    if (g.c(n2) || this.a.a(n2) == 8) {
                        this.f = true;
                        return;
                    }
                    if (n2 == -8) {
                        s s2 = this;
                        switch (s2.a) {
                            case 61: {
                                if (s2.a >= s2.a() - 1) return;
                                s2.m();
                                s2.a((byte)67);
                                return;
                            }
                        }
                        return;
                    }
                    n2 = this.a.a(n2);
                    switch (n2) {
                        case 0: {
                            --this.a;
                            if (this.a < 0) {
                                n2 = this.a();
                                this.a = n2 - 1;
                                this.c = n2 - this.b;
                                if (this.c >= 0) return;
                                this.c = 0;
                                break block1;
                            }
                            if (this.a >= this.c) {
                                if (this.a - this.c < this.b) return;
                                this.c = this.a - this.b + 1;
                                return;
                            }
                            this.c = this.a;
                            break block1;
                        }
                        case 1: {
                            ++this.a;
                            n2 = this.a();
                            if (this.a > n2 - 1) {
                                this.a = 0;
                                this.c = 0;
                            }
                            if (this.a - this.c < this.b) {
                                if (this.a >= this.c) return;
                                this.c = this.a;
                                return;
                            }
                            this.c = this.a - this.b + 1;
                            break block1;
                        }
                        case 2: 
                        case 3: {
                            n2 = n2 == 2 ? 1 : 0;
                            if (this.a != null) return;
                            if (a[this.j] == null) return;
                            if (a[this.j][this.a] == null) return;
                            if (a[this.j][this.a][0] < 0) {
                                if (n2 != 0 && this.b[this.a] > a[this.j][this.a][1]) {
                                    this.b[this.a] = (byte)Math.max(a[this.j][this.a][1], this.b[this.a] + a[this.j][this.a][0]);
                                    this.c(this.a);
                                    this.k();
                                    return;
                                }
                                if (n2 != 0) return;
                                if (this.b[this.a] >= a[this.j][this.a][2]) return;
                                this.b[this.a] = (byte)Math.min(a[this.j][this.a][2], this.b[this.a] - a[this.j][this.a][0]);
                                this.c(this.a);
                                this.k();
                                return;
                            }
                            if (n2 != 0 && (this.b[this.a] > 0 || a[this.j][this.a].length == 2)) {
                                int n3 = this.a;
                                this.b[n3] = (byte)(this.b[n3] - 1);
                                if (this.b[this.a] < 0) {
                                    this.b[this.a] = (byte)(a[this.j][this.a].length - 1);
                                }
                                this.c(this.a);
                                this.k();
                                return;
                            }
                            if (n2 != 0) return;
                            if (this.b[this.a] >= a[this.j][this.a].length - 1) {
                                if (a[this.j][this.a].length != 2) return;
                            }
                            int n4 = this.a;
                            this.b[n4] = (byte)(this.b[n4] + 1);
                            this.b[this.a] = (byte)(this.b[this.a] % a[this.j][this.a].length);
                            this.c(this.a);
                            this.k();
                        }
                    }
                }
            }
            return;
        }
        catch (Exception exception) {}
    }

    public final void b(int n2) {
        switch (this.b) {
            case 1: {
                switch (this.a.a(n2)) {
                    case 0: 
                    case 1: {
                        this.a.l();
                    }
                }
            }
        }
    }

    public static void e() {
        Main.a.a = (byte)6;
    }

    public static void f() {
        Main.a.a = 1;
        ak.b();
        a = null;
    }

    public static void a(String string, boolean bl) {
        try {
            Main.a.platformRequest(string);
        }
        catch (ConnectionNotFoundException connectionNotFoundException) {}
        if (bl) {
            Main.a.destroyApp(true);
        }
    }

    private void q() {
        try {
            this.a.b("settings");
            this.a.a.writeByte(Main.e);
            this.a.a.writeBoolean(this.a.g);
            this.a.a.writeByte(this.a.a.g);
            this.a.a.writeByte(this.a.d);
            this.a.a.writeBoolean(this.a.a.c);
            this.a.a.writeByte(at.a);
            this.a.a.writeBoolean(this.a.a.b);
            this.a.a.writeByte(Main.f);
            this.a.a.writeBoolean(false);
            this.a.a.writeBoolean(false);
            this.a.a.writeUTF(this.a);
            this.a.a.writeBoolean(true);
            this.a.a(true);
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    public final void g() {
        try {
            this.a.a("settings");
            Main.e = this.a.a.readByte();
            this.a.g = this.a.a.readBoolean();
            this.a.a.g = this.a.a.readByte();
            this.a.d = this.a.a.readByte();
            this.a.a.c = this.a.a.readBoolean();
            this.a.a.readByte();
            this.a.a.b = this.a.a.readBoolean();
            Main.f = this.a.a.readByte();
            this.a.a.readBoolean();
            this.a.a.readBoolean();
            this.a = this.a.a.readUTF();
            this.a.a.readBoolean();
            this.a.a(false);
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    static {
        a = new short[][]{{13, 26, 60, 11, 10, 29, 5}, {12, 6, 11, 10, 29, 30}, {33, 105, 34, 82, 62, 17}, null, {21, 20}, null, {27, 28, 74, 88, 107}, {106, 27, 28, 74, 88, 107}, {12, 6, 46, 11, 10, 29, 30}, {27, 43, 44, 45, 94, 74}, {106, 27, 43, 44, 45, 94, 74}, null, null, null, {12, 11, 10, 29, 30}, null, null, null, null, {13, 26, 123, 60, 11, 10, 29, 5}, null, null};
        a = new short[][][]{null, null, new short[][]{{-1, 0, 10}, {-1, 0, 10}, {8, 9}, {8, 9}, {64, 67, 63}, null, null}, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null};
    }
}

