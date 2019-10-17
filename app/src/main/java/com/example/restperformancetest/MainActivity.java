package com.example.restperformancetest;

import android.os.Bundle;

import com.example.restperformancetest.functions.CheckPermissions;
import com.example.restperformancetest.functions.RecordManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        CheckPermissions.setActivity(this);
        CheckPermissions.checkPermissions();

        final RecordManager recordManager= RecordManager.manager;//녹음 관련 관리해주는 클래스.
        RecordManager.setActivity(this);
        final FloatingActionButton fab= findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "recording started", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
                for (Runnable i:recordManager.jobs()) {//해당 상태일 때 해야 하는 job들을 한다.
                    i.run();
                }
                if(recordManager.isSuccessful()){//만약 모든 job들이 성공적으로 끝났다면, recordManager
                    if(recordManager.isRecording()){
                        recordManager.setRecordingStateOff();
                        fab.setImageResource(RecordManager.FLOATINGACTIONBUTTON_ON);
                    }
                    else{
                        recordManager.setRecordingStateOn();
                        fab.setImageResource(RecordManager.FLOATINGACTIONBUTTON_OFF);
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(), "Recording state change failed.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);//메뉴 서랍(옆에서 나오는 것)
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_saved_recordings, R.id.nav_tools, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
