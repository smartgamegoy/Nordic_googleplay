package com.jetec.nordic_googleplay.NewActivity.GetString;

public class ByteToHex {

    public ByteToHex(){
        super();
    }

    public String[] hexstring(byte[] revalue){
        int i = 0;
        String[] hex = new String[revalue.length];
        //StringBuilder sb = new StringBuilder();
        for (byte b : revalue) {
            hex[i] = String.format("%02X", b);
            //sb.append(String.format("%02X ", b));
            i++;
        }
        return hex;
    }
}
