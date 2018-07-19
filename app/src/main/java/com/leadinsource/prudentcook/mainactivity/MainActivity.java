package com.leadinsource.prudentcook.mainactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.leadinsource.prudentcook.R;
import com.leadinsource.prudentcook.ingredientsactivity.IngredientsActivity;

import java.util.ArrayList;
import java.util.List;

import model.RVItemImpl;
import model.Recipe;

public class MainActivity extends AppCompatActivity implements RecyclerViewAdapter.OnClickListener {

    private static final int INGREDIENT_REQUEST = 528;
    private FirebaseAnalytics firebaseAnalytics;
    private FlowLayout choiceLayout;
    private int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //...
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        choiceLayout = findViewById(R.id.choices);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Floating Action Button");
                firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

                FlowLayout.LayoutParams flowLP = new FlowLayout.LayoutParams(5,5);
                IngredientView choice = new IngredientView(MainActivity.this);
                choice.setText("Cucumber no "+(++counter));
                choice.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });

                choiceLayout.addView(choice, flowLP);
                Intent intent = new Intent(MainActivity.this, IngredientsActivity.class);
                startActivityForResult(intent, INGREDIENT_REQUEST);

            }
        });

        firebaseAnalytics = FirebaseAnalytics.getInstance(this);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //test
        List<RVItem> items = new ArrayList<>();
        Recipe recipe = new Recipe("Spaghetti", "pasta, sauce", "Cook spaghetti, cook everything");
        items.add(new RVItemImpl(recipe));
        recipe = new Recipe("Brocolli and thyme", "thyme, brocolli, pasta, rice", "Cook rice & pasta\nRinse the spoon, add ketchup\nMix everything");
        items.add(new RVItemImpl(recipe));
        recipe = new Recipe("Spices", "salt, pepper, curry, THC, grated chees", "Mix everything\nUnmix everything\nSeparate the spices");
        items.add(new RVItemImpl(recipe));
        recyclerView.setAdapter(new RecyclerViewAdapter(items, this));
        // end of test
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(RVItem item) {
        Toast.makeText(this, "Clicked "+item.getRecipeName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==INGREDIENT_REQUEST) {
            Toast.makeText(this, "Result", Toast.LENGTH_SHORT).show();
        }
    }
}
