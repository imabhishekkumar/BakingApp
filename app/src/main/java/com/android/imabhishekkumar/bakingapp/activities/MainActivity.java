package com.android.imabhishekkumar.bakingapp.activities;

import android.content.Context;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
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
    private String url;
    private RecyclerView mRecyclerView;
    private RecipeRecyclerAdapter recipeRecyclerAdapter;
    final List<Recipe> recipeArrayList = new ArrayList<>();

    //https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.main_recyclerView);
        mRecyclerView.setHasFixedSize(true);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
            mRecyclerView.setLayoutManager(gridLayoutManager);
        } else {
            GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
            mRecyclerView.setLayoutManager(gridLayoutManager);
        }
        /*if (checkConnection(this) == true)
            getMovies(buildURL(currentOrder));
        else
            Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();*/
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);

        Call<List<Recipe>> call = api.getRecipe();

        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                List<Recipe> recipes = response.body();
                for (Recipe r : recipes) {
                    recipeArrayList.add(r);

                }
                recipeRecyclerAdapter = new RecipeRecyclerAdapter(MainActivity.this, recipeArrayList);
                mRecyclerView.setAdapter(recipeRecyclerAdapter);
                recipeRecyclerAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Failed to retrive data", Toast.LENGTH_SHORT).show();
            }
        });
        //getFoodItems(buildURL());
    }

    private boolean checkConnection(Context applicationContext) {
        ConnectivityManager conMgr = (ConnectivityManager) applicationContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        assert conMgr != null;
        NetworkInfo i = conMgr.getActiveNetworkInfo();
        if (i == null)
            return false;
        if (!i.isConnected())
            return false;
        if (!i.isAvailable())
            return false;
        return true;
    }
  /*  private String buildURL() {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority(Constants.BASE_URL)
                .appendPath("topher")
                .appendPath("2017")
                .appendPath("May")
                .appendPath("59121517_baking")
                .appendPath("baking.json");
        return builder.build().toString();

    }

    private void getFoodItems(String Url) {
        final List<Recipe> movieList = new ArrayList<>();




    }*/
}
