/*
    ShengDao Android Client, BaseApplication
    Copyright (c) 2014 ShengDao Tech Company Limited
 */

package com.chenganrt.smartSupervision.business.app;

import android.content.Context;
import android.text.TextUtils;

import com.android.commonlib.utils.LibContext;
import com.apkfuns.logutils.LogUtils;
import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.chenganrt.smartSupervision.BuildConfig;
import com.chenganrt.smartSupervision.utils.CommonUtils;
import com.chenganrt.smartSupervision.utils.NLog;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;
import cn.jpush.android.api.JPushInterface;
import timber.log.Timber;

/**
 * [系统Application类，设置全局变量以及初始化组件]
 *
 * @author devin.hu
 * @version 1.0
 * @date 2013-9-17
 **/
public class BaseApplication extends MultiDexApplication {
    private final String tag = BaseApplication.class.getSimpleName();
    private static BaseApplication mBaseApplication;


    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    /**
     * 初始化
     */
    private void init() {
        //初始化debug模式
        String flag = CommonUtils.getProperty(getApplicationContext(), "debug");
        if (!TextUtils.isEmpty(flag)) {
            Boolean isDebug = Boolean.parseBoolean(flag);
            NLog.setDebug(isDebug);
            NLog.e(tag, "isDebug: " + isDebug);
        }
        JPushInterface.setDebugMode(true); //正式环境时去掉此行代码
        JPushInterface.init(this);
        mBaseApplication = this;
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
        LogUtils.getLogConfig()
                .configShowBorders(false);
        LibContext.getInstance().setContext(this);

        // 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
        // 默认本地个性化地图初始化方法
        SDKInitializer.initialize(this);

        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.BD09LL);
    }

    public static BaseApplication getApp() {
        return mBaseApplication;
    }
}
