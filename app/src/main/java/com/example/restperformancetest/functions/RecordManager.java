package com.example.restperformancetest.functions;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ActivityNotFoundException;
import android.content.Context;

import com.example.restperformancetest.functions.callbacks.RecordingServiceRunnables;
import com.example.restperformancetest.functions.callbacks.RecordingTimerRunnables;
import com.example.restperformancetest.service.RecordService;
import com.example.restperformancetest.service.ScreenRecordService;

import java.util.LinkedList;

public class RecordManager {
    public static final int FLOATINGACTIONBUTTON_ON = android.R.drawable.ic_lock_silent_mode_off;
    public static final int FLOATINGACTIONBUTTON_OFF = android.R.drawable.ic_btn_speak_now;

    private static RecordManager manager;
    private static Activity activity;

    public static RecordManager getManager(){
        if(RecordManager.activity()==null){
            throw new ActivityNotFoundException("No Activity Specified on RecordManager");
        }
        if(RecordManager.manager == null){
            RecordManager.manager = new RecordManager();
        }
        return manager;
    }
    public static void setActivity(Activity activity){
        RecordManager.activity = activity;
    }
    public static Activity activity(){
        return RecordManager.activity;
    }

    /* instance variables */
    private boolean recording;
    LinkedList<Runnable> nextjobs;//버튼 on/off시 수행할 작업 목록
    private boolean successful;

    /* constructor */
    private RecordManager(){
        if(isServiceRunning()){//만약 앱을 껐다 켰는데 녹음중이었을 경우
            this.setRecordingStateOn();
        }
        else{
            this.setRecordingStateOff();
        }
    }

    /* public functions */
    public void setRecordingStateOn(){
        //서비스 종료 작업을 올려둬야함.(on 상태이므로 다음 off명령 시 수행하도록)
        nextjobs = new LinkedList<Runnable>();
        nextjobs.add(new RecordingServiceRunnables.Stop());
        nextjobs.add(new RecordingTimerRunnables.Stop());
        this.recording = true;
        this.successful = true;
    }

    public void setRecordingStateOff(){
        //서비스 시작 작업을 올려둬야 함.(off 상태이므로 다음 on명령 시 수행하도록)
        nextjobs = new LinkedList<Runnable>();
        nextjobs.add(new RecordingServiceRunnables.Start());
        nextjobs.add(new RecordingTimerRunnables.Start());
        this.recording = false;
        this.successful = true;
    }
    public boolean isServiceRunning(){//서비스가 실행 중인지 확인하는 메소드
        ActivityManager manager = (ActivityManager) activity.getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);

        for (ActivityManager.RunningServiceInfo serviceInfo : manager.getRunningServices(Integer.MAX_VALUE))
        {
            if (RecordService.class.getName().equals(serviceInfo.service.getClassName()))
                return true;
        }
        return false;
    }
    public boolean screenReordingNotPermitted(){
        return ScreenRecordService.permitted();
    }
    public LinkedList<Runnable> jobs(){
        return this.nextjobs;
    }
    public boolean isRecording(){
        return this.recording;
    }
    public boolean isSuccessful(){
        return this.successful;
    }
}
