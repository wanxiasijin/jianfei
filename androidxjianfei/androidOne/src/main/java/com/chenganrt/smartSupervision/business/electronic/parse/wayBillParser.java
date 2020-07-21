package com.chenganrt.smartSupervision.business.electronic.parse;


import com.chenganrt.smartSupervision.business.electronic.BillEntity;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by Administrator on 2019/5/21.
 */

public class wayBillParser extends HeadParser {
    @Override
    public BillEntity parse(JSONObject json) throws JSONException {
        BillEntity billEntity = new BillEntity(super.parse(json));
        if (json.has("Content") && !json.isNull("Content")) {
            JSONObject jsonObject = json.getJSONObject("Content");

            if (jsonObject.has("OrderID") && !jsonObject.isNull("OrderID")) {
                billEntity.setOrderId(jsonObject.getString("OrderID"));
            }

            if (jsonObject.has("VehicleNo") && !jsonObject.isNull("VehicleNo")) {
                billEntity.setVehicleNo(jsonObject.getString("VehicleNo"));
            }

            if (jsonObject.has("OutTime") && !jsonObject.isNull("OutTime")) {
                billEntity.setOutTime(jsonObject.getString("OutTime"));
            }

            if (jsonObject.has("WasteType") && !jsonObject.isNull("WasteType")) {
                billEntity.setWasteType(jsonObject.getString("WasteType"));
            }

            if (jsonObject.has("Loading") && !jsonObject.isNull("Loading")) {
                billEntity.setLoading(jsonObject.getString("Loading"));
            }

            if (jsonObject.has("ProjectAddress") && !jsonObject.isNull("ProjectAddress")) {
                billEntity.setProjectAddress(jsonObject.getString("ProjectAddress"));
            }

            if (jsonObject.has("SignTime") && !jsonObject.isNull("SignTime")) {
                billEntity.setSignTime(jsonObject.getString("SignTime"));
            }

            if (jsonObject.has("CancelTime") && !jsonObject.isNull("CancelTime")) {
                billEntity.setCancelTime(jsonObject.getString("CancelTime"));
            }

            if (jsonObject.has("ReceivingName") && !jsonObject.isNull("ReceivingName")) {
                billEntity.setReceivingName(jsonObject.getString("ReceivingName"));
            }

            if (jsonObject.has("OrderStatus") && !jsonObject.isNull("OrderStatus")) {
                billEntity.setOrderStatus(jsonObject.getString("OrderStatus"));
            }

            if (jsonObject.has("StatusName") && !jsonObject.isNull("StatusName")) {
                billEntity.setStatusName(jsonObject.getString("StatusName"));
            }

            if (jsonObject.has("ProjectName") && !jsonObject.isNull("ProjectName")) {
                billEntity.setProjectName(jsonObject.getString("ProjectName"));
            }

            if (jsonObject.has("SupervisorUnit") && !jsonObject.isNull("SupervisorUnit")) {
                billEntity.setSupervisorUnit(jsonObject.getString("SupervisorUnit"));
            }

            if (jsonObject.has("BuildUnit") && !jsonObject.isNull("BuildUnit")) {
                billEntity.setBuildUnit(jsonObject.getString("BuildUnit"));
            }

            if (jsonObject.has("ConstructionUnit") && !jsonObject.isNull("ConstructionUnit")) {
                billEntity.setConstructionUnit(jsonObject.getString("ConstructionUnit"));
            }

            if (jsonObject.has("InOrOut") && !jsonObject.isNull("InOrOut")) {
                billEntity.setInOrOut(jsonObject.getString("InOrOut"));
            }

            if (jsonObject.has("ApplyStatus") && !jsonObject.isNull("ApplyStatus")) {
                billEntity.setApplyStatus(jsonObject.getString("ApplyStatus"));
            }

            if (jsonObject.has("PushTime") && !jsonObject.isNull("PushTime")) {
                billEntity.setPushTime(jsonObject.getString("PushTime"));
            }

            if (jsonObject.has("SignExpire") && !jsonObject.isNull("SignExpire")) {
                billEntity.setSignExpire(jsonObject.getString("SignExpire"));
            }

            if (jsonObject.has("ReceiveTime") && !jsonObject.isNull("ReceiveTime")) {
                billEntity.setReceiveTime(jsonObject.getString("ReceiveTime"));
            }

            if (jsonObject.has("SignStatus") && !jsonObject.isNull("SignStatus")) {
                billEntity.setSignStatus(jsonObject.getString("SignStatus"));
            }

            if (jsonObject.has("InFieldTime") && !jsonObject.isNull("InFieldTime")) {
                billEntity.setInFieldTime(jsonObject.getString("InFieldTime"));
            }

            if (jsonObject.has("EbillStatus") && !jsonObject.isNull("EbillStatus")) {
                billEntity.setEbillStatus(jsonObject.getString("EbillStatus"));
            }

            if (jsonObject.has("SiteFence") && !jsonObject.isNull("SiteFence")) {
                billEntity.setSiteFence(jsonObject.getString("SiteFence"));
            }

            if (jsonObject.has("SoilStatus") && !jsonObject.isNull("SoilStatus")) {
                billEntity.setSoilStatus(jsonObject.getInt("SoilStatus"));
            }

            if (jsonObject.has("IsSupply") && !jsonObject.isNull("IsSupply")) {
                billEntity.setIsSupply(jsonObject.getInt("IsSupply"));
            }

        }
        return billEntity;
    }
}