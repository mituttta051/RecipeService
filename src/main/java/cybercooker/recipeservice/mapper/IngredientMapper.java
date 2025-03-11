package cybercooker.recipeservice.mapper;

import cybercooker.recipeservice.entity.Ingredient;
import cybercooker.recipeservice.grpc.IngredientCreateRequest;
import cybercooker.recipeservice.grpc.IngredientDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface IngredientMapper {
    IngredientMapper INSTANCE = Mappers.getMapper(IngredientMapper.class);
    
    IngredientDTO toIngredientDTO(Ingredient ingredient);
    Ingredient fromRequestToIngredient(IngredientCreateRequest ingredientDTO);
    Ingredient fromDTOToIngredient(IngredientDTO ingredientDTO);

}
