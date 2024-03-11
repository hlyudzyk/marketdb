package businesslogic.validation.exceptions;

public class EntityValidationException extends RuntimeException {

    public EntityValidationException(String errorMsg) {
        super(errorMsg);
    }
}
