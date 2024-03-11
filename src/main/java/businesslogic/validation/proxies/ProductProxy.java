package businesslogic.validation.proxies;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class ProductProxy {
    String name;
    double price;
    int categoryId;
    int manufacturerId;
}
