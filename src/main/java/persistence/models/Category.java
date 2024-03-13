package persistence.models;

import businesslogic.validation.proxies.CategoryProxy;

public class Category extends Model {
    private String name;

    private Category(CategoryBuilder categoryBuilder) {
        this.setId(categoryBuilder.id);
        this.name = categoryBuilder.name;
    }

    public String getName() {
        return name;
    }

    public static class CategoryBuilder {
        private int id;
        private String name;

        public CategoryBuilder() {}
        public CategoryBuilder(CategoryProxy categoryProxy){
            this.name = categoryProxy.getName();
        }

        public CategoryBuilder id(int id) {
            this.id = id;
            return this;
        }

        public CategoryBuilder name(String name) {
            this.name = name;
            return this;
        }

        public Category build() {
            return new Category(this);
        }
    }
}

