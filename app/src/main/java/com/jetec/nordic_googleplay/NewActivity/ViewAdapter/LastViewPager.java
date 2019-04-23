package com.jetec.nordic_googleplay.NewActivity.ViewAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.jetec.nordic_googleplay.NewActivity.New_Dialog.ResetButton;
import com.jetec.nordic_googleplay.NewActivity.Parase;
import com.jetec.nordic_googleplay.NewModel;
import com.jetec.nordic_googleplay.R;
import com.jetec.nordic_googleplay.Value;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LastViewPager {

    private String TAG = "LastViewPager";
    private Parase parase = new Parase();

    public LastViewPager(){
        super();
    }

    @SuppressLint("SetTextI18n")
    public View setView(Context context, Vibrator vibrator, int locate){
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        @SuppressLint("InflateParams")
        View view = layoutInflater.inflate(R.layout.tablayoutview, null);

        Button b1 = view.findViewById(R.id.button1);
        Button b2 = view.findViewById(R.id.button2);
        Button b3 = view.findViewById(R.id.button3);
        Button b4 = view.findViewById(R.id.button4);
        Button b5 = view.findViewById(R.id.button5);
        Button b6 = view.findViewById(R.id.button6);
        Button b7 = view.findViewById(R.id.button7);
        Button b8 = view.findViewById(R.id.button8);
        Button b9 = view.findViewById(R.id.button9);
        Button b10 = view.findViewById(R.id.button10);

        b1.setVisibility(View.GONE); //View.VISIBLE
        b2.setVisibility(View.GONE);
        b3.setVisibility(View.GONE);
        b4.setVisibility(View.GONE);
        b5.setVisibility(View.GONE);
        b6.setVisibility(View.GONE);
        b7.setVisibility(View.GONE);
        b8.setVisibility(View.GONE);
        b9.setVisibility(View.GONE);
        b10.setVisibility(View.GONE);

        List<byte[]> list = new ArrayList<>();
        list.clear();
        list = getlist(locate);
        int count = list.size();

        for(int i = 0; i < count; i++){
            if(i == 0){
                b1.setVisibility(View.VISIBLE);
                byte[] arr = list.get(i);
                byte[] value = Arrays.copyOfRange(arr, 3, arr.length);
                int chose = parase.byteArrayToInt(value);
                String buttontext = getText(chose, context);
                String title = setButtontext(context, get_String(list.get(i)[1], list.get(i)[0]));
                b1.setText(title + "\n" + buttontext);
                int finalI = i;
                b1.setOnClickListener(v -> {
                    vibrator.vibrate(100);
                    ResetButton resetButton = new ResetButton();
                    resetButton.set_Dialog(context, b1, vibrator, title, finalI, locate);
                });
            }
            else if(i == 1){
                b2.setVisibility(View.VISIBLE);
                byte[] arr = list.get(i);
                byte[] value = Arrays.copyOfRange(arr, 3, arr.length);
                int chose = parase.byteArrayToInt(value);
                String buttontext = getText(chose, context);
                String title = setButtontext(context, get_String(list.get(i)[1], list.get(i)[0]));
                b2.setText(title + "\n" + buttontext);
                int finalI = i;
                b2.setOnClickListener(v -> {
                    vibrator.vibrate(100);
                    ResetButton resetButton = new ResetButton();
                    resetButton.set_Dialog(context, b2, vibrator, title, finalI, locate);
                });
            }
            else if(i == 2){
                b3.setVisibility(View.VISIBLE);
                byte[] arr = list.get(i);
                byte[] value = Arrays.copyOfRange(arr, 3, arr.length);
                int chose = parase.byteArrayToInt(value);
                String buttontext = getText(chose, context);
                String title = setButtontext(context, get_String(list.get(i)[1], list.get(i)[0]));
                b3.setText(title + "\n" + buttontext);
                int finalI = i;
                b3.setOnClickListener(v -> {
                    vibrator.vibrate(100);
                    ResetButton resetButton = new ResetButton();
                    resetButton.set_Dialog(context, b3, vibrator, title, finalI, locate);
                });
            }
        }

        return view;
    }

    private String getText(int chose, Context context){
        String str = "";
        String model = Value.deviceModel;
        String[] arr = model.split("-");
        String name = arr[2];
        List<Character> nameList = new ArrayList<>();
        for (int i = 0; i < name.length(); i++) {
            nameList.add(name.charAt(i));
        }
        Log.e(TAG, "nameList = " + nameList);
        Log.e(TAG, "chose = " + chose);
        if(chose == 0){
            str = context.getString(R.string.chose);
        }
        else {
            if(nameList.get((chose - 1)).toString().matches("T")){
                str = context.getString(R.string.T);
            }else if(nameList.get((chose - 1)).toString().matches("H")){
                str = context.getString(R.string.H);
            }else if(nameList.get((chose - 1)).toString().matches("C")){
                str = context.getString(R.string.C);
            }else if(nameList.get((chose - 1)).toString().matches("D")){
                str = context.getString(R.string.C);
            }else if(nameList.get((chose - 1)).toString().matches("E")){
                str = context.getString(R.string.C);
            }else if(nameList.get((chose - 1)).toString().matches("M")){
                str = context.getString(R.string.pm);
            }else if(nameList.get((chose - 1)).toString().matches("I")){
                List<Integer> ilist = new ArrayList<>();
                ilist.clear();
                for(int i  = 0; i < nameList.size(); i++){
                    if(nameList.get(i).toString().matches("I")){
                        ilist.add((i + 1));
                    }
                }
                for(int i = 0; i < ilist.size(); i++){
                    if((ilist.get(i) + 1) == chose){
                        str = context.getString(R.string.table_i) + i;
                    }
                }
            }
        }
        return str;
    }

    private String setButtontext(Context context, String gets) {
        String str = "";

        if (gets.startsWith("OT1")) {
            str = context.getString(R.string.output) + 1;
        } else if (gets.startsWith("OT2")) {
            str = context.getString(R.string.output) + 2;
        } else if (gets.startsWith("OT3")) {
            str = context.getString(R.string.output) + 3;
        }

        return str;
    }

    private String get_String(byte a, byte b) {
        String str = "";

        if (a == 0x01) {
            str = "PV" + b;
        } else if (a == 0x02) {
            str = "EH" + b;
        } else if (a == 0x03) {
            str = "EL" + b;
        } else if (a == 0x04) {
            str = "IH" + b;
        } else if (a == 0x05) {
            str = "IL" + b;
        } else if (a == 0x06) {
            str = "CR" + b;
        } else if (a == 0x07) {
            str = "ADR";
        } else if (a == 0x08) {
            str = "Register" + b;
        } else if (a == 0x09) {
            str = "length" + b;
        } else if (a == 0x0A) {
            str = "RL1";
        } else if (a == 0x0B) {
            str = "RL2";
        } else if (a == 0x0C) {
            str = "RL3";
        } else if (a == 0x0D) { //4-20mA output
            str = "OT1";
        } else if (a == 0x0E) {
            str = "OT2";
        } else if (a == 0x0F) {
            str = "OT3";
        }
        return str;
    }

    private List<byte[]> getlist(int locate){
        List<byte[]> list = new ArrayList<>();
        list.clear();

        Log.e(TAG, "locate = " + locate);
        list = NewModel.viewList.get(locate);

        return list;
    }

}
