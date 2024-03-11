package persistence;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.stream.Collectors;
import persistence.exceptions.PersistenceException;

/** Temp class, using only for initialization DB for learn/test new features. */
public final class DbInitialization {

    private DbInitialization() {}

    /** Read ddl.sql and dml.sql from resources and apply it to database. */
    public static void apply() {
        getSqlFromFile("src/main/resources/db/ddl.sql");
        try (Connection connection = ConnectionPool.get();
            Statement statementForDDL = connection.createStatement()){
              //  Statement statementForDML = connection.createStatement()) {
            statementForDDL.execute(getSqlFromFile("src/main/resources/db/ddl.sql"));
            //statementForDML.execute(getSqlFromFile("src/main/resources/db/dml.sql"));
        } catch (SQLException e) {
            e.printStackTrace();
            throw new PersistenceException("Помилка, під час створення та заповнення бази даних.");

        }
    }

    private static String getSqlFromFile(final String resourceName) {
        /*Supplier<URI> uriSupplier =
                () -> {
                    URL url = ConnectionPool.class.getClassLoader().getResource(resourceName);
                    if (url == null) {
                        throw new NullPointerException("Відсутній SQL файл");
                    }
                    try {
                        return url.toURI();
                    } catch (URISyntaxException e) {
                        throw new PersistenceException(
                                "Помилка формування URI посилання до ресурсного файлу.");
                    }
                };*/

        try (var stream = Files.lines(Path.of("src","main","resources","db",resourceName), StandardCharsets.UTF_8)) {
            String result = stream.collect(Collectors.joining("\n"));
            return result;
        } catch (IOException e) {
            throw new PersistenceException("Помилка, під час зчитування DLL чи DML файлу.");
        }
    }
}