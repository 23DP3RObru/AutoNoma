package lv.rvt;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        String filePath = "C:\\Users\\reinc\\Documents\\Vadim projekts\\AutoNoma\\data\\data.csv";
        CarService carService = new CarService(filePath);
        carService.searchAndDisplayCars();
    }
}

