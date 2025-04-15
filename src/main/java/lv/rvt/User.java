package lv.rvt;

public class User {
    private float balance;
    private String name;
    private String surname;
    private int phoneNumber;
    private int identityNumber;
    
    public User(float balance, String name, String surname, int phoneNumber, int identityNumber) {
        this.balance = balance;
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.identityNumber = identityNumber;
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
}