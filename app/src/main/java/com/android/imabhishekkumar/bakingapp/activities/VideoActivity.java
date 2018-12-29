package com.android.imabhishekkumar.bakingapp.activities;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.imabhishekkumar.bakingapp.R;

public class VideoActivity extends AppCompatActivity {
    Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        VideoFragment videoFragment = new VideoFragment();
        bundle = new Bundle();
        bundle = getIntent().getExtras();
        FragmentManager fragmentManager = getSupportFragmentManager();
        videoFragment.setArguments(bundle);
        fragmentManager.beginTransaction()
                .replace(R.id.video_frame_layout, videoFragment).commit();
        
    }
}
