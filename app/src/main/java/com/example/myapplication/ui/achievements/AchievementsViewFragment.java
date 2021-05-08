package com.example.myapplication.ui.achievements;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AchievementsViewFragment extends ViewModel {

    private MutableLiveData<String> mText;

    public AchievementsViewFragment() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}