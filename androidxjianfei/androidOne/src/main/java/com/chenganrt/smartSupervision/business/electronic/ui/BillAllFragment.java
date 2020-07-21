package com.chenganrt.smartSupervision.business.electronic.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.chenganrt.smartSupervision.R;

import com.chenganrt.smartSupervision.business.electronic.activity.TrackShowActivity;
import com.chenganrt.smartSupervision.business.electronic.util.AppConstant;
import com.chenganrt.smartSupervision.business.electronic.bill.BaseFragment;
import com.chenganrt.smartSupervision.business.electronic.BillAction;
import com.chenganrt.smartSupervision.business.electronic.ui.adapter.BillAllAdapter;
import com.chenganrt.smartSupervision.business.electronic.BillEntity;
import com.chenganrt.smartSupervision.business.electronic.RecycleItemDecor;
import com.chenganrt.smartSupervision.business.electronic.RemovieEvent;
import com.chenganrt.smartSupervision.business.electronic.util.ToastUtil;
import com.chenganrt.smartSupervision.business.electronic.okhttp.UICallback;
import com.chenganrt.smartSupervision.business.electronic.util.UserUtil;
import com.chenganrt.smartSupervision.business.electronic.activity.BillDetailActivity;
import com.chenganrt.smartSupervision.business.login.UserSP;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/5/20.
 */

