/*
 * Decompiled with CFR 0.152.
 */
/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class ao {
    private static az a;

    private ao() {
    }

    public static ah a(String object, String string) {
        String string2 = string;
        string = object;
        object = ao.a();
        object = new av(string, string2);
        boolean bl = true;
        av av2 = object;
        ((av)object).a = true;
        return object;
    }

    public static az a() {
        if (a == null) {
            boolean bl = ao.a();
            try {
                if (bl) {
                    Object var0_1 = null;
                    a = (az)Class.forName("com.zeemote.zc.c").newInstance();
                } else {
                    a = (az)Class.forName("az").newInstance();
                }
            }
            catch (ClassNotFoundException classNotFoundException) {
                throw new RuntimeException();
            }
            catch (InstantiationException instantiationException) {
                throw new RuntimeException();
            }
            catch (IllegalAccessException illegalAccessException) {
                throw new RuntimeException();
            }
        }
        return a;
    }

    static boolean a() {
        try {
            Class.forName("net.rim.device.api.system.Device");
            return true;
        }
        catch (ClassNotFoundException classNotFoundException) {
            return false;
        }
    }
}

