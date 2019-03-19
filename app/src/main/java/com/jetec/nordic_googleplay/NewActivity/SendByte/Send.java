package com.jetec.nordic_googleplay.NewActivity.SendByte;

import com.jetec.nordic_googleplay.Service.BluetoothLeService;

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

    public Send(BluetoothLeService bluetoothLeService) {
        this.bluetoothLeService = bluetoothLeService;
    }


}
