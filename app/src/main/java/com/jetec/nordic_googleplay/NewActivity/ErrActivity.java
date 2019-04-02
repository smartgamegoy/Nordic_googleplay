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
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jetec.nordic_googleplay.Activity.DeviceList;
import com.jetec.nordic_googleplay.Dialog.WriteDialog;
import com.jetec.nordic_googleplay.NewActivity.SendByte.Initialization;
import com.jetec.nordic_googleplay.NewModel;
import com.jetec.nordic_googleplay.R;
import com.jetec.nordic_googleplay.SendValue;
import com.jetec.nordic_googleplay.Service.BluetoothLeService;
import com.jetec.nordic_googleplay.Value;

import java.nio.charset.StandardCharsets;

import static com.jetec.nordic_googleplay.Activity.DeviceList.getManager;
import static java.lang.Thread.sleep;

public class ErrActivity extends AppCompatActivity {

    private String TAG = "CheckPassword";
    private Vibrator vibrator;
    private Intent intents;
    private String[] default_model;
    private BluetoothLeService mBluetoothLeService;
    private BluetoothAdapter mBluetoothAdapter;
    private boolean s_connect = false, log = false;
    private SendValue sendValue;
    private WriteDialog writeDialog = new WriteDialog();
    private Initialization initialization = new Initialization();
    private String str = "", getchar = "", modelhead = "BT-", dash = "-", newmodel = "N", allstr = "";

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

