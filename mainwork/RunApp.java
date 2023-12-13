package console_interaction;

import java.util.*;

public class RunApp{
    static void runApp(){
        Scanner scn = new Scanner(System.in);

        System.out.println("\n");
        System.out.println("Welcome to Flight Ticket Booking System");

        boolean running = true;
        while(running){
            System.out.println("\n");
            System.out.println("Select from the below options:- \n");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit \n");

            System.out.print("Enter your choice: ");
            int option = scn.nextInt();
            switch(option){
                case 1:
                    RegisterConsole.register();
                    break;
                case 2:
                    LoginConsole.login();
                    break;
                case 3:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option selected. Try again:-\n");
            }
        }
        
    }

    public static void main(String[] args) {
        RunApp.runApp();
    }
}