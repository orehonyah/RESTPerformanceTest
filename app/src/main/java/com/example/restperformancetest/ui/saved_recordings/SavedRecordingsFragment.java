package com.example.restperformancetest.ui.saved_recordings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.restperformancetest.R;

public class SavedRecordingsFragment extends Fragment {

    private SavedRecordingsViewModel savedRecordingsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        savedRecordingsViewModel =
                ViewModelProviders.of(this).get(SavedRecordingsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_saved_recordings, container, false);
        final TextView textView = root.findViewById(R.id.text_saved_recordings);
        savedRecordingsViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}