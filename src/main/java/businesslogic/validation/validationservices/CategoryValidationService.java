package businesslogic.validation.validationservices;

import businesslogic.validation.ValidationResult;
import businesslogic.validation.exceptions.EntityValidationException;
import businesslogic.validation.proxies.CategoryProxy;
import org.springframework.stereotype.Service;

@Service
public interface CategoryValidationService {
    ValidationResult validate(CategoryProxy categoryProxy);
}
