package com.jetec.nordic_googleplay.Dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import com.jetec.nordic_googleplay.R;
import com.jetec.nordic_googleplay.Screen;
import java.util.Objects;

import static android.content.Context.VIBRATOR_SERVICE;

public class WriteDialog {

    private Dialog progressDialog = null;
    private Vibrator vibrator;
    private boolean password = false;

    public WriteDialog(){
        super();
    }

    public void set_Dialog(Context context, boolean password){
        vibrator = (Vibrator) context.getSystemService(VIBRATOR_SERVICE);
        this.password = password;
        progressDialog = showDialog(context);
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
    }

    public void closeDialog(){
        if(progressDialog != null) {
            if (progressDialog.isShowing())
                progressDialog.dismiss();
        }
    }

    public boolean checkshowing(){
        if(progressDialog != null) {
            return progressDialog.isShowing();
        }
        else
            return false;
    }

    private Dialog showDialog(Context context){
        Screen screen = new Screen(context);
        DisplayMetrics dm = screen.size();
        progressDialog = new Dialog(context);
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(progressDialog.getWindow()).setBackgroundDrawable(new ColorDrawable());

        LayoutInflater inflater = LayoutInflater.from(context);
        @SuppressLint("InflateParams") View v = inflater.inflate(R.layout.loadgif, null);

        if (dm.heightPixels > dm.widthPixels) {
            progressDialog.setContentView(v,new LinearLayout.LayoutParams(dm.widthPixels / 4,
                    dm.widthPixels / 4));
        } else {
            progressDialog.setContentView(v, new LinearLayout.LayoutParams(dm.heightPixels / 4,
                    dm.heightPixels / 4));
        }

        if(password) {
            progressDialog.setOnKeyListener((dialog, keyCode, event) -> {
                vibrator.vibrate(100);
                //noinspection deprecation
                return keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0;
            });
        }

        return progressDialog;
    }
}
