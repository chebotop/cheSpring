package task1;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/** The class implements getting data from database */
public class Task1RealDataTest {
    private static Connection conn = null;
    private static Statement statement = null;

    public static void main(String[] args) {
        try {
            fetchData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Getting processed data from remote PostgreSQL
     * @return Array
     * @throws SQLException
     */
    public static List<String[]> fetchData() throws SQLException {
        try {
            // Hold connection
            conn = PostgreSQLConnection.getConnection();
            statement = conn.createStatement();
            // Method call to get data with a strict format
            return rawData();
            // Close connection
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    /** Processing data from the database
     * @return Columns "surname" and "salary"*/
    public static List<String[]> rawData() throws SQLException {
        List<String[]> list = new ArrayList<>();
        ResultSet resultSet = null;
        try {
            String sqlQuery = "SELECT surname, salary, department FROM JavaTask1_table";
            resultSet = statement.executeQuery(sqlQuery);

            while (resultSet.next()) {
                String surname = resultSet.getString("surname");
                String salary = resultSet.getString("salary");
                String department = resultSet.getString("department");

                String[] rows = {surname, salary == null ? "null" : salary, department == null ? "" : department};
                list.add(rows);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

}
