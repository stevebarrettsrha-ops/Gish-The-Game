/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Font
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 *  javax.microedition.midlet.MIDlet
 */
import com.hardwire.blob.Main;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.midlet.MIDlet;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class ak {
    public static boolean a;
    private static String a;
    private static boolean b;
    private static boolean[] a;
    private static String[] a;
    private static String[] b;
    private static String[] c;
    private static boolean c;
    private static String b;
    private static String c;
    private static int a;
    private static int b;
    private static int c;
    private static int d;
    private static int e;
    private static int f;
    private static boolean d;
    private static boolean e;
    private static String d;
    private static String e;
    private static String f;
    private static String g;
    private static String h;
    private static String i;
    private static String j;
    private static String k;
    private static Image[] a;
    private static Image a;
    private static Image b;
    private static Font a;
    private static Font b;
    private static Font c;
    private int g;
    private int h;
    private int i;
    private static int j;
    private l a = new l();
    private l b = new l();
    private l c = new l();
    private l d = new l();
    private l e = new l();
    private int k = -1;
    private int l;
    private static Vector a;

    public static void a(MIDlet mIDlet) {
        Object object = Main.a("/mi");
        if (object == null) {
            return;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] byArray = new byte[1024];
        try {
            int n2;
            while ((n2 = ((InputStream)object).read(byArray)) > 0) {
                byteArrayOutputStream.write(byArray, 0, n2);
            }
        }
        catch (IOException iOException) {}
        try {
            ((InputStream)object).close();
        }
        catch (IOException iOException) {}
        a = true;
        object = byteArrayOutputStream.toByteArray();
        int n3 = 0;
        if (((Object)object).length >= 3 && (object[0] & 0xFF) == 239 && (object[1] & 0xFF) == 187 && (object[2] & 0xFF) == 191) {
            n3 = 3;
        }
        String string = null;
        String string2 = null;
        StringBuffer stringBuffer = new StringBuffer();
        while (n3 < ((Object)object).length) {
            Object object2;
            if (((object2 = object[n3++]) & 0x80) == 0) {
                object2 = (char)object2;
            } else {
                Object object3 = object[n3++];
                if ((object2 & 0x20) == 0) {
                    object2 = (char)((object2 & 0x1F) << 6 | object3 & 0x3F);
                } else {
                    Object object4 = object[n3++];
                    object2 = (char)((object2 & 0xF) << 12 | (object3 & 0x3F) << 6 | object4 & 0x3F);
                }
            }
            if (object2 == 61 && string == null) {
                string = stringBuffer.toString().trim();
                stringBuffer.setLength(0);
            } else if (object2 == 10) {
                string2 = stringBuffer.toString().trim();
                stringBuffer.setLength(0);
            } else {
                stringBuffer.append((char)object2);
            }
            if (n3 == ((Object)object).length && string != null && string2 == null) {
                string2 = stringBuffer.toString().trim();
                stringBuffer.setLength(0);
            }
            if (string == null || string2 == null) continue;
            String string3 = string2;
            String string4 = string;
            MIDlet mIDlet2 = mIDlet;
            if (string4.equals("moregames.url")) {
                a = string3;
            } else if (string4.equals("moregames.url.jad.key")) {
                a = mIDlet2.getAppProperty(string3);
                if (a == null) {
                    b = true;
                } else if (!(a = a.trim()).toLowerCase().startsWith("http://")) {
                    b = true;
                    a = null;
                }
            } else if (string4.startsWith("moregames.game.")) {
                int n4 = string4.charAt("moregames.game.".length()) - 48;
                ak.a[n4 - 1] = true;
                if (string4.endsWith(".name")) {
                    ak.a[n4 - 1] = string3;
                } else if (string4.endsWith(".desc")) {
                    ak.b[n4 - 1] = string3;
                } else if (string4.endsWith(".link")) {
                    ak.c[n4 - 1] = string3;
                } else if (string4.endsWith(".link.jad.key")) {
                    ak.c[n4 - 1] = mIDlet2.getAppProperty(string3);
                    if (c[n4 - 1] == null) {
                        b = true;
                    } else {
                        ak.c[n4 - 1] = c[n4 - 1].trim();
                        if (!c[n4 - 1].toLowerCase().startsWith("http://")) {
                            b = true;
                            ak.c[n4 - 1] = null;
                        }
                    }
                }
            } else if (string4.equals("moregames.operator.name")) {
                c = true;
            } else if (string4.equals("moregames.operator.desc")) {
                b = string3;
                c = true;
            } else if (string4.equals("moregames.operator.link")) {
                c = string3;
                c = true;
            } else if (string4.equals("moregames.operator.link.jad.key")) {
                c = mIDlet2.getAppProperty(string3);
                if (c == null) {
                    b = true;
                } else {
                    c = c.trim();
                }
                c = true;
            } else if (string4.equals("moregames.color.game.bg")) {
                a = Integer.parseInt(string3, 16);
            } else if (string4.equals("moregames.color.game.desc")) {
                b = Integer.parseInt(string3, 16);
            } else if (string4.equals("moregames.color.operator.bg")) {
                c = Integer.parseInt(string3, 16);
            } else if (string4.equals("moregames.color.operator.desc")) {
                d = Integer.parseInt(string3, 16);
            } else if (string4.equals("moregames.color.na.bg")) {
                e = Integer.parseInt(string3, 16);
            } else if (string4.equals("moregames.color.na.desc")) {
                f = Integer.parseInt(string3, 16);
            } else if (string4.equals("moregames.device.platformrequest")) {
                d = string3.toLowerCase().equals("true");
            } else if (string4.equals("moregames.force.quit")) {
                e = string3.toLowerCase().equals("true");
            } else if (string4.equals("moregames.text.visit")) {
                d = string3;
            } else if (string4.equals("moregames.text.back")) {
                e = string3;
            } else if (string4.equals("moregames.text.yes")) {
                f = string3;
            } else if (string4.equals("moregames.text.no")) {
                g = string3;
            } else if (string4.equals("moregames.text.exitapp")) {
                h = string3;
            } else if (string4.equals("moregames.text.nobrowser")) {
                i = string3;
            } else if (string4.equals("moregames.text.jarsize1")) {
                j = string3;
            } else if (string4.equals("moregames.text.jarsize2")) {
                k = string3;
            }
            string = null;
            string2 = null;
        }
        for (int i2 = 0; i2 < a.length; ++i2) {
            if (!a[i2]) continue;
            ++j;
        }
        if (c) {
            ++j;
        }
        if (b) {
            a = false;
        }
        System.out.println("MoreGames.enabled = " + a);
    }

    private static Image a(String string) {
        try {
            string = Image.createImage((String)("/mg/" + string + ".png"));
            return string;
        }
        catch (Exception exception) {
            return null;
        }
    }

    public final void a() {
        this.k = -1;
        if (a != null) {
            if (d) {
                if (e && h != null) {
                    b = ak.a("softkeyicons");
                    this.g = 2;
                    s.e();
                    return;
                }
                s.a(a, e);
                return;
            }
            b = ak.a("softkeyicons");
            this.g = 3;
            s.e();
            return;
        }
        this.h = 0;
        this.g = 0;
        s.e();
        if (j == 0) {
            this.g = 3;
            return;
        }
        this.i = -1;
        for (int i2 = 0; i2 < a.length; ++i2) {
            if (!a[i2]) continue;
            if (this.i == -1) {
                this.i = i2;
            }
            ak.a[i2] = ak.a("game" + (i2 + 1));
            ++this.h;
            g.a.n();
        }
        if (this.i == -1) {
            this.i = 9;
        }
        if (c) {
            a = ak.a("operator");
            ++this.h;
            g.a.n();
        }
        b = ak.a("softkeyicons");
        ++this.h;
        g.a.n();
        this.g = 1;
    }

    public static void b() {
        for (int i2 = 0; i2 < a.length; ++i2) {
            ak.a[i2] = null;
        }
        a = null;
        b = null;
        System.gc();
    }

    private void d() {
        if (d) {
            if (a != null) {
                s.a(a, e);
                return;
            }
            if (this.i == 9) {
                s.a(c, e);
                return;
            }
            s.a(c[this.i], e);
            return;
        }
        this.g = 4;
    }

    public final void a(int n2, int n3) {
        this.k = n2;
        this.l = n3;
    }

    public final void c() {
        int n2 = this.k;
        int n3 = this.l;
        this.k = -1;
        if (n2 == -1) {
            return;
        }
        if (this.a.a(n2, n3)) {
            this.b(-6, 0);
        } else if (this.b.a(n2, n3)) {
            this.b(-7, 0);
        }
        if (this.g == 1) {
            if (this.c.a(n2, n3)) {
                this.b(g.b(2), 2);
                return;
            }
            if (this.d.a(n2, n3)) {
                this.b(g.b(3), 3);
                return;
            }
            if (!this.c.a(n2) && !this.d.a(n2) && this.e.a(n2, n3)) {
                this.b(-6, 0);
            }
        }
    }

    public final void b(int n2, int n3) {
        block27: {
            if (this.g == 2) {
                if (g.c(n2) || n3 == 8) {
                    this.d();
                    return;
                }
                if (g.a(n2)) {
                    if (a != null) {
                        s.f();
                        return;
                    }
                    this.g = 1;
                    return;
                }
            } else if (this.g == 4 || this.g == 3) {
                if (g.a(n2)) {
                    if (j == 0) {
                        s.f();
                        return;
                    }
                    this.g = 1;
                    return;
                }
            } else if (this.g == 1) {
                if (g.c(n2) || n3 == 8) {
                    if (!e || h == null) {
                        this.d();
                        return;
                    }
                    this.g = 2;
                    return;
                }
                if (g.a(n2)) {
                    s.f();
                    return;
                }
                if (j > 1 && n3 == 2) {
                    n2 = this.i - 1;
                    while (true) {
                        if (n2 < 0) {
                            n2 = 9;
                        }
                        if (n2 == 9) {
                            if (c) {
                                this.i = n2;
                                break block27;
                            }
                        } else if (a[n2]) {
                            this.i = n2;
                            break block27;
                        }
                        --n2;
                    }
                }
                if (j > 1 && n3 == 3) {
                    n2 = this.i + 1;
                    while (true) {
                        if (n2 > 9) {
                            n2 = 0;
                        }
                        if (n2 == 9) {
                            if (c) {
                                this.i = n2;
                                return;
                            }
                        } else if (a[n2]) {
                            this.i = n2;
                            return;
                        }
                        ++n2;
                    }
                }
            }
        }
    }

    public final void a(Graphics graphics, int n2, int n3) {
        int n4 = n2 * 3 / 4;
        if (this.g == 0) {
            graphics.setColor(a);
            graphics.fillRect(0, 0, n2, n3);
            graphics.setColor(b);
            graphics.setFont(a);
            graphics.drawString("Loading...", n2 / 2, n3 / 2, 33);
            int n5 = n2 * 3 / 4;
            int n6 = a.getHeight() / 2;
            n4 = (n2 - n5) / 2;
            int n7 = n3 / 2;
            int n8 = j + 1;
            int n9 = this.h * n5 / n8;
            graphics.drawRect(n4, n7, n5 - 1, n6 - 1);
            graphics.fillRect(n4, n7, n9, n6);
        } else if (this.g == 2 || this.g == 3 || this.g == 4) {
            graphics.setColor(this.g == 3 ? e : a);
            graphics.fillRect(0, 0, n2, n3);
            graphics.setColor(this.g == 3 ? f : b);
            Font font = a;
            String string = null;
            if (this.g == 2) {
                string = h;
            } else if (this.g == 3) {
                string = !d ? j : k;
            } else if (this.g == 4) {
                string = i;
            }
            graphics.setFont(font);
            String[] stringArray = ak.a(string, font, n4, -1);
            int n10 = (n3 - stringArray.length * c.getHeight()) / 2;
            for (int i2 = 0; i2 < stringArray.length; ++i2) {
                graphics.drawString(stringArray[i2], n2 / 2, n10, 17);
                n10 += font.getHeight();
            }
        } else if (this.g == 1) {
            int n11;
            graphics.setColor(this.i == 9 ? c : a);
            graphics.fillRect(0, 0, n2, n3);
            graphics.setColor(this.i == 9 ? d : b);
            int n12 = 0;
            String string = this.i == 9 ? b : b[this.i];
            String[] stringArray = string != null ? ak.a(string, c, n4, -1) : null;
            int n13 = 0;
            int n14 = 0;
            if (this.i != 9 && a[this.i] != null) {
                n13 = a[this.i].getWidth();
                n14 = 0 + (a[this.i].getHeight() + 4);
            }
            if (stringArray != null) {
                n14 += 4 + stringArray.length * c.getHeight();
                for (n11 = 0; n11 < stringArray.length; ++n11) {
                    int n15 = c.stringWidth(stringArray[n11]);
                    n13 = Math.max(n13, n15);
                }
            }
            n11 = n3;
            n11 = b == null ? (n11 -= b.getHeight() + 4) : (n11 -= b.getHeight() + 8);
            n12 = (n11 - n14) / 2;
            this.e.a((n2 - n13) / 2, n12, n13, n14);
            Image image = this.i == 9 ? a : a[this.i];
            if (image != null) {
                if (this.i == 9) {
                    graphics.drawImage(image, n2 - 4 - image.getWidth(), n11 - image.getHeight(), 20);
                } else {
                    graphics.drawImage(image, (n2 - image.getWidth()) / 2, n12 += 4, 20);
                    n12 += image.getHeight();
                }
            }
            if (string != null) {
                graphics.setFont(c);
                n12 += 4;
                for (int i3 = 0; i3 < stringArray.length; ++i3) {
                    graphics.drawString(stringArray[i3], n2 / 2, n12, 17);
                    n12 += c.getHeight();
                }
            }
            if (j > 1) {
                if (b != null) {
                    ak.a(graphics, 5, 4, n3 / 2, 36, this.c);
                    ak.a(graphics, 4, n2 - 4, n3 / 2, 40, this.d);
                } else {
                    graphics.setColor(this.i == 9 ? d : b);
                    int n16 = 0;
                    int n17 = n3 / 2 - 12;
                    graphics.fillTriangle(4, n17, 16, n17 - 12, 16, n17 + 12);
                    this.c.a(4, n17 - 12, 12, 12);
                    n16 = n2 - 4;
                    graphics.fillTriangle(n16, n17, n16 - 12, n17 - 12, n16 - 12, n17 + 12);
                    this.d.a(n16 - 12, n17 - 12, 12, 12);
                }
            }
        }
        if (b != null) {
            if (this.g == 1 || this.g == 2) {
                ak.a(graphics, 0, 4, n3 - 4, 36, this.a);
            }
            ak.a(graphics, 1, n2 - 4, n3 - 4, 40, this.b);
            return;
        }
        graphics.setColor(this.i == 9 ? d : b);
        graphics.setFont(b);
        String string = null;
        String string2 = null;
        if (this.g == 1) {
            string = d;
            string2 = e;
        } else if (this.g == 3 || this.g == 4) {
            string2 = e;
        } else if (this.g == 2) {
            string = f;
            string2 = g;
        }
        if (string != null) {
            graphics.drawString(string, 2, n3, 36);
            this.a.a(2, n3 - b.getHeight(), b.stringWidth(string), b.getHeight());
        }
        if (string2 != null) {
            graphics.drawString(string2, n2 - 2, n3, 40);
            int n18 = b.stringWidth(string2);
            this.b.a(2 - n18, n3 - b.getHeight(), n18, b.getHeight());
        }
    }

    private static void a(Graphics graphics, int n2, int n3, int n4, int n5, l l2) {
        int n6 = b.getWidth() / 6;
        int n7 = b.getHeight();
        if ((n5 & 8) != 0) {
            n3 -= n6;
        } else if ((n5 & 1) != 0) {
            n3 -= n6 / 2;
        }
        if ((n5 & 0x20) != 0) {
            n4 -= n7;
        } else if ((n5 & 2) != 0) {
            n4 -= n7 / 2;
        }
        l2.a(n3, n4, n6, n7);
        graphics.drawRegion(b, n2 * n6, 0, n6, n7, 0, n3, n4, 20);
    }

    private static String[] a(String string, Font font, int n2, int n3) {
        a.removeAllElements();
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer stringBuffer2 = new StringBuffer();
        int n4 = 0;
        int n5 = 0;
        int n6 = font.charWidth(' ');
        char[] cArray = new char[string.length() + 1];
        string.getChars(0, string.length(), cArray, 0);
        cArray[string.length()] = 10;
        block4: for (int i2 = 0; i2 < cArray.length; ++i2) {
            char c2 = cArray[i2];
            int n7 = font.charWidth(c2);
            switch (c2) {
                case '\t': 
                case ' ': {
                    if (stringBuffer.length() == 0) {
                        if (stringBuffer2.length() == 0) {
                            stringBuffer.append(' ');
                            n4 = n6;
                        } else {
                            stringBuffer.append((Object)stringBuffer2);
                            n4 += n5;
                        }
                    } else if (n4 + n5 + n6 <= n2) {
                        stringBuffer.append(' ');
                        n4 += n6;
                        stringBuffer.append((Object)stringBuffer2);
                        n4 += n5;
                    } else {
                        a.addElement(stringBuffer.toString());
                        stringBuffer.setLength(0);
                        stringBuffer.append(stringBuffer2.toString());
                        n4 = n5;
                    }
                    stringBuffer2.setLength(0);
                    n5 = 0;
                    continue block4;
                }
                case '\n': 
                case '|': {
                    if (stringBuffer.length() == 0) {
                        a.addElement(stringBuffer2.toString());
                    } else if (n4 + n5 + n6 <= n2) {
                        stringBuffer.append(' ');
                        stringBuffer.append((Object)stringBuffer2);
                        a.addElement(stringBuffer.toString());
                        stringBuffer.setLength(0);
                        n4 = 0;
                    } else {
                        a.addElement(stringBuffer.toString());
                        a.addElement(stringBuffer2.toString());
                        stringBuffer.setLength(0);
                        n4 = 0;
                    }
                    stringBuffer2.setLength(0);
                    n5 = 0;
                    continue block4;
                }
                default: {
                    if (n5 + n7 <= n2) {
                        stringBuffer2.append(c2);
                        n5 += n7;
                        continue block4;
                    }
                    if (stringBuffer.length() != 0) {
                        a.addElement(stringBuffer.toString());
                    }
                    a.addElement(stringBuffer2.toString());
                    stringBuffer.setLength(0);
                    n4 = 0;
                    stringBuffer2.setLength(0);
                    stringBuffer2.append(c2);
                    n5 = n7;
                }
            }
        }
        Object[] objectArray = new String[a.size()];
        a.copyInto(objectArray);
        return objectArray;
    }

    static {
        a = new boolean[9];
        a = new String[9];
        b = new String[9];
        c = new String[9];
        a = 0;
        b = 0xFFFFFF;
        c = 0;
        d = 0xFFFFFF;
        e = 0;
        f = 0xFFFFFF;
        a = new Image[9];
        a = Font.getFont((int)64, (int)1, (int)0);
        b = Font.getFont((int)64, (int)1, (int)0);
        c = Font.getFont((int)64, (int)0, (int)0);
        a = new Vector();
    }
}

