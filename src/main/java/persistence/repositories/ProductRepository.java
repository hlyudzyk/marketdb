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
import persistence.models.Product;

@org.springframework.stereotype.Repository
public class ProductRepository extends Repository<Product> {
    private static final String SAVE_SQL =
        """
  INSERT INTO products(name,category_id,manufacturer_id, price)
  VALUES (?,?,?,?);
  """;

    private static final String UPDATE_SQL =
        """
  UPDATE products
     SET name = ?, category_id = ?,
     manufacturer_id = ?,
     price = ?
   WHERE id = ?;
  """;

    private static final String FIND_BY_NAME_SQL =
        "SELECT * FROM products WHERE name = ?";

    @Override
    public boolean update(Product product) {
        try (Connection connection = ConnectionPool.get();
            PreparedStatement statement = connection.prepareStatement(UPDATE_SQL)) {
            statement.setString(1, product.getName());
            statement.setInt(2, product.getCategoryId()); // Updated to use categoryId
            statement.setInt(3, product.getManufacturerId()); // Updated to use manufacturerId
            statement.setDouble(4, product.getPrice());
            statement.setInt(5, product.getId());

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new PersistenceException(
                "При оновленні запису");
        }
    }

    @Override
    public Product save(Product product) {
        try (Connection connection = ConnectionPool.get();
            PreparedStatement statement =
                connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, product.getName());
            statement.setInt(2, product.getCategoryId()); // Updated to use categoryId
            statement.setInt(3, product.getManufacturerId()); // Updated to use manufacturerId
            statement.setDouble(4, product.getPrice());

            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                product.setId(generatedKeys.getInt("id"));
            }
            return product;
        } catch (SQLException e) {
            throw new PersistenceException(
                "При збереженні запису");
        }
    }

    @Override
    protected Product buildEntity(ResultSet resultSet) {
        try {
            return new Product.ProductBuilder()
                .id(resultSet.getInt("id"))
                .name(resultSet.getString("name"))
                .categoryId(resultSet.getInt("category_id")) // Updated to use categoryId
                .manufacturerId(resultSet.getInt("manufacturer_id")) // Updated to use manufacturerId
                .price(resultSet.getInt("price"))
                .build();
        } catch (SQLException e) {
            throw new NoResultException(
                "Не вдалось отримати ResultSet");
        }
    }

    public Optional<Product> findProductByName(String name) {
        try (Connection connection = ConnectionPool.get();
            PreparedStatement statement = connection.prepareStatement(FIND_BY_NAME_SQL)) {
            statement.setString(1, name);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(buildEntity(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new PersistenceException("Error while finding product by name: " + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    protected String getTableName() {
        return "products";
    }

    private static class ProductDaoHolder {
        public static final ProductRepository HOLDER_INSTANCE = new ProductRepository();
    }

    public static ProductRepository getInstance() {
        return ProductDaoHolder.HOLDER_INSTANCE;
    }
}
