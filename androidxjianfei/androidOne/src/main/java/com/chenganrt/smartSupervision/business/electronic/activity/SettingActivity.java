package com.chenganrt.smartSupervision.business.electronic.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.provider.Settings;

import androidx.annotation.NonNull;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.chenganrt.smartSupervision.R;
import com.chenganrt.smartSupervision.business.electronic.util.AppTools;
import com.chenganrt.smartSupervision.business.electronic.util.ToastUtil;
import com.chenganrt.smartSupervision.business.electronic.okhttp.UICallback;
import com.chenganrt.smartSupervision.business.electronic.util.UserUtil;
import com.chenganrt.smartSupervision.business.electronic.permission.AbstractPermissionImp;
import com.chenganrt.smartSupervision.business.electronic.permission.PermissionDispatcher;
import com.chenganrt.smartSupervision.business.electronic.ui.adapter.HomeAction;
import com.chenganrt.smartSupervision.business.electronic.wheel.FAlertDialog;
import com.chenganrt.smartSupervision.business.electronic.wheel.PopWindow;
import com.chenganrt.smartSupervision.business.login.UserSP;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;



public class SettingActivity extends BaseActivity {
    @BindView(R.id.tv_company_name)
    TextView tvCompany;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_version)
    TextView tvVersion;
    @BindView(R.id.tab_message)
    TextView tabNum;
    @BindView(R.id.tv_fence_name)
    TextView fenceName;
    @BindView(R.id.fence_status)
    TextView fenceStatus;
    @BindView(R.id.image_red)
    ImageView imageRed;
    private HomeAction homeAction;
    private PermissionDispatcher dispatcher;
    private final int REQUEST_CODE = 1221;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        setSubTitle(R.string.set_title);
        initView();
        getMessageTotal();
        checkUpdate();
    }

    private void initView() {
        tvName.setText(UserUtil.getInstance().getRealName(getContext()));
        tvCompany.setText("所属公司: " + UserUtil.getInstance().getCompanyName(getContext()));
        fenceName.setText("电子围栏: " + UserUtil.getInstance().getFenceName(getContext()));

        if(TextUtils.isEmpty(UserUtil.getInstance().getFenceStatus(getContext()))) {
            fenceStatus.setText("未设置");
        }else if("0".equals(UserUtil.getInstance().getFenceStatus(getContext()))){
            fenceStatus.setText("已开启");
        }else if("1".equals(UserUtil.getInstance().getFenceStatus(getContext()))){
            fenceStatus.setText("已关闭");
        }

        tvVersion.setText("V" + AppTools.getAppVersionName(getContext()));

        if (AppTools.isDebug()) {
            CheckBox cbNet = (CheckBox) findViewById(R.id.cb_net_sel);
            cbNet.setChecked(getSharedPreferences("debug", 0).getBoolean("enable_net_sel", false));
            cbNet.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    getSharedPreferences("debug", 0).edit().putBoolean("enable_net_sel", isChecked).commit();
                }
            });
            //cbNet.setVisibility(View.VISIBLE);
            cbNet.setVisibility(View.GONE);
        }

    }


    @OnClick({R.id.layout_message, R.id.layout_paw, R.id.layout_about, R.id.tv_sign_out, R.id.layout_version, R.id.fence_status})
    public void onViewClick(View view) {
//        switch (view.getId()) {
//            case R.id.layout_message:
//                startActivityForResult(new Intent(getActivity(), MessageNotificationActivity.class), REQUEST_CODE);
//                break;
//            case R.id.layout_paw:
//                startActivity(ResetPasswordActivity.startAct(getActivity(), "", ResetPasswordActivity.PAW_RESET));
//                break;
//            case R.id.layout_about:
//                startActivity(new Intent(getActivity(), AboutActivity.class));
//                break;
//            case R.id.tv_sign_out:
//                showAskDialog();
//                break;
//            case R.id.layout_version:
//                checkVersion();
//                break;
//            case R.id.fence_status:
//                //showPopwindow(fenceStatus, UserUtil.getInstance().getFenceName(getContext()));
//                break;
//        }
    }

    private void showPopwindow(View v, String content) {
        PopWindow p = new PopWindow();
        View contentView = LayoutInflater.from(getContext()).inflate(R.layout.popwindow_tip,
                null, false);
        TextView textView = (TextView) contentView.findViewById(R.id.txt);
        textView.setText(content);
        p.setPopView(getContext(), v, contentView);
        p.showPopWindow(v, 0, 2);
        p.dismissPopWindow(new PopWindow.IPopwindow() {
            @Override
            public void popDismiss(PopupWindow popupWindow) {
            }
        });
    }

