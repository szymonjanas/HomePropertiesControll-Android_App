package com.HomePropertiesControll.User;

import androidx.annotation.NonNull;

public class UserModel {
    private String username;
    private String password;
    private boolean isLogin;

    public UserModel(String username, String password) {
        this.username = username;
        this.password = password;
        this.isLogin = false;
    }

    public UserModel() {}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }
}
