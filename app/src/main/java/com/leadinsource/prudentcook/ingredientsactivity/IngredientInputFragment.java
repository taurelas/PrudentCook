package com.leadinsource.prudentcook.ingredientsactivity;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;


import com.leadinsource.prudentcook.R;
import com.leadinsource.prudentcook.model.Ingredient;
import com.leadinsource.prudentcook.model.IngredientImpl;

import java.util.Set;

import timber.log.Timber;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IngredientInputFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IngredientInputFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String[] INGREDIENTS = new String[]{
            "Basil", "Black Pepper", "Cardamom", "Chicken", "Eggs", "Flour", "Salt", "Zucchini"
    };
    private Set<Ingredient> ingredients;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private AutoCompleteTextView actv;


    public IngredientInputFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment IngredientInputFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static IngredientInputFragment newInstance(String param1, String param2) {
        IngredientInputFragment fragment = new IngredientInputFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        Timber.d("Creating ingredients input");
        View rootView = inflater.inflate(R.layout.ingredients_input, container, false);
        actv = rootView.findViewById(R.id.autoCompleteTextView);
        ACTVSetup(INGREDIENTS);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        IngredientsViewModel viewModel = ViewModelProviders.of(getActivity()).get(IngredientsViewModel.class);

        viewModel.getIngredients().observe(this, new Observer<Set<Ingredient>>() {
            @Override
            public void onChanged(@Nullable Set<Ingredient> ingredients) {

                IngredientInputFragment.this.ingredients = ingredients;

                Ingredient[] ingredientArray = ingredients.toArray(new Ingredient[ingredients.size()]);

                String[] array = new String[ingredients.size()];

                for(int i=0;i<ingredients.size();i++) {
                    array[i] = ingredientArray[i].getName();
                }

                ACTVSetup(array);
            }
        });
    }

    public void ACTVSetup(String[] ingredients) {
        if (getContext() == null) {
            return;
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, ingredients);
        actv.setAdapter(adapter);
    }
}
