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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import timber.log.Timber;

public class IngredientsViewModel extends ViewModel {

    private final Repository repository;
    private MediatorLiveData<Set<String>> availableIngredients = new MediatorLiveData<>();
    private MutableLiveData<List<String>> chosenIngredients = new MutableLiveData<>();
    private MutableLiveData<Boolean> addingComplete = new MutableLiveData<>();

    public IngredientsViewModel() {
        repository = Repository.getInstance();
        addingComplete.postValue(false);
        availableIngredients.addSource(repository.getIngredients(), new Observer<Set<Ingredient>>() {
            @Override
            public void onChanged(@Nullable Set<Ingredient> ingredients) {
                if(ingredients==null) {
                    Timber.d("No ingredients in the database");
                    return;
                }
                // converting to a list of strings, we only need this for the activity
                Set<String> ingredientsStringList = new HashSet<>();
                for(Ingredient ingredient : ingredients) {
                    Timber.d("adding to available ingredients: %s", ingredient.getName());
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

                Set<String> ingredientsStringList = availableIngredients.getValue();

                if(ingredientsStringList==null) return;

                if(chosenIngredientsList!=null) {
                    ingredientsStringList.removeAll(chosenIngredientsList);
                }

                availableIngredients.postValue(ingredientsStringList);
            }
        });

    }


    LiveData<Set<String>> getAvailableIngredients() {

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

    public void setAddingComplete() {
        addingComplete.postValue(true);
    }

    public LiveData<Boolean> isAddingComplete() {
        return addingComplete;
    }
}
