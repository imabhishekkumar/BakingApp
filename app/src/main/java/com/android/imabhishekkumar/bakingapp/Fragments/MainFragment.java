package com.android.imabhishekkumar.bakingapp.Fragments;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.imabhishekkumar.bakingapp.Adapters.RecipeRecyclerAdapter;
import com.android.imabhishekkumar.bakingapp.Api.Api;
import com.android.imabhishekkumar.bakingapp.Model.Recipe;
import com.android.imabhishekkumar.bakingapp.R;
import com.android.imabhishekkumar.bakingapp.Utils.Constants;
import com.android.imabhishekkumar.bakingapp.Utils.SimpleIdlingResource;
import com.android.imabhishekkumar.bakingapp.activities.MainActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainFragment extends Fragment {
    public MainFragment() {

    }
    SimpleIdlingResource idlingResource;
    private TypedArray cakeImage;
    private static boolean mTwoPane;
    private RecyclerView mRecyclerView;
    private RecipeRecyclerAdapter recipeRecyclerAdapter;
    final List<Recipe> recipeArrayList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mTwoPane = MainActivity.getNoPane();
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_main, container, false);

        idlingResource = (SimpleIdlingResource) ((MainActivity) getActivity()).getIdlingResource();
        if (idlingResource != null) {
            idlingResource.setIdleState(false);
        }
        mRecyclerView = view.findViewById(R.id.main_recyclerView);
        mRecyclerView.setHasFixedSize(true);
        cakeImage = getResources().
                obtainTypedArray(R.array.food_image_array);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
            mRecyclerView.setLayoutManager(gridLayoutManager);
            idlingResource.setIdleState(true);
        } else if(mTwoPane) {
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),3 );
            mRecyclerView.setLayoutManager(gridLayoutManager);
            idlingResource.setIdleState(true);        }
        else{
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
            mRecyclerView.setLayoutManager(gridLayoutManager);
            idlingResource.setIdleState(true);
        }
        if (checkConnection(getContext())) {
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
                    int i = 0;
                    for (Recipe r : recipes) {

                        r.setCakeImage(cakeImage.getResourceId(i, 0));
                        recipeArrayList.add(r);
                        i++;

                    }
                    recipeRecyclerAdapter = new RecipeRecyclerAdapter(view.getContext(), recipeArrayList);
                    mRecyclerView.setAdapter(recipeRecyclerAdapter);
                    recipeRecyclerAdapter.notifyDataSetChanged();

                }

                @Override
                public void onFailure(Call<List<Recipe>> call, Throwable t) {
                    Toast.makeText(getContext(), "Failed to retrive data", Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            Toast.makeText(getContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
        }


        return view;
    }

    private boolean checkConnection(Context applicationContext) {
        ConnectivityManager conMgr = (ConnectivityManager) applicationContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        assert conMgr != null;
        NetworkInfo i = conMgr.getActiveNetworkInfo();
        if (i == null)
            return false;
        return i.isConnected();
    }
}
