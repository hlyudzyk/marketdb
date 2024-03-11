package persistence.models;


import businesslogic.validation.proxies.ClientProxy;

public class Client extends Model {
    private String name;
    private String email;

    private Client(ClientBuilder clientBuilder) {
        this.setId(clientBuilder.id);
        this.name = clientBuilder.name;
        this.email = clientBuilder.email;
    }


    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public static class ClientBuilder {
        private int id;
        private String name;
        private String email;

        public ClientBuilder() {}

        public ClientBuilder(ClientProxy clientProxy){
            this.name = clientProxy.getName();
            this.email = clientProxy.getEmail();
        }

        public ClientBuilder id(int id) {
            this.id = id;
            return this;
        }

        public ClientBuilder name(String name) {
            this.name = name;
            return this;
        }

        public ClientBuilder email(String email) {
            this.email = email;
            return this;
        }

        public Client build() {
            return new Client(this);
        }
    }
}
