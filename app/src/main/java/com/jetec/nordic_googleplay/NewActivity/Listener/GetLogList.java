package com.jetec.nordic_googleplay.NewActivity.Listener;

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import java.util.ArrayList;
import java.util.List;

public class GetLogList {

    private String TAG = "GetLogList";
    private ListViewListener listViewListener;

    public void setListener(ListViewListener mListViewListener) {
        listViewListener = mListViewListener;
    }

    public void readytointent(String log) {
        if (listViewListener != null && log != null) {
            List<String> nameList = new ArrayList<>();
            List<String> timeList = new ArrayList<>();
            List<List<String>> saveList = new ArrayList<>();
            nameList.clear();
            timeList.clear();
            saveList.clear();

            try {
                JSONArray logjson = new JSONArray(log);
                String namejson = logjson.get(0).toString();
                JSONArray namejsonlist = new JSONArray(namejson);
                for(int i = 0; i < namejsonlist.length(); i++){
                    nameList.add(namejsonlist.get(i).toString());
                }

                String timejson = logjson.get(1).toString();
                JSONArray timejsonlist = new JSONArray(timejson);
                for(int i = 0; i < timejsonlist.length(); i++){
                    timeList.add(timejsonlist.get(i).toString());
                }

                for(int i = 2; i < logjson.length(); i++){
                    List<String> getjsonlist = new ArrayList<>();
                    getjsonlist.clear();
                    String savejson = logjson.get(i).toString();
                    JSONArray savejsonlist = new JSONArray(savejson);
                    for(int j = 0; j < savejsonlist.length(); j++){
                        getjsonlist.add(savejsonlist.get(j).toString());
                    }
                    saveList.add(getjsonlist);
                }
            } catch (JSONException e) {
                Log.e(TAG, "未知錯誤進" + e);
                e.printStackTrace();
            }

            listViewListener.convert(nameList, timeList, saveList);
        }
        else {
            Log.e(TAG, "未知錯誤" + "readytointent");
            Log.e(TAG, "listViewListener = " + listViewListener);
            Log.e(TAG, "log" + log);
        }
    }

    public void readytocsv(){
        if (listViewListener != null){
            listViewListener.makecsv();
        }
    }

    public void readytopdf(){
        if (listViewListener != null){
            listViewListener.makepdf();
        }
    }
}
