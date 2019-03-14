package com.jetec.nordic_googleplay.Activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.jetec.nordic_googleplay.Dialog.WriteDialog;
import com.jetec.nordic_googleplay.NewActivity.CheckPassword;
import com.jetec.nordic_googleplay.NewModel;
import com.jetec.nordic_googleplay.ScanParse.*;
import com.jetec.nordic_googleplay.Service.BluetoothLeService;
import com.jetec.nordic_googleplay.Thread.ConnectThread;
import com.jetec.nordic_googleplay.ViewAdapter.DeviceAdapter;
import com.jetec.nordic_googleplay.R;
import com.jetec.nordic_googleplay.SendValue;
import com.jetec.nordic_googleplay.Value;
import org.json.JSONArray;
import org.json.JSONException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Thread.sleep;

public class DeviceList extends AppCompatActivity {

    private DeviceParse deviceParse = new DeviceParse();
    private WriteDialog writeDialog = new WriteDialog();
    private BluetoothLeService mBluetoothLeService;
    private BluetoothAdapter mBluetoothAdapter;
    private DeviceAdapter deviceAdapter;
    private SendValue sendValue;
    private Vibrator vibrator;
    private Intent intents;
    private Handler mHandler, checkHandler;
    private ArrayList<String> Jsonlist;
    private List<BluetoothDevice> deviceList, checkdeviceList;
    private Map<Integer, List<String>> record;
    private List<String> Deviceposition;
    private List<byte[]> setrecord;
    private byte[] txValue;
    private JSONArray modelJSON;
    private View no_device;
    private ListView list_device;
    private boolean s_connect = false;
    private String TAG = "DeviceList", Jetec = "Jetec", text;
    private final String[] T = {"PV", "EH", "EL", "CR"};
    private final String[] H = {"PV", "EH", "EL", "CR"};
    private final String[] C = {"PV", "EH", "EL", "CR"};
    private final String[] D = {"PV", "EH", "EL", "CR"};
    private final String[] E = {"PV", "EH", "EL", "CR"};
    private final String[] I = {"IH", "IL", "PV", "EH", "EL", "CR", "DP"};
    private final String[] L = {"COUNT", "INTER", "DATE", "TIME", "LOG"};
    private final String[] SP = {"SPK"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vibrator = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        }

