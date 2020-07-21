package com.chenganrt.smartSupervision.business.electronic.wheel;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListAdapter;

import com.chenganrt.smartSupervision.R;
import com.chenganrt.smartSupervision.business.electronic.wheel.adapter.WheelViewAdapter;


/**
 * Dialog or DialogFragment
 */
public class FAlertDialog extends Dialog {
    private Context mContext;
    public final static int LAYOUT_GENERAL = R.layout.dialog_alert_general;
    public final static int LAYOUT_BOTTOM = R.layout.dialog_alert_bottom;
    public final static int LAYOUT_WHEEL = R.layout.dialog_alert_wheel;
    public final static int LAYOUT_WHEELONE = R.layout.dialog_alert_wheel1;
    public final static int LAYOUT_HOUR_WHEEL = R.layout.dialog_alert_hour_wheel;

    protected FAlertDialog(Context context, int theme) {
        super(context, theme);
        this.mContext = context;
    }

    protected FAlertDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    public static Builder createMessage(Context context, String message) {
        FAlertDialog.Builder builder = new FAlertDialog.Builder(context);
        builder.setCancelable(true);
        builder.setCanceledOnTouchOutside(false);
        builder.setMessage(message);
        builder.setPositiveButton(R.string.positive, null);
        return builder;
    }

    public static Builder createMessage(Context context, String title, String message) {
        FAlertDialog.Builder builder = new FAlertDialog.Builder(context);
        builder.setCancelable(true);
        builder.setCanceledOnTouchOutside(false);
        builder.setMessage(message);
        builder.setTitle(title);
        builder.setPositiveButton(R.string.positive, null);
        return builder;
    }

    public static Builder createMessage(Context context, String message, OnClickListener listener) {
        FAlertDialog.Builder builder = new FAlertDialog.Builder(context);
        builder.setCancelable(true);
        builder.setCanceledOnTouchOutside(false);
        builder.setMessage(message);
        builder.setPositiveButton(R.string.positive, listener);
        return builder;
    }

    public static Builder createMessage(Context context, int resId) {
        return createMessage(context, context.getString(resId));
    }


    public static class Builder {
        int BUTTON_NEUTRAL2 = -4;

        protected int mTheme;
        protected int mResource;
        protected Context mContext;
        protected String mTitle;
        protected String mMessage;
        protected String mPositivieBtnText;
        protected String mNegativieBtnText;
        protected String mNeutralBtnText, mNeutral2BtnText;
        protected int mTitleColor = -1;
        protected View mContentView;
        protected boolean mCancelable = true;
        protected boolean mIsFullscreen = false;
        protected boolean mCanceledOnTouchOutside = false;
        protected int mGravity = -1;
        protected int mMaxHeight = -1;
        protected ListAdapter mAdapter;
        protected OnItemClickListener mOnItemClickListener;

        protected OnClickListener mPositiveBtnListener, mNegativeBtnListener, mNeutralBtnListener, mNeutral2BtnListener;
        protected int mPositivieStyle, mNegativeStyle, mNeutralStyle, mNeutral2Style;
        protected OnCancelListener mCancelListener;
        protected OnKeyListener mOnKeyListener;
        protected OnDismissListener mOnDismissListener;
        protected OnShowListener mOnShowListener;
        protected String[] mWheelData1, mWheelData2, mWheelData3;
        protected boolean mUseCusView = false;
        private FFTDialog fftDialog;

        public Builder(Context context) {
            mContext = context;
            mTheme = R.style.Dialog_General;
            mResource = LAYOUT_GENERAL;
            mContentView = LayoutInflater.from(mContext).inflate(mResource, null);
            initFftDialog();
        }

        public Builder(Context context, int resource) {
            mContext = context;
            mTheme = R.style.Dialog_General;
            mResource = resource;
            mContentView = LayoutInflater.from(mContext).inflate(mResource, null);
            initFftDialog();
        }

