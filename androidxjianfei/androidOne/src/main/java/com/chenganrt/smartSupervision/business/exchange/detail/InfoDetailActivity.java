package com.chenganrt.smartSupervision.business.exchange.detail;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.commonlib.mvp.BaseActivity;
import com.android.commonlib.mvp.BasePresenter;
import com.android.commonlib.okhttp.NetWorkDataManager;
import com.android.commonlib.okhttp.bean.DetailData;
import com.android.commonlib.okhttp.bean.Response;
import com.android.commonlib.rxjava.RxJava;
import com.android.commonlib.utils.GsonUtil;
import com.android.commonlib.utils.ToastUtils;
import com.android.commonlib.view.buttomdialog.ShareBottomPopupDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chenganrt.smartSupervision.R;
import com.chenganrt.smartSupervision.business.app.BaseApplication;
import com.chenganrt.smartSupervision.business.exchange.publish.PublishActivity;
import com.chenganrt.smartSupervision.business.login.LoginActivity;
import com.chenganrt.smartSupervision.business.login.UserSP;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import timber.log.Timber;

import static com.chenganrt.smartSupervision.business.exchange.publish.PublishActivity.EXCHANGE_DATA;
import static com.chenganrt.smartSupervision.business.login.LoginActivity.LOGIN_REQUSET_CODE;

