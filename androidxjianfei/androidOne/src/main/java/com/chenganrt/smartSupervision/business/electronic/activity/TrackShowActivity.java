/*
 * Copyright (C) 2018 Baidu, Inc. All Rights Reserved.
 */
package com.chenganrt.smartSupervision.business.electronic.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.ConditionVariable;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.android.commonlib.okhttp.NetWorkDataManager;
import com.android.commonlib.okhttp.bean.TrackData;
import com.android.commonlib.okhttp.bean.TrackInfo;
import com.android.commonlib.okhttp.bean.TrackResponse;
import com.android.commonlib.rxjava.RxJava;
import com.android.commonlib.utils.ToastUtils;
import com.android.commonlib.utils.Utils;
import com.android.commonlib.utils.WindowUtil;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Polygon;
import com.baidu.mapapi.map.PolygonOptions;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.model.LatLng;
import com.chenganrt.smartSupervision.R;
import com.chenganrt.smartSupervision.business.app.BaseApplication;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import timber.log.Timber;

/**
 * 轨迹运行demo展示
 */
public class TrackShowActivity extends AppCompatActivity {

    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private Polyline mPolyline;
    private Polygon mStartPolyline;
    private Polygon mEndPolyline;
    private Marker mMoveMarker;
    private Handler mHandler;
    private Button button;
    private View view;
    private TextView textView1, textView2, textView3, date, startTime, endTime;
    private SeekBar mSeekBar;
    private ImageButton mBtnStart, mBtnStop;
    private String ebill_no;
    // 通过设置间隔时间和距离可以控制速度和图标移动的距离
    private static final int TIME_INTERVAL = 80;
    private long mTime;
    private static final double DISTANCE = 0.00002;
    private BitmapDescriptor mGreenTexture = BitmapDescriptorFactory.fromAsset("icon_road_green_arrow.png");
    private BitmapDescriptor mBitmapCar = BitmapDescriptorFactory.fromResource(R.drawable.dzld_ckgj_car);
    private List<TrackData> mTrackList = new ArrayList<>();
    private List<TrackData> mStartTrackList = new ArrayList<>();
    private List<TrackData> mEndTrackList = new ArrayList<>();
    private int mStartProcess = 0;
    private boolean isPause;
    private ConditionVariable mLock = new ConditionVariable(true);
    private boolean isInterrupted = false;
    private boolean isReStart;