//    private UpgradeProgress.UpgradeView upgradeView = new UpgradeProgress.UpgradeViewWithDialog();
//    private UpgradeProgress progress;

    private void checkVersion() {
//        progress = new UpgradeProgress(getActivity());
//        upgradeView.bind(this);
//        progress.commonStart(upgradeView, new UpgradeProgress.OnUpgradeStopedListener() {
//            @Override
//            public void onUpgradStoped(int why) {
//                switch (why) {
//                    case UpgradeProgress.STOP_BY_UPDAE:
//                        getExternalStoragePermission();
//                        break;
//                    case UpgradeProgress.STOP_BY_NEWEST:
//                        ToastUtil.showToast(getContext(), "已经是最新版本");
//                        break;
//                    case UpgradeProgress.STOP_BY_EXIST:
//                        progress.showThinkDialog();
//                        break;
//                    default:
//                        break;
//                }
//            }
//        });

    }

    private void checkUpdate() {
//        UpdateAction updateAction = new UpdateAction();
//        updateAction.UpdateVersion(getContext(), new UICallback(getActivity(), false) {
//            @Override
//            public void handleSuccess(Message msg) {
//                VersionEntity versionEntity = ((VersionEntity) msg.obj);
//                if (versionEntity == null) return;
//                imageRed.setVisibility(TextUtils.isEmpty(versionEntity.getUrl()) ? View.GONE : View.VISIBLE);
//            }
//
//            @Override
//            public void handleFailure(Message msg) {
//                //super.handleFailure(msg);
//            }
//        });
    }


    private void getMessageTotal() {
        if (homeAction == null) homeAction = new HomeAction();
        homeAction.getMessageTotal(UserSP.getInstance().getUserId(getContext()), new UICallback(getActivity(), false) {
            @Override
            public void handleSuccess(Message msg) {
                String todoCount = getJsonFiled("TodoCount");//未读
                if (!TextUtils.isEmpty(todoCount)) {
                    if ("0".equals(todoCount)) {
                        tabNum.setVisibility(View.GONE);
                    } else {
                        tabNum.setVisibility(View.VISIBLE);
                        tabNum.setText(todoCount);
                     //   BadgeUtil.setBadge(getContext(), Integer.valueOf(todoCount));
                    }
                }
            }
        });
    }


    private void showAskDialog() {
        FAlertDialog.Builder builder = FAlertDialog.createMessage(getActivity(), "确定要退出吗?");
        builder.setNegativeButton(R.string.negative, null);
        builder.setPositiveButton(R.string.positive, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                logOut();
            }
        });
        builder.show();
    }

    private void logOut() {
//        LoginAction loginAction = new LoginAction();
//        loginAction.logOut(UserUtil.getInstance().getUserId(getContext()), new UICallback(getActivity(), true) {
//            @Override
//            public void handleSuccess(Message msg) {
//                String userName = UserUtil.getInstance().getUserName(getContext());
//                MainControllerFactory.getApi().needReloain(getActivity());
//                UserUtil.getInstance().clear(getActivity());
//                UserUtil.getInstance().setUserName(getContext(), userName);//要保留用户名
//                finish();
//            }
//        });
    }

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
                ToastUtil.showToast(getContext(), getResources().getString(R.string.permission_common_rational));
            }

            @Override
            public void onNeverAskAgain() {
                ToastUtil.showToast(getContext(), getResources().getString(R.string.permissionSet));
                try {
                    Intent intent = new Intent(Settings.ACTION_MANAGE_APPLICATIONS_SETTINGS);
                    startActivity(intent);
                } catch (Exception ex) {

                }
            }
        });
        dispatcher.showPermissionWithCheck();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        dispatcher.onRequestPermissionsResult(requestCode, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            getMessageTotal();
        }
    }
}
