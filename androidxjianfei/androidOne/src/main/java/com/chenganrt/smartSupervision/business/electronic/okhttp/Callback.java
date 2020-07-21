package com.chenganrt.smartSupervision.business.electronic.okhttp;

import android.os.Handler;
import android.os.Message;

import com.chenganrt.smartSupervision.business.electronic.parse.Parser;
import com.chenganrt.smartSupervision.business.electronic.SuperServerAction;
import com.chenganrt.smartSupervision.business.electronic.util.AppConstant;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import androidx.annotation.NonNull;
import okhttp3.Call;
import okhttp3.Response;

public class Callback<T> implements Handler.Callback, ProgressCallback, okhttp3.Callback {

    protected int arg1, arg2;
    protected Handler handler;
    protected Parser<T> parser;
    protected JSONObject data;

    public Callback() {
        this.handler = new Handler(this);
    }

    public Callback setArg1(int arg1) {
        this.arg1 = arg1;
        return this;
    }

    public Callback setArg2(int arg2) {
        this.arg2 = arg2;
        return this;
    }

    public Callback setArg(int arg1, int arg2) {
        this.arg1 = arg1;
        this.arg2 = arg2;
        return this;
    }

    public Callback setParser(Parser parser) {
        this.parser = parser;
        return this;
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case AppConstant.TaskState.ISRUNING:
                onStart();
                handleRuning(msg);
                break;
            case AppConstant.TaskState.SUCCESS:
                onFinish();
                if (baseDeal(msg)) handleSuccess(msg);
                break;
            case AppConstant.TaskState.FAILURE:
                onFinish();
                handleFailure(msg);
                break;
            case AppConstant.TaskState.PAUSE:
                onFinish();
                handlePause(msg);
                break;
            case AppConstant.TaskState.CODEEXCEPTION:
                onFinish();
                handleCodeException(msg);
            default:
                break;
        }
        return true;
    }


    public void handleCodeException(Message msg) {
    }

    public void handleSuccess(Message msg) {
    }

    public void handleRuning(Message msg) {
    }

    public void handleFailure(Message msg) {
    }

    public void handlePause(Message msg) {
    }

    public boolean baseDeal(Message msg) {
        boolean deal = msg.obj != null;
        if (msg.obj instanceof SuperServerAction.ActionResult) {
            SuperServerAction.ActionResult result = (SuperServerAction.ActionResult) msg.obj;
            deal = result.isSuccess();
        }
        if (!deal) handleFailure(msg);
        return deal;
    }

    @Override
    public void onProgress(long currentBytes, long contentLength, boolean done) {

    }

    public void onRuning() {
        handler.obtainMessage(AppConstant.TaskState.ISRUNING, this.arg1, this.arg2).sendToTarget();
    }

    public void onStart() {

    }

    public void onFinish() {

    }

    @Override
    public void onFailure(@NonNull Call call, @NonNull IOException e) {
        if (e.toString().contains("Canceled") || e.toString().contains("closed")) {//如果是主动取消的情况下
            handler.obtainMessage(AppConstant.TaskState.PAUSE, this.arg1, this.arg2).sendToTarget();
        } else {
            handler.obtainMessage(AppConstant.TaskState.FAILURE, this.arg1, this.arg2).sendToTarget();
        }
    }

    @Override
    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
        if (!isSuccessful(call, response, this)) return;
        try {
            String body = response.body().string();
            handler.obtainMessage(AppConstant.TaskState.SUCCESS, arg1, arg2, onParser(body)).sendToTarget();
        } catch (Exception e) {
            onFailure(call, new IOException(e));
        }
    }

    public boolean isSuccessful(Call call, Response response, okhttp3.Callback callback) {
        if (response.isSuccessful() && response.body() != null) return true;
        callback.onFailure(call, new IOException("Unexpected code " + response));
        return false;
    }

    protected T onParser(String body) throws JSONException {
        data = new JSONObject(body);
        if (parser == null) return null;
        return parser.parse(data);
    }

    protected String getJsonFiled(String name) {
        if (!hasJsonObject("Content")) return null;
        try {
            JSONObject jsonObject = data.getJSONObject("Content");
            if (jsonObject != null && jsonObject.has(name) && !jsonObject.isNull(name)) {
                String value = jsonObject.getString(name);
                return value;
            }
            return null;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected boolean hasJsonObject(String name) {
        return data != null && data.has(name);
    }

    protected String optJsonString(String name) {
        return data != null ? data.optString(name) : null;
    }

    protected boolean optJsonBoolean(String name) {
        return data != null && data.optBoolean(name);
    }

    public Handler getHandler() {
        return handler;
    }
}
