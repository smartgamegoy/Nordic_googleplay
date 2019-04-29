package com.jetec.nordic_googleplay.NewActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        listview = new ArrayList<>();
        List<List<byte[]>> savelist = new ArrayList<>();
        List<byte[]> list1 = new ArrayList<>();
        List<byte[]> list2 = new ArrayList<>();
        List<byte[]> list3 = new ArrayList<>();
        List<byte[]> list4 = new ArrayList<>();
        List<byte[]> list5 = new ArrayList<>();
        List<byte[]> list6 = new ArrayList<>();
        List<byte[]> list7 = new ArrayList<>();

        listview.clear();
        savelist.clear();
        list1.clear();
        list2.clear();
        list3.clear();
        list4.clear();
        list5.clear();
        list6.clear();
        list7.clear();

        list1 = NewModel.sub1;
        list2 = NewModel.sub2;
        list3 = NewModel.sub3;
        list4 = NewModel.sub4;
        list5 = NewModel.sub5;
        list6 = NewModel.sub6;
        list7 = NewModel.sub7;

        if (list1.size() != 0)
            savelist.add(list1);
        if (list2.size() != 0)
            savelist.add(list2);
        if (list3.size() != 0)
            savelist.add(list3);
        if (list4.size() != 0)
            savelist.add(list4);
        if (list5.size() != 0)
            savelist.add(list5);
        if (list6.size() != 0)
            savelist.add(list6);
        if (list7.size() != 0)
            savelist.add(list7);

        NewModel.viewList = savelist;
        newModel.setString(this);

        showlist();
    }

    private void showlist() {
        setContentView(R.layout.user_function);
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
}
