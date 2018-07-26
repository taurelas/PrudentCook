package com.leadinsource.prudentcook.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.Map;

public class FavoriteManager {

    public static void saveRecipe(Context context, String recipeName, String ingredients) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(recipeName, ingredients);
        editor.apply();
    }

    public static void removeRecipe(Context context, String recipeName) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(recipeName);
        editor.apply();
    }

    public static void switchRecipe(Context context, String recipeName, String ingredients) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if(sharedPreferences.getString(recipeName, "").equals("")) {
            editor.putString(recipeName, ingredients);
        } else {
            editor.remove(recipeName);
        }
        editor.apply();
    }

    public static boolean isFavorite(Context context, String recipeName) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        // returns false if default value (string not found), true otherwise
        return !sharedPreferences.getString(recipeName, "").equals("");

    }

    public static Map<String, ?> getFavorites(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getAll();
    }

    public static String getIngredients(Context context, String recipeName) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(recipeName, "");
    }
}
