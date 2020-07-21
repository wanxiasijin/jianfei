package com.chenganrt.smartSupervision.business.electronic.wheel;

import android.app.Dialog;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chenganrt.smartSupervision.R;


/**
 */

public class FFTDialog {

    public Dialog createDialog(FAlertDialog.Builder configBuilder) {
        if(getDialogType() == 0 && !configBuilder.mUseCusView) {
            return createNativeDialog(configBuilder);
        }
        return createSelfDialog(configBuilder);
    }

    /**
     * 原生dialog
     *@param configBuilder 配置builder
     *  **/
    protected Dialog createNativeDialog(FAlertDialog.Builder configBuilder) {
        return null;
    }

    /***
     * 自定义dialog
     * @param configBuilder 配置builder
     * **/
    protected Dialog createSelfDialog(FAlertDialog.Builder configBuilder) {
        return null;
    }

    protected FAlertDialog getFAlertDialog(FAlertDialog.Builder configBuilder) {
        FAlertDialog dialog = new FAlertDialog(configBuilder.mContext, configBuilder.mTheme);
        dialog.setContentView(configBuilder.mContentView);
        dialog.setCancelable(configBuilder.mCancelable);
        dialog.setCanceledOnTouchOutside(configBuilder.mCanceledOnTouchOutside);
        dialog.setOnCancelListener(configBuilder.mCancelListener);
        dialog.setOnKeyListener(configBuilder.mOnKeyListener);
        dialog.setOnDismissListener(configBuilder.mOnDismissListener);
        dialog.setOnShowListener(configBuilder.mOnShowListener);
        return dialog;
    }

    protected void setFAlertDialogWindow(FAlertDialog dialog, FAlertDialog.Builder configBuilder) {
        if (configBuilder.mGravity > 0) {
            Window window = dialog.getWindow();
            window.setGravity(configBuilder.mGravity);
        }

        if (configBuilder.mIsFullscreen) {
            Window win = dialog.getWindow();
            win.getDecorView().setPadding(0, 0, 0, 0);
            WindowManager.LayoutParams lp = win.getAttributes();
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            win.setAttributes(lp);
        } else {
            Window win = dialog.getWindow();
            WindowManager.LayoutParams lp = win.getAttributes();
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            win.setAttributes(lp);
        }
    }

    protected void initSelfTitleBar(FAlertDialog.Builder configBuilder) {
        View mContentView = configBuilder.mContentView;
        View titleBar = mContentView.findViewById(R.id.title_bar);
        if (!TextUtils.isEmpty(configBuilder.mTitle)) {
            TextView titleText = (TextView) mContentView.findViewById(R.id.title);
            if (configBuilder.mTitleColor != -1) {
                titleText.setTextColor(configBuilder.mTitleColor);
            }
            View separator = mContentView.findViewById(R.id.title_separator);
            if (separator != null) {
                separator.setBackgroundColor(titleText.getCurrentTextColor());
            }
            titleText.setText(configBuilder.mTitle);
            titleBar.setVisibility(View.VISIBLE);
        } else {
            titleBar.setVisibility(View.GONE);
        }
    }

    protected void initSelfMessageView(FAlertDialog.Builder configBuilder) {
        View mContentView = configBuilder.mContentView;
        TextView message = (TextView) mContentView.findViewById(R.id.message);
        if (!TextUtils.isEmpty(configBuilder.mMessage)) {
            message.setText(configBuilder.mMessage);
            message.setVisibility(View.VISIBLE);
        } else {
            message.setVisibility(View.GONE);
        }
    }

    protected void initSelfButtonBar(final FAlertDialog dialog, final FAlertDialog.Builder configBuilder) {
        View mContentView = configBuilder.mContentView;
        Button positiveBtn = (Button) mContentView.findViewById(R.id.positive_btn);
        Button negativeBtn = (Button) mContentView.findViewById(R.id.negative_btn);
        LinearLayout layout_positive_btn = (LinearLayout) mContentView.findViewById(R.id.layout_positive_btn);
        LinearLayout layout_negative_btn = (LinearLayout) mContentView.findViewById(R.id.layout_negative_btn);
        View separator = mContentView.findViewById(R.id.button_separator);

        if (!TextUtils.isEmpty(configBuilder.mPositivieBtnText)) {
            if (configBuilder.mPositivieStyle != 0) {
                positiveBtn.setTextAppearance(configBuilder.mContext, configBuilder.mPositivieStyle);
            }
            positiveBtn.setVisibility(View.VISIBLE);
            positiveBtn.setText(configBuilder.mPositivieBtnText);
            positiveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (configBuilder.mPositiveBtnListener != null)
                        configBuilder.mPositiveBtnListener.onClick(dialog, DialogInterface.BUTTON_POSITIVE);
                    dialog.dismiss();
                }
            });
        } else {
            layout_positive_btn.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(configBuilder.mNegativieBtnText)) {
            if (configBuilder.mNegativeStyle != 0) {
                negativeBtn.setTextAppearance(configBuilder.mContext, configBuilder.mNegativeStyle);
            }
            negativeBtn.setVisibility(View.VISIBLE);
            negativeBtn.setText(configBuilder.mNegativieBtnText);
            negativeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (configBuilder.mNegativeBtnListener != null)
                        configBuilder.mNegativeBtnListener.onClick(dialog, DialogInterface.BUTTON_NEGATIVE);
                    dialog.dismiss();
                }
            });
        } else {
            layout_negative_btn.setVisibility(View.GONE);
        }

        if (separator != null) {
            if (!TextUtils.isEmpty(configBuilder.mPositivieBtnText) && !TextUtils.isEmpty(configBuilder.mNegativieBtnText)) {
                separator.setVisibility(View.VISIBLE);
            } else {
                separator.setVisibility(View.GONE);
            }
        }
    }

    protected void initSelfCustomView(FAlertDialog.Builder configBuilder, View view) {
        ((ViewGroup) configBuilder.mContentView.findViewById(R.id.custom_view))
                .addView(view);
    }

    protected void setWheelCurrentPostion(FAlertDialog.Builder configBuilder, int postion1, int postion2, int postion3) {
    }

    protected int getDialogType() {
        return 1;
    }

}
