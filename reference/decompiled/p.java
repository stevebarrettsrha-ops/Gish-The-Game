/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.midlet.MIDlet
 */
import java.util.Hashtable;
import javax.microedition.midlet.MIDlet;

public final class p
extends be {
    private MIDlet a = null;

    public static be a(MIDlet mIDlet) {
        if (!be.a.containsKey(mIDlet)) {
            p p2 = new p(mIDlet);
            be.a.put(mIDlet, p2);
        }
        return (be)be.a.get(mIDlet);
    }

    private p(MIDlet mIDlet) {
        this.a = mIDlet;
        this.a();
    }

    protected final String a(int n2) {
        return this.a.getAppProperty("controller-quickconnect-" + n2);
    }

    protected final boolean a() {
        return this.a.getAppProperty("zc-ac-enabled") != null;
    }

    protected final boolean b() {
        String string = ((p)((Object)string)).a.getAppProperty("zc-ac-enabled");
        return string != null && string.equals("true");
    }

    protected final boolean c() {
        boolean bl = false;
        if (this.b != null) {
            Object object = this.a;
            Object object2 = this.b;
            String string = object.getAppProperty("MIDlet-Vendor");
            object = object.getAppProperty("MIDlet-Name");
            object = string;
            if (!((Hashtable)object2).containsKey(au.a((String)object, string = object))) {
                boolean bl2 = true;
                object = this.a;
                object2 = this.b;
                string = object.getAppProperty("MIDlet-Vendor");
                object = object.getAppProperty("MIDlet-Name");
                String string2 = string;
                boolean bl3 = true;
                string = object;
                object = string2;
                object = au.a((String)object, string);
                ((Hashtable)object2).put(object, "true");
                this.c();
                this.b();
            }
            if (this.b != null) {
                object = this.a;
                object2 = this.b;
                string = object.getAppProperty("MIDlet-Vendor");
                object = object.getAppProperty("MIDlet-Name");
                String string3 = string;
                string = object;
                object = string3;
                bl = (object2 = (String)((Hashtable)object2).get(object = au.a((String)object, string))) != null && ((String)object2).equals("true");
            }
        }
        return bl;
    }
}

