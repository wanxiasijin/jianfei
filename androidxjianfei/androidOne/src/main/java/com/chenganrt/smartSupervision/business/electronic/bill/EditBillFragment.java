package com.chenganrt.smartSupervision.business.electronic.bill;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.chenganrt.smartSupervision.R;
import com.chenganrt.smartSupervision.business.electronic.BillAction;
import com.chenganrt.smartSupervision.business.electronic.util.BillStatusUtil;
import com.chenganrt.smartSupervision.business.electronic.util.ToastUtil;
import com.chenganrt.smartSupervision.business.electronic.okhttp.UICallback;
import com.chenganrt.smartSupervision.business.electronic.parse.entiy.OrderDetailEntity;
import com.chenganrt.smartSupervision.business.electronic.wheel.FAlertDialog;
import com.chenganrt.smartSupervision.business.electronic.wheel.FDataPickerDialog;
import com.chenganrt.smartSupervision.business.electronic.wheel.FNormalDialog;
import com.chenganrt.smartSupervision.business.electronic.wheel.PopWindow;
import com.chenganrt.smartSupervision.business.login.UserSP;

import java.util.List;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Administrator on 2019/6/11.
 */

public class EditBillFragment extends BaseFragment {
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
    @BindView(R.id.tv_number_plate1)
    TextView mTvNumber_plate;
    @BindView(R.id.tv_start_address)
    TextView mTvStart_address;
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
    @BindView(R.id.tv_end_address)
    TextView tvEndAddress;
    @BindView(R.id.et_loading_amount)
    EditText mEt_loading_amount;

    @BindView(R.id.tv_push_time)
    TextView tvPushTime;
    @BindView(R.id.tv_reciver_time)
    TextView tvReciverTime;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;

    private String orderID, orderStatus;
    private String carNo;
    private String status;
    private List<OrderDetailEntity.WasteEntity> wasteEntities;
    private List<OrderDetailEntity.ReceiveEntity> receiveEntities;

    private String receivingId, wasteType;

    private final int REQUEST_REPORTED_CODE = 1019;
    private final int REQUEST_UNREPORT_CODE = 1020;

    private IEdit iEdit;

    public interface IEdit {
        void updateSuccess();
    }

