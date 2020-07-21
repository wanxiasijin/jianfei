package com.chenganrt.smartSupervision.business.electronic.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;

import com.chenganrt.smartSupervision.R;


/**
 * Created by Administrator on 2019/5/21.
 */

public class BillStatusUtil {
    /*司机端：11已激活、12待出场、15待处置、21已完成
    工地施工单位监理员：12待确认、15待签认、14已签认
    工地监理单位监理员：12待确认、15待签认、13已签认
    处置场处理员：15待确认、21待签认、22已签认
     //改成
    /*司机端：11待确认、12待出场、15待处置、21已完成
    工地施工单位监理员：12待确认、17待签认、14已签认
    工地监理单位监理员：12待确认、17待签认、13已签认
    处置场处理员：15待确认、17待签认、22已签认*/
    //"2";//工地施工单位监理员
    // "3";//工地监理单位监理员
    public static int getStatusColor(Context context, String UserType, String OrderStatus) {
        int color = context.getResources().getColor(R.color.text_backcolor);
        if (TextUtils.isEmpty(OrderStatus)) return color;
        if ("1".equals(UserType)) {//是司机
            if ("11".equals(OrderStatus)) {
                color = context.getResources().getColor(R.color.text_backcolor);
            } else if ("12".equals(OrderStatus)) {
                color = context.getResources().getColor(R.color.text_backcolor_f7);
            } else if ("15".equals(OrderStatus)) {
                color = context.getResources().getColor(R.color.text_backcolor_2d);
            } else {
                color = context.getResources().getColor(R.color.text_backcolor_1a);
            }
        } else if ("2".equals(UserType) || "3".equals(UserType)) {
            if ("12".equals(OrderStatus)) {
                color = context.getResources().getColor(R.color.text_backcolor_f7);
            } else if ("17".equals(OrderStatus)) {
                color = context.getResources().getColor(R.color.text_backcolor_2d);
            } else {
                color = context.getResources().getColor(R.color.text_backcolor_1a);
            }
        } else {
            if ("15".equals(OrderStatus)) {
                color = context.getResources().getColor(R.color.text_backcolor_f7);
            } else if ("17".equals(OrderStatus)) {
                color = context.getResources().getColor(R.color.text_backcolor_2d);
            } else {
                color = context.getResources().getColor(R.color.text_backcolor_1a);
            }
        }
        return color;
    }

    public static int getStatusDrawable(Context context, String UserType, String OrderStatus) {
        int drawable = R.drawable.bg_coners_green;
        if (TextUtils.isEmpty(OrderStatus)) return drawable;
        if ("1".equals(UserType)) {//是司机
            if ("11".equals(OrderStatus)) {
                drawable = R.drawable.bg_coners_orange;
            } else if ("12".equals(OrderStatus)) {
                drawable = R.drawable.bg_coners_orange;
            } else if ("15".equals(OrderStatus)) {
                drawable = R.drawable.bg_coners_green;
            } else {
                drawable = R.drawable.bg_coners_gray;
            }
        } else if ("2".equals(UserType) || "3".equals(UserType)) {
            if ("12".equals(OrderStatus)) {
                drawable = R.drawable.bg_coners_orange;
            } else if ("17".equals(OrderStatus)) {
                drawable = R.drawable.bg_coners_green;
            } else {
                drawable = R.drawable.bg_coners_gray;
            }
        } else {
            if ("15".equals(OrderStatus)) {
                drawable = R.drawable.bg_coners_orange;
            } else if ("17".equals(OrderStatus)) {
                drawable = R.drawable.bg_coners_green;
            } else {
                drawable = R.drawable.bg_coners_gray;
            }
        }
        return drawable;
    }

    /**
     * 签认状态：
     * 0-待签认
     * 1-已签认
     * 2-已取消
     * 3-未签认
     */


    public static Drawable getTypeDrawable(Context context, String signStatus) {
        if (!TextUtils.isEmpty(signStatus)) {
            if ("0".equals(signStatus)) {
                return context.getResources().getDrawable(R.drawable.bg_coners_orange);
            } else if ("1".equals(signStatus)) {
                return context.getResources().getDrawable(R.drawable.bg_coners_green);
            } else if ("2".equals(signStatus)) {
                return context.getResources().getDrawable(R.drawable.bg_coners_gray);
            } else if ("3".equals(signStatus)) {
                return context.getResources().getDrawable(R.drawable.bg_coners_brown);
            } else if ("4".equals(signStatus)) {
                return context.getResources().getDrawable(R.drawable.bg_coners_red);
            } else if ("5".equals(signStatus)) {
                return context.getResources().getDrawable(R.drawable.bg_coners_brown);
            }
        }

        return context.getResources().getDrawable(R.drawable.bg_coners_green);
    }

    /**
     * 司机端
     *
     * @param context
     * @param orderStatus
     * @return
     */
    public static Drawable getDriverTypeDrawable(Context context, String orderStatus) {
        if (!TextUtils.isEmpty(orderStatus)) {
            if ("11".equals(orderStatus)) {
                return context.getResources().getDrawable(R.drawable.bg_coners_orange);
            } else if ("12".equals(orderStatus)) {
                return context.getResources().getDrawable(R.drawable.bg_coners_red);
            } else if ("15".equals(orderStatus)) {
                return context.getResources().getDrawable(R.drawable.bg_coners_green);
            }
        }
        return context.getResources().getDrawable(R.drawable.bg_coners_green);
    }


    public static String getTypeName(String signStatus, boolean isSupply) {
        if (!TextUtils.isEmpty(signStatus)) {
            if ("0".equals(signStatus)) {
                return "待签认";
            } else if ("1".equals(signStatus)) {
                return isSupply ? "已确认(补)" : "已确认";
            } else if ("2".equals(signStatus)) {
                return "已取消";
            } else if ("3".equals(signStatus)) {
                return "未签认";
            } else if ("4".equals(signStatus)) {
                return "土质异常";
            } else if ("5".equals(signStatus)) {
                return "自动确认";
            }
        }

        return "";
    }

}
