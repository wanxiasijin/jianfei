package com.chenganrt.smartSupervision.business.electronic.ui;

import android.text.TextUtils;

import com.chenganrt.smartSupervision.business.electronic.util.AppConstant;
import com.chenganrt.smartSupervision.business.electronic.okhttp.FType;
import com.chenganrt.smartSupervision.business.electronic.SuperServerAction;

import java.io.Serializable;
import java.util.ArrayList;



public class Group<T> extends ArrayList<T> implements
        FType, Serializable, SuperServerAction.ActionResult {

    private static final long serialVersionUID = -1512413373608295999L;

    private String Result;//处理结果	0000
    private String ShowTips;//处理结果消息	“成功”
    private boolean isResult = true;
    private String totalCount;//内容总数

    @Override
    public boolean isSuccess() {
        if (!TextUtils.isEmpty(getResult()) && (getResult().equals(AppConstant.RESULT_CODE_SUCCESS)|| getResult().equals(AppConstant.RESULT_CODE_EMPTY))) {
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

    public int getTotalCount() {
        try {
            return Integer.parseInt(totalCount);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }

    @Override
    public String toString() {
        return "Group{" +
                ", returnCode='" + Result + '\'' +
                ", returnMsg='" + ShowTips + '\'' +
                ", totalCount='" + totalCount + '\'' +
                "} " + super.toString();
    }

}
