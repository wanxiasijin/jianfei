package com.chenganrt.smartSupervision.business.electronic;

import com.chenganrt.smartSupervision.business.electronic.okhttp.Head;

import java.io.Serializable;

/**
 * Created by Administrator on 2019/5/21.
 */

public class UserEntity extends Head implements Serializable {
    private static final long serialVersionUID = -1522230076608069223L;

    private String userId;
    private String userName;
    private String realName;
    private String userType;
    private String isBindVehicle;
    private String vehicleNo;
    private String enterName;
    private String fenceName;
    private String fenceStatus;
    private String upgrading;
    private String autoConfirmed;


    public UserEntity() {
    }

    public UserEntity(String userName) {
        this.userName = userName;
    }

    public UserEntity(Head head) {
        super(head);
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getIsBindVehicle() {
        return isBindVehicle;
    }

    public void setIsBindVehicle(String isBindVehicle) {
        this.isBindVehicle = isBindVehicle;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public String getEnterName() {
        return enterName;
    }

    public void setEnterName(String enterName) {
        this.enterName = enterName;
    }

    public String getFenceName() {
        return fenceName;
    }

    public void setFenceName(String fenceName) {
        this.fenceName = fenceName;
    }

    public String getFenceStatus() {
        return fenceStatus;
    }

    public void setFenceStatus(String fenceStatus) {
        this.fenceStatus = fenceStatus;
    }

    public String getUpgrading() {
        return upgrading;
    }

    public void setUpgrading(String upgrading) {
        this.upgrading = upgrading;
    }

    public String getAutoConfirmed() {
        return autoConfirmed;
    }

    public void setAutoConfirmed(String autoConfirmed) {
        this.autoConfirmed = autoConfirmed;
    }
}
