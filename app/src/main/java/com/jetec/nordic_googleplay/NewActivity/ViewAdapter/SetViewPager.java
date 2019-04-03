package com.jetec.nordic_googleplay.NewActivity.ViewAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.jetec.nordic_googleplay.R;

import java.util.List;

public class SetViewPager {

    public SetViewPager(){
        super();
    }

    public View setView(Context context, String s, List<byte[]> list){
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        int count = list.size();
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

        for(int i = 0; i < count; i++) {
            if(i == 0){
                b1.setVisibility(View.VISIBLE);
                b1.setText(setButtontext(context, s, get_String(list.get(i)[1], list.get(i)[0])));
            }
            else if(i == 1){
                b2.setVisibility(View.VISIBLE);
                b2.setText(setButtontext(context, s, get_String(list.get(i)[1], list.get(i)[0])));
            }
            else if(i == 2){
                b3.setVisibility(View.VISIBLE);
                b3.setText(setButtontext(context, s, get_String(list.get(i)[1], list.get(i)[0])));
            }
            else if(i == 3){
                b4.setVisibility(View.VISIBLE);
                b4.setText(setButtontext(context, s, get_String(list.get(i)[1], list.get(i)[0])));
            }
            else if(i == 4){
                b5.setVisibility(View.VISIBLE);
                b5.setText(setButtontext(context, s, get_String(list.get(i)[1], list.get(i)[0])));
            }
            else if(i == 5){
                b6.setVisibility(View.VISIBLE);
                b6.setText(setButtontext(context, s, get_String(list.get(i)[1], list.get(i)[0])));
            }
            else if(i == 6){
                b7.setVisibility(View.VISIBLE);
                b7.setText(setButtontext(context, s, get_String(list.get(i)[1], list.get(i)[0])));
            }
            else if(i == 7){
                b8.setVisibility(View.VISIBLE);
                b8.setText(setButtontext(context, s, get_String(list.get(i)[1], list.get(i)[0])));
            }
            else if(i == 8){
                b9.setVisibility(View.VISIBLE);
                b9.setText(setButtontext(context, s, get_String(list.get(i)[1], list.get(i)[0])));
            }
            else if(i == 9){
                b10.setVisibility(View.VISIBLE);
                b10.setText(setButtontext(context, s, get_String(list.get(i)[1], list.get(i)[0])));
            }
        }
        return view;
    }

    private String setButtontext(Context context, String s, String gets){
        String str = "";
        if(s.matches("T")){
            if(gets.startsWith("PV")){
                str = context.getString(R.string.T) + context.getString(R.string.PV1);
            }
            else if(gets.startsWith("EH")){
                str = context.getString(R.string.T) + context.getString(R.string.EH1);
            }
            else if(gets.startsWith("EL")){
                str = context.getString(R.string.T) + context.getString(R.string.EL1);
            }
            else if(gets.startsWith("CR")){
                str = context.getString(R.string.T) + context.getString(R.string.CR1);
            }
            else if(gets.startsWith("RL1")){
                str = context.getString(R.string.RL1);
            }
            else if(gets.startsWith("RL2")){
                str = context.getString(R.string.RL2);
            }
            else if(gets.startsWith("RL3")){
                str = context.getString(R.string.RL3);
            }
        }
        else if(s.matches("H")){
            if(gets.startsWith("PV")){
                str = context.getString(R.string.H) + context.getString(R.string.PV1);
            }
            else if(gets.startsWith("EH")){
                str = context.getString(R.string.H) + context.getString(R.string.EH1);
            }
            else if(gets.startsWith("EL")){
                str = context.getString(R.string.H) + context.getString(R.string.EL1);
            }
            else if(gets.startsWith("CR")){
                str = context.getString(R.string.H) + context.getString(R.string.CR1);
            }
            else if(gets.startsWith("RL1")){
                str = context.getString(R.string.RL1);
            }
            else if(gets.startsWith("RL2")){
                str = context.getString(R.string.RL2);
            }
            else if(gets.startsWith("RL3")){
                str = context.getString(R.string.RL3);
            }
        }
        else if(s.matches("C")){
            if(gets.startsWith("PV")){
                str = context.getString(R.string.Co2) + context.getString(R.string.PV1);
            }
            else if(gets.startsWith("EH")){
                str = context.getString(R.string.Co2) + context.getString(R.string.EH1);
            }
            else if(gets.startsWith("EL")){
                str = context.getString(R.string.Co2) + context.getString(R.string.EL1);
            }
            else if(gets.startsWith("CR")){
                str = context.getString(R.string.Co2) + context.getString(R.string.CR1);
            }
            else if(gets.startsWith("RL1")){
                str = context.getString(R.string.RL1);
            }
            else if(gets.startsWith("RL2")){
                str = context.getString(R.string.RL2);
            }
            else if(gets.startsWith("RL3")){
                str = context.getString(R.string.RL3);
            }
        }
        else if(s.matches("D")){
            if(gets.startsWith("PV")){
                str = context.getString(R.string.Co2) + context.getString(R.string.PV1);
            }
            else if(gets.startsWith("EH")){
                str = context.getString(R.string.Co2) + context.getString(R.string.EH1);
            }
            else if(gets.startsWith("EL")){
                str = context.getString(R.string.Co2) + context.getString(R.string.EL1);
            }
            else if(gets.startsWith("CR")){
                str = context.getString(R.string.Co2) + context.getString(R.string.CR1);
            }
            else if(gets.startsWith("RL1")){
                str = context.getString(R.string.RL1);
            }
            else if(gets.startsWith("RL2")){
                str = context.getString(R.string.RL2);
            }
            else if(gets.startsWith("RL3")){
                str = context.getString(R.string.RL3);
            }
        }
        else if(s.matches("E")){
            if(gets.startsWith("PV")){
                str = context.getString(R.string.Co2) + context.getString(R.string.PV1);
            }
            else if(gets.startsWith("EH")){
                str = context.getString(R.string.Co2) + context.getString(R.string.EH1);
            }
            else if(gets.startsWith("EL")){
                str = context.getString(R.string.Co2) + context.getString(R.string.EL1);
            }
            else if(gets.startsWith("CR")){
                str = context.getString(R.string.Co2) + context.getString(R.string.CR1);
            }
            else if(gets.startsWith("RL1")){
                str = context.getString(R.string.RL1);
            }
            else if(gets.startsWith("RL2")){
                str = context.getString(R.string.RL2);
            }
            else if(gets.startsWith("RL3")){
                str = context.getString(R.string.RL3);
            }
        }else if(s.matches("I")){
            if(gets.startsWith("PV")){
                str = context.getString(R.string.table_i) + context.getString(R.string.PV1);
            }
            else if(gets.startsWith("EH")){
                str = context.getString(R.string.table_i) + context.getString(R.string.EH1);
            }
            else if(gets.startsWith("EL")){
                str = context.getString(R.string.table_i) + context.getString(R.string.EL1);
            }
            else if(gets.startsWith("CR")){
                str = context.getString(R.string.table_i) + context.getString(R.string.CR1);
            }
            else if(gets.startsWith("IH")){
                str = context.getString(R.string.IH);
            }
            else if(gets.startsWith("IL")){
                str = context.getString(R.string.IL);
            }
            else if(gets.startsWith("RL1")){
                str = context.getString(R.string.RL1);
            }
            else if(gets.startsWith("RL2")){
                str = context.getString(R.string.RL2);
            }
            else if(gets.startsWith("RL3")){
                str = context.getString(R.string.RL3);
            }
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
}