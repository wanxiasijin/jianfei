package com.chenganrt.smartSupervision.business.electronic.bill;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.commonlib.view.flycotablayout.SlidingTabLayout;
import com.android.commonlib.view.flycotablayout.listener.OnTabSelectListener;
import com.chenganrt.smartSupervision.R;
import com.chenganrt.smartSupervision.business.electronic.activity.AnalyseActivity;
import com.chenganrt.smartSupervision.business.electronic.activity.RepairActivity;
import com.chenganrt.smartSupervision.business.electronic.ui.BillAllFragment;
import com.chenganrt.smartSupervision.business.electronic.ui.MainBaseFragment;
import com.chenganrt.smartSupervision.business.electronic.util.AppTools;
import com.chenganrt.smartSupervision.business.electronic.activity.LoginAction;
import com.chenganrt.smartSupervision.business.electronic.mainctrl.MainControllerFactory;
import com.chenganrt.smartSupervision.business.electronic.util.ToastUtil;
import com.chenganrt.smartSupervision.business.electronic.okhttp.UICallback;
import com.chenganrt.smartSupervision.business.electronic.util.UserUtil;
import com.chenganrt.smartSupervision.business.electronic.parse.entiy.AnalyseEntity;
import com.chenganrt.smartSupervision.business.electronic.ui.adapter.HomeAction;
import com.chenganrt.smartSupervision.business.electronic.ui.adapter.InnerPagerAdapter;
import com.chenganrt.smartSupervision.business.electronic.wheel.EditInputUtil;
import com.chenganrt.smartSupervision.business.electronic.wheel.FAlertDialog;
import com.chenganrt.smartSupervision.business.electronic.wheel.FBillDialog;
import com.chenganrt.smartSupervision.business.electronic.wheel.FSearchDialog;
import com.chenganrt.smartSupervision.business.login.UserSP;


import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Administrator on 2019/5/20.
 */

