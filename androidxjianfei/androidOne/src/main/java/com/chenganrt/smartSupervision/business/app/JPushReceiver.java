package com.chenganrt.smartSupervision.business.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.chenganrt.smartSupervision.business.home.MatchingMessageActivity;

import androidx.core.app.NotificationManagerCompat;
import cn.jpush.android.api.JPushInterface;

public class JPushReceiver extends BroadcastReceiver {
    private static final String TAG = "tanlin";

    @Override
    public void onReceive(Context context, Intent intent) {
     boolean isEnabled= NotificationManagerCompat.from(context.getApplicationContext()).areNotificationsEnabled();
     Context mContext=context.getApplicationContext();
//        if (!isEnabled) {
//            final AlertDialog dialog = new AlertDialog.Builder(context).create();
//            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
//                dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY);
//            }else {
//                dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
//            }
//            dialog.show();
//            View view = View.inflate(mContext,R.layout.notifction_dialog, null);
//            dialog.setContentView(view);
//            TextView tv = (TextView) view.findViewById(R.id.tv_dialog_context);
//            tv.setText("检测到您没有打开通知权限，是否去打开");
//            TextView confirm = (TextView) view.findViewById(R.id.btn_confirm);
//            confirm.setText("确定");
//            confirm.setOnClickListener(new View.OnClickListener() {
//                public void onClick(View v) {
//                    dialog.cancel();
//                    Intent localIntent = new Intent();
//                    localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    if (Build.VERSION.SDK_INT >= 9) {
//                        localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
//                        localIntent.setData(Uri.fromParts("package",mContext.getPackageName(), null));
//                    } else if (Build.VERSION.SDK_INT <= 8) {
//                        localIntent.setAction(Intent.ACTION_VIEW);
//                        localIntent.setClassName("com.android.settings",
//                                "com.android.settings.InstalledAppDetails");
//                        localIntent.putExtra("com.android.settings.ApplicationPkgName",
//                                mContext.getPackageName());
//                    }
//                    mContext.startActivity(localIntent);
//                }
//            });
//            TextView cancel = (TextView) view.findViewById(R.id.btn_off);
//            cancel.setOnClickListener(new View.OnClickListener() {
//                public void onClick(View v) {
//                    dialog.cancel();
//                }
//            });
//        }


        try {
            Bundle bundle = intent.getExtras();
            if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
                //极光服务器分配的Registration Id，
                String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
                //自定义消息

            } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
                //推送通知

            } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
//                当用户点击通知时的操作,打开自定义的Activity
                Intent i = new Intent(context, MatchingMessageActivity.class);
                i.putExtras(bundle);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(i);
            }
        } catch (Exception e) {

        }
    }
}
