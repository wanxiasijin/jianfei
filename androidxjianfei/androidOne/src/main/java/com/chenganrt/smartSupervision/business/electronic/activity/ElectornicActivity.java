package com.chenganrt.smartSupervision.business.electronic.activity;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.chenganrt.smartSupervision.R;
import com.chenganrt.smartSupervision.business.electronic.util.ToastUtil;
import com.chenganrt.smartSupervision.business.electronic.util.UserUtil;
import com.chenganrt.smartSupervision.business.electronic.mainctrl.CommandService;
import com.chenganrt.smartSupervision.business.electronic.mainctrl.MainControllerFactory;
import com.chenganrt.smartSupervision.business.electronic.permission.AbstractPermissionImp;
import com.chenganrt.smartSupervision.business.electronic.permission.PermissionDispatcher;
import com.chenganrt.smartSupervision.business.electronic.bill.BussnessFragment;
import com.chenganrt.smartSupervision.business.electronic.ui.PushEvent;
import com.chenganrt.smartSupervision.business.electronic.wheel.FAlertDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


public class ElectornicActivity extends BaseActivity {
    private static final String BROADCAST_FILTER = "mainpos";
    public static final long BACK_ENSURE_TIME = 3000;
    private long lasttime = -1;
    private static final String FG_TAG = "t";
    private PermissionDispatcher dispatcher;

//    private UpgradeProgress.UpgradeView upgradeView;
//    private UpgradeProgress progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTranslucentStatus();
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        EventBus.getDefault().register(this);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                CommandService.getInstant().excuteCommand(ElectornicActivity.this);
                Fragment fg = getCurFragment();
                if (fg == null || !(fg instanceof BussnessFragment)) {
                    Fragment ofg = new BussnessFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, ofg, FG_TAG).commitAllowingStateLoss();
                }
            }
        });

//        upgradeView = new UpgradeProgress.UpgradeView();
//        upgradeView.bind(this);
//        if ("1".equals(UserUtil.getInstance().getFenceStatus(getContext()))) showFenceDialog();
//        else checkUpdate();

    }

    private void checkUpdate() {
//        progress = new UpgradeProgress(getActivity());
//        progress.commonStart(upgradeView, new UpgradeProgress.OnUpgradeStopedListener() {
//            @Override
//            public void onUpgradStoped(int why) {
//                if (why == UpgradeProgress.STOP_BY_NEWEST) {
//
//                } else if (why == UpgradeProgress.STOP_BY_UPDAE) {
//                    getExternalStoragePermission();
//                } else if (why == UpgradeProgress.STOP_BY_EXIST) {
//                    if (UserUtil.getInstance().getUpdateTip(getActivity()) > 3) return;
//                    UserUtil.getInstance().setUpdateTip(getActivity(), UserUtil.getInstance().getUpdateTip(getActivity()) + 1);
//                    progress.showThinkDialog();
//                }
//            }
//        });
    }

    //围栏禁用提示
    private void showFenceDialog() {
        FAlertDialog.Builder builder = FAlertDialog.createMessage(getActivity(), "电子围栏:" + UserUtil.getInstance().getFenceName(getContext()) + "已关闭");
        builder.setPositiveButton(R.string.positive, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                checkUpdate();
                dialog.dismiss();
            }
        });
        builder.setCancelable(false);
        builder.show();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                CommandService.getInstant().excuteCommand(ElectornicActivity.this);
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void pushEventBus(PushEvent pushEvent) {
        if (pushEvent == null) return;
        if (pushEvent.getAction().equals("receiver")) {

        } else if (pushEvent.getAction().equals("open")) {
            if (getCurFragment() != null) {
                MainControllerFactory.getApi().callMain(getActivity(), "page://single.electronic.sz.com.electronicsingle.ui.activity.bill.BillDetailActivity?orderStatus=0&orderId=" + pushEvent.getNo());
            }
        }
    }

    private Fragment getCurFragment() {
        return getSupportFragmentManager().findFragmentByTag(FG_TAG);
    }

//    @Override
//    public boolean dispatchKeyEvent(KeyEvent event) {
//        if (KeyEvent.KEYCODE_BACK == event.getKeyCode()) {
//            if (KeyEvent.ACTION_UP == event.getAction()) {
//                onBackPressed();
//            }
//            return true;
//
//        } else {
//            return super.dispatchKeyEvent(event);
//        }
//    }

    private void getExternalStoragePermission() {
        dispatcher = new PermissionDispatcher(this, new AbstractPermissionImp.ExternalStoragePermission() {
            @Override
            public void onHasPermissions() {
//                if (progress != null) {
//                    progress.startLoawApp();
//                }
            }

            @Override
            public void onPermissionDenied() {
                ToastUtil.showToast(ElectornicActivity.this, getResources().getString(R.string.permission_common_rational));
            }

            @Override
            public void onNeverAskAgain() {
                ToastUtil.showToast(ElectornicActivity.this, getResources().getString(R.string.permissionSet));
                try {
                    Intent intent = new Intent(Settings.ACTION_MANAGE_APPLICATIONS_SETTINGS);
                    startActivity(intent);
                } catch (Exception ex) {

                }
            }
        });
        dispatcher.showPermissionWithCheck();
    }


//    @Override
//    public void onBackPressed() {
//        long curtime = Calendar.getInstance().getTimeInMillis();
//        if (curtime - lasttime < BACK_ENSURE_TIME) {
//            MainControllerFactory.getApi().finishApp(this);
//        } else {
//            lasttime = curtime;
//            ToastUtil.showToast(getApplicationContext(), R.string.click_to_quit);
//        }
//    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        dispatcher.onRequestPermissionsResult(requestCode, grantResults);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
      //  if (upgradeView != null) upgradeView.unbind();
    }
}
