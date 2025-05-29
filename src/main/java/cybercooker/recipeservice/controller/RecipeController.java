package cybercooker.recipeservice.controller;

import cybercooker.recipeservice.entity.Recipe;
import cybercooker.recipeservice.mapper.http.HttpRecipeMapper;
import cybercooker.recipeservice.request.recipe.RecipeCreateRequest;
import cybercooker.recipeservice.request.recipe.RecipeUpdateRequest;
import cybercooker.recipeservice.service.RecipeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/recipe")
public class RecipeController {
    @Autowired
    private RecipeService recipeService;

    @GetMapping("/{spaceId}/{id}")
    public ResponseEntity<Recipe> getRecipeById(@PathVariable int spaceId, @PathVariable int id) {
        Recipe recipe = recipeService.getById(id, spaceId);
        return ResponseEntity.ok(recipe);
    }

    @GetMapping("/{spaceId}")
    public ResponseEntity<List<Recipe>> getAllRecipesBySpaceId(@PathVariable int spaceId) {
        return ResponseEntity.ok(
                recipeService.getAllBySpaceId(spaceId).stream().collect(Collectors.toList())
        );
    }

    @PostMapping
    public void addRecipe(@Valid @RequestBody RecipeCreateRequest request) {
        Recipe recipe = HttpRecipeMapper.INSTANCE.fromCreateRequest(request);
        recipeService.saveRecipe(recipe);
    }

    @PutMapping
    public void updateRecipe(@Valid @RequestBody RecipeUpdateRequest request) {
        Recipe recipe = HttpRecipeMapper.INSTANCE.fromUpdateRequest(request);
        recipeService.updateRecipe(recipe);
    }

    @DeleteMapping("/{spaceId}/{id}")
    public void deleteRecipeById(@PathVariable int spaceId, @PathVariable int id) {
        recipeService.deleteRecipe(id, spaceId);
    }

}
