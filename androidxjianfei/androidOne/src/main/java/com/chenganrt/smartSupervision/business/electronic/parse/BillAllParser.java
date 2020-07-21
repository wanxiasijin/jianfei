package com.chenganrt.smartSupervision.business.electronic.parse;


import com.chenganrt.smartSupervision.business.electronic.BillEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2019/5/21.
 */

public class BillAllParser extends HeadParser {
    @Override
    public BillEntity parse(JSONObject json) throws JSONException {
        BillEntity billEntitys = new BillEntity(super.parse(json));
        List<BillEntity> billEntityList = new ArrayList<>();
        if (json.has("Content") && !json.isNull("Content")) {
            Object array = new JSONTokener(json.getString("Content")).nextValue();
            if (array != null && array instanceof JSONArray) {
                JSONArray jsonArray = json.getJSONArray("Content");
                for (int i = 0; i < jsonArray.length(); i++) {
                    BillEntity billEntity = new BillEntity();
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
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

                    if (jsonObject.has("PushTime") && !jsonObject.isNull("PushTime")) {
                        billEntity.setPushTime(jsonObject.getString("PushTime"));
                    }

                    if (jsonObject.has("ReceiveTime") && !jsonObject.isNull("ReceiveTime")) {
                        billEntity.setReceiveTime(jsonObject.getString("ReceiveTime"));
                    }

                    if (jsonObject.has("SignExpire") && !jsonObject.isNull("SignExpire")) {
                        billEntity.setSignExpire(jsonObject.getString("SignExpire"));
                    }

                    if (jsonObject.has("IsCountDown") && !jsonObject.isNull("IsCountDown")) {
                        billEntity.setIsCountDown(jsonObject.getString("IsCountDown"));
                    }

                    if (jsonObject.has("SignTime") && !jsonObject.isNull("SignTime")) {
                        billEntity.setSignTime(jsonObject.getString("SignTime"));
                    }

                    if (jsonObject.has("CancelTime") && !jsonObject.isNull("CancelTime")) {
                        billEntity.setCancelTime(jsonObject.getString("CancelTime"));
                    }

                    if (jsonObject.has("SignStatus") && !jsonObject.isNull("SignStatus")) {
                        billEntity.setSignStatus(jsonObject.getString("SignStatus"));
                    }

                    if (jsonObject.has("ApplyStatus") && !jsonObject.isNull("ApplyStatus")) {
                        billEntity.setApplyStatus(jsonObject.getString("ApplyStatus"));
                    }

                    if (jsonObject.has("SoilStatus") && !jsonObject.isNull("SoilStatus")) {
                        billEntity.setSoilStatus(jsonObject.getInt("SoilStatus"));
                    }

                    if (jsonObject.has("IsSupply") && !jsonObject.isNull("IsSupply")) {
                        billEntity.setIsSupply(jsonObject.getInt("IsSupply"));
                    }

                    billEntityList.add(billEntity);
                }

                billEntitys.setBillEntities(billEntityList);
            }
        }
        return billEntitys;
    }
}
