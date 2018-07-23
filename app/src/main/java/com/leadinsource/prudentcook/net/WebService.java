package com.leadinsource.prudentcook.net;

import com.leadinsource.prudentcook.model.Ingredient;/*

example query

https://api.edamam.com/search?q=rosemary,chicken&app_id=9959fa87&app_key=044b6c7f9a7ec374c3b6105dbbdefddd

 */

import java.util.List;

public interface WebService {

    interface Callback {
        void onResult(String result);
    }

    void searchAPI(List<Ingredient> ingredients, Callback callback);
}
