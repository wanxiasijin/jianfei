package com.chenganrt.smartSupervision.business.electronic;

import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public interface ListItem extends Parcelable {

	<T extends ListItem> T newObject();
	void parsFromJson(JSONObject json) throws JSONException;
}
