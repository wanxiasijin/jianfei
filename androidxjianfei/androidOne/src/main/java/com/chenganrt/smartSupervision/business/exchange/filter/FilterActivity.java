package com.chenganrt.smartSupervision.business.exchange.filter;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.commonlib.mvp.BaseActivity;
import com.android.commonlib.okhttp.NetWorkDataManager;
import com.android.commonlib.okhttp.bean.Response;
import com.android.commonlib.okhttp.bean.TypeBean;
import com.android.commonlib.rxjava.RxJava;
import com.android.commonlib.utils.GsonUtil;
import com.android.commonlib.utils.ToastUtils;
import com.android.commonlib.utils.Utils;
import com.android.commonlib.utils.WindowUtil;
import com.chenganrt.smartSupervision.R;
import com.chenganrt.smartSupervision.business.app.BaseApplication;
import com.chenganrt.smartSupervision.business.electronic.util.AppTools;
import com.chenganrt.smartSupervision.business.exchange.entity.ExchangeListResponse;
import com.chenganrt.smartSupervision.business.exchange.entity.FilterData;
import com.chenganrt.smartSupervision.business.exchange.filter.entity.FilterEntity;
import com.chenganrt.smartSupervision.business.exchange.filter.search.SearchLabelContainerView;
import com.chenganrt.smartSupervision.business.exchange.filter.tab.InfoFragment;
import com.chenganrt.smartSupervision.business.exchange.filter.view.FilterInfoBean;
import com.chenganrt.smartSupervision.business.exchange.filter.view.FilterResultBean;
import com.chenganrt.smartSupervision.business.exchange.filter.view.FilterTabConfig;
import com.chenganrt.smartSupervision.business.exchange.filter.view.FilterTabView;
import com.chenganrt.smartSupervision.business.exchange.filter.view.listener.OnPopupDismissListener;
import com.chenganrt.smartSupervision.business.exchange.filter.view.listener.OnSelectResultListener;
import com.chenganrt.smartSupervision.business.exchange.filter.slide.ui.RightSidesView;
import com.android.commonlib.okhttp.bean.ExchangeData;
import com.chenganrt.smartSupervision.utils.db.entity.SearchRecord;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import timber.log.Timber;

public class FilterActivity extends BaseActivity<FilterPresenter> implements OnSelectResultListener, OnPopupDismissListener, OnRefreshListener, OnLoadMoreListener, FilterContact.View {
    @BindView(R.id.search_toolbar)
    RelativeLayout mToolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawer;
    @BindView(R.id.btn_back)
    ImageView mBack;
    @BindView(R.id.et_toolbar_search)
    EditText mSearchContent;
    @BindView(R.id.tv_toolbar_search)
    TextView mSearch;
    @BindView(R.id.ftb_filter)
    FilterTabView ftb_filter;
    @BindView(R.id.nav_view)
    RelativeLayout mSlideFilterView;
    private InfoFragment mInfoFragment;
    private RightSidesView menuHeaderView;
    private List<Map<String, SearchRecord>> mMultipleSelectList = new ArrayList<>();
    private Map<String, String> mSingleSelectedMap = new LinkedHashMap<>();
    //search layout
    private PopupWindow mPop;
    private SearchLabelContainerView mHistoryLabel;
    private SearchLabelContainerView mHotLabel;
    private LinearLayout mHistoryLinear;
    //refresh
    @BindView(R.id.smart_fresh)
    SmartRefreshLayout mSmartRefreshLayout;
    private static final int REFRESH = 0;
    private static final int LOADMORE = 1;
    private int mCurrentPage = 1;
    private int mDirection = 0;

    @Override
    public int initLayout() {
        return R.layout.activity_exchange_list;
    }

    @Override
    public FilterPresenter initPresenter() {
        return new FilterPresenter(this);
    }

