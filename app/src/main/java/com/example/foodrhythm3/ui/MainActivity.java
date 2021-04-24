package com.example.foodrhythm3.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.foodrhythm3.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.findRecipesButton) Button mFindRecipesButton;
    @BindView(R.id.foodTypeEditText) EditText mFoodTypeEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mFindRecipesButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String foodType = mFoodTypeEditText.getText().toString();
        Intent intent = new Intent(MainActivity.this, RecipesActivity.class);
        intent.putExtra("foodType", foodType);
        startActivity(intent);
    }
}