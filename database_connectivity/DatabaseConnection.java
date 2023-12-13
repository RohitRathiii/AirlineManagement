package database_connectivity;

import java.sql.*;

public class DatabaseConnection{
    private static final String URL = "jdbc:mysql://localhost:3306/flight_booking_system";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "arpit1234";

    private static Connection connection;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            } catch (ClassNotFoundException | SQLException e) {
                System.out.println("Database connection error");
                e.printStackTrace();
            }
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                System.out.println("Error closing database connection");
                e.printStackTrace();
            }
        }
    }
}
