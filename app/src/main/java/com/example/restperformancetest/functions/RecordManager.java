package com.example.restperformancetest.functions;

import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.LinkedList;

public class RecordManager {
    public static final int FLOATINGACTIONBUTTON_ON = android.R.drawable.ic_lock_silent_mode_off;
    public static final int FLOATINGACTIONBUTTON_OFF = android.R.drawable.ic_btn_speak_now;
    public static final RecordManager manager = new RecordManager();

    public static RecordManager getManager(){
        return manager;
    }

    /* instance variables */
    private boolean recording;
    LinkedList<Runnable> jobs;
    private boolean successful;

    /* constructor */
    private RecordManager(){
        this.recording = false;
        this.jobs = new LinkedList<Runnable>();
        this.successful = true;
    }

    /* public functions */
    public void setRecordingStateOn(){
        //서비스 종료 작업을 올려둬야함.(on 상태이므로 다음 off명령 시 수행하도록)
        jobs.add(new Runnable() {
            @Override
            public void run() {

            }
        });
        this.recording = true;
    }

    public void setRecordingStateOff(){
        //서비스 시작 작업을 올려둬야 함.(off 상태이므로 다음 on명령 시 수행하도록)
        jobs.add(new Runnable() {
            @Override
            public void run() {
            }
        });
        this.recording = false;
    }
    public LinkedList<Runnable> jobs(){
        return this.jobs;
    }
    public boolean isRecording(){
        return this.recording;
    }
    public boolean isSuccessful(){
        return this.successful;
    }
}
