package com.leadinsource.prudentcook.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Recipe {

    public String name;

    public List<String> ingredientLines;

    transient private String ingredients;
    private String steps;


    public Recipe(String name, String ingredients, String steps) {
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
    }

    public String getName() {
        return name;
    }

    public String getIngredients() {
        return ingredients;
    }

    public String getSteps() {
        return steps;
    }
}
