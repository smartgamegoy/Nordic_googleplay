package com.jetec.nordic_googleplay;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import com.jetec.nordic_googleplay.NewActivity.Parase;
import com.jetec.nordic_googleplay.NewActivity.SendByte.SendString;
import com.jetec.nordic_googleplay.Service.BluetoothLeService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NewModel {

    private Parase parase = new Parase();
    private String TAG = "NewModel";
    private SendString sendString = new SendString();
    public static BluetoothLeService mBluetoothLeService;
    public static boolean checkmodel = false, checklost = false;
    public static List<byte[]> sub1, sub2, sub3, sub4, sub5, sub6, sub7;
    public static List<byte[]> list08, list09;
    public static List<List<byte[]>> viewList, saveList;
    public static List<String> spinList;
    public static Menu menu;

    public NewModel() {
        super();
    }

    public void setString(Context context) {
        spinList = new ArrayList<>();
        String model = Value.deviceModel;
        String[] namearr = model.split("-");
        String name = namearr[2];
        List<Character> nameList = new ArrayList<>();
        nameList.clear();
        for (int i = 0; i < name.length(); i++) {
            nameList.add(name.charAt(i));
        }
        for (int i = 0; i < sub7.size(); i++) {
            byte[] arr = sub7.get(i);
            byte[] value = Arrays.copyOfRange(arr, 3, arr.length);
            int chose = parase.byteArrayToInt(value);
            String text = getText(nameList, chose, context);
            Log.e(TAG, "text = " + text);
            spinList.add(text);
        }
        saveList();
    }

    private String getText(List<Character> nameList, int chose, Context context) {
        String str = "";
        if (chose == 0) {
            str = context.getString(R.string.chose);
        } else {
            if (nameList.get((chose - 1)).toString().matches("T")) {
                str = context.getString(R.string.T);
            } else if (nameList.get((chose - 1)).toString().matches("H")) {
                str = context.getString(R.string.H);
            } else if (nameList.get((chose - 1)).toString().matches("C")) {
                str = context.getString(R.string.C);
            } else if (nameList.get((chose - 1)).toString().matches("D")) {
                str = context.getString(R.string.C);
            } else if (nameList.get((chose - 1)).toString().matches("E")) {
                str = context.getString(R.string.C);
            } else if (nameList.get((chose - 1)).toString().matches("M")) {
                str = context.getString(R.string.pm);
            } else if (nameList.get((chose - 1)).toString().matches("I")) {
                List<Integer> ilist = new ArrayList<>();
                ilist.clear();
                for (int i = 0; i < nameList.size(); i++) {
                    if (nameList.get(i).toString().matches("I")) {
                        ilist.add((i + 1));
                    }
                }
                for (int i = 0; i < ilist.size(); i++) {
                    if ((ilist.get(i) + 1) == chose) {
                        str = context.getString(R.string.table_i) + i;
                    }
                }
            }
        }
        return str;
    }

    private void saveList() {
        List<List<byte[]>> list = new ArrayList<>();
        saveList = new ArrayList<>();
        list.clear();
        saveList.clear();
        list = viewList;

        for(int i = 0; i < list.size(); i++){
            List<byte[]> newlist = new ArrayList<>();
            newlist.clear();
            newlist.addAll(list.get(i));
            saveList.add(newlist);
        }
    }

    public void checkList(Context context) {
        boolean checkfirst = false;
        int count = 1;
        for (int i = 0; i < viewList.size(); i++) {
            if(viewList.get(i).equals(saveList.get(i))){
                Log.e(TAG, "viewList.get(" + i + ") = the same");
            }
            else {
                if(!checkfirst){
                    sendString.sendList(viewList.get(i), context);
                    checkfirst = true;
                }
                else {
                    Handler mHandler = new Handler();
                    int finalI = i;
                    mHandler.postDelayed(() -> sendString.sendList(viewList.get(finalI), context), 3000 * count);
                    count = count + 1;
                }
            }
        }
        saveList();
    }
}
