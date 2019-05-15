package com.jetec.nordic_googleplay.NewActivity.Listener;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.jetec.nordic_googleplay.NewActivity.Function.BubbleDownload;
import com.jetec.nordic_googleplay.NewActivity.GetString.ByteToInt;
import com.jetec.nordic_googleplay.NewModel;
import com.jetec.nordic_googleplay.R;
import com.jetec.nordic_googleplay.Service.BluetoothLeService;
import com.jetec.nordic_googleplay.Value;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CheckDownload implements CheckListener {

    private String TAG = "CheckDownload";
    private WantListener wantListener;
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
    private boolean listen;
    private Handler wanthandler;
    private GetCheck getCheck = new GetCheck();

    public CheckDownload() {
        super();
    }

    public void checklist(Context context, Vibrator vibrator, BluetoothLeService mBluetoothLeService
            , String date, String time, int count, int interval, GetCounter getCounter,
                          TextView showtext, Dialog DownloadprogressDialog) {
        wanthandler = new Handler();
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
        getCheck.setListener(this);
        recheck();
    }

    private void recheck() {
        new Thread(sortList).start();
        showtext.setText(context.getString(R.string.process));
    }

    public void noCount(Dialog DownloadprogressDialog, Context context){
        Toast.makeText(context, context.getString(R.string.logdata), Toast.LENGTH_SHORT).show();
        DownloadprogressDialog.dismiss();
    }

    public void setWantListener(WantListener mWantListener) {
        wantListener = mWantListener;
    }

    public void checkwant() {
        if (wantListener != null) {
            wantListener.checkwant(recordlist);
        }
    }

    public void removerecordlist(boolean rewant){
        if(rewant){
            listen = true;
            Log.e(TAG, "停止重發");
            wanthandler.removeCallbacksAndMessages(null);
            recordlist.remove(0);
            if(recordlist.size() > 0){
                Log.e(TAG, "發下一筆");
                Log.e(TAG, "recordlist = " + recordlist);
                countlist.clear();
                listnum.clear();
                checkwant();
            }
            else {
                NewModel.checkwant = false;
                recheck();
            }
        }
        else {
            Log.e(TAG, "準備重發");
            wanthandler.postDelayed(() -> {
                if(!listen)
                    checkwant();
            }, 300);
        }
    }

    @Override
    public void sorting() {
        new Thread(reSortlist).start();
    }

    @Override
    public void keepWork() {
        if (NewModel.list08.size() != count || NewModel.list09.size() != count || listnum.size() != count) {
            NewModel.checkwant = true;
            checkwant();
            //getlost(0);
        } else {
            dialog.dismiss();
            getCounter.readytointent(NewModel.list09.size(), count);
            NewModel.checklost = false;
            Value.opendialog = false;
            Log.e(TAG, "list08.size = " + NewModel.list08.size());
            Log.e(TAG, "list09.size = " + NewModel.list09.size());
            Log.e(TAG, "收工");
        }
    }

    private Runnable reSortlist = new Runnable() {
        @Override
        public void run() {
            for (int i = 0; i < listnum.size(); i++) {
                if (number < countlist.size()) {
                    if (!listnum.get(i).equals(countlist.get(number))) {
                        recordlist.add(countlist.get(number));
                        i--;
                    }
                    number++;
                } else {
                    break;
                }
            }
            getCheck.keeptoNext();
            /*Log.e(TAG, "countlist = " + countlist.size());
            Log.e(TAG, "recordlist = " + recordlist);
            Log.e(TAG, "recordlist = " + recordlist.size());*/

        }
    };

    private Runnable sortList = new Runnable() {
        @Override
        public void run() {
            BubbleDownload bubbleDownload = new BubbleDownload();
            bubbleDownload.sortList();
            for (int i = 0; i < NewModel.list09.size(); i++) {
                if (Arrays.equals(NewModel.list09.get(i), getnull) || Arrays.equals(NewModel.list09.get(i), getnull)) {
                    //noinspection SuspiciousListRemoveInLoop
                    NewModel.list09.remove(i);
                    //noinspection SuspiciousListRemoveInLoop
                    NewModel.list08.remove(i);
                } else {
                    byte[] getbyte = NewModel.list09.get(i);
                    byte[] data = Arrays.copyOfRange(getbyte, 1, 4);
                    int num = byteToInt.byteToInt(data);
                    listnum.add(num);
                    //Log.e(TAG, "num = " + num);
                }
            }
            for (int i = 1; i <= count; i++) {
                countlist.add(i);
            }
            Log.e(TAG, "list08.size = " + NewModel.list08.size());
            Log.e(TAG, "list09.size = " + NewModel.list09.size());
            getCheck.readytoNext();
        }
    };
}
