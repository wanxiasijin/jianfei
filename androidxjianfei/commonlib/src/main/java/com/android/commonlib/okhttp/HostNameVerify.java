package com.android.commonlib.okhttp;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;


public final class HostNameVerify implements HostnameVerifier {

    @Override
    public boolean verify(String hostname, SSLSession session) {
        return true;
    }

}
