package com.chenganrt.smartSupervision.business.login;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.android.commonlib.mvp.BaseActivity;
import com.android.commonlib.mvp.BasePresenter;
import com.android.commonlib.okhttp.NetWorkDataManager;
import com.android.commonlib.okhttp.bean.Response;
import com.android.commonlib.okhttp.bean.User;
import com.android.commonlib.rxjava.RxJava;
import com.android.commonlib.utils.GsonUtil;
import com.android.commonlib.utils.ToastUtils;
import com.android.commonlib.utils.Utils;
import com.android.commonlib.utils.WindowUtil;
import com.apkfuns.logutils.LogUtils;
import com.chenganrt.smartSupervision.R;
import com.chenganrt.smartSupervision.business.app.BaseApplication;
import com.chenganrt.smartSupervision.business.electronic.util.AppTools;
import com.chenganrt.smartSupervision.business.electronic.util.ToastUtil;
import com.chenganrt.smartSupervision.business.home.RefreshEvent;
import com.chenganrt.smartSupervision.business.login.view.CountDownView;

import org.greenrobot.eventbus.EventBus;

import java.lang.ref.WeakReference;
import java.util.LinkedHashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import timber.log.Timber;

public class LoginActivity extends BaseActivity implements CountDownView.Countdown {
    public static final String MES_CODE_TYPE = "mes_code_type";
    public static final int LOGIN_REQUSET_CODE = 100;
    public static final int PHONE_REGISTERED = 0;
    public static final int PHONE_NUMBER_LOGIN = 1;
    public static final int FORGET_PASSWORD = 2;
    @BindView(R.id.iv_back)
    ImageView mBack;
    @BindView(R.id.et_account)
    EditText mAccount;
    @BindView(R.id.et_password)
    EditText mPassword;
    @BindView(R.id.forget_password)
    CountDownView mForgetPassword;
    @BindView(R.id.rb_accept)
    RadioButton mAccept;
    @BindView(R.id.btn_login)
    TextView mLogin;
    @BindView(R.id.btn_register)
    TextView mRegister;
    @BindView(R.id.message_login)
    TextView mMessageLogin;
    private boolean isPasswordLogin = true;
    private int i = 1;
    private User user;


