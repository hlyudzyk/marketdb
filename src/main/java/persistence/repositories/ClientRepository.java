package persistence.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import persistence.ConnectionPool;
import persistence.exceptions.NoResultException;
import persistence.exceptions.PersistenceException;
import persistence.models.Client;
import persistence.models.Client.ClientBuilder;


@org.springframework.stereotype.Repository
public class ClientRepository extends Repository<Client> {

    private static final String SAVE_SQL =
        """
  INSERT INTO clients(name,email)
  VALUES (?,?);
  """;

    private static final String UPDATE_SQL =
        """
  UPDATE clients
     SET name = ?,
     email = ?
   WHERE id = ?;
  """;

    private static final String FIND_BY_USERNAME_SQL =
        "SELECT * FROM clients WHERE name = ?;";

    private static final String FIND_BY_EMAIL_SQL = "SELECT * FROM clients WHERE email = ?";

    private ClientRepository(){

    }

    @Override
    public boolean update(Client client) {
        try (Connection connection = ConnectionPool.get();
            PreparedStatement statement = connection.prepareStatement(UPDATE_SQL)) {
            statement.setString(1, client.getName());
            statement.setString(2, client.getEmail());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new PersistenceException(
                "При оновленні запису");
        }
    }

    @Override
    public Client save(Client client) {
        try (Connection connection = ConnectionPool.get();
            PreparedStatement statement =
                connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, client.getName());
            statement.setString(2, client.getEmail());

            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                client.setId(generatedKeys.getInt("id"));
            }
            return client;
        } catch (SQLException e) {
            throw new PersistenceException(
                "При збереженні запису");
        }
    }

    public Optional<Client> findByUsername(String username) {
        try (Connection connection = ConnectionPool.get();
            PreparedStatement statement = connection.prepareStatement(FIND_BY_USERNAME_SQL)) {
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(buildEntity(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new PersistenceException("Error while finding client by username: " + e.getMessage());
        }
        return Optional.empty();
    }

    public Optional<Client> findByEmail(String email) {
        try (Connection connection = ConnectionPool.get();
            PreparedStatement statement = connection.prepareStatement(FIND_BY_EMAIL_SQL)) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(buildEntity(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new PersistenceException("Error while finding client by email: " + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    protected Client buildEntity(ResultSet resultSet) {
        try {
            return new ClientBuilder()
                .id(resultSet.getInt("id"))
                .name(resultSet.getString("name"))
                .email(resultSet.getString("email"))
                .build();
        } catch (SQLException e) {
            throw new NoResultException(
                "Не вдалось отримати ResultSet");
        }
    }

    @Override
    protected String getTableName() {
        return "clients";
    }

    private static class ClientDaoHolder {
        public static final ClientRepository HOLDER_INSTANCE = new ClientRepository();
    }

    public static ClientRepository getInstance() {
        return ClientDaoHolder.HOLDER_INSTANCE;
    }
}
