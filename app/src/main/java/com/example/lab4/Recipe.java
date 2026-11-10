package com.example.lab4;

import java.util.List;

public class Recipe {

    private final String name;
    private final List<String> ingredients;
    private final List<String> method;

    public Recipe(String name, List<String> ingredients,
                  List<String> method) {
        this.name = name;
        this.ingredients = ingredients;
        this.method = method;
    }

    public String getName() {
        return name;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public List<String> getMethod() {
        return method;
    }
}
