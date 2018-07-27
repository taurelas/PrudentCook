package com.leadinsource.prudentcook.ingredientsactivity;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.leadinsource.prudentcook.R;

import java.util.List;

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

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private AutoCompleteTextView actv;
    private ArrayAdapter<String> adapter;
    private IngredientsViewModel viewModel;


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

    // we set up the view. if we click on an item, it is added to viewmodel
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.ingredients_input, container, false);
        actv = rootView.findViewById(R.id.autoCompleteTextView);
        actv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                actv.setText("");
                viewModel.addChosenIngredient((String) parent.getItemAtPosition(position));
            }
        });
        actv.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if((event.getAction()==KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_DPAD_CENTER)) {
                    viewModel.setAddingComplete();
                    return true;
                }
                return false;
            }
        });
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModel = ViewModelProviders.of(getActivity()).get(IngredientsViewModel.class);

        viewModel.getAvailableIngredients().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(@Nullable List<String> ingredients) {
                if(ingredients==null) return;

                actvDataSetup(ingredients.toArray(new String[ingredients.size()]));

            }
        });
    }

    public void actvDataSetup(String[] ingredients) {
        if (getContext() == null) {
            return;
        }
        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, ingredients);
        actv.setAdapter(adapter);
    }
}