        BluetoothManager bluetoothManager = getManager(this);
        mBluetoothAdapter = bluetoothManager.getAdapter();
        try {
            get_intent();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler connectHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Remote_connec();
        }
    };

    private void get_intent() throws JSONException {

        Intent intent = getIntent();
        String[] default_model = intent.getStringArrayExtra("default_model");
        modelJSON = new JSONArray(default_model);

        show_device();
    }

    private void show_device() {

        setContentView(R.layout.activity_main);

        mHandler = new Handler();
        checkHandler = new Handler();

        no_device = findViewById(R.id.no_data);
        no_device.setVisibility(View.VISIBLE);   //VISIBLE // GONE
        device_list();
    }

    @SuppressLint("UseSparseArrays")
    private void device_list() {
        Jsonlist = new ArrayList<>();
        deviceList = new ArrayList<>();
        Deviceposition = new ArrayList<>();
        checkdeviceList = new ArrayList<>();
        setrecord = new ArrayList<>();
        record = new HashMap<>();

        deviceAdapter = new DeviceAdapter(this);

        list_device = findViewById(R.id.list_data);
        list_device.setAdapter(deviceAdapter);
        list_device.setOnItemClickListener(mDeviceClickListener);

        setList();  //每0.5秒更新顯示之裝置列表
        settimer(); //每6秒檢查列表，將不再範圍內裝置從列表中移除，移除後更新列表
        scanLeDevice(); //掃描附近設備
    }

    private void setList() {    //每0.5秒更新顯示之裝置列表
        mHandler.postDelayed(() -> {
            record = deviceParse.regetList(deviceList, setrecord);
            if (record != null) {
                if (record.size() > 0) {
                    no_device.setVisibility(View.GONE);
                    list_device.setVisibility(View.VISIBLE);
                    deviceAdapter.getList(record);
                    deviceAdapter.notifyDataSetChanged();
                } else {
                    no_device.setVisibility(View.VISIBLE);
                    list_device.setVisibility(View.GONE);
                }
            }
            mHandler.removeCallbacksAndMessages(null);
            setList();
        }, 500);
    }

    private void settimer() {   //每6秒檢查列表，將不再範圍內裝置從列表中移除，移除後更新列表
        checkHandler.postDelayed(() -> {
            for (int i = 0; i < deviceList.size(); i++) {
                if (checkdeviceList.indexOf(deviceList.get(i)) == -1) {
                    //noinspection SuspiciousListRemoveInLoop
                    deviceList.remove(i);
                    //noinspection SuspiciousListRemoveInLoop
                    setrecord.remove(i);
                }
            }
            deviceAdapter.notifyDataSetChanged();
            checkdeviceList.clear();
            checkHandler.removeCallbacksAndMessages(null);
            settimer();
        }, 6000);
    }

    private AdapterView.OnItemClickListener mDeviceClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            vibrator.vibrate(100);
            Deviceposition = record.get(position);  //List<String>型態:{name, address}
            //noinspection deprecation
            mBluetoothAdapter.stopLeScan(mLeScanCallback);
            deviceList.clear(); //清除裝置列表
            setrecord.clear();  //清除byte[]列表
            checkHandler.removeCallbacksAndMessages(null);  //停止handler
            mHandler.removeCallbacksAndMessages(null);  //停止handler
            ConnectThread connectThread = new ConnectThread(connectHandler);
            connectThread.run();
        }
    };

    private void scanLeDevice() {   //掃描附近設備
        //noinspection deprecation
        mBluetoothAdapter.startLeScan(mLeScanCallback);
    }

    private BluetoothAdapter.LeScanCallback mLeScanCallback =   //將裝置與byte[]匯入
            (device, rssi, scanRecord) -> runOnUiThread(() -> runOnUiThread(() -> addDevice(device, scanRecord)));

    private void addDevice(BluetoothDevice device, byte[] scanRecord) { //同步新增裝置列表與byte[]列表
        boolean deviceFound = false;

        for (BluetoothDevice listDev : deviceList) {
            if (listDev.getAddress().equals(device.getAddress())) {
                deviceFound = true;
                int i = deviceList.indexOf(device);
                setrecord.set(i, scanRecord);
                break;
            }
        }
        if (!deviceFound) {
            deviceList.add(device);
            setrecord.add(scanRecord);
        }
    }

    private void Remote_connec() {  //開始對裝置連線
        Value.BID = Deviceposition.get(1);  //裝置address
        Value.BName = Deviceposition.get(0);    //裝置name
        Intent gattServiceIntent = new Intent(DeviceList.this, BluetoothLeService.class);
        s_connect = bindService(gattServiceIntent, mServiceConnection, BIND_AUTO_CREATE);
        if (s_connect) {
            if (!writeDialog.checkshowing()) {  //假如連線中之轉圈無顯示則顯示
                writeDialog.set_Dialog(this, getString(R.string.connecting));
            }
            registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter());
        } else {
            Log.d(TAG, "服務綁訂狀態  = " + false);
        }
    }

    private final ServiceConnection mServiceConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            Log.d(TAG, "連線中");
            //https://github.com/googlesamples/android-BluetoothLeGatt/tree/master/Application/src/main/java/com/example/android/bluetoothlegatt
            mBluetoothLeService = ((BluetoothLeService.LocalBinder) service).getService();
            if (!mBluetoothLeService.initialize()) {
                Log.d(TAG, "初始化失敗");
            }
            mBluetoothLeService.connect(Value.BID);
            Log.d(TAG, "進入連線");
        }

        public void onServiceDisconnected(ComponentName componentName) {
            mBluetoothLeService = null;
            Log.d(TAG, "失去連線端");
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
                Log.d(TAG, "連線成功");
            } else if (BluetoothLeService.ACTION_GATT_DISCONNECTED.equals(action)) {    //失去連線
                s_connect = false;
                if (mBluetoothLeService != null)
                    unbindService(mServiceConnection);
                Log.d(TAG, "連線中斷" + Value.connected);
                if (writeDialog.checkshowing()) {
                    writeDialog.closeDialog();
                }
                if (Value.connected) {  //取消裝置配對並嘗試重新連線
                    try {
                        Service_close();
                        sleep(2000);
                        ConnectThread newThread = new ConnectThread(connectHandler);
                        newThread.run();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } else if (BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED.equals(action)) {
                // Show all the supported services and characteristics on the user interface.
                //displayGattServices(mBluetoothLeService.getSupportedGattServices());
                Log.d(TAG, "連線狀態改變");
                Log.d(TAG,"Value.connected = " + Value.connected);
                mBluetoothLeService.enableTXNotification();
                if (!Value.connected)   //測試裝置是否有回傳值
                    new Thread(sendcheck).start();
            } else if (BluetoothLeService.ACTION_DATA_AVAILABLE.equals(action)) {
                runOnUiThread(() -> {
                    try {
                        txValue = intents.getByteArrayExtra(BluetoothLeService.EXTRA_DATA);
                        if (!NewModel.checkmodel) { //舊式型號
                            text = new String(txValue, StandardCharsets.UTF_8);
                            Log.d(TAG, "text = " + text);
                            if (text.startsWith("OK")) {
                                Jsonlist.clear();
                                new Thread(checkmodel).start();
                                for (; !Value.modelList; ) {
                                    sleep(100);
                                }
                                new Thread(sendpassword).start();
                            } else if (text.startsWith("BT")) {
                                Jsonlist.clear();
                                Value.model = true;
                                Value.deviceModel = text;
                                String[] arr = text.split("-");
                                if (arr.length == 3) {  //若型號為舊型，則進入此
                                    NewModel.checkmodel = false;
                                    String num = arr[1];
                                    Log.d(TAG, "num = " + num);
                                    String num2 = arr[2];
                                    Log.d(TAG, "num2 = " + num2);
                                    //timeclock
                                    Value.YMD = text.contains("Y");
                                    ArrayList<String> newList = new ArrayList<>();
                                    newList.clear();
                                    for (int i = 0; i < num2.length(); i++) {
                                        if (num2.charAt(i) == 'T') {
                                            for (String aT : T) {
                                                String str = aT + (i + 1);
                                                newList.add(str);
                                            }
                                        } else if (num2.charAt(i) == 'H') {
                                            for (String aH : H) {
                                                String str = aH + (i + 1);
                                                newList.add(str);
                                            }
                                        } else if (num2.charAt(i) == 'C') {
                                            for (String aC : C) {
                                                String str = aC + (i + 1);
                                                newList.add(str);
                                            }
                                        } else if (num2.charAt(i) == 'D') {
                                            for (String aD : D) {
                                                String str = aD + (i + 1);
                                                newList.add(str);
                                            }
                                        } else if (num2.charAt(i) == 'E') {
                                            for (String aE : E) {
                                                String str = aE + (i + 1);
                                                newList.add(str);
                                            }
                                        } else if (num2.charAt(i) == 'I') {
                                            for (String aI : I) {
                                                String str = aI + (i + 1);
                                                newList.add(str);
                                            }
                                        } else if (num2.charAt(i) == 'L') {
                                            newList.addAll(Arrays.asList(L));
                                        }
                                    }
                                    newList.addAll(Arrays.asList(SP));
                                    Value.Jsonlist = newList;
                                    Log.d(TAG, "newList = " + newList);
                                    Log.d(TAG, "Jsonlist = " + Value.Jsonlist);
                                } else {    //若為新式型號則將布林值開啟
                                    NewModel.checkmodel = true;
                                    sendValue.send("PASSWD");
                                }
                            } else if (text.startsWith("ENGE")) {
                                Value.E_word = text.substring(4);
                                Log.d(TAG, "管理者密碼 = " + Value.E_word);
                            } else if (text.startsWith("PASS")) {
                                Value.P_word = text.substring(4);
                                Log.d(TAG, "客戶密碼 = " + Value.P_word);
                            } else if (text.startsWith("INIT")) {
                                Value.I_word = text.substring(4);
                                Log.d(TAG, "初始化密碼 = " + Value.I_word);
                            } else if (text.startsWith("Delay")) {
                                Log.d(TAG, "Delay時間 = " + text);
                            } else if (text.startsWith("GUES")) {
                                Value.G_word = text.substring(4);
                                Log.d(TAG, "訪客密碼 = " + Value.G_word);
                                Value.connected = true;
                                check();
                            }
                        } else {    //新式型號區域
                            text = new String(txValue, StandardCharsets.UTF_8);
                            Log.d(TAG, "text = " + text);
                            if (text.startsWith("PASS")) {
                                Value.P_word = text.substring(4);
                                Log.d(TAG, "客戶密碼 = " + Value.P_word);
                            }
                            Value.G_word = "111111";    //訪客密碼
                            Value.E_word = "@JETEC";    //工程模式
                            Value.I_word = ">////<";    //初始化
                            new_deviceFunction();
                        }
                    } catch (InterruptedException e/* | JSONException e*/) {
                        e.printStackTrace();
                    }
                });
            }
        }
    };

    private Runnable checkmodel = new Runnable() {
        @Override
        public void run() {
            try {
                sleep(200);
                if (!Value.model) {
                    for (int i = 1; i < modelJSON.length(); i++) {
                        Jsonlist.add(modelJSON.get(i).toString());
                    }
                    Value.Jsonlist = Jsonlist;
                    Value.deviceModel = modelJSON.get(0).toString();
                }
                Value.modelList = true;
                Log.d(TAG, "Jsonlist = " + Jsonlist);
                Log.d(TAG, "modelList = " + Value.modelList);
            } catch (InterruptedException | JSONException e) {
                e.printStackTrace();
            }
        }
    };

    private Runnable sendcheck = () -> {
        try {
            sleep(500);
            Log.d(TAG, "sends = " + Jetec);
            if (s_connect) {
                sendValue = new SendValue(mBluetoothLeService);
                sendValue.send(Jetec);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    };

    private Runnable sendpassword = () -> {
        try {
            sleep(500);
            if (!NewModel.checkmodel) {
                Log.d(TAG, "log delay時間");
                sendValue.send("Delay00015");
                sleep(100);
                Log.d(TAG, "管理者密碼確認");
                sendValue.send("ENGEWD");  //ENGEWD = 管理者密碼確認
                sleep(100);
                Log.d(TAG, "客戶密碼確認");
                sendValue.send("PASSWD");  //PASSWD = 客戶密碼確認(只有此密碼可以修改)
                sleep(100);
                Log.d(TAG, "初始化密碼確認");
                sendValue.send("INITWD");
                sleep(100);
                Log.d(TAG, "訪客密碼確認");
                sendValue.send("GUESWD");  //GUESWD = 訪客密碼確認
                sleep(100);
            } else {    //若為新型號，則不作用
                Log.d(TAG, "切換至新型號function");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    };

    private void new_deviceFunction() {
        Intent intent = new Intent(DeviceList.this, CheckPassword.class);
        String[] default_model = new String[modelJSON.length()];
        for (int i = 0; i < modelJSON.length(); i++) {
            try {
                default_model[i] = modelJSON.get(i).toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        intent.putExtra("default_model", default_model);
        startActivity(intent);
        finish();
    }

    private void check() {
        Intent intent = new Intent(this, Check.class);
        String[] default_model = new String[modelJSON.length()];
        for (int i = 0; i < modelJSON.length(); i++) {
            try {
                default_model[i] = modelJSON.get(i).toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        intent.putExtra("default_model", default_model);
        startActivity(intent);
        finish();
    }

    private void backtofirst() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void Service_close() {  //結束配對
        if (mBluetoothLeService == null) {
            Log.d(TAG, "service close!");
            return;
        }
        mBluetoothLeService.disconnect();
    }

    public static BluetoothManager getManager(Context context) {    //獲取此設備默認藍芽適配器
        return (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
    }

    public boolean onKeyDown(int key, KeyEvent event) {
        switch (key) {
            case KeyEvent.KEYCODE_SEARCH:
                break;
            case KeyEvent.KEYCODE_BACK:
                vibrator.vibrate(100);
                backtofirst();
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
        Log.d(TAG, "onDestroy()");
        if (mBluetoothLeService != null) {
            if (s_connect) {
                unbindService(mServiceConnection);
                s_connect = false;
            }
            mBluetoothLeService.stopSelf();
            mBluetoothLeService = null;
        }
        deviceList.clear();
        setrecord.clear();
        checkHandler.removeCallbacksAndMessages(null);
        mHandler.removeCallbacksAndMessages(null);
        writeDialog.closeDialog();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume()");
        if (s_connect) {
            registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter());
            if (mBluetoothLeService != null) {
                final boolean result = mBluetoothLeService.connect(Value.BID);
                Log.d(TAG, "Connect request result = " + result);
            }
        }
        device_list();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause()");
        //noinspection deprecation
        mBluetoothAdapter.stopLeScan(mLeScanCallback);
        checkHandler.removeCallbacksAndMessages(null);
        mHandler.removeCallbacksAndMessages(null);
        deviceList.clear();
        setrecord.clear();
        if (s_connect)
            unregisterReceiver(mGattUpdateReceiver);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {   //手機翻轉
        super.onConfigurationChanged(newConfig);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) { //橫向
            // land do nothing is ok
            show_device();
        } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {   //直向
            // port do nothing is ok
            show_device();
        }
    }
}
