package com.chenganrt.smartSupervision.business.electronic.parse.entiy;

import com.chenganrt.smartSupervision.business.electronic.okhttp.Head;

import java.io.Serializable;
import java.util.List;


/**
 * Created by Administrator on 2019/5/22.
 */

public class MessageEntity extends Head implements Serializable {
    private static final long serialVersionUID = -6790404697979985808L;

    private String pushId;
    private String pushUserId;
    private String pushTitle;
    private String pushContent;
    private String pushTime;
    private String ebillNo;
    private String vehicleNo;
    private String status;

    private List<MessageEntity> messageEntities;

    public MessageEntity() {
    }

    public MessageEntity(Head head) {
        super(head);
    }

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }

    public String getPushUserId() {
        return pushUserId;
    }

    public void setPushUserId(String pushUserId) {
        this.pushUserId = pushUserId;
    }

    public String getPushTitle() {
        return pushTitle;
    }

    public void setPushTitle(String pushTitle) {
        this.pushTitle = pushTitle;
    }

    public String getPushContent() {
        return pushContent;
    }

    public void setPushContent(String pushContent) {
        this.pushContent = pushContent;
    }

    public String getPushTime() {
        return pushTime;
    }

    public void setPushTime(String pushTime) {
        this.pushTime = pushTime;
    }

    public String getEbillNo() {
        return ebillNo;
    }

    public void setEbillNo(String ebillNo) {
        this.ebillNo = ebillNo;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<MessageEntity> getMessageEntities() {
        return messageEntities;
    }

    public void setMessageEntities(List<MessageEntity> messageEntities) {
        this.messageEntities = messageEntities;
    }
}
