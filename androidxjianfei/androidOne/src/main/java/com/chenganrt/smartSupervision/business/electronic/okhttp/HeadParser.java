package com.chenganrt.smartSupervision.business.electronic.okhttp;


import com.chenganrt.smartSupervision.business.electronic.parse.AbstractParser;

import org.json.JSONException;
import org.json.JSONObject;

public class HeadParser extends AbstractParser<Head> {

    @Override
    public Head parse(JSONObject json) throws JSONException {
        Head head = new Head();
        if (json != null) {
            if (json.has("Result") && !json.isNull("Result")) {
                head.setResult(json.getString("Result"));
            }
            if (json.has("ShowTips") && !json.isNull("ShowTips")) {
                head.setShowTips(json.getString("ShowTips"));
            }
        } else {
            head.setResult(false);
        }
        return head;
    }
}
