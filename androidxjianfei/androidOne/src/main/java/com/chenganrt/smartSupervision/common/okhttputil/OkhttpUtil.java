package com.chenganrt.smartSupervision.common.okhttputil;

import android.content.Context;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Created by fighting on 2017/4/7.
 */

public class OkhttpUtil {

    public static final String METHOD_GET = "GET";
    public static final String METHOD_POST = "POST";
    public static final String METHOD_PUT = "PUT";
    public static final String METHOD_DELETE = "DELETE";

    public static final String FILE_TYPE_FILE = "file/*";
    public static final String FILE_TYPE_IMAGE = "image/*";
    public static final String FILE_TYPE_AUDIO = "audio/*";
    public static final String FILE_TYPE_VIDEO = "video/*";

    public static void okHttpGet(Context context,String url, CallBackUtil callBack) {
        okHttpGet(context,url, null, null, callBack);
    }
    public static void okHttpGet(Context context,String url, Map<String, String> paramsMap, CallBackUtil callBack) {
        okHttpGet(context,url, paramsMap, null, callBack);
    }
    public static void okHttpGet(Context context,String url, Map<String, String> paramsMap, Map<String, String> headerMap, CallBackUtil callBack) {
        new RequestUtil(context,METHOD_GET, url, paramsMap, headerMap, callBack).execute();
    }
    public static void okHttpPost(Context context,String url, CallBackUtil callBack) {
        okHttpPost(context,url, null, callBack);
    }
    public static void okHttpPost(Context context,String url, Map<String, String> paramsMap, CallBackUtil callBack) {
        okHttpPost(context,url, paramsMap, null, callBack);
    }

    public static void okHttpPost(Context context,String url, Map<String, String> paramsMap, Map<String, String> headerMap, CallBackUtil callBack) {
        new RequestUtil(context,METHOD_POST, url, paramsMap, headerMap, callBack).execute();
    }
    public static void okHttpPostJson(Context context,String url, String jsonStr, CallBackUtil callBack) {
        okHttpPostJson(context,url, jsonStr, null, callBack);
    }
    public static void okHttpPostJson(Context context,String url, String jsonStr, Map<String, String> headerMap, CallBackUtil callBack) {
        new RequestUtil(context,METHOD_POST, url, jsonStr, headerMap, callBack).execute();
    }








}
