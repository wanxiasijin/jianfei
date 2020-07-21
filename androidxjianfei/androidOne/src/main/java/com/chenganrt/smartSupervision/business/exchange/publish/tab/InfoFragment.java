package com.chenganrt.smartSupervision.business.exchange.publish.tab;

import android.Manifest;
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.commonlib.mvp.BaseFragment;
import com.android.commonlib.mvp.BasePresenter;
import com.android.commonlib.okhttp.NetWorkDataManager;
import com.android.commonlib.okhttp.bean.DetailData;
import com.android.commonlib.okhttp.bean.ImageData1;
import com.android.commonlib.okhttp.bean.Response;
import com.android.commonlib.okhttp.bean.TypeBean;
import com.android.commonlib.okhttp.bean.TypeList;
import com.android.commonlib.okhttp.bean.TypeResponse;
import com.android.commonlib.rxjava.RxJava;
import com.android.commonlib.utils.GsonUtil;
import com.android.commonlib.utils.ToastUtils;
import com.chenganrt.smartSupervision.R;
import com.chenganrt.smartSupervision.business.app.BaseApplication;
import com.chenganrt.smartSupervision.widget.FullyGridLayoutManager;
import com.chenganrt.smartSupervision.widget.adapter.GridImageAdapter;
import com.chenganrt.smartSupervision.business.electronic.util.AppTools;
import com.chenganrt.smartSupervision.business.exchange.entity.FilterData;
import com.chenganrt.smartSupervision.business.exchange.publish.calendar.CalendarView;
import com.chenganrt.smartSupervision.business.exchange.publish.tab.view.Util;
import com.chenganrt.smartSupervision.business.login.UserSP;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.permissions.Permission;
import com.luck.picture.lib.permissions.RxPermissions;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;
import timber.log.Timber;

import static com.chenganrt.smartSupervision.business.exchange.entity.FilterData.AREA;
import static com.chenganrt.smartSupervision.business.exchange.entity.FilterData.COMPREHENSIVE_UTILIZATION;
import static com.chenganrt.smartSupervision.business.exchange.entity.FilterData.CONSTRUCTION_WASTE;
import static com.chenganrt.smartSupervision.business.exchange.entity.FilterData.WASTE_TYPE;

@SuppressLint("ValidFragment")
public class InfoFragment extends BaseFragment {

    private Unbinder unbinder = null;
    private int maxSelectNum = 3;
    private List<LocalMedia> selectList = new ArrayList<>();
    private List<String> mImageUrls = new ArrayList<>();
    private GridImageAdapter adapter;
    private PopupWindow pop;
    @BindView(R.id.sl_publish)
    ScrollView mScrollView;
    @BindView(R.id.rl_waste_type)
    RelativeLayout mRlWasteType;
    @BindView(R.id.waste_type)
    TextView mWasteType;
    private String mWasteTypeCode;
    @BindView(R.id.et_title)
    EditText mTitle;
    @BindView(R.id.rl_area)
    RelativeLayout mRlArea;
    @BindView(R.id.area)
    TextView mArea;
    private String mAreaCode;
    @BindView(R.id.et_address)
    EditText mAddress;
    @BindView(R.id.rl_type)
    RelativeLayout mRlType;
    @BindView(R.id.type)
    TextView mType;
    private String mTypeCode;
    @BindView(R.id.et_amount)
    EditText mAmount;
    @BindView(R.id.et_time)
    TextView mTime;
    @BindView(R.id.et_contact)
    EditText mContact;
    @BindView(R.id.et_tel)
    EditText mTel;
    @BindView(R.id.et_detail)
    EditText mDetail;
    @BindView(R.id.recycler)
    RecyclerView mRecyclerView;
    @BindView(R.id.calendar_view)
    CalendarView mCalendar;
    @BindView(R.id.cardview)
    CardView mCardview;
    private int mExchangeType;
    private String mInfoId;
    private boolean isEdit;
    private int mWasteIndex = -1;

    public static InfoFragment getInstance() {
        return new InfoFragment();
    }

    public void setExchangeType(int exchangeType) {
        this.mExchangeType = exchangeType;
        if (mCardview != null) {
            mCardview.setVisibility(View.GONE);
        }
    }

