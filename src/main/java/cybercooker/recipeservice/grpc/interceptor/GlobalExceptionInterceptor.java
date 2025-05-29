package cybercooker.recipeservice.grpc.interceptor;

import cybercooker.recipeservice.exception.BaseException;
import cybercooker.recipeservice.mapper.grpc.GrpcErrorMapper;
import io.grpc.Status;
import org.springframework.grpc.server.exception.GrpcExceptionHandler;
import org.springframework.stereotype.Component;

@Component
public class GlobalExceptionInterceptor implements GrpcExceptionHandler {

    @Override
    public Status handleException(Throwable exception) {
        if (exception instanceof BaseException) {
            return GrpcErrorMapper.map((BaseException) exception);
        }
        return Status.INTERNAL.withDescription(exception.getMessage());
    }
}
