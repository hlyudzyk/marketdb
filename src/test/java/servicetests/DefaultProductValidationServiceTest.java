package servicetests;

import businesslogic.validation.ValidationResult;
import businesslogic.validation.proxies.ProductProxy;
import businesslogic.validation.validationservices.impl.DefaultProductValidationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Optional;
import persistence.models.Category.CategoryBuilder;
import persistence.models.Manufacturer;
import persistence.models.Manufacturer.ManufacturerBuilder;
import persistence.models.Product;
import persistence.models.Product.ProductBuilder;
import persistence.repositories.CategoryRepository;
import persistence.repositories.ManufacturerRepository;
import persistence.repositories.ProductRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class DefaultProductValidationServiceTest {

    @Mock
    private ProductRepository productDao;

    @Mock
    private ManufacturerRepository manufacturerDao;

    @Mock
    private CategoryRepository categoryDao;

    @InjectMocks
    private DefaultProductValidationService productValidationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testValidate_WithValidProduct() {
        ProductProxy productProxy = new ProductProxy("Test Product", 100.0, 1, 1);

        when(productDao.findProductByName("Test Product")).thenReturn(Optional.empty());
        when(manufacturerDao.findOneById(1)).thenReturn(Optional.of(new ManufacturerBuilder().build()));
        when(categoryDao.findOneById(1)).thenReturn(Optional.of(new CategoryBuilder().build()));

        ValidationResult result = productValidationService.validate(productProxy);

        assertEquals(true, result.isValid());
    }

    @Test
    void testValidate_WithInvalidProductName() {
        ProductProxy productProxy = new ProductProxy("Duplicate Product", 100.0, 1, 1);

        when(productDao.findProductByName("Duplicate Product")).thenReturn(Optional.of(new ProductBuilder().build()));
        when(manufacturerDao.findOneById(1)).thenReturn(Optional.of(new ManufacturerBuilder().build()));
        when(categoryDao.findOneById(1)).thenReturn(Optional.of(new CategoryBuilder().build()));

        ValidationResult result = productValidationService.validate(productProxy);

        assertEquals(false, result.isValid());
        assertEquals("Product name [Duplicate Product] is already taken", result.getErrorMsg());
    }

}
