package com.leadinsource.prudentcook.ingredientsactivity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leadinsource.prudentcook.R;

import java.util.List;

import timber.log.Timber;

/**
 * A placeholder fragment containing a simple view.
 */
public class IngredientsActivityFragment extends Fragment {

    private static final String INGREDIENTS_RECYCLER_VIEW_STATE = "INGREDIENTS_RECYCLER_VIEW_STATE";
    private IngredientsViewModel viewModel;
    private RecyclerView recyclerView;
    private Parcelable recyclerViewState;

    public IngredientsActivityFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ingredients, container, false);

        recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
        Timber.d("528491 changing layout in a normal way");
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModel = ViewModelProviders.of(getActivity()).get(IngredientsViewModel.class);

        viewModel.getChosenIngredients().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(@Nullable List<String> data) {
                Timber.d("528491 data is changing");
                if (data!=null) {
                    recyclerView.setAdapter(new RecyclerViewAdapter(data));
                    if(recyclerViewState!=null && data.size()>0) {
                        Timber.d("528491 restoring instance state when data changed %s", recyclerViewState);
                        recyclerView.getLayoutManager().onRestoreInstanceState(recyclerViewState);
                        recyclerViewState=null;
                    }
                }
            }
        });

        if(savedInstanceState != null) {
            Timber.d("528491 obtaining data from savedInstanceState");
            recyclerViewState = savedInstanceState.getParcelable(INGREDIENTS_RECYCLER_VIEW_STATE);
            Timber.d("528491 Restored %s",savedInstanceState.getString("TEST", "Nothing"));
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Timber.d("528491 saving instance state");
        outState.putParcelable(INGREDIENTS_RECYCLER_VIEW_STATE, recyclerView.getLayoutManager().onSaveInstanceState());
        Timber.d("528491 Saving inception");
        outState.putString("TEST", "Inception");
    }

}
