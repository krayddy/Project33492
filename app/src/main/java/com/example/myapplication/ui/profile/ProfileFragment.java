package com.example.myapplication.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.Login;
import com.example.myapplication.R;
import com.example.myapplication.Register;
import com.example.myapplication.User;
import com.example.myapplication.UserLocalStorage;

public class ProfileFragment extends Fragment{

    Button logout;
    TextView name, username, email;
    UserLocalStorage userLocalStorage;

    @Override
    public void onStart() {
        super.onStart();


    }

    private void DisplayUserData()
    {
        User user = userLocalStorage.GetLoggedUser();

        name.setText(user.name);
        username.setText(user.username);
        email.setText(user.email);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        logout = (Button)view.findViewById(R.id.logoutButton);
        name = (TextView)view.findViewById(R.id.Name);
        username = (TextView)view.findViewById(R.id.Username);
        email = (TextView)view.findViewById(R.id.Email);
        userLocalStorage = new UserLocalStorage(this.getContext());





        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.logoutButton:
//
                        userLocalStorage.ClearUserData();
                        userLocalStorage.SetUserLoggedIn(false);
//
                        startActivity(new Intent(getContext(), Login.class));
//
                        break;
                }
//
            }
        });

        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (Authenticate())
            DisplayUserData();
        else {
            startActivity(new Intent(this.getContext(), Login.class));
        }
    }

    private boolean Authenticate()
    {
        return userLocalStorage.GetUserLoggedIn();
    }
}