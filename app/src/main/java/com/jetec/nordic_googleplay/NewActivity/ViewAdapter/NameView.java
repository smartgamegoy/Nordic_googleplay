package com.jetec.nordic_googleplay.NewActivity.ViewAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.jetec.nordic_googleplay.R;

import java.util.List;

public class NameView {

    public NameView(){
        super();
    }

    public View setView(Context context, List<String> list){
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        int count = list.size();
        @SuppressLint("InflateParams")
        View view = layoutInflater.inflate(R.layout.tablayoutview, null);

        Button b1 = view.findViewById(R.id.button1);
        Button b2 = view.findViewById(R.id.button2);
        Button b3 = view.findViewById(R.id.button3);
        Button b4 = view.findViewById(R.id.button4);
        Button b5 = view.findViewById(R.id.button5);
        Button b6 = view.findViewById(R.id.button6);
        Button b7 = view.findViewById(R.id.button7);
        Button b8 = view.findViewById(R.id.button8);
        Button b9 = view.findViewById(R.id.button9);
        Button b10 = view.findViewById(R.id.button10);

        b1.setVisibility(View.GONE); //View.VISIBLE
        b2.setVisibility(View.GONE);
        b3.setVisibility(View.GONE);
        b4.setVisibility(View.GONE);
        b5.setVisibility(View.GONE);
        b6.setVisibility(View.GONE);
        b7.setVisibility(View.GONE);
        b8.setVisibility(View.GONE);
        b9.setVisibility(View.GONE);
        b10.setVisibility(View.GONE);


        return view;
    }
}
