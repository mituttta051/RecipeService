package cybercooker.recipeservice.repository.interfaces;

import cybercooker.recipeservice.entity.Recipe;
import cybercooker.recipeservice.entity.filter.Filter;
import cybercooker.recipeservice.exception.AlreadyExistsException;
import cybercooker.recipeservice.exception.NotFoundException;

import java.util.List;

public interface RecipeRepository {
    Recipe getById(int id, int spaceId) throws NotFoundException;

    List<Recipe> getAllBySpaceId(int spaceId);

    void save(Recipe recipe) throws AlreadyExistsException;

    void update(Recipe recipe) throws NotFoundException, AlreadyExistsException;

    void delete(int id, int spaceId) throws NotFoundException;

    List<Recipe> getRecipesByFilter(Filter filter);
}
