package com.leadinsource.prudentcook.recipeactivity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    private AdView adView;
    private AdRequest adRequest;
    private String recipeName;
    private String ingredients;
    private FloatingActionButton fab;

    public static final String EXTRA_RECIPE_NAME = "EXTRA_RECIPE_NAME";
    public static final String EXTRA_INGREDIENTS = "EXTRA_INGREDIENTS";
    public static final String WIDGET_ACTION = "WIDGET_DATA";
    private TextView tvIngredients;
    private TextView tvSteps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        Timber.d("SetcontentView 528491");
        RecipeViewModel viewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);
        Timber.d("Got with the model 528491");
        tvIngredients = findViewById(R.id.tvIngredients);
        tvSteps = findViewById(R.id.tvSteps);
        Intent intent = getIntent();

        if(intent==null) {
            finish();
            return;
        }

        viewModel.getRecipeData().observe(this, new Observer<RecipeData>() {
            @Override
            public void onChanged(@Nullable RecipeData recipeData) {
                if(recipeData!=null) {
                    ingredients = recipeData.getIngredientsString();
                    tvIngredients.setText(ingredients);
                    tvSteps.setText(recipeData.getSteps());
                }
            }
        });

        if(intent.getAction()!=null && intent.getAction().equals(WIDGET_ACTION)) {
            recipeName = intent.getStringExtra(EXTRA_RECIPE_NAME);
            viewModel.setRecipe(recipeName);
        //    ingredients = intent.getStringExtra(EXTRA_INGREDIENTS);

//            Timber.d("Got: %s / %s", recipeName, ingredients);
        } else {

            recipeName = intent.getStringExtra(EXTRA_RECIPE_NAME);

            viewModel.setRecipe(recipeName);

        }
        setTitle(recipeName);

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
        Timber.d("onResume in the midst 528491");
        updateFabButton();
        Timber.d("onResume completed 528491");

    }

    public void onFabClick(View view) {
        FavoriteManager.switchRecipe(this, recipeName, ingredients);
        updateFabButton();
    }

    private void updateFabButton() {
        Timber.d("updateFabButton 528491");
        if(FavoriteManager.isFavorite(this, recipeName)) {
            fab.setImageResource(R.drawable.ic_favorite_white_24dp);
        } else {
            fab.setImageResource(R.drawable.ic_favorite_border_white_24dp);
        }
        Timber.d("updateFabButton finished 528491" );
    }
}
