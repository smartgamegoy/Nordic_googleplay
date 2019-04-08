package com.jetec.nordic_googleplay.NewActivity.New_Dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Vibrator;
import android.support.constraint.ConstraintLayout;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jetec.nordic_googleplay.Activity.DeviceFunction;
import com.jetec.nordic_googleplay.R;
import com.jetec.nordic_googleplay.Screen;

import java.util.List;

import static android.content.Context.VIBRATOR_SERVICE;

public class New_WriteDialog {

    private Dialog progressDialog = null;
    private Vibrator vibrator;
    private String TAG = "New_WriteDialog";

    public New_WriteDialog(){
        super();
    }

    public void set_Dialog(Context context, int dp_flag, String str, List<byte[]> list, int i){
        vibrator = (Vibrator) context.getSystemService(VIBRATOR_SERVICE);
        progressDialog = showDialog(context, dp_flag, str);
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
    }

    private Dialog showDialog(Context context, int dp_flag, String str){
        Screen screen = new Screen(context);
        DisplayMetrics dm = screen.size();
        Dialog progressDialog = new Dialog(context);
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        LayoutInflater inflater = LayoutInflater.from(context);
        @SuppressLint("InflateParams") View v = inflater.inflate(R.layout.alterdialog, null);
        ConstraintLayout layout = v.findViewById(R.id.input_dialog);
        TextView title = v.findViewById(R.id.textView1);
        Button by = v.findViewById(R.id.button2);
        Button bn = v.findViewById(R.id.button1);
        final EditText editText = v.findViewById(R.id.editText1);
        title.setText(str);
        by.setText(context.getString(R.string.butoon_yes));
        bn.setText(context.getString(R.string.butoon_no));

        if (dm.heightPixels > dm.widthPixels) { //需修改
            progressDialog.setContentView(v,new LinearLayout.LayoutParams(dm.widthPixels / 4,
                    dm.widthPixels / 4));
        } else {
            progressDialog.setContentView(v, new LinearLayout.LayoutParams(dm.heightPixels / 4,
                    dm.heightPixels / 4));
        }

        return progressDialog;
    }
}
