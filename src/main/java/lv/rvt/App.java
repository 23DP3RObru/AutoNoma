package lv.rvt;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

public class App {
    public static void main(String[] args) {
        Start();
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
        }

        System.out.print("Your email: ");
        String email = scanner.nextLine();
            if (email.isEmpty()) {
                System.out.println("You left the email field empty."); // ELEKTRONISKĀ PASTA LAUKS ATSTĀTS TUKŠS

            } else if (validEmail(email)) {
                try {
                File loginFile = new File("../../data/userLoginCredentials.csv");

                PrintWriter out = new PrintWriter(new FileWriter(loginFile, true));

                out.println(email);

                out.close();
                } catch(IOException e) {
                    System.out.println("Error when trying to open the file: " + e.getMessage());
                }
            } else {
                System.out.println("The email you entered isn't valid."); // ELEKTRONOSKAIS PASTS NEATBILST PAREIZRAKSTĪBAI

            }

        System.out.print("A strong password: "); // PAROLES LIETOTĀJA IEVADE
        String password = scanner.nextLine();
            if (password.isEmpty()) {
                System.out.println("You left the password field empty.");  // PAROLE ATSTĀTA TUKŠA
            }
    scanner.close();
    }

    public static void login() {
        
    }
}