package com.chenganrt.smartSupervision.business.login;

import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.android.commonlib.mvp.BaseActivity;
import com.android.commonlib.mvp.BasePresenter;
import com.chenganrt.smartSupervision.R;

import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.Unbinder;

public class AgreementActivity extends BaseActivity {

    public Unbinder unbinder = null;
    @BindView(R.id.webview)
    WebView mWebView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.titleSub)
    TextView titleSub;

    @Override
    public int initLayout() {
        return R.layout.layout_user_agreement;
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    public void initConfig() {
        setStatusBar(toolbar);
        titleSub.setText("用户隐私协议");
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.loadUrl("http://172.17.30.103/UserAgreement.html");
    }

    @Override
    public void initData() {

    }
}
