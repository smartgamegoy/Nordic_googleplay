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

    public byte[] hex2Byte(String hexString) {
        byte[] bytes = new byte[hexString.length() / 2];
        for (int i=0 ; i < bytes.length ; i++)
            bytes[i] = (byte) Integer.parseInt(hexString.substring(2 * i, 2 * i + 2), 16);
        return bytes;
    }
}
