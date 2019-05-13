package com.jetec.nordic_googleplay.NewActivity;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
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
import android.os.IBinder;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.jetec.nordic_googleplay.Activity.SearchActivity;
import com.jetec.nordic_googleplay.NewActivity.Function.SearchNewList;
import com.jetec.nordic_googleplay.NewActivity.Function.SetDateTime;
import com.jetec.nordic_googleplay.NewActivity.Function.SetEditHint;
import com.jetec.nordic_googleplay.NewActivity.GetString.GetUnit;
import com.jetec.nordic_googleplay.NewActivity.Listener.GetLogList;
import com.jetec.nordic_googleplay.NewActivity.Listener.ListViewListener;
import com.jetec.nordic_googleplay.NewModel;
import com.jetec.nordic_googleplay.R;
import com.jetec.nordic_googleplay.Service.BluetoothLeService;
import com.jetec.nordic_googleplay.Value;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class UserSearchList extends AppCompatActivity implements ListViewListener {

    private String TAG = "UserSearchList";
    private Vibrator vibrator;
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothLeService mBluetoothLeService;
    private boolean s_connect = false;
    private Intent intents;
    private String[] default_model;
    private TextView t2, t3;
    private List<String> nameList, timeList;
    private List<String> spinnerList, spinnerList2;
    private List<List<String>> saveList;
    private GetLogList getLogList = new GetLogList();
    private GetUnit getUnit = new GetUnit();
    private SetDateTime setDateTime = new SetDateTime();
    private SearchNewList searchNewList = new SearchNewList();
    private SetEditHint setEditHint = new SetEditHint();
    private String date_time, record, chose1 = "", chose2 = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vibrator = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        }

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
        nameList = new ArrayList<>();
        timeList = new ArrayList<>();
        saveList = new ArrayList<>();
        spinnerList = new ArrayList<>();
        spinnerList2 = new ArrayList<>();
        nameList.clear();
        timeList.clear();
        saveList.clear();
        spinnerList.clear();
        spinnerList2.clear();

        Intent intent = getIntent();
        default_model = intent.getStringArrayExtra("default_model");
        String logjson = NewModel.LogString;
        getLogList.setListener(this);
        getLogList.readytointent(logjson);
        searchmenu();
    }

    private void searchmenu() {
        setContentView(R.layout.searchdatalist);

        Spinner s1 = findViewById(R.id.spinner1);
        Spinner s2 = findViewById(R.id.spinner2);
        EditText e1 = findViewById(R.id.editText1);
        Button b1 = findViewById(R.id.button1);
        Button b2 = findViewById(R.id.button2);
        Button b3 = findViewById(R.id.button3);
        TextView t1 = findViewById(R.id.textView5);
        t2 = findViewById(R.id.textView6);
        t3 = findViewById(R.id.textView7);
        LinearLayout l1 = findViewById(R.id.textlinear);

        date_time = getString(R.string.datetime);    //項目，條件，數值
        record = getString(R.string.size);

        spinnerList.add(getString(R.string.condition1));
        spinnerList.add(date_time);
        spinnerList.add(record);

        for (int i = 0; i < nameList.size(); i++) {
            spinnerList.add(getUnit.getItemString(this, nameList.get(i), i));
        }

        new Thread(timecheck).start();
        new Thread(arrayadd).start();

        s2.setEnabled(false);
        t2.setText("");
        t3.setVisibility(View.GONE);
        e1.setVisibility(View.GONE);
        b1.setVisibility(View.GONE);
        b2.setVisibility(View.GONE);

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this, R.layout.spinner_style, spinnerList) {    //android.R.layout.simple_spinner_item
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                } else {
                    return true;
                }
            }
        };

        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_style);    //R.layout.spinner_style
        s1.setAdapter(spinnerArrayAdapter);
        s1.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                vibrator.vibrate(100);
                e1.setText("");
                Log.e("myLog", "position = " + position);
                if (position == 0) {
                    s2.setEnabled(false);
                    t3.setVisibility(View.GONE);
                    e1.setVisibility(View.GONE);
                    b1.setVisibility(View.GONE);
                    b2.setVisibility(View.GONE);
                } else if (position == 1) {
                    s2.setEnabled(true);
                    e1.setVisibility(View.GONE);
                    t3.setVisibility(View.VISIBLE);
                    b1.setVisibility(View.VISIBLE);
                    b2.setVisibility(View.VISIBLE);
                    l1.setVisibility(View.VISIBLE);
                    chose1 = spinnerList.get(position);
                } else if (position == 2) {
                    s2.setEnabled(true);
                    e1.setVisibility(View.VISIBLE);
                    e1.setInputType(InputType.TYPE_CLASS_NUMBER);
                    e1.setKeyListener(DigitsKeyListener.getInstance("0123456789"));
                    e1.setHint("1 ~ " + timeList.size());
                    t3.setVisibility(View.GONE);
                    b1.setVisibility(View.GONE);
                    b2.setVisibility(View.GONE);
                    l1.setVisibility(View.GONE);
                    chose1 = spinnerList.get(position);
                } else {
                    s2.setEnabled(true);
                    e1.setVisibility(View.VISIBLE);
                    e1.setInputType(InputType.TYPE_NUMBER_FLAG_SIGNED |
                            InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                    //chose_position = position;
                    t3.setVisibility(View.GONE);
                    b1.setVisibility(View.GONE);
                    b2.setVisibility(View.GONE);
                    l1.setVisibility(View.GONE);
                    chose1 = spinnerList.get(position);
                    setEditHint.seteditHint(e1, nameList, position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        ArrayAdapter<String> spinnerArrayAdapter2 = new ArrayAdapter<String>(
                this, R.layout.spinner_style, spinnerList2) {    //android.R.layout.simple_spinner_item
            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                } else {
                    return true;
                }
            }
        };

        spinnerArrayAdapter2.setDropDownViewResource(R.layout.spinner_style);    //R.layout.spinner_style
        s2.setAdapter(spinnerArrayAdapter2);
        s2.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                vibrator.vibrate(100);
                Log.e("myLog", "position2 = " + position);
                chose2 = spinnerList2.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        b1.setOnClickListener(v -> {
            vibrator.vibrate(100);
            setDateTime.datechose(UserSearchList.this, t2);
        });
        b2.setOnClickListener(v -> {
            vibrator.vibrate(100);
            setDateTime.timechose(UserSearchList.this, t2);
        });
        b3.setOnClickListener(v -> {
            vibrator.vibrate(100);
            if (chose1.matches(date_time)) {
                if (!chose2.matches("") && !t2.getText().toString().trim().matches("")) {
                    @SuppressLint("SimpleDateFormat")
                    SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
                    String timecomparison = t2.getText().toString().trim() + ":00";
                    Log.e(TAG, "timecomparison = " + timecomparison);
                    searchNewList.timeSearchList(this, default_model, chose2, sdf,
                            timecomparison, timeList, saveList);
                } else
                    Toast.makeText(this, getString(R.string.wrong), Toast.LENGTH_SHORT).show();
            } else if (chose1.matches(record)) {
                if (!chose2.matches(getString(R.string.condition2)) && !e1.getText().toString().trim().matches(""))
                    calculate(chose1, chose2, e1.getText().toString().trim());
                else
                    Toast.makeText(this, getString(R.string.wrong), Toast.LENGTH_SHORT).show();
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
            } else if (BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED.equals(action)) {
                // Show all the supported services and characteristics on the user interface.
                //displayGattServices(mBluetoothLeService.getSupportedGattServices());
                mBluetoothLeService.enableTXNotification();
                Log.e(TAG, "連線狀態改變");
            } else if (BluetoothLeService.ACTION_DATA_AVAILABLE.equals(action)) {
                runOnUiThread(() -> {
                    /*String currentDateTimeString = DateFormat.getTimeInstance().format(new Date());
                    byte[] txValue = intents.getByteArrayExtra(BluetoothLeService.EXTRA_DATA);
                    String text = new String(txValue, StandardCharsets.UTF_8);
                    StringBuilder hex = new StringBuilder(txValue.length * 2);
                    for (byte aData : txValue) {
                        hex.append(String.format("%02X", aData));
                    }
                    String gethex = hex.toString();*/
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

    public static BluetoothManager getManager(Context context) {    //獲取此設備默認藍芽適配器
        return (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
    }

    private void goback() {
        Intent intent = new Intent(this, UserFunction.class);
        intent.putExtra("default_model", default_model);
        startActivity(intent);
        finish();
    }

    private Runnable timecheck = new Runnable() {
        @SuppressLint("SetTextI18n")
        @Override
        public void run() {
            String f_time = timeList.get(0);
            String e_time = timeList.get(timeList.size() - 1);
            t3.setText(getString(R.string.timerange) + ": " + f_time + " ~ " + e_time);
        }
    };

    private Runnable arrayadd = new Runnable() {
        @Override
        public void run() {
            spinnerList2.add(getString(R.string.condition2));
            spinnerList2.add("＞");
            spinnerList2.add("≧");
            spinnerList2.add("＝");
            spinnerList2.add("≦");
            spinnerList2.add("＜");
        }
    };

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
                goback();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.userlog, menu);
        return true;
    }

    @Override
    public void convert(List<String> nameList, List<String> timeList, List<List<String>> saveList) {
        this.nameList = nameList;
        this.timeList = timeList;
        this.saveList = saveList;
    }

    @Override
    public void makecsv() {
        //do nothing
    }

    @Override
    public void makepdf() {
        //do nothing
    }
}
