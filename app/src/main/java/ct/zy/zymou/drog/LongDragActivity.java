package ct.zy.zymou.drog;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.animation.DynamicAnimation;
import android.support.animation.SpringAnimation;
import android.support.animation.SpringForce;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import ct.zy.zymou.AAAAA;
import ct.zy.zymou.R;
import zy.library.base.BaseZyActivity;
import zy.library.ble.BleManager;
import zy.library.ble.callback.BleGattCallback;
import zy.library.ble.callback.BleScanCallback;
import zy.library.ble.comm.ObserverManager;
import zy.library.ble.data.BleDevice;
import zy.library.ble.exception.BleException;
import zy.library.ble.scan.BleScanRuleConfig;
import zy.library.ui.recyclerview.drag.adapter.ListDragAdapter;
import zy.library.utils.SpanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 长按拖拽
 */
public class LongDragActivity extends BaseZyActivity {

    private RecyclerView recyclerView;
    private String[] array = {"今日头条", "知乎", "网易新闻", "腾讯新闻", "稀土掘金", "简书", "美团"
//            , "饿了么",
//            "新浪微博", "微信", "高德地图", "百度地图", "支付宝", "英雄联盟", "绝地求生", "荒野行动",
//            "饥荒", "守望先锋", "王者荣耀", "QQ飞车"
    };
    private ListDragAdapter adapter;
    private List<String> list;

    private TextView tv_1,tv_2;

    private BluetoothAdapter mBluetoothAdapter;
    AnimationSet animationSet;

    AAAAA aaaaa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_long_drag);

        recyclerView = findViewById(R.id.recyclerView);
        tv_1 = findViewById(R.id.tv_1);
        tv_2 = findViewById(R.id.tv_2);
        list = new ArrayList<>();
        for (int i = 0; i < array.length; i++) {
            list.add(array[i]);
        }
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        adapter = new ListDragAdapter(this, list,recyclerView);
        recyclerView.setAdapter(adapter);

        SpringForce spring = new SpringForce(0)
                .setDampingRatio(SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY  )//弹性阻尼，dampingRatio越大，摆动次数越少
                .setStiffness(SpringForce.STIFFNESS_LOW);//stiffness值越小，弹簧越容易摆动，摆动的时间越长
        final SpringAnimation anim1 = new SpringAnimation(tv_1 ,SpringAnimation.TRANSLATION_X).setSpring(spring);
        final SpringAnimation anim2 = new SpringAnimation(tv_2 ,SpringAnimation.TRANSLATION_Y).setSpring(spring);



        animationSet = new AnimationSet(true);

        TranslateAnimation translateAnimation = new TranslateAnimation(
                //X轴初始位置
                Animation.RELATIVE_TO_SELF, 0.0f,
                //X轴移动的结束位置
                Animation.RELATIVE_TO_SELF,-0.2f,
                //y轴开始位置
                Animation.RELATIVE_TO_SELF,0.0f,
                //y轴移动后的结束位置
                Animation.RELATIVE_TO_SELF,0.0f);

        //3秒完成动画
        translateAnimation.setDuration(800);
        //如果fillAfter的值为真的话，动画结束后，控件停留在执行后的状态
        animationSet.setFillAfter(true);
        //将AlphaAnimation这个已经设置好的动画添加到 AnimationSet中
        animationSet.addAnimation(translateAnimation);

        tv_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                String s = "";
//                for (String name : adapter.getList()){
//                    s += name+"-";
//                }
//                tv_1.setText(s);

