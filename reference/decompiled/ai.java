/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.bluetooth.DeviceClass
 *  javax.bluetooth.DiscoveryListener
 *  javax.bluetooth.RemoteDevice
 *  javax.bluetooth.ServiceRecord
 */
import java.util.Vector;
import javax.bluetooth.DeviceClass;
import javax.bluetooth.DiscoveryListener;
import javax.bluetooth.RemoteDevice;
import javax.bluetooth.ServiceRecord;

final class ai
implements DiscoveryListener {
    public Vector a = new Vector();
    public Vector b = new Vector();

    public ai(r r2) {
    }

    public final void deviceDiscovered(RemoteDevice remoteDevice, DeviceClass deviceClass) {
        int n2 = deviceClass.getMajorDeviceClass();
        if (n2 == 512 && (deviceClass.getServiceClasses() & 0x400000) != 0 && !this.a.contains(remoteDevice)) {
            this.a.addElement(remoteDevice);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void inquiryCompleted(int n2) {
        ai ai2 = this;
        synchronized (ai2) {
            this.notify();
            return;
        }
    }

    public final void servicesDiscovered(int n2, ServiceRecord[] serviceRecordArray) {
        if (serviceRecordArray != null && serviceRecordArray.length == 1) {
            this.b.addElement(serviceRecordArray[0]);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void serviceSearchCompleted(int n2, int n3) {
        ai ai2 = this;
        synchronized (ai2) {
            this.notify();
            return;
        }
    }
}

