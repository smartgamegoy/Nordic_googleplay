package com.jetec.nordic_googleplay;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Button;

public class Firstpage_buttonstyle {

    private Context context;
    private DisplayMetrics dm;

    public Firstpage_buttonstyle(Context context){
        Screen screen = new Screen(context);
        dm = screen.size();
        this.context = context;
    }

    public void buttonstyle(Button btn){
        int strokeWidth = 2;    //圓邊框寬度
        int roundRadius;    //圓半徑
        int strokeColor = ContextCompat.getColor(context, R.color.colormenu_btn);   //邊框顏色
        int fillColor = ContextCompat.getColor(context, R.color.colorBackground);   //內部填充顏色
        if (dm.widthPixels > dm.heightPixels) {
            roundRadius = dm.heightPixels / 2;
        }
        else {
            roundRadius = dm.widthPixels / 2;
        }
        GradientDrawable gd = new GradientDrawable();   //創建drawable
        gd.setColor(fillColor);
        Log.e("buttonstyle","roundRadius = " + roundRadius);
        gd.setCornerRadius((float) roundRadius);
        gd.setStroke(strokeWidth, strokeColor);
        gd.setGradientType(GradientDrawable.LINEAR_GRADIENT);
        gd.setGradientCenter(0.5f,0.5f);
        //noinspection deprecation,ConstantConditions
        btn.setWidth(roundRadius);
        btn.setHeight(roundRadius);
        btn.setBackground(gd);
    }
}
