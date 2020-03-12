package com.HomePropertiesControll.HttpRequest;

import com.android.volley.VolleyError;

public interface OnUserResponseCallback {
    void onResponse(String response);
    void onError(VolleyError error);
}
