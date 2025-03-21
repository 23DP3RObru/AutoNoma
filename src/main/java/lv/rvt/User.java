package lv.rvt;

import java.util.*;

public class User {
    private float bilance;
    private String name;
    private String surname;
    private int phoneNumber;
    private int identityNumber;
    
    public User(float bilance, String name, String surname, int phoneNumber, int identityNumber) {
        this.bilance = bilance;
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.identityNumber = identityNumber;
    }

    public float moneyAddition(float amount) {
        return this.bilance + amount;
    }

    public float moneyDeduction(float amount) {
        return this.bilance - amount;
    }
}