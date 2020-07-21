package com.chenganrt.smartSupervision.business.electronic.wheel;

import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.chenganrt.smartSupervision.R;


public class FNormalDialog extends FAlertDialog {
    private Context mContext;

    protected FNormalDialog(Context context, int theme) {
        super(context, theme);
    }

    protected FNormalDialog(Context context) {
        super(context);
    }


    public static Builder createNormalDialog(Context context) {
        final FNormalDialog.Builder builder = new FNormalDialog.Builder(context, R.layout.dialog_normal);
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

        private TextView title, content;
        private TextView positive, negative;

        @Override
        public FNormalDialog create() {
            FNormalDialog dialog = new FNormalDialog(mContext, mTheme);
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

        public void setNegativeText(String t) {
            negative.setText(t);
        }

        public void setPositiveText(String t) {
            positive.setText(t);
        }

        public void setTitles(String t) {
            title.setText(t);
            title.setVisibility(View.VISIBLE);
        }

        public void setContents(String c) {
            content.setText(c);
            content.setVisibility(View.VISIBLE);
        }

        private void init(final FNormalDialog dialog) {
            positive = (TextView) mContentView.findViewById(R.id.sure);
            negative = (TextView) mContentView.findViewById(R.id.cancel);
            title = (TextView) mContentView.findViewById(R.id.tv_title);
            content = (TextView) mContentView.findViewById(R.id.tv_content);

            positive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemChooseListener != null)
                        mOnItemChooseListener.onPositiveListener(dialog);
                }
            });

            negative.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

        }

        public void setOnItemChooseListener(OnItemChooseListener l) {
            this.mOnItemChooseListener = l;
        }

    }

    public interface OnItemChooseListener {
        void onNegativeListener(Dialog dialog);

        void onPositiveListener(Dialog dialog);
    }
}
