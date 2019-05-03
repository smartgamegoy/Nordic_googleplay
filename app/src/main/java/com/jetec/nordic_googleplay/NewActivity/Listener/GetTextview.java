package com.jetec.nordic_googleplay.NewActivity.Listener;

public class GetTextview {

    private TextListener textListener;

    public void setListener(TextListener mtextListener) {
        textListener = mtextListener;
    }

    public void readytointent(int size, int count) {
        if (textListener != null) {
            textListener.updateText(size, count);
        }
    }
}
