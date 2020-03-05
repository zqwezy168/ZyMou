package ct.zy.zymou.base;


import android.os.StrictMode;

import zy.library.base.AppContext;


/**
 * Created by zy
 * on 2018/11/27.
 */
public class MyApplication extends AppContext {

    private static float sScale;
    private static int sWidthDp;
    private static int sWidthPix;
    private static int sHeightPix;

    @Override
    public void onCreate() {
        super.onCreate();

        // 解决 Android 8.0 无法拍照问题 start
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        builder.detectFileUriExposure();
        StrictMode.setVmPolicy(builder.build());
        // 解决 Android 8.0 无法拍照问题 end

        sScale = getResources().getDisplayMetrics().density;
        sWidthPix = getResources().getDisplayMetrics().widthPixels;
        sHeightPix = getResources().getDisplayMetrics().heightPixels;
        sWidthDp = (int) (sWidthPix / sScale);

    }

}
