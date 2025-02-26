package cybercooker.recipeservice.service;

import cybercooker.recipeservice.entity.Recipe;
import cybercooker.recipeservice.exception.AlreadyExistsException;
import cybercooker.recipeservice.exception.NotFoundException;
import cybercooker.recipeservice.repository.interfaces.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeService {
    private final RecipeRepository recipeRepository;

    @Autowired
    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public Recipe getRecipeById(int id, int spaceId) throws NotFoundException {
        return recipeRepository.getById(id, spaceId);
    }

    public List<Recipe> getAllRecipesBySpaceId(int spaceId) {
        return recipeRepository.getAllBySpaceId(spaceId);
    }

    public void saveRecipe(Recipe recipe) throws AlreadyExistsException {
        recipeRepository.save(recipe);
    }

    public void updateRecipe(Recipe recipe) throws NotFoundException, AlreadyExistsException {
        recipeRepository.update(recipe);
    }

    public void deleteRecipe(int id, int spaceId) throws NotFoundException {
        recipeRepository.delete(id, spaceId);
    }
}
