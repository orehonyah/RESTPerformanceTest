package com.example.restperformancetest.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.restperformancetest.R;

public class HomeFragment extends Fragment {

    private static HomeViewModel homeViewModel;
    public static HomeViewModel homeViewModel(){
        return HomeFragment.homeViewModel;
    }
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView timerTextView = root.findViewById(R.id.main_timer_textbox);
        homeViewModel.getTimerText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                timerTextView.setText(s);
            }
        });
        final TextView statusTextView = root.findViewById(R.id.main_status_textbox);
        homeViewModel.getStatusText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                statusTextView.setText(s);
            }
        });
        return root;
    }
}