package com.chenganrt.smartSupervision.business.electronic.bill;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.chenganrt.smartSupervision.R;
import com.chenganrt.smartSupervision.business.electronic.BillAction;
import com.chenganrt.smartSupervision.business.electronic.BillEntity;
import com.chenganrt.smartSupervision.business.electronic.util.BillStatusUtil;
import com.chenganrt.smartSupervision.business.electronic.okhttp.UICallback;
import com.chenganrt.smartSupervision.business.electronic.activity.BillExcepActivity;
import com.chenganrt.smartSupervision.business.electronic.wheel.FNormalDialog;
import com.chenganrt.smartSupervision.business.electronic.wheel.PopWindow;
import com.chenganrt.smartSupervision.business.login.UserSP;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


import static android.app.Activity.RESULT_OK;
import static com.chenganrt.smartSupervision.business.electronic.activity.BillExcepActivity.REQUEST_CODE_EXCEPTION;

/**
 * Created by Administrator on 2019/6/11.
 */

public class DriverBillFragment extends BaseFragment {
    @BindView(R.id.btn_submit)
    TextView mBtnSubmit;
    @BindView(R.id.tv_waybill_number)
    TextView mTvWaybill_number;
    @BindView(R.id.tv_out_time)
    TextView mTvOut_time;
    @BindView(R.id.tv_status)
    TextView mTvStatus;
    @BindView(R.id.tv_waste_types)
    TextView mTvWaste_types;
    @BindView(R.id.tv_loading_amount)
    TextView mTvLoading_amount;
    @BindView(R.id.tv_number_plate2)
    TextView mTvNumber_plate;
    @BindView(R.id.tv_start_address)
    TextView mTvStart_address;
    @BindView(R.id.tv_end_address)
    TextView mTvEnd_address;
    @BindView(R.id.tv_project_name)
    TextView mTvProject_name;
    @BindView(R.id.tv_project_address)
    TextView mTvProject_address;
    @BindView(R.id.tv_fency_name)
    TextView tvFecnyName;
    @BindView(R.id.tv_build_unit)
    TextView mTvBuild_unit;
    @BindView(R.id.tv_supervision_unit)
    TextView mTvSupervision_unit;
    @BindView(R.id.tv_construction_unit)
    TextView mTvConstruction_unit;
    @BindView(R.id.ll_btn)
    LinearLayout mLiBtn;
    @BindView(R.id.tv_project)
    TextView tvProject;
    @BindView(R.id.layout_abandon)
    LinearLayout layout_abandon;

    private BillEntity billEntitys;
    private String orderID;
    private String orderStatus;
    private String carNo;
    private String billStatus;

    private IDriver iDriver;

    public interface IDriver {
        void driverSubmit();
    }

