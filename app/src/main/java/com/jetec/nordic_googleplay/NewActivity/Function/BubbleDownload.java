package com.jetec.nordic_googleplay.NewActivity.Function;

import android.util.Log;

import com.jetec.nordic_googleplay.NewActivity.GetString.Parase;
import com.jetec.nordic_googleplay.NewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BubbleDownload {

    private String TAG = "BubbleDownload";
    private List<byte[]> list1, list2;
    private Parase parase = new Parase();

    public BubbleDownload(){
        super();
    }

    public void sortList(){
        list1 = new ArrayList<>();
        list2 = new ArrayList<>();
        list1.clear();
        list2.clear();
        list1 = NewModel.list08;
        list2 = NewModel.list09;

        int listlenth = list2.size();
        while (listlenth > 1){
            listlenth--;
            for(int i = 0; i < list2.size(); i++){  //bubblesort
                if((i + 1) != list2.size()) {
                    if (getcount(list2.get(i)) > getcount(list2.get(i + 1))) {
                        byte[] tempbyte = list2.get(i);
                        byte[] tempbyte2 = list1.get(i);
                        list2.set(i, list2.get(i + 1));
                        list2.set((i + 1), tempbyte);
                        list1.set(i, list1.get(i + 1));
                        list1.set((i + 1), tempbyte2);
                    }
                }
            }
        }
        /*for(int i = 0; i < list2.size(); i++){
            Log.e(TAG, "count = " + getcount(list2.get(i)));
        }*/
        Log.e(TAG, "list2 = " + list2.size());
    }

    public List<byte[]> getlist1(){
        return list1;
    }

    public List<byte[]> getlist2(){
        return list2;
    }

    private int getcount(byte[] getbyte){
        byte[] data = Arrays.copyOfRange(getbyte, 1, 4);
        return parase.byteArrayToInt(data);
    }
}
