package zy.library.ble.callback;


import zy.library.ble.exception.BleException;

public abstract class BleReadCallback extends BleBaseCallback {

    public abstract void onReadSuccess(byte[] data);

    public abstract void onReadFailure(BleException exception);

}
