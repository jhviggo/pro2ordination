package controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import ordination.*;
import storage.Storage;

public class Controller {
    private Storage storage;
    private static Controller controller;

    private Controller() {
        storage = new Storage();
    }

    public static Controller getController() {
        if (controller == null) {
            controller = new Controller();
        }
        return controller;
    }

    public static Controller getTestController() {
        return new Controller();
    }

    /**
     * Hvis startDato er efter slutDato kastes en IllegalArgumentException og
     * ordinationen oprettes ikke
     * Pre: startDen, slutDen, patient og laegemiddel er ikke null
     *
     * @return opretter og returnerer en PN ordination.
     */
    public PN opretPNOrdination(LocalDate startDen, LocalDate slutDen,
        Patient patient, Laegemiddel laegemiddel, double antal) {

        if (!this.checkStartFoerSlut(startDen, slutDen)) {
            throw new IllegalArgumentException("Slutdatoen må ikke komme før startdatoen");
        }

        PN ordination = new PN(startDen, slutDen, laegemiddel, antal);
        patient.addOrdination(ordination);

        return ordination;
    }

    /**
     * Opretter og returnerer en DagligFast ordination. Hvis startDato er efter
     * slutDato kastes en IllegalArgumentException og ordinationen oprettes ikke
     * Pre: startDen, slutDen, patient og laegemiddel er ikke null
     */
    public DagligFast opretDagligFastOrdination(LocalDate startDen,
        LocalDate slutDen, Patient patient, Laegemiddel laegemiddel,
        double morgenAntal, double middagAntal, double aftenAntal,
        double natAntal) {

        if (!this.checkStartFoerSlut(startDen, slutDen)) {
            throw new IllegalArgumentException("Slutdatoen må ikke komme før startdatoen");
        }

        DagligFast ordination = new DagligFast(startDen, slutDen, laegemiddel);
        ordination.addDosis(morgenAntal, middagAntal, aftenAntal, natAntal);
        patient.addOrdination(ordination);

        return ordination;
    }

    /**
     * Opretter og returnerer en DagligSkæv ordination. Hvis startDato er efter
     * slutDato kastes en IllegalArgumentException og ordinationen oprettes ikke.
     * Hvis antallet af elementer i klokkeSlet og antalEnheder er forskellige kastes også en IllegalArgumentException.
     *
     * Pre: startDen, slutDen, patient og laegemiddel er ikke null
     */
    public DagligSkaev opretDagligSkaevOrdination(LocalDate startDen,
        LocalDate slutDen, Patient patient, Laegemiddel laegemiddel,
        LocalTime[] klokkeSlet, double[] antalEnheder) {

        if (!this.checkStartFoerSlut(startDen, slutDen)) {
            throw new IllegalArgumentException("Slutdatoen må ikke komme før startdatoen");
        }

        if (klokkeSlet.length != antalEnheder.length) {
            throw new IllegalArgumentException("Antal klokkeslæt og antal enheder skal have samme");
        }

        DagligSkaev ordination = new DagligSkaev(startDen, slutDen, laegemiddel);
        ordination.opretDosis(klokkeSlet, antalEnheder);
        patient.addOrdination(ordination);

        return ordination;
    }

    /**
     * En dato for hvornår ordinationen anvendes tilføjes ordinationen. Hvis
     * datoen ikke er indenfor ordinationens gyldighedsperiode kastes en
     * IllegalArgumentException
     * Pre: ordination og dato er ikke null
     */
    public boolean ordinationPNAnvendt(PN ordination, LocalDate dato) {
        if(!this.checkStartFoerSlut(ordination.getStartDen(), dato) || !this.checkStartFoerSlut(dato, ordination.getSlutDen())) {
            throw new IllegalArgumentException("Den valgte andvendelse er uden for ordinationens gyldighedsperiode");
        }
        return ordination.givDosis(dato);
    }

    /**
     * Den anbefalede dosis for den pågældende patient (der skal tages hensyn
     * til patientens vægt). Det er en forskellig enheds faktor der skal
     * anvendes, og den er afhængig af patientens vægt.
     * Pre: patient og lægemiddel er ikke null
     */
    public double anbefaletDosisPrDoegn(Patient patient, Laegemiddel laegemiddel) {
        double result;
        if (patient.getVaegt() < 25) {
            result = patient.getVaegt() * laegemiddel.getEnhedPrKgPrDoegnLet();
        }
        else if (patient.getVaegt() > 120) {
            result = patient.getVaegt() * laegemiddel.getEnhedPrKgPrDoegnTung();
        }
        else {
            result = patient.getVaegt()
                * laegemiddel.getEnhedPrKgPrDoegnNormal();
        }
        return result;
    }

