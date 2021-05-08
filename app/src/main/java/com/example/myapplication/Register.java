package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Register extends AppCompatActivity implements View.OnClickListener {

    Button regButton;
    EditText name, username, email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        regButton=(Button)findViewById(R.id.registerButton);
        name=(EditText)findViewById(R.id.Name);
        username=(EditText)findViewById(R.id.Username);
        email=(EditText)findViewById(R.id.Email);
        password=(EditText)findViewById(R.id.Password);

        regButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.registerButton:

                String _name = name.getText().toString();
                String _username = username.getText().toString();
                String _email = email.getText().toString();
                String _password = password.getText().toString();

                User registeredUser = new User(_username, _name, _email, _password);

                registerUser(registeredUser);

                break;
        }
    }

    private void registerUser(User user) {
        ServerRequest serverRequest = new ServerRequest(this);
        serverRequest.storeUserDataInBackground(user, new GetUserCallback() {
            @Override
            public void done(User returnedUser) {
                startActivity(new Intent(Register.this, Login.class));
            }
        });
    }
}