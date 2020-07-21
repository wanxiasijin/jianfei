package com.chenganrt.smartSupervision.business.electronic;

import android.content.Context;

/**
 * Created by Administrator on 2019/3/12.
 */

public class SuperServerAction {

    public SuperServerAction(Context ctx) {
        super();
        this.ctx = ctx.getApplicationContext();
    }

    public interface ActionResult {
        public boolean isSuccess();

        public String getErrorCode();

        public String getErrorMessage();

        public boolean isResult();
    }

    protected Context ctx;

}
