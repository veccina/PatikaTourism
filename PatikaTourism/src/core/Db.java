package core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Db {
    private static Db instance = null;
    private Connection connection = null;
    private static final String Db_Url = "jdbc:mysql://localhost/patikatourism";
    private static final String DbUsername = "root";
    private static final String DbPass = "mysql";

    private Db() {
        try {
            connection = DriverManager.getConnection(Db_Url, DbUsername, DbPass);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private Connection getConnection() {
        return connection;
    }

    public static Connection getInstance() {
        try {
            if (instance == null || instance.getConnection().isClosed()) {
                instance = new Db();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return instance.getConnection();
    }
}
