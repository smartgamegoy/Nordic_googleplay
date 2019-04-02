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
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.jetec.nordic_googleplay.Activity.MainActivity;
import com.jetec.nordic_googleplay.DialogFunction.ENGIN;
import com.jetec.nordic_googleplay.EditManagert.EditChangePointer;
import com.jetec.nordic_googleplay.NewActivity.GetString.*;
import com.jetec.nordic_googleplay.NewActivity.SendByte.*;
import com.jetec.nordic_googleplay.NewModel;
import com.jetec.nordic_googleplay.R;
import com.jetec.nordic_googleplay.ScanParse.*;
import com.jetec.nordic_googleplay.SendValue;
import com.jetec.nordic_googleplay.Service.BluetoothLeService;
import com.jetec.nordic_googleplay.Value;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;
import static com.jetec.nordic_googleplay.Activity.DeviceList.getManager;
import static java.lang.Thread.sleep;

public class New_Engin extends AppCompatActivity {

    private Vibrator vibrator;
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothLeService mBluetoothLeService;
    private String[] default_model;
    private boolean s_connect = false, byteconvert = false, b_bool = false, change = false;
    private String TAG = "New_Engin", name;
    private Intent intents;
    private ListView list1;
    private Button b_send, SP1, ER1, SV1, SP2, ER2, SV2, SP3, ER3, SV3;
    private EditText e1;
    private ArrayAdapter<String> listAdapter;
    private SendValue sendValue;
    private Send send;
    private byte[] getbyte = {0x42, 0x59, 0x54, 0x45}, getover = {0x4F, 0x56, 0x45, 0x52},
            getDate = {0x44, 0x41, 0x54, 0x45}, getTime = {0x54, 0x49, 0x4D, 0x45},
            getPWR = {0x50, 0x57, 0x52, 0x3D}, getName = {0x4E, 0x41, 0x4D, 0x45};
    private ByteToHex byteToHex = new ByteToHex();
    private Getparse getparse = new Getparse();
    private Initialization initialization = new Initialization();

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

