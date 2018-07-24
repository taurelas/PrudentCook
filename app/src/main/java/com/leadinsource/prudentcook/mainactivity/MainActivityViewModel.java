package com.leadinsource.prudentcook.mainactivity;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.leadinsource.prudentcook.data.Repository;
import com.leadinsource.prudentcook.model.RVItemImpl;
import com.leadinsource.prudentcook.model.Recipe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivityViewModel extends ViewModel {

    private final Repository repository;
    private MutableLiveData<ArrayList<String>> chosenIngredients = new MutableLiveData<>();

    public MainActivityViewModel() {
        repository = new Repository();
    }

    MutableLiveData<List<RVItem>> matches;

    LiveData<List<RVItem>> getMatches() {
        if(matches==null) {
            matches = new MutableLiveData<>();
        }

        return matches;
    }

    public void testData() {
        List<RVItem> items = new ArrayList<>();
        Recipe recipe = new Recipe("Spaghetti", "pasta, sauce", "Cook spaghetti, cook everything");
        items.add(new RVItemImpl(recipe));
        recipe = new Recipe("Broccoli and thyme", "thyme, broccoli, pasta, rice", "Cook rice & pasta\nRinse the spoon, add ketchup\nMix everything");
        items.add(new RVItemImpl(recipe));
        recipe = new Recipe("Spices", "salt, pepper, curry, THC, grated cheese", "Mix everything\nUnmix everything\nSeparate the spices");
        items.add(new RVItemImpl(recipe));
        matches.postValue(items);
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
