package com.example.restperformancetest.ui.saved_recordings;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SavedRecordingsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SavedRecordingsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}