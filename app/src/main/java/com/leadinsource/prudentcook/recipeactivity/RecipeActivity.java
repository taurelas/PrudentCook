package com.leadinsource.prudentcook.recipeactivity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.leadinsource.prudentcook.R;
import com.leadinsource.prudentcook.data.FavoriteManager;
import com.leadinsource.prudentcook.data.Repository;
import com.leadinsource.prudentcook.mainactivity.MainActivity;
import com.leadinsource.prudentcook.model.RecipeData;

import timber.log.Timber;

/**
 * App photos unless otherwise stated are CC0 licensed (no attribution required)
 */
public class RecipeActivity extends AppCompatActivity {

    public static final String EXTRA_RECIPE_NAME = "EXTRA_RECIPE_NAME";
    public static final String EXTRA_INGREDIENTS = "EXTRA_INGREDIENTS";
    public static final String WIDGET_ACTION = "WIDGET_DATA";
    private static final String SAVED_RECIPE_NAME = "SAVED_RECIPE_NAME";
    private AdView adView;
    private AdRequest adRequest;
    private String recipeName;
    private String ingredients;
    private FloatingActionButton fab;
    private TextView tvIngredients;
    private TextView tvSteps;
    private CollapsingToolbarLayout toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        RecipeViewModel viewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);

        toolbar = findViewById(R.id.collapsing_toolbar_layout);

        tvIngredients = findViewById(R.id.tvIngredients);
        tvSteps = findViewById(R.id.tvSteps);
        Intent intent = getIntent();

        if(savedInstanceState==null) {
            Timber.d("528491 SavedInstanceState is null");
            if (intent == null) {
                Timber.d("528491 Intent is null, finishing activity");
                finish();
                return;
            } else {
                recipeName = intent.getStringExtra(EXTRA_RECIPE_NAME);
                if (intent.getAction() != null && intent.getAction().equals(WIDGET_ACTION)) {
                    Timber.d("528491 Got: %s from widget", recipeName);
                } else {
                    Timber.d("528491 Got: %s from another Activity", recipeName);
                }
            }
        } else {
            Timber.d("528491 SavedInstanceState is not null");
            Timber.d("528491 Got: %s from savedInstanceState", recipeName);
            recipeName = savedInstanceState.getString(SAVED_RECIPE_NAME);
        }

        if (TextUtils.isEmpty(recipeName)) {
            Timber.d("528491 Wrong recipe name");
            finish();
        }

        viewModel.setRecipe(recipeName);
        Timber.d("528491 passed to viewmodel got %s", recipeName);
        setTitle(recipeName);
        Timber.d("528491 title set %s", getTitle());
        viewModel.getRecipeData().observe(this, new Observer<RecipeData>() {
            @Override
            public void onChanged(@Nullable RecipeData recipeData) {
                Timber.d("528491 recipeData is changed to %s", recipeData);
                if (recipeData != null) {
                    ingredients = recipeData.getIngredientsString();
                    tvIngredients.setText(ingredients);
                    tvSteps.setText(recipeData.getSteps());
                    toolbar.setTitle(recipeName);
                }
            }
        });

        fab = findViewById(R.id.fab);

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                MobileAds.initialize(RecipeActivity.this, getString(R.string.admob_id));
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
        if (FavoriteManager.isFavorite(this, recipeName)) {
            fab.setImageResource(R.drawable.ic_favorite_white_24dp);
        } else {
            fab.setImageResource(R.drawable.ic_favorite_border_white_24dp);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(SAVED_RECIPE_NAME, recipeName);
    }
}
