package com.chenganrt.smartSupervision.business.exchange.publish;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.android.commonlib.mvp.BaseActivity;
import com.android.commonlib.mvp.BasePresenter;
import com.android.commonlib.okhttp.bean.DetailData;
import com.chenganrt.smartSupervision.business.home.Tab;
import com.flyco.tablayout.CommonTabLayout;
import com.chenganrt.smartSupervision.R;
import com.android.commonlib.view.processdialog.HProgressDialog;
import com.chenganrt.smartSupervision.business.exchange.publish.tab.InfoFragment;
import com.chenganrt.smartSupervision.business.home.TabEntity;

import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.OnClick;
import timber.log.Timber;

public class PublishActivity  extends BaseActivity {
    public static final String EXCHANGE_DATA = "exchange_data";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.titleSub)
    TextView titleSub;
    @BindView(R.id.common_tab_layout)
    CommonTabLayout mCommonTabLayout;
    private InfoFragment mInfoFragment1;
    private InfoFragment mInfoFragment2;
    private int mCurrentTabPosition = 0;
    private boolean isEdit;

    @Override
    public int initLayout() {
        return R.layout.activity_publish;
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    public void initConfig() {
        setStatusBar(toolbar);
        titleSub.setText("发布信息");
    }

    @Override
    public void initData() {
        String[] mTitles = {"供应信息", "需求信息"};
        ArrayList<Fragment> mFragments = new ArrayList<>();
        ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
        mInfoFragment1 = InfoFragment.getInstance();
        mFragments.add(mInfoFragment1);
        mTabEntities.add(new Tab(mTitles[0], 0, 0));
        mInfoFragment2 = InfoFragment.getInstance();
        mFragments.add(mInfoFragment2);
        mTabEntities.add(new Tab(mTitles[1], 0, 0));
        mCommonTabLayout.setTabData(mTabEntities, this, R.id.fl_change, mFragments);
        mCommonTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                Timber.d("onTabSelect:" + position);
                mCurrentTabPosition = position;
                if (position == 1) {
                    mInfoFragment2.setExchangeType(position);
                } else {
                    mInfoFragment1.setExchangeType(position);
                }
            }

            @Override
            public void onTabReselect(int position) {
                Timber.d("onTabReselect:" + position);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        initExchangeData();
    }

    private void initExchangeData() {
        Intent intent = getIntent();
        if (intent != null) {
            DetailData exchangeData = (DetailData) intent.getSerializableExtra(EXCHANGE_DATA);
            if (exchangeData != null) {
                Timber.d("edit exchangeData:" + exchangeData.toString());
                isEdit = true;
                mCommonTabLayout.setCurrentTab(Integer.parseInt(exchangeData.getExchange_type()));
                if (exchangeData.getExchange_type().equals("0")) {
                    mInfoFragment1.showExchangeDetail(exchangeData);
                } else {
                    mInfoFragment2.showExchangeDetail(exchangeData);
                }
            }
        }
    }

    @OnClick({R.id.tv_cancel, R.id.btn_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel:
                onBackPressed();
                break;
            case R.id.btn_submit:
                if (isNetworkAvailable()) {
                    if (mCurrentTabPosition == 0) {
                        mInfoFragment1.publish(isEdit);
                    } else {
                        mInfoFragment2.publish(isEdit);
                    }
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<LocalMedia> images;
        if (resultCode == RESULT_OK) {
            if (requestCode == PictureConfig.CHOOSE_REQUEST) {// 图片选择结果回调
                images = PictureSelector.obtainMultipleResult(data);
                if (mCurrentTabPosition == 0) {
                    mInfoFragment1.showSelectedImages(images);
                } else {
                    mInfoFragment2.showSelectedImages(images);
                }
            }
        }
    }
}