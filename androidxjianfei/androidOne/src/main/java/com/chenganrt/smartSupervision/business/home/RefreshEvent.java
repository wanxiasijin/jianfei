package com.chenganrt.smartSupervision.business.home;

import java.io.Serializable;

/**
 * author : tanyonglin
 * e-mail : 1760032445@qq.com
 * date   : 2020/7/9
 */
public class RefreshEvent implements Serializable {
    public String tag;


    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public RefreshEvent(String tag) {
        this.tag = tag;
    }
}
