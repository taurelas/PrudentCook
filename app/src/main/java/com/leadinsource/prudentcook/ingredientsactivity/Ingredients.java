package com.leadinsource.prudentcook.ingredientsactivity;

import android.support.annotation.NonNull;

import com.leadinsource.prudentcook.model.Ingredient;

import java.util.Set;

public class Ingredients {

    private static final String[] EMPTY_ARRAY = {};

    private Set<Ingredient> set;


    public Ingredients(Set<Ingredient> ingredients) {
        set = ingredients;
    }

    @NonNull
    public String[] getIngredients() {

        if (set == null) {
            return EMPTY_ARRAY;
        }

        Ingredient[] ingredientArray = set.toArray(new Ingredient[set.size()]);

        String[] array = new String[set.size()];

        for (int i = 0; i < set.size(); i++) {
            array[i] = ingredientArray[i].getName();
        }

        return array;
    }
}
