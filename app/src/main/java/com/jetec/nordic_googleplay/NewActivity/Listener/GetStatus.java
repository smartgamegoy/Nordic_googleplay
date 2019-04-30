package com.jetec.nordic_googleplay.NewActivity.Listener;

public class GetStatus {

    private LoadListener loadListener;

    public void setListener(LoadListener mloadListener) {
        loadListener = mloadListener;
    }

    public void readytointent(String str1, String str2) {
        if (loadListener != null && str1 != null && str2 != null) {
            loadListener.update(str1, str2);
        }
    }
}
