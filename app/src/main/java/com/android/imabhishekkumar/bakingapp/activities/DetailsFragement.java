package com.android.imabhishekkumar.bakingapp.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.imabhishekkumar.bakingapp.Adapters.StepAdapter;
import com.android.imabhishekkumar.bakingapp.Model.Ingredient;
import com.android.imabhishekkumar.bakingapp.Model.Step;
import com.android.imabhishekkumar.bakingapp.R;
import com.android.imabhishekkumar.bakingapp.Utils.ClickListener;
import com.android.imabhishekkumar.bakingapp.Utils.Constants;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class DetailsFragement extends Fragment implements ClickListener {
    String steps;
    String ingredients;
    Boolean twoPane;
    private List<Step> stepList;
    private List<Ingredient> ingredientList;
    RecyclerView stepRecyclerView;
    StepAdapter stepAdapter;
    LinearLayoutManager linearLayoutManager;
    Gson gson;

    public DetailsFragement() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        steps = bundle.getString(Constants.KEY_STEPS_JSON);
        ingredients = bundle.getString(Constants.KEY_INGREDIENTS_JSON);
        gson = new Gson();
        ingredientList = gson.fromJson(ingredients,
                new TypeToken<List<Ingredient>>() {
                }.getType());
        stepList = gson.fromJson(steps,
                new TypeToken<List<Step>>() {
                }.getType());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.ingredients_row, container, false);

        TextView ingredientTV = rootView.findViewById(R.id.ingredientTV);
        stepRecyclerView = rootView.findViewById(R.id.stepsRV);
        StringBuilder stringBuffer = new StringBuilder();

        for (Ingredient ingredient : ingredientList) {
            stringBuffer.append(ingredient.getQuantity()).append(" ").append(ingredient.getMeasure()).append(" of ").append(ingredient.getIngredient()).append("\n");
        }
        setHasOptionsMenu(true);
        ingredientTV.setText(stringBuffer.toString());
        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        stepRecyclerView.setLayoutManager(linearLayoutManager);
        stepAdapter = new StepAdapter(getActivity(), stepList);
        stepRecyclerView.setAdapter(stepAdapter);
        stepAdapter.setOnClick(this);


        return rootView;
    }


    @Override
    public void onClick(Context context, Integer id, String description, String url, String thumbnailUrl) {
        Intent intent = new Intent(context, VideoActivity.class);
        intent.putExtra(Constants.BUNDLE_STEPS_ID, id);
        intent.putExtra(Constants.BUNDLE_STEPS_DESC, description);
        intent.putExtra(Constants.BUNDLE_STEPS_VIDEO_URL, url);
        intent.putExtra(Constants.BUNDLE_STEPS_THUMB_URL, thumbnailUrl);
        context.startActivity(intent);
    }
}
