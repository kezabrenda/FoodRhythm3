package com.example.foodrhythm3.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.foodrhythm3.Constants;
import com.example.foodrhythm3.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = MainActivity.class.getSimpleName();

    private ValueEventListener mSearchedRecipesReferenceListener;
    private DatabaseReference mSearchedRecipesReference;
    @BindView(R.id.findRecipesButton) Button mFindRecipesButton;
    @BindView(R.id.appNameTextView) TextView mAppNameTextView;
    @BindView(R.id.savedRecipesButton) Button mSavedRecipesButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mSearchedRecipesReference = FirebaseDatabase
                .getInstance()
                .getReference()
                .child(Constants.FIREBASE_CHILD_SEARCHED_RECIPES);
        mSearchedRecipesReferenceListener = mSearchedRecipesReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot recipeSnapshot : dataSnapshot.getChildren()){
                    String foodType = recipeSnapshot.getValue().toString();
                    Log.d("Recipes updated", "recipe: " + foodType);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mFindRecipesButton.setOnClickListener(this);
        mSavedRecipesButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == mFindRecipesButton) {
            Intent intent = new Intent(MainActivity.this, RecipesActivity.class);
            startActivity(intent);
        }
        if (v == mSavedRecipesButton) {
            Intent intent = new Intent(MainActivity.this, SavedRecipesListActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSearchedRecipesReference.removeEventListener(mSearchedRecipesReferenceListener);
    }
}