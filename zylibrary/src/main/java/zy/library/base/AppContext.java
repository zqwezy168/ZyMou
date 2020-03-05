package zy.library.base;

import android.os.Build;
import android.os.StrictMode;
import android.support.multidex.MultiDexApplication;
import me.xiaopan.sketch.Sketch;


/**
 * Created by zy on 2018/3/12.
 */

public class AppContext extends MultiDexApplication {

    private static AppContext instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        // 解决 Android 8.0 无法拍照问题 start
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        builder.detectFileUriExposure();
        StrictMode.setVmPolicy(builder.build());
        // 解决 Android 8.0 无法拍照问题 end
    }

    public static AppContext getInstance() {
        return instance;
    }



    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            Sketch.with(getBaseContext()).onTrimMemory(level);
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            Sketch.with(getBaseContext()).onLowMemory();
        }
    }

}
