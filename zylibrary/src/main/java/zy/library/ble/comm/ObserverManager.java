package zy.library.ble.comm;



import java.util.ArrayList;
import java.util.List;

import zy.library.ble.data.BleDevice;

public class ObserverManager implements Observable {

    public static ObserverManager getInstance() {
        return ObserverManagerHolder.sObserverManager;
    }

    private static class ObserverManagerHolder {
        private static final ObserverManager sObserverManager = new ObserverManager();
    }

    private List<Observer> observers = new ArrayList<>();

    @Override
    public void addObserver(Observer obj) {
        observers.add(obj);
    }

    @Override
    public void deleteObserver(Observer obj) {
        int i = observers.indexOf(obj);
        if (i >= 0) {
            observers.remove(obj);
        }
    }

    @Override
    public void notifyObserver(BleDevice bleDevice) {
        for (int i = 0; i < observers.size(); i++) {
            Observer o = observers.get(i);
            o.disConnected(bleDevice);
        }
    }

}
