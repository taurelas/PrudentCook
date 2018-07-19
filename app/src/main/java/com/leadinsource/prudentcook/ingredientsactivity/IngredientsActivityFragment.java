package com.leadinsource.prudentcook.ingredientsactivity;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leadinsource.prudentcook.R;

import timber.log.Timber;

/**
 * A placeholder fragment containing a simple view.
 */
public class IngredientsActivityFragment extends Fragment {

    public IngredientsActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Timber.d("Creating ingredients");
        return inflater.inflate(R.layout.fragment_ingredients, container, false);
    }
}
