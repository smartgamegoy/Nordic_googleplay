package com.jetec.nordic_googleplay.NewActivity.EditListener;

import android.annotation.SuppressLint;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

public class SearchEditListener implements TextWatcher {

    private String TAG = "SearchEditListener";
    private EditText editText;
    private String name;
    private boolean last;

    public SearchEditListener() {
        super();
    }

    public void setValue(EditText editText, String name){
        this.editText = editText;
        this.name = name;
        Log.e(TAG, "name = " + name);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void afterTextChanged(Editable editable) {
        String num = editText.getText().toString().trim();
        Log.e(TAG, "num = " + num);
        if (name.startsWith("T")) {
            setSize(editable, num, (float) 65.0, (float) -10.0);
        } else if (name.startsWith("H")) {
            setSize(editable, num, (float) 100.0, (float) 0.0);
        } else if (name.startsWith("C")) {
            setSize(editable, num, (float) 2000.0, (float) 0.0);
        }else if (name.startsWith("D")) {
            setSize(editable, num, (float) 3000.0, (float) 0.0);
        }else if (name.startsWith("E")) {
            setSize(editable, num, (float) 5000.0, (float) 0.0);
        }else if (name.startsWith("I")) {
            setSize(editable, num, (float) 9999.0, (float) -999.0);
        }else if (name.startsWith("M")) {
            setSize(editable, num, (float) 1000.0, (float) 0.0);
        }
    }

    private void setSize(Editable editable, String num, float big, float small) {
        if (!num.equals("-") && !num.equals("")) {
            if (num.equals(".")) {
                last = true;
                editText.setText("0.");
            } else if (num.matches("-\\.") && !num.matches("-0")) {
                last = true;
                if (num.equals("-."))
                    editText.setText("-0.");
            } else {
                if (Float.valueOf(num) > big) {
                    last = true;
                    editText.setText(String.valueOf((int) big));
                } else if (Float.valueOf(num) < small) {
                    last = true;
                    editText.setText(String.valueOf((int) small));
                }
                if (last) {
                    last = false;
                    int selEndIndex = editText.getText().length();
                    Selection.setSelection(editable, selEndIndex);
                }
            }
        }
    }
}
