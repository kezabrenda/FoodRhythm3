package com.example.foodrhythm3.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.foodrhythm3.Constants;
import com.example.foodrhythm3.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = MainActivity.class.getSimpleName();
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    @BindView(R.id.findRecipesButton) Button mFindRecipesButton;
    @BindView(R.id.foodTypeEditText) EditText mFoodTypeEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mSharedPreferences.edit();

        mFindRecipesButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == mFindRecipesButton) {
            String foodType = mFoodTypeEditText.getText().toString();
            if(!(foodType).equals("")) {
                addToSharedPreferences(foodType);
            }
            Intent intent = new Intent(MainActivity.this, RecipesActivity.class);
            startActivity(intent);
        }
    }

    private void addToSharedPreferences(String foodType) {
        mEditor.putString(Constants.PREFERENCES_RECIPES_KEY, foodType).apply();
    }
}