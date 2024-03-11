package businesslogic.validation.validationservices.impl;

import businesslogic.validation.ValidationResult;
import businesslogic.validation.ValidationStep;
import businesslogic.validation.proxies.ClientProxy;
import businesslogic.validation.validationservices.ClientValidationService;
import java.util.Optional;
import java.util.regex.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import persistence.repositories.ClientRepository;
import persistence.models.Client;

@Service
@AllArgsConstructor
public class DefaultClientValidationService implements ClientValidationService {

    private final ClientRepository clientDao;

    @Override
    public ValidationResult validate(ClientProxy clientProxy) {
        return new UsernamePatternMatchValidationStep()
            .linkWith(new EmailValidationStep())
            .linkWith(new UsernameDuplicationValidationStep(clientDao))
            .linkWith(new EmailDuplicationValidationStep(clientDao))
            .validate(clientProxy);
    }

    @AllArgsConstructor
    private static class UsernameDuplicationValidationStep extends ValidationStep<ClientProxy> {

        private final ClientRepository clientDao;

        @Override
        public ValidationResult validate(ClientProxy clientProxy) {
            if (clientDao.findByUsername(clientProxy.getName()).isPresent()) {
                return ValidationResult.invalid(
                    String.format("Product name [%s] is already taken", clientProxy.getName()));
            }
            return checkNext(clientProxy);
        }
    }

    private static class UsernamePatternMatchValidationStep extends ValidationStep<ClientProxy> {

        private static final String USERNAME_REGEX = "[a-zA-Z0-9_]+";

        @Override
        public ValidationResult validate(ClientProxy clientProxy) {
            String username = clientProxy.getName();

            if (!username.matches(USERNAME_REGEX)) {
                return ValidationResult.invalid(
                    "Username must contain only letters, digits, or underscores");
            }

            return checkNext(clientProxy);
        }
    }

    private static class EmailValidationStep extends ValidationStep<ClientProxy> {
        private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

        @Override
        public ValidationResult validate(ClientProxy toValidate) {
            String email = toValidate.getEmail();

            if (!EMAIL_PATTERN.matcher(email).matches()) {
                return ValidationResult.invalid("Invalid email format");
            }

            return checkNext(toValidate);
        }
    }

    @AllArgsConstructor
    private static class EmailDuplicationValidationStep extends ValidationStep<ClientProxy> {

        private final ClientRepository clientDao;

        @Override
        public ValidationResult validate(ClientProxy clientProxy) {
            String email = clientProxy.getEmail();

            Optional<Client> existingClient = clientDao.findByEmail(email);
            if (existingClient.isPresent()) {
                return ValidationResult.invalid("Email [" + email + "] is already taken");
            }

            return checkNext(clientProxy);
        }
    }
}
