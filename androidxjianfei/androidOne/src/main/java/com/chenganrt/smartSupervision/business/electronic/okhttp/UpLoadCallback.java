package com.chenganrt.smartSupervision.business.electronic.okhttp;

import android.content.Context;
import android.os.Message;

import com.chenganrt.smartSupervision.business.electronic.Progress;

import org.json.JSONException;



public class UpLoadCallback extends UICallback {
    public UpLoadCallback(Context context) {
        super(context);
    }

    @Override
    public void handleRuning(Message msg) {
        Progress p = (Progress) msg.obj;
        onProgress(p.current, p.total, p.done);
    }

    @Override
    public boolean checkToken(String errorCode) {
        /*if (RESULT_CODE_TOKEN.equals(errorCode) && getContext() instanceof Activity) {
            new UserLoginAction(getContext()).excuteReToken(null);
            return false;
        }*/
        return super.checkToken(errorCode);
    }

    @Override
    protected Object onParser(String body) throws JSONException {
        if (parser == null) parser = new HeadParser();
        return super.onParser(body);
    }
}
