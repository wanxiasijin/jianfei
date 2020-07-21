package com.chenganrt.smartSupervision.business.electronic.parse;

import com.chenganrt.smartSupervision.business.electronic.parse.entiy.MessageEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2019/5/21.
 */

public class MessageParser extends HeadParser {
    @Override
    public MessageEntity parse(JSONObject json) throws JSONException {
        MessageEntity messageEntity = new MessageEntity(super.parse(json));
        List<MessageEntity> messageEntities = new ArrayList<>();
        if (json.has("Content") && !json.isNull("Content")) {
            Object array = new JSONTokener(json.getString("Content")).nextValue();
            if (array != null && array instanceof JSONArray) {
                JSONArray jsonArray = json.getJSONArray("Content");
                for (int i = 0; i < jsonArray.length(); i++) {
                    MessageEntity mesEntity = new MessageEntity();
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    if (jsonObject.has("PushID") && !jsonObject.isNull("PushID")) {
                        mesEntity.setPushId(jsonObject.getString("PushID"));
                    }

                    if (jsonObject.has("PushUserID") && !jsonObject.isNull("PushUserID")) {
                        mesEntity.setPushUserId(jsonObject.getString("PushUserID"));
                    }

                    if (jsonObject.has("PushTitle") && !jsonObject.isNull("PushTitle")) {
                        mesEntity.setPushTitle(jsonObject.getString("PushTitle"));
                    }

                    if (jsonObject.has("PushContent") && !jsonObject.isNull("PushContent")) {
                        mesEntity.setPushContent(jsonObject.getString("PushContent"));
                    }

                    if (jsonObject.has("PushTime") && !jsonObject.isNull("PushTime")) {
                        mesEntity.setPushTime(jsonObject.getString("PushTime"));
                    }

                    if (jsonObject.has("EbillNo") && !jsonObject.isNull("EbillNo")) {
                        mesEntity.setEbillNo(jsonObject.getString("EbillNo"));
                    }

                    if (jsonObject.has("VehicleNo") && !jsonObject.isNull("VehicleNo")) {
                        mesEntity.setVehicleNo(jsonObject.getString("VehicleNo"));
                    }

                    if (jsonObject.has("Status") && !jsonObject.isNull("Status")) {
                        mesEntity.setStatus(jsonObject.getString("Status"));
                    }

                    messageEntities.add(mesEntity);
                }

                messageEntity.setMessageEntities(messageEntities);
            }
        }
        return messageEntity;
    }
}
