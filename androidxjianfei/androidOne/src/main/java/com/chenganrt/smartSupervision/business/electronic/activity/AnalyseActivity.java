package com.chenganrt.smartSupervision.business.electronic.activity;

import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.chenganrt.smartSupervision.R;
import com.chenganrt.smartSupervision.business.electronic.util.AppConstant;
import com.chenganrt.smartSupervision.business.electronic.util.AppTools;
import com.chenganrt.smartSupervision.business.electronic.RecycleItemDecor;
import com.chenganrt.smartSupervision.business.electronic.util.ToastUtil;
import com.chenganrt.smartSupervision.business.electronic.okhttp.UICallback;
import com.chenganrt.smartSupervision.business.electronic.util.UserUtil;
import com.chenganrt.smartSupervision.business.electronic.parse.entiy.AnalyseDetailEntity;
import com.chenganrt.smartSupervision.business.electronic.parse.entiy.AnalyseEntity;
import com.chenganrt.smartSupervision.business.electronic.ui.adapter.AnalyseAdapter;
import com.chenganrt.smartSupervision.business.electronic.ui.adapter.HomeAction;
import com.chenganrt.smartSupervision.business.electronic.wheel.FDatePickerDialog;
import com.chenganrt.smartSupervision.business.electronic.wheel.PopWindow;
import com.chenganrt.smartSupervision.business.login.UserSP;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Administrator on 2019/5/29.
 */

public class AnalyseActivity extends BaseActivity implements AnalyseAdapter.IClickitem, OnRefreshListener, OnLoadMoreListener, RadioGroup.OnCheckedChangeListener {

