package com.HomePropertiesControll.HttpRequest;

import android.content.Context;

import com.HomePropertiesControll.User.User;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

public class HttpSensorsRequests {
    private OnSensorsListResponseCallback onSensorsListResponseCallback = null;

    public HttpSensorsRequests(OnSensorsListResponseCallback onSensorsListResponseCallback) {
        this.onSensorsListResponseCallback = onSensorsListResponseCallback;
    }

    public void sendRequest(final Context context){
        String url ="http://10.0.2.2:8080/api/android";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        onSensorsListResponseCallback.onResponse(response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        onSensorsListResponseCallback.onErrorResponse(error);
                    }
                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("Authorization", User.getInstance().getUserAuthorization());
                return params;
            }
        };
        HttpRequestSingleton.getInstance(context).addToRequestQueue(jsonArrayRequest);
    }
}
