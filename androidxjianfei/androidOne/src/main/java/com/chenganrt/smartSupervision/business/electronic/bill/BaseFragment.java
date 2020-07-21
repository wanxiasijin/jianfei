package com.chenganrt.smartSupervision.business.electronic.bill;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.chenganrt.smartSupervision.R;

import androidx.annotation.MenuRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;


public class BaseFragment extends Fragment implements Toolbar.OnMenuItemClickListener {

    public interface FgActivityInterface {
        void onFragmentFilled(String name, Intent data);
    }

    protected Context ctx;
    protected Toolbar toolbar;

    @Override
    public Context getContext() {
        return ctx;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ctx = activity.getApplicationContext();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initToolBar(view);
    }

    public void initToolBar(View view) {
        if (view.findViewById(R.id.toolbar) == null) return;
        this.toolbar = (Toolbar) view.findViewById(R.id.toolbar);
    }

    public void inflateMenu(@MenuRes int resId) {
        if (toolbar == null) return;
        toolbar.inflateMenu(resId);
        toolbar.setOnMenuItemClickListener(this);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }

    @SuppressLint("RestrictedApi")
    public void setDisplayHomeAsUpEnabled(boolean enabled) {
        if (toolbar == null) return;
        toolbar.getWrapper().setHomeButtonEnabled(true);
        int options = toolbar.getWrapper().getDisplayOptions();
        if (enabled) options |= ActionBar.DISPLAY_HOME_AS_UP;
        else options &= ~ActionBar.DISPLAY_HOME_AS_UP;
        toolbar.getWrapper().setDisplayOptions(options);
        setNavigationListener();
    }

    public void setNavigationListener() {
        if (toolbar == null) return;
    }

    public void setToolbarNavigation(int resID) {
        if (toolbar == null) return;
        toolbar.setNavigationIcon(resID);
    }

   // protected HProgressDialog progressDialog;
    private int dialogCount = 0;

    public void showLoadDialog(int resId) {
//        if (progressDialog == null) {
//            HProgressDialog.Builder mBuilder = new HProgressDialog.Builder(getActivity());
//            mBuilder.setCanceledOnTouchOutside(false);
//            mBuilder.setCancelable(true);
//            mBuilder.setMessage(resId);
//            progressDialog = mBuilder.create();
//        }
//        //progressDialog.setMessage(getString(resId));
//        dialogCount++;
//        if (getActivity() == null || getActivity().isFinishing()) {
//            return;
//        }
//        if (!progressDialog.isShowing()) {
//            progressDialog.show();
//        }

    }

    public void showLoadingDialog(int strId) {
        showLoadingDialog(strId, null);
    }



    public void showLoadingDialog(int strId, DialogInterface.OnCancelListener onCancelListener) {
//        if (progressDialog == null) {
//            HProgressDialog.Builder mBuilder = new HProgressDialog.Builder(getActivity());
//            mBuilder.setCanceledOnTouchOutside(false);
//            mBuilder.setCancelable(onCancelListener != null);
//            mBuilder.setCancelListener(onCancelListener);
//            mBuilder.setMessage(strId);
//            progressDialog = mBuilder.create();
//        }
//        //progressDialog.setMessage(getString(strId));
//        dialogCount++;
//        if (getActivity() == null || getActivity().isFinishing()) {
//            return;
//        }
//        if (!progressDialog.isShowing()) {
//            progressDialog.show();
//        }

    }


    public void dismissLoadingDialog(boolean force) {
//        dialogCount = force ? 0 : dialogCount - 1;
//        if (getActivity() == null || getActivity().isFinishing()) {
//            return;
//        }
//        if (progressDialog != null && progressDialog.isShowing()
//                && dialogCount <= 0) {
//            progressDialog.dismiss();
//        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public boolean onBackPressed() {
        return false;
    }

}
