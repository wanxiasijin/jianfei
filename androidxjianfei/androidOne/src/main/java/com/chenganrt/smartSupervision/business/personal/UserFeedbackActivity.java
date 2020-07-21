package com.chenganrt.smartSupervision.business.personal;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.chenganrt.smartSupervision.R;
import com.chenganrt.smartSupervision.business.app.BaseActivity;
import com.chenganrt.smartSupervision.widget.FullyGridLayoutManager;
import com.chenganrt.smartSupervision.business.electronic.util.ToastUtil;
import com.chenganrt.smartSupervision.business.login.UserSP;
import com.chenganrt.smartSupervision.common.okhttputil.CallBackUtil;
import com.chenganrt.smartSupervision.common.okhttputil.HttpConstant;
import com.chenganrt.smartSupervision.common.okhttputil.OkhttpUtil;
import com.chenganrt.smartSupervision.widget.adapter.GridImageAdapter;
import com.google.gson.Gson;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.permissions.Permission;
import com.luck.picture.lib.permissions.RxPermissions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.functions.Consumer;
import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.graphics.Color.WHITE;

public class UserFeedbackActivity extends BaseActivity implements View.OnClickListener {

    private int maxSelectNum = 3;
    private List<LocalMedia> selectList = new ArrayList<>();
    private GridImageAdapter adapter;
    private RecyclerView mRecyclerView;
    private PopupWindow pop;
    private EditText etSuggestContent;
    private TextView tvMaxCount;
    private TextView tv1;
    private String string;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userfeed);

        layout_head.setVisibility(View.VISIBLE);
        layout_head.setBackgroundColor(WHITE);
        tv_title.setText("意见反馈");
        tv_title.setTextColor(Color.BLACK);
        btn_left.setBackgroundResource(R.drawable.arrow_back);
        mRecyclerView = findViewById(R.id.recycler);
        initWidget();
        etSuggestContent=findViewById(R.id.et_suggest_content);
        tvMaxCount=findViewById(R.id.tvMaxCount);
        tv1=findViewById(R.id.tv1);
        tv1.setOnClickListener(this);
        etSuggestContent.addTextChangedListener(new TextWatcher() {
            CharSequence input;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                input = s;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tvMaxCount.setText(String.format("%d/240", input.length()));
                if (input.length() > 239) {
                    ToastUtil.showToast(UserFeedbackActivity.this,"您最多能输入240个字");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }
    private void initWidget() {
        FullyGridLayoutManager manager = new FullyGridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(manager);
        adapter = new GridImageAdapter(this, onAddPicClickListener);
        adapter.setList(selectList);
        adapter.setSelectMax(maxSelectNum);
        mRecyclerView.setAdapter(adapter);
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
                            PictureSelector.create(UserFeedbackActivity.this).externalPicturePreview(position, selectList);
                            break;
                        case 2:
                            // 预览视频
                            PictureSelector.create(UserFeedbackActivity.this).externalPictureVideo(media.getPath());
                            break;
                        case 3:
                            // 预览音频
                            PictureSelector.create(UserFeedbackActivity.this).externalPictureAudio(media.getPath());
                            break;
                    }
                }
            }
        });
    }

    private GridImageAdapter.onAddPicClickListener onAddPicClickListener = new GridImageAdapter.onAddPicClickListener() {

        @SuppressLint("CheckResult")
        @Override
        public void onAddPicClick() {
            //获取写的权限
            RxPermissions rxPermission = new RxPermissions(UserFeedbackActivity.this);
            rxPermission.requestEach(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .subscribe(new Consumer<Permission>() {
                        @Override
                        public void accept(Permission permission) {
                            if (permission.granted) {// 用户已经同意该权限
                                //第一种方式，弹出选择和拍照的dialog
                                showPop();

                                //第二种方式，直接进入相册，但是 是有拍照得按钮的
//                                showAlbum();
                            } else {
                                Toast.makeText(UserFeedbackActivity.this, "拒绝", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    };
    private void showPop() {
        View bottomView = View.inflate(UserFeedbackActivity.this, R.layout.layout_bottom_dialog, null);
        TextView mAlbum = bottomView.findViewById(R.id.tv_album);
        TextView mCamera = bottomView.findViewById(R.id.tv_camera);
        TextView mCancel = bottomView.findViewById(R.id.tv_cancel);

        pop = new PopupWindow(bottomView, -1, -2);
        pop.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pop.setOutsideTouchable(true);
        pop.setFocusable(true);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.5f;
        getWindow().setAttributes(lp);
        pop.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });
        pop.setAnimationStyle(R.style.main_menu_photo_anim);
        pop.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.tv_album:
                        //相册
                        PictureSelector.create(UserFeedbackActivity.this)
                                .openGallery(PictureMimeType.ofImage())
                                .maxSelectNum(maxSelectNum)
                                .minSelectNum(1)
                                .imageSpanCount(4)
                                .compress(true)
                                .minimumCompressSize(1024)// 小于1M的图片不压缩
                                .selectionMode(PictureConfig.MULTIPLE)
                                .forResult(PictureConfig.CHOOSE_REQUEST);
                        break;
                    case R.id.tv_camera:
                        //拍照
                        PictureSelector.create(UserFeedbackActivity.this)
                                .openCamera(PictureMimeType.ofImage())
                                .compress(true)
                                .minimumCompressSize(1024)// 小于1M的图片不压缩
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<LocalMedia> images;
        if (resultCode == RESULT_OK) {
            if (requestCode == PictureConfig.CHOOSE_REQUEST) {// 图片选择结果回调
                images = PictureSelector.obtainMultipleResult(data);
                selectList.addAll(images);
                //selectList = PictureSelector.obtainMultipleResult(data);
                // 例如 LocalMedia 里面返回三种path
                // 1.media.getPath(); 为原图path
                // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                adapter.setList(selectList);
                adapter.notifyDataSetChanged();
            }
        }
    }
    public String getString() {
        // 实例化StringBuffer
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < selectList.size(); i++) {
            LocalMedia media=selectList.get(i);
            if(media.isCompressed()){
                String path=uploadMultiFile(media.getCompressPath());
                if (i == selectList.size() - 1) {
                    sb.append(path);
                } else {
                    sb.append(path).append(",");
                }
            }
        }
        return sb.toString();
    }

    public String uploadMultiFile(String imgUrl) {
        String url = HttpConstant.baseUrl + "/common/image/upload";
        File file = new File(imgUrl);//imgUrl为图片位置
        RequestBody fileBody = RequestBody.create(MediaType.parse("image/jpg"), file);
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file",   "upload.jpg", fileBody)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        OkHttpClient mOkHttpClient = new OkHttpClient.Builder()
                .cache(new Cache(mContext.getCacheDir(), 10*1024*1024))
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
//                .addInterceptor(new CaptureInfoInterceptor())
                .build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String data = response.body().string();
                try {
                    JSONObject jsonObject=new JSONObject(data);
                    string=jsonObject.getJSONObject("data").getString("url");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        return string;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv1:
                String url = HttpConstant.baseUrl + "/user/saveFeedback";
                Map<String,String> map=new HashMap<>();
                map.put("user_id", UserSP.getInstance().getUserId(this));
                map.put("content",etSuggestContent.getText().toString().trim());
                map.put("urls",getString());
                OkhttpUtil.okHttpPostJson(this,url,new Gson().toJson(map), new CallBackUtil.CallBackString() {
                    @Override
                    public void onFailure(Call call, Exception e) {

                    }
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(UserFeedbackActivity.this,"提交成功",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
                break;

        }
    }






}
