/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.io.Connector
 *  javax.microedition.io.HttpConnection
 */
import com.hardwire.blob.Main;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class j
implements Runnable {
    private Main a;
    byte[] a;
    String a;
    private boolean a;
    byte a;
    private HttpConnection a = null;

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void run() {
        StringBuffer stringBuffer;
        block50: {
            OutputStream outputStream;
            InputStream inputStream;
            block49: {
                String string = "http://hardwire.cz:80/gish/";
                switch (this.a) {
                    case 0: {
                        string = string + "upload_score_goty.php";
                        break;
                    }
                    case 1: {
                        if (this.a.indexOf(47) == -1) {
                            string = "http://www.gishmobile.com/levels/" + this.a;
                            break;
                        }
                        string = this.a;
                        if (string.startsWith("http://")) break;
                        string = "http://" + string;
                        break;
                    }
                }
                string = string.replace(' ', '+');
                Main.c = false;
                this.a = null;
                inputStream = null;
                outputStream = null;
                stringBuffer = null;
                int n2 = 0;
                this.a = false;
                this.a = (HttpConnection)Connector.open((String)string, (int)3);
                if (this.a != null) {
                    this.a.setRequestProperty("Content-Type", "application/octet-stream");
                    this.a.setRequestProperty("Content-Length", Integer.toString(this.a.length + (this.a == null ? 0 : this.a.length())));
                    this.a.setRequestMethod("POST");
                    outputStream = this.a.openOutputStream();
                    if (this.a != null) {
                        outputStream.write(this.a.getBytes());
                    }
                    outputStream.write(this.a);
                    int n3 = this.a.getResponseCode();
                    switch (n3) {
                        case 200: 
                        case 201: {
                            stringBuffer = new StringBuffer();
                            inputStream = this.a.openInputStream();
                            while ((n3 = inputStream.read()) != -1) {
                                stringBuffer.append((char)n3);
                            }
                            if (stringBuffer.toString().compareTo("ok") == 0) break;
                        }
                        default: {
                            this.a = true;
                            break;
                        }
                    }
                    break block49;
                }
                this.a.setRequestMethod("GET");
                this.a.setRequestProperty("Content-Type", "x-www-form-urlencoded");
                inputStream = this.a.openInputStream();
                switch (this.a.getResponseCode()) {
                    case 200: 
                    case 201: {
                        int n4;
                        this.a = new byte[10000];
                        n2 = 0;
                        while ((n4 = inputStream.read()) != -1) {
                            this.a[n2] = (byte)n4;
                            if (++n2 < this.a.length) continue;
                            this.a = true;
                        }
                        break;
                    }
                    default: {
                        this.a = true;
                    }
                }
            }
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
                if (this.a != null) {
                    this.a.close();
                }
            }
            catch (IOException iOException) {}
            this.a = null;
            break block50;
            catch (Exception exception) {
                try {
                    this.a = true;
                }
                catch (Throwable throwable) {
                    try {
                        if (inputStream != null) {
                            inputStream.close();
                        }
                        if (outputStream != null) {
                            outputStream.close();
                        }
                        if (this.a != null) {
                            this.a.close();
                        }
                    }
                    catch (IOException iOException) {}
                    this.a = null;
                    throw throwable;
                }
                try {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    if (outputStream != null) {
                        outputStream.close();
                    }
                    if (this.a != null) {
                        this.a.close();
                    }
                }
                catch (IOException iOException) {}
                this.a = null;
            }
        }
        Main.c = true;
        if (stringBuffer != null && stringBuffer.length() > 0 && stringBuffer.charAt(stringBuffer.length() - 1) == '\u0000') {
            stringBuffer.delete(stringBuffer.length() - 1, stringBuffer.length());
        }
        if (stringBuffer != null && stringBuffer.toString().compareTo("ok") != 0) {
            this.a = true;
        }
        try {
            Thread.sleep(1000L);
        }
        catch (InterruptedException interruptedException) {}
        if (this.a) {
            switch (this.a) {
                case 0: {
                    this.a.a.a((byte)34);
                    break;
                }
                case 1: {
                    this.a.a.a((byte)62);
                }
            }
        } else {
            switch (this.a) {
                case 0: {
                    this.a.a.a((byte)35);
                    break;
                }
                case 1: {
                    if (this.a.indexOf(47) != -1) {
                        this.a = this.a.substring(this.a.lastIndexOf(47) + 1);
                    }
                    this.a.a.a(this.a, this.a);
                    break;
                }
            }
        }
        this.a = null;
        this.a = null;
    }

    public j(Main main) {
        this.a = main;
    }
}

