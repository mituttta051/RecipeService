package cybercooker.recipeservice.controller;

import cybercooker.recipeservice.entity.Ingredient;
import cybercooker.recipeservice.mapper.grpc.IngredientMapperGrpc;
import cybercooker.recipeservice.mapper.http.IngredientMapperHttp;
import cybercooker.recipeservice.request.ingredient.IngredientCreateRequest;
import cybercooker.recipeservice.request.ingredient.IngredientUpdateRequest;
import cybercooker.recipeservice.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/ingredient")
public class IngredientController {

    @Autowired
    private IngredientService ingredientService;

    @GetMapping("/{spaceId}/{id}")
    public ResponseEntity<Ingredient> getIngredientById(@PathVariable int spaceId, @PathVariable int id) {
        Ingredient ingredient = ingredientService.getById(id, spaceId);
        return ResponseEntity.ok(ingredient);
    }

    @GetMapping("/{spaceId}")
    public ResponseEntity<List<Ingredient>> getAllIngredientsBySpaceId(@PathVariable int spaceId) {
        return ResponseEntity.ok(
                ingredientService.getAllBySpaceId(spaceId).stream()
                        .collect(Collectors.toList())
        );
    }

    @PostMapping
    public void addIngredient(@Valid @RequestBody IngredientCreateRequest request) {
        Ingredient ingredient = IngredientMapperHttp.INSTANCE.fromCreateRequest(request);
        ingredientService.addIngredient(ingredient);
    }

    @PutMapping
    public void updateIngredient(@Valid @RequestBody IngredientUpdateRequest request) {
        Ingredient ingredient = IngredientMapperHttp.INSTANCE.fromUpdateRequest(request);
        ingredientService.updateIngredient(ingredient);
    }

    @DeleteMapping("/{spaceId}/{id}")
    public void deleteIngredientById(@PathVariable int spaceId, @PathVariable int id) {
        ingredientService.deleteIngredient(id, spaceId);
    }
}