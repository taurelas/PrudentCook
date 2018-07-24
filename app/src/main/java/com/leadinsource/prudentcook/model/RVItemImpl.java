package com.leadinsource.prudentcook.model;

import com.leadinsource.prudentcook.mainactivity.RVItem;
import com.leadinsource.prudentcook.model.Recipe;

public class RVItemImpl implements RVItem{

    private RecipeData recipeData;
    Recipe recipe;
    String recipeName;

    public RVItemImpl(Recipe recipe) {
        this.recipe = recipe;
    }

    public RVItemImpl(String recipeName, RecipeData recipeData) {
        this.recipeName = recipeName;
        this.recipeData = recipeData;
    }

    @Override
    public String getRecipeName() {
        return recipe.getName();
    }

    @Override
    public String getMissingIngredients() {
        return recipe.getIngredients();
    }

    @Override
    public String getRecipeExcerpt() {
        return recipe.getSteps();
    }
}