public class InfoDetailActivity extends BaseActivity implements BaseQuickAdapter.OnItemClickListener {
    private static final int ITEM_HEIGHT = 400;
    public static final String INFO_ID = "info_id";
    @BindView(R.id.rl_main)
    RelativeLayout mRootLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.titleSub)
    TextView titleSub;
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.address)
    TextView mAddress;
    @BindView(R.id.amount)
    TextView mAmount;
    @BindView(R.id.area)
    TextView mArea;
    @BindView(R.id.views_value)
    TextView mViewsValue;
    @BindView(R.id.collect_value)
    TextView mCollectValue;
    @BindView(R.id.contact)
    TextView mcontact;
    @BindView(R.id.end_time)
    TextView mEndTime;
    @BindView(R.id.create_time)
    TextView mCreateTime;
    @BindView(R.id.detail)
    TextView mDetail;
    @BindView(R.id.tv_collect)
    TextView mCollect;
    @BindView(R.id.tv_edit)
    TextView mEdit;
    @BindView(R.id.recycler)
    RecyclerView mRecyclerView;
    private DetailData mExchangeData;
    private boolean isMyInfo;

    private ImageListAdapter mAdapter;
    private List<String> mList = new ArrayList<>();
    private String mInfoId;

    @Override
    public int initLayout() {
        return R.layout.activity_exchange_detail;
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    public void initConfig() {
        setStatusBar(toolbar);
        titleSub.setText("信息详情");
        initView();
    }

    private void initView() {
        mAdapter = new ImageListAdapter(this, R.layout.item_detail_image, mList);
        mAdapter.openLoadAnimation();
        mAdapter.setOnItemClickListener(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            mInfoId = intent.getStringExtra(INFO_ID);
            Timber.d("mInfoId:" + mInfoId);
            if (isNetworkAvailable()) {
                showLoading();
                getInfoDetail();
            }
        }
    }

    private void getInfoDetail() {
        RxJava.getInstance().create(new InfoObservable(this), new RxJava.ObserveOnMainThread<Response>() {
            @Override
            public void accept(Response response) {
                if (isActive) {
                    dismissLoading();
                    Timber.d("getInfoDetail:" + response.toString());
                    if (response.getStatus() == 200) {
                        DetailData exchangeData = GsonUtil.JsonToObject(GsonUtil.toJson(response.getData()), DetailData.class);
                        if (exchangeData != null) {
                            Timber.d("getInfoDetail:" + exchangeData.toString());
                            showResult(exchangeData);
                        }
                    } else {
                        ToastUtils.showToast(BaseApplication.getApp(), response.getMessage());
                    }
                }
            }
        }, new RxJava.ThrowableOnMainThread() {
            @Override
            public void accept(String t) {
                if (isActive) {
                    dismissLoading();
                    ToastUtils.showToast(BaseApplication.getApp(), t);
                }
            }
        });
    }

    private void showResult(DetailData exchangeData) {
        mExchangeData = new DetailData(exchangeData);
        mTitle.setText(exchangeData.getTitle());
        mAddress.setText(exchangeData.getAddress());
        mAmount.setText(exchangeData.getAmount() + "万方");
        mArea.setText(exchangeData.getArea());
        mViewsValue.setText(exchangeData.getViews());
        mCollectValue.setText(exchangeData.getCollection_volume());
        mcontact.setText(exchangeData.getContacts());
        mEndTime.setText(exchangeData.getTerm_validity());
        mCreateTime.setText(exchangeData.getUpdate_time());
        mDetail.setText(exchangeData.getDetails());
        isMyInfo = exchangeData.getIssuer_id().equals(UserSP.getInstance().getUserId(this));
        if (!isMyInfo) {
            mEdit.setText("电话联系");
        }
        if (exchangeData.getFiles() != null && exchangeData.getFiles().size() > 0) {
            mList.addAll(exchangeData.getFiles());
            mAdapter.notifyDataSetChanged();
        }
    }

    @OnClick({R.id.tv_collect, R.id.tv_edit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_collect:
                if (isNetworkAvailable()) {
                    if (!TextUtils.isEmpty(UserSP.getInstance().getUserId(this))) {
                        CollectInfo();
                    } else {
                        Intent loginIntent = new Intent(this, LoginActivity.class);
                        startActivityForResult(loginIntent, LOGIN_REQUSET_CODE);
                    }
                }
                break;
            case R.id.tv_edit:
                if (isMyInfo) {
                    if (isNetworkAvailable()) {
                        if (!TextUtils.isEmpty(UserSP.getInstance().getUserId(this))) {
                            editInfo();
                        } else {
                            Intent loginIntent = new Intent(this, LoginActivity.class);
                            startActivityForResult(loginIntent, LOGIN_REQUSET_CODE);
                        }
                    }
                } else {
                    String tel = mExchangeData.getTel();
                    if (tel != null && !tel.isEmpty()) {
                        showTelPop(mExchangeData.getTel());
                    } else {
                        ToastUtils.showToast(BaseApplication.getApp(), "电话号码不正确");
                    }
                }
                break;
        }
    }

    private void CollectInfo() {
        RxJava.getInstance().create(new CollectInfoObservable(this), new RxJava.ObserveOnMainThread<Response>() {
            @Override
            public void accept(Response response) {
                if (isActive) {
                    Timber.d("CollectInfo:" + response.toString());
                    if (response.getStatus() == 200) {
                        ToastUtils.showToast(BaseApplication.getApp(), "收藏成功");
                    } else {
                        ToastUtils.showToast(BaseApplication.getApp(), response.getMessage());
                    }
                }
            }
        }, new RxJava.ThrowableOnMainThread() {
            @Override
            public void accept(String t) {
                if (isActive) {
                    ToastUtils.showToast(BaseApplication.getApp(), t);
                }
            }
        });
    }

    private void editInfo() {
        if (mExchangeData != null) {
            Intent intent = new Intent(this, PublishActivity.class);
            intent.putExtra(EXCHANGE_DATA, mExchangeData);
            startActivity(intent);
        } else {
            ToastUtils.showToast(BaseApplication.getApp(), "编辑失败");
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }

    private void showTelPop(String tel) {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.buttom_dialog, null);
        ShareBottomPopupDialog shareBottomPopupDialog = new ShareBottomPopupDialog(this, dialogView);
        shareBottomPopupDialog.showPopup(mRootLayout);

        Button bt_cancel = (Button) dialogView.findViewById(R.id.bt_cancel);
        Button bt_tel = (Button) dialogView.findViewById(R.id.bt_tel);
        bt_tel.setText("呼叫 " + tel);
        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.bt_cancel:
                        break;
                    case R.id.bt_tel:
                        if (ContextCompat.checkSelfPermission(InfoDetailActivity.this, Manifest.
                                permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(InfoDetailActivity.this, new
                                    String[]{Manifest.permission.CALL_PHONE}, 1);
                        } else {
                            call(tel);
                        }
                        break;
                }
                shareBottomPopupDialog.dismiss();
            }
        };
        bt_tel.setOnClickListener(clickListener);
        bt_cancel.setOnClickListener(clickListener);
    }

    private void call(String tel) {
        try {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + tel));
            startActivity(intent);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    call(mExchangeData.getTel());
                } else {
                    Toast.makeText(this, "电话权限未开启", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

    private static class InfoObservable implements ObservableOnSubscribe<Response> {

        private WeakReference<InfoDetailActivity> mWeakReference;

        public InfoObservable(InfoDetailActivity infoDetailActivity) {
            mWeakReference = new WeakReference<>(infoDetailActivity);
        }

        @Override
        public void subscribe(ObservableEmitter<Response> emitter) throws Exception {
            InfoDetailActivity infoDetailActivity = mWeakReference.get();
            if (infoDetailActivity == null) {
                return;
            }
            try {
                Map<String, String> map = new LinkedHashMap<>();
                map.put("eid", String.valueOf(infoDetailActivity.mInfoId));
                Response response = NetWorkDataManager.getInfoDetail(map);
                emitter.onNext(response);
            } catch (Exception e) {
                emitter.onError(e);
            }
            emitter.onComplete();
        }
    }

    private static class CollectInfoObservable implements ObservableOnSubscribe<Response> {

        private WeakReference<InfoDetailActivity> mWeakReference;

        public CollectInfoObservable(InfoDetailActivity infoDetailActivity) {
            mWeakReference = new WeakReference<>(infoDetailActivity);
        }

        @Override
        public void subscribe(ObservableEmitter<Response> emitter) throws Exception {
            InfoDetailActivity infoDetailActivity = mWeakReference.get();
            if (infoDetailActivity == null) {
                return;
            }
            try {
                Map<String, String> map = new LinkedHashMap<>();
                map.put("person_id", UserSP.getInstance().getUserId(infoDetailActivity));
                map.put("eid", infoDetailActivity.mInfoId);
                Response response = NetWorkDataManager.collectInfo(map);
                emitter.onNext(response);
            } catch (Exception e) {
                emitter.onError(e);
            }
            emitter.onComplete();
        }
    }
}