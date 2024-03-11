package persistence;

import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class  ConnectionPool {
    private static final String DB_PATH = Path.of(".", "src","main","resources","db", "market").toString();
    private static final String URL = "jdbc:h2:file:%s".formatted(DB_PATH);
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "admin";
    public static Connection get() throws SQLException {
        return DriverManager.getConnection(URL,USERNAME,PASSWORD);
    }

}
