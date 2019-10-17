package com.example.restperformancetest.functions.runnables;

import android.widget.TextView;

import com.example.restperformancetest.R;
import com.example.restperformancetest.functions.RecordManager;
import com.example.restperformancetest.functions.RecordingTimer;
import com.example.restperformancetest.ui.home.HomeFragment;
import com.example.restperformancetest.ui.home.HomeViewModel;

import java.util.Date;

public class RecordingTimerRunnables {
    public static class Start implements Runnable{
        @Override
        public void run(){
            RecordingTimer.reset();
            RecordingTimer.timer().start();
            HomeViewModel homeViewModel = HomeFragment.homeViewModel();
            if(homeViewModel==null){
                return;
            }
            homeViewModel.setStatusText("녹음 중");
        }
    }
    public static class Stop implements Runnable{
        @Override
        public void run(){
            RecordingTimer.timer().stop();
            TextView timertxt = RecordManager.activity().findViewById(R.id.main_status_textbox);
            HomeViewModel homeViewModel = HomeFragment.homeViewModel();
            if(homeViewModel==null){
                return;
            }
            homeViewModel.setStatusText("녹음 저장됨");
        }
    }
    public static class ResumeAtRestart implements  Runnable{
        @Override
        public void run(){

        }
    }
}
