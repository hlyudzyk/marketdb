package businesslogic.services;

import businesslogic.validation.ValidationResult;
import businesslogic.validation.exceptions.EntityValidationException;
import businesslogic.validation.proxies.CategoryProxy;
import businesslogic.validation.proxies.ProductProxy;
import businesslogic.validation.validationservices.CategoryValidationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import persistence.models.Category;
import persistence.models.Category.CategoryBuilder;
import persistence.models.Product.ProductBuilder;
import persistence.repositories.CategoryRepository;

@Service
@AllArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryValidationService categoryValidationService;

    public boolean createCategory(String name) throws EntityValidationException{
        CategoryProxy categoryProxy = new CategoryProxy(name);
        ValidationResult validationResult = categoryValidationService.validate(categoryProxy);

        if (validationResult.isValid()) {
            categoryRepository.save(new CategoryBuilder(categoryProxy).build());
            return true;
        } else {
            throw new EntityValidationException(validationResult.getErrorMsg());
        }
    }

}
