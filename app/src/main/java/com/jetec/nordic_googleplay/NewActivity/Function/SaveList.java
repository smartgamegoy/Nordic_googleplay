package com.jetec.nordic_googleplay.NewActivity.Function;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.jetec.nordic_googleplay.NewActivity.GetString.ByteToHex;
import com.jetec.nordic_googleplay.NewActivity.Listener.GetLog;
import com.jetec.nordic_googleplay.NewActivity.GetString.Parase;
import com.jetec.nordic_googleplay.NewActivity.UserSQL.LogSQL;
import com.jetec.nordic_googleplay.Value;

import org.json.JSONArray;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class SaveList {

    private String TAG = "SaveList";
    private List<byte[]> list1, list2;
    private Parase parase = new Parase();
    private LogSQL logSQL;
    private Context context;

    public SaveList(Context context) {
        this.context = context;
        logSQL = new LogSQL(context);
    }

    public void convertLogdata(String logdate, String logtime, int loginter, GetLog getLog) {
        list1 = new ArrayList<>();
        list2 = new ArrayList<>();
        list1.clear();
        list2.clear();
        BubbleDownload bubbleDownload = new BubbleDownload();
        bubbleDownload.sortList();
        list1 = bubbleDownload.getlist1();
        list2 = bubbleDownload.getlist2();
        //測試counter
        /*List<Integer> t1 = new ArrayList<>();
        List<Integer> t2 = new ArrayList<>();
        t1.clear();
        t2.clear();
        for(int i = 0; i < list1.size(); i++){
            t1.add(getcount(list1.get(i)));
            t2.add(getcount(list2.get(i)));
        }
        Log.e(TAG, "t1 = " + t1);
        Log.e(TAG, "t2 = " + t2);*/
        convert(logdate, logtime, loginter, getLog);
    }

    private int getcount(byte[] getbyte) {
        byte[] data = Arrays.copyOfRange(getbyte, 1, 4);
        return parase.byteArrayToInt(data);
    }

    private void convert(String date, String time, int inter, GetLog getLog) {
        String model = Value.deviceModel;
        String[] arr = model.split("-");
        String name = arr[2];
        name = name.replace("Y", "");
        name = name.replace("L", "");
        name = name.replace("Z", "");
        int namesize = name.length();
        Log.e(TAG, "date = " + date);
        Log.e(TAG, "time = " + time);

        if(logSQL.getCount(name) > 0){
            logSQL.delete(name);
        }
        if(logSQL.getCount() > 0){
            logSQL.deleteAll();
            logSQL = new LogSQL(context);
        }

        List<String> timeList = new ArrayList<>();
        timeList.clear();
        timeList = timelist(date, time, inter);

        List<String> record1, record2, record3, record4, record5, record6;
        record1 = new ArrayList<>();
        record2 = new ArrayList<>();
        record3 = new ArrayList<>();
        record4 = new ArrayList<>();
        record5 = new ArrayList<>();
        record6 = new ArrayList<>();
        record1.clear();
        record2.clear();
        record3.clear();
        record4.clear();
        record5.clear();
        record6.clear();

        DecimalFormat decimalFormat = new DecimalFormat("#.##############");

        for (int i = 0; i < list1.size(); i++) {
            if (namesize <= 3) {
                byte[] getbyte = list1.get(i);
                byte[] data = Arrays.copyOfRange(getbyte, 4, 5);
                if (parase.byteArrayToInt(data) == 5) {
                    byte[] dp = Arrays.copyOfRange(getbyte, 5, 6);
                    byte[] value = Arrays.copyOfRange(getbyte, 6, 10);
                    if (parase.byteArrayToInt(dp) == 0) {
                        int getvalue = parase.byteArrayToInt(value);
                        record1.add(String.valueOf(getvalue));
                    } else {
                        int b = parase.byteArrayToInt(dp) * -1;
                        double point = Math.pow(10, b);
                        int getvalue = parase.byteArrayToInt(value);
                        double d_value = getvalue * point;
                        record1.add(String.valueOf(decimalFormat.format(d_value)));
                    }
                } else if (parase.byteArrayToInt(data) == 10) {
                    byte[] dp1 = Arrays.copyOfRange(getbyte, 5, 6);
                    byte[] dp2 = Arrays.copyOfRange(getbyte, 10, 11);
                    byte[] value = Arrays.copyOfRange(getbyte, 6, 10);
                    byte[] value2 = Arrays.copyOfRange(getbyte, 11, 15);
                    if (parase.byteArrayToInt(dp1) == 0) {
                        int getvalue = parase.byteArrayToInt(value);
                        record1.add(String.valueOf(getvalue));
                    } else {
                        int b = parase.byteArrayToInt(dp1) * -1;
                        double point = Math.pow(10, b);
                        int getvalue = parase.byteArrayToInt(value);
                        double d_value = getvalue * point;
                        record1.add(String.valueOf(decimalFormat.format(d_value)));
                    }
                    if (parase.byteArrayToInt(dp2) == 0) {
                        int getvalue = parase.byteArrayToInt(value2);
                        record2.add(String.valueOf(getvalue));
                    } else {
                        int b = parase.byteArrayToInt(dp2) * -1;
                        double point = Math.pow(10, b);
                        int getvalue = parase.byteArrayToInt(value2);
                        double d_value = getvalue * point;
                        record2.add(String.valueOf(decimalFormat.format(d_value)));
                    }
                } else if (parase.byteArrayToInt(data) == 15) {
                    byte[] dp1 = Arrays.copyOfRange(getbyte, 5, 6);
                    byte[] dp2 = Arrays.copyOfRange(getbyte, 10, 11);
                    byte[] dp3 = Arrays.copyOfRange(getbyte, 15, 16);
                    byte[] value = Arrays.copyOfRange(getbyte, 6, 10);
                    byte[] value2 = Arrays.copyOfRange(getbyte, 11, 15);
                    byte[] value3 = Arrays.copyOfRange(getbyte, 16, getbyte.length);
                    if (parase.byteArrayToInt(dp1) == 0) {
                        int getvalue = parase.byteArrayToInt(value);
                        record1.add(String.valueOf(getvalue));
                    } else {
                        int b = parase.byteArrayToInt(dp1) * -1;
                        double point = Math.pow(10, b);
                        int getvalue = parase.byteArrayToInt(value);
                        double d_value = getvalue * point;
                        record1.add(String.valueOf(decimalFormat.format(d_value)));
                    }
                    if (parase.byteArrayToInt(dp2) == 0) {
                        int getvalue = parase.byteArrayToInt(value2);
                        record2.add(String.valueOf(getvalue));
                    } else {
                        int b = parase.byteArrayToInt(dp2) * -1;
                        double point = Math.pow(10, b);
                        int getvalue = parase.byteArrayToInt(value2);
                        double d_value = getvalue * point;
                        record2.add(String.valueOf(decimalFormat.format(d_value)));
                    }
                    if (parase.byteArrayToInt(dp3) == 0) {
                        int getvalue = parase.byteArrayToInt(value3);
                        record3.add(String.valueOf(getvalue));
                    } else {
                        int b = parase.byteArrayToInt(dp3) * -1;
                        double point = Math.pow(10, b);
                        int getvalue = parase.byteArrayToInt(value3);
                        double d_value = getvalue * point;
                        record3.add(String.valueOf(decimalFormat.format(d_value)));
                    }
                }
            } else {
                byte[] getbyte = list1.get(i);
                byte[] getbyte2 = list2.get(i);
                byte[] data2 = Arrays.copyOfRange(getbyte2, 4, 5);
                if (parase.byteArrayToInt(data2) == 5) {
                    byte[] dp1 = Arrays.copyOfRange(getbyte, 5, 6);
                    byte[] dp2 = Arrays.copyOfRange(getbyte, 10, 11);
                    byte[] dp3 = Arrays.copyOfRange(getbyte, 15, 16);
                    byte[] dp4 = Arrays.copyOfRange(getbyte2, 5, 6);
                    byte[] value = Arrays.copyOfRange(getbyte, 6, 10);
                    byte[] value2 = Arrays.copyOfRange(getbyte, 11, 15);
                    byte[] value3 = Arrays.copyOfRange(getbyte, 16, getbyte.length);
                    byte[] value4 = Arrays.copyOfRange(getbyte2, 6, 10);
                    if (parase.byteArrayToInt(dp1) == 0) {
                        int getvalue = parase.byteArrayToInt(value);
                        record1.add(String.valueOf(getvalue));
                    } else {
                        int b = parase.byteArrayToInt(dp1) * -1;
                        double point = Math.pow(10, b);
                        int getvalue = parase.byteArrayToInt(value);
                        double d_value = getvalue * point;
                        record1.add(String.valueOf(decimalFormat.format(d_value)));
                    }
                    if (parase.byteArrayToInt(dp2) == 0) {
                        int getvalue = parase.byteArrayToInt(value2);
                        record2.add(String.valueOf(getvalue));
                    } else {
                        int b = parase.byteArrayToInt(dp2) * -1;
                        double point = Math.pow(10, b);
                        int getvalue = parase.byteArrayToInt(value2);
                        double d_value = getvalue * point;
                        record2.add(String.valueOf(decimalFormat.format(d_value)));
                    }
                    if (parase.byteArrayToInt(dp3) == 0) {
                        int getvalue = parase.byteArrayToInt(value3);
                        record3.add(String.valueOf(getvalue));
                    } else {
                        int b = parase.byteArrayToInt(dp3) * -1;
                        double point = Math.pow(10, b);
                        int getvalue = parase.byteArrayToInt(value3);
                        double d_value = getvalue * point;
                        record3.add(String.valueOf(decimalFormat.format(d_value)));
                    }
                    if (parase.byteArrayToInt(dp4) == 0) {
                        int getvalue = parase.byteArrayToInt(value4);
                        record4.add(String.valueOf(getvalue));
                    } else {
                        int b = parase.byteArrayToInt(dp4) * -1;
                        double point = Math.pow(10, b);
                        int getvalue = parase.byteArrayToInt(value4);
                        double d_value = getvalue * point;
                        record4.add(String.valueOf(decimalFormat.format(d_value)));
                    }
                } else if (parase.byteArrayToInt(data2) == 10) {
                    byte[] dp1 = Arrays.copyOfRange(getbyte, 5, 6);
                    byte[] dp2 = Arrays.copyOfRange(getbyte, 10, 11);
                    byte[] dp3 = Arrays.copyOfRange(getbyte, 15, 16);
                    byte[] dp4 = Arrays.copyOfRange(getbyte2, 5, 6);
                    byte[] dp5 = Arrays.copyOfRange(getbyte2, 10, 11);
                    byte[] value = Arrays.copyOfRange(getbyte, 6, 10);
                    byte[] value2 = Arrays.copyOfRange(getbyte, 11, 15);
                    byte[] value3 = Arrays.copyOfRange(getbyte, 16, getbyte.length);
                    byte[] value4 = Arrays.copyOfRange(getbyte2, 6, 10);
                    byte[] value5 = Arrays.copyOfRange(getbyte2, 11, 15);
                    if (parase.byteArrayToInt(dp1) == 0) {
                        int getvalue = parase.byteArrayToInt(value);
                        record1.add(String.valueOf(getvalue));
                    } else {
                        int b = parase.byteArrayToInt(dp1) * -1;
                        double point = Math.pow(10, b);
                        int getvalue = parase.byteArrayToInt(value);
                        double d_value = getvalue * point;
                        record1.add(String.valueOf(decimalFormat.format(d_value)));
                    }
                    if (parase.byteArrayToInt(dp2) == 0) {
                        int getvalue = parase.byteArrayToInt(value2);
                        record2.add(String.valueOf(getvalue));
                    } else {
                        int b = parase.byteArrayToInt(dp2) * -1;
                        double point = Math.pow(10, b);
                        int getvalue = parase.byteArrayToInt(value2);
                        double d_value = getvalue * point;
                        record2.add(String.valueOf(decimalFormat.format(d_value)));
                    }
                    if (parase.byteArrayToInt(dp3) == 0) {
                        int getvalue = parase.byteArrayToInt(value3);
                        record3.add(String.valueOf(getvalue));
                    } else {
                        int b = parase.byteArrayToInt(dp3) * -1;
                        double point = Math.pow(10, b);
                        int getvalue = parase.byteArrayToInt(value3);
                        double d_value = getvalue * point;
                        record3.add(String.valueOf(decimalFormat.format(d_value)));
                    }
                    if (parase.byteArrayToInt(dp4) == 0) {
                        int getvalue = parase.byteArrayToInt(value4);
                        record4.add(String.valueOf(getvalue));
                    } else {
                        int b = parase.byteArrayToInt(dp4) * -1;
                        double point = Math.pow(10, b);
                        int getvalue = parase.byteArrayToInt(value4);
                        double d_value = getvalue * point;
                        record4.add(String.valueOf(decimalFormat.format(d_value)));
                    }
                    if (parase.byteArrayToInt(dp5) == 0) {
                        int getvalue = parase.byteArrayToInt(value5);
                        record5.add(String.valueOf(getvalue));
                    } else {
                        int b = parase.byteArrayToInt(dp5) * -1;
                        double point = Math.pow(10, b);
                        int getvalue = parase.byteArrayToInt(value5);
                        double d_value = getvalue * point;
                        record5.add(String.valueOf(decimalFormat.format(d_value)));
                    }
                } else if (parase.byteArrayToInt(data2) == 15) {
                    byte[] dp1 = Arrays.copyOfRange(getbyte, 5, 6);
                    byte[] dp2 = Arrays.copyOfRange(getbyte, 10, 11);
                    byte[] dp3 = Arrays.copyOfRange(getbyte, 15, 16);
                    byte[] dp4 = Arrays.copyOfRange(getbyte2, 5, 6);
                    byte[] dp5 = Arrays.copyOfRange(getbyte2, 10, 11);
                    byte[] dp6 = Arrays.copyOfRange(getbyte, 15, 16);
                    byte[] value = Arrays.copyOfRange(getbyte, 6, 10);
                    byte[] value2 = Arrays.copyOfRange(getbyte, 11, 15);
                    byte[] value3 = Arrays.copyOfRange(getbyte, 16, getbyte.length);
                    byte[] value4 = Arrays.copyOfRange(getbyte2, 6, 10);
                    byte[] value5 = Arrays.copyOfRange(getbyte2, 11, 15);
                    byte[] value6 = Arrays.copyOfRange(getbyte2, 16, getbyte2.length);
                    if (parase.byteArrayToInt(dp1) == 0) {
                        int getvalue = parase.byteArrayToInt(value);
                        record1.add(String.valueOf(getvalue));
                    } else {
                        int b = parase.byteArrayToInt(dp1) * -1;
                        double point = Math.pow(10, b);
                        int getvalue = parase.byteArrayToInt(value);
                        double d_value = getvalue * point;
                        record1.add(String.valueOf(decimalFormat.format(d_value)));
                    }
                    if (parase.byteArrayToInt(dp2) == 0) {
                        int getvalue = parase.byteArrayToInt(value2);
                        record2.add(String.valueOf(getvalue));
                    } else {
                        int b = parase.byteArrayToInt(dp2) * -1;
                        double point = Math.pow(10, b);
                        int getvalue = parase.byteArrayToInt(value2);
                        double d_value = getvalue * point;
                        record2.add(String.valueOf(decimalFormat.format(d_value)));
                    }
                    if (parase.byteArrayToInt(dp3) == 0) {
                        int getvalue = parase.byteArrayToInt(value3);
                        record3.add(String.valueOf(getvalue));
                    } else {
                        int b = parase.byteArrayToInt(dp3) * -1;
                        double point = Math.pow(10, b);
                        int getvalue = parase.byteArrayToInt(value3);
                        double d_value = getvalue * point;
                        record3.add(String.valueOf(decimalFormat.format(d_value)));
                    }
                    if (parase.byteArrayToInt(dp4) == 0) {
                        int getvalue = parase.byteArrayToInt(value4);
                        record4.add(String.valueOf(getvalue));
                    } else {
                        int b = parase.byteArrayToInt(dp4) * -1;
                        double point = Math.pow(10, b);
                        int getvalue = parase.byteArrayToInt(value4);
                        double d_value = getvalue * point;
                        record4.add(String.valueOf(decimalFormat.format(d_value)));
                    }
                    if (parase.byteArrayToInt(dp5) == 0) {
                        int getvalue = parase.byteArrayToInt(value5);
                        record5.add(String.valueOf(getvalue));
                    } else {
                        int b = parase.byteArrayToInt(dp5) * -1;
                        double point = Math.pow(10, b);
                        int getvalue = parase.byteArrayToInt(value5);
                        double d_value = getvalue * point;
                        record5.add(String.valueOf(decimalFormat.format(d_value)));
                    }
                    if (parase.byteArrayToInt(dp6) == 0) {
                        int getvalue = parase.byteArrayToInt(value6);
                        record6.add(String.valueOf(getvalue));
                    } else {
                        int b = parase.byteArrayToInt(dp6) * -1;
                        double point = Math.pow(10, b);
                        int getvalue = parase.byteArrayToInt(value6);
                        double d_value = getvalue * point;
                        record6.add(String.valueOf(decimalFormat.format(d_value)));
                    }
                }
            }
        }

        List<List<String>> saveList = new ArrayList<>();
        saveList.clear();

        if (record1.size() > 0) {
            Log.e(TAG, "record1 = " + record1);
            saveList.add(record1);
        }
        if (record2.size() > 0) {
            Log.e(TAG, "record2 = " + record2);
            saveList.add(record2);
        }
        if (record3.size() > 0) {
            Log.e(TAG, "record3 = " + record3);
            saveList.add(record3);
        }
        if (record4.size() > 0) {
            Log.e(TAG, "record4 = " + record4);
            saveList.add(record4);
        }
        if (record5.size() > 0) {
            Log.e(TAG, "record5 = " + record5);
            saveList.add(record5);
        }
        if (record6.size() > 0) {
            Log.e(TAG, "record6 = " + record6);
            saveList.add(record6);
        }

        JSONArray timeJson = new JSONArray(timeList);
        JSONArray saveJson = new JSONArray(saveList);

        String devicename = Value.BName;
        logSQL.insert(timeJson, saveJson, name, devicename);
        getLog.readytointent();
        logSQL.close();
    }

    private List<String> timelist(String date, String time, int inter) {
        ByteToHex byteToHex = new ByteToHex();
        List<String> timelist = new ArrayList<>();
        timelist.clear();
        try {
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat log_date = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
            date = date.substring(0, 2) + "-" + date.substring(2, 4) + "-" + date.substring(4, 6);
            time = time.substring(0, 2) + ":" + time.substring(2, 4) + ":" + time.substring(4, 6);
            String all_date = date + " " + time;

            for(int i = 0; i < list2.size(); i++){
                Date setdate = log_date.parse(all_date);
                byte[] data = list1.get(i);
                byte[] data2 = list2.get(i);
                String[] arr = byteToHex.hexstring(data);
                String[] arr2 = byteToHex.hexstring(data2);
                StringBuilder str = new StringBuilder();
                StringBuilder str2 = new StringBuilder();
                for (String s : arr) {
                    str.append(s).append(" ");
                }
                for (String s : arr2) {
                    str2.append(s).append(" ");
                }
                Log.e(TAG, "08 = " + str);
                Log.e(TAG, "09 = " + str2);
                byte[] count = Arrays.copyOfRange(data, 1, 4);
                int timer = parase.byteArrayToInt(count);
                Log.e(TAG, "timer = " + timer);
                setdate.setTime(setdate.getTime() + (inter * (timer - 1) * 1000));
                String formattime = log_date.format(setdate);
                timelist.add(formattime);
            }
            Log.e(TAG, "timelist = " + timelist);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timelist;
    }
}
