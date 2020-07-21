package com.chenganrt.smartSupervision.business.electronic.parse;


import com.chenganrt.smartSupervision.business.electronic.UserEntity;

import org.json.JSONException;
import org.json.JSONObject;



/**
 * Created by Administrator on 2019/5/21.
 */

public class UserParser extends HeadParser {

    @Override
    public UserEntity parse(JSONObject json) throws JSONException {
        UserEntity userEntity = new UserEntity(super.parse(json));
        if (json.has("Content") && !json.isNull("Content")) {
            JSONObject jsonData = json.getJSONObject("Content");

            if (jsonData.has("UserID") && !jsonData.isNull("UserID")) {
                userEntity.setUserId(jsonData.getString("UserID"));
            }

            if (jsonData.has("UserName") && !jsonData.isNull("UserName")) {
                userEntity.setUserName(jsonData.getString("UserName"));
            }

            if (jsonData.has("RealName") && !jsonData.isNull("RealName")) {
                userEntity.setRealName(jsonData.getString("RealName"));
            }

            if (jsonData.has("UserType") && !jsonData.isNull("UserType")) {
                userEntity.setUserType(jsonData.getString("UserType"));
            }

            if (jsonData.has("IsBindVehicle") && !jsonData.isNull("IsBindVehicle")) {
                userEntity.setIsBindVehicle(jsonData.getString("IsBindVehicle"));
            }

            if (jsonData.has("VehicleNo") && !jsonData.isNull("VehicleNo")) {
                userEntity.setVehicleNo(jsonData.getString("VehicleNo"));
            }

            if (jsonData.has("EnterName") && !jsonData.isNull("EnterName")) {
                userEntity.setEnterName(jsonData.getString("EnterName"));
            }

            if (jsonData.has("FenceStatus") && !jsonData.isNull("FenceStatus")) {
                userEntity.setFenceStatus(jsonData.getString("FenceStatus"));
            }

            if (jsonData.has("FenceName") && !jsonData.isNull("FenceName")) {
                userEntity.setFenceName(jsonData.getString("FenceName"));
            }

            if (jsonData.has("Upgrading") && !jsonData.isNull("Upgrading")) {
                userEntity.setUpgrading(jsonData.getString("Upgrading"));
            }

            if (jsonData.has("AutoConfirmed") && !jsonData.isNull("AutoConfirmed")) {
                userEntity.setAutoConfirmed(jsonData.getString("AutoConfirmed"));
            }
        }
        return userEntity;
    }
}
