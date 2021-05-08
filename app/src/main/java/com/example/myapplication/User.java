package com.example.myapplication;

public class User {

    public String username, name, email, password;

    public User (String username, String name, String email, String password)
    {
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public User (String username, String password)
    {
        this.name = username;
        this.username = username;
        this.email = "";
        this.password = password;
    }

}
