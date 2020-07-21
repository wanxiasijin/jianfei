package com.chenganrt.smartSupervision.business.electronic.bill;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chenganrt.smartSupervision.R;
import com.chenganrt.smartSupervision.business.electronic.BillAction;
import com.chenganrt.smartSupervision.business.electronic.BillEntity;
import com.chenganrt.smartSupervision.business.electronic.util.BillStatusUtil;
import com.chenganrt.smartSupervision.business.electronic.util.ToastUtil;
import com.chenganrt.smartSupervision.business.electronic.okhttp.UICallback;
import com.chenganrt.smartSupervision.business.electronic.parse.entiy.SoilTypeEntity;
import com.chenganrt.smartSupervision.business.electronic.activity.SoilQualityActivity;
import com.chenganrt.smartSupervision.business.electronic.wheel.FAlertDialog;
import com.chenganrt.smartSupervision.business.electronic.wheel.FDataPickerDialog;
import com.chenganrt.smartSupervision.business.electronic.wheel.FNormalDialog;
import com.chenganrt.smartSupervision.business.electronic.wheel.PopWindow;
import com.chenganrt.smartSupervision.business.login.UserSP;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Administrator on 2019/6/11.
 */

public class NormalBillFragment extends BaseFragment implements CompoundButton.OnCheckedChangeListener {
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
    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    @BindView(R.id.tv_push_time)
    TextView tvPushTime;
    @BindView(R.id.tv_reciver_time)
    TextView tvReciverTime;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;
    @BindView(R.id.ll_btn)
    LinearLayout mLiBtn;

    @BindView(R.id.checkBox)
    CheckBox checkBox;
    @BindView(R.id.tv_except_type)
    TextView tvExcepType;
    @BindView(R.id.camera_driver)
    ImageButton cameraDriver;
    @BindView(R.id.camera_vehicle)
    ImageButton cameraVehicle;
    @BindView(R.id.camera_soil)
    ImageButton cameraSoil;

    @BindView(R.id.driver_num)
    TextView tvDriverNum;
    @BindView(R.id.vehicle_num)
    TextView tvVehicleNum;
    @BindView(R.id.soil_num)
    TextView tvSoilNum;
    @BindView(R.id.ed_mark)
    EditText edMark;

    @BindView(R.id.layout_soil)
    LinearLayout layoutSoil;
    @BindView(R.id.layout_except_info)
    LinearLayout layoutInfo;

    private String soilExceptType;
    private final String TYPE_SOIL = "47";
    private final int REQUEST_DRIVER = 201;
    private final int REQUEST_VEHICLE = 202;
    private final int REQUEST_SOIL = 203;

    private String[] soilTypeArray;
    private List<Uri> uriDriverList = new ArrayList<>();
    private List<Uri> uriVehicleList = new ArrayList<>();
    private List<Uri> uriSoilList = new ArrayList<>();
    private List<SoilTypeEntity> soilTypeEntities;
    private BillEntity billEntitys;
    private String orderID;
    private String orderStatus;
    private String carNo;
    private String billStatus;

