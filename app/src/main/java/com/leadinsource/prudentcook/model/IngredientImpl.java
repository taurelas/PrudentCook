package com.leadinsource.prudentcook.model;

public class IngredientImpl implements Ingredient {

    private final String name;
    private IngredientData data;

    public IngredientImpl(String name, IngredientData data) {
        this.name = name;
        this.data = data;
    }

    public IngredientData getData() {
        return data;
    }

    @Override
    public String getName() {
        return name;
    }
}

