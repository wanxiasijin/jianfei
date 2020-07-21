package com.chenganrt.smartSupervision.business.electronic.parse;

import com.chenganrt.smartSupervision.business.electronic.parse.entiy.VersionEntity;

import org.json.JSONException;
import org.json.JSONObject;



/**
 * Created by Administrator on 2019/5/22.
 */

public class VersionParser extends HeadParser {

    @Override
    public VersionEntity parse(JSONObject json) throws JSONException {
        VersionEntity versionEntity = new VersionEntity(super.parse(json));

        if (json.has("Content") && !json.isNull("Content")) {
            versionEntity.setUrl(json.getString("Content"));
        }
        return versionEntity;
    }
}
