package com.jetec.nordic_googleplay.NewActivity.Listener;

public class GetCheck {

    private CheckListener checkListener;

    public void setListener(CheckListener mCheckListener){
        checkListener = mCheckListener;
    }

    public void readytoNext() {
        if (checkListener != null) {
            checkListener.sorting();
        }
    }

    public void keeptoNext(){
        if (checkListener != null) {
            checkListener.keepWork();
        }
    }
}
