package zy.library.utils;

import android.content.Context;
import android.content.SharedPreferences;

import zy.library.base.AppContext;


/**
 * 描述：本地配置
 * 创建人：gsm
 * 创建时间：2016/11/3 10:09
 * 修改人：
 * 修改时间：2016/11/3 10:09
 * 修改备注：
 */
public class LocalConfig {
    private static final String LOCAL_SHARED_NAME = "local.config";
    private static final String KEY_LOGIN_STATUS = "login_status";
    private static final String KEY_REGISTER = "register";
    private static final String KEY_DEVICE_TOKEN = "device_token";
    private static final String KEY_ACCOUNT = "account";
    private static final String KEY_MOBILE = "mobile";

    public static void saveAccount(String account){
        SharedPreferences.Editor edit = getSp().edit();
        edit.putString(KEY_ACCOUNT,account);
        edit.apply();
    }

    public static String getAccount(){
        return getSp().getString(KEY_ACCOUNT,"");
    }

    public static void saveMobile(String mobile){
        SharedPreferences.Editor edit = getSp().edit();
        edit.putString(KEY_MOBILE,mobile);
        edit.apply();
    }

    public static String getMobile(){
        return getSp().getString(KEY_MOBILE,"");
    }

    public static void saveLoginStatus(boolean loginStatus){
        SharedPreferences.Editor spEdit = getSp().edit();
        spEdit.putBoolean(KEY_LOGIN_STATUS, loginStatus);
        spEdit.apply();
    }

    public static boolean getLoginStatus(){
        return getSp().getBoolean(KEY_LOGIN_STATUS,false);
    }

    public static void saveRegister(boolean register){
        SharedPreferences.Editor spEdit = getSp().edit();
        spEdit.putBoolean(KEY_REGISTER, register);
        spEdit.apply();
    }

    public static boolean getRegister(){
        return getSp().getBoolean(KEY_REGISTER,false);
    }

    public static void saveDeviceToken(String deviceToken){
        SharedPreferences.Editor spEdit = getSp().edit();
        spEdit.putString(KEY_DEVICE_TOKEN, deviceToken);
        spEdit.apply();
    }

    public static String getDeviceToken(){
        return getSp().getString(KEY_DEVICE_TOKEN,"");
    }

    private static SharedPreferences getSp(){
        return AppContext.getInstance().getSharedPreferences(LOCAL_SHARED_NAME, Context.MODE_PRIVATE);
    }
}
