package com.leadinsource.prudentcook.model;

public class IngredientImpl implements Ingredient{


    private final String name;
    private final String nameWithQuantity;

    public IngredientImpl(String name, String nameWithQuantity) {
        this.name = name;
        this.nameWithQuantity = nameWithQuantity;
    }

    @Override
    public String getName() {
        return name;
    }
}

