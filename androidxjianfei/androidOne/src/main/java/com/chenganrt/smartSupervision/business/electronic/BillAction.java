package com.chenganrt.smartSupervision.business.electronic;

import com.chenganrt.smartSupervision.business.electronic.okhttp.Callback;
import com.chenganrt.smartSupervision.business.electronic.okhttp.HeadParser;
import com.chenganrt.smartSupervision.business.electronic.okhttp.OKHttpApi;
import com.chenganrt.smartSupervision.business.electronic.okhttp.RequestUrlConst;
import com.chenganrt.smartSupervision.business.electronic.parse.BillAllParser;
import com.chenganrt.smartSupervision.business.electronic.parse.BuildParser;
import com.chenganrt.smartSupervision.business.electronic.parse.GroupGsonParser;
import com.chenganrt.smartSupervision.business.electronic.parse.GsonParse;
import com.chenganrt.smartSupervision.business.electronic.parse.OrderDetailParser;
import com.chenganrt.smartSupervision.business.electronic.parse.SoilTypeParser;
import com.chenganrt.smartSupervision.business.electronic.parse.VehicleParser;
import com.chenganrt.smartSupervision.business.electronic.parse.entiy.AccomEntity;
import com.chenganrt.smartSupervision.business.electronic.parse.entiy.FieldDetailEntity;
import com.chenganrt.smartSupervision.business.electronic.parse.entiy.FieldEntity;
import com.chenganrt.smartSupervision.business.electronic.parse.wayBillParser;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.Request;

import static com.chenganrt.smartSupervision.business.electronic.okhttp.RequestUrlConst.METHOD_CONFIRM;
import static com.chenganrt.smartSupervision.business.electronic.okhttp.RequestUrlConst.METHOD_EDIT_ORDER_DETAILS;
import static com.chenganrt.smartSupervision.business.electronic.okhttp.RequestUrlConst.METHOD_GET_DETAILS;
import static com.chenganrt.smartSupervision.business.electronic.okhttp.RequestUrlConst.METHOD_NOSPOIL;
import static com.chenganrt.smartSupervision.business.electronic.okhttp.RequestUrlConst.METHOD_ORDER_LIST;
import static com.chenganrt.smartSupervision.business.electronic.okhttp.RequestUrlConst.METHOD_SAVE_ORDER_DETAILS;
import static com.chenganrt.smartSupervision.business.electronic.okhttp.RequestUrlConst.METHOD_SEARCH_ORDER;
import static com.chenganrt.smartSupervision.business.electronic.okhttp.RequestUrlConst.METHOD_UPLOAD_ERROR;
import static com.chenganrt.smartSupervision.business.electronic.okhttp.RequestUrlConst.URL_APPLY_ACCOM;
import static com.chenganrt.smartSupervision.business.electronic.okhttp.RequestUrlConst.URL_BILL_ALL;
import static com.chenganrt.smartSupervision.business.electronic.okhttp.RequestUrlConst.URL_BUILD_INFO;
import static com.chenganrt.smartSupervision.business.electronic.okhttp.RequestUrlConst.URL_FIELD_DETAIL;
import static com.chenganrt.smartSupervision.business.electronic.okhttp.RequestUrlConst.URL_FIELD_TYPE;
import static com.chenganrt.smartSupervision.business.electronic.okhttp.RequestUrlConst.URL_LOAD_IMAGE;
import static com.chenganrt.smartSupervision.business.electronic.okhttp.RequestUrlConst.URL_SOIL_REPORT;
import static com.chenganrt.smartSupervision.business.electronic.okhttp.RequestUrlConst.URL_TYPE_REPORT;
import static com.chenganrt.smartSupervision.business.electronic.okhttp.RequestUrlConst.URL_VEHICLE_LIST;
import static com.chenganrt.smartSupervision.business.electronic.okhttp.RequestUrlConst.URL_VEHICLE_REPAIR;


public class BillAction {

    /**
     * 运单详情
     *
     * @param OrderID
     * @param callback
     */
    public void wayBillDetailAction(String OrderID, String UserID, Callback callback) {
        OKHttpApi api = OKHttpApi.getInstance();
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("No", METHOD_EDIT_ORDER_DETAILS);
        builder.add("OrderID", String.valueOf(OrderID));
        builder.add("UserID", String.valueOf(UserID));
        Request request = api.createHttpPost(RequestUrlConst.http, builder);
        api.enqueue(request, callback.setParser(new OrderDetailParser()));
    }