//                checkPermissions();
//                tv_1.startAnimation(animationSet);

                anim1.cancel();
                anim1.setStartValue(-50);
                anim1.start();

                tv_2.setVisibility(View.INVISIBLE);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        tv_2.setVisibility(View.VISIBLE);

                        anim2.cancel();
                        anim2.setStartValue(100);
                        anim2.start();
                    }
                },300);
            }
        });

        translateAnimation.setAnimationListener(
                new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {//开始时

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {//结束时

                        anim1.cancel();
                        anim1.setStartValue(-100);
                        anim1.start();

                        tv_2.setVisibility(View.VISIBLE);
                        anim2.cancel();
                        anim2.setStartValue(100);
                        anim2.start();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {//进行时

                    }
                });


        initBlue();

        tv_1.setText("第一段");
//                new SpanUtils()
//                .append("我是第一段").setBold().setFontSize(20,true)
//                .append("我是第二段").setBold().setForegroundColor(getResources().getColor(R.color.blue_3d94ff))
//                .append("使用报告").setBold()
//                .create());

        aaaaa = new AAAAA() {
            @Override
            public void onSuccess(String info) {
                tv_1.setText(info);
            }

            @Override
            public void failure() {

            }
        };

        aaaaa.onSuccess("sssss");
    }


    private ProgressDialog progressDialog;
    private static final int REQUEST_CODE_OPEN_GPS = 1;
    private static final int REQUEST_CODE_PERMISSION_LOCATION = 2;
    private void initBlue(){

        BleManager.getInstance().init(getApplication());
        BleManager.getInstance()
                .enableLog(true)
                .setReConnectCount(1, 5000)
                .setConnectOverTime(20000)
                .setOperateTimeout(5000);

        progressDialog = new ProgressDialog(this);
        mDeviceAdapter = new DeviceAdapter(this);
        mDeviceAdapter.setOnDeviceClickListener(new DeviceAdapter.OnDeviceClickListener() {
            @Override
            public void onConnect(BleDevice bleDevice) {
                if (!BleManager.getInstance().isConnected(bleDevice)) {
                    BleManager.getInstance().cancelScan();
                    connect(bleDevice);
                }
            }

            @Override
            public void onDisConnect(final BleDevice bleDevice) {
                if (BleManager.getInstance().isConnected(bleDevice)) {
                    BleManager.getInstance().disconnect(bleDevice);
                }
            }

            @Override
            public void onDetail(BleDevice bleDevice) {
                if (BleManager.getInstance().isConnected(bleDevice)) {
//                    Intent intent = new Intent(activity, OperationActivity.class);
//                    intent.putExtra(OperationActivity.KEY_DATA, bleDevice);
//                    startActivity(intent);
                }
            }
        });
        ListView listView_device = (ListView) findViewById(R.id.list_device);
        listView_device.setAdapter(mDeviceAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        showConnectedDevice();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BleManager.getInstance().disconnectAllDevice();
        BleManager.getInstance().destroy();
    }

    // 申请打开蓝牙请求的回调
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_OPEN_GPS) {
            if (checkGPSIsOpen()) {
                setScanRule();
                startScan();
            }
        }
    }

    private DeviceAdapter mDeviceAdapter;
    private void showConnectedDevice() {
        List<BleDevice> deviceList = BleManager.getInstance().getAllConnectedDevice();
        mDeviceAdapter.clearConnectedDevice();
        for (BleDevice bleDevice : deviceList) {
            mDeviceAdapter.addDevice(bleDevice);
        }
        mDeviceAdapter.notifyDataSetChanged();
    }


    private void connect(final BleDevice bleDevice) {
        BleManager.getInstance().connect(bleDevice, new BleGattCallback() {
            @Override
            public void onStartConnect() {
                progressDialog.show();
            }

            @Override
            public void onConnectFail(BleDevice bleDevice, BleException exception) {
//                img_loading.clearAnimation();
//                img_loading.setVisibility(View.INVISIBLE);
//                btn_scan.setText(getString(R.string.start_scan));
                progressDialog.dismiss();
                showToastMsg(getString(R.string.connect_fail));
            }

            @Override
            public void onConnectSuccess(BleDevice bleDevice, BluetoothGatt gatt, int status) {
                progressDialog.dismiss();
                mDeviceAdapter.addDevice(bleDevice);
                mDeviceAdapter.notifyDataSetChanged();
            }

            @Override
            public void onDisConnected(boolean isActiveDisConnected, BleDevice bleDevice, BluetoothGatt gatt, int status) {
                progressDialog.dismiss();

                mDeviceAdapter.removeDevice(bleDevice);
                mDeviceAdapter.notifyDataSetChanged();

                if (isActiveDisConnected) {
                    showToastMsg(getString(R.string.active_disconnected));
                } else {
                    showToastMsg(getString(R.string.disconnected));
                    ObserverManager.getInstance().notifyObserver(bleDevice);
                }

            }
        });
    }

    private void startScan() {
        BleManager.getInstance().scan(new BleScanCallback() {
            @Override
            public void onScanStarted(boolean success) {
                mDeviceAdapter.clearScanDevice();
                mDeviceAdapter.notifyDataSetChanged();
//                img_loading.startAnimation(operatingAnim);
//                img_loading.setVisibility(View.VISIBLE);
//                btn_scan.setText(getString(R.string.stop_scan));
            }

            @Override
            public void onLeScan(BleDevice bleDevice) {
                super.onLeScan(bleDevice);
            }

            @Override
            public void onScanning(BleDevice bleDevice) {

                mDeviceAdapter.addDevice(bleDevice);
                mDeviceAdapter.notifyDataSetChanged();
            }

            @Override
            public void onScanFinished(List<BleDevice> scanResultList) {
//                img_loading.clearAnimation();
//                img_loading.setVisibility(View.INVISIBLE);
//                btn_scan.setText(getString(R.string.start_scan));
            }
        });
    }

    private void setScanRule() {
//        String[] uuids;
//        String str_uuid = et_uuid.getText().toString();
//        if (TextUtils.isEmpty(str_uuid)) {
//            uuids = null;
//        } else {
//            uuids = str_uuid.split(",");
//        }
//        UUID[] serviceUuids = null;
//        if (uuids != null && uuids.length > 0) {
//            serviceUuids = new UUID[uuids.length];
//            for (int i = 0; i < uuids.length; i++) {
//                String name = uuids[i];
//                String[] components = name.split("-");
//                if (components.length != 5) {
//                    serviceUuids[i] = null;
//                } else {
//                    serviceUuids[i] = UUID.fromString(uuids[i]);
//                }
//            }
//        }

//        String[] names;
//        String str_name = et_name.getText().toString();
//        if (TextUtils.isEmpty(str_name)) {
//            names = null;
//        } else {
//            names = str_name.split(",");
//        }

//        String mac = et_mac.getText().toString();

//        boolean isAutoConnect = sw_auto.isChecked();

        BleScanRuleConfig scanRuleConfig = new BleScanRuleConfig.Builder()
//                .setServiceUuids(serviceUuids)      // 只扫描指定的服务的设备，可选
//                .setDeviceName(true, names)   // 只扫描指定广播名的设备，可选
//                .setDeviceMac(mac)                  // 只扫描指定mac的设备，可选
//                .setAutoConnect(isAutoConnect)      // 连接时的autoConnect参数，可选，默认false
                .setScanTimeOut(10000)              // 扫描超时时间，可选，默认10秒
                .build();
        BleManager.getInstance().initScanRule(scanRuleConfig);
    }


    //开始扫描
    private void checkPermissions() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (!bluetoothAdapter.isEnabled()) {
            Toast.makeText(this, getString(R.string.please_open_blue), Toast.LENGTH_LONG).show();
            return;
        }

        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION};
        List<String> permissionDeniedList = new ArrayList<>();
        for (String permission : permissions) {
            int permissionCheck = ContextCompat.checkSelfPermission(this, permission);
            if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                onPermissionGranted(permission);
            } else {
                permissionDeniedList.add(permission);
            }
        }
        if (!permissionDeniedList.isEmpty()) {
            String[] deniedPermissions = permissionDeniedList.toArray(new String[permissionDeniedList.size()]);
            ActivityCompat.requestPermissions(this, deniedPermissions, REQUEST_CODE_PERMISSION_LOCATION);
        }
    }

    private void onPermissionGranted(String permission) {
        switch (permission) {
            case Manifest.permission.ACCESS_FINE_LOCATION:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !checkGPSIsOpen()) {
                    new AlertDialog.Builder(this)
                            .setTitle(R.string.notifyTitle)
                            .setMessage(R.string.gpsNotifyMsg)
                            .setNegativeButton(R.string.cancel,
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            finish();
                                        }
                                    })
                            .setPositiveButton(R.string.setting,
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                            startActivityForResult(intent, REQUEST_CODE_OPEN_GPS);
                                        }
                                    })

                            .setCancelable(false)
                            .show();
                } else {
                    setScanRule();
                    startScan();
                }
                break;
        }
    }

    private boolean checkGPSIsOpen() {
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (locationManager == null)
            return false;
        return locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER);
    }

    @Override
    public final void onRequestPermissionsResult(int requestCode,
                                                 @NonNull String[] permissions,
                                                 @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE_PERMISSION_LOCATION:
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                            onPermissionGranted(permissions[i]);
                        }
                    }
                }
                break;
        }
    }

}
