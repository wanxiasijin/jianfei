package com.chenganrt.smartSupervision.business.electronic.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.chenganrt.smartSupervision.R;
import com.chenganrt.smartSupervision.business.electronic.BillAction;
import com.chenganrt.smartSupervision.business.electronic.util.FileUtil;
import com.chenganrt.smartSupervision.business.electronic.util.LogUtil;
import com.chenganrt.smartSupervision.business.electronic.util.ToastUtil;
import com.chenganrt.smartSupervision.business.electronic.okhttp.UICallback;
import com.chenganrt.smartSupervision.business.electronic.permission.AbstractPermissionImp;
import com.chenganrt.smartSupervision.business.electronic.permission.PermissionDispatcher;
import com.chenganrt.smartSupervision.business.electronic.permission.PermissionInterface;
import com.chenganrt.smartSupervision.business.electronic.ui.PhotoTools;
import com.chenganrt.smartSupervision.business.electronic.ui.adapter.ImageAdapter;
import com.chenganrt.smartSupervision.business.electronic.wheel.FAlertDialog;
import com.chenganrt.smartSupervision.business.electronic.wheel.FileProgressDialog;
import com.chenganrt.smartSupervision.business.login.UserSP;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import androidx.annotation.NonNull;
import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

import static com.chenganrt.smartSupervision.business.electronic.util.AppConstant.TaskState.ISRUNING;

/**
 * Created by Administrator on 2019/11/25.
 */

