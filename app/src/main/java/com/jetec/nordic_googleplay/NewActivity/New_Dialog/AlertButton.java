package com.jetec.nordic_googleplay.NewActivity.New_Dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import com.jetec.nordic_googleplay.NewActivity.GetString.ByteToHex;
import com.jetec.nordic_googleplay.NewActivity.Parase;
import com.jetec.nordic_googleplay.NewModel;
import com.jetec.nordic_googleplay.R;
import com.jetec.nordic_googleplay.Screen;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AlertButton {

    private String TAG = "AlertButton";
    private Dialog progressDialog = null;
    private Parase parase = new Parase();
    private ByteToHex byteToHex = new ByteToHex();
    private int setSwitch;
    private byte[] newArray = new byte[7];
    private String sp_str;

    public AlertButton() {
        super();
    }

    public void set_Dialog(Context context, Button button, Vibrator vibrator,
                           String title, int getlist_i, int i) {
        byte[] getbyte = getbyte(getlist_i, i);
        //Log.e(TAG, "spinnerList = " + spinnerList);
        progressDialog = showDialog(context, button, vibrator, getbyte, title, getlist_i, i);
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
    }

    @SuppressLint("SetTextI18n")
    private Dialog showDialog(Context context, Button button, Vibrator vibrator, byte[] getbyte,
                              String str, int getlist_i, int i){
        Screen screen = new Screen(context);
        DisplayMetrics dm = screen.size();
        Dialog progressDialog = new Dialog(context);
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        LayoutInflater inflater = LayoutInflater.from(context);
        @SuppressLint("InflateParams") View v = inflater.inflate(R.layout.chose_switch, null);
        TextView title = v.findViewById(R.id.textView1);
        Switch sw = v.findViewById(R.id.switch1);
        Button by = v.findViewById(R.id.button2);
        Button bn = v.findViewById(R.id.button1);
        title.setText(str);
        by.setText(context.getString(R.string.butoon_yes));
        bn.setText(context.getString(R.string.butoon_no));

        byte[] data = Arrays.copyOfRange(getbyte, 3, getbyte.length);
        int value = parase.byteArrayToInt(data);
        setSwitch = value;
        if (value != 0)
            sw.setChecked(true);
        else
            sw.setChecked(false);

        sw.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // TODO Auto-generated method stub
            vibrator.vibrate(100);
            if (isChecked) {
                setSwitch = 1;
                Log.e(TAG, "ON");
            } else {
                setSwitch = 0;
                Log.e(TAG, "OFF");
            }
        });

        by.setOnClickListener(v12 -> {
            Log.e(TAG, "setSwitch = " + setSwitch);
            vibrator.vibrate(100);
            if(setSwitch == 0) {
                button.setText(str + "\n" + context.getString(R.string.off));
            }
            else {
                button.setText(str + "\n" + context.getString(R.string.on));
            }
            setNewbyte(getlist_i, i, setSwitch);
            //setNewbyte(getlist_i, i, spinnerList);
            progressDialog.dismiss();
        });

        bn.setOnClickListener(v12 -> {
            vibrator.vibrate(100);
            progressDialog.dismiss();
        });

        if (dm.heightPixels > dm.widthPixels) { //需修改
            progressDialog.setContentView(v, new LinearLayout.LayoutParams(2 * dm.widthPixels / 3,
                    dm.heightPixels / 3));
        } else {
            progressDialog.setContentView(v, new LinearLayout.LayoutParams(dm.widthPixels / 3,
                    dm.heightPixels / 2));
        }

        return progressDialog;
    }

    private void setNewbyte(int getlist_i, int finalI, int chose){
        List<byte[]> list = new ArrayList<>();
        list.clear();
        list = NewModel.viewList.get(getlist_i);
        byte[] original = list.get(finalI);
        byte[] calcu = Arrays.copyOfRange(original, 0, 3);
        byte[] value = parase.intToByteArray(chose);
        System.arraycopy(calcu, 0, newArray, 0, calcu.length);
        System.arraycopy(value, 0, newArray, 3, value.length);
        list.set(finalI, newArray);
        NewModel.viewList.set(getlist_i, list);

        for(int i = 0; i < list.size(); i++){
            byte[] data = Arrays.copyOfRange(NewModel.viewList.get(getlist_i).get(i), 0,
                    NewModel.viewList.get(getlist_i).get(i).length);
            String[] arr = byteToHex.hexstring(data);
            StringBuilder gstr = new StringBuilder();
            for (String arrs : arr) {
                gstr.append(arrs).append(" ");
            }
            Log.e(TAG, "sb1 = " + gstr);
        }
    }

    private byte[] getbyte(int getlist_i, int i) {
        List<List<byte[]>> list = NewModel.viewList;
        List<byte[]> listbyte = list.get(getlist_i);
        return listbyte.get(i);
    }
}
