package com.chenganrt.smartSupervision.business.electronic.ui.adapter;

import com.chenganrt.smartSupervision.business.electronic.okhttp.Callback;
import com.chenganrt.smartSupervision.business.electronic.okhttp.OKHttpApi;
import com.chenganrt.smartSupervision.business.electronic.okhttp.RequestUrlConst;
import com.chenganrt.smartSupervision.business.electronic.parse.AnalyseDetailParser;
import com.chenganrt.smartSupervision.business.electronic.parse.AnalyseParser;
import com.chenganrt.smartSupervision.business.electronic.parse.HeadParser;
import com.chenganrt.smartSupervision.business.electronic.parse.MessageParser;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.Request;

import static com.chenganrt.smartSupervision.business.electronic.okhttp.RequestUrlConst.METHOD_BIND_VEHICLE;
import static com.chenganrt.smartSupervision.business.electronic.okhttp.RequestUrlConst.METHOD_GETMESSAGE;
import static com.chenganrt.smartSupervision.business.electronic.okhttp.RequestUrlConst.METHOD_UN_BIND_VEHICLE;
import static com.chenganrt.smartSupervision.business.electronic.okhttp.RequestUrlConst.METHOD_UPLOAD_ERROR;
import static com.chenganrt.smartSupervision.business.electronic.okhttp.RequestUrlConst.URL_BILLSIGN_QUARY;
import static com.chenganrt.smartSupervision.business.electronic.okhttp.RequestUrlConst.URL_MESSAGE_COUNT;
import static com.chenganrt.smartSupervision.business.electronic.okhttp.RequestUrlConst.URL_STATISTIC;


public class HomeAction {

    /**
     * 异常上报
     *
     * @param OrderID
     * @param Describe
     * @param UserID
     * @param callback
     */
    public void uploadExcepAction(String OrderID, String Describe, String UserID, Callback callback) {
        OKHttpApi api = OKHttpApi.getInstance();
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("No", METHOD_UPLOAD_ERROR);
        builder.add("UserID", String.valueOf(UserID));
        builder.add("OrderID", String.valueOf(OrderID));
        builder.add("Describe", String.valueOf(Describe));
        Request request = api.createHttpPost(RequestUrlConst.http, builder);
        api.enqueue(request, callback.setParser(new HeadParser()));
    }

    /**
     * 消息列表
     *
     * @param UserID
     * @param callback
     */
    public void messageAction(String UserID, String Page, String Size, Callback callback) {
        OKHttpApi api = OKHttpApi.getInstance();
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("No", METHOD_GETMESSAGE);
        builder.add("UserID", String.valueOf(UserID));
        builder.add("Page", String.valueOf(Page));
        builder.add("Size", String.valueOf(Size));
        Request request = api.createHttpPost(RequestUrlConst.http, builder);
        api.enqueue(request, callback.setParser(new MessageParser()));
    }

    /**
     * 绑定车牌号
     *
     * @param UserName
     * @param VehicleNo
     * @param callback
     */
    public void bindCarAction(String UserName, String VehicleNo, Callback callback) {
        OKHttpApi api = OKHttpApi.getInstance();
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("No", METHOD_BIND_VEHICLE);
        builder.add("UserName", String.valueOf(UserName));
        builder.add("VehicleNo", String.valueOf(VehicleNo));
        Request request = api.createHttpPost(RequestUrlConst.http, builder);
        api.enqueue(request, callback.setParser(new HeadParser()));
    }

    /**
     * 解绑车牌号
     *
     * @param UserName
     * @param callback
     */
    public void unbindCarAction(String UserName, Callback callback) {
        OKHttpApi api = OKHttpApi.getInstance();
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("No", METHOD_UN_BIND_VEHICLE);
        builder.add("UserName", String.valueOf(UserName));
        Request request = api.createHttpPost(RequestUrlConst.http, builder);
        api.enqueue(request, callback.setParser(new HeadParser()));
    }

    /**
     * 统计分析
     *
     * @param UserName
     * @param callback
     */
    public Call statisticAction(String UserName, String UserType, String BeginDate, String EndDate, String EbillType, String VehicleNo, Callback callback) {
        OKHttpApi api = OKHttpApi.getInstance();
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("No", URL_STATISTIC);
        builder.add("UserName", String.valueOf(UserName));
        builder.add("UserType", String.valueOf(UserType));
        builder.add("BeginDate", String.valueOf(BeginDate));
        builder.add("EndDate", String.valueOf(EndDate));
        builder.add("EbillType", String.valueOf(EbillType));
        builder.add("VehicleNo", String.valueOf(VehicleNo));
        Request request = api.createHttpPost(RequestUrlConst.http, builder);
        return api.enqueue(request, callback.setParser(new AnalyseParser()));
    }

    /**
     * 签认联单查询
     *
     * @param UserID
     * @param UserType
     * @param BeginDate
     * @param EndDate
     * @param VehicleNo
     * @param Page
     * @param Size
     * @param callback
     */
    public Call signQuaryAction(String UserName, String UserType, String BeginDate, String EndDate, String VehicleNo, String Page, String Size, Callback callback) {
        OKHttpApi api = OKHttpApi.getInstance();
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("No", URL_BILLSIGN_QUARY);
        builder.add("UserName", String.valueOf(UserName));
        builder.add("UserType", String.valueOf(UserType));
        builder.add("BeginDate", String.valueOf(BeginDate));
        builder.add("EndDate", String.valueOf(EndDate));
        builder.add("VehicleNo", String.valueOf(VehicleNo));
        builder.add("Page", String.valueOf(Page));
        builder.add("Size", String.valueOf(Size));
        Request request = api.createHttpPost(RequestUrlConst.http, builder);
        return api.enqueue(request, callback.setParser(new AnalyseDetailParser()));
    }

    /**
     * 消息统计
     *
     * @param UserID
     * @param callback
     */
    public void getMessageTotal(String UserID, Callback callback) {
        OKHttpApi api = OKHttpApi.getInstance();
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("No", URL_MESSAGE_COUNT);
        builder.add("UserID", String.valueOf(UserID));
        Request request = api.createHttpPost(RequestUrlConst.http, builder);
        api.enqueue(request, callback.setParser(new HeadParser()));
    }
}
