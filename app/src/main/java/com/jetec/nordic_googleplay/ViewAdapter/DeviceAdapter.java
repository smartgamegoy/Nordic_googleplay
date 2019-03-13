package com.jetec.nordic_googleplay.ViewAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.jetec.nordic_googleplay.R;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeviceAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private Map<Integer, List<String>> newList;

    @SuppressLint("UseSparseArrays")
    public DeviceAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        newList = new HashMap<>();
    }

    @Override
    public int getCount() {
        return newList.size();
    }

    @Override
    public Object getItem(int position) {
        return newList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewGroup view;

        if (convertView != null) {
            view = (ViewGroup) convertView;
        } else {
            view = (ViewGroup) inflater.inflate(R.layout.search_device, null);
        }

        List<String> device = new ArrayList<>();
        device.clear();
        device = newList.get(position);

        final TextView device_name = view.findViewById(R.id.search_device);
        final TextView device_address = view.findViewById(R.id.device_address);
        device_name.setVisibility(View.VISIBLE);
        device_address.setVisibility(View.VISIBLE);
        assert device != null;
        if (device.size() > 0) {
            if(device.get(0) != null)
                device_name.setText(device.get(0));
            if(device.get(1) != null)
                device_address.setText(device.get(1));
        }
        return view;
    }

    public void getList(Map<Integer, List<String>> newList) {
        this.newList = newList;
    }
}