package com.chenganrt.smartSupervision.business.electronic.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.chenganrt.smartSupervision.R;
import com.chenganrt.smartSupervision.business.electronic.util.AppTools;
import com.chenganrt.smartSupervision.business.electronic.BillAction;
import com.chenganrt.smartSupervision.business.electronic.util.ToastUtil;
import com.chenganrt.smartSupervision.business.electronic.okhttp.UICallback;
import com.chenganrt.smartSupervision.business.electronic.parse.entiy.BuildEntity;
import com.chenganrt.smartSupervision.business.electronic.parse.entiy.SoilTypeEntity;
import com.chenganrt.smartSupervision.business.electronic.wheel.FAlertDialog;
import com.chenganrt.smartSupervision.business.electronic.wheel.FDataPickerDialog;
import com.chenganrt.smartSupervision.business.electronic.wheel.FDatePickerDialog;
import com.chenganrt.smartSupervision.business.login.UserSP;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Administrator on 2019/12/23.
 */

public class RepairActivity extends BaseActivity {

    private final int REQUEST_REPORTED_CODE = 1006;
    private final int REQUEST_VEHICLE_CODE = 1007;

    private final String TYPE_RUBBISH = "42";

    @BindView(R.id.tv_project_name)
    TextView siteName;
    @BindView(R.id.tv_project_address)
    TextView siteAddress;
    @BindView(R.id.tv_fency_name)
    TextView siteFency;
    @BindView(R.id.tv_build_unit)
    TextView siteUnit;
    @BindView(R.id.tv_supervision_unit)
    TextView siteSupervise;
    @BindView(R.id.tv_construction_unit)
    TextView siteBuild;
    @BindView(R.id.tv_waste_types)
    TextView mTvWaste_types;
    @BindView(R.id.tv_end_address)
    TextView tvEndAddress;
    @BindView(R.id.et_loading_amount)
    EditText edMount;
    @BindView(R.id.tv_lisence)
    TextView tvLisence;
    @BindView(R.id.time_in)
    TextView timeStart;
    @BindView(R.id.time_out)
    TextView timeEnd;
    @BindView(R.id.tv_start_address)
    TextView mTvStart_address;
    @BindView(R.id.ed_mark)
    EditText edMark;

    private List<SoilTypeEntity> soilTypeEntities;
    private String[] soilTypeArray;

