package com.android.commonlib.utils;

import android.content.Context;
import android.widget.Toast;

public final class ToastUtils {

    private ToastUtils() {
    }

    private static Toast mToast;

    private static Toast getToast(Context context) {
        if (mToast == null) {
            context = context.getApplicationContext();
            mToast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
        }
        return mToast;
    }

    public static void showToast(Context context, String msg) {
        Toast toast = getToast(context);
        toast.setText(msg);
        toast.show();
    }

    public static void showLongToast(Context context, String msg) {
        Toast toast = getToast(context);
        toast.setText(msg);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }

    public static void showToast(Context context, int res) {
        Toast toast = getToast(context);
        toast.setText(res);
        toast.show();
    }

    public static void showLongToast(Context context, int res) {
        Toast toast = getToast(context);
        toast.setText(res);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }

    public static void showToastLong(String toastContent, Context ctxt) {
        showLongToast(ctxt,toastContent);
    }

    public static void showToastLong(int res, Context ctxt) {
        showLongToast(ctxt,res);
    }

    public static void showToastShort(String toastContent, Context ctxt) {
        showToast(ctxt,toastContent);
    }

    public static void showToastShort(int res, Context ctxt) {
        showToast(ctxt,res);
    }
}