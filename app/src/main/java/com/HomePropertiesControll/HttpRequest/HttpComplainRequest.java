package com.HomePropertiesControll.HttpRequest;

import com.HomePropertiesControll.User.User;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class HttpComplainRequest {
    private OnComplainResponseCallback onComplainResponseCallback;

    public HttpComplainRequest(OnComplainResponseCallback onComplainResponseCallback) {
        this.onComplainResponseCallback = onComplainResponseCallback;
    }

    public void sendRequest(JSONObject object){
        String url = HttpConfig.get_url_prod_server() + "complains";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.PUT,
                url,
                object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        onComplainResponseCallback.onResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        onComplainResponseCallback.onErrorResponse(error);
                    }
                }
        ){
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
        HttpRequestSingleton.getInstance().addToRequestQueue(jsonObjectRequest);
    }
}
