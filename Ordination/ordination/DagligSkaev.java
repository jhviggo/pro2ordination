package ordination;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class DagligSkaev extends Ordination {
    private ArrayList<Dosis> doser;

    public DagligSkaev(LocalDate startDen, LocalDate slutDen, Laegemiddel laegemiddel) {
        super(startDen, slutDen, laegemiddel);
        this.doser = new ArrayList<>();
    }

    public ArrayList<Dosis> getDoser() {
        return new ArrayList<>(this.doser);
    }

    public void opretDosis(LocalTime tid, double antal) {
        doser.add(new Dosis(tid, antal));
    }

    public void opretDosis(LocalTime[] tider, double[] antal) {
        for(int i = 0; i < tider.length || i < antal.length; i++) {
            opretDosis(tider[i], antal[i]);
        }
    }

    public void removeDosis(Dosis d) {
        if(doser.contains(d)) {
            doser.remove(d);
        }
    }

    @Override
    public double samletDosis() {
        int days = (int) ChronoUnit.DAYS.between(this.getStartDen(), this.getSlutDen()) + 1;
        return this.doegnDosis() * days;
    }

    @Override
    public double doegnDosis() {
        double total = 0;
        for(Dosis d : doser) {
            total += d.getAntal();
        }
        return total;
    }

    @Override
    public String getType() {
        return "DagligSkaev";
    }
}
