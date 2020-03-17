package com.HomePropertiesControll.Sensors;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.HomePropertiesControll.Complain.ComplainActivity;
import com.HomePropertiesControll.HttpRequest.HttpSensorsRequests;
import com.HomePropertiesControll.HttpRequest.OnSensorsListResponseCallback;
import com.HomePropertiesControll.ItemsList.Sensor;
import com.HomePropertiesControll.ItemsList.SensorAdapter;
import com.HomePropertiesControll.R;

import com.HomePropertiesControll.User.User;
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

    private TextView userCardUsername;
    private TextView userCardMOTD;
    private Button complainBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sensors_list);

        final SwipeRefreshLayout refresh = findViewById(R.id.sensors_refresh);

        userCardUsername = findViewById(R.id.user_card_username);
        userCardMOTD = findViewById(R.id.user_card_motd);
        complainBtn = findViewById(R.id.complain_button);

        sensorRecycleView = findViewById(R.id.sensors_recycler_viewer);
        sensorRecycleView.setHasFixedSize(true);
        sensorLayoutManager = new LinearLayoutManager(this);
        sensorAdapter = new SensorAdapter(sensorArrayList);
        sensorRecycleView.setLayoutManager(sensorLayoutManager);
        sensorRecycleView.setAdapter(sensorAdapter);

        userCardUsername.setText(User.getInstance().getUser().getUsername());
        userCardMOTD.setText(R.string.motd_default);

        complainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                complain();
            }
        });

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

    public void complain(){
        Intent intent = new Intent(this, ComplainActivity.class);
        startActivity(intent);
    }
}
