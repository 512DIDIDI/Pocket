package com.dididi.pocket.core.util;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.dididi.pocket.core.app.Pocket;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Created by dididi
 * on 02/09/2018 .
 */

public class PocketPreferences {
    //本地存储用户信息

    //生成包名_preferences.xml
    private static final SharedPreferences PREFERENCES =
            PreferenceManager.getDefaultSharedPreferences(Pocket.getApplicationContext());
    private static final String POCKET_PREFERENCES_KEY = "profile";

    private static SharedPreferences getPreferences() {
        return PREFERENCES;
    }

    public static void setPocketProfile(String value) {
        getPreferences()
                .edit()
                .putString(POCKET_PREFERENCES_KEY, value)
                .apply();
    }

    @SuppressWarnings("WeakerAccess")
    public static String getPocketProfile() {
        return getPreferences().getString(POCKET_PREFERENCES_KEY, null);
    }

    public static JsonObject getPocketProfileJson() {
        final String profile = getPocketProfile();
        return new JsonParser().parse(profile).getAsJsonObject();
    }

    public static void removePocketProfile() {
        getPreferences()
                .edit()
                .remove(POCKET_PREFERENCES_KEY)
                .apply();
    }

    public static void clearPocketProfile() {
        getPreferences()
                .edit()
                .clear()
                .apply();
    }

    public static void setPocketFlag(String key, boolean flag) {
        getPreferences()
                .edit()
                .putBoolean(key, flag)
                .apply();
    }

    public static boolean getPocketFlag(String key) {
        return getPreferences().getBoolean(key, false);
    }

    public static void addCustomPocketProfile(String key, String value) {
        getPreferences()
                .edit()
                .putString(key, value)
                .apply();
    }

    public static String getCustomPocketProfile(String key) {
        return getPreferences().getString(key, "");
    }
}
