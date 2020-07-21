
package com.chenganrt.smartSupervision.business.electronic.parse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Parser基础接口
 *
 * @param <T>
 */
public interface Parser<T> {

    public abstract T parse(JSONObject json) throws JSONException;

    public T parse(JSONArray array) throws JSONException;

}
