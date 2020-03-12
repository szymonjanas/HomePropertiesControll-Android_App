package com.HomePropertiesControll.HttpRequest;

import com.android.volley.VolleyError;

import org.json.JSONArray;

public interface OnSensorsListResponseCallback {
    void onResponse(JSONArray array);
    void onErrorResponse(VolleyError error);
}
