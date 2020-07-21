package com.chenganrt.smartSupervision.business.electronic.okhttp;

import com.chenganrt.smartSupervision.BuildConfig;

public class RequestUrlConst {

    public static String http = BuildConfig.DEBUG ?
            "http://139.9.32.144:8100/api/EbillApp/PostApi" : "https://ld.jzfqw.szsti.org:8100/api/EbillApp/PostApi";

    public static final String METHOD_LOGIN_SERVLET = "UserLogin";//登录
    public static final String METHOD_ORDER_LIST = "GetList";//获取全部运单列表
    public static final String METHOD_GET_DETAILS = "GetDetails";//获取运单详情
    public static final String METHOD_CONFIRM = "Confirm";//确认运单信息
    public static final String METHOD_EDIT_ORDER_DETAILS = "EditOrderDetails";//修改运单详情
    public static final String METHOD_SAVE_ORDER_DETAILS = "SaveOrderDetails";//提交修改运单详情
    public static final String METHOD_UPLOAD_ERROR = "UploadError";//6、异常上报
    public static final String METHOD_BIND_VEHICLE = "BindVehicle";//6、绑定车牌号
    public static final String METHOD_SEARCH_ORDER = "SearchOrder";//6、运单搜索
    public static final String METHOD_NOSPOIL = "NoSpoil";//非弃土上报
    public static final String METHOD_ABOUTUS = "AboutUs";//关于我们
    public static final String METHOD_RESETPWD = "ResetPwd";//重置密码
    public static final String METHOD_GETCODE = "GetCode";//获取验证码
    public static final String METHOD_FOUNDPWD = "SendCode";//忘记密码
    public static final String METHOD_LOGOUT = "LogOut";//退出登录
    public static final String METHOD_UN_BIND_VEHICLE = "UnBindVehicle";// 解除绑定
    public static final String METHOD_GETMESSAGE = "GetMessage";// 消息列表
    public static final String URL_UPDATE_VERSION = "GetUpPackageByVersion";// 版本更新
    public static final String URL_STATISTIC = "GetSignStatic";// 统计分析
    public static final String URL_BILLSIGN_QUARY = "SearchSignStatic";// 联单签认查询

    public static final String URL_FIELD_TYPE = "GetFieldType";//受纳场类型
    public static final String URL_FIELD_DETAIL = "GetFieldList";//受纳场列表

    public static final String URL_MESSAGE_COUNT = "GetMessageStatistic";//消息统计
    public static final String URL_BILL_ALL = "SignEbill";//联单确认、取消
    public static final String URL_PAW_FOUND = "RetrievePwd";//找回密码
    public static final String URL_LOAD_IMAGE = "UploadImage";//图片上传
    public static final String URL_TYPE_REPORT = "GetAppDict";//土质上报类型选择
    public static final String URL_SOIL_REPORT = "DisposalConfirm";//土质上报
    public static final String URL_BUILD_INFO = "GetSiteInfo";//工地信息
    public static final String URL_VEHICLE_LIST = "GetApplyVehicles";//车辆列表
    public static final String URL_VEHICLE_REPAIR = "SiteSupplyEbill";//工地补签联单
    public static final String URL_APPLY_ACCOM = "GetApplyFields";//申报消纳场

}