    public static Intent startAct(Activity activity, String orderId) {
        Intent intent = new Intent(activity, TrackShowActivity.class);
        intent.putExtra("ebill_no", orderId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.avtivity_tract_show);
        getSupportActionBar().hide();
        WindowUtil.setStatusTextColor(true, getWindow(), getResources().getColor(com.android.commonlib.R.color.black));

        TextView title = findViewById(R.id.toolbar_title);
        title.setText("车辆轨迹详情");
        ImageView iv_back = findViewById(R.id.btn_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        date = findViewById(R.id.date);
        startTime = findViewById(R.id.start_time);
        endTime = findViewById(R.id.end_time);
        mSeekBar = findViewById(R.id.map_seekbar);
        mBtnStart = findViewById(R.id.btn_start);
        mBtnStop = findViewById(R.id.btn_stop);
        mBtnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isReStart) {
                    isReStart = false;
                    myThread.start();
                } else {
                    if (isPause) {
                        mLock.open();
                    }
                    isPause = false;
                }
            }
        });
        mBtnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isPause = true;
            }
        });

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Timber.d("onProgressChanged progress:" + progress);
                if (progress == 100) {
                    isReStart = true;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mStartProcess = (int) (seekBar.getProgress() * 0.01 * mTrackList.size());
                if (isReStart) {
                    isReStart = false;
                    myThread.start();
                } else {
                    if (isPause) {
                        mLock.open();
                    }
                    isInterrupted = true;
                }
            }
        });
        mMapView = (MapView) findViewById(R.id.bmapView);
        mMapView.onCreate(this, savedInstanceState);
        mBaiduMap = mMapView.getMap();
        initData();
    }

    Thread myThread = new Thread() {
        public void run() {
            try {
                for (int i = mStartProcess; i < mTrackList.size() - 1; i++) {
                    final LatLng startPoint = new LatLng(Double.valueOf(mTrackList.get(i).getLat()), Double.valueOf(mTrackList.get(i).getLng()));
                    final LatLng endPoint = new LatLng(Double.valueOf(mTrackList.get(i + 1).getLat()), Double.valueOf(mTrackList.get(i + 1).getLng()));
                    //车辆位置
                    mMoveMarker.setPosition(startPoint);
                    //车辆角度
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (mMapView == null) {
                                return;
                            }
                            mMoveMarker.setRotate((float) getAngle(startPoint, endPoint));
                        }
                    });
                    double slope = getSlope(startPoint, endPoint);
                    // 是不是正向的标示
                    boolean isYReverse = (startPoint.latitude > endPoint.latitude);
                    boolean isXReverse = (startPoint.longitude > endPoint.longitude);
                    double intercept = getInterception(slope, startPoint);
                    double xMoveDistance = isXReverse ? getXMoveDistance(slope) : -1 * getXMoveDistance(slope);
                    double yMoveDistance = isYReverse ? getYMoveDistance(slope) : -1 * getYMoveDistance(slope);

                    for (double j = startPoint.latitude, k = startPoint.longitude;
                         !((j > endPoint.latitude) ^ isYReverse) && !((k > endPoint.longitude) ^ isXReverse); ) {
                        if (isInterrupted) {
                            isInterrupted = false;
                            mMoveMarker.hideInfoWindow();
                            throw new InterruptedException();
                        }
                        if (isPause) {
                            mLock.block();
                            mLock.close();
                        }
                        LatLng latLng = null;

                        if (slope == Double.MAX_VALUE) {
                            latLng = new LatLng(j, k);
                            j = j - yMoveDistance;
                        } else if (slope == 0.0) {
                            latLng = new LatLng(j, k - xMoveDistance);
                            k = k - xMoveDistance;
                        } else {
                            latLng = new LatLng(j, (j - intercept) / slope);
                            j = j - yMoveDistance;
                        }

                        final LatLng finalLatLng = latLng;
                        if (finalLatLng.latitude == 0 && finalLatLng.longitude == 0) {
                            continue;
                        }
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (mMapView == null) {
                                    return;
                                }
                                mMoveMarker.setPosition(finalLatLng);
                                // 设置 Marker 覆盖物的位置坐标,并同步更新与Marker关联的InfoWindow的位置坐标.
                                mMoveMarker.setPositionWithInfoWindow(finalLatLng);
                            }
                        });
                        Thread.sleep(TIME_INTERVAL);
                    }
                    mSeekBar.setProgress((i + 1) * 100 / (mTrackList.size() - 1));
                    textView1.setText(mTrackList.get(i).getVelocity() + "Km/h");
                    textView2.setText(mTrackList.get(i).getGps_time());
                    textView3.setText("里程：" + mTrackList.get(i).getEdr_odometer() + "Km");
                    mMoveMarker.updateInfoWindowView(textView1);
                    mMoveMarker.updateInfoWindowView(textView2);
                    mMoveMarker.updateInfoWindowView(textView3);
                    mMoveMarker.hideInfoWindow();
                    mMoveMarker.showInfoWindow(new InfoWindow(BitmapDescriptorFactory.fromView(view), new LatLng(Double.valueOf(mTrackList.get(mTrackList.size() - 1).getLat()), Double.valueOf(mTrackList.get(mTrackList.size() - 1).getLng())), -47,
                            null));
                }
            } catch (InterruptedException e) {
                mMoveMarker.hideInfoWindow();
                Timber.d("InterruptedException 中断之后:" + mStartProcess);
                e.printStackTrace();
                myThread.start();
                Timber.d("InterruptedException 中断 重启线程：" + mStartProcess);
            }
        }

    };

    private void initData() {
        ebill_no = getIntent().getStringExtra("ebill_no");
        getTrackInfo();
    }

    private void showResult(List<TrackInfo> trackInfoList) {
        for (TrackInfo trackInfo : trackInfoList) {
            switch (trackInfo.getType()) {
                case 0:
                    List<TrackData> list1 = trackInfo.getList();
                    if (list1 != null && list1.size() != 0) {
                        Timber.d("list:" + list1.size());
                        mTrackList.clear();
                        mTrackList.addAll(list1);
                        date.setText(list1.get(0).getGps_time().substring(0, 10));
                        startTime.setText(list1.get(0).getGps_time().substring(11));
                        endTime.setText(list1.get(list1.size() - 1).getGps_time().substring(11));
                    } else {
                        ToastUtils.showToast(BaseApplication.getApp(), "无轨迹信息");
                    }
                    break;
                case 2:
                    List<TrackData> list2 = trackInfo.getList();
                    if (list2 != null && list2.size() != 0) {
                        Timber.d("lis2:" + list2.size());
                        mStartTrackList.clear();
                        mStartTrackList.addAll(list2);
                    } else {
                        ToastUtils.showToast(BaseApplication.getApp(), "无工地围栏位置信息");
                    }
                    break;
                case 4:
                    List<TrackData> list3 = trackInfo.getList();
                    if (list3 != null && list3.size() != 0) {
                        Timber.d("list3:" + list3.size());
                        mEndTrackList.clear();
                        mEndTrackList.addAll(list3);
                    } else {
                        ToastUtils.showToast(BaseApplication.getApp(), "无收纳场围栏位置信息");
                    }
                    break;
            }
        }

        Timber.d("mTrackList:" + mTrackList.size());
        Timber.d("mStartTrackList:" + mStartTrackList.size());
        Timber.d("mEndTrackList:" + mEndTrackList.size());
        MapStatus.Builder builder = new MapStatus.Builder();
        Timber.d("getLng:" + Double.valueOf(mTrackList.get(0).getLng()));
        Timber.d("getLat:" + Double.valueOf(mTrackList.get(0).getLat()));
        builder.target(new LatLng(Double.valueOf(mTrackList.get(0).getLat()), Double.valueOf(mTrackList.get(0).getLng())));
        builder.zoom(19.0f);
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
        mHandler = new Handler(Looper.getMainLooper());
        drawPolyLine();
        moveLooper();
        mMapView.showZoomControls(false);
    }

    private void drawPolyLine() {
        //工地纹理
        List<LatLng> polylines1 = new ArrayList<>();
        double startLatTotal = 0;
        double startLngTotal = 0;
        for (int index = 0; index < mStartTrackList.size(); index++) {
            startLatTotal += Double.valueOf(mStartTrackList.get(index).getLat());
            startLngTotal += Double.valueOf(mStartTrackList.get(index).getLng());
            polylines1.add(new LatLng(Double.valueOf(mStartTrackList.get(index).getLat()), Double.valueOf(mStartTrackList.get(index).getLng())));
        }
        if (polylines1.size() > 0) {
            //起点图标
            MarkerOptions markerOptionsA = new MarkerOptions()
                    .position(new LatLng(startLatTotal / polylines1.size(), startLngTotal / polylines1.size()))
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.dzld_ckgj_begin))// 设置 Marker 覆盖物的图标
                    .zIndex(9)// 设置 marker 覆盖物的 zIndex
                    .draggable(true); // 设置 marker 是否允许拖拽，默认不可拖拽
            mBaiduMap.addOverlay(markerOptionsA);


            OverlayOptions ooPolygon = new PolygonOptions()
                    .points(polylines1)// 设置多边形坐标点列表
                    .stroke(new Stroke(10,  Color.argb(255, 255, 0, 0)))// 设置多边形边框信息
                    .fillColor(Color.argb(100, 255, 0, 0));// 设置多边形填充颜色
            // 添加覆盖物
            mStartPolyline = (Polygon) mBaiduMap.addOverlay(ooPolygon);
        }

        //收纳场纹理
        List<LatLng> polylines2 = new ArrayList<>();
        double endLatTotal = 0;
        double endLngTotal = 0;
        for (int index = 0; index < mEndTrackList.size(); index++) {
            endLatTotal += Double.valueOf(mEndTrackList.get(index).getLat());
            endLngTotal += Double.valueOf(mEndTrackList.get(index).getLng());
            polylines2.add(new LatLng(Double.valueOf(mEndTrackList.get(index).getLat()), Double.valueOf(mEndTrackList.get(index).getLng())));
        }

        if (polylines2.size() > 0) {
            //终点图标
            MarkerOptions markerOptionsA = new MarkerOptions()
                    .position(new LatLng(endLatTotal / polylines1.size(), endLngTotal / polylines1.size()))
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.dzld_ckgj_end))// 设置 Marker 覆盖物的图标
                    .zIndex(9)// 设置 marker 覆盖物的 zIndex
                    .draggable(true); // 设置 marker 是否允许拖拽，默认不可拖拽
            mBaiduMap.addOverlay(markerOptionsA);

            OverlayOptions ooPolygon2 = new PolygonOptions()
                    .points(polylines2)// 设置多边形坐标点列表
                    .stroke(new Stroke(10,  Color.argb(100, 0, 0, 255)))// 设置多边形边框信息
                    .fillColor(Color.argb(50, 0, 0, 255));// 设置多边形填充颜色
            // 添加覆盖物
            mEndPolyline = (Polygon) mBaiduMap.addOverlay(ooPolygon2);
        }

        //轨迹纹理
        List<LatLng> polylines = new ArrayList<>();
        for (int index = 0; index < mTrackList.size(); index++) {
            polylines.add(new LatLng(Double.valueOf(mTrackList.get(index).getLat()), Double.valueOf(mTrackList.get(index).getLng())));
        }

        PolylineOptions polylineOptions = new PolylineOptions().points(polylines).width(10).customTexture(mGreenTexture)
                .dottedLine(true);
        mPolyline = (Polyline) mBaiduMap.addOverlay(polylineOptions);

        // 添加小车marker
        OverlayOptions markerOptions = new MarkerOptions().flat(true).anchor(0.5f, 0.5f).icon(mBitmapCar).
                position(polylines.get(0)).rotate((float) getAngle(0));
        mMoveMarker = (Marker) mBaiduMap.addOverlay(markerOptions);

        // 添加InfoWindow相关
        initMaekrer();

    }

    private void initMaekrer() {
        view = LayoutInflater.from(this).inflate(R.layout.daye, null);
        textView1 = view.findViewById(R.id.tv1);
        textView2 = view.findViewById(R.id.tv2);
        textView3 = view.findViewById(R.id.tv3);
        textView1.setText(mTrackList.get(0).getVelocity());
        textView2.setText(mTrackList.get(0).getGps_time());
        textView3.setText(mTrackList.get(0).getEdr_odometer());
    }

    /**
     * 根据点获取图标转的角度
     */
    private double getAngle(int startIndex) {
        if ((startIndex + 1) >= mPolyline.getPoints().size()) {
            throw new RuntimeException("index out of bonds");
        }
        LatLng startPoint = mPolyline.getPoints().get(startIndex);
        LatLng endPoint = mPolyline.getPoints().get(startIndex + 1);
        return getAngle(startPoint, endPoint);
    }

    /**
     * 根据两点算取图标转的角度
     */
    private double getAngle(LatLng fromPoint, LatLng toPoint) {
        double slope = getSlope(fromPoint, toPoint);
        if (slope == Double.MAX_VALUE) {
            if (toPoint.latitude > fromPoint.latitude) {
                return 0;
            } else {
                return 180;
            }
        } else if (slope == 0.0) {
            if (toPoint.longitude > fromPoint.longitude) {
                return -90;
            } else {
                return 90;
            }
        }
        float deltAngle = 0;
        if ((toPoint.latitude - fromPoint.latitude) * slope < 0) {
            deltAngle = 180;
        }
        double radio = Math.atan(slope);
        double angle = 180 * (radio / Math.PI) + deltAngle - 90;
        return angle;
    }

    /**
     * 根据点和斜率算取截距
     */
    private double getInterception(double slope, LatLng point) {
        double interception = point.latitude - slope * point.longitude;
        return interception;
    }

    /**
     * 算斜率
     */
    private double getSlope(LatLng fromPoint, LatLng toPoint) {
        if (toPoint.longitude == fromPoint.longitude) {
            return Double.MAX_VALUE;
        }
        double slope = ((toPoint.latitude - fromPoint.latitude) / (toPoint.longitude - fromPoint.longitude));
        return slope;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBitmapCar.recycle();
        mGreenTexture.recycle();
        mBaiduMap.clear();
        mMapView.onDestroy();
    }

    /**
     * 计算x方向每次移动的距离
     */
    private double getXMoveDistance(double slope) {
        if (slope == Double.MAX_VALUE || slope == 0.0) {
            return DISTANCE;
        }
        return Math.abs((DISTANCE * 1 / slope) / Math.sqrt(1 + 1 / (slope * slope)));
    }

    /**
     * 计算y方向每次移动的距离
     */
    private double getYMoveDistance(double slope) {
        if (slope == Double.MAX_VALUE || slope == 0.0) {
            return DISTANCE;
        }
        return Math.abs((DISTANCE * slope) / Math.sqrt(1 + slope * slope));
    }

    /**
     * 循环进行移动逻辑
     */
    public void moveLooper() {
        myThread.start();
    }


    private void getTrackInfo() {
        RxJava.getInstance().create(new TrackInfoObservable(this), new RxJava.ObserveOnMainThread<TrackResponse>() {
            @Override
            public void accept(TrackResponse response) {
                Timber.d("getTrackInfo:" + response.toString());
                if (response.getStatus() == 200) {
                    List<TrackInfo> trackInfoList = response.getData();
                    if (trackInfoList != null) {
                        Timber.d("getTrackInfo:" + trackInfoList.toString());
                        showResult(trackInfoList);
                    }
                } else {
                    ToastUtils.showToast(BaseApplication.getApp(), response.getMessage());
                }
            }
        }, new RxJava.ThrowableOnMainThread() {
            @Override
            public void accept(String t) {
                ToastUtils.showToast(BaseApplication.getApp(), t);
            }
        });
    }

    private static class TrackInfoObservable implements ObservableOnSubscribe<TrackResponse> {

        private WeakReference<TrackShowActivity> mWeakReference;

        public TrackInfoObservable(TrackShowActivity infoDetailActivity) {
            mWeakReference = new WeakReference<>(infoDetailActivity);
        }

        @Override
        public void subscribe(ObservableEmitter<TrackResponse> emitter) throws Exception {
            TrackShowActivity infoDetailActivity = mWeakReference.get();
            if (infoDetailActivity == null) {
                return;
            }
            try {
                Map<String, String> map = new LinkedHashMap<>();
                map.put("ebillNo", String.valueOf(infoDetailActivity.ebill_no));
                map.put("dataSource", "2000");
                TrackResponse response = NetWorkDataManager.getTrackInfo(map);
                emitter.onNext(response);
            } catch (Exception e) {
                emitter.onError(e);
            }
            emitter.onComplete();
        }
    }

}

