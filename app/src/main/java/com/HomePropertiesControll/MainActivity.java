package com.HomePropertiesControll;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.HomePropertiesControll.HttpRequest.HttpUserRequest;
import com.HomePropertiesControll.HttpRequest.OnUserResponseCallback;
import com.HomePropertiesControll.Sensors.SensorsList;
import com.HomePropertiesControll.User.User;
import com.HomePropertiesControll.User.UserModel;
import com.android.volley.VolleyError;


public class MainActivity extends AppCompatActivity {

    final Context context = this;
    private Button loginBtn;
    private EditText username;
    private EditText password;
    private TextView errorMessage;
    final UserModel user = new UserModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        loginBtn = findViewById(R.id.loginBtn);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        errorMessage = findViewById(R.id.errorMessage);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("BTN", "HELLO");
                isLogin();
            }
        });
    }

    public void onClick(final UserModel user){
         HttpUserRequest userRequest = new HttpUserRequest(new OnUserResponseCallback() {
            @Override
            public void onResponse(String response) {
                if (response.equals("TRUE")){
                    Log.i("USER", "Good user");
                        user.setLogin(true);
                        User.getInstance().setUser(user);
                        showSensorList();
                } else {
                    setNotLogin("Invalid user or password!");
                }
            }
            @Override
            public void onError(VolleyError error) {
                setNotLogin("Server error:" + error.toString());
            }});
        userRequest.sendRequest(context, user);
    }

    public void showSensorList(){
        Intent intent = new Intent(this, SensorsList.class);
        startActivity(intent);
        Log.i("SHOW", "test");
    }

    public void setNotLogin(String message){
        errorMessage.setText(message);
    }

    public void isLogin(){
        user.setUsername(username.getText().toString());
        user.setPassword(password.getText().toString());
        Log.i("USERNAME", user.getUsername());
        Log.i("PASSWORD", user.getPassword());
        Log.i("BTN", " HELLO 2");
        onClick(user);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (user.isLogin()){
            showSensorList();
        }
    }
}
