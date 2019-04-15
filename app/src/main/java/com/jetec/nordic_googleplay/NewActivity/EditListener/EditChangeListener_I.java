package com.jetec.nordic_googleplay.NewActivity.EditListener;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

public class EditChangeListener_I implements TextWatcher {

    private String TAG = "EditChangeListener_I";
    private EditText editText;
    private String name;
    private boolean last;

    public EditChangeListener_I(EditText editText, String name) {
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

    }
}
