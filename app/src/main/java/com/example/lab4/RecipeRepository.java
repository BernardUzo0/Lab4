package com.example.lab4;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class RecipeRepository {

    private static final String TAG = "RecipeRepository";

    public static List<Recipe> loadRecipes(Context context) {

        List<Recipe> recipes = new ArrayList<>();

        try (InputStream input =
                     context.getAssets().open("recipes.json");

             BufferedReader reader = new BufferedReader(
                     new InputStreamReader(
                             input, StandardCharsets.UTF_8))) {

            StringBuilder jsonText = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                jsonText.append(line);
            }

            JSONObject root =
                    new JSONObject(jsonText.toString());

            JSONArray recipeArray =
                    root.getJSONArray("recipes");

            for (int i = 0; i < recipeArray.length(); i++) {

                JSONObject recipeObject =
                        recipeArray.getJSONObject(i);

                String name = recipeObject.getString("name");

                List<String> ingredients = readStrings(
                        recipeObject.getJSONArray("ingredients")
                );

                List<String> method = readStrings(
                        recipeObject.getJSONArray("method")
                );

                recipes.add(
                        new Recipe(name, ingredients, method)
                );
            }

        } catch (IOException | JSONException e) {
            Log.e(TAG, "Unable to load recipes.json", e);
        }

        return recipes;
    }

    private static List<String> readStrings(JSONArray array)
            throws JSONException {

        List<String> items = new ArrayList<>();

        for (int i = 0; i < array.length(); i++) {
            items.add(array.getString(i));
        }

        return items;
    }
}
