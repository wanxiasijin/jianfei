package com.chenganrt.smartSupervision.business.electronic.okhttp;

import android.os.Message;

import com.chenganrt.smartSupervision.business.electronic.Progress;
import com.chenganrt.smartSupervision.business.electronic.util.AppConstant;
import com.chenganrt.smartSupervision.business.electronic.util.FileUtil;

import java.io.File;
import java.io.IOException;

import androidx.annotation.NonNull;
import okhttp3.Call;
import okhttp3.Response;
import okio.BufferedSource;
import okio.Okio;
import okio.Sink;


public class DownLoadCallback extends Callback {
    public DownLoadCallback() {
        super();
    }

    @Override
    public void handleRuning(Message msg) {
        Progress p = (Progress) msg.obj;
        onProgress(p.current, p.total, p.done);
    }

    @Override
    public boolean baseDeal(Message msg) {
        return true;
    }

    @Override
    public void onFailure(@NonNull Call call, @NonNull IOException e) {
        handler.obtainMessage(AppConstant.TaskState.FAILURE, this.arg1, this.arg2, e.getMessage()).sendToTarget();
    }

    @Override
    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
        if (!isSuccessful(call, response, this)) return;
        try {
            File file = OKHttpApi.getInstance().getFile((OKTag) call.request().tag());
            BufferedSource source = response.body().source();
            Sink sink = Okio.sink(file);
            source.readAll(sink);
            FileUtil.closeQuietly(source);
            FileUtil.closeQuietly(sink);
            handler.obtainMessage(AppConstant.TaskState.SUCCESS, file.getAbsolutePath()).sendToTarget();
        } catch (IOException e) {
            onFailure(call, e);
        }
    }
}
