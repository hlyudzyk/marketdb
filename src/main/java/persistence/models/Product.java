package persistence.models;

import businesslogic.validation.proxies.ProductProxy;
import lombok.Value;

@Value
public class Product extends Model {
    String name;
    double price;
    int manufacturerId;
    int categoryId;

    private Product(ProductBuilder productBuilder) {
        this.setId(productBuilder.id);
        this.name = productBuilder.name;
        this.manufacturerId = productBuilder.manufacturerId;
        this.categoryId = productBuilder.categoryId;
        this.price = productBuilder.price;
    }

    public static class ProductBuilder {
        private int id;
        private String name;
        private int categoryId;
        private int manufacturerId;
        private double price;

        public ProductBuilder() {}

        public ProductBuilder(ProductProxy productProxy){
            this.name = productProxy.getName();
            this.price = productProxy.getPrice();
            this.categoryId = productProxy.getCategoryId();
            this.manufacturerId = productProxy.getManufacturerId();
        }
        public ProductBuilder id(int id) {
            this.id = id;
            return this;
        }

        public ProductBuilder name(String name) {
            this.name = name;
            return this;
        }

        public ProductBuilder categoryId(int categoryId) {
            this.categoryId = categoryId;
            return this;
        }

        public ProductBuilder manufacturerId(int manufacturerId) {
            this.manufacturerId = manufacturerId;
            return this;
        }

        public ProductBuilder price(double price) {
            this.price = price;
            return this;
        }

        public Product build() {
            return new Product(this);
        }
    }
}
