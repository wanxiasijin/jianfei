package com.chenganrt.smartSupervision.business.electronic.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.chenganrt.smartSupervision.R;
import com.chenganrt.smartSupervision.business.electronic.BillAction;
import com.chenganrt.smartSupervision.business.electronic.util.ToastUtil;
import com.chenganrt.smartSupervision.business.electronic.okhttp.UICallback;
import com.chenganrt.smartSupervision.business.login.UserSP;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Administrator on 2019/11/4.
 */

public class BillExcepActivity extends BaseActivity {

    public static final int REQUEST_CODE_EXCEPTION = 1302;

    public static Intent startAct(Activity activity, String orderId) {
        Intent intent = new Intent(activity, BillExcepActivity.class);
        intent.putExtra("orderId", orderId);
        return intent;
    }

    @BindView(R.id.ed_description)
    EditText edDescript;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_exception);
        setSubTitle(R.string.exception_title);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.tv_submit})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.tv_submit:
                if (!TextUtils.isEmpty(edDescript.getText().toString().trim()))
                    upException(edDescript.getText().toString().trim());
                else ToastUtil.showToast(getContext(), "请输入异常信息");
                break;
        }
    }


    private void upException(String exceptContent) {
        String orderId = getIntent().getStringExtra("orderId");
        BillAction billAction = new BillAction();
        billAction.billExcepAction(orderId, UserSP.getInstance().getUserId(getContext()), exceptContent, new UICallback(getActivity()) {
            @Override
            public void handleSuccess(Message msg) {
                super.handleSuccess(msg);
                setResult(RESULT_OK);
                finish();
            }
        });
    }

}
