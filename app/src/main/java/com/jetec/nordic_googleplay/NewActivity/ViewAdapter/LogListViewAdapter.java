package com.jetec.nordic_googleplay.NewActivity.ViewAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.jetec.nordic_googleplay.NewActivity.GetUnit;
import com.jetec.nordic_googleplay.R;
import java.util.ArrayList;
import java.util.List;

public class LogListViewAdapter extends BaseAdapter {

    private String TAG = "LogListViewAdapter";
    private List<String> nameList, timeList;
    private List<List<String>> saveList;
    private LayoutInflater inflater;
    private String getdate, getsize;
    private Context context;
    private GetUnit getUnit = new GetUnit();

    public LogListViewAdapter() {
        nameList = new ArrayList<>();
        timeList = new ArrayList<>();
        saveList = new ArrayList<>();
        nameList.clear();
        timeList.clear();
        saveList.clear();
    }

    public void setList(Context context, List<String> nameList, List<String> timeList, List<List<String>> saveList) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        getdate = context.getString(R.string.datetime);
        getsize = context.getString(R.string.size);
        this.nameList = nameList;
        this.timeList = timeList;
        this.saveList = saveList;
    }

    @Override
    public int getCount() {
        return timeList.size();
    }

    @Override
    public Object getItem(int position) {
        return timeList.get(position);
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
            view = (ViewGroup) inflater.inflate(R.layout.showdata, null);
        }

        TextView time = view.findViewById(R.id.textView1);
        TextView num = view.findViewById(R.id.textView2);
        TextView textView3 = view.findViewById(R.id.textView3);
        TextView textView4 = view.findViewById(R.id.textView4);
        TextView textView5 = view.findViewById(R.id.textView5);
        TextView textView6 = view.findViewById(R.id.textView6);
        TextView textView7 = view.findViewById(R.id.textView7);
        TextView textView8 = view.findViewById(R.id.textView8);

        time.setVisibility(View.GONE);
        num.setVisibility(View.GONE);
        textView3.setVisibility(View.GONE);
        textView4.setVisibility(View.GONE);
        textView5.setVisibility(View.GONE);
        textView6.setVisibility(View.GONE);
        textView7.setVisibility(View.GONE);
        textView8.setVisibility(View.GONE);

        List<TextView> textList = new ArrayList<>();
        textList.clear();
        textList.add(time);
        textList.add(num);
        textList.add(textView3);
        textList.add(textView4);
        textList.add(textView5);
        textList.add(textView6);
        textList.add(textView7);
        textList.add(textView8);

        int textnum = nameList.size() + 2;

        setTextView(textList, textnum, position);


        return view;
    }

    @SuppressLint("SetTextI18n")
    private void setTextView(List<TextView> textList, int textnum, int position) {
        for (int i = 0; i < textnum; i++) {
            TextView textView = textList.get(i);
            textView.setVisibility(View.VISIBLE);
            if (i == 0) {
                textView.setText(getdate + " : " + timeList.get(position));
            }
            if (i == 1) {
                textView.setText(getsize + " : " + String.valueOf((position + 1)));
            }
            if (i > 1) {
                int j = i - 2;
                String str = getUnit.getItemString(context, nameList.get(j), j);
                String unit = getUnit.getunit(nameList.get(j));
                textView.setText(str + " : " + saveList.get(j).get(position) + unit);
            }
        }
    }
}