    @Override
    public int initLayout() {
        return R.layout.activity_login;
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    public void initConfig() {
        getSupportActionBar().hide();
        WindowUtil.setStatusTextColor(true, getWindow(), getResources().getColor(com.android.commonlib.R.color.black));
        mAccount.setText(UserSP.getInstance().getUserName(this));
        mPassword.setText(!UserSP.getInstance().getPaw(this).isEmpty() ? UserSP.getInstance().getPaw(this) : "");
        mForgetPassword.setOnCountDownListener(this);
        mForgetPassword.start(this);
    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.iv_back, R.id.forget_password, R.id.btn_login, R.id.btn_register, R.id.message_login, R.id.tv_accept_content})
    public void viewOnClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.btn_login:
                AppTools.collapseSoftInputMethod(this);
                if (checkNull() && isNetworkAvailable()) {
                    showLoading();
                    login();
                }
                break;
            case R.id.btn_register:
                AppTools.collapseSoftInputMethod(this);
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.message_login:
                AppTools.collapseSoftInputMethod(this);
                if (isPasswordLogin) {
                    mMessageLogin.setText("密码登录");
                    mAccount.setText("");
                    mAccount.setHint("请输入手机号");
                    mPassword.setText("");
                    mPassword.setHint("请输入验证码");
                    mForgetPassword.setText("发送验证码");
                } else {
                    mForgetPassword.stopCountDown();
                    mMessageLogin.setText("短信登录");
                    mAccount.setText("");
                    mAccount.setHint("请输入账号");
                    mPassword.setText("");
                    mPassword.setHint("请输入密码");
                    mForgetPassword.setText("忘记密码");
                }
                isPasswordLogin = !isPasswordLogin;
                break;
            case R.id.tv_accept_content:
                startActivity(new Intent(this, AgreementActivity.class));
                break;
        }
    }

    private boolean checkNull() {
        String account = mAccount.getText().toString().trim();
        String password = mPassword.getText().toString().trim();
        if (isPasswordLogin) {
            if (TextUtils.isEmpty(account)) {
                ToastUtil.showToast(this, "账号不能为空");
                return false;
            }

            if (TextUtils.isEmpty(password)) {
                ToastUtil.showToast(this, "密码不能为空");
                return false;
            }
        } else {
            if (TextUtils.isEmpty(account)) {
                ToastUtil.showToast(this, "手机号不能为空");
                return false;
            }

            if (!Utils.isMobile(account)) {
                ToastUtils.showLongToast(this, "请输入正确格式的手机号");
                return false;
            }

            if (TextUtils.isEmpty(password)) {
                ToastUtil.showToast(this, "验证码不能为空");
                return false;
            }
        }

        if (!mAccept.isChecked()) {
            ToastUtil.showToast(this, "请勾选用户隐私协议");
            return false;
        }
        return true;
    }

    private void setData(User user) {
        Timber.d("token:" + UserSP.getInstance().getToken(this));
        UserSP.getInstance().setUserInfo(this, user);
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


    private void login() {
        RxJava.getInstance().create(new LoginObservable(this), new RxJava.ObserveOnMainThread<Response>() {
            @Override
            public void accept(Response response) {
                dismissLoading();
                Timber.d("getMesCode:" + response.toString());
                if (response.getStatus() == 200) {
                    ToastUtils.showToast(BaseApplication.getApp(), "登陆成功");
                    user = GsonUtil.JsonToObject(GsonUtil.toJson(response.getData()), User.class);
                    if (user != null) {
                        setData(user);
                        i++;
                        JPushInterface.setAlias(LoginActivity.this, i, user.getUser_id());
                        LogUtils.tag("bieming").d(user.getUser_id());
                     //   UserSP.getInstance().setIsRefresh(LoginActivity.this,true);

                        EventBus.getDefault().post(new RefreshEvent("position"));
                    }
                    finishActivity();
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

    private void finishActivity() {
        setResult(Activity.RESULT_OK);
        finish();
    }

    @Override
    public boolean beforeStart() {
        Timber.d("beforeStart isPasswordLogin" + isPasswordLogin);
        Timber.d("forget_password");
        AppTools.collapseSoftInputMethod(this);
        String account = mAccount.getText().toString().trim();
        if (isPasswordLogin) {
//            if (TextUtils.isEmpty(account)) {
//                ToastUtil.showToast(this, "账号不能为空");
//                return false;
//            }
        } else {
            if (TextUtils.isEmpty(account)) {
                ToastUtil.showToast(this, "手机号不能为空");
                return false;
            }

            if (!Utils.isMobile(account)) {
                ToastUtils.showLongToast(this, "请输入正确格式的手机号");
                return false;
            }
        }

        if (isPasswordLogin) {
            Intent intent = new Intent(this, RegisterCodeActivity.class);
            intent.putExtra(MES_CODE_TYPE, FORGET_PASSWORD);
            startActivity(intent);
        } else {
            if (isNetworkAvailable()) {
                getMesCode();
            } else {
                return false;
            }
        }
        return !isPasswordLogin;
    }

    @Override
    public void timeCountdown(int time) {
        if (mForgetPassword != null) {
            mForgetPassword.setText(time + "s");
        }
    }

    @Override
    public void stop() {
        Timber.d("stop");
        mForgetPassword.setText("发送验证码");
    }

    private static class SendMesCodeObservable implements ObservableOnSubscribe<Response> {

        private WeakReference<LoginActivity> mLoginActivity;

        public SendMesCodeObservable(LoginActivity loginActivity) {
            mLoginActivity = new WeakReference<>(loginActivity);
        }

        @Override
        public void subscribe(ObservableEmitter<Response> emitter) throws Exception {
            LoginActivity loginActivity = mLoginActivity.get();
            if (loginActivity == null) {
                return;
            }
            try {
                Map<String, String> map = new LinkedHashMap<>();
                map.put("business_type", "phoneNumLogin");
                map.put("telephone", loginActivity.mAccount.getText().toString().trim());
                Response response = NetWorkDataManager.getMesCode(map);
                emitter.onNext(response);
            } catch (Exception e) {
                emitter.onError(e);
            }
            emitter.onComplete();
        }
    }

    private static class LoginObservable implements ObservableOnSubscribe<Response> {

        private WeakReference<LoginActivity> mLoginActivity;

        public LoginObservable(LoginActivity loginActivity) {
            mLoginActivity = new WeakReference<>(loginActivity);
        }

        @Override
        public void subscribe(ObservableEmitter<Response> emitter) throws Exception {
            LoginActivity loginActivity = mLoginActivity.get();
            if (loginActivity == null) {
                return;
            }
            try {
                Map<String, String> map = new LinkedHashMap<>();
                Response response = null;
                if (loginActivity.isPasswordLogin) {
                    map.put("type", "0");
                    map.put("account", loginActivity.mAccount.getText().toString().trim());
                    map.put("password", AppTools.getMD5String(loginActivity.mPassword.getText().toString().trim()));
                    Timber.d("password:" + AppTools.getMD5String(loginActivity.mPassword.getText().toString().trim()));
                    response = NetWorkDataManager.login(map);
                } else {
                    map.put("type", "1");
                    map.put("telephone", loginActivity.mAccount.getText().toString().trim());
                    map.put("code", loginActivity.mPassword.getText().toString().trim());
                    response = NetWorkDataManager.login(map);
                }
                emitter.onNext(response);
            } catch (Exception e) {
                emitter.onError(e);
            }
            emitter.onComplete();
        }
    }
}
