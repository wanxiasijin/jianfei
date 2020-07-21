package com.chenganrt.smartSupervision.business.main;

import android.app.Activity;
import android.app.ActivityGroup;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;

import com.chenganrt.smartSupervision.business.electronic.activity.ElectornicActivity;
import com.chenganrt.smartSupervision.business.login.LoginActivity;
import com.chenganrt.smartSupervision.business.login.UserSP;
import com.chenganrt.smartSupervision.business.personal.PersonalCenterActivity;
import com.jaeger.library.StatusBarUtil;
import com.chenganrt.smartSupervision.R;
import com.chenganrt.smartSupervision.business.exchange.entry.ExchangeActivity;
import com.chenganrt.smartSupervision.business.home.HomeActivity;
import com.chenganrt.smartSupervision.common.manager.ActivityPageManager;
import com.chenganrt.smartSupervision.common.manager.LruCacheManager;
import com.chenganrt.smartSupervision.widget.dialog.MessageDialog;

import timber.log.Timber;

@SuppressWarnings("deprecation")
public class MainActivity extends ActivityGroup implements OnCheckedChangeListener {
    private static final int ELECTORNIC_REQUSET_CODE = 101;
    private TabHost tabHost;
    private RadioGroup radioGroup;
    private RadioButton mElectronicRadio;
    private RadioButton mHomeRadio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
//        StatusBarUtil.setColor(MainActivity.this, Color.YELLOW);
        initTab();
    }

    private void initTab() {
        tabHost = (TabHost) findViewById(R.id.tabhost);
        mHomeRadio = (RadioButton) findViewById(R.id.radio_home);
        mElectronicRadio = (RadioButton) findViewById(R.id.radio_electronic);
        tabHost.setup(getLocalActivityManager());
        Intent homeIntent = new Intent(this, HomeActivity.class);
        Intent categoryIntent = new Intent(this, ElectornicActivity.class);
        Intent exchangeIntent = new Intent(this, ExchangeActivity.class);
        Intent cartIntent = new Intent(this, PersonalCenterActivity.class);
        tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("tab1").setContent(homeIntent));
        tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("tab2").setContent(categoryIntent));
        tabHost.addTab(tabHost.newTabSpec("tab3").setIndicator("tab3").setContent(exchangeIntent));
        tabHost.addTab(tabHost.newTabSpec("tab4").setIndicator("tab4").setContent(cartIntent));
        radioGroup = (RadioGroup) super.findViewById(R.id.radioGroup_menu);
        radioGroup.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup arg0, int checkedId) {
        switch (checkedId) {
            case R.id.radio_home:
                tabHost.setCurrentTab(0);
                break;
            case R.id.radio_electronic:
                if (!TextUtils.isEmpty(UserSP.getInstance().getUserId(this))) {
                    Timber.d("UserId:" + UserSP.getInstance().getUserId(this));
                    tabHost.setCurrentTab(1);
                } else {
                    Intent loginIntent = new Intent(this, LoginActivity.class);
                    startActivityForResult(loginIntent, ELECTORNIC_REQUSET_CODE);
                }
                break;
            case R.id.radio_collection:
                tabHost.setCurrentTab(2);
                break;
            case R.id.radio_cart:
                tabHost.setCurrentTab(3);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        int userType = UserSP.getInstance().getUserType(this);
        Timber.d("userType1:" + userType);
        if (userType == 98) {
            mElectronicRadio.setVisibility(View.GONE);
        } else {
            mElectronicRadio.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Timber.d("requestCode:" + requestCode);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == ELECTORNIC_REQUSET_CODE) {
                int userType = UserSP.getInstance().getUserType(this);
                Timber.d("userType2:" + userType);
                if (userType == 98) {
                    mElectronicRadio.setVisibility(View.GONE);
                    tabHost.setCurrentTab(0);
                } else {
                    mElectronicRadio.setVisibility(View.VISIBLE);
                    tabHost.setCurrentTab(1);
                }
            }
        } else{
            mHomeRadio.setChecked(true);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        //TODO 接受intent
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            MessageDialog dialog = new MessageDialog(MainActivity.this,
                    getString(R.string.common_title_tips),
                    getString(R.string.common_confirm),
                    getString(R.string.common_cancel),
                    getString(R.string.common_exit));
            dialog.setBtn1ClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    LruCacheManager.getInstance().evictAll();
                    ActivityPageManager.getInstance().exit(MainActivity.this);
                }
            });
            dialog.show();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
