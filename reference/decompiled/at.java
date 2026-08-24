/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import com.hardwire.blob.Main;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class at {
    public static int a = 0;
    public static final String[] a = new String[]{"en", "de", "fr", "es", "it", "cz", "chi", "ru"};
    public static final String[] b = new String[]{"intro", "1/01_s0", "1/02_j0", "1/03_j4", "1/04_s3", "1/05_s4", "1/06_j3", "1/07_j1", "1/08_s5", "1/09_", "1/10_j2", "1/11_s11", "1/12_s13", "1/13_", "1/14_s17", "1/15_s18", "2/01_e0", "2/02_", "2/03_", "2/04_e3", "2/05_e4", "2/06_", "2/07_", "2/08_e9", "2/09_", "2/10_e14", "2/11_e15", "3/01_h2", "3/02_h4", "3/03_", "3/04_h7", "3/05_j5", "3/06_h9", "3/07_", "3/08_h14", "3/09_h15", "playgr/01", "playgr/02", "playgr/03", "playgr/04", "playgr/05", "coop/00", "coop/01", "coop/02", "coop/03", "coop/04", "coop/05", "coop/06", "coop/07", "coop/08", "coop/09", "coop/10", "coop/11", "coop/12", "coop/13", "coop/14", "coop/15", "coop/16", "coop/17", "coop/18", "coop/19", "coop/20", "coop/21", "coop/22", "coop/23", "coop/24", "coop/25", "coop/26", "dm/01", "dm/02", "dm/03", "dm/04", "dm/05", "dm/06", "dm/07", "dm/08", "dm/09", "dm/10", "race/01", "race/02", "race/03", "race/04", "race/05", "race/06", "race/07", "race/08", "race/09", "race/10"};
    public boolean[] a;
    private static char[][] a = new char[][]{{'e', '\u02c7'}, {'s', '\u02c7'}, {'c', '\u02c7'}, {'r', '\u02c7'}, {'z', '\u02c7'}, {'y', '\u00b4'}, {'a', '\u00b4'}, {'i', '\u00b4'}, {'e', '\u00b4'}, {'u', '\u00b4'}, {'u', '\u00b0'}, {'d', '\u02c7'}, {'t', '\u02c7'}, {'n', '\u02c7'}, {'a', '\u00a8'}, {'a', '\u00b0'}, {'a', '\u00a7'}, {'o', '\u00a8'}, {'e', '`'}, {'a', '`'}, {'e', '\u00a7'}, {'u', '\u00a8'}, {'u', '\u00a7'}, {'u', '`'}, {'o', '\u00a7'}, {'o', '\u00b4'}, {'o', '`'}, {'i', '`'}, {'i', '\u00a7'}, {'n', '\u02dc'}, {'\''}, {'\''}, {'\''}};
    private static String[] c = new String[]{"abcdefghijklmnopqrstuvwxyz0123456789.,:;'\"!?/()#@*-_\u02c7\u00a7\u00a8\u00b0\u00b4`\u02dc\u00bf\u00a1\u00df\u00e7", "013", "abcdefghijklmnopqrstuvwxyz0123456789.,:;'\"!?/()#@*-_\u02c7\u00a7\u00a8\u00b0\u00b4`\u02dc\u00bf\u00a1\u00df\u00e7", "abcdefghijklmnopqrstuvwxyz0123456789.,:;'\"!?/()#@*-_\u02c7\u00a7\u00a8\u00b0\u00b4`\u02dc\u00bf\u00a1\u00df\u00e7", "abcdefghijklmnopqrstuvwxyz0123456789.,:;'\"!?/()#@*-_\u02c7\u00a7\u00a8\u00b0\u00b4`\u02dc\u00bf\u00a1\u00df\u00e7"};
    private static String[] d = new String[]{"\u02c7\u00b4`\u00a7\u00b0\u00a8\u02dc", "", "\u02c7\u00b4`\u00a7\u00b0\u00a8\u02dc", "\u02c7\u00b4`\u00a7\u00b0\u00a8\u02dc", "\u02c7\u00b4`\u00a7\u00b0\u00a8\u02dc"};
    private static int[] a = new int[]{5, 1, 5, 3};
    private static final int[] b = new int[]{17, 17, 17, 17, 17, 17, 17, 17, 9, 17, 17, 17, 25, 17, 17, 17, 17, 17, 17, 21, 17, 17, 25, 19, 17, 17, 17, 11, 17, 17, 17, 17, 17, 17, 17, 17, 9, 9, 9, 9, 9, 18, 9, 17, 21, 13, 13, 25, 21, 15, 17, 17, 17, 17, 17, 9, 16, 16, 17, 17, 9, 19, 17};
    private static int[][] a = new int[][]{b, {6, 3, 6}, b, {11, 11, 11, 11, 11, 11, 11, 11, 5, 11, 11, 11, 17, 11, 11, 11, 11, 11, 11, 13, 11, 11, 17, 11, 11, 11, 11, 7, 11, 11, 11, 11, 11, 11, 11, 11, 5, 5, 5, 5, 5, 11, 5, 11, 17, 9, 9, 15, 14, 11, 13, 11, 7, 7, 11, 7, 8, 7, 9, 11, 5, 13, 11}};
    private static int[] c = new int[]{17, 6, 17, 11};
    private static int[] d = new int[]{-1, 1, -1, 0};
    private static Image[] a;
    private static int[] e;
    private static int[][] b;
    private static int[][] c;
    private static boolean[][] a;
    private byte[][] a;
    private byte[][][] a;
    private byte[] a;
    private static Main a;

    public static boolean a(int n2) {
        return n2 >= 1 && n2 <= 35;
    }

    public static boolean b(int n2) {
        return n2 >= 41 && n2 <= 67;
    }

    public static int a(int n2) {
        if (at.a.a.e == 0) {
            if (n2 < 35) {
                return n2 + 1;
            }
            return -1;
        }
        if (at.a.a.e == 2) {
            if (n2 < 67) {
                return n2 + 1;
            }
            return -1;
        }
        return -1;
    }

    public static int b(int n2) {
        return n2 - 1;
    }

    public static int c(int n2) {
        return n2 - 41;
    }

    public final byte[] a(int n2) {
        if (n2 == 15 || n2 == 26) {
            int n3 = 112;
            at at2 = this;
            return at2.a[n3];
        }
        if (n2 == 0) {
            int n4 = 69;
            at at3 = this;
            return at3.a[n4];
        }
        if (n2 == 35 || n2 == 67) {
            int n5 = 113;
            at at4 = this;
            return at4.a[n5];
        }
        int n6 = n2;
        if (n6 >= 36 && n6 <= 40) {
            int n7 = n2 + 114 - 36;
            at at5 = this;
            return at5.a[n7];
        }
        String string = at.a(n2) ? (n2 < 16 ? "1-" + (n2 - 1 + 1) : (n2 < 27 ? "2-" + (n2 - 16 + 1) : "3-" + (n2 - 27 + 1))) : (at.b(n2) ? "c-" + (n2 - 41 + 1) : ((n6 = n2) >= 78 && n6 <= 87 ? "r-" + (n2 - 78 + 1) : ((n6 = n2) >= 68 && n6 <= 77 ? "d-" + (n2 - 68 + 1) : "unknown")));
        Object object = null;
        int n8 = 28;
        object = this;
        object = at.a((int)((at)object).a[n8], string);
        if (n2 == 5 || n2 == 11) {
            Object object2 = object;
            n8 = 70;
            object = this;
            object = at.a((byte[])object2, ((at)object).a[n8]);
        } else if (n2 == 20 || n2 == 23) {
            Object object3 = object;
            n8 = 71;
            object = this;
            object = at.a((byte[])object3, ((at)object).a[n8]);
        } else if (n2 == 28 || n2 == 32) {
            Object object4 = object;
            n8 = 72;
            object = this;
            object = at.a((byte[])object4, ((at)object).a[n8]);
        } else if (n2 == 14 || n2 == 25 || n2 == 34) {
            Object object5 = object;
            n8 = 73;
            object = this;
            object = at.a((byte[])object5, ((at)object).a[n8]);
        }
        return object;
    }

    private void a(boolean[] blArray) {
        for (int i2 = 0; i2 < blArray.length; ++i2) {
            if (!blArray[i2]) continue;
            this.a[i2] = 3;
        }
        this.a[15] = 3;
        this.a[99] = 3;
        this.a[98] = 3;
        this.a[97] = 3;
        this.a[108] = 2;
    }

    private static boolean[] a(int n2) {
        boolean[] blArray = new boolean[n2];
        for (int i2 = 0; i2 < n2; ++i2) {
            blArray[i2] = false;
        }
        blArray[1] = true;
        blArray[0] = true;
        blArray[22] = true;
        blArray[23] = true;
        blArray[24] = true;
        blArray[25] = true;
        blArray[31] = true;
        blArray[32] = true;
        blArray[47] = true;
        blArray[53] = true;
        blArray[54] = true;
        blArray[55] = true;
        blArray[58] = true;
        blArray[59] = true;
        blArray[61] = true;
        blArray[16] = true;
        blArray[19] = true;
        blArray[86] = true;
        blArray[87] = true;
        blArray[95] = true;
        blArray[85] = true;
        blArray[96] = true;
        blArray[89] = true;
        blArray[2] = true;
        blArray[102] = true;
        blArray[103] = true;
        blArray[104] = true;
        blArray[125] = true;
        blArray[127] = true;
        blArray[128] = true;
        blArray[129] = true;
        blArray[130] = true;
        blArray[131] = true;
        blArray[132] = true;
        blArray[109] = true;
        blArray[111] = true;
        blArray[119] = true;
        blArray[120] = true;
        return blArray;
    }

    public final void a() {
        this.a = null;
        this.a = new boolean[200];
        this.a = null;
        this.a = null;
        this.a = new byte[200][][];
    }

    public final void b() {
        int n2;
        if (this.a != null) {
            for (n2 = 0; n2 < this.a.length; ++n2) {
                this.a[n2] = null;
            }
        }
        if (this.a != null) {
            for (n2 = 0; n2 < this.a.length; ++n2) {
                this.a[n2] = null;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void c() {
        block18: {
            boolean bl = false;
            for (int i2 = 0; i2 < this.a.length; ++i2) {
                if (!this.a[i2]) {
                    this.a[i2] = null;
                    continue;
                }
                if (this.a[i2] != null) continue;
                bl = true;
            }
            if (!bl) return;
            InputStream inputStream = null;
            try {
                inputStream = Main.a("/tl_pointer." + a[a]);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                for (int i3 = 0; i3 < this.a.length; ++i3) {
                    int n2;
                    if (!this.a[i3] || this.a[i3] != null) {
                        while ((n2 = inputStream.read()) != 124 && n2 != -1 && n2 != 0) {
                        }
                    } else {
                        while ((n2 = inputStream.read()) != 124 && n2 != -1 && n2 != 0) {
                            byteArrayOutputStream.write(n2);
                        }
                        this.a[i3] = at.a(3, at.a(3, at.a(byteArrayOutputStream.toByteArray())), g.a - 10);
                        byteArrayOutputStream.reset();
                    }
                    if (n2 != 0) {
                        continue;
                    }
                    break;
                }
            }
            catch (Exception exception) {
                try {
                    inputStream.close();
                }
                catch (IOException iOException) {}
                break block18;
            }
            catch (Throwable throwable) {
                try {
                    inputStream.close();
                    throw throwable;
                }
                catch (IOException iOException) {}
                throw throwable;
            }
            try {
                inputStream.close();
            }
            catch (IOException iOException) {}
        }
        System.gc();
    }

    public at(Main main) {
        a = main;
    }

    public static void d() {
        a = -1;
        for (int i2 = 0; i2 < a.length; ++i2) {
            if (Main.a("/t_pointer." + a[i2]) == null) continue;
            a = i2;
            break;
        }
        System.out.println("Language = " + a);
    }

    public static void e() {
        a = new Image[4];
        e = new int[4];
        b = new int[4][];
        c = new int[4][];
        a = new boolean[4][];
        at.a(0, 8);
        at.a(1, 7);
        at.a(2, 237);
        at.a(3, 230);
        System.gc();
    }

    public static int d(int n2) {
        return c[0];
    }

    public final void a(Graphics graphics, int n2, String string, int n3, int n4, int n5) {
        at.a(graphics, n2, at.a(n2, string), n3, n4, Integer.MIN_VALUE, Integer.MAX_VALUE, n5);
    }

    public final void a(Graphics graphics, int n2, int n3, int n4, int n5) {
        if (this.a[n2] == null) {
            return;
        }
        at.a(graphics, this.a[n2], this.a[n2], n3, n4, Integer.MIN_VALUE, Integer.MAX_VALUE, n5);
    }

    public final void a(Graphics graphics, int n2, byte[] byArray, int n3, int n4, int n5) {
        at.a(graphics, n2, byArray, n3, n4, Integer.MIN_VALUE, Integer.MAX_VALUE, n5);
    }

    public static void a(Graphics graphics, int n2, byte[] byArray, int n3, int n4, int n5, int n6, int n7) {
        int n8;
        if (byArray == null) {
            return;
        }
        if ((n7 & 8) != 0) {
            byte[] byArray2 = byArray;
            n8 = n2;
            n3 -= at.a(n8, byArray2, byArray2.length);
        } else if ((n7 & 1) != 0) {
            byte[] byArray3 = byArray;
            n8 = n2;
            n3 -= at.a(n8, byArray3, byArray3.length) >> 1;
        }
        if ((n7 & 0x20) != 0 || (n7 & 0x40) != 0) {
            n8 = n2;
            n4 -= e[n8];
        } else if ((n7 & 2) != 0) {
            n8 = n2;
            n4 -= e[n8] >> 1;
        }
        n7 = graphics.getClipX();
        n8 = graphics.getClipY();
        int n9 = graphics.getClipWidth();
        int n10 = graphics.getClipHeight();
        int n11 = byArray.length;
        for (int i2 = 0; i2 < n11; ++i2) {
            if (byArray[i2] == -3) continue;
            if (byArray[i2] == -1) {
                n3 += c[n2] + d[n2];
                continue;
            }
            int n12 = n3;
            if (a[n2][byArray[i2]]) {
                n12 += (a[n2][byArray[i2 - 1]] >> 1) - (a[n2][byArray[i2]] >> 1);
            }
            if (n12 < n6 && n12 + a[n2][byArray[i2]] >= n5) {
                int n13 = n12 < n5 ? n5 : n12;
                int n14 = n12 + a[n2][byArray[i2]];
                n14 = n14 > n6 ? n6 : n14;
                graphics.setClip(n13, n4, n14 - n13, e[n2]);
                graphics.drawImage(a[n2], n12 - b[n2][byArray[i2]], n4 - c[n2][byArray[i2]], 20);
            }
            if (i2 >= n11 - 1) continue;
            if (a[n2][byArray[i2]]) {
                n3 += a[n2][byArray[i2 - 1]] + d[n2];
                continue;
            }
            if ((byArray[i2 + 1] != -1 || a[n2][byArray[i2]]) && (byArray[i2 + 1] == -1 || a[n2][byArray[i2 + 1]])) continue;
            n3 += a[n2][byArray[i2]] + d[n2];
        }
        graphics.setClip(n7, n8, n9, n10);
    }

    public final byte a(int n2) {
        return this.a[n2];
    }

    public final byte[] b(int n2) {
        return this.a[n2];
    }

    public final byte[][] a(int n2) {
        return this.a[n2];
    }

    public static int e(int n2) {
        return e[n2];
    }

    public static int a(int n2, String object) {
        object = at.a(0, (String)object);
        n2 = 0;
        return at.a(0, (byte[])object, ((Object)object).length);
    }

    public final int f(int n2) {
        if (this.a[n2] == null) {
            return 0;
        }
        byte[] byArray = this.a[n2];
        n2 = this.a[n2];
        return at.a(n2, byArray, byArray.length);
    }

    public static int a(int n2, byte[] byArray) {
        return at.a(n2, byArray, byArray.length);
    }

    private static int a(int n2, byte[] byArray, int n3) {
        int n4 = 0;
        for (int i2 = 0; i2 < n3; ++i2) {
            if (byArray[i2] == -3) continue;
            if (byArray[i2] == -1) {
                n4 += c[n2] + d[n2];
                continue;
            }
            if (a[n2][byArray[i2]]) continue;
            n4 += a[n2][byArray[i2]] + d[n2];
        }
        return n4;
    }

    public static int a(int n2, byte by) {
        if (by == -1) {
            return c[n2] + d[n2];
        }
        if (by == -3 || a[n2][by]) {
            return 0;
        }
        return a[n2][by] + d[n2];
    }

    public static byte a(int n2, char c2) {
        return (byte)c[n2].indexOf(c2);
    }

    public static byte[] a(int n2, String string) {
        int n3;
        string = string.toLowerCase();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i2 = 0; i2 < string.length(); ++i2) {
            n3 = "\u011b\u0161\u010d\u0159\u017e\u00fd\u00e1\u00ed\u00e9\u00fa\u016f\u010f\u0165\u0148\u00e4\u00e5\u00e2\u00f6\u00e8\u00e0\u00ea\u00fc\u00fb\u00f9\u00f4\u00f3\u00f2\u00ec\u00ee\u00f1\u00b4\u2019\u2018".indexOf(string.charAt(i2));
            if (n3 == -1) {
                stringBuffer.append(string.charAt(i2));
                continue;
            }
            stringBuffer.append(a[n3][0]);
            if (a[n3].length <= 1) continue;
            stringBuffer.append(a[n3][1]);
        }
        byte[] byArray = new byte[stringBuffer.length()];
        for (n3 = 0; n3 < byArray.length; ++n3) {
            char c2 = stringBuffer.charAt(n3);
            byArray[n3] = c2 == ' ' ? -1 : (c2 == '~' ? -2 : (c2 == '^' ? -3 : (byte)c[n2].indexOf(c2)));
        }
        return byArray;
    }

    public static byte[] a(int n2, int n3) {
        n2 = (byte)c[n2].indexOf(48);
        if (n3 <= 0) {
            return new byte[]{n2};
        }
        if (n3 < 10) {
            return new byte[]{(byte)(n2 + n3)};
        }
        if (n3 < 100) {
            return new byte[]{(byte)(n2 + n3 / 10), (byte)(n2 + n3 % 10)};
        }
        if (n3 < 1000) {
            return new byte[]{(byte)(n2 + n3 / 100), (byte)(n2 + n3 / 10 % 10), (byte)(n2 + n3 % 10)};
        }
        if (n3 < 10000) {
            return new byte[]{(byte)(n2 + n3 / 1000), (byte)(n2 + n3 / 100 % 10), (byte)(n2 + n3 / 10 % 10), (byte)(n2 + n3 % 10)};
        }
        if (n3 < 100000) {
            return new byte[]{(byte)(n2 + n3 / 10000), (byte)(n2 + n3 / 1000 % 10), (byte)(n2 + n3 / 100 % 10), (byte)(n2 + n3 / 10 % 10), (byte)(n2 + n3 % 10)};
        }
        if (n3 < 1000000) {
            return new byte[]{(byte)(n2 + n3 / 100000), (byte)(n2 + n3 / 10000 % 10), (byte)(n2 + n3 / 1000 % 10), (byte)(n2 + n3 / 100 % 10), (byte)(n2 + n3 / 10 % 10), (byte)(n2 + n3 % 10)};
        }
        return null;
    }

    /*
     * Loose catch block
     */
    public final void f() {
        block17: {
            int n2;
            int n3;
            Object object;
            InputStream inputStream;
            block16: {
                int n4;
                this.a = null;
                this.a = null;
                System.gc();
                inputStream = null;
                inputStream = Main.a("/t_pointer." + a[a]);
                object = new Vector<String>();
                Closeable closeable = new ByteArrayOutputStream();
                n3 = 0;
                while ((n4 = inputStream.read()) != -1 && n4 != 0) {
                    if (n4 == 124) {
                        ((Vector)object).addElement(at.a(((ByteArrayOutputStream)closeable).toByteArray()));
                        ((ByteArrayOutputStream)closeable).reset();
                        continue;
                    }
                    ((ByteArrayOutputStream)closeable).write(n4);
                    n3 += n4;
                }
                closeable = new DataInputStream(inputStream);
                n2 = ((DataInputStream)closeable).readInt();
                if (n3 == n2) break block16;
                try {
                    inputStream.close();
                    return;
                }
                catch (IOException iOException) {
                    return;
                }
            }
            n2 = ((Vector)object).size();
            this.a = new byte[n2][];
            this.a = new byte[n2][][];
            this.a = new byte[n2];
            boolean[] blArray = at.a(n2);
            for (n3 = 0; n3 < n2; ++n3) {
                this.a[n3] = 0;
            }
            this.a(blArray);
            int n5 = at.a();
            int n6 = g.a - 10;
            for (int i2 = 0; i2 < n2; ++i2) {
                String string = (String)((Vector)object).elementAt(i2);
                this.a[i2] = (byte[][])(blArray[i2] ? at.a(this.a[i2], at.a((int)this.a[i2], string), i2 == 128 || i2 == 129 || i2 == 130 || i2 == 131 || i2 == 132 ? n6 : n5) : at.a((int)this.a[i2], string));
            }
            ((Vector)object).removeAllElements();
            byte[][] byArray = this.a[0];
            byte[][] byArrayArray = new byte[byArray.length + 1][];
            object = byArrayArray;
            byArrayArray[0] = byArray[0];
            object[1] = at.a((int)this.a[0], "v" + a.getAppProperty("MIDlet-Version"));
            System.arraycopy(byArray, 1, object, 2, byArray.length - 1);
            this.a[0] = (byte[][])object;
            try {
                inputStream.close();
            }
            catch (IOException iOException) {
                return;
            }
            catch (Exception exception) {
                try {
                    inputStream.close();
                    break block17;
                }
                catch (IOException iOException) {
                    return;
                }
            }
            catch (Throwable throwable) {
                try {
                    inputStream.close();
                }
                catch (IOException iOException) {}
                throw throwable;
            }
        }
    }

    public static int a() {
        return g.a - 70 - 4;
    }

    private static void a(int n2, int n3) {
        try {
            int n4;
            at.a[n2] = at.a.a.a(n3);
            n3 = c[n2].length();
            at.e[n2] = a[n2].getHeight() / a[n2];
            at.b[n2] = new int[n3];
            at.c[n2] = new int[n3];
            int n5 = 0;
            int n6 = 0;
            for (n4 = 0; n4 < n3; ++n4) {
                if (n5 + a[n2][n4] > a[n2].getWidth()) {
                    n5 = 0;
                    n6 += e[n2];
                }
                at.b[n2][n4] = n5;
                at.c[n2][n4] = n6;
                n5 += a[n2][n4];
            }
            at.a[n2] = new boolean[n3];
            for (n4 = 0; n4 < n3; ++n4) {
                at.a[n2][n4] = d[n2].indexOf(c[n2].charAt(n4)) != -1;
            }
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    private static void a(Vector vector, byte[] byArray, int n2) {
        if (n2 > 1 && byArray[n2 - 1] == -1) {
            --n2;
        }
        byte[] byArray2 = new byte[n2];
        System.arraycopy(byArray, 0, byArray2, 0, n2);
        vector.addElement(byArray2);
    }

    private static byte[] a(byte[] byArray, byte[] byArray2) {
        byte[] byArray3 = new byte[byArray.length + byArray2.length];
        System.arraycopy(byArray, 0, byArray3, 0, byArray.length);
        System.arraycopy(byArray2, 0, byArray3, byArray.length, byArray2.length);
        return byArray3;
    }

    public static byte[] a(byte[][] byArray) {
        int n2 = 0;
        for (int i2 = 0; i2 < byArray.length && byArray[i2] != null; ++i2) {
            n2 += byArray[i2].length;
        }
        byte[] byArray2 = new byte[n2];
        n2 = 0;
        for (int i3 = 0; i3 < byArray.length && byArray[i3] != null; ++i3) {
            System.arraycopy(byArray[i3], 0, byArray2, n2, byArray[i3].length);
            n2 += byArray[i3].length;
        }
        return byArray2;
    }

    public static byte[][] a(int n2, byte[] byArray, int n3) {
        int n4;
        Vector vector = new Vector();
        byte[] byArray2 = new byte[200];
        byte[] byArray3 = new byte[50];
        int n5 = 0;
        int n6 = 0;
        for (n4 = 0; n4 < byArray.length; ++n4) {
            int n7;
            if (byArray[n4] != -1 && byArray[n4] != -2 || n4 < byArray.length - 1 && (byArray[n4 + 1] == 42 || byArray[n4 + 1] == 43)) {
                if (at.a(n2, byArray3, n6) + at.a(n2, byArray[n4]) > n3) {
                    if (n5 > 0) {
                        at.a(vector, byArray2, n5);
                    }
                    at.a(vector, byArray3, n6);
                    n5 = 0;
                    n6 = 1;
                    byArray3[0] = byArray[n4];
                    continue;
                }
                byArray3[n6++] = byArray[n4];
                continue;
            }
            if (at.a(n2, byArray2, n5) + at.a(n2, byArray3, n6) > n3) {
                at.a(vector, byArray2, n5);
                n5 = 0;
                byArray3[n6++] = -1;
                for (n7 = 0; n7 < n6; ++n7) {
                    byArray2[n5++] = byArray3[n7];
                }
                n6 = 0;
            } else {
                if (n6 == 0 || byArray[n4] != -2) {
                    byArray3[n6++] = -1;
                }
                for (n7 = 0; n7 < n6; ++n7) {
                    byArray2[n5++] = byArray3[n7];
                }
                n6 = 0;
            }
            if (byArray[n4] != -2) continue;
            at.a(vector, byArray2, n5);
            n5 = 0;
        }
        if (at.a(n2, byArray2, n5) + at.a(n2, byArray3, n6) < n3) {
            for (n4 = 0; n4 < n6; ++n4) {
                byArray2[n5++] = byArray3[n4];
            }
            if (n5 > 0) {
                at.a(vector, byArray2, n5);
            }
        } else {
            if (n5 > 0) {
                at.a(vector, byArray2, n5);
            }
            if (n6 > 0) {
                at.a(vector, byArray3, n6);
            }
        }
        byte[][] byArrayArray = new byte[vector.size()][];
        vector.copyInto((Object[])byArrayArray);
        vector.removeAllElements();
        return byArrayArray;
    }

    private static String a(byte[] byArray) {
        try {
            return new String(byArray, "UTF-8");
        }
        catch (Exception exception) {
            return null;
        }
    }
}

