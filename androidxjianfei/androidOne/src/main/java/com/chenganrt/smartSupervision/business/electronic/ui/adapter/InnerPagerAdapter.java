package com.chenganrt.smartSupervision.business.electronic.ui.adapter;

import android.view.ViewGroup;

import com.chenganrt.smartSupervision.business.electronic.ui.BillAllFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


/**
 * Created by Administrator on 2019/4/15.
 */

public class InnerPagerAdapter extends FragmentPagerAdapter {

    private String[] titleArray;
    private List<String> tags = new ArrayList<>();
    private FragmentManager fm;

    private IFadapter iFadapter;

    public interface IFadapter {
        void getPageFragment(BillAllFragment billAllFragment);
    }

    public void setiFadapter(IFadapter iFadapter) {
        this.iFadapter = iFadapter;
    }

    public InnerPagerAdapter(FragmentManager fm, String[] titleArray) {
        super(fm);
        this.fm = fm;
        this.titleArray = titleArray;
    }

    @Override
    public Fragment getItem(int position) {
        return BillAllFragment.getInstance(position);
    }

    @Override
    public int getCount() {
        return titleArray.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titleArray[position];
    }

    @Override
    public Object instantiateItem(ViewGroup container, int pageIndex) {
        String fragmentNameTag = makeFragmentName(container.getId(), pageIndex);//创建tag并存储到集合中
        if (!tags.contains(fragmentNameTag)) {
            tags.add(fragmentNameTag);
        }
        return super.instantiateItem(container, pageIndex);
    }

    private static String makeFragmentName(int viewId, long id) {
        return "android:switcher:" + viewId + ":" + id;
    }

    public void refreshPageData(int position,String vehicleNo) {
        for (int i = 0; i < tags.size(); i++) {
            BillAllFragment billAllFragment = (BillAllFragment) fm.findFragmentByTag(tags.get(i));
            if (iFadapter != null) iFadapter.getPageFragment(billAllFragment);
            billAllFragment.refreshData(position,vehicleNo);
        }
    }

}
