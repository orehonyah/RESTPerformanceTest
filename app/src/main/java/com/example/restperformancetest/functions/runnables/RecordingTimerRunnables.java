package com.example.restperformancetest.functions.runnables;

import android.widget.TextView;

import com.example.restperformancetest.R;
import com.example.restperformancetest.functions.RecordManager;
import com.example.restperformancetest.functions.RecordingTimer;
import com.example.restperformancetest.ui.home.HomeFragment;
import com.example.restperformancetest.ui.home.HomeViewModel;


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
    //필요 없을 것으로 보임. 만약 녹음 상태와 타이머가 맞지 않는 상황이 발견된다면
    //RecordingTimer의 resumeAtRestart도 함께 만들어줘야 함.
    //특정 저장소에 시작 시간 저장(성능을 덜 먹는다.) 혹은 RecordService에서 타이머를 관리하는 방식을 생각할 수 있음.
    public static class ResumeAtRestart implements  Runnable{
        @Override
        public void run(){

        }
    }
}
