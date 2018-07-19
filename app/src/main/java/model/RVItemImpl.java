package model;

import com.leadinsource.prudentcook.mainactivity.RVItem;

public class RVItemImpl implements RVItem{

    Recipe recipe;

    public RVItemImpl(Recipe recipe) {
        this.recipe = recipe;
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
