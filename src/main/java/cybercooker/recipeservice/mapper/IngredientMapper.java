package cybercooker.recipeservice.mapper;

import cybercooker.recipeservice.entity.Ingredient;
import cybercooker.recipeservice.grpc.ingredient.IngredientGrpc;
import cybercooker.recipeservice.grpc.ingredient.IngredientGrpcCreateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface IngredientMapper {
    IngredientMapper INSTANCE = Mappers.getMapper(IngredientMapper.class);

    IngredientGrpc toIngredientGrpc(Ingredient ingredient);

    Ingredient fromRequestToIngredient(IngredientGrpcCreateRequest ingredientGrpc);

    Ingredient fromGrpcToIngredient(IngredientGrpc ingredientGrpc);

}
