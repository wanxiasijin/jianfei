package com.chenganrt.smartSupervision.business.login;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.commonlib.mvp.BaseActivity;
import com.android.commonlib.mvp.BasePresenter;
import com.android.commonlib.okhttp.NetWorkDataManager;
import com.android.commonlib.okhttp.bean.Response;
import com.android.commonlib.rxjava.RxJava;
import com.android.commonlib.utils.ToastUtils;
import com.android.commonlib.utils.Utils;
import com.chenganrt.smartSupervision.R;
import com.chenganrt.smartSupervision.business.app.BaseApplication;
import com.chenganrt.smartSupervision.business.electronic.util.AppTools;
import com.chenganrt.smartSupervision.business.electronic.util.ToastUtil;
import com.chenganrt.smartSupervision.business.login.view.CountDownView;

import java.lang.ref.WeakReference;
import java.util.LinkedHashMap;
import java.util.Map;

import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import timber.log.Timber;

import static com.chenganrt.smartSupervision.business.login.LoginActivity.FORGET_PASSWORD;
import static com.chenganrt.smartSupervision.business.login.LoginActivity.MES_CODE_TYPE;
import static com.chenganrt.smartSupervision.business.login.LoginActivity.PHONE_REGISTERED;

public class RegisterCodeActivity extends BaseActivity implements CountDownView.Countdown {

