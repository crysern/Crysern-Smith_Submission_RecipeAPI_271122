package com.project.recipe;

import com.project.recipe.entity.Ingredient;
import com.project.recipe.entity.Recipe;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@WebMvcTest(value = RecipeController.class)
public class RecipeControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RecipeService service;

    Recipe recipe = new Recipe(0L, "recipe", new ArrayList<>(Arrays.asList(new Ingredient("ingredient", 1))), "instruction");
    List<Recipe> recipes = new ArrayList<>(Arrays.asList(recipe));
    String recipeJson = "{\"id\":0,\"name\":\"recipe\",\"ingredients\":[{\"name\":\"ingredient\",\"quantity\":1}],\"instructions\":\"instruction\"}";
    String recipeJsonList = "[{\"id\":0,\"name\":\"recipe\",\"ingredients\":[{\"name\":\"ingredient\",\"quantity\":1}],\"instructions\":\"instruction\"}]";
    String recipeJsonNoId = "{\"name\":\"recipe\",\"ingredients\":[{\"name\":\"ingredient\",\"quantity\":1}],\"instructions\":\"instruction\"}";

    Recipe newRecipe = new Recipe(0L, "recipe2", new ArrayList<>(Arrays.asList(new Ingredient("ingredient", 1))), "instruction");
    String newRecipeJson = "{\"name\":\"recipe2\",\"ingredients\":[{\"name\":\"ingredient\",\"quantity\":1}],\"instructions\":\"instruction\"}";
    String updatedRecipeJson = "{\"id\":0,\"name\":\"recipe2\",\"ingredients\":[{\"name\":\"ingredient\",\"quantity\":1}],\"instructions\":\"instruction\"}";

    @Test
    public void getAll() throws Exception {

        Mockito.when(service.getAll()).thenReturn(recipes);
        RequestBuilder builder = MockMvcRequestBuilders.get("/recipes");
        MvcResult result = mockMvc.perform(builder).andReturn();
        JSONAssert.assertEquals(recipeJsonList, result.getResponse().getContentAsString(), false);
    }

    @Test
    public void createRecipe() throws Exception {
        Mockito.when(service.createRecipe(Mockito.any(Recipe.class))).thenReturn(recipe);
        RequestBuilder builder = MockMvcRequestBuilders
                .post("/recipes")
                .accept(MediaType.APPLICATION_JSON)
                .content(recipeJsonNoId)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(builder).andReturn();
        JSONAssert.assertEquals(recipeJson, result.getResponse().getContentAsString(), false);
    }

    @Test
    public void updateRecipe() throws Exception {
        Mockito.when(service.updateRecipe(Mockito.any(Recipe.class), Mockito.anyLong())).thenReturn(newRecipe);
        RequestBuilder builder = MockMvcRequestBuilders
                .put("/recipes/0")
                .accept(MediaType.APPLICATION_JSON)
                .content(newRecipeJson)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(builder).andReturn();
        JSONAssert.assertEquals(updatedRecipeJson, result.getResponse().getContentAsString(), false);

    }
}
