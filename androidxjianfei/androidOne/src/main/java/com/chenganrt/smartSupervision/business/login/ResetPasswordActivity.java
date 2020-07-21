package com.chenganrt.smartSupervision.business.login;

import android.content.Intent;
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
import com.chenganrt.smartSupervision.R;
import com.chenganrt.smartSupervision.business.app.BaseApplication;
import com.chenganrt.smartSupervision.business.electronic.util.AppTools;

import java.lang.ref.WeakReference;
import java.util.LinkedHashMap;
import java.util.Map;

import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import timber.log.Timber;

import static com.chenganrt.smartSupervision.business.login.RegisterCodeActivity.PNONE_NUMBER;

public class ResetPasswordActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.titleSub)
    TextView titleSub;
    @BindView(R.id.et_password)
    EditText mPassword;
    @BindView(R.id.et_password_again)
    EditText mPasswordAgain;
    @BindView(R.id.btn_accept)
    TextView mNest;
    private String mPhoneNumber;

    @Override
    public int initLayout() {
        return R.layout.activity_reset_password;
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    public void initConfig() {
        setStatusBar(toolbar);
        titleSub.setText("设置密码");
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            mPhoneNumber = intent.getStringExtra(PNONE_NUMBER);
        }
    }

    @OnClick({R.id.btn_accept})
    public void viewOnClick(View view) {
        switch (view.getId()) {
            case R.id.btn_accept:
                AppTools.collapseSoftInputMethod(this);
                String password1 = mPassword.getText().toString().trim();
                if (password1.isEmpty()) {
                    ToastUtils.showLongToast(this, "密码不能为空");
                    return;
                }

                if (password1.length() > 32 || password1.length() < 8) {
                    ToastUtils.showLongToast(this, "密码长度为8-32位");
                    return;
                }

                String regexZST = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,32}$";
                if (!password1.matches(regexZST)) {
                    ToastUtils.showLongToast(this, "密码必须包含大写字母、小写字母、数字和特殊字符");
                    return;
                }

                String password2 = mPasswordAgain.getText().toString().trim();
                if (password2.isEmpty()) {
                    ToastUtils.showLongToast(this, "确认密码不能为空");
                    return;
                }

                if (password2.length() > 32 || password2.length() < 8) {
                    ToastUtils.showLongToast(this, "确认密码长度为8-32位");
                    return;
                }

                if (!password2.matches(regexZST)) {
                    ToastUtils.showLongToast(this, "确认密码必须包含大写字母、小写字母、数字和特殊字符");
                    return;
                }

                if (!password1.equals(password2)) {
                    ToastUtils.showLongToast(this, "两次输入密码不一致");
                    return;
                }

                if (isNetworkAvailable()) {
                    showLoading();
                    registerAccount();
                }
        }
    }

    private void registerAccount() {
        RxJava.getInstance().create(new ResetPasswordObservable(this), new RxJava.ObserveOnMainThread<Response>() {
            @Override
            public void accept(Response response) {
                dismissLoading();
                Timber.d("registerAccount:" + response.toString());
                if (response.getStatus() == 200) {
                    ToastUtils.showToast(BaseApplication.getApp(), "设置成功");
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

    private static class ResetPasswordObservable implements ObservableOnSubscribe<Response> {

        private WeakReference<ResetPasswordActivity> mRegisterActivity;

        public ResetPasswordObservable(ResetPasswordActivity registerCodeActivity) {
            mRegisterActivity = new WeakReference<>(registerCodeActivity);
        }

        @Override
        public void subscribe(ObservableEmitter<Response> emitter) throws Exception {
            ResetPasswordActivity registerActivity = mRegisterActivity.get();
            if (registerActivity == null) {
                return;
            }
            try {
                Map<String, String> map = new LinkedHashMap<>();
                map.put("telephone", registerActivity.mPhoneNumber);
                map.put("password", AppTools.getMD5String(registerActivity.mPassword.getText().toString().trim()));
                Response response = NetWorkDataManager.resetPassword(map);
                emitter.onNext(response);
            } catch (Exception e) {
                emitter.onError(e);
            }
            emitter.onComplete();
        }
    }
}
