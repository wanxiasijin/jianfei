package com.chenganrt.smartSupervision.business.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chenganrt.smartSupervision.R;
import com.chenganrt.smartSupervision.business.exchange.detail.InfoDetailActivity;
import com.chenganrt.smartSupervision.business.home.adapter.EarthworkExchangeAdapter;
import com.chenganrt.smartSupervision.business.login.UserSP;
import com.chenganrt.smartSupervision.common.okhttputil.CallBackUtil;
import com.chenganrt.smartSupervision.common.okhttputil.HttpConstant;
import com.chenganrt.smartSupervision.common.okhttputil.OkhttpUtil;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.Call;


public class EarthmenExchangeMessagesFragment extends Fragment implements OnRefreshListener, OnLoadMoreListener {
    private RecyclerView mRecyclerView;
    private EarthworkExchangeAdapter mEarthworkExchangeAdapter;
    private LinearLayoutManager mLayoutManager;
    private List<MatchMessageEntity> mMatchMessageEntity1List;
    private SmartRefreshLayout mRefreshLayout;
    private final int REFRESH = 0, LOADMORE = 1;
    private int page = 1;
    private Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.earthmen_messages_layout, null);
        mRecyclerView=view.findViewById(R.id.recyclerview);
        mContext=getActivity();

        mRefreshLayout =view.findViewById(R.id.refreshLayout);

        mRefreshLayout.setEnableRefresh(true);//是否启用下拉刷新功能
        mRefreshLayout.setEnableLoadMore(true);//是否启用上拉加载功能

        mLayoutManager = new LinearLayoutManager(mContext);
        mLayoutManager.setOrientation(RecyclerView.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mEarthworkExchangeAdapter =new EarthworkExchangeAdapter(mContext);
        mRecyclerView.setAdapter(mEarthworkExchangeAdapter);
        mEarthworkExchangeAdapter.setOnItemClickListener(new EarthworkExchangeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, MatchMessageEntity vehicleEntity, int position,String id) {
                Intent intent = new Intent(getActivity(), InfoDetailActivity.class);
                intent.putExtra(InfoDetailActivity.INFO_ID,id);
                startActivity(intent);
            }
        });
        getMessageDetail(REFRESH,"0");
        return view;
    }
    private void getMessageDetail(final int direction, String type) {
        String url = HttpConstant.baseUrl + "/sys/ssitgNoticeMsg/publishList";
        Map<String,String> map=new HashMap<>();
        map.put("user_id", UserSP.getInstance().getUserId(mContext));
        map.put("current",page+"");
        map.put("size", "10");
        map.put("type",type);
        OkhttpUtil.okHttpPostJson(getActivity(),url,new Gson().toJson(map),new CallBackUtil.CallBackString() {
            @Override
            public void onFailure(Call call, Exception e) {
                if (direction == REFRESH) {
                    mRefreshLayout.finishRefresh();
                  //  mMatchMessageAdapter.refresh(null, new ArrayList<PieEntry>(), 0);
                    mEarthworkExchangeAdapter.refresh(null);
                } else if (direction == LOADMORE) {
                    mRefreshLayout.finishLoadMore();
                }

            }
            @Override
            public void onResponse(String response) {
                List<ContentEntity> mList = new ArrayList<>();
                try {
                    mMatchMessageEntity1List = MatchMessageEntiyParser.parse(response);
                    if (mMatchMessageEntity1List == null) return;
                    if (direction == REFRESH) {
                        mRefreshLayout.finishRefresh();
                        mEarthworkExchangeAdapter.refresh(mMatchMessageEntity1List);
                    } else if (direction == LOADMORE) {
                        List<MatchMessageEntity> moreList = MatchMessageEntiyParser.parse(response);
                        mRefreshLayout.finishLoadMore();
                        mEarthworkExchangeAdapter.addData(moreList);
//                    if (AppConstant.RESULT_CODE_EMPTY.equals(detailEntity.getErrorCode())) {
//                        ToastUtil.showToast(getContext(), R.string.no_more_data);
//                    }
                        //判断是否为没有更多数据了
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        page++;
        getMessageDetail(LOADMORE,"0");
    }
    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        page=1;
        getMessageDetail(REFRESH,"0");
    }
}