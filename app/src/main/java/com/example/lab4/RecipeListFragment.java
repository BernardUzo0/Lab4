package com.example.lab4;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RecipeListFragment extends Fragment {

    public interface OnRecipeSelectedListener {
        void onRecipeSelected(int recipeIndex);
    }

    private OnRecipeSelectedListener listener;

    public RecipeListFragment() {
        // Required empty constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof OnRecipeSelectedListener) {
            listener = (OnRecipeSelectedListener) context;
        } else {
            throw new IllegalStateException(
                    "Activity must implement OnRecipeSelectedListener"
            );
        }
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(
                R.layout.fragment_recipe_list,
                container,
                false
        );

        RecyclerView recyclerView =
                view.findViewById(R.id.recipes_recycler_view);

        recyclerView.setLayoutManager(
                new LinearLayoutManager(requireContext())
        );

        RecipeAdapter adapter = new RecipeAdapter(
                RecipeRepository.loadRecipes(requireContext()),
                new RecipeAdapter.OnRecipeClickListener() {

                    @Override
                    public void onRecipeClicked(int recipeIndex) {
                        listener.onRecipeSelected(recipeIndex);
                    }
                }
        );

        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}
