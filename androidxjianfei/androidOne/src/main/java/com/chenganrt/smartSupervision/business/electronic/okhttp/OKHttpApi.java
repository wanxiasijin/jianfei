package com.chenganrt.smartSupervision.business.electronic.okhttp;

import android.content.Context;
import android.os.Handler;

import com.chenganrt.smartSupervision.business.electronic.util.AppConstant;
import com.chenganrt.smartSupervision.business.electronic.util.AppTools;
import com.chenganrt.smartSupervision.business.electronic.parse.Parser;
import com.chenganrt.smartSupervision.business.electronic.util.FileUtil;
import com.chenganrt.smartSupervision.business.electronic.util.JsonUtil;
import com.chenganrt.smartSupervision.business.electronic.util.LogUtil;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLException;

import okhttp3.Call;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import rx.android.BuildConfig;

import static com.chenganrt.smartSupervision.business.electronic.util.AppConstant.HTTP_CONNECT_TIMEOUT;
import static com.chenganrt.smartSupervision.business.electronic.util.AppConstant.HTTP_READ_TIMEOUT;
import static com.chenganrt.smartSupervision.business.electronic.util.AppConstant.HTTP_WRITE_TIMEOUT;
import static com.chenganrt.smartSupervision.business.electronic.okhttp.OKHttpApi.Http.ERROR_CODE_IO;


/**
 *
 */

public class OKHttpApi {
    private OkHttpClient client;
    private Context context;

    private static class SingleTonHolder {
        private static final OKHttpApi INSTANCE = new OKHttpApi();
    }

    public synchronized static OKHttpApi getInstance() {
        return OKHttpApi.SingleTonHolder.INSTANCE;
    }

    public void init(Context context) {
        this.context = context;
        //if (BuildConfig.DEBUG) Stetho.initializeWithDefaults(context);
    }

    public OKHttpApi() {
    }

    private final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();

    public synchronized OkHttpClient getClient() {
        if (client != null) return client;
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(HTTP_CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(HTTP_READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(HTTP_WRITE_TIMEOUT, TimeUnit.SECONDS)
                .cookieJar(new CookieJar() {
                    @Override
                    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                        cookieStore.put(url.host(), cookies);
                    }

                    @Override
                    public List<Cookie> loadForRequest(HttpUrl url) {
                        List<Cookie> cookies = cookieStore.get(url.host());
                        return cookies != null ? cookies : new ArrayList<Cookie>();
                    }
                });
        //.cache(new Cache(FileUtil.getCacheDir(context), HTTP_CACHE_MAX_SIZE));
        //.addInterceptor(new TokenInterceptor(context));
        initInterceptor(builder, OkHttpLoggingInterceptor.Level.BODY);
        return client = builder.build();
    }

    public synchronized OkHttpClient getFileClient() {
        OkHttpClient.Builder builder = getClient().newBuilder().connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS).writeTimeout(60, TimeUnit.SECONDS);
        builder.interceptors().clear();
        builder.networkInterceptors().clear();
        initInterceptor(builder, OkHttpLoggingInterceptor.Level.HEADERS);
        return builder.build();
    }

