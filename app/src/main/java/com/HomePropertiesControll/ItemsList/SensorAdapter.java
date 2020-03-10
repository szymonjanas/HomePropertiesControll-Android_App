package com.HomePropertiesControll.ItemsList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.HomePropertiesControll.R;

import java.util.ArrayList;

public class SensorAdapter extends RecyclerView.Adapter<SensorAdapter.SensorViewHolder> {

    private ArrayList<Sensor> sensorArrayList;

    public static class SensorViewHolder extends RecyclerView.ViewHolder {

        public TextView sensorName;
        public TextView sensorLocation;
        public SensorViewHolder(@NonNull View itemView) {
            super(itemView);
            sensorName = itemView.findViewById(R.id.sensor_name);
            sensorLocation = itemView.findViewById(R.id.sensor_location);
        }
    }

    public SensorAdapter(ArrayList<Sensor> sensorArrayList) {
        this.sensorArrayList = sensorArrayList;
    }

    @NonNull
    @Override
    public SensorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.sensor_item, parent, false);
        return new SensorViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SensorViewHolder holder, int position) {
        Sensor currentItem = sensorArrayList.get(position);
        holder.sensorName.setText(currentItem.getName());
        holder.sensorLocation.setText(currentItem.getLocation());
    }

    @Override
    public int getItemCount() {
        return sensorArrayList.size();
    }

}
