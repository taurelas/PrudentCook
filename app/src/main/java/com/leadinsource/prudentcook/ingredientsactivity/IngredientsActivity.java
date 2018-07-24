package com.leadinsource.prudentcook.ingredientsactivity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.leadinsource.prudentcook.R;
import com.leadinsource.prudentcook.mainactivity.MainActivity;

import java.util.List;

public class IngredientsActivity extends AppCompatActivity {

    private IngredientsViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients);
        //getActionBar().setDisplayHomeAsUpEnabled(true);
        viewModel = ViewModelProviders.of(this).get(IngredientsViewModel.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_ingredients, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intentWithIngredients = new Intent();

        List<String> chosenIngredients = viewModel.getChosenIngredients().getValue();
        if(chosenIngredients!=null) {
            intentWithIngredients.putExtra(MainActivity.EXTRA_INGREDIENTS, chosenIngredients.toArray(new String[chosenIngredients.size()]));
        }

        setResult(RESULT_OK, intentWithIngredients);

        finish();
        return true;
    }
}
