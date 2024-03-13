package persistence.models;

import businesslogic.validation.proxies.ManufacturerProxy;

public class Manufacturer extends Model {
    private String name;
    private String country;

    private Manufacturer(ManufacturerBuilder manufacturerBuilder) {
        this.setId(manufacturerBuilder.id);
        this.name = manufacturerBuilder.name;
        this.country = manufacturerBuilder.country;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public static class ManufacturerBuilder {
        private int id;
        private String name;
        private String country;

        public ManufacturerBuilder() {}

        public ManufacturerBuilder(ManufacturerProxy manufacturerProxy){
            this.name = manufacturerProxy.getName();
            this.country = manufacturerProxy.getCountry();
        }
        public ManufacturerBuilder id(int id) {
            this.id = id;
            return this;
        }

        public ManufacturerBuilder name(String name) {
            this.name = name;
            return this;
        }

        public ManufacturerBuilder country(String country) {
            this.country = country;
            return this;
        }

        public Manufacturer build() {
            return new Manufacturer(this);
        }
    }
}
