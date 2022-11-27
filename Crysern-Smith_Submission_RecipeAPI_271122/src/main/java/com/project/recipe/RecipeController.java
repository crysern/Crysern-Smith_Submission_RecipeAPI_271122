package com.project.recipe;

import com.project.recipe.entity.Recipe;
import com.project.recipe.exceptions.RecipeNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController()
public class RecipeController {

    @Autowired
    RecipeService recipeService;

    @GetMapping("/recipes")
    public List<Recipe> getAll() {
        return recipeService.getAll();
    }

    @PostMapping("/recipes")
    public Recipe createRecipe(@RequestBody Recipe recipe) {
        return recipeService.createRecipe(recipe);
    }

    @PutMapping("/recipes/{id}")
    public Recipe updateRecipe(@RequestBody Recipe newRecipe, @PathVariable Long id) {
        Recipe updated = recipeService.updateRecipe(newRecipe,id);
        if (updated == null)
            throw new RecipeNotFoundException(id);
        else
            return updated;
    }

    @DeleteMapping("/recipes/{id}")
    public void deleteRecipe(@PathVariable Long id) {
        recipeService.deleteRecipe(id);
    }
}
