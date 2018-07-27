package com.leadinsource.prudentcook.recipeactivity;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.leadinsource.prudentcook.data.Repository;
import com.leadinsource.prudentcook.model.RecipeData;

import timber.log.Timber;

public class RecipeViewModel extends ViewModel {

    private final Repository repository;
    private MutableLiveData<String> recipeName = new MutableLiveData<>();
    private LiveData<RecipeData> recipeDataLiveData;

    public RecipeViewModel() {
        repository = Repository.getInstance();
        recipeDataLiveData = Transformations.switchMap(recipeName, new Function<String, LiveData<RecipeData>>() {
            @Override
            public LiveData<RecipeData> apply(String input) {
                return repository.getData(input);
            }
        });
    }

    public LiveData<RecipeData> getRecipeData() {
        return recipeDataLiveData;
    }

    public void setRecipe(String recipeName) {
        this.recipeName.postValue(recipeName);
    }

}
