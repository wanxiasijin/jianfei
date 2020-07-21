package com.android.commonlib.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public final class DimenUtil {

    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    private static int sScreenWidth;
    private static int sScreenHeight;

    public static int[] getScreenSizes(Context context) {
        if (sScreenWidth != 0 && sScreenHeight != 0) {
            return new int[]{sScreenWidth, sScreenHeight};
        }
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metrics);
        sScreenWidth = Math.min(metrics.widthPixels, metrics.heightPixels);
        sScreenHeight = Math.max(metrics.widthPixels, metrics.heightPixels);
        return new int[]{sScreenWidth, sScreenHeight};
    }
}
