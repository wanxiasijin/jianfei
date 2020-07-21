package com.chenganrt.smartSupervision.business.electronic.mainctrl;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.chenganrt.smartSupervision.R;
import com.chenganrt.smartSupervision.business.electronic.activity.ElectornicActivity;
import com.chenganrt.smartSupervision.business.electronic.util.AppConstant;


/**
 * Created by Administrator on 2019/3/11.
 */

public class MainController implements MainControllerFactory.IMainController {

    public static boolean isLaunched = false;

    @Override
    public void excuteProtocol(Context context, String protocol) {
        if (isLaunched) {
            AssitantActivity.excuteProtocol(context, protocol);
        } else {
//            CommandService.getInstant().insertCommand(protocol);
//            Intent intent = new Intent(context, LoginActivity.class);
//            if (!(context instanceof Activity)) {
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            }
//            context.startActivity(intent);
        }
    }

    @Override
    public void callModule(Activity activity, String moduleName) {

    }

    @Override
    public void needReloain(Activity activity) {
//        BottomActivity.needRelogin(activity);
//        AppConstant.appIsLaunch = false;
    }

    @Override
    public void finishApp(Activity activity) {
       // BottomActivity.closeApp(activity);
        isLaunched = false;
        AppConstant.appIsLaunch = false;
    }

    @Override
    public void firstLaunch(Activity activity, String param) {
      //  BottomActivity.startApp(activity);
    }

    @Override
    public void callMain(Activity activity, String cmd) {
        Intent intent = new Intent();
        intent.setClass(activity, getmainActivityClass());
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        if (cmd != null) CommandService.getInstant().insertCommand(cmd);
        intent.putExtra("command", cmd);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
        isLaunched = true;
    }

    @Override
    public void gotoAuth(Activity activity, int requestCode) {

    }

    @Override
    public void reStartAct(Activity activity) {
     //   BottomActivity.reStart(activity);
    }

    private Class getmainActivityClass() {
        return ElectornicActivity.class;
    }

}
