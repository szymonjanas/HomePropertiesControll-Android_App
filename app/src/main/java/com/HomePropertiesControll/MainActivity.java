package com.HomePropertiesControll;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.HomePropertiesControll.HttpRequest.HttpRequestSingleton;
import com.HomePropertiesControll.User.LoginActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HttpRequestSingleton.initHttpRequestSingleton(this);
        login();
    }

    public void login(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        login();
    }
}
