package com.example.myapplication.ui.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.EventActivity;
import com.example.myapplication.GetUserCallbackArray;
import com.example.myapplication.ListViewAdapter;
import com.example.myapplication.R;
import com.example.myapplication.ServerRequest;
import com.example.myapplication.User;
import com.example.myapplication.UserLocalStorage;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    UserLocalStorage userLocalStorage;
    User user;
    Context context;
    Activity activity;
    String[] titleArray, descArray, startDateArray, endDateArray;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        context = this.getContext();
        activity = this.getActivity();
        userLocalStorage = new UserLocalStorage(context);
        user = userLocalStorage.GetLoggedUser();

        ListView listView = root.findViewById(R.id.eventsScheduleList);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context, EventActivity.class);
                TextView textView = (TextView) view.findViewById(R.id.dayEventTitle);
                intent.putExtra("title", textView.getText());
                startActivity(intent);
            }
        });





            ServerRequest serverRequest = new ServerRequest(context);
            serverRequest.getEventsSchedule(user, new GetUserCallbackArray() {
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
                                startDateArray, endDateArray, "");
                        listView.setAdapter(aa);

                        for (int i = 0; i < array.size(); i++) {
                            titleArray[i] = ((String[])array.get(i))[0];
                            descArray[i] = ((String[])array.get(i))[1];
                            startDateArray[i] = ((String[])array.get(i))[2];
                            endDateArray[i] = ((String[])array.get(i))[3];

                            Log.e("array", titleArray[i]);
                        }
                    }




                }
            });


        return root;
    }
}