    private void initInterceptor(OkHttpClient.Builder builder, OkHttpLoggingInterceptor.Level level) {
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(new OkHttpLoggingInterceptor().setLevel(level));
            //builder.addNetworkInterceptor(new StethoInterceptor());
            //builder.proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("192.168.3.213", 8888)));
        }
    }

    /**
     * @param url
     * @return
     */
    public Request createHttpGet(String url) {
        String getUrl = (url.startsWith("http://") || url.startsWith("https://")) ? url : RequestUrlConst.http + url;
        return new Request.Builder().get().url(getUrl).tag(creatTag()).build();
    }

    /**
     * @param url
     * @param body
     * @return
     */
    public Request createHttpPost(String url, RequestBody body) {
        String postUrl = (url.startsWith("http://") || url.startsWith("https://")) ? url : RequestUrlConst.http + url;
        return new Request.Builder().url(postUrl).post(body).tag(creatTag()).build();
    }

    /**
     * @param request
     * @return
     */
    /*public Request createHttpPost(FftRequest request) {
        return createHttpPost(request.requestUrl, createRequestBody(request.arg));
    }*/

    /**
     * @param url
     * @param map
     * @return
     */
    public Request createHttpPost(String url, TreeMap<String, String> map) {
        return createHttpPost(url, createRequestBody(map));
    }

    /**
     * @param url
     * @param json JSONObject json = new JSONObject();
     *             JsonUtil.put(json, "pageSize", pageSize);
     *             JsonUtil.put(json, "pageNo", pageNo);
     *             JsonUtil.put(json, "tplType", type.getValue());
     * @return
     */
    public Request createHttpPost(String url, JSONObject json) {
        return createHttpPost(url, createRequestBody(json));
    }


    /*public String getUrl(Map<String, String> map) {
        Iterator iter = map.entrySet().iterator();
        StringBuilder stringBuilder = new StringBuilder();
        while (iter.hasNext()) {
            Map.Entry mapEntry = (Map.Entry) iter.next();
            Object key = mapEntry.getKey();
            Object value = mapEntry.getValue();
            stringBuilder.append("")
        }
        return "";
    }*/

    /**
     * @param url
     * @param builder
     * @return
     */
    public Request createHttpPost(String url, FormBody.Builder builder) {
        return createHttpPost(url, createRequestBody(builder));
    }

    /**
     * @param url
     * @param builder
     * @return
     */
    public Request createUploadPost(String url, MultipartBody.Builder builder) {
        String postUrl = (url.startsWith("http://") || url.startsWith("https://")) ? url : RequestUrlConst.http + url;
        StringBuilder str = new StringBuilder();
        RequestBody body = createRequestBody(builder, str);
        return new Request.Builder().url(postUrl + str.toString()).post(body).tag(creatTag()).build();
    }

    public Request createDownloadPost(String url, String path, String name, FormBody.Builder builder) {
        String postUrl = (url.startsWith("http://") || url.startsWith("https://")) ? url : RequestUrlConst.http + url;
        OKTag tag = new OKTag.Builder().put("Path", path).put("Name", name).builder();
        if (builder == null) return new Request.Builder().url(postUrl).tag(tag).build();
        return new Request.Builder().url(postUrl).post(createRequestBody(builder)).tag(tag).build();
    }

    public Request createDownloadPost(String url, String path, String name) {
        return createDownloadPost(url, path, name, null);
    }

    /**
     * @param request
     * @return
     * @throws IOException
     */
    public Response _executeRequest(Request request) throws IOException {
        Response response = newCall(request).execute();
        return response;
    }

    /**
     * @param request
     * @return response body ，on ERROR with statusCode
     */
    public String executeRequest(Request request) {
        try {
            Response response = _executeRequest(request);
            String body = response.isSuccessful() ? response.body().string() :
                    ErrorResponse(response.code(), request, null);
            return body;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Call newCall(Request request) {
        return getClient().newCall(request);
    }

    public Call enqueueUploadProgress(Request request, UpLoadCallback callback) {
        ProgressRequestBody body = new ProgressRequestBody(request.body(), callback.getHandler());
        Call call = getFileClient().newCall(request.newBuilder().post(body).build());
        call.enqueue(callback);
        return call;
    }

    public Call enqueueDownloadProgress(Request request, final DownLoadCallback callback) {
        OkHttpClient mClient = getFileClient().newBuilder().addNetworkInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Response response = chain.proceed(chain.request());
                ProgressResponseBody body = new ProgressResponseBody(response.body(), callback.getHandler());
                return response.newBuilder().body(body).build();
            }
        }).build();
        Call call = mClient.newCall(request);
        call.enqueue(callback);
        return call;
    }

    public Call enqueue(Request request, Handler mHandler) {
        return enqueue(request, null, mHandler, 0, 0);
    }

    public Call enqueue(Request request, Parser parser, Handler mHandler) {
        return enqueue(request, parser, mHandler, 0, 0);
    }

    public Call enqueue(Request request, Parser parser, Handler mHandler, int arg1) {
        return enqueue(request, parser, mHandler, arg1, 0);
    }

    public Call enqueue(Request request, final Parser parser, final Handler mHandler, final int arg1, final int arg2) {
        Call call = newCall(request);
        call.enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (mHandler != null)
                    mHandler.obtainMessage(AppConstant.TaskState.SUCCESS, arg1, arg2, null).sendToTarget();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!isSuccessful(call, response, this)) return;
                Object obj = null;
                try {
                    String body = response.body().string();
                    obj = parser == null ? body : parser.parse(new JSONObject(body));
                } catch (Exception e) {
                    LogUtil.e("onResponse enqueue Parser Exception", e);
                }
                if (mHandler != null)
                    mHandler.obtainMessage(AppConstant.TaskState.SUCCESS, arg1, arg2, obj).sendToTarget();
            }
        });
        if (mHandler != null)
            mHandler.obtainMessage(AppConstant.TaskState.ISRUNING, arg1, arg2, request.tag()).sendToTarget();
        return call;
    }

    public Call enqueue(Request request, Callback callback) {
        Call call = newCall(request);
        call.enqueue(callback);
        callback.onRuning();
        return call;
    }

    public Call enqueueNotRun(Request request, Callback callback) {
        Call call = newCall(request);
        call.enqueue(callback);
        return call;
    }

    /**
     * @param map
     * @return
     */
    public RequestBody createRequestBody(TreeMap<String, String> map) {
        FormBody.Builder builder = new FormBody.Builder();
        if (map == null) map = new TreeMap();
        /*if (!map.containsKey("timestamp")) {
            map.put("timestamp", AppTools.getDateString());
        }
        if (!map.containsKey("token")) {
            String token = UserUtil.getInstance().getToken(context);
            if (!TextUtils.isEmpty(token)) map.put("token", token);
        }
        if (!map.containsKey("sign")) {
            map.put("sign", AppTools.getMD5String(AppTools.sortRequestSign(map)));
        }*/
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (entry.getValue() == null) continue;
            builder.add(entry.getKey(), entry.getValue());
        }
        return builder.build();
    }

    /**
     * @param builder
     * @return
     */
    public RequestBody createRequestBody(FormBody.Builder builder) {
        if (builder == null) builder = new FormBody.Builder();
        //builder.add("timestamp", AppTools.getDateString());
        //builder.add("token", UserUtil.getInstance().getToken(context));

        FormBody body = builder.build();
        TreeMap<String, String> map = new TreeMap();
        for (int i = 0; i < body.size(); i++) {
            map.put(body.name(i), body.value(i));
        }
        //builder.add("sign", AppTools.getMD5String(AppTools.sortRequestSign(map)));
        return builder.build();
    }

    /**
     * @param builder
     * @return
     */
    public RequestBody createRequestBody(MultipartBody.Builder builder, StringBuilder str) {
        if (builder == null) builder = new MultipartBody.Builder();
        //if (TextUtils.isEmpty(UserUtil.getInstance().getToken(context)))
        //    return builder.build();
        TreeMap<String, String> map = new TreeMap();
        //map.put("timestamp", AppTools.getDateString());
        //map.put("token", UserUtil.getInstance().getToken(context));
        //map.put("userId", UserUtil.getInstance().getUserId(context));
        //map.put("sign", AppTools.getMD5String(AppTools.sortRequestSign(map)));

        //builder.addFormDataPart("timestamp", map.get("timestamp"));
        //builder.addFormDataPart("token", map.get("token"));
        //builder.addFormDataPart("userId",map.get("userId"));
        //builder.addFormDataPart("sign", map.get("sign"));

        str.append("?").append(AppTools.sortRequestSign(map));

        return builder.build();
    }

    /**
     * @param json
     * @return
     */
    public RequestBody createRequestBody(JSONObject json) {
        FormBody.Builder builder = new FormBody.Builder();
        Iterator it = json.keys();
        while (it.hasNext()) {
            String key = String.valueOf(it.next());
            Object value = JsonUtil.get(json, key);
            if (value != null) builder.add(key, value.toString());
        }
        return createRequestBody(builder);
    }

    public boolean isSuccessful(Call call, Response response, okhttp3.Callback callback) {
        if (response.isSuccessful() && response.body() != null) return true;
        callback.onFailure(call, new IOException("Unexpected code " + response));
        return false;
    }

    private String ErrorResponse(int statusCode, Request request, Exception e) {
        JSONObject json = new JSONObject();
        if (e == null) {
        } else if (e instanceof SSLException) {
            statusCode = Http.ERROR_CODE_SSL;
        } else if (e instanceof IOException) {
            statusCode = ERROR_CODE_IO;
        } else if (e instanceof IllegalStateException) {
            statusCode = Http.ERROR_CODE_ILLEGAL;
        } else {
            statusCode = Http.ERROR_UNKNOWN;
        }
        JsonUtil.put(json, "statusCode", statusCode);
        JsonUtil.put(json, "Request", request.toString());
        return json.toString();
    }

    public boolean cancel(Object tag) {
        if (tag == null) return false;
        boolean isCancel = false;
        for (Call call : getClient().dispatcher().queuedCalls()) {
            if (call.request().tag().equals(tag)) {
                isCancel = true;
                call.cancel();
            }
        }
        for (Call call : getClient().dispatcher().runningCalls()) {
            if (call.request().tag().equals(tag)) {
                isCancel = true;
                call.cancel();
            }
        }
        return isCancel;
    }

    private OKTag creatTag() {
        return new OKTag();
    }


    public File getFile(OKTag tag) throws IOException {
        String path = tag.get("Path", FileUtil.getDownloadDir(context)).toString();
        String name = tag.get("Name", tag.hashCode()).toString();
        File pathDir = new File(path);
        if (!pathDir.exists() && !pathDir.mkdirs())
            throw new IOException("mkdirs fail" + pathDir.getAbsolutePath());
        File file = new File(pathDir, name);
        if (file.exists() && !file.delete()) {
        //    if (BuildConfig.DEBUG) LogUtil.d("delete File fail" + file.getAbsolutePath());
        }
        return file;
    }

    public void setProxy(Proxy proxy) {
        client = getClient().newBuilder().proxy(proxy).build();
    }

    public Proxy getProxy() {
        return getClient().proxy();
    }

    /*
     * HTTP常量
     */
    public static final class Http {
        public static final int ERROR_UNKNOWN = -6;// 未定义
        public static final int ERROR_CODE_ILLEGAL = -5;// HTTP错误参数
        public static final int ERROR_CODE_SSL = -4;// SSL通道建立失败
        public static final int ERROR_CODE_IO = -3;// IO异常
        public static final int ERROR_CODE_ENCODING = -2;// URL参数编码失败
        public static final int ERROR_CODE_JSON = -1;// Json解析异常
    }
}
