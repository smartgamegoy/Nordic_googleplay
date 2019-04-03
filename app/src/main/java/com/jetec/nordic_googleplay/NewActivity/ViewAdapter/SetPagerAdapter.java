package com.jetec.nordic_googleplay.NewActivity.ViewAdapter;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;

public class SetPagerAdapter extends PagerAdapter {

    private List<View> listview;

    public SetPagerAdapter(){
        super();
    }

    public void setView(List<View> listview){
        this.listview = listview;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        container.addView(listview.get(position), 0);
        return listview.get(position);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView(listview.get(position));
    }

    @Override
    public int getCount() {
        return listview.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View arg0, @NonNull Object arg1) {
        return arg0 == arg1;
    }
}