    /**
     * For et givent vægtinterval og et givent lægemiddel, hentes antallet af
     * ordinationer.
     * Pre: laegemiddel er ikke null
     */
    public int antalOrdinationerPrVægtPrLægemiddel(double vægtStart,
        double vægtSlut, Laegemiddel laegemiddel) {
        int sum = 0;
        for (Patient p : getAllPatienter()) {
            if (vægtStart <= p.getVaegt() && p.getVaegt() <= vægtSlut) {
                for (Ordination o : p.getOrdinationer()) {
                    if (o.getLaegemiddel().equals(laegemiddel)) {
                        sum++;
                    }
                }
            }
        }
        return sum;
    }

    public List<Patient> getAllPatienter() {
        return storage.getAllPatienter();
    }

    public List<Laegemiddel> getAllLaegemidler() {
        return storage.getAllLaegemidler();
    }

    /**
     * Metode der kan bruges til at checke at en startDato ligger før en
     * slutDato.
     *
     * @return true hvis startDato er før slutDato, false ellers.
     */
    private boolean checkStartFoerSlut(LocalDate startDato, LocalDate slutDato) {
        boolean result = true;
        if (slutDato.compareTo(startDato) < 0) {
            result = false;
        }
        return result;
    }

    public Patient opretPatient(String cpr, String navn, double vaegt) {
        Patient p = new Patient(cpr, navn, vaegt);
        storage.addPatient(p);
        return p;
    }

    public Laegemiddel opretLaegemiddel(String navn,
        double enhedPrKgPrDoegnLet, double enhedPrKgPrDoegnNormal,
        double enhedPrKgPrDoegnTung, String enhed) {
        Laegemiddel lm = new Laegemiddel(navn, enhedPrKgPrDoegnLet,
            enhedPrKgPrDoegnNormal, enhedPrKgPrDoegnTung, enhed);
        storage.addLaegemiddel(lm);
        return lm;
    }

    public void createSomeObjects() {
        opretPatient("121256-0512", "Jane Jensen", 63.4);
        opretPatient("070985-1153", "Finn Madsen", 83.2);
        opretPatient("050972-1233", "Hans Jørgensen", 89.4);
        opretPatient("011064-1522", "Ulla Nielsen", 59.9);
        opretPatient("090149-2529", "Ib Hansen", 87.7);

        opretLaegemiddel("Acetylsalicylsyre", 0.1, 0.15, 0.16, "Styk");
        opretLaegemiddel("Paracetamol", 1, 1.5, 2, "Ml");
        opretLaegemiddel("Fucidin", 0.025, 0.025, 0.025, "Styk");
        opretLaegemiddel("Methotrexat", 0.01, 0.015, 0.02, "Styk");

        opretPNOrdination(LocalDate.of(2019, 1, 1), LocalDate.of(2019, 1, 12),
            storage.getAllPatienter().get(0), storage.getAllLaegemidler()
                .get(1),
            123);

        opretPNOrdination(LocalDate.of(2019, 2, 12), LocalDate.of(2019, 2, 14),
            storage.getAllPatienter().get(0), storage.getAllLaegemidler()
                .get(0),
            3);

        opretPNOrdination(LocalDate.of(2019, 1, 20), LocalDate.of(2019, 1, 25),
            storage.getAllPatienter().get(3), storage.getAllLaegemidler()
                .get(2),
            5);

        opretPNOrdination(LocalDate.of(2019, 1, 1), LocalDate.of(2019, 1, 12),
            storage.getAllPatienter().get(0), storage.getAllLaegemidler()
                .get(1),
            123);

        opretDagligFastOrdination(LocalDate.of(2019, 1, 10),
            LocalDate.of(2019, 1, 12), storage.getAllPatienter().get(1),
            storage.getAllLaegemidler().get(1), 2, -1, 1, -1);

        LocalTime[] kl = { LocalTime.of(12, 0), LocalTime.of(12, 40),
            LocalTime.of(16, 0), LocalTime.of(18, 45) };
        double[] an = { 0.5, 1, 2.5, 3 };

        opretDagligSkaevOrdination(LocalDate.of(2019, 1, 23),
            LocalDate.of(2019, 1, 24), storage.getAllPatienter().get(1),
            storage.getAllLaegemidler().get(2), kl, an);
    }

}
