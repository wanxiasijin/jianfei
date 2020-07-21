package com.chenganrt.smartSupervision.business.electronic.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.chenganrt.smartSupervision.business.electronic.UserEntity;

import androidx.annotation.NonNull;


public class UserUtil {

    private final String USER_PREFERENCES = "elect_preferences";
    private final String PREFERENCE_USERNAME = "username";
    private final String PREFERENCE_USERID = "userid";
    private final String PREFERENCE_PAW = "paw";
    private final String PREFERENCE_CHECK_PAW = "check_paw";
    private final String PREFERENCE_COMPANY_NAME = "company_name";
    private final String PREFERENCE_REAL_NAME = "real_name";
    private final String PREFERENCE_USER_TYPE = "user_type";
    private final String PREFERENCE_BIND_VEHICLE = "bind_Vehicle";
    private final String PREFERENCE_VEHICLE_NO = "vehicle_No";
    private final String PREFERENCE_UPDATE_TIP = "update_tip";
    private final String PREFERENCE_FENCE_NAME = "fence_name";
    private final String PREFERENCE_FENCE_STATUS = "fence_status";
    private final String PREFERENCE_AUTO_CONFIRM = "confirm_auto";

    private static class SingleTonHolder {
        private static final UserUtil INSTANCE = new UserUtil();
    }

    public static UserUtil getInstance() {
        return SingleTonHolder.INSTANCE;
    }

    private UserUtil() {

    }

    private UserEntity mUserInfo;
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

    public void setUserInfo(Context context, @NonNull UserEntity userInfo) {
        mUserInfo = userInfo;
        putString(context, PREFERENCE_USERID, userInfo.getUserId());
        putString(context, PREFERENCE_USERNAME, userInfo.getUserName());
        putString(context, PREFERENCE_REAL_NAME, userInfo.getRealName());
        putString(context, PREFERENCE_USER_TYPE, userInfo.getUserType());
        putString(context, PREFERENCE_BIND_VEHICLE, userInfo.getIsBindVehicle());
        putString(context, PREFERENCE_VEHICLE_NO, userInfo.getVehicleNo());
        putString(context, PREFERENCE_COMPANY_NAME, userInfo.getEnterName());
        putString(context, PREFERENCE_FENCE_NAME, userInfo.getFenceName());
        putString(context, PREFERENCE_FENCE_STATUS, userInfo.getFenceStatus());
        putString(context, PREFERENCE_AUTO_CONFIRM, userInfo.getAutoConfirmed());
    }

    public UserEntity getUserInfo(Context context) {
        if (mUserInfo == null) {
            UserEntity tmp = new UserEntity();
            tmp.setUserId(getString(context, PREFERENCE_USERID, null));
            tmp.setUserName(getString(context, PREFERENCE_USERNAME, null));
            tmp.setRealName(getString(context, PREFERENCE_REAL_NAME, null));
            tmp.setUserType(getString(context, PREFERENCE_USER_TYPE, null));
            tmp.setIsBindVehicle(getString(context, PREFERENCE_BIND_VEHICLE, null));
            tmp.setVehicleNo(getString(context, PREFERENCE_VEHICLE_NO, null));
            tmp.setEnterName(getString(context, PREFERENCE_COMPANY_NAME, null));
            mUserInfo = tmp;
        }
        return mUserInfo;
    }

    public void setBindVehicle(Context context, String bindVehicle) {
        putString(context, PREFERENCE_BIND_VEHICLE, bindVehicle);
    }

    public String getBindVehicle(Context context) {
        return getString(context, PREFERENCE_BIND_VEHICLE, "");
    }

    public void setVehicleNo(Context context, String vehicleNo) {
        putString(context, PREFERENCE_VEHICLE_NO, vehicleNo);
    }

    public String getVehicleNo(Context context) {
        return getString(context, PREFERENCE_VEHICLE_NO, "");
    }

    public void setCheckPaw(Context context, boolean isCheck) {
        putBoolean(context, PREFERENCE_CHECK_PAW, isCheck);
    }

    public boolean getCheckPaw(Context context) {
        return getBoolean(context, PREFERENCE_CHECK_PAW, false);
    }

    public void setPaw(Context context, String paw) {
        putString(context, PREFERENCE_PAW, paw);
    }

    public String getPaw(Context context) {
        return getString(context, PREFERENCE_PAW, "");
    }

    public void setUpdateTip(Context context, int update) {
        putInt(context, PREFERENCE_UPDATE_TIP, update);
    }

    public int getUpdateTip(Context context) {
        return getInt(context, PREFERENCE_UPDATE_TIP, 0);
    }

    public void setUserName(Context context, String username) {
        putString(context, PREFERENCE_USERNAME, username);
    }

    public String getUserName(Context context) {
        return getString(context, PREFERENCE_USERNAME, "");
    }

    public String getCompanyName(Context context) {
        return getString(context, PREFERENCE_COMPANY_NAME, "");
    }

    public String getRealName(Context context) {
        return getString(context, PREFERENCE_REAL_NAME, "");
    }

    public String getUserType(Context context) {
        return getString(context, PREFERENCE_USER_TYPE, "");
    }

    public String getUserId(Context context) {
        return getString(context, PREFERENCE_USERID, "");
    }

    public String getFenceName(Context context) {
        return getString(context, PREFERENCE_FENCE_NAME, "");
    }

    public String getFenceStatus(Context context) {
        return getString(context, PREFERENCE_FENCE_STATUS, "");
    }

    public void setAutoConfirm(Context context, String autoConfirm) {
        putString(context, PREFERENCE_AUTO_CONFIRM, autoConfirm);
    }

    public String getAutoConfirm(Context context) {
        return getString(context, PREFERENCE_AUTO_CONFIRM, "");
    }

    private SharedPreferences getSharedPreferences(Context context) {
        SharedPreferences sp = context.getSharedPreferences(USER_PREFERENCES, Context.MODE_PRIVATE);
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
        if (!TextUtils.isEmpty(str)) {
            for (int i = 0; i < str.length(); i++) {
                char c = (char) (str.charAt(i) ^ KEY);
                encrypt.append(c);
            }
        }

        return encrypt.toString();
    }

}
