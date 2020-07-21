package com.chenganrt.smartSupervision.business.electronic.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.chenganrt.smartSupervision.R;
import com.chenganrt.smartSupervision.business.electronic.bill.DriverBillFragment;
import com.chenganrt.smartSupervision.business.electronic.bill.EditBillFragment;
import com.chenganrt.smartSupervision.business.electronic.bill.EmptyFragment;
import com.chenganrt.smartSupervision.business.electronic.bill.NormalBillFragment;
import com.chenganrt.smartSupervision.business.electronic.wheel.FNormalDialog;
import com.chenganrt.smartSupervision.business.login.UserSP;


/**
 * Created by Administrator on 2019/6/11.
 */

public class BillDetailActivity extends BaseActivity implements EmptyFragment.IEmpty, EditBillFragment.IEdit, NormalBillFragment.INormal, DriverBillFragment.IDriver {

    private NormalBillFragment normalBillFragment;//受纳场
    private EditBillFragment editBillFragment;
    private DriverBillFragment driverBillFragment;
    private EmptyFragment emptyFragment;

    public static Intent startAct(Activity activity, String orderId, String signStatus) {
        Intent intent = new Intent(activity, BillDetailActivity.class);
        intent.putExtra("orderId", orderId);
        intent.putExtra("orderStatus", signStatus);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_bill_detail);
        getSupportActionBar().hide();
        setSubTitle(R.string.bill_detail_title);
        initView();
    }

    private void initView() {
        String oStatus = getIntent().getStringExtra("orderStatus");
        normalBillFragment = (NormalBillFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_normal);
        editBillFragment = (EditBillFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_edit);
        driverBillFragment = (DriverBillFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_driver);
        emptyFragment = (EmptyFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_empty);
        emptyFragment.setiEmpty(this);
        normalBillFragment.setINormal(this);
        editBillFragment.setiEdit(this);
        driverBillFragment.setiDriver(this);
        if ("1".equals(UserSP.getInstance().getUserType(getContext()))) {
            getSupportFragmentManager().beginTransaction().show(driverBillFragment).hide(normalBillFragment).hide(editBillFragment).hide(emptyFragment).commitAllowingStateLoss();
            driverBillFragment.getData();
        } else {
            if (!TextUtils.isEmpty(oStatus) && "0".equals(oStatus) && "2".equals(UserSP.getInstance().getUserType(getContext()))) {//只有工地+待签认状态才进入可编辑界面
                getSupportFragmentManager().beginTransaction().show(editBillFragment).hide(driverBillFragment).hide(normalBillFragment).hide(emptyFragment).commitAllowingStateLoss();
                editBillFragment.getData();
            } else {
                getSupportFragmentManager().beginTransaction().show(normalBillFragment).hide(driverBillFragment).hide(editBillFragment).hide(emptyFragment).commitAllowingStateLoss();
                normalBillFragment.getData();
            }
        }
    }

    private void showGiveUpDialog(String msg) {
        FNormalDialog.Builder builder1 = FNormalDialog.createNormalDialog(getActivity());
        builder1.setOnItemChooseListener(new FNormalDialog.OnItemChooseListener() {
            @Override
            public void onNegativeListener(Dialog dialog) {

            }

            @Override
            public void onPositiveListener(Dialog dialog) {
                finish();
                setResult(RESULT_OK);
                dialog.dismiss();

            }
        });
        builder1.show();
        builder1.setTitles(msg);
        builder1.setNegativeText(getString(R.string.cancel));
        builder1.setPositiveText(getString(R.string.confirmed));

    }

    @Override
    public void noData() {

    }

    @Override
    public void updateSuccess() {
        //getSupportFragmentManager().beginTransaction().show(normalBillFragment).hide(editBillFragment).hide(emptyFragment).commitAllowingStateLoss();
        //normalBillFragment.getData();
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void normalSubmit() {
        setResult(RESULT_OK);
        finish();
    }

    boolean isSelected;

    @Override
    public void checkRadio(boolean isSelected) {
        this.isSelected = isSelected;
    }

    @Override
    public void closeLoading() {
        super.closeLoading();
        finish();
    }

    @Override
    public void driverSubmit() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void onBackPressed() {
        if (isSelected) {
            showGiveUpDialog("确认放弃此次编辑？");
        } else {
            super.onBackPressed();
        }
    }
}
