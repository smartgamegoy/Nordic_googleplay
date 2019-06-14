package com.jetec.nordic_googleplay.DialogFunction;

import android.app.Dialog;
import android.util.Log;

import com.jetec.nordic_googleplay.SendValue;
import com.jetec.nordic_googleplay.Service.BluetoothLeService;

public class CR {

    private String TAG = "CR";

    public CR() {
        super();
    }

    public void todo(Float t, String name, Dialog inDialog, BluetoothLeService bluetoothLeService, String gets){
        SendValue sendValue = new SendValue(bluetoothLeService);
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
                String out;
                gets = String.valueOf(t);
                int i = gets.indexOf(".");
                String num1 = gets.substring(0, gets.indexOf("."));
                String num2 = gets.substring(gets.indexOf("."), gets.indexOf(".") + 2);
                StringBuilder set = new StringBuilder("0");
                Log.e(TAG, "i = " + i);
                Log.e(TAG, "num1 = " + num1);
                Log.e(TAG, "num2 = " + num2);
                for (int j = 1; j < (4 - i); j++) {
                    Log.e(TAG, "j = "+ j);
                    set.append("0");
                }
                if(i < 4) {
                    out = name + "+" + set + num1 + num2;
                }
                else {
                    out = name + "+" + num1 + num2;
                }
                sendValue.send(out);
                inDialog.dismiss();
            }
        }
    }
}
