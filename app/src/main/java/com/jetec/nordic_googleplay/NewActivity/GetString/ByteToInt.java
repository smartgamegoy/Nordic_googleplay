package com.jetec.nordic_googleplay.NewActivity.GetString;

public class ByteToInt {

    public ByteToInt(){
        super();
    }

    public int byteToInt(byte[] b) {
        if (b.length == 4)
            return b[0] << 24 | (b[1] & 0xff) << 16 | (b[2] & 0xff) << 8
                    | (b[3] & 0xff);
        else if(b.length == 3)
            return b[0] << 16 | (b[1] & 0xff) << 8 | (b[2] & 0xff);
        else if (b.length == 2)
            return (b[0] & 0xff) << 8 | (b[1] & 0xff);
        return 0;
    }

    public byte[] intToByteArray(int a) {
        byte[] ret = new byte[3];
        ret[2] = (byte) (a & 0xFF);
        ret[1] = (byte) ((a >> 8) & 0xFF);
        ret[0] = (byte) ((a >> 16) & 0xFF);
        return ret;
    }
}
