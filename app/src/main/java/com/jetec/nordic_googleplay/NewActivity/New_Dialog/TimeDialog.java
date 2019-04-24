package com.jetec.nordic_googleplay.NewActivity.New_Dialog;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.jetec.nordic_googleplay.NewActivity.SendByte.SendString;
import com.jetec.nordic_googleplay.R;
import com.jetec.nordic_googleplay.Screen;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeDialog {

    private String TAG = "TimeDialog";
    private Dialog progressDialog = null;
    private SendString sendString = new SendString();
    private Handler mHandler;
    private String getdate = "", gettime = "";

    public TimeDialog(){
        super();
    }

    public void setDialog(Context context, String str, Vibrator vibrator){
        progressDialog = showDialog(context, vibrator, str);
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
    }

    private Dialog showDialog(Context context, Vibrator vibrator, String str) {
        Screen screen = new Screen(context);
        DisplayMetrics dm = screen.size();
        Dialog progressDialog = new Dialog(context);
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        LayoutInflater inflater = LayoutInflater.from(context);
        @SuppressLint("InflateParams") View v = inflater.inflate(R.layout.datesetdialog, null);

        TextView title = v.findViewById(R.id.textView1);
        TextView t_date = v.findViewById(R.id.textView2);
        TextView t_time = v.findViewById(R.id.textView3);
        Button b_date = v.findViewById(R.id.button1);
        Button b_time = v.findViewById(R.id.button2);
        Button by = v.findViewById(R.id.button4);
        Button bn = v.findViewById(R.id.button3);
        Button bq = v.findViewById(R.id.button5);

        title.setText(str);
        b_date.setText(context.getString(R.string.date));
        b_time.setText(context.getString(R.string.time));
        by.setText(context.getString(R.string.butoon_yes));
        bn.setText(context.getString(R.string.butoon_no));
        bq.setText(context.getString(R.string.quick));

        b_date.setOnClickListener(v12 -> {
            vibrator.vibrate(100);
            datechose(context, t_date);
        });

        b_time.setOnClickListener(v12 -> {
            vibrator.vibrate(100);
            timechose(context, t_time);
        });

        by.setOnClickListener(v12 -> {
            vibrator.vibrate(100);
            if(!getdate.matches("") && !gettime.matches("")){
                setnewtime();
                progressDialog.dismiss();
            }
        });

        bq.setOnClickListener(v12 -> {
            vibrator.vibrate(100);
            quicksettime();
        });

        bn.setOnClickListener(v12 -> {
            vibrator.vibrate(100);
            progressDialog.dismiss();
        });

        if (dm.heightPixels > dm.widthPixels) { //需修改
            progressDialog.setContentView(v, new LinearLayout.LayoutParams(2 * dm.widthPixels / 3,
                    3 * dm.heightPixels / 4));
        } else {
            progressDialog.setContentView(v, new LinearLayout.LayoutParams(dm.widthPixels / 3,
                    dm.heightPixels / 2));
        }

        return progressDialog;
    }

    private void quicksettime(){
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat get_date = new SimpleDateFormat("yyMMdd");
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat get_time = new SimpleDateFormat("HHmmss"); //for 12hours use hhmmss
        Date date = new Date();
        String strDate = get_date.format(date);
        String strtime = get_time.format(date);

        mHandler = new Handler();

        Log.e(TAG, "strDate = " + strDate);
        Log.e(TAG, "strtime = " + strtime);

        String setDate = "DATE" + strDate;
        String setTime = "TIME" + strtime;

        sendString.sendstr(setDate);
        mHandler.postDelayed(() -> {
            sendString.sendstr(setTime);
            mHandler.removeCallbacksAndMessages(null);
            progressDialog.dismiss();
        }, 500);
    }

    @SuppressLint("SetTextI18n")
    private void datechose(Context context, TextView textView) {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR); // current year
        int mMonth = c.get(Calendar.MONTH); // current month
        int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
        // date picker dialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, (view, year, monthOfYear, dayOfMonth) -> {
            // set day of month , month and year value in the edit text
            getdate = Fix(year).substring(2) + Fix((monthOfYear + 1)) + Fix(dayOfMonth);
            textView.setText(Fix(year) + "-" + Fix((monthOfYear + 1)) + "-" + Fix(dayOfMonth));
            Log.e(TAG, "getdate = " + getdate);
        }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    @SuppressLint("SetTextI18n")
    private void timechose(Context context, TextView textView) {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minutes = c.get(Calendar.MINUTE);
        // date picker dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(context, (view, hourOfDay, minute) -> {
            gettime = Fix(hourOfDay) + Fix(minute) + "00";
            textView.setText(Fix(hourOfDay) + ":" + Fix(minute) + ":" + "00");
            Log.e(TAG, "gettime = " + gettime);
        }, hour, minutes, true);
        timePickerDialog.show();
    }

    private static String Fix(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }

    private void setnewtime(){
        mHandler = new Handler();

        String senddate = "DATE" + getdate;
        String sendtime = "TIME" + gettime;

        sendString.sendstr(senddate);
        mHandler.postDelayed(() -> {
            sendString.sendstr(sendtime);
            mHandler.removeCallbacksAndMessages(null);
            progressDialog.dismiss();
        }, 500);
    }
}
