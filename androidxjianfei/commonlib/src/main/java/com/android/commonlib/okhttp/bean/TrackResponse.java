package com.android.commonlib.okhttp.bean;

import java.util.List;

public class TrackResponse {
    private int status;
    private String message;
    private List<TrackInfo> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<TrackInfo> getData() {
        return data;
    }

    public void setData(List<TrackInfo> data) {
        this.data = data;
    }
}
