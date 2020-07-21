package com.chenganrt.smartSupervision.business.electronic.wheel;

import android.app.Dialog;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.chenganrt.smartSupervision.R;


/**
 */

public class FFTBottomDialog extends FFTDialog {

    private final static int DIALOG_TYPE = 1;// 0  - 原生   1 - 自定义

    @Override
    protected int getDialogType() {
        return DIALOG_TYPE;
    }

    @Override
    protected Dialog createNativeDialog(FAlertDialog.Builder configBuilder) {
        return createSelfDialog(configBuilder);
    }

    @Override
    protected Dialog createSelfDialog(FAlertDialog.Builder configBuilder) {
        FAlertDialog dialog = getFAlertDialog(configBuilder);

        configBuilder.mGravity = Gravity.BOTTOM;
        initSelfButtonBar(dialog, configBuilder);
        initBottomBar(dialog, configBuilder);

        setFAlertDialogWindow(dialog, configBuilder);
        return dialog;
    }

    protected void initBottomBar(final FAlertDialog dialog, final FAlertDialog.Builder configBuilder) {
        View mContentView = configBuilder.mContentView;
        View bottom_bar = mContentView.findViewById(R.id.bottom_bar);
        Button neutralBtn = (Button) mContentView.findViewById(R.id.neutral_btn);
        Button neutral2Btn = (Button) mContentView.findViewById(R.id.neutral2_btn);
        LinearLayout layout_neutral_btn = (LinearLayout) mContentView.findViewById(R.id.layout_neutral_btn);
        LinearLayout layout_neutral2_btn = (LinearLayout) mContentView.findViewById(R.id.layout_neutral2_btn);
        View separator = mContentView.findViewById(R.id.button_separator);
        View separator2 = mContentView.findViewById(R.id.button_separator2);
        if (!TextUtils.isEmpty(configBuilder.mNeutralBtnText)) {
            if (configBuilder.mNeutralStyle != 0) {
                neutralBtn.setTextAppearance(configBuilder.mContext, configBuilder.mNeutralStyle);
            }
            neutralBtn.setVisibility(View.VISIBLE);
            neutralBtn.setText(configBuilder.mNeutralBtnText);
            neutralBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (configBuilder.mNeutralBtnListener != null)
                        configBuilder.mNeutralBtnListener.onClick(dialog, DialogInterface.BUTTON_NEGATIVE);
                    dialog.dismiss();
                }
            });
        } else {
            layout_neutral_btn.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(configBuilder.mNeutral2BtnText)) {
            if (configBuilder.mNeutral2Style != 0) {
                neutral2Btn.setTextAppearance(configBuilder.mContext, configBuilder.mNeutral2Style);
            }
            neutral2Btn.setVisibility(View.VISIBLE);
            neutral2Btn.setText(configBuilder.mNeutral2BtnText);
            neutral2Btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (configBuilder.mNeutral2BtnListener != null)
                        configBuilder.mNeutral2BtnListener.onClick(dialog, configBuilder.BUTTON_NEUTRAL2);
                    dialog.dismiss();
                }
            });
        } else {
            layout_neutral2_btn.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(configBuilder.mPositivieBtnText) && !TextUtils.isEmpty(configBuilder.mNeutralBtnText) && !TextUtils.isEmpty(configBuilder.mNeutral2BtnText)) {
            separator.setVisibility(View.VISIBLE);
            separator2.setVisibility(View.VISIBLE);
        } else if (!TextUtils.isEmpty(configBuilder.mPositivieBtnText) && !TextUtils.isEmpty(configBuilder.mNeutralBtnText)) {
            separator.setVisibility(View.VISIBLE);
            separator2.setVisibility(View.GONE);
        } else if (!TextUtils.isEmpty(configBuilder.mPositivieBtnText) && !TextUtils.isEmpty(configBuilder.mNeutral2BtnText)) {
            separator.setVisibility(View.VISIBLE);
            separator2.setVisibility(View.GONE);
        } else if (!TextUtils.isEmpty(configBuilder.mNeutralBtnText) && !TextUtils.isEmpty(configBuilder.mNeutral2BtnText)) {
            separator.setVisibility(View.GONE);
            separator2.setVisibility(View.VISIBLE);
        } else {
            separator.setVisibility(View.GONE);
            separator2.setVisibility(View.GONE);
        }
    }
}
