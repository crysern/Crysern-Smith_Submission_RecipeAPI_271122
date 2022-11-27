package com.project.recipe;

import com.project.recipe.entity.Ingredient;
import com.project.recipe.entity.Recipe;
import com.project.recipe.exceptions.RecipeNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.*;

@SpringBootTest
class RecipeApplicationTests {

	private static String RECIPE_NAME = "recipe";
	private static String INGREDIENT_NAME = "ingredient";
	private static  int quantity = 2;
	private static String INSTRUCTION = "instruction";

	@Autowired
	private RecipeService service;
	private Recipe recipe;
	private Long id = 0L;
	private HashMap<Long,Recipe> recipes;

	@BeforeEach
	public void setUp() {
		recipe = new Recipe(RECIPE_NAME,
				new ArrayList<>(Arrays.asList(new Ingredient(INGREDIENT_NAME,quantity))), INSTRUCTION);
		recipes = new HashMap<>();
	}

	@Test
	void testGetAllReturnRecipe() {
		recipe.setId(id);
		List<Recipe> expectedList = new ArrayList<>(Arrays.asList(recipe));

		recipes.put(id, recipe);
		service.setRecipes(recipes);

		Assertions.assertEquals(expectedList, service.getAll());
	}

	@Test
	void testCreateRecipe() {
		Recipe newRecipe = service.createRecipe(recipe);
		recipe.setId(id);
		Assertions.assertEquals(recipe, newRecipe);
	}

	@Test
	void testUpdateRecipe() {
		recipe.setId(id);
		recipes.put(id, recipe);
		Recipe newRecipe = new Recipe("recipe 2", null, "instruction 2");
		Assertions.assertEquals(recipe, service.updateRecipe(newRecipe, id));
		Assertions.assertEquals(recipe, service.getRecipes().get(id));
	}

	@Test
	void testUpdateThrowsNotFoundException() {
		Assertions.assertThrows(RecipeNotFoundException.class, () -> { service.updateRecipe(recipe, 1L);});
	}

	@Test
	void testDeleteRecipe() {
		recipe.setId(id);
		recipes.put(id, recipe);
		service.setRecipes(recipes);
		service.deleteRecipe(id);
		Assertions.assertEquals(0, service.getRecipes().size());
	}

	@Test
	void testDeleteRecipeThrowsNotFoundException() {
		Assertions.assertThrows(RecipeNotFoundException.class, () -> { service.deleteRecipe(1L);});
	}
}
