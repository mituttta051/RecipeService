package cybercooker.recipeservice.exception;

import com.fasterxml.jackson.annotation.JsonValue;
import cybercooker.recipeservice.exception.details.ErrorDetails;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public abstract class BaseException extends RuntimeException {
    ErrorDetails details;

    @JsonValue
    private Wrapper getDetails() {
        return new Wrapper();
    }

    private class Wrapper {
        public ErrorDetails getDetails() {
            return details;
        }
    }

}