    @Override
    public void initConfig() {
        getSupportActionBar().hide();
        WindowUtil.setStatusTextColor(true, getWindow(), getResources().getColor(com.android.commonlib.R.color.black));
        initFragment();
        initFilter();
        initRefresh();
    }

    @Override
    public void initData() {

    }

    private void initFragment() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        mInfoFragment = InfoFragment.getInstance();
        transaction.add(R.id.fl_change, mInfoFragment).commit();
    }

    private void initFilter() {
        mDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.RIGHT);
        menuHeaderView = new RightSidesView(FilterActivity.this);
        menuHeaderView.setListener(new RightSidesView.RightSideListener() {
            @Override
            public void onMultipleSelectResult(List<Map<String, SearchRecord>> list) {
                Timber.d("mMultipleSelectList1 size" + mMultipleSelectList.size());
                mMultipleSelectList.clear();
                Timber.d("mMultipleSelectList2 size" + mMultipleSelectList.size());
                mMultipleSelectList.addAll(list);
                Timber.d("mMultipleSelectList3 size" + mMultipleSelectList.size());
                onDismiss();
                mSmartRefreshLayout.autoRefresh();
            }
        });
        menuHeaderView.setData(getPresenter().getLabelData());
        mSlideFilterView.addView(menuHeaderView);

        String jsonStr = GsonUtil.getJson(this, "filter_data.json");
        FilterEntity filterEntity = GsonUtil.JsonToObject(jsonStr, FilterEntity.class);

        ftb_filter.removeViews();
        FilterInfoBean bean1 = new FilterInfoBean("发布时间", FilterTabConfig.FILTER_TYPE_SINGLE_SELECT, filterEntity.getTime());
        FilterInfoBean bean2 = new FilterInfoBean("类型", FilterTabConfig.FILTER_TYPE_SINGLE_SELECT, filterEntity.getType());
        FilterInfoBean bean3 = new FilterInfoBean("区域", FilterTabConfig.FILTER_TYPE_SINGLE_SELECT, filterEntity.getArea());
        FilterInfoBean bean4 = new FilterInfoBean("筛选", FilterTabConfig.FILTER_TYPE_MUL_SELECT, filterEntity.getMulSelect());

        ftb_filter.addFilterItem(bean1.getTabName(), bean1.getFilterData(), bean1.getPopupType(), 0);
        ftb_filter.addFilterItem(bean2.getTabName(), bean2.getFilterData(), bean2.getPopupType(), 1);
        ftb_filter.addFilterItem(bean3.getTabName(), bean3.getFilterData(), bean3.getPopupType(), 2);
        ftb_filter.addFilterItem(bean4.getTabName(), bean4.getFilterData(), bean4.getPopupType(), 3);

        ftb_filter.setOnSelectResultListener(this);
        ftb_filter.setOnPopupDismissListener(this);
    }

    private void initRefresh() {
        mSmartRefreshLayout.setOnLoadMoreListener(this);
        mSmartRefreshLayout.setOnRefreshListener(this);
        mSmartRefreshLayout.setEnableOverScrollDrag(true);
        mSmartRefreshLayout.setEnableLoadMoreWhenContentNotFull(true);//内容不满一页时候启用加载更多
        if (isNetworkAvailable()) {
            mSmartRefreshLayout.autoRefresh();
        }
    }

    @Override
    public void onSelectResult(FilterResultBean resultBean) {
        if (mSingleSelectedMap.get(String.valueOf(resultBean.getPopupIndex())) != null) {
            mSingleSelectedMap.remove(String.valueOf(resultBean.getPopupIndex()));
        }
        mSingleSelectedMap.put(String.valueOf(resultBean.getPopupIndex()), resultBean.getName());
        Timber.d("onSelectResult message:" + resultBean.getName());
        if (isNetworkAvailable()) {
            mSmartRefreshLayout.autoRefresh();
        }
    }

    private void getFilterData() {
        RxJava.getInstance().create(new FilterInfoObservable(this), new RxJava.ObserveOnMainThread<Response>() {
            @Override
            public void accept(Response response) {
                if (isActive) {
                    if (mDirection == REFRESH) {
                        mSmartRefreshLayout.finishRefresh();
                    } else {
                        mSmartRefreshLayout.finishLoadMore();
                    }
                    Timber.d("getRecord:" + response.toString());
                    if (response.getStatus() == 200) {
                        ExchangeListResponse exchangeListResponse = GsonUtil.JsonToObject(GsonUtil.toJson(response.getData()), ExchangeListResponse.class);
                        if (exchangeListResponse != null) {
                            showResult(exchangeListResponse.getRecords());
                        }
                    } else {
                        showResult(null);
                        ToastUtils.showToast(BaseApplication.getApp(), response.getMessage());
                    }
                }
            }
        }, new RxJava.ThrowableOnMainThread() {
            @Override
            public void accept(String t) {
                if (isActive) {
                    if (mDirection == REFRESH) {
                        mSmartRefreshLayout.finishRefresh();
                    } else {
                        mSmartRefreshLayout.finishLoadMore();
                    }
                    showResult(null);
                    ToastUtils.showToast(BaseApplication.getApp(), t);
                }
            }
        });
    }

    private void showResult(List<ExchangeData> list) {
        if (mDirection == REFRESH) {
            mInfoFragment.refreshData(list);
        } else {
            if (list != null && list.size() == 0) {
                ToastUtils.showToast(BaseApplication.getApp(), "没有更多数据");
            } else {
                mInfoFragment.addData(list);
            }
        }
    }

    @Override
    public void onSelectResultList(List<FilterResultBean> resultBeans) {
        String message = "";
        List<FilterResultBean> list = resultBeans;
        for (int i = 0; i < list.size(); i++) {
            FilterResultBean bean = list.get(i);
            if (i == (list.size() - 1)) {
                message = message + bean.getName();
            } else {
                message = message + bean.getName() + ",";
            }
        }
        Timber.d("onSelectResultList message:" + message);
        Toast.makeText(BaseApplication.getApp(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        if (isNetworkAvailable()) {
            mCurrentPage++;
            mDirection = LOADMORE;
            getFilterData();
        } else {
            mSmartRefreshLayout.finishLoadMore(false);
        }
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        if (isNetworkAvailable()) {
            mCurrentPage = 1;
            mDirection = REFRESH;
            getFilterData();
        } else {
            mSmartRefreshLayout.finishRefresh(false);
        }
    }


    @OnClick({R.id.btn_back, R.id.et_toolbar_search, R.id.tv_toolbar_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                onBackPressed();
                break;
            case R.id.et_toolbar_search:
//                Timber.d("showSearchReferenceView1");
//                showSearchReferenceView();
//                getPresenter().getHotRearch();
//                getPresenter().getRecord();
//                Utils.setSoftInput(this, this.getWindow().getDecorView(), true);
                break;
            case R.id.tv_toolbar_search:
                AppTools.collapseSoftInputMethod(this);
                if (mSearchContent.getText().toString().isEmpty()) {
                    showError("内容不能为空");
                } else {
//                    mSearchContent.setFocusable(false);
//                    onBackPressed();
//                    getPresenter().saveRecord(true, mSearchContent.getText().toString());
                    if (isNetworkAvailable()) {
                        mSmartRefreshLayout.autoRefresh();
                    }
                }
                break;
        }
    }

    private void showSearchReferenceView() {
        View searchView = View.inflate(this, R.layout.search_reference_layout, null);
        ImageView back = searchView.findViewById(R.id.btn_back);
        EditText content = searchView.findViewById(R.id.et_toolbar_search);
        TextView search = searchView.findViewById(R.id.tv_toolbar_search);
        mHistoryLinear = searchView.findViewById(R.id.ll_record_search);
        mHistoryLabel = searchView.findViewById(R.id.record_label_layout);
        mHotLabel = searchView.findViewById(R.id.hot_label_layout);
        Button clearButton = searchView.findViewById(R.id.btn_clear);

        mPop = new PopupWindow(searchView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mPop.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mPop.setOutsideTouchable(false);
        mPop.setFocusable(true);
        mPop.showAtLocation(this.getWindow().getDecorView(), Gravity.TOP, 0, 0);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPop.dismiss();
                mSearchContent.setText("");
                mSearchContent.setHint("搜索你想要的内容");
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (content.getText().toString().isEmpty()) {
                    showError("内容不能为空");
                } else {
                    mSearchContent.setText(content.getText().toString().trim());
                    onBackPressed();
                    getPresenter().saveRecord(true, mSearchContent.getText().toString());
                    mSmartRefreshLayout.autoRefresh();
                }
            }
        });

        mHistoryLabel.setOnLabelItemClickListener(new SearchLabelContainerView.OnLabelItemListener() {
            @Override
            public void onLabelItemClick(SearchRecord label) {
                hideSearchReferenceView();
                getPresenter().saveRecord(true, label.getKeyword());
                mSearchContent.setText(label.getKeyword());
                mSmartRefreshLayout.autoRefresh();
            }
        });

        mHotLabel.setOnLabelItemClickListener(new SearchLabelContainerView.OnLabelItemListener() {
            @Override
            public void onLabelItemClick(SearchRecord label) {
                hideSearchReferenceView();
                getPresenter().saveRecord(true, label.getKeyword());
                mSearchContent.setText(label.getKeyword());
                mSmartRefreshLayout.autoRefresh();
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHistoryLinear.setVisibility(View.GONE);
                getPresenter().saveRecord(false, "");
            }
        });

    }

    public void hideSearchReferenceView() {
        if (mPop != null && mPop.isShowing()) {
            Utils.setSoftInput(this, this.getWindow().getDecorView(), false);
            mPop.dismiss();
            mPop = null;
        }
    }

    @Override
    public void onDismiss() {
        if (mDrawer.isDrawerOpen(GravityCompat.END)) {
            mDrawer.closeDrawer(GravityCompat.END);
        } else {
            mDrawer.openDrawer(GravityCompat.END);
        }
    }

    @Override
    public void onBackPressed() {
        if (mPop != null && mPop.isShowing()) {
            hideSearchReferenceView();
            Timber.d("hideSearchReferenceView");
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void showHotData(List<SearchRecord> list) {
        mHotLabel.setLabels(list);
    }

    @Override
    public void showRecord(List<SearchRecord> list) {
        if (list != null && list.size() > 0) {
            mHistoryLinear.setVisibility(View.VISIBLE);
            mHistoryLabel.setLabels(list);
        } else {
            mHistoryLinear.setVisibility(View.GONE);
        }
    }

    @Override
    public void showError(String error) {
        Toast.makeText(BaseApplication.getApp(), error, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean isActive() {
        return false;
    }

    private static class FilterInfoObservable implements ObservableOnSubscribe<Response> {

        private WeakReference<FilterActivity> mWeakReference;

        public FilterInfoObservable(FilterActivity filterActivity) {
            mWeakReference = new WeakReference<>(filterActivity);
        }

        @Override
        public void subscribe(ObservableEmitter<Response> emitter) throws Exception {
            FilterActivity filterActivity = mWeakReference.get();
            if (filterActivity == null) {
                return;
            }
            try {
                Map<String, String> map = new LinkedHashMap<>();
                if (filterActivity.mSingleSelectedMap.get("0") != null && !filterActivity.mSingleSelectedMap.get("0").equals("不限")) {
                    map.put("sort_type", filterActivity.mSingleSelectedMap.get("0").equals("升序") ? "1" : "0");
                }

                String exchange_type = filterActivity.mSingleSelectedMap.get("1");
                if (exchange_type != null && !exchange_type.equals("不限")) {
                    map.put("exchange_type", exchange_type.substring(exchange_type.length() - 2).equals("供应") ? "0" : "1");
                    map.put("waste_type", exchange_type.substring(0, exchange_type.length() - 2).equals("废弃物")? "construction_waste" : "comprehensive_utilization");
                }

                String area = filterActivity.mSingleSelectedMap.get("2");
                if (area != null && !area.equals("不限")) {
                    for (TypeBean typeBean : FilterData.getTypes(FilterData.AREA)) {
                        if (typeBean.getName().equals(area)) {
                            Timber.d("area:" + typeBean.getCode());
                            map.put("area", typeBean.getCode());
                        }
                    }
                }
                StringBuilder stringBuilder = new StringBuilder();
                for (int multipleType = 0; multipleType < filterActivity.mMultipleSelectList.size(); multipleType++) {
                    switch (multipleType) {
                        case 0:
                            int wasteTypeSize = filterActivity.mMultipleSelectList.get(0).size();
                            if (wasteTypeSize > 0) {
                                for (SearchRecord label : filterActivity.mMultipleSelectList.get(0).values()) {
                                    Timber.d("label0:" + label.getKeyword());
                                    for (TypeBean typeBean : FilterData.getTypes(FilterData.CONSTRUCTION_WASTE)) {
                                        Timber.d("typeBean0:" + typeBean.getName());
                                        if (typeBean.getName().equals(label.getKeyword())) {
                                            stringBuilder.append(typeBean.getCode() + ",");
                                        }
                                    }
                                }
                                Timber.d("CONSTRUCTION_WASTE:" + stringBuilder.toString());
                            }
                            break;
                        case 1:
                            int wasteTypeDetailSize = filterActivity.mMultipleSelectList.get(1).size();
                            if (wasteTypeDetailSize > 0) {
                                for (SearchRecord label : filterActivity.mMultipleSelectList.get(1).values()) {
                                    Timber.d("label1:" + label.getKeyword());
                                    for (TypeBean typeBean : FilterData.getTypes(FilterData.COMPREHENSIVE_UTILIZATION)) {
                                        Timber.d("typeBean1:" + typeBean.getName());
                                        if (typeBean.getName().equals(label.getKeyword())) {
                                            stringBuilder.append(typeBean.getCode() + ",");
                                        }
                                    }
                                }
                                Timber.d("COMPREHENSIVE_UTILIZATION:" + stringBuilder.toString());
                            }
                            break;
                        case 2:
                            int dateRangeSize = filterActivity.mMultipleSelectList.get(2).size();
                            if (dateRangeSize > 0) {
                                StringBuilder dataStringBuilder = new StringBuilder();
                                for (SearchRecord label : filterActivity.mMultipleSelectList.get(2).values()) {
                                    dataStringBuilder.append(label.getKeyword() + ",");
                                }
                                String dataRange = dataStringBuilder.toString().substring(0, dataStringBuilder.toString().length() - 3);
                                Timber.d("dataRange:" + dataRange);
                                map.put("date_range", dataRange);
                            }
                            break;
                    }
                }
                if (stringBuilder.length() != 0) {
                    String wasteTypeDetail = stringBuilder.toString().substring(0, stringBuilder.toString().length() - 1);
                    Timber.d("wasteTypeDetail:" + wasteTypeDetail);
                    map.put("waste_type_detail", wasteTypeDetail);
                }

                map.put("current", String.valueOf(filterActivity.mCurrentPage));
                if (!filterActivity.mSearchContent.getText().toString().trim().isEmpty()) {
                    map.put("keyword", filterActivity.mSearchContent.getText().toString().trim());
                }
                Response response = NetWorkDataManager.getExchangeDataList(map);
                emitter.onNext(response);
            } catch (Exception e) {
                emitter.onError(e);
            }
            emitter.onComplete();
        }
    }
}