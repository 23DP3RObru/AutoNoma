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
            System.out.println("Nav pieejamu automasinu.");
            return;
        }

        System.out.println("Pieejamas automasinu markas:");
        for (String make : availableMakes) {
            System.out.println("- " + make);
        }

        System.out.print("\nIevadiet automasinas marku: ");
        String makeInput = scanner.nextLine().trim();
        List<Car> filteredCars = carLookup.filterCarsByMake(makeInput);

        if (filteredCars.isEmpty()) {
            System.out.println("Netika atrastas automasinas ar marku: " + makeInput);
            return;
        }

        System.out.println("\nAtrastas automasinas ar marku: " + makeInput);
        for (int i = 0; i < filteredCars.size(); i++) {
            System.out.println((i + 1) + ". " + filteredCars.get(i));
        }

        System.out.print("\nIzvelieties automasinas numuru, kuru velaties rezervet: ");
        int choice;
        try {
            choice = Integer.parseInt(scanner.nextLine()) - 1;
            if (choice < 0 || choice >= filteredCars.size()) {
                System.out.println("Nederiga izvele.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Ludzu ievadiet derigu numuru.");
            return;
        }

        Car selectedCar = filteredCars.get(choice);
        carLookup.markCarAsTaken(selectedCar);
        System.out.println("Jus veiksmigi rezervejat: " + selectedCar);
    }
}
