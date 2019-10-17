package com.example.restperformancetest.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> timerText;
    private MutableLiveData<String> statusText;

    public void setTimerText(String value){
        timerText.setValue(value);
    }
    public void setStatusText(String value){
        statusText.setValue(value);
    }
    public HomeViewModel() {
        timerText = new MutableLiveData<>();
        timerText.setValue("00:00:00");
        statusText = new MutableLiveData<>();
        statusText.setValue("하단의 스피커 버튼을 눌러 녹음 시작");
    }

    public LiveData<String> getTimerText() {
        return timerText;
    }
    public LiveData<String> getStatusText() {
        return statusText;
    }
}