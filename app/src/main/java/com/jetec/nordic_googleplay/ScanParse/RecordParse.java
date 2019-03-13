package com.jetec.nordic_googleplay.ScanParse;

import android.annotation.SuppressLint;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class RecordParse {

    /**
     * private static final int EBLE_FLAGS           = 0x01;//«Flags»	Bluetooth Core Specification:
     * private static final int EBLE_16BitUUIDInc    = 0x02;//«Incomplete List of 16-bit Service Class UUIDs»	Bluetooth Core Specification:
     * private static final int EBLE_16BitUUIDCom    = 0x03;//«Complete List of 16-bit Service Class UUIDs»	Bluetooth Core Specification:
     * private static final int EBLE_32BitUUIDInc    = 0x04;//«Incomplete List of 32-bit Service Class UUIDs»	Bluetooth Core Specification:
     * private static final int EBLE_32BitUUIDCom    = 0x05;//«Complete List of 32-bit Service Class UUIDs»	Bluetooth Core Specification:
     * private static final int EBLE_128BitUUIDInc   = 0x06;//«Incomplete List of 128-bit Service Class UUIDs»	Bluetooth Core Specification:
     * private static final int EBLE_128BitUUIDCom   = 0x07;//«Complete List of 128-bit Service Class UUIDs»	Bluetooth Core Specification:
     * private static final int EBLE_SHORTNAME       = 0x08;//«Shortened Local Name»	Bluetooth Core Specification:
     * private static final int EBLE_LOCALNAME       = 0x09;//«Complete Local Name»	Bluetooth Core Specification:
     * private static final int EBLE_TXPOWERLEVEL    = 0x0A;//«Tx Power Level»	Bluetooth Core Specification:
     * private static final int EBLE_DEVICECLASS     = 0x0D;//«Class of Device»	Bluetooth Core Specification:
     * private static final int EBLE_SIMPLEPAIRHASH  = 0x0E;//«Simple Pairing Hash C»	Bluetooth Core Specification:​«Simple Pairing Hash C-192»	​Core Specification Supplement, Part A, section 1.6
     * private static final int EBLE_SIMPLEPAIRRAND  = 0x0F;//«Simple Pairing Randomizer R»	Bluetooth Core Specification:​«Simple Pairing Randomizer R-192»	​Core Specification Supplement, Part A, section 1.6
     * private static final int EBLE_DEVICEID        = 0x10;//«Device ID»	Device ID Profile v1.3 or later,«Security Manager TK Value»	Bluetooth Core Specification:
     * private static final int EBLE_SECURITYMANAGER = 0x11;//«Security Manager Out of Band Flags»	Bluetooth Core Specification:
     * private static final int EBLE_SLAVEINTERVALRA = 0x12;//«Slave Connection Interval Range»	Bluetooth Core Specification:
     * private static final int EBLE_16BitSSUUID     = 0x14;//«List of 16-bit Service Solicitation UUIDs»	Bluetooth Core Specification:
     * private static final int EBLE_128BitSSUUID    = 0x15;//«List of 128-bit Service Solicitation UUIDs»	Bluetooth Core Specification:
     * private static final int EBLE_SERVICEDATA     = 0x16;//«Service Data»	Bluetooth Core Specification:​«Service Data - 16-bit UUID»	​Core Specification Supplement, Part A, section 1.11
     * private static final int EBLE_PTADDRESS       = 0x17;//«Public Target Address»	Bluetooth Core Specification:
     * private static final int EBLE_RTADDRESS       = 0x18;;//«Random Target Address»	Bluetooth Core Specification:
     * private static final int EBLE_APPEARANCE      = 0x19;//«Appearance»	Bluetooth Core Specification:
     * private static final int EBLE_DEVADDRESS      = 0x1B;//«​LE Bluetooth Device Address»	​Core Specification Supplement, Part A, section 1.16
     * private static final int EBLE_LEROLE          = 0x1C;//«​LE Role»	​Core Specification Supplement, Part A, section 1.17
     * private static final int EBLE_PAIRINGHASH     = 0x1D;//«​Simple Pairing Hash C-256»	​Core Specification Supplement, Part A, section 1.6
     * private static final int EBLE_PAIRINGRAND     = 0x1E;//«​Simple Pairing Randomizer R-256»	​Core Specification Supplement, Part A, section 1.6
     * private static final int EBLE_32BitSSUUID     = 0x1F;//​«List of 32-bit Service Solicitation UUIDs»	​Core Specification Supplement, Part A, section 1.10
     * private static final int EBLE_32BitSERDATA    = 0x20;//​«Service Data - 32-bit UUID»	​Core Specification Supplement, Part A, section 1.11
     * private static final int EBLE_128BitSERDATA   = 0x21;//​«Service Data - 128-bit UUID»	​Core Specification Supplement, Part A, section 1.11
     * private static final int EBLE_SECCONCONF      = 0x22;//​«​LE Secure Connections Confirmation Value»	​Core Specification Supplement Part A, Section 1.6
     * private static final int EBLE_SECCONRAND      = 0x23;//​​«​LE Secure Connections Random Value»	​Core Specification Supplement Part A, Section 1.6​
     * private static final int EBLE_3DINFDATA       = 0x3D;//​​«3D Information Data»	​3D Synchronization Profile, v1.0 or later
     **/
    private static final int EBLE_MANDATA = 0xFF;//«Manufacturer Specific Data»	Bluetooth Core Specification:
    private static final int EBLE_32BitUUIDCom = 0x05;
    private static final int EBLE_128BitUUIDCom = 0x07;

    private String TAG = "RecordParse";

    public RecordParse() {
        super();
    }

    public Map<Integer, String> ParseRecord(byte[] scanRecord) {
        @SuppressLint("UseSparseArrays")
        Map<Integer, String> ret = new HashMap<>();
        int index = 0;
        while (index < scanRecord.length) {
            int length = byteToInt(scanRecord[index++]);
            //Zero value indicates that we are done with the record now
            if (length == 0) break;

            int type = byteToInt(scanRecord[index]);
            //if the type is zero, then we are pass the significant section of the data,
            // and we are thud done
            if (type == 0) {
                break;
            }
            byte[] data = Arrays.copyOfRange(scanRecord, index + 1, index + length);
            if (data.length > 0) {
                StringBuilder hex = new StringBuilder(data.length * 2);
                // the data appears to be there backwards
                for (byte aData : data) {
                    hex.append(String.format("%02X", aData));
                }
                String gethex = hex.toString();
                if(type == EBLE_128BitUUIDCom){
                    String resetuuid = "";
                            ArrayList<Character> uuid = new ArrayList<>();
                    for(int i = 0; i < gethex.length(); i++){
                        uuid.add(gethex.charAt(i));
                    }
                    for(int j = uuid.size() - 2; j > -2; j = j - 2){
                        resetuuid = resetuuid + uuid.get(j).toString() + uuid.get(j + 1).toString();
                    }
                    gethex = resetuuid;
                }
                if (type == EBLE_MANDATA) {
                    gethex = gethex.substring(2, 4) +
                            gethex.substring(0, 2) + gethex.substring(4, gethex.length());
                }
                ret.put(type, gethex);
            }
            index += length;
        }
        return ret;
    }

    public String getServiceUUID(Map <Integer,String> record){
        String ret = "";
        // for example: 0105FACB00B01000800000805F9B34FB --> 010510ee-0000-1000-8000-00805f9b34fb
        if(record.containsKey(EBLE_128BitUUIDCom)){
            String tmpString= record.get(EBLE_128BitUUIDCom).toString();
            ret = tmpString.substring(0, 8) + "-" + tmpString.substring(8,12)+ "-" + tmpString.substring(12,16)+ "-" + tmpString.substring(16,20)+ "-" + tmpString.substring(20,tmpString.length());
            //010510EE --> 010510ee-0000-1000-8000-00805f9b34fb
        }else if(record.containsKey(EBLE_32BitUUIDCom)){
            ret = record.get(EBLE_32BitUUIDCom).toString() + "-0000-1000-8000-00805f9b34fb";
        }
        return ret;
    }

    private int byteToInt(byte b) {
        return b & 0xFF;
    }
}
