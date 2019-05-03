package com.jetec.nordic_googleplay.NewActivity.Listener;

public class GetCounter {

    private CountListener countListener;

    public void setListener(CountListener mcountListener) {
        countListener = mcountListener;
    }

    public void readytointent(int size, int count) {
        if (countListener != null && size == count) {
            countListener.update(size, count);
        }
    }
}
