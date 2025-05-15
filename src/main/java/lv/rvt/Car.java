package lv.rvt;

import java.util.Objects;

public class Car {
    private String marka;
    private String modelis;
    private String tips;
    private int gads;
    private double stundasMaksa;

    public Car(String marka, String modelis, String tips, int gads, double stundasMaksa) {
        this.marka = marka;
        this.modelis = modelis;
        this.tips = tips;
        this.gads = gads;
        this.stundasMaksa = stundasMaksa;
    }

    public String getMarka() {
        return marka;
    }

    public String getModelis() {
        return modelis;
    }

    public String getTips() {
        return tips;
    }

    public int getGads() {
        return gads;
    }

    public double getStundasMaksa() {
        return stundasMaksa;
    }

    @Override
    public String toString() {
        return gads + " " + marka + " " + modelis + " (" + tips + ") - " + stundasMaksa + " eiro/stunda";
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Car car = (Car) obj;
        return gads == car.gads &&
               Double.compare(car.stundasMaksa, stundasMaksa) == 0 &&
               marka.equals(car.marka) &&
               modelis.equals(car.modelis) &&
               tips.equals(car.tips);
    }

    @Override
    public int hashCode() {
        return Objects.hash(marka, modelis, tips, gads, stundasMaksa);
    }
}
