package com.HomePropertiesControll.Sensors;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.HomePropertiesControll.HttpRequest.HttpSensorsRequests;
import com.HomePropertiesControll.HttpRequest.OnSensorsListResponseCallback;
import com.HomePropertiesControll.ItemsList.Sensor;
import com.HomePropertiesControll.ItemsList.SensorAdapter;
import com.HomePropertiesControll.R;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SensorsListActivity extends AppCompatActivity {

    private RecyclerView sensorRecycleView;
    private SensorAdapter sensorAdapter;
    private RecyclerView.LayoutManager sensorLayoutManager;
    final ArrayList<Sensor> sensorArrayList = new ArrayList<>();
    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sensors_list);

        final SwipeRefreshLayout refresh = findViewById(R.id.sensors_refresh);

        sensorRecycleView = findViewById(R.id.sensors_recycler_viewer);
        sensorRecycleView.setHasFixedSize(true);
        sensorLayoutManager = new LinearLayoutManager(this);
        sensorAdapter = new SensorAdapter(sensorArrayList);
        sensorRecycleView.setLayoutManager(sensorLayoutManager);
        sensorRecycleView.setAdapter(sensorAdapter);


        sendRequest();
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                sendRequest();
                refresh.setRefreshing(false);
            }
        });

        sensorAdapter.setOnItemClickListener(new SensorAdapter.OnItemClickListener() {
            @Override
            public void onSwitchClickListener(int position, boolean isChecked) {
                Sensor sensor = sensorArrayList.get(position);
                HttpSensorsRequests requests = new HttpSensorsRequests();
                try {
                    sensor.setState(isChecked);
                    requests.sendChangeRequest(sensor.getId(), "state", isChecked);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });


    }
    private void sendRequest(){
        HttpSensorsRequests httpSensorsRequests = new HttpSensorsRequests(new OnSensorsListResponseCallback() {
            @Override
            public void onResponse(JSONArray array) {
                sensorArrayList.clear();
                for (int i = 0; i < array.length(); i++) {
                    try {
                        JSONObject jsonobject = array.getJSONObject(i);

                        sensorArrayList.add(new Sensor(
                                jsonobject.getString("id"),
                                jsonobject.getString("name"),
                                jsonobject.getString("type"),
                                jsonobject.getString("location"),
                                jsonobject.getInt("level"),
                                jsonobject.getBoolean("state")
                        ));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    sensorAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                sensorArrayList.clear();
                sensorAdapter.notifyDataSetChanged();
                System.out.println(error.toString());
            }
        });
        httpSensorsRequests.sendRequest();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.onStop();
    }
}