    public void setiDriver(IDriver iDriver) {
        this.iDriver = iDriver;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        initData();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_driver_detail, container, false);
    }

    @OnClick({R.id.btn_submit, R.id.tv_exception, R.id.tv_number_plate2})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.btn_submit:
                if (billEntitys == null) return;
                showTipDialog(getTips(), "11".equals(orderStatus) ? getString(R.string.bill_detail_subupTip) : "");
                break;
            case R.id.tv_exception:
                startActivityForResult(BillExcepActivity.startAct(getActivity(), orderID), REQUEST_CODE_EXCEPTION);
                break;
            case R.id.tv_number_plate2:
                if (!TextUtils.isEmpty(billStatus))
                    showPopwindow(mTvNumber_plate, billStatus);
                break;
        }
    }

    private void initData() {
        tvFecnyName.setVisibility(View.GONE);
        layout_abandon.setVisibility(View.GONE);
        tvProject.setText("工程信息");
        orderID = getActivity().getIntent().getStringExtra("orderId");//运单编号
        //orderStatus = getActivity().getIntent().getStringExtra("orderStatus");//运单状态
    }

    public void getData() {
        getDetails(UserSP.getInstance().getUserId(getContext()), orderID, String.valueOf(UserSP.getInstance().getUserType(getContext())));
    }

    private void showCancelDialog(String carNo) {
        FNormalDialog.Builder builder1 = FNormalDialog.createNormalDialog(getActivity());
        builder1.setOnItemChooseListener(new FNormalDialog.OnItemChooseListener() {
            @Override
            public void onNegativeListener(Dialog dialog) {
                //dialog.dismiss();
            }

            @Override
            public void onPositiveListener(Dialog dialog) {
                dialog.dismiss();
                //landUp(orderID);
                billOperate(orderID, "2");
            }
        });
        builder1.show();
        builder1.setTitles(String.format(getString(R.string.bill_detail_submitTip), carNo));
        builder1.setContents(getString(R.string.bill_detail_subupTip));
        builder1.setNegativeText(getString(R.string.no));
        builder1.setPositiveText(getString(R.string.yes));
    }

    private void showPopwindow(View v, String type) {
        PopWindow p = new PopWindow();
        View contentView = LayoutInflater.from(getContext()).inflate(R.layout.popwindow_tip,
                null, false);
        TextView textView = (TextView) contentView.findViewById(R.id.txt);
        if (!TextUtils.isEmpty(type) && type.equals("0")) textView.setText("未申报");
        if (!TextUtils.isEmpty(type) && type.equals("1")) textView.setText("已申报");
        p.setPopView(getContext(), v, contentView);
        p.showPopWindow(v, -80, 2);
        p.dismissPopWindow(new PopWindow.IPopwindow() {
            @Override
            public void popDismiss(PopupWindow popupWindow) {
            }
        });
    }

    /**
     * 运单提交
     * 司机状态：11待确认、12待出场、15待处置、21已完成
     * 工地施工单位监理员：12待确认、15待签认、14已签认
     * 工地监理单位监理员：12待确认、15待签认、13已签认
     * 处置场处理员：15待确认、21待签认、22已签认
     * //改成
     * /*司机端：11待确认、12待出场、15待处置、21已完成
     * 工地施工单位监理员：12待确认、17待签认、14已签认
     * 工地监理单位监理员：12待确认、17待签认、13已签认
     * 处置场处理员：15待确认、17待签认、22已签认
     */
    private String getTips() {
        if ("11".equals(orderStatus)) {//待确认 确认运单信息
            return "确认并提交运单信息?";
        } else if ("12".equals(orderStatus)) {//待出场
            return "车辆号" + carNo + "车辆是否确认出场?";
        } else if ("15".equals(orderStatus)) {//待处置
            return "确认车辆(" + carNo + ")已进入处置场?";
        }
        return "确认并提交联单信息?";
    }

    /*private void setBottomTex(String usetType, String orderStatus) {
        if ("1".equals(usetType)) {//司机端
            if ("11".equals(orderStatus)) {//待确认
                mBtnSubmit.setText("确认运单信息");
            } else if ("12".equals(orderStatus)) {//待出场
                mBtnSubmit.setText("出场确认");
            } else if ("15".equals(orderStatus)) {//待处置
                mBtnSubmit.setText("确认处置");
            } else {//已完成//已完成时,没有异常上报和确认按扭
                mLiBtn.setVisibility(View.GONE);
            }
        } else if ("2".equals(UserUtil.getInstance().getUserType(getContext()))) {//施工单位管理员
            tvCancel.setVisibility(View.GONE);
            if ("12".equals(orderStatus)) {//待确认
                mBtnSubmit.setText("确认运单信息");
            } else if ("17".equals(orderStatus)) {//待签认
                mBtnSubmit.setText("出场确认");
            } else if ("14".equals(orderStatus)) {//已签认//已完成时,没有异常上报和确认按扭
                mLiBtn.setVisibility(View.GONE);
            }
        } else if ("3".equals(UserUtil.getInstance().getUserType(getContext()))) {//工地理监单位监理员
            tvCancel.setVisibility(View.GONE);
            if ("12".equals(orderStatus)) {//待确认
                mBtnSubmit.setText("确认运单信息");
            } else if ("17".equals(orderStatus)) {//待签认
                mBtnSubmit.setText("出场确认");
            } else if ("13".equals(orderStatus)) {//已签认
                mLiBtn.setVisibility(View.GONE);
            }
        } else if ("4".equals(UserUtil.getInstance().getUserType(getContext()))) {
            if ("15".equals(orderStatus)) {
                tvCancel.setVisibility(View.VISIBLE);
                mBtnSubmit.setText("确认运单信息");
            } else if ("17".equals(orderStatus)) {
                mBtnSubmit.setText("入场确认");
            } else if ("22".equals(orderStatus)) {
                mLiBtn.setVisibility(View.GONE);
            }
        } else {//处置场处理员
            tvCancel.setVisibility(View.GONE);
            if ("15".equals(orderStatus)) {//待确认
                mBtnSubmit.setText("确认运单信息");
            } else if ("17".equals(orderStatus)) {//待签认
                mBtnSubmit.setText("入场确认");
            } else if ("22".equals(orderStatus)) {//已签认
                mLiBtn.setVisibility(View.GONE);
            }
        }
    }*/

    private void setBottomBar(String status) {
        mLiBtn.setVisibility(TextUtils.isEmpty(status) ? View.GONE : "21".equals(status) ? View.GONE : View.VISIBLE);
        if ("11".equals(status)) {//待确认
            mBtnSubmit.setText("确认联单信息");
        } else if ("12".equals(status)) {//待出场
            mBtnSubmit.setText("出场确认");
        } else if ("15".equals(status)) {//待处置
            mBtnSubmit.setText("确认处置");
        }
    }

    private void setIcon(String status) {
        if (TextUtils.isEmpty(status)) return;
        if ("1".equals(status))
            mTvNumber_plate.setCompoundDrawablesWithIntrinsicBounds(R.drawable.checked,
                    0, 0, 0);
        if ("0".equals(status))
            mTvNumber_plate.setCompoundDrawablesWithIntrinsicBounds(R.drawable.uncheck,
                    0, 0, 0);
    }

    private void showTipDialog(String msg, String content) {
        FNormalDialog.Builder builder1 = FNormalDialog.createNormalDialog(getActivity());
        builder1.setOnItemChooseListener(new FNormalDialog.OnItemChooseListener() {
            @Override
            public void onNegativeListener(Dialog dialog) {
                //dialog.dismiss();
            }

            @Override
            public void onPositiveListener(Dialog dialog) {
                dialog.dismiss();
                billOperate(orderID, orderStatus);
            }
        });
        builder1.show();
        builder1.setTitles(msg);
        builder1.setContents(content);
        builder1.setNegativeText(getString(R.string.no));
        builder1.setPositiveText(getString(R.string.yes));

    }


    private void billOperate(String orderId, String ebillType) {
        BillAction billAction = new BillAction();
        billAction.billDriverOperateAction(orderId, UserSP.getInstance().getUserId(getContext()), ebillType, String.valueOf(UserSP.getInstance().getUserType(getContext())), new UICallback(getActivity(), true) {
            @Override
            public void handleSuccess(Message msg) {
                if (iDriver != null) iDriver.driverSubmit();
            }
        });

    }

    private void getDetails(String UserID, String OrderID, final String UserType) {
        BillAction billAction = new BillAction();
        billAction.orderDriverDetailAction(OrderID, UserID, UserType, new UICallback(getActivity(), true, true) {
            @Override
            public void handleSuccess(Message msg) {
                BillEntity billEntity = (BillEntity) msg.obj;
                if (billEntity == null) return;
                billEntitys = billEntity;
                carNo = billEntity.getVehicleNo();
                updatedView(billEntity);
                setIcon(billEntity.getApplyStatus());
                billStatus = billEntity.getApplyStatus();
                orderStatus = billEntity.getOrderStatus();
                setBottomBar(billEntity.getOrderStatus());
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_EXCEPTION) {
            if (iDriver != null) iDriver.driverSubmit();
        }
    }

    /**
     * @param //司机状态：11待确认、12待出场、15待处置、21已完成 工地施工单位监理员：12待确认、15待签认、14已签认
     *                                       工地监理单位监理员：12待确认、15待签认、13已签认
     *                                       处置场处理员：15待确认、21待签认、22已签认
     *                                       //改成
     *                                       /*司机端：11待确认、12待出场、15待处置、21已完成
     *                                       工地施工单位监理员：12待确认、17待签认、14已签认
     *                                       工地监理单位监理员：12待确认、17待签认、13已签认
     *                                       处置场处理员：15待确认、17待签认、22已签认
     */
    private void updatedView(BillEntity billEntity) {
        orderStatus = billEntity.getSignStatus();
        mTvWaybill_number.setText("联单编号:" + billEntity.getOrderId());

        mTvOut_time.setText("出场时间:" + billEntity.getOutTime());

        mTvStatus.setText(billEntity.getStatusName());
        mTvStatus.setBackgroundDrawable(BillStatusUtil.getDriverTypeDrawable(getContext(), billEntity.getOrderStatus()));

        mTvWaste_types.setText(billEntity.getWasteType());//废弃物各类
        mTvLoading_amount.setText(billEntity.getLoading() + "立方/次");
        mTvNumber_plate.setText(billEntity.getVehicleNo());
        mTvStart_address.setText(billEntity.getProjectAddress());
        mTvEnd_address.setText(billEntity.getReceivingName());
        mTvProject_name.setText("工程名称: " + billEntity.getProjectName());
        mTvProject_address.setText("工程地址: " + billEntity.getProjectAddress());
        tvFecnyName.setText("围栏名称: " + billEntity.getSiteFence());
        mTvBuild_unit.setText("建设单位: " + billEntity.getBuildUnit());
        mTvSupervision_unit.setText("监理单位: " + billEntity.getSupervisorUnit());
        mTvConstruction_unit.setText("施工单位: " + billEntity.getConstructionUnit());

    }

}
