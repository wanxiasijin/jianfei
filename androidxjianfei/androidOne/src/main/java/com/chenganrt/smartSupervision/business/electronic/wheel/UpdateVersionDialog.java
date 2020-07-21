package com.chenganrt.smartSupervision.business.electronic.wheel;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chenganrt.smartSupervision.R;


public class UpdateVersionDialog extends Dialog {
    public UpdateVersionDialog(Context context) {
        this(context, R.style.versionUpdateDialog);
    }

    public UpdateVersionDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public static class Builder {
        private Context ctx;
        private String tVersionCodeText, tVersionDescText;
        private String negativeText, positiveText;
        private boolean isSingle = false;
        private boolean isCancel = false;
        private OnClickListener positiveButtonClickListener;
        private OnClickListener negativeButtonClickListener;

        public Builder(Context context) {
            this.ctx = context;
        }

        public Builder setVersionCode(String versionCode) {
            tVersionCodeText = "";
            return this;
        }

        public Builder setVersionDesc(String versionDesc) {
            tVersionDescText = versionDesc;
            return this;
        }


        public Builder setPositiveButton(OnClickListener listener) {
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(OnClickListener listener) {
            this.negativeButtonClickListener = listener;
            return this;
        }

        public Builder setMandatoryUpdate(boolean isMandatory) {
            isSingle = isMandatory;
            return this;
        }

        protected Builder setCancelable(boolean isCancel) {
            this.isCancel = isCancel;
            return this;
        }

        public UpdateVersionDialog create() {
            LayoutInflater inflater = (LayoutInflater) ctx
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // instantiate the dialog with the custom Theme
            final UpdateVersionDialog dialog = new UpdateVersionDialog(ctx);
            dialog.setCancelable(false);
            View containerView = inflater.inflate(R.layout.dialog_version_update, null);
            dialog.addContentView(containerView, new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));

            Window win = dialog.getWindow();
            win.getDecorView().setPadding(0, 0, 0, 0);
            WindowManager.LayoutParams lp = win.getAttributes();
            DisplayMetrics d = ctx.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
            lp.width = (int) (d.widthPixels * 0.8); // 宽度设置为屏幕的0.8
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            win.setAttributes(lp);

            TextView tvCancel = (TextView) containerView.findViewById(R.id.tv_cancel);
            TextView tvVesion = (TextView) containerView.findViewById(R.id.tv_version);
            TextView tvDesc = (TextView) containerView.findViewById(R.id.tv_desc);
            TextView tvUpdate = (TextView) containerView.findViewById(R.id.tv_update);

            //tvVesion.setText(tVersionCodeText);
            //tvDesc.setText(Html.fromHtml(tVersionDescText));
            //tvDesc.setText(tVersionDescText);

            if (isSingle) {
                tvCancel.setVisibility(View.GONE);
            } else {
                tvCancel.setVisibility(View.VISIBLE);
            }

            dialog.setCancelable(isCancel);
            if (positiveButtonClickListener != null) {
                tvUpdate.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        positiveButtonClickListener.onClick(dialog,
                                DialogInterface.BUTTON_POSITIVE);
                    }
                });
            }

            if (negativeButtonClickListener != null) {
                tvCancel.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        negativeButtonClickListener.onClick(dialog,
                                DialogInterface.BUTTON_NEGATIVE);
                    }
                });
            }
            return dialog;
        }
    }
}
