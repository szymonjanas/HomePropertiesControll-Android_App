package com.HomePropertiesControll.User;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.HomePropertiesControll.HttpRequest.HttpUserRequest;
import com.HomePropertiesControll.HttpRequest.OnUserResponseCallback;
import com.HomePropertiesControll.R;
import com.HomePropertiesControll.Sensors.SensorsListActivity;
import com.android.volley.VolleyError;

public class LoginActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private TextView errorMessage;
    private CheckBox rememberMe;
    final UserModel user = new UserModel();
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;

    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        Button loginBtn = findViewById(R.id.loginBtn);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        errorMessage = findViewById(R.id.errorMessage);
        rememberMe = findViewById(R.id.remember_me);

        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();

        Boolean saveLogin = loginPreferences.getBoolean("saveLogin", false);
        if (saveLogin.equals(true)){
            username.setText(loginPreferences.getString("username", ""));
            password.setText(loginPreferences.getString("password", ""));
            rememberMe.setChecked(true);
        }

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
                    if (!rememberMe.isChecked()){
                        username.getText().clear();
                        password.getText().clear();
                    }
                    showSensorList();
                } else {
                    setNotLogin("Invalid user or password!");
                }
            }
            @Override
            public void onError(VolleyError error) {
                setNotLogin("Server error:" + error.toString());
            }});
        userRequest.sendRequest(user);
    }

    public void showSensorList(){
        Intent intent = new Intent(this, SensorsListActivity.class);
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
        if (rememberMe.isChecked()){
            loginPrefsEditor.putBoolean("saveLogin", true);
            loginPrefsEditor.putString("username", user.getUsername());
            loginPrefsEditor.putString("password", user.getPassword());
            loginPrefsEditor.commit();
        } else {
            loginPrefsEditor.clear();
            loginPrefsEditor.commit();
        }

        onClick(user);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
