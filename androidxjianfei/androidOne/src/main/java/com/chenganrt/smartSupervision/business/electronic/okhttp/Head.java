package com.chenganrt.smartSupervision.business.electronic.okhttp;


import android.text.TextUtils;

import com.chenganrt.smartSupervision.business.electronic.util.AppConstant;
import com.chenganrt.smartSupervision.business.electronic.SuperServerAction;

import java.io.Serializable;



/**
 * 响应头
 */
public class Head implements FType, Serializable, SuperServerAction.ActionResult {

    private static final long serialVersionUID = 2198950801125232138L;
    private String Result;//处理结果	00000
    private String ShowTips;//处理结果消息	“成功”
    private boolean isResult = true;

    public Head() {
    }

    public Head(Head head) {
        setHead(head);
    }

    @Override
    public boolean isSuccess() {
        if (!TextUtils.isEmpty(getResult()) && (getResult().equals(AppConstant.RESULT_CODE_SUCCESS) || getResult().equals(AppConstant.RESULT_CODE_EMPTY))) {
            return true;
        }
        return false;
    }

    @Override
    public String getErrorCode() {
        return Result;
    }

    @Override
    public String getErrorMessage() {
        return ShowTips;
    }

    @Override
    public boolean isResult() {
        return isResult;
    }

    public void setResult(boolean result) {
        isResult = result;
    }


    public String getResult() {
        return Result;
    }

    public void setResult(String result) {
        Result = result;
    }

    public String getShowTips() {
        return ShowTips;
    }

    public void setShowTips(String showTips) {
        ShowTips = showTips;
    }

    public void setHead(Head head) {
        setResult(head.getErrorCode());
        setShowTips(head.getShowTips());
    }

    @Override
    public String toString() {
        return "Head{" +
                ", returnCode='" + Result + '\'' +
                '}';
    }
}
