package com.leadinsource.prudentcook.mainactivity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.leadinsource.prudentcook.R;
import com.leadinsource.prudentcook.ingredientsactivity.IngredientsActivity;
import com.leadinsource.prudentcook.recipeactivity.RecipeActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import timber.log.Timber;

import static com.leadinsource.prudentcook.recipeactivity.RecipeActivity.EXTRA_INGREDIENTS;
import static com.leadinsource.prudentcook.recipeactivity.RecipeActivity.EXTRA_RECIPE_NAME;

public class MainActivity extends AppCompatActivity implements RecyclerViewAdapter.OnClickListener {

    public static final int INGREDIENT_REQUEST = 528;

    public static final String EXTRA_STEPS = "EXTRA_STEPS";
    public static final String EXTRA_CHOSEN_INGREDIENTS = "EXTRA_CHOSEN_INGREDIENTS";

    private FirebaseAnalytics firebaseAnalytics;
    private FlowLayout choiceLayout;
    private RecyclerView recyclerView;
    private MainActivityViewModel model;

    private FlowLayout.LayoutParams flowLP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //...
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        firebaseAnalytics = FirebaseAnalytics.getInstance(this);
        model = ViewModelProviders.of(this).get(MainActivityViewModel.class);

        model.getMatches().observe(this, new Observer<List<RVItem>>() {
            @Override
            public void onChanged(@Nullable List<RVItem> items) {
                if (items!=null) {
                    recyclerView.setAdapter(new RecyclerViewAdapter(items, MainActivity.this));
                }
            }
        });

        choiceLayout = findViewById(R.id.choices);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, IngredientsActivity.class);

                Bundle bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this, null)
                        .toBundle();

                startActivityForResult(intent, INGREDIENT_REQUEST, bundle);
            }
        });

        model.getChosenIngredients().observe(this, new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(@Nullable ArrayList<String> ingredientNames) {
                if(ingredientNames==null) return;

                choiceLayout.removeAllViews();
                flowLP = new FlowLayout.LayoutParams(5,5);

                for(final String ingredientName : ingredientNames) {
                    IngredientView choice = new IngredientView(MainActivity.this);
                    choice.setText(ingredientName);
                    Timber.d("Processing %s!", ingredientName);
                    choice.setClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Timber.d("Onclick!");
                            model.removeChosenIngredient(ingredientName);

                        }
                    });
                    choiceLayout.addView(choice, flowLP);
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_submit) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(RVItem item, View[] view) {
        Intent intent = new Intent(MainActivity.this, RecipeActivity.class);
        intent.putExtra(EXTRA_RECIPE_NAME, item.getRecipeName());
        intent.putExtra(EXTRA_INGREDIENTS, item.getMissingIngredients());
        intent.putExtra(EXTRA_STEPS, item.getRecipeExcerpt());
        Bundle bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this,
                        Pair.create(view[0], view[0].getTransitionName()),
                        Pair.create(view[1], view[1].getTransitionName()))
                .toBundle();

        startActivity(intent, bundle);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(data==null) return;

        if(requestCode==INGREDIENT_REQUEST) {
            String[] chosenIngredients = data.getStringArrayExtra(EXTRA_CHOSEN_INGREDIENTS);

            if(chosenIngredients!=null && chosenIngredients.length>0) {

               sendItemsToAnalytics(chosenIngredients);

                model.setChosenIngredients(new ArrayList<>(Arrays.asList(chosenIngredients)));
            } else {
                Timber.d("No ingredients chosen");
            }

        }
    }

    void sendItemsToAnalytics(String[] items) {
        for(String item : items) {
            Bundle bundle = new Bundle();
            bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, item);
            firebaseAnalytics.logEvent(FirebaseAnalytics.Event.VIEW_SEARCH_RESULTS, bundle);
        }
    }
}
