package com.chenganrt.smartSupervision.business.electronic.parse;

import com.chenganrt.smartSupervision.business.electronic.parse.entiy.VehicleEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by Administrator on 2019/5/21.
 */

public class VehicleParser extends HeadParser {

    @Override
    public VehicleEntity parse(JSONObject json) throws JSONException {
        VehicleEntity vehicleEntity = new VehicleEntity(super.parse(json));
        List<VehicleEntity> vehicleEntities = new ArrayList<>();
        if (json.has("Content") && !json.isNull("Content")) {
            Object array = new JSONTokener(json.getString("Content")).nextValue();
            if (array != null && array instanceof JSONArray) {
                JSONArray jsonArray = json.getJSONArray("Content");
                for (int i = 0; i < jsonArray.length(); i++) {
                    VehicleEntity vEntity = new VehicleEntity();
                    String vehicleNo = (String) jsonArray.get(i);
                    vEntity.setVehicleNo(vehicleNo);
                    vehicleEntities.add(vEntity);
                }

                vehicleEntity.setVehicleEntities(vehicleEntities);
            }
        }
        return vehicleEntity;
    }
}
