package com.android.imabhishekkumar.bakingapp.Api;

import com.android.imabhishekkumar.bakingapp.Model.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {

    @GET("baking.json")
    Call<List<Recipe>> getRecipe();
}
