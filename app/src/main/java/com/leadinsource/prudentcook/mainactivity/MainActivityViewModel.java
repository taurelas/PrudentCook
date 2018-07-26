package com.leadinsource.prudentcook.mainactivity;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.leadinsource.prudentcook.data.Repository;

import java.util.ArrayList;
import java.util.List;

public class MainActivityViewModel extends ViewModel {

    private final Repository repository;
    private MutableLiveData<ArrayList<String>> chosenIngredients = new MutableLiveData<>();
    private LiveData<List<RVItem>> matches;
    public MainActivityViewModel() {
        repository = Repository.getInstance();
        matches = Transformations.switchMap(chosenIngredients, new Function<ArrayList<String>, LiveData<List<RVItem>>>() {
            @Override
            public LiveData<List<RVItem>> apply(ArrayList<String> input) {
                return repository.getMatches(input);
            }
        });
    }

    LiveData<List<RVItem>> getMatches() {
        return matches;
    }

    public void setChosenIngredients(ArrayList<String> chosenIngredients) {
        this.chosenIngredients.postValue(chosenIngredients);
    }

    public LiveData<ArrayList<String>> getChosenIngredients() {
        return chosenIngredients;
    }

    public void removeChosenIngredient(String ingredientName) {
        ArrayList<String> list = chosenIngredients.getValue();

        if(list==null) return;

        list.remove(ingredientName);

        chosenIngredients.postValue(list);
    }
}
