package lv.rvt;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class User {
    private String username;
    private String email;
    private String password;
    private float balance;
    private String currency = "EUR";


    public User(String username, String email, String password, float balance, String currency) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.balance = balance;
        this.currency = currency;
    }

    public String getUsername() {
        return username;
    }

    public float getBalance() {
        return balance;
    }

    public void addFunds(float amount) {
        if (amount > 0) {
            balance += amount;
        }
    }

    public boolean deductFunds(double amount) {
        if (amount > 0 && balance >= amount) {
            balance -= amount;
            return true;
        }
        return false;
    }

    public void setBalance(float newBalance) {
        if (newBalance >= 0) {
            this.balance = newBalance;
        }
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public float getConvertedBalance() {
        float rate = switch (currency) {
            case "USD" -> 1.09f;
            case "CAD" -> 1.47f;
            case "AUD" -> 1.63f;
            default -> 1.0f; // Noklusejuma valūta ir EIRO
        };
        return balance / rate;
    }

    public String getCurrency() {
    return currency;
}


    public static void main(String[] args) {
        String csvFile = "users.csv";
        String line;
        String csvSplitBy = ",";

        ArrayList<User> users = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            boolean firstLine = true;
            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }
        
                String[] data = line.split(", ");
                
                String username = data[0].trim();
                String email = data[1].trim();
                String password = data[2].trim();
                float balance = Float.parseFloat(data[3].trim()); // Make sure the balance exists
            
                String currency = "EUR"; // Noklusejuma valūta
                if (data.length > 4) {
                    currency = data[4].trim();
                }
                
                User user = new User(username, email, password, balance, currency);
                users.add(user);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (User u : users) {
            System.out.println("Username: " + u.getUsername() + ", Balance: " + u.getBalance());
        }

        if (!users.isEmpty()) {
            User firstUser = users.get(0);
            firstUser.addFunds(100.0f);
            System.out.println("After top-up:");
            System.out.println("Username: " + firstUser.getUsername() + ", Balance: " + firstUser.getBalance());
        }

    
    }
}