/*
 * Copyright (c) 2020. All rights reserved.
 * Created by wanlc on 20-7-10 下午4:24
 */

package com.chenganrt.smartSupervision.business.main;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import com.chenganrt.smartSupervision.R;
import com.chenganrt.smartSupervision.business.home.HomeFragment;
import com.chenganrt.smartSupervision.business.personal.PersonalFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.LabelVisibilityMode;

public class MainAppActivity extends AppCompatActivity {

    private Fragment[] mFragments;
    private int mLastIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        HomeFragment mHomeFragment = new HomeFragment();
        ElectornicMainFragment mDashboardFragment = new ElectornicMainFragment();
        ExchangeMianFragment mMessageFragment = new ExchangeMianFragment();
        PersonalFragment mPersonalFragment = new PersonalFragment();

        mFragments = new Fragment[]{mHomeFragment, mDashboardFragment, mMessageFragment, mPersonalFragment};
        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, mHomeFragment).show(mHomeFragment).commit();

        BottomNavigationView mBottomNavigationView = findViewById(R.id.nav_view);
        mBottomNavigationView.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);//修复4个及以上item文字不显示问题
        mBottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    if (mLastIndex != 0) {
                        switchFragment(mLastIndex, 0);
                        mLastIndex = 0;
                    }
                    return true;
                case R.id.navigation_dashboard:
                    if (mLastIndex != 1) {
                        switchFragment(mLastIndex, 1);
                        mLastIndex = 1;
                    }
                    return true;
                case R.id.navigation_notifications:
                    if (mLastIndex != 2) {
                        switchFragment(mLastIndex, 2);
                        mLastIndex = 2;
                    }
                    return true;
                case R.id.navigation_personal:
                    if (mLastIndex != 3) {
                        switchFragment(mLastIndex, 3);
                        mLastIndex = 3;
                    }
                    return true;
                default:
                    break;
            }
            return false;
        }
    };

    /**
     *切换fragment
     */
    private void switchFragment(int lastFragment, int index) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.hide(mFragments[lastFragment]);
        if (!mFragments[index].isAdded()) {
            transaction.add(R.id.nav_host_fragment, mFragments[index]);
        }
        transaction.show(mFragments[index]).commitAllowingStateLoss();
    }

}
