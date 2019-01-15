package com.android.imabhishekkumar.bakingapp.activities;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.android.imabhishekkumar.bakingapp.Fragments.MainFragment;

import com.android.imabhishekkumar.bakingapp.R;

import com.android.imabhishekkumar.bakingapp.Utils.SimpleIdlingResource;



public class MainActivity extends AppCompatActivity {

    private static boolean mTwoPane;
    @Nullable
    private SimpleIdlingResource mIdlingResource;

    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (findViewById(R.id.tab_list_recipe_container) != null){
            mTwoPane = true;
            MainFragment fragment = new MainFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction()
                    .replace(R.id.tab_list_recipe_container, fragment)
                    .commit();
        }

        else{
            mTwoPane = false;
            MainFragment fragment = new MainFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction()
                    .replace(R.id.list_recipe_container, fragment)
                    .commit();
        }

        getIdlingResource();
    }




    public static boolean getNoPane() {
        return mTwoPane;
    }
}
