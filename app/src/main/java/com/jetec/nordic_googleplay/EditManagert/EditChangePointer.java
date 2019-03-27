package com.jetec.nordic_googleplay.EditManagert;

import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.widget.EditText;
import java.util.ArrayList;

public class EditChangePointer implements TextWatcher {

    private EditText editText;
    private boolean last = false;
    private ArrayList<Character> num;
    private int lenth = 0;

    public EditChangePointer(EditText editText) {
        this.editText = editText;
        num = new ArrayList<>();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        String str = editText.getText().toString().trim();
        str = str.toUpperCase();
        str = str.replace(" ", "");
        if (last) {
            last = false;
            int selEndIndex = editText.getText().length();
            Selection.setSelection(editable, selEndIndex);
        }
        else {
            int index = editText.getSelectionStart();
            Selection.setSelection(editable, index);
        }
        if(str.length() == 2 && str.length() != lenth){
            lenth = str.length();
            last = true;
            editText.setText(str);
        }
        if (str.length() > 1 && str.length() != lenth) {
            lenth = str.length();
            num.clear();
            ArrayList<String> a = new ArrayList<>();
            a.clear();
            String getnum;
            if (str.length() % 2 == 0) {
                for (int i = 0; i < str.length(); i++) {
                    num.add(str.charAt(i));
                }
                for (int i = 1; i <= num.size(); i++) {
                    if (i > 1 && i % 2 == 0) {
                        getnum = num.get(i - 2).toString() + num.get(i - 1).toString();
                        a.add(getnum);
                    }
                }
            }
            else {
                for (int i = 0; i < str.length(); i++) {
                    num.add(str.charAt(i));
                }
                for (int i = 1; i <= num.size(); i++) {
                    if (i > 1 && i % 2 == 0 && i != num.size()) {
                        getnum = num.get(i - 2).toString() + num.get(i - 1).toString();
                        a.add(getnum);
                    }
                    if (i == num.size() && i > 1) {
                        getnum = num.get(i - 1).toString();
                        a.add(getnum);
                    }
                }
            }
            StringBuilder str2 = new StringBuilder();

            for (int i = 0; i < a.size(); i++) {
                if (i != a.size() - 1)
                    str2.append(a.get(i)).append(" ");
                else
                    str2.append(a.get(i));
            }
            last = true;
            editText.setText(str2.toString());
        }
    }
}
