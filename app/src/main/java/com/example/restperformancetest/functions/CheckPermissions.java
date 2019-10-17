package com.example.restperformancetest.functions;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class CheckPermissions {
    final static int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1;
    final static int MY_PERMISSIONS_REQUEST_RECORD_AUDIO = 2;
    private static Activity activity;
    final static CheckPermissions cp = new CheckPermissions();

    private CheckPermissions(){}
    public static void setActivity(Activity activity){
        CheckPermissions.activity = activity;
    }
    public static boolean checkPermissions(){
        return cp.run();
    }
    private boolean run(){
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(CheckPermissions.activity,Manifest.permission.RECORD_AUDIO)!= PackageManager.PERMISSION_GRANTED
        ||ContextCompat.checkSelfPermission(CheckPermissions.activity,Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(CheckPermissions.activity,Manifest.permission.RECORD_AUDIO)
            ||ActivityCompat.shouldShowRequestPermissionRationale(CheckPermissions.activity,Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                ActivityCompat.requestPermissions(CheckPermissions.activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.RECORD_AUDIO},MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
                return check();
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(CheckPermissions.activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.RECORD_AUDIO},MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
                //ActivityCompat.requestPermissions(CheckPermissions.activity, new String[]{Manifest.permission.RECORD_AUDIO},MY_PERMISSIONS_REQUEST_RECORD_AUDIO);
                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
                return check();
            }
        } else {
            return true;
            // Permission has already been granted
        }
    }
    private boolean check(){
        if (ContextCompat.checkSelfPermission(CheckPermissions.activity,Manifest.permission.RECORD_AUDIO)!= PackageManager.PERMISSION_GRANTED
                ||ContextCompat.checkSelfPermission(CheckPermissions.activity,Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(CheckPermissions.activity,Manifest.permission.RECORD_AUDIO)
                    ||ActivityCompat.shouldShowRequestPermissionRationale(CheckPermissions.activity,Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                return false;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }
}
