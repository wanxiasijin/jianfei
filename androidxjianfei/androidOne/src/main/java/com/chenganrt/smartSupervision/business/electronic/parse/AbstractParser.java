
package com.chenganrt.smartSupervision.business.electronic.parse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Parser基类
 *
 * @param <T>
 */
public abstract class AbstractParser<T> implements Parser<T> {

    /**
     * All derived parsers must implement parsing a JSONObject instance of themselves.
     *
     * @throws JSONException
     */

    public abstract T parse(JSONObject json) throws JSONException;

    /**
     * Only the GroupParser needs to implement this.
     *
     * @throws JSONException
     */
    public T parse(JSONArray array) throws JSONException {
        throw new JSONException("Unexpected JSONArray parse type encountered.");
    }

    public T parse(String json) throws JSONException {
        return parse(new JSONObject(json));
    }

    public boolean checkNull(JSONObject json, String name) {
        return json != null && json.has(name) && !json.isNull(name);
    }
}