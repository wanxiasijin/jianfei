package com.chenganrt.smartSupervision.business.electronic.parse;

import com.chenganrt.smartSupervision.business.electronic.parse.entiy.OrderDetailEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by Administrator on 2019/5/21.
 */

public class OrderDetailParser extends HeadParser {
    @Override
    public OrderDetailEntity parse(JSONObject json) throws JSONException {
        OrderDetailEntity orderDetailEntity = new OrderDetailEntity(super.parse(json));
        List<OrderDetailEntity.WasteEntity> wasteEntities = new ArrayList<>();
        List<OrderDetailEntity.ReceiveEntity> receiveEntities = new ArrayList<>();
        if (json.has("Content") && !json.isNull("Content")) {
            JSONObject jsonObject = json.getJSONObject("Content");

            if (jsonObject.has("OrderID") && !jsonObject.isNull("OrderID")) {
                orderDetailEntity.setOrderId(jsonObject.getString("OrderID"));
            }

            if (jsonObject.has("VehicleNo") && !jsonObject.isNull("VehicleNo")) {
                orderDetailEntity.setVehicleNo(jsonObject.getString("VehicleNo"));
            }

            if (jsonObject.has("OutTime") && !jsonObject.isNull("OutTime")) {
                orderDetailEntity.setOutTime(jsonObject.getString("OutTime"));
            }

            if (jsonObject.has("WasteType") && !jsonObject.isNull("WasteType")) {
                orderDetailEntity.setWasteType(jsonObject.getString("WasteType"));
            }

            if (jsonObject.has("Loading") && !jsonObject.isNull("Loading")) {
                orderDetailEntity.setLoading(jsonObject.getString("Loading"));
            }

            if (jsonObject.has("ProjectAddress") && !jsonObject.isNull("ProjectAddress")) {
                orderDetailEntity.setProjectAddress(jsonObject.getString("ProjectAddress"));
            }

            if (jsonObject.has("ReceivingName") && !jsonObject.isNull("ReceivingName")) {
                orderDetailEntity.setReceivingName(jsonObject.getString("ReceivingName"));
            }

            if (jsonObject.has("OrderStatus") && !jsonObject.isNull("OrderStatus")) {
                orderDetailEntity.setOrderStatus(jsonObject.getString("OrderStatus"));
            }

            if (jsonObject.has("StatusName") && !jsonObject.isNull("StatusName")) {
                orderDetailEntity.setStatusName(jsonObject.getString("StatusName"));
            }

            if (jsonObject.has("ProjectName") && !jsonObject.isNull("ProjectName")) {
                orderDetailEntity.setProjectName(jsonObject.getString("ProjectName"));
            }

            if (jsonObject.has("SupervisorUnit") && !jsonObject.isNull("SupervisorUnit")) {
                orderDetailEntity.setSupervisorUnit(jsonObject.getString("SupervisorUnit"));
            }
            if (jsonObject.has("BuildUnit") && !jsonObject.isNull("BuildUnit")) {
                orderDetailEntity.setBuildUnit(jsonObject.getString("BuildUnit"));
            }

            if (jsonObject.has("ConstructionUnit") && !jsonObject.isNull("ConstructionUnit")) {
                orderDetailEntity.setConstructionUnit(jsonObject.getString("ConstructionUnit"));
            }

            if (jsonObject.has("InOrOut") && !jsonObject.isNull("InOrOut")) {
                orderDetailEntity.setInOrOut(jsonObject.getString("InOrOut"));
            }

            if (jsonObject.has("ApplyStatus") && !jsonObject.isNull("ApplyStatus")) {
                orderDetailEntity.setApplyStatus(jsonObject.getString("ApplyStatus"));
            }

            if (jsonObject.has("EbillStatus") && !jsonObject.isNull("EbillStatus")) {
                orderDetailEntity.setEbillStatus(jsonObject.getString("EbillStatus"));
            }

            if (jsonObject.has("SignStatus") && !jsonObject.isNull("SignStatus")) {
                orderDetailEntity.setSignStatus(jsonObject.getString("SignStatus"));
            }

            if (jsonObject.has("PushTime") && !jsonObject.isNull("PushTime")) {
                orderDetailEntity.setPushTime(jsonObject.getString("PushTime"));
            }

            if (jsonObject.has("SignTime") && !jsonObject.isNull("SignTime")) {
                orderDetailEntity.setSignTime(jsonObject.getString("SignTime"));
            }

            if (jsonObject.has("CancelTime") && !jsonObject.isNull("CancelTime")) {
                orderDetailEntity.setCancelTime(jsonObject.getString("CancelTime"));
            }

            if (jsonObject.has("ReceiveTime") && !jsonObject.isNull("ReceiveTime")) {
                orderDetailEntity.setReceiveTime(jsonObject.getString("ReceiveTime"));
            }


            if (jsonObject.has("SignExpire") && !jsonObject.isNull("SignExpire")) {
                orderDetailEntity.setSignExpire(jsonObject.getString("SignExpire"));
            }

            if (jsonObject.has("SiteFence") && !jsonObject.isNull("SiteFence")) {
                orderDetailEntity.setSiteFence(jsonObject.getString("SiteFence"));
            }


            if (jsonObject.has("Waste") && !jsonObject.isNull("Waste")) {
                Object array = new JSONTokener(jsonObject.getString("Waste")).nextValue();
                if (array != null && array instanceof JSONArray) {
                    JSONArray jsonArray = jsonObject.getJSONArray("Waste");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        OrderDetailEntity.WasteEntity wasteEntity = new OrderDetailEntity.WasteEntity();
                        JSONObject jb = jsonArray.getJSONObject(i);
                        if (jb.has("WasteID") && !jb.isNull("WasteID")) {
                            wasteEntity.setWasteId(jb.getString("WasteID"));
                        }

                        if (jb.has("WasteName") && !jb.isNull("WasteName")) {
                            wasteEntity.setWasteName(jb.getString("WasteName"));
                        }

                        wasteEntities.add(wasteEntity);
                    }

                    orderDetailEntity.setWasteEntities(wasteEntities);
                }
            }

            if (jsonObject.has("Receiving") && !jsonObject.isNull("Receiving")) {
                Object array = new JSONTokener(jsonObject.getString("Receiving")).nextValue();
                if (array != null && array instanceof JSONArray) {
                    JSONArray jsonArray = jsonObject.getJSONArray("Receiving");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        OrderDetailEntity.ReceiveEntity receiveEntity = new OrderDetailEntity.ReceiveEntity();
                        JSONObject jb = jsonArray.getJSONObject(i);
                        if (jb.has("ReceivingID") && !jb.isNull("ReceivingID")) {
                            receiveEntity.setReceivingId(jb.getString("ReceivingID"));
                        }

                        if (jb.has("ReceivingName") && !jb.isNull("ReceivingName")) {
                            receiveEntity.setReceivingName(jb.getString("ReceivingName"));
                        }

                        receiveEntities.add(receiveEntity);
                    }

                    orderDetailEntity.setReceiveEntities(receiveEntities);
                }
            }

        }

        return orderDetailEntity;
    }
}
