package lv.rvt;

import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class CarService {
    private CarLookup carLookup;

    public CarService(String filePath) {
        this.carLookup = new CarLookup(filePath);
    }

    public void searchAndDisplayCars() {
        Set<String> availableMakes = carLookup.getUniqueCarMakes();
        System.out.println("Pieejamas automasinu markas: ");
        for (String make : availableMakes) {
            System.out.println("- " + make);
        }
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ievadiet automasinas marku: ");
        String makeInput = scanner.nextLine().trim();
        
        List<Car> filteredCars = carLookup.filterCarsByMake(makeInput);

        if (filteredCars.isEmpty()) {
            System.out.println("Netika atrastas automasinas ar marku: " + makeInput);
        } else {
            System.out.println("Atrastas automasinas ar marku: " + makeInput);
            for (Car car : filteredCars) {
                System.out.println(car);
            }
        }

        scanner.close();
    }
}

