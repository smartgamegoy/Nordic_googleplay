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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.jetec.nordic_googleplay.NewActivity.GetString.ByteToHex;
import com.jetec.nordic_googleplay.R;
import com.jetec.nordic_googleplay.Screen;

import java.util.Arrays;
import java.util.List;

public class DpDialog {

    private Dialog progressDialog = null;
    private ByteToHex byteToHex = new ByteToHex();
    private byte[] newArray = new byte[7];
    private String TAG = "DpDialog";
    private int dp_flag;
    private boolean check;

    public DpDialog() {
        super();
    }

    public void set_Dialog(Context context, int dpflag, Vibrator vibrator, String str, List<byte[]> list, int i, String s) {
        progressDialog = showDialog(context, dpflag, vibrator, str, list, i, s);
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
    }

    private Dialog showDialog(Context context, int dpflag, Vibrator vibrator, String str, List<byte[]> list, int i, String s) {
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

        check = false;
        if (dpflag != 0)
            sw.setChecked(true);
        else
            sw.setChecked(false);

        sw.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // TODO Auto-generated method stub
            vibrator.vibrate(100);
            if (isChecked) {
                check = true;
                dp_flag = 1;
                Log.e(TAG, "ON");
            } else {
                check = true;
                dp_flag = 0;
                Log.e(TAG, "OFF");
            }
        });

        bn.setOnClickListener(v12 -> {
            vibrator.vibrate(100);
            progressDialog.dismiss();
        });

        by.setOnClickListener(v1 -> {
            vibrator.vibrate(100);
            if(check) {
                byte[] b_dp = pointToByteArray(dp_flag);
                int lenth = list.size();
                for (int j = 0; j < lenth; j++) {
                    if(list.get(j)[2] == b_dp[0]){
                        Log.e(TAG,"SAME DP");
                    }
                    else {
                        if(list.get(j)[2] > b_dp[0]){
                            list.get(j)[2] = b_dp[0];
                            byte[] data = Arrays.copyOfRange(list.get(j), 3, list.get(j).length);
                            int value = byteArrayToInt(data);
                            int c_calue = value / 10;
                            byte[] redata = intToByteArray(c_calue);
                            for(int k = 0; k < redata.length; k++){
                                list.get(j)[k + 3] = redata[k];
                            }
                        }
                        else if(list.get(j)[2] < b_dp[0]){
                            list.get(j)[2] = b_dp[0];
                        }
                    }

                    byte[] data = Arrays.copyOfRange(list.get(j), 0, list.get(j).length);

                    String[] arr = byteToHex.hexstring(data);
                    StringBuilder gstr = new StringBuilder();
                    for (String arrs : arr) {
                        gstr.append(arrs).append(" ");
                    }
                    Log.e(TAG, "sb1 = " + gstr);
                }
            }
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

    private byte[] pointToByteArray(int a) {
        byte[] ret = new byte[1];
        ret[0] = (byte) (a & 0xFF);
        return ret;
    }

    private  int byteArrayToInt(byte[] b) {
        if (b.length == 4)
            return b[0] << 24 | (b[1] & 0xff) << 16 | (b[2] & 0xff) << 8
                    | (b[3] & 0xff);
        else if (b.length == 2)
            return (b[0] & 0xff) << 8 | (b[1] & 0xff);
        return 0;
    }

    private byte[] intToByteArray(int a) {
        byte[] ret = new byte[4];
        ret[3] = (byte) (a & 0xFF);
        ret[2] = (byte) ((a >> 8) & 0xFF);
        ret[1] = (byte) ((a >> 16) & 0xFF);
        ret[0] = (byte) ((a >> 24) & 0xFF);
        return ret;
    }
}
