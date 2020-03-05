package zy.library.ble.callback;

import zy.library.ble.data.BleDevice;

public interface BleScanPresenterImp {

    void onScanStarted(boolean success);

    void onScanning(BleDevice bleDevice);

}
