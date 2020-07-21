package com.chenganrt.smartSupervision.utils;

import java.util.Calendar;

/**
 * author : tanyonglin
 * e-mail : 1760032445@qq.com
 * date   : 2020/7/1
 */
public class PreventRepeatUtil {
    private static long lastClickTime = 0;
    public static final int MIN_CLICK_DELAY_TIME = 1000;

    public static boolean noDoubleClick() {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
            lastClickTime = currentTime;
            return true;
        }else {
            return false;
        }
    }

}
