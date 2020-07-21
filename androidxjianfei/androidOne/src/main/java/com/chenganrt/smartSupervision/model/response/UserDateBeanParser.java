package com.chenganrt.smartSupervision.model.response;
import org.json.JSONException;
import org.json.JSONObject;
/**
 * Created by Administrator on 2019/5/21.
 */

public class UserDateBeanParser {
    public static UserDdtaBean parse(String json) throws JSONException {
        UserDdtaBean userDdtaBean = new UserDdtaBean();
        JSONObject jsonObject = new JSONObject(json);
        if (jsonObject.optInt("status") == 200) {
            JSONObject jsonData = jsonObject.getJSONObject("data");
            if (jsonData.has("user_id") && !jsonData.isNull("user_id")) {
                userDdtaBean.setUser_id(jsonData.getString("user_id"));
            }
            if (jsonData.has("user_type") && !jsonData.isNull("user_type")) {
                userDdtaBean.setUser_type(jsonData.getString("user_type"));
            }
            if (jsonData.has("user_name") && !jsonData.isNull("user_name")) {
                userDdtaBean.setUser_name(jsonData.getString("user_name"));
            }
            if (jsonData.has("mobile_phone") && !jsonData.isNull("mobile_phone")) {
                userDdtaBean.setMobile_phone(jsonData.getString("mobile_phone"));
            }
            if (jsonData.has("app_account") && !jsonData.isNull("app_account")) {
                userDdtaBean.setApp_account(jsonData.getString("app_account"));
            }
            if (jsonData.has("url") && !jsonData.isNull("url")) {
                userDdtaBean.setUrl(jsonData.getString("url"));
            }
            if (jsonData.has("project_name") && !jsonData.isNull("project_name")) {
                userDdtaBean.setProject_name(jsonData.getString("project_name"));
            }
            if (jsonData.has("enter_name") && !jsonData.isNull("enter_name")) {
                userDdtaBean.setEnter_name(jsonData.getString("enter_name"));
            }
            if (jsonData.has("area_name") && !jsonData.isNull("area_name")) {
                userDdtaBean.setArea_name(jsonData.getString("area_name"));
            }

        }
        return userDdtaBean;
    }
}
