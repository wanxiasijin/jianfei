package com.chenganrt.smartSupervision.business.electronic.parse;

import com.chenganrt.smartSupervision.business.electronic.parse.entiy.BuildEntity;

import org.json.JSONException;
import org.json.JSONObject;



/**
 * Created by Administrator on 2019/5/21.
 */

public class BuildParser extends HeadParser {

    @Override
    public BuildEntity parse(JSONObject json) throws JSONException {
        BuildEntity buildEntity = new BuildEntity(super.parse(json));
        if (json.has("Content") && !json.isNull("Content")) {
            JSONObject jsonData = json.getJSONObject("Content");

            if (jsonData.has("SiteName") && !jsonData.isNull("SiteName")) {
                buildEntity.setSiteName(jsonData.getString("SiteName"));
            }

            if (jsonData.has("SiteFenceName") && !jsonData.isNull("SiteFenceName")) {
                buildEntity.setSiteFenceName(jsonData.getString("SiteFenceName"));
            }

            if (jsonData.has("SiteAddress") && !jsonData.isNull("SiteAddress")) {
                buildEntity.setSiteAddress(jsonData.getString("SiteAddress"));
            }

            if (jsonData.has("BuildingUnit") && !jsonData.isNull("BuildingUnit")) {
                buildEntity.setBuildingUnit(jsonData.getString("BuildingUnit"));
            }

            if (jsonData.has("ConstructUnit") && !jsonData.isNull("ConstructUnit")) {
                buildEntity.setConstructUnit(jsonData.getString("ConstructUnit"));
            }

            if (jsonData.has("MonitorUnit") && !jsonData.isNull("MonitorUnit")) {
                buildEntity.setMonitorUnit(jsonData.getString("MonitorUnit"));
            }

            if (jsonData.has("SiteId") && !jsonData.isNull("SiteId")) {
                buildEntity.setSiteId(jsonData.getString("SiteId"));
            }

            if (jsonData.has("SiteFenceId") && !jsonData.isNull("SiteFenceId")) {
                buildEntity.setSiteFenceId(jsonData.getString("SiteFenceId"));
            }
        }
        return buildEntity;
    }
}
