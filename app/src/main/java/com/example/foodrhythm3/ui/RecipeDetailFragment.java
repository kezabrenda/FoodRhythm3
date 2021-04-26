package com.example.foodrhythm3.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodrhythm3.Constants;
import com.example.foodrhythm3.R;
import com.example.foodrhythm3.models.Recipe;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailFragment extends Fragment implements View.OnClickListener{
        @BindView(R.id.imageUrlImageView) ImageView mImageLabel;
        @BindView(R.id.recipeNameTextView) TextView mNameLabel;
        @BindView(R.id.socialRankTextView) TextView mSocialRankLabel;
        @BindView(R.id.sourceUrlTextView) TextView mSourceUrlLabel;
        @BindView(R.id.saveRecipeButton) TextView mSaveRecipeButton;
        private Recipe mRecipe;

    public RecipeDetailFragment() {
        }

        public static RecipeDetailFragment newInstance(Recipe recipe) {
            RecipeDetailFragment recipeDetailFragment = new RecipeDetailFragment();
            Bundle args = new Bundle();
            args.putParcelable("recipe", Parcels.wrap(recipe));
            recipeDetailFragment.setArguments(args);
            return recipeDetailFragment;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            assert getArguments() != null;
            mRecipe = Parcels.unwrap(getArguments().getParcelable("recipe"));
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view =  inflater.inflate(R.layout.fragment_recipe_detail, container, false);
            ButterKnife.bind(this, view);
            Picasso.get().load(mRecipe.getImageUrl()).into(mImageLabel);

            List<String> categories = new ArrayList<>();

            mNameLabel.setText(mRecipe.getTitle());
            mSourceUrlLabel.setText(android.text.TextUtils.join(", ", categories));
            mSocialRankLabel.setText(Double.toString(mRecipe.getSocialRank()) + "/100");

            mSourceUrlLabel.setOnClickListener(this);
            mSaveRecipeButton.setOnClickListener(this);
            return view;
        }
        @Override
        public void onClick(View v) {
        if (v == mSourceUrlLabel) {
            Intent webIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse(mRecipe.getSourceUrl()));
            startActivity(webIntent);
        }
        if (v == mSaveRecipeButton) {
            DatabaseReference restaurantRef = FirebaseDatabase
                    .getInstance()
                    .getReference(Constants.FIREBASE_CHILD_RECIPES);
            restaurantRef.push().setValue(mRecipe);
            Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();
            }
    }
}