package console_interaction;

import java.util.Scanner;
import java.io.Console;
import database_connectivity.LoginJDBC;
import model.Agent;
import model.Customer;

public class LoginConsole {
    static void login() {
        Scanner scn = new Scanner(System.in);

        System.out.println();
        System.out.println("Login:- \n");

        System.out.print("Enter the username: ");
        String username = scn.nextLine();

        // Hide password while entering
        char[] passwordChars = readPassword("Enter the password: ");
        String password = new String(passwordChars);

        System.out.print("Enter the type of user(1. Customer,  2. Agent): ");
        int userType = scn.nextInt();

        if (userType == 1) {
            Customer customer = (Customer) LoginJDBC.loginUser(username, password, userType);
            if (customer != null)
                CustomerHomeConsole.runCustomerConsole(customer);
        } else {
            Agent agent = (Agent) LoginJDBC.loginUser(username, password, userType);
            if (agent != null)
                AgentHomeConsole.runAgentConsole(agent);
        }
    }

    // Method to read password with echoing disabled
    private static char[] readPassword(String prompt) {
        Console console = System.console();
        if (console != null) {
            return console.readPassword(prompt);
        } else {
            // If running in an IDE, fallback to using Scanner
            Scanner scanner = new Scanner(System.in);
            System.out.print(prompt);
            return scanner.nextLine().toCharArray();
        }
    }
}
