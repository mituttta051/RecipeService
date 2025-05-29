package cybercooker.recipeservice.mapper.http;

import cybercooker.recipeservice.entity.Ingredient;
import cybercooker.recipeservice.request.ingredient.IngredientCreateRequest;
import cybercooker.recipeservice.request.ingredient.IngredientUpdateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface HttpIngredientMapper {
    HttpIngredientMapper INSTANCE = Mappers.getMapper(HttpIngredientMapper.class);

    Ingredient fromCreateRequest(IngredientCreateRequest ingredient);

    Ingredient fromUpdateRequest(IngredientUpdateRequest ingredient);
}
