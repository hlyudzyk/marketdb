package businesslogic.services;

import businesslogic.validation.ValidationResult;
import businesslogic.validation.exceptions.EntityValidationException;
import businesslogic.validation.proxies.SaleProxy;
import businesslogic.validation.validationservices.SaleValidationService;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import persistence.models.Sale;
import persistence.models.Sale.SaleBuilder;
import persistence.repositories.SaleRepository;

@Service
@AllArgsConstructor
public class SaleService {
    private final SaleRepository saleRepository;
    private final SaleValidationService saleValidationService;

    public void createSale(int productId, int clientId,int quantity, LocalDate saleDate) throws EntityValidationException{
        SaleProxy saleProxy = new SaleProxy(productId,clientId,quantity,saleDate);
        ValidationResult validationResult = saleValidationService.validate(saleProxy);

        if(validationResult.isValid()){
            saleRepository.save(new SaleBuilder(saleProxy).build());
        }
        else {
            throw new EntityValidationException(validationResult.getErrorMsg());
        }


    }

}
