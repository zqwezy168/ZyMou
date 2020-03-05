package zy.library.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * Created by Hypero on 1/22/16.
 */
public class Utils {

    private static final float DENSITY = Resources.getSystem().getDisplayMetrics().density;
    private static long lastClickTime;

    public static int dp2px(int dp) {
        return Math.round(dp * DENSITY);
    }

    public static String subZeroAndDot(String s) {
        if (s.indexOf(".") > 0) {
            s = s.replaceAll("0+?$", "");//去掉多余的0
            s = s.replaceAll("[.]$", "");//如最后一位是.则去掉
        }
        return s;
    }

    //防止重复点击
    public synchronized static boolean isFastClick() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < 1500) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

//    public static String hexSHA1(String value) {
//        try {
//            MessageDigest md = MessageDigest.getInstance("SHA-1");
//            md.update(value.getBytes("utf-8"));
//            byte[] digest = md.digest();
//            return byteToHexString(digest);
//        } catch (Exception ex) {
//            throw new RuntimeException(ex);
//        }
//    }
//
//    public static String byteToHexString(byte[] bytes) {
//        return String.valueOf(Hex.encodeHex(bytes, false));
//    }


    public static String getendTime(String startTime, String endTime) {
        String ouyputTime = "";
        if (TextUtils.isEmpty(startTime)) {
            ouyputTime = endTime;
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                if (sdf.parse(startTime).before(sdf.parse(endTime))) {
                    ouyputTime = endTime;
                } else {
                    ouyputTime = startTime;
                }
            } catch (ParseException e) {
                ouyputTime = endTime;
                e.printStackTrace();
            }
        }
        return ouyputTime;
    }

    public static String getStartTime(String startTime, String endTime) {
        String ouyputTime = "";
        if (TextUtils.isEmpty(endTime)) {
            ouyputTime = startTime;
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                if (sdf.parse(startTime).before(sdf.parse(endTime))) {
                    ouyputTime = startTime;
                } else {
                    ouyputTime = endTime;
                }
            } catch (ParseException e) {
                ouyputTime = startTime;
                e.printStackTrace();
            }
        }
        return ouyputTime;
    }

    public static void saveFile(Context mContext, Bitmap bm) throws IOException {
        String subForder = "/sdcard/13/Picture";
        File foder = new File(subForder);
        if (!foder.exists()) {
            foder.mkdirs();
        }
        File myCaptureFile = new File(subForder, String.valueOf(System.currentTimeMillis()/1000)+".jpg");
        if (!myCaptureFile.exists()) {
            myCaptureFile.createNewFile();
        }
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
        bm.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        bos.flush();
        bos.close();

        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(myCaptureFile);
        intent.setData(uri);
        mContext.sendBroadcast(intent);
        ToastMessage(mContext,"保存成功");
    }

    public static void ToastMessage(Context cont, String msg) {
        if(cont == null || msg == null) {
            return;
        }
        if (msg.equals("")||msg.equals("")){

        }
        Toast.makeText(cont, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 获取版本信息
     * @return
     * @throws Exception
     */
    public static String getVersionName(Context activity) throws Exception {
        //获取packagemanager的实例
        PackageManager packageManager = activity.getPackageManager();
        //getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = packageManager.getPackageInfo(activity.getPackageName(), 0);
        return packInfo.versionName;
    }
    public static int getVersionCode(Context activity) throws Exception {
        //获取packagemanager的实例
        PackageManager packageManager = activity.getPackageManager();
        //getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = packageManager.getPackageInfo(activity.getPackageName(), 0);
        return packInfo.versionCode;
    }


    // 获取视频缩略图
    public static Bitmap getVideoThumbnail(String filePath) {
        Bitmap b=null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            retriever.setDataSource(filePath);
            b=retriever.getFrameAtTime();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (RuntimeException e) {
            e.printStackTrace();

        } finally {
            try {
                retriever.release();
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
        return b;
    }

    /**
     * 判断软键盘状态
     * @return
     */
    public static boolean isSoftShowing(Activity activity) {
        //获取当前屏幕内容的高度
        int screenHeight = activity.getWindow().getDecorView().getHeight();
        //获取View可见区域的bottom
        Rect rect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);

        Log.e("height","screenHeight:"+screenHeight+"  rect.bottom:"+rect.bottom);
        if (rect.bottom+200>screenHeight){
            return true;
        }else {
            return false;
        }
    }

    public static void showSoftInput(Context context){
        InputMethodManager imm = (InputMethodManager)context.getSystemService(INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.RESULT_SHOWN);

    }

    public static void hideSoftInput(Context context, View view){
        InputMethodManager imm = (InputMethodManager)context.getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
    }
}
