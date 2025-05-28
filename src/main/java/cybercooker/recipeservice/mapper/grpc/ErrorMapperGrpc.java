package cybercooker.recipeservice.mapper.grpc;

import cybercooker.recipeservice.exception.AlreadyExistsException;
import cybercooker.recipeservice.exception.BaseException;
import cybercooker.recipeservice.exception.NotFoundException;
import io.grpc.Status;

public class ErrorMapperGrpc {
    public static Status map(BaseException exception) {
        return switch (exception) {
            case NotFoundException e -> Status.NOT_FOUND;
            case AlreadyExistsException e -> Status.ALREADY_EXISTS;
            default -> Status.INTERNAL;
        };
    }
}
