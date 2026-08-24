/*
 * Decompiled with CFR 0.152.
 */
final class l {
    private int a;
    private int b;
    private int c;
    private int d;

    l() {
    }

    final void a(int n2, int n3, int n4, int n5) {
        this.a = n2;
        this.b = n3;
        this.c = n4;
        this.d = n5;
    }

    final boolean a(int n2) {
        return n2 >= this.a - 5 && n2 < this.a + this.c + 5;
    }

    final boolean a(int n2, int n3) {
        if (this.a(n2)) {
            l l2 = this;
            if (n3 >= l2.b - 5 && n3 < l2.b + l2.d + 5) {
                return true;
            }
        }
        return false;
    }
}

