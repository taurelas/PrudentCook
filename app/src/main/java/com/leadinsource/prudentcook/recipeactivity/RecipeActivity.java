package com.leadinsource.prudentcook.recipeactivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.leadinsource.prudentcook.R;
import com.leadinsource.prudentcook.mainactivity.MainActivity;

/**
 * App photos unless otherwise stated are CC0 licensed (no attribution required)
 */
public class RecipeActivity extends AppCompatActivity {

    private AdView adView;
    private AdRequest adRequest;
    private String recipeName;
    private String ingredients;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        Intent intent = getIntent();

        if(intent==null) {
            finish();
            return;
        }

        recipeName = intent.getStringExtra(MainActivity.EXTRA_RECIPE_NAME);
        ingredients = intent.getStringExtra(MainActivity.EXTRA_INGREDIENTS);
        String steps = intent.getStringExtra(MainActivity.EXTRA_STEPS);

        setTitle(recipeName);

        TextView tvIngredients = findViewById(R.id.tvIngredients);

        TextView tvSteps = findViewById(R.id.tvSteps);

        tvIngredients.setText(ingredients);
        tvSteps.setText(steps);
        fab = findViewById(R.id.fab);

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                MobileAds.initialize(RecipeActivity.this, "ca-app-pub-1785118724408316~9524578956");
                //...
                adView = findViewById(R.id.adView);
                adRequest = new AdRequest.Builder().build();

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                adView.loadAd(adRequest);
            }
        }.execute();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateFabButton();

    }

    public void onFabClick(View view) {
        FavoriteManager.switchRecipe(this, recipeName, ingredients);
        updateFabButton();
    }

    private void updateFabButton() {

        if(FavoriteManager.isFavorite(this, recipeName)) {
            fab.setImageResource(R.drawable.ic_favorite_white_24dp);
        } else {
            fab.setImageResource(R.drawable.ic_favorite_border_white_24dp);
        }
    }
}
