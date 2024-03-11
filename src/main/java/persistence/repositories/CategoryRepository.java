package persistence.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import persistence.ConnectionPool;
import persistence.exceptions.NoResultException;
import persistence.exceptions.PersistenceException;
import persistence.models.Category;
import persistence.models.Category.CategoryBuilder;

@org.springframework.stereotype.Repository
public class CategoryRepository extends Repository<Category> {
    private static final String SAVE_SQL =
        """
  INSERT INTO categories(name)
  VALUES (?);
  """;
    private static final String UPDATE_SQL =
        """
  UPDATE categories
     SET name = ?
   WHERE id = ?;
  """;

    private CategoryRepository() {
    }

    @Override
    public boolean update(Category category) {
        try (Connection connection = ConnectionPool.get();
            PreparedStatement statement = connection.prepareStatement(UPDATE_SQL)) {
            statement.setString(1, category.getName());
            statement.setInt(2, category.getId());

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new PersistenceException(
                "При оновленні запису");
        }
    }
    @Override
    public Category save(Category category) {
        try (Connection connection = ConnectionPool.get();
            PreparedStatement statement =
                connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, category.getName());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                category.setId(generatedKeys.getInt("id"));
            }
            return category;
        } catch (SQLException e) {
            throw new PersistenceException(
                "При збереженні запису");
        }
    }

    @Override
    protected Category buildEntity(ResultSet resultSet) {
        try {
            return new CategoryBuilder()
                .id(resultSet.getInt("id"))
                .name(resultSet.getString("name"))
                .build();
        } catch (SQLException e) {
            throw new NoResultException(
                "Не вдалось отримати ResultSet");
        }
    }

    @Override
    protected String getTableName() {
        return "categories";
    }

    private static class CategoryDaoHolder {
        public static final CategoryRepository HOLDER_INSTANCE = new CategoryRepository();
    }

    public static CategoryRepository getInstance() {
        return CategoryDaoHolder.HOLDER_INSTANCE;
    }
}
