package com.chenganrt.smartSupervision.business.electronic.mainctrl;

import android.app.Activity;


public class CommandService {
    private static CommandService self;
    public static CommandService getInstant(){
        if(self == null){
            self = new CommandService();
        }
        return self;
    }

    private String cmd;
    public void insertCommand(String cmd){
        this.cmd = cmd;
    }

    public void excuteCommand(Activity activity){
        ProtocolUtilV2.parseFftBannerUrl(activity, this.cmd);
        this.cmd = null;
    }

    public String getCmd() {
        return cmd;
    }
}
