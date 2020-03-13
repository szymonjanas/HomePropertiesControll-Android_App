package com.HomePropertiesControll.Complain;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.HomePropertiesControll.HttpRequest.HttpRequestSingleton;
import com.HomePropertiesControll.R;
import com.HomePropertiesControll.User.User;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ComplainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.complain);

        final TextView complainInfo = findViewById(R.id.complain_info);
        final EditText complainMessage = findViewById(R.id.complain_input);
        final EditText complainType = findViewById(R.id.complain_type);
        final String url = "http://10.0.2.2:8080/api/complains";
        final Button complainSubmit = findViewById(R.id.complain_submit);

        complainSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("message", complainMessage.getText());
                    jsonObject.put("type", complainType.getText());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                        Request.Method.PUT,
                        url,
                        jsonObject,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    complainInfo.setText(response.getString("message"));
                                } catch (JSONException e) {
                                    complainInfo.setText("Server error");
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                complainInfo.setText("Server error!");
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
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
