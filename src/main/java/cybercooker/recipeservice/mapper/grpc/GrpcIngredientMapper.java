package cybercooker.recipeservice.mapper.grpc;

import cybercooker.recipeservice.entity.Ingredient;
import cybercooker.recipeservice.grpc.ingredient.IngredientGrpc;
import cybercooker.recipeservice.grpc.ingredient.IngredientGrpcCreateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface GrpcIngredientMapper {
    GrpcIngredientMapper INSTANCE = Mappers.getMapper(GrpcIngredientMapper.class);

    IngredientGrpc toGrpc(Ingredient ingredient);

    Ingredient fromCreateRequest(IngredientGrpcCreateRequest ingredientGrpc);

    Ingredient fromUpdateRequest(IngredientGrpc ingredientGrpc);

}
