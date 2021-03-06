package com.leadinsource.prudentcook.data;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.leadinsource.prudentcook.R;
import com.leadinsource.prudentcook.model.RecipeData;

import java.util.HashMap;

import timber.log.Timber;

class RecipeDatabase {

    private static final String TOP_LEVEL_KEY = "recipes";

    private HashMap<String, RecipeData> recipes;
    private RepositoryCallback callback;

    interface RepositoryCallback {
        void onRecipeAdded(HashMap<String, RecipeData> recipes);
    }

    RecipeDatabase(RepositoryCallback callback) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference().child(TOP_LEVEL_KEY);
        databaseReference.addChildEventListener(childEventListener);
        this.callback = callback;
    }


    private ChildEventListener childEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            if (recipes == null) {
                recipes = new HashMap<>();
            }

            recipes.put(dataSnapshot.getKey(), dataSnapshot.getValue(RecipeData.class));

            callback.onRecipeAdded(recipes);
        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Timber.d("Database error: %s", databaseError);
        }
    };


}
