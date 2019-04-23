package com.jetec.nordic_googleplay.NewActivity.SendByte;

import android.util.Log;

import com.jetec.nordic_googleplay.NewModel;
import com.jetec.nordic_googleplay.Value;
import java.nio.charset.StandardCharsets;

public class SendString {

    private String TAG = "SendString";

    public SendString(){
        super();
    }

    public void sendstr(String str){
        byte[] sends;
        sends = str.getBytes(StandardCharsets.UTF_8);
        Log.e(TAG, "sends = " + str);
        Log.e(TAG, "sendstr = " + Value.mBluetoothLeService);
        NewModel.mBluetoothLeService.writeRXCharacteristic(sends);
    }


}
