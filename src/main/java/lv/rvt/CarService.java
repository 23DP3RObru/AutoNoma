package lv.rvt;

import java.util.List;
import java.util.Scanner;
import java.util.Set;

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
            System.out.println("This vehicle is not available.");
            return;
        }

        System.out.println("Available car makes:");
        for (String make : availableMakes) {
            System.out.println("- " + make);
        }

        System.out.print("\nPlease enter car make you wish to select: ");
        String makeInput = scanner.nextLine().trim();
        List<Car> filteredCars = carLookup.filterCarsByMake(makeInput);

        if (filteredCars.isEmpty()) {
            System.out.println("Unable to find this make: " + makeInput);
            return;
        }

        System.out.println("\nAtrastas automasinas ar marku: " + makeInput);
        for (int i = 0; i < filteredCars.size(); i++) {
            System.out.println((i + 1) + ". " + filteredCars.get(i));
        }

        System.out.print("\nPlease select the number of the car you wish to select: ");
        int choice;
        try {
            choice = Integer.parseInt(scanner.nextLine()) - 1;
            if (choice < 0 || choice >= filteredCars.size()) {
                System.out.println("Not an option.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Enter a suitable number.");
            return;
        }

        Car selectedCar = filteredCars.get(choice);
        carLookup.markCarAsTaken(selectedCar);
        System.out.println("Rezervation successful of " + selectedCar);
    }
}
