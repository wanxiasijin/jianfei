package com.chenganrt.smartSupervision.business.electronic.parse.entiy;

import com.chenganrt.smartSupervision.business.electronic.okhttp.Head;

import java.io.Serializable;


/**
 * Created by Administrator on 2019/7/9.
 */

public class FieldDetailEntity extends Head implements Serializable {

    private static final long serialVersionUID = 2833967802787623172L;

    public FieldDetailEntity() {
    }

    public FieldDetailEntity(Head head) {
        super(head);
    }

    private String ReceivingID;
    private String ReceivingName;

    public String getReceivingID() {
        return ReceivingID;
    }

    public void setReceivingID(String receivingID) {
        ReceivingID = receivingID;
    }

    public String getReceivingName() {
        return ReceivingName;
    }

    public void setReceivingName(String receivingName) {
        ReceivingName = receivingName;
    }
}
