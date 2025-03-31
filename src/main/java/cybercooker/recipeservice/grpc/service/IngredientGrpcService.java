package cybercooker.recipeservice.grpc.service;

import cybercooker.recipeservice.entity.Ingredient;
import cybercooker.recipeservice.grpc.ingredient.*;
import cybercooker.recipeservice.mapper.IngredientMapper;
import cybercooker.recipeservice.service.IngredientService;
import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.grpc.server.service.GrpcService;

import java.util.stream.Collectors;

@GrpcService
public class IngredientGrpcService extends IngredientServiceGrpc.IngredientServiceImplBase {
    @Autowired
    private IngredientService ingredientService;


    @Override
    public void getIngredientById(IngredientIdGrpc request, StreamObserver<IngredientGrpc> responseObserver) {
        Ingredient ingredient = ingredientService.getById(request.getId(), request.getSpaceId());
        IngredientGrpc ingredientGrpc = IngredientMapper.INSTANCE.toIngredientGrpc(ingredient);
        responseObserver.onNext(ingredientGrpc);
        responseObserver.onCompleted();

    }

    @Override
    public void getAllIngredientsBySpaceId(IngredientGrpcGetAll request, StreamObserver<IngredientListGrpc> responseObserver) {
        IngredientListGrpc ingredientListGrpc = IngredientListGrpc.newBuilder()
                .addAllIngredients(ingredientService.getAllBySpaceId(request.getSpaceId()).stream()
                        .map(IngredientMapper.INSTANCE::toIngredientGrpc)
                        .collect(Collectors.toList()))
                .build();
        responseObserver.onNext(ingredientListGrpc);
        responseObserver.onCompleted();
    }

    @Override
    public void addIngredient(IngredientGrpcCreateRequest request, StreamObserver<Empty> responseObserver) {
        Ingredient ingredient = IngredientMapper.INSTANCE.fromRequestToIngredient(request);
        ingredientService.addIngredient(ingredient);

        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();
    }

    @Override
    public void updateIngredient(IngredientGrpc request, StreamObserver<Empty> responseObserver) {
        Ingredient ingredient = IngredientMapper.INSTANCE.fromGrpcToIngredient(request);
        ingredientService.updateIngredient(ingredient);

        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();
    }

    @Override
    public void deleteIngredientById(IngredientIdGrpc request, StreamObserver<Empty> responseObserver) {
        ingredientService.deleteIngredient(request.getId(), request.getSpaceId());

        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();

    }
}
