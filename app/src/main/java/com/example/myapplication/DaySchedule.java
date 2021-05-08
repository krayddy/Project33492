package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class DaySchedule extends AppCompatActivity {

    UserLocalStorage userLocalStorage;
    User user;
    Context context;
    Activity activity;
    String[] titleArray, descArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_schedule);
        context = this;
        activity = this;
        userLocalStorage = new UserLocalStorage(this);
        user = userLocalStorage.GetLoggedUser();

        ListView listView = findViewById(R.id.dayScheduleList);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sendEmail();

            }
        });



        Bundle arguments = getIntent().getExtras();

        if (arguments != null)
        {
            String date = arguments.get("date").toString();
            setTitle(date);
            Log.e("date", date);
            ServerRequest serverRequest = new ServerRequest(this);
            serverRequest.getDaySchedule(user, date, new GetUserCallbackArray() {
                @Override
                public void done(ArrayList array) {
                    titleArray = new String[0];
                    descArray = new String[0];
                    if (array != null && array.size() != 0) {
                        titleArray = new String[array.size()];
                        descArray = new String[array.size()];
                        ListViewAdapter aa = new ListViewAdapter(activity,
                                titleArray, descArray, 0, null, null, date);
                        listView.setAdapter(aa);

                        for (int i = 0; i < array.size(); i++) {
                            titleArray[i] = ((String[])array.get(i))[0];
                            descArray[i] = ((String[])array.get(i))[1];

                            Log.e("array", titleArray[i]);
                        }
                    }




                }
            });
        }


    }


    @SuppressLint("LongLogTag")
    protected void sendEmail() {
        Log.i("Send email", "");

        String[] TO = {"ddsssqq1@gmail.com"};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");


        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Введи название задания");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "");

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
            Log.i("Finished sending email...", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this,
                    "У вас не установлены почтовые приложения", Toast.LENGTH_SHORT).show();
        }
    }
}