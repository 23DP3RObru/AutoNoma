package lv.rvt;

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

    @Override
    public String toString() {
        return gads + " " + marka + " " + modelis + " (" + tips + ") - " + stundasMaksa + " eiro/stunda";
    }
}
