package com.example.restperformancetest.functions.callbacks;

import android.content.Context;
import android.content.Intent;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;

import com.example.restperformancetest.MainActivity;
import com.example.restperformancetest.functions.RecordManager;
import com.example.restperformancetest.service.RecordService;
import com.example.restperformancetest.service.ScreenRecordService;

public class RecordingServiceRunnables {

    public static class Start implements Runnable{
        @Override
        public void run() {
            Intent intent = new Intent(RecordManager.activity().getApplicationContext(), ScreenRecordService.class);
            RecordManager.activity().startService(intent);
        }
    }
    public static class Stop implements Runnable {
        @Override
        public void run() {
            Intent intent = new Intent(RecordManager.activity().getApplicationContext(), ScreenRecordService.class);
            RecordManager.activity().stopService(intent);
        }
    }
}
