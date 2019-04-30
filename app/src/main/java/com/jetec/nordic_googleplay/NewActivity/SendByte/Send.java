package com.jetec.nordic_googleplay.NewActivity.SendByte;

import android.os.Handler;
import android.util.Log;

import com.jetec.nordic_googleplay.NewModel;
import com.jetec.nordic_googleplay.Service.BluetoothLeService;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import static java.lang.Thread.sleep;

public class Send {
    /*
            PV 1
            EH 2
            EL 3
            IH 4
            IL 5
            CR 6
            ADR 7
            Register 8
            length 9
            RL1 10
            RL2 11
            RL3 12
            */
    private BluetoothLeService bluetoothLeService;
    private String TAG = "Send";
    private byte[] PV1 = {0x01, 0x01};
    private byte[] PV2 = {0x02, 0x01};
    private byte[] PV3 = {0x03, 0x01};
    private byte[] PV4 = {0x04, 0x01};
    private byte[] PV5 = {0x05, 0x01};
    private byte[] PV6 = {0x06, 0x01};
    private byte[] EH1 = {0x01, 0x02};
    private byte[] EH2 = {0x02, 0x02};
    private byte[] EH3 = {0x03, 0x02};
    private byte[] EH4 = {0x04, 0x02};
    private byte[] EH5 = {0x05, 0x02};
    private byte[] EH6 = {0x06, 0x02};
    private byte[] EL1 = {0x01, 0x03};
    private byte[] EL2 = {0x02, 0x03};
    private byte[] EL3 = {0x03, 0x03};
    private byte[] EL4 = {0x04, 0x03};
    private byte[] EL5 = {0x05, 0x03};
    private byte[] EL6 = {0x06, 0x03};
    private byte[] IH1 = {0x01, 0x04};
    private byte[] IH2 = {0x02, 0x04};
    private byte[] IH3 = {0x03, 0x04};
    private byte[] IH4 = {0x04, 0x04};
    private byte[] IH5 = {0x05, 0x04};
    private byte[] IH6 = {0x06, 0x04};
    private byte[] IL1 = {0x01, 0x05};
    private byte[] IL2 = {0x02, 0x05};
    private byte[] IL3 = {0x03, 0x05};
    private byte[] IL4 = {0x04, 0x05};
    private byte[] IL5 = {0x05, 0x05};
    private byte[] IL6 = {0x06, 0x05};
    private byte[] CR1 = {0x01, 0x06};
    private byte[] CR2 = {0x02, 0x06};
    private byte[] CR3 = {0x03, 0x06};
    private byte[] CR4 = {0x04, 0x06};
    private byte[] CR5 = {0x05, 0x06};
    private byte[] CR6 = {0x06, 0x06};
    private byte[] ADR = {0x06, 0x07};
    private byte[] RL1 = {0x01, 0x0A};
    private byte[] RL2 = {0x02, 0x0B};
    private byte[] RL3 = {0x03, 0x0C};
    private byte[] DPP = {0x00, 0x01};
    private int listsize;
    private Handler sendHandler;

    public Send(BluetoothLeService bluetoothLeService) {
        this.bluetoothLeService = bluetoothLeService;
    }

