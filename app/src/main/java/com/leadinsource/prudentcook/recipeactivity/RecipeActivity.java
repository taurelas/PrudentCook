package com.leadinsource.prudentcook.recipeactivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.leadinsource.prudentcook.R;
import com.leadinsource.prudentcook.mainactivity.MainActivity;

public class RecipeActivity extends AppCompatActivity {

    private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        Intent intent = getIntent();

        if(intent==null) {
            finish();
            return;
        }

        String recipeName = intent.getStringExtra(MainActivity.EXTRA_RECIPE_NAME);
        String ingredients = intent.getStringExtra(MainActivity.EXTRA_INGREDIENTS);
        String steps = intent.getStringExtra(MainActivity.EXTRA_STEPS);

        setTitle(recipeName);

        TextView tvIngredients = findViewById(R.id.tvIngredients);

        TextView tvSteps = findViewById(R.id.tvSteps);

        tvIngredients.setText(ingredients);
        tvSteps.setText(steps);


        MobileAds.initialize(this, "ca-app-pub-1785118724408316~9524578956");
        //...
        adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }
}
