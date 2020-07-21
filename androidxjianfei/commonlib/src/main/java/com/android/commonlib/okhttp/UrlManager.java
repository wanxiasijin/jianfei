package com.android.commonlib.okhttp;

public final class UrlManager {

    public static String getUploadImageUrl() {
        return NetConfig.getServerAddress() + ServerSuffix.UPLOAD_IMAGE;
    }

    public static String getExchangeStatisticsUrl() {
        return NetConfig.getServerAddress() + ServerSuffix.EXCHANGE_STATISTICS;
    }

    public static String getExchangeListUrl() {
        return NetConfig.getServerAddress() + ServerSuffix.EXCHANGE_LIST;
    }

    public static String getDictList() {
        return NetConfig.getServerAddress() + ServerSuffix.DICT_LIST;
    }

    public static String getEditPublishInfoUrl() {
        return NetConfig.getServerAddress() + ServerSuffix.EDIT_PUBLISH_INFO;
    }

    public static String getPublishInfoUrl() {
        return NetConfig.getServerAddress() + ServerSuffix.PUBLISH_INFO;
    }

    public static String getPublishedInfoUrl() {
        return NetConfig.getServerAddress() + ServerSuffix.PUBLISHED_INFO;
    }

    public static String deletePublishedInfoUrl() {
        return NetConfig.getServerAddress() + ServerSuffix.DELETE_PUBLISHES_INFO;
    }

    public static String getCollectInfoUrl() {
        return NetConfig.getServerAddress() + ServerSuffix.COLLECT_INFO;
    }

    public static String getCollectedInfoUrl() {
        return NetConfig.getServerAddress() + ServerSuffix.COLLECTED_INFO;
    }

    public static String getCancelCollectedInfoUrl() {
        return NetConfig.getServerAddress() + ServerSuffix.CANCEL_COLLECTED_INFO;
    }

    public static String getInfoDetailUrl() {
        return NetConfig.getServerAddress() + ServerSuffix.INFO_DETAIL;
    }

    public static String getSendMesCodeUrl() {
        return NetConfig.getServerAddress() + ServerSuffix.SEND_MES_CODE;
    }

    public static String getVerifyCodeUrl() {
        return NetConfig.getServerAddress() + ServerSuffix.VERIFY_MES_CODE;
    }

    public static String getRegisterAccountUrl() {
        return NetConfig.getServerAddress() + ServerSuffix.REGISTER_ACCOUNT;
    }

    public static String getResetPasswordUrl() {
        return NetConfig.getServerAddress() + ServerSuffix.RESET_PASSWORD;
    }

    public static String getLoginUrl() {
        return NetConfig.getServerAddress() + ServerSuffix.LOGIN;
    }

    public static String getBannerUrl() {
        return NetConfig.getServerAddress() + ServerSuffix.BANNER;
    }

    public static String getTrackUrl() {
        return NetConfig.getServerAddress() + ServerSuffix.TRACK_INFO;
    }

    public static String getSaveUrl() {
        return NetConfig.getServerAddress() + ServerSuffix.SAVE;
    }

    private static final class ServerSuffix {
        static final String UPLOAD_IMAGE = "/common/image/upload";
        static final String EXCHANGE_STATISTICS = "/earthworkexchange/statistics";
        static final String EXCHANGE_LIST = "/earthworkexchange/list";//供需信息
        static final String DICT_LIST = "/earthworkexchange/getDictList";
        static final String EDIT_PUBLISH_INFO = "/earthworkexchange/edit";
        static final String PUBLISH_INFO = "/earthworkexchange/publishInfo";
        static final String PUBLISHED_INFO = "/earthworkexchange/published";//获取发布信息
        static final String DELETE_PUBLISHES_INFO = "/earthworkexchange/remove";//删除发布信息
        static final String COLLECT_INFO = "/earthworkexchange/collection";//收藏信息
        static final String COLLECTED_INFO = "/earthworkexchange/myCollect";//已收藏信息列表
        static final String CANCEL_COLLECTED_INFO = "/earthworkexchange/cancelCollect";//取消收藏信息
        static final String INFO_DETAIL = "/earthworkexchange/detail";//信息详情
        static final String SEND_MES_CODE = "/common/sms/send";
        static final String VERIFY_MES_CODE = "/common/sms/verify";
        static final String REGISTER_ACCOUNT = "/user/register";//用户注册
        static final String RESET_PASSWORD = "/user/resetPassword";//忘记密码
        static final String LOGIN = "/user/login";//用户登录
        static final String TRACK_INFO = "/ebill/trackInfo";//车辆轨迹
        static final String BANNER = "ucs-api/service/banner.do";
        static final String SAVE = "insight-api/qa/save.do";
    }


}
