
package console_interaction;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.Console;
import model.*;
import database_connectivity.RegisterJDBC;

public class RegisterConsole {
    static void register() {
        Scanner scn = new Scanner(System.in);

        System.out.println("");
        System.out.println("Registration:- \n");
        System.out.println("Which of the following user are you?");
        System.out.println("1. Customer");
        System.out.println("2. Agent");

        System.out.print("\nEnter your choice: ");
        int option = scn.nextInt();
        scn.nextLine();

        if (option == 1) {
            // Customer registration
            System.out.println();
            System.out.print("Enter the username: ");
            String username = scn.nextLine();

            // Hide password while entering
            char[] passwordChars = readPassword("Enter the password: ");

            String password = new String(passwordChars);

            String emailAddress = "";
            boolean validEmail = false;

            // Validate email format
            while (!validEmail) {
                System.out.print("Enter your email address: ");
                emailAddress = scn.nextLine();
                validEmail = isValidEmail(emailAddress);

                if (!validEmail) {
                    System.out.println("Invalid email address format. Please enter a valid email address.");
                }
            }

            System.out.print("Enter your first name: ");
            String firstName = scn.nextLine();
            System.out.print("Enter your last name: ");
            String lastName = scn.nextLine();
            System.out.print("Enter your address details: ");
            String address = scn.nextLine();
            System.out.print("Enter your mobile number: ");
            String mobileNo = scn.nextLine();

            Customer customer = new Customer(0, username, password, emailAddress, firstName, lastName, address, mobileNo);
            RegisterJDBC.addCustomer(customer);
        } else {
            // Agent registration
            System.out.println();
            System.out.print("Enter the username: ");
            String username = scn.nextLine();

            // Hide password while entering
            char[] passwordChars = readPassword("Enter the password: ");

            String password = new String(passwordChars);

            String emailAddress = "";
            boolean validEmail = false;

            // Validate email format
            while (!validEmail) {
                System.out.print("Enter your email address: ");
                emailAddress = scn.nextLine();
                validEmail = isValidEmail(emailAddress);

                if (!validEmail) {
                    System.out.println("Invalid email address format. Please enter a valid email address.");
                }
            }

            System.out.print("Enter your role(1. Head,  2. Manager,  3. Supervisor): ");
            int roleNumber = scn.nextInt();

            Agent agent = new Agent(0, username, password, emailAddress,
                    roleNumber == 1 ? AgentRole.MANAGER : AgentRole.SUPERVISOR);
            RegisterJDBC.addAgent(agent);
        }
    }

    // Email format validation using regular expression
    private static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    // Method to read password with echoing disabled
    private static char[] readPassword(String prompt) {
        Console console = System.console();
        if (console != null) {
            // If running from the command line with Console support
            return console.readPassword(prompt);
        } else {
            // If running in an IDE or unsupported environment, use asterisk masking
            System.out.print(prompt);

            Scanner scanner = new Scanner(System.in);
            String password = scanner.nextLine();

            // Print asterisks in place of each character
            for (int i = 0; i < password.length(); i++) {
                System.out.print('*');
            }

            System.out.println();  // Move to the next line after masking
            return password.toCharArray();
        }
    }
}
