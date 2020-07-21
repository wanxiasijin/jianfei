package com.chenganrt.smartSupervision.business.electronic.permission;

import android.app.Activity;
import android.content.DialogInterface;
import android.text.TextUtils;

import com.chenganrt.smartSupervision.R;
import com.chenganrt.smartSupervision.business.electronic.wheel.FAlertDialog;


/**
 */

public class AbstractPermissionImp{

    /**
     * Resquests the READ_PHONE_STATE
     * **/
    public static abstract class PhoneStatePermission extends EasyBasePermission {
        @Override
        public String[] getPermissions() {
            return new String[]{"android.permission.READ_PHONE_STATE"};
        }

        @Override
        public String getRationaleMsg() {
            return null;
        }

        @Override
        public boolean isShowRationale() {
            return false;
        }

        @Override
        public void proceed() {

        }

        @Override
        public void cancel() {

        }
    }

    /***
     * Requests the external storage
     * **/
    public static abstract class ExternalStoragePermission extends EasyBasePermission {
        @Override
        public String[] getPermissions() {
            return new String[]{"android.permission.WRITE_EXTERNAL_STORAGE","android.permission.READ_EXTERNAL_STORAGE"};
        }

        @Override
        public String getRationaleMsg() {
            return "请开启SD卡权限";
        }

        @Override
        public boolean isShowRationale() {
            return false;
        }

        @Override
        public void proceed() {

        }

        @Override
        public void cancel() {

        }
    }

    /**
     * Requests the camera permission
     * **/
    public static abstract class CameraPermission extends EasyBasePermission{
        @Override
        public String[] getPermissions() {
            return new String[]{"android.permission.CAMERA"};
        }

        @Override
        public String getRationaleMsg() {
            return "该功能需要开启照相机权限";
        }
    }

    /**
     * Requests the camera and photo album permission
     * **/
    public static abstract class CameraAndPhotoPermission extends EasyBasePermission{
        @Override
        public String[] getPermissions() {
            return new String[]{"android.permission.WRITE_EXTERNAL_STORAGE","android.permission.READ_EXTERNAL_STORAGE", "android.permission.CAMERA"};
        }

        @Override
        public String getRationaleMsg() {
            return "该功能需要开启相机权限";
        }

        @Override
        public boolean isShowRationale() {
            return false;
        }

        @Override
        public void proceed() {

        }

        @Override
        public void cancel() {

        }

    }

    /**
     * Requests the Scan Location permission
     * **/
    public static abstract class LocationPermission extends EasyBasePermission{
        @Override
        public String[] getPermissions() {
            return new String[]{"android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_COARSE_LOCATION"};
        }

        @Override
        public boolean isShowRationale() {
            return false;
        }

        @Override
        public String getRationaleMsg() {
            return null;
        }

        @Override
        public void proceed() {

        }

        @Override
        public void cancel() {

        }
    }

    /**
     * Requests the Scan Blutooth permission
     * **/
    public static abstract class BlutoothPermission extends EasyBasePermission{
        @Override
        public String[] getPermissions() {
            return new String[]{"android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_COARSE_LOCATION"};
        }

        @Override
        public boolean isShowRationale() {
            return super.isShowRationale();
        }

        @Override
        public String getRationaleMsg() {
            return "该功能需要开启蓝牙搜索权限";
        }
    }


    public static abstract class EasyBasePermission implements PermissionInterface, RationaleRequest{

        @Override
        public boolean isShowRationale() {
            return true;
        }

        @Override
        public void onShowRationale(final Activity activity) {
            String rationaleMsg = getRationaleMsg();
            if(TextUtils.isEmpty(rationaleMsg)) {
                rationaleMsg = activity.getString(R.string.permission_common_rational);
            }
            FAlertDialog.Builder builder = FAlertDialog.createMessage(activity, rationaleMsg);
            builder.setPositiveButton(R.string.positive, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    PermissionUtils.requestPermissions(activity, getPermissions(), PermissionDispatcher.PERMISSION_REQUEST_CODE);
                    proceed();
                }
            });

            builder.setNegativeButton(R.string.negative, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    cancel();
                }
            });
            builder.show();
        }
    }

}
