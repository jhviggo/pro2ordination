import org.junit.Test;
import static org.junit.Assert.*;

import ordination.PN;
import ordination.Laegemiddel;

import java.time.LocalDate;

public class PNTest {
    private Laegemiddel laegemiddel;
    private PN pn;

    @Test
    public void givDosisTest() {
        laegemiddel = new Laegemiddel("test middel", 1, 1, 1, "ml");
        pn = new PN(LocalDate.of(2019, 9, 12), LocalDate.of(2019, 9, 17), laegemiddel, 3);
        assertTrue(pn.givDosis(LocalDate.of(2019, 9,15)));
        assertEquals(pn.getDoser().size(), 1);
    }

    @Test
    public void givFlereDosisTest() {
        laegemiddel = new Laegemiddel("test middel", 1, 1, 1, "ml");
        pn = new PN(LocalDate.of(2019, 9, 12), LocalDate.of(2019, 9, 17), laegemiddel, 3);
        assertTrue(pn.givDosis(LocalDate.of(2019, 9,15)));
        assertTrue(pn.givDosis(LocalDate.of(2019, 9,16)));
        assertEquals(pn.getDoser().size(), 2);
    }

    @Test
    public void givDosisForkertDatoTest() {
        laegemiddel = new Laegemiddel("test middel", 1, 1, 1, "ml");
        pn = new PN(LocalDate.of(2019, 9, 12), LocalDate.of(2019, 9, 17), laegemiddel, 3);
        assertFalse(pn.givDosis(LocalDate.of(2019, 9,18)));
        assertFalse(pn.givDosis(LocalDate.of(2019, 9,11)));
        assertFalse(pn.givDosis(LocalDate.of(2019, 10,13)));
        assertEquals(pn.getDoser().size(), 0);
    }

    @Test
    public void samletDosis() {
        laegemiddel = new Laegemiddel("test middel", 1, 1, 1, "ml");
        pn = new PN(LocalDate.of(2019, 9, 12), LocalDate.of(2019, 9, 17), laegemiddel, 3);
        assertEquals(pn.samletDosis(), 0, 0.001);
        pn.givDosis(LocalDate.of(2019, 9, 13));
        assertEquals(pn.samletDosis(), 3, 0.001);
        pn.givDosis(LocalDate.of(2019, 9, 12));
        pn.givDosis(LocalDate.of(2019, 9, 15));
        pn.givDosis(LocalDate.of(2019, 9, 16));
        assertEquals(pn.samletDosis(), 12, 0.001);
    }

    @Test
    public void doegnDosis() {
        laegemiddel = new Laegemiddel("test middel", 1, 1, 1, "ml");
        pn = new PN(LocalDate.of(2019, 9, 12), LocalDate.of(2019, 9, 17), laegemiddel, 3);
        assertEquals(pn.doegnDosis(), 0, 0.001);
        pn.givDosis(LocalDate.of(2019, 9, 12));
        assertEquals(pn.doegnDosis(), 0.5, 0.001);
        pn.givDosis(LocalDate.of(2019, 9, 12));
        pn.givDosis(LocalDate.of(2019, 9, 12));
        pn.givDosis(LocalDate.of(2019, 9, 12));
        pn.givDosis(LocalDate.of(2019, 9, 12));
        assertEquals(pn.doegnDosis(), 2.5, 0.001);
    }
}
