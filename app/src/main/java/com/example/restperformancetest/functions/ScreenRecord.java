package com.example.restperformancetest.functions;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.display.VirtualDisplay;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.os.Handler;

import androidx.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class ScreenRecord {
    public static final int REQUEST_CODE = 10;
    private MediaProjectionManager mpm;
    private MediaProjection mp;
    private VirtualDisplay vd;


    //init, destroy methods
    private static ScreenRecord screenRecord = null;
    private ScreenRecord(Activity activity){
        screenRecord = this;
        mpm = (MediaProjectionManager)activity.getSystemService(Context.MEDIA_PROJECTION_SERVICE);
        activity.startActivityForResult(mpm.createScreenCaptureIntent(), 10);

    }
    public static ScreenRecord newInstance(Activity activity){
        if(screenRecord!=null){
            screenRecord.release();
        }
        screenRecord = new ScreenRecord(activity);
        return screenRecord;
    }
    public void start(int resultCode, Intent resultData, Handler handler){//intent에서 ok 받은 후
        mp = mpm.getMediaProjection(resultCode, resultData);
        mp.registerCallback(null, handler);
    }
    public void stop(){

    }
    public void release(){

    }
    private class MediaProjectionCallback extends MediaProjection.Callback{
        @Override
        public void onStop(){

            super.onStop();
        }
    }
}
