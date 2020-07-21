package com.android.commonlib.view.flycotablayout.utils;



import android.util.Log;

import com.apkfuns.logutils.LogUtils;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class FragmentChangeManager {
    private FragmentManager mFragmentManager;
    private int mContainerViewId;
    /** Fragment切换数组 */
    private ArrayList<Fragment> mFragments;
    /** 当前选中的Tab */
    private int mCurrentTab;

    public FragmentChangeManager(FragmentManager fm, int containerViewId, ArrayList<Fragment> fragments,int index) {
        this.mFragmentManager = fm;
        this.mContainerViewId = containerViewId;
        this.mFragments = fragments;
        initFragments(index);
    }
    /** 初始化fragments */
    private void initFragments(int index) {
        for (int i=0;i<mFragments.size();i++){
            Fragment fragment=mFragments.get(i);
            addFragment(mFragmentManager,fragment,fragment.toString());
        }
        setFragments(index);
    }

    private void addFragment(FragmentManager fm, Fragment fragment, String tag) {
        if (!fragment.isAdded() && null ==fm.findFragmentByTag(tag)) {
            FragmentTransaction ft = fm.beginTransaction();
            fm.executePendingTransactions();
            ft.add(mContainerViewId, fragment,tag);
            ft.commitAllowingStateLoss();
        }
    }

    /** 界面切换控制 */
    public void setFragments(int index) {
        for (int i = 0; i < mFragments.size(); i++) {
            FragmentTransaction ft = mFragmentManager.beginTransaction();
            Fragment fragment = mFragments.get(i);
            if (i == index) {
               //  ft.show(fragment);
                  ft.replace(mContainerViewId,fragment,fragment.getTag());
            } else {
                //  ft.hide(fragment);
                   ft.remove(fragment);
            }
          //  ft.commit();
            ft.commitAllowingStateLoss();
        }
        mCurrentTab = index;
    }

    public int getCurrentTab() {
        return mCurrentTab;
    }

    public Fragment getCurrentFragment() {
        return mFragments.get(mCurrentTab);
    }
}