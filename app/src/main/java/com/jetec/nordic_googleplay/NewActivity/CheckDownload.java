package com.jetec.nordic_googleplay.NewActivity;

import android.content.Context;
import android.os.Vibrator;
import android.util.Log;

import com.jetec.nordic_googleplay.NewActivity.GetString.ByteToInt;
import com.jetec.nordic_googleplay.NewModel;
import com.jetec.nordic_googleplay.Service.BluetoothLeService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CheckDownload {

    private String TAG = "CheckDownload";
    private Context context;
    private Vibrator vibrator;
    private BluetoothLeService mBluetoothLeService;
    private String date, time;
    private int count, interval, number;
    private int[] listnum;
    private List<Integer> countlist, recordlist;
    private byte[] getnull = {0x4E, 0x55, 0x4C, 0x4C};
    private ByteToInt byteToInt = new ByteToInt();

    public CheckDownload(){
        super();
    }

    public void checklist(Context context, Vibrator vibrator, BluetoothLeService mBluetoothLeService
            , String date, String time, int count, int interval){
        listnum = new int[count];
        countlist = new ArrayList<>();
        recordlist = new ArrayList<>();
        countlist.clear();
        recordlist.clear();
        number = 0;
        this.context = context;
        this.vibrator = vibrator;
        this.mBluetoothLeService = mBluetoothLeService;
        this.date = date;
        this.time = time;
        this.count = count;
        this.interval = interval;

        recheck();
    }

    private void recheck(){
        for(int i = count; i > 0; i--){
            countlist.add(i);
        }
        for(int i = 0; i < NewModel.list09.size(); i++){
            if(Arrays.equals(NewModel.list09.get(i), getnull) || Arrays.equals(NewModel.list09.get(i), getnull)){
                //noinspection SuspiciousListRemoveInLoop
                NewModel.list09.remove(i);
                //noinspection SuspiciousListRemoveInLoop
                NewModel.list08.remove(i);
                listnum[i] = 0;
            }
            else {
                byte[] getbyte = NewModel.list09.get(i);
                byte[] data = Arrays.copyOfRange(getbyte, 1, 4);
                int num = byteToInt.byteToInt(data);
                listnum[i] = num;
                //Log.e(TAG, "num = " + num);
            }
        }
        Log.e(TAG, "listnum = " + listnum.length);
        Log.e(TAG, "countlist = " + countlist.size());
        for(int i = 0; i < listnum.length; i++){
            Log.e(TAG, "i & num = " + i + " & " + number);
            if(listnum[i] != countlist.get(number)){

                Log.e(TAG, "跑進來" + listnum[i] + " / " + countlist.get(number));
                recordlist.add(countlist.get(number));
                i--;
            }
            number++;
        }

        Log.e(TAG, "recordlist = " + recordlist);
        Log.e(TAG, "list08.size = " + NewModel.list08.size());
        Log.e(TAG, "list09.size = " + NewModel.list09.size());
    }


}
