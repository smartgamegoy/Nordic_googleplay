package com.jetec.nordic_googleplay.DialogFunction;

import android.app.Dialog;
import android.content.Context;
import android.widget.Toast;
import com.jetec.nordic_googleplay.R;
import com.jetec.nordic_googleplay.SendValue;
import com.jetec.nordic_googleplay.Service.BluetoothLeService;

public class IH {

    private Context context;

    public IH(Context context) {
        this.context = context;
    }

    public void todo(Float t, String name, Dialog inDialog, BluetoothLeService bluetoothLeService,
                     String gets, Float Min){
        SendValue sendValue = new SendValue(bluetoothLeService);
        if (t > Min) {
            if (t == 0.0) {
                String out = name + "+" + "0000.0";
                sendValue.send(out);
                inDialog.dismiss();
            } else {
                if (gets.startsWith("-")) {
                    gets = String.valueOf(t);
                    int i = gets.indexOf(".");
                    String num1 = gets.substring(1, gets.indexOf("."));
                    String num2 = gets.substring(gets.indexOf("."), gets.indexOf(".") + 2);
                    StringBuilder set = new StringBuilder("0");
                    if (i != 4) {
                        for (int j = 0; j < (4 - i); j++)
                            set.append("0");
                        String out = name + "-" + set + num1 + num2;
                        sendValue.send(out);
                    } else {
                        String out = name + "-" + "0" + num1 + num2;
                        sendValue.send(out);
                    }
                    inDialog.dismiss();
                } else {
                    gets = String.valueOf(t);
                    int i = gets.indexOf(".");
                    String num1 = gets.substring(0, gets.indexOf("."));
                    String num2 = gets.substring(gets.indexOf("."), gets.indexOf(".") + 2);
                    StringBuilder set = new StringBuilder("0");
                    for (int j = 1; j < (4 - i); j++)
                        set.append("0");
                    String out = name + "+" + set + num1 + num2;
                    sendValue.send(out);
                    inDialog.dismiss();
                }
            }
        } else {
            Toast.makeText(context, context.getString(R.string.MAX), Toast.LENGTH_SHORT).show();
        }
    }
}