    private INormal iNormal;

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isCheck) {
        layoutInfo.setVisibility(isCheck ? View.VISIBLE : View.GONE);
        if (iNormal != null) iNormal.checkRadio(checkBox.isChecked());
    }

    public interface INormal {
        void normalSubmit();

        void checkRadio(boolean isSelected);
    }

    public void setINormal(INormal iNormal) {
        this.iNormal = iNormal;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        checkBox.setOnCheckedChangeListener(this);
        initData();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_waybill_details, container, false);
    }

    @OnClick({R.id.btn_submit, R.id.tv_cancel, R.id.tv_number_plate2, R.id.camera_driver, R.id.camera_vehicle, R.id.camera_soil, R.id.tv_except_select})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.btn_submit:
                if (billEntitys == null) return;
                if (checkBox.isChecked()) {
                    if (checkNull()) showTipDialog(getTips());
                } else {
                    showTipDialog(getTips());
                }
                break;
            case R.id.tv_cancel:
                showCancelDialog(carNo);
                break;
            case R.id.tv_number_plate2:
                if (!TextUtils.isEmpty(billStatus))
                    showPopwindow(mTvNumber_plate, billStatus);
                break;
            case R.id.camera_driver:
                startActivityForResult(SoilQualityActivity.startAct(getActivity(), SoilQualityActivity.TYPE_DRIVER, orderID, uriDriverList), REQUEST_DRIVER);
                break;
            case R.id.camera_vehicle:
                startActivityForResult(SoilQualityActivity.startAct(getActivity(), SoilQualityActivity.TYPE_VEHICLE, orderID, uriVehicleList), REQUEST_VEHICLE);
                break;
            case R.id.camera_soil:
                startActivityForResult(SoilQualityActivity.startAct(getActivity(), SoilQualityActivity.TYPE_SOIL, orderID, uriSoilList), REQUEST_SOIL);
                break;
            case R.id.tv_except_select:
                if (soilTypeArray == null) getSoilType(TYPE_SOIL, "");
                else showSoilTypeSelecte(soilTypeArray);
                break;
        }
    }


    private void initData() {
        orderID = getActivity().getIntent().getStringExtra("orderId");//运单编号
        orderStatus = getActivity().getIntent().getStringExtra("orderStatus");//运单状态
    }

    public void getData() {
        getDetails(UserSP.getInstance().getUserId(getContext()), orderID, String.valueOf(UserSP.getInstance().getUserType(getContext())), orderStatus);
        if ("0".equals(orderStatus)) getSoilType(TYPE_SOIL, "");
    }

    private boolean checkNull() {
        if (TextUtils.isEmpty(tvExcepType.getText().toString())) {
            ToastUtil.showToast(getContext(), R.string.soil_type_tip);
            return false;
        }

        /*if (uriDriverList.size() <= 0) {
            ToastUtil.showToast(getContext(), R.string.photo_driver_tip);
            return false;
        }*/

        if (uriSoilList.size() <= 0) {
            ToastUtil.showToast(getContext(), R.string.photo_soil_tip);
            return false;
        }

        if (uriVehicleList.size() <= 0) {
            ToastUtil.showToast(getContext(), R.string.photo_vehicle_tip);
            return false;
        }

        return true;
    }

    private void getSoilType(String type, String search) {
        BillAction billAction = new BillAction();
        billAction.soilTypeAcion(type, search, new UICallback(getActivity(), false) {
            @Override
            public void handleSuccess(Message msg) {
                SoilTypeEntity soilTypeEntity = (SoilTypeEntity) msg.obj;
                if (soilTypeEntity == null) return;
                soilTypeEntities = soilTypeEntity.getSoilTypeEntities();
                getSoilArray(soilTypeEntities);
                initSoilType();

            }
        });
    }

    private void initSoilType() {
        if (soilTypeArray != null && soilTypeArray.length > 0) {
            tvExcepType.setText(soilTypeArray[0]);
            soilExceptType = soilTypeEntities.get(0).getValue();
        }
    }

    private void soilReportAction(String orderID, String userName, String isReport, String soilExceptType, String SoilExceptDetail) {
        BillAction billAction = new BillAction();
        billAction.soilReportAction(orderID, userName, isReport, soilExceptType, SoilExceptDetail, new UICallback(getActivity()) {
            @Override
            public void handleSuccess(Message msg) {
                if (iNormal != null) iNormal.normalSubmit();
            }
        });
    }

    private String[] getSoilArray(List<SoilTypeEntity> soilTypeEntities) {
        if (soilTypeEntities == null) return null;
        soilTypeArray = new String[soilTypeEntities.size()];
        for (int i = 0; i < soilTypeEntities.size(); i++) {
            soilTypeArray[i] = soilTypeEntities.get(i).getText();
        }
        return soilTypeArray;
    }

    private void showSoilTypeSelecte(final String[] str) {
        if (str == null) return;
        FAlertDialog.Builder builder = FDataPickerDialog.createSingleDataPicker(getActivity(), str, new FDataPickerDialog.OnWheelItemSelectedListener() {
            @Override
            public void onWheelItemSelected(FAlertDialog.Builder Builder, int position) {
                tvExcepType.setText(str[position]);
                soilExceptType = soilTypeEntities.get(position).getValue();
            }
        });
        builder.setWheelCurrentPostion(0);
        builder.show();
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
        if (!TextUtils.isEmpty(status) && "0".equals(status))
            mLiBtn.setVisibility(View.VISIBLE);
        else mLiBtn.setVisibility(View.GONE);
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

    private void showTipDialog(String msg) {
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
                //billOperate(orderID, "1");
                soilReportAction(orderID,
                        UserSP.getInstance().getUserName(getContext()),
                        checkBox.isChecked() ? "1" : "0",
                        TextUtils.isEmpty(soilExceptType) ? "" : soilExceptType,
                        edMark.getText().toString().trim());
            }
        });
        builder1.show();
        builder1.setTitles(msg);
        builder1.setContents(getString(R.string.bill_detail_subupTip));
        builder1.setNegativeText(getString(R.string.no));
        builder1.setPositiveText(getString(R.string.yes));

    }

    //运单提交(废弃)
    private void submitDetail(final String UserID, String OrderID, String OrderStatus, final String UserType) {
        BillAction billAction = new BillAction();
        billAction.subBillAction(OrderID, UserID, UserType, OrderStatus, new UICallback(getActivity(), true) {
            @Override
            public void handleSuccess(Message msg) {
                //getDetails(UserID, orderID, UserType);//重新获取运单详情
                if (iNormal != null) iNormal.normalSubmit();
            }
        });
    }


    private void billOperate(String orderId, String signType) {
        BillAction billAction = new BillAction();
        billAction.billOperateAction(orderId, UserSP.getInstance().getUserId(getContext()), signType, String.valueOf(UserSP.getInstance().getUserType(getContext())), new UICallback(getActivity(), true) {
            @Override
            public void handleSuccess(Message msg) {
                if (iNormal != null) iNormal.normalSubmit();
            }
        });

    }

    //弃土上报(废弃)
    private void landUp(String orderId) {
        BillAction billAction = new BillAction();
        billAction.landUpAction(orderId, UserSP.getInstance().getUserId(getContext()), new UICallback(getActivity(), true) {
            @Override
            public void handleSuccess(Message msg) {
                ToastUtil.showToast(getContext(), "上报成功");
                getActivity().setResult(getActivity().RESULT_OK);
                getActivity().finish();
            }
        });
    }

    private void getDetails(String UserID, String OrderID, final String UserType, String SignStatus) {
        BillAction billAction = new BillAction();
        billAction.orderDetailAction(OrderID, UserID, UserType, SignStatus, new UICallback(getActivity(), true, true) {
            @Override
            public void handleSuccess(Message msg) {
                BillEntity billEntity = (BillEntity) msg.obj;
                if (billEntity == null) return;
                billEntitys = billEntity;
                carNo = billEntity.getVehicleNo();
                updatedView(billEntity);
                setIcon(billEntity.getApplyStatus());
                billStatus = billEntity.getApplyStatus();
                //setBottomTex(UserUtil.getInstance().getUserType(getContext()), billEntity.getOrderStatus());
                setBottomBar(billEntity.getSignStatus());
            }
        });
    }

    private void setStateType(String orderState, boolean isSupply) {
        mTvStatus.setText(BillStatusUtil.getTypeName(orderState, isSupply));
        mTvStatus.setBackgroundDrawable(BillStatusUtil.getTypeDrawable(getContext(), orderState));

        /*if ("1".equals(orderState)) {
            if (!TextUtils.isEmpty(String.valueOf(soilState)) && "0".equals(String.valueOf(soilState))) {
                mTvStatus.setText(getString(R.string.soil_except_bill));
                mTvStatus.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_coners_red));
            } else {
                mTvStatus.setText(BillStatusUtil.getTypeName(orderState));
                mTvStatus.setBackgroundDrawable(BillStatusUtil.getTypeDrawable(getContext(), orderState));
            }
        } else {
            mTvStatus.setText(BillStatusUtil.getTypeName(orderState));
            mTvStatus.setBackgroundDrawable(BillStatusUtil.getTypeDrawable(getContext(), orderState));
        }*/
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

        setStateType(orderStatus, 1 == billEntity.getIsSupply());
        layoutSoil.setVisibility("0".equals(orderStatus) ? View.VISIBLE : View.GONE);//是否显示土质异常

        mTvOut_time.setText(UserSP.getInstance().getUserType(getContext()) == 2 ? "出场时间:" + billEntity.getOutTime() : "入场时间:" + billEntity.getInFieldTime());

        mTvWaste_types.setText(billEntity.getWasteType());
        mTvLoading_amount.setText(billEntity.getLoading() + "立方/次");
        mTvNumber_plate.setText(billEntity.getVehicleNo());
        mTvStart_address.setText(billEntity.getProjectAddress());
        mTvEnd_address.setText(billEntity.getReceivingName());
        mTvProject_name.setText("工地名称: " + billEntity.getProjectName());
        mTvProject_address.setText("工地地址: " + billEntity.getProjectAddress());
        tvFecnyName.setText("围栏名称: " + billEntity.getSiteFence());
        mTvBuild_unit.setText("建设单位: " + billEntity.getBuildUnit());
        mTvSupervision_unit.setText("监理单位: " + billEntity.getSupervisorUnit());
        mTvConstruction_unit.setText("施工单位: " + billEntity.getConstructionUnit());

        tvPushTime.setText(String.format(getString(R.string.bill_detail_timePush), billEntity.getPushTime()));
        tvReciverTime.setText(String.format(getString(R.string.bill_detail_timeReciver), billEntity.getReceiveTime()));

        if (!TextUtils.isEmpty(orderStatus)) {
            if ("1".equals(orderStatus)) {
                tvEndTime.setText(String.format(getString(R.string.bill_detail_timeSure), billEntity.getSignTime()));
            } else if ("2".equals(orderStatus)) {
                tvEndTime.setText(String.format(getString(R.string.bill_detail_timeCancel), billEntity.getCancelTime()));
            } else if ("4".equals(orderStatus)) {
                tvEndTime.setText(String.format(getString(R.string.bill_detail_report), billEntity.getSignTime()));
            } else if ("5".equals(orderStatus)) {
                tvEndTime.setText(String.format(getString(R.string.bill_detail_sure), billEntity.getSignTime()));
            } else {
                tvEndTime.setText(String.format(getString(R.string.bill_detail_timeEnd), billEntity.getSignExpire()));
                tvEndTime.setVisibility(View.GONE);
            }
        }
    }


    private void showLastImage(List<Uri> urlList, int requestCode) {
        if (requestCode == REQUEST_DRIVER) {
            uriDriverList = urlList;
            Glide.with(getContext()).load(urlList.get(urlList.size() - 1)).into(cameraDriver);
        }
        if (requestCode == REQUEST_VEHICLE) {
            uriVehicleList = urlList;
            Glide.with(getContext()).load(urlList.get(urlList.size() - 1)).into(cameraVehicle);
        }
        if (requestCode == REQUEST_SOIL) {
            uriSoilList = urlList;
            Glide.with(getContext()).load(urlList.get(urlList.size() - 1)).into(cameraSoil);
        }
    }

    private void setImageBadgeNum(int count, int requestCode) {
        if (requestCode == REQUEST_DRIVER) setTxt(tvDriverNum, count);
        if (requestCode == REQUEST_VEHICLE) setTxt(tvVehicleNum, count);
        if (requestCode == REQUEST_SOIL) setTxt(tvSoilNum, count);
    }

    private void setTxt(TextView txtView, int count) {
        txtView.setText(String.valueOf(count));
        txtView.setVisibility(count > 0 ? View.VISIBLE : View.GONE);
    }

    private void updateImage(List<Uri> urlList, int requestCode) {
        if (urlList == null) return;
        if (urlList.size() > 0) showLastImage(urlList, requestCode);
        setImageBadgeNum(urlList.size(), requestCode);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK) {
            if (data != null) {
                List<Uri> urlList = (List<Uri>) data.getSerializableExtra("uriList");
                updateImage(urlList, requestCode);
            }
        }
    }

}
