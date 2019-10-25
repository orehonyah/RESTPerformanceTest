package com.example.restperformancetest.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.MediaRecorder;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.restperformancetest.MainActivity;
import com.example.restperformancetest.functions.RecordManager;
import com.example.restperformancetest.functions.SavedRecordings;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.app.Activity.RESULT_OK;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class ScreenRecordService extends Service {
    private static final String LOG_TAG = "RecorderService";
    private MediaRecorder mMediaRecorder =  null;
    private String filename = null;

    public static final int PERMISSION_CODE = 10;
    private static MediaProjectionManager mProjectionManager;
    private static MediaProjection mMediaProjection;
    private VirtualDisplay mVirtualDisplay;
    private static boolean screenCapturePermitted = false;

    public void init(){
        initRecorder();
        initProjection();
    }
    private void initRecorder() {
        if (mMediaRecorder == null) {
            mMediaRecorder = new MediaRecorder();
            mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.SURFACE);
            mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            mMediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
            mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mMediaRecorder.setVideoEncodingBitRate(512 * 1000);
            mMediaRecorder.setVideoFrameRate(60);
            mMediaRecorder.setVideoSize(MainActivity.mWidth, MainActivity.mHeight);
        }
    }
    private void initProjection(){
        mProjectionManager = (MediaProjectionManager) RecordManager.activity().getSystemService(Context.MEDIA_PROJECTION_SERVICE);
        RecordManager.activity().startActivityForResult(mProjectionManager.createScreenCaptureIntent(), PERMISSION_CODE);
    }

    public static boolean permitted(){
        return screenCapturePermitted;
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        //서비스가 처음으로 실행되면 ~하는 동작

    }

    @Override
    public void onDestroy() {
        //서비스가 중지되면 녹음을 중지한다
        Toast.makeText(this, "Record Service가 중지되었습니다.", Toast.LENGTH_SHORT).show();
        Toast.makeText(this, this.filename+" 에 저장되었습니다.", Toast.LENGTH_LONG).show();
        Log.d(LOG_TAG, "onDestroy()");
        mMediaRecorder.stop();
        mMediaRecorder.release();
        mMediaRecorder = null;
        if(mMediaProjection != null){
            mMediaProjection.stop();
            mMediaProjection = null;
        }
        if(mVirtualDisplay != null)
            mVirtualDisplay.release();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //서비스가 시작 될 때마다 녹음을 시작한다
        if(!screenCapturePermitted){
            Toast.makeText(this, "화면 녹화가 허용되지 않았습니다.", Toast.LENGTH_SHORT).show();
            return super.onStartCommand(intent, flags, startId);
        }
        this.init();
        filename = SavedRecordings.ROOTDIR + SavedRecordings.PATHDIR;
        File file = new File(filename);
        file.mkdirs();
        filename += new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss").format(new Date(System.currentTimeMillis()))+".3gp";
        if (mMediaRecorder != null) {
            mMediaRecorder.setOutputFile(filename);

            try {
                mMediaRecorder.prepare();
            } catch (IOException e) {
                Log.e(LOG_TAG, "prepare() failed");
            }
            mVirtualDisplay = mMediaProjection.createVirtualDisplay("ScreenRecordService", MainActivity.mWidth, MainActivity.mHeight, MainActivity.mScreenDensity,
                    DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR, mMediaRecorder.getSurface(), null, null);
            Toast.makeText(this, "Record Service가 시작되었습니다.", Toast.LENGTH_LONG).show();
            Log.d(LOG_TAG, "onStart()");
            mMediaRecorder.start();//녹음 시작

        }
        return super.onStartCommand(intent, flags, startId);
    }

    // private method

    public static class InitScreenCapture implements Runnable{
        private int requestCode;
        private int resultCode;
        private Intent data;
        public void setArgs(int requestCode, int resultCode, Intent data){
            this.requestCode = requestCode;
            this.resultCode = resultCode;
            this.data = data;
        }
        @Override
        public void run(){//to
            if (resultCode != RESULT_OK) {
                Toast.makeText(RecordManager.activity(),
                        "Screen Cast Permission Denied", Toast.LENGTH_SHORT).show();
                return;
            }
            mMediaProjection = mProjectionManager.getMediaProjection(resultCode, data);
            //mMediaProjection.registerCallback(new MediaProjectionCallback(), null);
            screenCapturePermitted = true;
        }
    }/*
    private static class MediaProjectionCallback extends MediaProjection.Callback {
        @Override
        public void onStop() {
            if (mToggleButton.isChecked()) {
                mToggleButton.setChecked(false);
                mMediaRecorder.stop();
                mMediaRecorder.reset();
                Log.v(LOG_TAG, "Recording Stopped");
            }
            mMediaProjection = null;
            stopScreenSharing();
            Log.i(LOG_TAG, "MediaProjection Stopped");
        }
    }*/
}
