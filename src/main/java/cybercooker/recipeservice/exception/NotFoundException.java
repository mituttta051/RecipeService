package cybercooker.recipeservice.exception;

import cybercooker.recipeservice.exception.details.DatabaseDetails;

public class NotFoundException extends BaseException {
    public NotFoundException(DatabaseDetails details) {
        super(details);
    }

}
