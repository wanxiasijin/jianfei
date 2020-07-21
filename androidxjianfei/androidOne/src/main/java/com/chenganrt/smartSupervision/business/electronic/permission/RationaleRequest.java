package com.chenganrt.smartSupervision.business.electronic.permission;

/**
 * Created by cuiwenkai on 2017/7/20.
 */

/***
 *Interface used by OnShowRational methods to allow to continue or cancel the process.
 * **/
public interface RationaleRequest {

    void proceed();

    void cancel();
}
