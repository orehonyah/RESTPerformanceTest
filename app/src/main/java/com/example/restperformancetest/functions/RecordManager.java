package com.example.restperformancetest.functions;

import android.app.Activity;
import android.content.Intent;

import com.example.restperformancetest.service.RecordService;

import java.util.LinkedList;

public class RecordManager {
    public static final int FLOATINGACTIONBUTTON_ON = android.R.drawable.ic_lock_silent_mode_off;
    public static final int FLOATINGACTIONBUTTON_OFF = android.R.drawable.ic_btn_speak_now;
    public static final RecordManager manager = new RecordManager();
    private static Activity activity;

    public static RecordManager getManager(){
        return manager;
    }
    public static void setActivity(Activity activity){
        RecordManager.activity = activity;
    }

    /* instance variables */
    private boolean recording;
    LinkedList<Runnable> nextjobs;
    private boolean successful;

    /* constructor */
    private RecordManager(){
        this.nextjobs = new LinkedList<Runnable>();
        this.setRecordingStateOff();
    }

    /* public functions */
    public void setRecordingStateOn(){
        //서비스 종료 작업을 올려둬야함.(on 상태이므로 다음 off명령 시 수행하도록)
        nextjobs.add(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(activity.getApplicationContext(), RecordService.class);
                activity.stopService(intent);
            }
        });
        this.recording = true;
        this.successful = true;
    }

    public void setRecordingStateOff(){
        //서비스 시작 작업을 올려둬야 함.(off 상태이므로 다음 on명령 시 수행하도록)
        nextjobs.add(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(activity.getApplicationContext(), RecordService.class);
                activity.startService(intent);
            }
        });
        this.recording = false;
        this.successful = true;
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
