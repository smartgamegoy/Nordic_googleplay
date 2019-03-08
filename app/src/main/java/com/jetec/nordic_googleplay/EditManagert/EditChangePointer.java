package com.jetec.nordic_googleplay.EditManagert;

import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.widget.EditText;

public class EditChangePointer implements TextWatcher {

    private EditText editText;
    private boolean last = false;

    public EditChangePointer(EditText editText) {
        this.editText = editText;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        int len = 15;
        byte[] bytes = String.valueOf(editable).getBytes();
        if(last){
            last = false;
            int selEndIndex = editText.getText().length();
            Selection.setSelection(editable, selEndIndex);
        }
        if (bytes.length > len) {
            last = true;
            byte[] newBytes = new byte[len];
            System.arraycopy(bytes, 0, newBytes, 0, len);
            String newStr = new String(newBytes);
            editText.setText(newStr);
        }
        else {
            int index = editText.getSelectionStart();
            Selection.setSelection(editable, index);
        }
    }
}
