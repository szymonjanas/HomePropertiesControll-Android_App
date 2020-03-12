package com.HomePropertiesControll.User;

import android.util.Base64;

public class User {
    private static User instance;
    private UserModel user;

    private User(){}

     private void isUser(){
        initInstance();
        if (user == null){
            throw new SecurityException("UserModel not setted! setUser first!");
        }
    }

    public static void initInstance() {
        if (instance == null){
            instance = new User();
        }
    }

    public static User getInstance(){
        initInstance();
        return instance;
    }

    public UserModel getUser(){
        isUser();
        return user;
    }

    public void setUser(UserModel userModel){
        user = userModel;
    }

    public String getUserAuthorization(){
        isUser();
        String authRaw = String.format("%s:%s",
                instance.getUser().getUsername(),
                instance.getUser().getPassword());
        return "Basic " + Base64.encodeToString(authRaw.getBytes(), Base64.DEFAULT);
    }

    public static String encryptUser(UserModel user){
        String authRaw = String.format("%s:%s",
                user.getUsername(),
                user.getPassword());
        return "Basic " + Base64.encodeToString(authRaw.getBytes(), Base64.DEFAULT);
    }
}
