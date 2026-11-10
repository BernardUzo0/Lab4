package com.example.lab4;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.List;

public class RecipeDetailFragment extends Fragment {

    private static final String ARG_RECIPE_INDEX = "recipe_index";

    public RecipeDetailFragment() {
        // Required empty constructor
    }

    public static RecipeDetailFragment newInstance(int recipeIndex) {

        RecipeDetailFragment fragment =
                new RecipeDetailFragment();

        Bundle arguments = new Bundle();
        arguments.putInt(ARG_RECIPE_INDEX, recipeIndex);

        fragment.setArguments(arguments);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(
                R.layout.fragment_recipe_detail,
                container,
                false
        );

        TextView recipeName =
                view.findViewById(R.id.detail_recipe_name);

        TextView ingredients =
                view.findViewById(R.id.detail_ingredients);

        TextView method =
                view.findViewById(R.id.detail_method);

        int index = requireArguments().getInt(
                ARG_RECIPE_INDEX, -1
        );

        List<Recipe> recipes =
                RecipeRepository.loadRecipes(requireContext());

        if (index >= 0 && index < recipes.size()) {

            Recipe recipe = recipes.get(index);

            recipeName.setText(recipe.getName());

            ingredients.setText(
                    formatLines(recipe.getIngredients(), false)
            );

            method.setText(
                    formatLines(recipe.getMethod(), true)
            );

        } else if (index == -1) {

            recipeName.setText(R.string.select_recipe);

            view.findViewById(R.id.ingredients_label)
                    .setVisibility(View.GONE);

            view.findViewById(R.id.method_label)
                    .setVisibility(View.GONE);

        } else {

            recipeName.setText(R.string.recipe_missing);
        }

        return view;
    }

    private String formatLines(
            List<String> lines, boolean numbered) {

        StringBuilder text = new StringBuilder();

        for (int i = 0; i < lines.size(); i++) {

            if (numbered) {
                text.append(i + 1).append(". ");
            } else {
                text.append("• ");
            }

            text.append(lines.get(i));

            if (i < lines.size() - 1) {
                text.append("\n");
            }
        }

        return text.toString();
    }
}
