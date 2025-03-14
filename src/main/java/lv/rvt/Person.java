package lv.rvt;

import java.util.*;

private float bilance;
private String vārds;
private String uzvārds;
private int tālrunis;
private int personasKods;

public class Person {

    public Person(float bilance, String vārds, String uzvārds, int tālrunis, int personasKods) {
        this.bilance = bilance;
        this.vārds = vārds;
        this.uzvārds = uzvārds;
        this.tālrunis = tālrunis;
        this.personasKods = personasKods;
    }

    public float algasDiena(float naudasDaudzums) {
        this.bilance = this.bilance + naudasDaudzums;
    }

    
}