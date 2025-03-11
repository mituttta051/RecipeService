package cybercooker.recipeservice.exception;

import io.grpc.Status;

public class AlreadyExistsException extends InternalException {
    public AlreadyExistsException(String message) {
        super(message);
    }

    @Override
    public Status toGrpcStatus() {
        return Status.ALREADY_EXISTS.withDescription(getMessage());
    }

}
