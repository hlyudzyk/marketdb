package businesslogic.validation.validationservices.impl;

import businesslogic.validation.ValidationResult;
import businesslogic.validation.proxies.ManufacturerProxy;
import businesslogic.validation.validationservices.ManufacturerValidationService;

public class DefaultManufacturerValidationService implements ManufacturerValidationService {

    @Override
    public ValidationResult validate(ManufacturerProxy manufacturerProxy) {
        //TODO: complete this validation chain
        return ValidationResult.valid();
    }
}
