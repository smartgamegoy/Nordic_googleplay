package com.jetec.nordic_googleplay.NewActivity.SendByte;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.jetec.nordic_googleplay.Dialog.WriteDialog;
import com.jetec.nordic_googleplay.NewModel;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class SendString {

    private String TAG = "SendString";
    private android.os.Handler mHandler, sendHandler;
    private WriteDialog writeDialog = new WriteDialog();
    private int listsize;

    public SendString() {
        super();
    }

    public void sendstr(String str) {
        byte[] sends;
        sends = str.getBytes(StandardCharsets.UTF_8);
        NewModel.mBluetoothLeService.writeRXCharacteristic(sends);
    }

    public void sendList(List<byte[]> getList, Context context) {
        writeDialog.set_Dialog(context, false);
        mHandler = new Handler();
        sendHandler = new Handler();
        listsize = getList.size();
        sendstr("ERA");
        mHandler.postDelayed(() -> {
            checklist(getList);
        }, 2000);
    }

    private void checklist(List<byte[]> getList) {
        sendHandler.postDelayed(() -> {
            Log.e(TAG, "listsize = " + listsize);
            if (listsize > 0) {
                sendbyte(getList.get((listsize - 1)));
                checklist(getList);
                listsize--;
            } else {
                if (writeDialog.checkshowing()) {
                    writeDialog.closeDialog();
                }
                //sendHandler.removeCallbacksAndMessages(null);
            }
        }, 120);
    }

    private void sendbyte(byte[] getbyte) {
        StringBuilder hex = new StringBuilder(getbyte.length * 2);
        for (byte aData : getbyte) {
            hex.append(String.format("%02X", aData));
        }
        String gethex = hex.toString();
        Log.e(TAG, "getbyte = " + gethex);
        NewModel.mBluetoothLeService.writeRXCharacteristic(getbyte);
    }
}
