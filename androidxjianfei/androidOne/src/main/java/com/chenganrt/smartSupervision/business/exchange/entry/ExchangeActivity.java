
package com.chenganrt.smartSupervision.business.exchange.entry;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.commonlib.mvp.BaseActivity;
import com.android.commonlib.okhttp.bean.ExchangeStatistics;
import com.android.commonlib.utils.WindowUtil;
import com.android.commonlib.view.flycotablayout.CommonTabLayout;
import com.android.commonlib.view.flycotablayout.listener.CustomTabEntity;
import com.android.commonlib.view.flycotablayout.listener.OnTabSelectListener;
import com.chenganrt.smartSupervision.R;
import com.chenganrt.smartSupervision.business.main.MainActivity;
import com.chenganrt.smartSupervision.business.exchange.collect.CollectedActivity;
import com.chenganrt.smartSupervision.business.exchange.entry.tab.InfoFragment;
import com.chenganrt.smartSupervision.business.exchange.filter.FilterActivity;
import com.chenganrt.smartSupervision.business.exchange.publish.PublishActivity;
import com.chenganrt.smartSupervision.business.exchange.published.PublishedActivity;
import com.chenganrt.smartSupervision.business.home.TabEntity;
import com.chenganrt.smartSupervision.business.login.LoginActivity;
import com.chenganrt.smartSupervision.business.login.UserSP;
import com.android.commonlib.okhttp.bean.ExchangeData;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.OnClick;
import timber.log.Timber;

import static com.chenganrt.smartSupervision.business.login.LoginActivity.LOGIN_REQUSET_CODE;

/**
 * @author wanlc
 * @date 2020-6-1
 **/

public class ExchangeActivity extends BaseActivity<ExchangePresenter> implements ExchangeContact.View, OnRefreshListener {
    private static final int PUBLISHED_REQUSET_CODE = 200;
    private static final int COLLECTED_REQUSET_CODE = 300;
    @BindView(R.id.iv_top)
    ImageView mIvTop;
    @BindView(R.id.ll_publish)
    LinearLayout mPublish;
    @BindView(R.id.ll_published)
    LinearLayout mPublished;
    @BindView(R.id.ll_collect)
    LinearLayout mCollect;
    @BindView(R.id.tv_waste_supply_value)
    TextView mWasteSupply;
    @BindView(R.id.tv_waste_demand_value)
    TextView mWasteDemand;
    @BindView(R.id.tv_all_supply_value)
    TextView mAllSupply;
    @BindView(R.id.tv_all_demand_value)
    TextView mALLDemand;
    @BindView(R.id.common_tab_layout)
    CommonTabLayout mCommonTabLayout;
    @BindView(R.id.smart_fresh)
    SmartRefreshLayout mSmartRefreshLayout;
    @BindView(R.id.tv_info)
    TextView mInfo;
    private InfoFragment mSupplyInfoFragment;
    private InfoFragment mDemandInfoFragment;
    private int mCurrentTabPosition = 0;

    @Override
    public int initLayout() {
        return R.layout.exchange_layout;
    }

    @Override
    public ExchangePresenter initPresenter() {
        return new ExchangePresenter(this);
    }

    @Override
    public void initConfig() {
        getSupportActionBar().hide();
        WindowUtil.setStatusTextColor(true, getWindow(), getResources().getColor(com.android.commonlib.R.color.black));
        initTab();
        initRefresh();
    }

    private void initRefresh() {
        mSmartRefreshLayout.setOnRefreshListener(this);
        mSmartRefreshLayout.setEnableOverScrollDrag(true);
        mSmartRefreshLayout.setEnableLoadMoreWhenContentNotFull(true);//内容不满一页时候启用加载更多
        if (isNetworkAvailable()) {
            mSmartRefreshLayout.autoRefresh();
        }
    }

    @Override
    public void initData() {

    }

    private void initTab() {
        String[] mTitles = {"供应信息", "需求信息"};
        ArrayList<Fragment> mFragments = new ArrayList<>();
        ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
        mSupplyInfoFragment = InfoFragment.getInstance();
        mFragments.add(mSupplyInfoFragment);
        mTabEntities.add(new TabEntity(mTitles[0], 0, 0));
        mDemandInfoFragment = InfoFragment.getInstance();
        mFragments.add(mDemandInfoFragment);
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

    @OnClick({R.id.ll_publish, R.id.ll_published, R.id.ll_collect, R.id.tv_info})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_publish:
                if (!TextUtils.isEmpty(UserSP.getInstance().getUserId(this))) {
                    Timber.d("UserId:" + UserSP.getInstance().getUserId(this));
                    startActivity(new Intent(this, PublishActivity.class));
                } else {
                    Intent loginIntent = new Intent(this, LoginActivity.class);
                    startActivityForResult(loginIntent, LOGIN_REQUSET_CODE);
                }
                break;
            case R.id.ll_published:
                if (!TextUtils.isEmpty(UserSP.getInstance().getUserId(this))) {
                    Timber.d("UserId:" + UserSP.getInstance().getUserId(this));
                    startActivity(new Intent(this, PublishedActivity.class));
                } else {
                    Intent loginIntent = new Intent(this, LoginActivity.class);
                    startActivityForResult(loginIntent, LOGIN_REQUSET_CODE);
                }
                break;
            case R.id.ll_collect:
                if (!TextUtils.isEmpty(UserSP.getInstance().getUserId(this))) {
                    Timber.d("UserId:" + UserSP.getInstance().getUserId(this));
                    startActivity(new Intent(this, CollectedActivity.class));
                } else {
                    Intent loginIntent = new Intent(this, LoginActivity.class);
                    startActivityForResult(loginIntent, LOGIN_REQUSET_CODE);
                }
                break;
            case R.id.tv_info:
                startActivity(new Intent(this, FilterActivity.class));
                break;
        }
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        if (isNetworkAvailable()) {
            getPresenter().getExchangeStatistics();
            getPresenter().getTotalSupplyAndDemand();
        } else {
            mSmartRefreshLayout.finishRefresh(false);
        }
    }

    @Override
    public void showExchangeStatistics(ExchangeStatistics data) {
        mWasteSupply.setText(data.getWaste_supply() + "万方");
        mWasteDemand.setText(data.getWaste_demand() + "万方");
        mAllSupply.setText(data.getMultipurpose_use_supply() + "万方");
        mALLDemand.setText(data.getMultipurpose_use_demand() + "万方");
    }

    @Override
    public void showExchangeData(List<ExchangeData> list) {
        if (list != null) {
            Timber.d("showExchangeData:" + list.size());
        }
        mSmartRefreshLayout.finishRefresh();
        if (mCurrentTabPosition == 0) {
            mSupplyInfoFragment.refreshData(list);
        } else {
            mDemandInfoFragment.refreshData(list);
        }
    }

    @Override
    public void showError(String error) {
        mSmartRefreshLayout.finishRefresh();
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }

    @Override
    public String getExchangeType() {
        return String.valueOf(mCurrentTabPosition);
    }

    @Override
    public boolean isActive() {
        return isActive;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == LOGIN_REQUSET_CODE) {
            startActivity(new Intent(this, MainActivity.class));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getPresenter().clear();
    }
}
