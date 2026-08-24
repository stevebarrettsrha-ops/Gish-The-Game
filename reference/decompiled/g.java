/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Canvas
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import com.hardwire.blob.Main;
import java.io.DataInputStream;
import java.io.FilterInputStream;
import java.io.InputStream;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class g
extends Canvas
implements i {
    public static g a;
    private Main a;
    private at a;
    public static int a;
    public static int b;
    public static int c;
    public static int d;
    public Image[] a;
    public int e;
    private Image[] b;
    private int k = -1;
    private Image[] c;
    private int[] c;
    private boolean[] a;
    private int l;
    public boolean a;
    private int m;
    private int n;
    public boolean b;
    public boolean c = false;
    public byte[][] a;
    private int o;
    public int f;
    private int p;
    private byte a;
    private long a;
    public int g;
    private int q;
    private int r;
    private int s;
    private static char[][] a;
    private short a;
    public StringBuffer a;
    private long b;
    private int t;
    public int h;
    private int u;
    public boolean d;
    public boolean e;
    public int i;
    public int j;
    public int[] a;
    public int[] b = true;
    public boolean f = false;
    public boolean g = false;
    private byte[] a;
    private InputStream a = null;
    public boolean h = false;

    public g(Main main) {
        this.b = new int[6];
        a = this;
        this.a = main;
        this.a = this.a.a;
        this.setFullScreenMode(true);
        this.b();
    }

    public final void a() {
        int n2;
        this.m = 0;
        this.a("/images.img");
        this.a = new Image[4];
        for (n2 = 0; n2 < 3; ++n2) {
            this.a[n2] = this.a();
        }
        this.b = new Image[4];
        for (n2 = 0; n2 < this.b.length; ++n2) {
            this.b[n2] = this.a();
        }
        this.n = (a - (this.b[1].getWidth() << 1)) * 9 / 10;
    }

    public final void b() {
        a = this.getWidth();
        b = this.getHeight();
        c = a >> 1;
        d = b >> 1;
    }

    public final Image a(int n2) {
        return this.c[this.c[n2]];
    }

    public final void c() {
        try {
            short s2;
            int n2 = 0;
            this.c = new int[1024];
            int n3 = 0;
            int n4 = 0;
            for (n2 = 0; n2 < 1024; ++n2) {
                this.c[n2] = -1;
            }
            Object object = null;
            try {
                object = new DataInputStream(Main.a("/images.map"));
                n3 = ((DataInputStream)object).readShort();
                for (n2 = 0; n2 < n3; ++n2) {
                    s2 = ((DataInputStream)object).readShort();
                    this.c[s2] = n2;
                }
            }
            catch (Exception exception) {}
            try {
                ((FilterInputStream)object).close();
            }
            catch (Exception exception) {}
            try {
                object = new DataInputStream(Main.a("/images2.map"));
                n4 = ((DataInputStream)object).readShort();
                for (n2 = 0; n2 < n4; ++n2) {
                    s2 = ((DataInputStream)object).readShort();
                    this.c[s2 + 256] = n3 + n2;
                }
            }
            catch (Exception exception) {}
            try {
                ((FilterInputStream)object).close();
            }
            catch (Exception exception) {}
            this.c = new Image[n3 + n4];
            for (n2 = 7; n2 < n3; ++n2) {
                this.c[n2] = this.a();
                object = this;
                ((g)object).b(1);
            }
            this.p();
            this.l = n3;
            this.a = new boolean[n4];
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    public final void d() {
        try {
            int n2;
            for (n2 = 0; n2 < this.a.length; ++n2) {
                if (this.a[n2]) continue;
                this.c[n2 + this.l] = null;
            }
            System.gc();
            n2 = 0;
            int n3 = -1;
            for (int i2 = 0; i2 < this.a.length; ++i2) {
                if (!this.a[i2] || this.c[i2 + this.l] != null) continue;
                if (i2 + this.l == this.c[ab.a[2] + 6]) {
                    this.c[i2 + this.l] = Image.createImage((String)"/img_tiles/fg/6_alpha.png");
                    continue;
                }
                if (i2 + this.l == this.c[ab.a[2] + 7]) {
                    this.c[i2 + this.l] = Image.createImage((String)"/img_tiles/fg/7_alpha.png");
                    continue;
                }
                if (i2 + this.l == this.c[ab.a[2] + 36]) {
                    this.c[i2 + this.l] = Image.createImage((String)"/img_tiles/fg/36_alpha.png");
                    continue;
                }
                if (i2 + this.l == this.c[471]) {
                    this.c[i2 + this.l] = Image.createImage((String)"/img_gish/dark_corner_alpha.png");
                    this.a.a.a.a();
                    continue;
                }
                if (n2 == 0) {
                    this.a("/images2.img");
                    n2 = 1;
                }
                while (n3 < i2 - 1) {
                    int n4;
                    g g2 = this;
                    int n5 = 256 * g2.a.read() + g2.a.read();
                    int n6 = g2.a.read() * 3;
                    for (n4 = 0; n4 < 10; ++n4) {
                        g2.a.read();
                    }
                    for (n4 = 0; n4 < n6 + 4; ++n4) {
                        g2.a.read();
                    }
                    for (n4 = 0; n4 < n5 + 4; ++n4) {
                        g2.a.read();
                    }
                    ++n3;
                }
                this.c[i2 + this.l] = this.a();
                n3 = i2;
            }
            if (n2 != 0) {
                this.p();
            }
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    public final void a(int n2) {
        if (n2 < 256) {
            return;
        }
        if (n2 == ab.a[0] + 8 || n2 == ab.a[0] + 9) {
            for (n2 = 0; n2 < 2; ++n2) {
                this.a[this.c[ab.a[0] + 8 + n2] - this.l] = true;
            }
            return;
        }
        if (n2 == ab.a[0] + 30) {
            for (n2 = 0; n2 < 4; ++n2) {
                this.a[this.c[n2 + 272] - this.l] = true;
            }
            return;
        }
        if (n2 == ab.a[2] + 7) {
            this.a[this.c[n2] - this.l] = true;
            for (n2 = 0; n2 < 4; ++n2) {
                this.a[this.c[n2 + 464] - this.l] = true;
            }
            return;
        }
        if ((n2 = this.c[n2] - this.l) >= 0) {
            this.a[n2] = true;
        }
    }

    public final void e() {
        for (int i2 = 0; i2 < this.a.length; ++i2) {
            this.a[i2] = false;
        }
    }

    public final void f() {
        byte[][][] byArray = ab.a[this.a.a.a.j];
        int n2 = byArray[0].length;
        int n3 = byArray[0][0].length;
        for (int i2 = 0; i2 < 3; ++i2) {
            for (int i3 = 0; i3 < n2; ++i3) {
                for (int i4 = 0; i4 < n3; ++i4) {
                    this.a(ab.a[i2] + byArray[i2][i3][i4]);
                }
            }
        }
    }

    public final Image[] a(int n2, int n3, boolean bl) {
        Image[] imageArray;
        if (bl) {
            imageArray = new Image[n3 << 1];
            for (int i2 = 0; i2 < n3; ++i2) {
                Image image;
                imageArray[i2 << 1] = image = this.a(n2 + i2);
                imageArray[(i2 << 1) + 1] = Image.createImage((Image)image, (int)0, (int)0, (int)image.getWidth(), (int)image.getHeight(), (int)2);
            }
        } else {
            imageArray = new Image[n3];
            for (int i3 = 0; i3 < n3; ++i3) {
                imageArray[i3] = this.a(n2 + i3);
            }
        }
        return imageArray;
    }

    public static void a(Graphics graphics, Image[] image, int n2, int n3, int n4, boolean bl, boolean bl2, int n5) {
        if (bl2) {
            graphics.drawImage(image[(n4 << 1) + (bl ? 1 : 0)], n2, n3, n5);
            return;
        }
        image = image[n4];
        graphics.drawRegion(image, 0, 0, image.getWidth(), image.getHeight(), bl ? 2 : 0, n2, n3, n5);
    }

    public final Image[] a(int n2) {
        Image[] imageArray = new Image[16];
        for (int i2 = 0; i2 < imageArray.length; ++i2) {
            if (i2 < 4) {
                imageArray[i2] = this.a(i2 + 145);
                continue;
            }
            Image image = this.a(145 + (i2 & 3));
            int n3 = image.getWidth();
            int n4 = image.getHeight();
            imageArray[i2] = Image.createImage((Image)image, (int)0, (int)0, (int)n3, (int)n4, (int)(i2 < 8 ? 5 : (i2 < 12 ? 3 : 6)));
        }
        return imageArray;
    }

    public static void a(Graphics graphics, Image[] imageArray, int n2, int n3, int n4) {
        n4 = al.a(n4 + 205887);
        n4 = (n4 << 4) / 6588397;
        graphics.drawImage(imageArray[n4], n2, n3, 3);
    }

    public final Image[] a(int n2, boolean bl) {
        Image[] imageArray = new Image[32];
        for (int i2 = 0; i2 < imageArray.length; ++i2) {
            if (i2 < 8) {
                imageArray[i2] = this.a(n2 + i2);
                continue;
            }
            Image image = this.a(n2 + (i2 & 7));
            int n3 = image.getWidth();
            int n4 = image.getHeight();
            imageArray[i2] = Image.createImage((Image)image, (int)0, (int)0, (int)n3, (int)n4, (int)(i2 < 16 ? 5 : (i2 < 24 ? 3 : 6)));
        }
        return imageArray;
    }

    public static void a(Graphics graphics, Image[] image, bg bg2, int n2) {
        n2 = al.a(n2 + 102943);
        n2 = (n2 << 5) / 6588397;
        if (((Image[])image).length > 8 || n2 < 8) {
            graphics.drawImage(image[n2], bg2.a, bg2.b, 3);
            return;
        }
        image = image[n2 & 7];
        n2 = n2 < 16 ? 5 : (n2 < 24 ? 3 : 6);
        graphics.drawRegion(image, 0, 0, image.getWidth(), image.getHeight(), n2, bg2.a, bg2.b, 3);
    }

    public final Image b(int n2) {
        Image image = Image.createImage((int)(this.a.f(52) + 1), (int)(at.e(0) + 1));
        Graphics graphics = image.getGraphics();
        graphics.setColor(-13684945);
        graphics.fillRect(0, 0, image.getWidth(), image.getHeight());
        this.a.a(graphics, 52, 1, 0, 0);
        return image;
    }

    public final void g() {
        this.b(1);
    }

    public final void b(int n2) {
        this.m += n2;
        if (this.k * this.n / 196 != this.m * this.n / 196) {
            this.n();
        }
    }

    public final void h() {
        this.m = 196;
        this.n();
        for (int i2 = 0; i2 < this.b.length; ++i2) {
            this.b[i2] = null;
        }
        this.b = null;
    }

    protected final void paint(Graphics graphics) {
        if (this.h) {
            return;
        }
        this.setFullScreenMode(true);
        this.b();
        graphics.setClip(0, 0, a, b);
        if (this.a) {
            graphics.setColor(0);
            graphics.fillRect(0, 0, a, b);
            this.a.a(graphics, 3, c, d, 3);
            this.c = false;
            return;
        }
        switch (this.a.a) {
            case 6: {
                s.a.a(graphics, a, b);
                break;
            }
            case 0: {
                this.a.a.a.a(graphics);
                break;
            }
            case 1: {
                this.a.a.b(graphics);
                break;
            }
            case 2: {
                int[] nArray = new int[]{1912910, 0xFFFFFF, 0xFFFFFF, 0};
                int n2 = this.a != null && this.e >= this.a.length ? this.a.length - 1 : this.e;
                graphics.setColor(nArray[n2]);
                graphics.fillRect(0, 0, a, b);
                if (this.a == null || this.a[n2] == null) break;
                graphics.drawImage(this.a[n2], c, d, 3);
                break;
            }
            case 3: {
                int n3;
                if (this.b == null) {
                    return;
                }
                if (this.b) {
                    graphics.setColor(0);
                    graphics.fillRect(0, 0, a, b);
                }
                int n4 = n3 + this.m * this.n / 196;
                if (this.b) {
                    graphics.drawImage(this.b[1], n3, d, 10);
                    int n5 = n3 + this.n;
                    for (n3 = c - (this.n >> 1); n3 < n4; ++n3) {
                        graphics.drawImage(this.b[0], n3, d, 6);
                    }
                    while (n3 < n5) {
                        graphics.drawImage(this.b[2], n3, d, 6);
                        ++n3;
                    }
                    graphics.drawImage(this.b[3], n3, d, 6);
                } else {
                    n3 += this.k * this.n / 196;
                    while (n3 < n4) {
                        graphics.drawImage(this.b[0], n3, d, 6);
                        ++n3;
                    }
                }
                if (this.m == 196) {
                    g.a(graphics, this.b, c - (this.n >> 1) + this.n + (this.b[1].getWidth() >> 1), d, 1, true, false, 3);
                }
                this.k = this.m;
                this.b = false;
            }
        }
        this.c = false;
    }

    public final void a(short s2, String string, int n2) {
        this.a = s2;
        this.u = -1;
        this.a = new StringBuffer(string);
        this.h = n2;
    }

    public final void a(Graphics graphics, int n2, int n3, int n4, int n5) {
        this.a.a(graphics, this.a, n2, n3, 0);
        graphics.setColor(n4);
        Object object = at.a(0, this.a.toString());
        n5 = a - n2 * 2;
        int n6 = at.a(0, object);
        n6 -= n5;
        int n7 = 0;
        while (n6 > 0) {
            n6 -= at.a(0, object[n7]);
            ++n7;
        }
        object = this.a.toString().substring(n7);
        graphics.fillRect(n2, n3 += 1 + at.e(this.a.a(this.a)), n5, at.e(0));
        this.a.a(graphics, 0, (String)object, n2 + 1, n3 - 1, 0);
    }

    public final void c(int n2) {
        int n3 = n2 == 35 || n2 == -8 ? 1 : 0;
        if (n3 != 0) {
            if (this.a.length() > 0) {
                this.a.deleteCharAt(this.a.length() - 1);
            }
            this.u = -1;
            return;
        }
        if (this.a.length() < this.h && (n2 == 35 || n2 == 42 || n2 >= 48 && n2 <= 57)) {
            n3 = n2 - 46;
            if (n2 == 42) {
                n3 += 5;
            } else if (n2 == 35) {
                n3 += 11;
            }
            if (this.u != n3 || System.currentTimeMillis() - this.b > 400L) {
                this.a.append(a[n3][0]);
                this.t = 1;
            } else if (this.t < a[n3].length) {
                this.a.setCharAt(this.a.length() - 1, a[n3][this.t]);
                ++this.t;
            }
            this.u = n3;
            this.b = System.currentTimeMillis();
        }
    }

    public final void a(byte[][] byArray, int n2, int n3, int n4, int n5) {
        this.a = byArray;
        this.p = 3;
        this.f = 0;
        n2 = at.e(3) + 1;
        this.o = n5 / n2;
        if (this.o > byArray.length) {
            this.o = byArray.length;
        }
        this.q = 0;
        for (n3 = 0; n3 < byArray.length; ++n3) {
            n5 = at.a(3, byArray[n3]);
            if (this.q >= n5) continue;
            this.q = n5;
        }
        this.a = 0;
        this.g = this.o * n2;
        this.r = 3;
        this.s = n4;
    }

    public final void b(Graphics graphics, int n2, int n3, int n4, int n5) {
        byte[][] byArray = this.a;
        int n6 = this.f + Math.min(byArray.length, this.o);
        n3 += (n4 >> 1) - ((n6 - this.f) * (1 + at.e(this.p)) >> 1);
        n4 = n3;
        int n7 = this.f;
        while (n7 < n6) {
            if (byArray[n7].length > 0) {
                if (byArray[n7][0] == -3) {
                    graphics.setColor(this.s);
                    graphics.fillRect(n2, n4 - 1, at.a(this.r, byArray[n7]) + 1, at.e(this.r) + 2);
                    this.a.a(graphics, this.r, byArray[n7], n2 + 1, n4, 20);
                    ++n4;
                } else {
                    this.a.a(graphics, this.p, byArray[n7], n2, n4, 20);
                }
            }
            ++n7;
            n4 += 1 + at.e(this.p);
        }
        if (byArray.length > this.o) {
            n7 = n5;
            n4 = n3;
            n2 = this.g;
            ++n7;
            ++n4;
            graphics.setColor(255, 255, 255);
            n2 = 2 + n2 * (this.o - 1) / (byArray.length - 1);
            graphics.fillRect(--n7, n4 += (n2 -= 2) * this.f / (byArray.length - 1), 4, n2);
        }
    }

    public final void i() {
        if (this.a == 0) {
            return;
        }
        if (System.currentTimeMillis() - this.a > 100L) {
            if (this.a == 1) {
                if (this.f + this.o < this.a.length) {
                    ++this.f;
                    this.repaint();
                }
            } else if (this.f > 0) {
                --this.f;
                this.repaint();
            }
            this.a = System.currentTimeMillis();
        }
    }

    public final void j() {
        this.a = (byte)-1;
        this.a = 0L;
        this.i();
    }

    public final void k() {
        this.a = 1;
        this.a = 0L;
        this.i();
    }

    public final void l() {
        this.a = 0;
    }

    public static boolean a(int n2) {
        return n2 == -7 || n2 > 20 && n2 == 7;
    }

    public static boolean b(int n2) {
        return n2 == -5;
    }

    public static boolean c(int n2) {
        return n2 == -6 || n2 > 20 && n2 == 6;
    }

    public final int a(int n2) {
        switch (n2) {
            case 49: {
                return 4;
            }
            case -1: 
            case 50: {
                return 0;
            }
            case 51: {
                return 5;
            }
            case -3: 
            case 52: {
                return 2;
            }
            case -5: 
            case 53: {
                if (this.a.a == 0 && this.a.a.c == 0) {
                    return 9;
                }
                return 8;
            }
            case -4: 
            case 54: {
                return 3;
            }
            case 55: {
                return 6;
            }
            case -2: 
            case 56: {
                return 1;
            }
            case 57: {
                return 7;
            }
            case 42: {
                return 9;
            }
            case 48: {
                return 11;
            }
            case 35: {
                return 10;
            }
        }
        return Integer.MIN_VALUE;
    }

    public static int b(int n2) {
        switch (n2) {
            case 2: {
                return 52;
            }
            case 3: {
                return 54;
            }
            case 0: {
                return 50;
            }
            case 1: {
                return 56;
            }
            case 4: {
                return 49;
            }
            case 5: {
                return 51;
            }
            case 6: {
                return 55;
            }
            case 7: {
                return 57;
            }
            case 8: {
                return 53;
            }
            case 9: {
                return 42;
            }
            case 10: {
                return 35;
            }
            case 11: {
                return 48;
            }
        }
        return Integer.MIN_VALUE;
    }

    public final void m() {
        int n2;
        for (n2 = 0; n2 < this.a.length; ++n2) {
            this.a[n2] = 0;
        }
        for (n2 = 0; n2 < this.b.length; ++n2) {
            this.b[n2] = 0;
        }
    }

    private void o() {
        if (this.a.a != 1 || this.a.a.a != 5) {
            return;
        }
        this.m();
        try {
            int n2;
            this.a.b("achi");
            this.a.a.writeInt(35);
            this.a.a.writeInt(27);
            this.a.a.writeInt(40);
            for (n2 = 0; n2 < Main.a.length; ++n2) {
                for (int i2 = 0; i2 < Main.a[n2]; ++i2) {
                    this.a.a.writeBoolean(true);
                }
            }
            for (n2 = 0; n2 < Main.e.length; ++n2) {
                this.a.a.writeBoolean(Main.e[n2] != 0);
            }
            this.a.a(true);
        }
        catch (Exception exception) {}
        this.a.a.a = this.a.a.a() - 3;
        this.a.a.a(g.b(8));
    }

    protected final void keyPressed(int n2) {
        if (this.a) {
            return;
        }
        for (int i2 = 0; i2 < this.a.length - 1; ++i2) {
            this.a[i2] = this.a[i2 + 1];
        }
        this.a[this.a.length - 1] = n2;
        if (this.a[0] == 49 && this.a[1] == 51 && this.a[2] == 57 && this.a[3] == 49 && this.a[4] == 57 && this.a[5] == 49) {
            this.o();
        }
        switch (this.a.a) {
            case 0: {
                this.a.a.a.a(n2);
                return;
            }
            case 1: {
                this.a.a.a(n2);
                return;
            }
            case 2: {
                ++this.e;
                return;
            }
            case 6: {
                s.a.b(n2, this.a(n2));
            }
        }
    }

    protected final void keyReleased(int n2) {
        if (this.a) {
            return;
        }
        try {
            switch (this.a.a) {
                case 0: {
                    this.a.a.a.b(n2);
                    break;
                }
                case 1: {
                    this.a.a.b(n2);
                }
                default: {
                    return;
                }
            }
        }
        catch (Exception exception) {}
    }

    public final void a(b b2) {
        if (this.a) {
            return;
        }
        try {
            switch (this.a.a) {
                case 0: {
                    this.a.a.a.a(b2);
                    break;
                }
                case 1: {
                    this.a.a.a(b2);
                }
                default: {
                    return;
                }
            }
        }
        catch (Exception exception) {}
    }

    public final void b(b b2) {
        if (this.a) {
            return;
        }
        try {
            switch (this.a.a) {
                case 0: {
                    this.a.a.a.b(b2);
                    break;
                }
                case 1: {
                    this.a.a.b(b2);
                }
                default: {
                    return;
                }
            }
        }
        catch (Exception exception) {}
    }

    public final void a(am am2) {
        if (this.a) {
            return;
        }
        try {
            for (int i2 = 0; i2 < this.a.length; ++i2) {
                this.a[i2] = 0;
            }
            switch (this.a.a) {
                case 0: {
                    this.a.a.a.a(am2);
                }
            }
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    protected final void pointerPressed(int n2, int n3) {
        try {
            if (this.a) {
                return;
            }
            switch (this.a.a) {
                case 0: {
                    this.a.a.a.a(n2, n3);
                    break;
                }
                case 1: {
                    this.a.a.a(n2, n3);
                    break;
                }
                case 2: {
                    ++this.e;
                    break;
                }
                case 6: {
                    s.a.a(n2, n3);
                }
                default: {
                    return;
                }
            }
        }
        catch (Exception exception) {}
    }

    protected final void pointerReleased(int n2, int n3) {
        try {
            int n4 = 0;
            if (n2 < a / 8 && n3 < b / 6) {
                n4 = 1;
            } else if (n2 > a * 7 / 8 && n3 < b / 6) {
                n4 = 2;
            } else if (n2 > a * 7 / 8 && n3 > b * 5 / 6) {
                n4 = 4;
            } else if (n2 < a / 8 && n3 > b * 5 / 6) {
                n4 = 3;
            }
            for (int i2 = 0; i2 < this.b.length - 1; ++i2) {
                this.b[i2] = this.b[i2 + 1];
            }
            this.b[this.b.length - 1] = n4;
            if (this.a) {
                return;
            }
            if (this.b[0] == 1 && this.b[1] == 2 && this.b[2] == 1 && this.b[3] == 1 && this.b[4] == 2 && this.b[5] == 2) {
                this.o();
                return;
            }
            this.d = false;
            if (this.e) {
                this.e = false;
                g g2 = this;
                this.a = 0;
                return;
            }
            switch (this.a.a) {
                case 0: {
                    this.a.a.a.b();
                    break;
                }
                case 1: {
                    this.a.a.b(n2, n3);
                }
                default: {
                    return;
                }
            }
        }
        catch (Exception exception) {}
    }

    protected final void pointerDragged(int n2, int n3) {
        try {
            if (this.a) {
                return;
            }
            if (this.d) {
                if (this.a.a == 1 && this.a.a.b == 0) {
                    int n4;
                    int n5 = this.j + (this.i - n3) / this.a.a.d;
                    if (n5 != this.a.a.c) {
                        this.e = true;
                    }
                    if (n5 + this.a.a.b >= (n4 = this.a.a.a())) {
                        n5 = n4 - this.a.a.b;
                    }
                    if (n5 < 0) {
                        n5 = 0;
                    }
                    if (this.a.a.c != n5) {
                        this.a.a.c = n5;
                        this.a.a.a = -1;
                    }
                } else if (this.a.a == 1 && this.a.a.b == 1 || this.a.a == 0 && this.a.a.c == 6) {
                    int n6;
                    int n7 = this.j + (this.i - n3) / (1 + at.e(this.p));
                    if (n7 != this.f) {
                        this.e = true;
                    }
                    if (n7 + this.o >= (n6 = this.a.length)) {
                        n7 = n6 - this.o;
                    }
                    if (n7 < 0) {
                        n7 = 0;
                    }
                    if (this.f != n7) {
                        this.f = n7;
                        this.repaint();
                    }
                }
                if (this.e) {
                    return;
                }
            }
            switch (this.a.a) {
                case 0: {
                    this.a.a.a.b(n2, n3);
                }
            }
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    protected final void hideNotify() {
        this.a.c();
    }

    protected final void showNotify() {
        this.a.d();
    }

    private void a(String string) {
        this.a = Main.a(string);
    }

    private void p() {
        try {
            this.a = null;
            this.a.close();
            this.a = null;
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    private Image a() {
        int n2 = 0;
        try {
            int n3;
            int n4 = 256 * (this.a.read() & 0xFF) + (this.a.read() & 0xFF);
            int n5 = (this.a.read() & 0xFF) * 3;
            int n6 = this.a.read() & 0xFF;
            n2 = n4 + n5 + 69 + n6 * 13;
            if (this.a == null || n2 > this.a.length) {
                this.a = null;
                this.a = new byte[n2];
            }
            this.a[0] = -119;
            this.a[1] = 80;
            this.a[2] = 78;
            this.a[3] = 71;
            this.a[4] = 13;
            this.a[5] = 10;
            this.a[6] = 26;
            this.a[7] = 10;
            this.a[8] = 0;
            this.a[9] = 0;
            this.a[10] = 0;
            this.a[11] = 13;
            this.a[12] = 73;
            this.a[13] = 72;
            this.a[14] = 68;
            this.a[15] = 82;
            this.a[16] = 0;
            this.a[17] = 0;
            this.a[18] = (byte)this.a.read();
            this.a[19] = (byte)this.a.read();
            this.a[20] = 0;
            this.a[21] = 0;
            this.a[22] = (byte)this.a.read();
            this.a[23] = (byte)this.a.read();
            this.a[24] = (byte)this.a.read();
            this.a[25] = 3;
            this.a[26] = 0;
            this.a[27] = 0;
            this.a[28] = 0;
            this.a[29] = (byte)this.a.read();
            this.a[30] = (byte)this.a.read();
            this.a[31] = (byte)this.a.read();
            this.a[32] = (byte)this.a.read();
            this.a[33] = 0;
            this.a[34] = 0;
            this.a[35] = (byte)(n5 >> 8);
            this.a[36] = (byte)n5;
            this.a[37] = 80;
            this.a[38] = 76;
            this.a[39] = 84;
            this.a[40] = 69;
            for (n3 = 0; n3 < n5 + 4; ++n3) {
                this.a[n3 + 41] = (byte)this.a.read();
            }
            n3 = n5 + 41 + 4;
            if (n6 == 1) {
                this.a[n3++] = 0;
                this.a[n3++] = 0;
                this.a[n3++] = 0;
                this.a[n3++] = 1;
                this.a[n3++] = 116;
                this.a[n3++] = 82;
                this.a[n3++] = 78;
                this.a[n3++] = 83;
                this.a[n3++] = 0;
                this.a[n3++] = 64;
                this.a[n3++] = -26;
                this.a[n3++] = -40;
                this.a[n3++] = 102;
            }
            this.a[n3++] = 0;
            this.a[n3++] = 0;
            this.a[n3++] = (byte)(n4 >> 8);
            this.a[n3++] = (byte)n4;
            this.a[n3++] = 73;
            this.a[n3++] = 68;
            this.a[n3++] = 65;
            this.a[n3++] = 84;
            for (n5 = 0; n5 < n4 + 4; ++n5) {
                this.a[n3++] = (byte)this.a.read();
            }
            this.a[n3++] = 0;
            this.a[n3++] = 0;
            this.a[n3++] = 0;
            this.a[n3++] = 0;
            this.a[n3++] = 73;
            this.a[n3++] = 69;
            this.a[n3++] = 78;
            this.a[n3++] = 68;
            this.a[n3++] = -82;
            this.a[n3++] = 66;
            this.a[n3++] = 96;
            this.a[n3] = -126;
        }
        catch (Exception exception) {}
        if (this.a == null) {
            return null;
        }
        return Image.createImage((byte[])this.a, (int)0, (int)n2);
    }

    public final void n() {
        if (!this.isShown()) {
            return;
        }
        this.c = true;
        this.repaint();
        Thread.yield();
        while (this.c) {
            try {
                Thread.sleep(1L);
            }
            catch (InterruptedException interruptedException) {}
        }
    }

    protected final void sizeChanged(int n2, int n3) {
        if (!this.a.b) {
            return;
        }
        new Thread(new aj(this, this.a, null)).start();
    }

    static {
        a = new char[][]{{' '}, {'.', '/', ':', '-', '_'}, {'0'}, {'1', '.', '/', ':', '-', '_', '@'}, {'a', 'b', 'c', '2'}, {'d', 'e', 'f', '3'}, {'g', 'h', 'i', '4'}, {'j', 'k', 'l', '5'}, {'m', 'n', 'o', '6'}, {'p', 'q', 'r', 's', '7'}, {'t', 'u', 'v', '8'}, {'w', 'x', 'y', 'z', '9'}};
    }
}

