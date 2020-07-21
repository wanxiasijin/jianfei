package com.chenganrt.smartSupervision.business.electronic.mainctrl;

import android.app.Activity;
import android.content.Context;

/**
 * Created by Administrator on 2019/3/11.
 */

public class MainControllerFactory {


    //    public static final String CMD_BILLMANAGER_TAB = "tab://1";
    public static final String CMD_AUTOPAY_TAB = "tab://2";
//    public static final String CMD_FFTCARD_TAB = "tab://3";
//    public static final String CMD_ACCOUNT_TAB = "tab://4";

    public static IMainController getApi(){
        return new MainController();
    }

    public interface IMainController {
        void excuteProtocol(Context context, String protocol);

        void callModule(Activity activity, String moduleName);

        void needReloain(Activity activity);

        void finishApp(Activity activity);

        void firstLaunch(Activity activity, String param);

        void callMain(Activity activity, String cmd);

        void gotoAuth(Activity activity, int requestCode);

        void reStartAct(Activity activity);
    }
}
