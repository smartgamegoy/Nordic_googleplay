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
