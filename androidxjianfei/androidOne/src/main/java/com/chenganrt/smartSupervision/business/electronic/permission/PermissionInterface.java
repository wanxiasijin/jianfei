package com.chenganrt.smartSupervision.business.electronic.permission;

import android.app.Activity;

/**
 * Created by cuiwenkai on 2017/7/19.
 */

public interface PermissionInterface {
    public String[] getPermissions();
    public boolean isShowRationale();
    public void onHasPermissions();
    public String getRationaleMsg();
    public void onShowRationale(Activity activity);
    public void onPermissionDenied();
    public void onNeverAskAgain();
}
