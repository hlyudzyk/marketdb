package businesslogic.validation.validationservices.impl;

import businesslogic.validation.ValidationResult;
import businesslogic.validation.ValidationStep;
import businesslogic.validation.proxies.SaleProxy;
import businesslogic.validation.validationservices.SaleValidationService;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import persistence.models.Client;
import persistence.models.Product;
import persistence.repositories.ClientRepository;
import persistence.repositories.ProductRepository;


@Service
@AllArgsConstructor
public class DefaultSaleValidationService implements SaleValidationService {

    private final ClientRepository clientRepository;
    private final ProductRepository productRepository;

    @Override
    public ValidationResult validate(SaleProxy saleProxy) {
        return new QuantityValidationStep()
            .linkWith(new DateValidationStep())
            .linkWith(new ForeignKeysValidationStep(clientRepository,productRepository))
            .validate(saleProxy);
    }

    @AllArgsConstructor
    private static class ForeignKeysValidationStep extends ValidationStep<SaleProxy>{
        private final ClientRepository clientRepository;
        private final ProductRepository productRepository;

        @Override
        public ValidationResult validate(SaleProxy saleProxy) {
            if (clientRepository.findOneById(saleProxy.getClientId()).isEmpty()) {
                return ValidationResult.invalid(String.format("Manufacturer with id=[%s] does not exist", saleProxy.getClientId()));
            }
            if (productRepository.findOneById(saleProxy.getProductId()).isEmpty()) {
                return ValidationResult.invalid(String.format("Category with id=[%s] does not exist", saleProxy.getProductId()));
            }

            return checkNext(saleProxy);
        }
    }

    @AllArgsConstructor
    private static class QuantityValidationStep extends ValidationStep<SaleProxy> {

        @Override
        public ValidationResult validate(SaleProxy saleProxy) {
            int quantity = saleProxy.getQuantity();

            if (quantity <= 0) {
                return ValidationResult.invalid("Quantity must be greater than 0");
            }

            return checkNext(saleProxy);
        }
    }

    @AllArgsConstructor
    private static class DateValidationStep extends ValidationStep<SaleProxy> {

        @Override
        public ValidationResult validate(SaleProxy saleProxy) {
            LocalDate date = saleProxy.getDate();
            if (date == null) {
                return ValidationResult.invalid("Date is required");
            }
            return checkNext(saleProxy);
        }
    }
}
