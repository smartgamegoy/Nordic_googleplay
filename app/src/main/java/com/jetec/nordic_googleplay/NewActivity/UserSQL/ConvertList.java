package com.jetec.nordic_googleplay.NewActivity.UserSQL;

import android.util.Log;

import com.jetec.nordic_googleplay.NewModel;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class ConvertList {

    private String TAG = "ConvertList";
    private JSONArray SQLjson, Recordjson;

    public ConvertList() {
        super();
    }

    public JSONArray getsetlist() {
        List<List<String>> setSQLlist = new ArrayList<>();
        setSQLlist.clear();

        List<List<byte[]>> nowList = new ArrayList<>();
        nowList.clear();

        nowList = NewModel.saveList;

        for (int i = 0; i < nowList.size(); i++) {
            List<String> converbytelist = new ArrayList<>();
            converbytelist.clear();
            for (int j = 0; j < nowList.get(i).size(); j++) {
                byte[] getbyte = nowList.get(i).get(j);
                StringBuilder hex = new StringBuilder(getbyte.length * 2);
                for (byte aData : getbyte) {
                    hex.append(String.format("%02X", aData));
                }
                String gethex = hex.toString();
                converbytelist.add(gethex);
            }
            setSQLlist.add(converbytelist);
        }
        Log.e(TAG, "setSQLlist = " + setSQLlist);
        SQLjson = new JSONArray(setSQLlist);
        Log.e(TAG, "SQLjson = " + SQLjson);

        return SQLjson;
    }

    public JSONArray getnumList() {
        List<String> checkList = new ArrayList<>();
        checkList.clear();
        if (NewModel.sub1.size() != 0) {
            checkList.add("1");
        }
        if (NewModel.sub2.size() != 0) {
            checkList.add("2");
        }
        if (NewModel.sub3.size() != 0) {
            checkList.add("3");
        }
        if (NewModel.sub4.size() != 0) {
            checkList.add("4");
        }
        if (NewModel.sub5.size() != 0) {
            checkList.add("5");
        }
        if (NewModel.sub6.size() != 0) {
            checkList.add("6");
        }
        if (NewModel.sub7.size() != 0) {
            checkList.add("7");
        }

        Recordjson = new JSONArray(checkList);
        Log.e(TAG, "Reordjson = " + Recordjson);

        return Recordjson;
    }

    public void getloadlist(String savelist) {
        try {
            List<String> getjson = new ArrayList<>();
            getjson.clear();
            List<List<String>> getlist = new ArrayList<>();
            getlist.clear();
            JSONArray convertlist = new JSONArray(savelist);
            Log.e(TAG, "convertlist = " + convertlist.length());
            for (int i = 0; i < convertlist.length(); i++) {
                JSONArray newjson = (JSONArray) convertlist.get(i);
                for (int j = 0; j < newjson.length(); j++) {
                    getjson.add(newjson.get(j).toString());
                }
                getlist.add(getjson);
            }
            Log.e(TAG, "getlist = " + getlist);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void getloadnumlist(String numlist) {
        try {
            List<String> getjson = new ArrayList<>();
            getjson.clear();
            JSONArray convertlist = new JSONArray(numlist);
            for (int i = 0; i < convertlist.length(); i++) {
                getjson.add(convertlist.get(i).toString());
            }
            Log.e(TAG, "getjson = " + getjson);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
