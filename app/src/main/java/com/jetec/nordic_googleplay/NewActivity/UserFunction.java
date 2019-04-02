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
import android.os.IBinder;
import android.os.Vibrator;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import com.jetec.nordic_googleplay.NewActivity.GetString.ByteToHex;
import com.jetec.nordic_googleplay.NewActivity.SendByte.Send;
import com.jetec.nordic_googleplay.NewModel;
import com.jetec.nordic_googleplay.R;
import com.jetec.nordic_googleplay.ScanParse.Getparse;
import com.jetec.nordic_googleplay.Service.BluetoothLeService;
import com.jetec.nordic_googleplay.Value;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static com.jetec.nordic_googleplay.Activity.DeviceList.getManager;

public class UserFunction extends AppCompatActivity {

    private String TAG = "UserFunction";
    private Vibrator vibrator;
    private String[] default_model;
    private BluetoothLeService mBluetoothLeService;
    private BluetoothAdapter mBluetoothAdapter;
    private boolean s_connect = false;
    private Send send;
    private Intent intents;
    public static List<byte[]> list1, list2, list3, list4, list5, list6, list7;
    private byte[] getbyte = {0x42, 0x59, 0x54, 0x45}, getover = {0x4F, 0x56, 0x45, 0x52};
    private ByteToHex byteToHex = new ByteToHex();
    private Getparse getparse = new Getparse();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vibrator = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);
        BluetoothManager bluetoothManager = getManager(this);
        mBluetoothAdapter = bluetoothManager.getAdapter();
        if (mBluetoothLeService == null) {
            Intent gattServiceIntent = new Intent(this, BluetoothLeService.class);
            s_connect = bindService(gattServiceIntent, mServiceConnection, BIND_AUTO_CREATE);
            if (s_connect)
                registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter());
            else
                Log.e(TAG, "連線失敗");
        }
        get_intent();
    }

    private void get_intent() {
        Intent intent = getIntent();
        default_model = intent.getStringArrayExtra("default_model");
        list1 = NewModel.sub1;
        list2 = NewModel.sub2;
        list3 = NewModel.sub3;
        list4 = NewModel.sub4;
        list5 = NewModel.sub5;
        list6 = NewModel.sub6;
        list7 = NewModel.sub7;

        showlist();
    }

    private final ServiceConnection mServiceConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            //https://github.com/googlesamples/android-BluetoothLeGatt/tree/master/Application/src/main/java/com/example/android/bluetoothlegatt
            mBluetoothLeService = ((BluetoothLeService.LocalBinder) service).getService();
            if (!mBluetoothLeService.initialize()) {
                Log.e(TAG, "初始化失敗");
            }
            mBluetoothLeService.connect(Value.BID);
            Log.e(TAG, "進入連線");
        }

        public void onServiceDisconnected(ComponentName componentName) {
            mBluetoothLeService = null;
            Log.e(TAG, "失去連線端");
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
                Log.e(TAG, "連線狀態改變");
                mBluetoothLeService.enableTXNotification();
            } else if (BluetoothLeService.ACTION_DATA_AVAILABLE.equals(action)) {
                runOnUiThread(() -> {
                    try {
                        String currentDateTimeString = DateFormat.getTimeInstance().format(new Date());
                        byte[] txValue = intents.getByteArrayExtra(BluetoothLeService.EXTRA_DATA);
                        String text = new String(txValue, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                });
            }
        }
    };

    private void showlist() {
        setContentView(R.layout.userlist);

        String model = Value.deviceModel;
        String[] arr = model.split("-");
        String name = arr[2];
        List<Character> ch = new ArrayList<>();
        for (int i = 0; i < name.length(); i++) {
            ch.add(name.charAt(i));
        }
        Log.e(TAG, "name = " + name);

        TabLayout tabLayout = findViewById(R.id.tablayout);

        int count = 1;
        for (int i = 0; i < ch.size(); i++) {
            if (ch.get(i).toString().matches("T")) {
                tabLayout.addTab(tabLayout.newTab());
                Objects.requireNonNull(tabLayout.getTabAt(i)).setText(getString(R.string.bt_t));
            } else if (ch.get(i).toString().matches("H")) {
                tabLayout.addTab(tabLayout.newTab());
                Objects.requireNonNull(tabLayout.getTabAt(i)).setText(getString(R.string.bt_h));
            } else if (ch.get(i).toString().matches("C")) {
                tabLayout.addTab(tabLayout.newTab());
                Objects.requireNonNull(tabLayout.getTabAt(i)).setText(getString(R.string.bt_c));
            } else if (ch.get(i).toString().matches("D")) {
                tabLayout.addTab(tabLayout.newTab());
                Objects.requireNonNull(tabLayout.getTabAt(i)).setText(getString(R.string.bt_d));
            } else if (ch.get(i).toString().matches("E")) {
                tabLayout.addTab(tabLayout.newTab());
                Objects.requireNonNull(tabLayout.getTabAt(i)).setText(getString(R.string.bt_e));
            } else if (ch.get(i).toString().matches("I")) {
                tabLayout.addTab(tabLayout.newTab());
                Objects.requireNonNull(tabLayout.getTabAt(i)).setText(getString(R.string.table_i) + count + "\n" + "(4~20mA)");
                count++;
            } else if (ch.get(i).toString().matches("S")) {
                tabLayout.addTab(tabLayout.newTab());
                Objects.requireNonNull(tabLayout.getTabAt(i)).setText(getString(R.string.bt_co));
            }
        }

        tabLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy()");
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
                Log.d(TAG, "Connect request result=" + result);
            }
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
            case KeyEvent.KEYCODE_BACK:
                vibrator.vibrate(100);
                break;
            case KeyEvent.KEYCODE_DPAD_CENTER:
                break;
            default:
                return false;
        }
        return false;
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
}
