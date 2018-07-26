package com.leadinsource.prudentcook.model;

import com.leadinsource.prudentcook.mainactivity.RVItem;
import com.leadinsource.prudentcook.model.Recipe;

public class RVItemImpl implements RVItem{

    private RecipeData recipeData;
    private String recipeName;

    public RVItemImpl(String recipeName, RecipeData recipeData) {
        this.recipeName = recipeName;
        this.recipeData = recipeData;
    }

    @Override
    public String getRecipeName() {
        return recipeName;
    }

    @Override
    public String getMissingIngredients() {
        if(recipeData==null) return "";

        return recipeData.getIngredientsString();
    }

    @Override
    public String getRecipeExcerpt() {
        if(recipeData==null) return "";

        return recipeData.getSteps();
    }
}
