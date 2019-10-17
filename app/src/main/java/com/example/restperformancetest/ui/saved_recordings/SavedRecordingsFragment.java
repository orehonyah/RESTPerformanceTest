package com.example.restperformancetest.ui.saved_recordings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.restperformancetest.R;

public class SavedRecordingsFragment extends Fragment {

    private SavedRecordingsViewModel savedRecordingsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        savedRecordingsViewModel =
                ViewModelProviders.of(this).get(SavedRecordingsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_saved_recordings, container, false);
        View itemxml = inflater.inflate(R.layout.record_item,container, false);
        LinearLayout savedRecordings = root.findViewById(R.id.view_saved_recordings);
        for(int i = 0;i<1;i++){
            LinearLayout item = (LinearLayout)itemxml.getRootView();
            item.getChildAt(0);
            savedRecordings.addView(item);

        }
        return root;
    }
}