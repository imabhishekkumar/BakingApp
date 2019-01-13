package com.android.imabhishekkumar.bakingapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.imabhishekkumar.bakingapp.Model.Ingredient;
import com.android.imabhishekkumar.bakingapp.Model.Recipe;
import com.android.imabhishekkumar.bakingapp.Model.Step;
import com.android.imabhishekkumar.bakingapp.R;
import com.android.imabhishekkumar.bakingapp.Utils.Constants;
import com.android.imabhishekkumar.bakingapp.activities.DetailsActivity;
import com.google.gson.Gson;


import java.util.List;

public class RecipeRecyclerAdapter extends RecyclerView.Adapter<RecipeRecyclerAdapter.ViewHolder> {
    private List<Recipe> recipesList;
    private LayoutInflater mLayoutInflator;
    private Context context;
    private List<Ingredient> ingredientList;
    private List<Step> stepList;
    private SharedPreferences sharedPreferences;
    private Gson gson;
    public RecipeRecyclerAdapter(Context context, List<Recipe> data) {
        this.context = context;
//        mLayoutInflator = LayoutInflater.from(context);
        recipesList = data;
        sharedPreferences = context.getSharedPreferences(Constants.SHARED_PREFERENCES,
                Context.MODE_PRIVATE);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recipe_row,
                        viewGroup,
                        false);


        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Recipe currentRecipe = recipesList.get(i);
        viewHolder.textView.setText(currentRecipe.getName());
        viewHolder.imageView.setImageResource(
                currentRecipe.getCakeImage());

    }

    @Override
    public int getItemCount() {
        return recipesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;
        public TextView textView;

        public ViewHolder(@NonNull final View itemView, Context ctx) {
            super(itemView);
            imageView = itemView.findViewById(R.id.recipeImage);
            textView= itemView.findViewById(R.id.recipeName);
            context=ctx;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    ingredientList = recipesList.get(pos).getIngredients();
                    stepList = recipesList.get(pos).getSteps();
                    Intent intent = new Intent(context,DetailsActivity.class);
                    gson = new Gson();
                    String ingredientJson = gson.toJson(ingredientList);
                    String stepJson = gson.toJson(stepList);
                    intent.putExtra(Constants.KEY_INGREDIENTS, ingredientJson);
                    intent.putExtra(Constants.KEY_STEPS, stepJson);
                    String resultJson = gson.toJson(recipesList.get(pos));
                    sharedPreferences.edit().putString(Constants.WIDGET_RESULT, resultJson).apply();
                    context.startActivity(intent);

                }
            });
        }
    }
}
