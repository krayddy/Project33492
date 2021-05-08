package com.example.myapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.ui.profile.ProfileFragment;

public class Login extends AppCompatActivity implements View.OnClickListener {

    Button logButton, registerButton;
    EditText textUsername, textPassword;
    UserLocalStorage userLocalStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        textUsername=(EditText)findViewById(R.id.Username);
        textPassword=(EditText)findViewById(R.id.Password);
        logButton=(Button)findViewById(R.id.logScreenButton);
        registerButton=(Button)findViewById(R.id.logScreenRegisterButton);


        logButton.setOnClickListener(this);
        registerButton.setOnClickListener(this);

        userLocalStorage = new UserLocalStorage(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.logScreenButton:

                String username = textUsername.getText().toString();
                String password = textPassword.getText().toString();

                User user = new User(username, password);
                //userLocalStorage.SetUserLoggedIn(true);
                Authenticate(user);




                //startActivity(new Intent(this, MainActivity.class));

                break;
            case R.id.logScreenRegisterButton:
                startActivity(new Intent(this, Register.class));
                break;
        }
    }

    private void Authenticate(User user){
        ServerRequest serverRequest = new ServerRequest(this);
        serverRequest.fetchUserDataInBackground(user, new GetUserCallback() {
            @Override
            public void done(User returnedUser) {
                if (returnedUser != null && !returnedUser.username.equals("") && returnedUser.username!=null
                && !returnedUser.password.equals("") && returnedUser.password!=null){
                    logUserIn(returnedUser);
                } else {
                    findViewById(R.id.loginError).setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void logUserIn(User returnedUser) {
        userLocalStorage.StoreUserData(returnedUser);
        userLocalStorage.SetUserLoggedIn(true);
        findViewById(R.id.loginError).setVisibility(View.INVISIBLE);

        startActivity(new Intent(this, MainActivity.class));
    }

    private void showErrorMessage(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(Login.this);
        dialogBuilder.setMessage("Неправильное имя пользователя или пароль");
        dialogBuilder.setPositiveButton("Ок", null);
        dialogBuilder.show();
    }
}