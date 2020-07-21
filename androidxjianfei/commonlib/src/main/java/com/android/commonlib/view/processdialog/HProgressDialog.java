package com.android.commonlib.view.processdialog;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.commonlib.R;


/**
 * Created by Administrator on 2019/3/11.
 */

public class HProgressDialog extends ProgressDialog {
    public final static int LAYOUT_PROGRESS_LOADING = R.layout.layout_loading;

    private ProgressBar mProgress;
    private TextView tvLoading;
    private ImageView imageClose;

    protected HProgressDialog(Context context) {
        super(context);
    }

    protected HProgressDialog(Context context, int theme) {
        super(context, theme);
    }

    private ICloseInterface iCloseInterface;

    public interface ICloseInterface {
        void closeLoading();
    }

    public void setiCloseInterface(ICloseInterface iCloseInterface) {
        this.iCloseInterface = iCloseInterface;
    }

    public void setCloseVisible(boolean isClose) {
        imageClose = (ImageView) findViewById(R.id.close);
        if (imageClose != null) imageClose.setVisibility(isClose ? View.VISIBLE : View.GONE);
    }

    public static HProgressDialog.Builder createProgress(Context context) {
        HProgressDialog.Builder mBuilder = new HProgressDialog.Builder(context);
        mBuilder.setMessage(R.string.loading);
        mBuilder.setCanceledOnTouchOutside(false);
        mBuilder.setCancelable(false);
        mBuilder.setProgressDrawable(context.getResources().getDrawable(R.drawable.abc_list_divider_light));
        return mBuilder;
    }

    public static HProgressDialog.Builder createProgress(Context context, OnCancelListener listener) {
        HProgressDialog.Builder mBuilder = new HProgressDialog.Builder(context);
        mBuilder.setMessage(R.string.loading);
        mBuilder.setCanceledOnTouchOutside(false);
        mBuilder.setCancelable(true);
        mBuilder.setCancelListener(listener);
        return mBuilder;
    }

    @Override
    public void setIndeterminateDrawable(Drawable d) {
        //int color = ContextCompat.getColor(getContext(), R.color.blue_font);
        //Drawable drawable = d.mutate();
        //drawable.setColorFilter(new LightingColorFilter(color, 0x000000));
        super.setIndeterminateDrawable(d);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_loading);
        tvLoading = (TextView) findViewById(R.id.tv_loading);
        imageClose = (ImageView) findViewById(R.id.close);
        setCloseListener();
        //mProgress = findViewById(android.R.id.progress);
        //if (mProgress != null) setIndeterminateDrawable(mProgress.getIndeterminateDrawable());
    }

    public void setLoadText(String load) {
        if (tvLoading != null && !TextUtils.isEmpty(load))
            tvLoading.setText(load);
    }

    private void setCloseListener() {
        if (imageClose != null) imageClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (iCloseInterface != null) iCloseInterface.closeLoading();
            }
        });
    }

    public static class Builder {
        protected Context mContext;
        protected String mMessage;
        protected View mContentView;
        protected int mResource;
        private int mMax;
        private int mProgressVal;
        private int mSecondaryProgressVal;
        private int mIncrementBy;
        private int mIncrementSecondaryBy;
        private Drawable mProgressDrawable;
        private Drawable mIndeterminateDrawable;
        private boolean mIndeterminate;
        private boolean mCanceledOnTouchOutside = false;
        private boolean mCancelable = true;

        private OnCancelListener mCancelListener;
        private OnKeyListener mOnKeyListener;
        private OnDismissListener mOnDismissListener;
        private OnShowListener mOnShowListener;

        public Builder(Context context) {
            this.mContext = context;
            //this.mResource = LAYOUT_PROGRESS_LOADING;
            //this.mContentView = LayoutInflater.from(mContext).inflate(mResource, null);
        }

        public HProgressDialog.Builder setMessage(String msg) {
            this.mMessage = msg;
            return this;
        }

        public Builder setMessage(int resId) {
            mMessage = mContext.getString(resId);
            return this;
        }

        public Builder setCanceledOnTouchOutside(boolean canceledOnTouchOutside) {
            mCanceledOnTouchOutside = canceledOnTouchOutside;
            return this;
        }

        public Builder setCancelable(boolean cancelable) {
            mCancelable = cancelable;
            return this;
        }

        public Builder setCancelListener(OnCancelListener cancelListener) {
            mCancelListener = cancelListener;
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


        public int getMax() {
            return mMax;
        }

        public Builder setMax(int max) {
            mMax = max;
            return this;
        }

        public Builder setProgress(int value) {
            mProgressVal = value;
            return this;
        }

        public Builder setSecondaryProgress(int secondaryProgress) {
            mSecondaryProgressVal = secondaryProgress;
            return this;
        }

        public int getProgress() {
            return mProgressVal;
        }

        public int getSecondaryProgress() {
            return mSecondaryProgressVal;
        }

        public Builder incrementProgressBy(int diff) {
            mIncrementBy += diff;
            return this;
        }

        public Builder incrementSecondaryProgressBy(int diff) {
            mIncrementSecondaryBy += diff;
            return this;
        }

        public Builder setProgressDrawable(Drawable d) {
            mProgressDrawable = d;
            return this;
        }

        public Builder setIndeterminateDrawable(Drawable d) {
            mIndeterminateDrawable = d;
            return this;
        }

        public Builder setIndeterminate(boolean indeterminate) {
            mIndeterminate = indeterminate;
            return this;
        }

        public boolean isIndeterminate() {
            return mIndeterminate;
        }

        private void initProgress(HProgressDialog dialog) {
            if (mMax > 0) {
                dialog.setMax(mMax);
            }
            if (mProgressVal > 0) {
                dialog.setProgress(mProgressVal);
            }
            if (mSecondaryProgressVal > 0) {
                dialog.setSecondaryProgress(mSecondaryProgressVal);
            }
            if (mIncrementBy > 0) {
                dialog.incrementProgressBy(mIncrementBy);
            }
            if (mIncrementSecondaryBy > 0) {
                dialog.incrementSecondaryProgressBy(mIncrementSecondaryBy);
            }
            if (mProgressDrawable != null) {
                dialog.setProgressDrawable(mProgressDrawable);
            }
            if (mIndeterminateDrawable != null) {
                dialog.setIndeterminateDrawable(mIndeterminateDrawable);
            }
            if (!TextUtils.isEmpty(mMessage)) {
                dialog.setMessage(mMessage);
            }
            dialog.setIndeterminate(mIndeterminate);
        }

        public HProgressDialog create() {
            final HProgressDialog dialog = new HProgressDialog(mContext, R.style.CustomDialog);
            dialog.setCancelable(mCancelable);
            dialog.setCanceledOnTouchOutside(mCanceledOnTouchOutside);
            dialog.setOnCancelListener(mCancelListener);
            dialog.setOnKeyListener(mOnKeyListener);
            dialog.setOnDismissListener(mOnDismissListener);
            dialog.setOnShowListener(mOnShowListener);
            initProgress(dialog);
            return dialog;
        }

        public HProgressDialog show() {
            final HProgressDialog dialog = create();
            if (mContext instanceof Activity
                    && !((Activity) mContext).isFinishing()) {
                dialog.show();
            }
            return dialog;
        }
    }
}
