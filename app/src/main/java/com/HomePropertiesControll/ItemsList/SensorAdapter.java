package com.HomePropertiesControll.ItemsList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.HomePropertiesControll.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class SensorAdapter extends RecyclerView.Adapter<SensorAdapter.SensorViewHolder> {

    private ArrayList<Sensor> sensorArrayList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {

        void onSwitchClickListener(int position, boolean isChecked);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    static class SensorViewHolder extends RecyclerView.ViewHolder {
        TextView sensorName;
        TextView sensorLocation;
        SwitchCompat switchState;
        ProgressBar levelBar;
        TextView sensorType;
        ImageView sensorIcon;
        SensorViewHolder(@NonNull View itemView, final OnItemClickListener listener, final ArrayList<Sensor> sensorsList) {
            super(itemView);
            sensorName = itemView.findViewById(R.id.sensor_name);
            sensorLocation = itemView.findViewById(R.id.sensor_location);
            switchState = itemView.findViewById(R.id.switch_state);
            levelBar = itemView.findViewById(R.id.level_bar);
            sensorType = itemView.findViewById(R.id.sensor_type);
            sensorIcon = itemView.findViewById(R.id.sensor_icon);

            switchState.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onSwitchClickListener(position, isChecked);
                        }
                    }
                }
            });
        }
    }

    public SensorAdapter(ArrayList<Sensor> sensorArrayList) {
        this.sensorArrayList = sensorArrayList;
    }

    @NonNull
    @Override
    public SensorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.sensor_item, parent, false);
        return new SensorViewHolder(v, mListener, sensorArrayList);
    }

    @Override
    public void onBindViewHolder(@NonNull SensorViewHolder holder, int position) {
        Sensor currentItem = sensorArrayList.get(position);
        holder.sensorName.setText(currentItem.getName());
        holder.sensorLocation.setText(currentItem.getLocation());
        holder.switchState.setChecked(currentItem.getState());
        holder.levelBar.setProgress(currentItem.getLevel());
        holder.sensorType.setText(currentItem.getType());
        holder.sensorIcon.setImageResource(getResource(currentItem.getType()));
    }

    @Override
    public int getItemCount() {
        return sensorArrayList.size();
    }

    private int getResource(String type){
        switch (type){
            case "Power": return R.drawable.power;
            case "Light": return R.drawable.light;
            default:
                return R.drawable.default_type_icon;
        }
    }


}
