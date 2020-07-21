package com.android.commonlib.okhttp;

import android.content.Context;
import android.text.TextUtils;

import com.android.commonlib.okhttp.bean.Banner;
import com.android.commonlib.okhttp.bean.Response;
import com.android.commonlib.okhttp.bean.TrackResponse;
import com.android.commonlib.okhttp.bean.TypeResponse;
import com.android.commonlib.utils.GsonUtil;
import com.android.commonlib.utils.LogUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import timber.log.Timber;

public class NetWorkDataManager {

    private static final String TAG = NetWorkDataManager.class.getSimpleName();

    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/*");
    private static final MediaType JSON_TYPE = MediaType.parse("application/json; charset=utf-8");

    public NetWorkDataManager() {
    }

    public static Response getImageUrl(String imageUrl) throws IOException {
        String result = NetUtil.post(UrlManager.getUploadImageUrl(), imageUrl);
        Timber.d("getImageUrl result : " + result);
        if (TextUtils.isEmpty(result)) {
            Timber.d("getImageUrl result is null");
            return null;
        }
        return GsonUtil.JsonToObject(result, Response.class);
    }


    public static Response getExchangeStatisticsTotal() throws IOException {
        String result = NetUtil.get(UrlManager.getExchangeStatisticsUrl(), null);
        Timber.d("getExchangeStatistics result : " + result);
        if (TextUtils.isEmpty(result)) {
            Timber.d("getExchangeStatistics result is null");
            return null;
        }
//        Type type = new TypeToken<ExchangeStatistics>() {}.getType();//转换为列表
        return GsonUtil.JsonToObject(result, Response.class);
    }

    public static Response getExchangeDataList(Map<String, String> map) throws IOException {
        Timber.d("getExchangeDataList:" + GsonUtil.toJson(map));
        RequestBody requestBody = RequestBody.create(JSON_TYPE, GsonUtil.toJson(map));
        String result = NetUtil.post(UrlManager.getExchangeListUrl(), requestBody);
        Timber.i("getExchangeDataList result : " + result);
        if (result == null || TextUtils.isEmpty(result)) {
            Timber.d("getExchangeDataList result is null");
            return null;
        }
        return GsonUtil.JsonToObject(result, Response.class);
    }

    public static TypeResponse getTypes() throws IOException {
        String result = NetUtil.get(UrlManager.getDictList(), null);
        Timber.i("publishInfo result : " + result);
        if (result == null || TextUtils.isEmpty(result)) {
            Timber.d("publishInfo result is null");
            return null;
        }
        return GsonUtil.JsonToObject(result, TypeResponse.class);
    }

    public static Response publishInfo(Map<String, String> map) throws IOException {
        Timber.d("publishInfo1:" + GsonUtil.toJson(map));
        RequestBody requestBody = RequestBody.create(JSON_TYPE, GsonUtil.toJson(map));
        String result = NetUtil.post(UrlManager.getPublishInfoUrl(), requestBody);
        Timber.i("publishInfo result : " + result);
        if (result == null || TextUtils.isEmpty(result)) {
            Timber.d("publishInfo result is null");
            return null;
        }
        return GsonUtil.JsonToObject(result, Response.class);
    }

    public static Response editPublishInfo(Map<String, String> map) throws IOException {
        Timber.d("editPublishInfo:" + GsonUtil.toJson(map));
        RequestBody requestBody = RequestBody.create(JSON_TYPE, GsonUtil.toJson(map));
        String result = NetUtil.post(UrlManager.getEditPublishInfoUrl(), requestBody);
        Timber.i("editPublishInfo result : " + result);
        if (result == null || TextUtils.isEmpty(result)) {
            Timber.d("editPublishInfo result is null");
            return null;
        }
        return GsonUtil.JsonToObject(result, Response.class);
    }

    public static Response getPublishedInfo(Map<String, String> map) throws IOException {
        Timber.d("getPublishedInfo:" + GsonUtil.toJson(map));
        RequestBody requestBody = RequestBody.create(JSON_TYPE, GsonUtil.toJson(map));
        String result = NetUtil.post(UrlManager.getPublishedInfoUrl(), requestBody);
        Timber.i("getPublishedInfo result : " + result);
        if (result == null || TextUtils.isEmpty(result)) {
            Timber.d("getPublishedInfo result is null");
            return null;
        }
        return GsonUtil.JsonToObject(result, Response.class);
    }

    public static Response deletePublishedInfo(Map<String, String> map) throws IOException {
        Timber.d("deletePublishedInfo:" + GsonUtil.toJson(map));
        RequestBody requestBody = RequestBody.create(JSON_TYPE, GsonUtil.toJson(map));
        String result = NetUtil.post(UrlManager.deletePublishedInfoUrl(), requestBody);
        Timber.i("deletePublishedInfo result : " + result);
        if (result == null || TextUtils.isEmpty(result)) {
            Timber.d("deletePublishedInfo result is null");
            return null;
        }
        return GsonUtil.JsonToObject(result, Response.class);
    }

    public static Response collectInfo(Map<String, String> map) throws IOException {
        Timber.d("collectInfo:" + GsonUtil.toJson(map));
        RequestBody requestBody = RequestBody.create(JSON_TYPE, GsonUtil.toJson(map));
        String result = NetUtil.post(UrlManager.getCollectInfoUrl(), requestBody);
        Timber.i("collectInfo result : " + result);
        if (result == null || TextUtils.isEmpty(result)) {
            Timber.d("collectInfo result is null");
            return null;
        }
        return GsonUtil.JsonToObject(result, Response.class);
    }

    public static Response getCollectedInfo(Map<String, String> map) throws IOException {
        Timber.d("getCollectedInfo:" + GsonUtil.toJson(map));
        RequestBody requestBody = RequestBody.create(JSON_TYPE, GsonUtil.toJson(map));
        String result = NetUtil.post(UrlManager.getCollectedInfoUrl(), requestBody);
        Timber.i("getCollectedInfo result : " + result);
        if (result == null || TextUtils.isEmpty(result)) {
            Timber.d("getCollectedInfo result is null");
            return null;
        }
        return GsonUtil.JsonToObject(result, Response.class);
    }

    public static Response cancelCollectedInfo(Map<String, String> map) throws IOException {
        Timber.d("getCollectedInfo:" + GsonUtil.toJson(map));
        RequestBody requestBody = RequestBody.create(JSON_TYPE, GsonUtil.toJson(map));
        String result = NetUtil.post(UrlManager.getCancelCollectedInfoUrl(), requestBody);
        Timber.i("getCollectedInfo result : " + result);
        if (result == null || TextUtils.isEmpty(result)) {
            Timber.d("getCollectedInfo result is null");
            return null;
        }
        return GsonUtil.JsonToObject(result, Response.class);
    }

    public static Response getInfoDetail(Map<String, String> map) throws IOException {
        Timber.d("getInfoDetail:" + GsonUtil.toJson(map));
        RequestBody requestBody = RequestBody.create(JSON_TYPE, GsonUtil.toJson(map));
        String result = NetUtil.post(UrlManager.getInfoDetailUrl(), requestBody);
        Timber.i("getInfoDetail result : " + result);
        if (result == null || TextUtils.isEmpty(result)) {
            Timber.d("getInfoDetail result is null");
            return null;
        }
        return GsonUtil.JsonToObject(result, Response.class);
    }

    public static Response getMesCode(Map<String, String> map) throws IOException {
        Timber.d("getMesCode:" + GsonUtil.toJson(map));
        RequestBody requestBody = RequestBody.create(JSON_TYPE, GsonUtil.toJson(map));
        String result = NetUtil.post(UrlManager.getSendMesCodeUrl(), requestBody);
        Timber.i("getSendMesCodeUrl result : " + result);
        if (result == null || TextUtils.isEmpty(result)) {
            Timber.d("getSendMesCodeUrl result is null");
            return null;
        }
        return GsonUtil.JsonToObject(result, Response.class);
    }

    public static Response verifyCode(Map<String, String> map) throws IOException {
        RequestBody requestBody = RequestBody.create(JSON_TYPE, GsonUtil.toJson(map));
        String result = NetUtil.post(UrlManager.getVerifyCodeUrl(), requestBody);
        Timber.i("verifyCode result : " + result);
        if (result == null || TextUtils.isEmpty(result)) {
            Timber.d("verifyCode result is null");
            return null;
        }
        return GsonUtil.JsonToObject(result, Response.class);
    }

    public static Response registerAccount(Map<String, String> map) throws IOException {
        RequestBody requestBody = RequestBody.create(JSON_TYPE, GsonUtil.toJson(map));
        String result = NetUtil.post(UrlManager.getRegisterAccountUrl(), requestBody);
        Timber.i("registerAccount result : " + result);
        if (result == null || TextUtils.isEmpty(result)) {
            Timber.d("registerAccount result is null");
            return null;
        }
        return GsonUtil.JsonToObject(result, Response.class);
    }

    public static Response resetPassword(Map<String, String> map) throws IOException {
        RequestBody requestBody = RequestBody.create(JSON_TYPE, GsonUtil.toJson(map));
        String result = NetUtil.post(UrlManager.getResetPasswordUrl(), requestBody);
        Timber.i("resetPassword result : " + result);
        if (result == null || TextUtils.isEmpty(result)) {
            Timber.d("resetPassword result is null");
            return null;
        }
        return GsonUtil.JsonToObject(result, Response.class);
    }

    public static Response login(Map<String, String> map) throws IOException {
        RequestBody requestBody = RequestBody.create(JSON_TYPE, GsonUtil.toJson(map));
        String result = NetUtil.post(UrlManager.getLoginUrl(), requestBody);
        Timber.i("login result : " + result);
        if (result == null || TextUtils.isEmpty(result)) {
            Timber.d("login result is null");
            return null;
        }
        return GsonUtil.JsonToObject(result, Response.class);
    }

    public static TrackResponse getTrackInfo(Map<String, String> map) throws IOException {
        String result = NetUtil.get(UrlManager.getTrackUrl(), map);
        Timber.i("getTrackInfo result : " + result);
        if (result == null || TextUtils.isEmpty(result)) {
            Timber.d("getTrackInfo result is null");
            return null;
        }
        return GsonUtil.JsonToObject(result, TrackResponse.class);
    }

    /**
     * 获取首页banner的相关网络数据
     *
     * @param netType
     * @return
     * @throws IOException
     */
    public static ArrayList<Banner> getBannerData(Context context, String netType) throws IOException {
        LogUtil.trace();
        Gson mGson = new Gson();
        HashMap<String, String> params = new HashMap<>();
        params.put("model", "");
        params.put("imei", "");
        params.put("nt", netType);
        params.put("module", "3");
        String result = NetUtil.get(UrlManager.getBannerUrl(), params);
        Timber.d("getBannerData result : " + result);
        if (TextUtils.isEmpty(result)) {
            Timber.d("getBannerData result is null");
            return null;
        }
        Type type = new TypeToken<ArrayList<Banner>>() {
        }.getType();
        ArrayList<Banner> arrayList = mGson.fromJson(result, type);
        LogUtil.trace(arrayList.size() + "");
        return arrayList;
    }

