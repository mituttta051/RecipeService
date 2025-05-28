package cybercooker.recipeservice.exception;

import cybercooker.recipeservice.exception.details.DatabaseDetails;

public class AlreadyExistsException extends BaseException {
    public AlreadyExistsException(DatabaseDetails details) {
        super(details);
    }
    
}