public class SoilQualityActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    private final int REQUEST_CODE = 1010;
    private static final int MAX_IMAGE = 5;
    private static final int PHOTOS = 0x08;
    private final int IMAGE_MAX_SIZE = 5 * 1024;

    public static final String TYPE_DRIVER = "1";//司机
    public static final String TYPE_VEHICLE = "2";//车辆
    public static final String TYPE_SOIL = "3";//土质

    private final String IMAGE_HEAD = "data:image/jpeg;base64,";

    @BindView(R.id.gridView)
    GridView gridView;

    private List<Uri> uriList;
    private PermissionDispatcher dispatcher;
    private ImageAdapter imageAdapter;
    private File file;

    private BillAction billAction;
    private ExecutorService singleExecutor;
    private Future<?> future;
    private Call call;
    private String orderId, imageType;

    public static Intent startAct(Activity activity, String imageType, String orderId, List<Uri> uriList) {
        Intent intent = new Intent(activity, SoilQualityActivity.class);
        intent.putExtra("imageType", imageType);
        intent.putExtra("orderId", orderId);
        intent.putExtra("uriList", (Serializable) uriList);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_soil_image);
        ButterKnife.bind(this);
        setSubTitle(R.string.upload_image);
        initView();
        initData();
    }

    private void initView() {
        singleExecutor = Executors.newSingleThreadExecutor();

        imageAdapter = new ImageAdapter(getContext());
        gridView.setAdapter(imageAdapter);
        gridView.setOnItemClickListener(this);
        imageAdapter.refresh(getUrlList());
    }

    private void initData() {
        orderId = getIntent().getStringExtra("orderId");
        imageType = getIntent().getStringExtra("imageType");
    }

    private List<Uri> getUrlList() {
        uriList = (List<Uri>) getIntent().getSerializableExtra("uriList");
        if (uriList == null) uriList = new ArrayList<>();
        return uriList;
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        //Uri uri = (Uri) adapterView.getItemAtPosition(position);
        if (position == 0) {
            if (uriList.size() >= MAX_IMAGE) {
                ToastUtil.showToast(getContext(), R.string.photo_limit);
                return;
            }
            takePhoto();
        } else {

        }
    }

    private void takePhoto() {
        if (dispatcher == null) {
            dispatcher = new PermissionDispatcher(getActivity(), getPmInterface());
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
            builder.detectFileUriExposure();
        }
        Intent intent = new Intent();
        dispatcher.setData(intent);
        dispatcher.showPermissionWithCheck();
    }

    private File getImageFile() {
        String name = "szelect_" + DateFormat.format("yyyyMMdd_hhmmss", System.currentTimeMillis()).toString() + ".jpg";
        return new File(FileUtil.getUploadDir(getActivity()), name);
    }

    class CompressTask implements Callable<String> {
        private String imgPath, outPath;
        private int what;

        public CompressTask(String imgPath, String outPath, int what) {
            this.imgPath = imgPath;
            this.outPath = outPath;
            this.what = what;
        }

        @Override
        public String call() throws Exception {
            try {
                handler.obtainMessage(ISRUNING).sendToTarget();
                PhotoTools.compressAndGenImage(imgPath, outPath, IMAGE_MAX_SIZE, false);
                if (Thread.currentThread().isInterrupted()) return "";
                handler.obtainMessage(what, outPath).sendToTarget();
            } catch (IOException e) {
                e.printStackTrace();
                handler.obtainMessage(what).sendToTarget();
            }
            return null;
        }
    }

    protected Handler handler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            if (msg.what == ISRUNING) {
                showLoadingDialog(R.string.uploade_compressing, false);
            } else if (msg.what == PHOTOS) {
                dismissLoadingDialog();
                if (msg.obj == null) {
                    FAlertDialog.createMessage(getActivity(), R.string.uploade_compress_fail).show();
                    return;
                }
                file = new File(msg.obj.toString());
                //upLoadPhotos();
                startUpload();
            }
        }
    };

    private void checkImage() {
        if (progressDialog != null && progressDialog.isShowing()) return;
        if (file == null || !file.exists() || file.length() <= 0) {
            FAlertDialog.createMessage(getActivity(), R.string.photo_not_exist).show();
        } else if (file.length() / 1024 >= IMAGE_MAX_SIZE) {
            File out = getImageFile();
            future = singleExecutor.submit(new CompressTask(file.getAbsolutePath(), out.getAbsolutePath(), PHOTOS));
        } else {
            //upLoadPhotos();
            startUpload();
        }
    }

    private void showImage() {
        Uri uri = Uri.fromFile(file);
        imageAdapter.addImage(uri);
        uriList.add(uri);
    }

    private void upLoadPhotos() {
        if (billAction == null) billAction = new BillAction();
        String imageData = IMAGE_HEAD + PhotoTools.fileToBase64(file);
        billAction.upLoadImage(orderId, imageType, imageData, UserSP.getInstance().getUserName(getContext()), new UICallback(getActivity(), true) {
            @Override
            public void handleSuccess(Message msg) {
                ToastUtil.showToast(getContext(), R.string.uploade_image_success);
                showImage();
            }

            @Override
            public void handleRuning(Message msg) {
                super.handleRuning(msg);
            }
        });
    }

    public void startUpload() {
        //showLoadingDialog(R.string.progress_loading, false);
        //setProgressMsg(String.format(getString(R.string.progress_loading), " 0%"));
        //String imageData = IMAGE_HEAD + PhotoTools.fileToBase64(file);
        final FileProgressDialog.Builder builder = FileProgressDialog.createFileDialog(getActivity());
        final FileProgressDialog fileProgressDialog = builder.create();
        builder.setCancelable(false);//
//        call = new UpdateAction().Upload(file, orderId, imageType, "", UserUtil.getInstance().getUserName(getContext()), new UpLoadCallback(getActivity()) {
//            @Override
//            public void handleSuccess(Message msg) {
//                ToastUtil.showToast(getContext(), R.string.uploade_image_success);
//                showImage();
//            }
//
//            @Override
//            public void onProgress(long currentBytes, long contentLength, boolean done) {
//                double accuracy = AppTools.accuracyFormat(currentBytes, contentLength, 0);
//                if (fileProgressDialog != null) {
//                    builder.setProgress((float) accuracy);
//                    builder.setTvProgeress(String.format("%1.2f", (float) accuracy));
//                }
//                //setProgressMsg(String.format(getString(R.string.progress_loading), accuracy));
//            }
//
//            @Override
//            public void onStart() {
//                if (fileProgressDialog != null && !fileProgressDialog.isShowing())
//                    fileProgressDialog.show();
//            }
//
//            @Override
//            public void onFinish() {
//                if (fileProgressDialog != null && fileProgressDialog.isShowing())
//                    fileProgressDialog.dismiss();
//            }
//
//            @Override
//            public void handleFailure(Message msg) {
//                super.handleFailure(msg);
//                if (fileProgressDialog != null && fileProgressDialog.isShowing())
//                    fileProgressDialog.dismiss();
//            }
//        });
    }

    private void getExternalStoragePermission() {
        dispatcher.setPermissionInterface(new AbstractPermissionImp.ExternalStoragePermission() {
            @Override
            public void onHasPermissions() {
                file = getImageFile();
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                startActivityForResult(intent, REQUEST_CODE);
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
                } catch (Exception e) {
                    LogUtil.i(e.getMessage());
                    ToastUtil.showToast(getContext(), "打开设置页面失败,请到手机设置里打开");
                }
            }
        });
        dispatcher.showPermissionWithCheck();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            checkImage();
        }
    }


    private PermissionInterface getPmInterface() {
        return new AbstractPermissionImp.CameraAndPhotoPermission() {
            @Override
            public void onHasPermissions() {
                getExternalStoragePermission();
            }

            @Override
            public void onPermissionDenied() {
                ToastUtil.showToast(getActivity(), R.string.permission_camera_denied);
            }

            @Override
            public void onNeverAskAgain() {
                ToastUtil.showToast(getActivity(), R.string.permission_camera_neverask);
            }
        };
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (dispatcher != null)
            dispatcher.onRequestPermissionsResult(requestCode, grantResults);
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK, new Intent().putExtra("uriList", (Serializable) uriList));
        finish();
    }

}