    public static ArrayList<Banner> getBannerDataAssets(Context context) throws IOException {
        Gson mGson = new Gson();
        String fileName = "Banner.txt";
        String bannerDataAssets = GsonUtil.getJson(context, fileName);
        Timber.i("getBannerDataAssets bannerDataAssets : " + bannerDataAssets);
        Type type = new TypeToken<ArrayList<Banner>>() {
        }.getType();
        return mGson.fromJson(bannerDataAssets, type);
    }

    public static long saveFeedback(Context context, List<File> fileList, String message, String contact, String type, String netType, long moduleId) throws IOException {
        Gson mGson = new Gson();
        MultipartBody.Builder bodyBuilder = new MultipartBody.Builder();
        bodyBuilder.setType(MultipartBody.FORM);
        bodyBuilder.addFormDataPart("model", "");
        bodyBuilder.addFormDataPart("message", message);
        bodyBuilder.addFormDataPart("contact", contact);
        bodyBuilder.addFormDataPart("type", type);
        bodyBuilder.addFormDataPart("net", netType);
        bodyBuilder.addFormDataPart("typeId", String.valueOf(moduleId));

        if (fileList != null) {
            for (File file : fileList) {
                bodyBuilder.addPart(Headers.of("Content-Disposition", "form-data; name=\"file\"; filename=\"" + file.getName() + "\""), RequestBody.create(MEDIA_TYPE_PNG, file));
            }
        }

        String result = NetUtil.post(UrlManager.getSaveUrl(), bodyBuilder.build());
        Timber.i("saveFeedback result : " + result);

        if (TextUtils.isEmpty(result)) {
            Timber.d("saveFeedback result is null");
            return -1;
        }
        Timber.i("saveFeedback result : " + result);

        Banner response = mGson.fromJson(result, Banner.class);
        if (response == null) {
            return -1;
        }

        long id = response.getId();

        return id;
    }

}
