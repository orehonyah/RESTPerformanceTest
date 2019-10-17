package com.example.restperformancetest.ui.saved_recordings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.restperformancetest.R;
import com.example.restperformancetest.functions.SavedRecordings;

import java.util.LinkedList;

public class SavedRecordingsFragment extends Fragment {

    private static SavedRecordingsViewModel savedRecordingsViewModel;
    public static SavedRecordingsViewModel savedRecordingsViewModel(){
        return SavedRecordingsFragment.savedRecordingsViewModel;
    }
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        savedRecordingsViewModel =ViewModelProviders.of(this).get(SavedRecordingsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_saved_recordings, container, false);
        LinearLayout savedRecordingsLayout = root.findViewById(R.id.view_saved_recordings);
        LinkedList<String> files = SavedRecordings.fileNames();
        for(String fileName : files){
            View itemxml = inflater.inflate(R.layout.record_item,container, false);
            LinearLayout item = (LinearLayout)itemxml.getRootView();
            final SeekBar seebar = (SeekBar)item.getChildAt(0);
            final TextView tv = (TextView)((LinearLayout)item.getChildAt(1)).getChildAt(0);
            ImageButton ib_play = (ImageButton)((LinearLayout)((LinearLayout)item.getChildAt(1)).getChildAt(1)).getChildAt(0);
            ImageButton ib_pause = (ImageButton)((LinearLayout)((LinearLayout)item.getChildAt(1)).getChildAt(1)).getChildAt(1);
            savedRecordingsLayout.addView(item);
            tv.setText(fileName);
            final String s = fileName;
            ib_play.setOnClickListener(new View.OnClickListener() {
                private String fileName = s;
                @Override
                public void onClick(View v) {
                    SavedRecordings.play(fileName, seebar.getProgress());
                }
            });
            ib_pause.setOnClickListener(new View.OnClickListener() {
                private String fileName = s;
                @Override
                public void onClick(View v) {
                    SavedRecordings.pause(fileName);
                }
            });
            seebar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                private String fileName = s;
                private int progress = 0;
                public void onStopTrackingTouch(SeekBar seekBar) {
                    SavedRecordings.seekTo(fileName, progress);
                }

                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                public void onProgressChanged(SeekBar seekBar, int progress,
                                              boolean fromUser) {
                    this.progress = progress;
                }
            });

        }
        return root;
    }
}