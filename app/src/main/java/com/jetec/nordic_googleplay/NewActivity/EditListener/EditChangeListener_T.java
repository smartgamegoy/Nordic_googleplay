package com.jetec.nordic_googleplay.NewActivity.EditListener;

import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

public class EditChangeListener_T implements TextWatcher {

    private String TAG = "EditChangeListener_T";
    private EditText editText;
    private String name;
    private boolean last;

    public EditChangeListener_T(EditText editText, String name) {
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

    @Override
    public void afterTextChanged(Editable editable) {
        String num = editText.getText().toString().trim();
        Log.e(TAG, "num = " + num);
        if(name.startsWith("PV")) {
            if (!num.equals("-") && !num.equals("")) {
                if (num.equals(".")) {
                    last = true;
                    editText.setText("0.");
                } else if (num.matches("-\\.") && !num.matches("-0")) {
                    last = true;
                    if (num.equals("-."))
                        editText.setText("-0.");
                } else {
                    if (Float.valueOf(num) > 5.0) {
                        last = true;
                        editText.setText("5");
                    } else if (Float.valueOf(num) < -5.0) {
                        last = true;
                        editText.setText("-5");
                    }
                    if (last) {
                        last = false;
                        int selEndIndex = editText.getText().length();
                        Selection.setSelection(editable, selEndIndex);
                    }
                }
            }
        }
        else if(name.startsWith("EH") || name.startsWith("EL") || name.startsWith("CR")){
            if(!num.contains("+")) {
                if (!num.equals("-") && !num.equals("")) {
                    if (num.equals(".")) {
                        last = true;
                        editText.setText("0.");
                    } else if (num.matches("-\\.") && !num.matches("-0")) {
                        last = true;
                        if (num.equals("-."))
                            editText.setText("-0.");
                    } else {
                        if (Float.valueOf(num) > 65.0) {
                            last = true;
                            editText.setText("65");
                        } else if (Float.valueOf(num) < -10.0) {
                            last = true;
                            editText.setText("-10");
                        } else {
                            if (last) {
                                last = false;
                                int selEndIndex = editText.getText().length();
                                Selection.setSelection(editable, selEndIndex);
                            }
                        }
                    }
                }
            }
        }
    }
}
