package com.jetec.nordic_googleplay.Activity;

import android.annotation.SuppressLint;
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
import android.os.Message;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jetec.nordic_googleplay.*;
import com.jetec.nordic_googleplay.Dialog.WriteDialog;
import com.jetec.nordic_googleplay.Service.BluetoothLeService;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import static java.lang.Thread.sleep;

public class Check extends AppCompatActivity {

    private String TAG = "Check";
    private Vibrator vibrator;
    private Intent intents;
    private String[] default_model;
    private BluetoothLeService mBluetoothLeService;
    private BluetoothAdapter mBluetoothAdapter;
    private Initialization initialization;
    private boolean s_connect = false;
    private SendValue sendValue;
    private ArrayList<String> return_RX, SelectItem, DataSave, checklist;
    private CheckDeviceName checkDeviceName = new CheckDeviceName();
    private GetString setString = new GetString();
    private WriteDialog writeDialog = new WriteDialog();

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
                Log.d(TAG, "連線失敗");
        }

        get_Intent();
    }

    private void get_Intent() {

        Intent intent = getIntent();
        default_model = intent.getStringArrayExtra("default_model");

        return_RX = new ArrayList<>();
        SelectItem = new ArrayList<>();
        DataSave = new ArrayList<>();
        checklist = new ArrayList<>();

        return_RX.clear();
        SelectItem.clear();
        DataSave.clear();
        checklist.clear();

        check();
    }

    @SuppressLint("HandlerLeak")
    private Handler standbyHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            SelectItem.clear();
            DataSave.clear();
            return_RX.clear();
            checklist.clear();
            login();
        }
    };

    @SuppressLint("SetTextI18n")
    private void check() {
        setContentView(R.layout.checkpassword);

        Button by = findViewById(R.id.button2);
        Button bn = findViewById(R.id.button1);
        TextView t1 = findViewById(R.id.textView3);
        EditText e1 = findViewById(R.id.editText1);

        t1.setText(getString(R.string.device_name) + "： " + Value.BName);
        e1.setKeyListener(DigitsKeyListener.getInstance(".,$%&^!()-_=+';:|}{[]*→←↘↖、，。?~～#€￠" +
                "￡￥abcdefghigklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789@>/<"));
        e1.setInputType(InputType.TYPE_NUMBER_FLAG_SIGNED |
                InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

        bn.setOnClickListener(v -> {
            vibrator.vibrate(100);
            devicelist();
        });

        by.setOnClickListener(v -> {
            vibrator.vibrate(100);
            if (e1.getText().toString().length() == 6) {
                Log.d(TAG, "輸入之密碼 = " + e1.getText().toString().trim());
                if (e1.getText().toString().trim().matches(Value.E_word)) { //工程模式
                    Value.passwordFlag = 1;
                    Value.Engin = true;
                    Log.d(TAG, "管理者 登入");
                    Value.get_noti = true;
                    login();
                } else if (e1.getText().toString().trim().matches(Value.P_word)) {  //一般登入
                    Value.Engin = false;
                    Value.passwordFlag = 2;
                    Log.d(TAG, "客戶 登入");
                    Value.get_noti = true;
                    login();
                } else if (e1.getText().toString().trim().matches(Value.I_word)) {  //初始化
                    Toast.makeText(Check.this, getString(R.string.initialization), Toast.LENGTH_SHORT).show();
                    writeDialog.set_Dialog(this, getString(R.string.intervalset));
                    Log.d(TAG, "初始化裝置 = " + Value.deviceModel);
                    initialization = new Initialization(Value.deviceModel, mBluetoothLeService);
                    try {
                        Value.Engin = false;
                        Value.get_noti = true;
                        Value.init = true;
                        initialization.start();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        if(writeDialog.checkshowing()) {
                            writeDialog.closeDialog();
                        }
                        Value.passwordFlag = 3;
                        Log.d(TAG, "開始進行原廠設定");
                        Toast.makeText(Check.this, getString(R.string.complete), Toast.LENGTH_SHORT).show();
                        Value.get_noti = false;
                        Value.connected = false;
                        Service_close();
                        backtofirst();
                    }
                } else if (e1.getText().toString().trim().matches(Value.G_word)) {  //訪客登入
                    Value.passwordFlag = 4;
                    Value.Engin = false;
                    Value.get_noti = true;
                    Log.d(TAG, "訪客 登入");
                    login();
                } else {
                    Toast.makeText(Check.this, getString(R.string.passworderror), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(Check.this, getString(R.string.inputerror), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void devicelist() {    //一般模式

        Intent intent = new Intent(Check.this, DeviceList.class);
        NewModel.checkmodel = false;
        Value.connected = false;
        intent.putExtra("default_model", default_model);
        startActivity(intent);
        finish();
    }

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
            } else if (BluetoothLeService.ACTION_GATT_DISCONNECTED.equals(action)) {
                Log.d(TAG, "連線中斷" + Value.connected);
            } else if (BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED.equals(action)) {
                // Show all the supported services and characteristics on the user interface.
                //displayGattServices(mBluetoothLeService.getSupportedGattServices());
                Log.d(TAG, "連線狀態改變");
                mBluetoothLeService.enableTXNotification();
                sendValue = new SendValue(mBluetoothLeService);
            } else if (BluetoothLeService.ACTION_DATA_AVAILABLE.equals(action)) {
                runOnUiThread(() -> {
                    try {
                        byte[] txValue = intents.getByteArrayExtra(BluetoothLeService.EXTRA_DATA);
                        String text = new String(txValue, StandardCharsets.UTF_8);
                        if (text.startsWith("+") || text.startsWith("-")) { //裝置正在發送Log，使其停止
                            sleep(300);
                            sendValue.send("STOP");
                        } else {
                            if (!Value.init) {  //檢查是否正在初始化裝置
                                if (!text.startsWith("OVER")) {
                                    if (!(text.startsWith("COUNT") || text.startsWith("DATE") ||
                                            text.startsWith("TIME") || text.matches("LOGON") ||
                                            text.matches("LOGOFF") || text.startsWith("LOG") ||
                                            text.startsWith("+") || text.startsWith("-") ||
                                            text.startsWith("STOP"))) {
                                        SelectItem.add(checkDeviceName.setName(text));
                                        return_RX.add(text);
                                        DataSave.add(text);
                                        checklist.add(text);
                                        //check = check + 1;
                                    } else if ((text.startsWith("COUNT") || text.startsWith("DATE") ||
                                            text.startsWith("TIME") || text.matches("LOGON") ||
                                            text.matches("LOGOFF"))) {
                                        setString.set(text);
                                        checklist.add(text);
                                    } else {    //若裝置正在傳送Log則發送STOP停止
                                        if (text.startsWith("+") || text.startsWith("-"))
                                            sendValue.send("STOP");
                                    }
                                } else if (text.matches("OVER") && !text.startsWith("LOG")) {
                                    //OVER為裝置溝通結束
                                    Log.d(TAG, "RX = " + return_RX);
                                    Log.d(TAG, "SelectItem = " + SelectItem);
                                    Log.d(TAG, "型號 = " + Value.deviceModel);
                                    Log.d(TAG, "Value.Jsonlist = " + Value.Jsonlist);
                                    Log.d(TAG, "Value.Jsonlist.size() = " + Value.Jsonlist.size());
                                    Log.d(TAG, "return_RX.size() = " + return_RX.size());
                                    if (Value.Jsonlist != null) {
                                        if (Value.Jsonlist.size() == checklist.size()) {
                                            Value.SelectItem = SelectItem;
                                            Value.DataSave = DataSave;
                                            Value.return_RX = return_RX;
                                            Value.get_noti = false;
                                            if (!Value.Engin)
                                                device_function();
                                            else    //工程模式
                                                Engineer_function();
                                        }
                                    }
                                }
                            } else {    //若初始化設定接收到OVER即將開啟之布林值取消
                                if (text.matches("OVER")) {
                                    Value.init = false;
                                }
                            }
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            }
        }
    };

    private final ServiceConnection mServiceConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            //https://github.com/googlesamples/android-BluetoothLeGatt/tree/master/Application/src/main/java/com/example/android/bluetoothlegatt
            mBluetoothLeService = ((BluetoothLeService.LocalBinder) service).getService();
            if (!mBluetoothLeService.initialize()) {
                Log.d(TAG, "初始化失敗");
            }
            mBluetoothLeService.connect(Value.BID);
            sendValue = new SendValue(mBluetoothLeService);
            Log.d(TAG, "進入連線");
        }

        public void onServiceDisconnected(ComponentName componentName) {
            mBluetoothLeService = null;
            Log.d(TAG, "失去連線端");
        }
    };

    private void login() {
        SelectItem.add("NAME");
        DataSave.add(Value.BName);
        sendValue.send("get");

        if (!writeDialog.checkshowing()) {
            writeDialog.set_Dialog(this, getString(R.string.login));
        }

        new Thread(timedelay).start();
    }

    private Runnable timedelay = new Runnable() {
        @Override
        public void run() {
            try {
                sleep(2000);
                //noinspection StatementWithEmptyBody
                if (Value.Jsonlist.size() == checklist.size()) {
                } else {
                    standbyHandler.sendEmptyMessage(1);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };

    private void device_function() {    //一般模式

        Intent intent = new Intent(this, DeviceFunction.class);

        startActivity(intent);
        finish();
    }

    private void Engineer_function() {  //工程模式

        Intent intent = new Intent(this, Engineer.class);

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
        Service_close();
        standbyHandler.removeCallbacksAndMessages(null);
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
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause()");
        //noinspection deprecation
        if (s_connect)
            unregisterReceiver(mGattUpdateReceiver);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {   //手機翻轉
        super.onConfigurationChanged(newConfig);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) { //橫向
            // land do nothing is ok
            Log.d(TAG, "橫向");
        } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {   //直向
            // port do nothing is ok
            Log.d(TAG, "直向");
        }
    }
}
