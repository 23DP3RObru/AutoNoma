package lv.rvt;

import java.io.*;
import java.util.*;

public class CarLookup {
    private List<Car> cars;
    private List<Car> takenCars;
    private final String takenCarsFile = "C:\\Users\\reinc\\Documents\\Vadim projekts\\AutoNoma\\data\\taken_cars.csv";

    public CarLookup(String filePath) {
        this.cars = readCarsFromCSV(filePath);
        this.takenCars = readCarsFromCSV(takenCarsFile);
    }

    private List<Car> readCarsFromCSV(String filePath) {
        List<Car> cars = new ArrayList<>();
        File file = new File(filePath);

        if (!file.exists()) return cars;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length == 5) {
                    String marka = values[0].trim();
                    String modelis = values[1].trim();
                    String tips = values[2].trim();
                    int gads = Integer.parseInt(values[3].trim());
                    double stundasMaksa = Double.parseDouble(values[4].trim());
                    cars.add(new Car(marka, modelis, tips, gads, stundasMaksa));
                }
            }
        } catch (IOException e) {
            System.out.println("Kļūda lasot CSV failu: " + e.getMessage());
        }
        return cars;
    }

    public Set<String> getUniqueCarMakes() {
        Set<String> uniqueMakes = new HashSet<>();
        for (Car car : cars) {
            if (!takenCars.contains(car)) {
                uniqueMakes.add(car.getMarka());
            }
        }
        return uniqueMakes;
    }

    public List<Car> filterCarsByMake(String make) {
        List<Car> result = new ArrayList<>();
        for (Car car : cars) {
            if (car.getMarka().equalsIgnoreCase(make) && !takenCars.contains(car)) {
                result.add(car);
            }
        }
        return result;
    }

    public void markCarAsTaken(Car car) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(takenCarsFile, true))) {
            bw.write(car.getMarka() + "," + car.getModelis() + "," + car.getTips() + "," + car.getGads() + "," + car.getStundasMaksa());
            bw.newLine();
            System.out.println("Rezervacija veiksmiga: " + car);

            takenCars.add(car);

        } catch (IOException e) {
            System.out.println("Kluda saglabajot rezerveto automasinu: " + e.getMessage());
        }
    }
}
