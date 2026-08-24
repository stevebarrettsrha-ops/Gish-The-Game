/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.bluetooth.DeviceClass
 *  javax.bluetooth.DiscoveryAgent
 *  javax.bluetooth.DiscoveryListener
 *  javax.bluetooth.LocalDevice
 *  javax.bluetooth.RemoteDevice
 *  javax.bluetooth.ServiceRecord
 */
import java.io.IOException;
import java.util.Enumeration;
import java.util.Vector;
import javax.bluetooth.DeviceClass;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.DiscoveryListener;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.RemoteDevice;
import javax.bluetooth.ServiceRecord;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class ar
implements DiscoveryListener {
    private static DiscoveryAgent a;
    private bc a;
    private Vector b;
    public Vector a;
    public volatile boolean a;
    private volatile boolean b = true;
    private volatile int a = -1;

    public final void a(bc bc2) {
        if (this.a) {
            throw new IllegalStateException();
        }
        this.a = bc2;
        this.a = true;
        try {
            this.b = new Vector();
            this.a = new Vector();
            this.b = false;
            this.a = -1;
            int n2 = 0;
            if (!ar.a().startInquiry(10390323, (DiscoveryListener)this)) {
                throw new IOException();
            }
            this.b();
            if (this.a == -1 || this.a == 7) {
                throw new IOException();
            }
            n2 = 0;
            int n3 = this.b.size();
            int[] nArray = new int[2];
            Enumeration enumeration = this.b.elements();
            while (enumeration.hasMoreElements()) {
                nArray[0] = ++n2;
                nArray[1] = n3;
                this.a.a(an.a().a(7, nArray));
                Object object = (RemoteDevice)enumeration.nextElement();
                String string = object.getBluetoothAddress();
                String string2 = null;
                for (int i2 = 0; string2 == null && i2 < 3; ++i2) {
                    try {
                        if (i2 > 0) {
                            try {
                                Thread.sleep(i2 * 1000);
                            }
                            catch (InterruptedException interruptedException) {}
                        }
                        string2 = object.getFriendlyName(true);
                        continue;
                    }
                    catch (IOException iOException) {}
                }
                if (string2 != null && (string2 = string2.trim()).length() <= 0) {
                    string2 = null;
                }
                String string3 = string2;
                if (string2 == null) {
                    string3 = string;
                }
                object = new bf(string3, (RemoteDevice)object);
                this.a.addElement(object);
            }
            return;
        }
        finally {
            this.a = false;
        }
    }

    public final void a() {
        ar.a().cancelInquiry((DiscoveryListener)this);
    }

    private synchronized void b() {
        if (!this.b) {
            try {
                this.wait(60000L);
            }
            catch (InterruptedException interruptedException) {}
            if (!this.b) {
                try {
                    this.a();
                    try {
                        this.wait(5000L);
                    }
                    catch (InterruptedException interruptedException) {}
                }
                catch (IOException iOException) {}
            }
        }
        if (!this.b) {
            this.b = true;
        }
    }

    public final void deviceDiscovered(RemoteDevice remoteDevice, DeviceClass deviceClass) {
        switch (deviceClass.getMajorDeviceClass()) {
            case 256: 
            case 512: 
            case 768: {
                return;
            }
        }
        this.b.addElement(remoteDevice);
        int n2 = this.b.size();
        this.a.a(an.a().a(6, new int[]{n2}));
    }

    public final synchronized void inquiryCompleted(int n2) {
        this.a = n2;
        this.b = true;
        this.notify();
    }

    public final void servicesDiscovered(int n2, ServiceRecord[] serviceRecordArray) {
        throw new IllegalStateException();
    }

    public final void serviceSearchCompleted(int n2, int n3) {
        throw new IllegalStateException();
    }

    static DiscoveryAgent a() {
        if (a == null) {
            try {
                Thread.sleep(1000L);
            }
            catch (InterruptedException interruptedException) {}
            LocalDevice localDevice = bf.a();
            if (localDevice == null) {
                throw new IOException();
            }
            try {
                Thread.sleep(1000L);
            }
            catch (InterruptedException interruptedException) {}
            a = localDevice.getDiscoveryAgent();
        }
        return a;
    }
}

