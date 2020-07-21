package com.chenganrt.smartSupervision.business.electronic.okhttp;

public interface ProgressCallback {
    void onProgress(long currentBytes, long contentLength, boolean done);
}