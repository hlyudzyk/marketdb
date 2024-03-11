package businesslogic.services;

import businesslogic.validation.ValidationResult;
import businesslogic.validation.exceptions.EntityValidationException;
import businesslogic.validation.proxies.ProductProxy;
import businesslogic.validation.validationservices.ProductValidationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import persistence.repositories.ProductRepository;
import persistence.models.Product.ProductBuilder;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductValidationService productValidationService;

    public boolean createProduct(String name, double price, int categoryId, int manufacturerId) {
        ProductProxy productProxy = new ProductProxy(name, price, categoryId, manufacturerId);
        ValidationResult validationResult = productValidationService.validate(productProxy);

        if (validationResult.isValid()) {
            productRepository.save(new ProductBuilder(productProxy).build());
            return true;
        } else {
            throw new EntityValidationException(validationResult.getErrorMsg());
        }
    }

    public boolean deleteProduct(int id){
        return productRepository.delete(id);
    }

}
