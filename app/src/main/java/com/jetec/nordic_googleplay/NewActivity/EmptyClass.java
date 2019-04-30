package com.jetec.nordic_googleplay.NewActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;

import com.itextpdf.text.pdf.PRIndirectReference;
import com.jetec.nordic_googleplay.Dialog.WriteDialog;
import com.jetec.nordic_googleplay.NewActivity.GetString.ByteToHex;
import com.jetec.nordic_googleplay.NewActivity.SendByte.Send;
import com.jetec.nordic_googleplay.NewActivity.UserSQL.ConvertList;
import com.jetec.nordic_googleplay.NewModel;
import com.jetec.nordic_googleplay.R;
import com.jetec.nordic_googleplay.Service.BluetoothLeService;
import com.jetec.nordic_googleplay.Value;

import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EmptyClass extends AppCompatActivity {

    private String TAG = "EmptyClass";
    private BluetoothAdapter mBluetoothAdapter;
    private String[] default_model;
    private BluetoothLeService mBluetoothLeService;
    private boolean s_connect = false;
    private Intent intents;
    private Vibrator vibrator;
    private ByteToHex byteToHex = new ByteToHex();
    private WriteDialog writeDialog = new WriteDialog();
    private Handler mHandler, sendHandler, intentHandler;
    private Send send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        writeDialog.set_Dialog(this, true);
        vibrator = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);
        BluetoothManager bluetoothManager = getManager(this);
        mBluetoothAdapter = bluetoothManager.getAdapter();
        if (mBluetoothLeService == null) {
            Intent gattServiceIntent = new Intent(this, BluetoothLeService.class);
            s_connect = bindService(gattServiceIntent, mServiceConnection, BIND_AUTO_CREATE);
            if (s_connect) {
                registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter());
            } else
                Log.e(TAG, "連線失敗");
        }
        get_intent();
    }

    private void get_intent() {
        Intent intent = getIntent();
        default_model = intent.getStringArrayExtra("default_model");
        String savelist = intent.getStringExtra("savelist");
        String numlist = intent.getStringExtra("numlist");
        showlist(savelist, numlist);
    }

    private void showlist(String savelist, String numlist) {
        setContentView(R.layout.empty);

        mHandler = new Handler();
        sendHandler = new Handler();
        intentHandler = new Handler();
        ConvertList convertList = new ConvertList();
        List<List<String>> getloadlist = convertList.getloadlist(savelist);
        List<String> getloadnumlist = convertList.getloadnumlist(numlist);
        List<List<byte[]>> readytosend = new ArrayList<>();
        readytosend.clear();
        NewModel.sub1.clear();
        NewModel.sub2.clear();
        NewModel.sub3.clear();
        NewModel.sub4.clear();
        NewModel.sub5.clear();
        NewModel.sub6.clear();
        NewModel.sub7.clear();
        for(int i = 0; i < getloadlist.size(); i++){
            List<byte[]> readytoconvert = new ArrayList<>();
            readytoconvert.clear();
            for(int j = 0; j < getloadlist.get(i).size(); j++){
                byte[] getbyte = byteToHex.hex2Byte(getloadlist.get(i).get(j));
                readytoconvert.add(getbyte);
            }

            if (getloadnumlist.get(i).matches("1")) {
                NewModel.sub1 = readytoconvert;
            } else if (getloadnumlist.get(i).matches("2")) {
                NewModel.sub2 = readytoconvert;
            } else if (getloadnumlist.get(i).matches("3")) {
                NewModel.sub3 = readytoconvert;
            } else if (getloadnumlist.get(i).matches("4")) {
                NewModel.sub4 = readytoconvert;
            } else if (getloadnumlist.get(i).matches("5")) {
                NewModel.sub5 = readytoconvert;
            } else if (getloadnumlist.get(i).matches("6")) {
                NewModel.sub6 = readytoconvert;
            } else if (getloadnumlist.get(i).matches("7")) {
                NewModel.sub7 = readytoconvert;
            }
            readytosend.add(readytoconvert);
            show(readytoconvert, i);
        }
        sendera(readytosend);
        Log.e(TAG, "READY!");
    }

    private void sendera(List<List<byte[]>> readytosend){
        Handler RE = new Handler();
        RE.postDelayed(() -> {
            send.sendString(Value.deviceModel);
            mHandler.postDelayed(() -> {
                Log.e(TAG, "readytosend = " + readytosend);
                sendlist(readytosend);
                mHandler.removeCallbacksAndMessages(null);
            }, 2000);
        }, 2000);
    }

    private void sendlist(List<List<byte[]>> readytosend){
        int count = 1;
        for (int i = 0; i < readytosend.size(); i++) {
            int finali = i;
            sendHandler.postDelayed(() ->{
                Log.e(TAG, "readytosend.get(finali) = " + readytosend.get(finali));
                send.sendlist(readytosend.get(finali));
            } , 3200 * count);
            count++;
        }

        intentHandler.postDelayed(() -> {
            Intent intent = new Intent(this, UserFunction.class);
            intent.putExtra("default_model", default_model);
            writeDialog.closeDialog();
            startActivity(intent);
            finish();
        }, 3000 * (readytosend.size() + 1));
    }

    private void show(List<byte[]> nowList, int k){
        List<String> converbytelist = new ArrayList<>();
        converbytelist.clear();
        for (int i = 0; i < nowList.size(); i++) {
            byte[] getbyte = nowList.get(i);
            StringBuilder hex = new StringBuilder(getbyte.length * 2);
            for (byte aData : getbyte) {
                hex.append(String.format("%02X", aData));
            }
            String gethex = hex.toString();
            converbytelist.add(gethex);
        }
        Log.e(TAG, "converbytelist" + k + " = " + converbytelist);
    }

    public static BluetoothManager getManager(Context context) {    //獲取此設備默認藍芽適配器
        return (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
    }

    private final ServiceConnection mServiceConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            //https://github.com/googlesamples/android-BluetoothLeGatt/tree/master/Application/src/main/java/com/example/android/bluetoothlegatt
            mBluetoothLeService = ((BluetoothLeService.LocalBinder) service).getService();
            if (!mBluetoothLeService.initialize()) {
                Log.e(TAG, "初始化失敗");
            }
            mBluetoothLeService.connect(Value.BID);
            NewModel.mBluetoothLeService = mBluetoothLeService;
            Log.e(TAG, "進入連線");
            send = new Send(mBluetoothLeService);
        }

        public void onServiceDisconnected(ComponentName componentName) {
            mBluetoothLeService = null;
            Log.e(TAG, "失去連線端");
        }
    };

    public final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            intents = intent;
            final String action = intent.getAction();
            if (BluetoothLeService.ACTION_GATT_CONNECTED.equals(action)) {
                Log.e(TAG, "連線成功");
            } else if (BluetoothLeService.ACTION_GATT_DISCONNECTED.equals(action)) {
                //s_connect = false;
                Log.e(TAG, "連線中斷" + Value.connected);
            } else if (BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED.equals(action)) {
                // Show all the supported services and characteristics on the user interface.
                //displayGattServices(mBluetoothLeService.getSupportedGattServices());
                mBluetoothLeService.enableTXNotification();
                Log.e(TAG, "連線狀態改變");
            } else if (BluetoothLeService.ACTION_DATA_AVAILABLE.equals(action)) {
                runOnUiThread(() -> {
                    String currentDateTimeString = DateFormat.getTimeInstance().format(new Date());
                    byte[] txValue = intents.getByteArrayExtra(BluetoothLeService.EXTRA_DATA);
                    String text = new String(txValue, StandardCharsets.UTF_8);
                    Log.e(TAG, "[" + currentDateTimeString + "] send: " + text);
                });
            }
        }
    };

    public static IntentFilter makeGattUpdateIntentFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_CONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_DISCONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED);
        intentFilter.addAction(BluetoothLeService.ACTION_DATA_AVAILABLE);
        return intentFilter;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // land do nothing is ok
        } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            // port do nothing is ok
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    public boolean onKeyDown(int key, KeyEvent event) {
        switch (key) {
            case KeyEvent.KEYCODE_SEARCH:
                break;
            case KeyEvent.KEYCODE_BACK: {
                vibrator.vibrate(100);
            }
            break;
            case KeyEvent.KEYCODE_DPAD_CENTER:
                break;
            default:
                return false;
        }
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy()");
        Value.passwordFlag = 0;
        if (mBluetoothLeService != null) {
            if (s_connect) {
                unbindService(mServiceConnection);
                s_connect = false;
            }
            mBluetoothLeService.stopSelf();
            mBluetoothLeService = null;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (s_connect)
            unregisterReceiver(mGattUpdateReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (s_connect) {
            registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter());
            if (mBluetoothLeService != null) {
                final boolean result = mBluetoothLeService.connect(Value.BID);
                NewModel.mBluetoothLeService = mBluetoothLeService;
                Log.d(TAG, "Connect request result=" + result);
            }
        }
    }

    public void Service_close() {
        if (mBluetoothLeService == null) {
            Log.e(TAG, "masaga");
            return;
        }
        mBluetoothLeService.disconnect();
    }
}
