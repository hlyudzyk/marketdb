package businesslogic.validation.proxies;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Value;
import persistence.models.Client;
import persistence.models.Product;

@Value
@AllArgsConstructor
public class SaleProxy {
    int productId;
    int clientId;
    int quantity;
    LocalDate date;
}
