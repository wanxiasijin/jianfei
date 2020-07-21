package com.chenganrt.smartSupervision.business.electronic.ui;

import java.io.Serializable;

/**
 * Created by Administrator on 2019/5/5.
 */

public class PushEvent implements Serializable {
    private static final long serialVersionUID = -3071581227799731142L;

    private String action;
    private String no;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public PushEvent() {
    }

    public PushEvent(String action) {
        this.action = action;
    }
}
