package com.chenganrt.smartSupervision.business.electronic.parse;

import android.text.TextUtils;

import com.chenganrt.smartSupervision.business.electronic.okhttp.Head;
import com.chenganrt.smartSupervision.business.electronic.ListItem;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;



public class GsonParse<T> extends AbstractParser<T> {

    private Class<T> o;
    private String key;

    public GsonParse(Class<T> o) {
        this.o = o;
    }

    public GsonParse(Class<T> o, String key) {
        this.o = o;
        this.key = key;
    }

    @Override
    public T parse(JSONObject json) throws JSONException {
        if (o == null) throw new JSONException("classOfT is null");
        if (ListItem.class.isInstance(o)) {
            return parseListItem(json);
        } else if (!TextUtils.isEmpty(key) && json.has(key)) {
            return parseHead(fromJson(json.getJSONObject(key), o), json);
        } else {
            return fromJson(json, o);
        }
    }

    public T parseListItem(JSONObject json) throws JSONException {
        try {
            ListItem item = (ListItem) o.newInstance();
            item.parsFromJson(json);
            return (T) item;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        throw new JSONException(o.getSimpleName() + " newInstance Error");
    }

    private T parseHead(T obj, JSONObject json) throws JSONException {
        if (obj instanceof Head) {
            Head head = ((Head) obj);
            head.setHead(new HeadParser().parse(json));
        }
        return obj;
    }

    public T fromJson(JSONObject json, Class<T> t) throws JSONException {
        try {
            return new Gson().fromJson(json.toString(), t);
        } catch (Exception e) {
            e.printStackTrace();
            throw new JSONException(e.getMessage());
        }
    }
}
