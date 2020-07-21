package com.chenganrt.smartSupervision.business.electronic.wheel;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.chenganrt.smartSupervision.R;


public class FileProgressDialog extends FAlertDialog {
    private Context mContext;

    protected FileProgressDialog(Context context, int theme) {
        super(context, theme);
    }

    protected FileProgressDialog(Context context) {
        super(context);
    }


    public static Builder createFileDialog(Context context) {
        final FileProgressDialog.Builder builder = new FileProgressDialog.Builder(context, R.layout.layout_file_progress);
        builder.setCancelable(false);
        builder.setCanceledOnTouchOutside(false);
        return builder;
    }

    public static class Builder extends FAlertDialog.Builder {

        //ProgressBar indicatorSeekBar;
     //   IndicatorSeekBar indicatorSeekBar;
        TextView tvProgeress;

        public Builder(Context context) {
            super(context);
        }

        public Builder(Context context, int resource) {
            super(context, resource);
        }

        @Override
        public FileProgressDialog create() {
            FileProgressDialog dialog = new FileProgressDialog(mContext, mTheme);
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
                lp.width = (int) (d.widthPixels * 0.9); // 宽度设置为屏幕的0.8
                //lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                win.setAttributes(lp);
            }
            return dialog;
        }

        private void init(final FileProgressDialog dialog) {
//            indicatorSeekBar = mContentView.findViewById(R.id.progressBar);
//            tvProgeress = mContentView.findViewById(R.id.tv_progress);
//            indicatorSeekBar.setProgress(0);
//            /*mContentView.findViewById(R.id.download_bg).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (dialog != null) dialog.dismiss();
//                }
//            });*/
//            indicatorSeekBar.setOnTouchListener(new View.OnTouchListener() {
//                @Override
//                public boolean onTouch(View view, MotionEvent motionEvent) {
//                    return true;
//                }
//            });
        }

        public void setProgress(float progress) {
//            if (indicatorSeekBar != null) {
//                indicatorSeekBar.setProgress(progress);
//            }
        }

        public void setTvProgeress(String progeress) {
            if (tvProgeress != null)
                tvProgeress.setText(String.format(mContext.getResources().getString(R.string.progress_loading_state), progeress + "%"));
        }


    }

}
