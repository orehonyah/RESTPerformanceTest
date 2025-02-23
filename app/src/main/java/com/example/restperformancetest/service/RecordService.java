package com.example.restperformancetest.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.restperformancetest.functions.SavedRecordings;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RecordService extends Service {

    private static final String LOG_TAG = "RecorderService";
    private static MediaRecorder recorder =  null;
    private static String filename = null;

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
        Toast.makeText(this, RecordService.filename+" 에 저장되었습니다.", Toast.LENGTH_LONG).show();
        Log.d(LOG_TAG, "onDestroy()");
        recorder.stop();
        recorder.release();
        recorder = null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //서비스가 시작 될 때마다 녹음을 시작한다

        filename = SavedRecordings.ROOTDIR + SavedRecordings.PATHDIR;
        File file = new File(filename);
        file.mkdirs();

        filename += new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss").format(new Date(System.currentTimeMillis()))+".3gp";
        if (recorder == null) {
            recorder = new MediaRecorder();
            recorder.setAudioSource(MediaRecorder.AudioSource.MIC);//마이크 사용
            recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);//파일 확장자 설정
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);// 인코더 설정
            recorder.setOutputFile(filename);

            try {
                recorder.prepare();
            } catch (IOException e) {
                Log.e(LOG_TAG, "prepare() failed");
            }
            Toast.makeText(this, "Record Service가 시작되었습니다.", Toast.LENGTH_LONG).show();
            Log.d(LOG_TAG, "onStart()");
            recorder.start();//녹음 시작

        }
        return super.onStartCommand(intent, flags, startId);
    }
}