    /**
     * 修改运单详情
     *
     * @param OrderID
     * @param Loading
     * @param ReceivingID
     * @param UserID
     * @param WasteType
     * @param callback
     */
    public void updateWayBillAction(String OrderID, String Loading, String ReceivingID, String UserID, String WasteType, Callback callback) {
        OKHttpApi api = OKHttpApi.getInstance();
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("No", METHOD_SAVE_ORDER_DETAILS);
        builder.add("OrderID", String.valueOf(OrderID));
        builder.add("Loading", String.valueOf(Loading));
        builder.add("ReceivingID", String.valueOf(ReceivingID));
        builder.add("UserID", String.valueOf(UserID));
        builder.add("WasteType", String.valueOf(WasteType));
        Request request = api.createHttpPost(RequestUrlConst.http, builder);
        api.enqueue(request, callback.setParser(new HeadParser()));
    }


    /**
     * 订单详情
     *
     * @param OrderID
     * @param UserID
     * @param callback
     */
    public void orderDetailAction(String OrderID, String UserID, String UserType, String SignStatus, Callback callback) {
        OKHttpApi api = OKHttpApi.getInstance();
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("No", METHOD_GET_DETAILS);
        builder.add("OrderID", String.valueOf(OrderID));
        builder.add("UserID", String.valueOf(UserID));
        builder.add("UserType", String.valueOf(UserType));
        builder.add("SignStatus", String.valueOf(SignStatus));
        Request request = api.createHttpPost(RequestUrlConst.http, builder);
        api.enqueue(request, callback.setParser(new wayBillParser()));
    }

    public void orderDriverDetailAction(String OrderID, String UserID, String UserType, Callback callback) {
        OKHttpApi api = OKHttpApi.getInstance();
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("No", METHOD_GET_DETAILS);
        builder.add("OrderID", String.valueOf(OrderID));
        builder.add("UserID", String.valueOf(UserID));
        builder.add("UserType", String.valueOf(UserType));
        Request request = api.createHttpPost(RequestUrlConst.http, builder);
        api.enqueue(request, callback.setParser(new wayBillParser()));
    }

    /**
     * 废土上报
     *
     * @param OrderID
     * @param UserID
     * @param callback
     */
    public void landUpAction(String OrderID, String UserID, Callback callback) {
        OKHttpApi api = OKHttpApi.getInstance();
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("No", METHOD_NOSPOIL);
        builder.add("OrderID", String.valueOf(OrderID));
        builder.add("UserID", String.valueOf(UserID));
        Request request = api.createHttpPost(RequestUrlConst.http, builder);
        api.enqueue(request, callback.setParser(new HeadParser()));
    }

    /**
     * 运单提交
     *
     * @param OrderID
     * @param UserID
     * @param callback
     */
    public void subBillAction(String OrderID, String UserID, String UserType, String OrderStatus, Callback callback) {
        OKHttpApi api = OKHttpApi.getInstance();
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("No", METHOD_CONFIRM);
        builder.add("OrderID", String.valueOf(OrderID));
        builder.add("UserID", String.valueOf(UserID));
        builder.add("UserType", String.valueOf(UserType));
        builder.add("OrderStatus", String.valueOf(OrderStatus));
        Request request = api.createHttpPost(RequestUrlConst.http, builder);
        api.enqueue(request, callback.setParser(new HeadParser()));
    }

    /**
     * 订单搜索
     *
     * @param UserID
     * @param UserType
     * @param VehicleNo
     * @param callback
     */
    public void searchBillAction(String UserID, String UserType, String VehicleNo, Callback callback) {
        OKHttpApi api = OKHttpApi.getInstance();
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("No", METHOD_SEARCH_ORDER);
        builder.add("VehicleNo", String.valueOf(VehicleNo));
        builder.add("UserID", String.valueOf(UserID));
        builder.add("UserType", String.valueOf(UserType));
        Request request = api.createHttpPost(RequestUrlConst.http, builder);
        api.enqueue(request, callback.setParser(new BillAllParser()));
    }


    /**
     * @param UserName
     * @param UserType
     * @param SignStatus
     * @param Page
     * @param Size
     * @param callback
     */
    public Call getAllbillAction(String UserName, String UserType, String EbillType, String SignStatus, String VehicleNo, String BeginDate, String EndDate, String Page, String Size, Callback callback) {
        OKHttpApi api = OKHttpApi.getInstance();
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("No", METHOD_ORDER_LIST);
        builder.add("UserName", String.valueOf(UserName));
        builder.add("UserType", String.valueOf(UserType));
        builder.add("EbillType", String.valueOf(EbillType));
        builder.add("SignStatus", String.valueOf(SignStatus));
        builder.add("VehicleNo", String.valueOf(VehicleNo));
        builder.add("BeginDate", String.valueOf(BeginDate));
        builder.add("EndDate", String.valueOf(EndDate));
        builder.add("Page", String.valueOf(Page));
        builder.add("Size", String.valueOf(Size));
        Request request = api.createHttpPost(RequestUrlConst.http, builder);
        return api.enqueue(request, callback.setParser(new BillAllParser()));
    }


