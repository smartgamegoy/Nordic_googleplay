package com.jetec.nordic_googleplay.NewActivity.SendByte;

import com.jetec.nordic_googleplay.NewModel;
import java.nio.charset.StandardCharsets;

public class SendString {

    private String TAG = "SendString";

    public SendString(){
        super();
    }

    public void sendstr(String str){
        byte[] sends;
        sends = str.getBytes(StandardCharsets.UTF_8);
        NewModel.mBluetoothLeService.writeRXCharacteristic(sends);
    }


}
