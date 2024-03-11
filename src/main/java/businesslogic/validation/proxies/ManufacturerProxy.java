package businesslogic.validation.proxies;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class ManufacturerProxy {
    String name;
    String country;
}
