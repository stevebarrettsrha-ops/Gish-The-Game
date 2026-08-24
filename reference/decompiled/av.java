/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.bluetooth.BluetoothConnectionException
 *  javax.microedition.io.Connection
 *  javax.microedition.io.Connector
 *  javax.microedition.io.StreamConnection
 */
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.bluetooth.BluetoothConnectionException;
import javax.microedition.io.Connection;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
final class av
implements ah {
    private String a;
    private String b = null;
    private Connection a;
    private StreamConnection a;
    private InputStream a;
    private OutputStream a;
    boolean a = true;

    public av(String string, String string2) {
        this.a = string;
        this.b = string2;
    }

    public final String b() {
        return this.b;
    }

    public final void a() {
        Object object = this;
        object = ((av)object).b;
        if (object == null) {
            throw new IOException();
        }
        try {
            if (bf.a((String)object)) {
                bf.a();
            }
            for (int i2 = 0; this.a == null && i2 < 1; ++i2) {
                try {
                    this.a = Connector.open((String)object);
                    continue;
                }
                catch (BluetoothConnectionException bluetoothConnectionException) {
                    BluetoothConnectionException bluetoothConnectionException2 = bluetoothConnectionException;
                    if (bluetoothConnectionException.getStatus() == 5) continue;
                    throw bluetoothConnectionException2;
                }
            }
            if (this.a == null) {
                throw new IOException();
            }
            if (this.a instanceof StreamConnection) {
                this.a = (StreamConnection)this.a;
                this.a = this.a ? new bd(this.a.openInputStream()) : this.a.openInputStream();
                this.a = this.a.openOutputStream();
                return;
            }
            throw new IOException();
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw new IOException();
        }
        catch (SecurityException securityException) {
            throw securityException;
        }
        catch (RuntimeException runtimeException) {
            throw new IOException();
        }
    }

    public final void b() {
        try {
            if (this.a != null) {
                this.a.close();
                this.a = null;
            }
            if (this.a != null) {
                this.a.close();
                this.a = null;
            }
            if (this.a != null) {
                this.a.close();
                this.a = null;
            }
            return;
        }
        finally {
            this.a = null;
            this.a = null;
            this.a = null;
            this.a = null;
        }
    }

    public final boolean a() {
        return this.a != null;
    }

    public final String a() {
        return this.a;
    }

    public final boolean b() {
        return false;
    }

    public final void a(ay ay2) {
        throw new RuntimeException();
    }

    public final byte[] a(byte[] byArray) {
        int n2 = 0;
        Object var4_3 = null;
        n2 = this.a.read();
        if (n2 < 0) {
            return null;
        }
        if (byArray == null || byArray.length < n2 + 1) {
            byArray = new byte[n2 + 1];
        }
        byArray[0] = (byte)n2;
        for (int i2 = 1; i2 <= n2; ++i2) {
            var4_3 = null;
            int n3 = this.a.read();
            if (n3 < 0) {
                return null;
            }
            byArray[i2] = (byte)n3;
        }
        return byArray;
    }

    public final void a(byte[] byArray) {
        this.a.write(byArray);
        this.a.flush();
    }
}

