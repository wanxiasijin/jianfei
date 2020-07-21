package com.chenganrt.smartSupervision.business.home;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/5/21.
 */

public class MatchMessageEntiyParser {
    public static List<MatchMessageEntity> parse(String json) throws JSONException {
        List<MatchMessageEntity> messageEntity1List = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(json);
        if (jsonObject.optInt("status") == 200) {
            JSONObject jsonData = jsonObject.getJSONObject("data");
            JSONArray jsonArray = jsonData.getJSONArray("records");
            for (int i = 0; i < jsonArray.length(); i++) {
                MatchMessageEntity matchMessageEntity1 = new MatchMessageEntity();
                matchMessageEntity1.setTitle(jsonArray.getJSONObject(i).getString("title"));
                matchMessageEntity1.setAmount(jsonArray.getJSONObject(i).getString("amount"));
                matchMessageEntity1.setWaste_type_detail(jsonArray.getJSONObject(i).getString("waste_type_detail"));
                matchMessageEntity1.setNotice_id(jsonArray.getJSONObject(i).getString("notice_id"));
                matchMessageEntity1.setCreate_time(jsonArray.getJSONObject(i).getString("push_time"));
                messageEntity1List.add(matchMessageEntity1);
            }
        }
        return messageEntity1List;
    }
}
