package cybercooker.recipeservice.advice;

import cybercooker.recipeservice.exception.BaseException;
import cybercooker.recipeservice.exception.NotValidRequestException;
import cybercooker.recipeservice.exception.details.NotValidRequestDetails;
import cybercooker.recipeservice.mapper.http.HttpErrorMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GeneralAdvice {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<BaseException> serviceExceptionHandler(BaseException e) {
        return HttpErrorMapper.map(e);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseException> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new NotValidRequestException(new NotValidRequestDetails(errors)));
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String internalServerErrorHandler(Exception e) {
        e.printStackTrace();
        return e.getMessage();
    }
}