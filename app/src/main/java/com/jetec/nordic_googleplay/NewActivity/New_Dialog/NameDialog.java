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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.jetec.nordic_googleplay.EditManagert.EditChangeName;
import com.jetec.nordic_googleplay.NewActivity.SendByte.SendString;
import com.jetec.nordic_googleplay.R;
import com.jetec.nordic_googleplay.Screen;

public class NameDialog {

    private String TAG = "NameDialog";
    private SendString sendString = new SendString();

    public NameDialog(){
        super();
    }

    public void setDialog(Context context, Button button, String str, Vibrator vibrator){
        Dialog progressDialog = showDialog(context, button, vibrator, str);
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
    }

    @SuppressLint("SetTextI18n")
    private Dialog showDialog(Context context, Button button, Vibrator vibrator, String str) {
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

        editText.setHint(context.getString(R.string.changename));
        editText.addTextChangedListener(new EditChangeName(editText));
        button.setAllCaps(false);

        by.setOnClickListener(v12 -> {
            vibrator.vibrate(100);
            String text = editText.getText().toString().trim();
            if(!text.matches("")) {
                button.setText(str + "\n" + text);
                String devicename = "NAME" + text;
                sendString.sendstr(devicename);
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
}
