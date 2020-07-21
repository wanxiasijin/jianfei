package com.chenganrt.smartSupervision.business.electronic.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import rx.android.BuildConfig;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * Created by Administrator on 2019/3/11.
 */

public class AppTools {
    public static final boolean isDebug() {
        return BuildConfig.DEBUG;
    }

    public static final boolean isConnectToRelease() {
        return AppConstant.CURENV == AppConstant.CompileEnv.RELEASE;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static float px2dipfloat(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (pxValue / scale + 0.5f);
    }

    /**
     * MD5加密
     *
     * @param content
     * @return
     */
    public static String getMD5String(String content) {
        if (TextUtils.isEmpty(content)) return "";
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(content.getBytes());
            byte[] md5Byte = messageDigest.digest();
            StringBuffer sb = new StringBuffer();
            int lenght;
            for (int i = 0; i < md5Byte.length; i++) {
                lenght = md5Byte[i];
                if (lenght < 0) {
                    lenght += 256;
                }
                if (lenght < 16) {
                    sb.append("0");
                }
                sb.append(Integer.toHexString(lenght));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            //LogUtil.e(e.getMessage());
        }
        return "";
    }

    /**
     * 请求参数排序
     *
     * @param map
     * @return
     */
    public static String sortRequestSign(TreeMap<String, String> map) {
        if (map != null) {
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, String> entry : map.entrySet()) {
                String value = entry.getValue();
                if (value != null) {
                    sb.append("&" + entry.getKey() + "=" + value);
                }
            }
            if (sb.length() > 0) {
                sb.deleteCharAt(0);
            }
            return sb.toString();
        }
        return "";
    }

    //空集合判断
    public static boolean isEmptyList(List list) {
        if (null != list && 0 != list.size()) {
            return false;
        } else {
            return true;
        }
    }

    //数组转LIST集合
    public static List<String> getList(String[] array) {
        List<String> strList = new ArrayList<>();
        if (array == null) return strList;
        for (int i = 0; i < array.length; i++) {
            strList.add(array[i]);
        }
        return strList;
    }

