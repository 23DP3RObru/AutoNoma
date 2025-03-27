package lv.rvt;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;

public class App {
    public static void main(String[] args) {
        Start();
    }

    public static boolean validData(String filename, String categoryToCheck, int column) { // DATU PARBAUDE ATKARIBA NO KATEGORIJAS
        try (BufferedReader br = new BufferedReader(new FileReader(filename))){
            String line;

            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] columns = line.split(", ");
                
                String category = columns[column];
                
                if (category.equalsIgnoreCase(categoryToCheck)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean validEmail(String email) { // LIETOTĀJA IEVADĪTĀ ELEKTRONISKĀ PASTA PĀRBAUDES METODE
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static void Start() { // PROGRAMMAS PALAIŠANAS METODE
        Scanner scanner = new Scanner(System.in);

        System.out.println("Do you wish to register {R} or log in {L}? (Upper-case sensitive!)"); // LIETOTĀJA IZVĒLE, REĢISTRĒTIES VAI PIERAKSTĪTIES
        String choice = scanner.nextLine();
        
        if (choice.equals("R")) { // IZVĒLE REĢISTRĒTIES
            register();
        }

        if (choice.equals("L")) { // IZVĒLE PIERAKSTĪTIES
            login();
        }

        scanner.close();
    }

    public static void register() { // REĢISTRĀCIJAS METODE
        Scanner scanner = new Scanner(System.in);

        System.out.print("Your unique username: "); // LIETOTĀJVĀRDA LIETOTĀJA IEVADE
        String username = scanner.nextLine();
        if (username.isEmpty()) {
            System.out.println("You left the username field empty."); // LIETOTĀJVĀRDA LAUKS ATSTĀTS TUKŠS

        } else {
            try {
                File loginFile = new File("C:\\Users\\emils\\AutoNoma\\data\\loginCredentials.csv");

                PrintWriter out = new PrintWriter(new FileWriter(loginFile, true));

                out.print(username + ", ");

                out.close();
                } catch(IOException e) {
                    System.out.println("Error when trying to open the file: " + e.getMessage());
                }
        }

        System.out.print("Your email: ");
        String email = scanner.nextLine();
            if (email.isEmpty()) {
                System.out.println("You left the email field empty."); // ELEKTRONISKĀ PASTA LAUKS ATSTĀTS TUKŠS
                
            } else if (!validEmail(email)) {
                System.out.println("The email you entered isn't valid."); // ELEKTRONOSKAIS PASTS NEATBILST PAREIZRAKSTĪBAI

            } else if (validEmail(email) && !validData("C:\\Users\\emils\\AutoNoma\\data\\loginCredentials.csv", email, 1)) { // JA ELEKTRONISKAIS PASTS IZRAKSTITS PAREIZI UN TAS NEATKĀRTOJAS
                try {
                File loginFile = new File("C:\\Users\\emils\\AutoNoma\\data\\loginCredentials.csv");

                PrintWriter out = new PrintWriter(new FileWriter(loginFile, true));

                out.print(email + ", ");

                out.close();
                } catch(IOException e) {
                    System.out.println("Error when trying to open the file: " + e.getMessage());
                }

        System.out.print("A strong password: "); // PAROLES LIETOTĀJA IEVADE
        String password = scanner.nextLine();
            if (password.isEmpty()) {
                System.out.println("You left the password field empty.");  // PAROLE ATSTĀTA TUKŠA
            } else {
                try {
                    File loginFile = new File("C:\\Users\\emils\\AutoNoma\\data\\loginCredentials.csv");
    
                    PrintWriter out = new PrintWriter(new FileWriter(loginFile, true));
    
                    out.println(password);
    
                    out.close();
                    } catch(IOException e) {
                        System.out.println("Error when trying to open the file: " + e.getMessage());
                    }
            }
    scanner.close();
    }
    }
    public static void login() {
        
    }
}