package com.android.imabhishekkumar.bakingapp.activities;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.imabhishekkumar.bakingapp.Fragments.VideoFragment;
import com.android.imabhishekkumar.bakingapp.R;

public class VideoActivity extends AppCompatActivity {
    Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        if (savedInstanceState==null) {
            VideoFragment videoFragment = new VideoFragment();
            bundle = new Bundle();
            bundle = getIntent().getExtras();

            //Only init when the bool is false and fragments need to be transacted
            //for preserving the ExoPlayer instance so that it resumes properly

            FragmentManager fragmentManager = getSupportFragmentManager();
            videoFragment.setArguments(bundle);
            fragmentManager.beginTransaction()
                    .replace(R.id.video_frame_layout, videoFragment).commit();
        }
    }
}