    public static DisplayMetrics getDisplayMetrics(Context context) {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metrics);
        return metrics;
    }

    //
    public static String getString(String str) {
        return TextUtils.isEmpty(str) ? "" : str;
    }


    /**
     * 元转化成分
     *
     * @param yuan
     * @return
     */
    public static int yuanToFen(String yuan) {
        int fen = 0;
        if (!TextUtils.isEmpty(yuan)) {
            try {
                BigDecimal bigDecimal = new BigDecimal(yuan);
                bigDecimal = bigDecimal.multiply(new BigDecimal(100));
                fen = bigDecimal.intValue();
            } catch (Exception e) {
              //  LogUtil.e(e.getMessage());
            }
        }
        return fen;
    }

    public static String yuanToFenString(String yuan) {
        return String.valueOf(yuanToFen(yuan));
    }

    /**
     * 收起软键盘
     */
    public static void collapseSoftInputMethod(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity
                .getSystemService(INPUT_METHOD_SERVICE);
        if (activity.getCurrentFocus() != null)
            inputMethodManager.hideSoftInputFromWindow(activity
                            .getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);

    }

    public static void showSoftInputMethod(Context context, final View view) {
        view.requestFocus();
        InputMethodManager imm = (InputMethodManager) context.getSystemService(INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
    }

    public static String formatDate(long time) {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
        return formatter.format(time);
    }

    /**
     * 补0
     *
     * @param str
     * @return
     */
    public static String addZero(String str) {
        if (!TextUtils.isEmpty(str) && str.length() == 1)
            return "0" + str;
        return str;
    }

    /**
     * 百分比计算
     *
     * @param num
     * @param total
     * @param scale
     * @return
     */
    public static String accuracy(double num, double total, int scale) {
        DecimalFormat df = (DecimalFormat) NumberFormat.getInstance();
        df.setMaximumFractionDigits(scale);
        df.setRoundingMode(RoundingMode.HALF_UP);
        double accuracy_num = num / total * 100;
        return df.format(accuracy_num) + "%";
    }

    public static double accuracyFormat(double num, double total, int scale) {
        //DecimalFormat df = (DecimalFormat) NumberFormat.getInstance();
        DecimalFormat df = new DecimalFormat("0.00");
        df.setMaximumFractionDigits(scale);
        df.setRoundingMode(RoundingMode.HALF_UP);
        return num / total * 100;
    }

    /**
     * 获取app版本(内部)
     *
     * @param context
     * @return
     */
    public static String getAppVersionCode(Context context) {
        String version = "";
        try {
            version = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0).versionCode
                    + "";
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return version;
    }

    /**
     * 获取app版本(外部，显示在检查版本旁边)
     *
     * @param context
     * @return
     */
    public static String getAppVersionName(Context context) {
        String version = "";
        try {
            version = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0).versionName
                    + "";
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }

    /**
     * 判断必须包含某些字符种类
     *
     * @param string
     * @return
     */
    public static boolean limitInput(String string) {
        String input = "^(?:(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[^A-Za-z0-9]).{8,16}$).*$";
        Pattern p = Pattern.compile(input);
        Matcher matcher = p.matcher(string);
        boolean isInput = matcher.matches();
        return isInput;
    }

    /**
     * 控制窗口背景的不透明度
     */
    public static void setWindowBackgroundAlpha(Context context, float alpha) {
        if (context == null) return;
        if (context instanceof Activity) {
            Window window = ((Activity) context).getWindow();
            WindowManager.LayoutParams layoutParams = window.getAttributes();
            layoutParams.alpha = alpha;
            window.setAttributes(layoutParams);
        }
    }

    /**
     * 两个日期之间的天数
     *
     * @param t1
     * @param t2
     * @return
     */
    public static long spaceDay(String t1, String t2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date d1 = null, d2 = null;
        try {
            d1 = df.parse(t1);
            d2 = df.parse(t2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return (d1.getTime() - d2.getTime()) / (60 * 60 * 1000 * 24);
    }

    /**
     * 两个日期相关几个小时
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static int getGapHours(String startDate, String endDate) {
        long start = 0;
        long end = 0;
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            start = df.parse(startDate).getTime();
            end = df.parse(endDate).getTime();

        } catch (Exception e) {

        }
        int minutes = (int) ((end - start) / (1000 * 60 * 60));
        //LogUtil.i("minutes::" + minutes);
        return minutes;
    }


    public static long dateDiff(String startTime, String endTime) {
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
        long nh = 1000 * 60 * 60;// 一小时的毫秒数
        long nm = 1000 * 60;// 一分钟的毫秒数
        long ns = 1000;// 一秒钟的毫秒数
        long diff;
        long day = 0;
        long hour = 0;
        long min = 0;
        long sec = 0;
        try {
            diff = sd.parse(endTime).getTime()
                    - sd.parse(startTime).getTime();
            day = diff / nd;
            hour = diff % nd / nh;
            min = diff % nd % nh / nm;
            sec = diff % nd % nh % nm / ns;

        } catch (ParseException e) {
            e.printStackTrace();
        }
        //LogUtil.i("时间相差：" + day + "天" + hour + "小时" + min + "分钟" + sec + "秒。");
        return day * 24 * 60 + hour * 60 + min;

    }

    /**
     * 获取现在时间
     *
     * @return 返回短时间字符串格式yyyy-MM-dd
     */
    public static String getStringDateShort() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public static String getCurrenDate() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String dateString = formatter.format(currentTime);
        return dateString;
    }


    /**
     * 计算最近天数
     *
     * @param day
     * @return
     */
    public static String getDownTime(int day) {
        Calendar rightNow = Calendar.getInstance();
        rightNow.add(Calendar.DAY_OF_YEAR, day);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        Date date = rightNow.getTime();
        return sdf.format(date);
    }

    /**
     * 是否网络连接
     *
     * @param context
     * @return
     */
    public static boolean isConnected(Context context) {
        ConnectivityManager conn = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = conn.getActiveNetworkInfo();
        return (info != null && info.isConnected());
    }

    /**
     * 得到指定月的天数
     */
    public static int getMonthDay(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DATE, 1);
        calendar.roll(Calendar.DATE, -1);
        return calendar.get(Calendar.DATE);
    }

}
