package businesslogic.validation.validationservices;

import businesslogic.validation.ValidationResult;
import businesslogic.validation.proxies.CategoryProxy;

public interface CategoryValidationService {
    ValidationResult validate(CategoryProxy categoryProxy);
}
