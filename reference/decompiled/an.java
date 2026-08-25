/*
 * Decompiled with CFR 0.152.
 */
import java.io.IOException;
import java.io.InputStream;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class an {
    private static String a = null;
    private static String b = null;
    private static an a;
    private String[] a;
    private static Class a;

    private an(String[] stringArray) {
        this.a = stringArray;
    }

    public final void a() {
        an an2 = an.b();
        this.a = an2.a;
    }

    public final String a(int n2, Object[] objectArray) {
        if (n2 < 0 || n2 >= ((an)((Object)string)).a.length) {
            return null;
        }
        String string = ((an)((Object)string)).a[n2];
        if (string == null) {
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (int i2 = 0; i2 < string.length(); ++i2) {
            char c2 = string.charAt(i2);
            if (c2 == '\\') {
                c2 = string.charAt(++i2);
                stringBuffer.append(c2);
                continue;
            }
            if (c2 == '%') {
                char c3;
                int n3;
                StringBuffer stringBuffer2 = new StringBuffer();
                for (n3 = i2 + 1; n3 < string.length() && Character.isDigit(c3 = string.charAt(n3)); ++n3) {
                    stringBuffer2.append(c3);
                    ++i2;
                }
                n3 = Integer.parseInt(stringBuffer2.toString());
                if (objectArray == null) {
                    throw new ArrayIndexOutOfBoundsException();
                }
                stringBuffer.append(objectArray[n3]);
                continue;
            }
            stringBuffer.append(c2);
        }
        return stringBuffer.toString();
    }

    public final String a(int n2) {
        return this.a(n2, (Object[])null);
    }

    public final String a(int n2, int[] nArray) {
        Object[] objectArray = null;
        objectArray = new Object[nArray.length];
        for (int i2 = 0; i2 < nArray.length; ++i2) {
            objectArray[i2] = new Integer(nArray[i2]);
        }
        return this.a(n2, objectArray);
    }

    public static an a() {
        if (a == null) {
            a = an.b();
        }
        return a;
    }

    private static an b() {
        Object object = a;
        try {
            if (object == null) {
                object = "en-US";
                object = ("/zc-" + (String)object + ".txt").replace('_', '-');
            }
            object = an.a((String)object, b);
            object = an.a((String)object, 0, 0);
            return new an((String[])object);
        }
        catch (IOException iOException) {
            throw new RuntimeException();
        }
    }

    public static void a(String string) {
        a = string;
    }

    public static void b(String string) {
        b = string;
    }

    private static final String[] a(String string, int n2, int n3) {
        char c2;
        int n4;
        if (n2 >= string.length()) {
            return new String[n3];
        }
        int n5 = 0;
        int n6 = 0;
        for (n4 = n2; n4 < string.length() && (c2 = string.charAt(n4)) != '\n'; ++n4) {
            n6 = c2;
            ++n5;
        }
        n4 = n5;
        if (n6 == 13) {
            --n4;
        }
        if (n4 > 0) {
            ++n3;
        }
        String[] stringArray = an.a(string, n2 + n5 + 1, n3);
        if (n4 > 0) {
            stringArray[n3 - 1] = string.substring(n2, n2 + n4).intern();
        }
        return stringArray;
    }

    private static String a(String string, String string2) {
        InputStream inputStream = null;
        inputStream = (a == null ? (a = an.a("an")) : a).getResourceAsStream(string);
        if (null == inputStream) {
            throw new IOException();
        }
        string = an.a(inputStream, string2);
        try {
            inputStream.close();
        }
        catch (IOException iOException) {}
        return string;
    }

    private static String a(InputStream inputStream, String string) {
        byte[] byArray = new byte[1024];
        int n2 = 0;
        int n3 = 0;
        while ((n3 = inputStream.read()) > 0) {
            if (n2 >= byArray.length) {
                int n4 = byArray.length;
                byte[] byArray2 = new byte[n4 += 512];
                System.arraycopy(byArray, 0, byArray2, 0, byArray.length);
                byArray = byArray2;
            }
            byArray[n2++] = (byte)n3;
        }
        inputStream.close();
        if (string == null) {
            return new String(byArray, 0, n2);
        }
        return new String(byArray, 0, n2, string);
    }

    private static Class a(String string) {
        try {
            return Class.forName(string);
        }
        catch (ClassNotFoundException classNotFoundException) {
            throw new NoClassDefFoundError(classNotFoundException.getMessage());
        }
    }
}

