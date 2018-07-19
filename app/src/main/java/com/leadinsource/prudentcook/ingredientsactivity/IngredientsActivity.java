package com.leadinsource.prudentcook.ingredientsactivity;

import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;

import com.leadinsource.prudentcook.R;

public class IngredientsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients);
        //getActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
