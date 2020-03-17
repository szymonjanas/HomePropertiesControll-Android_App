package com.HomePropertiesControll.HttpRequest;

import com.android.volley.VolleyError;

import org.json.JSONObject;

public interface OnComplainResponseCallback {
    void onResponse(JSONObject object);
    void onErrorResponse(VolleyError error);
}
