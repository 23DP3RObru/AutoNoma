package lv.rvt;

import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.ArrayList;
import java.util.Comparator;
import java.io.*;

public class CarService {
    private CarLookup carLookup;
    private Scanner scanner;
    private final String reservationsFile = "data/client_reserved_vehicle.csv";

    public CarService(String filePath) {
        this.carLookup = new CarLookup(filePath);
        this.scanner = new Scanner(System.in);
        ensureReservationsFile();
    }

    private void ensureReservationsFile() {
        File file = new File(reservationsFile);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            try (PrintWriter out = new PrintWriter(file)) {
                out.println("username,marka,modelis,tips,gads,stundas_maksa");
            } catch (IOException e) {
                System.out.println(ConsoleColors.RED + "Error creating reservations file: " + e.getMessage() + ConsoleColors.RESET);
            }
        }
    }
    private boolean hasReservation(String username) {
        try (BufferedReader br = new BufferedReader(new FileReader(reservationsFile))) {
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length > 0 && parts[0].equals(username)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println(ConsoleColors.RED + "Error reading reservations: " + e.getMessage() + ConsoleColors.RESET);
        }
        return false;
    }

    private void handleReturnOption(User user) {
        System.out.print("Would you like to return your current vehicle? (yes/no): ");
        String choice = scanner.nextLine().trim();
        if (choice.equalsIgnoreCase("yes")) {
            returnVehicle(user.getUsername());
        }
    }

    public void returnVehicle(String username) {
        List<String> lines = new ArrayList<>();
        boolean found = false;
        Car returnedCar = null;

        try (BufferedReader br = new BufferedReader(new FileReader(reservationsFile))) {
            String header = br.readLine();
            lines.add(header);

            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 6 && parts[0].equals(username)) {
                    found = true;
                    returnedCar = new Car(parts[1], parts[2], parts[3], 
                                        Integer.parseInt(parts[4]), 
                                        Double.parseDouble(parts[5]));
                } else {
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            System.out.println(ConsoleColors.RED + "Error reading reservations: " + e.getMessage() + ConsoleColors.RESET);
            return;
        }

        if (found && returnedCar != null) {
            try (PrintWriter out = new PrintWriter(new FileWriter(reservationsFile))) {
                for (String line : lines) {
                    out.println(line);
                }
            } catch (IOException e) {
                System.out.println(ConsoleColors.RED + "Error updating reservations: " + e.getMessage() + ConsoleColors.RESET);
                return;
            }

            try (BufferedWriter bw = new BufferedWriter(new FileWriter("data/taken_cars.csv"))) {
                boolean firstLine = true;
                for (Car car : carLookup.getTakenCars()) {
                    if (!car.equals(returnedCar)) {
                        if (!firstLine) bw.newLine();
                        bw.write(car.getMarka() + "," + car.getModelis() + "," + 
                                 car.getTips() + "," + car.getGads() + "," + car.getStundasMaksa());
                        firstLine = false;
                    }
                }
            } catch (IOException e) {
                System.out.println(ConsoleColors.RED + "Error updating taken cars: " + e.getMessage() + ConsoleColors.RESET);
                return;
            }

            System.out.println(ConsoleColors.GREEN + "Vehicle returned successfully: " + returnedCar + ConsoleColors.RESET);
        } else {
            System.out.println(ConsoleColors.RED + "No reservation found for your account." + ConsoleColors.RESET);
        }
    }

    public void searchAndDisplayCars(User user) {
        if (hasReservation(user.getUsername())) {
            System.out.println(ConsoleColors.YELLOW + "You already have a reserved vehicle. Please return it before reserving another one." + ConsoleColors.RESET);
            handleReturnOption(user);
            return;
        }
    
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
            } else if (user.getBalance() < filteredCars.get(choice).getStundasMaksa()) {
                System.out.println(ConsoleColors.RED + "Not enough funds." + ConsoleColors.RESET);
                return;
            } else if (user.getBalance() < filteredCars.get(choice).getStundasMaksa()) {
                System.out.println(ConsoleColors.RED + "Not enough funds." + ConsoleColors.RESET);
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println(ConsoleColors.RED + "Enter a suitable number." + ConsoleColors.RESET);
            return;
        }
    
        Car selectedCar = filteredCars.get(choice);
        carLookup.markCarAsTaken(selectedCar);
        recordReservation(user.getUsername(), selectedCar);
        System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "Reservation successful of " + selectedCar + ConsoleColors.RESET);
        System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "You have been charged " + selectedCar.getStundasMaksa() + " EUR." + ConsoleColors.RESET);
        user.deductFunds(selectedCar.getStundasMaksa());
    }

    private void recordReservation(String username, Car car) {
        try (PrintWriter out = new PrintWriter(new FileWriter(reservationsFile, true))) {
            out.println(username + "," + car.getMarka() + "," + car.getModelis() + "," + 
                       car.getTips() + "," + car.getGads() + "," + car.getStundasMaksa());
        } catch (IOException e) {
            System.out.println(ConsoleColors.RED + "Error saving reservation: " + e.getMessage() + ConsoleColors.RESET);
        }
    }

    
}