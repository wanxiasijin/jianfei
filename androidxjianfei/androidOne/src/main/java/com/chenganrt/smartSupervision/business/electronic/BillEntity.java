package com.chenganrt.smartSupervision.business.electronic;

import com.chenganrt.smartSupervision.business.electronic.okhttp.Head;

import java.io.Serializable;
import java.util.List;


/**
 * Created by Administrator on 2019/5/21.
 */

public class BillEntity extends Head implements Serializable {
    private static final long serialVersionUID = -2030454970018559384L;

    private String orderId;
    private String vehicleNo;
    private String outTime;
    private String wasteType;
    private String loading;
    private String projectAddress;
    private String receivingName;
    private String orderStatus;
    private String statusName;
    private String projectName;
    private String supervisorUnit;
    private String buildUnit;
    private String constructionUnit;
    private String inOrOut;
    private String applyStatus;
    private String pushTime;
    private String receiveTime;
    private String signExpire;
    private String isCountDown;
    private String signTime;
    private String cancelTime;
    private String signStatus;

    private String inFieldTime;
    private String ebillStatus;
    private String siteFence;
    private int soilStatus;
    private int isSupply;

    private String over;


    private List<BillEntity> billEntities;

    public BillEntity() {
    }

    public BillEntity(Head head) {
        super(head);
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public String getOutTime() {
        return outTime;
    }

    public void setOutTime(String outTime) {
        this.outTime = outTime;
    }

    public String getWasteType() {
        return wasteType;
    }

    public void setWasteType(String wasteType) {
        this.wasteType = wasteType;
    }

    public String getLoading() {
        return loading;
    }

    public void setLoading(String loading) {
        this.loading = loading;
    }

    public String getProjectAddress() {
        return projectAddress;
    }

    public void setProjectAddress(String projectAddress) {
        this.projectAddress = projectAddress;
    }

    public String getReceivingName() {
        return receivingName;
    }

    public void setReceivingName(String receivingName) {
        this.receivingName = receivingName;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getSupervisorUnit() {
        return supervisorUnit;
    }

    public void setSupervisorUnit(String supervisorUnit) {
        this.supervisorUnit = supervisorUnit;
    }

    public String getBuildUnit() {
        return buildUnit;
    }

    public void setBuildUnit(String buildUnit) {
        this.buildUnit = buildUnit;
    }

    public String getConstructionUnit() {
        return constructionUnit;
    }

    public void setConstructionUnit(String constructionUnit) {
        this.constructionUnit = constructionUnit;
    }

    public String getInOrOut() {
        return inOrOut;
    }

    public void setInOrOut(String inOrOut) {
        this.inOrOut = inOrOut;
    }


    public String getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(String applyStatus) {
        this.applyStatus = applyStatus;
    }

    public String getPushTime() {
        return pushTime;
    }

    public void setPushTime(String pushTime) {
        this.pushTime = pushTime;
    }

    public String getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(String receiveTime) {
        this.receiveTime = receiveTime;
    }

    public String getSignExpire() {
        return signExpire;
    }

    public void setSignExpire(String signExpire) {
        this.signExpire = signExpire;
    }

    public String getIsCountDown() {
        return isCountDown;
    }

    public void setIsCountDown(String isCountDown) {
        this.isCountDown = isCountDown;
    }

    public String getSignTime() {
        return signTime;
    }

    public void setSignTime(String signTime) {
        this.signTime = signTime;
    }

    public String getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(String cancelTime) {
        this.cancelTime = cancelTime;
    }

    public String getSignStatus() {
        return signStatus;
    }

    public void setSignStatus(String signStatus) {
        this.signStatus = signStatus;
    }

    public String getInFieldTime() {
        return inFieldTime;
    }

    public void setInFieldTime(String inFieldTime) {
        this.inFieldTime = inFieldTime;
    }

    public String getEbillStatus() {
        return ebillStatus;
    }

    public void setEbillStatus(String ebillStatus) {
        this.ebillStatus = ebillStatus;
    }

    public String getSiteFence() {
        return siteFence;
    }

    public void setSiteFence(String siteFence) {
        this.siteFence = siteFence;
    }

    public int getSoilStatus() {
        return soilStatus;
    }

    public void setSoilStatus(int soilStatus) {
        this.soilStatus = soilStatus;
    }

    public String getOver() {
        return over;
    }

    public void setOver(String over) {
        this.over = over;
    }

    public int getIsSupply() {
        return isSupply;
    }

    public void setIsSupply(int isSupply) {
        this.isSupply = isSupply;
    }

    public List<BillEntity> getBillEntities() {
        return billEntities;
    }

    public void setBillEntities(List<BillEntity> billEntities) {
        this.billEntities = billEntities;
    }
}
