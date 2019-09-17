package ordination;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class PN extends Ordination{

    private double antalEnheder;

    private ArrayList<LocalDate> doser;

    public PN(LocalDate startDen, LocalDate slutDen, Laegemiddel laegemiddel, double antalEnheder) {
        super(startDen, slutDen, laegemiddel);
        this.antalEnheder = antalEnheder;
        this.doser = new ArrayList<>();
    }

    /**
     * Registrerer at der er givet en dosis paa dagen givesDen
     * Returnerer true hvis givesDen er inden for ordinationens gyldighedsperiode og datoen huskes
     * Retrurner false ellers og datoen givesDen ignoreres
     * @param givesDen
     * @return
     */
    public boolean givDosis(LocalDate givesDen) {
        if (this.getStartDen().compareTo(givesDen) <= 0 && 0 <= this.getSlutDen().compareTo(givesDen)) {
            this.doser.add(givesDen);
            return true;
        }
        return false;
    }

    public double doegnDosis() {
        int days = (int) ChronoUnit.DAYS.between(this.getStartDen(), this.getSlutDen()) + 1;
        return samletDosis() / days;
    }

    public ArrayList<LocalDate> getDoser() {
        return new ArrayList<>(doser);
    }

    @Override
    public String getType() {
        return "PN";
    }


    public double samletDosis() {
        return antalEnheder * this.doser.size();
    }

    /**
     * Returnerer antal gange ordinationen er anvendt
     * @return
     */
    public int getAntalGangeGivet() {
        return doser.size();
    }

    public double getAntalEnheder() {
        return antalEnheder;
    }

}
