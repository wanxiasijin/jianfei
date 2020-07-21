package com.chenganrt.smartSupervision.business.electronic.parse;


import com.chenganrt.smartSupervision.business.electronic.ui.Group;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;



public class GroupGsonParser<E> extends AbstractParser<Group<E>> {

    private Parser<E> mSubParser;
    private Group<E> group;

    public GroupGsonParser(Parser<E> subParser) {
        mSubParser = subParser;
        group = new Group<E>();
    }

    @Override
    public Group<E> parse(JSONObject json) throws JSONException {
        if (json == null) {
            group.setResult(false);
        } else {
            Iterator<String> it = json.keys();
            while (it.hasNext()) {
                String key = it.next();
                Object obj = json.get(key);
                if (obj instanceof JSONArray) {
                    parse((JSONArray) obj);
                } else {
                    parserHead(key, json);
                }
            }
        }
        return group;
    }

    public Group<E> parserHead(String key, JSONObject json) throws JSONException {
        if (key.equals("Result")) {
            group.setResult(json.getString("Result"));
        } else if (key.equals("ShowTips")) {
            group.setShowTips(json.getString("ShowTips"));
        } else if (key.equals("Count")) {
            group.setTotalCount(json.getString("Count"));
        }
        return group;
    }

    @Override
    public Group<E> parse(JSONArray array) throws JSONException {
        if (mSubParser == null) return new Group<>();
        for (int i = 0, m = array.length(); i < m; i++) {
            Object element = array.get(i);
            if (element.equals(JSONObject.NULL)) continue;
            if (element instanceof JSONArray) {
                group.addAll((Group<E>) mSubParser.parse((JSONArray) element));
            } else {
                group.add(mSubParser.parse((JSONObject) element));
            }
        }
        return group;
    }
}
