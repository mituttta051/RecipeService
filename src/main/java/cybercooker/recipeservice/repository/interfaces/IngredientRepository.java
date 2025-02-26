package cybercooker.recipeservice.repository.interfaces;

import cybercooker.recipeservice.entity.Ingredient;
import cybercooker.recipeservice.exception.AlreadyExistsException;
import cybercooker.recipeservice.exception.NotFoundException;

import java.util.List;

public interface IngredientRepository {
    Ingredient getById(int id, int spaceId) throws NotFoundException;

    List<Ingredient> getAllBySpaceId(int spaceId);

    void save(Ingredient ingredient) throws AlreadyExistsException;

    void update(Ingredient ingredient) throws NotFoundException, AlreadyExistsException;

    void delete(int id, int spaceId) throws NotFoundException;
}
