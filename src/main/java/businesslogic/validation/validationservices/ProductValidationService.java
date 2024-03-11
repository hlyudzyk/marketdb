package businesslogic.validation.validationservices;

import businesslogic.validation.ValidationResult;
import businesslogic.validation.proxies.ProductProxy;


public interface ProductValidationService {
    ValidationResult validate(ProductProxy productProxy);
}