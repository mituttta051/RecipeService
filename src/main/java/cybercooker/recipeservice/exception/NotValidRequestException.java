package cybercooker.recipeservice.exception;

import cybercooker.recipeservice.exception.details.NotValidRequestDetails;

public class NotValidRequestException extends BaseException {
    public NotValidRequestException(NotValidRequestDetails details) {
        super(details);
    }

}