    public void showSelectedImages(List<LocalMedia> images) {
        selectList.addAll(images);
        adapter.setList(selectList);
        adapter.notifyDataSetChanged();
    }

    public void showExchangeDetail(DetailData exchangeData) {
        isEdit = true;
        mInfoId = exchangeData.getId();
        mWasteType.setText(exchangeData.getWaste_type());
        for (int i = 0; i < FilterData.getTypes(1).size(); i++) {
            Timber.d("mWasteType:%s", FilterData.getTypes(1).get(i).getName());
            if (FilterData.getTypes(1).get(i).getName().equals(exchangeData.getWaste_type())) {
                mWasteTypeCode = FilterData.getTypes(1).get(i).getCode();
                Timber.d("mWasteTypeCode:%s", FilterData.getTypes(1).get(i).getCode());
            }
        }
        mTitle.setText(exchangeData.getTitle());
        mArea.setText(exchangeData.getArea());
        for (int i = 0; i < FilterData.getTypes(2).size(); i++) {
            Timber.d("mArea:%s", FilterData.getTypes(2).get(i).getName());
            if (FilterData.getTypes(2).get(i).getName().equals(exchangeData.getArea())) {
                mAreaCode = FilterData.getTypes(2).get(i).getCode();
                Timber.d("mAreaCode:%s", FilterData.getTypes(2).get(i).getCode());
            }
        }
        mAddress.setText(exchangeData.getAddress());
        mType.setText(exchangeData.getWaste_type_detail());
        for (int i = 0; i < FilterData.getTypes(3).size(); i++) {
            Timber.d("mType:%s", FilterData.getTypes(3).get(i).getName());
            if (FilterData.getTypes(3).get(i).getName().equals(exchangeData.getWaste_type_detail())) {
                mTypeCode = FilterData.getTypes(3).get(i).getCode();
                Timber.d("mTypeCode:%s", FilterData.getTypes(3).get(i).getCode());
            }
        }
        mAmount.setText(String.valueOf(exchangeData.getAmount()));
        mTime.setText(exchangeData.getTerm_validity());
        mContact.setText(exchangeData.getContacts());
        mTel.setText(exchangeData.getTel());
        mDetail.setText(exchangeData.getDetails());
    }

    @Override
    public BasePresenter createPresenter() {
        return null;
    }

    @Override
    public int initLayout() {
        return R.layout.exchange_publish;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        getTypeList();
    }

