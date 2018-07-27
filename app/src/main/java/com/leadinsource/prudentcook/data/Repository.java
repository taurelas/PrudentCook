package com.leadinsource.prudentcook.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

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

    private MutableLiveData<HashMap<String, RecipeData>> recipes = new MutableLiveData<>();
    private MutableLiveData<Set<Ingredient>> ingredients = new MutableLiveData<>();
    private MutableLiveData<List<RVItem>> matches = new MutableLiveData<>();
    private MediatorLiveData<RecipeData> recipeDataLiveData = new MediatorLiveData<>();
    private MutableLiveData<String> recipeName = new MutableLiveData<>();

    private static Repository instance;


    public static Repository getInstance() {
        if(instance==null) {
            instance = new Repository();
        }

        return instance;
    }


    private Repository() {
        new RecipeDatabase(this);
        recipeDataLiveData.addSource(recipeName, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String recipe) {
                if(recipe==null || recipes == null || recipes.getValue()==null) return;

                recipeDataLiveData.postValue(recipes.getValue().get(recipe));
            }
        });
        recipeDataLiveData.addSource(recipes, new Observer<HashMap<String, RecipeData>>() {
            @Override
            public void onChanged(@Nullable HashMap<String, RecipeData> stringRecipeDataHashMap) {
                if(recipeName==null || recipeName.getValue()==null || stringRecipeDataHashMap==null || stringRecipeDataHashMap.get(recipeName.getValue())==null)
                    return;

                recipeDataLiveData.postValue(stringRecipeDataHashMap.get(recipeName.getValue()));
            }
        });
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

    public LiveData<RecipeData> getData(String input) {
        recipeDataLiveData.setValue(recipes.getValue().get(input));
        recipeName.postValue(input);

        return recipeDataLiveData;
    }
}
