package com.HomePropertiesControll.Complain;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.HomePropertiesControll.HttpRequest.HttpComplainRequest;
import com.HomePropertiesControll.HttpRequest.OnComplainResponseCallback;
import com.HomePropertiesControll.R;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

public class ComplainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.complain);

        final TextView complainInfo = findViewById(R.id.complain_info);
        final EditText complainMessage = findViewById(R.id.complain_input);
        final EditText complainType = findViewById(R.id.complain_type);
        final Button complainSubmit = findViewById(R.id.complain_submit);

        final HttpComplainRequest complainRequest = new HttpComplainRequest(new OnComplainResponseCallback() {
            @Override
            public void onResponse(JSONObject object) {
                try {
                    complainInfo.setText(object.getString("message"));
                } catch (JSONException e) {
                    complainInfo.setText(R.string.server_error);
                }
            }
            @Override
            public void onErrorResponse(VolleyError error) {
                complainInfo.setText(R.string.server_error);
            }
        });

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
                complainRequest.sendRequest(jsonObject);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