public class BillAllFragment extends BaseFragment implements BillAllAdapter.IClicItem, OnRefreshListener, OnLoadMoreListener {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.smarfresh)
    SmartRefreshLayout smartRefreshLayout;
    private final int REQUEST_CODE = 1000;
    private BillAllAdapter billAllAdapter;
   // private ICountDownTime countDown;
    private int pageNum = 1;
    private final String size = "10";
    private final int REFRESH = 0, LOADING = 1;
    private int position = 0;
    private String vehicleNo;
    public final static String KEY_PAGE_INDEX = "pageIndex";//页面索引
    private int pagePosition;//当前页面的索引
    private boolean isFresh = true;

    public static BillAllFragment getInstance(int position) {
        BillAllFragment billAllFragment = new BillAllFragment();
        Bundle budle = new Bundle();
        budle.putInt(KEY_PAGE_INDEX, position);
        billAllFragment.setArguments(budle);
        return billAllFragment;
    }

    private INotice interfaceNotece;

    public interface INotice {
        void noticeRefresh(int position);

        void noticeTitle(int position, int size);
    }

    public void setInterfaceNotece(INotice interfaceNotece) {
        this.interfaceNotece = interfaceNotece;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pagePosition = null != getArguments() ? getArguments().getInt(KEY_PAGE_INDEX) : 0;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_unfnished, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        initViwe();
        recyclerViewTouchListener();
    }


    private void initViwe() {
        smartRefreshLayout.setOnLoadMoreListener(this);
        smartRefreshLayout.setOnRefreshListener(this);
        smartRefreshLayout.setEnableOverScrollDrag(true);
        smartRefreshLayout.setEnableLoadMoreWhenContentNotFull(false);//内容不满一页时候启用加载更多

        ((ClassicsFooter) smartRefreshLayout.getRefreshFooter()).REFRESH_FOOTER_LOADING = getString(R.string.login_bottom);
        ((ClassicsFooter) smartRefreshLayout.getRefreshFooter()).REFRESH_FOOTER_FINISH = "";
        ((ClassicsFooter) smartRefreshLayout.getRefreshFooter()).REFRESH_FOOTER_NOTHING = getString(R.string.no_more_bottom);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return true;
            }
        };

    //    countDown = new CountDown(100, true);//倒计时

        billAllAdapter = new BillAllAdapter(getContext());
        billAllAdapter.setItemClick(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new RecycleItemDecor(20));
        recyclerView.setAdapter(billAllAdapter);
      //  countDown.bindRecyclerView(recyclerView);

        billAllAdapter.refresh(new ArrayList<BillEntity>());

        if ("1".equals(UserSP.getInstance().getUserType(getContext())))
            smartRefreshLayout.setEnableLoadMore(false);
    }


    public void refreshData(int position, String vehicleNo) {
        if (!isFresh) return;
        this.position = position;
        this.vehicleNo = vehicleNo;
        if (smartRefreshLayout != null && position == pagePosition)
            smartRefreshLayout.autoRefresh();
    }

    private String getOrderType() {
        if ("1".equals(UserSP.getInstance().getUserType(getContext()))) {
            return position == 0 ? "1" : "0";
        } else {
            return getPosType();
        }
    }

    private String getPosType() {
        if ("4".equals(UserSP.getInstance().getUserType(getContext()))) {
            if (position == 0) return "0";
            if (position == 1) return "1";
            if (position == 2) return "2";
            if (position == 3) return "4";
            if (position == 4)
                return "1".equals(UserUtil.getInstance().getAutoConfirm(getContext())) ? "5" : "3";
        } else {
            if (position == 0) return "0";
            if (position == 1) return "1";
            if (position == 2) return "2";
            if (position == 3)
                return "1".equals(UserUtil.getInstance().getAutoConfirm(getContext())) ? "5" : "3";
        }

        return "";
    }

    private void recyclerViewTouchListener() {
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        isFresh = false;
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        isFresh = true;
                        break;
                }
                return false;
            }
        });
    }

    private void getAllbill(final int direction, String userName, String userType, String ebillType, String signStatus, String vehicleNo, String page, String size) {
        BillAction billAction = new BillAction();
        billAction.getAllbillAction(userName, userType, ebillType, signStatus, vehicleNo, "", "", page, size, new UICallback(getActivity(), false) {
            @Override
            public void handleSuccess(Message msg) {
                BillEntity billEntity = (BillEntity) msg.obj;
                if (billEntity == null) return;
                List<BillEntity> billEntityList = billEntity.getBillEntities();
                if (interfaceNotece != null)
                    interfaceNotece.noticeRefresh(position);
                if (direction == REFRESH) {
                    smartRefreshLayout.finishRefresh();
                    billAllAdapter.refresh(billEntityList);
                } else if (direction == LOADING) {
                    smartRefreshLayout.finishLoadMore();
                    billAllAdapter.addData(billEntityList);
                    if (AppConstant.RESULT_CODE_EMPTY.equals(billEntity.getErrorCode())) {
                        ToastUtil.showToast(getContext(), R.string.no_more_data);
                        smartRefreshLayout.finishLoadMoreWithNoMoreData();
                    }
                }
            }

            @Override
            public void handleFailure(Message msg) {
                super.handleFailure(msg);
                smartRefreshLayout.finishLoadMore();
                if (REFRESH == direction) {
                    smartRefreshLayout.finishRefresh();
                    billAllAdapter.refresh(new ArrayList<BillEntity>());
                } else if (LOADING == direction) {
                    smartRefreshLayout.finishLoadMore();
                }
            }
        });
    }


    /**
     * 司机端
     *
     * @param direction
     * @param userId
     * @param ebillType
     * @param userType
     */
    private void getAllDriverbill(final int direction, String userId, String ebillType, String userType, String OrderID) {
        BillAction billAction = new BillAction();
        billAction.getAllDriverbillAction(userId, ebillType, userType, OrderID, new UICallback(getActivity(), false) {
            @Override
            public void handleSuccess(Message msg) {
                BillEntity billEntity = (BillEntity) msg.obj;
                if (billEntity == null) return;
                List<BillEntity> billEntityList = billEntity.getBillEntities();
                if (interfaceNotece != null)
                    interfaceNotece.noticeTitle(position, billEntityList == null ? 0 : billEntityList.size());
                if (direction == REFRESH) {
                    smartRefreshLayout.finishRefresh();
                    billAllAdapter.refresh(billEntityList);
                } else if (direction == LOADING) {
                    smartRefreshLayout.finishLoadMore();
                    billAllAdapter.addData(billEntityList);
                    if (AppConstant.RESULT_CODE_EMPTY.equals(billEntity.getErrorCode())) {
                        ToastUtil.showToast(getContext(), R.string.no_more_data);
                        smartRefreshLayout.finishLoadMoreWithNoMoreData();
                    }
                }
            }

            @Override
            public void handleFailure(Message msg) {
                super.handleFailure(msg);
                smartRefreshLayout.finishLoadMore();
                if (REFRESH == direction) {
                    smartRefreshLayout.finishRefresh();
                    billAllAdapter.refresh(new ArrayList<BillEntity>());
                } else if (LOADING == direction) {
                    smartRefreshLayout.finishLoadMore();
                }
            }
        });
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        pageNum++;
        requeryData(LOADING, String.valueOf(UserSP.getInstance().getUserType(getContext())));
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        pageNum = 1;
        requeryData(REFRESH, String.valueOf(UserSP.getInstance().getUserType(getContext())));

    }

    private void requeryData(int direction, String userType) {
        if ("1".equals(userType)) {
            getAllDriverbill(direction,
                    UserSP.getInstance().getUserId(getContext()),
                    getOrderType(),
                    String.valueOf(UserSP.getInstance().getUserType(getContext())),
                    vehicleNo);
        } else {
            getAllbill(direction,
                    UserSP.getInstance().getUserName(getContext()),
                    String.valueOf(UserSP.getInstance().getUserType(getContext())),
                    "1",
                    getOrderType(),
                    vehicleNo,
                    String.valueOf(pageNum),
                    size);
        }
    }

    @Override
    public void selectedItem(BillEntity billEntity, int position, int type) {
        if (billEntity == null) return;
        if (type == 0) {
            if (!TextUtils.isEmpty(billEntity.getOver()) && billEntity.getOver().equals("1")) return;
            startActivityForResult(BillDetailActivity.startAct(getActivity(), billEntity.getOrderId(), billEntity.getSignStatus()), REQUEST_CODE);//只有工地施工单位监理员并具运单为待确认状态时才会进入修改订单
        } else {
            startActivity(new Intent(TrackShowActivity.startAct(getActivity(), billEntity.getOrderId())));
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void removieEventBus(RemovieEvent removieEvent) {
        if (removieEvent == null) return;
        //billAllAdapter.notifyItem(removieEvent.getPosition());
        billAllAdapter.removeItem(removieEvent.getPosition());
        /*if (billAllAdapter != null && recyclerView != null) {
            if (recyclerView.getScrollState() == RecyclerView.SCROLL_STATE_IDLE
                    && !recyclerView.isComputingLayout()) {
                billAllAdapter.notifyItem(removieEvent.getPosition());
            }
        }*/
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == getActivity().RESULT_OK) {
            if (smartRefreshLayout != null)
                smartRefreshLayout.autoRefresh();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
       // if (countDown != null) countDown.deleteObservers();
    }
}