    /**
     * 获取司机端的全部联单
     *
     * @param UserID
     * @param EbillType
     * @param UserType
     * @param callback
     * @return
     */
    public Call getAllDriverbillAction(String UserID, String EbillType, String UserType, String OrderID, Callback callback) {
        OKHttpApi api = OKHttpApi.getInstance();
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("No", METHOD_ORDER_LIST);
        builder.add("UserID", String.valueOf(UserID));
        builder.add("EbillType", String.valueOf(EbillType));
        builder.add("UserType", String.valueOf(UserType));
        builder.add("OrderID", String.valueOf(OrderID));
        Request request = api.createHttpPost(RequestUrlConst.http, builder);
        return api.enqueue(request, callback.setParser(new BillAllParser()));
    }


    /**
     * 受纳场类型
     *
     * @param callback
     */
    public void getAddressAction(Callback callback) {
        OKHttpApi api = OKHttpApi.getInstance();

        FormBody.Builder builder = new FormBody.Builder();
        builder.add("No", URL_FIELD_TYPE);
        Request request = api.createHttpPost(RequestUrlConst.http, builder);
        api.enqueue(request, callback.setParser(new GroupGsonParser<>(new GsonParse<>(FieldEntity.class))));
    }

    /**
     * 受纳场类型列表
     *
     * @param fieldType
     * @param callback
     */
    public void getAddressListAction(String fieldType, Callback callback) {
        OKHttpApi api = OKHttpApi.getInstance();
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("No", URL_FIELD_DETAIL);
        builder.add("FieldType", fieldType);
        Request request = api.createHttpPost(RequestUrlConst.http, builder);
        api.enqueue(request, callback.setParser(new GroupGsonParser<>(new GsonParse<>(FieldDetailEntity.class))));
    }

    /**
     * 联单取消、确认 1-确认，2-取消
     *
     * @param OrderID
     * @param UserID
     * @param SignType
     * @param UserType
     * @param callback
     */
    public void billOperateAction(String OrderID, String UserID, String SignType, String UserType, Callback callback) {
        OKHttpApi api = OKHttpApi.getInstance();
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("No", URL_BILL_ALL);
        builder.add("OrderID", String.valueOf(OrderID));
        builder.add("UserID", String.valueOf(UserID));
        builder.add("SignType", String.valueOf(SignType));
        builder.add("UserType", String.valueOf(UserType));
        Request request = api.createHttpPost(RequestUrlConst.http, builder);
        api.enqueue(request, callback.setParser(new HeadParser()));
    }


    /**
     * 司机确认联单
     *
     * @param OrderID
     * @param UserID
     * @param EbillType
     * @param UserType
     * @param callback
     */
    public void billDriverOperateAction(String OrderID, String UserID, String EbillType, String UserType, Callback callback) {
        OKHttpApi api = OKHttpApi.getInstance();
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("No", METHOD_CONFIRM);
        builder.add("OrderID", String.valueOf(OrderID));
        builder.add("UserID", String.valueOf(UserID));
        builder.add("EbillType", String.valueOf(EbillType));
        builder.add("UserType", String.valueOf(UserType));
        Request request = api.createHttpPost(RequestUrlConst.http, builder);
        api.enqueue(request, callback.setParser(new HeadParser()));
    }


    /**
     * 异常上报
     *
     * @param OrderID
     * @param UserID
     * @param Describe
     * @param callback
     */
    public void billExcepAction(String OrderID, String UserID, String Describe, Callback callback) {
        OKHttpApi api = OKHttpApi.getInstance();
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("No", METHOD_UPLOAD_ERROR);
        builder.add("OrderID", String.valueOf(OrderID));
        builder.add("UserID", String.valueOf(UserID));
        builder.add("Describe", String.valueOf(Describe));
        Request request = api.createHttpPost(RequestUrlConst.http, builder);
        api.enqueue(request, callback.setParser(new HeadParser()));
    }


    /**
     * 图片上传
     *
     * @param OrderID
     * @param ImageType
     * @param ImageData
     * @param UserName
     * @param callback
     */
    public void upLoadImage(String OrderID, String ImageType, String ImageData, String UserName, Callback callback) {
        OKHttpApi api = OKHttpApi.getInstance();
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("No", URL_LOAD_IMAGE);
        builder.add("OrderID", String.valueOf(OrderID));
        builder.add("ImageType", String.valueOf(ImageType));
        builder.add("ImageData", String.valueOf(ImageData));
        builder.add("UserName", String.valueOf(UserName));
        Request request = api.createHttpPost(RequestUrlConst.http, builder);
        api.enqueue(request, callback.setParser(new HeadParser()));
    }


