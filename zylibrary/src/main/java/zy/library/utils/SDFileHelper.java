package zy.library.utils;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;

import java.io.File;
import java.io.FileOutputStream;

import zy.library.R;
import me.xiaopan.sketch.Sketch;
import me.xiaopan.sketch.request.CancelCause;
import me.xiaopan.sketch.request.DownloadListener;
import me.xiaopan.sketch.request.DownloadResult;
import me.xiaopan.sketch.request.ErrorCause;

/**
 * Created by zy on 2018/5/16.
 * 下载图片
 */

public class SDFileHelper {
    private Context context;
    private String filePath;

    public SDFileHelper() {
    }

    public SDFileHelper(Context context) {
        super();
        this.context = context;
    }

    //Glide保存图片
    public void savePicture(final String filename, String url){

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            Sketch.with(context).download(url, new DownloadListener() {
                @Override
                public void onStarted() {

                }

                @Override
                public void onCompleted(@NonNull DownloadResult result) {
                    if (result.hasData()){
                        try {
                            savaFileToSD(filename,result.getImageData());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }

                @Override
                public void onError(@NonNull ErrorCause cause) {

                }

                @Override
                public void onCanceled(@NonNull CancelCause cause) {

                }
            });
        }
    }
    //往SD卡写入文件的方法
    public void savaFileToSD(String filename, byte[] bytes) throws Exception {
        //如果手机已插入sd卡,且app具有读写sd卡的权限
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            filePath = Environment.getExternalStorageDirectory().getCanonicalPath()+"/"+(context.getResources().getString(R.string.app_name));
            File dir1 = new File(filePath);
            if (!dir1.exists()){
                dir1.mkdirs();
            }
            filename = filePath+ "/" + filename;
            //这里就不要用openFileOutput了,那个是往手机内存中写数据的
            FileOutputStream output = new FileOutputStream(filename);
            output.write(bytes);
            //将bytes写入到输出流中
            output.close();
            //关闭输出流
            Utils.ToastMessage(context,"图片已成功保存到"+filePath);
        } else  Utils.ToastMessage(context,"SD卡不存在或者不可读写");
    }

    public String getPath(){
        return filePath;
    }
}
