package cybercooker.recipeservice.grpc.service;

import cybercooker.recipeservice.entity.Recipe;
import cybercooker.recipeservice.grpc.recipe.*;
import cybercooker.recipeservice.mapper.grpc.FilterMapperGrpc;
import cybercooker.recipeservice.mapper.grpc.RecipeMapperGrpc;
import cybercooker.recipeservice.service.RecipeService;
import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.grpc.server.service.GrpcService;

@GrpcService
public class RecipeGrpcService extends RecipeServiceGrpc.RecipeServiceImplBase {
    @Autowired
    private RecipeService recipeService;

    @Override
    public void getRecipeById(RecipeIdGrpc request, StreamObserver<RecipeGrpc> responseObserver) {
        Recipe recipe = recipeService.getById(request.getId(), request.getSpaceId());
        RecipeGrpc recipeRequestResponse = RecipeMapperGrpc.INSTANCE.toGrpc(recipe);

        responseObserver.onNext(recipeRequestResponse);
        responseObserver.onCompleted();
    }

    @Override
    public void getAllRecipesBySpaceId(RecipeGetAllGrpc request, StreamObserver<RecipeListGrpc> responseObserver) {
        RecipeListGrpc recipeListGrpc = RecipeListGrpc.newBuilder()
                .addAllRecipes(recipeService.getAllBySpaceId(request.getSpaceId()).stream()
                        .map(RecipeMapperGrpc.INSTANCE::toGrpc)
                        .toList())
                .build();

        responseObserver.onNext(recipeListGrpc);
        responseObserver.onCompleted();
    }

    @Override
    public void addRecipe(RecipeGrpcCreateRequest request, StreamObserver<Empty> responseObserver) {
        Recipe recipe = RecipeMapperGrpc.INSTANCE.fromCreateRequest(request);
        recipeService.saveRecipe(recipe);

        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();
    }

    @Override
    public void updateRecipe(RecipeGrpc request, StreamObserver<Empty> responseObserver) {
        Recipe recipe = RecipeMapperGrpc.INSTANCE.toRecipe(request);
        recipeService.updateRecipe(recipe);

        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();
    }

    @Override
    public void deleteRecipeById(RecipeIdGrpc request, StreamObserver<Empty> responseObserver) {
        recipeService.deleteRecipe(request.getId(), request.getSpaceId());

        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();
    }

    @Override
    public void getRecipesByFilter(GetRecipesByFilterGrpc request, StreamObserver<RecipeListGrpc> responseObserver) {
        RecipeListGrpc recipeListGrpc = RecipeListGrpc.newBuilder()
                .addAllRecipes(recipeService.getRecipesByFilter(FilterMapperGrpc.map(request.getFilter()), request.getSpaceId()).stream()
                        .map(RecipeMapperGrpc.INSTANCE::toGrpc)
                        .toList())
                .build();

        responseObserver.onNext(recipeListGrpc);
        responseObserver.onCompleted();
    }
}
