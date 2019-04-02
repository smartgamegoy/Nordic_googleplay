package com.jetec.nordic_googleplay.NewActivity;

import android.support.v4.view.PagerAdapter;
import android.view.View;

public class ViewPager extends PagerAdapter {

    @Override
    public int getCount() {
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return o == view;
    }
}
