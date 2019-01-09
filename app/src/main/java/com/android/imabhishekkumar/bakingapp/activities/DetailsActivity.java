package com.android.imabhishekkumar.bakingapp.activities;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.imabhishekkumar.bakingapp.Fragments.DetailsFragement;
import com.android.imabhishekkumar.bakingapp.Fragments.VideoFragment;
import com.android.imabhishekkumar.bakingapp.R;
import com.android.imabhishekkumar.bakingapp.Utils.Constants;

public class DetailsActivity extends AppCompatActivity {
    String stepJson, ingredientJson;
    Boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        DetailsFragement detailsFragement = new DetailsFragement();
        stepJson = getIntent().getStringExtra(Constants.KEY_STEPS);
        ingredientJson = getIntent().getStringExtra(Constants.KEY_INGREDIENTS);
        mTwoPane=false;
        if (findViewById(R.id.video_frame_layout) != null) {
            mTwoPane = true;
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.video_frame_layout, new VideoFragment()).commit();
        }
        if(savedInstanceState==null) {
            Bundle bundle = new Bundle();
            bundle.putString(Constants.KEY_STEPS_JSON, stepJson);
            bundle.putString(Constants.KEY_INGREDIENTS_JSON, ingredientJson);
            bundle.putBoolean(Constants.KEY_TWO_PANE,mTwoPane);
            detailsFragement.setArguments(bundle);
            FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, detailsFragement)
                    .commit();
        }
    }
}

