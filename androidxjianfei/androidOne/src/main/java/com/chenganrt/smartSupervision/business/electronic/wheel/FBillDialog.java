package com.chenganrt.smartSupervision.business.electronic.wheel;

import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.chenganrt.smartSupervision.R;


public class FBillDialog extends FAlertDialog {
    private Context mContext;

    protected FBillDialog(Context context, int theme) {
        super(context, theme);
    }

    protected FBillDialog(Context context) {
        super(context);
    }


    public static Builder createCancelDialog(Context context) {
        final FBillDialog.Builder builder = new FBillDialog.Builder(context, R.layout.layout_dialog_edit);
        builder.setCancelable(true);
        builder.setCanceledOnTouchOutside(false);
        return builder;
    }

    public static class Builder extends FAlertDialog.Builder {

        protected OnItemChooseListener mOnItemChooseListener;

        public Builder(Context context) {
            super(context);
        }

        public Builder(Context context, int resource) {
            super(context, resource);
        }

        @Override
        public FBillDialog create() {
            FBillDialog dialog = new FBillDialog(mContext, mTheme);
            dialog.setContentView(mContentView);
            dialog.setCancelable(mCancelable);
            dialog.setCanceledOnTouchOutside(mCanceledOnTouchOutside);
            dialog.setOnCancelListener(mCancelListener);
            dialog.setOnKeyListener(mOnKeyListener);
            init(dialog);
            if (mGravity > 0) {
                Window window = dialog.getWindow();
                window.setGravity(mGravity);
            }

            if (mIsFullscreen) {
                Window win = dialog.getWindow();
                win.getDecorView().setPadding(0, 0, 0, 0);
                WindowManager.LayoutParams lp = win.getAttributes();
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                win.setAttributes(lp);
            } else {
                Window win = dialog.getWindow();
                win.getDecorView().setPadding(0, 0, 0, 0);
                WindowManager.LayoutParams lp = win.getAttributes();
                DisplayMetrics d = mContext.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
                lp.width = (int) (d.widthPixels * 0.8); // 宽度设置为屏幕的0.8
                //lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                win.setAttributes(lp);
            }
            return dialog;
        }

        private void init(final FBillDialog dialog) {
            TextView positive = (TextView) mContentView.findViewById(R.id.btn_dialog_ok);
            final EditText editText = (EditText) mContentView.findViewById(R.id.et_sim_no);
            positive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemChooseListener != null)
                        mOnItemChooseListener.onPositiveListener(dialog, editText.getText().toString().trim());
                }
            });

        }

        public void setOnItemChooseListener(OnItemChooseListener l) {
            this.mOnItemChooseListener = l;
        }

    }

    public interface OnItemChooseListener {
        void onNegativeListener(Dialog dialog);

        void onPositiveListener(Dialog dialog, String content);
    }
}
