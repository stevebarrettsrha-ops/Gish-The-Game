/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.bluetooth.DeviceClass
 *  javax.bluetooth.DiscoveryListener
 *  javax.bluetooth.LocalDevice
 *  javax.bluetooth.RemoteDevice
 *  javax.bluetooth.ServiceRecord
 *  javax.bluetooth.UUID
 */
import java.io.IOException;
import java.util.Vector;
import javax.bluetooth.DeviceClass;
import javax.bluetooth.DiscoveryListener;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.RemoteDevice;
import javax.bluetooth.ServiceRecord;
import javax.bluetooth.UUID;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
final class bf
implements ah,
DiscoveryListener {
    private static final UUID[] a = new UUID[]{new UUID("8e1f0cf7508f4875b62cfbb67fd34812", false)};
    private static final UUID[] b = new UUID[]{new UUID(4353L)};
    private String a;
    private RemoteDevice a;
    private ah a;
    private volatile boolean a;
    private volatile int a;
    private int b;
    private Vector a = -1;

    bf(String string, RemoteDevice remoteDevice) {
        this.a = string;
        this.a = remoteDevice;
    }

    public final String b() {
        return this.a().b();
    }

    private ah a() {
        if (this.a == null) {
            String string = this.c();
            if (string == null) {
                throw new IOException();
            }
            this.a = ao.a(this.a, string);
        }
        return this.a;
    }

    private String c() {
        Object object = null;
        for (int i2 = 0; object == null && i2 < 3; ++i2) {
            if (i2 > 0) {
                try {
                    Thread.sleep(i2 * 1000);
                }
                catch (InterruptedException interruptedException) {}
            }
            bf bf2 = object = this;
            ((bf)object).a = new Vector();
            bf2.b = -1;
            bf2.a = false;
            bf2.a = -1;
            UUID[] uUIDArray = a;
            if (bf2.a != null && bf2.a.startsWith("ZeemoteLink")) {
                uUIDArray = b;
            }
            bf2.b = ar.a().searchServices(null, uUIDArray, bf2.a, (DiscoveryListener)bf2);
            if (bf2.b < 0) {
                throw new IOException();
            }
            bf2.c();
            object = ((bf)object).a.size() > 0 ? ((ServiceRecord)((bf)object).a.elementAt(0)).getConnectionURL(0, false) : null;
            if (object == null && this.a != 6) break;
        }
        return object;
    }

    public final void deviceDiscovered(RemoteDevice remoteDevice, DeviceClass deviceClass) {
        throw new IllegalStateException();
    }

    public final void inquiryCompleted(int n2) {
        throw new IllegalStateException();
    }

    public final void servicesDiscovered(int n2, ServiceRecord[] serviceRecordArray) {
        for (n2 = 0; n2 < serviceRecordArray.length; ++n2) {
            this.a.addElement(serviceRecordArray[n2]);
        }
    }

    public final synchronized void serviceSearchCompleted(int n2, int n3) {
        this.a = n3;
        this.a = true;
        this.notify();
    }

    private synchronized void c() {
        if (!this.a) {
            try {
                this.wait(60000L);
            }
            catch (InterruptedException interruptedException) {}
            if (!this.a) {
                try {
                    bf bf2 = this;
                    if (ar.a().cancelServiceSearch(bf2.b)) {
                        try {
                            this.wait(5000L);
                        }
                        catch (InterruptedException interruptedException) {}
                    }
                }
                catch (IOException iOException) {}
            }
        }
        if (!this.a) {
            this.a = true;
        }
    }

    static boolean a(String string) {
        return string != null && string.length() >= "btspp:".length() && string.substring(0, "btspp:".length()).equalsIgnoreCase("btspp:");
    }

    static LocalDevice a() {
        LocalDevice localDevice = null;
        try {
            localDevice = LocalDevice.getLocalDevice();
        }
        catch (NullPointerException nullPointerException) {}
        return localDevice;
    }

    public final void a() {
        this.a().a();
    }

    public final void b() {
        if (this.a != null) {
            this.a.b();
        }
    }

    public final String a() {
        return this.a;
    }

    public final boolean a() {
        if (this.a != null) {
            return this.a.a();
        }
        return false;
    }

    public final byte[] a(byte[] byArray) {
        if (this.a != null) {
            return this.a.a(byArray);
        }
        throw new IOException();
    }

    public final void a(ay ay2) {
        if (this.a != null) {
            this.a.a(ay2);
            return;
        }
        throw new IllegalStateException();
    }

    public final boolean b() {
        return this.a().b();
    }

    public final void a(byte[] byArray) {
        if (this.a != null) {
            this.a.a(byArray);
            return;
        }
        throw new IOException();
    }
}

