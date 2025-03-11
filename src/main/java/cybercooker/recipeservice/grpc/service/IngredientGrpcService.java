package cybercooker.recipeservice.grpc.service;

import cybercooker.recipeservice.entity.Ingredient;
import cybercooker.recipeservice.grpc.*;
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
    public void getIngredientById(IngredientId request, StreamObserver<IngredientDTO> responseObserver) {
        Ingredient ingredient = ingredientService.getById(request.getId(), request.getSpaceId());
        IngredientDTO ingredientDTO = IngredientMapper.INSTANCE.toIngredientDTO(ingredient);
        responseObserver.onNext(ingredientDTO);
        responseObserver.onCompleted();

    }

    @Override
    public void getAllIngredientsBySpaceId(IngredientGetAllIBySpaceIdRequest request, StreamObserver<IngredientListDTO> responseObserver) {
        IngredientListDTO ingredientListDTO = IngredientListDTO.newBuilder()
                .addAllIngredients(ingredientService.getAllBySpaceId(request.getSpaceId()).stream()
                        .map(IngredientMapper.INSTANCE::toIngredientDTO)
                        .collect(Collectors.toList()))
                .build();
        responseObserver.onNext(ingredientListDTO);
        responseObserver.onCompleted();
    }

    @Override
    public void addIngredient(IngredientCreateRequest request, StreamObserver<Empty> responseObserver) {
        Ingredient ingredient = IngredientMapper.INSTANCE.fromRequestToIngredient(request);
        ingredientService.addIngredient(ingredient);

        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();
    }

    @Override
    public void updateIngredient(IngredientDTO request, StreamObserver<Empty> responseObserver) {
        Ingredient ingredient = IngredientMapper.INSTANCE.fromDTOToIngredient(request);
        ingredientService.updateIngredient(ingredient);

        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();
    }

    @Override
    public void deleteIngredientById(IngredientId request, StreamObserver<Empty> responseObserver) {
        ingredientService.deleteIngredient(request.getId(), request.getSpaceId());

        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();

    }
}
