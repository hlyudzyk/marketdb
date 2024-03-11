package businesslogic.validation.validationservices;

import businesslogic.validation.ValidationResult;
import businesslogic.validation.proxies.SaleProxy;

public interface SaleValidationService {
    ValidationResult validate(SaleProxy saleProxy);
}
