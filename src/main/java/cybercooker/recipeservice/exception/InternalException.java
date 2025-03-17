package cybercooker.recipeservice.exception;

import io.grpc.Status;

public abstract class InternalException extends RuntimeException {
    public InternalException(String message) {
        super(message);
    }

    public abstract Status toGrpcStatus();

}
