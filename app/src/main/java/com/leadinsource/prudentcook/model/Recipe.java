package com.leadinsource.prudentcook.model;

public class Recipe {

    private String name;
    private String ingredients;
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
