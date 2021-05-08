package com.example.myapplication.ui.calendar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.DaySchedule;
import com.example.myapplication.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CalendarFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        CalendarView calendar = (CalendarView)view.findViewById(R.id.calendarFull);
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                String selectedDates = sdf.format(new Date(year-1900, month, dayOfMonth));
                Intent intent = new Intent(getContext(), DaySchedule.class);
                intent.putExtra("date", selectedDates);
                startActivity(intent);
            }
        });

        return view;
    }
}