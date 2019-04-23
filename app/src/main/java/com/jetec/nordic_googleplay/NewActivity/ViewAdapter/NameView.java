package com.jetec.nordic_googleplay.NewActivity.ViewAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.jetec.nordic_googleplay.NewActivity.New_Dialog.NameDialog;
import com.jetec.nordic_googleplay.NewActivity.New_Dialog.TimeDialog;
import com.jetec.nordic_googleplay.R;
import com.jetec.nordic_googleplay.Service.BluetoothLeService;
import com.jetec.nordic_googleplay.Value;

import java.util.List;

public class NameView {

    private String devicename = Value.BName;
    private String TAG = "NameView";

    public NameView(){
        super();
    }

    @SuppressLint("SetTextI18n")
    public View setView(Context context, List<String> list, Vibrator vibrator){
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

        for(int i = 0; i < count; i++){
            String str = list.get(i);
            if(i == 0){
                b1.setVisibility(View.VISIBLE);
                b1.setAllCaps(false);
                b1.setText(get_String(context, str) + "\n" + devicename);
                setviewdialog(context, b1, str, vibrator, get_String(context, str));
            }
            else if(i == 1){
                b2.setVisibility(View.VISIBLE);
                b2.setText(get_String(context, str));
                setviewdialog(context, b2, str, vibrator, get_String(context, str));
            }
            else if(i == 2){
                b3.setVisibility(View.VISIBLE);
                b3.setText(get_String(context, str));
                setviewdialog(context, b3, str, vibrator, get_String(context, str));
            }
            else if(i == 3){
                b4.setVisibility(View.VISIBLE);
                b4.setText(get_String(context, str));
                setviewdialog(context, b4, str, vibrator, get_String(context, str));
            }
            else if(i == 4){
                b5.setVisibility(View.VISIBLE);
                b5.setText(get_String(context, str));
                setviewdialog(context, b5, str, vibrator, get_String(context, str));
            }
            else if(i == 5){
                b6.setVisibility(View.VISIBLE);
                b6.setText(get_String(context, str));
                setviewdialog(context, b6, str, vibrator, get_String(context, str));
            }
            else if(i == 6){
                b7.setVisibility(View.VISIBLE);
                b7.setText(get_String(context, str));
                setviewdialog(context, b7, str, vibrator, get_String(context, str));
            }
            else if(i == 7){
                b8.setVisibility(View.VISIBLE);
                b8.setText(get_String(context, str));
                setviewdialog(context, b8, str, vibrator, get_String(context, str));
            }
            else if(i == 8){
                b9.setVisibility(View.VISIBLE);
                b9.setText(get_String(context, str));
                setviewdialog(context, b9, str, vibrator, get_String(context, str));
            }
            else if(i == 9){
                b10.setVisibility(View.VISIBLE);
                b10.setText(get_String(context, str));
                setviewdialog(context, b10, str, vibrator, get_String(context, str));
            }
        }
        return view;
    }

    private String get_String(Context context, String str){
        String gets = "";
        if(str.matches("NAME")){
            gets = context.getString(R.string.device_name);
        }
        else if(str.matches("TIME")){
            gets = context.getString(R.string.settimes);
        }
        else if(str.matches("INTER")){
            gets = context.getString(R.string.INTER);
        }
        else if(str.matches("SPK")){
            gets = context.getString(R.string.SPK);
        }
        return gets;
    }

    private void setviewdialog(Context context, Button button, String str, Vibrator vibrator, String title){
        button.setOnClickListener(v -> {
            vibrator.vibrate(100);
            Log.e(TAG, "str = " + str);
            if(str.matches("NAME")){
                NameDialog nameDialog = new NameDialog();
                nameDialog.setDialog(context, button, title, vibrator);
            }
            else if(str.matches("TIME")){
                TimeDialog timeDialog = new TimeDialog();
                timeDialog.setDialog(context, button, title, vibrator);
            }
            else if(str.matches("INTER")){

            }
            else if(str.matches("SPK")){

            }
        });
    }
}
