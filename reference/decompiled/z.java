/*
 * Decompiled with CFR 0.152.
 */
public class z {
    private w a;

    public z(w w2) {
        this.a = w2;
    }

    public final w a() {
        return this.a;
    }

    protected static int a(int n2, int n3, int n4, int n5, int n6) {
        if (n2 < n3) {
            n2 = n3;
        } else if (n2 > n4) {
            n2 = n4;
        }
        float f2 = (float)(n2 - n3) / (float)(n4 - n3) * (float)(n6 - n5);
        int n7 = (int)Math.floor((double)f2 + 0.5) + n5;
        if (n7 < n5) {
            n7 = n5;
        } else if (n7 > n6) {
            n7 = n6;
        }
        return n7;
    }
}

