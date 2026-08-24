/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.bluetooth.DataElement
 *  javax.bluetooth.DiscoveryAgent
 *  javax.bluetooth.DiscoveryListener
 *  javax.bluetooth.L2CAPConnection
 *  javax.bluetooth.L2CAPConnectionNotifier
 *  javax.bluetooth.LocalDevice
 *  javax.bluetooth.RemoteDevice
 *  javax.bluetooth.ServiceRecord
 *  javax.bluetooth.UUID
 *  javax.microedition.io.Connection
 *  javax.microedition.io.Connector
 */
import com.hardwire.blob.Main;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Vector;
import javax.bluetooth.DataElement;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.DiscoveryListener;
import javax.bluetooth.L2CAPConnection;
import javax.bluetooth.L2CAPConnectionNotifier;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.RemoteDevice;
import javax.bluetooth.ServiceRecord;
import javax.bluetooth.UUID;
import javax.microedition.io.Connection;
import javax.microedition.io.Connector;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class r
implements Runnable {
    private int b;
    private int c;
    private L2CAPConnection a;
    LocalDevice a;
    private L2CAPConnectionNotifier a;
    Vector a;
    private String a;
    int a;
    byte a;
    byte b;
    private s a;
    private boolean b = false;
    public boolean a = null;
    private boolean c = false;

    public r(s s2) {
        this.a = s2;
    }

    void a() {
        if (this.c) {
            return;
        }
        try {
            this.a = LocalDevice.getLocalDevice();
            this.a = this.a.getFriendlyName();
            if (this.a == null || this.a.length() == 0 || this.a.compareTo(" ") == 0) {
                this.a = "unknown";
            } else {
                StringBuffer stringBuffer = new StringBuffer(this.a);
                int n2 = stringBuffer.length();
                for (int i2 = 0; i2 < n2; ++i2) {
                    char c2 = stringBuffer.charAt(i2);
                    if (c2 >= 'A' && c2 <= 'Z' || c2 >= 'a' && c2 <= 'z' || c2 >= '0' && c2 <= '9') continue;
                    stringBuffer.setCharAt(i2, '_');
                }
                this.a = stringBuffer.toString();
            }
            this.c = true;
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void run() {
        switch (this.b) {
            case 1: {
                r r2 = this;
                boolean bl = false;
                try {
                    DiscoveryAgent discoveryAgent = r2.a.getDiscoveryAgent();
                    r2.a.setDiscoverable(0);
                    ai ai2 = new ai(r2);
                    Object object = ai2;
                    synchronized (ai2) {
                        discoveryAgent.startInquiry(10390323, (DiscoveryListener)ai2);
                        try {
                            ai2.wait();
                        }
                        catch (InterruptedException interruptedException) {}
                        object = ai2.a.elements();
                        while (object.hasMoreElements()) {
                            ai ai3 = ai2;
                            synchronized (ai3) {
                                discoveryAgent.searchServices(new int[]{256}, new UUID[]{new UUID("01834587449266546213012382234327", false)}, (RemoteDevice)object.nextElement(), (DiscoveryListener)ai2);
                                try {
                                    ai2.wait();
                                }
                                catch (InterruptedException interruptedException) {}
                            }
                        }
                        try {
                            Thread.sleep(1L);
                        }
                        catch (InterruptedException interruptedException) {}
                        r2.a = ai2.b;
                    }
                }
                catch (Exception exception) {
                    bl = true;
                }
                {
                    r2.a.a(bl);
                    return;
                }
            }
            case 0: {
                r r3 = this;
                boolean bl = false;
                Main.c = false;
                if (r3.b) {
                    try {
                        r3.a.setDiscoverable(10390323);
                    }
                    catch (Exception exception) {}
                    Main.c = true;
                    break;
                }
                try {
                    r3.a.setDiscoverable(10390323);
                    String string = "btl2cap://localhost:01834587449266546213012382234327;ReceiveMTU=512;TransmitMTU=512;authenticate=true;authorize=true;encrypt=false;name=" + r3.a;
                    r3.a = (L2CAPConnectionNotifier)Connector.open((String)string);
                    ServiceRecord serviceRecord = r3.a.getRecord((Connection)r3.a);
                    serviceRecord.setAttributeValue(8, new DataElement(8, 255L));
                    serviceRecord.setDeviceServiceClasses(0x400000);
                }
                catch (Exception exception) {
                    bl = true;
                }
                Main.c = true;
                r3.a.b(bl);
                if (!bl) {
                    r3.b = true;
                    try {
                        r3.a = r3.a.acceptAndOpen();
                        r3.b = r3.a.getReceiveMTU();
                        r3.c = r3.a.getTransmitMTU();
                        r3.b = false;
                        r3.a = false;
                        Main.c = false;
                        try {
                            r3.a.setDiscoverable(0);
                            r3.a.close();
                        }
                        catch (Exception exception) {}
                        Main.c = true;
                    }
                    catch (Exception exception) {
                        bl = true;
                    }
                }
                r3.a.c(bl);
                return;
            }
            case 2: {
                r r4 = this;
                boolean bl = false;
                Main.c = false;
                try {
                    ServiceRecord serviceRecord = (ServiceRecord)r4.a.elementAt(r4.a);
                    String string = serviceRecord.getConnectionURL(1, false);
                    if (string.indexOf("ReceiveMTU") == -1) {
                        string = string + ";ReceiveMTU=512";
                    }
                    if (string.indexOf("TransmitMTU") == -1) {
                        string = string + ";TransmitMTU=512";
                    }
                    r4.a = (L2CAPConnection)Connector.open((String)string);
                    r4.b = r4.a.getReceiveMTU();
                    r4.c = r4.a.getTransmitMTU();
                    r4.a = false;
                }
                catch (Exception exception) {
                    bl = true;
                }
                Main.c = true;
                r4.a.d(bl);
            }
        }
    }

    public final void b() {
        try {
            this.a = true;
            this.a.close();
        }
        catch (Exception exception) {}
        try {
            this.a.setDiscoverable(0);
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    public final ByteArrayInputStream a() {
        try {
            Object object = new byte[this.b];
            this.a.receive(object);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream((byte[])object);
            object = byteArrayInputStream;
            return byteArrayInputStream;
        }
        catch (Exception exception) {
            return null;
        }
    }

    public final boolean a() {
        if (this.a) {
            return true;
        }
        try {
            return this.a.ready();
        }
        catch (IOException iOException) {
            return true;
        }
    }

    public final boolean a(byte[] byArray) {
        try {
            this.a.send(byArray);
            return true;
        }
        catch (Exception exception) {
            return false;
        }
    }
}

