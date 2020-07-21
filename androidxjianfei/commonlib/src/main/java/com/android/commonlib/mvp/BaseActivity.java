package com.android.commonlib.mvp;

import android.os.Bundle;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.commonlib.R;
import com.android.commonlib.utils.LibContext;
import com.android.commonlib.utils.ToastUtils;
import com.android.commonlib.utils.Utils;
import com.android.commonlib.utils.WindowUtil;
import com.android.commonlib.view.processdialog.HProgressDialog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity {
    public Unbinder unbinder = null;
    T presenter;
    public boolean isActive = true;
    private HProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(initLayout());
        this.presenter = initPresenter();
        unbinder = ButterKnife.bind(this);
        initUI();
    }

    protected void setDisplayHomeAsUpEnabled(boolean enabled) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(enabled);
            getSupportActionBar().setHomeButtonEnabled(enabled);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isActive = false;
        unbinder.unbind();
    }

    /**
     * 获取Presenter
     *
     * @return presenter
     */
    public T getPresenter() {
        return presenter;
    }

    /**
     * 加载布局
     *
     * @return
     */
    public abstract int initLayout();

    /**
     * 记载Presenter
     *
     * @return presenter
     */
    public abstract T initPresenter();

    public void initUI() {
        initConfig();
        initData();
    }

    public abstract void initConfig();

    public abstract void initData();

    public void initListener() {
    }

    public void setStatusBar(Toolbar toolbar) {
        toolbar.setNavigationIcon(R.drawable.arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        setDisplayHomeAsUpEnabled(true);
        WindowUtil.setStatusTextColor(true, getWindow(), getResources().getColor(R.color.black));
        getSupportActionBar().hide();
    }

    public void showLoading() {
        if (mDialog == null) {
            mDialog = HProgressDialog.createProgress(this).show();
        }
        mDialog.show();
    }

    /**
     * 关闭等待框
     */
    public void dismissLoading() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.hide();
        }
    }

    public boolean isNetworkAvailable() {
        if (Utils.isNetworkAvailable(LibContext.getInstance().getContext())) {
            return true;
        } else {
            ToastUtils.showToast(LibContext.getInstance().getContext(), "网络不可用");
            return false;
        }
    }

}
