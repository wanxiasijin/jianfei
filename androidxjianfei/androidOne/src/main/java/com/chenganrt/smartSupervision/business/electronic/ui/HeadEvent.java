package com.chenganrt.smartSupervision.business.electronic.ui;

import java.io.Serializable;

/**
 * Created by Administrator on 2019/5/5.
 */

public class HeadEvent implements Serializable {

    private String action;
    private String flag;


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

    public HeadEvent(String action, String flag) {
        this.action = action;
        this.flag = flag;
    }
}
