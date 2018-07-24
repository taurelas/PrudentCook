package com.leadinsource.prudentcook.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import timber.log.Timber;

public class RecipeData {

    private HashMap<String, IngredientData> ingredients;
    private String steps;

    public HashMap<String, IngredientData> getIngredients() {
        return ingredients;
    }

    public void setIngredients(HashMap<String, IngredientData> ingredients) {
        this.ingredients = ingredients;
    }

    public String getSteps() {
        return steps;
    }

    public void setSteps(String steps) {
        this.steps = steps;
    }

    public Set<Ingredient> getIngredientsSet() {
        Set<Ingredient> result = new HashSet<>();
        Timber.d("Processing start");
        for(String key : ingredients.keySet()) {
            Timber.d("Processing: %s", key);
            result.add(new IngredientImpl(key, ingredients.get(key)));
        }

        return result;
    }
}