    /**
     * 异常类型
     *
     * @param DictType
     * @param Keyword
     * @param callback
     */
    public void soilTypeAcion(String DictType, String Keyword, Callback callback) {
        OKHttpApi api = OKHttpApi.getInstance();
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("No", URL_TYPE_REPORT);
        builder.add("DictType", String.valueOf(DictType));
        builder.add("Keyword", String.valueOf(Keyword));
        Request request = api.createHttpPost(RequestUrlConst.http, builder);
        api.enqueue(request, callback.setParser(new SoilTypeParser()));
    }

    /**
     * 异常上报
     *
     * @param OrderID
     * @param UserName
     * @param IsReport
     * @param SoilExceptType
     * @param SoilExceptDetail
     * @param callback
     */

    public void soilReportAction(String OrderID, String UserName, String IsReport, String SoilExceptType, String SoilExceptDetail, Callback callback) {
        OKHttpApi api = OKHttpApi.getInstance();
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("No", URL_SOIL_REPORT);
        builder.add("OrderID", String.valueOf(OrderID));
        builder.add("UserName", String.valueOf(UserName));
        builder.add("IsReport", String.valueOf(IsReport));
        builder.add("SoilExceptType", String.valueOf(SoilExceptType));
        builder.add("SoilExceptDetail", String.valueOf(SoilExceptDetail));
        Request request = api.createHttpPost(RequestUrlConst.http, builder);
        api.enqueue(request, callback.setParser(new HeadParser()));
    }

    /**
     * 工地信息
     *
     * @param UserName
     * @param callback
     */
    public void buildInfoAction(String UserName, Callback callback) {
        OKHttpApi api = OKHttpApi.getInstance();
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("No", URL_BUILD_INFO);
        builder.add("UserName", String.valueOf(UserName));
        Request request = api.createHttpPost(RequestUrlConst.http, builder);
        api.enqueue(request, callback.setParser(new BuildParser()));
    }


    /**
     * 车辆列表
     *
     * @param Page
     * @param Size
     * @param callback
     */
    public void searchVehicleAction(String UserName, String VehicleNo, String Page, String Size, Callback callback) {
        OKHttpApi api = OKHttpApi.getInstance();
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("No", URL_VEHICLE_LIST);
        builder.add("UserName", String.valueOf(UserName));
        builder.add("VehicleNo", String.valueOf(VehicleNo));
        builder.add("Page", String.valueOf(Page));
        builder.add("Size", String.valueOf(Size));
        Request request = api.createHttpPost(RequestUrlConst.http, builder);
        api.enqueue(request, callback.setParser(new VehicleParser()));
    }

    /**
     * 补联单
     *
     * @param UserName
     * @param VehicleNo
     * @param WasteType
     * @param Loading
     * @param SiteId
     * @param SiteFence
     * @param EntryTime
     * @param LeaveTime
     * @param ReceivingID
     * @param callback
     */
    public void billRepair(String UserName, String VehicleNo, String WasteType, String Loading,
                           String SiteId, String SiteFence, String EntryTime, String LeaveTime,
                           String ReceivingID, String Remark,
                           Callback callback) {
        OKHttpApi api = OKHttpApi.getInstance();
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("No", URL_VEHICLE_REPAIR);
        builder.add("UserName", String.valueOf(UserName));
        builder.add("VehicleNo", String.valueOf(VehicleNo));
        builder.add("WasteType", String.valueOf(WasteType));
        builder.add("Loading", String.valueOf(Loading));
        builder.add("SiteId", String.valueOf(SiteId));
        builder.add("SiteFence", String.valueOf(SiteFence));
        builder.add("EntryTime", String.valueOf(EntryTime));
        builder.add("LeaveTime", String.valueOf(LeaveTime));
        builder.add("ReceivingID", String.valueOf(ReceivingID));
        builder.add("Remark", String.valueOf(Remark));
        Request request = api.createHttpPost(RequestUrlConst.http, builder);
        api.enqueue(request, callback.setParser(new HeadParser()));
    }

    /**
     * 申报消纳场
     *
     * @param UserName
     * @param FieldName
     * @param callback
     */
    public void getAccAction(String UserName, String FieldName, Callback callback) {
        OKHttpApi api = OKHttpApi.getInstance();
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("No", URL_APPLY_ACCOM);
        builder.add("UserName", String.valueOf(UserName));
        builder.add("FieldName", String.valueOf(FieldName));
        Request request = api.createHttpPost(RequestUrlConst.http, builder);
        api.enqueue(request, callback.setParser(new GroupGsonParser<>(new GsonParse<>(AccomEntity.class))));
    }
}
