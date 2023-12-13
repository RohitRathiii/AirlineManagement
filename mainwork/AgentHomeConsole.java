package console_interaction;
import java.sql.Timestamp;
import java.util.*;

import database_connectivity.*;
import model.*;
import utils.TimeFormat;

public class AgentHomeConsole{
    static Scanner scn = new Scanner(System.in);

    public static void addFlight(){
        System.out.println();
        System.out.println("Enter the flight details:-\n");
        System.out.print("Enter the flight number: ");
        int flightNo = scn.nextInt();
        scn.nextLine();
        System.out.print("Enter the departure city: ");
        String departureCity = scn.nextLine();
        System.out.print("Enter the arrival city: ");
        String arrivalCity = scn.nextLine();

        FlightJDBC.addFlight(new Flight(flightNo, departureCity, arrivalCity));
    }
    
    public static void removeFlight(){
        System.out.println();
        System.out.println("Enter the flight details:-\n");
        System.out.print("Enter the flight number: ");
        int flightNo = scn.nextInt();
        scn.nextLine();

        FlightJDBC.removeFlight(flightNo);
    }

    public static void viewFlights(){
        System.out.println();
        FlightJDBC.searchFlights();
    }

    public static void addSchedule() {
        System.out.println();
        System.out.println("Enter the schedule details:-\n");

        System.out.print("Enter the flight number: ");
        int flightNo = scn.nextInt();

        if (!FlightJDBC.checkFlight(flightNo)) {
            System.out.println("\n\tThe flight is currently unavailable!");
            return;
        }

        scn.nextLine(); // Consume the newline character left by nextInt()

        // Validate and parse departure time
        Timestamp startTime = null;
        boolean validDepartureTime = false;
        while (!validDepartureTime) {
            System.out.print("Enter the departure time (\"yyyy-MM-dd HH:mm:ss\"): ");
            String departureTimeString = scn.nextLine();
            startTime = TimeFormat.stringToTimestamp(departureTimeString);

            if (startTime != null) {
                validDepartureTime = true;
            } else {
                System.out.println("Invalid time format. Please use the format \"yyyy-MM-dd HH:mm:ss\".");
            }
        }

        // Validate and parse arrival time
        Timestamp endTime = null;
        boolean validArrivalTime = false;
        while (!validArrivalTime) {
            System.out.print("Enter the arrival time (\"yyyy-MM-dd HH:mm:ss\"): ");
            String arrivalTimeString = scn.nextLine();
            endTime = TimeFormat.stringToTimestamp(arrivalTimeString);

            if (endTime != null && !endTime.equals(startTime)) {
                validArrivalTime = true;
            } else {
                System.out.println("Invalid arrival time. Arrival time cannot be the same as departure time.");
            }
        }

        System.out.print("Enter the available seats: ");
        int availableSeats = scn.nextInt();

        ScheduleJDBC.addSchedule(new Schedule(flightNo, startTime, endTime, availableSeats));
    }


    public static void removeSchedule(){
        System.out.println();
        System.out.println("Enter the schedule details:-\n");
        System.out.print("Enter the schedule number: ");
        int scheduleId = scn.nextInt();
        ScheduleJDBC.removeSchedule(scheduleId);
    }

    public static void viewSchedule(){
        System.out.println();
        ScheduleJDBC.viewSchedule();
    }

    public static void viewBookings(){
        System.out.println();
        BookingJDBC.viewAllBookings();
    }
    
    public static void runAgentConsole(Agent agent){
        Scanner scn = new Scanner(System.in);
        
        System.out.println();
        System.out.println("\n\nWelcome Agent " + agent.getUsername());

        boolean loggedIn = true;
        while(loggedIn){
            System.out.println();
            System.out.println("Select from the options:- ");
            System.out.println("1. Add flight");
            System.out.println("2. Remove flight");
            System.out.println("3. View flights");
            System.out.println("4. Add Schedule");
            System.out.println("5. Remove Schedule");
            System.out.println("6. View Schedule");
            System.out.println("7. View Bookings");
            System.out.println("8. Logout");

            System.out.print("Enter your option: ");
            int option = scn.nextInt();
            switch(option){
                case 1:
                    addFlight();
                    break;
                case 2:
                    removeFlight();
                    break;
                case 3:
                    viewFlights();
                    break;
                case 4:
                    addSchedule();
                    break;
                case 5:
                    removeSchedule();
                    break;
                case 6:
                    viewSchedule();
                    break;
                case 7:
                    viewBookings();
                    break;
                case 8:
                    loggedIn = false;
                    break;
                default:
                    System.out.println("Invalid option. Try again...");
            }
        }
    }
}
