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
import android.text.InputType;
import android.text.method.DigitsKeyListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.jetec.nordic_googleplay.CheckDeviceName;
import com.jetec.nordic_googleplay.GetString;
import com.jetec.nordic_googleplay.NewModel;
import com.jetec.nordic_googleplay.ScanParse.*;
import com.jetec.nordic_googleplay.Service.BluetoothLeService;
import com.jetec.nordic_googleplay.Thread.ConnectThread;
import com.jetec.nordic_googleplay.ViewAdapter.DeviceAdapter;
import com.jetec.nordic_googleplay.Initialization;
import com.jetec.nordic_googleplay.SQL.ModelSQL;
import com.jetec.nordic_googleplay.R;
import com.jetec.nordic_googleplay.SendValue;
import com.jetec.nordic_googleplay.Value;
import org.json.JSONArray;
import org.json.JSONException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static java.lang.Thread.sleep;

public class DeviceList extends AppCompatActivity {

    private CheckDeviceName checkDeviceName;
    private GetString setString;
    private Initialization initialization;
    private Handler mHandler, checkHandler;
    private SendValue sendValue;
    private DeviceAdapter deviceAdapter;
    private BluetoothLeService mBluetoothLeService;
    private BluetoothAdapter mBluetoothAdapter;
    private List<BluetoothDevice> deviceList, checkdeviceList;
    private Map<Integer, List<String>> record;
    private List<String> Deviceposition;
    private Vibrator vibrator;
    private JSONArray modelJSON;
    private View no_device;
    private ListView list_device;
    private Intent intents;
    private ArrayList<String> return_RX, SelectItem, DataSave, Jsonlist;
    private Dialog progressDialog = null, progressDialog2 = null, initialDialog = null;
    private String TAG = "DeviceList";
    private String text;
    private String Jetec = "Jetec";
    private int check, flag;
    private boolean s_connect = false;
    private byte[] txValue;
    public List<byte[]> setrecord;
    private DeviceParse deviceParse = new DeviceParse();
    private Getparse getparse = new Getparse();
    private final String[] T = {"PV", "EH", "EL", "CR"};
    private final String[] H = {"PV", "EH", "EL", "CR"};
    private final String[] C = {"PV", "EH", "EL", "CR"};
    private final String[] D = {"PV", "EH", "EL", "CR"};
    private final String[] E = {"PV", "EH", "EL", "CR"};
    private final String[] I = {"IH", "IL", "PV", "EH", "EL", "CR", "DP"};
    private final String[] L = {"COUNT", "INTER", "DATE", "TIME", "LOG"};
    private final String[] SP = {"SPK", "OVER"};

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

