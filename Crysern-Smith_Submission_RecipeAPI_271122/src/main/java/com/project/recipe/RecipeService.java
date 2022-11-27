package com.project.recipe;

import com.project.recipe.entity.Recipe;
import com.project.recipe.exceptions.RecipeNotFoundException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class RecipeService {

    private Long id;
    @Getter @Setter
    private HashMap<Long,Recipe> recipes;

    public RecipeService() {
        this.recipes = new HashMap<>();
        id = 0L;
    }

    public List<Recipe> getAll() {
        return new ArrayList<>(recipes.values());
    }

    public Recipe createRecipe(Recipe recipe) {
        recipe.setId(id);
        recipes.put(id++, recipe);
        return recipe;
    }

    public Recipe updateRecipe(Recipe newRecipe, Long id) {
        newRecipe.setId(id);
        if(recipes.replace(id, newRecipe) == null)
            throw new RecipeNotFoundException(id);
        return newRecipe;
    }

    public void deleteRecipe (Long id) {
        if(recipes.remove(id) == null)
            throw new RecipeNotFoundException(id);
    }
}
