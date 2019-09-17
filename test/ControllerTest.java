import controller.Controller;
import ordination.DagligFast;
import ordination.DagligSkaev;
import ordination.Laegemiddel;
import ordination.PN;
import ordination.Patient;
import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.Before;
import org.junit.Test;

public class ControllerTest {

    private Laegemiddel laegemiddel;
    private Patient patient;
    private Controller controller;

    @Before
    public void setUp() throws Exception {
        controller = Controller.getTestController();
    }

    @Test
    public void controllerDagligFastTest() {
        laegemiddel = new Laegemiddel("Estrogen", 1, 2, 3, "ml");
        patient = new Patient("12091980 2222", "Bob", 80);

        DagligFast df = controller.opretDagligFastOrdination(LocalDate.of(2019, 9, 12), LocalDate.of(2019, 9, 17),
                patient, laegemiddel, 1, 2, 1, 1);

        assertNotNull(df);

        assertEquals(df.getStartDen(), LocalDate.of(2019, 9, 12));

        assertEquals(df.getSlutDen(), LocalDate.of(2019, 9, 17));

        assertEquals(df.getLaegemiddel(), laegemiddel);

        assertEquals(df.getDoser()[0].getAntal(), 1, 0.001);

        assertEquals(df.getDoser()[1].getAntal(), 2, 0.001);

        assertEquals(df.getDoser()[2].getAntal(), 1, 0.001);

        assertEquals(df.getDoser()[3].getAntal(), 1, 0.001);

    }

    @Test
    public void controllerDagligFastTest2() {

        laegemiddel = new Laegemiddel("Estrogen", 1, 2, 3, "ml");
        patient = new Patient("12091980 2222", "Bob", 80);

        DagligFast df = controller.opretDagligFastOrdination(LocalDate.of(2019, 9, 12), LocalDate.of(2019, 9, 17),
                patient, laegemiddel, 1, 0, 0, 1);

        assertNotNull(df);

        assertEquals(df.getStartDen(), LocalDate.of(2019, 9, 12));

        assertEquals(df.getSlutDen(), LocalDate.of(2019, 9, 17));
        
        assertEquals(df.getLaegemiddel(), laegemiddel);

        assertEquals(df.getDoser()[0].getAntal(), 1, 0.001);

        assertEquals(df.getDoser()[1].getAntal(), 0, 0.001);

        assertEquals(df.getDoser()[2].getAntal(), 0, 0.001);

        assertEquals(df.getDoser()[3].getAntal(), 1, 0.001);

    }

