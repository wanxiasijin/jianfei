package com.chenganrt.smartSupervision.model.response;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2019/5/21.
 */

public class UserTotalParser {
    public static UserTotalBean parse(String json) throws JSONException {
        UserTotalBean userTotalBean = new UserTotalBean();
        JSONObject jsonObject = new JSONObject(json);
        if (jsonObject.optInt("status") == 200) {
            JSONObject jsonData = jsonObject.getJSONObject("data");
            if (jsonData.has("waste_supply") && !jsonData.isNull("waste_supply")) {
                userTotalBean.setWaste_supply(jsonData.getInt("waste_supply"));
            }
            if (jsonData.has("waste_demand") && !jsonData.isNull("waste_demand")) {
                userTotalBean.setWaste_demand(jsonData.getInt("waste_demand"));
            }
            if (jsonData.has("multipurpose_use_supply") && !jsonData.isNull("multipurpose_use_supply")) {
                userTotalBean.setMultipurpose_use_supply(jsonData.getInt("multipurpose_use_supply"));
            }
            if (jsonData.has("multipurpose_use_demand") && !jsonData.isNull("multipurpose_use_demand")) {
                userTotalBean.setMultipurpose_use_demand(jsonData.getInt("multipurpose_use_demand"));
            }
        }
        return userTotalBean;
    }
}
