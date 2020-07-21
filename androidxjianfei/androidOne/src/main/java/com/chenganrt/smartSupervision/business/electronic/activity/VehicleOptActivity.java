package com.chenganrt.smartSupervision.business.electronic.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;

import com.chenganrt.smartSupervision.R;
import com.chenganrt.smartSupervision.business.electronic.util.AppTools;
import com.chenganrt.smartSupervision.business.electronic.BillAction;
import com.chenganrt.smartSupervision.business.electronic.util.ToastUtil;
import com.chenganrt.smartSupervision.business.electronic.okhttp.UICallback;
import com.chenganrt.smartSupervision.business.electronic.parse.entiy.VehicleEntity;
import com.chenganrt.smartSupervision.business.electronic.ui.EditTextDel;
import com.chenganrt.smartSupervision.business.electronic.ui.adapter.VehicleNoAdapter;
import com.chenganrt.smartSupervision.business.login.UserSP;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Administrator on 2019/12/25.
 */

public class VehicleOptActivity extends BaseActivity implements VehicleNoAdapter.OnItemClickListener, OnRefreshListener, OnLoadMoreListener {

    private final int LOADING = 1, REFRESH = 0;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.smarfresh)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.ed_vehicle)
    EditTextDel edVehicle;

    private final String PageSizee = "20";
    private int pageNum = 1;
    private VehicleNoAdapter buildAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_vehicle_search);
        getSupportActionBar().hide();
        ButterKnife.bind(this);
        setSubTitle(R.string.vehicle_title);
        initView();
        smartRefreshLayout.autoRefresh();
    }

    private void initView() {
        smartRefreshLayout.setOnLoadMoreListener(this);
        smartRefreshLayout.setOnRefreshListener(this);
        smartRefreshLayout.setEnableOverScrollDrag(true);
        smartRefreshLayout.setEnableLoadMoreWhenContentNotFull(false);//内容不满一页时候启用加载更多

        ((ClassicsFooter) smartRefreshLayout.getRefreshFooter()).REFRESH_FOOTER_LOADING = getString(R.string.login_bottom);
        ((ClassicsFooter) smartRefreshLayout.getRefreshFooter()).REFRESH_FOOTER_FINISH = "";
        ((ClassicsFooter) smartRefreshLayout.getRefreshFooter()).REFRESH_FOOTER_NOTHING = getString(R.string.no_more_bottom);
        buildAdapter = new VehicleNoAdapter(getContext());
        buildAdapter.setOnItemClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(buildAdapter);

        findViewById(R.id.search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkNull())
                    AppTools.collapseSoftInputMethod(getActivity());
                smartRefreshLayout.autoRefresh();
            }
        });
    }

    private boolean checkNull() {
        if (TextUtils.isEmpty(edVehicle.getText().toString().trim())) {
            ToastUtil.showToast(getContext(), "请输入车牌号");
            return false;
        }
        return true;
    }

    private void searchVehicle(final int direction, String vehicleNo) {
        BillAction billAction = new BillAction();
        billAction.searchVehicleAction(UserSP.getInstance().getUserName(getContext()), vehicleNo, String.valueOf(pageNum), PageSizee,
                new UICallback(getActivity(), false) {
                    @Override
                    public void handleSuccess(Message msg) {
                        VehicleEntity vehicleEntity = (VehicleEntity) msg.obj;
                        if (vehicleEntity == null) return;
                        if (direction == REFRESH) {
                            buildAdapter.refresh(vehicleEntity.getVehicleEntities());
                            smartRefreshLayout.finishRefresh();
                        } else if (direction == LOADING) {
                            buildAdapter.addData(vehicleEntity.getVehicleEntities());
                            smartRefreshLayout.finishLoadMore();
                        }
                    }

                    @Override
                    public void handleFailure(Message msg) {
                        super.handleFailure(msg);
                        if (direction == REFRESH) {
                            smartRefreshLayout.finishRefresh();
                        } else if (direction == LOADING) {
                            smartRefreshLayout.finishLoadMore();
                        }
                    }
                });
    }

    @Override
    public void onItemClick(View view, VehicleEntity vehicleEntity) {
        setResult(RESULT_OK, new Intent().putExtra("vehicleNo", vehicleEntity.getVehicleNo()));
        finish();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        pageNum++;
        searchVehicle(LOADING, edVehicle.getText().toString().trim());
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        pageNum = 1;
        searchVehicle(REFRESH, edVehicle.getText().toString().trim());
    }
}
