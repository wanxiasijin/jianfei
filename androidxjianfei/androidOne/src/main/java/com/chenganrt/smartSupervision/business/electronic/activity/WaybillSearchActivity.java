package com.chenganrt.smartSupervision.business.electronic.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.chenganrt.smartSupervision.R;
import com.chenganrt.smartSupervision.business.electronic.util.AppTools;
import com.chenganrt.smartSupervision.business.electronic.BillAction;
import com.chenganrt.smartSupervision.business.electronic.ui.adapter.BillAllAdapter;
import com.chenganrt.smartSupervision.business.electronic.BillEntity;
import com.chenganrt.smartSupervision.business.electronic.RecycleItemDecor;
import com.chenganrt.smartSupervision.business.electronic.okhttp.UICallback;
import com.chenganrt.smartSupervision.business.electronic.ui.EditTextDel;
import com.chenganrt.smartSupervision.business.electronic.ui.PushEvent;
import com.chenganrt.smartSupervision.business.electronic.wheel.EditInputUtil;
import com.chenganrt.smartSupervision.business.login.UserSP;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;



public class WaybillSearchActivity extends BaseActivity implements BillAllAdapter.IClicItem, OnRefreshListener, OnLoadMoreListener {
    @BindView(R.id.et_account)
    EditTextDel mEdtAccount;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.view)
    View viewDrop;
    @BindView(R.id.smarfresh)
    SmartRefreshLayout smartRefreshLayout;
    private BillAllAdapter billAllAdapter;
    //private ICountDownTime countDown;
    private String bTime, eTime, signStatus;
    private final int REFRESH = 0, LOADING = 1;
    private int pageNum = 1;
    private final String size = "20";
    private final int REQUEST_CODE = 1001;

    public static Intent startAct(Activity activity, String title, String signStatus, String bDate, String eDate) {
        Intent intent = new Intent(activity, WaybillSearchActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("signStatus", signStatus);
        intent.putExtra("bDate", bDate);
        intent.putExtra("eDate", eDate);
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waybill_search);
        ButterKnife.bind(this);
        setSubTitle(getIntent().getStringExtra("title"));
        initView();
        initData();
        edWatch();
        smartRefreshLayout.autoRefresh();
    }


    @OnClick({R.id.cancel})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.cancel:
                AppTools.collapseSoftInputMethod(getActivity());
                finish();
                break;
        }
    }

    private void edWatch() {
        mEdtAccount.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    AppTools.collapseSoftInputMethod(getActivity());
                    smartRefreshLayout.autoRefresh();
                    return true;
                }
                return false;
            }
        });
    }

    private void initView() {
        smartRefreshLayout.setOnLoadMoreListener(this);
        smartRefreshLayout.setOnRefreshListener(this);
        smartRefreshLayout.setEnableOverScrollDrag(true);
        smartRefreshLayout.setEnableLoadMoreWhenContentNotFull(false);//内容不满一页时候启用加载更多

        EditInputUtil.makeEdtOnlyChinese(mEdtAccount);

        ((ClassicsFooter) smartRefreshLayout.getRefreshFooter()).REFRESH_FOOTER_LOADING = getString(R.string.login_bottom);
        ((ClassicsFooter) smartRefreshLayout.getRefreshFooter()).REFRESH_FOOTER_FINISH = "";
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return true;
            }
        };

        //countDown = new CountDown(1000, true);//倒计时
        billAllAdapter = new BillAllAdapter(getContext());
        billAllAdapter.setItemClick(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new RecycleItemDecor(20));
        recyclerView.setAdapter(billAllAdapter);
      //  countDown.bindRecyclerView(recyclerView);
        billAllAdapter.refresh(new ArrayList<BillEntity>());
    }

    private void initData() {
        bTime = getIntent().getStringExtra("bDate");
        eTime = getIntent().getStringExtra("eDate");
        signStatus = getIntent().getStringExtra("signStatus");
    }


    private void getSearchbill(final int direction, String userName, String userType, String ebillType, String signStatus, String vehicleNo, String beginDate, String endDate, String page, String size) {
        BillAction billAction = new BillAction();
        billAction.getAllbillAction(userName, userType, ebillType, signStatus, vehicleNo, beginDate, endDate, page, size, new UICallback(getActivity(), false) {
            @Override
            public void handleSuccess(Message msg) {
                BillEntity billEntity = (BillEntity) msg.obj;
                if (billEntity == null) return;
                List<BillEntity> billEntityList = billEntity.getBillEntities();
                if (direction == REFRESH) {
                    smartRefreshLayout.finishRefresh();
                    billAllAdapter.refresh(billEntityList);
                } else if (direction == LOADING) {
                    smartRefreshLayout.finishLoadMore();
                    billAllAdapter.addData(billEntityList);
                }
            }

            @Override
            public void handleFailure(Message msg) {
                super.handleFailure(msg);
                smartRefreshLayout.finishRefresh();
                smartRefreshLayout.finishLoadMore();
                if (REFRESH == direction)
                    billAllAdapter.refresh(new ArrayList<BillEntity>());
            }
        });
    }


    @Override
    public void selectedItem(BillEntity billEntity, int position, int type) {
        if (billEntity == null) return;
        startActivityForResult(BillDetailActivity.startAct(getActivity(), billEntity.getOrderId(), billEntity.getSignStatus()), REQUEST_CODE);//只有工地施工单位监理员并具运单为待确认状态时才会进入修改订单
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            EventBus.getDefault().post(new PushEvent("receiver"));
            smartRefreshLayout.autoRefresh();
        }
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        pageNum++;
        getSearchbill(LOADING,
                UserSP.getInstance().getUserName(getContext()),
                String.valueOf(UserSP.getInstance().getUserType(getContext())),
                "2",
                signStatus,
                mEdtAccount.getText().toString().trim(),
                bTime,
                eTime,
                String.valueOf(pageNum),
                size);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        pageNum = 1;
        getSearchbill(REFRESH,
                UserSP.getInstance().getUserName(getContext()),
                String.valueOf(UserSP.getInstance().getUserType(getContext())),
                "2",
                signStatus,
                mEdtAccount.getText().toString().trim(),
                bTime,
                eTime,
                String.valueOf(pageNum),
                size);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
       // if (countDown != null) countDown.deleteObservers();
    }


}
