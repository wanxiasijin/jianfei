package com.chenganrt.smartSupervision.business.electronic.wheel;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;


/**
 * Created by Administrator on 2019/6/10.
 */

public class PopWindow extends PopupWindow {

    private PopupWindow mPopupWindow;
    private int x, y;

    public interface IPopwindow {
        void popDismiss(PopupWindow popupWindow);
    }

    public void setPopView(Context context, View v, View contentView) {
        mPopupWindow = new PopupWindow(contentView, RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT, true);
        mPopupWindow.setBackgroundDrawable(context.getResources().getDrawable(android.R.color
                .transparent));
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOutsideTouchable(true);
        if (mPopupWindow.isShowing()) return;
        int local[] = new int[2];
        v.getLocationOnScreen(local);
        int width = contentView.getWidth();
        int height = contentView.getHeight();
        if (width == 0 || height == 0) {
            int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            contentView.measure(w, h);
            width = contentView.getMeasuredWidth();
            height = contentView.getMeasuredHeight();
        }
        x = local[0] + (v.getWidth() / 2) - width / 2;
        y = local[1] - height;

    }

    public void showPopWindow(View view, int xOff, int yOff) {
        if (mPopupWindow != null)
            mPopupWindow.showAtLocation(view, Gravity.NO_GRAVITY, x + xOff, y - yOff);
    }

    public void dismissPopWindow(final IPopwindow iPopwindow) {
        if (mPopupWindow != null)
            mPopupWindow.setOnDismissListener(new OnDismissListener() {
                @Override
                public void onDismiss() {
                    if (iPopwindow != null) iPopwindow.popDismiss(mPopupWindow);
                }
            });
    }
}
