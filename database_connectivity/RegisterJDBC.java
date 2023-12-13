package database_connectivity;
import java.sql.*;
import java.util.*;

import model.*;

public class RegisterJDBC{
    static String successfulTransaction = "\n\tUser added successfully!";
    static String failedTransaction = "\n\tFailed to add the user!";
    static String incorrectQuery = "\n\tDatabase query error";
    static String connectionError = "\n\tDatabase connection is null";
    
    static Connection connection = null;

    public static boolean addUser(User user){
        if(connection == null) 
            connection = DatabaseConnection.getConnection();
        
        if(connection != null){
            try {
                String query = "INSERT INTO User(userId, username, password, emailAddress) VALUES (?, ?, ?, ?)";
                
                int randUserId = new Random().nextInt(2000);
                user.setUserId(randUserId);
                
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setInt(1, randUserId);
                statement.setString(2, user.getUsername());
                statement.setString(3, user.getPassword());
                statement.setString(4, user.getEmailAddress());
                int rowsInserted = statement.executeUpdate();   
                if (rowsInserted > 0)
                    return true;
                else
                    return false;
            } catch (SQLException e) {
                System.out.println("Database query error");
                e.printStackTrace();
                return false;
            }
        }
        System.out.println(connectionError);
        return false;
    }
    
    public static void addCustomer(Customer customer){
        if(connection == null) 
        connection = DatabaseConnection.getConnection();
        
        boolean userAdded = addUser(customer);
        if(!userAdded)
            return;
        
        if(connection != null){
            try {
                String query = "INSERT INTO Customer(id, firstName, lastName, address, mobileNo) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setInt(1, customer.getUserId());
                statement.setString(2, customer.getFirstName());
                statement.setString(3, customer.getLastName());
                statement.setString(4, customer.getAddress());
                statement.setString(5, customer.getMobileNo());
                String mobileNo = customer.getMobileNo();
                if (mobileNo.length() <= 10) {
                    statement.setString(5, mobileNo);
                } else {
                    // Handle the case where mobileNo is too long
                    System.out.println("Error: Mobile number is too long. Please provide a valid mobile number.");
                    return;
                }
                int rowsInserted = statement.executeUpdate();   
                if(rowsInserted > 0)
                    System.out.println(successfulTransaction);
                else
                    System.out.println(failedTransaction);
            } catch (SQLException e) {
                System.out.println(incorrectQuery);
                e.printStackTrace();
            }
        } else {
            System.out.println(connectionError);
        }
    }
    
    public static void addAgent(Agent agent){
        if(connection == null) 
            connection = DatabaseConnection.getConnection();
        
        boolean userAdded = addUser(agent);
        if(!userAdded)
            return;

        if(connection != null){
            try {
                String query = "INSERT INTO Agent(id, roleName) VALUES (?, ?)";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setInt(1, agent.getUserId());
                statement.setString(2, agent.getRoleName().name());
                int rowsInserted = statement.executeUpdate();   
                if(rowsInserted > 0)
                    System.out.println(successfulTransaction);
                else
                    System.out.println(failedTransaction);
            } catch (SQLException e) {
                System.out.println(incorrectQuery);
                e.printStackTrace();
            }
        } else {
            System.out.println(connectionError);
        }
    }
}
