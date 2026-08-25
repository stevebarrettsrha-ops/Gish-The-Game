/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class v {
    private byte a;
    private int[] a;
    private int[] b;
    private int[] c;
    private boolean a = false;
    private int[] d;

    public final void a(Graphics graphics, int n2) {
        if (this.a == 4) {
            int n3 = this.c.length / 3;
            graphics.setColor(n2);
            for (int i2 = 0; i2 < n3; ++i2) {
                n2 = i2 * 3;
                int n4 = this.c[n2];
                int n5 = this.c[n2 + 1];
                n2 = this.c[n2 + 2];
                graphics.fillTriangle(this.a[n4], this.b[n4], this.a[n5], this.b[n5], this.a[n2], this.b[n2]);
            }
            return;
        }
        if (this.a == 3) {
            graphics.setColor(n2);
            int n6 = this.a.length - 1;
            n2 = 0;
            int n7 = n6 - 1;
            while (n2 < n6) {
                graphics.fillTriangle(this.a[n7], this.b[n7], this.a[n2], this.b[n2], this.a[n6], this.b[n6]);
                n7 = n2++;
            }
            return;
        }
        if (this.a == 6) {
            graphics.setColor(n2);
            int n8 = this.a.length;
            n2 = 2;
            int n9 = 1;
            while (n2 < n8) {
                graphics.fillTriangle(this.a[n9], this.b[n9], this.a[n2], this.b[n2], this.a[0], this.b[0]);
                n9 = n2++;
            }
        }
    }

    public v(byte by, int n2) {
        this.a = by;
        if (this.a == 2 || this.a == 3 || this.a == 6 || this.a == 4 || this.a == 5 || this.a == 8) {
            this.a = new int[this.a == 3 ? n2 + 1 : n2];
            this.b = new int[this.a == 3 ? n2 + 1 : n2];
        }
    }

    public final void a(x[] object, bg xArray) {
        block20: {
            int n2;
            int n3;
            int n4;
            int n5;
            block21: {
                bg bg2;
                if (this.a == 2 || this.a == 3 || this.a == 6 || this.a == 4 || this.a == 5 || this.a == 8) {
                    n5 = ((x[])object).length;
                    for (n4 = 0; n4 < n5; ++n4) {
                        bg2 = object[n4].a;
                        this.a[n4] = bg2.a >> 10;
                        this.b[n4] = bg2.b >> 10;
                    }
                    if (this.a == 3) {
                        this.a[((x[])object).length] = xArray.a >> 10;
                        this.b[((x[])object).length] = xArray.b >> 10;
                    }
                }
                if (this.a != 1 && this.a != 4) break block20;
                xArray = object;
                object = this;
                if (object.c == null || object.a) break block21;
                n5 = object.c.length / 3;
                n4 = 0;
                for (n3 = 0; n3 < n5; ++n3) {
                    int n6 = n3 * 3;
                    bg bg3 = xArray[object.c[n6]].a;
                    bg bg4 = xArray[object.c[n6 + 1]].a;
                    bg2 = xArray[object.c[n6 + 2]].a;
                    if ((int)((long)(bg4.a - bg3.a) * (long)(bg2.b - bg3.b) - (long)(bg4.b - bg3.b) * (long)(bg2.a - bg3.a) >> 10) < 0) continue;
                    n4 = 1;
                    break;
                }
                if (n4 == 0) break block20;
            }
            if (object.c == null) {
                object.c = new int[(xArray.length - 2) * 3];
            }
            n5 = 0;
            n4 = xArray.length;
            if (object.d == null) {
                object.d = new int[n4];
            }
            for (n2 = 0; n2 < n4; ++n2) {
                object.d[n2] = n2;
            }
            n2 = n4 << 1;
            n3 = n4 - 1;
            while (n4 > 2) {
                boolean bl;
                int n7;
                int n8;
                block19: {
                    if (0 >= n2--) {
                        object.a = true;
                        return;
                    }
                    n8 = n3;
                    if (n8 >= n4) {
                        n8 = 0;
                    }
                    if ((n3 = n8 + 1) >= n4) {
                        n3 = 0;
                    }
                    if ((n7 = n3 + 1) >= n4) {
                        n7 = 0;
                    }
                    int n9 = n4;
                    int[] nArray = object.d;
                    int n10 = n7;
                    int n11 = n3;
                    int n12 = n8;
                    x[] xArray2 = xArray;
                    bg bg5 = xArray[nArray[n11]].a;
                    bg bg6 = xArray2[nArray[n12]].a;
                    bg bg7 = xArray2[nArray[n10]].a;
                    if ((int)((long)(bg5.a - bg6.a) * (long)(bg7.b - bg6.b) - (long)(bg5.b - bg6.b) * (long)(bg7.a - bg6.a) >> 10) < 0) {
                        bl = false;
                    } else {
                        for (int i2 = 0; i2 < n9; ++i2) {
                            if (i2 == n12 || i2 == n11 || i2 == n10) continue;
                            bg bg8 = xArray2[nArray[i2]].a;
                            int n13 = (int)((long)(bg7.a - bg5.a) * (long)(bg8.b - bg5.b) - (long)(bg7.b - bg5.b) * (long)(bg8.a - bg5.a) >> 10);
                            int n14 = (int)((long)(bg5.a - bg6.a) * (long)(bg8.b - bg6.b) - (long)(bg5.b - bg6.b) * (long)(bg8.a - bg6.a) >> 10);
                            int n15 = (int)((long)(bg6.a - bg7.a) * (long)(bg8.b - bg7.b) - (long)(bg6.b - bg7.b) * (long)(bg8.a - bg7.a) >> 10);
                            if (n13 < 0 || n14 < 0 || n15 < 0) continue;
                            bl = false;
                            break block19;
                        }
                        bl = true;
                    }
                }
                if (!bl) continue;
                n2 = n5 * 3;
                object.c[n2++] = object.d[n7];
                object.c[n2++] = object.d[n3];
                object.c[n2] = object.d[n8];
                ++n5;
                n2 = n3;
                n8 = n3 + 1;
                while (n8 < n4) {
                    object.d[n2] = object.d[n8];
                    n2 = n8++;
                }
                if (n3 >= --n4) {
                    n3 = 0;
                }
                n2 = n4 << 1;
            }
            object.a = false;
        }
    }
}

