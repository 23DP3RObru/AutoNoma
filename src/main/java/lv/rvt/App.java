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
        System.out.println("|              " + ConsoleColors.GREEN + "Registration" + ConsoleColors.RESET + "              |");
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
            out.println(username + ", " + email + ", " + password + ", " + 0.0f + ", EUR");
            System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "Registration successful!"+ ConsoleColors.RESET);
            return true;
        } catch (IOException e) {
            System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Error saving credentials: " + e.getMessage() + ConsoleColors.RESET);
            return false;
        }
    }
    
    public User login() {
        Scanner scanner = new Scanner(System.in);
    
        System.out.println("+----------------------------------------+");
        System.out.println("|                " + ConsoleColors.GREEN + "Log" + ConsoleColors.RESET + "-in                  |");
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
                
                if (parts.length >= 3 && parts[1].trim().equalsIgnoreCase(email)) {
                    if (parts[2].trim().equals(password)) {
                        System.out.println(ConsoleColors.GREEN_BOLD_BRIGHT + "  Login successful!" + ConsoleColors.RESET);
                        
                        String username = parts[0].trim();
                        String userEmail = parts[1].trim();
                        String userPassword = parts[2].trim();
                        float balance = parts.length > 3 ? Float.parseFloat(parts[3].trim()) : 0.0f;
                        String currency = parts.length > 4 ? parts[4].trim() : "EUR";
                        return new User(username, userEmail, userPassword, balance, currency);

                    }
                    System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Error: Incorrect password!" + ConsoleColors.RESET);
                    return null;
                }
            }
            System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Error: Email not found!" + ConsoleColors.RESET);
            return null;
        } catch (IOException e) {
            System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Error reading credentials: " + e.getMessage() + ConsoleColors.RESET);
            return null;
        }
    }
    
    private boolean validateRegistration(String username, String email, String password) {
        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Error: All fields are required!"+ ConsoleColors.RESET);
            return false;
        }
        
        if (!validEmail(email)) {
            System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Error: Invalid email format!"+ ConsoleColors.RESET);
            return false;
        }
        
        if (isCredentialExists(username, 0)) {
            System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Error: Username already exists!"+ ConsoleColors.RESET);
            return false;
        }
        
        if (isCredentialExists(email, 1)) {
            System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Error: Email already exists!"+ ConsoleColors.RESET);
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

    public void updateUserBalance(User user) {
        File inputFile = new File(credentialsFile);
        List<String> updatedLines = new ArrayList<>();
    
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String header = reader.readLine();
            updatedLines.add(header);
    
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(", ");
                if (parts.length >= 4 && parts[0].equals(user.getUsername())) {
                    String newLine = user.getUsername() + ", " + parts[1] + ", " + parts[2] + ", " + user.getBalance() + ", " + user.getCurrency();
                    updatedLines.add(newLine);
                } else {
                    updatedLines.add(line);
                }
            }
    
            try (PrintWriter writer = new PrintWriter(new FileWriter(inputFile))) {
                for (String updatedLine : updatedLines) {
                    writer.println(updatedLine);
                }
            }
    
        } catch (IOException e) {
            System.out.println(ConsoleColors.RED_BOLD_BRIGHT + "Error updating balance: " + e.getMessage() + ConsoleColors.RESET);
        }
    }
}
