package com.jetec.nordic_googleplay;

import android.util.Log;

public class GetString {

    public GetString(){
        super();
    }

    public void set(String text){
        String TAG = "GetString";
        if(text.startsWith("COUNT")) {
            Value.Count = text.substring(5);
            Log.e(TAG, "Count = " + Value.Count);
        }
        if(text.startsWith("TIME")) {
            Value.Time = text.substring(4);
            Log.e(TAG, "Time = " + Value.Time);
        }
        if(text.startsWith("DATE")) {
            Value.Date = text.substring(4);
            Log.e(TAG, "Date = " + Value.Date);
        }
        if(text.startsWith("LOG")){
            Value.GetLog = text.substring(3);
            if(Value.GetLog.matches("ON")){
                Value.downlog = true;
            }
            else if(Value.GetLog.matches("OFF")){
                Value.downlog = false;
            }
            Log.e(TAG, "LOG = " + Value.GetLog);
        }
    }
}
