package com.softvision.botanica.bl.persist;

import android.content.Context;
import android.content.SharedPreferences;

public class PersistantUtils {
    private static final String SETTINGS_SHARED_PREFS = "SettingsSharedPrefs";
    private static final String USER_EMAIL = "UserEmail";
    private static final String LAST_ERROR_CODE = "ErrorCode";

    public static boolean isLoggedIn(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SETTINGS_SHARED_PREFS, Context.MODE_PRIVATE);
        return sharedPreferences.contains(USER_EMAIL);
    }

    public static String getUserEmail(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SETTINGS_SHARED_PREFS, Context.MODE_PRIVATE);
        return sharedPreferences.getString(USER_EMAIL, "test@yahoo.com");
    }

    public static void setLoginInfo(Context context, String email) {
        if (context == null) return;
        SharedPreferences sharedPreferences = context.getSharedPreferences(SETTINGS_SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_EMAIL, email);
        editor.commit();
    }

    public static void setLastErrorCode(Context context, int error) {
        if (context == null) return;
        SharedPreferences sharedPreferences = context.getSharedPreferences(SETTINGS_SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(LAST_ERROR_CODE, error);
        editor.commit();
    }

    public static int getLastErrorCode(Context context) {
        if (context == null) return 0;

        SharedPreferences sharedPreferences = context.getSharedPreferences(SETTINGS_SHARED_PREFS, Context.MODE_PRIVATE);

        return sharedPreferences.getInt(LAST_ERROR_CODE, 0);
    }
}
