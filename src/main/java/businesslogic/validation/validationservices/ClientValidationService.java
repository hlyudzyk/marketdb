package businesslogic.validation.validationservices;

import businesslogic.validation.ValidationResult;
import businesslogic.validation.proxies.ClientProxy;

public interface ClientValidationService {
    ValidationResult validate(ClientProxy clientProxy);
}
