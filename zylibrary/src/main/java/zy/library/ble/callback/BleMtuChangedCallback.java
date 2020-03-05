package zy.library.ble.callback;


import zy.library.ble.exception.BleException;

public abstract class BleMtuChangedCallback extends BleBaseCallback {

    public abstract void onSetMTUFailure(BleException exception);

    public abstract void onMtuChanged(int mtu);

}
