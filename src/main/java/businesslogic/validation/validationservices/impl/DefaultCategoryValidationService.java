package businesslogic.validation.validationservices.impl;

import businesslogic.validation.ValidationResult;
import businesslogic.validation.proxies.CategoryProxy;
import businesslogic.validation.validationservices.CategoryValidationService;

public class DefaultCategoryValidationService implements CategoryValidationService {

    @Override
    public ValidationResult validate(CategoryProxy categoryProxy) {
        //TODO: complete this validation chain
        return ValidationResult.valid();
    }
}
