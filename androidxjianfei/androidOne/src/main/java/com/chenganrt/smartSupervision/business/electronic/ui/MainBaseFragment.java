package com.chenganrt.smartSupervision.business.electronic.ui;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;

import com.android.commonlib.utils.WindowUtil;
import com.chenganrt.smartSupervision.R;
import com.chenganrt.smartSupervision.business.electronic.bill.BaseFragment;

import androidx.annotation.StringRes;


public abstract class MainBaseFragment extends BaseFragment {
    protected TextView title;
    private TextView titlecenter;
    public TextView titleLeft;
    public TextView titleRight;

    public String getDescripTag() {
        return this.getClass().getName();
    }

    public void onShown() {
    }

    @Override
    public void onActivityResult(int arg0, int arg1, Intent arg2) {
        super.onActivityResult(arg0, arg1, arg2);
    }

    @Override
    public void initToolBar(View view) {
        super.initToolBar(view);
        WindowUtil.getInstence().setTransparentStatusBar(getActivity());
        WindowUtil.setStatusTextColor(true, getActivity().getWindow(), getResources().getColor(R.color.black));
        title = (TextView) view.findViewById(android.R.id.title);
        titlecenter = (TextView) view.findViewById(R.id.titlecenter);
        titleLeft = (TextView) view.findViewById(R.id.titleleft);
        titleRight = (TextView) view.findViewById(R.id.titleright);
        titleLeftListener();
        titleRightListener();
    }

    public void sendMessage(Intent intent) {
    }

    public void titleLeftListener() {

    }

    public void titleRightListener() {

    }

    public void setLeftTitle(String text) {
        titleLeft.setText(text);
    }

    public void setRightTitle(String text) {
        titleRight.setText(text);
    }

    public void setLeftTitle(int resID) {
        setLeftTitle(getString(resID));
    }

    public void setCenterTitle(String text) {
        titlecenter.setText(text);
    }

    public void setCenterTitle(int resID) {
        setCenterTitle(getString(resID));
    }

    public void setTitleLeftColor(int colorId) {
        if (titleLeft != null)
            titleLeft.setTextColor(getContext().getResources().getColor(colorId));
    }

    public void setLeftDrawable(Drawable drawable) {
        if (titleLeft != null)
            titleLeft.setCompoundDrawables(drawable, null, null, null);
    }

    public void setTitleRightColor(int colorId) {
        if (titleRight != null)
            titleRight.setTextColor(getContext().getResources().getColor(colorId));
    }

    public void setRightDrawable(int drawableId) {
        if (titleRight != null)
            titleRight.setBackgroundDrawable(getContext().getResources().getDrawable(drawableId));
    }

    public void setTitle(@StringRes int resID) {
        setTitle(getString(resID));
    }

    public void setTitle(String text) {
        if (toolbar != null) {
            toolbar.setTitle(text);
        }
    }
}
