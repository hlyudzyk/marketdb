package persistence.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import persistence.ConnectionPool;
import persistence.exceptions.EntityNotFoundException;
import persistence.exceptions.PersistenceException;
import persistence.models.Model;

public abstract class Repository<M extends Model> {
    final String findAllSql = "SELECT * FROM %s".formatted(getTableName());
    final String findByIdSql = "%s WHERE id = ?".formatted(findAllSql);

    public Optional<M> findOneById(int id){
        try (Connection connection = ConnectionPool.get();
            PreparedStatement statement = connection.prepareStatement(findByIdSql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            M model = null;
            if (resultSet.next()) {
                model = buildEntity(resultSet);
            }
            return Optional.ofNullable(model);
        } catch (SQLException e) {
            throw new EntityNotFoundException("При знаходженні запису по id.");
        }
    }

    public List<M> findAll() {
        try (var connection = ConnectionPool.get();
            var statement = connection.prepareStatement(findAllSql)) {
            var resultSet = statement.executeQuery();
            List<M> entities = new ArrayList<>(resultSet.getFetchSize());
            while (resultSet.next()) {
                entities.add(buildEntity(resultSet));
            }
            return entities;
        } catch (SQLException e) {
            throw new PersistenceException("При знаходженні всіх записів");
        }
    }

    public boolean delete(final int id) {
        final String DELETE_SQL = "DELETE FROM %s WHERE id = ?;".formatted(getTableName());

        try (Connection connection = ConnectionPool.get();
            PreparedStatement statement = connection.prepareStatement(DELETE_SQL)) {
            statement.setInt(1, id);

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new PersistenceException("Помилка при операції видалення рядка таблиці.");
        }
    }
    public abstract boolean update(final M model);
    public abstract M save(M model);

    protected abstract M buildEntity(final ResultSet resultSet);
    protected abstract String getTableName();

}
