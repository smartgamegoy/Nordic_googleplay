package com.jetec.nordic_googleplay.NewActivity;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;
import android.widget.TextView;
import com.jetec.nordic_googleplay.NewActivity.GetString.ByteToInt;
import com.jetec.nordic_googleplay.NewActivity.Listener.GetCounter;
import com.jetec.nordic_googleplay.NewActivity.SendByte.SendString;
import com.jetec.nordic_googleplay.NewModel;
import com.jetec.nordic_googleplay.R;
import com.jetec.nordic_googleplay.Service.BluetoothLeService;
import com.jetec.nordic_googleplay.Value;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CheckDownload {

    private String TAG = "CheckDownload";
    private Context context;
    private Vibrator vibrator;
    private BluetoothLeService mBluetoothLeService;
    private String date, time;
    private int count, interval, number;
    private List<Integer> listnum, countlist, recordlist;
    private byte[] getnull = {0x4E, 0x55, 0x4C, 0x4C};
    private ByteToInt byteToInt = new ByteToInt();
    private byte[] getwant = {0x57, 0x41, 0x4E, 0x54};
    private GetCounter getCounter;
    private TextView showtext;
    private Dialog dialog;

    public CheckDownload(){
        super();
    }

    public void checklist(Context context, Vibrator vibrator, BluetoothLeService mBluetoothLeService
            , String date, String time, int count, int interval, GetCounter getCounter,
                          TextView showtext, Dialog DownloadprogressDialog){
        listnum = new ArrayList<>();
        countlist = new ArrayList<>();
        recordlist = new ArrayList<>();
        listnum.clear();
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
        this.getCounter = getCounter;
        this.showtext = showtext;
        this.dialog = DownloadprogressDialog;
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
            }
            else {
                byte[] getbyte = NewModel.list09.get(i);
                byte[] data = Arrays.copyOfRange(getbyte, 1, 4);
                int num = byteToInt.byteToInt(data);
                listnum.add(num);
                //Log.e(TAG, "num = " + num);
            }
        }
        Log.e(TAG, "countlist = " + countlist.size());
        Log.e(TAG, "listnum = " + listnum.size());
        for(int i = 0; i < listnum.size(); i++){
            if(!listnum.get(i).equals(countlist.get(number))){
                recordlist.add(countlist.get(number));
                i--;
            }
            number++;
        }
        Log.e(TAG, "countlist = " + countlist.size());
        Log.e(TAG, "recordlist = " + recordlist);
        Log.e(TAG, "recordlist = " + recordlist.size());
        Log.e(TAG, "list08.size = " + NewModel.list08.size());
        Log.e(TAG, "list09.size = " + NewModel.list09.size());
        showtext.setText(context.getString(R.string.process));
        if(NewModel.list08.size() != count && NewModel.list09.size() != count) {
            getlost(0);
        }
        else {
            dialog.dismiss();
            getCounter.readytointent(NewModel.list09.size(), count);
            NewModel.checklost = false;
            Value.opendialog = false;
            Log.e(TAG, "收工");
        }
    }

    private void getlost(int i){
        SendString sendString = new SendString();
        Handler lostHandler = new Handler();
        final int[] lost_i = {i};
        if(i < recordlist.size()) {
            NewModel.checklost = true;
            lostHandler.postDelayed(() -> {
                int lost = recordlist.get(i);
                byte[] lostbyte = byteToInt.intToByteArray(lost);
                byte[] wantbyte = new byte[(getwant.length + lostbyte.length)];
                System.arraycopy(getwant, 0, wantbyte, 0, getwant.length);
                System.arraycopy(lostbyte, 0, wantbyte, getwant.length, lostbyte.length);
                sendString.sendbyte(wantbyte);
                lost_i[0]++;
                getlost(lost_i[0]);
            }, 500);
        }
        else {
            lostHandler.postDelayed(() -> {
                getCounter.readytointent(NewModel.list09.size(), count);
                dialog.dismiss();
                NewModel.checklost = false;
                Value.opendialog = false;
                Log.e(TAG, "收工");
            }, 2000);
        }
    }
}
