package com.chenganrt.smartSupervision.business.electronic.wheel;

import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.chenganrt.smartSupervision.R;


public class FSearchDialog extends FAlertDialog {
    private Context mContext;

    protected FSearchDialog(Context context, int theme) {
        super(context, theme);
    }

    public FSearchDialog(Context context) {
        super(context);
    }


    public static Builder createSearchDialog(Context context) {
        final FSearchDialog.Builder builder = new FSearchDialog.Builder(context, R.layout.dialog_search);
        builder.setCancelable(false);
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
        public FSearchDialog create() {
            FSearchDialog dialog = new FSearchDialog(mContext, mTheme);
            dialog.setContentView(mContentView);
            dialog.setCancelable(mCancelable);
            dialog.setCanceledOnTouchOutside(mCanceledOnTouchOutside);
            dialog.setOnCancelListener(mCancelListener);
            dialog.setOnKeyListener(mOnKeyListener);
            init(dialog);
            Window window = dialog.getWindow();
            window.setGravity(Gravity.TOP);

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
                lp.width = (int) (d.widthPixels * 1); // 宽度设置为屏幕的
                //lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                win.setAttributes(lp);
            }

            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN |
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            return dialog;
        }

        private EditText editText;

        public void setEdHint(String hint) {
            if (editText != null) editText.setHint(hint);
        }

        private void init(final FSearchDialog dialog) {
            TextView positive = (TextView) mContentView.findViewById(R.id.tv_can);
            editText = (EditText) mContentView.findViewById(R.id.ed_search);
            //editText.setFilters(new InputFilter[]{new InputFilterBlank()});
            EditInputUtil.makeEdtOnlyChinese(editText);
            positive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemChooseListener != null)
                        mOnItemChooseListener.onNegativeListener(dialog);

                }
            });

            editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        if (mOnItemChooseListener != null)
                            mOnItemChooseListener.onPositiveListener(dialog, editText.getText().toString().trim());
                        return true;
                    }
                    return false;
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
