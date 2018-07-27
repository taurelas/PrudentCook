package com.leadinsource.prudentcook.model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RecipeDataTest {

    /*private RecipeData recipeDataUnderTest;

    @Before
    public void setUp() {
        recipeDataUnderTest = new RecipeData();
        HashMap<String, IngredientData> ingredientDataHashMap = new HashMap<>();

        ingredientDataHashMap.put("spaghetti", createIngredientData("g", 300, true));
        ingredientDataHashMap.put("olive oil", createIngredientData("tbsp", 5, true));
        ingredientDataHashMap.put("garlic", createIngredientData("cloves", 2, true));
        ingredientDataHashMap.put("parmiggiano", createIngredientData("", 0, false));
        ingredientDataHashMap.put("fresh basil", createIngredientData("leaves", 4, false));

        recipeDataUnderTest.setIngredients(ingredientDataHashMap);

    }



    @Test
    public void matches() {
        assertFalse(recipeDataUnderTest.matches(null));
        assertFalse(recipeDataUnderTest.matches(new ArrayList<String>()));
        assertFalse(recipeDataUnderTest.matches(new ArrayList<>(Collections.singletonList("olive oil"))));
        assertFalse(recipeDataUnderTest.matches(new ArrayList<>(Arrays.asList("olive oil", "garlic"))));
        assertTrue(recipeDataUnderTest.matches(new ArrayList<>(Arrays.asList("olive oil", "garlic", "spaghetti"))));
        assertTrue(recipeDataUnderTest.matches(new ArrayList<>(Arrays.asList("garlic", "olive oil", "spaghetti"))));
        assertTrue(recipeDataUnderTest.matches(new ArrayList<>(Arrays.asList("spaghetti", "garlic", "olive oil"))));
        assertTrue(recipeDataUnderTest.matches(new ArrayList<>(Arrays.asList("olive oil", "garlic", "spaghetti", "parmiggiano"))));
        assertTrue(recipeDataUnderTest.matches(new ArrayList<>(Arrays.asList("olive oil", "garlic", "spaghetti", "cheese"))));
        assertTrue(recipeDataUnderTest.matches(new ArrayList<>(Arrays.asList("olive oil", "garlic", "spaghetti", "cheese", "bacon"))));
        assertFalse(recipeDataUnderTest.matches(new ArrayList<>(Arrays.asList("olive oil", "spaghetti"))));
        assertFalse(recipeDataUnderTest.matches(new ArrayList<>(Collections.singletonList("cheese"))));
        assertTrue(recipeDataUnderTest.matches(new ArrayList<>(Arrays.asList("olive oil", "spaghetti", "garlic", "olive oil"))));
        assertFalse(recipeDataUnderTest.matches(new ArrayList<>(Arrays.asList("olive oil", "olive oil", "olive oil"))));
        assertFalse(recipeDataUnderTest.matches(new ArrayList<>(Arrays.asList("", "", ""))));

    }

    private IngredientData createIngredientData(String unit, long amount, boolean required) {
        IngredientData ingredientData = new IngredientData();
        ingredientData.setUnit(unit);
        ingredientData.setAmount(amount);
        ingredientData.setRequired(required);

        return ingredientData;
    }*/
}