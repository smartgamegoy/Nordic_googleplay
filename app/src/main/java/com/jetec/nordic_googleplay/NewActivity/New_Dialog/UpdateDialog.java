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
import android.widget.ListView;
import android.widget.Toast;
import com.jetec.nordic_googleplay.NewActivity.UserSQL.SaveSQL;
import com.jetec.nordic_googleplay.R;
import com.jetec.nordic_googleplay.Screen;
import com.jetec.nordic_googleplay.Value;
import java.util.ArrayList;
import java.util.HashMap;

public class UpdateDialog {

    private Dialog progressDialog = null;
    private SaveSQL saveSQL;
    private ArrayList<HashMap<String, String>> listData;
    private DataListView dataListView;

    public UpdateDialog(){
        super();
    }

    public void setDialog(Context context, Vibrator vibrator, int select_item, ArrayList<String> SQLdata, ListView list){
        saveSQL = new SaveSQL(context);
        listData = new ArrayList<>();
        listData.clear();
        progressDialog = showDialog(context, vibrator, select_item, SQLdata, list);
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
    }

    private Dialog showDialog(Context context, Vibrator vibrator, int select_item, ArrayList<String> SQLdata, ListView list){
        Screen screen = new Screen(context);
        DisplayMetrics dm = screen.size();
        Dialog progressDialog = new Dialog(context);
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        LayoutInflater inflater = LayoutInflater.from(context);
        @SuppressLint("InflateParams") View v = inflater.inflate(R.layout.usermodifyname, null);
        Button by = v.findViewById(R.id.button2);
        Button bn = v.findViewById(R.id.button1);
        EditText ed1 = v.findViewById(R.id.editText1);

        bn.setOnClickListener(v12 -> {
            vibrator.vibrate(100);
            saveSQL.close();
            progressDialog.dismiss();
        });

        final int select = select_item;

        by.setOnClickListener(v1 -> {
            vibrator.vibrate(100);
            String getname = ed1.getText().toString().trim();
            if (getname.matches("")) {
                Toast.makeText(context, context.getString(R.string.addblank), Toast.LENGTH_SHORT).show();
            } else {
                if (saveSQL.getCount(getname, Value.deviceModel) == 0) {
                    saveSQL.update(Integer.valueOf(SQLdata.get(select)), getname);
                    listData = saveSQL.fillList(Value.deviceModel);
                    dataListView = new DataListView(context, listData);
                    list.setAdapter(dataListView);
                    saveSQL.close();
                    progressDialog.dismiss();
                } else {
                    Toast.makeText(context, context.getString(R.string.same), Toast.LENGTH_SHORT).show();
                }
            }
        });

        progressDialog.setContentView(v, new LinearLayout.LayoutParams((3 * dm.widthPixels / 5),
                (2 * dm.heightPixels / 5)));

        return progressDialog;
    }
}
