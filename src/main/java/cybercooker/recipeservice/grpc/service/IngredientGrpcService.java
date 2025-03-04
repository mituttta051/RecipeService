package cybercooker.recipeservice.grpc.service;

import com.google.protobuf.Empty;
import cybercooker.recipeservice.entity.Ingredient;
import cybercooker.recipeservice.exception.AlreadyExistsException;
import cybercooker.recipeservice.exception.NotFoundException;
import cybercooker.recipeservice.grpc.*;
import cybercooker.recipeservice.service.IngredientService;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.grpc.server.service.GrpcService;

@GrpcService
public class IngredientGrpcService extends IngredientServiceGrpc.IngredientServiceImplBase {
    @Autowired
    private IngredientService ingredientService;

    @Override
    public void getIngredientById(IngredientId request, StreamObserver<IngredientDTO> responseObserver) {
        try {
            Ingredient ingredient = ingredientService.getById(request.getId(), request.getSpaceId());
            IngredientDTO ingredientDTO = IngredientDTO.newBuilder()
                    .setId(ingredient.getId())
                    .setSpaceId(ingredient.getSpaceId())
                    .setName(ingredient.getName())
                    .build();
            responseObserver.onNext(ingredientDTO);
            responseObserver.onCompleted();
        } catch (NotFoundException e) {
            Status status = Status.NOT_FOUND.withDescription(e.getMessage());
            responseObserver.onError(status.asRuntimeException());
        } catch (Exception e) {
            Status status = Status.INTERNAL.withDescription(e.getMessage());
            responseObserver.onError(status.asRuntimeException());
        }
    }

    @Override
    public void getAllIngredientsBySpaceId(IngredientGetAllIBySpaceIdRequest request, StreamObserver<IngredientListDTO> responseObserver) {
        IngredientListDTO.Builder builder = IngredientListDTO.newBuilder();
        ingredientService.getAllBySpaceId(request.getSpaceId()).forEach(ingredient -> {
            IngredientDTO ingredientDTO = IngredientDTO.newBuilder()
                    .setId(ingredient.getId())
                    .setSpaceId(ingredient.getSpaceId())
                    .setName(ingredient.getName())
                    .build();
            builder.addIngredients(ingredientDTO);
        });
        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }

    @Override
    public void addIngredient(IngredientCreateRequest request, StreamObserver<Empty> responseObserver) {
        Ingredient ingredient = Ingredient.builder()
                .spaceId(request.getSpaceId())
                .name(request.getName())
                .build();
        try {
            ingredientService.addIngredient(ingredient);
            responseObserver.onNext(Empty.newBuilder().build());
            responseObserver.onCompleted();
        } catch (AlreadyExistsException e) {
            Status status = Status.ALREADY_EXISTS.withDescription(e.getMessage());
            responseObserver.onError(status.asRuntimeException());
        } catch (Exception e) {
            Status status = Status.INTERNAL.withDescription(e.getMessage());
            responseObserver.onError(status.asRuntimeException());
        }
    }

    @Override
    public void updateIngredient(IngredientDTO request, StreamObserver<Empty> responseObserver) {
        Ingredient ingredient = Ingredient.builder()
                .id(request.getId())
                .spaceId(request.getSpaceId())
                .name(request.getName())
                .build();
        try {
            ingredientService.updateIngredient(ingredient);
            responseObserver.onNext(Empty.newBuilder().build());
            responseObserver.onCompleted();
        } catch (NotFoundException e) {
            Status status = Status.NOT_FOUND.withDescription(e.getMessage());
            responseObserver.onError(status.asRuntimeException());
        } catch (AlreadyExistsException e) {
            Status status = Status.ALREADY_EXISTS.withDescription(e.getMessage());
            responseObserver.onError(status.asRuntimeException());
        } catch (Exception e) {
            Status status = Status.INTERNAL.withDescription(e.getMessage());
            responseObserver.onError(status.asRuntimeException());
        }
    }

    @Override
    public void deleteIngredientById(IngredientId request, StreamObserver<Empty> responseObserver) {
        try {
            ingredientService.deleteIngredient(request.getId(), request.getSpaceId());
            responseObserver.onNext(Empty.newBuilder().build());
            responseObserver.onCompleted();
        } catch (NotFoundException e) {
            Status status = Status.NOT_FOUND.withDescription(e.getMessage());
            responseObserver.onError(status.asRuntimeException());
        } catch (Exception e) {
            Status status = Status.INTERNAL.withDescription(e.getMessage());
            responseObserver.onError(status.asRuntimeException());
        }
    }
}
