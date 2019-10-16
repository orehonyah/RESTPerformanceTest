package com.example.restperformancetest.functions;

import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.LinkedList;

public class SpeakingRecorder {
    public static final int FLOATINGACTIONBUTTON_ON = android.R.drawable.ic_lock_silent_mode_off;
    public static final int FLOATINGACTIONBUTTON_OFF = android.R.drawable.ic_btn_speak_now;

    final FloatingActionButton fab;
    LinkedList<Runnable> jobs = new LinkedList<Runnable>();

    public SpeakingRecorder(FloatingActionButton fab){
        this.fab = fab;
        fab.setImageResource(android.R.drawable.ic_btn_speak_now);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Snackbar.make(view, "recording started", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
                for (Runnable i:jobs) {
                    i.run();
                }
            }
        });
    }

    public void setRecordingStateOn(){
        jobs.add(new Runnable() {
            @Override
            public void run() {
                fab.setImageResource(FLOATINGACTIONBUTTON_ON);
            }
        });
    }
}
