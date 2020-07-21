package com.chenganrt.smartSupervision.business.electronic.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.android.commonlib.utils.WindowUtil;
import com.android.commonlib.view.processdialog.HProgressDialog;
import com.chenganrt.smartSupervision.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.LayoutRes;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.Toolbar;
import okhttp3.Call;


public class BaseActivity extends AppCompatActivity implements View.OnClickListener, Toolbar.OnMenuItemClickListener, HProgressDialog.ICloseInterface {
    protected HProgressDialog progressDialog;
    protected MenuItem rightMenu;
    private boolean isWebActivity = false;
    protected TextView titleSub;
    protected Toolbar toolbar;
    protected List<Call> calls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        initToolBar();
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        initToolBar();
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        initToolBar();
    }

    public void initToolBar() {
        if (calls == null) calls = new ArrayList<>();
        if (findViewById(R.id.toolbar) == null) return;
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        titleSub = (TextView) findViewById(R.id.titleSub);
        toolbar.setNavigationIcon(R.drawable.arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
//        setSupportActionBar(toolbar);
        setDisplayHomeAsUpEnabled(true);
        WindowUtil.setStatusTextColor(true, getWindow(), getResources().getColor(R.color.black));
    }

    public void setDisplayHomeAsUpEnabled(boolean enabled) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(enabled);
            getSupportActionBar().setHomeButtonEnabled(enabled);
        }
    }

    public void setRightText(@StringRes int resID) {
        setRightText(getString(resID));
    }

    public void setRightText(CharSequence title) {
        ensureRightMenu();
        rightMenu.setTitle(title);
        invalidateOptionsMenu();
    }

    public void hideRightTextMenu() {
        ensureRightMenu();
        rightMenu.setVisible(false);
        invalidateOptionsMenu();
    }

    public void showRightTextMenu() {
        ensureRightMenu();
        rightMenu.setVisible(true);
        invalidateOptionsMenu();
    }

    @SuppressLint("RestrictedApi")
    private void ensureRightMenu() {
        if (rightMenu == null) {
            MenuBuilder builder = new MenuBuilder(this);
            rightMenu = builder.add(0, 0, 0, "");
        }
    }

    public void initWebToolBar() {
        isWebActivity = true;
    }

    public void setSubTitle(String text) {
        if (titleSub != null)
            titleSub.setText(text);
    }

    public void setSubTitle(int resID) {
        setSubTitle(getString(resID));
    }

    public void hideToolBar() {
        if (getSupportActionBar() != null) getSupportActionBar().hide();
    }

    public void showToolBar() {
        if (getSupportActionBar() != null) getSupportActionBar().show();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (menu.findItem(R.id.action_text) != null) {
            MenuItem item = menu.findItem(R.id.action_text);
            if (rightMenu != null) item.setVisible(rightMenu.isVisible());
            if (rightMenu != null) item.setTitle(rightMenu.getTitle());
            if (rightMenu != null) item.setIcon(rightMenu.getIcon());
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            return onHomePressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean onHomePressed() {
        try {
            if (isWebActivity) finish();
            else onBackPressed();
        } catch (IllegalStateException ignored) {
        }
        return true;
    }

    /**
     * 解决有些5.0手机 状态栏未透明问题
     */
    public void setTranslucentStatus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
    }

    public Context getContext() {
        return getApplicationContext();
    }

    public BaseActivity getActivity() {
        return this;
    }

    @Override
    public void onClick(View view) {
    }

    public void showLoadingDialog(int strId, boolean isClose) {
        showLoadingDialog(strId, isClose, null);
    }


    public void showLoadingDialog(int strId, boolean isClose, DialogInterface.OnCancelListener onCancelListener) {
        if (progressDialog == null) {
            HProgressDialog.Builder mBuilder = new HProgressDialog.Builder(this);
            mBuilder.setCanceledOnTouchOutside(false);
            mBuilder.setCancelable(onCancelListener != null);
            mBuilder.setCancelListener(onCancelListener);
            mBuilder.setMessage(strId);
            progressDialog = mBuilder.create();
        }
        //progressDialog.setMessage(getString(strId));
        progressDialog.setiCloseInterface(this);
        if (this.isFinishing()) return;
        if (!progressDialog.isShowing()) {
            progressDialog.show();
            progressDialog.setLoadText(getString(strId));
            progressDialog.setCloseVisible(isClose);
        }
    }

    public void dismissLoadingDialog() {
        if (progressDialog != null && progressDialog.isShowing() && (!isFinishing())) {
            progressDialog.dismiss();
        }
    }

    public void setProgressMsg(String msg) {
        progressDialog.setLoadText(msg);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }

    public void onResume() {
        super.onResume();
//        MobclickAgent.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
     //   MobclickAgent.onPause(this);
    }

    @Override
    public void closeLoading() {
        dismissLoadingDialog();
        if (calls != null && calls.size() > 0) {
            for (Call call : calls) {
                call.cancel();
            }
            calls.clear();
        }
    }
}
