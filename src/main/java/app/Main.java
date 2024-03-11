package app;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import persistence.repositories.ProductRepository;
import persistence.models.Product;

public class Main {

    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(ProjectConfig.class);
        ProductRepository productRepository = context.getBean(ProductRepository.class);

        for(Product pr:productRepository.findAll()){
            System.out.println(pr.getId() + "\t" + pr.getName() + "\t" + pr.getCategoryId() + "\t"+
                    pr.getManufacturerId());
        }

    }
}
