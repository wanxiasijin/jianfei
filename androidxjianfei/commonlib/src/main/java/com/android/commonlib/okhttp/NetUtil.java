package com.android.commonlib.okhttp;

import android.content.SharedPreferences;

import com.android.commonlib.utils.LogUtil;
import com.android.commonlib.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import timber.log.Timber;

public final class NetUtil {

    public static String post(String url, String imageUrl) throws IOException {
        MultipartBody.Builder requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
        File file = new File(imageUrl);
        // MediaType.parse() 里面是上传的文件类型。
        RequestBody body = RequestBody.create(MediaType.parse("image/*"), file);
        // 参数分别为， 请求key ，文件名称 ， RequestBody
        requestBody.addFormDataPart("file", file.getName(), body);

        Request request = new Request.Builder()
                .addHeader("Authorization", getToken())
                .url(url)
                .post(requestBody.build())
                .build();
        return call(request);
    }

    public static String post(String url, RequestBody body) throws IOException {
        Request request = new Request.Builder()
                .addHeader("Authorization", getToken())
                .url(url)
                .post(body)
                .build();
        return call(request);
    }

    public static String get(String url, Map<String, String> params) throws IOException {
        Timber.d("url : %s", url);
        Request.Builder builder = new Request.Builder();
        StringBuilder strBuilder = new StringBuilder(url);
        if (params != null) {
            strBuilder.append("?");
            for (Map.Entry entry : params.entrySet()) {
                strBuilder.append(entry.getKey() + "=" + entry.getValue() + "&");
            }
        }
        builder.url(strBuilder.toString())
                .get();
        return call(builder.build());
    }

    private static String call(Request request) throws IOException {
        LogUtil.d("request : %s", request.toString());
        //回调会开启线程池 这个我们交给rxJava来进行处理
        Response response = OkHttpClientHolder.getOkHttpClient().newCall(request).execute();
        if (!response.isSuccessful()) {
            throw new IOException("Unexpected code " + response);
        }
        LogUtil.d("call response:%s", response.toString());
        Headers headers = response.headers();
        if (headers != null) {
            String token = headers.get("Authorization");
            LogUtil.d("token:" + token);
            if (token != null && !token.isEmpty()) {
                saveToken(token);
            }
        }
        if (response.body() != null) {
            return response.body().string();
        }
        return null;
    }

    private static void saveToken(String token) {
        SharedPreferences.Editor editor = Utils.getSp().edit();
        editor.putString("token", token);
        editor.apply();
    }

    private static String getToken() {
        return Utils.getSp().getString("token", "");
    }
}
