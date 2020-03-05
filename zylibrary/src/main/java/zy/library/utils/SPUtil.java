package zy.library.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;
import android.util.Log;

import zy.library.R;
import zy.library.base.AppContext;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Set;


/**
 * SharedPreferences封装类
 */
public class SPUtil {
    private static SharedPreferences sp = null;
    public static String TOKEN = "token";

    public static SharedPreferences getSharedPreferences() {
        return AppContext.getInstance().getSharedPreferences("my_info", Context.MODE_PRIVATE);
    }

    public static SharedPreferences getSp() {
        if (sp == null) {
            sp = getSharedPreferences();
        }
        return sp;
    }

    public static void clearAll() {
        SharedPreferences.Editor spEdit = getSp().edit();
        spEdit.clear();
        spEdit.apply();
    }

    /**
     * 查询缓存值
     *
     * @param key
     * @param id
     */
    public static void putInt(String key, int id) {
        SharedPreferences.Editor spEdit = getSp().edit();
        spEdit.putInt(key, id);
        spEdit.apply();
    }

    /**
     * 查询缓存值
     *
     * @param key
     * @return
     */
    public static int getInt(String key) {
        return getSp().getInt(key, -1);
    }

    /**
     * 查询缓存值
     *
     * @param key
     * @param str
     */
    public static void putString(String key, String str) {
        SharedPreferences.Editor spEdit = getSp().edit();
        spEdit.putString(key, str);
        spEdit.apply();
    }

    /**
     * 获取string
     */
    public static String getString(String key) {
        return getSp().getString(key, null);
    }

    /**
     * 获取string 自定义默认值的那种
     */
    public static String getString(String key, String defaultStr) {
        return getSp().getString(key, defaultStr);
    }

    /**
     * 获取Float
     */
    public static float getFloat(String key) {
        return getSp().getFloat(key, -1F);
    }

    /**
     * 设置
     */
    public static void putLong(String key, long l) {
        SharedPreferences.Editor spEdit = getSp().edit();
        spEdit.putLong(key, l);
        spEdit.apply();
    }

    /**
     *
     */
    public static long getLong(String key) {
        return getSp().getLong(key, -1L);
    }

    public static long getLong(String key, long defaultValue) {
        return getSp().getLong(key, defaultValue);
    }

    /**
     * 查询缓存值
     *
     * @param key
     * @param b
     */
    public static void putBoolean(String key, boolean b) {
        SharedPreferences.Editor spEdit = getSp().edit();
        spEdit.putBoolean(key, b);
        spEdit.apply();
    }

    /**
     * 查询缓存值
     *
     * @param key
     * @return
     */
    public static boolean getBoolean(String key) {
        return getSp().getBoolean(key, false);
    }

    public static boolean getBoolean(String key, boolean def) {
        return getSp().getBoolean(key, def);
    }

    /**
     * 缓存Set集合值
     *
     * @param key
     * @param ids
     */
    public static void putSet(String key, Set<String> ids) {
        SharedPreferences.Editor spEdit = getSp().edit();
        spEdit.putStringSet(key, ids);
        spEdit.apply();
    }

    public static void remove(String key) {
        SharedPreferences.Editor spEdit = getSp().edit();
        spEdit.remove(key);
        spEdit.apply();
    }

    /**
     * SharedPreferenc 存储对象
     *
     * @param key
     * @param obj
     */
    public static void putObject(String key, Object obj) {
        ByteArrayOutputStream baos = null;
        ObjectOutputStream oos = null;
        try {
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(obj);
            // Save user preferences. use Editor object to make changes.
            SharedPreferences.Editor spEdit = getSp().edit();
            String strObj = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
            spEdit.putString(key, strObj);
            spEdit.apply();
        } catch (IOException e) {
            Log.e("IOException", e.toString());
        } finally {
            try {
                if (baos != null) baos.close();
                if (oos != null) oos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 从SharedPreferences文件流中获取对象
     *
     * @param key
     * @return
     */
    public static Object getObject(String key) {
        ObjectInputStream ois = null;
        ByteArrayInputStream bais = null;
        try {
            String paramBase64 = getSp().getString(key, null);
            if (paramBase64 == null) return null;
            byte[] base64Bytes = Base64.decode(paramBase64.getBytes(), Base64.DEFAULT);
            bais = new ByteArrayInputStream(base64Bytes);
            ois = new ObjectInputStream(bais);
            return ois.readObject();
        } catch (Exception e) {
            Log.d("Exception", "" + e.toString());
        } finally {
            try {
                if (bais != null) bais.close();
                if (ois != null) ois.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
