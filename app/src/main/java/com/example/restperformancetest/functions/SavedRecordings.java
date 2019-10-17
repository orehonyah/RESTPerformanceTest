package com.example.restperformancetest.functions;

import android.media.MediaPlayer;
import android.os.Environment;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class SavedRecordings {

    public static final String ROOTDIR = Environment.getExternalStorageDirectory().getAbsolutePath();
    public static final String PATHDIR = "/AAA/";
    public static String playing = null;
    public static MediaPlayer mp;
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
        if(SavedRecordings.playing==null){
            playing = fileName;
            mp = new MediaPlayer();
            try {
                mp.setDataSource(ROOTDIR + PATHDIR + fileName);
                mp.prepare();
                seekTo(fileName, progress);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void pause(String fileName){
        if(fileName.equals(SavedRecordings.playing)){
            playing = null;
            if(mp==null){
                return;
            }
            mp.pause();
        }
    }
    public static void seekTo(String fileName, int progress){
        if(fileName.equals(playing)){
            mp.seekTo(mp.getDuration()*progress/100);
        }
    }

}
