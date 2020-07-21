package com.chenganrt.smartSupervision.business.electronic;

import java.io.IOException;

import okhttp3.Call;


public class Progress {
    public long current = 0L;
    public long total = 0L;
    public boolean done;

    public Call call;
    public String response;
    public IOException e;

    public Progress(long current, long total, boolean done) {
        this.current = current;
        this.total = total;
        this.done = done;
    }

    public Progress(Call call, IOException e) {
        this.call = call;
        this.e = e;
    }

    public Progress(Call call, String response) {
        this.call = call;
        this.response = response;
    }
}