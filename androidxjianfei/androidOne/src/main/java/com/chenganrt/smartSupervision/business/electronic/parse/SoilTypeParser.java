package com.chenganrt.smartSupervision.business.electronic.parse;

import com.chenganrt.smartSupervision.business.electronic.parse.entiy.SoilTypeEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by Administrator on 2019/5/21.
 */

public class SoilTypeParser extends HeadParser {
    @Override
    public SoilTypeEntity parse(JSONObject json) throws JSONException {
        SoilTypeEntity soilTypeEntity = new SoilTypeEntity(super.parse(json));
        List<SoilTypeEntity> soilTypeEntities = new ArrayList<>();

        if (json.has("Content") && !json.isNull("Content")) {
            Object array = new JSONTokener(json.getString("Content")).nextValue();
            if (array != null && array instanceof JSONArray) {
                JSONArray jsonArray = json.getJSONArray("Content");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    SoilTypeEntity sEntity = new SoilTypeEntity();
                    if (jsonObject.has("Value") && !jsonObject.isNull("Value")) {
                        sEntity.setValue(jsonObject.getString("Value"));
                    }

                    if (jsonObject.has("Text") && !jsonObject.isNull("Text")) {
                        sEntity.setText(jsonObject.getString("Text"));
                    }

                    soilTypeEntities.add(sEntity);
                }
                soilTypeEntity.setSoilTypeEntities(soilTypeEntities);
            }
        }

        return soilTypeEntity;
    }
}