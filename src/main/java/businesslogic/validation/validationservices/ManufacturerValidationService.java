package businesslogic.validation.validationservices;

import businesslogic.validation.ValidationResult;
import businesslogic.validation.proxies.ManufacturerProxy;

public interface ManufacturerValidationService {
    ValidationResult validate(ManufacturerProxy manufacturerProxy);
}
