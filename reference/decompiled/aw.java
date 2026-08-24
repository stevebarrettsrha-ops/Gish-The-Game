/*
 * Decompiled with CFR 0.152.
 */
import java.util.TimerTask;

final class aw
extends TimerTask {
    private final ay a;

    aw(ay ay2) {
        this.a = ay2;
    }

    public final void run() {
        ay ay2 = this.a;
        if (!ay2.b) {
            ay2 = this.a;
            ay2.b();
        }
    }
}

