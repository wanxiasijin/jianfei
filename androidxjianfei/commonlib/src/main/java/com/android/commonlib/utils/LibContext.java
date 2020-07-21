package com.android.commonlib.utils;

import android.content.Context;

public class LibContext {
    private static volatile LibContext instance;
    private Context mContext;

    public Context getContext() {
        return mContext;
    }

    public void setContext(Context mContext) {
        this.mContext = mContext;
    }

    public static LibContext getInstance() {
        if (instance == null) {
            synchronized(LibContext.class) {
                if (instance == null) {
                    instance = new LibContext();
                }
            }
        }
        return instance;
    }
}