        showlist();
    }

    private void showlist() {

        setContentView(R.layout.engineer_title);

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        myToolbar.setTitle(Value.deviceModel);

        ENGIN engin = new ENGIN(this);

        list1 = findViewById(R.id.listMessage);
        e1 = findViewById(R.id.sendText);
        b_send = findViewById(R.id.sendButton);
        Button fix = findViewById(R.id.button);
        SP1 = findViewById(R.id.button2);
        ER1 = findViewById(R.id.button3);
        SV1 = findViewById(R.id.button4);
        Button GET = findViewById(R.id.button5);
        SP2 = findViewById(R.id.button6);
        ER2 = findViewById(R.id.button7);
        SV2 = findViewById(R.id.button8);
        Button init = findViewById(R.id.button9);
        SP3 = findViewById(R.id.button10);
        ER3 = findViewById(R.id.button11);
        SV3 = findViewById(R.id.button12);

        b_send.setText("string");
        fix.setText("zerospn");
        if(!change)
            GET.setText("get");
        else
            GET.setText("fix");

        listAdapter = new ArrayAdapter<>(this, R.layout.message_detail);
        list1.setAdapter(listAdapter);
        list1.setDivider(null);

        String model = Value.deviceModel;
        String[] modelname = model.split("-");
        name = modelname[2];
        Log.e(TAG, "str2 = " + name);

        if (b_bool) {
            b_send.setText("byte");
        } else {
            b_send.setText("string");
        }

        b_send.setOnClickListener(v -> {
            vibrator.vibrate(100);
            String message = e1.getText().toString().trim();
            if (!message.matches("")) {
                if(b_send.getText().toString().trim().matches("string")) {
                    try {
                        byteconvert = false;
                        String currentDateTimeString = DateFormat.getTimeInstance().format(new Date());
                        sendValue = new SendValue(mBluetoothLeService);
                        sendValue.send(message);
                        sleep(100);
                        listAdapter.add("[" + currentDateTimeString + "] send: " + message);
                        list1.smoothScrollToPosition(listAdapter.getCount() - 1);
                        e1.setText("");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                else if(b_send.getText().toString().trim().matches("byte")){
                    try {
                        byteconvert = true;
                        String currentDateTimeString = DateFormat.getTimeInstance().format(new Date());
                        send = new Send(mBluetoothLeService);
                        message = message.toUpperCase();
                        send.sendByte(message);
                        sleep(100);
                        listAdapter.add("[" + currentDateTimeString + "] send: " + message);
                        list1.smoothScrollToPosition(listAdapter.getCount() - 1);
                        e1.setText("");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        fix.setOnClickListener(v -> {
            vibrator.vibrate(100);
            change = !change;
            if (change) {
                GET.setText("fix");
                SP1.setText("SP4");
                ER1.setText("ER4");
                SV1.setText("SV4");
                SP2.setText("SP5");
                ER2.setText("ER5");
                SV2.setText("SV5");
                SP3.setText("SP6");
                ER3.setText("ER6");
                SV3.setText("SV6");
            } else {
                GET.setText("get");
                SP1.setText("SP1");
                ER1.setText("ER1");
                SV1.setText("SV1");
                SP2.setText("SP2");
                ER2.setText("ER2");
                SV2.setText("SV2");
                SP3.setText("SP3");
                ER3.setText("ER3");
                SV3.setText("SV3");
            }
        });

        SP1.setOnClickListener(v -> {
            vibrator.vibrate(100);
            String message = e1.getText().toString().trim();
            if (!message.matches("")) {
                try {
                    byteconvert = false;
                    if(change){
                        message = engin.todo(Float.valueOf(message), "SP4", message);
                    }
                    else {
                        message = engin.todo(Float.valueOf(message), "SP1", message);
                    }
                    String currentDateTimeString = DateFormat.getTimeInstance().format(new Date());
                    sendValue = new SendValue(mBluetoothLeService);
                    sendValue.send(message);
                    sleep(100);
                    listAdapter.add("[" + currentDateTimeString + "] send: " + message);
                    list1.smoothScrollToPosition(listAdapter.getCount() - 1);
                    e1.setText("");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        ER1.setOnClickListener(v -> {
            vibrator.vibrate(100);
            String message = e1.getText().toString().trim();
            if (!message.matches("")) {
                try {
                    byteconvert = false;
                    if(change){
                        message = engin.todo(Float.valueOf(message), "ER4", message);
                    }
                    else {
                        message = engin.todo(Float.valueOf(message), "ER1", message);
                    }
                    String currentDateTimeString = DateFormat.getTimeInstance().format(new Date());
                    sendValue = new SendValue(mBluetoothLeService);
                    sendValue.send(message);
                    sleep(100);
                    listAdapter.add("[" + currentDateTimeString + "] send: " + message);
                    list1.smoothScrollToPosition(listAdapter.getCount() - 1);
                    e1.setText("");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        SV1.setOnClickListener(v -> {
            vibrator.vibrate(100);
            String message = e1.getText().toString().trim();
            if (!message.matches("")) {
                try {
                    byteconvert = false;
                    if(change){
                        message = engin.todo(Float.valueOf(message), "SV4", message);
                    }
                    else {
                        message = engin.todo(Float.valueOf(message), "SV1", message);
                    }
                    String currentDateTimeString = DateFormat.getTimeInstance().format(new Date());
                    sendValue = new SendValue(mBluetoothLeService);
                    sendValue.send(message);
                    sleep(100);
                    listAdapter.add("[" + currentDateTimeString + "] send: " + message);
                    list1.smoothScrollToPosition(listAdapter.getCount() - 1);
                    e1.setText("");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        GET.setOnClickListener(v -> {
            vibrator.vibrate(100);
            if(change){
                try {
                    byteconvert = false;
                    String currentDateTimeString = DateFormat.getTimeInstance().format(new Date());
                    sendValue = new SendValue(mBluetoothLeService);
                    sendValue.send("FIX");
                    sleep(100);
                    listAdapter.add("[" + currentDateTimeString + "] send: " + "FIX");
                    list1.smoothScrollToPosition(listAdapter.getCount() - 1);
                    e1.setText("");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            else {
                try {
                    String currentDateTimeString = DateFormat.getTimeInstance().format(new Date());
                    sendValue = new SendValue(mBluetoothLeService);
                    sendValue.send("get");
                    sleep(100);
                    listAdapter.add("[" + currentDateTimeString + "] send: " + "get");
                    list1.smoothScrollToPosition(listAdapter.getCount() - 1);
                    e1.setText("");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        SP2.setOnClickListener(v -> {
            vibrator.vibrate(100);
            String message = e1.getText().toString().trim();
            if (!message.matches("")) {
                try {
                    byteconvert = false;
                    if(change){
                        message = engin.todo(Float.valueOf(message), "SV5", message);
                    }
                    else {
                        message = engin.todo(Float.valueOf(message), "SP2", message);
                    }
                    String currentDateTimeString = DateFormat.getTimeInstance().format(new Date());
                    sendValue = new SendValue(mBluetoothLeService);
                    sendValue.send(message);
                    sleep(100);
                    listAdapter.add("[" + currentDateTimeString + "] send: " + message);
                    list1.smoothScrollToPosition(listAdapter.getCount() - 1);
                    e1.setText("");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        ER2.setOnClickListener(v -> {
            vibrator.vibrate(100);
            String message = e1.getText().toString().trim();
            if (!message.matches("")) {
                try {
                    byteconvert = false;
                    if(change){
                        message = engin.todo(Float.valueOf(message), "ER5", message);
                    }
                    else {
                        message = engin.todo(Float.valueOf(message), "ER2", message);
                    }
                    String currentDateTimeString = DateFormat.getTimeInstance().format(new Date());
                    sendValue = new SendValue(mBluetoothLeService);
                    sendValue.send(message);
                    sleep(100);
                    listAdapter.add("[" + currentDateTimeString + "] send: " + message);
                    list1.smoothScrollToPosition(listAdapter.getCount() - 1);
                    e1.setText("");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        SV2.setOnClickListener(v -> {
            vibrator.vibrate(100);
            String message = e1.getText().toString().trim();
            if (!message.matches("")) {
                try {
                    byteconvert = false;
                    if(change){
                        message = engin.todo(Float.valueOf(message), "SV5", message);
                    }
                    else {
                        message = engin.todo(Float.valueOf(message), "SV2", message);
                    }
                    String currentDateTimeString = DateFormat.getTimeInstance().format(new Date());
                    sendValue = new SendValue(mBluetoothLeService);
                    sendValue.send(message);
                    sleep(100);
                    listAdapter.add("[" + currentDateTimeString + "] send: " + message);
                    list1.smoothScrollToPosition(listAdapter.getCount() - 1);
                    e1.setText("");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        init.setOnClickListener(v -> {
            vibrator.vibrate(100);
            byteconvert = false;
            sendValue = new SendValue(mBluetoothLeService);
            String str = Value.deviceModel;
            String[] arr = str.split("-");
            String str2 = arr[2];
            Log.e(TAG, "str2 = " + str2);
            String sp, er, sv;
            int j = 1;
            //noinspection MismatchedQueryAndUpdateOfCollection
            for (int i = 0; i < str2.length(); i++) {
                if (str2.charAt(i) == 'I') {
                    try {
                        sp = "SP" + j + "+1250.0";
                        showsending(sp);
                        sendValue.send(sp);
                        sleep(100);
                        er = "ER" + j + "+1000.0";
                        showsending(er);
                        sendValue.send(er);
                        sleep(100);
                        sv = "SV" + j + "+0000.0";
                        showsending(sv);
                        sendValue.send(sv);
                        sleep(100);
                        j = j + 1;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        SP3.setOnClickListener(v -> {
            vibrator.vibrate(100);
            String message = e1.getText().toString().trim();
            if (!message.matches("")) {
                try {
                    byteconvert = false;
                    if(change){
                        message = engin.todo(Float.valueOf(message), "SP6", message);
                    }
                    else {
                        message = engin.todo(Float.valueOf(message), "SP3", message);
                    }
                    String currentDateTimeString = DateFormat.getTimeInstance().format(new Date());
                    sendValue = new SendValue(mBluetoothLeService);
                    sendValue.send(message);
                    sleep(100);
                    listAdapter.add("[" + currentDateTimeString + "] send: " + message);
                    list1.smoothScrollToPosition(listAdapter.getCount() - 1);
                    e1.setText("");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        ER3.setOnClickListener(v -> {
            vibrator.vibrate(100);
            String message = e1.getText().toString().trim();
            if (!message.matches("")) {
                try {
                    byteconvert = false;
                    if(change){
                        message = engin.todo(Float.valueOf(message), "ER6", message);
                    }
                    else {
                        message = engin.todo(Float.valueOf(message), "ER3", message);
                    }
                    String currentDateTimeString = DateFormat.getTimeInstance().format(new Date());
                    sendValue = new SendValue(mBluetoothLeService);
                    sendValue.send(message);
                    sleep(100);
                    listAdapter.add("[" + currentDateTimeString + "] send: " + message);
                    list1.smoothScrollToPosition(listAdapter.getCount() - 1);
                    e1.setText("");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        SV3.setOnClickListener(v -> {
            vibrator.vibrate(100);
            String message = e1.getText().toString().trim();
            if (!message.matches("")) {
                try {
                    byteconvert = false;
                    if(change){
                        message = engin.todo(Float.valueOf(message), "SV6", message);
                    }
                    else {
                        message = engin.todo(Float.valueOf(message), "SV3", message);
                    }
                    String currentDateTimeString = DateFormat.getTimeInstance().format(new Date());
                    sendValue = new SendValue(mBluetoothLeService);
                    sendValue.send(message);
                    sleep(100);
                    listAdapter.add("[" + currentDateTimeString + "] send: " + message);
                    list1.smoothScrollToPosition(listAdapter.getCount() - 1);
                    e1.setText("");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
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
                String currentDateTimeString = DateFormat.getTimeInstance().format(new Date());
                listAdapter.add("[" + currentDateTimeString + "] 連線中斷: " + "自動重連中...");
            } else if (BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED.equals(action)) {
                // Show all the supported services and characteristics on the user interface.
                //displayGattServices(mBluetoothLeService.getSupportedGattServices());
                Log.e(TAG, "連線狀態改變");
                mBluetoothLeService.enableTXNotification();
                String currentDateTimeString = DateFormat.getTimeInstance().format(new Date());
                listAdapter.add("[" + currentDateTimeString + "] 連線狀態: " + "已重新連線");
            } else if (BluetoothLeService.ACTION_DATA_AVAILABLE.equals(action)) {
                runOnUiThread(() -> {
                    try {
                        String currentDateTimeString = DateFormat.getTimeInstance().format(new Date());
                        byte[] txValue = intents.getByteArrayExtra(BluetoothLeService.EXTRA_DATA);
                        if (Arrays.equals(getbyte, txValue)) {
                            byteconvert = true;
                            showhex(txValue);
                        } else if (Arrays.equals(getover, txValue)) {
                            byteconvert = false;
                            getparse.recodesub();
                            showhex(txValue);
                        } else {
                            if (byteconvert) {
                                byte[] data = Arrays.copyOfRange(txValue, 0, 4);
                                if(Arrays.equals(data, getDate)){
                                    String text = new String(txValue, "UTF-8");
                                    listAdapter.add("[" + currentDateTimeString + "] RE: " + text);
                                    list1.smoothScrollToPosition(listAdapter.getCount() - 1);
                                }
                                else if(Arrays.equals(data, getTime)){
                                    String text = new String(txValue, "UTF-8");
                                    listAdapter.add("[" + currentDateTimeString + "] RE: " + text);
                                    list1.smoothScrollToPosition(listAdapter.getCount() - 1);
                                }
                                else if(Arrays.equals(data, getPWR)){
                                    String text = new String(txValue, "UTF-8");
                                    listAdapter.add("[" + currentDateTimeString + "] RE: " + text);
                                    list1.smoothScrollToPosition(listAdapter.getCount() - 1);
                                    getparse.recodesub();
                                }
                                else if(Arrays.equals(data, getName)){
                                    String text = new String(txValue, "UTF-8");
                                    listAdapter.add("[" + currentDateTimeString + "] RE: " + text);
                                    list1.smoothScrollToPosition(listAdapter.getCount() - 1);
                                }
                                else
                                    showhex(txValue);
                            } else {
                                String text = new String(txValue, "UTF-8");
                                listAdapter.add("[" + currentDateTimeString + "] RE: " + text);
                                list1.smoothScrollToPosition(listAdapter.getCount() - 1);
                            }
                        }
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                });
            }
        }
    };

    private void showhex(byte[] txValue) {
        String currentDateTimeString = DateFormat.getTimeInstance().format(new Date());
        String[] arr = byteToHex.hexstring(txValue);
        StringBuilder str = new StringBuilder();
        String str2 = "";
        for (String s : arr) {
            str.append(s).append(" ");
        }
        if (txValue.length == 4) {
            if (Arrays.equals(getbyte, txValue)) {
                str2 = "BYTE";
            } else if (Arrays.equals(getover, txValue)) {
                str2 = "OVER";
            }
        } else {
            str2 = getparse.parsebyte(txValue);
        }
        listAdapter.add("[" + currentDateTimeString + "] RE: " + str2);
        listAdapter.add("[" + currentDateTimeString + "] RE: " + str);
        list1.smoothScrollToPosition(listAdapter.getCount() - 1);
    }

    public static IntentFilter makeGattUpdateIntentFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_CONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_DISCONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED);
        intentFilter.addAction(BluetoothLeService.ACTION_DATA_AVAILABLE);
        return intentFilter;
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

    private void resetmodel() {
        Intent intent = new Intent(this, ErrActivity.class);
        NewModel.checkmodel = false;
        intent.putExtra("default_model", default_model);
        startActivity(intent);
        finish();
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
        NewModel.checkmodel = false;
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
            case KeyEvent.KEYCODE_BACK: {
                vibrator.vibrate(100);
                new AlertDialog.Builder(this)
                        .setTitle("結束連線")
                        .setMessage("斷開藍牙")
                        .setPositiveButton(R.string.butoon_yes, (dialog, which) -> {
                            vibrator.vibrate(100);
                            if (mBluetoothAdapter != null)
                                //noinspection deprecation
                                mBluetoothAdapter.stopLeScan(mLeScanCallback);
                            Service_close();
                            Value.YMD = false;
                            Value.connected = false;
                            Value.deviceModel = "";
                            Value.BID = "";
                            Value.BName = "";
                            disconnect();
                        })
                        .setNeutralButton(R.string.butoon_no, (dialog, which) -> {
                        })
                        .show();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.new_engin, menu);
        return true;
    }

    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem value = menu.findItem(R.id.string_byte);
        if (b_bool) {
            value.setTitle("string");
        } else {
            value.setTitle("byte");
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {   //toolbar menu item
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            vibrator.vibrate(100);
            /*Intent intent = new Intent(this, DeviceEngineer.class);
            startActivity(intent);
            finish();*/
            return true;
        } else if (id == R.id.reset) {
            vibrator.vibrate(100);
            byteconvert = true;
            initialization.setinitial(name, mBluetoothLeService);
            initialization.startinitial();
            initialization.initialname();
            return true;
        } else if (id == R.id.string_byte) {
            vibrator.vibrate(100);
            b_bool = !b_bool;
            if (b_bool) {
                e1.setKeyListener(DigitsKeyListener.getInstance("0123456789abcdefABCDEF"));
                e1.addTextChangedListener(new EditChangePointer(e1));
                b_send.setText("byte");
            } else {
                e1.setInputType(InputType.TYPE_CLASS_TEXT);
                b_send.setText("string");
            }
            return true;
        } else if (id == R.id.resetmodel) {
            vibrator.vibrate(100);
            resetmodel();
            return true;
        }

        return true;
    }

    public void Service_close() {
        if (mBluetoothLeService == null) {
            Log.e(TAG, "masaga");
            return;
        }
        mBluetoothLeService.disconnect();
    }

    private void disconnect() {
        Intent intent = new Intent(this, MainActivity.class);
        //Value.Jsonlist.clear();
        startActivity(intent);
        finish();
    }

    private BluetoothAdapter.LeScanCallback mLeScanCallback =
            (device, rssi, scanRecord) -> runOnUiThread(() -> runOnUiThread(this::addDevice));

    private void addDevice() {
    }

    private void showsending(String message) {
        String currentDateTimeString = DateFormat.getTimeInstance().format(new Date());
        listAdapter.add("[" + currentDateTimeString + "] send: " + message);
        list1.smoothScrollToPosition(listAdapter.getCount() - 1);
    }
}
