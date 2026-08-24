/*
 * Decompiled with CFR 0.152.
 */
import java.io.IOException;
import java.util.Hashtable;

final class au {
    private au() {
    }

    static String a(String string, String string2) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("ap.ug.");
        stringBuffer.append(au.a(string));
        stringBuffer.append('|');
        stringBuffer.append(au.a(string2));
        return stringBuffer.toString();
    }

    private static String a(int n2) {
        return "zp.lc." + n2;
    }

    static ah a(Hashtable object, int n2) {
        int n3;
        int n4;
        block5: {
            Object object2 = au.a(n2);
            if ((object = (String)((Hashtable)object).get(object2)) == null) {
                return null;
            }
            object2 = object;
            boolean bl = false;
            for (int i2 = 0; i2 < ((String)object2).length(); ++i2) {
                char c2 = ((String)object2).charAt(i2);
                if (!bl && c2 == '\\') {
                    bl = true;
                    continue;
                }
                if (!bl && c2 == '|') {
                    n4 = i2;
                    break block5;
                }
                bl = false;
            }
            n4 = n3 = -1;
        }
        if (n4 >= 0) {
            String string = au.b(((String)object).substring(0, n3));
            object = au.b(((String)object).substring(n3 + 1));
            return ao.a(string, (String)object);
        }
        return null;
    }

    static void a(Hashtable hashtable, int n2, ah ah2) {
        String string = au.a(n2);
        CharSequence charSequence = null;
        if (ah2 != null) {
            try {
                charSequence = new StringBuffer();
                charSequence.append(au.a(ah2.a()));
                charSequence.append('|');
                charSequence.append(au.a(ah2.b()));
                charSequence = charSequence.toString();
                hashtable.put(string, charSequence);
                return;
            }
            catch (IOException iOException) {
                return;
            }
        }
        hashtable.remove(string);
    }

    static boolean a(Hashtable hashtable) {
        return hashtable.get("zp.ace").equals("true");
    }

    static void a(Hashtable hashtable, boolean bl) {
        if (bl) {
            hashtable.put("zp.ace", "true");
            return;
        }
        hashtable.put("zp.ace", "false");
    }

    private static String a(String string) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i2 = 0; i2 < string.length(); ++i2) {
            char c2 = string.charAt(i2);
            if (c2 == '\\' || c2 == '|') {
                stringBuffer.append('\\');
            }
            stringBuffer.append(c2);
        }
        return stringBuffer.toString();
    }

    private static String b(String string) {
        StringBuffer stringBuffer = new StringBuffer();
        boolean bl = false;
        for (int i2 = 0; i2 < string.length(); ++i2) {
            char c2 = string.charAt(i2);
            if (!bl && c2 == '\\') {
                bl = true;
                continue;
            }
            stringBuffer.append(c2);
            bl = false;
        }
        return stringBuffer.toString();
    }
}

