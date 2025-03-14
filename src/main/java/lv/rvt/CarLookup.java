package lv.rvt;
import java.io.*;
import java.util.*;

public class CarLookup {
    private List<Car> cars;

    public CarLookup(String filePath) {
        this.cars = readCarsFromCSV(filePath);
    }

    private List<Car> readCarsFromCSV(String filePath) {
        List<Car> cars = new ArrayList<>();
        String line;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.readLine();
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
            System.out.println("Kluda lasot CSV failu: " + e.getMessage());
        }
        return cars;
    }


    public List<Car> filterCarsByMake(String make) {
        List<Car> result = new ArrayList<>();
        for (Car car : cars) {
            if (car.getMarka().equalsIgnoreCase(make)) {
                result.add(car);
            }
        }
        return result;
    }
    public Set<String> getUniqueCarMakes() {
        Set<String> uniqueMakes = new HashSet<>();
        for (Car car : cars) {
            uniqueMakes.add(car.getMarka());
        }
        return uniqueMakes;
    }}


