package com.example.myapplication.ui.achievements;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.R;

public class AchievementsFragment extends Fragment {

    private AchievementsViewFragment achievementsViewFragmentViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        achievementsViewFragmentViewModel =
                new ViewModelProvider(this).get(AchievementsViewFragment.class);
        View root = inflater.inflate(R.layout.fragment_achievements, container, false);
        return root;
    }
}