package com.chenganrt.smartSupervision.business.electronic.wheel;

import android.app.Dialog;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AlertDialog;


public class FFTGeneralDialog extends FFTDialog {
    private final static int DIALOG_TYPE = 0;// 0  - 原生   1 - 自定义

    @Override
    protected int getDialogType() {
        return DIALOG_TYPE;
    }

    @Override
    protected Dialog createNativeDialog(FAlertDialog.Builder configBuilder) {
        AlertDialog.Builder builder = new AlertDialog.Builder(configBuilder.mContext);

        if (!TextUtils.isEmpty(configBuilder.mTitle)) {
            builder.setTitle(configBuilder.mTitle);
        }
        if(!TextUtils.isEmpty(configBuilder.mMessage)) {
            builder.setMessage(configBuilder.mMessage);
        }
        initNativeButtonBar(builder, configBuilder);

        Dialog dialog = builder.create();
        dialog.setCancelable(configBuilder.mCancelable);
        dialog.setCanceledOnTouchOutside(configBuilder.mCanceledOnTouchOutside);
        dialog.setOnCancelListener(configBuilder.mCancelListener);
        dialog.setOnKeyListener(configBuilder.mOnKeyListener);
        dialog.setOnDismissListener(configBuilder.mOnDismissListener);
        dialog.setOnShowListener(configBuilder.mOnShowListener);

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
        return dialog;
    }

    private void initNativeButtonBar(AlertDialog.Builder builder, final FAlertDialog.Builder configBuilder) {
        if (!TextUtils.isEmpty(configBuilder.mPositivieBtnText)) {
            builder.setPositiveButton(configBuilder.mPositivieBtnText, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(configBuilder.mPositiveBtnListener != null) {
                        configBuilder.mPositiveBtnListener.onClick(dialog, which);
                    }
                    dialog.dismiss();
                }
            });
        }
        if (!TextUtils.isEmpty(configBuilder.mNegativieBtnText)) {
            builder.setNegativeButton(configBuilder.mNegativieBtnText, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (configBuilder.mNegativeBtnListener != null)
                        configBuilder.mNegativeBtnListener.onClick(dialog, which);
                    dialog.dismiss();
                }
            });
        }
        if (!TextUtils.isEmpty(configBuilder.mNeutralBtnText)) {
            builder.setNeutralButton(configBuilder.mNeutralBtnText, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (configBuilder.mNeutralBtnListener != null)
                        configBuilder.mNeutralBtnListener.onClick(dialog, which);
                    dialog.dismiss();
                }
            });
        }
    }

    @Override
    protected Dialog createSelfDialog(FAlertDialog.Builder configBuilder) {
        FAlertDialog dialog = getFAlertDialog(configBuilder);

        initSelfTitleBar(configBuilder);
        initSelfMessageView(configBuilder);
        initSelfButtonBar(dialog, configBuilder);

        setFAlertDialogWindow(dialog, configBuilder);
        return dialog;
    }

}
