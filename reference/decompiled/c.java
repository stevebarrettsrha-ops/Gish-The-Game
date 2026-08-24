/*
 * Decompiled with CFR 0.152.
 */
/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class c {
    private int a;
    private int b;
    private int c;
    private int d;
    private long[] a;
    private long[] b;
    private int[] a;
    private int[] b;
    private int[][] a;

    public c(int n2, int n3, int n4) {
        this.a = n2 >> 15;
        this.b = n3 >> 15;
        if (n2 % 32768 != 0) {
            ++this.a;
        }
        if (n3 % 32768 != 0) {
            ++this.b;
        }
        this.c = (n4 >> 6) + 1;
        this.d = this.c << 6;
        this.a = new long[this.a * this.c];
        this.b = new long[this.b * this.c];
        this.a = new int[this.d + 1];
        this.b = new int[this.d + 1];
    }

    public final int[] a(int n2, int n3, int[] nArray, boolean bl) {
        int n4;
        int[] nArray2 = this.a;
        if (n3 == 0 || this.c == 0) {
            nArray2[0] = 0;
            return nArray2;
        }
        n3 = nArray[0] >> 15;
        int n5 = nArray[1] >> 15;
        int n6 = nArray[2] >> 15;
        int n7 = nArray[3] >> 15;
        n3 = n3 < 0 ? 0 : n3;
        n5 = n5 < 0 ? 0 : n5;
        n6 = n6 >= this.a ? this.a - 1 : n6;
        n7 = n7 >= this.b ? this.b - 1 : n7;
        n3 *= this.c;
        n5 *= this.c;
        n6 *= this.c;
        n7 *= this.c;
        int n8 = 0;
        int n9 = n2 >> 6;
        int n10 = n4 = n2 == -1 ? -1 : n2 - (n9 << 6);
        long l2 = n2 == -1 ? 0L : (n4 == 63 ? -1L : (1L << n4 + 1) - 1L);
        int n11 = n2 = n2 == -1 ? 0 : n9;
        while (n2 != this.c) {
            int n12;
            long l3 = 0L;
            long l4 = 0L;
            for (n12 = n3; n12 <= n6; n12 += this.c) {
                l3 |= this.a[n12 + n2];
            }
            for (n12 = n5; n12 <= n7; n12 += this.c) {
                l4 |= this.b[n12 + n2];
            }
            l4 &= l3;
            while (l4 != 0L) {
                long l5 = l4 & -l4;
                if ((l5 & l2) == 0L || n2 != n9) {
                    n4 = 0;
                    if ((l5 & 0xFFFFFFFFL) == 0L) {
                        n4 += 32;
                    }
                    if ((l5 & 65535L << n4) == 0L) {
                        n4 += 16;
                    }
                    if ((l5 & 255L << n4) == 0L) {
                        n4 += 8;
                    }
                    if ((l5 & 15L << n4) == 0L) {
                        n4 += 4;
                    }
                    if ((l5 & 3L << n4) == 0L) {
                        n4 += 2;
                    }
                    if ((l5 & 1L << n4) == 0L) {
                        ++n4;
                    }
                    nArray2[++n8] = n4 + (n2 << 6);
                }
                l4 ^= l5;
            }
            ++n2;
        }
        nArray2[0] = n8;
        return nArray2;
    }

    public final int[] a(int n2, int n3, int[][] nArray) {
        if (n3 == 0 || this.c == 0) {
            this.b[0] = 0;
            return this.b;
        }
        n2 = nArray.length;
        if (this.a == null || n2 != this.a.length) {
            this.a = null;
            this.a = new int[n2][4];
        }
        for (n3 = 0; n3 < n2; ++n3) {
            int[] nArray2 = this.a[n3];
            int[] nArray3 = nArray2;
            nArray2[0] = nArray[n3][0] >> 15;
            nArray3[1] = nArray[n3][1] >> 15;
            nArray3[2] = nArray[n3][2] >> 15;
            nArray3[3] = nArray[n3][3] >> 15;
            nArray3[0] = nArray3[0] < 0 ? 0 : nArray3[0];
            nArray3[1] = nArray3[1] < 0 ? 0 : nArray3[1];
            nArray3[2] = nArray3[2] >= this.a ? this.a - 1 : nArray3[2];
            nArray3[3] = nArray3[3] >= this.b ? this.b - 1 : nArray3[3];
            nArray3[0] = nArray3[0] * this.c;
            nArray3[1] = nArray3[1] * this.c;
            nArray3[2] = nArray3[2] * this.c;
            nArray3[3] = nArray3[3] * this.c;
        }
        n3 = 0;
        for (int i2 = 0; i2 != this.c; ++i2) {
            int n4;
            long l2 = 0L;
            for (int i3 = 0; i3 < n2; ++i3) {
                long l3 = 0L;
                long l4 = 0L;
                int n5 = this.a[i3][2];
                for (n4 = this.a[i3][0]; n4 <= n5; n4 += this.c) {
                    l3 |= this.a[n4 + i2];
                }
                n5 = this.a[i3][3];
                for (n4 = this.a[i3][1]; n4 <= n5; n4 += this.c) {
                    l4 |= this.b[n4 + i2];
                }
                l2 |= l4 & l3;
            }
            while (l2 != 0L) {
                long l5;
                long cfr_ignored_0 = l2 & -l2;
                if (0L == 0L || i2 != -1) {
                    n4 = 0;
                    if ((l5 & 0xFFFFFFFFL) == 0L) {
                        n4 += 32;
                    }
                    if ((l5 & 65535L << n4) == 0L) {
                        n4 += 16;
                    }
                    if ((l5 & 255L << n4) == 0L) {
                        n4 += 8;
                    }
                    if ((l5 & 15L << n4) == 0L) {
                        n4 += 4;
                    }
                    if ((l5 & 3L << n4) == 0L) {
                        n4 += 2;
                    }
                    if ((l5 & 1L << n4) == 0L) {
                        ++n4;
                    }
                    this.b[++n3] = n4 + (i2 << 6);
                }
                l2 ^= l5;
            }
        }
        this.b[0] = n3;
        return this.b;
    }

    public final void a(int n2, int n3, int n4, int n5, int n6, int n7, int n8, int n9, int n10) {
        if ((n3 >>= 15) != (n7 >>= 15) || (n4 >>= 15) != (n8 >>= 15) || (n5 >>= 15) != (n9 >>= 15) || (n6 >>= 15) != (n10 >>= 15)) {
            this.b(n2, n3, n4, n5, n6);
            this.a(n2, n7, n8, n9, n10);
        }
    }

    public final void a(int n2, int[] nArray) {
        this.a(n2, nArray[0] >> 15, nArray[1] >> 15, nArray[2] >> 15, nArray[3] >> 15);
    }

    public final void b(int n2, int[] nArray) {
        this.b(n2, nArray[0] >> 15, nArray[1] >> 15, nArray[2] >> 15, nArray[3] >> 15);
    }

    private void a(int n2, int n3, int n4, int n5, int n6) {
        n3 = n3 < 0 ? 0 : n3;
        n4 = n4 < 0 ? 0 : n4;
        n5 = n5 >= this.a ? this.a - 1 : n5;
        n6 = n6 >= this.b ? this.b - 1 : n6;
        int n7 = n3 * this.c;
        int n8 = n4 * this.c;
        int n9 = n2 >> 6;
        long l2 = 1L << (n2 -= n9 << 6);
        while (n3 <= n5) {
            int n10 = n7 + n9;
            this.a[n10] = this.a[n10] | l2;
            ++n3;
            n7 += this.c;
        }
        while (n4 <= n6) {
            int n11 = n8 + n9;
            this.b[n11] = this.b[n11] | l2;
            ++n4;
            n8 += this.c;
        }
    }

    private void b(int n2, int n3, int n4, int n5, int n6) {
        n3 = n3 < 0 ? 0 : n3;
        n4 = n4 < 0 ? 0 : n4;
        n5 = n5 >= this.a ? this.a - 1 : n5;
        n6 = n6 >= this.b ? this.b - 1 : n6;
        int n7 = n3 * this.c;
        int n8 = n4 * this.c;
        int n9 = n2 >> 6;
        long l2 = 1L << (n2 -= n9 << 6) ^ 0xFFFFFFFFFFFFFFFFL;
        while (n3 <= n5) {
            int n10 = n7 + n9;
            this.a[n10] = this.a[n10] & l2;
            ++n3;
            n7 += this.c;
        }
        while (n4 <= n6) {
            int n11 = n8 + n9;
            this.b[n11] = this.b[n11] & l2;
            ++n4;
            n8 += this.c;
        }
    }
}

