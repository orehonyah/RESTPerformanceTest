package com.example.restperformancetest.ui.saved_recordings;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SavedRecordingsViewModel extends ViewModel {

    private MutableLiveData<Integer> mText;
    public SavedRecordingsViewModel() {
        mText = new MutableLiveData<>();
    }

    public LiveData<Integer> getmText() {
        return mText;
    }
    public void setmText(Integer message){
        mText.setValue(message);
    }
}