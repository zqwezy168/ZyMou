package zy.library.ble.comm;


import zy.library.ble.data.BleDevice;

public interface Observer {

    void disConnected(BleDevice bleDevice);
}
