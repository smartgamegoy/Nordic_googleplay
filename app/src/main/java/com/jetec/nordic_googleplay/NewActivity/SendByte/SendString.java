package com.jetec.nordic_googleplay.NewActivity.SendByte;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import com.jetec.nordic_googleplay.Dialog.WriteDialog;
import com.jetec.nordic_googleplay.NewActivity.GetString.ByteToInt;
import com.jetec.nordic_googleplay.NewActivity.Parase;
import com.jetec.nordic_googleplay.NewModel;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class SendString {

    private String TAG = "SendString";
    private android.os.Handler mHandler, sendHandler;
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
        WriteDialog writeDialog = new WriteDialog();
        writeDialog.set_Dialog(context, false);
        Parase parase = new Parase();
        mHandler = new Handler();
        sendHandler = new Handler();
        listsize = getList.size();
        byte[] row = getList.get(0);
        byte[] data = Arrays.copyOfRange(row, 0, 1);
        int getrow = parase.byteArrayToInt(data);
        sendstr("ERA" + getrow);
        mHandler.postDelayed(() -> {
            checklist(getList, writeDialog);
        }, 1500);
    }

    private void checklist(List<byte[]> getList, WriteDialog writeDialog) {
        sendHandler.postDelayed(() -> {
            Log.e(TAG, "listsize = " + listsize);
            if (listsize > 0) {
                sendbyte(getList.get((listsize - 1)));
                checklist(getList, writeDialog);
                listsize--;
            } else {
                if (writeDialog.checkshowing()) {
                    writeDialog.closeDialog();
                }
                //sendHandler.removeCallbacksAndMessages(null);
            }
        }, 100);
    }

    public void sendbyte(byte[] getbyte) {
        StringBuilder hex = new StringBuilder(getbyte.length * 2);
        for (byte aData : getbyte) {
            hex.append(String.format("%02X", aData));
        }
        String gethex = hex.toString();
        Log.e(TAG, "getbyte = " + gethex);
        NewModel.mBluetoothLeService.writeRXCharacteristic(getbyte);
    }
}
