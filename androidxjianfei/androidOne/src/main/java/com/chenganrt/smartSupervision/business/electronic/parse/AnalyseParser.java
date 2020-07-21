package com.chenganrt.smartSupervision.business.electronic.parse;

import com.chenganrt.smartSupervision.business.electronic.parse.entiy.AnalyseEntity;

import org.json.JSONException;
import org.json.JSONObject;



/**
 * Created by Administrator on 2019/5/21.
 */

public class AnalyseParser extends HeadParser {

    @Override
    public AnalyseEntity parse(JSONObject json) throws JSONException {
        AnalyseEntity analyseEntity = new AnalyseEntity(super.parse(json));

        if (json.has("Content") && !json.isNull("Content")) {
            JSONObject jsonData = json.getJSONObject("Content");

            if (jsonData.has("Unsigned") && !jsonData.isNull("Unsigned")) {
                analyseEntity.setUnsigned(jsonData.getInt("Unsigned"));
            }

            if (jsonData.has("UnsignedRate") && !jsonData.isNull("UnsignedRate")) {
                analyseEntity.setUnsignedRate(jsonData.getString("UnsignedRate"));
            }

            if (jsonData.has("Confirmed") && !jsonData.isNull("Confirmed")) {
                analyseEntity.setConfirmed(jsonData.getInt("Confirmed"));
            }

            if (jsonData.has("ConfirmedRate") && !jsonData.isNull("ConfirmedRate")) {
                analyseEntity.setConfirmedRate(jsonData.getString("ConfirmedRate"));
            }

            if (jsonData.has("Cancelled") && !jsonData.isNull("Cancelled")) {
                analyseEntity.setCancelled(jsonData.getInt("Cancelled"));
            }

            if (jsonData.has("CancelledRate") && !jsonData.isNull("CancelledRate")) {
                analyseEntity.setCancelledRate(jsonData.getString("CancelledRate"));
            }

            if (jsonData.has("Signed") && !jsonData.isNull("Signed")) {
                analyseEntity.setSigned(jsonData.getInt("Signed"));
            }

            if (jsonData.has("SignedRate") && !jsonData.isNull("SignedRate")) {
                analyseEntity.setSignedRate(jsonData.getString("SignedRate"));
            }

            if (jsonData.has("ToSign") && !jsonData.isNull("ToSign")) {
                analyseEntity.setToSign(jsonData.getInt("ToSign"));
            }

            if (jsonData.has("ToSignRate") && !jsonData.isNull("ToSignRate")) {
                analyseEntity.setToSignRate(jsonData.getString("ToSignRate"));
            }

            if (jsonData.has("SoilExcepted") && !jsonData.isNull("SoilExcepted")) {
                analyseEntity.setSoilExcepted(jsonData.getInt("SoilExcepted"));
            }

            if (jsonData.has("SoilExceptedRate") && !jsonData.isNull("SoilExceptedRate")) {
                analyseEntity.setSoilExceptedRate(jsonData.getString("SoilExceptedRate"));
            }

            if (jsonData.has("AutoConfirmed") && !jsonData.isNull("AutoConfirmed")) {
                analyseEntity.setAutoConfirmed(jsonData.getInt("AutoConfirmed"));
            }

            if (jsonData.has("AutoConfirmedRate") && !jsonData.isNull("AutoConfirmedRate")) {
                analyseEntity.setAutoConfirmedRate(jsonData.getString("AutoConfirmedRate"));
            }

        }
        return analyseEntity;
    }
}
