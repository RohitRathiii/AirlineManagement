package database_connectivity;
import java.sql.*;

import model.Flight;

public class FlightJDBC{
    static String incorrectQuery = "\n\tDatabase query error";
    static String connectionError = "\n\tDatabase connection is null";
    
    static Connection connection = null;

    public static void addFlight(Flight flight) {
        if(connection == null) 
            connection = DatabaseConnection.getConnection();
        
        if(connection != null){
            try {
                String query = "INSERT INTO Flight VALUES (?, ?, ?)";
                
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setInt(1, flight.getFlightNo());
                statement.setString(2, flight.getDepartureCity());
                statement.setString(3, flight.getArrivalCity());
                int rowsInserted = statement.executeUpdate();   
                if (rowsInserted > 0)
                    System.out.println("\n\tFlight added successfully!");
                else
                    System.out.println("\n\tFailed to add the flight!");
                } catch (SQLException e) {
                    System.out.println(incorrectQuery);
                e.printStackTrace();
            }
        }
        else{
            System.out.println(connectionError);
        }
    }

    public static void removeFlight(int flightNo) {
        if (connection == null)
            connection = DatabaseConnection.getConnection();

        if (connection != null) {
            try {
                // Check if there are related schedules
                if (hasRelatedSchedules(flightNo)) {
                    System.out.println("Error: There are schedules associated with this flight. Remove schedules first.");
                    return;
                }

                // If no related schedules, proceed with deleting the flight
                String query = "DELETE FROM Flight WHERE flightNo = ?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setInt(1, flightNo);
                int rowsDeleted = statement.executeUpdate();
                if (rowsDeleted > 0)
                    System.out.println("\n\tRemoved the flight successfully!");
                else
                    System.out.println("\n\tFailed to remove the flight!");
            } catch (SQLException e) {
                System.out.println(incorrectQuery);
                e.printStackTrace();
            }
        } else {
            System.out.println(connectionError);
        }
    }

    // Helper method to check if there are related schedules
    private static boolean hasRelatedSchedules(int flightNo) throws SQLException {
        String query = "SELECT * FROM Schedule WHERE flightNo = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, flightNo);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next(); // Return true if there are related schedules
            }
        }
    }



    public static void searchFlights(){
        if(connection == null) 
        connection = DatabaseConnection.getConnection();
            
        if(connection != null){
            try{
                String query = "SELECT * FROM Flight";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);   
                System.out.println("Flight No.   Departure   Arrival");
                while(resultSet.next()) {
                    int flightNo = resultSet.getInt("flightNo");
                    String departureCity = resultSet.getString("departureCity");
                    String arrivalCity = resultSet.getString("arrivalCity");

                    System.out.println(flightNo + "           " + departureCity + "          " + arrivalCity);
                }
            } catch(Exception e){
                System.out.println(incorrectQuery);
                e.printStackTrace();
            }
        }
        else{
            System.out.println(connectionError);
        }
    }
    
    public static boolean checkFlight(int flightNo){
        if(connection == null) 
            connection = DatabaseConnection.getConnection();
            
        if(connection != null){
            try{
                String query = "SELECT * FROM Flight WHERE flightNo = " + flightNo;
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);   
                if(resultSet.next()) {
                    return true;
                }
            } catch(Exception e){
                System.out.println(incorrectQuery);
                e.printStackTrace();
                return false;
            }
        }
        else{
            System.out.println(connectionError);
        }
        return false;
    }
}
