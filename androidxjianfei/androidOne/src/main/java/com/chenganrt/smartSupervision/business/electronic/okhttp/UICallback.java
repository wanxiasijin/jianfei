package com.chenganrt.smartSupervision.business.electronic.okhttp;

import android.app.Activity;
import android.content.Context;
import android.os.Message;
import android.text.TextUtils;

import com.chenganrt.smartSupervision.R;
import com.chenganrt.smartSupervision.business.electronic.SuperServerAction;
import com.chenganrt.smartSupervision.business.electronic.activity.BaseActivity;
import com.chenganrt.smartSupervision.business.electronic.mainctrl.MainControllerFactory;
import com.chenganrt.smartSupervision.business.electronic.util.AppConstant;
import com.chenganrt.smartSupervision.business.electronic.util.ToastUtil;

import java.lang.ref.WeakReference;




public class UICallback extends Callback {

    private WeakReference<Context> mWeakReference;
    private boolean loadDialog;
    private boolean isClose = false;

    public UICallback(Context context) {
        super();
        this.mWeakReference = new WeakReference<>(context);
    }

    public UICallback(Context context, boolean loadDialog) {
        this(context);
        this.loadDialog = loadDialog;
    }

    public UICallback(Context context, boolean loadDialog, boolean isClose) {
        this(context);
        this.loadDialog = loadDialog;
        this.isClose = isClose;
    }

    public UICallback loadDialog() {
        this.loadDialog = true;
        return this;
    }

    @Override
    public boolean handleMessage(Message msg) {
        if (isFinishing()) return true;
        return super.handleMessage(msg);
    }

    @Override
    public void onStart() {
        if (loadDialog && !isFinishing() && getContext() instanceof BaseActivity) {
            ((BaseActivity) getContext()).showLoadingDialog(R.string.loading, isClose);
        }
    }

    @Override
    public void onFinish() {
        if (loadDialog && !isFinishing() && getContext() instanceof BaseActivity) {
            ((BaseActivity) getContext()).dismissLoadingDialog();
        }
    }

    @Override
    public void handleSuccess(Message msg) {
    }

    @Override
    public void handleRuning(Message msg) {
    }

    @Override
    public void handlePause(Message msg) {
        super.handlePause(msg);
    }

    @Override
    public void handleFailure(Message msg) {
        if (getContext() == null) return;
        if (msg.obj == null) {
            ToastUtil.showToast(getContext(), R.string.request_faild);
        } else if (msg.obj instanceof SuperServerAction.ActionResult) {
            SuperServerAction.ActionResult result = (SuperServerAction.ActionResult) msg.obj;
            if (TextUtils.isEmpty(result.getErrorMessage())) {
                ToastUtil.showToast(getContext(), R.string.request_faild);
            } else if (AppConstant.RESULT_CODE_EMPTY.equals(result.getErrorMessage())) {
            } else {
                ToastUtil.showToast(getContext(), result.getErrorMessage());
            }
        }
    }

    @Override
    public void handleCodeException(Message msg) {
        if (getContext() == null) return;
        if (getContext() instanceof Activity) {
            ToastUtil.showToast(getContext(), R.string.tokenExcep);
            MainControllerFactory.getApi().needReloain((Activity) getContext());
        }
    }

    public boolean isFinishing() {
        if (getContext() == null) return true;
        if (getContext() instanceof Activity) {
            Activity activity = (Activity) getContext();
            return activity.isFinishing();
        }
        return true;
    }

    @Override
    public boolean baseDeal(Message msg) {
        boolean deal = msg.obj != null;
        if (msg.obj instanceof SuperServerAction.ActionResult) {
            SuperServerAction.ActionResult result = (SuperServerAction.ActionResult) msg.obj;
            if (result.isSuccess()) return true;
            //if (checkToken(result.getErrorCode())) return false;//不需要分发错误回掉 Activity已经结束
            deal = false;
        }
        if (!deal && !isFinishing()) handleFailure(msg);
        return deal;
    }

    public boolean checkToken(String errorCode) {
        /*if (!TextUtils.isEmpty(errorCode) && getContext() instanceof Activity) {
            TokenControllerV3 controller = TokenControllerV3.getIns(getContext());
            return controller.checkToken((Activity) getContext(), errorCode);
        }*/
        return false;
    }

    public Context getContext() {
        return mWeakReference.get();
    }
}
