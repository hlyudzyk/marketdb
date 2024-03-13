package businesslogic.services;

import businesslogic.validation.ValidationResult;
import businesslogic.validation.exceptions.EntityValidationException;
import businesslogic.validation.proxies.CategoryProxy;
import businesslogic.validation.proxies.ManufacturerProxy;
import businesslogic.validation.validationservices.ManufacturerValidationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import persistence.models.Category.CategoryBuilder;
import persistence.models.Manufacturer.ManufacturerBuilder;
import persistence.repositories.ManufacturerRepository;

@Service
@AllArgsConstructor
public class ManufacturerService {
    private final ManufacturerRepository manufacturerRepository;
    private final ManufacturerValidationService manufacturerValidationService;

    public boolean createManufacturer(String name,String country) throws EntityValidationException {
        ManufacturerProxy manufacturerProxy = new ManufacturerProxy(name,country);
        ValidationResult validationResult = manufacturerValidationService.validate(manufacturerProxy);

        if (validationResult.isValid()) {
            manufacturerRepository.save(new ManufacturerBuilder(manufacturerProxy).build());
            return true;
        } else {
            throw new EntityValidationException(validationResult.getErrorMsg());
        }
    }
}
