/*
 * Decompiled with CFR 0.152.
 */
import com.hardwire.blob.Main;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Vector;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class k {
    public static final int[] a = new int[]{1024, 1024, 682, 341, 1024, 341};
    public static final int[] b = new int[]{-1, -1, -1536, -1, -1, -1536};
    public static final int[] c = new int[]{-1, -1, -1, -1, 32768, -1};
    private static boolean[] g = new boolean[]{true, true, true, true, true, true, false, true, false, false, true, true, true, false, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, false, false, true, true};
    public static final boolean[] a = new boolean[]{false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false};
    public static final boolean[] b = new boolean[]{true, true, true, false, false, false, false, false, false, true, false, true, false, true, false, true, true, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true};
    private static final short[] a = new short[]{0, 4, 8, 2, 4, 12, 10, 2, 8, 6, 12, 14, 10, 0, 14, 32, 16, 128, 64, 0, 0, 0, 0, 40, 24, 128, 64, 8, 0};
    public Main a;
    public g a;
    public at a;
    public ab a;
    private r a;
    public Vector a;
    public byte a;
    public boolean a;
    public int[] d;
    public int a;
    public byte b;
    public byte c;
    public byte d;
    public int b;
    public af a;
    public ax[] a;
    public ac[] a;
    public ae[] a;
    public u[] a;
    public d[] a;
    public ag[] a;
    public byte[][][] a;
    public byte[][] a;
    public bg[][] a;
    public boolean[] c;
    public boolean[] d;
    public boolean[] e;
    public boolean[] f;
    public short[][] a;
    public int c;
    public int d;
    public int e;
    public int[][] a;
    public int f;
    public int g;
    public byte e;
    public int[] e;
    public int[] f;
    public int h;
    public int i;
    public int j;
    public int k;
    public int l;
    public boolean b = -1;
    public int m;
    public int n;
    private int p;
    public int o;
    public String a;
    public byte[] a;
    public static boolean c;

    public k(Main object) {
        this.c = (byte)-1;
        this.d = (byte)-1;
        this.e = new int[10];
        this.f = new int[10];
        this.a = object;
        this.a = this.a.a;
        this.a = this.a.a;
        this.a = new af();
        k k2 = this;
        object = this.a;
        this.a.a = k2;
        this.a = new ab(this.a, this);
    }

    public final boolean a(byte by, byte by2) {
        try {
            this.a.c(12);
            this.d();
            DataInputStream dataInputStream = this.b == -1 ? this.a.a(this.a, this.a) : this.a.a(at.b[this.b], null);
            if (dataInputStream == null) {
                return false;
            }
            do {
                this.a.j = al.b(0, ab.a.length - 1);
            } while (ab.e[this.a.j] != this.a.a);
            if (this.a.b == -1 || this.a.b != this.a.a) {
                this.a.a = true;
                this.a.n();
                this.a.a = false;
            }
            if (this.a.b != -1 && this.a.b != this.a.a) {
                this.a.e();
            }
            this.a.f();
            this.a.d();
            this.a.a.a = false;
            this.c = (byte)7;
            this.d = (byte)7;
            this.a.h = Integer.MAX_VALUE;
            this.a.a = 0;
            this.a.n();
            long l2 = System.currentTimeMillis();
            this.e = by2;
            this.b = by;
            this.a = this.a.a;
            this.a = 0;
            if (this.b == 2 && this.a == null) {
                this.a = new Vector();
            }
            if (this.b == 4) {
                this.f = 1;
                this.g = 0;
            } else {
                this.f = 0;
                this.g = 1;
            }
            if ((this.b & 3) != 0) {
                int[][] nArray = this.a = new int[this.b == 2 ? 2 : 1][4];
                by = (byte)200;
                af af2 = this.a;
                this.a.e = 200;
                af2.a = nArray;
                af2.b = new Vector();
                af2.a = new Vector();
                af2.c = new Vector();
                af2.b = new int[16][2];
                af2.a = false;
                af2.b = true;
            }
            System.gc();
            this.a.a(dataInputStream);
            System.gc();
            this.a.e();
            if ((this.b & 3) != 0) {
                int n2 = (this.a.length << 1) + 18 * this.a.length;
                this.a.a(this.a, this.a[1], this.a, g, 15, this.a.length + this.a.length + this.a.length, this.a.length, n2 += 16 * this.a.length);
            }
            this.k = 0;
            if (this.e != 4 && this.e != 5) {
                this.f[1] = 0;
                this.f[0] = 0;
                this.e[1] = 0;
                this.e[0] = 0;
            }
            this.o = 0;
            if (this.e == 4 || this.e == 5) {
                this.m = 1;
                this.p = -1;
            } else {
                this.m = -1;
            }
            this.a.e();
            long l3 = System.currentTimeMillis() - l2;
            if (l3 < 500L) {
                Thread.sleep(500L - l3);
            }
        }
        catch (Exception exception) {
            return false;
        }
        System.gc();
        Main.d = false;
        this.c = 0;
        this.d = 0;
        this.a.h = 0;
        return true;
    }

    public final int a(int[] nArray) {
        c = false;
        int n2 = nArray[0] >> 15;
        int n3 = nArray[1] >> 15;
        int n4 = nArray[2] >> 15;
        int n5 = nArray[3] >> 15;
        if (n2 < 0) {
            n2 = 0;
        }
        if (n3 < 0) {
            n3 = 0;
        }
        if (n4 >= this.d) {
            n4 = this.d - 1;
        }
        if (n5 >= this.e) {
            n5 = this.e - 1;
        }
        while (n3 <= n5) {
            for (int i2 = n2; i2 <= n4; ++i2) {
                switch (this.a[2][i2][n3]) {
                    case 36: {
                        c = true;
                    }
                    case 6: 
                    case 7: {
                        return n3;
                    }
                }
            }
            ++n3;
        }
        return -1;
    }

    public final boolean a(int n2, int n3) {
        switch (this.a[2][n2][n3]) {
            case 6: 
            case 7: 
            case 36: {
                return true;
            }
        }
        return false;
    }

    private void e() {
        long l2;
        long l3 = l2 = System.currentTimeMillis();
        while (!this.a && !this.a.a()) {
            this.a.b();
            long l4 = System.currentTimeMillis();
            if (l4 - l2 > 500L && l4 - l3 > (long)Main.a) {
                if (this.a.a == 1) {
                    if (this.a.a.a()) {
                        this.a.n();
                    }
                } else {
                    this.a.a = true;
                    this.a.n();
                    this.a.a = false;
                }
                l3 = System.currentTimeMillis();
                continue;
            }
            try {
                Thread.sleep(1L);
            }
            catch (InterruptedException interruptedException) {}
        }
    }

    private void f() {
        this.d();
        this.a.a.b();
        this.a.a.a((byte)10);
        this.a.a = 1;
    }

    private boolean a(DataInputStream dataInputStream) {
        switch (dataInputStream.readByte()) {
            case 1: {
                this.d = (byte)2;
                dataInputStream.close();
                return true;
            }
            case 2: {
                this.a.a.c(false);
                return true;
            }
            case 3: {
                this.b = dataInputStream.readInt();
                this.a = dataInputStream.readByte();
                this.e[0] = dataInputStream.readInt();
                this.e[1] = dataInputStream.readInt();
                this.f[0] = dataInputStream.readInt();
                this.f[1] = dataInputStream.readInt();
                if (this.e == 2) {
                    this.h = dataInputStream.readInt();
                    this.i = dataInputStream.readInt();
                    this.j = dataInputStream.readInt();
                }
                this.d = (byte)5;
                return true;
            }
            case 4: {
                this.m = dataInputStream.readInt();
                this.p = dataInputStream.readInt();
                this.n = dataInputStream.readInt();
                return true;
            }
            case 5: {
                if (this.m < 0) {
                    this.m = 0;
                    this.p = 4;
                }
                return true;
            }
        }
        return false;
    }

    private boolean a() {
        byte by = this.a;
        this.a = 0;
        switch (by) {
            case 1: {
                this.a.a(new byte[]{1});
                return true;
            }
            case 2: {
                this.a.a(new byte[]{2});
                return true;
            }
            case 5: {
                this.a.a(new byte[]{5});
                return true;
            }
            case 3: {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
                dataOutputStream.writeByte(3);
                dataOutputStream.writeInt(this.b);
                dataOutputStream.writeByte(this.a);
                dataOutputStream.writeInt(this.e[0]);
                dataOutputStream.writeInt(this.e[1]);
                dataOutputStream.writeInt(this.f[0]);
                dataOutputStream.writeInt(this.f[1]);
                if (this.e == 2) {
                    dataOutputStream.writeInt(this.h);
                    dataOutputStream.writeInt(this.i);
                    dataOutputStream.writeInt(this.j);
                }
                this.a.a(byteArrayOutputStream.toByteArray());
                return true;
            }
        }
        return false;
    }

    public final void a() {
        if (this.a) {
            return;
        }
        if (this.b == 4) {
            try {
                if (this.m >= 0 && this.o > 0) {
                    this.a = (byte)5;
                }
                if (this.a()) {
                    return;
                }
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
                dataOutputStream.writeByte(0);
                dataOutputStream.writeInt(this.a.d[0]);
                dataOutputStream.writeInt(this.a.d[1]);
                dataOutputStream.writeInt(this.a.d[2]);
                dataOutputStream.writeInt(this.a.d[3]);
                int n2 = 0;
                if (this.a[this.f].d) {
                    n2 = 1;
                }
                if (this.a[this.f].e) {
                    n2 = (byte)(n2 | 2);
                }
                if (this.a[this.f].f) {
                    n2 = (byte)(n2 | 4);
                }
                if (this.a[this.f].g) {
                    n2 = (byte)(n2 | 8);
                }
                if (this.a[this.f].h) {
                    n2 = (byte)(n2 | 0x20);
                }
                dataOutputStream.writeByte(n2);
                dataOutputStream.writeByte(this.a[this.f].h);
                dataOutputStream.writeByte(this.a[this.f].i);
                dataOutputStream.writeByte(this.a[this.f].b);
                if (!this.a.a(byteArrayOutputStream.toByteArray())) {
                    this.f();
                }
                return;
            }
            catch (IOException iOException) {
                this.f();
                return;
            }
        }
        if (this.b == 2) {
            this.a.removeAllElements();
            this.e();
            if (this.a) {
                return;
            }
            ByteArrayInputStream byteArrayInputStream = null;
            byteArrayInputStream = this.a.a();
            if (byteArrayInputStream == null) {
                this.f();
                return;
            }
            try {
                DataInputStream dataInputStream = new DataInputStream(byteArrayInputStream);
                if (this.a(dataInputStream)) {
                    return;
                }
                if (this.d == null) {
                    this.d = new int[4];
                }
                this.d[0] = dataInputStream.readInt();
                this.d[1] = dataInputStream.readInt();
                this.d[2] = dataInputStream.readInt();
                this.d[3] = dataInputStream.readInt();
                byte by = dataInputStream.readByte();
                this.a[this.g].d = (by & 1) != 0;
                this.a[this.g].e = (by & 2) != 0;
                this.a[this.g].f = (by & 4) != 0;
                this.a[this.g].g = (by & 8) != 0;
                this.a[this.g].h = (by & 0x20) != 0;
                this.a[this.g].h = dataInputStream.readByte();
                this.a[this.g].i = dataInputStream.readByte();
                this.a[this.g].b = dataInputStream.readByte();
                return;
            }
            catch (IOException iOException) {
                this.f();
            }
        }
    }

    public final void b() {
        block110: {
            if (this.a) {
                return;
            }
            try {
                if (this.b == 2) {
                    try {
                        Object object;
                        int n2;
                        int n3;
                        Object object2;
                        int n4;
                        if (this.a()) {
                            return;
                        }
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
                        if (this.m >= 0 && this.o > 0) {
                            k k2 = this;
                            if (k2.m >= 0 && k2.o > 0) {
                                if (k2.p == -1) {
                                    if (k2.a.h >= 4) {
                                        k2.p = 0;
                                        k2.n = 0;
                                    }
                                } else if (k2.p == 0 && k2.n < 3) {
                                    ++k2.n;
                                } else if (k2.p < 4) {
                                    ++k2.p;
                                } else if (k2.n < 6) {
                                    ++k2.n;
                                } else {
                                    --k2.m;
                                    k2.p = -1;
                                }
                            }
                            dataOutputStream.writeByte(4);
                            dataOutputStream.writeInt(this.m);
                            dataOutputStream.writeInt(this.p);
                            dataOutputStream.writeInt(this.n);
                            if (!this.a.a(byteArrayOutputStream.toByteArray())) {
                                this.f();
                            }
                            return;
                        }
                        this.d[0] = this.d[0] - 32768;
                        this.d[1] = this.d[1] - 32768;
                        this.d[2] = this.d[2] + 32768;
                        this.d[3] = this.d[3] + 32768;
                        dataOutputStream.writeByte(0);
                        dataOutputStream.writeInt(this.k);
                        int n5 = this.a.length;
                        for (n4 = 0; n4 < n5; ++n4) {
                            object2 = this.a[n4];
                            dataOutputStream.writeByte(((d)object2).a);
                            n3 = ((d)object2).a;
                            for (n2 = 0; n2 < n3; ++n2) {
                                object = ((d)object2).a[n2].a.a;
                                dataOutputStream.writeShort(((bg)object).a >> 10);
                                dataOutputStream.writeShort(((bg)object).b >> 10);
                            }
                            for (n2 = 0; n2 < ((d)object2).a.a.length; ++n2) {
                                object = ((d)object2).a.a[n2];
                                dataOutputStream.writeShort(((x)object).a.a >> 10);
                                dataOutputStream.writeShort(((x)object).a.b >> 10);
                                dataOutputStream.writeByte(((x)object).b);
                            }
                            dataOutputStream.writeByte(((d)object2).d >> 10);
                            n2 = this.e[n4] << 1;
                            if (((d)object2).a) {
                                n2 |= 1;
                            }
                            dataOutputStream.writeShort(n2);
                            byte by = ((d)object2).c;
                            n2 = by;
                            n2 = by | ((d)object2).d << 2;
                            if (((d)object2).e > 0) {
                                n2 |= 0x10;
                            }
                            dataOutputStream.writeByte(n2 |= ((d)object2).a.b << 5);
                            if (((d)object2).d == 2) {
                                dataOutputStream.writeByte(((d)object2).c);
                            }
                            dataOutputStream.writeInt(((d)object2).a.c().d());
                        }
                        n5 = this.a.length;
                        for (n4 = 0; n4 < n5; ++n4) {
                            object2 = this.a[n4];
                            if (((ae)object2).a == 2 || !al.a(this.d, ((ae)object2).a())) continue;
                            dataOutputStream.writeByte(n4);
                            dataOutputStream.writeShort(((ae)object2).a.a.a.a >> 10);
                            dataOutputStream.writeShort(((ae)object2).a.a.a.b >> 10);
                            if (((ae)object2).b != null) {
                                dataOutputStream.writeShort(((ae)object2).b.a.a.a >> 10);
                                dataOutputStream.writeShort(((ae)object2).b.a.a.b >> 10);
                            }
                            n3 = ((ae)object2).a;
                            if (((ae)object2).a) {
                                n3 = (short)(n3 | 8);
                            }
                            if (((ae)object2).a != -1) {
                                n3 = (short)(n3 | 0x10);
                            }
                            if (((ae)object2).b) {
                                n3 = (short)(n3 | 0x20);
                            }
                            short s2 = (short)(n3 | ((ae)object2).b << 6);
                            n3 = s2;
                            n3 = (short)(s2 | ((ae)object2).c << 11);
                            dataOutputStream.writeShort(n3);
                        }
                        dataOutputStream.writeByte(-1);
                        n5 = this.a.length;
                        for (n4 = 0; n4 < n5; ++n4) {
                            object2 = this.a[n4];
                            if (!al.a(this.d, ((ac)object2).a.a())) continue;
                            dataOutputStream.writeByte(n4);
                            dataOutputStream.writeShort(((ac)object2).a.a.a >> 10);
                            dataOutputStream.writeShort(((ac)object2).a.a.b >> 10);
                        }
                        dataOutputStream.writeByte(-1);
                        n5 = this.a.length;
                        for (n4 = 0; n4 < n5; ++n4) {
                            object = object2 = this.a[n4];
                            if (!al.a(this.d, ((u)object).a)) continue;
                            dataOutputStream.writeByte(n4);
                            int n6 = n3 = this.a[n4].b ? 1 : 0;
                            if (this.a[n4].a.a) {
                                n3 |= 2;
                            }
                            dataOutputStream.writeByte(n3);
                        }
                        dataOutputStream.writeByte(-1);
                        n5 = this.a.length;
                        for (n4 = 0; n4 < n5; ++n4) {
                            object2 = this.a[n4];
                            if (!al.a(this.d, ((ax)object2).a.a())) continue;
                            dataOutputStream.writeByte(n4);
                            n3 = ((ax)object2).a.a.length;
                            for (n2 = 0; n2 < n3; ++n2) {
                                object = ((ax)object2).a.a[n2].a;
                                dataOutputStream.writeShort(((bg)object).a >> 10);
                                dataOutputStream.writeShort(((bg)object).b >> 10);
                            }
                            if (((ax)object2).a.c != null) {
                                n3 = ((ax)object2).a.c.length;
                                for (n2 = 0; n2 < n3; ++n2) {
                                    object = ((ax)object2).a.c[n2].a;
                                    dataOutputStream.writeShort(((bg)object).a >> 10);
                                    dataOutputStream.writeShort(((bg)object).b >> 10);
                                }
                            }
                            if (((ax)object2).a.c != null) {
                                n3 = ((ax)object2).a.c.length;
                                for (n2 = 0; n2 < n3; ++n2) {
                                    object = ((ax)object2).a.c[n2];
                                    if (object == null) {
                                        dataOutputStream.writeShort(Short.MAX_VALUE);
                                        continue;
                                    }
                                    if (((ag)object).b != null) {
                                        dataOutputStream.writeShort(Short.MIN_VALUE);
                                        continue;
                                    }
                                    if (((ag)object).b != null) continue;
                                    dataOutputStream.writeShort(((ag)object).a.a >> 10);
                                    dataOutputStream.writeShort(((ag)object).a.b >> 10);
                                }
                            }
                            n2 = ((ax)object2).a;
                            if (((ax)object2).b) {
                                n2 |= 4;
                            }
                            dataOutputStream.writeByte(n2);
                        }
                        dataOutputStream.writeByte(-1);
                        n5 = this.a.size();
                        dataOutputStream.writeByte(n5);
                        for (n4 = 0; n4 < n5; ++n4) {
                            object2 = (byte[])this.a.elementAt(n4);
                            for (n3 = 0; n3 < ((Object)object2).length; ++n3) {
                                dataOutputStream.writeByte((int)object2[n3]);
                            }
                        }
                        this.a.removeAllElements();
                        if (!this.a.a(byteArrayOutputStream.toByteArray())) {
                            this.f();
                        }
                        break block110;
                    }
                    catch (IOException iOException) {
                        this.f();
                    }
                    break block110;
                }
                if (this.b == 4) {
                    this.e();
                    if (this.a) {
                        return;
                    }
                    ByteArrayInputStream byteArrayInputStream = null;
                    byteArrayInputStream = this.a.a();
                    if (byteArrayInputStream == null) {
                        this.f();
                        break block110;
                    }
                    try {
                        int n7;
                        int n8;
                        Object object;
                        int n9;
                        DataInputStream dataInputStream = new DataInputStream(byteArrayInputStream);
                        if (this.a(dataInputStream)) {
                            return;
                        }
                        this.k = dataInputStream.readInt();
                        int n10 = this.a.length;
                        for (n9 = 0; n9 < n10; ++n9) {
                            Object object3;
                            object = this.a[n9];
                            this.a[n9].a = dataInputStream.readByte();
                            n8 = ((d)object).a;
                            for (n7 = 0; n7 < n8; ++n7) {
                                if (((d)object).a[n7] == null) {
                                    ((d)object).a[n7] = new as(new x(new bg(), 2048), 5120);
                                }
                                object3 = ((d)object).a[n7].a;
                                ((d)object).a[n7].a.b.a = ((x)object3).a.a = dataInputStream.readShort() << 10;
                                ((x)object3).b.b = ((x)object3).a.b = dataInputStream.readShort() << 10;
                            }
                            for (n7 = 0; n7 < ((d)object).a.a.length; ++n7) {
                                object3 = ((d)object).a.a[n7];
                                ((d)object).a.a[n7].b.a = ((x)object3).a.a = dataInputStream.readShort() << 10;
                                ((x)object3).b.b = ((x)object3).a.b = dataInputStream.readShort() << 10;
                                ((x)object3).b = dataInputStream.readByte();
                            }
                            n7 = dataInputStream.readByte() << 10;
                            if (((d)object).d > n7) {
                                ((d)object).c = 2;
                                if (((d)object).b == this.f) {
                                    this.a.a(Main.b / 2);
                                }
                            }
                            ((d)object).d = n7;
                            int n11 = dataInputStream.readShort() & 0xFFFF;
                            this.e[n9] = n11 >> 1;
                            boolean bl = (n11 & 1) != 0;
                            n8 = ((d)object).a ? 1 : 0;
                            ((d)object).a = bl;
                            n11 = dataInputStream.readByte();
                            ((d)object).c = (byte)(n11 & 3);
                            byte by = (byte)(n11 >> 2 & 3);
                            n7 = by;
                            if (by == 1 && ((d)object).d != 1 && al.a(this.a.d, ((d)object).a.a())) {
                                this.a.a(4, false);
                            }
                            ((d)object).d = n7;
                            if ((n11 >> 4 & 1) != 0) {
                                if (((d)object).e == 0) {
                                    ((d)object).e = 1;
                                }
                            } else {
                                ((d)object).e = 0;
                            }
                            ((d)object).a.b = n11 >> 5;
                            if (n7 == 2) {
                                ((d)object).c = dataInputStream.readByte();
                            }
                            ((d)object).f = dataInputStream.readInt();
                            ((d)object).a.d();
                            object3 = ((d)object).a;
                            ((d)object).a.a = true;
                            if (((d)object).a() || !al.a(this.a.d, ((d)object).a.a())) continue;
                            n7 = this.a(((d)object).a.a());
                            if (bl && n8 == 0 && ((d)object).f > 10240) {
                                this.a.a(7, false);
                                if (this.a.g > 0) {
                                    ((d)object).a(n7);
                                }
                            }
                            if (this.a.g <= 1 || ((d)object).a.a()[1] >> 15 >= n7) continue;
                            this.a.b(((d)object).a.a().a >> 10, n7 << 5, 16, 1);
                        }
                        while (true) {
                            boolean bl;
                            boolean bl2;
                            byte by = dataInputStream.readByte();
                            n9 = by;
                            if (by == -1) break;
                            object = this.a[n9];
                            as as2 = ((ae)object).a;
                            ((ae)object).a.a = true;
                            if (((ae)object).b != null) {
                                as2 = ((ae)object).b;
                                ((ae)object).b.a = true;
                            }
                            ((ae)object).a.a.b.a = ((ae)object).a.a.a.a = dataInputStream.readShort() << 10;
                            ((ae)object).a.a.b.b = ((ae)object).a.a.a.b = dataInputStream.readShort() << 10;
                            if (((ae)object).b != null) {
                                ((ae)object).b.a.b.a = ((ae)object).b.a.a.a = dataInputStream.readShort() << 10;
                                ((ae)object).b.a.b.b = ((ae)object).b.a.a.b = dataInputStream.readShort() << 10;
                            }
                            short s3 = dataInputStream.readShort();
                            n8 = s3;
                            n7 = (byte)(s3 & 7);
                            boolean bl3 = bl2 = (n8 >> 4 & 1) != 0;
                            if (n7 != ((ae)object).a) {
                                if (!bl2 && n7 == 1 && ((ae)object).a != 2) {
                                    this.a.a(((ae)object).d == 1 ? 8 : 4, false);
                                    as as3 = ((ae)object).a;
                                    bg bg2 = as3.a.a;
                                    this.a.a(bg2.a >> 10, bg2.b >> 10, 30, 30);
                                }
                                if (n7 != 1 || ((ae)object).a != 2) {
                                    ((ae)object).a((byte)n7);
                                }
                            }
                            boolean bl4 = ((ae)object).a = (n8 >> 3 & 1) != 0;
                            if ((n8 >> 4 & 1) != 0) {
                                ((ae)object).a = 0;
                            }
                            if ((bl = (n8 >> 5 & 1) != 0) && !((ae)object).b && this.a.g > 0 && al.a(this.a.d, ((ae)object).a.a())) {
                                as as4 = ((ae)object).a;
                                this.a.a(as4.a.a.a >> 10, this.a(((ae)object).a.a()) << 5, 2);
                            }
                            ((ae)object).b = bl;
                            ((ae)object).b = (byte)(n8 >> 6 & 0x1F);
                            ((ae)object).c = (byte)(n8 >> 11);
                            ((ae)object).c = true;
                        }
                        n10 = this.a.length;
                        for (n9 = 0; n9 < n10; ++n9) {
                            if (this.a[n9].c) {
                                this.a[n9].c = false;
                                continue;
                            }
                            if (this.a[n9].a == 2) continue;
                            int[] nArray = this.a[n9].a.a();
                            object = nArray;
                            object[3] = Integer.MIN_VALUE;
                            object[2] = Integer.MIN_VALUE;
                            object[1] = Integer.MIN_VALUE;
                            nArray[0] = Integer.MIN_VALUE;
                            if (this.a[n9].b == null) continue;
                            int[] nArray2 = this.a[n9].b.a();
                            object = nArray2;
                            object[3] = Integer.MIN_VALUE;
                            object[2] = Integer.MIN_VALUE;
                            object[1] = Integer.MIN_VALUE;
                            nArray2[0] = Integer.MIN_VALUE;
                        }
                        while (true) {
                            byte by = dataInputStream.readByte();
                            n9 = by;
                            if (by == -1) break;
                            object = this.a[n9];
                            this.a[n9].a.a.a = dataInputStream.readShort() << 10;
                            ((ac)object).a.a.b = dataInputStream.readShort() << 10;
                            ((ac)object).b = true;
                        }
                        n10 = this.a.length;
                        for (n9 = 0; n9 < n10; ++n9) {
                            if (this.a[n9].b) {
                                this.a[n9].b = false;
                                continue;
                            }
                            this.a[n9].a.a.b(0x3FFFFFFF, 0x3FFFFFFF);
                        }
                        while (true) {
                            byte by = dataInputStream.readByte();
                            n9 = by;
                            if (by == -1) break;
                            byte by2 = dataInputStream.readByte();
                            n8 = (by2 & 1) != 0 ? 1 : 0;
                            boolean bl = this.a[n9].a.a = (by2 & 2) != 0;
                            if (n8 != 0 && !this.a[n9].b) {
                                u u2 = this.a[n9];
                                if (al.a(this.a.d, u2.a)) {
                                    this.a.a(5, false);
                                }
                            }
                            if (n8 != 0 && !this.a[n9].b) {
                                this.a[n9].b();
                                continue;
                            }
                            if (n8 != 0 || !this.a[n9].b) continue;
                            this.a[n9].a();
                        }
                        while (true) {
                            short s4;
                            byte by = dataInputStream.readByte();
                            n9 = by;
                            if (by == -1) break;
                            ax ax2 = this.a[n9];
                            n8 = ax2.a.a.length;
                            for (n7 = 0; n7 < n8; ++n7) {
                                x x2 = ax2.a.a[n7];
                                ax2.a.a[n7].b.a = x2.a.a = dataInputStream.readShort() << 10;
                                x2.b.b = x2.a.b = dataInputStream.readShort() << 10;
                            }
                            if (ax2.a.c != null) {
                                n8 = ax2.a.c.length;
                                for (n7 = 0; n7 < n8; ++n7) {
                                    x x3 = ax2.a.c[n7];
                                    ax2.a.c[n7].b.a = x3.a.a = dataInputStream.readShort() << 10;
                                    x3.b.b = x3.a.b = dataInputStream.readShort() << 10;
                                }
                            }
                            if (ax2.a.c != null) {
                                n8 = ax2.a.c.length;
                                for (n7 = 0; n7 < n8; ++n7) {
                                    ag ag2 = ax2.a.c[n7];
                                    s4 = dataInputStream.readShort();
                                    if (s4 == Short.MAX_VALUE) {
                                        ax2.a.c[n7] = null;
                                        continue;
                                    }
                                    if (s4 == Short.MIN_VALUE) continue;
                                    ag2.a.a = s4 << 10;
                                    ag2.a.b = dataInputStream.readShort() << 10;
                                }
                            }
                            byte by3 = dataInputStream.readByte();
                            n7 = by3;
                            byte by4 = (byte)(by3 & 3);
                            if (ax2.a != by4) {
                                if (by4 == 2 && ax2.a != 2) {
                                    ax2.b();
                                }
                                if (by4 != 1 || ax2.a != 2) {
                                    ax2.a = by4;
                                }
                            }
                            s4 = (n7 >> 2 & 1) != 0 ? (short)1 : 0;
                            ax2.a.d();
                            h h2 = ax2.a;
                            ax2.a.a = true;
                            if (s4 != 0 && !ax2.b && this.a.g > 0 && al.a(this.a.d, ax2.a.a())) {
                                this.a.a(ax2.a.a().a >> 10, this.a(ax2.a.a()) << 5, 2);
                            }
                            ax2.b = s4;
                            ax2.a = true;
                        }
                        n10 = this.a.length;
                        for (n9 = 0; n9 < n10; ++n9) {
                            if (this.a[n9].a) {
                                this.a[n9].a = false;
                                continue;
                            }
                            int[] nArray = this.a[n9].a.a();
                            int[] nArray3 = nArray;
                            nArray3[3] = Integer.MIN_VALUE;
                            nArray3[2] = Integer.MIN_VALUE;
                            nArray3[1] = Integer.MIN_VALUE;
                            nArray[0] = Integer.MIN_VALUE;
                        }
                        n10 = dataInputStream.readByte();
                        block41: for (n9 = 0; n9 < n10; ++n9) {
                            byte by = dataInputStream.readByte();
                            switch (by >> 1) {
                                case 0: {
                                    n8 = dataInputStream.readByte() & 0xFF;
                                    n7 = dataInputStream.readByte() & 0xFF;
                                    if ((by & 1) != 0) {
                                        this.a.a(2, false);
                                        this.a.a((n8 << 5) + 16, (n7 << 5) + 16, 4);
                                    }
                                    this.a[1][n8][n7] = -1;
                                    this.a.a((n8 << 5) + 16, (n7 << 5) + 16, 10, 10);
                                    continue block41;
                                }
                                case 1: {
                                    n8 = dataInputStream.readByte() & 0xFF;
                                    n7 = dataInputStream.readByte() & 0xFF;
                                    if ((by & 1) != 0) {
                                        this.a.a(1, false);
                                        this.a.a((n8 << 5) + 16, (n7 << 5) + 16, 5);
                                    }
                                    this.a[1][n8][n7] = -1;
                                    continue block41;
                                }
                                case 2: {
                                    if ((by & 1) != 0) {
                                        this.a.a(9, false);
                                    }
                                    this.a[dataInputStream.readByte() & 0xFF] = null;
                                    continue block41;
                                }
                                case 3: {
                                    n8 = dataInputStream.readByte() & 0xFF;
                                    n7 = dataInputStream.readByte() & 0xFF;
                                    if ((by & 1) != 0) {
                                        this.a.a(6, false);
                                        int n12 = (n8 << 5) + 16;
                                        int n13 = (n7 << 5) + 16;
                                        this.a.a(n12, n13, 1);
                                        this.a.b(n12, n13, 16, 0);
                                    }
                                    this.a[1][n8][n7] = -1;
                                    continue block41;
                                }
                                case 4: {
                                    this.a[this.f].b = this.a[this.f].c;
                                }
                                case 5: {
                                    this.a.a(0, false);
                                    continue block41;
                                }
                                case 6: {
                                    if ((by & 1) != 0) {
                                        this.a.a(11, false);
                                        continue block41;
                                    }
                                    this.a.a(10, false);
                                    continue block41;
                                }
                                case 7: {
                                    this.a.c(dataInputStream.readByte() & 0xFF);
                                }
                            }
                        }
                        break block110;
                    }
                    catch (IOException iOException) {
                        this.f();
                    }
                }
                return;
            }
            catch (Exception exception) {}
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final void c() {
        try {
            switch (this.c) {
                case 0: {
                    this.a.h();
                    if (this.a.d > 10) {
                        Main.d = true;
                    }
                    break;
                }
                case 6: {
                    this.a.i();
                }
            }
        }
        catch (Exception exception) {}
        try {
            if (this.a != 0 || this.d == this.c) return;
            this.c = this.d;
            switch (this.d) {
                case 2: {
                    this.a(this.b, this.e);
                    return;
                }
                case 5: {
                    if (this.b == 0) {
                        this.b = at.a(this.b);
                        this.a("save", this.b);
                        this.a((byte)1, (byte)0);
                        return;
                    }
                    if (this.e == 0 || this.e == 2) {
                        k k2 = this;
                        try {
                            k2.a.a("achi");
                            int n2 = k2.a.a.readInt();
                            int n3 = k2.a.a.readInt();
                            k2.a.i = k2.a.a.readInt();
                            byte[] byArray = new byte[200];
                            int n4 = k2.a.a.read(byArray);
                            k2.a.a(false);
                            boolean bl = false;
                            if (k2.e == 0 && at.b(k2.b) + 1 > n2) {
                                n2 = at.b(k2.b) + 1;
                                bl = true;
                            } else if (k2.e == 2 && at.c(k2.b) + 1 > n3) {
                                n3 = at.c(k2.b) + 1;
                                bl = true;
                            }
                            if (bl) {
                                k2.a.b("achi");
                                k2.a.a.writeInt(n2);
                                k2.a.a.writeInt(n3);
                                k2.a.a.writeInt(k2.a.i);
                                k2.a.a.write(byArray, 0, n4);
                                k2.a.a(true);
                            }
                        }
                        catch (Exception exception) {}
                    }
                    this.a.d = 0;
                    return;
                }
                case 4: {
                    if (this.e != 0 && this.e != 2) return;
                    int n5 = at.a(this.b);
                    if (n5 == -1) return;
                    this.a(this.e == 0 ? "save" : "msave", n5);
                    return;
                }
                case 8: {
                    this.d();
                    if (this.e == 0 || this.e == 2) {
                        if (this.e == 0 && this.b == 35 || this.e == 2 && this.b == 67) {
                            if (this.b == 2) {
                                this.a.a = 1;
                                if (this.a.a.b()) {
                                    this.a.a.a((byte)26);
                                    return;
                                } else {
                                    this.a.d("msave");
                                    this.a.a.a((byte)31);
                                }
                                return;
                            } else if (this.b == 4) {
                                this.a.a.a((byte)9);
                                this.a.a = 1;
                                this.a.n();
                                this.a.a.c(false);
                                return;
                            } else {
                                this.a.a = 1;
                                if (this.a.a.b()) {
                                    this.a.a.a((byte)26);
                                    return;
                                } else {
                                    this.a.d("save");
                                    this.a.a.a((byte)31);
                                }
                            }
                            return;
                        } else {
                            this.b = at.a(this.b);
                            this.d = (byte)2;
                        }
                        return;
                    }
                    if (this.b == 1) {
                        this.a.a = 1;
                        if (this.b == -1) {
                            this.a.a.a((byte)61);
                            return;
                        } else {
                            this.a.a.a((byte)4);
                            this.a.a.a = at.b(this.b) - 1;
                            this.a.a.a(g.b(1));
                        }
                        return;
                    }
                    if (this.e == 4 || this.e == 5) {
                        if (this.e[0] == 5 || this.e[1] == 5) {
                            if (this.b == 2) {
                                this.a.a = 1;
                                this.a.a.a((byte)17);
                                return;
                            } else {
                                this.a.a = 1;
                                this.a.a.a((byte)9);
                                this.a.a.c(false);
                            }
                            return;
                        } else {
                            this.d = (byte)2;
                        }
                        return;
                    }
                    if (this.b == 4) {
                        this.a.a = 1;
                        this.a.a.a((byte)9);
                        this.a.a.c(false);
                        return;
                    }
                    if (this.b != 2) return;
                    this.a.a = 1;
                    if (at.b(this.b)) {
                        this.a.a.a((byte)20);
                        this.a.a.a = at.c(this.b);
                    } else {
                        this.a.a.a((byte)47);
                        this.a.a.a = at.b(this.b);
                    }
                    --this.a.a.a;
                    this.a.a.a(g.b(1));
                    if (this.a.a.a != -1) return;
                    this.a.a.a = 0;
                }
                default: {
                    return;
                }
            }
        }
        catch (Exception exception) {}
    }

    private void a(String string, int n2) {
        try {
            this.a.b(string);
            this.a.a.writeInt(n2);
            this.a.a.writeInt(this.h);
            this.a.a.writeInt(this.i);
            this.a.a.writeInt(this.j);
            this.a.a.writeBoolean(this.b);
        }
        catch (Exception exception) {}
        this.a.a(true);
    }

    public final void d() {
        this.a.f();
        this.a = null;
        this.d = null;
        this.c = null;
        this.f = null;
        this.e = null;
        this.a = null;
        this.a = null;
        this.a = null;
        this.a = null;
        this.a = null;
        this.a = null;
        this.a = null;
        this.a = null;
        this.a = null;
        if (this.a != null) {
            int n2;
            af af2 = this.a;
            this.a.b = false;
            if (af2.a != null) {
                for (n2 = 0; n2 < af2.a; ++n2) {
                    af2.a[n2] = null;
                }
                af2.a = null;
                af2.a = 0;
            }
            if (af2.a != null) {
                for (n2 = 0; n2 < af2.b; ++n2) {
                    af2.a[n2] = null;
                }
                af2.a = null;
                af2.b = 0;
            }
            if (af2.a != null) {
                for (n2 = 0; n2 < af2.c; ++n2) {
                    af2.a[n2] = null;
                }
                af2.a = null;
                af2.c = 0;
            }
            af2.b = null;
            af2.a = null;
            af2.a = null;
            af2.b = null;
            af2.a = null;
            af2.b = null;
            af2.c = null;
            af2.a = null;
            af2.b = null;
            af2.a = null;
            af2.a = null;
        }
        if (this.a != null) {
            this.a.removeAllElements();
        }
        this.a = null;
        System.gc();
    }

    public final void a(as as2, int n2, int n3) {
        ae ae2;
        Object object;
        int n4;
        if (this.d[n2] && this.c[n3]) {
            n4 = this.a.length;
            for (int i2 = 0; i2 < n4; ++i2) {
                u u2 = this.a[i2];
                if (u2.a != n2 || u2.b != n3 || as2.a.a.b >= n3 << 15) continue;
                if (!u2.b) {
                    object = u2;
                    if (al.a(this.a.d, ((u)object).a)) {
                        this.a.a(5, false);
                    }
                }
                u2.a = true;
            }
        }
        if (!(as2.d != 2 && as2.d != 5 || (n4 = this.a[1][n2][n3]) != 7 && n4 != 10 && n4 != 11 && n4 != 12 || (ae2 = (ae)as2.a).a())) {
            ae2.b();
            int n5 = 0;
            while (n5 < this.a.length) {
                int n6 = n5++;
                this.e[n6] = this.e[n6] + 30;
            }
            object = as2;
            bg bg2 = ((as)object).a.a;
            this.a.a(bg2.a >> 10, bg2.b >> 10, 30, 30);
        }
    }

    public final void a(h h2, int n2, int n3) {
        int n4;
        int n5;
        if (this.d[n2] && this.c[n3]) {
            int n6 = this.a.length;
            for (n5 = 0; n5 < n6; ++n5) {
                u u2 = this.a[n5];
                if (u2.a != n2 || u2.b != n3) continue;
                boolean bl = false;
                for (n4 = 0; n4 < h2.a.length; ++n4) {
                    bg bg2 = h2.a[n4].a;
                    if (bg2.a < n2 << 15 || bg2.a > n2 + 1 << 15 || bg2.b >= (n3 << 15) + 4096) continue;
                    bl = true;
                    break;
                }
                if (!bl) continue;
                if (!u2.b) {
                    u u3 = u2;
                    if (al.a(this.a.d, u3.a)) {
                        this.a.a(5, false);
                    }
                }
                u2.a = true;
            }
        }
        if (h2.j == 1) {
            long l2;
            d d2 = (d)h2.a;
            n5 = this.a[1][n2][n3];
            if (a[n5] && ((l2 = d2.a.c().a()) > 0x1E00000L || d2.e > 0 && l2 > 0L)) {
                int n7;
                boolean bl = al.a(this.a.d, d2.a.a());
                n4 = bl ? 1 : 0;
                if (bl) {
                    this.a.a(6, false);
                    int n8 = (n2 << 5) + 16;
                    int n9 = (n3 << 5) + 16;
                    this.a.a(n8, n9, 1);
                    this.a.b(n8, n9, 16, 0);
                }
                if (this.b == 2) {
                    this.a.addElement(new byte[]{(byte)(6 | (al.a(this.d, d2.a.a()) ? 1 : 0)), (byte)n2, (byte)n3});
                }
                int n10 = (n2 << 15) - 2048;
                int n11 = (n3 << 15) - 2048;
                n5 = (n2 + 1 << 15) + 2048;
                int n12 = (n3 + 1 << 15) + 2048;
                for (n7 = 0; n7 < d2.a.g; ++n7) {
                    ag ag2 = d2.a.b[n7];
                    if (ag2.a == null || ag2.a.a < n10 || ag2.a.a > n5 || ag2.a.b < n11 || ag2.a.b > n12) continue;
                    ag2.b();
                }
                this.a[1][n2][n3] = -1;
                this.a[n2][n3] = -1;
                n10 = n2 - 1;
                n11 = n3 - 1;
                n5 = n2 + 1;
                n12 = n3 + 1;
                for (n7 = n10; n7 <= n5; ++n7) {
                    for (int i2 = n11; i2 <= n12; ++i2) {
                        if (n10 < 0 || n11 < 0 || n5 >= this.d || n12 >= this.e) continue;
                        this.a.a(n7, i2);
                    }
                }
                return;
            }
            if (n5 == 7 || n5 == 10 || n5 == 11 || n5 == 12 || n5 == 68 || n5 == 71 || n5 == 72) {
                d2.b(1024);
            }
            if (d2.e == 0 && d2.c == 2 && g[n5]) {
                bg bg3 = d2.a.a();
                int n13 = bg3.a >> 15;
                n4 = bg3.b >> 15;
                short s2 = a[this.a[n2][n3]];
                if (!d2.f && d2.h == 0 && n13 < n2 && (s2 & 2) != 0) {
                    d2.j |= 2;
                }
                if ((d2.d || d2.i < 0) && (d2.j & 6) != 0 && n4 <= n3 && (s2 & 0x30) != 0) {
                    d2.j |= s2 & 0x30;
                }
                if (!d2.g && d2.h == 0 && n13 > n2 && (s2 & 4) != 0) {
                    d2.j |= 4;
                }
                if (!d2.e && !d2.d && d2.i == 0 && n4 > n3 && (s2 & 8) != 0) {
                    d2.j |= 8;
                }
                if (!d2.e && !d2.d && d2.i == 0 && n4 >= n3 && (s2 & 0xC0) != 0) {
                    d2.j |= 0xC0;
                }
            }
        }
    }

    public final void a(h h2) {
        int n2 = this.a.length;
        for (int i2 = 0; i2 < n2; ++i2) {
            if (!this.a[i2].a.equals(h2)) continue;
            this.a[i2].b();
        }
    }

    public static void a(h h2, h h3) {
        if (h2.j == 1) {
            ((d)h2.a).a(h3);
        }
        if (h3.j == 1) {
            ((d)h3.a).a(h2);
        }
        if (h2.j == 1 && h3.j == 4) {
            ((d)h2.a).b = true;
            return;
        }
        if (h3.j == 1 && h2.j == 4) {
            ((d)h3.a).b = true;
        }
    }

    public final void a(h object, as object2) {
        if (((h)object).j == 1 && ((as)object2).d == 2) {
            ((d)((h)object).a).a((ae)((as)object2).a);
            return;
        }
        if (((h)object).j == 3 && ((as)object2).d == 2) {
            object2 = (ae)((as)object2).a;
            object = (ax)((h)object).a;
            if (!(((ax)object).a.b == 0 || ((ae)object2).a() || ((ae)object2).d == 2 && ((ax)object).b != 4 || ((ae)object2).d == 5 || ((ax)object).a.c().a() <= 0xA00000L)) {
                int n2 = 0;
                while (n2 < ((ax)object).a.a.length) {
                    int n3 = n2++;
                    ((ax)object).a.e[n3] = ((ax)object).a.e[n3] + 30;
                }
                Object object3 = ((ae)object2).a;
                object3 = ((as)object3).a.a;
                ((ax)object).a.a.a(((bg)object3).a >> 10, ((bg)object3).b >> 10, 30, 30);
                ((ae)object2).b();
            }
            return;
        }
        if (this.b == 0 && ((h)object).j == 3 && ((as)object2).d == 5 && this.a.c <= 1) {
            ++this.a.c;
            this.a.c(10);
            return;
        }
        if (this.e != 4 && this.e != 5 && ((h)object).j == 1 && (((d)((h)object).a).a != 1 || ((d)((h)object).a).e == 0) && ((as)object2).d == 6) {
            this.a.b(((as)object2).c);
            ((as)object2).a.b |= 0x10;
            object2 = (d)((as)object2).a;
            --((d)object2).g;
            if (((d)object2).g == 0) {
                ((d)object2).a = null;
                object = ((d)((h)object).a).a.a();
                int n4 = ((bg)object).a >> 15;
                int n5 = ((bg)object).b >> 15;
                if (this.a.a(n4, n5 - 1) != 0) {
                    ((d)object2).a(n4 << 5, n5 - 1 << 5);
                } else if (this.a.a(n4 - 1, n5) != 0) {
                    ((d)object2).a(n4 - 1 << 5, n5 << 5);
                } else if (this.a.a(n4 + 1, n5) != 0) {
                    ((d)object2).a(n4 + 1 << 5, n5 << 5);
                } else {
                    ((d)object2).a(n4 << 5, n5 + 1 << 5);
                }
                ((d)object2).d >>= 2;
                ((d)object2).d = (byte)2;
            }
        }
    }
}

