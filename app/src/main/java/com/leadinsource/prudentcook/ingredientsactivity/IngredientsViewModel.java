package com.leadinsource.prudentcook.ingredientsactivity;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.Nullable;

import com.leadinsource.prudentcook.data.Repository;
import com.leadinsource.prudentcook.model.Ingredient;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class IngredientsViewModel extends ViewModel {

    private final Repository repository;
    private MediatorLiveData<List<String>> availableIngredients = new MediatorLiveData<>();
    private MutableLiveData<List<String>> chosenIngredients = new MutableLiveData<>();

    public IngredientsViewModel() {
        repository = Repository.getInstance();

        availableIngredients.addSource(repository.getIngredients(), new Observer<Set<Ingredient>>() {
            @Override
            public void onChanged(@Nullable Set<Ingredient> ingredients) {
                if(ingredients==null) return;

                // converting to a list of strings, we only need this for the activity
                List<String> ingredientsStringList = new ArrayList<>();
                for(Ingredient ingredient : ingredients) {
                    ingredientsStringList.add(ingredient.getName());
                }

                //removing items that are chosen

                List<String> chosenIngredientsList = chosenIngredients.getValue();
                if(chosenIngredientsList!=null) {
                    ingredientsStringList.removeAll(chosenIngredientsList);
                }

                //posting the remaining values to LiveData
                availableIngredients.postValue(ingredientsStringList);
            }
        });

        availableIngredients.addSource(chosenIngredients, new Observer<List<String>>() {
            @Override
            public void onChanged(@Nullable List<String> chosenIngredientsList) {

                List<String> ingredientsStringList = availableIngredients.getValue();

                if(ingredientsStringList==null) return;

                if(chosenIngredientsList!=null) {
                    ingredientsStringList.removeAll(chosenIngredientsList);
                }

                availableIngredients.postValue(ingredientsStringList);
            }
        });

    }


    LiveData<List<String>> getAvailableIngredients() {

        return availableIngredients;
    }

    public void addChosenIngredient(String ingredient) {
        List<String> list = chosenIngredients.getValue();
        if(list==null) {
            list = new ArrayList<>();
        }

        list.add(ingredient);

        chosenIngredients.postValue(list);
    }

    public LiveData<List<String>> getChosenIngredients() {
        return chosenIngredients;
    }
}