    public void setiEdit(IEdit iEdit) {
        this.iEdit = iEdit;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        initView();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_edit_order_details, container, false);
    }

    public void getData() {
        getWayBillDetail(orderID, UserSP.getInstance().getUserId(getContext()));
    }

    private void initView() {
        orderID = getActivity().getIntent().getStringExtra("orderId");//运单编号
        orderStatus = getActivity().getIntent().getStringExtra("orderStatus");//运单状态
    }

    @OnClick({R.id.btn_submit, R.id.tv_abnormal_report, R.id.layout_category, R.id.tv_end_address, R.id.tv_number_plate1})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.btn_submit:
                if (checkNull())
                    showAskDialog(mEt_loading_amount.getText().toString().trim());
                break;
            case R.id.tv_abnormal_report:
                showCancelDialog(carNo);
                break;
            case R.id.layout_category:
                if (wasteNameList(wasteEntities) != null)
                    showCategorySelected(wasteNameList(wasteEntities));
                break;
            case R.id.tv_end_address:
                if (receiveEntities != null && receiveEntities.size() == 1) {
                } else if (receiveEntities != null && receiveEntities.size() > 1) {
                  //  startActivityForResult(AddressActivity.startAct(getActivity(), receiveEntities), REQUEST_REPORTED_CODE);
                } else {
                  //  startActivityForResult(AddressActivity.startAct(getActivity(), null), REQUEST_UNREPORT_CODE);
                }
                break;
            case R.id.tv_number_plate1:
                if (!TextUtils.isEmpty(status))
                    showPopwindow(mTvNumber_plate, status);
                break;
        }
    }

    private boolean checkNull() {
        if (TextUtils.isEmpty(wasteType)) {
            ToastUtil.showToast(getContext(), "请选择废弃物种类");
            return false;
        } else if (TextUtils.isEmpty(mEt_loading_amount.getText().toString().trim())) {
            ToastUtil.showToast(getContext(), "请输入装载量");
            return false;
        } else if (TextUtils.isEmpty(receivingId)) {
            ToastUtil.showToast(getContext(), "请选择终点");
            return false;
        }
        return true;
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_REPORTED_CODE && resultCode == getActivity().RESULT_OK) {
            if (data != null) {
                receivingId = data.getStringExtra("id");
                tvEndAddress.setText(data.getStringExtra("address"));
            }
        } else if (requestCode == REQUEST_UNREPORT_CODE && resultCode == getActivity().RESULT_OK) {
            receivingId = data.getStringExtra("id");
            tvEndAddress.setText(data.getStringExtra("address"));
        }
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
                landUp(orderID, "2");//1-确认，2-取消
            }
        });
        builder1.show();
        builder1.setTitles(String.format(getString(R.string.bill_detail_submitTip), carNo));
        builder1.setContents(getString(R.string.bill_detail_subupTip));
        builder1.setNegativeText(getString(R.string.no));
        builder1.setPositiveText(getString(R.string.yes));
    }

    private void showAskDialog(final String loading) {
        FNormalDialog.Builder builder1 = FNormalDialog.createNormalDialog(getActivity());
        builder1.setOnItemChooseListener(new FNormalDialog.OnItemChooseListener() {
            @Override
            public void onNegativeListener(Dialog dialog) {
                //dialog.dismiss();
            }

            @Override
            public void onPositiveListener(Dialog dialog) {
                dialog.dismiss();
                updateWayBillDetail(orderID, wasteType, loading, receivingId, UserSP.getInstance().getUserId(getContext()));
            }
        });
        builder1.show();
        builder1.setTitles(getString(R.string.bill_detail_subsureTip));
        builder1.setContents(getString(R.string.bill_detail_subupTip));
        builder1.setNegativeText(getString(R.string.no));
        builder1.setPositiveText(getString(R.string.yes));
    }

    private void initEndAddress() {
        if (receiveEntities != null && receiveEntities.size() > 0) {
            tvEndAddress.setText(receiveEntities.get(0).getReceivingName());
            receivingId = receiveEntities.get(0).getReceivingId();
        }
    }

    private void initCategory(List<OrderDetailEntity.WasteEntity> wasteEntities) {
        if (wasteEntities != null && wasteEntities.size() > 0) {
            mTvWaste_types.setText(wasteEntities.get(0).getWasteName());
            wasteType = wasteEntities.get(0).getWasteId();
        }
    }

    //获取订单详情
    private void getWayBillDetail(String orderId, String userId) {
        BillAction billAction = new BillAction();
        billAction.wayBillDetailAction(orderId, userId, new UICallback(getActivity(), true, true) {
            @Override
            public void handleSuccess(Message msg) {
                OrderDetailEntity orderDetailEntity = (OrderDetailEntity) msg.obj;
                if (orderDetailEntity == null) return;
                updatedView(orderDetailEntity);
                wasteEntities = orderDetailEntity.getWasteEntities();
                receiveEntities = orderDetailEntity.getReceiveEntities();
                carNo = orderDetailEntity.getVehicleNo();
                wasteNameList(orderDetailEntity.getWasteEntities());
                status = orderDetailEntity.getApplyStatus();
                setIcon(status);
                initEndAddress();
                initCategory(wasteEntities);
            }
        });
    }


    //修改订单详情
    private void updateWayBillDetail(final String orderId, String wasteType, String loading, String receivingId, String userId) {
        BillAction billAction = new BillAction();
        billAction.updateWayBillAction(orderId,
                loading,
                receivingId,
                userId,
                wasteType,
                new UICallback(getActivity(), true) {
                    @Override
                    public void handleSuccess(Message msg) {
                        //startActivity(WaybillDetailsActivity.startAct(getActivity(), orderId, orderStatus));
                        if (iEdit != null) iEdit.updateSuccess();
                    }
                });
    }

    //弃土上报
    private void landUp(String orderId, String signType) {
        BillAction billAction = new BillAction();
        /*billAction.landUpAction(orderId, UserUtil.getInstance().getUserId(getContext()), new UICallback(getActivity(), true) {
            @Override
            public void handleSuccess(Message msg) {
                ToastUtil.showToast(getContext(), "上报成功");
                getActivity().finish();
            }
        });*/

        billAction.billOperateAction(orderId, UserSP.getInstance().getUserId(getContext()), signType, String.valueOf(UserSP.getInstance().getUserType(getContext())), new UICallback(getActivity(), true) {
            @Override
            public void handleSuccess(Message msg) {
                ToastUtil.showToast(getContext(), "操作成功");
                if (iEdit != null) iEdit.updateSuccess();
            }
        });

    }

    //工程
    private String[] wasteNameList(List<OrderDetailEntity.WasteEntity> wasteEntities) {
        String[] strCategory = null;
        if (wasteEntities != null && wasteEntities.size() > 0) {
            strCategory = new String[wasteEntities.size()];
            for (int i = 0; i < wasteEntities.size(); i++) {
                strCategory[i] = wasteEntities.get(i).getWasteName();
            }
        }
        return strCategory;
    }


    /**
     * @param orderDetailEntity 司机状态：11待确认、12待出场、15待处置、21已完成
     */
    private void updatedView(OrderDetailEntity orderDetailEntity) {
        mTvWaybill_number.setText("联单编号:" + orderDetailEntity.getOrderId());
        mTvOut_time.setText("出场时间:" + orderDetailEntity.getOutTime());

        mTvStatus.setText(BillStatusUtil.getTypeName(orderDetailEntity.getSignStatus(), false));
        mTvStatus.setBackgroundDrawable(BillStatusUtil.getTypeDrawable(getContext(), orderDetailEntity.getSignStatus()));

        mTvNumber_plate.setText(orderDetailEntity.getVehicleNo());
        mTvStart_address.setText(orderDetailEntity.getProjectAddress());
        mTvProject_name.setText("工地名称: " + orderDetailEntity.getProjectName());
        mTvProject_address.setText("工地地址: " + orderDetailEntity.getProjectAddress());
        tvFecnyName.setText("围栏名称: " + orderDetailEntity.getSiteFence());
        mTvBuild_unit.setText("建设单位: " + orderDetailEntity.getBuildUnit());
        mTvSupervision_unit.setText("监理单位: " + orderDetailEntity.getSupervisorUnit());
        mTvConstruction_unit.setText("施工单位: " + orderDetailEntity.getConstructionUnit());
        mEt_loading_amount.setText(orderDetailEntity.getLoading());//方/量
        mEt_loading_amount.setSelection(mEt_loading_amount.getText().toString().trim().length());
        tvPushTime.setText(String.format(getString(R.string.bill_detail_timePush), orderDetailEntity.getPushTime()));
        tvReciverTime.setText(String.format(getString(R.string.bill_detail_timeReciver), orderDetailEntity.getReceiveTime()));

        if (!TextUtils.isEmpty(orderStatus)) {
            if ("1".equals(orderStatus)) {
                tvEndTime.setText(String.format(getString(R.string.bill_detail_timeSure), orderDetailEntity.getSignTime()));
            } else if ("2".equals(orderStatus)) {
                tvEndTime.setText(String.format(getString(R.string.bill_detail_timeCancel), orderDetailEntity.getCancelTime()));
            } else {
                tvEndTime.setText(String.format(getString(R.string.bill_detail_timeEnd), orderDetailEntity.getSignExpire()));
                tvEndTime.setVisibility(View.GONE);
            }
        }
    }

    private void showCategorySelected(final String[] str) {
        if (str == null) return;
        FAlertDialog.Builder builder = FDataPickerDialog.createSingleDataPicker(getActivity(), str, new FDataPickerDialog.OnWheelItemSelectedListener() {
            @Override
            public void onWheelItemSelected(FAlertDialog.Builder Builder, int position) {
                mTvWaste_types.setText(str[position]);
                wasteType = wasteEntities.get(position).getWasteId();
            }
        });
        builder.setWheelCurrentPostion(0);
        builder.show();
    }

}
