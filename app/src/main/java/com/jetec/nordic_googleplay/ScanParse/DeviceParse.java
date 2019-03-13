package com.jetec.nordic_googleplay.ScanParse;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import android.util.Log;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeviceParse {

    private String TAG = "DeviceParse";
    private RecordParse recordParse = new RecordParse();
    private Map<Integer, List<String>> newList;
    private List<String> list;
    private static final int EBLE_128BitUUIDCom = 0x07;//«Complete List of 128-bit Service Class UUIDs»	Bluetooth Core Specification:
    private static final int EBLE_LOCALNAME = 0x09;//«Complete Local Name»	Bluetooth Core Specification:

    @SuppressLint("UseSparseArrays")
    public DeviceParse() {
        newList = new HashMap<>();
    }

    public Map<Integer, List<String>> regetList(List<BluetoothDevice> devices, List<byte[]> record){
        @SuppressLint("UseSparseArrays")
        Map<Integer, List<String>> setList = new HashMap<>();
        List<BluetoothDevice> newdevicesList = new ArrayList<>();
        List<byte[]> newrecord = new ArrayList<>();
        setList.clear();
        newdevicesList.clear();
        newrecord.clear();
        for (int i = 0; i < devices.size(); i++) {
            list = new ArrayList<>();
            Map<Integer, String> parse = recordParse.ParseRecord(record.get(i));
            String uuid = getServiceUUID(parse);
            Log.d(TAG, "uuid = " + uuid);
            String service_uuid = "6e400001-b5a3-f393-e0a9-e50e24dcca9e";
            if (uuid.matches(service_uuid.toUpperCase())) {
                newdevicesList.add(devices.get(i));
                newrecord.add(record.get(i));
            }
        }

        setList = parseList(newdevicesList, newrecord);

        return setList;
    }

    private Map<Integer, List<String>> parseList(List<BluetoothDevice> devices, List<byte[]> record) {

        newList.clear();
        for (int i = 0; i < devices.size(); i++) {
            list = new ArrayList<>();
            Map<Integer, String> parse = recordParse.ParseRecord(record.get(i));

            if(parse.containsKey(EBLE_LOCALNAME)) {
                String name = parse.get(EBLE_LOCALNAME);
                assert name != null;
                list.add(getname(name));
            }
            else {
                list.add("N/A");
            }
            list.add(devices.get(i).getAddress());
            newList.put(i, list);
        }
        Log.e(TAG,"newList = " + newList);
        return newList;
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

    private String getServiceUUID(Map <Integer,String> record){
        String ret = "";

        if(record.containsKey(EBLE_128BitUUIDCom)){
            String tmpString= record.get(EBLE_128BitUUIDCom);
            assert tmpString != null;
            ret = tmpString.substring(0, 8) + "-" + tmpString.substring(8,12)+ "-" + tmpString.substring(12,16)+ "-" + tmpString.substring(16,20)+ "-" + tmpString.substring(20);
        }

        return ret;
    }
}