        public Builder(Context context, int theme, int resource) {
            mContext = context;
            mTheme = theme;
            mResource = resource;
            mContentView = LayoutInflater.from(mContext).inflate(mResource, null);
            initFftDialog();
        }

        private void initFftDialog() {
            if (mResource == LAYOUT_GENERAL) {
                fftDialog = new FFTGeneralDialog();
            } else if (mResource == LAYOUT_BOTTOM) {
                mGravity = Gravity.BOTTOM;
                fftDialog = new FFTBottomDialog();
            } else if (mResource == LAYOUT_WHEEL) {
                fftDialog = new FFTWheelDialog();
            } else if (mResource == LAYOUT_WHEELONE) {
                fftDialog = new FFTWheelDialog();
            } else if (mResource == LAYOUT_HOUR_WHEEL) {
                fftDialog = new FFTWheelDialog();
            }else {
                fftDialog = new FFTDialog();
            }
        }

        public boolean isIsFullscreen() {
            return mIsFullscreen;
        }

        /**
         * 宽度填充整个屏幕
         */
        public void setIsFullscreen(boolean isFullscreen) {
            mIsFullscreen = isFullscreen;
        }

        public void setMaxHeight(int mMaxHeight) {
            this.mMaxHeight = mMaxHeight;
        }

        public Builder setCancelable(boolean cancelable) {
            mCancelable = cancelable;
            return this;
        }

        public Builder setCancelListener(OnCancelListener cancelListener) {
            mCancelListener = cancelListener;
            return this;
        }

        public Builder setCanceledOnTouchOutside(boolean canceledOnTouchOutside) {
            mCanceledOnTouchOutside = canceledOnTouchOutside;
            return this;
        }

        public Builder setOnKeyListener(final OnKeyListener onKeyListener) {
            mOnKeyListener = onKeyListener;
            return this;
        }

        public Builder setOnDismissListener(OnDismissListener onDismissListener) {
            mOnDismissListener = onDismissListener;
            return this;
        }

        public void setOnShowListener(OnShowListener onShowListener) {
            mOnShowListener = onShowListener;
        }

        public Builder setMessage(String msg) {
            mMessage = msg;
            return this;
        }

        public Builder setMessage(int resId) {
            mMessage = mContext.getString(resId);
            return this;
        }

        public Builder setTitle(String title) {
            mTitle = title;
            return this;
        }

        public Builder setTitle(int resId) {
            mTitle = mContext.getString(resId);
            return this;
        }

        public Builder setTitleColor(int colorId) {
            mTitleColor = colorId;
            return this;
        }

        public Builder setPositiveTextColor(int style) {
            mPositivieStyle = style;
            return this;
        }

        public Builder setNegativeTextColor(int style) {
            mNegativeStyle = style;
            return this;
        }

        public Builder setNeutralTextColor(int style) {
            mNeutralStyle = style;
            return this;
        }

        public Builder setNeutral2TextColor(int style) {
            mNeutral2Style = style;
            return this;
        }

        public Builder setPositiveButton(int resId, OnClickListener listener) {
            mPositivieBtnText = mContext.getString(resId);
            mPositiveBtnListener = listener;
            return this;
        }

        public Builder setPositiveButton(int resId) {
            mPositivieBtnText = mContext.getString(resId);
            return this;
        }

        public Builder setPositiveButton(String positiveBtnText, OnClickListener listener) {
            mPositivieBtnText = positiveBtnText;
            mPositiveBtnListener = listener;
            return this;
        }

        public Builder setNegativeButton(int resId, OnClickListener listener) {
            mNegativieBtnText = mContext.getString(resId);
            mNegativeBtnListener = listener;
            return this;
        }

        public Builder setNegativeButton(int resId) {
            mNegativieBtnText = mContext.getString(resId);
            return this;
        }

        public Builder setNegativeButton(String negtiveBtnText, OnClickListener listener) {
            mNegativieBtnText = negtiveBtnText;
            mNegativeBtnListener = listener;
            return this;
        }

