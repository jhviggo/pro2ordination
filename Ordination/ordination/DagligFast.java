package ordination;

import java.time.*;
import java.time.temporal.ChronoUnit;

public class DagligFast extends Ordination {
    private Dosis[] doser = new Dosis[4];

    public DagligFast(LocalDate startDen, LocalDate slutDen, Laegemiddel laegemiddel) {
        super(startDen, slutDen, laegemiddel);
    }

    public void addDosis(int timeslot, LocalTime tid, double antal) {
        doser[timeslot] = new Dosis(tid, antal);
    }

    public void addDosis(double morgenAntal, double middagAntal, double aftenAntal, double natAntal) {
        doser[0] = new Dosis(LocalTime.of(8, 0), morgenAntal);
        doser[1] = new Dosis(LocalTime.of(12, 0), middagAntal);
        doser[2] = new Dosis(LocalTime.of(16, 0), aftenAntal);
        doser[3] = new Dosis(LocalTime.of(20, 0), natAntal);
    }

    public Dosis[] getDoser() {
        return this.doser;
    }

    public void removeDosis(int timeslot) {
        doser[timeslot] = null;
    }

    public double samletDosis() {
        int days = (int) ChronoUnit.DAYS.between(this.getStartDen(), this.getSlutDen()) + 1;
        return this.doegnDosis() * days;
    }

    public double doegnDosis() {
        int antalDoser = 0;
        for (Dosis d : doser) {
            if (d != null) {
                antalDoser += d.getAntal();
            }
        }
        return antalDoser;
    }

    public String getType() {
        return "DagligtFast";
    };
}
