package zy.library.utils;

import android.text.TextUtils;

import java.text.DecimalFormat;

/**
 * 描述：
 * 创建人：Justin
 * 创建时间：2016/9/9 14:29
 * 修改人：
 * 修改时间：2016/9/9 14:29
 * 修改备注：
 */
public class FormatUtil {

    public static String get2Double(String value){
        return get2FormatString(getDoubleFromString(value));
    }

    public static double getDoubleFromString(String value){
        double format = 0.00;
        if (TextUtils.isEmpty(value)) return 0.00;
        try {
            format = Double.parseDouble(value);
        }catch (Exception e){
            e.printStackTrace();
        }
        return format;
    }

    public static String getString(double value){
        return String.valueOf(value);
    }

    public static String get2FormatString(double value){
        String format = "0.00";
        DecimalFormat df = new DecimalFormat("0.00");
        try {
            format = df.format(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return format;
    }

    public static String getFormatString(double value){
        String format = "0.0";
        DecimalFormat df = new DecimalFormat("0.0");
        try {
            format = df.format(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return format;
    }

    /**
     * 将double转为(不保留小数位)int的string
     * @param value 需要转的double
     * @return 返回(不保留小数位)int的string
     */
    public static String getFormatInt(double value){
        String format = "0";
        DecimalFormat df = new DecimalFormat("0");
        try {
            format = df.format(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return format;
    }


    public static int getInt(String value){
        int format = 0;
        if (TextUtils.isEmpty(value)) return format;
        try {
            format = Integer.parseInt(value);
        }catch (Exception e){
            e.printStackTrace();
        }
        return format;
    }
}

