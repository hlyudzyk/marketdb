package businesslogic.validation.validationservices;

import businesslogic.validation.ValidationResult;
import businesslogic.validation.proxies.ManufacturerProxy;
import org.springframework.stereotype.Service;

@Service
public interface ManufacturerValidationService {
    ValidationResult validate(ManufacturerProxy manufacturerProxy);
}
