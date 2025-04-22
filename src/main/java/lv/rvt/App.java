package lv.rvt;

import java.io.*;
import java.util.*;
import java.util.regex.*;

public class App {
    private String credentialsFile;
    
    public App(String credentialsFile) {
        this.credentialsFile = credentialsFile;
        ensureCredentialsFile();
    }
    
    private void ensureCredentialsFile() {
        File file = new File(credentialsFile);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            try (PrintWriter out = new PrintWriter(file)) {
                out.println("username, email, password");
            } catch (IOException e) {
                System.err.println("Error creating credentials file: " + e.getMessage());
            }
        }
    }
    
    public boolean register() {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("+----------------------------------------+");
        System.out.println("|              Registration              |");
        System.out.println("+----------------------------------------+");
        System.out.print("  Enter username: ");
        String username = scanner.nextLine().trim();
        
        System.out.print("  Enter email: ");
        String email = scanner.nextLine().trim();
        
        System.out.print("  Enter password: ");
        String password = scanner.nextLine().trim();
        
        if (!validateRegistration(username, email, password)) {
            return false;
        }
        
        try (PrintWriter out = new PrintWriter(new FileWriter(credentialsFile, true))) {
            out.println(username + ", " + email + ", " + password);
            System.out.println("Registration successful!");
            return true;
        } catch (IOException e) {
            System.out.println("Error saving credentials: " + e.getMessage());
            return false;
        }
    }
    
    public User login() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("+----------------------------------------+");
        System.out.println("|                 Login                  |");
        System.out.println("+----------------------------------------+");
        System.out.print("  Email: ");
        String email = scanner.nextLine().trim();
        System.out.print("  Password: ");
        String password = scanner.nextLine().trim();
        
        try (BufferedReader br = new BufferedReader(new FileReader(credentialsFile))) {
            br.readLine();
            
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(", ");
                if (parts.length >= 3 && parts[1].equalsIgnoreCase(email)) {
                    if (parts[2].equals(password)) {
                        System.out.println("  Login successful!");
                        return new User(100.00f, "Default", "User", 0, 0);
                    }
                    System.out.println("Error: Incorrect password!");
                    return null;
                }
            }
            System.out.println("Error: Email not found!");
            return null;
        } catch (IOException e) {
            System.out.println("Error reading credentials: " + e.getMessage());
            return null;
        }
    }
    
    private boolean validateRegistration(String username, String email, String password) {
        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            System.out.println("Error: All fields are required!");
            return false;
        }
        
        if (!validEmail(email)) {
            System.out.println("Error: Invalid email format!");
            return false;
        }
        
        if (isCredentialExists(username, 0)) {
            System.out.println("Error: Username already exists!");
            return false;
        }
        
        if (isCredentialExists(email, 1)) {
            System.out.println("Error: Email already exists!");
            return false;
        }
        
        return true;
    }
    
    private boolean isCredentialExists(String value, int column) {
        try (BufferedReader br = new BufferedReader(new FileReader(credentialsFile))) {
            br.readLine();
            
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(", ");
                if (parts.length > column && parts[column].equalsIgnoreCase(value)) {
                    return true;
                }
            }
        } catch (IOException e) {
            return false;
        }
        return false;
    }
    
    private boolean validEmail(String email) {
        return Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")
                     .matcher(email)
                     .matches();
    }
}