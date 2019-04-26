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
import android.widget.ListView;
import android.widget.TextView;

import com.jetec.nordic_googleplay.NewActivity.UserSQL.SaveSQL;
import com.jetec.nordic_googleplay.R;
import com.jetec.nordic_googleplay.Screen;
import com.jetec.nordic_googleplay.Value;

public class SaveDialog {

    private Dialog progressDialog = null;
    private String TAG = "SaveDialog";
    private SaveSQL saveSQL;
    private int select_item;

    public SaveDialog(){
        super();
    }

    public void setDialog(Context context, Vibrator vibrator){
        saveSQL = new SaveSQL(context);
        progressDialog = showDialog(context, vibrator);
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
    }

    private Dialog showDialog(Context context, Vibrator vibrator){
        Screen screen = new Screen(context);
        DisplayMetrics dm = screen.size();
        Dialog progressDialog = new Dialog(context);
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        LayoutInflater inflater = LayoutInflater.from(context);
        @SuppressLint("InflateParams") View v = inflater.inflate(R.layout.savedatalist, null);
        ConstraintLayout setlinear = v.findViewById(R.id.savelist);
        Button close = v.findViewById(R.id.button1);
        EditText name = v.findViewById(R.id.editText);
        Button add = v.findViewById(R.id.button2);
        Button del = v.findViewById(R.id.button3);
        Button update = v.findViewById(R.id.button4);
        ListView list = v.findViewById(R.id.datalist1);
        TextView t1 = v.findViewById(R.id.no_list);

        if (saveSQL.getCount() == 0) {
            list.setVisibility(View.GONE);
            t1.setVisibility(View.VISIBLE);
        }else {
            if (saveSQL.modelsearch(Value.deviceModel) > 0) {
                select_item = -1;
                list.setVisibility(View.VISIBLE);
                t1.setVisibility(View.GONE);
                listData = data_table.fillList(Value.deviceModel);
                dataList = new DataList(this, listData, Value.all_Width);
                list.setAdapter(dataList);
                list.setOnItemClickListener(mListClickListener);
            } else {
                list.setVisibility(View.GONE);
                t1.setVisibility(View.VISIBLE);
            }
        }

        if(dm.heightPixels > dm.widthPixels) {
            progressDialog.setContentView(v, new ConstraintLayout.LayoutParams((3 * dm.widthPixels / 5),
                    (dm.heightPixels / 2)));
        }
        else {
            progressDialog.setContentView(v, new ConstraintLayout.LayoutParams((2 * dm.widthPixels / 5),
                    (5 * dm.heightPixels / 6)));
        }

        return progressDialog;
    }
}
