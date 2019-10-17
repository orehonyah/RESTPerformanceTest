package com.example.restperformancetest.functions;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;

import com.example.restperformancetest.MainActivity;
import com.example.restperformancetest.ui.saved_recordings.SavedRecordingsFragment;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class SavedRecordings {

    public static final String ROOTDIR = Environment.getExternalStorageDirectory().getAbsolutePath();
    public static final String PATHDIR = "/AAA/";
    public static String playingFile = null;
    public static MediaPlayer mp;
    public static boolean playing = false;
    public static Timer progressBarUpdater;
    public static Map<String, Integer> progressList = new HashMap<String, Integer>();
    private SavedRecordings(){

    }
    public static LinkedList<String> fileNames(){
        File[] fileList = new File(ROOTDIR+PATHDIR).listFiles();
        LinkedList<String> result = new LinkedList<>();
        for(File f : fileList){
            result.add(f.getName());
        }
        return result;
    }
    public static void play(String fileName, int progress){
        MediaPlayer tmpmp;
        if(SavedRecordings.playingFile ==null){
            playing = true;
            playingFile = fileName;
            tmpmp = MediaPlayer.create(RecordManager.activity(), Uri.parse(ROOTDIR + PATHDIR + fileName));
            tmpmp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    MainActivity.handler().post(new Runnable() {
                        @Override
                        public void run() {
                            SavedRecordingsFragment.savedRecordingsViewModel().setmInteger(0);
                        }
                    });
                    playing = false;
                    pauseProgressListener();
                    SavedRecordings.playingFile =null;
                }
            });
            mp = tmpmp;
            seekTo(fileName, progress);
            setProgressListener();
            mp.start();
        }
        else if(!fileName.equals(playingFile)){
            pause(playingFile);
            playingFile = null;
            play(fileName, progress);
        }else if(!playing){
            playing = true;
            playingFile = fileName;
            seekTo(fileName, progress);
            setProgressListener();
            mp.start();
        }
    }
    public static void pause(String fileName){
        if(fileName.equals(SavedRecordings.playingFile)){
            playing = false;
            if(mp==null){
                return;
            }
            pauseProgressListener();
            mp.pause();
        }
    }
    public static void seekTo(String fileName, int progress){
        if(fileName.equals(playingFile)){
            mp.seekTo(mp.getDuration()*progress/100);
        }
    }
    public static void setProgressListener(){
        progressBarUpdater = new Timer();
        progressBarUpdater.schedule(new TimerTask() {
            MediaPlayer tmpmp = mp;
            @Override
            public void run() {
                final int progress = tmpmp.getCurrentPosition()*100/tmpmp.getDuration();
                MainActivity.handler().post(new Runnable() {
                    @Override
                    public void run() {
                        SavedRecordingsFragment.savedRecordingsViewModel().setmInteger(progress);
                    }
                });
            }
        }, 0, 50);
    }
    public static void pauseProgressListener(){
        progressBarUpdater.cancel();
    }

}