    public static final String PNONE_NUMBER = "phone_number";
    public static final String PASSWORD_CODE = "password_type";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.titleSub)
    TextView titleSub;
    @BindView(R.id.et_account)
    EditText mPhoneNumber;
    @BindView(R.id.tv_verification_code)
    CountDownView mCodeButton;
    @BindView(R.id.et_code)
    EditText mEtCode;
    @BindView(R.id.btn_nest)
    TextView mNest;
    private int mMesCodeType;

    @Override
    public int initLayout() {
        return R.layout.register_verification_code;
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    public void initConfig() {
        setStatusBar(toolbar);
        titleSub.setText("手机验证");
        mCodeButton.setOnCountDownListener(this);
        mCodeButton.start(this);
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            mMesCodeType = intent.getIntExtra(MES_CODE_TYPE, 0);
        }
    }

    @OnClick({R.id.tv_verification_code, R.id.btn_nest})
    public void viewOnClick(View view) {
        switch (view.getId()) {
            case R.id.tv_verification_code:
                AppTools.collapseSoftInputMethod(this);
                String phoneNumber = mPhoneNumber.getText().toString().trim();
                if (Utils.isMobile(phoneNumber)) {
                    if (isNetworkAvailable()) {
                        getMesCode();
                    }
                } else {
                    ToastUtils.showLongToast(this, "请输入正确格式的手机号");
                }
                break;
            case R.id.btn_nest:
                AppTools.collapseSoftInputMethod(this);
                String phoneNumber2 = mPhoneNumber.getText().toString().trim();
                if (Utils.isMobile(phoneNumber2)) {
                    String code = mEtCode.getText().toString().trim();
                    if (code.length() != 4) {
                        ToastUtils.showLongToast(this, "请输入正确格式的验证码");
                    } else {
                        if (isNetworkAvailable()) {
                            showLoading();
                            verifyCode();
                        }
                    }
                } else {
                    ToastUtils.showLongToast(this, "请输入正确格式的手机号");
                }
                break;
        }
    }

    private void getMesCode() {
        RxJava.getInstance().create(new SendMesCodeObservable(this), new RxJava.ObserveOnMainThread<Response>() {
            @Override
            public void accept(Response response) {
                Timber.d("getMesCode:" + response.toString());
                if (response.getStatus() == 200) {
                    ToastUtils.showToast(BaseApplication.getApp(), "发送成功");
                } else {
                    ToastUtils.showToast(BaseApplication.getApp(), response.getMessage());
                }
            }
        }, new RxJava.ThrowableOnMainThread() {
            @Override
            public void accept(String t) {
                ToastUtils.showToast(BaseApplication.getApp(), t);
            }
        });
    }

    private void verifyCode() {
        RxJava.getInstance().create(new VerifyCodeObservable(this), new RxJava.ObserveOnMainThread<Response>() {
            @Override
            public void accept(Response response) {
                dismissLoading();
                Timber.d("verifyCode response:" + response.toString());
                if (response.getStatus() == 200) {
                    startRegister();
                } else {
                    ToastUtils.showToast(BaseApplication.getApp(), response.getMessage());
                }
            }
        }, new RxJava.ThrowableOnMainThread() {
            @Override
            public void accept(String t) {
                dismissLoading();
                ToastUtils.showToast(BaseApplication.getApp(), t);
            }
        });
    }

    private void startRegister() {
        Intent intent = null;
        if (mMesCodeType == PHONE_REGISTERED) {
            intent = new Intent(this, RegisterActivity.class);
            intent.putExtra(PNONE_NUMBER, mPhoneNumber.getText().toString().trim());
        } else if (mMesCodeType == FORGET_PASSWORD) {
            intent = new Intent(this, ResetPasswordActivity.class);
            intent.putExtra(PNONE_NUMBER, mPhoneNumber.getText().toString().trim());
        }
        startActivity(intent);
    }

    @Override
    public boolean beforeStart() {
        AppTools.collapseSoftInputMethod(this);
        String phoneNum = mPhoneNumber.getText().toString().trim();
        if (TextUtils.isEmpty(phoneNum)) {
            ToastUtil.showToast(this, "手机号不能为空");
            return false;
        }

        if (!Utils.isMobile(phoneNum)) {
            ToastUtils.showLongToast(this, "请输入正确格式的手机号");
            return false;
        }

        if (isNetworkAvailable()) {
            getMesCode();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void timeCountdown(int time) {
        if (mCodeButton != null) {
            mCodeButton.setText(time + "s");
        }
    }

    @Override
    public void stop() {
        mCodeButton.setText("发送验证码");
    }

    private static class SendMesCodeObservable implements ObservableOnSubscribe<Response> {

        private WeakReference<RegisterCodeActivity> mRegisterCodeActivity;

        public SendMesCodeObservable(RegisterCodeActivity registerCodeActivity) {
            mRegisterCodeActivity = new WeakReference<>(registerCodeActivity);
        }

        @Override
        public void subscribe(ObservableEmitter<Response> emitter) throws Exception {
            RegisterCodeActivity registerCodeActivity = mRegisterCodeActivity.get();
            if (registerCodeActivity == null) {
                return;
            }
            try {
                Map<String, String> map = new LinkedHashMap<>();
                Response response = null;
                if (registerCodeActivity.mMesCodeType == PHONE_REGISTERED) {
                    map.put("business_type", "phoneRegistered");
                    map.put("telephone", registerCodeActivity.mPhoneNumber.getText().toString().trim());
                    response = NetWorkDataManager.getMesCode(map);
                } else if (registerCodeActivity.mMesCodeType == FORGET_PASSWORD) {
                    map.put("business_type", "resetPassword");
                    map.put("telephone", registerCodeActivity.mPhoneNumber.getText().toString().trim());
                    response = NetWorkDataManager.getMesCode(map);
                }
                emitter.onNext(response);
            } catch (Exception e) {
                emitter.onError(e);
            }
            emitter.onComplete();
        }
    }

    private static class VerifyCodeObservable implements ObservableOnSubscribe<Response> {

        private WeakReference<RegisterCodeActivity> mRegisterCodeActivity;

        public VerifyCodeObservable(RegisterCodeActivity registerCodeActivity) {
            mRegisterCodeActivity = new WeakReference<>(registerCodeActivity);
        }

        @Override
        public void subscribe(ObservableEmitter<Response> emitter) throws Exception {
            RegisterCodeActivity registerCodeActivity = mRegisterCodeActivity.get();
            if (registerCodeActivity == null) {
                return;
            }
            try {
                Map<String, String> map = new LinkedHashMap<>();
                Response response = null;
                if (registerCodeActivity.mMesCodeType == PHONE_REGISTERED) {
                    map.put("business_type", "phoneRegistered");
                    map.put("telephone", registerCodeActivity.mPhoneNumber.getText().toString().trim());
                    map.put("code", registerCodeActivity.mEtCode.getText().toString().trim());
                    response = NetWorkDataManager.verifyCode(map);
                } else if (registerCodeActivity.mMesCodeType == FORGET_PASSWORD) {
                    map.put("business_type", "resetPassword");
                    map.put("telephone", registerCodeActivity.mPhoneNumber.getText().toString().trim());
                    map.put("code", registerCodeActivity.mEtCode.getText().toString().trim());
                    response = NetWorkDataManager.getMesCode(map);
                }
                Timber.d("VerifyCodeObservable response" + response);
                emitter.onNext(response);
            } catch (Exception e) {
                emitter.onError(e);
            }
            emitter.onComplete();
        }
    }
}
