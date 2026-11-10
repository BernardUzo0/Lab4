package com.example.lab4;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class MainActivity extends AppCompatActivity
        implements RecipeListFragment.OnRecipeSelectedListener {

    public static final String EXTRA_RECIPE_INDEX = "recipe_index";

    private static final String STATE_SELECTED = "selected_recipe";
    private static final String STATE_LANDSCAPE = "was_landscape";

    private int selectedRecipe = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        boolean wasLandscape = false;

        if (savedInstanceState != null) {

            selectedRecipe = savedInstanceState.getInt(
                    STATE_SELECTED, -1
            );

            wasLandscape = savedInstanceState.getBoolean(
                    STATE_LANDSCAPE, false
            );
        }

        FragmentManager manager =
                getSupportFragmentManager();

        // Display the recipe names Fragment.
        if (manager.findFragmentById(R.id.list_container) == null) {

            manager.beginTransaction()
                    .replace(R.id.list_container,
                            new RecipeListFragment())
                    .commitNow();
        }

        if (isLandscape()) {

            // Show both Fragments in the same activity.
            if (manager.findFragmentById(R.id.detail_container) == null) {

                manager.beginTransaction()
                        .replace(R.id.detail_container,
                                RecipeDetailFragment.newInstance(-1))
                        .commitNow();
            }

            int requestedRecipe = getIntent().getIntExtra(
                    EXTRA_RECIPE_INDEX, -1
            );

            if (requestedRecipe >= 0) {

                showRecipeInLandscape(requestedRecipe);

                getIntent().removeExtra(EXTRA_RECIPE_INDEX);
            }

        } else {

            // Clear previous landscape transactions.
            manager.popBackStackImmediate(
                    null,
                    FragmentManager.POP_BACK_STACK_INCLUSIVE
            );

            Fragment oldDetails =
                    manager.findFragmentById(R.id.detail_container);

            if (oldDetails != null) {

                manager.beginTransaction()
                        .remove(oldDetails)
                        .commitNow();
            }

            if (wasLandscape && selectedRecipe >= 0) {

                Intent intent = new Intent(
                        this, RecipeDetailActivity.class
                );

                intent.putExtra(
                        EXTRA_RECIPE_INDEX, selectedRecipe
                );

                startActivity(intent);
            }
        }
    }

    @Override
    public void onRecipeSelected(int recipeIndex) {

        selectedRecipe = recipeIndex;

        if (isLandscape()) {

            showRecipeInLandscape(recipeIndex);

        } else {

            Intent intent = new Intent(
                    this, RecipeDetailActivity.class
            );

            intent.putExtra(
                    EXTRA_RECIPE_INDEX, recipeIndex
            );

            startActivity(intent);
        }
    }

    private void showRecipeInLandscape(int recipeIndex) {

        selectedRecipe = recipeIndex;

        RecipeDetailFragment detailFragment =
                RecipeDetailFragment.newInstance(recipeIndex);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.detail_container, detailFragment)
                .addToBackStack(null)
                .commit();
    }

    private boolean isLandscape() {

        return getResources()
                .getConfiguration()
                .orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        setIntent(intent);

        int recipeIndex = intent.getIntExtra(
                EXTRA_RECIPE_INDEX, -1
        );

        if (recipeIndex >= 0 && isLandscape()) {

            showRecipeInLandscape(recipeIndex);

            intent.removeExtra(EXTRA_RECIPE_INDEX);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {

        outState.putInt(STATE_SELECTED, selectedRecipe);

        outState.putBoolean(
                STATE_LANDSCAPE, isLandscape()
        );

        super.onSaveInstanceState(outState);
    }
}
