package com.chenganrt.smartSupervision.business.electronic.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Administrator on 2019/3/11.
 */

public class ToastUtil {
    private static void showToast(Context context, String text, int duration) {
        Context ctx = context.getApplicationContext();
        Toast.makeText(ctx, text, duration).show();

    }


    public static void showToast(Context context, int resId, int duration) {
        if (context != null) {
            showToast(context, context.getString(resId), duration);
        }
    }

    public static void showToast(Context context, int resId) {
        if (context != null) {
            showToast(context, context.getString(resId), Toast.LENGTH_SHORT);
        }
    }

    public static void showToast(Context context, String text) {
        if (context != null) {
            showToast(context, text, Toast.LENGTH_SHORT);
        }
    }
}