        public Builder setNeutralButton(int resId, OnClickListener listener) {
            mNeutralBtnText = mContext.getString(resId);
            mNeutralBtnListener = listener;
            return this;
        }


        public Builder setNeutralButton(String neutralBtnText, OnClickListener listener) {
            mNeutralBtnText = neutralBtnText;
            mNeutralBtnListener = listener;
            return this;
        }

        public Builder setNeutral2Button(int resId, OnClickListener listener) {
            mNeutral2BtnText = mContext.getString(resId);
            mNeutral2BtnListener = listener;
            return this;
        }


        public Builder setNeutral2Button(String neutral2BtnText, OnClickListener listener) {
            mNeutral2BtnText = neutral2BtnText;
            mNeutral2BtnListener = listener;
            return this;
        }

        public Builder setWheelData(String[] data1) {
            return setWheelData(data1, null, null);
        }

        public Builder setWheelData(String[] data1, String[] data2, String[] data3) {
            mWheelData1 = data1;
            mWheelData2 = data2;
            mWheelData3 = data3;
            return this;
        }

        public Builder setWheelCurrentPostion(int postion1) {
            return setWheelCurrentPostion(postion1, -1, -1);
        }

        public Builder setWheelCurrentPostion(int postion1, int postion2, int postion3) {
            if (fftDialog != null) {
                fftDialog.setWheelCurrentPostion(this, postion1, postion2, postion3);
            }
            return this;
        }

        public Builder setView(View view) {
            if (fftDialog != null) {
                fftDialog.initSelfCustomView(this, view);
                mUseCusView = true;
            }
            return this;
        }

        public Builder setAdapter(final ListAdapter adapter, final OnItemClickListener listener) {
            mAdapter = adapter;
            mOnItemClickListener = listener;
            return this;
        }

        /***
         * See {@link Gravity}
         **/
        public Builder setGravity(int gravity) {
            mGravity = gravity;
            return this;
        }

        public Builder replaceContentView(View mContentView) {
            this.mContentView = mContentView;
            this.mResource = mContentView.getId();
            initFftDialog();
            return this;
        }

        public View getContentView() {
            return mContentView;
        }

        protected void initTitleBar() {
            if (fftDialog != null) {
                fftDialog.initSelfTitleBar(this);
                mUseCusView = true;
            }
        }

        protected void initMessageView() {
            if (fftDialog != null) {
                fftDialog.initSelfMessageView(this);
                mUseCusView = true;
            }
        }


        protected void initButtonBar(final FAlertDialog dialog) {
            if (fftDialog != null) {
                fftDialog.initSelfButtonBar(dialog, this);
                mUseCusView = true;
            }
        }

        public Dialog create() {
            if (fftDialog != null) {
                Dialog dialog = fftDialog.createDialog(this);
                if (dialog != null) {
                    return dialog;
                }
            }
            return createFAlertDialog();
        }

        private Dialog createFAlertDialog() {
            FAlertDialog dialog = new FAlertDialog(mContext, mTheme);
            dialog.setContentView(mContentView);
            dialog.setCancelable(mCancelable);
            dialog.setCanceledOnTouchOutside(mCanceledOnTouchOutside);
            dialog.setOnCancelListener(mCancelListener);
            dialog.setOnKeyListener(mOnKeyListener);
            dialog.setOnDismissListener(mOnDismissListener);
            dialog.setOnShowListener(mOnShowListener);

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
                WindowManager.LayoutParams lp = win.getAttributes();
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                win.setAttributes(lp);
            }
            return dialog;
        }

        public Dialog show() {
            final Dialog dialog = create();
            if (mContext instanceof Activity
                    && !((Activity) mContext).isFinishing()) {
                dialog.show();
            }
            return dialog;
        }
    }

    public interface OnItemClickListener {
        void onItemClick(FAlertDialog d, AdapterView<?> parent, View view, int position, long id);
    }

    public interface OnItemSelectListener {
        void onItemSelect(WheelViewAdapter adapter, int position);
    }
}
