package com.example.lab4;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class RecipeDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int recipeIndex = getIntent().getIntExtra(
                MainActivity.EXTRA_RECIPE_INDEX, -1
        );

        if (getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE) {

            Intent intent = new Intent(
                    this, MainActivity.class
            );

            intent.addFlags(
                    Intent.FLAG_ACTIVITY_CLEAR_TOP |
                            Intent.FLAG_ACTIVITY_SINGLE_TOP
            );

            intent.putExtra(
                    MainActivity.EXTRA_RECIPE_INDEX,
                    recipeIndex
            );

            startActivity(intent);
            finish();
            return;
        }

        setContentView(R.layout.activity_recipe_detail);

        if (savedInstanceState == null) {

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(
                            R.id.detail_container,
                            RecipeDetailFragment.newInstance(recipeIndex)
                    )
                    .commit();
        }
    }
}
