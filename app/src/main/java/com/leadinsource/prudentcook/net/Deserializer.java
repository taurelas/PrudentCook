package com.leadinsource.prudentcook.net;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.leadinsource.prudentcook.model.Recipe;

import java.util.ArrayList;
import java.util.List;

public class Deserializer {
    String json;
    private final Gson gson;

    public Deserializer(String json) {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        gson = builder.create();
        this.json = json;
    }

    public List<Recipe> getRecipes() {
        List<Recipe> result = new ArrayList<>();
        Response response = gson.fromJson(json, Response.class);
        if(response!=null && response.hits!=null && response.hits.size()>0) {
            for(Hit hit : response.hits) {
                if(hit.recipe!=null) {
                    result.add(hit.recipe);
                }
            }
        }

        return result;
    }
}
