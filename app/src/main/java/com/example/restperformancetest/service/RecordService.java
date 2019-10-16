package com.example.restperformancetest.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.IOException;

public class RecordService extends Service {

    private  static final String Log_Tag = "RecorderService";
    private MediaRecorder recorder =  null;
    private  static String filename = null;

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
        Toast.makeText(this, "Record Service가 중지되었습니다.", Toast.LENGTH_LONG).show();
        Log.d(Log_Tag, "onDestroy()");
        recorder.stop();
        recorder.release();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //서비스가 시작 될 때마다 녹음을 시작한다

        filename = Environment.getExternalStorageDirectory().getAbsolutePath();
        filename += "/Record.3gp";
        if (recorder == null) {
            recorder = new MediaRecorder();
            recorder.setAudioSource(MediaRecorder.AudioSource.MIC);//마이크 사용
            recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);//파일 확장자 설정
            recorder.setOutputFile(filename);
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);// 인코더 설정

            try {
                recorder.prepare();
            } catch (IOException e) {
                Log.e(Log_Tag, "prepare() failed");
            }
            Toast.makeText(this, "Record Service가 시작되었습니다.", Toast.LENGTH_LONG).show();
            Log.d(Log_Tag, "onStart()");
            recorder.start();//녹음 시작


        } else {
            Toast.makeText(this, "Record Service가 정지되었습니다.", Toast.LENGTH_LONG).show();
            Log.d(Log_Tag, "onDestroy()");
            recorder.stop();
            recorder.release();
            recorder = null;
        }
        return super.onStartCommand(intent, flags, startId);
    }
}
