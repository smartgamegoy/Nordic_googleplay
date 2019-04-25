package com.jetec.nordic_googleplay.NewActivity.UserSQL;

import android.util.Log;

import com.jetec.nordic_googleplay.NewModel;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class ConvertList {

    private String TAG = "ConvertList";
    private JSONArray SQLjson;

    public ConvertList() {
        super();
    }

    public void bytetoString() {
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
        /*try {
            List<String> getjson = new ArrayList<>();
            getjson.clear();
            List<List<String>> testlist = new ArrayList<>();
            testlist.clear();
            for (int i = 0; i < SQLjson.length(); i++) {
                JSONArray newjson = (JSONArray) SQLjson.get(i);
                for(int j = 0; j < newjson.length(); j++){
                    getjson.add(newjson.get(j).toString());
                }
                testlist.add(getjson);
            }
            Log.e(TAG, "testlist = " + testlist);
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
    }
}
