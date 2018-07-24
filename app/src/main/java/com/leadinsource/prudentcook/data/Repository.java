package com.leadinsource.prudentcook.data;

import android.arch.lifecycle.MutableLiveData;

import com.leadinsource.prudentcook.model.RecipeData;

import java.util.HashMap;

public class Repository implements RecipeDatabase.RepositoryCallback{

    MutableLiveData<HashMap<String, RecipeData>> recipes;

    public Repository() {
        new RecipeDatabase(this);
    }

    @Override
    public void onRecipesChanged(HashMap<String, RecipeData> recipes) {
        if(this.recipes==null) {
            this.recipes = new MutableLiveData<>();
        }

        this.recipes.postValue(recipes);
    }
}
