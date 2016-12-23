package com.ezcocoa.linkpay_sample.ez;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 *
 */
public class EZSharedPreferences {
    private static final String TAG = EZSharedPreferences.class.getSimpleName();
    public static String getStringValue(Context context, String key) {
        SharedPreferences prefs = context.getSharedPreferences("Pref", Activity.MODE_PRIVATE);
        return prefs.getString(key, "");
    }

    public static String getStringValue(Context context, String key, String defaultValue) {
        SharedPreferences prefs  = context.getSharedPreferences("Pref", Activity.MODE_PRIVATE);
        return prefs.getString(key, defaultValue);
    }

    public static boolean setStringValue(Context context, String key, String value) {
        SharedPreferences prefs = context.getSharedPreferences("Pref", Activity.MODE_PRIVATE);
        SharedPreferences.Editor ed = prefs.edit();
        ed.putString(key, value);
        return ed.commit();
    }

    public static int getIntValue(Context context, String key) {
        SharedPreferences prefs  = context.getSharedPreferences("Pref", Activity.MODE_PRIVATE);
        return prefs.getInt(key, 0);
    }

    public static boolean setIntValue(Context context, String key, int value) {
        SharedPreferences prefs = context.getSharedPreferences("Pref", Activity.MODE_PRIVATE);
        SharedPreferences.Editor ed = prefs.edit();
        ed.putInt(key, value);
        return ed.commit();
    }

    public static long getLongValue(Context context, String key) {
        SharedPreferences prefs  = context.getSharedPreferences("Pref", Activity.MODE_PRIVATE);
        return prefs.getLong(key, 0);
    }

    public static boolean setLongValue(Context context, String key, long value) {
        SharedPreferences prefs = context.getSharedPreferences("Pref", Activity.MODE_PRIVATE);
        SharedPreferences.Editor ed = prefs.edit();
        ed.putLong(key, value);
        return ed.commit();
    }

    public static boolean getBoolean(Context context, String key) {
        SharedPreferences prefs = context.getSharedPreferences("Pref", Activity.MODE_PRIVATE);
        return prefs.getBoolean(key, false);
    }

    public static boolean getBoolean(Context context, String key, boolean defaultValue) {
        SharedPreferences prefs = context.getSharedPreferences("Pref", Activity.MODE_PRIVATE);
        return prefs.getBoolean(key, defaultValue);
    }

    public static boolean setBoolean(Context context, String key, boolean value) {
        SharedPreferences prefs = context.getSharedPreferences("Pref", Activity.MODE_PRIVATE);
        SharedPreferences.Editor ed = prefs.edit();
        ed.putBoolean(key, value);
        return ed.commit();
    }

    public static boolean contains(Context context, String key) {
        SharedPreferences prefs  = context.getSharedPreferences("Pref", Activity.MODE_PRIVATE);
        return prefs.contains(key);
    }

    /**
     * Preference에 저장되어 있는 환경 설정을 다 지운다.
     * @param context
     * @return
     */
    public static boolean removeAll(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("Pref", Activity.MODE_PRIVATE);
        SharedPreferences.Editor ed = prefs.edit();
        ed.clear();
        return ed.commit();
    }
}
