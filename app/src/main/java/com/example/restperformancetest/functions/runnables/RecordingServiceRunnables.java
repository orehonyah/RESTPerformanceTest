package com.example.restperformancetest.functions.runnables;

import android.content.Intent;

import com.example.restperformancetest.functions.RecordManager;
import com.example.restperformancetest.service.RecordService;

public class RecordingServiceRunnables {
    public static class Start implements Runnable{
        @Override
        public void run() {
            Intent intent = new Intent(RecordManager.activity().getApplicationContext(), RecordService.class);
            RecordManager.activity().startService(intent);
        }
    }
    public static class Stop implements Runnable {
        @Override
        public void run() {
            Intent intent = new Intent(RecordManager.activity().getApplicationContext(), RecordService.class);
            RecordManager.activity().stopService(intent);
        }
    }
}
