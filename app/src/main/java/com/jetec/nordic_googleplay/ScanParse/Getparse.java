package com.jetec.nordic_googleplay.ScanParse;

import android.util.Log;
import java.util.Arrays;

public class Getparse {

    private String TAG = "Getparse";

    public Getparse() {
        super();
    }

    public String parsebyte(byte[] btValue){
        String text = "";
        Log.e(TAG, "btValue.length = " + btValue.length);
        if(btValue.length == 7){
            byte[] data = Arrays.copyOfRange(btValue, 3, btValue.length);
            Log.e(TAG, "btValue[0] = " + btValue[0]);
            Log.e(TAG, "btValue[0] = " + btValue[1]);
            Log.e(TAG, "btValue[0] = " + btValue[2]);
            Log.e(TAG,"btValue[1] = " + byteArrayToInt(data));
        }
        return text;
    }

    private  int byteArrayToInt(byte[] b) {
        if (b.length == 4)
            return b[0] << 24 | (b[1] & 0xff) << 16 | (b[2] & 0xff) << 8
                    | (b[3] & 0xff);
        else if (b.length == 2)
            return (b[0] & 0xff) << 8 | (b[1] & 0xff);
        return 0;
    }
}
