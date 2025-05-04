package lv.rvt;

import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.Comparator;

public class CarService {
    private CarLookup carLookup;
    private Scanner scanner;

    public CarService(String filePath) {
        this.carLookup = new CarLookup(filePath);
        this.scanner = new Scanner(System.in);
    }

    public void searchAndDisplayCars() {
        Set<String> availableMakes = carLookup.getUniqueCarMakes();
        if (availableMakes.isEmpty()) {
            System.out.println("This vehicle is" + ConsoleColors.RED + " not available" + ConsoleColors.RESET + ".");
            return;
        }

        System.out.println("+-------------------------+");
        System.out.println("| Choose your car make:   |");
        System.out.println("+-------------------------+");
        for (String make : availableMakes) {
            System.out.println(" - " + make);
        }

        System.out.print("\nPlease enter car make you wish to select: ");
        String makeInput = scanner.nextLine().trim();
        List<Car> filteredCars = carLookup.filterCarsByMake(makeInput);

        if (filteredCars.isEmpty()) {
            System.out.println(ConsoleColors.RED + "Unable to find this make: " + makeInput + ConsoleColors.RESET);
            return;
        }

        // Ask user for sorting preference
        System.out.println("\n+--------------------------------+");
        System.out.println("| How would you like to sort?    |");
        System.out.println("+--------------------------------+");
        System.out.println("| 1. A-Z (by model)              |");
        System.out.println("| 2. Z-A (by model)              |");
        System.out.println("| 3. Cheapest to most expensive  |");
        System.out.println("| 4. Most expensive to cheapest  |");
        System.out.println("+--------------------------------+");
        System.out.print("Enter your choice (1-4): ");
        
        String sortChoice = scanner.nextLine().trim();
        
        // Sort the cars based on user's choice
        switch (sortChoice) {
            case "1":
                filteredCars.sort(Comparator.comparing(Car::getModelis));
                break;
            case "2":
                filteredCars.sort(Comparator.comparing(Car::getModelis).reversed());
                break;
            case "3":
                filteredCars.sort(Comparator.comparingDouble(Car::getStundasMaksa));
                break;
            case "4":
                filteredCars.sort(Comparator.comparingDouble(Car::getStundasMaksa).reversed());
                break;
            default:
                System.out.println(ConsoleColors.YELLOW + "Invalid choice. Showing unsorted list." + ConsoleColors.RESET);
        }

        System.out.println("\n+-------------------------------------------------+");
        System.out.println("  Cars found with make - " + makeInput);
        System.out.println("+-------------------------------------------------+");
        for (int i = 0; i < filteredCars.size(); i++) {
            System.out.println((i + 1) + ". " + filteredCars.get(i));
        }

        System.out.print("\nPlease select the number of the car you wish to select: ");
        int choice;
        try {
            choice = Integer.parseInt(scanner.nextLine()) - 1;
            if (choice < 0 || choice >= filteredCars.size()) {
                System.out.println(ConsoleColors.RED + "Not an option." + ConsoleColors.RESET);
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println(ConsoleColors.RED + "Enter a suitable number." + ConsoleColors.RESET);
            return;
        }

        Car selectedCar = filteredCars.get(choice);
        carLookup.markCarAsTaken(selectedCar);
        System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "Reservation successful of " + selectedCar + ConsoleColors.RESET);
    }
}