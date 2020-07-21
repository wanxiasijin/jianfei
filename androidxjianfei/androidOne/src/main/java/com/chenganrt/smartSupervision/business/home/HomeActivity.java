package com.chenganrt.smartSupervision.business.home;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.commonlib.view.flycotablayout.CommonTabLayout;
import com.android.commonlib.view.flycotablayout.listener.CustomTabEntity;
import com.chenganrt.smartSupervision.R;
import com.chenganrt.smartSupervision.business.app.BaseActivity;
import com.chenganrt.smartSupervision.business.home.adapter.ContentAdapter;
import com.chenganrt.smartSupervision.business.home.adapter.GlideImageLoader;
import com.chenganrt.smartSupervision.business.home.chart.DayAxisValueFormatter;
import com.chenganrt.smartSupervision.business.home.chart.XYMarkerView;
import com.chenganrt.smartSupervision.business.login.UserSP;
import com.chenganrt.smartSupervision.common.okhttputil.CallBackUtil;
import com.chenganrt.smartSupervision.common.okhttputil.HttpConstant;
import com.chenganrt.smartSupervision.common.okhttputil.OkhttpUtil;
import com.chenganrt.smartSupervision.model.response.HomeContentData;
import com.chenganrt.smartSupervision.model.response.HomeCoverBean;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yyydjk.library.BannerLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.Call;
import util.UpdateAppUtils;


public class HomeActivity extends BaseActivity implements OnClickListener, OnRefreshListener {
    private CommonTabLayout mTabLayout;
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private ArrayList<Fragment> mFragments = new ArrayList<>();

    private RecyclerView recyclerView_one;
    private ContentAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private ImageView iv_messag;

    private BarChart mChart;
    private List<BarEntry> mList;
    private List<BarEntry> mList1;

    private HomeCoverBean homeCoverBean;
    private HomeContentData mHomeContentData;

    private TextView tv_more;
    private TextView tv_number;
    private View view_message_num;
    private SmartRefreshLayout mSmartRefreshLayout;

    private ExchangeFragment exchangeFragment;
    private DuplicateFragment duplicateFragment;
    private BannerLayout bannerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);
		EventBus.getDefault().register(this);
        initView();

        recyclerView_one = (RecyclerView) findViewById(R.id.recyclerView_one);
        recyclerView_one.setHasFixedSize(true);
        //创建线性布局
        mLayoutManager = new LinearLayoutManager(this);
