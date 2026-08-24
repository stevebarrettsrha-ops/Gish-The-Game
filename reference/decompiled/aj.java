/*
 * Decompiled with CFR 0.152.
 */
import com.hardwire.blob.Main;

final class aj
implements Runnable {
    private Main a;

    private aj(g g2, Main main) {
        this.a = main;
    }

    public final void run() {
        try {
            if (!this.a.b) {
                return;
            }
            this.a.a.b();
            this.a.a.a = true;
            this.a.a.n();
            this.a.a.h = true;
            if (this.a.a != null && this.a.a.a != null) {
                this.a.b.b();
                this.a.b.c();
            }
            if (this.a.a != null) {
                this.a.a.f();
            }
            if (this.a.a == 0 || this.a.a == 1 && this.a.a.a == 1) {
                this.a.a.a.g();
            }
            this.a.a.h = false;
            this.a.a.c = false;
            this.a.a.a = false;
            if (this.a.a != 1) {
                if (this.a.a == 0 && this.a.a.c == 6) {
                    this.a.a.a.d();
                }
                return;
            }
            this.a.a.a(this.a.a.a);
        }
        catch (Exception exception) {}
    }

    aj(g g2, Main main, m m2) {
        this(g2, main);
    }
}

