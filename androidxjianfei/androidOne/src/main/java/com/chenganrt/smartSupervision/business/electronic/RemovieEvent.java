package com.chenganrt.smartSupervision.business.electronic;

import java.io.Serializable;

/**
 * Created by Administrator on 2019/7/29.
 */

public class RemovieEvent implements Serializable {
    private static final long serialVersionUID = 7735795061734743078L;

    private int position;

    public RemovieEvent(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
