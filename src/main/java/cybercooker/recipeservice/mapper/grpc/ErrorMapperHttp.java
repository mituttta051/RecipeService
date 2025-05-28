package cybercooker.recipeservice.mapper.grpc;

import cybercooker.recipeservice.exception.AlreadyExistsException;
import cybercooker.recipeservice.exception.BaseException;
import cybercooker.recipeservice.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ErrorMapperHttp {
    public static ResponseEntity<BaseException> map(BaseException exception) {
        return switch (exception) {
            case NotFoundException e -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception);
            case AlreadyExistsException e -> ResponseEntity.status(HttpStatus.CONFLICT).body(exception);
            default -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception);
        };
    }
}
