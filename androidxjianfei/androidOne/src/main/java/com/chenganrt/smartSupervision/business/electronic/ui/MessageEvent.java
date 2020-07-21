package com.chenganrt.smartSupervision.business.electronic.ui;

import java.io.Serializable;

/**
 * Created by Administrator on 2019/5/5.
 */

public class MessageEvent implements Serializable {

     public String action;
     public String flag;


    public MessageEvent(String action, String flag) {
        this.action = action;
        this.flag = flag;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}
