package com.jetec.nordic_googleplay.NewActivity.New_Dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;

import com.jetec.nordic_googleplay.NewActivity.SendByte.SendString;
import com.jetec.nordic_googleplay.R;
import com.jetec.nordic_googleplay.Screen;

public class TimeDialog {

    private String TAG = "TimeDialog";
    private Dialog progressDialog = null;
    private SendString sendString = new SendString();

    public TimeDialog(){
        super();
    }

    public void setDialog(Context context, Button button, String str, Vibrator vibrator){
        progressDialog = showDialog(context, button, vibrator, str);
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
    }

    private Dialog showDialog(Context context, Button button, Vibrator vibrator, String str) {
        Screen screen = new Screen(context);
        DisplayMetrics dm = screen.size();
        Dialog progressDialog = new Dialog(context);
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        LayoutInflater inflater = LayoutInflater.from(context);
        @SuppressLint("InflateParams") View v = inflater.inflate(R.layout.datesetdialog, null);



        if (dm.heightPixels > dm.widthPixels) { //需修改
            progressDialog.setContentView(v, new LinearLayout.LayoutParams(2 * dm.widthPixels / 3,
                    3 * dm.heightPixels / 4));
        } else {
            progressDialog.setContentView(v, new LinearLayout.LayoutParams(dm.widthPixels / 3,
                    dm.heightPixels / 2));
        }

        return progressDialog;
    }
}
