package com.HomePropertiesControll.HttpRequest;

import android.util.Log;

import com.HomePropertiesControll.User.User;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class HttpSensorsRequests {
    private OnSensorsListResponseCallback onSensorsListResponseCallback = null;

    public HttpSensorsRequests(OnSensorsListResponseCallback onSensorsListResponseCallback) {
        this.onSensorsListResponseCallback = onSensorsListResponseCallback;
    }

    public HttpSensorsRequests() {}

    public void sendRequest(){
        String url = HttpConfig.get_url_prod_server() + "android/";
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
        HttpRequestSingleton.getInstance().addToRequestQueue(jsonArrayRequest);
    }

    public void sendChangeRequest(String id, String parameter, Object value) throws JSONException {
        String url = HttpConfig.get_url_prod_server() + "android/" + parameter;

        JSONObject jsonObject = new JSONObject();
        jsonObject.put(parameter, value);
        jsonObject.put("id", id);

        JsonObjectRequest stringRequest = new JsonObjectRequest
                (Request.Method.PUT, url, jsonObject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.w("REQUEST RESPONSE", response.toString());
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("REQUEST ERROR", error.toString());
                    }
                }) {

            @Override
            public String getBodyContentType()
            {
                return "application/json; charset=utf-8";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("Authorization", User.getInstance().getUserAuthorization());
                params.put("Content-Type", "application/json");
                return params;
            };
        };
        HttpRequestSingleton.getInstance().addToRequestQueue(stringRequest);
    }
}
