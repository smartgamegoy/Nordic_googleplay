package com.jetec.nordic_googleplay.NewActivity;

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
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.StrictMode;
import android.os.Vibrator;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import com.jetec.nordic_googleplay.Activity.MainActivity;
import com.jetec.nordic_googleplay.Dialog.WriteDialog;
import com.jetec.nordic_googleplay.NewActivity.Export.MakeCSV;
import com.jetec.nordic_googleplay.NewActivity.Export.MakePDF;
import com.jetec.nordic_googleplay.NewActivity.Listener.GetLogList;
import com.jetec.nordic_googleplay.NewActivity.Listener.ListViewListener;
import com.jetec.nordic_googleplay.NewActivity.ViewAdapter.LogListViewAdapter;
import com.jetec.nordic_googleplay.NewModel;
import com.jetec.nordic_googleplay.R;
import com.jetec.nordic_googleplay.Service.BluetoothLeService;
import com.jetec.nordic_googleplay.Value;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LogListActivity extends AppCompatActivity implements ListViewListener {

    private String TAG = "LogListActivity";
    private Vibrator vibrator;
    private BluetoothLeService mBluetoothLeService;
    private BluetoothAdapter mBluetoothAdapter;
    private boolean s_connect = false;
    private String[] default_model;
    private Intent intents;
    private GetLogList getLogList = new GetLogList();
    private List<String> nameList, timeList;
    private List<List<String>> saveList;
    private ListView listView;
    private Uri csvuri;
    private File file, pdffile;
    private String filePath;
    private MakeCSV makeCSV = new MakeCSV();
    private MakePDF makePDF = new MakePDF();
    private WriteDialog writeDialog = new WriteDialog();
    private boolean doingPDF = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.user_loglist);

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        vibrator = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        }

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();

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

        listView = findViewById(R.id.datalist1);

        get_intent();
    }

    private void get_intent() {
        nameList = new ArrayList<>();
        timeList = new ArrayList<>();
        saveList = new ArrayList<>();
        nameList.clear();
        timeList.clear();
        saveList.clear();

        Intent intent = getIntent();
        default_model = intent.getStringArrayExtra("default_model");
        String logjson = NewModel.LogString;
        getLogList.setListener(this);
        getLogList.readytointent(logjson);
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

    public void Service_close() {
        if (mBluetoothLeService == null) {
            Log.e(TAG, "masaga");
            return;
        }
        mBluetoothLeService.disconnect();
    }

    private void disconnect() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void goback() {
        Intent intent = new Intent(this, UserFunction.class);
        intent.putExtra("default_model", default_model);
        startActivity(intent);
        finish();
    }

    private Runnable todoCSV = new Runnable() {
        @Override
        public void run() {
            makeCSV.todocsv(file, filePath, nameList, timeList, saveList);
        }
    };

    private Runnable todoPDF = new Runnable() {
        @Override
        public void run() {
            makePDF.todoPDF(LogListActivity.this, pdffile, nameList, timeList, saveList);
            doingPDF = true;
            if (writeDialog.checkshowing()) {
                writeDialog.closeDialog();
                Intent intent = new Intent(LogListActivity.this, PDFListView.class);
                intent.putExtra("default_model", default_model);
                startActivity(intent);
                finish();
            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.userlog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {   //toolbar menu item
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.export) {
            vibrator.vibrate(100);
            new AlertDialog.Builder(this)
                    .setTitle(R.string.report)
                    .setMessage(R.string.choose)
                    .setPositiveButton(R.string.CSV, (dialog, which) -> {
                        vibrator.vibrate(100);
                        csvuri = Uri.fromFile(file);
                        Intent shareIntent = new Intent();
                        shareIntent.setAction(Intent.ACTION_SEND);
                        shareIntent.putExtra(Intent.EXTRA_STREAM, csvuri);
                        shareIntent.setType("text/*");
                        startActivity(Intent.createChooser(shareIntent, getResources().getText(R.string.send_to)));
                    })
                    .setNegativeButton(R.string.PDF, (dialog, which) -> {
                        vibrator.vibrate(100);
                        if (!doingPDF) {
                            writeDialog.set_Dialog(this, true);
                        }
                        else {
                            Intent intent = new Intent(this, PDFListView.class);
                            intent.putExtra("default_model", default_model);
                            startActivity(intent);
                            finish();
                        }
                    })
                    .setNeutralButton(R.string.mes_no, (dialog, which) -> vibrator.vibrate(100))
                    .show();
        } else if (id == R.id.search) {
            vibrator.vibrate(100);
            Intent intent = new Intent(this, UserSearchList.class);
            intent.putExtra("default_model", default_model);
            startActivity(intent);
            finish();
        } else if (id == R.id.disconnect) {
            vibrator.vibrate(100);
            new AlertDialog.Builder(this)
                    .setTitle(getString(R.string.action_settings))
                    .setMessage(getString(R.string.disconnectdevice))
                    .setPositiveButton(R.string.butoon_yes, (dialog, which) -> {
                        vibrator.vibrate(100);
                        Service_close();
                        Value.YMD = false;
                        Value.downlog = false;
                        NewModel.checkmodel = false;
                        Value.connected = false;
                        Value.deviceModel = "";
                        Value.BID = "";
                        Value.BName = "";
                        disconnect();
                    })
                    .setNeutralButton(R.string.butoon_no, (dialog, which) -> vibrator.vibrate(100))
                    .show();
        }

        return true;
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
    public void convert(List<String> nameList, List<String> timeList, List<List<String>> saveList) {
        this.nameList = nameList;
        this.timeList = timeList;
        this.saveList = saveList;
        Log.e(TAG, "nameList = " + nameList);
        Log.e(TAG, "timeList = " + timeList);
        Log.e(TAG, "saveList = " + saveList);
        LogListViewAdapter logListViewAdapter = new LogListViewAdapter();
        logListViewAdapter.setList(this, nameList, timeList, saveList);
        listView.setAdapter(logListViewAdapter);
        getLogList.readytocsv();
        getLogList.readytopdf();
    }

    @Override
    public void makecsv() {
        Log.e(TAG, "開始做csv");
        String baseDir = android.os.Environment.getExternalStorageDirectory().getAbsolutePath();
        String fileName = "JetecRemote" + ".csv";
        filePath = baseDir + File.separator + fileName;
        file = new File(filePath);
        new Thread(todoCSV).start();
    }

    @Override
    public void makepdf() {
        // SD卡位置getApplicationContext().getFilesDir().getAbsolutePath();
        // 系統位置android.os.Environment.getExternalStorageDirectory().getAbsolutePath();
        Log.e(TAG, "開始做pdf");
        String baseDir = android.os.Environment.getExternalStorageDirectory().getAbsolutePath();
        String fileName = "JetecRemote" + ".pdf";
        String filePath = baseDir + File.separator + fileName;
        pdffile = new File(filePath);
        new Thread(todoPDF).start();
    }
}
