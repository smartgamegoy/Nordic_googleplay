package com.jetec.nordic_googleplay.EditManagert;

import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.EditText;

public class EditChangeName implements TextWatcher {

    private EditText editText;
    private boolean last;
    private String TAG = "EditChangeName";

    public EditChangeName(EditText editText) {
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
            Log.e(TAG, "selEndIndex = " + selEndIndex);
            Selection.setSelection(editable, selEndIndex);
            //editText.setSelection(selEndIndex);
            Log.e(TAG, "重設光標");
        }
        Log.e(TAG, "bytes.length" + bytes.length);
        if (bytes.length > len) {
            last = true;
            editText.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL));
            editText.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_DEL));
        }
    }
}
