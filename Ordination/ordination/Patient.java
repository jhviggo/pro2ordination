package ordination;

import java.util.ArrayList;

public class Patient {
    private String cprnr;
    private String navn;
    private double vaegt;

    ArrayList<Ordination> ordinationer;

    public Patient(String cprnr, String navn, double vaegt) {
        this.cprnr = cprnr;
        this.navn = navn;
        this.vaegt = vaegt;
        this.ordinationer = new ArrayList<>();
    }

    public String getCprnr() {
        return cprnr;
    }

    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public double getVaegt(){
        return vaegt;
    }

    public void setVaegt(double vaegt){
        this.vaegt = vaegt;
    }

    public void addOrdination(Ordination ordination) {
        if(!this.ordinationer.contains(ordination)) {
            this.ordinationer.add(ordination);
        }
    }

    public void removeOrdination(Ordination ordination) {
        if(this.ordinationer.contains(ordination)) {
            this.ordinationer.remove(ordination);
        }
    }

    @Override
    public String toString(){
        return navn + "  " + cprnr;
    }

    public ArrayList<Ordination> getOrdinationer() {
        return new ArrayList<>(ordinationer);
    }
}
