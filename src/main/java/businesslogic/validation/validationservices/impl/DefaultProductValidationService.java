package businesslogic.validation.validationservices.impl;

import businesslogic.validation.ValidationResult;
import businesslogic.validation.ValidationStep;
import businesslogic.validation.proxies.ProductProxy;
import businesslogic.validation.validationservices.ProductValidationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import persistence.repositories.CategoryRepository;
import persistence.repositories.ManufacturerRepository;
import persistence.repositories.ProductRepository;


@AllArgsConstructor
@Service
public class DefaultProductValidationService implements ProductValidationService {

    private final ProductRepository productDao;
    private final ManufacturerRepository manufacturerDao;
    private final CategoryRepository categoryDao;

    @Override
    public ValidationResult validate(ProductProxy productProxy) {
        return new ProductNameDuplicationValidationStep(productDao)
            .linkWith(new PriceValidationStep(productDao))
            .linkWith(new ForeignKeysValidationStep(manufacturerDao,categoryDao))
            .validate(productProxy);
    }

    @AllArgsConstructor
    private static class ProductNameDuplicationValidationStep extends ValidationStep<ProductProxy> {

        private final ProductRepository productDao;

        @Override
        public ValidationResult validate(ProductProxy productProxy) {
            if (productDao.findProductByName(productProxy.getName()).isPresent()) {
                return ValidationResult.invalid(String.format("Product name [%s] is already taken", productProxy.getName()));
            }
            return checkNext(productProxy);
        }
    }

    @AllArgsConstructor
    private static class PriceValidationStep extends ValidationStep<ProductProxy> {
        private final ProductRepository productDao;
        @Override
        public ValidationResult validate(ProductProxy productProxy) {
            if(productProxy.getPrice()<0|| productProxy.getPrice()>1_000_000){
                return ValidationResult.invalid("Price is out of correct bounds");
            }

            return checkNext(productProxy);
        }
    }

    @AllArgsConstructor
    private static class ForeignKeysValidationStep extends ValidationStep<ProductProxy>{
        private final ManufacturerRepository manufacturerDao;
        private final CategoryRepository categoryDao;

        @Override
        public ValidationResult validate(ProductProxy productProxy) {
            if (manufacturerDao.findOneById(productProxy.getManufacturerId()).isEmpty()) {
                return ValidationResult.invalid(String.format("Manufacturer with id=[%s] does not exist", productProxy.getManufacturerId()));
            }
            if (categoryDao.findOneById(productProxy.getCategoryId()).isEmpty()) {
                return ValidationResult.invalid(String.format("Category with id=[%s] does not exist", productProxy.getCategoryId()));
            }
            return checkNext(productProxy);
        }
    }

}