        setmodel();
    }

    @SuppressLint("SetTextI18n")
    private void setmodel() {
        setContentView(R.layout.errdialog);

        Button by = findViewById(R.id.button);
        Button bn = findViewById(R.id.button2);
        Button bt = findViewById(R.id.button3);
        Button bh = findViewById(R.id.button4);
        Button bc = findViewById(R.id.button5);
        Button bd = findViewById(R.id.button6);
        Button be = findViewById(R.id.button7);
        Button b_co = findViewById(R.id.button8);
        Button bi = findViewById(R.id.button9);
        Button b_time = findViewById(R.id.button10);
        Button b_ztime = findViewById(R.id.button14);
        Button b_log = findViewById(R.id.button11);
        Button b_back = findViewById(R.id.button12);
        Button b_reset = findViewById(R.id.button13);
        TextView textView = findViewById(R.id.textView2);

        bn.setOnClickListener(v -> {
            vibrator.vibrate(100);
            devicelist();
        });

        by.setOnClickListener(v -> {
            try {
                vibrator.vibrate(100);
                if(!str.matches("")) {
                    sendValue.send(allstr);
                    initialization.setinitial(str, mBluetoothLeService);
                    sleep(2000);
                    initialization.startinitial();
                    devicelist();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        bt.setOnClickListener(v -> {
            vibrator.vibrate(100);
            getchar = "T";
            if (!log) {
                if (str.length() < 6) {
                    if (!log) {
                        str = str + getchar;
                        int count = str.length();
                        textView.setText(modelhead + count + dash + str + dash + newmodel);
                        allstr = modelhead + count + dash + str + dash + newmodel;
                    } else {
                        str = str.substring(0, str.length() - 1) + getchar + str.substring(str.length() - 1);
                        int count = str.length() - 1;
                        textView.setText(modelhead + count + dash + str + dash + newmodel);
                        allstr = modelhead + count + dash + str + dash + newmodel;
                    }
                }
            } else {
                if (str.length() < 7) {
                    if (!log) {
                        str = str + getchar;
                        int count = str.length();
                        textView.setText(modelhead + count + dash + str + dash + newmodel);
                        allstr = modelhead + count + dash + str + dash + newmodel;
                    } else {
                        str = str.substring(0, str.length() - 1) + getchar + str.substring(str.length() - 1);
                        int count = str.length() - 1;
                        textView.setText(modelhead + count + dash + str + dash + newmodel);
                        allstr = modelhead + count + dash + str + dash + newmodel;
                    }
                }
            }
        });

        bh.setOnClickListener(v -> {
            vibrator.vibrate(100);
            getchar = "H";
            if (!log) {
                if (str.length() < 6) {
                    if (!log) {
                        str = str + getchar;
                        int count = str.length();
                        textView.setText(modelhead + count + dash + str + dash + newmodel);
                        allstr = modelhead + count + dash + str + dash + newmodel;
                    } else {
                        str = str.substring(0, str.length() - 1) + getchar + str.substring(str.length() - 1);
                        int count = str.length() - 1;
                        textView.setText(modelhead + count + dash + str + dash + newmodel);
                        allstr = modelhead + count + dash + str + dash + newmodel;
                    }
                }
            } else {
                if (str.length() < 7) {
                    if (!log) {
                        str = str + getchar;
                        int count = str.length();
                        textView.setText(modelhead + count + dash + str + dash + newmodel);
                        allstr = modelhead + count + dash + str + dash + newmodel;
                    } else {
                        str = str.substring(0, str.length() - 1) + getchar + str.substring(str.length() - 1);
                        int count = str.length() - 1;
                        textView.setText(modelhead + count + dash + str + dash + newmodel);
                        allstr = modelhead + count + dash + str + dash + newmodel;
                    }
                }
            }
        });

        bc.setOnClickListener(v -> {
            vibrator.vibrate(100);
            getchar = "C";
            if (!log) {
                if (str.length() < 6) {
                    if (!log) {
                        str = str + getchar;
                        int count = str.length();
                        textView.setText(modelhead + count + dash + str + dash + newmodel);
                        allstr = modelhead + count + dash + str + dash + newmodel;
                    } else {
                        str = str.substring(0, str.length() - 1) + getchar + str.substring(str.length() - 1);
                        int count = str.length() - 1;
                        textView.setText(modelhead + count + dash + str + dash + newmodel);
                        allstr = modelhead + count + dash + str + dash + newmodel;
                    }
                }
            } else {
                if (str.length() < 7) {
                    if (!log) {
                        str = str + getchar;
                        int count = str.length();
                        textView.setText(modelhead + count + dash + str + dash + newmodel);
                        allstr = modelhead + count + dash + str + dash + newmodel;
                    } else {
                        str = str.substring(0, str.length() - 1) + getchar + str.substring(str.length() - 1);
                        int count = str.length() - 1;
                        textView.setText(modelhead + count + dash + str + dash + newmodel);
                        allstr = modelhead + count + dash + str + dash + newmodel;
                    }
                }
            }
        });

        bd.setOnClickListener(v -> {
            vibrator.vibrate(100);
            getchar = "D";
            if (!log) {
                if (str.length() < 6) {
                    if (!log) {
                        str = str + getchar;
                        int count = str.length();
                        textView.setText(modelhead + count + dash + str + dash + newmodel);
                        allstr = modelhead + count + dash + str + dash + newmodel;
                    } else {
                        str = str.substring(0, str.length() - 1) + getchar + str.substring(str.length() - 1);
                        int count = str.length() - 1;
                        textView.setText(modelhead + count + dash + str + dash + newmodel);
                        allstr = modelhead + count + dash + str + dash + newmodel;
                    }
                }
            } else {
                if (str.length() < 7) {
                    if (!log) {
                        str = str + getchar;
                        int count = str.length();
                        textView.setText(modelhead + count + dash + str + dash + newmodel);
                        allstr = modelhead + count + dash + str + dash + newmodel;
                    } else {
                        str = str.substring(0, str.length() - 1) + getchar + str.substring(str.length() - 1);
                        int count = str.length() - 1;
                        textView.setText(modelhead + count + dash + str + dash + newmodel);
                        allstr = modelhead + count + dash + str + dash + newmodel;
                    }
                }
            }
        });

        be.setOnClickListener(v -> {
            vibrator.vibrate(100);
            getchar = "E";
            if (!log) {
                if (str.length() < 6) {
                    if (!log) {
                        str = str + getchar;
                        int count = str.length();
                        textView.setText(modelhead + count + dash + str + dash + newmodel);
                        allstr = modelhead + count + dash + str + dash + newmodel;
                    } else {
                        str = str.substring(0, str.length() - 1) + getchar + str.substring(str.length() - 1);
                        int count = str.length() - 1;
                        textView.setText(modelhead + count + dash + str + dash + newmodel);
                        allstr = modelhead + count + dash + str + dash + newmodel;
                    }
                }
            } else {
                if (str.length() < 7) {
                    if (!log) {
                        str = str + getchar;
                        int count = str.length();
                        textView.setText(modelhead + count + dash + str + dash + newmodel);
                        allstr = modelhead + count + dash + str + dash + newmodel;
                    } else {
                        str = str.substring(0, str.length() - 1) + getchar + str.substring(str.length() - 1);
                        int count = str.length() - 1;
                        textView.setText(modelhead + count + dash + str + dash + newmodel);
                        allstr = modelhead + count + dash + str + dash + newmodel;
                    }
                }
            }
        });

        b_co.setOnClickListener(v -> {
            vibrator.vibrate(100);
            getchar = "S";
            if (!log) {
                if (str.length() < 6) {
                    if (!log) {
                        str = str + getchar;
                        int count = str.length();
                        textView.setText(modelhead + count + dash + str + dash + newmodel);
                        allstr = modelhead + count + dash + str + dash + newmodel;
                    } else {
                        str = str.substring(0, str.length() - 1) + getchar + str.substring(str.length() - 1);
                        int count = str.length() - 1;
                        textView.setText(modelhead + count + dash + str + dash + newmodel);
                        allstr = modelhead + count + dash + str + dash + newmodel;
                    }
                }
            } else {
                if (str.length() < 7) {
                    if (!log) {
                        str = str + getchar;
                        int count = str.length();
                        textView.setText(modelhead + count + dash + str + dash + newmodel);
                        allstr = modelhead + count + dash + str + dash + newmodel;
                    } else {
                        str = str.substring(0, str.length() - 1) + getchar + str.substring(str.length() - 1);
                        int count = str.length() - 1;
                        textView.setText(modelhead + count + dash + str + dash + newmodel);
                        allstr = modelhead + count + dash + str + dash + newmodel;
                    }
                }
            }
        });

        bi.setOnClickListener(v -> {
            vibrator.vibrate(100);
            getchar = "I";
            if (!log) {
                if (str.length() < 6) {
                    if (!log) {
                        str = str + getchar;
                        int count = str.length();
                        textView.setText(modelhead + count + dash + str + dash + newmodel);
                        allstr = modelhead + count + dash + str + dash + newmodel;
                    } else {
                        str = str.substring(0, str.length() - 1) + getchar + str.substring(str.length() - 1);
                        int count = str.length() - 1;
                        textView.setText(modelhead + count + dash + str + dash + newmodel);
                        allstr = modelhead + count + dash + str + dash + newmodel;
                    }
                }
            } else {
                if (str.length() < 7) {
                    if (!log) {
                        str = str + getchar;
                        int count = str.length();
                        textView.setText(modelhead + count + dash + str + dash + newmodel);
                        allstr = modelhead + count + dash + str + dash + newmodel;
                    } else {
                        str = str.substring(0, str.length() - 1) + getchar + str.substring(str.length() - 1);
                        int count = str.length() - 1;
                        textView.setText(modelhead + count + dash + str + dash + newmodel);
                        allstr = modelhead + count + dash + str + dash + newmodel;
                    }
                }
            }
        });

        b_time.setOnClickListener(v -> {
            vibrator.vibrate(100);
            getchar = "Y";
            if (!log) {
                if (str.length() < 6) {
                    if (!log) {
                        str = str + getchar;
                        int count = str.length();
                        textView.setText(modelhead + count + dash + str + dash + newmodel);
                        allstr = modelhead + count + dash + str + dash + newmodel;
                    } else {
                        str = str.substring(0, str.length() - 1) + getchar + str.substring(str.length() - 1);
                        int count = str.length() - 1;
                        textView.setText(modelhead + count + dash + str + dash + newmodel);
                        allstr = modelhead + count + dash + str + dash + newmodel;
                    }
                }
            } else {
                if (str.length() < 7) {
                    if (!log) {
                        str = str + getchar;
                        int count = str.length();
                        textView.setText(modelhead + count + dash + str + dash + newmodel);
                        allstr = modelhead + count + dash + str + dash + newmodel;
                    } else {
                        str = str.substring(0, str.length() - 1) + getchar + str.substring(str.length() - 1);
                        int count = str.length() - 1;
                        textView.setText(modelhead + count + dash + str + dash + newmodel);
                        allstr = modelhead + count + dash + str + dash + newmodel;
                    }
                }
            }
        });

        b_ztime.setOnClickListener(v -> {
            vibrator.vibrate(100);
            getchar = "Z";
            if (!log) {
                if (str.length() < 6) {
                    if (!log) {
                        str = str + getchar;
                        int count = str.length();
                        textView.setText(modelhead + count + dash + str + dash + newmodel);
                        allstr = modelhead + count + dash + str + dash + newmodel;
                    } else {
                        str = str.substring(0, str.length() - 1) + getchar + str.substring(str.length() - 1);
                        int count = str.length() - 1;
                        textView.setText(modelhead + count + dash + str + dash + newmodel);
                        allstr = modelhead + count + dash + str + dash + newmodel;
                    }
                }
            } else {
                if (str.length() < 7) {
                    if (!log) {
                        str = str + getchar;
                        int count = str.length();
                        textView.setText(modelhead + count + dash + str + dash + newmodel);
                        allstr = modelhead + count + dash + str + dash + newmodel;
                    } else {
                        str = str.substring(0, str.length() - 1) + getchar + str.substring(str.length() - 1);
                        int count = str.length() - 1;
                        textView.setText(modelhead + count + dash + str + dash + newmodel);
                        allstr = modelhead + count + dash + str + dash + newmodel;
                    }
                }
            }
        });

        b_log.setOnClickListener(v -> {
            vibrator.vibrate(100);
            if (str.length() <= 6 && !str.matches("")) {
                log = true;
                getchar = "L";
                str = str + getchar;
                int count = str.length() - 1;
                textView.setText(modelhead + count + dash + str + dash + newmodel);
                allstr = modelhead + count + dash + str + dash + newmodel;
            }
        });

        b_back.setOnClickListener(v -> {
            vibrator.vibrate(100);
            if (str.length() > 0) {
                if (str.length() > 1) {
                    if (log) {
                        log = false;
                        str = str.substring(0, str.length() - 1);
                        int count = str.length();
                        textView.setText(modelhead + count + dash + str + dash + newmodel);
                        allstr = modelhead + count + dash + str + dash + newmodel;
                    } else {
                        str = str.substring(0, str.length() - 1);
                        int count = str.length();
                        textView.setText(modelhead + count + dash + str + dash + newmodel);
                        allstr = modelhead + count + dash + str + dash + newmodel;
                    }
                } else {
                    log = false;
                    str = "";
                    textView.setText("");
                    allstr = "";
                }
            }
        });

        b_reset.setOnClickListener(v -> {
            vibrator.vibrate(100);
            log = false;
            str = "";
            textView.setText("");
            allstr = "";
        });
    }

    private void devicelist() {

        Intent intent = new Intent(this, DeviceList.class);
        NewModel.checkmodel = false;
        intent.putExtra("default_model", default_model);
        startActivity(intent);
        finish();
    }

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

    public final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            intents = intent;
            final String action = intent.getAction();
            if (BluetoothLeService.ACTION_GATT_CONNECTED.equals(action)) {
                Log.d(TAG, "連線成功");
            } else if (BluetoothLeService.ACTION_GATT_DISCONNECTED.equals(action)) {
                //s_connect = false;
                Log.d(TAG, "連線中斷" + Value.connected);
            } else if (BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED.equals(action)) {
                // Show all the supported services and characteristics on the user interface.
                //displayGattServices(mBluetoothLeService.getSupportedGattServices());
                Log.d(TAG, "連線狀態改變");
                mBluetoothLeService.enableTXNotification();
                sendValue = new SendValue(mBluetoothLeService);
            } else if (BluetoothLeService.ACTION_DATA_AVAILABLE.equals(action)) {
                runOnUiThread(() -> {
                    byte[] txValue = intents.getByteArrayExtra(BluetoothLeService.EXTRA_DATA);
                    String text = new String(txValue, StandardCharsets.UTF_8);

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

    private void Service_close() {  //結束配對
        if (mBluetoothLeService == null) {
            Log.d(TAG, "service close!");
            return;
        }
        mBluetoothLeService.disconnect();
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
        Value.YMD = false;
        NewModel.checkmodel = false;
        Service_close();
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
        } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {   //直向
            // port do nothing is ok
        }
    }
}
