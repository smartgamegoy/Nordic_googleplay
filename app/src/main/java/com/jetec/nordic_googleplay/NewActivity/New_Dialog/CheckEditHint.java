package com.jetec.nordic_googleplay.NewActivity.New_Dialog;

import android.text.InputType;
import android.widget.EditText;

import com.jetec.nordic_googleplay.NewActivity.EditListener.*;

public class CheckEditHint {

    public CheckEditHint(){
        super();
    }

    public void setHint(EditText editText, byte[] ch, String s, int dp){
        if(get_String(ch[1],ch[0]).startsWith("PV")){
            if(s.matches("T")){
                editText.setHint(" -5 ~ 5");
                editText.setInputType(InputType.TYPE_NUMBER_FLAG_SIGNED |
                        InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                editText.addTextChangedListener(new EditChangeListener_T(editText, get_String(ch[1],ch[0])));
            }
            else if(s.matches("H")){
                editText.setHint(" -10 ~ 10");
                editText.setInputType(InputType.TYPE_NUMBER_FLAG_SIGNED |
                        InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                editText.addTextChangedListener(new EditChangeListener_H(editText, get_String(ch[1],ch[0])));
            }
            else if(s.matches("C")){
                editText.setHint(" -500 ~ 500");
                editText.setInputType(InputType.TYPE_NUMBER_FLAG_SIGNED |
                        InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                editText.addTextChangedListener(new EditChangeListener_C(editText, get_String(ch[1],ch[0])));
            }
            else if(s.matches("D")){
                editText.setHint(" -500 ~ 500");
                editText.setInputType(InputType.TYPE_NUMBER_FLAG_SIGNED |
                        InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                editText.addTextChangedListener(new EditChangeListener_D(editText, get_String(ch[1],ch[0])));
            }
            else if(s.matches("E")){
                editText.setHint(" -500 ~ 500");
                editText.setInputType(InputType.TYPE_NUMBER_FLAG_SIGNED |
                        InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                editText.addTextChangedListener(new EditChangeListener_E(editText, get_String(ch[1],ch[0])));
            }
            else if(s.matches("M")){

            }
            else if(s.matches("I")){
                if(dp == 0)
                    editText.setHint(" 999 ~ -999");
                else
                    editText.setHint(" 99.9 ~ -99.9");
                editText.setInputType(InputType.TYPE_NUMBER_FLAG_SIGNED |
                        InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                editText.addTextChangedListener(new EditChangeListener_I(editText, get_String(ch[1],ch[0]), dp));
            }
        }
        else if(get_String(ch[1],ch[0]).startsWith("EH") || get_String(ch[1],ch[0]).startsWith("EL")
                || get_String(ch[1],ch[0]).startsWith("CR") || get_String(ch[1],ch[0]).startsWith("IH") ||
                get_String(ch[1],ch[0]).startsWith("IL")){
            if(s.matches("T")){
                editText.setHint(" -10 ~ 65");
                editText.setInputType(InputType.TYPE_NUMBER_FLAG_SIGNED |
                        InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                editText.addTextChangedListener(new EditChangeListener_T(editText, get_String(ch[1],ch[0])));
            }
            else if(s.matches("H")){
                editText.setHint(" 0 ~ 100");
                editText.setInputType(InputType.TYPE_NUMBER_FLAG_SIGNED |
                        InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                editText.addTextChangedListener(new EditChangeListener_H(editText, get_String(ch[1],ch[0])));
            }
            else if(s.matches("C")){
                editText.setHint(" 0 ~ 2000");
                editText.setInputType(InputType.TYPE_NUMBER_FLAG_SIGNED |
                        InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                editText.addTextChangedListener(new EditChangeListener_C(editText, get_String(ch[1],ch[0])));
            }
            else if(s.matches("D")){

            }
            else if(s.matches("E")){

            }
            else if(s.matches("M")){

            }
            else if(s.matches("I")){

            }
        }
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
