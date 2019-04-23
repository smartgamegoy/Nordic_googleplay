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
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import com.jetec.nordic_googleplay.Activity.MainActivity;
import com.jetec.nordic_googleplay.NewActivity.GetString.ByteToHex;
import com.jetec.nordic_googleplay.NewActivity.SendByte.Send;
import com.jetec.nordic_googleplay.NewActivity.ViewAdapter.*;
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
    private List<byte[]> list1, list2, list3, list4, list5, list6, list7;
    private List<View> listview;
    private List<List<byte[]>> savelist;
    private byte[] getbyte = {0x42, 0x59, 0x54, 0x45}, getover = {0x4F, 0x56, 0x45, 0x52};
    private ByteToHex byteToHex = new ByteToHex();
    private Getparse getparse = new Getparse();
    private SetViewPager setViewPager = new SetViewPager();
    private NewModel newModel = new NewModel();
    private LastViewPager lastViewPager = new LastViewPager();
    private SetPagerAdapter setPagerAdapter = new SetPagerAdapter();
    private NameView nameView = new NameView();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vibrator = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);
        BluetoothManager bluetoothManager = getManager(this);
        mBluetoothAdapter = bluetoothManager.getAdapter();
        if (mBluetoothLeService == null) {
            Intent gattServiceIntent = new Intent(this, BluetoothLeService.class);
            s_connect = bindService(gattServiceIntent, mServiceConnection, BIND_AUTO_CREATE);
            if (s_connect) {
                registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter());
            }
            else
                Log.e(TAG, "連線失敗");
        }
        get_intent();
    }

    private void get_intent() {
        Intent intent = getIntent();
        default_model = intent.getStringArrayExtra("default_model");

        listview = new ArrayList<>();
        savelist = new ArrayList<>();
        list1 = new ArrayList<>();
        list2 = new ArrayList<>();
        list3 = new ArrayList<>();
        list4 = new ArrayList<>();
        list5 = new ArrayList<>();
        list6 = new ArrayList<>();
        list7 = new ArrayList<>();

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

        if(list1.size() != 0)
            savelist.add(list1);
        if(list2.size() != 0)
            savelist.add(list2);
        if(list3.size() != 0)
            savelist.add(list3);
        if(list4.size() != 0)
            savelist.add(list4);
        if(list5.size() != 0)
            savelist.add(list5);
        if(list6.size() != 0)
            savelist.add(list6);
        if(list7.size() != 0)
            savelist.add(list7);

        newModel.setString(this);
        NewModel.viewList = savelist;

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
            Value.mBluetoothLeService = mBluetoothLeService;
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
                mBluetoothLeService.enableTXNotification();
                Log.e(TAG, "連線狀態改變");
            } else if (BluetoothLeService.ACTION_DATA_AVAILABLE.equals(action)) {
                runOnUiThread(() -> {
                    try {
                        String currentDateTimeString = DateFormat.getTimeInstance().format(new Date());
                        byte[] txValue = intents.getByteArrayExtra(BluetoothLeService.EXTRA_DATA);
                        String text = new String(txValue, "UTF-8");
                        Log.e(TAG, "[" + currentDateTimeString + "] send: " + text);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                });
            }
        }
    };

    private void showlist() {
        setContentView(R.layout.userlist);

        int count = 0;
        String model = Value.deviceModel;
        String[] arr = model.split("-");
        String name = arr[2];
        List<String> nameList = new ArrayList<>();
        nameList.clear();
        nameList.add("NAME");
        if(name.contains("Y") || name.contains("Z")){
            nameList.add("TIME");
            count++;
        }
        /*if(name.contains("L")){
            nameList.add("INTER");
        }*/
        nameList.add("SPK");
        name = name.replace("Y", "");
        name = name.replace("L", "");
        name = name.replace("Z", "");
        List<Character> ch = new ArrayList<>();
        for (int i = 0; i < name.length(); i++) {
            ch.add(name.charAt(i));
        }
        Log.e(TAG, "name = " + model);
        Log.e(TAG, "ch = " + ch);

        TabLayout tabLayout = findViewById(R.id.tablayout);
        ViewPager viewPager = findViewById(R.id.viewpager);

        tabLayout.addTab(tabLayout.newTab());
        Objects.requireNonNull(tabLayout.getTabAt(0)).setText(getString(R.string.bluetoothset));
        listview.add(nameView.setView(this, nameList, vibrator));

        for (int i = 0; i < ch.size(); i++) {
            //TabLayout.Tab tab = tabLayout.newTab();
            if (ch.get(i).toString().matches("T")) {
                /*tab.setCustomView(setViewPager.setView(this, ch.get(i).toString(), savelist.get(i)));
                tabLayout.addTab(tab);*/
                tabLayout.addTab(tabLayout.newTab());
                Objects.requireNonNull(tabLayout.getTabAt(i + 1)).setText(getString(R.string.bt_t));
                listview.add(setViewPager.setView(this, ch.get(i).toString(), i , vibrator, ch));
                //setPagerAdapter.setView(setViewPager.setView(this, ch.get(i).toString(), savelist.get(i)));
            } else if (ch.get(i).toString().matches("H")) {
                /*tab.setCustomView(setViewPager.setView(this, ch.get(i).toString(), savelist.get(i)));
                tabLayout.addTab(tab);*/
                tabLayout.addTab(tabLayout.newTab());
                Objects.requireNonNull(tabLayout.getTabAt(i + 1)).setText(getString(R.string.bt_h));
                listview.add(setViewPager.setView(this, ch.get(i).toString(), i , vibrator, ch));
                //setPagerAdapter.setView(setViewPager.setView(this, ch.get(i).toString(), savelist.get(i)));
            } else if (ch.get(i).toString().matches("C")) {
                /*tab.setCustomView(setViewPager.setView(this, ch.get(i).toString(), savelist.get(i)));
                tabLayout.addTab(tab);*/
                tabLayout.addTab(tabLayout.newTab());
                Objects.requireNonNull(tabLayout.getTabAt(i + 1)).setText(getString(R.string.bt_c));
                listview.add(setViewPager.setView(this, ch.get(i).toString(), i , vibrator, ch));
                //setPagerAdapter.setView(setViewPager.setView(this, ch.get(i).toString(), savelist.get(i)));
            } else if (ch.get(i).toString().matches("D")) {
                /*tab.setCustomView(setViewPager.setView(this, ch.get(i).toString(), savelist.get(i)));
                tabLayout.addTab(tab);*/
                tabLayout.addTab(tabLayout.newTab());
                Objects.requireNonNull(tabLayout.getTabAt(i + 1)).setText(getString(R.string.bt_d));
                listview.add(setViewPager.setView(this, ch.get(i).toString(), i , vibrator, ch));
                //setPagerAdapter.setView(setViewPager.setView(this, ch.get(i).toString(), savelist.get(i)));
            } else if (ch.get(i).toString().matches("E")) {
                /*tab.setCustomView(setViewPager.setView(this, ch.get(i).toString(), savelist.get(i)));
                tabLayout.addTab(tab);*/
                tabLayout.addTab(tabLayout.newTab());
                Objects.requireNonNull(tabLayout.getTabAt(i + 1)).setText(getString(R.string.bt_e));
                listview.add(setViewPager.setView(this, ch.get(i).toString(), i , vibrator, ch));
            } else if (ch.get(i).toString().matches("I")) {
                /*tab.setCustomView(setViewPager.setView(this, ch.get(i).toString(), savelist.get(i)));
                tabLayout.addTab(tab);*/
                tabLayout.addTab(tabLayout.newTab());
                Objects.requireNonNull(tabLayout.getTabAt(i + 1)).setText(getString(R.string.table_i) + (i + 1 + count) + "\n" + "(4~20mA)");
                listview.add(setViewPager.setView(this, ch.get(i).toString(), i , vibrator, ch));
                //setPagerAdapter.setView(setViewPager.setView(this, ch.get(i).toString(), savelist.get(i)));
            } else if (ch.get(i).toString().matches("S")) {
                /*tab.setCustomView(setViewPager.setView(this, ch.get(i).toString(), savelist.get(i)));
                tabLayout.addTab(tab);*/
                tabLayout.addTab(tabLayout.newTab());
                Objects.requireNonNull(tabLayout.getTabAt(i + 1)).setText(getString(R.string.bt_co));
                listview.add(setViewPager.setView(this, ch.get(i).toString(), i , vibrator, ch));
                //setPagerAdapter.setView(setViewPager.setView(this, ch.get(i).toString(), savelist.get(i)));
            }
        }

        tabLayout.addTab(tabLayout.newTab());
        Objects.requireNonNull(tabLayout.getTabAt(ch.size() + 1)).setText(getString(R.string.out));
        listview.add(lastViewPager.setView(this, vibrator, ch.size()));

        setPagerAdapter.setView(listview);
        viewPager.setAdapter(setPagerAdapter);
        //viewPager.setCurrentItem((listview.size()) * 1000);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
    }

    private BluetoothAdapter.LeScanCallback mLeScanCallback =
            (device, rssi, scanRecord) -> runOnUiThread(() -> runOnUiThread(this::addDevice));

    private void addDevice() {
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
                NewModel.mBluetoothLeService = mBluetoothLeService;
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
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // land do nothing is ok
        } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            // port do nothing is ok
        }
    }
}
