package com.leadinsource.prudentcook.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.leadinsource.prudentcook.model.IngredientData;
import com.leadinsource.prudentcook.model.Ingredient;
import com.leadinsource.prudentcook.model.RecipeData;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Repository implements RecipeDatabase.RepositoryCallback {

    MutableLiveData<HashMap<String, RecipeData>> recipes;
    MutableLiveData<Set<Ingredient>> ingredients = new MutableLiveData<>();

    public Repository() {
        new RecipeDatabase(this);
    }

    @Override
    public void onRecipeAdded(HashMap<String, RecipeData> recipes) {
        if (this.recipes == null) {
            this.recipes = new MutableLiveData<>();
        }

        this.recipes.postValue(recipes);

        Set<Ingredient> newSet = new HashSet<>();


        for (String key : recipes.keySet()) {
            RecipeData recipeData = recipes.get(key);
            newSet.addAll(recipeData.getIngredientsSet());
        }

        ingredients.postValue(newSet);
    }

    public LiveData<Set<Ingredient>> getIngredients() {

        return ingredients;
    }
}
