package com.leadinsource.prudentcook.net;

import com.leadinsource.prudentcook.model.Recipe;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

import static org.junit.Assert.*;

public class DeserializerTest {

    String json;

    @Before
    public void setUp() throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource("testData.json");
        File file = new File(resource.getPath());
        Scanner scanner = new Scanner(file);
        json = scanner.useDelimiter("\\A").next();
        scanner.close();

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getRecipes() {
        List<Recipe> recipes = new Deserializer(json).getRecipes();

        assertTrue(recipes.size() > 0);

    }

    @Test
    public void getHits() {
        List<Recipe> recipes = new Deserializer(json).getRecipes();

        Recipe recipe = recipes.get(0);

        assertNotNull(recipe);
        assertNotNull(recipe.name);
        assertNotNull(recipe.ingredientLines);
        assertTrue(recipe.ingredientLines.size() >0);

        System.out.print(recipe.name);

    }

}