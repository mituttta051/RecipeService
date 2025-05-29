package cybercooker.recipeservice.mapper.http;

import cybercooker.recipeservice.entity.Recipe;
import cybercooker.recipeservice.request.recipe.RecipeCreateRequest;
import cybercooker.recipeservice.request.recipe.RecipeUpdateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface HttpRecipeMapper {
    HttpRecipeMapper INSTANCE = Mappers.getMapper(HttpRecipeMapper.class);

    Recipe fromCreateRequest(RecipeCreateRequest request);

    Recipe fromUpdateRequest(RecipeUpdateRequest request);

}
