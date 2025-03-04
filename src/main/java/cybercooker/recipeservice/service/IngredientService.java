package cybercooker.recipeservice.service;

import cybercooker.recipeservice.entity.Ingredient;
import cybercooker.recipeservice.exception.AlreadyExistsException;
import cybercooker.recipeservice.exception.NotFoundException;
import cybercooker.recipeservice.repository.interfaces.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientService {
    @Autowired
    private IngredientRepository ingredientRepository;

    public Ingredient getById(int id, int spaceId) throws NotFoundException {
        return ingredientRepository.getById(id, spaceId);
    }

    public List<Ingredient> getAllBySpaceId(int spaceId) {
        return ingredientRepository.getAllBySpaceId(spaceId);
    }

    public void addIngredient(Ingredient ingredient) throws AlreadyExistsException {
        ingredientRepository.save(ingredient);
    }

    public void updateIngredient(Ingredient ingredient) throws NotFoundException, AlreadyExistsException {
        ingredientRepository.update(ingredient);
    }

    public void deleteIngredient(int id, int spaceId) throws NotFoundException {
        ingredientRepository.delete(id, spaceId);
    }
    
}
