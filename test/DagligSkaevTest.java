import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.Test;

import ordination.DagligSkaev;
import ordination.Laegemiddel;

public class DagligSkaevTest {

    Laegemiddel lm1 = new Laegemiddel("CBD", 2, 4, 6, "ML");

    DagligSkaev ds1 = new DagligSkaev(LocalDate.of(2020, 05, 01), LocalDate.of(2020, 05, 5), lm1);

    @Test
    public void opretDosisTest() {
        LocalTime[] tider = { LocalTime.of(7, 0), LocalTime.of(10, 0), LocalTime.of(14, 15) };
        double[] antal = { 5.5, 1, 2 };

        ds1.opretDosis(tider, antal);
        assertEquals(7, ds1.getDoser().get(0).getTid().getHour(), 0.001);
        assertEquals(0, ds1.getDoser().get(0).getTid().getMinute(), 0.001);
        assertEquals(5.5, ds1.getDoser().get(0).getAntal(), 0.001);

        assertEquals(10, ds1.getDoser().get(1).getTid().getHour(), 0.001);
        assertEquals(0, ds1.getDoser().get(1).getTid().getMinute(), 0.001);
        assertEquals(1, ds1.getDoser().get(1).getAntal(), 0.001);

        assertEquals(14, ds1.getDoser().get(2).getTid().getHour(), 0.001);
        assertEquals(15, ds1.getDoser().get(2).getTid().getMinute(), 0.001);
        assertEquals(2, ds1.getDoser().get(2).getAntal(), 0.001);

    }

    @Test
    public void samletDosis() {
        LocalTime[] tider = { LocalTime.of(7, 0), LocalTime.of(10, 0), LocalTime.of(14, 15) };
        double[] antal = { 5.5, 1, 2 };
        ds1.opretDosis(tider, antal);
        assertEquals(42.5, ds1.samletDosis(), 0.001);
    }

    @Test
    public void DoegnDosis() {
        LocalTime[] tider = { LocalTime.of(7, 0), LocalTime.of(10, 0), LocalTime.of(14, 15) };
        double[] antal = { 5.5, 1, 2 };
        ds1.opretDosis(tider, antal);
        assertEquals(8.5, ds1.doegnDosis(), 0.001);
    }

}
