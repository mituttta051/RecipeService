package cybercooker.recipeservice.grpc.interceptor;

import cybercooker.recipeservice.exception.InternalException;
import io.grpc.Status;
import org.springframework.grpc.server.exception.GrpcExceptionHandler;
import org.springframework.stereotype.Component;

@Component
public class GlobalExceptionInterceptor implements GrpcExceptionHandler {

    @Override
    public Status handleException(Throwable exception) {
        if (exception instanceof InternalException) {
            return ((InternalException) exception).toGrpcStatus();
        }
        return Status.INTERNAL.withDescription(exception.getMessage());
    }
}
