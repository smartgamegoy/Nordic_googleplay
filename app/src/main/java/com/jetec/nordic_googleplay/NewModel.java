package com.jetec.nordic_googleplay;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class NewModel {

    public static boolean checkmodel = false;
    public static boolean checkbyte = false;
    public static List<byte[]> sub1, sub2, sub3, sub4, sub5, sub6, sub7;
    public static List<List<byte[]>> viewList;
    public static List<String> spinList;

    public NewModel(){
        super();
    }

    public void setString(Context context){
        spinList = new ArrayList<>();
        for(int i = 0; i < 3; i ++)
            spinList.add(context.getString(R.string.chose));
    }
}
