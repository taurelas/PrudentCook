package com.leadinsource.prudentcook.model;

import java.util.ArrayList;
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
        for (String key : ingredients.keySet()) {
            result.add(new IngredientImpl(key, ingredients.get(key)));
        }

        return result;
    }

    public boolean matches(ArrayList<String> ingredientList) {
        if (ingredients == null) return false;
        if (ingredientList == null) return false;

        //if (ingredientList.size() < ingredients.size()) return false;

        for (String name : ingredients.keySet()) {
            if (ingredients.get(name).isRequired()) {
                if (!ingredientList.contains(name)) {
                    return false;
                }
            }
        }

        return true;
    }

    public String getIngredientsString() {
        if(ingredients==null) return "";
        if(ingredients.keySet().isEmpty()) return "";

        StringBuilder sb = new StringBuilder();

        for(String ingredient : ingredients.keySet()) {
            sb.append(ingredient).append(" ");
        }

        return sb.toString();
    }
}
