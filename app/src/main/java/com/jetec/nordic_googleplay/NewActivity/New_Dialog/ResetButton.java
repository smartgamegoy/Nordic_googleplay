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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.jetec.nordic_googleplay.NewActivity.GetString.ByteToHex;
import com.jetec.nordic_googleplay.NewActivity.GetString.Parase;
import com.jetec.nordic_googleplay.NewModel;
import com.jetec.nordic_googleplay.R;
import com.jetec.nordic_googleplay.Screen;
import com.jetec.nordic_googleplay.Value;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ResetButton {

    private String TAG = "ResetButton";
    private Dialog progressDialog = null;
    private Parase parase = new Parase();
    private List<Character> nameList;
    private ByteToHex byteToHex = new ByteToHex();
    private byte[] newArray = new byte[7];
    private String sp_str;

    public ResetButton() {
        super();
    }

    public void set_Dialog(Context context, Button button, Vibrator vibrator, String title, int i, int locate) {
        List<String> spinnerList = new ArrayList<>();
        spinnerList.clear();
        spinnerList = getspinner(context, i);
        getList();
        Log.e(TAG, "spinnerList = " + spinnerList);
        progressDialog = showDialog(context, button, vibrator, spinnerList, title, i, locate);
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
    }

    @SuppressLint("SetTextI18n")
    private Dialog showDialog(Context context, Button button, Vibrator vibrator, List<String> spinnerList,
                              String str, int i, int locate) {
        Screen screen = new Screen(context);
        DisplayMetrics dm = screen.size();
        Dialog progressDialog = new Dialog(context);
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        LayoutInflater inflater = LayoutInflater.from(context);
        @SuppressLint("InflateParams") View v = inflater.inflate(R.layout.resetdialog, null);
        TextView title = v.findViewById(R.id.textView1);
        Button by = v.findViewById(R.id.button2);
        Button bn = v.findViewById(R.id.button1);
        Spinner s1 = v.findViewById(R.id.spinner1);
        title.setText(str);
        by.setText(context.getString(R.string.butoon_yes));
        bn.setText(context.getString(R.string.butoon_no));

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                context, R.layout.spinner_button, spinnerList) {    //android.R.layout.simple_spinner_item
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    sp_str = spinnerList.get(position);
                    return true;
                } else {
                    return true;
                }
            }
        };
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_button);
        s1.setAdapter(spinnerArrayAdapter);
        s1.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sp_str = spinnerList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        by.setOnClickListener(v12 -> {
            vibrator.vibrate(100);
            List<String> list = NewModel.spinList;
            button.setText(str + "\n" + sp_str);
            setNewbyte(context, i, locate);
            list.set(i, sp_str);
            NewModel.menu.getItem(0).setTitle(context.getString(R.string.send));
            NewModel.menu.getItem(0).setEnabled(true);
            progressDialog.dismiss();
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

    private void setNewbyte(Context context, int finalI, int locate) {
        int chose = 0;
        List<byte[]> list = new ArrayList<>();
        list.clear();
        list = NewModel.viewList.get(locate);
        byte[] original = list.get(finalI);
        if (sp_str.matches(context.getString(R.string.chose))) {
            //chose = 0;
        } else if (sp_str.matches(context.getString(R.string.T))) {
            chose = nameList.indexOf('T') + 1;
        } else if (sp_str.matches(context.getString(R.string.H))) {
            chose = nameList.indexOf('H') + 1;
        } else if (sp_str.matches(context.getString(R.string.C))) {
            if (nameList.indexOf('C') != -1)
                chose = nameList.indexOf('C') + 1;
            else if (nameList.indexOf('D') != -1)
                chose = nameList.indexOf('D') + 1;
            else if (nameList.indexOf('E') != -1)
                chose = nameList.indexOf('E') + 1;
        } else if (sp_str.matches(context.getString(R.string.pm))) {
            chose = nameList.indexOf('M') + 1;
        } else if (sp_str.matches(context.getString(R.string.table_i))) {
            chose = nameList.indexOf('I') + 1;
        }
        Log.e(TAG, "index chose = " + chose);
        byte[] calcu = Arrays.copyOfRange(original, 0, 3);
        byte[] value = parase.intToByteArray(chose);
        System.arraycopy(calcu, 0, newArray, 0, calcu.length);
        System.arraycopy(value, 0, newArray, 3, value.length);
        list.set(finalI, newArray);
        NewModel.viewList.set(locate, list);

        for (int i = 0; i < list.size(); i++) {
            byte[] data = Arrays.copyOfRange(NewModel.viewList.get(locate).get(i), 0,
                    NewModel.viewList.get(locate).get(i).length);
            String[] arr = byteToHex.hexstring(data);
            StringBuilder gstr = new StringBuilder();
            for (String arrs : arr) {
                gstr.append(arrs).append(" ");
            }
            Log.e(TAG, "sb1 = " + gstr);
        }
    }

    private List<String> getspinner(Context context, int list_i) {
        int count = 0;
        String model = Value.deviceModel;
        String[] arr = model.split("-");
        String name = arr[2];
        if (name.contains("Y") || name.contains("Z")) {
            count++;
        }
        name = name.replace("Y", "");
        name = name.replace("L", "");
        name = name.replace("Z", "");
        List<Character> nameList = new ArrayList<>();
        nameList.clear();
        for (int i = 0; i < name.length(); i++) {
            nameList.add(name.charAt(i));
        }
        List<String> spinnerList = new ArrayList<>();
        spinnerList.clear();
        spinnerList.add(context.getString(R.string.chose));
        for (int i = 0; i < nameList.size(); i++) {
            if (nameList.get(i).toString().matches("T")) {
                spinnerList.add(context.getString(R.string.T));
            } else if (nameList.get(i).toString().matches("H")) {
                spinnerList.add(context.getString(R.string.H));
            } else if (nameList.get(i).toString().matches("C")) {
                spinnerList.add(context.getString(R.string.C));
            } else if (nameList.get(i).toString().matches("D")) {
                spinnerList.add(context.getString(R.string.C));
            } else if (nameList.get(i).toString().matches("E")) {
                spinnerList.add(context.getString(R.string.C));
            } else if (nameList.get(i).toString().matches("M")) {
                spinnerList.add(context.getString(R.string.pm));
            } else if (nameList.get(i).toString().matches("I")) {
                spinnerList.add((context.getString(R.string.table_i) + (i + 1 + count)));
            }
        }

        List<String> list = NewModel.spinList;
        int here;
        for (int j = 0; j < list.size(); j++) {
            if (!list.get(j).matches(context.getString(R.string.chose))) {
                here = spinnerList.indexOf(list.get(j));
                if (here != -1) {
                    spinnerList.remove(list.get(j));
                }
            }
        }

        Log.e(TAG, "list_i = " + list_i);
        Log.e(TAG, "spinnerList = " + spinnerList.size());
        return spinnerList;
    }

    private void getList() {
        String model = Value.deviceModel;
        String[] arr = model.split("-");
        String name = arr[2];
        nameList = new ArrayList<>();
        nameList.clear();
        for (int i = 0; i < name.length(); i++) {
            nameList.add(name.charAt(i));
        }
    }
}
