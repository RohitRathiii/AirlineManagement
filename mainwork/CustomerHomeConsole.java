package console_interaction;
import java.util.Scanner;

import database_connectivity.*;
import model.*;

public class CustomerHomeConsole{
    static Scanner scn = new Scanner(System.in);
    static Customer currentCustomer;

    public static void viewSchedule(){
        System.out.println();
        ScheduleJDBC.viewSchedule();;
    }

    public static void bookTickets(){
        System.out.println();
        System.out.println("Enter the details:-\n");
        System.out.print("Enter the schedule ID: ");
        int scheduleId = scn.nextInt();
        System.out.print("Enter the number of tickets: ");
        int noOfTickets = scn.nextInt();

        BookingJDBC.addBooking(new Booking(currentCustomer.getUserId(), scheduleId, noOfTickets));   
    }

    public static void cancelBooking(){
        System.out.println();
        System.out.println("Enter the details:-\n");
        System.out.print("Enter the booking ID: ");
        int bookingId = scn.nextInt();

        BookingJDBC.cancelBooking(bookingId);  
    }

    public static void viewMyBookings(){
        System.out.println();
        BookingJDBC.viewMyBookings(currentCustomer.getUserId());
    }


    public static void runCustomerConsole(Customer customer) {
        System.out.println();
        currentCustomer = customer;
        System.out.println("\n\nWelcome Customer " + customer.getUsername());
        
        
        boolean loggedIn = true;
        while(loggedIn){
            System.out.println();
            System.out.println("Select from the options:- ");
            System.out.println("1. View Flight Schedule");
            System.out.println("2. Book tickets");
            System.out.println("3. Cancel Booking");
            System.out.println("4. View my bookings");
            System.out.println("5. Logout");
    
            System.out.print("Enter your option: ");
            int option = scn.nextInt();
            switch(option){
                case 1:
                    viewSchedule();
                    break;
                case 2:
                    bookTickets();
                    break;
                case 3:
                    cancelBooking();
                    break;
                case 4:
                    viewMyBookings();
                    break;
                case 5:
                    loggedIn = false;
                    break;
                default:
                    System.out.println("Invalid option. Try again...");
            }
        }
    }
}
