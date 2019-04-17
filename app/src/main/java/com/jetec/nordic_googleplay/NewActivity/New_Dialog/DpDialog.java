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
import com.jetec.nordic_googleplay.NewActivity.Parase;
import com.jetec.nordic_googleplay.NewActivity.ViewAdapter.SetViewPager;
import com.jetec.nordic_googleplay.NewModel;
import com.jetec.nordic_googleplay.R;
import com.jetec.nordic_googleplay.Screen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DpDialog {

    private Dialog progressDialog = null;
    private ByteToHex byteToHex = new ByteToHex();
    private Parase parase = new Parase();
    private byte[] b_dp = {0x00};
    private byte[] newArray = new byte[7];
    private String TAG = "DpDialog";
    private int dp_flag;
    private boolean check;

    public DpDialog() {
        super();
    }

    public void set_Dialog(Context context, Vibrator vibrator, String str, int getlist_i, int i,
                           String s, List<Button> buttonList) {
        List<byte[]> list = new ArrayList<>();
        list.clear();
        list = getList(getlist_i);
        byte[] arr = list.get(0);
        byte[] dp = Arrays.copyOfRange(arr, 2, 3);
        int dpflag = parase.byteArrayToInt(dp);
        progressDialog = showDialog(context, dpflag, vibrator, str, list, i, s, buttonList, getlist_i);
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
    }

    private Dialog showDialog(Context context, int dpflag, Vibrator vibrator, String str, List<byte[]> list, int i,
                              String s, List<Button> buttonList, int getlist_i) {
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

        Log.e(TAG,"dpflag = " + dpflag);
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
            if (check) {
                byte[] b_dp = parase.pointToByteArray(dp_flag);
                int lenth = list.size();
                for (int j = 0; j < lenth; j++) {
                    if (list.get(j)[2] == b_dp[0]) {
                        Log.e(TAG, "SAME DP");
                    } else {
                        if (list.get(j)[2] > b_dp[0]) {
                            list.get(j)[2] = b_dp[0];
                            byte[] data = Arrays.copyOfRange(list.get(j), 3, list.get(j).length);
                            int value = parase.byteArrayToInt(data);
                            int c_calue = value / 10;
                            byte[] redata = parase.intToByteArray(c_calue);
                            for (int k = 0; k < redata.length; k++) {
                                list.get(j)[k + 3] = redata[k];
                            }
                        } else if (list.get(j)[2] < b_dp[0]) {
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

                resetButtonView(context, list, s, buttonList, getlist_i);
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

    @SuppressLint("SetTextI18n")
    private void resetButtonView(Context context, List<byte[]> list, String s, List<Button> buttonList, int getlist_i) {
        int point;
        int count = list.size();
        NewModel.viewList.set(getlist_i, list);
        byte[] getarr = list.get(0);
        byte[] getdp = Arrays.copyOfRange(getarr, 2, 3);
        if (Arrays.equals(getdp, b_dp)) {
            point = parase.byteArrayToInt(getdp);
            Log.e(TAG, "point = " + point);
        } else {
            point = parase.byteArrayToInt(getdp);
            Log.e(TAG, "point = " + point);
        }

        for (int i = 0; i < count; i++) {
            if (i == 0) {
                byte[] arr = list.get(i);
                byte[] dp = Arrays.copyOfRange(arr, 2, 3);
                byte[] value = Arrays.copyOfRange(arr, 3, arr.length);
                double p = Math.pow(10, parase.byteArrayToInt(dp));
                if (parase.byteArrayToInt(dp) == 0) {
                    buttonList.get(i).setText(setButtontext(context, s, get_String(list.get(i)[1], list.get(i)[0])) +
                            "\n" + parase.byteArrayToInt(value));
                } else {
                    buttonList.get(i).setText(setButtontext(context, s, get_String(list.get(i)[1], list.get(i)[0])) +
                            "\n" + (parase.byteArrayToInt(value) / p));
                }
            } else if (i == 1) {
                byte[] arr = list.get(i);
                byte[] dp = Arrays.copyOfRange(arr, 2, 3);
                byte[] value = Arrays.copyOfRange(arr, 3, arr.length);
                double p = Math.pow(10, parase.byteArrayToInt(dp));
                if (parase.byteArrayToInt(dp) == 0)
                    buttonList.get(i).setText(setButtontext(context, s, get_String(list.get(i)[1], list.get(i)[0])) +
                            "\n" + parase.byteArrayToInt(value));
                else
                    buttonList.get(i).setText(setButtontext(context, s, get_String(list.get(i)[1], list.get(i)[0])) +
                            "\n" + (parase.byteArrayToInt(value) / p));
            } else if (i == 2) {
                byte[] arr = list.get(i);
                byte[] dp = Arrays.copyOfRange(arr, 2, 3);
                byte[] value = Arrays.copyOfRange(arr, 3, arr.length);
                double p = Math.pow(10, parase.byteArrayToInt(dp));
                if (parase.byteArrayToInt(dp) == 0)
                    buttonList.get(i).setText(setButtontext(context, s, get_String(list.get(i)[1], list.get(i)[0])) +
                            "\n" + parase.byteArrayToInt(value));
                else
                    buttonList.get(i).setText(setButtontext(context, s, get_String(list.get(i)[1], list.get(i)[0])) +
                            "\n" + (parase.byteArrayToInt(value) / p));
            } else if (i == 3) {
                byte[] arr = list.get(i);
                byte[] dp = Arrays.copyOfRange(arr, 2, 3);
                byte[] value = Arrays.copyOfRange(arr, 3, arr.length);
                double p = Math.pow(10, parase.byteArrayToInt(dp));
                if (parase.byteArrayToInt(dp) == 0)
                    buttonList.get(i).setText(setButtontext(context, s, get_String(list.get(i)[1], list.get(i)[0])) +
                            "\n" + parase.byteArrayToInt(value));
                else
                    buttonList.get(i).setText(setButtontext(context, s, get_String(list.get(i)[1], list.get(i)[0])) +
                            "\n" + (parase.byteArrayToInt(value) / p));
            } else if (i == 4) {
                byte[] arr = list.get(i);
                byte[] dp = Arrays.copyOfRange(arr, 2, 3);
                byte[] value = Arrays.copyOfRange(arr, 3, arr.length);
                double p = Math.pow(10, parase.byteArrayToInt(dp));
                if (parase.byteArrayToInt(dp) == 0)
                    buttonList.get(i).setText(setButtontext(context, s, get_String(list.get(i)[1], list.get(i)[0])) +
                            "\n" + parase.byteArrayToInt(value));
                else
                    buttonList.get(i).setText(setButtontext(context, s, get_String(list.get(i)[1], list.get(i)[0])) +
                            "\n" + (parase.byteArrayToInt(value) / p));
            } else if (i == 5) {
                byte[] arr = list.get(i);
                byte[] dp = Arrays.copyOfRange(arr, 2, 3);
                byte[] value = Arrays.copyOfRange(arr, 3, arr.length);
                double p = Math.pow(10, parase.byteArrayToInt(dp));
                if (parase.byteArrayToInt(dp) == 0)
                    buttonList.get(i).setText(setButtontext(context, s, get_String(list.get(i)[1], list.get(i)[0])) +
                            "\n" + parase.byteArrayToInt(value));
                else
                    buttonList.get(i).setText(setButtontext(context, s, get_String(list.get(i)[1], list.get(i)[0])) +
                            "\n" + (parase.byteArrayToInt(value) / p));
            } else if (i == 6) {
                byte[] arr = list.get(i);
                byte[] dp = Arrays.copyOfRange(arr, 2, 3);
                byte[] value = Arrays.copyOfRange(arr, 3, arr.length);
                double p = Math.pow(10, parase.byteArrayToInt(dp));
                if (parase.byteArrayToInt(dp) == 0)
                    buttonList.get(i).setText(setButtontext(context, s, get_String(list.get(i)[1], list.get(i)[0])) +
                            "\n" + parase.byteArrayToInt(value));
                else
                    buttonList.get(i).setText(setButtontext(context, s, get_String(list.get(i)[1], list.get(i)[0])) +
                            "\n" + (parase.byteArrayToInt(value) / p));
            } else if (i == 7) {
                byte[] arr = list.get(i);
                byte[] dp = Arrays.copyOfRange(arr, 2, 3);
                byte[] value = Arrays.copyOfRange(arr, 3, arr.length);
                double p = Math.pow(10, parase.byteArrayToInt(dp));
                if (parase.byteArrayToInt(dp) == 0)
                    buttonList.get(i).setText(setButtontext(context, s, get_String(list.get(i)[1], list.get(i)[0])) +
                            "\n" + parase.byteArrayToInt(value));
                else
                    buttonList.get(i).setText(setButtontext(context, s, get_String(list.get(i)[1], list.get(i)[0])) +
                            "\n" + (parase.byteArrayToInt(value) / p));
            } else if (i == 8) {
                byte[] arr = list.get(i);
                byte[] dp = Arrays.copyOfRange(arr, 2, 3);
                byte[] value = Arrays.copyOfRange(arr, 3, arr.length);
                double p = Math.pow(10, parase.byteArrayToInt(dp));
                if (parase.byteArrayToInt(dp) == 0)
                    buttonList.get(i).setText(setButtontext(context, s, get_String(list.get(i)[1], list.get(i)[0])) +
                            "\n" + parase.byteArrayToInt(value));
                else
                    buttonList.get(i).setText(setButtontext(context, s, get_String(list.get(i)[1], list.get(i)[0])) +
                            "\n" + (parase.byteArrayToInt(value) / p));
            } else if (i == 9) {
                byte[] arr = list.get(i);
                byte[] dp = Arrays.copyOfRange(arr, 2, 3);
                byte[] value = Arrays.copyOfRange(arr, 3, arr.length);
                double p = Math.pow(10, parase.byteArrayToInt(dp));
                if (parase.byteArrayToInt(dp) == 0)
                    buttonList.get(i).setText(setButtontext(context, s, get_String(list.get(i)[1], list.get(i)[0]))
                            + "\n" + parase.byteArrayToInt(value));
                else
                    buttonList.get(i).setText(setButtontext(context, s, get_String(list.get(i)[1], list.get(i)[0]))
                            + "\n" + (parase.byteArrayToInt(value) / p));
            }
        }
    }

    private String get_String(byte a, byte b) {
        String str = "";

        if (a == 0x01) {
            str = "PV" + b;
        } else if (a == 0x02) {
            str = "EH" + b;
        } else if (a == 0x03) {
            str = "EL" + b;
        } else if (a == 0x04) {
            str = "IH" + b;
        } else if (a == 0x05) {
            str = "IL" + b;
        } else if (a == 0x06) {
            str = "CR" + b;
        } else if (a == 0x07) {
            str = "ADR";
        } else if (a == 0x08) {
            str = "Register" + b;
        } else if (a == 0x09) {
            str = "length" + b;
        } else if (a == 0x0A) {
            str = "RL1";
        } else if (a == 0x0B) {
            str = "RL2";
        } else if (a == 0x0C) {
            str = "RL3";
        } else if (a == 0x0D) { //4-20mA output
            str = "OT1";
        } else if (a == 0x0E) {
            str = "OT2";
        } else if (a == 0x0F) {
            str = "OT3";
        }

        return str;
    }

    private String setButtontext(Context context, String s, String gets) {
        String str = "";
        if (s.matches("T")) {
            if (gets.startsWith("PV")) {
                str = context.getString(R.string.T) + context.getString(R.string.PV1);
            } else if (gets.startsWith("EH")) {
                str = context.getString(R.string.T) + context.getString(R.string.EH1);
            } else if (gets.startsWith("EL")) {
                str = context.getString(R.string.T) + context.getString(R.string.EL1);
            } else if (gets.startsWith("CR")) {
                str = context.getString(R.string.T) + context.getString(R.string.CR1);
            } else if (gets.startsWith("RL1")) {
                str = context.getString(R.string.RL1);
            } else if (gets.startsWith("RL2")) {
                str = context.getString(R.string.RL2);
            } else if (gets.startsWith("RL3")) {
                str = context.getString(R.string.RL3);
            }
        } else if (s.matches("H")) {
            if (gets.startsWith("PV")) {
                str = context.getString(R.string.H) + context.getString(R.string.PV1);
            } else if (gets.startsWith("EH")) {
                str = context.getString(R.string.H) + context.getString(R.string.EH1);
            } else if (gets.startsWith("EL")) {
                str = context.getString(R.string.H) + context.getString(R.string.EL1);
            } else if (gets.startsWith("CR")) {
                str = context.getString(R.string.H) + context.getString(R.string.CR1);
            } else if (gets.startsWith("RL1")) {
                str = context.getString(R.string.RL1);
            } else if (gets.startsWith("RL2")) {
                str = context.getString(R.string.RL2);
            } else if (gets.startsWith("RL3")) {
                str = context.getString(R.string.RL3);
            }
        } else if (s.matches("C")) {
            if (gets.startsWith("PV")) {
                str = context.getString(R.string.Co2) + context.getString(R.string.PV1);
            } else if (gets.startsWith("EH")) {
                str = context.getString(R.string.Co2) + context.getString(R.string.EH1);
            } else if (gets.startsWith("EL")) {
                str = context.getString(R.string.Co2) + context.getString(R.string.EL1);
            } else if (gets.startsWith("CR")) {
                str = context.getString(R.string.Co2) + context.getString(R.string.CR1);
            } else if (gets.startsWith("RL1")) {
                str = context.getString(R.string.RL1);
            } else if (gets.startsWith("RL2")) {
                str = context.getString(R.string.RL2);
            } else if (gets.startsWith("RL3")) {
                str = context.getString(R.string.RL3);
            }
        } else if (s.matches("D")) {
            if (gets.startsWith("PV")) {
                str = context.getString(R.string.Co2) + context.getString(R.string.PV1);
            } else if (gets.startsWith("EH")) {
                str = context.getString(R.string.Co2) + context.getString(R.string.EH1);
            } else if (gets.startsWith("EL")) {
                str = context.getString(R.string.Co2) + context.getString(R.string.EL1);
            } else if (gets.startsWith("CR")) {
                str = context.getString(R.string.Co2) + context.getString(R.string.CR1);
            } else if (gets.startsWith("RL1")) {
                str = context.getString(R.string.RL1);
            } else if (gets.startsWith("RL2")) {
                str = context.getString(R.string.RL2);
            } else if (gets.startsWith("RL3")) {
                str = context.getString(R.string.RL3);
            }
        } else if (s.matches("E")) {
            if (gets.startsWith("PV")) {
                str = context.getString(R.string.Co2) + context.getString(R.string.PV1);
            } else if (gets.startsWith("EH")) {
                str = context.getString(R.string.Co2) + context.getString(R.string.EH1);
            } else if (gets.startsWith("EL")) {
                str = context.getString(R.string.Co2) + context.getString(R.string.EL1);
            } else if (gets.startsWith("CR")) {
                str = context.getString(R.string.Co2) + context.getString(R.string.CR1);
            } else if (gets.startsWith("RL1")) {
                str = context.getString(R.string.RL1);
            } else if (gets.startsWith("RL2")) {
                str = context.getString(R.string.RL2);
            } else if (gets.startsWith("RL3")) {
                str = context.getString(R.string.RL3);
            }
        } else if (s.matches("I")) {
            if (gets.startsWith("PV")) {
                str = context.getString(R.string.table_i) + context.getString(R.string.PV1);
            } else if (gets.startsWith("EH")) {
                str = context.getString(R.string.table_i) + context.getString(R.string.EH1);
            } else if (gets.startsWith("EL")) {
                str = context.getString(R.string.table_i) + context.getString(R.string.EL1);
            } else if (gets.startsWith("CR")) {
                str = context.getString(R.string.table_i) + context.getString(R.string.CR1);
            } else if (gets.startsWith("IH")) {
                str = context.getString(R.string.IH);
            } else if (gets.startsWith("IL")) {
                str = context.getString(R.string.IL);
            } else if (gets.startsWith("RL1")) {
                str = context.getString(R.string.RL1);
            } else if (gets.startsWith("RL2")) {
                str = context.getString(R.string.RL2);
            } else if (gets.startsWith("RL3")) {
                str = context.getString(R.string.RL3);
            }
        }

        return str;
    }

    private List<byte[]> getList(int i){
        List<byte[]> list = new ArrayList<>();
        list.clear();

        list = NewModel.viewList.get(i);

        return list;
    }
}