    @BindView(R.id.radiogroup)
    RadioGroup radioGroup;
    @BindView(R.id.smarfresh)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.radio_more)
    RadioButton radioMore;
    private AnalyseAdapter analyseAdapter;
    private List<PieEntry> pie = new ArrayList<>();
    private List<AnalyseDetailEntity.AnalyseContentEntity> contentEntities = new ArrayList<>();
    private HomeAction homeAction;
    private int page = 1;
    private final String pageSize = "20";
    private final int REFRESH = 0, LOADMORE = 1;
    private String bDate, eDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_analyse);
        getSupportActionBar().hide();
        ButterKnife.bind(this);
        setSubTitle(R.string.statis_title);
        initView();
        initData();
        initRequired(AppTools.getStringDateShort(),
                AppTools.getStringDateShort());
    }

    private void initView() {
        radioGroup.setOnCheckedChangeListener(this);
        smartRefreshLayout.setOnLoadMoreListener(this);
        smartRefreshLayout.setOnRefreshListener(this);
        smartRefreshLayout.setEnableOverScrollDrag(true);
        smartRefreshLayout.setEnableLoadMoreWhenContentNotFull(false);//内容不满一页时候启用加载更多
    }

    private void initData() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return true;
            }
        };
        analyseAdapter = new AnalyseAdapter(getContext(), "4".equals(UserSP.getInstance().getUserType(getContext())),
                "1".equals(UserUtil.getInstance().getAutoConfirm(getContext())));
        analyseAdapter.setiClickitem(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new RecycleItemDecor(0));
        recyclerView.setAdapter(analyseAdapter);
    }

    @OnClick({R.id.radio_today, R.id.radio_yesterday, R.id.radio_week, R.id.radio_more})
    public void onViewClick(View view) {
        page = 1;
        switch (view.getId()) {
            case R.id.radio_today://今天
                radioMore.setText(getString(R.string.statis_more));
                radioMore.setTextColor(getResources().getColor(R.color.black_sd3));
                initRequired(AppTools.getStringDateShort(),
                        AppTools.getStringDateShort());
                break;
            case R.id.radio_yesterday://昨天
                radioMore.setText(getString(R.string.statis_more));
                radioMore.setTextColor(getResources().getColor(R.color.black_sd3));
                initRequired(AppTools.getDownTime(-1),
                        AppTools.getDownTime(-1));
                break;
            case R.id.radio_week://最近7天
                radioMore.setText(getString(R.string.statis_more));
                radioMore.setTextColor(getResources().getColor(R.color.black_sd3));
                initRequired(AppTools.getDownTime(-6),
                        AppTools.getStringDateShort());
                break;
            case R.id.radio_more://更多
                showDataWheel();
                break;
        }
    }

    private void initRequired(String bTime, String eTime) {
        this.bDate = bTime;
        this.eDate = eTime;
        getAnalyseData(bDate, eDate);//饼状
        getAnalyDetail(REFRESH, bDate, eDate, "", true);//列表
    }


    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int id) {
        switch (id) {
        }
    }

    private void setPieChart(AnalyseEntity analyseEntity) {
        /*//test data
        analyseEntity = new AnalyseEntity();
        //待签认
        analyseEntity.setToSign(521);
        analyseEntity.setToSignRate("76.62");

        //已确认
        analyseEntity.setConfirmed(2);
        analyseEntity.setConfirmedRate("0.29");

        //已取消
        analyseEntity.setCancelled(1);
        analyseEntity.setCancelledRate("0.15");

        //未签认
        analyseEntity.setUnsigned(156);
        analyseEntity.setUnsignedRate("22.94");*/

        if (analyseEntity == null) return;
        pie.clear();

        if ("4".equals(UserUtil.getInstance().getUserType(getContext()))) {//受纳场
            if ("1".equals(UserUtil.getInstance().getAutoConfirm(getContext()))) {
                if (isZero(analyseEntity.getConfirmed())
                        && isZero(analyseEntity.getToSign())
                        && isZero(analyseEntity.getCancelled())
                        && isZero(analyseEntity.getAutoConfirmed())
                        && isZero(analyseEntity.getSoilExcepted())) {
                    pie.add(new PieEntry(Float.valueOf(1), "", new AnalyseEntity(0, "unknown", "-1")));
                    return;
                }
            } else {
                if (isZero(analyseEntity.getConfirmed())
                        && isZero(analyseEntity.getToSign())
                        && isZero(analyseEntity.getCancelled())
                        && isZero(analyseEntity.getUnsigned())
                        && isZero(analyseEntity.getSoilExcepted())) {
                    pie.add(new PieEntry(Float.valueOf(1), "", new AnalyseEntity(0, "unknown", "-1")));
                    return;
                }
            }

        } else {
            if ("1".equals(UserUtil.getInstance().getAutoConfirm(getContext()))) {
                if (isZero(analyseEntity.getConfirmed())
                        && isZero(analyseEntity.getToSign())
                        && isZero(analyseEntity.getCancelled())
                        && isZero(analyseEntity.getAutoConfirmed())) {
                    pie.add(new PieEntry(Float.valueOf(1), "", new AnalyseEntity(0, "unknown", "-1")));
                    return;
                }
            } else {
                if (isZero(analyseEntity.getConfirmed())
                        && isZero(analyseEntity.getToSign())
                        && isZero(analyseEntity.getCancelled())
                        && isZero(analyseEntity.getUnsigned())) {
                    pie.add(new PieEntry(Float.valueOf(1), "", new AnalyseEntity(0, "unknown", "-1")));
                    return;
                }
            }
        }


        if (!isZero(analyseEntity.getToSign()))
            pie.add(new PieEntry(getNormalValue(analyseEntity.getToSignRate()), "待签认", new AnalyseEntity(analyseEntity.getToSign(), analyseEntity.getToSignRate(), "0")));
        if (!isZero(analyseEntity.getConfirmed()))
            pie.add(new PieEntry(getNormalValue(analyseEntity.getConfirmedRate()), "已确认", new AnalyseEntity(analyseEntity.getConfirmed(), analyseEntity.getConfirmedRate(), "1")));
        if (!isZero(analyseEntity.getCancelled()))
            pie.add(new PieEntry(getNormalValue(analyseEntity.getCancelledRate()), "已取消", new AnalyseEntity(analyseEntity.getCancelled(), analyseEntity.getCancelledRate(), "2")));

        if ("1".equals(UserUtil.getInstance().getAutoConfirm(getContext()))) {
            if (!isZero(analyseEntity.getAutoConfirmed()))
                pie.add(new PieEntry(getNormalValue(analyseEntity.getAutoConfirmedRate()), "自动确认", new AnalyseEntity(analyseEntity.getAutoConfirmed(), analyseEntity.getAutoConfirmedRate(), "5")));
        } else {
            if (!isZero(analyseEntity.getUnsigned()))
                pie.add(new PieEntry(getNormalValue(analyseEntity.getUnsignedRate()), "未签认", new AnalyseEntity(analyseEntity.getUnsigned(), analyseEntity.getUnsignedRate(), "3")));
        }

        if ("4".equals(UserSP.getInstance().getUserType(getContext()))) {
            if (!isZero(analyseEntity.getSoilExcepted()))
                pie.add(new PieEntry(getNormalValue(analyseEntity.getSoilExceptedRate()), "土质异常", new AnalyseEntity(analyseEntity.getSoilExcepted(), analyseEntity.getSoilExceptedRate(), "4")));
        }
    }

    private float getNormalValue(String value) {//此返回值仅用于饼图所占空间大小,非真实值,5f是防止值过低时(有时会小于1)强制返回的一个空间值
        float v = Float.valueOf(value);
        return v < 8.0f ? AppTools.px2dipfloat(getContext(), 26f) + v : v;
    }

    private boolean isZero(int num) {
        if (Float.valueOf(num) <= 0.0f) return true;
        return false;
    }

    private boolean isNormalDate(String date1, String date2) {
        if (AppTools.spaceDay(date2, date1) > 31) {
            ToastUtil.showToast(getContext(), "查询的日期不能超过一个月");
            return false;
        }

        if (AppTools.spaceDay(date2, date1) < 0) {
            ToastUtil.showToast(getContext(), "起始时间不能大于结束时间");
            return false;
        }


        return true;
    }

    private void showDataWheel() {
        FDatePickerDialog.createDateSpePicker(getActivity(), new FDatePickerDialog.wheelItemDateListener() {
            @Override
            public void wheelCurrentSelected(String bText, String mText, String eText, boolean isDay) {
                if (isDay && !TextUtils.isEmpty(bText) && !TextUtils.isEmpty(eText)) {//按日选择
                    if (isNormalDate(bText, eText)) {
                        radioMore.setTextColor(getResources().getColor(R.color.green_theam));
                        radioMore.setText(String.format(getString(R.string.statis_dateFormat), bText, eText));
                        initRequired(bText, eText);
                    }
                } else if (!TextUtils.isEmpty(mText) && mText.length() > 4) {//按月选择
                    String[] array = mText.split("-");
                    initRequired(mText + "-01", mText + "-" + AppTools.getMonthDay(Integer.parseInt(array[0]), Integer.parseInt(array[1])));
                    radioMore.setTextColor(getResources().getColor(R.color.green_theam));
                    radioMore.setText(mText);
                }
            }
        }).show();
    }

    private void showDropPop(View v) {
        PopWindow p = new PopWindow();
        View contentView = LayoutInflater.from(getContext()).inflate(R.layout.layout_popupwin,
                null, false);
        TextView textView = (TextView) contentView.findViewById(R.id.tip);
        textView.setText(getDropTxt());
        p.setPopView(getContext(), v, contentView);
        AppTools.setWindowBackgroundAlpha(getActivity(), 0.6f);
        p.showPopWindow(v, 0, 46);
        p.dismissPopWindow(new PopWindow.IPopwindow() {
            @Override
            public void popDismiss(PopupWindow popupWindow) {
                AppTools.setWindowBackgroundAlpha(getActivity(), 1.0f);
            }
        });
    }

    private String getDropTxt() {
        boolean isAuto = "1".equals(UserUtil.getInstance().getAutoConfirm(getContext()));
        boolean isExcept = "4".equals(UserSP.getInstance().getUserType(getContext()));
        if (isAuto && isExcept) return getString(R.string.statis_tip_auto);
        if (isAuto && !isExcept) return getString(R.string.statis_tip_auto_noexcept);
        if (!isAuto && isExcept) return getString(R.string.statis_tip_except);
        if (!isAuto && !isExcept) return getString(R.string.statis_tip);
        return "";
    }

    /**
     * 饼状图数据
     *
     * @param bTime
     * @param eTime
     */
    private void getAnalyseData(String bTime, String eTime) {
        if (homeAction == null) homeAction = new HomeAction();
        homeAction.statisticAction(UserSP.getInstance().getUserName(getContext()),
                String.valueOf(UserSP.getInstance().getUserType(getContext())),
                bTime,
                eTime,
                "2",//历史联单
                "",
                new UICallback(getActivity(), false) {
                    @Override
                    public void handleSuccess(Message msg) {
                        AnalyseEntity analyseEntity = (AnalyseEntity) msg.obj;
                        if (analyseEntity == null) return;
                        setPieChart(analyseEntity);
                        analyseAdapter.refresh(contentEntities, pie, 100);
                    }

                    @Override
                    public void handlePause(Message msg) {
                        super.handlePause(msg);
                    }

                    @Override
                    public void handleFailure(Message msg) {
                        super.handleFailure(msg);
                    }
                });
    }

    /**
     * 列表记录
     *
     * @param BeginDate
     * @param EndDate
     * @param VehicleNo
     * @param direction
     * @param isShow
     */
    private void getAnalyDetail(final int direction, String BeginDate, String EndDate, String VehicleNo, boolean isShow) {
        if (homeAction == null) homeAction = new HomeAction();
        homeAction.signQuaryAction(UserSP.getInstance().getUserName(getContext()),
                String.valueOf(UserSP.getInstance().getUserType(getContext())), BeginDate, EndDate, VehicleNo, String.valueOf(page), pageSize, new UICallback(getActivity(), isShow) {
                    @Override
                    public void handleSuccess(Message msg) {
                        AnalyseDetailEntity detailEntity = (AnalyseDetailEntity) msg.obj;
                        if (detailEntity == null) return;
                        if (direction == REFRESH) {
                            contentEntities = detailEntity.getContentEntityList();
                            smartRefreshLayout.finishRefresh();
                            analyseAdapter.refresh(contentEntities, pie, 0);
                        } else if (direction == LOADMORE) {
                            List<AnalyseDetailEntity.AnalyseContentEntity> cEntities = detailEntity.getContentEntityList();
                            smartRefreshLayout.finishLoadMore();
                            analyseAdapter.addData(cEntities);
                            if (AppConstant.RESULT_CODE_EMPTY.equals(detailEntity.getErrorCode())) {
                                ToastUtil.showToast(getContext(), R.string.no_more_data);
                            }
                        }
                    }

                    @Override
                    public void handlePause(Message msg) {
                        super.handlePause(msg);
                    }

                    @Override
                    public void handleFailure(Message msg) {
                        super.handleFailure(msg);
                        if (direction == REFRESH) {
                            smartRefreshLayout.finishRefresh();
                            analyseAdapter.refresh(null, new ArrayList<PieEntry>(), 0);
                        } else if (direction == LOADMORE) {
                            smartRefreshLayout.finishLoadMore();
                        }


                    }
                });

    }


    @Override
    public void dropTip(TextView view) {
        showDropPop(view);
    }

    @Override
    public void pieviewPartSelect(Entry e, Highlight h) {
        AnalyseEntity analyseEntity = (AnalyseEntity) e.getData();
        if (analyseEntity == null) return;
        String type = analyseEntity.getType();
        if ("0".equals(type)) {
            startActivity(WaybillSearchActivity.startAct(getActivity(), "待签认联单", type, bDate, eDate));
        } else if ("1".equals(type)) {
            startActivity(WaybillSearchActivity.startAct(getActivity(), "已确认联单", type, bDate, eDate));
        } else if ("2".equals(type)) {
            startActivity(WaybillSearchActivity.startAct(getActivity(), "已取消联单", type, bDate, eDate));
        } else if ("3".equals(type)) {
            startActivity(WaybillSearchActivity.startAct(getActivity(), "未签认联单", type, bDate, eDate));
        } else if ("4".equals(type)) {
            startActivity(WaybillSearchActivity.startAct(getActivity(), "土质异常联单", type, bDate, eDate));
        } else if ("5".equals(type)) {
            startActivity(WaybillSearchActivity.startAct(getActivity(), "自动确认联单", type, bDate, eDate));
        }
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        page++;
        getAnalyDetail(LOADMORE, bDate, eDate, "", false);

    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        page = 1;
        getAnalyseData(bDate, eDate);//饼状
        getAnalyDetail(REFRESH, bDate, eDate, "", false);//列表
    }
}
