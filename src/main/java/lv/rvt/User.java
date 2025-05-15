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

    public User(String username, String email, String password, float balance) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.balance = balance;
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
        if (currency.equalsIgnoreCase("USD")) {

            System.out.println("Currency set to American dollars.");

        } else if (currency.equalsIgnoreCase("CAD")) {

            System.out.println("Currency set to Canadian dollars.");

        } else if (currency.equalsIgnoreCase("EUR")) {

            System.out.println("Currency set to European euros.");

        } else if (currency.equalsIgnoreCase("AUD")) {

            System.out.println("Currency set to Australian dollars.");

        } else {
            
            System.out.println("Invalid currency. Defaulting to Euro.");
        }
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
                float balance = Float.parseFloat(data[3].trim());
            
                User user = new User(username, email, password, balance);
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