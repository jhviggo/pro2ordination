import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Ignore;
import org.junit.Test;

import ordination.DagligFast;
import ordination.Laegemiddel;

public class DagligFastTest {

    private DagligFast dagligfast;
    private Laegemiddel laegemiddel;

    @Test
    public void addDoserTest() {

        laegemiddel = new Laegemiddel("Testmiddel", 1, 2, 3, "ml");
        dagligfast = new DagligFast(LocalDate.of(2019, 9, 12), LocalDate.of(2019, 9, 17), laegemiddel);

        dagligfast.addDosis(1, 2, 1, 1);


        assertEquals(dagligfast.getDoser()[0].getAntal(), 1, 0.001);
        assertEquals(dagligfast.getDoser()[1].getAntal(), 2, 0.001);
        assertEquals(dagligfast.getDoser()[2].getAntal(), 1, 0.001);
        assertEquals(dagligfast.getDoser()[3].getAntal(), 1, 0.001);

        dagligfast.addDosis(1, 0, 1, 0);

        assertEquals(dagligfast.getDoser()[0].getAntal(), 1, 0.001);
        assertEquals(dagligfast.getDoser()[1].getAntal(), 0, 0.001);
        assertEquals(dagligfast.getDoser()[2].getAntal(), 1, 0.001);
        assertEquals(dagligfast.getDoser()[3].getAntal(), 0, 0.001);

        dagligfast.addDosis(-1, 1, 1, 1);

        assertEquals(dagligfast.getDoser()[0].getAntal(), -1, 0.001);
        assertEquals(dagligfast.getDoser()[1].getAntal(), 1, 0.001);
        assertEquals(dagligfast.getDoser()[2].getAntal(), 1, 0.001);
        assertEquals(dagligfast.getDoser()[3].getAntal(), 1, 0.001);
    }

    @Test
    public void samletDosisTest() {

        laegemiddel = new Laegemiddel("Testmiddel", 1, 2, 3, "ml");
        dagligfast = new DagligFast(LocalDate.of(2019, 9, 12), LocalDate.of(2019, 9, 17), laegemiddel);

        dagligfast.addDosis(1, 2, 1, 1);

        double faktiskVærdi = dagligfast.samletDosis();

        assertEquals(30, faktiskVærdi, 0.001);

        dagligfast.addDosis(1, 0, 1, 0);

        double faktiskVærdi2 = dagligfast.samletDosis();

        assertEquals(12, faktiskVærdi2, 0.001);
    }

    @Test
    public void doegnDosisTest() {
        laegemiddel = new Laegemiddel("Testmiddel", 1, 2, 3, "ml");
        dagligfast = new DagligFast(LocalDate.of(2019, 9, 12), LocalDate.of(2019, 9, 17), laegemiddel);

        dagligfast.addDosis(1, 2, 1, 1);

        double faktiskVærdi = dagligfast.doegnDosis();

        assertEquals(5, faktiskVærdi, 0.001);

    }

}
