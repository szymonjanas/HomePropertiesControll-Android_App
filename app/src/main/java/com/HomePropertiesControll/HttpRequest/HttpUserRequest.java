package com.HomePropertiesControll.HttpRequest;

import android.content.Context;

import com.HomePropertiesControll.User.User;
import com.HomePropertiesControll.User.UserModel;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class HttpUserRequest {
    private OnUserResponseCallback onUserResponseCallback = null;

    public HttpUserRequest(OnUserResponseCallback onUserResponseCallback) {
        this.onUserResponseCallback = onUserResponseCallback;

    }

    public void sendRequest(final Context context, final UserModel user){
        String url ="http://10.0.2.2:8080/api/android/login";
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET, url,
            new Response.Listener<String>(){
                @Override
                public void onResponse(String response) {
                    onUserResponseCallback.onResponse(response);
                }
        },  new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    onUserResponseCallback.onError(error);
                }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("Authorization", User.encryptUser(user));
                return params;
            }
        };
        HttpRequestSingleton.getInstance(context).addToRequestQueue(stringRequest);
    }
}
