package com.chenganrt.smartSupervision.business.electronic.activity;

import com.chenganrt.smartSupervision.business.electronic.okhttp.Callback;
import com.chenganrt.smartSupervision.business.electronic.okhttp.RequestUrlConst;
import com.chenganrt.smartSupervision.business.electronic.okhttp.HeadParser;
import com.chenganrt.smartSupervision.business.electronic.okhttp.OKHttpApi;
import com.chenganrt.smartSupervision.business.electronic.parse.AboutParser;
import com.chenganrt.smartSupervision.business.electronic.parse.UserParser;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.Request;

import static com.chenganrt.smartSupervision.business.electronic.okhttp.RequestUrlConst.METHOD_ABOUTUS;
import static com.chenganrt.smartSupervision.business.electronic.okhttp.RequestUrlConst.METHOD_FOUNDPWD;
import static com.chenganrt.smartSupervision.business.electronic.okhttp.RequestUrlConst.METHOD_GETCODE;
import static com.chenganrt.smartSupervision.business.electronic.okhttp.RequestUrlConst.METHOD_LOGIN_SERVLET;
import static com.chenganrt.smartSupervision.business.electronic.okhttp.RequestUrlConst.METHOD_LOGOUT;
import static com.chenganrt.smartSupervision.business.electronic.okhttp.RequestUrlConst.METHOD_RESETPWD;
import static com.chenganrt.smartSupervision.business.electronic.okhttp.RequestUrlConst.URL_PAW_FOUND;


/**
 * Created by Administrator on 2019/5/15.
 */

public class LoginAction {

    /**
     * 登录
     * params.put(PARAM_NO, METHOD_LOGIN_SERVLET);
     * params.put(PARAM_USER_NAME, UserName);
     * params.put(PARAM_PASSWORD, PassWord);
     * params.put(PARAM_REMEMBERPWD, RememberPwd);
     * params.put(PARAM_DEVICEID, DeviceID);
     *
     * @param PassWord
     * @param RememberPwd
     * @param DeviceID
     * @param callback
     */
    public Call login(String UserName, String PassWord, String RememberPwd, String DeviceID, Callback callback) {
        OKHttpApi api = OKHttpApi.getInstance();
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("No", METHOD_LOGIN_SERVLET);
        builder.add("UserName", UserName);
        builder.add("PassWord", String.valueOf(PassWord));
        builder.add("RememberPwd", String.valueOf(RememberPwd));
        builder.add("DeviceID", String.valueOf(DeviceID));
        Request request = api.createHttpPost(RequestUrlConst.http, builder);
        return api.enqueue(request, callback.setParser(new UserParser()));
    }

    /**
     * 退出登录
     *
     * @param UserID
     * @param callback
     */
    public void logOut(String UserID, Callback callback) {
        OKHttpApi api = OKHttpApi.getInstance();
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("No", METHOD_LOGOUT);
        builder.add("UserID", String.valueOf(UserID));
        Request request = api.createHttpPost(RequestUrlConst.http, builder);
        api.enqueue(request, callback.setParser(new HeadParser()));
    }

    /**
     * 获取验证码
     *
     * @param Tel
     * @param callback
     */
    public void getVerifiCode(String Tel, Callback callback) {
        OKHttpApi api = OKHttpApi.getInstance();
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("No", METHOD_GETCODE);
        builder.add("Tel", String.valueOf(Tel));
        Request request = api.createHttpPost(RequestUrlConst.http, builder);
        api.enqueue(request, callback.setParser(new HeadParser()));
    }

    /**
     * 找回密码
     *
     * @param Tel
     * @param Code
     * @param callback
     */
    public void forgetPaw(String Tel, String Code, Callback callback) {
        OKHttpApi api = OKHttpApi.getInstance();
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("No", METHOD_FOUNDPWD);
        builder.add("Tel", String.valueOf(Tel));
        builder.add("Code", String.valueOf(Code));
        Request request = api.createHttpPost(RequestUrlConst.http, builder);
        api.enqueue(request, callback.setParser(new HeadParser()));
    }

    /**
     * 重置密码
     *
     * @param UserName
     * @param NewPwd
     * @param callback
     */
    public Call resetPaw(String UserName, String OldPwd, String NewPwd, Callback callback) {
        OKHttpApi api = OKHttpApi.getInstance();
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("No", METHOD_RESETPWD);
        builder.add("UserName", String.valueOf(UserName));
        builder.add("OldPwd", String.valueOf(OldPwd));
        builder.add("NewPwd", String.valueOf(NewPwd));
        Request request = api.createHttpPost(RequestUrlConst.http, builder);
        return api.enqueue(request, callback.setParser(new HeadParser()));
    }

    /**
     * 找回密码
     *
     * @param Tel
     * @param PassWord
     * @param callback
     * @return
     */
    public Call findPaw(String Tel, String PassWord, Callback callback) {
        OKHttpApi api = OKHttpApi.getInstance();
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("No", URL_PAW_FOUND);
        builder.add("Tel", String.valueOf(Tel));
        builder.add("PassWord", String.valueOf(PassWord));
        Request request = api.createHttpPost(RequestUrlConst.http, builder);
        return api.enqueue(request, callback.setParser(new HeadParser()));
    }


    /**
     * 关于我们
     *
     * @param callback
     */
    public void aboutAction(Callback callback) {
        OKHttpApi api = OKHttpApi.getInstance();
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("No", METHOD_ABOUTUS);
        Request request = api.createHttpPost(RequestUrlConst.http, builder);
        api.enqueue(request, callback.setParser(new AboutParser()));
    }
}
