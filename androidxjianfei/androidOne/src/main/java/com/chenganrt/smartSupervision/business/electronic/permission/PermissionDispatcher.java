package com.chenganrt.smartSupervision.business.electronic.permission;

import android.app.Activity;
import android.content.Intent;

import java.lang.ref.WeakReference;

/**
 */

public class PermissionDispatcher {
    public static final int PERMISSION_REQUEST_CODE = 11;
    private WeakReference<Activity> weakTarget;
    private PermissionInterface pInterface;
    private Intent data;

    public PermissionDispatcher(Activity activity, PermissionInterface pInterface) {
        this.weakTarget = new WeakReference<>(activity);
        this.pInterface = pInterface;
    }

    public void setPermissionInterface(PermissionInterface pInterface) {
        this.pInterface = pInterface;
    }

    public Intent getData() {
        return data;
    }

    public void setData(Intent intent) {
        this.data = intent;
    }

    public void showPermissionWithCheck() {
        if(weakTarget == null || weakTarget.get() == null || pInterface == null) {
            return;
        }
        if(PermissionUtils.hasSelfPermissions(weakTarget.get(), pInterface.getPermissions())) {
            pInterface.onHasPermissions();
        } else {
            if(PermissionUtils.shouldShowRequestPermissionRationale(weakTarget.get(), pInterface.getPermissions()) && pInterface.isShowRationale()) {
                pInterface.onShowRationale(weakTarget.get());
            } else {
                PermissionUtils.requestPermissions(weakTarget.get(), pInterface.getPermissions(), PERMISSION_REQUEST_CODE);
            }
        }
    }

    public void requestPermissions() {
        if(weakTarget == null || weakTarget.get() == null || pInterface == null) {
            return;
        }
        PermissionUtils.requestPermissions(weakTarget.get(), pInterface.getPermissions(), PERMISSION_REQUEST_CODE);
    }


    public void onRequestPermissionsResult(int requestCode, int[] grantResults) {
        if(requestCode == PERMISSION_REQUEST_CODE) {
            if(grantResults != null && PermissionUtils.verifyPermissions(grantResults)) {
                pInterface.onHasPermissions();
            } else {
                if(PermissionUtils.shouldShowRequestPermissionRationale(weakTarget.get(), pInterface.getPermissions())) {
                    pInterface.onPermissionDenied();
                } else {
                    pInterface.onNeverAskAgain();
                }
            }
        }
    }

    public void clear() {
        weakTarget.clear();
        weakTarget = null;
        pInterface = null;
    }


}
