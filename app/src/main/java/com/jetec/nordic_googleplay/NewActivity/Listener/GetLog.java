package com.jetec.nordic_googleplay.NewActivity.Listener;

import android.content.Context;
import android.util.Log;

import com.jetec.nordic_googleplay.NewActivity.UserSQL.LogSQL;

import java.util.ArrayList;
import java.util.List;

public class GetLog {

    private String TAG = "GetLog";
    private LogListener logListener;
    private List<List<String>> loglist;

    public void setListener(LogListener mlogListener) {
        logListener = mlogListener;
    }

    public void readytointent() {
        if (logListener != null) {
            logListener.getjson();
        }
    }

    public void showlog() {
        if (logListener != null) {
            logListener.showjson();
        }
    }

    public void getJsonlist(LogSQL logSQL, String name){
        loglist = new ArrayList<>();
        loglist.clear();
        List<String> jsonlist = new ArrayList<>();
        jsonlist.clear();

        jsonlist = logSQL.getjson(name);

        Log.e(TAG, "jsonlist = " + jsonlist);


    }
}
