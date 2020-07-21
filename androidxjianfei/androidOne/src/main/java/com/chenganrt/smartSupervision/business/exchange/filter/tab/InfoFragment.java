package com.chenganrt.smartSupervision.business.exchange.filter.tab;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chenganrt.smartSupervision.R;
import com.chenganrt.smartSupervision.business.exchange.detail.InfoDetailActivity;
import com.android.commonlib.okhttp.bean.ExchangeData;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import timber.log.Timber;

@SuppressLint("ValidFragment")
public class InfoFragment extends Fragment implements BaseQuickAdapter.OnItemClickListener {

    private RecyclerView mRecyclerView;
    private TextView mEmptyView;
    private InfoAdapter mInfoAdapter;
    private List<ExchangeData> mList = new ArrayList<>();

    public static InfoFragment getInstance() {
        return new InfoFragment();
    }

    public void refreshData(List<ExchangeData> list) {
        if (list != null && list.size() > 0) {
            mEmptyView.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
            if (mList.size() > 0) {
                mList.clear();
            }
            mList.addAll(list);
            mInfoAdapter.notifyDataSetChanged();
        } else {
            setEmptyView();
        }
    }

    public void addData(List<ExchangeData> list) {
        if (list != null && list.size() > 0) {
            mList.addAll(list);
            mInfoAdapter.notifyDataSetChanged();
        }
    }

    public void setEmptyView() {
        if (mList.size() > 0) {
            mList.clear();
        }
        mInfoAdapter.notifyDataSetChanged();
        mRecyclerView.setVisibility(View.GONE);
        mEmptyView.setVisibility(View.VISIBLE);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.exchange_supply_demand_layout, null);
        mRecyclerView = v.findViewById(R.id.exchange_recyclerView);
        mEmptyView = v.findViewById(R.id.no_info);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mInfoAdapter = new InfoAdapter(getContext(), R.layout.item_exchange, mList);
        mInfoAdapter.openLoadAnimation();
        mInfoAdapter.setOnItemClickListener(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mInfoAdapter);
    }

    @Override
    public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        view.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.press_anim));
        Timber.d("info id:" + mList.get(i).getId());
        Intent intent = new Intent(getActivity(), InfoDetailActivity.class);
        intent.putExtra(InfoDetailActivity.INFO_ID, mList.get(i).getId());
        startActivity(intent);
    }
}
