package com.HomePropertiesControll;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.HomePropertiesControll.HttpRequest.HttpRequestSingleton;
import com.HomePropertiesControll.ItemsList.Sensor;
import com.HomePropertiesControll.ItemsList.SensorAdapter;
import com.HomePropertiesControll.User.User;
import com.HomePropertiesControll.User.UserModel;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private RecyclerView sensorRecycleView;
    private RecyclerView.Adapter sensorAdapter;
    private RecyclerView.LayoutManager sensorLayoutManager;
    final ArrayList<Sensor> sensorArrayList = new ArrayList<>();
    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        User.initInstance();
        User.getInstance().setUser(new UserModel("android", "androidpassword"));
        final Button refreshButton = findViewById(R.id.refreshButton);

        sensorRecycleView = findViewById(R.id.sensors_recycler_viewer);
        sensorRecycleView.setHasFixedSize(true);
        sensorLayoutManager = new LinearLayoutManager(this);
        sensorAdapter = new SensorAdapter(sensorArrayList);
        sensorRecycleView.setLayoutManager(sensorLayoutManager);
        sensorRecycleView.setAdapter(sensorAdapter);

        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HttpRequestSingleton.getInstance(context).addToRequestQueue(refresh());
            }
        });


    }

    public JsonArrayRequest refresh(){
        sensorArrayList.clear();
        String url ="http://10.0.2.2:8080/api/android";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        sensorArrayList.clear();
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject jsonobject = null;
                            try {
                                jsonobject = response.getJSONObject(i);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                sensorArrayList.add(new Sensor(
                                        jsonobject.getString("id"),
                                        jsonobject.getString("name"),
                                        jsonobject.getString("type"),
                                        jsonobject.getString("location"),
                                        jsonobject.getInt("level")
                                ));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            sensorAdapter.notifyDataSetChanged();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        sensorArrayList.clear();
                        sensorAdapter.notifyDataSetChanged();
                        System.out.println(error.toString());
                    }
                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("Authorization", User.getInstance().getUserAuthorization());
                return params;
            }
        };
        return jsonArrayRequest;
    }
}
