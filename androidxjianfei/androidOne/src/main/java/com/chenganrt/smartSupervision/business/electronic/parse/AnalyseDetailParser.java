package com.chenganrt.smartSupervision.business.electronic.parse;

import com.chenganrt.smartSupervision.business.electronic.parse.entiy.AnalyseDetailEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by Administrator on 2019/5/21.
 */

public class AnalyseDetailParser extends HeadParser {

    @Override
    public AnalyseDetailEntity parse(JSONObject json) throws JSONException {
        AnalyseDetailEntity detailEntity = new AnalyseDetailEntity(super.parse(json));
        List<AnalyseDetailEntity.AnalyseContentEntity> contentEntityList = new ArrayList<>();

        if (json.has("Content") && !json.isNull("Content")) {
            Object array = new JSONTokener(json.getString("Content")).nextValue();
            if (array != null && array instanceof JSONArray) {
                JSONArray jsonArray = json.getJSONArray("Content");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jb = jsonArray.getJSONObject(i);


                    AnalyseDetailEntity.AnalyseContentEntity contentEntity = new AnalyseDetailEntity.AnalyseContentEntity();
                    if (jb.has("Date") && !jb.isNull("Date")) {
                        contentEntity.setDate(jb.getString("Date"));
                    }

                    if (jb.has("Unsigned") && !jb.isNull("Unsigned")) {
                        contentEntity.setUnsigned(jb.getInt("Unsigned"));
                    }

                    if (jb.has("UnsignedRate") && !jb.isNull("UnsignedRate")) {
                        contentEntity.setUnsignedRate(jb.getString("UnsignedRate"));
                    }

                    if (jb.has("Confirmed") && !jb.isNull("Confirmed")) {
                        contentEntity.setConfirmed(jb.getInt("Confirmed"));
                    }

                    if (jb.has("ConfirmedRate") && !jb.isNull("ConfirmedRate")) {
                        contentEntity.setConfirmedRate(jb.getString("ConfirmedRate"));
                    }

                    if (jb.has("Cancelled") && !jb.isNull("Cancelled")) {
                        contentEntity.setCancelled(jb.getInt("Cancelled"));
                    }

                    if (jb.has("CancelledRate") && !jb.isNull("CancelledRate")) {
                        contentEntity.setCancelledRate(jb.getString("CancelledRate"));
                    }

                    if (jb.has("Signed") && !jb.isNull("Signed")) {
                        contentEntity.setSigned(jb.getInt("Signed"));
                    }

                    if (jb.has("SignedRate") && !jb.isNull("SignedRate")) {
                        contentEntity.setSignedRate(jb.getString("SignedRate"));
                    }

                    if (jb.has("ToSign") && !jb.isNull("ToSign")) {
                        contentEntity.setToSign(jb.getInt("ToSign"));
                    }

                    if (jb.has("ToSignRate") && !jb.isNull("ToSignRate")) {
                        contentEntity.setToSignRate(jb.getString("ToSignRate"));
                    }

                    if (jb.has("SoilExcepted") && !jb.isNull("SoilExcepted")) {
                        contentEntity.setSoilExcepted(jb.getInt("SoilExcepted"));
                    }

                    if (jb.has("SoilExceptedRate") && !jb.isNull("SoilExceptedRate")) {
                        contentEntity.setSoilExceptedRate(jb.getString("SoilExceptedRate"));
                    }

                    if (jb.has("AutoConfirmed") && !jb.isNull("AutoConfirmed")) {
                        contentEntity.setAutoConfirmed(jb.getInt("AutoConfirmed"));
                    }

                    if (jb.has("AutoConfirmedRate") && !jb.isNull("AutoConfirmedRate")) {
                        contentEntity.setAutoConfirmedRate(jb.getString("AutoConfirmedRate"));
                    }

                    contentEntityList.add(contentEntity);
                }
                detailEntity.setContentEntityList(contentEntityList);
            }
        }
        return detailEntity;
    }
}
