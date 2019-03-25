package com.jetec.nordic_googleplay.ScanParse;

import android.util.Log;

import com.jetec.nordic_googleplay.NewActivity.GetString.Get;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Getparse {

    //[row, str, point, value]
    private String TAG = "Getparse";
    private Get get = new Get();
    private List<String> s1, s2, s3, s4, s5, s6, s7;

    public Getparse() {
        super();
        s1 = new ArrayList<>();
        s2 = new ArrayList<>();
        s3 = new ArrayList<>();
        s4 = new ArrayList<>();
        s5 = new ArrayList<>();
        s6 = new ArrayList<>();
        s7 = new ArrayList<>();

        s1.clear();
        s2.clear();
        s3.clear();
        s4.clear();
        s5.clear();
        s6.clear();
        s7.clear();
    }

    public String parsebyte(byte[] btValue){
        String text = "", value = "";
        //Log.e(TAG, "btValue.length = " + btValue.length);
        if(btValue.length == 7){
            String str = get_String(btValue[1], btValue[0]);
            byte[] data = Arrays.copyOfRange(btValue, 3, btValue.length);
            String str2 = get.todo(str, btValue[2], byteArrayToInt(data));
            Log.e(TAG, "data = " + byteArrayToInt(data));
            //Log.e(TAG, "str2 = " + str2);
            if(btValue[0] == 1){
                s1.add(str2);
            }
            else if(btValue[0] == 2){
                s2.add(str2);
            }
            else if(btValue[0] == 3){
                s3.add(str2);
            }
            else if(btValue[0] == 4){
                s4.add(str2);
            }
            else if(btValue[0] == 5){
                s5.add(str2);
            }
            else if(btValue[0] == 6){
                s6.add(str2);
            }
            else if(btValue[0] == 7){
                s7.add(str2);
            }

            text = str2;
        }
        Log.e(TAG,"s1 = " + s1);
        Log.e(TAG,"s2 = " + s2);
        Log.e(TAG,"s3 = " + s3);
        Log.e(TAG,"s4 = " + s4);
        Log.e(TAG,"s5 = " + s5);
        Log.e(TAG,"s6 = " + s6);
        Log.e(TAG,"s7 = " + s7);
        return text;
    }

    private String get_String(byte a, byte b){
        String str = "";

        if(a == 0x01){
            str = "PV" + b;
        }
        else if(a == 0x02){
            str = "EH" + b;
        }
        else if(a == 0x03){
            str = "EL" + b;
        }
        else if(a == 0x04){
            str = "IH" + b;
        }
        else if(a == 0x05){
            str = "IL" + b;
        }
        else if(a == 0x06){
            str = "CR" + b;
        }
        else if(a == 0x07){
            str = "ADR";
        }
        else if(a == 0x08){
            str = "Register" + b;
        }
        else if(a == 0x09){
            str = "length" + b;
        }
        else if(a == 0x0A){
            str = "RL1";
        }
        else if(a == 0x0B){
            str = "RL2";
        }
        else if(a == 0x0C){
            str = "RL3";
        }
        else if(a == 0x0D){ //4-20mA output
            str = "OT1";
        }
        else if(a == 0x0E){
            str = "OT2";
        }
        else if(a == 0x0F){
            str = "OT3";
        }
        /*
        PV 1
        EH 2
        EL 3
        IH 4
        IL 5
        CR 6
        ADR 7
        Register 8
        length 9
        RL1 10
        RL2 11
        RL3 12
        */
        return str;
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
