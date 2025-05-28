package cybercooker.recipeservice.mapper.http;

import cybercooker.recipeservice.entity.Recipe;
import cybercooker.recipeservice.grpc.recipe.RecipeGrpcCreateRequest;
import cybercooker.recipeservice.request.recipe.RecipeCreateRequest;
import cybercooker.recipeservice.request.recipe.RecipeUpdateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RecipeMapperHttp {
    RecipeMapperHttp INSTANCE = Mappers.getMapper(RecipeMapperHttp.class);

    Recipe fromCreateRequest(RecipeCreateRequest request);

    Recipe fromUpdateRequest(RecipeUpdateRequest request);
    
}
