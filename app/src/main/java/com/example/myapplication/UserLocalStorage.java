package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;

public class UserLocalStorage {

    public static final String SP_NAME = "userDetails";
    SharedPreferences userLocalDatabase;

    public UserLocalStorage(Context context)
    {
        userLocalDatabase = context.getSharedPreferences(SP_NAME, 0);
    }

    public void StoreUserData(User user)
    {
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();

        spEditor.putString("name", user.name);
        spEditor.putString("username", user.username);
        spEditor.putString("password", user.password);
        spEditor.putString("email", user.email);

        spEditor.apply();
    }

    public User GetLoggedUser()
    {
        String name = userLocalDatabase.getString("name", "");
        String username = userLocalDatabase.getString("username", "");
        String email = userLocalDatabase.getString("email", "");
        String password = userLocalDatabase.getString("password", "");

        User storedUser = new User(username, name, email, password);

        return storedUser;
    }

    public boolean GetUserLoggedIn()
    {
        return userLocalDatabase.getBoolean("loggedIn", false);
    }

    public void SetUserLoggedIn(boolean loggedIn)
    {
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putBoolean("loggedIn", loggedIn);
        spEditor.apply();
    }

    public void ClearUserData()
    {
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.clear();
        spEditor.apply();
    }

}