    @Test
    public void controllerDagligFastTest3() {
        laegemiddel = new Laegemiddel("Estrogen", 1, 2, 3, "ml");
        patient = new Patient("12091980 2222", "Bob", 80);

        try {
            DagligFast df = controller.opretDagligFastOrdination(LocalDate.of(2019, 9, 14), LocalDate.of(2019, 9, 12),
                    patient, laegemiddel, 1, 2, 1, 1);
            fail();

        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "Slutdatoen må ikke komme før startdatoen");
        }

    }

    @Test
    public void controllerDagligSkaevTest1() {
        laegemiddel = new Laegemiddel("Kryptonit", 1, 2, 3, "ml");
        patient = new Patient("12091980 2222", "Bob", 80);
        LocalTime[] klokkeSlet = { LocalTime.of(7, 0), LocalTime.of(10, 0), LocalTime.of(14, 15) };
        double[] antalenheder = { 5.5, 1, 2 };

        DagligSkaev ds = controller.opretDagligSkaevOrdination(LocalDate.of(2019, 9, 12), LocalDate.of(2019, 9, 17),
                patient, laegemiddel, klokkeSlet, antalenheder);

        assertNotNull(ds);

        assertEquals(ds.getStartDen(), LocalDate.of(2019, 9, 12));

        assertEquals(ds.getSlutDen(), LocalDate.of(2019, 9, 17));

        // Patient test??

        assertEquals(ds.getLaegemiddel(), laegemiddel);

        assertEquals(ds.getDoser().get(0).getTid().getHour(), 7);
        assertEquals(ds.getDoser().get(0).getTid().getMinute(), 0);
        assertEquals(ds.getDoser().get(0).getAntal(), 5.5, 0.001);

        assertEquals(ds.getDoser().get(1).getTid().getHour(), 10);
        assertEquals(ds.getDoser().get(1).getTid().getMinute(), 0);
        assertEquals(ds.getDoser().get(1).getAntal(), 1, 0.001);

        assertEquals(ds.getDoser().get(2).getTid().getHour(), 14);
        assertEquals(ds.getDoser().get(2).getTid().getMinute(), 15);
        assertEquals(ds.getDoser().get(2).getAntal(), 2, 0.001);

    }

    @Test
    public void controllerDagligSkaevTest2() {
        laegemiddel = new Laegemiddel("Kryptonit", 1, 2, 3, "ml");
        patient = new Patient("12091980 2222", "Bob", 80);
        LocalTime[] klokkeSlet = { LocalTime.of(7, 0), LocalTime.of(14, 15) };
        double[] antalenheder = { 5.5, 1, 2 };

        try {
            DagligSkaev ds = controller.opretDagligSkaevOrdination(LocalDate.of(2019, 9, 12), LocalDate.of(2019, 9, 17),
                    patient, laegemiddel, klokkeSlet, antalenheder);
            fail();

        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "Antal klokkeslæt og antal enheder skal have samme");
        }

    }

    @Test
    public void controllerDagligSkaevTest3() {
        laegemiddel = new Laegemiddel("Kryptonit", 1, 2, 3, "ml");
        patient = new Patient("12091980 2222", "Bob", 80);
        LocalTime[] klokkeSlet = { LocalTime.of(7, 0), LocalTime.of(10, 0), LocalTime.of(14, 15) };
        double[] antalenheder = { 5.5, 1, 2 };

        try {
            DagligSkaev ds = controller.opretDagligSkaevOrdination(LocalDate.of(2019, 9, 15), LocalDate.of(2019, 9, 12),
                    patient, laegemiddel, klokkeSlet, antalenheder);
            fail();

        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "Slutdatoen må ikke komme før startdatoen");
        }
    }

    @Test
    public void controllerOpretPNTest1() {
        laegemiddel = new Laegemiddel("Bacon", 1, 2, 3, "ml");
        patient = new Patient("12091980 2222", "Bob", 80);

        PN PN = controller.opretPNOrdination(LocalDate.of(2019, 9, 12), LocalDate.of(2019, 9, 17), patient, laegemiddel,
                5);

        assertNotNull(PN);

        assertEquals(PN.getStartDen(), LocalDate.of(2019, 9, 12));

        assertEquals(PN.getSlutDen(), LocalDate.of(2019, 9, 17));

        // Patient

        assertEquals(PN.getLaegemiddel(), laegemiddel);

        assertEquals(PN.getAntalEnheder(), 5, 0.001);

    }

    @Test
    public void controllerOpretPNTest2() {
        laegemiddel = new Laegemiddel("Bacon", 1, 2, 3, "ml");
        patient = new Patient("12091980 2222", "Bob", 80);

        try {
            PN PN = controller.opretPNOrdination(LocalDate.of(2019, 9, 12), LocalDate.of(2019, 9, 11), patient,
                    laegemiddel, 5);
            fail();

        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "Slutdatoen må ikke komme før startdatoen");
        }

    }

    @Test
    public void anbefaletDosisPrDoegnTest() {
        Laegemiddel kryptonit = controller.opretLaegemiddel("Kryptonit", 0.5, 1, 1.5, "??");

        Patient bob = controller.opretPatient("140592-1143", "Bob", 80);
        Patient mogens = controller.opretPatient("030285-2383", "Mogens", 130);
        Patient svend = controller.opretPatient("29102016-8391", "Svend", 20);

        assertEquals(80, controller.anbefaletDosisPrDoegn(bob, kryptonit), 0.001);
        assertEquals(195, controller.anbefaletDosisPrDoegn(mogens, kryptonit), 0.001);
        assertEquals(10, controller.anbefaletDosisPrDoegn(svend, kryptonit), 0.001);
    }

    @Test
    public void controllerAntalOrdinationerPrVægtPrLægemiddelTest1() {
        Laegemiddel l1 = new Laegemiddel("Estrogen", 1, 2, 3, "ml");
        Laegemiddel l2 = new Laegemiddel("Kryptonit", 1, 2, 3, "ml");

        Patient p1 = controller.opretPatient("123456 1234", "Bob", 80);
        Patient p2 = controller.opretPatient("123456 1235", "Mogens", 130);
        Patient p3 = controller.opretPatient("123456 1236", "Svend", 20);

        DagligFast df1 = controller.opretDagligFastOrdination(LocalDate.of(2019, 9, 10), LocalDate.of(2019, 9, 20), p1,
                l1, 1, 1, 1, 1);
        DagligFast df2 = controller.opretDagligFastOrdination(LocalDate.of(2019, 9, 10), LocalDate.of(2019, 9, 20), p2,
                l1, 1, 1, 1, 1);
        DagligFast df3 = controller.opretDagligFastOrdination(LocalDate.of(2019, 9, 10), LocalDate.of(2019, 9, 20), p3,
                l1, 1, 1, 1, 1);

        double faktiskVærdi = controller.antalOrdinationerPrVægtPrLægemiddel(70, 140, l1);

        assertEquals(2, faktiskVærdi, 0.001);

    }

    @Test
    public void controllerAntalOrdinationerPrVægtPrLægemiddelTest2() {
        Laegemiddel l1 = new Laegemiddel("Estrogen", 1, 2, 3, "ml");
        Laegemiddel l2 = new Laegemiddel("Kryptonit", 1, 2, 3, "ml");

        Patient p1 = controller.opretPatient("123456 1234", "Bob", 80);
        Patient p2 = controller.opretPatient("123456 1235", "Mogens", 130);
        Patient p3 = controller.opretPatient("123456 1236", "Svend", 20);

        DagligFast df1 = controller.opretDagligFastOrdination(LocalDate.of(2019, 9, 10), LocalDate.of(2019, 9, 20), p1,
                l1, 1, 1, 1, 1);
        DagligFast df2 = controller.opretDagligFastOrdination(LocalDate.of(2019, 9, 10), LocalDate.of(2019, 9, 20), p2,
                l1, 1, 1, 1, 1);
        DagligFast df3 = controller.opretDagligFastOrdination(LocalDate.of(2019, 9, 10), LocalDate.of(2019, 9, 20), p3,
                l1, 1, 1, 1, 1);

        double faktiskVærdi = controller.antalOrdinationerPrVægtPrLægemiddel(60, 70, l1);

        assertEquals(0, faktiskVærdi, 0.001);

    }

    @Test
    public void controllerAntalOrdinationerPrVægtPrLægemiddelTest3() {
        Laegemiddel l1 = new Laegemiddel("Estrogen", 1, 2, 3, "ml");
        Laegemiddel l2 = new Laegemiddel("Kryptonit", 1, 2, 3, "ml");

        Patient p1 = controller.opretPatient("123456 1234", "Bob", 80);
        Patient p2 = controller.opretPatient("123456 1235", "Mogens", 130);
        Patient p3 = controller.opretPatient("123456 1236", "Svend", 20);

        DagligFast df1 = controller.opretDagligFastOrdination(LocalDate.of(2019, 9, 10), LocalDate.of(2019, 9, 20), p1,
                l1, 1, 1, 1, 1);
        DagligFast df2 = controller.opretDagligFastOrdination(LocalDate.of(2019, 9, 10), LocalDate.of(2019, 9, 20), p2,
                l1, 1, 1, 1, 1);
        DagligFast df3 = controller.opretDagligFastOrdination(LocalDate.of(2019, 9, 10), LocalDate.of(2019, 9, 20), p3,
                l1, 1, 1, 1, 1);

        double faktiskVærdi = controller.antalOrdinationerPrVægtPrLægemiddel(10, 140, l1);

        assertEquals(3, faktiskVærdi, 0.001);

    }

    @Test
    public void controllerAntalOrdinationerPrVægtPrLægemiddelTest4() {
        Laegemiddel l1 = new Laegemiddel("Estrogen", 1, 2, 3, "ml");
        Laegemiddel l2 = new Laegemiddel("Kryptonit", 1, 2, 3, "ml");

        Patient p1 = controller.opretPatient("123456 1234", "Bob", 80);
        Patient p2 = controller.opretPatient("123456 1235", "Mogens", 130);
        Patient p3 = controller.opretPatient("123456 1236", "Svend", 20);

        DagligFast df1 = controller.opretDagligFastOrdination(LocalDate.of(2019, 9, 10), LocalDate.of(2019, 9, 20), p1,
                l1, 1, 1, 1, 1);
        DagligFast df2 = controller.opretDagligFastOrdination(LocalDate.of(2019, 9, 10), LocalDate.of(2019, 9, 20), p2,
                l1, 1, 1, 1, 1);
        DagligFast df3 = controller.opretDagligFastOrdination(LocalDate.of(2019, 9, 10), LocalDate.of(2019, 9, 20), p3,
                l1, 1, 1, 1, 1);

        double faktiskVærdi = controller.antalOrdinationerPrVægtPrLægemiddel(10, 140, l2);

        assertEquals(0, faktiskVærdi, 0.001);

    }

    @Test
    public void ordinationerPNAnvendtExceptionTest() {
        Laegemiddel kryptonit = controller.opretLaegemiddel("Kryptonit", 0.5, 1, 1.5, "??");
        Patient bob = controller.opretPatient("140592-1143", "Bob", 80);
        PN pn = controller.opretPNOrdination(LocalDate.of(2019, 9, 12), LocalDate.of(2019, 10, 12), bob, kryptonit, 2);

        assertEquals(true, controller.ordinationPNAnvendt(pn, LocalDate.of(2019, 9, 13)));
    }

    @Test
    public void ordinationerPNAnvendtExceptionTekst() {
        Laegemiddel kryptonit = controller.opretLaegemiddel("Kryptonit", 0.5, 1, 1.5, "??");
        Patient bob = controller.opretPatient("140592-1143", "Bob", 80);
        PN pn = controller.opretPNOrdination(LocalDate.of(2019, 9, 12), LocalDate.of(2019, 10, 12), bob, kryptonit, 2);
        try {
            controller.ordinationPNAnvendt(pn, LocalDate.of(2019, 10, 15));
            fail();
        } catch (RuntimeException e) {
            assertEquals(e.getMessage(), "Den valgte andvendelse er uden for ordinationens gyldighedsperiode");
        }
    }

}
