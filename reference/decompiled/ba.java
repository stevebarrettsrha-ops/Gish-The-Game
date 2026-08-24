/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.rms.RecordEnumeration
 *  javax.microedition.rms.RecordStore
 *  javax.microedition.rms.RecordStoreException
 */
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.util.Hashtable;
import javax.microedition.rms.RecordEnumeration;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;

final class ba {
    private ba() {
    }

    static Hashtable a(boolean bl) {
        Hashtable hashtable;
        block7: {
            RecordStore recordStore = null;
            try {
                recordStore = bl ? ba.a("com.zeemote.zc.lzp", true) : ba.a();
                hashtable = ba.a(recordStore);
                if (null == recordStore) break block7;
            }
            catch (Throwable throwable) {
                if (null != recordStore) {
                    try {
                        recordStore.closeRecordStore();
                    }
                    catch (RecordStoreException recordStoreException) {}
                }
                throw throwable;
            }
            try {
                recordStore.closeRecordStore();
            }
            catch (RecordStoreException recordStoreException) {}
        }
        return hashtable;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static void a(Hashtable hashtable, boolean bl) {
        RecordStore recordStore = null;
        try {
            ByteArrayOutputStream byteArrayOutputStream;
            block22: {
                recordStore = bl ? ba.a("com.zeemote.zc.lzp", true) : ba.a();
                RecordStore recordStore2 = recordStore;
                RecordEnumeration recordEnumeration = null;
                DataOutputStream dataOutputStream = null;
                byteArrayOutputStream = null;
                try {
                    byteArrayOutputStream = new ByteArrayOutputStream();
                    dataOutputStream = new DataOutputStream(byteArrayOutputStream);
                    dataOutputStream.writeInt(1);
                    Object object = hashtable.keys();
                    while (object.hasMoreElements()) {
                        String string = (String)object.nextElement();
                        String string2 = (String)hashtable.get(string);
                        dataOutputStream.writeUTF(string);
                        dataOutputStream.writeUTF(string2);
                    }
                    dataOutputStream.flush();
                    dataOutputStream.close();
                    dataOutputStream = null;
                    object = byteArrayOutputStream.toByteArray();
                    recordEnumeration = recordStore2.enumerateRecords(null, null, false);
                    while (recordEnumeration.hasNextElement()) {
                        recordStore2.deleteRecord(recordEnumeration.nextRecordId());
                    }
                    recordStore2.addRecord((byte[])object, 0, ((Object)object).length);
                    if (null == recordEnumeration) break block22;
                }
                catch (RecordStoreException recordStoreException) {
                    try {
                        throw new IOException(recordStoreException.getMessage());
                        catch (SecurityException securityException) {
                            throw new IOException(securityException.getMessage());
                        }
                    }
                    catch (Throwable throwable) {
                        if (null != recordEnumeration) {
                            recordEnumeration.destroy();
                        }
                        if (null != byteArrayOutputStream) {
                            try {
                                byteArrayOutputStream.close();
                            }
                            catch (IOException iOException) {}
                        }
                        if (null == dataOutputStream) throw throwable;
                        try {
                            dataOutputStream.close();
                            throw throwable;
                        }
                        catch (IOException iOException) {}
                        throw throwable;
                    }
                }
                recordEnumeration.destroy();
            }
            if (null != byteArrayOutputStream) {
                try {
                    byteArrayOutputStream.close();
                }
                catch (IOException iOException) {}
            }
            if (null == recordStore) return;
        }
        catch (Throwable throwable) {
            if (null == recordStore) throw throwable;
            try {
                recordStore.closeRecordStore();
                throw throwable;
            }
            catch (RecordStoreException recordStoreException) {}
            throw throwable;
        }
        try {
            recordStore.closeRecordStore();
            return;
        }
        catch (RecordStoreException recordStoreException) {
            return;
        }
    }

    private static RecordStore a() {
        try {
            return RecordStore.openRecordStore((String)"gzp", (String)"Zeemote, Inc.", (String)"Zeemote Manager");
        }
        catch (RecordStoreException recordStoreException) {
            throw new IOException(recordStoreException.getMessage());
        }
        catch (SecurityException securityException) {
            throw new IOException(securityException.getMessage());
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static Hashtable a(RecordStore object) {
        Object object2;
        RecordEnumeration recordEnumeration;
        block15: {
            recordEnumeration = null;
            FilterInputStream filterInputStream = null;
            try {
                recordEnumeration = object.enumerateRecords(null, null, false);
                object = new Hashtable();
                if (recordEnumeration.hasNextElement()) {
                    byte[] byArray = recordEnumeration.nextRecord();
                    object2 = byArray;
                    if (byArray != null && (filterInputStream = new DataInputStream(new ByteArrayInputStream((byte[])object2))).available() > 0) {
                        boolean bl = false;
                        if (((DataInputStream)filterInputStream).readInt() != 1) {
                            throw new IOException();
                        }
                        while (filterInputStream.available() > 0) {
                            String string = ((DataInputStream)filterInputStream).readUTF();
                            String string2 = ((DataInputStream)filterInputStream).readUTF();
                            ((Hashtable)object).put(string, string2);
                        }
                    }
                }
                object2 = object;
                if (null == filterInputStream) break block15;
            }
            catch (RecordStoreException recordStoreException) {
                try {
                    throw new IOException(recordStoreException.getMessage());
                }
                catch (Throwable throwable) {
                    if (null != filterInputStream) {
                        try {
                            filterInputStream.close();
                        }
                        catch (IOException iOException) {}
                    }
                    if (null != recordEnumeration) {
                        recordEnumeration.destroy();
                    }
                    throw throwable;
                }
            }
            try {
                filterInputStream.close();
            }
            catch (IOException iOException) {}
        }
        if (null != recordEnumeration) {
            recordEnumeration.destroy();
        }
        return object2;
    }

    private static RecordStore a(String string, boolean bl) {
        RecordStore recordStore = null;
        try {
            recordStore = RecordStore.openRecordStore((String)string, (boolean)true);
            recordStore.setMode(0, true);
            return recordStore;
        }
        catch (RecordStoreException recordStoreException) {
            throw new IOException(recordStoreException.getMessage());
        }
        catch (SecurityException securityException) {
            throw new IOException(securityException.getMessage());
        }
    }
}

