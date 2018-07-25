package com.leadinsource.prudentcook.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.leadinsource.prudentcook.mainactivity.RVItem;
import com.leadinsource.prudentcook.model.Ingredient;
import com.leadinsource.prudentcook.model.RVItemImpl;
import com.leadinsource.prudentcook.model.RecipeData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Repository implements RecipeDatabase.RepositoryCallback {

    private MutableLiveData<HashMap<String, RecipeData>> recipes;
    private MutableLiveData<Set<Ingredient>> ingredients = new MutableLiveData<>();
    private MutableLiveData<List<RVItem>> matches = new MutableLiveData<>();

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

    public LiveData<List<RVItem>> getMatches(ArrayList<String> input) {

        List<RVItem> result = new ArrayList<>();

        HashMap<String, RecipeData> recipesMap = new HashMap<>(recipes.getValue());

        for(String recipeName : recipesMap.keySet()) {
            RecipeData recipeData = recipesMap.get(recipeName);
            if(recipeData.matches(input)) {
                result.add(new RVItemImpl(recipeName, recipeData));
            }
        }

        matches.setValue(result);

        return matches;
    }
}
