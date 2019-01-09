package com.android.imabhishekkumar.bakingapp;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

import com.android.imabhishekkumar.bakingapp.Model.Ingredient;
import com.android.imabhishekkumar.bakingapp.Model.Recipe;
import com.android.imabhishekkumar.bakingapp.Utils.Constants;
import com.google.gson.Gson;

import java.util.List;



/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, String recipeName, List<Ingredient> ingredientList) {


        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget_provider);
        views.setTextViewText(R.id.widget_recipe_name, recipeName);
        views.removeAllViews(R.id.widget_ingredients_container);
        for (Ingredient ingredient : ingredientList) {
            RemoteViews ingredientView = new RemoteViews(context.getPackageName(),
                    R.layout.widget_recipe_list);
            ingredientView.setTextViewText(R.id.ingredient_name_text_view,
                    String.valueOf(ingredient.getIngredient()) + " " +
                            String.valueOf(ingredient.getMeasure()));
            views.addView(R.id.widget_ingredients_container, ingredientView);
        }
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
         SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.SHARED_PREFERENCES,
                Context.MODE_PRIVATE);
        String result = sharedPreferences.getString(Constants.WIDGET_RESULT, null);
        Gson gson = new Gson();
        Recipe recipe = gson.fromJson(result, Recipe.class);
        String recipeName = recipe.getName();
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, recipeName, recipe.getIngredients());
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

