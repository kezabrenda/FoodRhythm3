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
//    private SharedPreferences mSharedPreferences;
//    private SharedPreferences.Editor mEditor;

    private ValueEventListener mSearchedRecipesReferenceListener;
    private DatabaseReference mSearchedRecipesReference;
    @BindView(R.id.findRecipesButton) Button mFindRecipesButton;
    @BindView(R.id.foodTypeEditText) EditText mFoodTypeEditText;
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
                for(DataSnapshot locationSnapshot : dataSnapshot.getChildren()){
                    String location = locationSnapshot.getValue().toString();
                    Log.d("Locations updated", "location: " + location);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
//        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
//        mEditor = mSharedPreferences.edit();
        mFindRecipesButton.setOnClickListener(this);
        mSavedRecipesButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == mFindRecipesButton) {
            String foodType = mFoodTypeEditText.getText().toString();
            saveRecipeToFirebase(foodType);

            Intent intent = new Intent(MainActivity.this, RecipesActivity.class);
            intent.putExtra("foodType", foodType);
            startActivity(intent);
        }
        if (v == mSavedRecipesButton) {
            Intent intent = new Intent(MainActivity.this, SavedRecipesListActivity.class);
            startActivity(intent);
        }
    }
    public void saveRecipeToFirebase(String location) {
        mSearchedRecipesReference.push().setValue(location);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSearchedRecipesReference.removeEventListener(mSearchedRecipesReferenceListener);
    }
}