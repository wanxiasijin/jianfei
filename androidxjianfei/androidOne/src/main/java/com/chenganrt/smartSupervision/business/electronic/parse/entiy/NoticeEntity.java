package com.chenganrt.smartSupervision.business.electronic.parse.entiy;


import com.chenganrt.smartSupervision.business.electronic.okhttp.FType;
import com.chenganrt.smartSupervision.business.electronic.okhttp.Head;

/**
 * Created by Administrator on 2019/8/6.
 */

public class NoticeEntity extends Head implements FType {
    private static final long serialVersionUID = 2022771479687580162L;

    private String EbillNo;//联单编号

    public String getEbillNo() {
        return EbillNo;
    }

    public void setEbillNo(String ebillNo) {
        EbillNo = ebillNo;
    }
}