    private String receivingId, siteId, siteFenceId, wasteType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_repair);
        getSupportActionBar().hide();
        ButterKnife.bind(this);
        setSubTitle(R.string.repair_title);
        initView();
        getSoilType(TYPE_RUBBISH, "");
        getBuildInfo();
    }

    @OnClick({R.id.layout_category, R.id.tv_end_address, R.id.layout_in, R.id.layout_out, R.id.layout_vehicle,
            R.id.tv_abnormal_report, R.id.btn_submit})
    public void onClickView(View view) {
        switch (view.getId()) {
            case R.id.layout_category:
                if (soilTypeArray == null) getSoilType(TYPE_RUBBISH, "");
                else showSoilTypeSelecte(soilTypeArray);
                break;
            case R.id.tv_end_address:
                startActivityForResult(new Intent(getActivity(), RepairAddressActivity.class), REQUEST_REPORTED_CODE);
                break;
            case R.id.layout_in:
                showDataWheel(true);
                break;
            case R.id.layout_out:
                showDataWheel(false);
                break;
            case R.id.layout_vehicle:
                startActivityForResult(new Intent(getActivity(), VehicleOptActivity.class), REQUEST_VEHICLE_CODE);
                break;
            case R.id.tv_abnormal_report:
                finish();
                break;
            case R.id.btn_submit:
                if (checkNULL()) submit();
                break;
        }
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
                //initSoilType();

            }
        });
    }

    private void getBuildInfo() {
        BillAction billAction = new BillAction();
        billAction.buildInfoAction(UserSP.getInstance().getUserName(getContext()), new UICallback(getActivity(), true) {
            @Override
            public void handleSuccess(Message msg) {
                BuildEntity buildEntity = (BuildEntity) msg.obj;
                if (buildEntity == null) return;
                setSiteInfo(buildEntity);
            }
        });
    }

    /*private void initSoilType() {
        if (soilTypeArray != null) {
            tvExcepType.setText(soilTypeArray[0]);
            soilExceptType = soilTypeEntities.get(0).getValue();
        }
    }*/

    private boolean checkNULL() {
        if (TextUtils.isEmpty(mTvWaste_types.getText().toString().trim())) {
            ToastUtil.showToast(getContext(), R.string.repair_rubbish_empty);
            return false;
        }

        if (TextUtils.isEmpty(edMount.getText().toString().trim())) {
            ToastUtil.showToast(getContext(), R.string.repair_mount_empty);
            return false;
        }

        if (TextUtils.isEmpty(tvLisence.getText().toString().trim())) {
            ToastUtil.showToast(getContext(), R.string.repair_vehicle_empty);
            return false;
        }

        if (TextUtils.isEmpty(timeStart.getText().toString().trim())) {
            ToastUtil.showToast(getContext(), R.string.repair_intime_empty);
            return false;
        }

        if (TextUtils.isEmpty(timeEnd.getText().toString().trim())) {
            ToastUtil.showToast(getContext(), R.string.repair_outtime_empty);
            return false;
        }

        if (AppTools.dateDiff(timeStart.getText().toString().trim(), timeEnd.getText().toString().trim()) < 0) {
            ToastUtil.showToast(getContext(), R.string.repair_timecompary_empty);
            return false;
        }

        if (AppTools.dateDiff(timeEnd.getText().toString().trim(), AppTools.getCurrenDate()) > 180
                || AppTools.dateDiff(timeEnd.getText().toString().trim(), AppTools.getCurrenDate()) < 0) {
            ToastUtil.showToast(getContext(), R.string.repair_twohour_empty);
            return false;
        }

        if (TextUtils.isEmpty(tvEndAddress.getText().toString().trim())) {
            ToastUtil.showToast(getContext(), R.string.repair_twohour_distation);
            return false;
        }

        return true;
    }

    private void showSoilTypeSelecte(final String[] str) {
        if (str == null) return;
        FAlertDialog.Builder builder = FDataPickerDialog.createSingleDataPicker(getActivity(), str, new FDataPickerDialog.OnWheelItemSelectedListener() {
            @Override
            public void onWheelItemSelected(FAlertDialog.Builder Builder, int position) {
                mTvWaste_types.setText(str[position]);
                wasteType = soilTypeEntities.get(position).getValue();
            }
        });
        builder.setWheelCurrentPostion(0);
        builder.show();
    }

    private String[] getSoilArray(List<SoilTypeEntity> soilTypeEntities) {
        if (soilTypeEntities == null) return null;
        soilTypeArray = new String[soilTypeEntities.size()];
        for (int i = 0; i < soilTypeEntities.size(); i++) {
            soilTypeArray[i] = soilTypeEntities.get(i).getText();
        }
        return soilTypeArray;
    }

    private void submit() {
        BillAction billAction = new BillAction();
        billAction.billRepair(UserSP.getInstance().getUserName(getContext()), tvLisence.getText().toString(),
                wasteType, edMount.getText().toString().trim(), siteId, siteFenceId, timeStart.getText().toString(),
                timeEnd.getText().toString(), receivingId, edMark.getText().toString().trim(), new UICallback(getActivity(), true) {
                    @Override
                    public void handleSuccess(Message msg) {
                        setResult(RESULT_OK);
                        finish();
                    }
                });
    }

    private void initView() {
        edMount.setText("11");
        edMount.setSelection(edMount.getText().toString().length());
        timeEnd.setText(AppTools.getCurrenDate());
    }

    private void setSiteInfo(BuildEntity buildEntity) {
        siteId = buildEntity.getSiteId();
        siteFenceId = buildEntity.getSiteFenceId();
        mTvStart_address.setText(AppTools.getString(buildEntity.getSiteAddress()));
        siteName.setText("工地名称: " + AppTools.getString(buildEntity.getSiteName()));
        siteAddress.setText("工地地址: " + AppTools.getString(buildEntity.getSiteAddress()));
        siteFency.setText("围栏名称: " + AppTools.getString(buildEntity.getSiteFenceName()));
        siteUnit.setText("建设单位: " + AppTools.getString(buildEntity.getBuildingUnit()));
        siteSupervise.setText("监理单位: " + AppTools.getString(buildEntity.getMonitorUnit()));
        siteBuild.setText("施工单位: " + AppTools.getString(buildEntity.getConstructUnit()));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_REPORTED_CODE && resultCode == getActivity().RESULT_OK) {
            if (data != null) {
                receivingId = data.getStringExtra("fieldId");
                tvEndAddress.setText(data.getStringExtra("address"));
            }
        } else if (requestCode == REQUEST_VEHICLE_CODE && resultCode == getActivity().RESULT_OK) {
            tvLisence.setText(data.getStringExtra("vehicleNo"));
        }
    }

    private void showDataWheel(final boolean isStart) {
        String[] arrayHour = getContext().getResources().getStringArray(R.array.hour);
        String[] arrayMinute = getContext().getResources().getStringArray(R.array.minute);
        FAlertDialog.Builder builder = FDatePickerDialog.createDateHourPicker(getActivity(), arrayHour, arrayMinute, new FDatePickerDialog.onWheelHourItemSelectedListener() {
            @Override
            public void onWheelItemSelected(FAlertDialog.Builder Builder, int year1, int month1, int day1, String hour, String minute) {
                String time = year1 + "-" + AppTools.addZero(String.valueOf(month1)) + "-" + AppTools.addZero(String.valueOf(day1)) + " " + hour + ":" + minute;
                if (isStart) {
                    timeStart.setText(time);
                } else {
                    timeEnd.setText(time);
                }
            }
        });
        builder.show();
    }


}
