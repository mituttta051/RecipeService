package cybercooker.recipeservice.exception;

import io.grpc.Status;

public class NotFoundException extends InternalException {
    public NotFoundException(String message) {
        super(message);
    }

    @Override
    public Status toGrpcStatus() {
        return Status.NOT_FOUND.withDescription(getMessage());
    }
}
