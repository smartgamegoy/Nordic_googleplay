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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.jetec.nordic_googleplay.NewActivity.GetString.Parase;
import com.jetec.nordic_googleplay.NewModel;
import com.jetec.nordic_googleplay.R;
import com.jetec.nordic_googleplay.Screen;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class New_WriteDialog {

    private Dialog progressDialog = null;
    private Parase parase = new Parase();
    private CheckEditHint checkEditHint = new CheckEditHint();
    private Animation animation;
    private byte[] newArray = new byte[7];
    private String TAG = "New_WriteDialog";

    public New_WriteDialog() {
        super();
    }

    public void set_Dialog(Context context, String str, int getlist_i, int i, byte[] ch,
                           Button button, Vibrator vibrator, String s) {
        animation = AnimationUtils.loadAnimation(context, R.anim.animate);
        progressDialog = showDialog(context, str, getlist_i, i , ch, button, vibrator, s);
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
    }

    private String getnewStr(int dp_flag){
        byte[] dp = Arrays.copyOfRange(newArray, 2, 3);
        byte[] value = Arrays.copyOfRange(newArray, 3, newArray.length);
        double p = Math.pow(10, parase.byteArrayToInt(dp));
        String str;
        if(dp_flag == 0) {
            str = String.valueOf((parase.byteArrayToInt(value) / p));
            str = str.substring(0, str.indexOf("."));
        }
        else
            str = String.valueOf((parase.byteArrayToInt(value) / p));
        return str;
    }

    @SuppressLint("SetTextI18n")
    private Dialog showDialog(Context context, String str, int getlist_i, int i, byte[] ch,
                              Button button, Vibrator vibrator, String s) {
        Screen screen = new Screen(context);
        DisplayMetrics dm = screen.size();
        Dialog progressDialog = new Dialog(context);
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        LayoutInflater inflater = LayoutInflater.from(context);
        @SuppressLint("InflateParams") View v = inflater.inflate(R.layout.alterdialog, null);
        TextView title = v.findViewById(R.id.textView1);
        Button by = v.findViewById(R.id.button2);
        Button bn = v.findViewById(R.id.button1);
        final EditText editText = v.findViewById(R.id.editText1);
        title.setText(str);
        by.setText(context.getString(R.string.butoon_yes));
        bn.setText(context.getString(R.string.butoon_no));

        List<byte[]> list = new ArrayList<>();
        list.clear();
        list = getList(getlist_i);
        byte[] arr = list.get(0);
        byte[] dp = Arrays.copyOfRange(arr, 2, 3);
        int dp_flag = parase.byteArrayToInt(dp);
        checkEditHint.setHint(editText, ch, s, dp_flag);

        by.setOnClickListener(v1 -> {
            vibrator.vibrate(100);
            String gets = editText.getText().toString().trim();
            if(!gets.matches("")) {
                byte[] getb = convertString(gets, ch, dp_flag);
                newArray = getb;
                StringBuilder hex = new StringBuilder(getb.length * 2);
                for (byte aData : getb) {
                    hex.append(String.format("%02X", aData));
                }
                String gethex = hex.toString();
                Log.e(TAG, "gethex = " + gethex);
                button.setText(str + "\n" + getnewStr(dp_flag));
                resetlist(getlist_i, i, getb);
                NewModel.menu.getItem(0).setTitle(context.getString(R.string.send));
                NewModel.menu.getItem(0).setEnabled(true);
                progressDialog.dismiss();
            }
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

    private void resetlist(int getlist_i, int i, byte[] value){
        List<byte[]> list = new ArrayList<>();
        list.clear();
        list = NewModel.viewList.get(getlist_i);
        list.set(i, value);
        NewModel.viewList.set(getlist_i, list);
    }

    private byte[] convertString(String str, byte[] ch, int dp_flag) {
        byte[] convert = new byte[7];
        for(int j = 0; j < ch.length; j++){
            convert[j] = ch[j];
        }
        if(dp_flag == 0) {
            int num;
            if(str.contains(".")) {
                str = str.substring(0, str.indexOf("."));
                num = Integer.valueOf(str);
            }
            else {
                num = Integer.valueOf(str);
            }
            convert[2] = 0x00;
            byte[] value = parase.intToByteArray(num);
            for (int j = 0; j < value.length; j++) {
                convert[2 + (j + 1)] = value[j];
            }
        }
        else {
            if (str.contains(".")) {
                int i = str.length() - str.indexOf(".") - 1;
                str = str.replace(".", "");
                int num = Integer.valueOf(str);
                byte[] point = parase.pointToByteArray(i);
                byte[] value = parase.intToByteArray(num);
                convert[2] = point[0];
                for (int j = 0; j < value.length; j++) {
                    convert[2 + (j + 1)] = value[j];
                }
            } else {
                int num = Integer.valueOf(str);
                convert[2] = 0x00;
                byte[] value = parase.intToByteArray(num);
                for (int j = 0; j < value.length; j++) {
                    convert[2 + (j + 1)] = value[j];
                }
            }
        }

        return convert;
    }

    private List<byte[]> getList(int i){
        List<byte[]> list = new ArrayList<>();
        list.clear();

        list = NewModel.viewList.get(i);

        return list;
    }
}
