package com.chenganrt.smartSupervision.business.login;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.android.commonlib.okhttp.bean.User;

import androidx.annotation.NonNull;


public class UserSP {

    private final String USER_SP = "user_sp";
    private final String PREFERENCE_USERID = "user_id";
    private final String PREFERENCE_USERNAME = "user_name";
    private final String PREFERENCE_PASSWORD = "user_password";
    private final String PREFERENCE_USER_TYPE = "user_type";
    private final String PREFERENCE_PHONR = "mobile_phone";
    private final String PREFERENCE_APP_ACCOUNT= "app_account";
    private final String PREFERENCE_URL = "url";
    private final String PREFERENCE_PROJECT_NAME = "project_name";
    private final String PREFERENCE_ENTER_NAME = "enter_name";
    private final String PREFERENCE_TOKEN = "token";

    private static class SingleTonHolder {
        private static final UserSP INSTANCE = new UserSP();
    }

    public static UserSP getInstance() {
        return SingleTonHolder.INSTANCE;
    }

    private UserSP() {

    }

    private User mUserInfo;
    private final int KEY = 85572;


    public void clear(Context context) {
        mUserInfo = null;
        getEditor(context).clear().commit();
    }

    public void clearCache() {
        mUserInfo = null;
    }

    public void init(Context context) {
        SharedPreferences sp = getSharedPreferences(context);
    }

    public void setUserInfo(Context context, @NonNull User userInfo) {
        mUserInfo = userInfo;
        if (userInfo != null) {
            putString(context, PREFERENCE_USERID, userInfo.getUser_id());
            putString(context, PREFERENCE_USERNAME, userInfo.getUser_name());
            putInt(context, PREFERENCE_USER_TYPE, userInfo.getUser_type());
            putString(context, PREFERENCE_PHONR, userInfo.getMobile_phone());
            putString(context, PREFERENCE_APP_ACCOUNT, userInfo.getApp_account());
            putString(context, PREFERENCE_URL, userInfo.getUrl());
            putString(context, PREFERENCE_PROJECT_NAME, userInfo.getProject_name());
            putString(context, PREFERENCE_ENTER_NAME, userInfo.getEnter_name());
        }
    }

    public User getUserInfo(Context context) {
        return mUserInfo;
    }

    public void setPaw(Context context, String paw) {
        putString(context, PREFERENCE_PASSWORD, paw);
    }

    public String getPaw(Context context) {
        return getString(context, PREFERENCE_PASSWORD, "");
    }

    public void setUserName(Context context, String username) {
        putString(context, PREFERENCE_USERNAME, username);
    }

    public String getUserName(Context context) {
        return getString(context, PREFERENCE_USERNAME, "");
    }

    public String getUserId(Context context) {
        return getString(context, PREFERENCE_USERID, "");
    }

    public int getUserType(Context context) {
        return getInt(context, PREFERENCE_USER_TYPE, 0);
    }

    public void setUserType(Context context, String username) {
        putString(context, PREFERENCE_USER_TYPE, username);
    }

    public void setToken(Context context, String username) {
        putString(context, PREFERENCE_TOKEN, username);
    }

    public String getToken(Context context) {
        SharedPreferences sp = getSharedPreferences(context);
        return sp.getString(PREFERENCE_TOKEN, "");
    }

    private SharedPreferences getSharedPreferences(Context context) {
        SharedPreferences sp = context.getSharedPreferences(USER_SP, Context.MODE_PRIVATE);
        return sp;
    }

    public SharedPreferences.Editor getEditor(Context context) {
        return getSharedPreferences(context).edit();
    }

    public boolean putString(Context context, String key, String object) {
        return getEditor(context).putString(key, encodeStr(object)).commit();
    }

    public boolean putInt(Context context, String key, int object) {
        return getEditor(context).putInt(key, object).commit();
    }

    public boolean putBoolean(Context context, String key, boolean object) {
        return getEditor(context).putBoolean(key, object).commit();
    }

    public boolean putFloat(Context context, String key, float object) {
        return getEditor(context).putFloat(key, object).commit();
    }

    public boolean putLong(Context context, String key, long object) {
        return getEditor(context).putLong(key, object).commit();
    }

    public String getString(Context context, String key, String def) {
        SharedPreferences sp = getSharedPreferences(context);
        if (sp.contains(key)) {
            return decodeStr(sp.getString(key, def));
        } else {
            return def;
        }
    }

    public int getInt(Context context, String key, int def) {
        return getSharedPreferences(context).getInt(key, def);
    }

    public boolean getBoolean(Context context, String key, boolean def) {
        return getSharedPreferences(context).getBoolean(key, def);
    }

    public float getFloat(Context context, String key, float def) {
        return getSharedPreferences(context).getFloat(key, def);
    }

    public long getLong(Context context, String key, long def) {
        return getSharedPreferences(context).getLong(key, def);
    }

    private String decodeStr(String str) {
        return encryptDecrypt(str);
    }

    private String encodeStr(String str) {
        return encryptDecrypt(str);
    }

    private String encryptDecrypt(String str) {
        StringBuffer encrypt = new StringBuffer();
        if (str != null && !TextUtils.isEmpty(str)) {
            for (int i = 0; i < str.length(); i++) {
                char c = (char) (str.charAt(i) ^ KEY);
                encrypt.append(c);
            }
        }

        return encrypt.toString();
    }

}
