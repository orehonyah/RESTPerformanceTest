package com.example.restperformancetest.functions;

import android.content.SharedPreferences;
import android.widget.TextView;

import com.example.restperformancetest.MainActivity;
import com.example.restperformancetest.R;
import com.example.restperformancetest.ui.home.HomeFragment;
import com.example.restperformancetest.ui.home.HomeViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;


public class RecordingTimer{
    //class variables
    private static RecordingTimer timer = new RecordingTimer();
    private static SimpleDateFormat dateFormat;
    //instance variables
    private Timer repeatTimer;
    private long startTime;
    private RecordingTimer(){
        dateFormat = new SimpleDateFormat("HH:mm:ss");
    }
    //static functions
    public static void reset(){
        RecordingTimer.timer = new RecordingTimer();
        MainActivity.handler().post(new Runnable() {//handler 이용
            @Override
            public void run() {
                HomeFragment.homeViewModel().setTimerText(dateFormat.format(new Date(0)));
            }
        });
    }
    public static RecordingTimer timer(){
        return RecordingTimer.timer;
    }
    //instance functions
    public void start(){
        this.startTime = System.currentTimeMillis();//영구적인 저장소에 저장해두어야함
        this.repeatTimer = new Timer();
        this.repeatTimer.schedule(new TimerTask() {//1초마다 실행
            @Override
            public void run() {
                MainActivity.handler().post(new Runnable() {//handler 이용
                    @Override
                    public void run() {
                        TextView timertxt = RecordManager.activity().findViewById(R.id.main_timer_textbox);
                        HomeViewModel homeViewModel = HomeFragment.homeViewModel();
                        if(homeViewModel==null){
                            return;
                        }
                        homeViewModel.setTimerText(dateFormat.format(new Date(System.currentTimeMillis()-startTime)));
                    }
                });
            }
        },0,1000);
    }
    public void stop(){
        this.repeatTimer.cancel();
    }
    public void resumeAtRestart(){

    }
}
