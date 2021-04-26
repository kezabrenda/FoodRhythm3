package com.example.foodrhythm3.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.foodrhythm3.Constants;
import com.example.foodrhythm3.R;
import com.google.firebase.database.DatabaseReference;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = MainActivity.class.getSimpleName();
    private DatabaseReference mSearchedRecipesReference;
    @BindView(R.id.findRecipesButton) Button mFindRecipesButton;
    @BindView(R.id.foodTypeEditText) EditText mFoodTypeEditText;
    @BindView(R.id.appNameTextView) TextView mAppNameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mFindRecipesButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == mFindRecipesButton) {
            String foodType = mFoodTypeEditText.getText().toString();
            saveRecipesToFirebase(foodType);
            //if(!(foodType).equals("")) { addToSharedPreferences(foodType); }

            Intent intent = new Intent(MainActivity.this, RecipesActivity.class);
            intent.putExtra("foodType", foodType);
            startActivity(intent);
        }
    }

    public void saveRecipesToFirebase(String foodType) {
        mSearchedRecipesReference.push().setValue(foodType);
    }

    //private void addToSharedPreferences(String foodType) {
      //  mEditor.putString(Constants.PREFERENCES_RECIPES_KEY, foodType).apply();}
}