package cybercooker.recipeservice.grpc.service;

import cybercooker.recipeservice.entity.Recipe;
import cybercooker.recipeservice.grpc.filter.FilterGrpc;
import cybercooker.recipeservice.grpc.recipe.*;
import cybercooker.recipeservice.mapper.FilterMapper;
import cybercooker.recipeservice.mapper.RecipeMapper;
import cybercooker.recipeservice.service.RecipeService;
import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.grpc.server.service.GrpcService;

@GrpcService
public class RecipeGrpcService extends RecipeServiceGrpc.RecipeServiceImplBase {
    @Autowired
    private RecipeService recipeService;

    @Override
    public void getRecipeById(RecipeId request, StreamObserver<RecipeRequestResponse> responseObserver) {
        Recipe recipe = recipeService.getById(request.getId(), request.getSpaceId());
        RecipeRequestResponse recipeRequestResponse = RecipeMapper.INSTANCE.toRecipeDTO(recipe);

        responseObserver.onNext(recipeRequestResponse);
        responseObserver.onCompleted();
    }

    @Override
    public void getAllRecipesBySpaceId(RecipeGetAllBySpaceIdRequest request, StreamObserver<RecipeListResponse> responseObserver) {
        RecipeListResponse recipeListDTO = RecipeListResponse.newBuilder()
                .addAllRecipes(recipeService.getAllBySpaceId(request.getSpaceId()).stream()
                        .map(RecipeMapper.INSTANCE::toRecipeDTO)
                        .toList())
                .build();

        responseObserver.onNext(recipeListDTO);
        responseObserver.onCompleted();
    }

    @Override
    public void addRecipe(RecipeCreateRequest request, StreamObserver<Empty> responseObserver) {
        Recipe recipe = RecipeMapper.INSTANCE.fromRequestToRecipe(request);
        recipeService.saveRecipe(recipe);

        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();
    }

    @Override
    public void updateRecipe(RecipeRequestResponse request, StreamObserver<Empty> responseObserver) {
        Recipe recipe = RecipeMapper.INSTANCE.fromDTOToRecipe(request);
        recipeService.updateRecipe(recipe);

        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();
    }

    @Override
    public void deleteRecipeById(RecipeId request, StreamObserver<Empty> responseObserver) {
        recipeService.deleteRecipe(request.getId(), request.getSpaceId());

        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();
    }

    @Override
    public void getRecipesByFilter(FilterGrpc request, StreamObserver<RecipeListResponse> responseObserver) {
        RecipeListResponse recipeListDTO = RecipeListResponse.newBuilder()
                .addAllRecipes(recipeService.getRecipesByFilter(FilterMapper.map(request)).stream()
                        .map(RecipeMapper.INSTANCE::toRecipeDTO)
                        .toList())
                .build();

        responseObserver.onNext(recipeListDTO);
        responseObserver.onCompleted();
    }
}
