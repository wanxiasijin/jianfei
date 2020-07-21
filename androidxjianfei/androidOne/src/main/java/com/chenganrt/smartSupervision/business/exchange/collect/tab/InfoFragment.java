package com.chenganrt.smartSupervision.business.exchange.collect.tab;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.android.commonlib.okhttp.NetWorkDataManager;
import com.android.commonlib.okhttp.bean.Response;
import com.android.commonlib.rxjava.RxJava;
import com.android.commonlib.utils.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chenganrt.smartSupervision.R;
import com.chenganrt.smartSupervision.business.app.BaseApplication;
import com.chenganrt.smartSupervision.business.exchange.detail.InfoDetailActivity;
import com.chenganrt.smartSupervision.business.login.UserSP;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import timber.log.Timber;

@SuppressLint("ValidFragment")
public class InfoFragment extends Fragment implements BaseQuickAdapter.OnItemClickListener, InfoAdapter.OnCancelCollectClickListener {

    private RecyclerView mRecyclerView;
    private TextView mEmptyView;
    private InfoAdapter mInfoAdapter;
    private CollectData mExchangeData;
    private boolean isActive;
    private List<CollectData> mList = new ArrayList<>();

    public static InfoFragment getInstance() {
        return new InfoFragment();
    }

    public void refreshData(List<CollectData> list) {
        if (list.size() > 0) {
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

    public void addData(List<CollectData> list) {
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
        isActive = true;
        mInfoAdapter = new InfoAdapter(getContext(), R.layout.item_collected, mList);
        mInfoAdapter.openLoadAnimation();
        mInfoAdapter.setOnItemClickListener(this);
        mInfoAdapter.setCancelCollectClickListener(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mInfoAdapter);
    }

    @Override
    public void onCancelCollectClick(CollectData exchangeData) {
        mExchangeData = exchangeData;
        cancelCollect();
    }

    @Override
    public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        view.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.press_anim));
        Timber.d("info id:" + mList.get(i).getId());
        Intent intent = new Intent(getActivity(), InfoDetailActivity.class);
        intent.putExtra(InfoDetailActivity.INFO_ID, mList.get(i).getId());
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isActive = false;
    }

    private void cancelCollect() {
        RxJava.getInstance().create(new CancelCollectObservable(this), new RxJava.ObserveOnMainThread<Response>() {
            @Override
            public void accept(Response response) {
                if (isActive) {
                    Timber.d("cancelCollect:" + response.toString());
                    if (response.getStatus() == 200) {
                        mList.remove(mExchangeData);
                        List<CollectData> list = new ArrayList<>();
                        list.addAll(mList);
                        refreshData(list);
                    } else {
                        ToastUtils.showToast(BaseApplication.getApp(), response.getMessage());
                    }
                }
            }
        }, new RxJava.ThrowableOnMainThread() {
            @Override
            public void accept(String t) {
                if (isActive) {
                    ToastUtils.showToast(BaseApplication.getApp(), t);
                }
            }
        });
    }

    private static class CancelCollectObservable implements ObservableOnSubscribe<Response> {

        private WeakReference<InfoFragment> mWeakReference;

        public CancelCollectObservable(InfoFragment infoFragment) {
            mWeakReference = new WeakReference<>(infoFragment);
        }

        @Override
        public void subscribe(ObservableEmitter<Response> emitter) throws Exception {
            InfoFragment infoFragment = mWeakReference.get();
            if (infoFragment == null) {
                return;
            }
            try {
                Map<String, String> map = new LinkedHashMap<>();
                map.put("person_id", UserSP.getInstance().getUserId(infoFragment.getContext()));
                map.put("eid", infoFragment.mExchangeData.getId());
                Response response = NetWorkDataManager.cancelCollectedInfo(map);
                emitter.onNext(response);
            } catch (Exception e) {
                emitter.onError(e);
            }
            emitter.onComplete();
        }
    }
}
