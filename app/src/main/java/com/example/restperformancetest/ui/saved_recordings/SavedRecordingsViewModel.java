package com.example.restperformancetest.ui.saved_recordings;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SavedRecordingsViewModel extends ViewModel {

    private MutableLiveData<Integer> mInteger;
    public SavedRecordingsViewModel() {
        mInteger = new MutableLiveData<>();
        mInteger.setValue(0);
    }

    public LiveData<Integer> getmInteger() {
        return mInteger;
    }
    public void setmInteger(Integer message){
        mInteger.setValue(message);
    }
}