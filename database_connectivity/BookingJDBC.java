package database_connectivity;

import java.sql.*;

import com.mysql.cj.xdevapi.Result;

import model.*;

public class BookingJDBC {
    static String incorrectQuery = "\n\tDatabase query error";
    static String connectionError = "\n\tDatabase connection is null";
    
    static Connection connection = null;

    private static boolean reserveSeats(Booking booking) {
        if (connection == null)
            connection = DatabaseConnection.getConnection();

        if (connection != null) {
            try {
                String query = "SELECT availableSeats FROM Schedule WHERE scheduleId = ?";
                try (PreparedStatement statement = connection.prepareStatement(query)) {
                    statement.setInt(1, booking.getScheduleId());
                    try (ResultSet resultSet = statement.executeQuery()) {
                        if (resultSet.next()) {
                            int availableSeats = resultSet.getInt("availableSeats");
                            if (availableSeats >= booking.getNoOfTickets()) {
                                int remainingSeats = availableSeats - booking.getNoOfTickets();
                                if (remainingSeats >= 0) {
                                    String updateQuery = "UPDATE Schedule SET availableSeats = ? WHERE scheduleId = ?";
                                    try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                                        updateStatement.setInt(1, remainingSeats);
                                        updateStatement.setInt(2, booking.getScheduleId());
                                        int rowsUpdated = updateStatement.executeUpdate();
                                        return rowsUpdated > 0;
                                    }
                                } else {
                                    return false; // Insufficient seats available
                                }
                            } else {
                                return false; // Insufficient seats available
                            }
                        }
                    }
                }
            } catch (SQLException e) {
                System.out.println(incorrectQuery);
                e.printStackTrace();
            }
        } else {
            System.out.println(connectionError);
        }
        return false;
    }


    private static boolean unreserveSeats(int bookingId) {
        if (connection == null)
            connection = DatabaseConnection.getConnection();

        if (connection != null) {
            try {
                String query = "SELECT * FROM Booking WHERE bookingId = " + bookingId;

                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);

                if (!resultSet.next()) {
                    System.out.println("\n\tBooking ID not found. Cannot unreserve seats!");
                    return false;
                }

                int noOfTickets = resultSet.getInt("noOfTickets");
                int scheduleId = resultSet.getInt("scheduleId");

                String query2 = "SELECT availableSeats FROM Schedule where scheduleId = " + scheduleId;
                Statement statement2 = connection.createStatement();
                ResultSet resultSet2 = statement2.executeQuery(query2);

                if (!resultSet2.next()) {
                    System.out.println("\n\tError retrieving available seats. Cannot unreserve seats!");
                    return false;
                }

                int availableSeats = resultSet2.getInt("availableSeats");
                int updatedSeatsCount = noOfTickets + availableSeats;

                String query3 = "UPDATE Schedule SET availableSeats = " + updatedSeatsCount + " WHERE scheduleId = " + scheduleId;
                Statement statement3 = connection.createStatement();
                int rowsUpdated = statement3.executeUpdate(query3);

                if (rowsUpdated > 0)
                    return true;
                else
                    return false;

            } catch (SQLException e) {
                System.out.println(incorrectQuery);
                e.printStackTrace();
                return false;
            }
        } else {
            System.out.println(connectionError);
            return false;
        }
    }


    public static void addBooking(Booking booking){
        if(connection == null) 
            connection = DatabaseConnection.getConnection();
        
        if(connection != null){
            try {
                if(!reserveSeats(booking)){
                    System.out.println("\n\tNumber of seats available is insufficient!");
                    return;
                }

                String query = "INSERT INTO Booking (userId, scheduleId, noOfTickets) VALUES (?, ?, ?)";
                
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setInt(1, booking.getUserId());
                statement.setInt(2, booking.getScheduleId());
                statement.setInt(3, booking.getNoOfTickets());
                int rowsInserted = statement.executeUpdate();   
                if (rowsInserted > 0)
                    System.out.println("\n\tBooking successfully completed!");
                else
                    System.out.println("\n\tFailed to book the tickets!");
                } catch (SQLException e) {
                    System.out.println(incorrectQuery);
                e.printStackTrace();
            }
        }
        else{
            System.out.println(connectionError);
        }
    }

    public static void cancelBooking(int bookingId) {
        if (connection == null)
            connection = DatabaseConnection.getConnection();

        if (connection != null) {
            try {
                if (!unreserveSeats(bookingId)) {
                    System.out.println("\n\tCould not unreserve the seats!");
                    return;
                }

                String query = "DELETE FROM Booking WHERE bookingId = " + bookingId;

                Statement statement = connection.createStatement();
                int rowsDeleted = statement.executeUpdate(query);
                if (rowsDeleted > 0)
                    System.out.println("\n\tBooking cancelled successfully!");
                else
                    System.out.println("\n\tFailed to cancel the booking. Booking ID not found!");

            } catch (SQLException e) {
                if (e.getMessage().contains("Illegal operation on empty result set")) {
                    System.out.println("\n\tFailed to cancel the booking. Booking ID not found!");
                } else {
                    System.out.println(incorrectQuery);
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println(connectionError);
        }
    }


    public static void viewAllBookings(){
        if(connection == null) 
            connection = DatabaseConnection.getConnection();
            
        if(connection != null){
            try{
                String query = "SELECT * FROM Booking";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);   
                System.out.println("Booking ID\tUser ID\tSchedule ID\tNo. Of Tickets");
                while(resultSet.next()) {
                    int bookingId = resultSet.getInt("bookingId");
                    int userId = resultSet.getInt("userId");
                    int scheduleId = resultSet.getInt("scheduleId");
                    int noOfTickets = resultSet.getInt("noOfTickets");

                    System.out.println( bookingId + "\t" + userId + "\t" + scheduleId + "\t" + noOfTickets);
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

    public static void viewMyBookings(int userId){
        if(connection == null) 
            connection = DatabaseConnection.getConnection();
            
        if(connection != null){
            try{
                String query = "SELECT * FROM Booking WHERE userId = " + userId;
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);   
                System.out.println("Booking ID\tSchedule ID\tNo. Of Tickets");
                while(resultSet.next()) {
                    int bookingId = resultSet.getInt("bookingId");
                    int scheduleId = resultSet.getInt("scheduleId");
                    int noOfTickets = resultSet.getInt("noOfTickets");

                    System.out.println(bookingId + "\t" + scheduleId + "\t" + noOfTickets);
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
}
