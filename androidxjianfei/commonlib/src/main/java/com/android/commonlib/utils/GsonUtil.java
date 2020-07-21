package com.android.commonlib.utils;

import android.content.Context;
import android.content.res.AssetManager;

import com.google.gson.Gson;
import com.google.gson.JsonNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class GsonUtil {

    public static String getJson(Context context, String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = context.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line = "";
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            LogUtil.d("", "getJson IOException" + e.toString());
        }
        return stringBuilder.toString();
    }

    /**
     * 将字符串转换为 对象
     *
     * @param json
     * @param type
     * @return
     */
    public static <T> T JsonToObject(String json, Class<T> type) {
        try {
            Gson gson = new Gson();
            return gson.fromJson(json, type);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String toJson(Object src) {
        try {
            Gson gson = new Gson();
            return gson.toJson(src);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