public class BussnessFragment extends MainBaseFragment implements OnTabSelectListener, ViewPager.OnPageChangeListener, BillAllFragment.INotice, InnerPagerAdapter.IFadapter {
    @BindView(R.id.slidingtablayout)
    SlidingTabLayout slidingTabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.layout_search)
    LinearLayout layoutSerach;
    @BindView(R.id.tvCancel)
    TextView tvCancel;
    @BindView(R.id.ed_content)
    EditText editText;
    private InnerPagerAdapter innerPagerAdapter;
    private HomeAction homeAction;
    private String[] titleArr;
    private int pos = 0;
    private String vehicleNo = "";

    private boolean mRunning = false;
    private final int FLIP_MSG = 1;
    private static final int DEFAULT_INTERVAL = 60 * 1000;

    private final int REQUEST_CODE_ANALYSE = 1092;
    private final int REQUEST_CODE_REPAIR = 1093;

    @Override
    public void initToolBar(View view) {
        super.initToolBar(view);
        setCenterTitle(R.string.main_title);
    }

    @Override
    public void setNavigationListener() {
        super.setNavigationListener();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSearchDialog();
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId() == R.id.action_static) {//统计分析
            startActivityForResult(new Intent(getActivity(), AnalyseActivity.class), REQUEST_CODE_ANALYSE);
        } else if (item.getItemId() == R.id.action_repair) {//补联单
            startActivityForResult(new Intent(getActivity(), RepairActivity.class), REQUEST_CODE_REPAIR);
        } else if (item.getItemId() == R.id.action_driver_quiet) {//退出
            showAskDialog();
        }
        return super.onMenuItemClick(item);
    }

    @Override
    public void titleLeftListener() {
        super.titleLeftListener();
        titleLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSearchBillDialog();
            }
        });
    }

    @Override
    public void titleRightListener() {
        super.titleRightListener();
        if ("1".equals(UserSP.getInstance().getUserType(getContext())))
            titleRight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showUnbindDialog();
                }
            });
    }

    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == FLIP_MSG) {
                if (mRunning) {
                    getData();
                    sendMessageDelayed(obtainMessage(FLIP_MSG), DEFAULT_INTERVAL);
                }
            }
        }
    };

    private void updateRunning(boolean isRunning) {
        if (isRunning != mRunning) {
            if (isRunning) {
                Message msg = mHandler.obtainMessage(FLIP_MSG);
                mHandler.sendMessageDelayed(msg, DEFAULT_INTERVAL);
            } else {
                mHandler.removeMessages(FLIP_MSG);
            }
            mRunning = isRunning;
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        initTool();
        initView();
        initData();
        initTabNum("0", "0", "0", "0", "0");
        edWatch();
        getBadgeCount();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bussness, container, false);
    }


    private void initTool() {
        toolbar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return titleRight.dispatchTouchEvent(motionEvent);
            }
        });
        String gg= String.valueOf(UserSP.getInstance().getUserType(getContext()));

        Log.d("mabi",gg);

        if ("1".equals(UserSP.getInstance().getUserType(getContext()))) {//司机
            setDisplayHomeAsUpEnabled(false);
            inflateMenu(R.menu.menu_home_driver);
            if ("0".equals(UserUtil.getInstance().getBindVehicle(getContext()))) {//已绑定
                setRightTitle(UserUtil.getInstance().getVehicleNo(getContext()));
                setTitleRightColor(R.color.green_theam);
                setLeftDrawable(getDrawable(R.drawable.btn_search_selector));
                setRightDrawable(R.drawable.btn_lock_selector);
            } else {//未绑定 首页只有设置
                showBindDialog();
            }
        } else if ("2".equals(UserSP.getInstance().getUserType(getContext()))) {//工地
            setDisplayHomeAsUpEnabled(true);
            setToolbarNavigation(R.drawable.btn_search_selector);
            inflateMenu(R.menu.menu_home_repair);
        } else {
            setDisplayHomeAsUpEnabled(true);
            setToolbarNavigation(R.drawable.btn_search_selector);
            inflateMenu(R.menu.menu_home_repair);
//            inflateMenu(R.menu.menu_home);
        }
    }

    private Drawable getDrawable(int drawableId) {
        Drawable drawable = getContext().getResources().getDrawable(drawableId);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        return drawable;
    }

    private void initView() {
        if ("1".equals(UserSP.getInstance().getUserType(getContext()))) {
            titleArr = getContext().getResources().getStringArray(R.array.bill_unstatus);
        } else if ("4".equals(UserSP.getInstance().getUserType(getContext()))) {
            titleArr = getContext().getResources().getStringArray(R.array.bill_status_except);
        } else {
            titleArr = getContext().getResources().getStringArray(R.array.bill_status);
        }

        slidingTabLayout.setOnTabSelectListener(this);
        viewPager.setOnPageChangeListener(this);
        innerPagerAdapter = new InnerPagerAdapter(getChildFragmentManager(), titleArr);
        innerPagerAdapter.setiFadapter(this);
        viewPager.setAdapter(innerPagerAdapter);
        viewPager.setCurrentItem(0);
        viewPager.setOffscreenPageLimit(titleArr.length);
        slidingTabLayout.setViewPager(viewPager);
        slidingTabLayout.onPageSelected(0);
        slidingTabLayout.setCurrentTab(0);

        EditInputUtil.setEditTextInhibitInputSpeChat(editText);
    }

    @OnClick({R.id.tvCancel})
    public void onViewClick(View view) {
        switch (view.getId()) {
            case R.id.tvCancel:
                AppTools.collapseSoftInputMethod(getActivity());
                layoutSerach.setVisibility(View.GONE);
                vehicleNo = "";
                getData();
                break;
        }
    }

    private void initData() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getData();
            }
        }, 200);
    }

    private void initTabNum(String tab1, String tab2, String tab3, String tab4, String tab5) {
        setTabTitle(0, tab1);
        setTabTitle(1, tab2);
        if (!"1".equals(UserSP.getInstance().getUserType(getContext()))) {
            setTabTitle(2, tab3);
            if ("4".equals(UserSP.getInstance().getUserType(getContext()))) {
                setTabTitle(3, tab4);
                setTabTitle(4, tab5);
            } else {
                setTabTitle(3, tab5);
            }
        }
    }

    private void setTabTitle(int position, String num) {
        if (slidingTabLayout == null) return;
        TextView tv = slidingTabLayout.getTitleView(position);
        tv.setSingleLine(false);
        tv.setText(String.format(getString(R.string.home_statuesFormat), getTabText(position), num));
    }

    private String getTabText(int position) {
        if (position == 0)
            return "1".equals(UserSP.getInstance().getUserType(getContext())) ?
                    getString(R.string.home_unfinish) : getString(R.string.home_confirm);
        if (position == 1)
            return "1".equals(UserSP.getInstance().getUserType(getContext())) ?
                    getString(R.string.home_finished) : getString(R.string.home_signed);
        if (position == 2) return getString(R.string.home_canceled);
        if (position == 3)
            return "4".equals(UserSP.getInstance().getUserType(getContext())) ?
                    getString(R.string.home_except) : "1".equals(UserUtil.getInstance().getAutoConfirm(getContext())) ?
                    getString(R.string.home_auto) : getString(R.string.home_unsigned);
        if (position == 4) return "1".equals(UserUtil.getInstance().getAutoConfirm(getContext())) ?
                getString(R.string.home_auto) : getString(R.string.home_unsigned);

        return "";
    }

    private void edWatch() {
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (TextUtils.isEmpty(editText.getText().toString().trim())) {
                        ToastUtil.showToast(getContext(), !"1".equals(UserSP.getInstance().getUserType(getContext())) ? "请输入车牌号码" : "请输入联单编号");
                    } else {
                        AppTools.collapseSoftInputMethod(getActivity());
                        vehicleNo = editText.getText().toString().trim();
                        getData();
                    }
                    return true;
                }
                return false;
            }
        });

    }


    public void getData() {
        if (!"1".equals(UserSP.getInstance().getUserType(getContext())))//非司机端
            getAnalyseData(AppTools.getStringDateShort(), AppTools.getStringDateShort(), "1", vehicleNo, false);
        innerPagerAdapter.refreshPageData(pos, vehicleNo);
    }

    private void getBadgeCount() {
        if (homeAction == null) homeAction = new HomeAction();
        homeAction.getMessageTotal(UserSP.getInstance().getUserId(getContext()), new UICallback(getActivity(), false) {
            @Override
            public void handleSuccess(Message msg) {
                String todoCount = getJsonFiled("TodoCount");//未读
                if (!TextUtils.isEmpty(todoCount)) {
                  //  BadgeUtil.setBadge(getContext(), Integer.valueOf(todoCount));
                }
            }
        });
    }

    private void getAnalyseData(String bTime, String eTime, String type, String Vehicle, boolean isLoading) {
        if (homeAction == null) homeAction = new HomeAction();
        homeAction.statisticAction(UserSP.getInstance().getUserName(getContext()),
                String.valueOf(UserSP.getInstance().getUserType(getContext())),
                bTime,
                eTime,
                type,
                Vehicle,
                new UICallback(getActivity(), isLoading) {
                    @Override
                    public void handleSuccess(Message msg) {
                        AnalyseEntity analyseEntity = (AnalyseEntity) msg.obj;
                        if (analyseEntity == null) return;
                        initTabNum(String.valueOf(analyseEntity.getToSign()),
                                String.valueOf(analyseEntity.getConfirmed()),
                                String.valueOf(analyseEntity.getCancelled()),
                                String.valueOf(analyseEntity.getSoilExcepted()),
                                "1".equals(UserUtil.getInstance().getAutoConfirm(getContext())) ? String.valueOf(analyseEntity.getAutoConfirmed()) : String.valueOf(analyseEntity.getUnsigned()));
                    }

                    @Override
                    public void handleFailure(Message msg) {
                        super.handleFailure(msg);
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
        LoginAction loginAction = new LoginAction();
        loginAction.logOut(UserSP.getInstance().getUserId(getContext()), new UICallback(getActivity(), true) {
            @Override
            public void handleSuccess(Message msg) {
                String userName = UserSP.getInstance().getUserName(getContext());
                MainControllerFactory.getApi().needReloain(getActivity());
                UserUtil.getInstance().clear(getActivity());
                UserUtil.getInstance().setUserName(getContext(), userName);//要保留用户名
                getActivity().finish();
            }
        });
    }

    private void showBindDialog() {
        FBillDialog.Builder builder1 = FBillDialog.createCancelDialog(getActivity());
        builder1.setOnItemChooseListener(new FBillDialog.OnItemChooseListener() {
            @Override
            public void onNegativeListener(Dialog dialog) {
                dialog.dismiss();
            }

            @Override
            public void onPositiveListener(Dialog dialog, String content) {
                if (TextUtils.isEmpty(content)) {
                    ToastUtil.showToast(getContext(), "请输入车牌号");
                    return;
                }
                bindCarNumber(content);
                dialog.dismiss();
            }
        });
        builder1.setCancelable(false);
        builder1.show();
    }

    /**
     * dialog车牌号输入
     */
    private void showSearchDialog() {
        FSearchDialog.Builder builder1 = FSearchDialog.createSearchDialog(getActivity());
        builder1.setOnItemChooseListener(new FSearchDialog.OnItemChooseListener() {
            @Override
            public void onNegativeListener(Dialog dialog) {
                dialog.dismiss();
            }

            @Override
            public void onPositiveListener(Dialog dialog, String content) {
                if (TextUtils.isEmpty(content)) {
                    ToastUtil.showToast(getContext(), "请输入车牌号");
                } else {
                    dialog.dismiss();
                    AppTools.collapseSoftInputMethod(getActivity());
                    layoutSerach.setVisibility(View.VISIBLE);
                    editText.setText(content);
                    vehicleNo = content;
                    getData();
                }
            }
        });
        //builder1.setCancelable(false);
        builder1.show();
    }

    private void showSearchBillDialog() {
        FSearchDialog.Builder builder1 = FSearchDialog.createSearchDialog(getActivity());
        builder1.setOnItemChooseListener(new FSearchDialog.OnItemChooseListener() {
            @Override
            public void onNegativeListener(Dialog dialog) {
                dialog.dismiss();
            }

            @Override
            public void onPositiveListener(Dialog dialog, String content) {
                if (TextUtils.isEmpty(content)) {
                    ToastUtil.showToast(getContext(), "请输入联单编号");
                } else {
                    dialog.dismiss();
                    AppTools.collapseSoftInputMethod(getActivity());
                    layoutSerach.setVisibility(View.VISIBLE);
                    editText.setHint("请输入联单编号");
                    editText.setText(content);
                    vehicleNo = content;
                    getData();
                }
            }
        });
        //builder1.setCancelable(false);
        builder1.show();
        builder1.setEdHint("请输入联单编号");
    }

    //绑定车牌号
    private void bindCarNumber(final String carNo) {
        HomeAction homeAction = new HomeAction();
        homeAction.bindCarAction(UserUtil.getInstance().getUserName(getContext()), carNo, new UICallback(getActivity(), true) {
            @Override
            public void handleSuccess(Message msg) {
                ToastUtil.showToast(getContext(), "绑定成功");
                UserUtil.getInstance().setVehicleNo(getContext(), carNo);
                UserUtil.getInstance().setBindVehicle(getContext(), "0");//0为绑定
                titleRight.setText(carNo);
                //initTool();
                getData();
            }

            @Override
            public void handleFailure(Message msg) {
                super.handleFailure(msg);
                showBindDialog();
            }
        });
    }

    //解绑车牌号
    private void unbindCarNumber() {
        HomeAction homeAction = new HomeAction();
        homeAction.unbindCarAction(UserUtil.getInstance().getUserName(getContext()), new UICallback(getActivity(), true) {
            @Override
            public void handleSuccess(Message msg) {
                ToastUtil.showToast(getContext(), "解绑成功");
                UserUtil.getInstance().setVehicleNo(getContext(), "");
                UserUtil.getInstance().setBindVehicle(getContext(), "1");
                //initTool();
                titleRight.setText("");
                getData();
                showBindDialog();
            }
        });
    }

    private void showUnbindDialog() {
        FAlertDialog.Builder builder = FAlertDialog.createMessage(getActivity(), "你确定解绑车辆吗?");
        builder.setNegativeButton(R.string.negative, null);
        builder.setPositiveButton(R.string.positive, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                unbindCarNumber();
            }
        });
        builder.show();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        pos = position;
        getData();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onTabSelect(int position) {
        pos = position;
    }

    @Override
    public void onTabReselect(int position) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_REPAIR && resultCode == getActivity().RESULT_OK) {
            viewPager.setCurrentItem(1);
            slidingTabLayout.onPageSelected(1);
            slidingTabLayout.setCurrentTab(1);
        }
    }

    @Override
    public void getPageFragment(BillAllFragment billAllFragment) {
        if (billAllFragment != null) billAllFragment.setInterfaceNotece(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateRunning(true);
    }

    @Override
    public void onPause() {
        super.onPause();
        updateRunning(false);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void noticeRefresh(int position) {
        getAnalyseData(AppTools.getStringDateShort(), AppTools.getStringDateShort(), "1", vehicleNo, false);
    }

    @Override
    public void noticeTitle(int position, int size) {
        if ("1".equals(UserSP.getInstance().getUserType(getContext()))) {
            if (this.pos == position) {
                setTabTitle(position, size + "");
            }
        }
    }
}
