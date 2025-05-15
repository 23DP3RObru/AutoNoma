package lv.rvt;

import java.util.Scanner;


public class Main {
    private static final String CARS_FILE = "data/masinas.csv";
    private static final String CREDENTIALS_FILE = "data/loginCredentials.csv";
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        App authSystem = new App(CREDENTIALS_FILE);
        CarService carService = new CarService(CARS_FILE);
        

        User currentUser = handleAuthentication(scanner, authSystem);
        if (currentUser == null) {
            System.out.println("Authentication " + ConsoleColors.RED + "failed" + ConsoleColors.RESET + ". Exiting...");
            scanner.close();
            return;
        }
        

        runMainMenu(scanner, carService, currentUser, authSystem);
        scanner.close();
    }
    
    private static User handleAuthentication(Scanner scanner, App authSystem) {
        while (true) {

            
            System.out.println("+----------------------------------------+");
            System.out.println("|                " + ConsoleColors.GREEN + "RE" + ConsoleColors.RESET + "-AUTO                 |");
            System.out.println("+----------------------------------------+");
            System.out.println("| To access our app you need to register |");
            System.out.println("| or log in if you have an already       |");
            System.out.println("| existing account.                      |");
            System.out.println("|                                        |");
            System.out.println("| Do you wish to register {R}            |");
            System.out.println("| or log in {L}? (Upper case sensitive!) |");
            System.out.print("  ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "L":
                    User user = authSystem.login();
                    if (user != null) return user;
                    break;
                case "R":
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
    
    private static void runMainMenu(Scanner scanner, CarService carService, User user, App authSystem) {
        boolean running = true;
        while (running) {
            System.out.println("\n+--------------------------------------------------------------------------------+");
            System.out.println("| " + ConsoleColors.GREEN + "RE" + ConsoleColors.RESET + "-Auto car rental services                                                    |");
            System.out.println("| Welcome back " + ConsoleColors.CYAN + user.getUsername() + ConsoleColors.RESET);
            System.out.println("|                                                                                |");
            System.out.println("+---------+-------------+------------+-------------------------------------------+");
            System.out.println("| 1. Auto |  2." + ConsoleColors.GREEN_BOLD_BRIGHT + " Balance" + ConsoleColors.RESET + " | 3." + ConsoleColors.RED_BOLD_BRIGHT +" Log out" + ConsoleColors.RESET + " | 4. Return Vehicle |");
            System.out.println("+---------+-------------+------------+-------------------------------------------+");
            System.out.println("|                                                                                |");
            System.out.println("| What section do you want to go to? (Auto {1}, Balance {2}, Log out {3}, Return {4}) |");
            System.out.print("| ");
            
            String choice = scanner.nextLine();
            
            switch (choice) {
                case "1":
                    carService.searchAndDisplayCars(user);
                    break;
                case "2":
                System.out.printf("Your balance: " + ConsoleColors.GREEN_BOLD_BRIGHT + "%.2f EUR%n" + ConsoleColors.RESET, user.getBalance());
                System.out.print("Would you like to top up your balance or change the currency? (yes/no): ");
                String topUpChoice = scanner.nextLine();
                if (topUpChoice.equalsIgnoreCase("yes")) {
                    System.out.println("+---------------------+");
                    System.out.println("| 1. Top-up           |");
                    System.out.println("| 2. Change currency  |");
                    System.out.println("+---------------------+");

                    Integer MoneyChoice = Integer.valueOf(scanner.nextLine());

                    if (MoneyChoice == 1) {
                        System.out.print("Enter amount to top up: ");
                        float amount;
                        try {
                            amount = Float.parseFloat(scanner.nextLine());
                            if (amount > 0) {
                                user.addFunds(amount);
                                authSystem.updateUserBalance(user);
                                System.out.printf("Your new balance: " + ConsoleColors.GREEN_BOLD_BRIGHT + "%.2f EUR%n" + ConsoleColors.RESET, user.getBalance());

                            } else {
                                System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Invalid amount!" + ConsoleColors.RESET);
                            }
                        } catch (NumberFormatException e) {
                            System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Invalid input!" + ConsoleColors.RESET);
                        }
                    } else if (MoneyChoice == 2) {
                        System.out.println("+----------------------------+");
                        System.out.println("| Available currencies       |");
                        System.out.println("+----------------------------+");
                        System.out.println("| 1. USD (American dollars)  |");
                        System.out.println("| 2. CAD (Canadian dollars)  |");
                        System.out.println("| 3. EURO (European euros)   |");
                        System.out.println("| 4. AUD (Australian dollar) |");
                        System.out.println("+----------------------------+");

                        Integer CurrencyChoice = Integer.valueOf(scanner.nextLine());
                        if (CurrencyChoice == 1) {
                            user.setCurrency("USD");
                        } else if (CurrencyChoice == 2) {
                            
                            user.setCurrency("CAD");
                        } else if (CurrencyChoice == 3) {
                            user.setCurrency("EUR");
                        } else if (CurrencyChoice == 4) {
                            user.setCurrency("AUD");
                        } else {
                            System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Invalid currency choice!" + ConsoleColors.RESET);
                        }   
                    } else {
                        System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Invalid option!" + ConsoleColors.RESET);
                    }
                    System.out.print("Enter amount to top up: ");
                    float amount;
                    try {
                        amount = Float.parseFloat(scanner.nextLine());
                        if (amount > 0) {
                            user.addFunds(amount);
                            authSystem.updateUserBalance(user);
                            System.out.printf("Your new balance: " + ConsoleColors.GREEN_BOLD_BRIGHT + "%.2f EUR%n" + ConsoleColors.RESET, user.getBalance());

                        } else {
                            System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Invalid amount!" + ConsoleColors.RESET);
                        }
                    } catch (NumberFormatException e) {
                        System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Invalid input!" + ConsoleColors.RESET);
                    }
                } else if (topUpChoice.equalsIgnoreCase("no")) {
                    System.out.println(ConsoleColors.CYAN_BOLD_BRIGHT + "You have chosen not to top up your balance." + ConsoleColors.RESET);
                } else {
                    System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Invalid option!" + ConsoleColors.RESET);
                }
                break;
                case "3":
                    running = false;
                    System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "Logged out successfully." + ConsoleColors.RESET);
                    break;
                case "4":
                    carService.returnVehicle(user.getUsername());
                    break;
                default:
                    System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Invalid option!" + ConsoleColors.RESET);
                    break;
            }
        }
    }}