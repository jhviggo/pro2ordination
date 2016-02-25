package service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import ordination.DagligFast;
import ordination.DagligSkaev;
import ordination.Laegemiddel;
import ordination.PN;
import ordination.Patient;
import storage.Storage;

public class Service {
	private Storage storage;
	private static Service service;

	private Service() {
		storage = new Storage();
	}

	public static Service getService() {
		if (service == null) {
			service = new Service();
		}
		return service;
	}

	public static Service getTestService() {
		return new Service();
	}

	/**
	 * Hvis startDato er efter slutDato kastes en IllegalArgumentException og
	 * ordinationen oprettes ikke
	 *
	 * @return opretter og returnerer en PN ordination.
	 */
	public PN opretPNOrdination(LocalDate startDen, LocalDate slutDen,
			Patient patient, Laegemiddel laegemiddel, double antal) {
		// TODO
		return null;
	}

	/**
	 * Opretter og returnerer en DagligFast ordination. Hvis startDato er efter
	 * slutDato kastes en IllegalArgumentException og ordinationen oprettes ikke
	 */
	public DagligFast opretDagligFastOrdination(LocalDate startDen,
			LocalDate slutDen, Patient patient, Laegemiddel laegemiddel,
			double morgenAntal, double middagAntal, double aftenAntal,
			double natAntal) {
		// TODO
		return null;
	}

	/**
	 * Opretter og returnerer en DagligSkæv ordination. Hvis startDato er efter
	 * slutDato kastes en IllegalArgumentException og ordinationen oprettes ikke
	 */
	public DagligSkaev opretDagligSkaevOrdination(LocalDate startDen,
			LocalDate slutDen, Patient patient, Laegemiddel laegemiddel,
			LocalTime[] klokkeSlet, double[] antalEnheder) {
		// TODO
		return null;
	}

	/**
	 * En dato for hvornår ordinationen anvendes tilføjes ordinationen. Hvis
	 * datoen ikke er indenfor ordinationens gyldighedsperiode kastes en
	 * IllegalArgumentException
	 */
	public void ordinationPNAnvendt(PN ordination, LocalDate dato) {
		// TODO
	}

	/**
	 * Den anbefalede dosis for den pågældende patient (der skal tages hensyn
	 * til patientens vægt). Det er en forskellig enheds faktor der skal
	 * anvendes, og den er afhængig af patientens vægt.
	 */
	public double anbefaletDosisPrDoegn(Patient patient, Laegemiddel laegemiddel) {
		double result;
		if (patient.getVaegt() < 25) {
			result = patient.getVaegt() * laegemiddel.getEnhedPrKgPrDoegnLet();
		} else if (patient.getVaegt() > 120) {
			result = patient.getVaegt() * laegemiddel.getEnhedPrKgPrDoegnTung();
		} else {
			result = patient.getVaegt()
					* laegemiddel.getEnhedPrKgPrDoegnNormal();
		}
		return result;
	}

	/**
	 * For et givent vægtinterval og et givent lægemiddel, hentes antallet af
	 * ordinationer.
	 */
	public int antalOrdinationerPrVægtPrLægemiddel(double vægtStart,
			double vægtSlut, Laegemiddel laegemiddel) {
		// TODO
		return 0;
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

    public void createSomeObjects() {
        Storage.gemPatient(new Patient("Jane Jensen", "121256-0512", 63.4));
        Storage.gemPatient(new Patient("Finn Madsen", "070985-1153", 83.2));
        Storage.gemPatient(new Patient("Hans Jørgensen", "050972-1233", 89.4));
        Storage.gemPatient(new Patient("Ulla Nielsen", "011064-1522", 59.9));
        Storage.gemPatient(new Patient("Ib Hansen", "090149-2529", 87.7));

        Storage.gemLaegemiddel(new Laegemiddel("Acetylsalicylsyre", 0.1, 0.15, 0.16, "Styk"));
        Storage.gemLaegemiddel(new Laegemiddel("Paracetamol", 1, 1.5, 2, "Ml"));
        Storage.gemLaegemiddel(new Laegemiddel("Fucidin", 0.025, 0.025, 0.025,
                "Styk"));
        Storage.gemLaegemiddel(new Laegemiddel("Methotrexat", 0.01, 0.015, 0.02, "Styk"));

        opretPNOrdination(LocalDate.of(2015, 1, 1),
                LocalDate.of(2015, 1, 12), Storage.getAllPatienter().get(0),
                Storage.getAllLaegemidler().get(1), 123);

        opretPNOrdination(LocalDate.of(2015, 2, 12),
                LocalDate.of(2015, 2, 14), Storage.getAllPatienter().get(0),
                Storage.getAllLaegemidler().get(0), 3);

        opretPNOrdination(LocalDate.of(2015, 1, 20),
                LocalDate.of(2015, 1, 25), Storage.getAllPatienter().get(3),
                Storage.getAllLaegemidler().get(2), 5);

        opretPNOrdination(LocalDate.of(2015, 1, 1),
                LocalDate.of(2015, 1, 12), Storage.getAllPatienter().get(0),
                Storage.getAllLaegemidler().get(1), 123);

        opretDagligFastOrdination(LocalDate.of(2015, 1, 10),
                LocalDate.of(2015, 1, 12), Storage.getAllPatienter().get(1),
                Storage.getAllLaegemidler().get(1), 2, -1, 1, -1);

        LocalTime[] kl = { LocalTime.of(12, 0), LocalTime.of(12, 40), LocalTime.of(16, 0), LocalTime.of(18, 45) };
        double[] an = { 0.5, 1, 2.5, 3 };

        opretDagligSkaevOrdination(LocalDate.of(2015, 1, 23),
                LocalDate.of(2015, 1, 24), Storage.getAllPatienter().get(1),
                Storage.getAllLaegemidler().get(2), kl, an);
    }

}
