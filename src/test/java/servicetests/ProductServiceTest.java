package servicetests;

import app.ProjectConfig;
import businesslogic.services.ProductService;
import businesslogic.validation.exceptions.EntityValidationException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ProductServiceTest {
    ProductService productService;

    @Before
    public void init(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ProjectConfig.class);
        productService = context.getBean(ProductService.class);
    }

    @Test(expected = EntityValidationException.class)
    public void saveWithValidationFailShouldThrowException(){
        productService.createProduct("product",-1,-2,0);
    }

    @Test
    public void successfulCreateProductShouldReturnTrue(){
        boolean isCreated = productService.createProduct("product",1,1,1);
        Assert.assertTrue(isCreated);
    }
}