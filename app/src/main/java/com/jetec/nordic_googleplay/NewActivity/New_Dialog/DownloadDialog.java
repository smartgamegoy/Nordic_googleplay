package com.jetec.nordic_googleplay.NewActivity.New_Dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Vibrator;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.jetec.nordic_googleplay.NewActivity.SendByte.SendString;
import com.jetec.nordic_googleplay.R;
import com.jetec.nordic_googleplay.Screen;
import com.jetec.nordic_googleplay.Value;

import static java.lang.Thread.sleep;

public class DownloadDialog {

    private Dialog progressDialog = null;
    private String TAG = "DownloadDialog";
    private TextView showing;

    public DownloadDialog(){
        super();
    }

    public void set_Dialog(Context context, Vibrator vibrator){
        progressDialog = showDialog(context, vibrator);
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
    }

    private Dialog showDialog(Context context, Vibrator vibrator){
        SendString sendString = new SendString();
        Screen screen = new Screen(context);
        DisplayMetrics dm = screen.size();
        Dialog progressDialog = new Dialog(context);
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        LayoutInflater inflater = LayoutInflater.from(context);
        @SuppressLint("InflateParams") View v = inflater.inflate(R.layout.userdownloaddialog, null);

        ProgressBar pb_progress_bar = v.findViewById(R.id.pb_progress_bar);
        showing = v.findViewById(R.id.textView4);
        Button bn = v.findViewById(R.id.button1);
        Button by = v.findViewById(R.id.button2);

        bn.setText(context.getString(R.string.mes_no));
        by.setText(context.getString(R.string.mes_yes));
        pb_progress_bar.setVisibility(View.GONE);

        bn.setOnClickListener(v12 -> {
            vibrator.vibrate(100);
            progressDialog.dismiss();
        });

        by.setOnClickListener(v1 -> {
            vibrator.vibrate(100);
            pb_progress_bar.setVisibility(View.VISIBLE);
            bn.setClickable(false);
            new AlertDialog.Builder(context)
                    .setTitle(R.string.warning)
                    .setMessage(R.string.stoprecord)
                    .setPositiveButton(R.string.butoon_yes, (dialog, which) -> {
                        vibrator.vibrate(100);
                        sendString.sendstr("END");
                        try {
                            sleep(100);
                            sendString.sendstr("Delay00010");
                            sleep(100);
                            sendString.sendstr("DOWNLOAD");
                            sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Value.opendialog = true;
                    })
                    .setNegativeButton(R.string.butoon_no, (dialog, which) -> {
                        vibrator.vibrate(100);
                        Log.e(TAG, "取消下載");
                    })
                    .show();
        });

        if (dm.heightPixels > dm.widthPixels) {
            progressDialog.setContentView(v, new ConstraintLayout.LayoutParams((2 * dm.widthPixels / 3),
                    (2 * dm.heightPixels / 5)));
        } else {
            progressDialog.setContentView(v, new ConstraintLayout.LayoutParams((2 * dm.widthPixels / 5),
                    (7 * dm.heightPixels / 11)));
        }

        progressDialog.setOnKeyListener((dialog, keyCode, event) -> {
            vibrator.vibrate(100);
            return true;
        });
        return progressDialog;
    }

    public TextView getText(){
        return showing;
    }

    public Dialog getprocess(){
        return progressDialog;
    }
}
