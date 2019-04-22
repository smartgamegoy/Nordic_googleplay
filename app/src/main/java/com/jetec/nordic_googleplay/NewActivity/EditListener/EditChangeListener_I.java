package com.jetec.nordic_googleplay.NewActivity.EditListener;

import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

public class EditChangeListener_I implements TextWatcher {

    private String TAG = "EditChangeListener_I";
    private EditText editText;
    private String name;
    private int dp;
    private boolean last;

    public EditChangeListener_I(EditText editText, String name, int dp) {
        this.editText = editText;
        this.name = name;
        this.dp = dp;
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
            if(dp == 0) {
                if (!num.equals("-") && !num.equals("")) {
                    if (num.equals(".")) {
                        last = true;
                        editText.setText("0.");
                    } else if (num.matches("-\\.") && !num.matches("-0")) {
                        last = true;
                        if (num.equals("-."))
                            editText.setText("-0.");
                    } else {
                        if (Float.valueOf(num) > 999.0) {
                            last = true;
                            editText.setText("999");
                        } else if (Float.valueOf(num) < -999.0) {
                            last = true;
                            editText.setText("-999");
                        }
                        if (last) {
                            last = false;
                            int selEndIndex = editText.getText().length();
                            Selection.setSelection(editable, selEndIndex);
                        }
                    }
                }
            }
            else {
                if (!num.equals("-") && !num.equals("")) {
                    if (num.equals(".")) {
                        last = true;
                        editText.setText("0.");
                    } else if (num.matches("-\\.") && !num.matches("-0")) {
                        last = true;
                        if (num.equals("-."))
                            editText.setText("-0.");
                    } else {
                        if (Float.valueOf(num) * 10 > 999) {
                            last = true;
                            editText.setText("99.9");
                        } else if (Float.valueOf(num) * 10 < -999) {
                            last = true;
                            editText.setText("-99.9");
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
        else if(name.startsWith("EH") || name.startsWith("EL") || name.startsWith("CR") ||
                name.startsWith("IH") || name.startsWith("IL")){
            if(dp == 0) {
                if (!num.contains("+")) {
                    if (!num.equals("-") && !num.equals("")) {
                        if (num.equals(".")) {
                            last = true;
                            editText.setText("0.");
                        } else if (num.matches("-\\.") && !num.matches("-0")) {
                            last = true;
                            if (num.equals("-."))
                                editText.setText("-0.");
                        } else {
                            if (Float.valueOf(num) > 9999.0) {
                                last = true;
                                editText.setText("9999");
                            } else if (Float.valueOf(num) < -999.0) {
                                last = true;
                                editText.setText("-999");
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
            else{
                if (!num.contains("+")) {
                    if (!num.equals("-") && !num.equals("")) {
                        if (num.equals(".")) {
                            last = true;
                            editText.setText("0.");
                        } else if (num.matches("-\\.") && !num.matches("-0")) {
                            last = true;
                            if (num.equals("-."))
                                editText.setText("-0.");
                        } else {
                            if (Float.valueOf(num) * 10 > 9999) {
                                last = true;
                                editText.setText("999.9");
                            } else if (Float.valueOf(num) * 10 < -1999) {
                                last = true;
                                editText.setText("-199.9");
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
}
