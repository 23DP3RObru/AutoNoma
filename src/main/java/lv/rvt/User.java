package lv.rvt;

public class User {
    private String username;
    private float balance;

    public User(String username) {
        this.username = username;
        this.balance = 0.0f; // default balance starts at 0
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

    public boolean deductFunds(float amount) {
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

                String[] data = line.split(csvSplitBy);
                String username = data[0].trim();
                User user = new User(username);
                users.add(user);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Display users and test topping up
        for (User u : users) {
            System.out.println("Username: " + u.getUsername() + ", Balance: " + u.getBalance());
        }

        // Example: Add funds
        if (!users.isEmpty()) {
            User firstUser = users.get(0);
            firstUser.addFunds(100.0f);
            System.out.println("After top-up:");
            System.out.println("Username: " + firstUser.getUsername() + ", Balance: " + firstUser.getBalance());
        }
    }
}