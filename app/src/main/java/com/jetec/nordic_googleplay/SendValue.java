package com.jetec.nordic_googleplay;

import android.util.Log;

import com.jetec.nordic_googleplay.Service.BluetoothLeService;
import java.nio.charset.StandardCharsets;
import static java.lang.Thread.sleep;

public class SendValue {

    private BluetoothLeService bluetoothLeService;
    private String TAG = "SendValue";
    private String log = "END";

    public SendValue(BluetoothLeService bluetoothLeService) {
        this.bluetoothLeService = bluetoothLeService;
    }

    public void send(String str) {
        byte[] sends;
        if (Value.downlog) {
            try {
                Value.downlog = false;
                sends = log.getBytes(StandardCharsets.UTF_8);
                if(str.contains("NAME")) {
                    if (sends.length != 20) {
                        byte[] check = new byte[20];
                        for (int i = 0; i < 20; i++) {
                            if (i < sends.length) {
                                check[i] = sends[i];
                            } else {
                                check[i] = 0x00;
                            }
                        }
                        sends = check;
                        StringBuilder hex = new StringBuilder(sends.length * 2);
                        for (byte aData : sends) {
                            hex.append(String.format("%02X", aData));
                        }
                        String gethex = hex.toString();
                        Log.e(TAG, "check = " + gethex);
                    }
                }
                bluetoothLeService.writeRXCharacteristic(sends);
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        sends = str.getBytes(StandardCharsets.UTF_8);
        Log.e(TAG, "sends = " + str);
        bluetoothLeService.writeRXCharacteristic(sends);
    }
}
