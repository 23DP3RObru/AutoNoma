package lv.rvt;

import java.util.Scanner;

public class Main {
    private static final String CARS_FILE = "data/masinas.csv";
    private static final String CREDENTIALS_FILE = "data/loginCredentials.csv";
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        App authSystem = new App(CREDENTIALS_FILE);
        CarService carService = new CarService(CARS_FILE);
        
        System.out.println("=== Car Rental System ===");
        

        User currentUser = handleAuthentication(scanner, authSystem);
        if (currentUser == null) {
            System.out.println("Authentication failed. Exiting...");
            scanner.close();
            return;
        }
        

        runMainMenu(scanner, carService, currentUser);
        scanner.close();
    }
    
    private static User handleAuthentication(Scanner scanner, App authSystem) {
        while (true) {
            System.out.println("\n1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");
            System.out.print("Choose option: ");
            
            String choice = scanner.nextLine();
            
            switch (choice) {
                case "1":
                    User user = authSystem.login();
                    if (user != null) return user;
                    break;
                case "2":
                    if (authSystem.register()) {
                        System.out.println("Please login with your new credentials");
                    }
                    break;
                case "3":
                    return null;
                default:
                    System.out.println("Invalid option!");
            }
        }
    }
    
    private static void runMainMenu(Scanner scanner, CarService carService, User user) {
        boolean running = true;
        while (running) {
            System.out.println("\n=== Main Menu ===");
            System.out.println("1. Search available cars");
            System.out.println("2. Check balance");
            System.out.println("3. Logout");
            System.out.print("Choose option: ");
            
            String choice = scanner.nextLine();
            
            switch (choice) {
                case "1":
                    carService.searchAndDisplayCars();
                    break;
                case "2":
                    System.out.printf("Your balance: %.2f EUR%n", user.getBalance());
                    break;
                case "3":
                    running = false;
                    System.out.println("Logged out successfully.");
                    break;
                default:
                    System.out.println("Invalid option!");
            }
        }
    }
}