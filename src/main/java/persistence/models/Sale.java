package persistence.models;

import businesslogic.validation.proxies.SaleProxy;
import java.time.LocalDate;

public class Sale extends Model {
    private Product product;
    private Client client;
    private int productId;
    private int clientId;
    private int quantity;
    private LocalDate date;

    private Sale(SaleBuilder saleBuilder) {
        this.setId(saleBuilder.id);
        this.product = saleBuilder.product;
        this.client = saleBuilder.client;
        this.productId = saleBuilder.productId;
        this.clientId = saleBuilder.clientId;
        this.quantity = saleBuilder.quantity;
        this.date = saleBuilder.sale_date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public Client getClient() {
        return client;
    }

    public int getProductId() {
        return productId;
    }

    public int getClientId() {
        return clientId;
    }

    public int getQuantity() {
        return quantity;
    }

    public LocalDate getDate() {
        return date;
    }

    public static class SaleBuilder {
        private int id;
        private Product product;
        private Client client;
        private int productId;
        private int clientId;
        private int quantity;
        private LocalDate sale_date;

        public SaleBuilder() {}
        public SaleBuilder(SaleProxy saleProxy){
            this.clientId = saleProxy.getClientId();
            this.productId = saleProxy.getProductId();
            this.quantity = saleProxy.getQuantity();
            this.sale_date = saleProxy.getDate();
        }

        public SaleBuilder id(int id) {
            this.id = id;
            return this;
        }

        public SaleBuilder product(Product product) {
            this.product = product;
            return this;
        }

        public SaleBuilder client(Client client) {
            this.client = client;
            return this;
        }

        public SaleBuilder quantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        public SaleBuilder date(LocalDate date) {
            this.sale_date = date;
            return this;
        }

        public Sale build() {
            return new Sale(this);
        }
    }
}
