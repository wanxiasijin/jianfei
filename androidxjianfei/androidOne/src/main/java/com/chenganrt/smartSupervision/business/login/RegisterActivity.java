package com.chenganrt.smartSupervision.business.login;

import android.content.Intent;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.TextUtils;
import android.text.style.TextAppearanceSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
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

public class RegisterActivity extends BaseActivity implements CountDownView.Countdown {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.titleSub)
    TextView titleSub;
    @BindView(R.id.et_account)
    EditText mAccount;
    @BindView(R.id.et_password)
    EditText mPassword;
    @BindView(R.id.et_password_again)
    EditText mPasswordAgain;
    @BindView(R.id.et_company)
    EditText mCompany;
    @BindView(R.id.et_number)
    EditText mPhoneNum;
    @BindView(R.id.et_code)
    EditText mCode;
    @BindView(R.id.tv_verification_code)
    CountDownView mCodeButton;
    @BindView(R.id.btn_nest)
    TextView mNest;
    @BindView(R.id.rb_accept)
    RadioButton mAccept;
    @BindView(R.id.tv_accept_content)
    TextView mAcceptment;

    @Override
    public int initLayout() {
        return R.layout.avtivity_register;
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    public void initConfig() {
        setStatusBar(toolbar);
        titleSub.setText("手机注册");
        mCodeButton.setOnCountDownListener(this);
        mCodeButton.start(this);
        SpannableString styledText1 = new SpannableString("用户名(首字母为英文或下划线，6-30位字符)");
        styledText1.setSpan(new TextAppearanceSpan(this, R.style.register_hint_Style2), 0, 3, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        styledText1.setSpan(new TextAppearanceSpan(this, R.style.register_hint_Style1), 3, 23, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        mAccount.setHint(new SpannedString(styledText1));

        SpannableString styledText2 = new SpannableString("密码(8-32位字符；必须包含大写字母、小写字母、数字和特殊字符)");
        styledText2.setSpan(new TextAppearanceSpan(this, R.style.register_hint_Style2), 0, 2, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        styledText2.setSpan(new TextAppearanceSpan(this, R.style.register_hint_Style1), 2, 33, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        mPassword.setHint(new SpannedString(styledText2));
    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.btn_nest, R.id.tv_accept_content})
    public void viewOnClick(View view) {
        switch (view.getId()) {
            case R.id.tv_accept_content:
                startActivity(new Intent(this, AgreementActivity.class));
                break;
            case R.id.btn_nest:
                AppTools.collapseSoftInputMethod(this);
                String account = mAccount.getText().toString().trim();
                if (account.isEmpty()) {
                    ToastUtils.showLongToast(this, "用户名不能为空");
                    return;
                }
                String accountFirst = "^[A-Za-z_]+$";
                if (!account.substring(0, 1).matches(accountFirst)) {
                    ToastUtils.showLongToast(this, "账户名首字母只能是英文或者下划线");
                    return;
                }
                if (account.length() > 30 || account.length() < 6) {
                    ToastUtils.showLongToast(this, "账户名长度为6-30位");
                    return;
                }
                String password = mPassword.getText().toString().trim();
                if (password.isEmpty()) {
                    ToastUtils.showLongToast(this, "密码不能为空");
                    return;
                }

                if (password.length() > 32 || password.length() < 8) {
                    ToastUtils.showLongToast(this, "密码长度为8-32位");
                    return;
                }

                String regexZST = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,32}$";
                if (!password.matches(regexZST)) {
                    ToastUtils.showLongToast(this, "密码必须包含大写字母、小写字母、数字和特殊字符");
                    return;
                }

                String passwordAgain = mPasswordAgain.getText().toString().trim();
                if (passwordAgain.isEmpty()) {
                    ToastUtils.showLongToast(this, "确认密码不能为空");
                    return;
                }

                if (passwordAgain.length() > 32 || passwordAgain.length() < 8) {
                    ToastUtils.showLongToast(this, "确认密码长度为8-32位");
                    return;
                }

                if (!passwordAgain.matches(regexZST)) {
                    ToastUtils.showLongToast(this, "确认密码必须包含大写字母、小写字母、数字和特殊字符");
                    return;
                }

                if (!password.equals(passwordAgain)) {
                    ToastUtils.showLongToast(this, "两次输入密码不一致");
                    return;
                }

                String company = mCompany.getText().toString().trim();
                if (company.isEmpty()) {
                    ToastUtils.showLongToast(this, "单位名称不能为空");
                    return;
                }
                String phoneNum = mPhoneNum.getText().toString().trim();
                if (phoneNum.isEmpty()) {
                    ToastUtils.showLongToast(this, "手机号码不能为空");
                    return;
                }
                String code = mCode.getText().toString().trim();
                if (code.isEmpty()) {
                    ToastUtils.showLongToast(this, "验证码不能为空");
                    return;
                }
                if (!Utils.isMobile(phoneNum)) {
                    ToastUtils.showLongToast(this, "手机号码格式不正确");
                    return;
                }
                if (mAccept.isChecked()) {
                    if (isNetworkAvailable()) {
                        showLoading();
                        registerAccount();
                    }
                } else {
                    ToastUtils.showLongToast(this, "请勾选用户隐私协议");
                }
        }
    }

    private void registerAccount() {
        RxJava.getInstance().create(new RegisterAccountObservable(this), new RxJava.ObserveOnMainThread<Response>() {
            @Override
            public void accept(Response response) {
                dismissLoading();
                Timber.d("registerAccount:" + response.toString());
                if (response.getStatus() == 200) {
                    ToastUtils.showToast(BaseApplication.getApp(), "注册成功");
                    startLogin();
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

    private void startLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public boolean beforeStart() {
        AppTools.collapseSoftInputMethod(this);
        String phoneNum = mPhoneNum.getText().toString().trim();
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
                Timber.d("verifyCode response:" + response.toString());
                if (response.getStatus() == 200) {
                    registerAccount();
                } else {
                    dismissLoading();
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


    private static class SendMesCodeObservable implements ObservableOnSubscribe<Response> {

        private WeakReference<RegisterActivity> mRegisterActivity;

        public SendMesCodeObservable(RegisterActivity registerActivity) {
            mRegisterActivity = new WeakReference<>(registerActivity);
        }

        @Override
        public void subscribe(ObservableEmitter<Response> emitter) throws Exception {
            RegisterActivity registerActivity = mRegisterActivity.get();
            if (registerActivity == null) {
                return;
            }
            try {
                Map<String, String> map = new LinkedHashMap<>();
                map.put("business_type", "phoneRegistered");
                map.put("telephone", registerActivity.mPhoneNum.getText().toString().trim());
                Response response = NetWorkDataManager.getMesCode(map);
                emitter.onNext(response);
            } catch (Exception e) {
                emitter.onError(e);
            }
            emitter.onComplete();
        }
    }

    private static class VerifyCodeObservable implements ObservableOnSubscribe<Response> {

        private WeakReference<RegisterActivity> mRegisterCodeActivity;

        public VerifyCodeObservable(RegisterActivity registerActivity) {
            mRegisterCodeActivity = new WeakReference<>(registerActivity);
        }

        @Override
        public void subscribe(ObservableEmitter<Response> emitter) throws Exception {
            RegisterActivity registerActivity = mRegisterCodeActivity.get();
            if (registerActivity == null) {
                return;
            }
            try {
                Map<String, String> map = new LinkedHashMap<>();
                map.put("business_type", "phoneRegistered");
                map.put("telephone", registerActivity.mPhoneNum.getText().toString().trim());
                map.put("code", registerActivity.mCode.getText().toString().trim());
                Response response = NetWorkDataManager.verifyCode(map);
                Timber.d("VerifyCodeObservable response" + response);
                emitter.onNext(response);
            } catch (Exception e) {
                emitter.onError(e);
            }
            emitter.onComplete();
        }
    }

    private static class RegisterAccountObservable implements ObservableOnSubscribe<Response> {

        private WeakReference<RegisterActivity> mRegisterActivity;

        public RegisterAccountObservable(RegisterActivity registerActivity) {
            mRegisterActivity = new WeakReference<>(registerActivity);
        }

        @Override
        public void subscribe(ObservableEmitter<Response> emitter) throws Exception {
            RegisterActivity registerActivity = mRegisterActivity.get();
            if (registerActivity == null) {
                return;
            }
            try {
                Map<String, String> map = new LinkedHashMap<>();
                map.put("telephone", registerActivity.mPhoneNum.getText().toString().trim());
                map.put("account", registerActivity.mAccount.getText().toString().trim());
                map.put("password", AppTools.getMD5String(registerActivity.mPassword.getText().toString().trim()));
                map.put("company", registerActivity.mCompany.getText().toString().trim());
                map.put("code", registerActivity.mCode.getText().toString().trim());
                Response response = NetWorkDataManager.registerAccount(map);
                emitter.onNext(response);
            } catch (Exception e) {
                emitter.onError(e);
            }
            emitter.onComplete();
        }
    }
}