    private void getW_H() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        Value.all_Width = dm.widthPixels;
        Value.all_Height = dm.heightPixels;
    }

    private void show_device() {

        setContentView(R.layout.activity_main);
        flag = 0;

        getW_H();
        mHandler = new Handler();
        checkHandler = new Handler();
        checkDeviceName = new CheckDeviceName();
        setString = new GetString();

        no_device = findViewById(R.id.no_data);
        no_device.setVisibility(View.VISIBLE);   //VISIBLE // GONE
        device_list();
    }

    @SuppressLint("UseSparseArrays")
    private void device_list() {
        return_RX = new ArrayList<>();
        SelectItem = new ArrayList<>();
        DataSave = new ArrayList<>();
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

        setList();
        settimer();
        scanLeDevice();
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
            progressDialog = writeDialog(DeviceList.this, getString(R.string.connecting));
            if (!progressDialog.isShowing()) {  //假如連線中之轉圈無顯示則顯示
                progressDialog.show();
                progressDialog.setCanceledOnTouchOutside(false);
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
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                    device_list();
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
                mBluetoothLeService.enableTXNotification();
                if (!Value.connected)   //測試裝置是否有回傳值
                    new Thread(sendcheck).start();
                else {
                    DataSave.clear();
                    return_RX.clear();
                    SelectItem.clear();
                    try {
                        sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    sendValue = new SendValue(mBluetoothLeService);
                    login();
                }
            } else if (BluetoothLeService.ACTION_DATA_AVAILABLE.equals(action)) {
                runOnUiThread(() -> {
                    try {
                        txValue = intents.getByteArrayExtra(BluetoothLeService.EXTRA_DATA);
                        if (!NewModel.checkmodel) { //舊式型號
                            text = new String(txValue, "UTF-8");
                            Log.d(TAG, "text = " + text);
                            if (text.startsWith("OK")) {
                                DataSave.clear();
                                return_RX.clear();
                                SelectItem.clear();
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
                                    if (text.contains("Y")) { //timeclock
                                        Value.YMD = true;
                                    } else {
                                        Value.YMD = false;
                                    }
                                    ArrayList<String> newList = new ArrayList<>();
                                    newList.clear();
                                    for (int i = 0; i < num2.length(); i++) {
                                        if (num2.charAt(i) == 'T') {
                                            for (int j = 0; j < T.length; j++) {
                                                String str = T[j] + (i + 1);
                                                newList.add(str);
                                            }
                                        } else if (num2.charAt(i) == 'H') {
                                            for (int j = 0; j < H.length; j++) {
                                                String str = H[j] + (i + 1);
                                                newList.add(str);
                                            }
                                        } else if (num2.charAt(i) == 'C') {
                                            for (int j = 0; j < C.length; j++) {
                                                String str = C[j] + (i + 1);
                                                newList.add(str);
                                            }
                                        } else if (num2.charAt(i) == 'D') {
                                            for (int j = 0; j < D.length; j++) {
                                                String str = D[j] + (i + 1);
                                                newList.add(str);
                                            }
                                        } else if (num2.charAt(i) == 'E') {
                                            for (int j = 0; j < E.length; j++) {
                                                String str = E[j] + (i + 1);
                                                newList.add(str);
                                            }
                                        } else if (num2.charAt(i) == 'I') {
                                            for (int j = 0; j < I.length; j++) {
                                                String str = I[j] + (i + 1);
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
                                    sendValue.send("get");
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
                                flag = 1;
                                check();
                            } else if (text.startsWith("+") || text.startsWith("-")) {
                                sleep(300);
                                sendValue = new SendValue(mBluetoothLeService);
                                sendValue.send("STOP");
                            } else {
                                if (!Value.init) {
                                    if (!text.startsWith("OVER")) {
                                        if (!(text.startsWith("COUNT") || text.startsWith("DATE") ||
                                                text.startsWith("TIME") || text.matches("LOGON") ||
                                                text.matches("LOGOFF") || text.startsWith("LOG") ||
                                                text.startsWith("+") || text.startsWith("-") ||
                                                text.startsWith("STOP"))) {
                                            SelectItem.add(checkDeviceName.setName(text));
                                            return_RX.add(text);
                                            DataSave.add(text);
                                            check = check + 1;
                                        } else if ((text.startsWith("COUNT") || text.startsWith("DATE") ||
                                                text.startsWith("TIME") || text.matches("LOGON") ||
                                                text.matches("LOGOFF"))) {
                                            setString.set(text, check);
                                            check = check + 1;
                                        } else {    //若裝置正在傳送Log則發送STOP停止
                                            if (text.startsWith("+") || text.startsWith("-"))
                                                sendValue.send("STOP");
                                        }
                                    } else if (text.matches("OVER") && !text.startsWith("LOG")) {
                                        //OVER為裝置溝通結束
                                        Log.d(TAG, "RX = " + return_RX);
                                        Log.d(TAG, "SelectItem = " + SelectItem);
                                        Log.d(TAG, "型號 = " + Value.deviceModel);
                                        if (Value.Jsonlist != null) {
                                            if (Value.Jsonlist.get(check).matches("OVER")) {
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
                        } else {    //新式型號區域
                            text = new String(txValue, StandardCharsets.UTF_8);
                            Log.d(TAG, "text = " + text);
                            if(NewModel.checkbyte){
                                getparse.parsebyte(txValue);
                            }
                            if(text.matches("BYTE")){
                                NewModel.checkbyte = true;
                            }
                            else if(text.matches("OVER")){
                                if(NewModel.checkbyte){
                                    NewModel.checkbyte = false;
                                }
                            }
                        }
                    } catch (UnsupportedEncodingException | InterruptedException e/* | JSONException e*/) {
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

    private void login() {
        if (progressDialog2 != null && progressDialog2.isShowing()) {
            progressDialog2.dismiss();
        }
        check = 0;
        SelectItem.add("NAME");
        DataSave.add(Deviceposition.get(0));
        sendValue.send("get");
        progressDialog2 = writeDialog(DeviceList.this, getString(R.string.login));
        if (!progressDialog2.isShowing()) {
            progressDialog2.show();
            progressDialog2.setCanceledOnTouchOutside(false);
        }
        new Thread(timedelay).start();
    }

    private Runnable timedelay = new Runnable() {
        @Override
        public void run() {
            try {
                sleep(2000);
                //noinspection StatementWithEmptyBody
                if (Value.Jsonlist.get(check).matches("OVER")) {
                } else {
                    //noinspection deprecation
                    mBluetoothAdapter.stopLeScan(mLeScanCallback);
                    Service_close();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };

    private Dialog writeDialog(Context context, String message) {
        final Dialog progressDialog = new Dialog(context);
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        LayoutInflater inflater = LayoutInflater.from(context);
        @SuppressLint("InflateParams") View v = inflater.inflate(R.layout.running, null);
        LinearLayout layout = v.findViewById(R.id.ll_dialog);
        ProgressBar pb_progress_bar = v.findViewById(R.id.pb_progress_bar);
        pb_progress_bar.setVisibility(View.VISIBLE);
        TextView tv = v.findViewById(R.id.tv_loading);

        if (message == null || message.equals("")) {
            tv.setVisibility(View.GONE);
        } else {
            tv.setText(message);
            tv.setTextColor(context.getResources().getColor(R.color.colorDialog));
        }

        if (Value.all_Height > Value.all_Width) {
            progressDialog.setContentView(layout, new LinearLayout.LayoutParams((int) (Value.all_Width / 2),
                    (int) (Value.all_Height / 5)));
        } else {
            progressDialog.setContentView(layout, new LinearLayout.LayoutParams((int) (Value.all_Width / 4),
                    (int) (Value.all_Height / 3)));
        }

        return progressDialog;
    }

    @SuppressLint("SetTextI18n")
    private void check() throws InterruptedException {

        setContentView(R.layout.checkpassword);

        Button by = findViewById(R.id.button2);
        Button bn = findViewById(R.id.button1);
        TextView t1 = findViewById(R.id.textView3);
        EditText e1 = findViewById(R.id.editText1);

        getW_H();

        progressDialog.dismiss();
        sleep(30);

        t1.setText(getString(R.string.device_name) + "： " + Deviceposition.get(0));
        e1.setKeyListener(DigitsKeyListener.getInstance(".,$%&^!()-_=+';:|}{[]*→←↘↖、，。?~～#€￠" +
                "￡￥abcdefghigklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789@>/<"));
        e1.setInputType(InputType.TYPE_NUMBER_FLAG_SIGNED | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        by.setOnClickListener(v -> {
            vibrator.vibrate(100);
            if (e1.getText().toString().length() == 6) {
                Log.d(TAG, "輸入之密碼 = " + e1.getText().toString().trim());
                if (e1.getText().toString().trim().matches(Value.E_word)) { //工程模式
                    Value.passwordFlag = 1;
                    Log.d(TAG, "管理者 登入");
                    Value.get_noti = true;
                    Engin();
                } else if (e1.getText().toString().trim().matches(Value.P_word)) {  //一般登入
                    Value.Engin = false;
                    Value.passwordFlag = 2;
                    Log.d(TAG, "客戶 登入");
                    Value.get_noti = true;
                    login();
                } else if (e1.getText().toString().trim().matches(Value.I_word)) {  //初始化
                    Toast.makeText(DeviceList.this, getString(R.string.initialization), Toast.LENGTH_SHORT).show();
                    initialDialog = writeDialog(this, getString(R.string.intervalset));
                    initialDialog.show();
                    initialDialog.setCanceledOnTouchOutside(false);
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
                        initialDialog.dismiss();
                        Value.passwordFlag = 3;
                        Log.d(TAG, "開始進行原廠設定");
                        Toast.makeText(DeviceList.this, getString(R.string.complete), Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(DeviceList.this, getString(R.string.passworderror), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(DeviceList.this, getString(R.string.inputerror), Toast.LENGTH_SHORT).show();
            }
        });

        bn.setOnClickListener(v -> {
            vibrator.vibrate(100);
            if (mBluetoothAdapter != null && mBluetoothLeService != null) {
                //noinspection deprecation
                mBluetoothAdapter.stopLeScan(mLeScanCallback);
                Value.connected = false;
                Value.get_noti = false;
                Service_close();
                if (s_connect) {
                    s_connect = false;
                }
            }
            show_device();
        });
    }

    private void Engin() {  //工程模式
        Value.Engin = true;
        if (progressDialog2 != null && progressDialog2.isShowing()) {
            progressDialog2.dismiss();
        }
        check = 0;
        SelectItem.add("NAME");
        DataSave.add(Deviceposition.get(0));
        sendValue.send("get");
        progressDialog2 = writeDialog(DeviceList.this, getString(R.string.login));
        if (!progressDialog2.isShowing()) {
            progressDialog2.show();
            progressDialog2.setCanceledOnTouchOutside(false);
        }
        new Thread(timedelay).start();
    }

    private void Engineer_function() {  //工程模式

        Intent intent = new Intent(DeviceList.this, Engineer.class);

        startActivity(intent);
        progressDialog.dismiss();
        progressDialog2.dismiss();
        finish();
    }

    private void device_function() {    //一般模式

        Intent intent = new Intent(DeviceList.this, DeviceFunction.class);

        startActivity(intent);
        progressDialog.dismiss();
        progressDialog2.dismiss();
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

    private void backtofirst() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public boolean onKeyDown(int key, KeyEvent event) {
        switch (key) {
            case KeyEvent.KEYCODE_SEARCH:
                break;
            case KeyEvent.KEYCODE_BACK:
                if (flag == 0) {
                    vibrator.vibrate(100);
                    NewModel.checkmodel = false;
                    backtofirst();
                } else if (flag == 1) {
                    vibrator.vibrate(100);
                    if (mBluetoothAdapter != null)
                        //noinspection deprecation
                        mBluetoothAdapter.stopLeScan(mLeScanCallback);
                    Value.get_noti = false;
                    Value.connected = false;
                    NewModel.checkmodel = false;
                    Service_close();
                    show_device();
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
            if (flag == 0) {    //flag = 0 → 裝置列表頁面
                show_device();
            } else if (flag == 1) { //flag = 1 → 輸入密碼頁面
                try {
                    check();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {   //直向
            // port do nothing is ok
            if (flag == 0) {    //flag = 0 → 裝置列表頁面
                show_device();
            } else if (flag == 1) { //flag = 1 → 輸入密碼頁面
                try {
                    check();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