    private void initView() {
        FullyGridLayoutManager manager = new FullyGridLayoutManager(getActivity(), 3, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(manager);
        adapter = new GridImageAdapter(getActivity(), onAddPicClickListener);
        adapter.setList(selectList);
        adapter.setSelectMax(maxSelectNum);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setFocusable(false);
        adapter.setOnItemClickListener(new GridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                if (selectList.size() > 0) {
                    LocalMedia media = selectList.get(position);
                    String pictureType = media.getPictureType();
                    int mediaType = PictureMimeType.pictureToVideo(pictureType);
                    switch (mediaType) {
                        case 1:
                            // 预览图片 可自定长按保存路径
                            //PictureSelector.create(MainActivity.this).externalPicturePreview(position, "/custom_file", selectList);
                            PictureSelector.create(getActivity()).externalPicturePreview(position, selectList);
                            break;
                        case 2:
                            // 预览视频
                            PictureSelector.create(getActivity()).externalPictureVideo(media.getPath());
                            break;
                        case 3:
                            // 预览音频
                            PictureSelector.create(getActivity()).externalPictureAudio(media.getPath());
                            break;
                    }
                }
            }
        });
        Calendar cal = Calendar.getInstance();
        int year = cal.get(java.util.Calendar.YEAR);
        int month = cal.get(java.util.Calendar.MONTH) + 1;//获取月份（因为在格里高利历和罗马儒略历一年中第一个月为JANUARY，它为0，最后一个月取决于一年中的月份数，所以这个值初始为0，所以需要加1）
        int day = cal.get(java.util.Calendar.DATE);
        mCalendar.setDate(year + "-" + month + "-" + day);
        mCalendar.setOnDateSelectedListener(new CalendarView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(int year, int month, int day) {
                mCardview.setVisibility(View.GONE);
                String twoBitMonth = month > 9 ? String.valueOf(month) : "0" + month;
                String twoBitDay = day > 9 ? String.valueOf(day) : "0" + day;
                mTime.setText(year + "-" + twoBitMonth + "-" + twoBitDay);
            }
        });
        mCalendar.setOnCancelSelectedListener(new CalendarView.OnCancelSelectedListener() {
            @Override
            public void onCancelSelected() {
                mCardview.setVisibility(View.GONE);
            }
        });
    }

    private void getTypeList() {
        RxJava.getInstance().create(new TypeListObservable(), new RxJava.ObserveOnMainThread<List<TypeBean>>() {
            @Override
            public void accept(List<TypeBean> typeBeans) {
                FilterData.setType((ArrayList<TypeBean>) typeBeans);
            }
        }, new RxJava.ThrowableOnMainThread() {
            @Override
            public void accept(String t) {
                ToastUtils.showToast(BaseApplication.getApp(), t);
            }
        });
    }

    public void publish(boolean isEdit) {
        if (mWasteType.getText().toString().trim().isEmpty()) {
            ToastUtils.showToast(BaseApplication.getApp(), "废弃物种类不能为空");
            return;
        }
        if (mTitle.getText().toString().trim().isEmpty()) {
            ToastUtils.showToast(BaseApplication.getApp(), "标题不能为空");
            return;
        }
        if (mArea.getText().toString().trim().isEmpty()) {
            ToastUtils.showToast(BaseApplication.getApp(), "区域不能为空");
            return;
        }
        if (mAddress.getText().toString().trim().isEmpty()) {
            ToastUtils.showToast(BaseApplication.getApp(), "详细地址不能为空");
            return;
        }
        if (mType.getText().toString().trim().isEmpty()) {
            ToastUtils.showToast(BaseApplication.getApp(), "类型不能为空");
            return;
        }
        if (mAmount.getText().toString().trim().isEmpty()) {
            ToastUtils.showToast(BaseApplication.getApp(), "数量不能为空");
            return;
        }
        if (mTime.getText().toString().trim().isEmpty()) {
            ToastUtils.showToast(BaseApplication.getApp(), "有效期不能为空");
            return;
        }
        if (mContact.getText().toString().trim().isEmpty()) {
            ToastUtils.showToast(BaseApplication.getApp(), "联系人不能为空");
            return;
        }
        if (mTel.getText().toString().trim().isEmpty()) {
            ToastUtils.showToast(BaseApplication.getApp(), "联系电话不能为空");
            return;
        }
        if (mDetail.getText().toString().trim().isEmpty()) {
            ToastUtils.showToast(BaseApplication.getApp(), "详情描述不能为空");
            return;
        }
        showLoading();
        if (selectList.size() > 0) {
            RxJava.getInstance().create(new ConvertImageObservable(this), new RxJava.ObserveOnMainThread<List<Response>>() {
                @Override
                public void accept(List<Response> list) {
                    for (Response response : list) {
                        Timber.d("convert:" + response.toString());
                        if (response.getStatus() == 200) {
                            ImageData1 imageData1 = GsonUtil.JsonToObject(GsonUtil.toJson(response.getData()), ImageData1.class);
                            mImageUrls.add(imageData1.getUrl());
                        } else {
                            ToastUtils.showToast(BaseApplication.getApp(), response.getMessage());
                            break;
                        }
                    }
                    publish();
                }
            }, new RxJava.ThrowableOnMainThread() {
                @Override
                public void accept(String t) {
                    dismissLoading();
                    ToastUtils.showToast(BaseApplication.getApp(), t);
                }
            });
        } else {
            publish();
        }
    }

    private void publish() {
        RxJava.getInstance().create(new PublishObservable(this), new RxJava.ObserveOnMainThread<Response>() {
            @Override
            public void accept(Response response) {
                dismissLoading();
                Timber.d("publish:" + response.toString());
                if (response.getStatus() == 200) {
                    ToastUtils.showToast(BaseApplication.getApp(), "发布成功");
                    getActivity().onBackPressed();
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


    private static class TypeListObservable implements ObservableOnSubscribe<List<TypeBean>> {

        @Override
        public void subscribe(ObservableEmitter<List<TypeBean>> emitter) throws Exception {
            try {
                TypeResponse response = NetWorkDataManager.getTypes();
                List<TypeBean> typeList = new ArrayList<>();
                if (response.getStatus() == 200) {
                    typeList.addAll(response.getData());
                    Timber.d("typeList1:" + typeList.size());
                } else {
                    String jsonStr = GsonUtil.getJson(BaseApplication.getApp(), "types.json");
                    TypeList typeList1 = GsonUtil.JsonToObject(jsonStr, TypeList.class);
                    typeList.addAll(typeList1.getData());
                    Timber.d("typeList2:" + typeList.size());
                }
                emitter.onNext(typeList);
            } catch (Exception e) {
                emitter.onError(e);
            }
            emitter.onComplete();
        }
    }

    private static class ConvertImageObservable implements ObservableOnSubscribe<List<Response>> {

        private WeakReference<InfoFragment> mInfoFragment;

        public ConvertImageObservable(InfoFragment infoFragment) {
            mInfoFragment = new WeakReference<>(infoFragment);
        }

        @Override
        public void subscribe(ObservableEmitter<List<Response>> emitter) throws Exception {
            InfoFragment infoFragment = mInfoFragment.get();
            if (infoFragment == null) {
                return;
            }
            try {
                List<Response> responseList = new ArrayList<>();
                for (int i = 0; i < infoFragment.selectList.size(); i++) {
                    Response response = NetWorkDataManager.getImageUrl(infoFragment.selectList.get(i).getPath());
                    responseList.add(response);
                }
                emitter.onNext(responseList);
            } catch (Exception e) {
                emitter.onError(e);
            }
            emitter.onComplete();
        }
    }

    private static class PublishObservable implements ObservableOnSubscribe<Response> {

        private WeakReference<InfoFragment> mInfoFragment;

        public PublishObservable(InfoFragment infoFragment) {
            mInfoFragment = new WeakReference<>(infoFragment);
        }

        @Override
        public void subscribe(ObservableEmitter<Response> emitter) throws Exception {
            InfoFragment infoFragment = mInfoFragment.get();
            if (infoFragment == null) {
                return;
            }
            try {
                Map<String, String> map = new LinkedHashMap<>();
                map.put("exchange_type", String.valueOf(infoFragment.mExchangeType));
                map.put("waste_type", infoFragment.mWasteTypeCode);
                map.put("title", infoFragment.mTitle.getText().toString().trim());
                map.put("area", infoFragment.mAreaCode);
                map.put("address", infoFragment.mAddress.getText().toString().trim());
                map.put("waste_type_detail", infoFragment.mTypeCode);
                map.put("amount", infoFragment.mAmount.getText().toString().trim());
                map.put("term_validity", infoFragment.mTime.getText().toString().trim());
                map.put("contacts", infoFragment.mContact.getText().toString().trim());
                map.put("tel", infoFragment.mTel.getText().toString().trim());
                map.put("details", infoFragment.mDetail.getText().toString().trim());
                map.put("issuer_id", UserSP.getInstance().getUserId(infoFragment.getContext()));
                if (infoFragment.mImageUrls.size() > 0) {
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < infoFragment.mImageUrls.size(); i++) {
                        if (i == infoFragment.mImageUrls.size() - 1) {
                            sb.append(infoFragment.mImageUrls.get(i));
                        } else {
                            sb.append(infoFragment.mImageUrls.get(i)).append(",");
                        }
                    }
                    map.put(infoFragment.isEdit ? "files" : "urls", sb.toString());
                }
                Response response;
                if (infoFragment.isEdit) {
                    map.put("id", infoFragment.mInfoId);
                    response = NetWorkDataManager.editPublishInfo(map);
                } else {
                    response = NetWorkDataManager.publishInfo(map);
                }
                emitter.onNext(response);
            } catch (Exception e) {
                emitter.onError(e);
            }
            emitter.onComplete();
        }
    }

    private GridImageAdapter.onAddPicClickListener onAddPicClickListener = new GridImageAdapter.onAddPicClickListener() {

        @SuppressLint("CheckResult")
        @Override
        public void onAddPicClick() {
            //获取写的权限
            RxPermissions rxPermission = new RxPermissions(getActivity());
            rxPermission.requestEach(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .subscribe(new Consumer<Permission>() {
                        @Override
                        public void accept(Permission permission) {
                            if (permission.granted) {
                                showPop();
                            } else {
                                Toast.makeText(getActivity(), "拒绝", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    };

    private void showPop() {
        View bottomView = View.inflate(getActivity(), R.layout.layout_bottom_dialog, null);
        TextView mAlbum = bottomView.findViewById(R.id.tv_album);
        TextView mCamera = bottomView.findViewById(R.id.tv_camera);
        TextView mCancel = bottomView.findViewById(R.id.tv_cancel);

        pop = new PopupWindow(bottomView, -1, -2);
        pop.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pop.setOutsideTouchable(true);
        pop.setFocusable(true);
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = 0.5f;
        getActivity().getWindow().setAttributes(lp);
        pop.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
                lp.alpha = 1f;
                getActivity().getWindow().setAttributes(lp);
            }
        });
        pop.setAnimationStyle(R.style.main_menu_photo_anim);
        pop.showAtLocation(getActivity().getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.tv_album:
                        //相册
                        PictureSelector.create(getActivity())
                                .openGallery(PictureMimeType.ofImage())
                                .maxSelectNum(maxSelectNum)
                                .minSelectNum(1)
                                .imageSpanCount(4)
                                .selectionMode(PictureConfig.MULTIPLE)
                                .forResult(PictureConfig.CHOOSE_REQUEST);
                        break;
                    case R.id.tv_camera:
                        //拍照
                        PictureSelector.create(getActivity())
                                .openCamera(PictureMimeType.ofImage())
                                .forResult(PictureConfig.CHOOSE_REQUEST);
                        break;
                    case R.id.tv_cancel:
                        //取消
                        //closePopupWindow();
                        break;
                }
                closePopupWindow();
            }
        };

        mAlbum.setOnClickListener(clickListener);
        mCamera.setOnClickListener(clickListener);
        mCancel.setOnClickListener(clickListener);
    }

    public void closePopupWindow() {
        if (pop != null && pop.isShowing()) {
            pop.dismiss();
            pop = null;
        }
    }

    @OnClick({R.id.rl_waste_type, R.id.rl_area, R.id.rl_type, R.id.rl_end_time})
    public void onViewClicked(View view) {
        AppTools.collapseSoftInputMethod(getActivity());
        mCardview.setVisibility(View.GONE);
        switch (view.getId()) {
            case R.id.rl_waste_type:
                Util.alertBottomWheelOption(getActivity(), FilterData.getTypes(WASTE_TYPE), new Util.OnWheelViewClick() {
                    @Override
                    public void onClick(View view, int position) {
                        mWasteIndex = position;
                        mWasteType.setText(FilterData.getTypes(WASTE_TYPE).get(position).getName());
                        mWasteTypeCode = FilterData.getTypes(WASTE_TYPE).get(position).getCode();
                    }
                });
                break;
            case R.id.rl_area:
                Util.alertBottomWheelOption(getActivity(), FilterData.getTypes(AREA), new Util.OnWheelViewClick() {
                    @Override
                    public void onClick(View view, int position) {
                        mArea.setText(FilterData.getTypes(AREA).get(position).getName());
                        mAreaCode = FilterData.getTypes(AREA).get(position).getCode();
                    }
                });
                break;
            case R.id.rl_type:
                if (mWasteIndex == -1) {
                    ToastUtils.showToast(BaseApplication.getApp(), "废弃物种类不能为空");
                    return;
                }
                int wasteType = mWasteIndex == 0 ? CONSTRUCTION_WASTE : COMPREHENSIVE_UTILIZATION;
                Util.alertBottomWheelOption(getActivity(), FilterData.getTypes(wasteType), new Util.OnWheelViewClick() {
                    @Override
                    public void onClick(View view, int position) {
                        mType.setText(FilterData.getTypes(wasteType).get(position).getName());
                        mTypeCode = FilterData.getTypes(wasteType).get(position).getCode();
                    }
                });
                break;
            case R.id.rl_end_time:
                mCardview.setVisibility(View.VISIBLE);
                break;
        }
    }
}