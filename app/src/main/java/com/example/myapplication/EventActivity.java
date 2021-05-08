package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class EventActivity extends AppCompatActivity {

    UserLocalStorage userLocalStorage;
    User user;
    Context context;
    Activity activity;
    String[] titleArray, descArray, startDateArray, endDateArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);


        context = this;
        activity = this;
        userLocalStorage = new UserLocalStorage(context);
        user = userLocalStorage.GetLoggedUser();

        ListView listView = findViewById(R.id.eventSchedule);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        Bundle arguments = getIntent().getExtras();
        String title = arguments.getString("title");

        Button addButton = (Button) findViewById(R.id.addToScheduleButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ServerRequest serverRequest = new ServerRequest(context);
                serverRequest.AddEventToSchedule(user, title, new GetUserCallback() {
                    @Override
                    public void done(User returnedUser) {

                    }
                });
            }
        });





        setTitle(title);
        ServerRequest serverRequest = new ServerRequest(context);
        serverRequest.getSingleEventSchedule(title, new GetUserCallbackArray() {
            @Override
            public void done(ArrayList array) {
                titleArray = new String[0];
                descArray = new String[0];
                startDateArray = new String[0];
                endDateArray = new String[0];
                if (array != null && array.size() != 0) {
                    titleArray = new String[array.size()];
                    descArray = new String[array.size()];
                    startDateArray = new String[array.size()];
                    endDateArray = new String[array.size()];
                    ListViewAdapter aa = new ListViewAdapter(activity,
                            titleArray, descArray, 0,
                            startDateArray, null, "");
                    listView.setAdapter(aa);

                    for (int i = 0; i < array.size(); i++) {
                        titleArray[i] = ((String[])array.get(i))[0];
                        descArray[i] = ((String[])array.get(i))[1];
                        startDateArray[i] = ((String[])array.get(i))[2];

                        Log.e("array", titleArray[i]);
                    }
                }




            }
        });
    }
}