//		{
//			@Override
//			public boolean canScrollVertically() {
//				return false;
//			}
//		}
        //垂直方向
        mLayoutManager.setOrientation(RecyclerView.VERTICAL);
        //给RecyclerView设置布局管理器
        recyclerView_one.setLayoutManager(mLayoutManager);
        //创建适配器，并且设置
        mAdapter = new ContentAdapter(this);
        recyclerView_one.setAdapter(mAdapter);
        recyclerView_one.setItemAnimator(null);
        mAdapter.setOnItemClickListener(new ContentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, ContentEntity vehicleEntity, int position) {
//                Toast.makeText(HomeActivity.this, position + "", Toast.LENGTH_SHORT).show();
            }
        });
        mChart = (BarChart) findViewById(R.id.chart1);
        // mChart.getViewPortHandler().setMaxOffsetLeft(150);
        mChart.getViewPortHandler().setMaximumScaleY(10f);
        mList = new ArrayList<>();
        mList1 = new ArrayList<>();
        initChart();
        initYAxis(); //右侧Y轴
        initLegend();
        requestData();
        //	initUpdate();


    }
    private void initUpdate() {
        String url = HttpConstant.baseUrl + "/earthworkexchange/userTotal";
        Map<String, String> map = new HashMap<>();
        map.put("id", UserSP.getInstance().getUserId(this));
        OkhttpUtil.okHttpGet(this, url, map, new CallBackUtil.CallBackString() {
            @Override
            public void onFailure(Call call, Exception e) {
                //Toast.makeText(HomeActivity.this, "服务器错误", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
                UpdateAppUtils.from(HomeActivity.this)
                        .checkBy(UpdateAppUtils.CHECK_BY_VERSION_NAME) //更新检测方式，默认为VersionCode
                        .serverVersionCode(2)
                        .serverVersionName("2.0")
                        //	.apkPath(apkPath)
                        .showNotification(false) //是否显示下载进度到通知栏，默认为true
                        .updateInfo("info")  //更新日志信息 String
                        .downloadBy(UpdateAppUtils.DOWNLOAD_BY_BROWSER) //下载方式：app下载、手机浏览器下载。默认app下载
                        .isForce(true) //是否强制更新，默认false 强制更新情况下用户不同意更新则不能使用app
                        .update();

            }
        });
    }

    private void requestData() {
        initCoverData();
        initContentData();
        initUnReadCount();
        initRecentlyData();
        updateComonTabLayout();
    }

    private void initRecentlyData() {
        String url = HttpConstant.baseUrl + "/ebill/timeRangeStatistics";
        Map<String, String> map = new HashMap<>();
        map.put("userId", UserSP.getInstance().getUserId(this));
        OkhttpUtil.okHttpGet(this, url, map, new CallBackUtil.CallBackString() {
            @Override
            public void onFailure(Call call, Exception e) {
                //Toast.makeText(HomeActivity.this, "服务器错误", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response) {
                List<String> mStringList = new ArrayList<>();
                List<Integer> mIntList = new ArrayList<>();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        String type = jsonArray.getJSONObject(i).getString("type");
                        int count = jsonArray.getJSONObject(i).getInt("count");
                        mStringList.add(type);
                        mIntList.add(count);
                    }

//					mIntList.add(500);
//					mIntList.add(1500);
//					mIntList.add(1000);
//					mIntList.add(3000);

                    IAxisValueFormatter xAxisFormatter = initAxis(mStringList);
                    initXYMarkerView(xAxisFormatter);
                    initData(mIntList);
                    initYAix(mIntList);


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });

    }

    private void initUnReadCount() {
        String url = HttpConstant.baseUrl + "/sys/ssitgNoticeMsg/unReadCount";
        OkhttpUtil.okHttpGet(this, url, new CallBackUtil.CallBackString() {
            @Override
            public void onFailure(Call call, Exception e) {

            }
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.has("data") && !jsonObject.isNull("data")) {
                        int number = (int) jsonObject.optJSONObject("data").optInt("total");
                        if (number > 0) {
                            view_message_num.setVisibility(View.VISIBLE);
                        } else {
                            view_message_num.setVisibility(View.INVISIBLE);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initContentData() {
        String url = HttpConstant.baseUrl + "/home/news";
        OkhttpUtil.okHttpGet(this, url, new CallBackUtil.CallBackString() {
            @Override
            public void onFailure(Call call, Exception e) {

            }
            @Override
            public void onResponse(String response) {
                List<ContentEntity> mList = new ArrayList<>();
                mHomeContentData = new Gson().fromJson(response, HomeContentData.class);
                if (mHomeContentData != null) {
                    if (mHomeContentData.getData() != null) {
                        for (int i = 0; i < mHomeContentData.getData().size(); i++) {
                            ContentEntity contentEntity = new ContentEntity(mHomeContentData.getData().get(i).getIs_top(), mHomeContentData.getData().get(i).getTitle(),
                                    "来源: " + mHomeContentData.getData().get(i).getTitle(), mHomeContentData.getData().get(i).getPublish_time());
                            mList.add(contentEntity);
                        }
                    }
                }
                mAdapter.refresh(mList);
                if (!mList.isEmpty()) {
                    tv_more.setVisibility(View.VISIBLE);
                } else {
                    tv_more.setVisibility(View.INVISIBLE);
                }
                tv_number.setText(95 + "%");
            }
        });
    }

    public void initCoverData() {
        String url = HttpConstant.baseUrl + "/home/cover";
        OkhttpUtil.okHttpGet(this, url, new CallBackUtil.CallBackString() {
            @Override
            public void onFailure(Call call, Exception e) {
            }

            @Override
            public void onResponse(String response) {
                homeCoverBean = new Gson().fromJson(response, HomeCoverBean.class);
                initBannerData();
            }
        });
    }

    private void initView() {
        mSmartRefreshLayout = findViewById(R.id.refreshLayout);
        mSmartRefreshLayout.setEnableRefresh(true);//是否启用下拉刷新功能
        mSmartRefreshLayout.setEnableLoadMore(false);//是否启用上拉加载功能
        mSmartRefreshLayout.setOnRefreshListener(this);

        iv_messag = (ImageView) findViewById(R.id.iv_message);
        iv_messag.setOnClickListener(this);
        tv_more = (TextView) findViewById(R.id.tv_more);
        tv_more.setOnClickListener(this);
        tv_number = (TextView) findViewById(R.id.tv_number);
        view_message_num = (View) findViewById(R.id.view_message_num);

        bannerLayout = (BannerLayout) findViewById(R.id.banner);
        mTabLayout = (CommonTabLayout) findViewById(R.id.commontablayout);
        if(exchangeFragment==null){
            exchangeFragment = new ExchangeFragment();
        }
        if(duplicateFragment==null){
            duplicateFragment = new DuplicateFragment();
        }

    }

    private void initBannerData() {
        final List<String> urls = new ArrayList<>();
        final List<String> urls_link = new ArrayList<>();
        if (homeCoverBean != null) {
            if (homeCoverBean.getData() != null) {
                for (int i = 0; i < homeCoverBean.getData().size(); i++) {
                    urls.add(homeCoverBean.getData().get(i).getImg_url());
                }
                for (int i = 0; i < homeCoverBean.getData().size(); i++) {
                    urls_link.add(homeCoverBean.getData().get(i).getLink_url());
                }
            }
            bannerLayout.setImageLoader(new GlideImageLoader());
            if (!urls.isEmpty()) {
                bannerLayout.setViewUrls(urls);
            }
            //添加监听事件
            bannerLayout.setOnBannerItemClickListener(new BannerLayout.OnBannerItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    WebViewActivity.start(HomeActivity.this, urls_link.get(position));
                }
            });
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refrasfEventBus(RefreshEvent refreshEvent) {
        if (refreshEvent == null) return;
          updateComonTabLayout();
    }

    public void updateComonTabLayout() {
        exchangeFragment.setRefresh(true);
        mFragments.clear();
        mTabEntities.clear();
        if (TextUtils.isEmpty(UserSP.getInstance().getUserId(this))) {
       // if (!TextUtils.isEmpty(UserSP.getInstance().getUserId(this))) {
     //  if (!TextUtils.isEmpty(UserSP.getInstance().getUserId(this))&&UserSP.getInstance().getUserType(this)!=98) {
            mFragments.add(duplicateFragment);
            mFragments.add(exchangeFragment);
            mTabEntities.add(new TabEntity("电子联单", 0, 0));
        } else {
            mFragments.add(exchangeFragment);
        }

       mTabEntities.add(new TabEntity("土方交换", 0, 0));


        if (mFragments.size() == 2) {
//            mTabLayout.setTabData(mTabEntities, this, R.id.fl_change, mFragments, 1);
//            mTabLayout.setCurrentTab(1);
            mTabLayout.setTabData(mTabEntities, this, R.id.fl_change, mFragments,mTabLayout.getCurrentTab());
        } else if (mFragments.size() == 1) {
            mTabLayout.setTabData(mTabEntities, this, R.id.fl_change, mFragments);
         //   mTabLayout.setCurrentTab(0);
            //添加这句会导致重叠
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_message:
                Intent intent = new Intent(HomeActivity.this, MatchingMessageActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_more:
                WebViewActivity.start(HomeActivity.this, "http://zjj.sz.gov.cn/xxgk/tzgg/index.html");
                break;
        }
    }

    private void initYAix(List<Integer> mIntList) {
        mChart.getAxisLeft().setAxisMinimum(0);
        if (mIntList != null && !mIntList.isEmpty()) {
            mChart.getAxisLeft().setAxisMaximum(Float.valueOf(Collections.max(mIntList)));
        }
        mChart.getAxisLeft().setLabelCount(4, false);
        mChart.getAxisLeft().setDrawAxisLine(false);
        mChart.getAxisLeft().setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                java.text.DecimalFormat myformat = new java.text.DecimalFormat("0.0");
                String str = myformat.format(value / 1000);
                return str + "k";
            }
        });
        mChart.getAxisLeft().setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
    }

    private void initChart() {
        mChart.setDrawBarShadow(false);
        mChart.setDrawValueAboveBar(true);
        mChart.getDescription().setEnabled(false);
        mChart.setPinchZoom(false);
        mChart.setDrawGridBackground(false);
        mChart.setDragEnabled(false);   //能否拖拽
        mChart.setScaleEnabled(false);  //能否缩放
        mChart.setNoDataText("暂无数据");
        mChart.setNoDataTextColor(Color.parseColor("#8d8e8e"));


    }

    private IAxisValueFormatter initAxis(List<String> mStringList) {
        IAxisValueFormatter xAxisFormatter = new DayAxisValueFormatter(mStringList);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setLabelCount(4);
        xAxis.setValueFormatter(xAxisFormatter);


        xAxis.setAxisMinimum(-0.2f);
        xAxis.setCenterAxisLabels(true);  // X轴的标签是不是居中
        return xAxisFormatter;
    }

    private void initYAxis() {
        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setEnabled(false);
    }

    private void initXYMarkerView(IAxisValueFormatter xAxisFormatter) {
        XYMarkerView mv = new XYMarkerView(this, xAxisFormatter);
        mv.setChartView(mChart); // For bounds control
        mChart.setMarker(mv); // Set the marker to the chart
    }

    private void initLegend() {

        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setForm(Legend.LegendForm.CIRCLE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(4f);
        l.setDrawInside(false);
    }

    private void initData(List<Integer> mIntList) {
        for (int i = 0; i < mIntList.size(); i++) {
            mList.add(new BarEntry(i + 1, mIntList.get(i), "联单数量"));
        }

        //为第一组添加数据
//		mList.add(new BarEntry(1, mIntList.get(0), "联单数量"));
//		mList.add(new BarEntry(2, mIntList.get(1), "联单数量"));
//		mList.add(new BarEntry(3, mIntList.get(2), "联单数量"));
//		mList.add(new BarEntry(4, mIntList.get(3), "联单数量"));

        //为第二组添加数据
//		mList1.add(new BarEntry(1, 100, "异常结束联单"));
//		mList1.add(new BarEntry(2, 50, "异常结束联单"));
//		mList1.add(new BarEntry(3, 570, "异常结束联单"));
//		mList1.add(new BarEntry(4, 400, "异常结束联单"));


        BarDataSet barDataSet = new BarDataSet(mList, "联单数量");
        barDataSet.setColor(getResources().getColor(R.color.red));

//		BarDataSet barDataSet2 = new BarDataSet(mList1, "异常结束联单");
//		barDataSet2.setColor(getResources().getColor(R.color.blue));


        mChart.animateX(1000, Easing.EasingOption.Linear);
        mChart.animateY(1000, Easing.EasingOption.Linear);

        BarData barData = new BarData();
        barData.addDataSet(barDataSet);
        barData.setBarWidth(0.3f);
        //	barData.addDataSet(barDataSet2);
        if (mIntList.isEmpty()) {
            mChart.setData(null);
        } else {
            mChart.setData(barData);
        }

        //	barData.groupBars(0f, 0.4f, 0.0001f);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mSmartRefreshLayout.finishRefresh(true);
        requestData();
    }

	@Override
	protected void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}
}
