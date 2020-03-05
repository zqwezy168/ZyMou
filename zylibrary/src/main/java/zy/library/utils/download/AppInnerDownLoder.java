package zy.library.utils.download;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 共通机能-版本检测/更新
 * 
 * @version 1.0
 * 
 */
public class AppInnerDownLoder {
	public final static String SD_FOLDER = Environment.getExternalStorageDirectory()+ "/liswater/lis/lis.apk";
	private static final String TAG = AppInnerDownLoder.class.getSimpleName();
	/**
	 * 从服务器中下载APK
	 */
	@SuppressWarnings("unused")
	public static void downLoadApk(final Context mContext, final String downURL , final int size) {

		final ProgressDialog pd; // 进度条对话框
		pd = new ProgressDialog(mContext);
		pd.setCancelable(false);// 必须一直下载完，不可取消
		pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		pd.setMessage("正在下载安装包，请稍后");
		pd.setTitle("版本升级");
		pd.show();
		new Thread() {
			@Override
			public void run() {
				try {
					File file = downloadFile(downURL, pd,size);
					sleep(3000);
					installApk(mContext, file);
					// 结束掉进度条对话框
					pd.dismiss();
				} catch (Exception e) {
					pd.dismiss();

				}
			}
		}.start();
	}

	/**
	 * 从服务器下载最新更新文件
	 * 
	 * @param path
	 *            下载路径
	 * @param pd
	 *            进度条
	 * @return
	 * @throws Exception
	 */
	private static File downloadFile(String path, ProgressDialog pd, int size) throws Exception {
		// 如果相等的话表示当前的sdcard挂载在手机上并且是可用的
		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {
			URL url = new URL(path);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5000);
			// 获取到文件的大小
			pd.setMax(100);
			InputStream is = conn.getInputStream();
			File file = new File(SD_FOLDER);
			//UserInfo.apkstatic 为1表示已经成功下载好了apk。且判断apk 存不存在，满足就返回
//			if (file.exists()){
//				return file;
//			}
			// 目录不存在创建目录
			if (!file.getParentFile().exists())
				file.getParentFile().mkdirs();
			FileOutputStream fos = new FileOutputStream(file);
			BufferedInputStream bis = new BufferedInputStream(is);
			byte[] buffer = new byte[1024];
			int len;
			int total = 0;
			while ((len = bis.read(buffer)) != -1) {
				fos.write(buffer, 0, len);
				total += len;
				// 获取当前下载量
				int aa = total*100/size;
				pd.setProgress(aa);
			}
			fos.close();
			bis.close();
			is.close();
			return file;
		} else {
			throw new IOException("未发现有SD卡");
		}
	}

	/**
	 * 安装apk
	 */
	private static void installApk(Context mContext, File file) {
		Uri fileUri = Uri.fromFile(file);
		Intent it = new Intent();
		it.setAction(Intent.ACTION_VIEW);
		it.setDataAndType(fileUri, "application/vnd.android.package-archive");
		it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);// 防止打不开应用
		mContext.startActivity(it);
	}

	/**
	 * 获取应用程序版本（versionName）
	 * 
	 * @return 当前应用的版本号
	 */

	private static double getLocalVersion(Context context) {
		PackageManager manager = context.getPackageManager();
		PackageInfo info = null;
		try {
			info = manager.getPackageInfo(context.getPackageName(), 0);
		} catch (NameNotFoundException e) {
			Log.e(TAG, "获取应用程序版本失败，原因：" + e.getMessage());
			return 0.0;
		}

		return Double.valueOf(info.versionName);
	}
	/** 
     * byte(字节)根据长度转成kb(千字节)和mb(兆字节) 
     *  
     * @param bytes 
     * @return 
     */  
    public static String bytes2kb(long bytes) {
        BigDecimal filesize = new BigDecimal(bytes);
        BigDecimal megabyte = new BigDecimal(1024 * 1024);
        float returnValue = filesize.divide(megabyte, 2, BigDecimal.ROUND_UP)
                .floatValue();  
        if (returnValue > 1)  
            return (returnValue + "MB");  
        BigDecimal kilobyte = new BigDecimal(1024);
        returnValue = filesize.divide(kilobyte, 2, BigDecimal.ROUND_UP)
                .floatValue();  
        return (returnValue + "KB");  
    }  
}
