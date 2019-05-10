package com.jetec.nordic_googleplay.NewActivity.Listener;

import android.content.Context;
import android.util.Log;

import com.jetec.nordic_googleplay.NewActivity.Simulate;
import com.jetec.nordic_googleplay.NewActivity.UserSQL.LogSQL;

import org.json.JSONArray;
import org.json.JSONException;

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

    public void endDownload() {
        if (logListener != null) {
            logListener.getEnd();
        }
    }

    public void showlog() {
        if (logListener != null) {
            logListener.showjson();
        }
    }

    public void getJsonlist(LogSQL logSQL, String name) {
        try {
            loglist = new ArrayList<>();
            loglist.clear();
            List<String> jsonlist = new ArrayList<>();
            jsonlist.clear();

            jsonlist = logSQL.getjson(name);

            List<String> namelist = new ArrayList<>();
            namelist.clear();
            String savename = jsonlist.get(0);
            for (int i = 0; i < savename.length(); i++) {
                namelist.add(String.valueOf(savename.charAt(i)));
            }
            loglist.add(namelist);

            List<String> timelist = new ArrayList<>();
            timelist.clear();
            String savetime = jsonlist.get(1);
            JSONArray timejson = new JSONArray(savetime);
            for(int i = 0; i < timejson.length(); i++){
                timelist.add(timejson.get(i).toString());
            }
            loglist.add(timelist);

            String savelist = jsonlist.get(2);
            JSONArray listjson = new JSONArray(savelist);
            for(int i = 0; i < listjson.length(); i++){
                List<String> rowlist = new ArrayList<>();
                rowlist.clear();
                String rowString = listjson.get(i).toString();
                JSONArray rowjson = new JSONArray(rowString);
                for(int j = 0; j < rowjson.length(); j++){
                    rowlist.add(rowjson.get(j).toString());
                }
                loglist.add(rowlist);
            }
            Log.e(TAG, "alllist = " + namelist);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public List<List<String>> getLoglist(){
        /*Simulate simulate = new Simulate();
        loglist = simulate.simulate();
        Log.e(TAG, "loglist = " + loglist);*/
        return loglist;
    }
}
