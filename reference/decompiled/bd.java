/*
 * Decompiled with CFR 0.152.
 */
import java.io.InputStream;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
final class bd
extends InputStream {
    private InputStream a;
    private byte[] a;
    private int a = 0;
    private int b = 0;

    bd(InputStream inputStream) {
        this(inputStream, 500);
    }

    private bd(InputStream inputStream, int n2) {
        if (inputStream == null) {
            throw new NullPointerException();
        }
        this.a = inputStream;
        this.a = new byte[500];
    }

    public final int read() {
        if (this.a >= this.b) {
            int n2 = this.a.read(this.a);
            if (n2 == -1) {
                return -1;
            }
            this.a = 0;
            this.b = n2;
        }
        return this.a[this.a++] & 0xFF;
    }

    public final int available() {
        return this.b - this.a + this.a.available();
    }

    public final long skip(long l2) {
        return 0L;
    }

    public final void close() {
        this.a.close();
    }
}

