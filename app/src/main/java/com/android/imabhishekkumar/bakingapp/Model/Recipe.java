package com.android.imabhishekkumar.bakingapp.Model;

import com.android.imabhishekkumar.bakingapp.R;

import java.io.Serializable;
import java.util.List;

public class Recipe implements Serializable {

    public int id;
    public String name;
    public List<Ingredient> ingredients;
    public List<Step> steps;
    public int servings;
    public String image;
    private int cakeImage;

    public Recipe() {
        cakeImage = R.drawable.image;
    }

    public int getCakeImage() {
        return cakeImage;
    }

    public void setCakeImage(int cakeImage) {
        this.cakeImage = cakeImage;
    }


    public Recipe(int id, String name, List<Ingredient> ingredients, List<Step> steps, int servings, String image, int cakeImage) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
        this.servings = servings;
        this.image = image;
        this.cakeImage = cakeImage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