    public void sendinitial(String str) {
        try {
            Log.e("set", "str = " + str);
            String[] arr = str.split("\\+");
            byte[] value = arr(arr[0], arr[1]);
            byte[] data = Arrays.copyOfRange(value, 3, value.length);
            Log.d("send", "sendbyte = " + "[" + value[0] + "," + value[1] + "," + value[2] + "," + byteArrayToInt(data) + "]");
            sleep(100);
            bluetoothLeService.writeRXCharacteristic(value);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void sendByte(String str){
        String getstr = str.replace(" ","");
        byte[] value = bytes(getstr);
        Log.w(TAG, "str = " + str);
        bluetoothLeService.writeRXCharacteristic(value);
    }

    public void sendString(String str){
        byte[] sends;
        sends = str.getBytes(StandardCharsets.UTF_8);
        Log.e(TAG, "str = " + str);
        bluetoothLeService.writeRXCharacteristic(sends);
    }

    public void sendlist(List<byte[]> getList){
        sendHandler = new Handler();
        listsize = getList.size();
        snedthislist(getList);
    }

    private void snedthislist(List<byte[]> getList) {
        sendHandler.postDelayed(() -> {
            Log.e(TAG, "listsize = " + listsize);
            if (listsize > 0) {
                sendbyte(getList.get((listsize - 1)));
                snedthislist(getList);
                listsize--;
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

    private byte[] bytes(String getnum){
        String str = "0123456789ABCDEF";
        char[] hexs = getnum.toCharArray();
        byte[] bytes = new byte[getnum.length() / 2];
        int n;

        for (int i = 0; i < bytes.length; i++) {
            n = str.indexOf(hexs[2 * i]) * 16;
            n += str.indexOf(hexs[2 * i + 1]);
            bytes[i] = (byte) (n & 0xff);
        }

        return bytes;
    }

    private byte[] arr(String str1, String str2) {
        byte[] arr = new byte[7], arr2;
        switch (str1) {
            case "PV1":
                arr[0] = PV1[0];
                arr[1] = PV1[1];
                break;
            case "PV2":
                arr[0] = PV2[0];
                arr[1] = PV2[1];
                break;
            case "PV3":
                arr[0] = PV3[0];
                arr[1] = PV3[1];
                break;
            case "PV4":
                arr[0] = PV4[0];
                arr[1] = PV4[1];
                break;
            case "PV5":
                arr[0] = PV5[0];
                arr[1] = PV5[1];
                break;
            case "PV6":
                arr[0] = PV6[0];
                arr[1] = PV6[1];
                break;
            case "EH1":
                arr[0] = EH1[0];
                arr[1] = EH1[1];
                break;
            case "EH2":
                arr[0] = EH2[0];
                arr[1] = EH2[1];
                break;
            case "EH3":
                arr[0] = EH3[0];
                arr[1] = EH3[1];
                break;
            case "EH4":
                arr[0] = EH4[0];
                arr[1] = EH4[1];
                break;
            case "EH5":
                arr[0] = EH5[0];
                arr[1] = EH5[1];
                break;
            case "EH6":
                arr[0] = EH6[0];
                arr[1] = EH6[1];
                break;
            case "EL1":
                arr[0] = EL1[0];
                arr[1] = EL1[1];
                break;
            case "EL2":
                arr[0] = EL2[0];
                arr[1] = EL2[1];
                break;
            case "EL3":
                arr[0] = EL3[0];
                arr[1] = EL3[1];
                break;
            case "EL4":
                arr[0] = EL4[0];
                arr[1] = EL4[1];
                break;
            case "EL5":
                arr[0] = EL5[0];
                arr[1] = EL5[1];
                break;
            case "EL6":
                arr[0] = EL6[0];
                arr[1] = EL6[1];
                break;
            case "IH1":
                arr[0] = IH1[0];
                arr[1] = IH1[1];
                break;
            case "IH2":
                arr[0] = IH2[0];
                arr[1] = IH2[1];
                break;
            case "IH3":
                arr[0] = IH3[0];
                arr[1] = IH3[1];
                break;
            case "IH4":
                arr[0] = IH4[0];
                arr[1] = IH4[1];
                break;
            case "IH5":
                arr[0] = IH5[0];
                arr[1] = IH5[1];
                break;
            case "IH6":
                arr[0] = IH6[0];
                arr[1] = IH6[1];
                break;
            case "IL1":
                arr[0] = IL1[0];
                arr[1] = IL1[1];
                break;
            case "IL2":
                arr[0] = IL2[0];
                arr[1] = IL2[1];
                break;
            case "IL3":
                arr[0] = IL3[0];
                arr[1] = IL3[1];
                break;
            case "IL4":
                arr[0] = IL4[0];
                arr[1] = IL4[1];
                break;
            case "IL5":
                arr[0] = IL5[0];
                arr[1] = IL5[1];
                break;
            case "IL6":
                arr[0] = IL6[0];
                arr[1] = IL6[1];
                break;
            case "CR1":
                arr[0] = CR1[0];
                arr[1] = CR1[1];
                break;
            case "CR2":
                arr[0] = CR2[0];
                arr[1] = CR2[1];
                break;
            case "CR3":
                arr[0] = CR3[0];
                arr[1] = CR3[1];
                break;
            case "CR4":
                arr[0] = CR4[0];
                arr[1] = CR4[1];
                break;
            case "CR5":
                arr[0] = CR5[0];
                arr[1] = CR5[1];
                break;
            case "CR6":
                arr[0] = CR6[0];
                arr[1] = CR6[1];
                break;
            case "RL11":
                arr[0] = 0x01;
                arr[1] = RL1[1];
                break;
            case "RL21":
                arr[0] = 0x01;
                arr[1] = RL2[1];
                break;
            case "RL31":
                arr[0] = 0x01;
                arr[1] = RL3[1];
                break;
            case "RL12":
                arr[0] = 0x02;
                arr[1] = RL1[1];
                break;
            case "RL22":
                arr[0] = 0x02;
                arr[1] = RL2[1];
                break;
            case "RL32":
                arr[0] = 0x02;
                arr[1] = RL3[1];
                break;
            case "RL13":
                arr[0] = 0x03;
                arr[1] = RL1[1];
                break;
            case "RL23":
                arr[0] = 0x03;
                arr[1] = RL2[1];
                break;
            case "RL33":
                arr[0] = 0x03;
                arr[1] = RL3[1];
                break;
            case "RL14":
                arr[0] = 0x04;
                arr[1] = RL1[1];
                break;
            case "RL24":
                arr[0] = 0x04;
                arr[1] = RL2[1];
                break;
            case "RL34":
                arr[0] = 0x04;
                arr[1] = RL3[1];
                break;
            case "RL15":
                arr[0] = 0x05;
                arr[1] = RL1[1];
                break;
            case "RL25":
                arr[0] = 0x05;
                arr[1] = RL2[1];
                break;
            case "RL35":
                arr[0] = 0x05;
                arr[1] = RL3[1];
                break;
            case "RL16":
                arr[0] = 0x06;
                arr[1] = RL1[1];
                break;
            case "RL26":
                arr[0] = 0x06;
                arr[1] = RL2[1];
                break;
            case "RL36":
                arr[0] = 0x06;
                arr[1] = RL3[1];
                break;
            case "OT1":
                arr[0] = 0x07;
                arr[1] = 0x0D;
                break;
            case "OT2":
                arr[0] = 0x07;
                arr[1] = 0x0E;
                break;
            case "OT3":
                arr[0] = 0x07;
                arr[1] = 0x0F;
                break;
        }
        arr[2] = DPP[0];
        arr2 = intToByteArray(Integer.valueOf(str2));
        arr[3] = arr2[0];
        arr[4] = arr2[1];
        arr[5] = arr2[2];
        arr[6] = arr2[3];
        return arr;
    }

    private byte[] intToByteArray(int a) {
        byte[] ret = new byte[4];
        ret[3] = (byte) (a & 0xFF);
        ret[2] = (byte) ((a >> 8) & 0xFF);
        ret[1] = (byte) ((a >> 16) & 0xFF);
        ret[0] = (byte) ((a >> 24) & 0xFF);
        return ret;
    }

    private String getname(String getname){
        String str = "0123456789ABCDEF";
        char[] hexs = getname.toCharArray();
        byte[] bytes = new byte[getname.length() / 2];
        int n;

        for (int i = 0; i < bytes.length; i++) {
            n = str.indexOf(hexs[2 * i]) * 16;
            n += str.indexOf(hexs[2 * i + 1]);
            bytes[i] = (byte) (n & 0xff);
        }

        return new String(bytes);
    }

    private int byteArrayToInt(byte[] b) {
        if (b.length == 4)
            return b[0] << 24 | (b[1] & 0xff) << 16 | (b[2] & 0xff) << 8
                    | (b[3] & 0xff);
        else if (b.length == 2)
            return (b[0] & 0xff) << 8 | (b[1] & 0xff);
        return 0;
    }
}
