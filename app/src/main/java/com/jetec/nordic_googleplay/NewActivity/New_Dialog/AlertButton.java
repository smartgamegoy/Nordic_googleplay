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
    private byte[] newArray = new byte[7];
    private String sp_str;

    public AlertButton() {
        super();
    }

    public void set_Dialog(Context context, Button button, Vibrator vibrator,
                           String title, int getlist_i, int i) {
        /*List<String> spinnerList = new ArrayList<>();
        spinnerList.clear();
        spinnerList = getspinner(context, nameList);*/
        //Log.e(TAG, "spinnerList = " + spinnerList);
        progressDialog = showDialog(context, button, vibrator, title, getlist_i, i);
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
    }

    @SuppressLint("SetTextI18n")
    private Dialog showDialog(Context context, Button button, Vibrator vibrator,
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
        
        by.setOnClickListener(v12 -> {
            vibrator.vibrate(100);
            button.setText(str + "\n" + sp_str);
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

    private void setNewbyte(int getlist_i, int finalI, List<String> spinnerList){
        List<byte[]> list = new ArrayList<>();
        list.clear();
        list = NewModel.viewList.get(getlist_i);
        byte[] original = list.get(finalI);
        int chose = spinnerList.indexOf(sp_str);
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

    private List<String> getspinner(Context context, List<Character> nameList) {
        List<String> spinnerList = new ArrayList<>();
        spinnerList.clear();
        spinnerList.add(context.getString(R.string.chose));
        for (int i = 0; i < nameList.size(); i++) {
            if (nameList.get(i).toString().matches("T")) {
                spinnerList.add(context.getString(R.string.T));
            } else if (nameList.get(i).toString().matches("H")) {
                spinnerList.add(context.getString(R.string.H));
            } else if (nameList.get(i).toString().matches("C")) {
                spinnerList.add(context.getString(R.string.C));
            } else if (nameList.get(i).toString().matches("D")) {
                spinnerList.add(context.getString(R.string.C));
            } else if (nameList.get(i).toString().matches("E")) {
                spinnerList.add(context.getString(R.string.C));
            } else if (nameList.get(i).toString().matches("M")) {
                spinnerList.add(context.getString(R.string.pm));
            } else if (nameList.get(i).toString().matches("I")) {
                spinnerList.add((context.getString(R.string.table_i) + (i + 1)));
            }
        }
        return spinnerList;
    }


}
