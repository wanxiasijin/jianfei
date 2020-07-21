package com.chenganrt.smartSupervision.business.electronic.util;

import com.chenganrt.smartSupervision.business.electronic.ListItem;
import com.chenganrt.smartSupervision.business.electronic.okhttp.OKHttpApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class JsonUtil {

    public static String getString(JSONObject jsonObject, String key) {
        String value = "";
        if (jsonObject != null && jsonObject.has(key))
            try {
                if (jsonObject.get(key) != JSONObject.NULL && !jsonObject.isNull(key)) {
                    value = jsonObject.getString(key);
                }
            } catch (Exception e) {
                LogUtil.e(e.getMessage());
            }
        return value;
    }

    public static int getInt(JSONObject jsonObject, String key) {
        int value = 0;
        if (jsonObject != null && jsonObject.has(key))
            try {
                if (jsonObject.get(key) != JSONObject.NULL && !jsonObject.isNull(key)) {
                    value = jsonObject.getInt(key);
                }
            } catch (Exception e) {
                LogUtil.e(e.getMessage());
            }
        return value;
    }

    public static boolean getBoolean(JSONObject jsonObject, String key) {
        boolean value = false;
        if (jsonObject != null && jsonObject.has(key))
            try {
                value = jsonObject.getBoolean(key);
                return value;
            } catch (Exception e) {
                LogUtil.e(e.getMessage());
            }
        return value;
    }

    public static <T extends ListItem> T getListEntity(JSONObject jsonObject, String key, T t) {
        if (jsonObject != null && jsonObject.has(key) && t != null) {
            try {
                String object = jsonObject.getString(key);
                if (object != null) {
                    JSONObject json = jsonObject.getJSONObject(key);
                    t.parsFromJson(json);
                    return t;
                }
            } catch (Exception e) {
                LogUtil.e(e.getMessage());
            }
        }
        return null;
    }

    public static <T extends ListItem> ArrayList<T> parsListFromJson(JSONObject jsonObject, String key, T t) {
        ArrayList<T> mlist = null;
        if (jsonObject != null && jsonObject.has(key) && t != null) {
            try {
                String object = jsonObject.getString(key);
                if (object != null) {
                    JSONArray jsonArray = jsonObject.getJSONArray(key);
                    if (jsonArray != null && jsonArray.length() > 0) {
                        mlist = new ArrayList<T>();
                        T tempT = t;
                        for (int i = 0; i < jsonArray.length(); i++) {
                            if (tempT == null) {
                                tempT = (T) t.newObject();
                            }
                            tempT.parsFromJson(jsonArray.getJSONObject(i));
                            mlist.add(tempT);
                            tempT = null;
                        }
                        return mlist;
                    }
                }
            } catch (Exception e) {
                LogUtil.e(e.getMessage());
            }
        }
        return mlist;
    }

    public static <T extends ListItem> T getEntityFromJson(JSONObject jsonObject, String key, T t) {
        if (t == null)
            return null;
        if (jsonObject != null && jsonObject.has(key)) {
            try {
                String object = jsonObject.getString(key);
                if (object != null && !object.toString().equals("null")) {
                    JSONObject json = jsonObject.getJSONObject(key);
                    t.parsFromJson(json);
                    return t;
                }
            } catch (Exception e) {
                LogUtil.e(e.getMessage());
            }
        }
        return null;
    }

    public static void put(JSONObject json, String key, Object obj) {
        if (json == null) return;
        try {
            json.put(key, obj);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static int getStatusCode(String json) {
        int statusCode = 200;
        if (json == null) return OKHttpApi.Http.ERROR_CODE_IO;
        try {
            JSONObject obj = new JSONObject(json);
            if (obj.has("statusCode"))
                statusCode = obj.getInt("statusCode");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return statusCode;
    }

    public static Object get(JSONObject json, String key) {
        try {
            if (json != null && !json.isNull(key) && json.has(key)) return json.get(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

}
