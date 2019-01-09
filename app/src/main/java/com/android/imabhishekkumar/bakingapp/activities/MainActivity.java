package com.android.imabhishekkumar.bakingapp.activities;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.imabhishekkumar.bakingapp.Adapters.RecipeRecyclerAdapter;
import com.android.imabhishekkumar.bakingapp.Api.Api;
import com.android.imabhishekkumar.bakingapp.BuildConfig;
import com.android.imabhishekkumar.bakingapp.Fragments.MainFragment;
import com.android.imabhishekkumar.bakingapp.Model.Recipe;
import com.android.imabhishekkumar.bakingapp.R;
import com.android.imabhishekkumar.bakingapp.Utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static boolean mTwoPane;


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


    }




    public static boolean getNoPane() {
        return mTwoPane;
    }
}
