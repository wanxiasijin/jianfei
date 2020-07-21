package com.android.commonlib.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.alibaba.fastjson.JSONReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import timber.log.Timber;

public class Utils {

    public static long parseTime(String time) {
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return calendar.getTimeInMillis();
    }

    public static boolean checkIp(String ip) {
        return ip.matches("((2[0-4]\\d|25[0-5]|[01]?\\d\\d?)\\.){3}(2[0-4]\\d|25[0-5]|[01]?\\d\\d?)");
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected() && info.getState() == NetworkInfo.State.CONNECTED) {
                return true;
            }
        }
        return false;
    }

    public static <T> List<T> readObjFromJSON(Context context, String fileName, Class<T> clz) {
        List<T> list = new ArrayList<>();
        try (InputStreamReader reader = new InputStreamReader(context.getAssets().open(fileName))) {
            JSONReader jsonReader = new JSONReader(reader);
            jsonReader.startArray();
            while (jsonReader.hasNext()) {
                T obj = jsonReader.readObject(clz);
                list.add(obj);
            }
            jsonReader.endArray();
            jsonReader.close();
        } catch (IOException e) {
            Timber.e(e);
        }
        return list;
    }

    public static String getTime(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        String formatedTime = simpleDateFormat.format(date);
        return formatedTime;
    }

    public static void setSoftInput(Context context, View view, boolean isShow) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            if (isShow) {
                boolean aaa = inputMethodManager.showSoftInput(view, 0);
            } else {
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    public static boolean isMobile(String mobile) {
        boolean flag = false;
        if (mobile.length() == 0) {
            return false;
        }
        String[] mobiles = mobile.split(",");
        int len = mobiles.length;
        if (len == 1) {
            return Pattern.matches("^((13[0-9])|(14[5,7,9])|(15[^4,\\D])|(17[0,1,3,5-8])|(18[0-9]))\\d{8}$", mobile);
        } else {
            for (int i = 0; i < len; i++) {
                if (isMobile(mobiles[i])) {
                    flag = true;
                } else {
                    flag = false;
                }
            }
        }
        return flag;
    }

    public static SharedPreferences getSp() {
        return LibContext.getInstance().getContext().getSharedPreferences("user_sp", Context.MODE_PRIVATE);
    }
}
