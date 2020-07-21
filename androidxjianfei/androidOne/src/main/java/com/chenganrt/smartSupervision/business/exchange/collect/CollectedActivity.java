package com.chenganrt.smartSupervision.business.exchange.collect;

import android.widget.TextView;

import com.android.commonlib.mvp.BaseActivity;
import com.android.commonlib.mvp.BasePresenter;
import com.android.commonlib.okhttp.NetWorkDataManager;
import com.android.commonlib.okhttp.bean.Response;
import com.android.commonlib.rxjava.RxJava;
import com.android.commonlib.utils.GsonUtil;
import com.android.commonlib.utils.ToastUtils;
import com.android.commonlib.view.flycotablayout.CommonTabLayout;
import com.android.commonlib.view.flycotablayout.listener.CustomTabEntity;
import com.android.commonlib.view.flycotablayout.listener.OnTabSelectListener;
import com.chenganrt.smartSupervision.R;
import com.chenganrt.smartSupervision.business.app.BaseApplication;
import com.chenganrt.smartSupervision.business.exchange.collect.tab.CollectData;
import com.chenganrt.smartSupervision.business.exchange.collect.tab.CollectResponse;
import com.chenganrt.smartSupervision.business.exchange.collect.tab.InfoFragment;
import com.chenganrt.smartSupervision.business.home.TabEntity;
import com.chenganrt.smartSupervision.business.login.UserSP;

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
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import timber.log.Timber;

public class CollectedActivity extends BaseActivity implements OnRefreshListener, OnLoadMoreListener {

    private static final int REFRESH = 0;
    private static final int LOADMORE = 1;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.titleSub)
    TextView titleSub;
    @BindView(R.id.common_tab_layout)
    CommonTabLayout mCommonTabLayout;
    @BindView(R.id.smart_fresh)
    SmartRefreshLayout mSmartRefreshLayout;
    private InfoFragment mInfoFragment1;
    private InfoFragment mInfoFragment2;
    private int mCurrentTabPosition = 0;
    private int mCurrentSupplyPage = 1;
    private int mCurrentDemandPage = 1;
    private int mDirection = 0;

    @Override
    public int initLayout() {
        return R.layout.activity_published;
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    public void initConfig() {
        setStatusBar(toolbar);
        titleSub.setText("我收藏的");
        initTab();
        initRefresh();
    }

    @Override
    public void initData() {

    }

    private void initTab() {
        String[] mTitles = {"供应信息", "需求信息"};
        ArrayList<Fragment> mFragments = new ArrayList<>();
        ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
        mInfoFragment1 = InfoFragment.getInstance();
        mFragments.add(mInfoFragment1);
        mTabEntities.add(new TabEntity(mTitles[0], 0, 0));
        mInfoFragment2 = InfoFragment.getInstance();
        mFragments.add(mInfoFragment2);
        mTabEntities.add(new TabEntity(mTitles[1], 0, 0));
        mCommonTabLayout.setTabData(mTabEntities, this, R.id.fl_change, mFragments);
        mCommonTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                Timber.d("onTabSelect:" + position);
                mCurrentTabPosition = position;
                if (isNetworkAvailable()) {
                    mSmartRefreshLayout.autoRefresh();
                }
            }

            @Override
            public void onTabReselect(int position) {
                Timber.d("onTabReselect:" + position);
            }
        });
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

    private void getCollectedInfo() {
        RxJava.getInstance().create(new CollectedInfoObservable(this), new RxJava.ObserveOnMainThread<Response>() {
            @Override
            public void accept(Response response) {
                if (isActive) {
                    if (mDirection == REFRESH) {
                        mSmartRefreshLayout.finishRefresh();
                    } else {
                        mSmartRefreshLayout.finishLoadMore();
                    }
                    Timber.d("getCollectedInfo:" + response.toString());
                    if (response.getStatus() == 200) {
                        CollectResponse collectResponse = GsonUtil.JsonToObject(GsonUtil.toJson(response.getData()), CollectResponse.class);
                        if (collectResponse != null) {
                            Timber.d("collectResponse:" + collectResponse.getRecords().toString());
                            showResult(collectResponse.getRecords());
                        }
                    } else {
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
                    ToastUtils.showToast(BaseApplication.getApp(), t);
                }
            }
        });
    }

    private void showResult(List<CollectData> list) {
        Timber.d("showResult:" + list.size());
        if (mDirection == REFRESH) {
            if (mCurrentTabPosition == 0) {
                mInfoFragment1.refreshData(list);
            } else {
                mInfoFragment2.refreshData(list);
            }
        } else {
            if (list.size() == 0) {
                ToastUtils.showToast(BaseApplication.getApp(), "没有更多数据");
//                mSmartRefreshLayout.finishLoadMoreWithNoMoreData();
            } else {
                if (mCurrentTabPosition == 0) {
                    mInfoFragment1.addData(list);
                } else {
                    mInfoFragment2.addData(list);
                }
            }
        }
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        if (isNetworkAvailable()) {
            if (mCurrentTabPosition == 0) {
                mCurrentSupplyPage++;
            } else {
                mCurrentDemandPage++;
            }
            mDirection = LOADMORE;
            getCollectedInfo();
        } else {
            mSmartRefreshLayout.finishLoadMore(false);
        }
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        if (isNetworkAvailable()) {
            if (mCurrentTabPosition == 0) {
                mCurrentSupplyPage = 1;
            } else {
                mCurrentDemandPage = 1;
            }
            mDirection = REFRESH;
            getCollectedInfo();
        } else {
            mSmartRefreshLayout.finishRefresh(false);
        }
    }

    private static class CollectedInfoObservable implements ObservableOnSubscribe<Response> {

        private WeakReference<CollectedActivity> mWeakReference;

        public CollectedInfoObservable(CollectedActivity collectedActivity) {
            mWeakReference = new WeakReference<>(collectedActivity);
        }

        @Override
        public void subscribe(ObservableEmitter<Response> emitter) throws Exception {
            CollectedActivity collectedActivity = mWeakReference.get();
            if (collectedActivity == null) {
                return;
            }
            try {
                Map<String, String> map = new LinkedHashMap<>();
                map.put("exchange_type", String.valueOf(collectedActivity.mCurrentTabPosition));
                map.put("person_id", UserSP.getInstance().getUserId(collectedActivity));
                map.put("current", collectedActivity.mCurrentTabPosition == 0? String.valueOf(collectedActivity.mCurrentSupplyPage) : String.valueOf(collectedActivity.mCurrentDemandPage));
                Response response = NetWorkDataManager.getCollectedInfo(map);
                emitter.onNext(response);
            } catch (Exception e) {
                emitter.onError(e);
            }
            emitter.onComplete();
        }
    }
}
