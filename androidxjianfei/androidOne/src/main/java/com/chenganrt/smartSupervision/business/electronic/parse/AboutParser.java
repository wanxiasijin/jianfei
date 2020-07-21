package com.chenganrt.smartSupervision.business.electronic.parse;

import com.chenganrt.smartSupervision.business.electronic.parse.entiy.AboutEntity;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by Administrator on 2019/5/21.
 */

public class AboutParser extends HeadParser {

    @Override
    public AboutEntity parse(JSONObject json) throws JSONException {
        AboutEntity aboutEntity = new AboutEntity(super.parse(json));
        if (json.has("Content") && !json.isNull("Content")) {
            JSONObject jsonData = json.getJSONObject("Content");

            if (jsonData.has("Version") && !jsonData.isNull("Version")) {
                aboutEntity.setVersion(jsonData.getString("Version"));
            }

            if (jsonData.has("Website") && !jsonData.isNull("Website")) {
                aboutEntity.setWebsite(jsonData.getString("Website"));
            }

            if (jsonData.has("Tel") && !jsonData.isNull("Tel")) {
                aboutEntity.setTel(jsonData.getString("Tel"));
            }

            if (jsonData.has("Company") && !jsonData.isNull("Company")) {
                aboutEntity.setCompany(jsonData.getString("Company"));
            }

            if (jsonData.has("Address") && !jsonData.isNull("Address")) {
                aboutEntity.setAddress(jsonData.getString("Address"));
            }

        }
        return aboutEntity;
    }
}
