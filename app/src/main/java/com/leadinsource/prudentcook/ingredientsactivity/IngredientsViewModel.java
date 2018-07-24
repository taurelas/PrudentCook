package com.leadinsource.prudentcook.ingredientsactivity;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.leadinsource.prudentcook.data.Repository;
import com.leadinsource.prudentcook.model.Ingredient;

import java.util.List;
import java.util.Set;

public class IngredientsViewModel extends ViewModel {

    private final Repository repository;

    public IngredientsViewModel() {
        repository = new Repository();
    }


    LiveData<Set<Ingredient>> getIngredients() {
        return repository.getIngredients();
    }
}
