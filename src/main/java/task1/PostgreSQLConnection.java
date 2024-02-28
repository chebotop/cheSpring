package task1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgreSQLConnection {
    public static Connection getConnection() {
        String url = "jdbc:postgresql://185.240.103.64:5432/db_convert";
        String user = "django";
        String password = System.getenv("DATABASE_PASSWORD"); // 8619204 (все равно в ней ничего ценного, а права юзера ограничены)

        Connection conn = null;

        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(url, user, password);
        }   catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }

        return conn;
    }

    public static void main (String[] args) {
            Connection conn = getConnection();
            if (conn != null) {
                System.out.println("Connected to the PostgreSQL server successfully.");
            } else {
                System.out.println("Failed to make connection");
        }
    }
}
