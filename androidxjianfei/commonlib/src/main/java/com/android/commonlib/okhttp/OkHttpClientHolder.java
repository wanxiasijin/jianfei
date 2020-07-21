package com.android.commonlib.okhttp;


import com.android.commonlib.utils.LogUtil;

import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public final class OkHttpClientHolder {
    private static final int DEFAULT_CONNECT_TIMEOUT = 10 * 1000;
    private static final int DEFAULT_WRITE_TIMEOUT = 10 * 1000;
    private static final int DEFAULT_READ_TIMEOUT = 10 * 1000;
    private static volatile OkHttpClient instance;

    public static OkHttpClient getOkHttpClient() {
        if (instance == null) {
            synchronized (OkHttpClientHolder.class) {
                if (instance == null) {
                    HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                    logging.setLevel(HttpLoggingInterceptor.Level.BODY);
                    OkHttpClient.Builder builder = new OkHttpClient.Builder()
                            .connectTimeout(DEFAULT_CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
                            .writeTimeout(DEFAULT_WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
                            .readTimeout(DEFAULT_READ_TIMEOUT, TimeUnit.MILLISECONDS);
//                            .addInterceptor(logging)
//                            .addInterceptor(new CaptureInfoInterceptor());
//                    builder.sslSocketFactory(createSSLSocketFactory());
//                    builder.hostnameVerifier(new HostNameVerify());
                    instance = builder.build();
                }
            }
        }
        return instance;
    }

    private static SSLSocketFactory createSSLSocketFactory() {
        SSLSocketFactory ssfFactory = null;
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{new TrustAllCerts()}, new SecureRandom());
            ssfFactory = sc.getSocketFactory();
        } catch (Exception e) {
            LogUtil.e(e.getMessage());
        }
        return ssfFactory;
    }


}
