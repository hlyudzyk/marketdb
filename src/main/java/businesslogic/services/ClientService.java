package businesslogic.services;

import businesslogic.validation.ValidationResult;
import businesslogic.validation.exceptions.EntityValidationException;
import businesslogic.validation.proxies.ClientProxy;
import businesslogic.validation.validationservices.ClientValidationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import persistence.repositories.ClientRepository;
import persistence.models.Client.ClientBuilder;

@Service
@AllArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;
    private final ClientValidationService clientValidationService;
    public void createClient(String name, String email){
        ClientProxy clientProxy = new ClientProxy(name,email);
        ValidationResult validationResult = clientValidationService.validate(clientProxy);

        if(validationResult.isValid()){
            clientRepository.save(new ClientBuilder(clientProxy).build());
        } else {
            throw new EntityValidationException(validationResult.getErrorMsg());
        }

    }
}
