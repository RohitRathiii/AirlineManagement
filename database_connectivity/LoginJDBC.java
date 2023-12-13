package database_connectivity;
import java.sql.*;

import model.*;

public class LoginJDBC{
    static String successfulLogin = "\n\tUser logged in successfully!";
    static String failedLogin = "\n\tInvalid username or password. Failed to login the user!";
    static String connectionError = "\n\tDatabase connection error!";

    static Connection connection = null;

    private static Customer createCustomerSession(ResultSet resultSet) throws SQLException{
        int userId = resultSet.getInt("userId");
        String username = resultSet.getString("username");
        String password = resultSet.getString("password");
        String emailAddress = resultSet.getString("emailAddress");

        String firstName = resultSet.getString("firstName");
        String lastName = resultSet.getString("lastName");
        String address = resultSet.getString("address");
        String mobileNo = resultSet.getString("mobileNo");

        return new Customer(userId, username, password, emailAddress, firstName, lastName, address, mobileNo);
    }

    private static Agent createAgentSession(ResultSet resultSet) throws SQLException{
        int userId = resultSet.getInt("userId");
        String username = resultSet.getString("username");
        String password = resultSet.getString("password");
        String emailAddress = resultSet.getString("emailAddress");

        String roleName = resultSet.getString("roleName");
        AgentRole role = null;
        if(roleName.equals("Head")) 
            role = AgentRole.HEAD;
        else
            role = "Manager".equals(roleName) ? AgentRole.MANAGER : AgentRole.SUPERVISOR;
        return new Agent(userId, username, password, emailAddress, role);
    }

    public static User loginUser(String username, String password, int uType){
        if(connection == null) 
            connection = DatabaseConnection.getConnection();
        
        if(connection != null){
            try{
                String userType = uType == 1 ? "Customer" : "Agent";
                String sql = "SELECT * FROM User INNER JOIN " + userType + " ON User.userId = " + userType + " .id AND username = ? AND password = ?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, username);
                statement.setString(2, password);

                // Execute the statement
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()) {
                    System.out.println(successfulLogin);
                    return uType == 1 ? createCustomerSession(resultSet) : createAgentSession(resultSet);
                } else {
                    System.out.println(failedLogin);
                    return null;
                }
            } catch (SQLException e) {
                System.out.println(connectionError);
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }
}
