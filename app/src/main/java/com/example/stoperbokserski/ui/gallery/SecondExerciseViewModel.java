package com.example.stoperbokserski.ui.gallery;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SecondExerciseViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public SecondExerciseViewModel() {
        mText = new MutableLiveData<>();
    }


    public LiveData<String> getText() {
        return mText;
    }
}



